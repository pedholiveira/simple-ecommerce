package com.tv.demo.simpleecommerce.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tv.demo.simpleecommerce.dto.ProductDto;
import com.tv.demo.simpleecommerce.dto.Views;
import com.tv.demo.simpleecommerce.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    List<ProductDto> listProducts() {
        return service.listProducts();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    ProductDto findProductById(@PathVariable UUID productId) {
        return service.findProductById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Read.class)
    ProductDto createProduct(
            @JsonView(Views.Create.class) @RequestBody ProductDto product) {
        return service.createProduct(product);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    ProductDto updateProduct(@PathVariable UUID productId,
                             @JsonView(Views.Update.class) @RequestBody ProductDto product) {
        return service.updateProduct(productId, product);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(@PathVariable UUID productId) {
        service.deleteProduct(productId);
    }
}
