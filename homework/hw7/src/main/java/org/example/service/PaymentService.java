package org.example.service;

import lombok.NonNull;
import org.example.dto.PaymentRequestDto;
import org.example.dto.ProductResponseDto;

import org.example.dto.UpdateProductAmountRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String productServiceUri = "/v1/products/";

    public ProductResponseDto getProductById(Integer id) {

        String productServiceUrl = productServiceUri + id;

        return restTemplate.getForObject(
                productServiceUrl,
                ProductResponseDto.class
        );
    }

    public void executePayment(@NonNull PaymentRequestDto paymentRequest) {
        String productServiceUrl = productServiceUri + "pay/" + paymentRequest.productId();

        restTemplate.postForObject(
                productServiceUrl,
                new UpdateProductAmountRequestDto(paymentRequest.paymentValue()),
                Void.class
        );
    }
}
