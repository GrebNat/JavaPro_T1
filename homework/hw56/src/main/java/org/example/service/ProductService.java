package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.dto.ProductResponseDto;
import org.example.dto.UpdateProductBalanceRequestDto;
import org.example.exception.InvalidPaymentAmountException;
import org.example.exception.ProductNotFoundException;
import org.example.repository.ProductRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @SneakyThrows
    public ProductResponseDto getProductById(Integer id) {
        return productRepository.findById(id)
                .map(x -> new ProductResponseDto(x.getId(), x.getAccount(), x.getBalance(), x.getProductType(), x.getUser()))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional
    public List<ProductResponseDto> getProductsByUserId(Integer userId) {
        return productRepository
                .findByUserId(userId).stream()
                .map(x -> new ProductResponseDto(x.getId(), x.getAccount(), x.getBalance(), x.getProductType(), x.getUser()))
                .toList();
    }

    public void pay(Integer productId, UpdateProductBalanceRequestDto amountRequest) {
        ProductResponseDto product = getProductById(productId);

        BigDecimal actualBalance = product.balance();
        if (amountRequest.amount().compareTo(actualBalance) >= 0)
            throw new InvalidPaymentAmountException();

        BigDecimal newBalance = actualBalance.subtract(amountRequest.amount());

        productRepository.updateProductBalance(productId, newBalance);
    }
}
