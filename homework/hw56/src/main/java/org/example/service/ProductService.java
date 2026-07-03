package org.example.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.dto.ProductResponseDto;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @SneakyThrows
    public ProductResponseDto getProductById(Integer id) {
        return productRepository.findById(id)
                .map(x -> new ProductResponseDto(x.getId(), x.getAccount(), x.getBalance(), x.getProductType(), x.getUser()))
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Transactional
    public List<ProductResponseDto> getProductsByUserId(Integer userId) {
        return productRepository
                .findByUserId(userId).stream()
                .map(x -> new ProductResponseDto(x.getId(), x.getAccount(), x.getBalance(), x.getProductType(), x.getUser()))
                .toList();
    }
}
