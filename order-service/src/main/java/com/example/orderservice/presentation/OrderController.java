package com.example.orderservice.presentation;

import com.example.orderservice.application.OrderService;
import com.example.orderservice.dto.OrderCreateCommand;
import com.example.orderservice.dto.OrderCreateRequest;
import com.example.orderservice.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody OrderCreateRequest request) {
        OrderCreateCommand command = OrderCreateCommand.builder()
                .userId(UUID.fromString(userId))
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderResponse.from(orderService.create(command)));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAll().stream()
                .map(OrderResponse::from)
                .toList());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(OrderResponse.from(orderService.findById(orderId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> findByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(orderService.findByUserId(userId).stream()
                .map(OrderResponse::from)
                .toList());
    }
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        orderService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
