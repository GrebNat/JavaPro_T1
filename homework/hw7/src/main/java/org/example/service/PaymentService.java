package org.example.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.dto.PaymentRequestDto;
import org.example.dto.ProductResponseDto;
import org.example.dto.UpdateProductAmountRequestDto;
import org.example.exception.InvalidPaymentAmountException;
import org.example.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentService {

    private final RestTemplate restTemplate;
    private final String productServiceUri = "http://localhost:8989/app/v1/products/";

    public ProductResponseDto getProductById(Integer id) {

        String productServiceUrl = productServiceUri + id;

        try {
            ProductResponseDto product = restTemplate.getForObject(
                    productServiceUrl,
                    ProductResponseDto.class
            );
            return product;
        } catch (HttpServerErrorException.InternalServerError e) {
            throw new ProductNotFoundException("Продукт не найден");
        }
    }

    public void executePayment(@NonNull PaymentRequestDto paymentRequest) {
        ProductResponseDto product = getProductById(paymentRequest.productId());

        BigDecimal actualBalance = product.balance();
        if (paymentRequest.paymentValue().compareTo(actualBalance) >= 0)
            throw new InvalidPaymentAmountException("На счете недостаточно средств");

        String productServiceUrl = productServiceUri + "save/" + paymentRequest.productId();

        restTemplate.postForObject(
                productServiceUrl,
                new UpdateProductAmountRequestDto(paymentRequest.paymentValue()),
                Void.class
        );
    }
}
