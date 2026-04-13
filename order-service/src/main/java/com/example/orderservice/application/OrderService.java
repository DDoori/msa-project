package com.example.orderservice.application;

import com.example.orderservice.client.NotificationClient;
import com.example.orderservice.client.ProductClient;
import com.example.orderservice.client.ProductResponse;
import com.example.orderservice.client.StockRequest;
import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderCreateCommand;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final NotificationClient notificationClient;

    public Order create(OrderCreateCommand command) {
        productClient.decreaseStock(command.getProductId(), new StockRequest(command.getQuantity()));
        Order order = orderRepository.save(command.toEntity());

        // 상품의 providerId 조회
        ProductResponse product = productClient.getProduct(command.getProductId());

        // PROVIDER에게 알림 전송
        notificationClient.sendNotification(new NotificationClient.NotificationRequest(
                product.getProviderId(),
                order.getId(),
                "ORDER_CREATED",
                "새 주문이 들어왔습니다. 주문 수량: " + command.getQuantity()
        ));

        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Order> findByUserId(UUID userId) {
        return orderRepository.findByUserId(userId);
    }


    @Transactional(readOnly = true)
    public List<Order> findByProvider(String userId) {
        List<ProductResponse> products = productClient.getProductsByProvider(userId);
        List<UUID> productIds = products.stream()
                .map(ProductResponse::getId)
                .toList();
        return orderRepository.findByProductIdIn(productIds);
    }

    public void cancel(UUID id) {
        Order order = findById(id);
        order.cancel();
        notificationClient.sendNotification(new NotificationClient.NotificationRequest(
                order.getUserId(),
                order.getId(),
                "ORDER_CANCELLED",
                "주문이 취소되었습니다."
        ));
    }

}