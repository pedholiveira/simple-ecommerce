package com.tv.demo.simpleecommerce.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tv.demo.simpleecommerce.dto.ErrorResponse;
import com.tv.demo.simpleecommerce.dto.ProductDto;
import com.tv.demo.simpleecommerce.dto.Views;
import com.tv.demo.simpleecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Products API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService service;

    @Operation(summary = "List products", description = "Returns a list with all persisted products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully listed.")
    })
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    List<ProductDto> listProducts() {
        return service.listProducts();
    }

    @Operation(summary = "Get product", description = "Returns a product by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully obtained."),
            @ApiResponse(responseCode = "404", description = "The product was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping(value = "/{productId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    ProductDto findProductById(@PathVariable UUID productId) {
        return service.findProductById(productId);
    }

    @Operation(summary = "Create product", description = "Creates a new product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created.")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Read.class)
    ProductDto createProduct(
            @JsonView(Views.Create.class) @RequestBody ProductDto product) {
        return service.createProduct(product);
    }

    @Operation(summary = "Update product", description = "Updates product data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated."),
            @ApiResponse(responseCode = "404", description = "The product was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PutMapping(value = "/{productId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    ProductDto updateProduct(@PathVariable UUID productId,
                             @JsonView(Views.Update.class) @RequestBody ProductDto product) {
        return service.updateProduct(productId, product);
    }

    @Operation(summary = "Delete product", description = "Deletes a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted."),
            @ApiResponse(responseCode = "404", description = "The product was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(@PathVariable UUID productId) {
        service.deleteProduct(productId);
    }
}
