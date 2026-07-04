package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.PaymentRequestDto;
import org.example.dto.ProductResponseDto;
import org.example.service.PaymentService;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping(value = "/{id}")
    public ProductResponseDto getProductById(@PathVariable Integer id) {
        return paymentService.getProductById(id);
    }

    @PostMapping(value = "/pay")
    public void executePayment(@RequestBody PaymentRequestDto request) {
         paymentService.executePayment(request);
    }
}
