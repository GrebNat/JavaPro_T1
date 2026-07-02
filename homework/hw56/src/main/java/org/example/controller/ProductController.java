package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.ProductResponseDto;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@ResponseBody
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{id}")
    public ProductResponseDto getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping(value = "/user/{userId}")
    @Transactional
    public List<ProductResponseDto> getProductsByUserId(@PathVariable Integer userId) {
        return productService.getProductsByUserId(userId);
    }
}
