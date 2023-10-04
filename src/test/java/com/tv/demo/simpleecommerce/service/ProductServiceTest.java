package com.tv.demo.simpleecommerce.service;

import com.tv.demo.simpleecommerce.dto.ProductDto;
import com.tv.demo.simpleecommerce.model.Product;
import com.tv.demo.simpleecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
public class ProductServiceTest extends PostgresContainerTest {

    @Autowired
    ProductRepository repository;
    @Autowired
    ProductService service;

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void testListProducts() {
        addProduct();
        var result = service.listProducts();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testListProducts_whenTableIsEmpty() {
        var result = service.listProducts();
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testFindProductById() {
        var product = addProduct();
        var result = service.findProductById(product.getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(product.getId());
    }

    @Test
    public void testFindProductById_whenNoProductIsFound() {
        var id = UUID.randomUUID();
        Assertions.assertThatThrownBy(() -> service.findProductById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testCreateProduct() {
        var dto = ProductDto.builder()
                .name("Product 1")
                .description("Description of product 1")
                .price(BigDecimal.TEN)
                .build();

        var result = service.createProduct(dto);

        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(dto.getName());
        Assertions.assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        Assertions.assertThat(result.getPrice()).isEqualTo(dto.getPrice());
    }

    @Test
    public void testCreateProduct_whenNameIsNull() {
        var dto = ProductDto.builder()
                .description("Description of product 1")
                .price(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> service.createProduct(dto))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Name");
    }

    @Test
    public void testCreateProduct_whenDescriptionIsNull() {
        var dto = ProductDto.builder()
                .name("Product 1")
                .price(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> service.createProduct(dto))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Description");
    }

    @Test
    public void testCreateProduct_whenPriceIsNull() {
        var dto = ProductDto.builder()
                .name("Product 1")
                .description("Description of product 1")
                .build();

        Assertions.assertThatThrownBy(() -> service.createProduct(dto))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Price");
    }

    @Test
    public void testUpdateProduct() {
        var id = addProduct().getId();
        var dto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Product name updated")
                .description("Description updated")
                .price(BigDecimal.valueOf(123.43))
                .build();

        var result = service.updateProduct(id, dto);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(id);
        Assertions.assertThat(result.getName()).isEqualTo(dto.getName());
        Assertions.assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        Assertions.assertThat(result.getPrice()).isEqualTo(dto.getPrice());
    }

    @Test
    public void testUpdateProduct_whenProductIsNotFound() {
        var id = UUID.randomUUID();
        var dto = ProductDto.builder()
                .name("Product 1")
                .description("Description of product 1")
                .price(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> service.updateProduct(id, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testDeleteProduct() {
        var product = addProduct();

        service.deleteProduct(product.getId());
        Assertions.assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void testDeleteProduct_whenProductIsNotFound() {
        var id = UUID.randomUUID();

        Assertions.assertThatThrownBy(() -> service.deleteProduct(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    private Product addProduct() {
        var product = new Product();
        product.setName("Product 1");
        product.setDescription("Description");
        product.setPrice(BigDecimal.TEN);
        return repository.save(product);
    }
}
