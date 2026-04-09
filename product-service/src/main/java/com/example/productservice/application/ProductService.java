package com.example.productservice.application;

import com.example.productservice.domain.Product;
import com.example.productservice.dto.ProductCreateCommand;
import com.example.productservice.dto.ProductCreateRequest;
import com.example.productservice.dto.ProductStockRequest;
import com.example.productservice.repository.ProductRepository;
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
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(ProductCreateCommand command) {
        Product product = command.toEntity();
        return productRepository.save(product);
    }

    @Transactional
    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    public Page<Product> findAll(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        if (name != null && !name.isBlank()) {
            return productRepository.findByNameContaining(name, pageable);
        }
        return productRepository.findAll(pageable);
    }

    public void decreaseStock(UUID id, ProductStockRequest request) {
        Product product = findById(id);
        product.decreaseStock(request.getStock());
    }

    public void increaseStock(UUID id, ProductStockRequest request) {
        Product product = findById(id);
        product.increaseStock(request.getStock());
    }
}
