package com.tv.demo.simpleecommerce.controller;

import com.tv.demo.simpleecommerce.dto.ProductDto;
import com.tv.demo.simpleecommerce.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest extends BaseControllerTest {

    private static final String API_URL = "/api/products";

    @MockBean
    private ProductService service;

    @Test
    public void testListProductsApi() throws Exception {
        List<ProductDto> products = List.of(ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .description("Description of product 1")
                .price(BigDecimal.valueOf(35.99))
                .build());

        Mockito.when(service.listProducts()).thenReturn(products);
        makeGetRequest(API_URL).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetProductByIdApi() throws Exception {
        var id = UUID.randomUUID();
        var product = ProductDto.builder()
                .id(id)
                .name("Product 1")
                .description("Description of product 1")
                .price(BigDecimal.valueOf(35.99))
                .build();

        Mockito.when(service.findProductById(id)).thenReturn(product);
        makeGetRequest(API_URL + "/" + id).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetProductByIdApi_whenNoProductIsFound() throws Exception {
        var id = UUID.randomUUID();
        Mockito.when(service.findProductById(id)).thenThrow(new EntityNotFoundException());
        makeGetRequest(API_URL + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateProductApi() throws Exception {
        var request = ProductDto.builder()
                .name("Product 1")
                .description("Description of product 1")
                .price(BigDecimal.valueOf(35.99))
                .build();

        var response = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .description("Description of product 1")
                .price(BigDecimal.valueOf(35.99))
                .build();

        Mockito.when(service.createProduct(request)).thenReturn(response);

        makePostRequest(API_URL, mapper.writeValueAsString(request))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateProductApi() throws Exception {
        var id = UUID.randomUUID();
        var product = ProductDto.builder()
                .name("Product 1")
                .description("Description of product 1")
                .price(BigDecimal.valueOf(35.99))
                .build();

        Mockito.when(service.updateProduct(id, product)).thenReturn(product);

        makePutRequest(API_URL + "/" + id, mapper.writeValueAsString(product))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateProductApi_whenProductIsNotFound() throws Exception {
        var id = UUID.randomUUID();

        Mockito.when(service.updateProduct(Mockito.eq(id), Mockito.any(ProductDto.class)))
                .thenThrow(new EntityNotFoundException());

        makePutRequest(API_URL + "/" + id, mapper.writeValueAsString(ProductDto.builder().build()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteProductApi() throws Exception {
        var id = UUID.randomUUID();

        makeDeleteRequest(API_URL + "/" + id)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteProductApi_whenProductIsNotFound() throws Exception {
        var id = UUID.randomUUID();

        Mockito.doThrow(new EntityNotFoundException()).when(service).deleteProduct(id);
        makeDeleteRequest(API_URL + "/" + id)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
