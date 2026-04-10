package com.example.orderservice.application;

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

    public Order create(OrderCreateCommand command) {
        return orderRepository.save(command.toEntity());
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

    public void cancel(UUID id) {
        Order order = findById(id);
        order.cancel();
    }
}