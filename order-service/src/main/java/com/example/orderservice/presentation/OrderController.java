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

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreateRequest request) {
        OrderCreateCommand command = OrderCreateCommand.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderResponse.from(orderService.create(command)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(OrderResponse.from(orderService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> findByUserId(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.findByUserId(userId, page, size).map(OrderResponse::from));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        orderService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
