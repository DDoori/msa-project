package com.example.orderservice.application;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderCreateCommand;
import com.example.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public Order create(OrderCreateCommand command) {
        return orderRepository.save(command.toEntity());
    }

    @Transactional
    public Order findById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }

    @Transactional
    public Page<Order> findByUserId(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return orderRepository.findByUserId(userId, pageable);
    }

    public void cancel(UUID id) {
        Order order = findById(id);
        order.cancel();
    }
}