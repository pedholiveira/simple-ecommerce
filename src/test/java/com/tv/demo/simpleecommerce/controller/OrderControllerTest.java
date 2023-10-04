package com.tv.demo.simpleecommerce.controller;

import com.tv.demo.simpleecommerce.dto.OrderDto;
import com.tv.demo.simpleecommerce.model.OrderStatus;
import com.tv.demo.simpleecommerce.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest extends BaseControllerTest {

    private static final String API_URL = "/api/orders";

    @MockBean
    private OrderService service;

    @Test
    public void testListOrdersApi() throws Exception {
        List<OrderDto> orders = List.of(OrderDto.builder()
                .id(UUID.randomUUID())
                .status(OrderStatus.IN_CREATION.name())
                .items(Collections.emptyList())
                .totalPrice(BigDecimal.ZERO)
                .build());

        Mockito.when(service.listOrders()).thenReturn(orders);
        makeGetRequest(API_URL).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetOrderByIdApi() throws Exception {
        var id = UUID.randomUUID();
        var order = OrderDto.builder()
                .id(id)
                .status(OrderStatus.IN_CREATION.name())
                .items(Collections.emptyList())
                .totalPrice(BigDecimal.ZERO)
                .build();

        Mockito.when(service.findOrderById(id)).thenReturn(order);
        makeGetRequest(API_URL + "/" + id).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetOrderByIdApi_whenNoOrderIsFound() throws Exception {
        var id = UUID.randomUUID();
        Mockito.when(service.findOrderById(id)).thenThrow(new EntityNotFoundException());
        makeGetRequest(API_URL + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateOrderApi() throws Exception {
        var order = OrderDto.builder()
                .id(UUID.randomUUID())
                .status(OrderStatus.IN_CREATION.name())
                .items(Collections.emptyList())
                .totalPrice(BigDecimal.ZERO)
                .build();

        Mockito.when(service.createOrder()).thenReturn(order);

        makePostRequest(API_URL).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateOrderApi() throws Exception {
        var id = UUID.randomUUID();
        var order = OrderDto.builder()
                .id(UUID.randomUUID())
                .status(OrderStatus.WAITING_PAYMENT.name())
                .items(Collections.emptyList())
                .totalPrice(BigDecimal.ZERO)
                .build();

        Mockito.when(service.updateOrder(id, order)).thenReturn(order);

        makePutRequest(API_URL + "/" + id, mapper.writeValueAsString(order))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateOrderApi_whenOrderIsNotFound() throws Exception {
        var id = UUID.randomUUID();

        Mockito.when(service.updateOrder(Mockito.eq(id), Mockito.any(OrderDto.class)))
                .thenThrow(new EntityNotFoundException());

        makePutRequest(API_URL + "/" + id, mapper.writeValueAsString(OrderDto.builder().build()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteOrderApi() throws Exception {
        var id = UUID.randomUUID();

        makeDeleteRequest(API_URL + "/" + id)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteOrderApi_whenOrderIsNotFound() throws Exception {
        var id = UUID.randomUUID();

        Mockito.doThrow(new EntityNotFoundException()).when(service).deleteOrder(id);
        makeDeleteRequest(API_URL + "/" + id)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
