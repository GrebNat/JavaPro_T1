package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.ProductResponseDto;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{id}")
    public ProductResponseDto getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping(value = "/user/{userId}")
    public List<ProductResponseDto> getProductsByUserId(@PathVariable Integer userId) {
        return productService.getProductsByUserId(userId);
    }
}
