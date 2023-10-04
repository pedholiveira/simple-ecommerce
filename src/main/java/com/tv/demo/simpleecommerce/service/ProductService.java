package com.tv.demo.simpleecommerce.service;

import com.tv.demo.simpleecommerce.dto.ProductDto;
import com.tv.demo.simpleecommerce.mapper.ProductMapper;
import com.tv.demo.simpleecommerce.model.Product;
import com.tv.demo.simpleecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductMapper mapper;
    ProductRepository repository;

    public List<ProductDto> listProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::createDtoFromModel)
                .toList();
    }

    public ProductDto findProductById(UUID productId) {
        return repository.findById(productId)
                .map(mapper::createDtoFromModel)
                .orElseThrow(() -> new EntityNotFoundException("No product was found with the given ID."));
    }

    @Transactional
    public ProductDto createProduct(@Valid ProductDto dto) {
        var product = repository.save(mapper.createModelFromDto(dto));
        return mapper.createDtoFromModel(product);
    }

    @Transactional
    public ProductDto updateProduct(UUID id, @Valid ProductDto dto) {
        var product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No product was found with the given ID."));
        mapper.updateModelFromDto(dto, product);

        var updatedProduct = repository.save(product);
        return mapper.createDtoFromModel(updatedProduct);
    }

    @Transactional
    public void deleteProduct(UUID id) {
        var product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No product was found with the given ID."));
        repository.delete(product);
    }
}
