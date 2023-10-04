package com.tv.demo.simpleecommerce.controller;

import com.tv.demo.simpleecommerce.dto.OrderItemDto;
import com.tv.demo.simpleecommerce.dto.OrderItemUpdateDto;
import com.tv.demo.simpleecommerce.exception.ProductAlreadyExistsException;
import com.tv.demo.simpleecommerce.exception.RelationshipEntityNotFoundException;
import com.tv.demo.simpleecommerce.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = OrderItemController.class)
public class OrderItemControllerTest extends BaseControllerTest {

    private static final String API_URL = "/api/order-items";

    @MockBean
    private OrderItemService service;

    @Test
    public void testListOrderItemsApi() throws Exception {
        List<OrderItemDto> orderItems = List.of(OrderItemDto.builder()
                .id(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .productQuantity(5)
                .totalPrice(BigDecimal.TEN)
                .build());

        Mockito.when(service.listOrderItems()).thenReturn(orderItems);
        makeGetRequest(API_URL).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetOrderItemByIdApi() throws Exception {
        var id = UUID.randomUUID();
        var orderItem = OrderItemDto.builder()
                .id(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .productQuantity(5)
                .totalPrice(BigDecimal.TEN)
                .build();

        Mockito.when(service.findOrderItemById(id)).thenReturn(orderItem);
        makeGetRequest(API_URL + "/" + id).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetOrderItemByIdApi_whenNoOrderItemIsFound() throws Exception {
        var id = UUID.randomUUID();
        Mockito.when(service.findOrderItemById(id)).thenThrow(new EntityNotFoundException());
        makeGetRequest(API_URL + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateOrderItemApi() throws Exception {
        var request = OrderItemDto.builder()
                .orderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .productQuantity(5)
                .build();

        var response = OrderItemDto.builder()
                .id(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .productQuantity(5)
                .totalPrice(BigDecimal.TEN)
                .build();

        Mockito.when(service.createOrderItem(request)).thenReturn(response);

        makePostRequest(API_URL, mapper.writeValueAsString(request))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateOrderItemApi_whenAnyRelationshipEntityIsNotFound() throws Exception {
        Mockito.when(service.createOrderItem(Mockito.any(OrderItemDto.class))).thenThrow(new RelationshipEntityNotFoundException(""));

        makePostRequest(API_URL, mapper.writeValueAsString(OrderItemDto.builder().build()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateOrderItemApi_whenTheProductIsAlreadyPresentInOrder() throws Exception {

        Mockito.when(service.createOrderItem(Mockito.any(OrderItemDto.class))).thenThrow(new ProductAlreadyExistsException(""));

        makePostRequest(API_URL, mapper.writeValueAsString(OrderItemDto.builder().build()))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testUpdateOrderItemApi() throws Exception {
        var id = UUID.randomUUID();
        var orderItem = OrderItemUpdateDto.builder()
                .productQuantity(5)
                .build();

        Mockito.when(service.updateOrderItem(id, orderItem)).thenReturn(OrderItemDto.builder().build());

        makePutRequest(API_URL + "/" + id, mapper.writeValueAsString(orderItem))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateOrderItemApi_whenOrderItemIsNotFound() throws Exception {
        var id = UUID.randomUUID();

        Mockito.when(service.updateOrderItem(Mockito.eq(id), Mockito.any(OrderItemUpdateDto.class)))
                .thenThrow(new EntityNotFoundException());

        makePutRequest(API_URL + "/" + id, mapper.writeValueAsString(OrderItemUpdateDto.builder().build()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteOrderItemApi() throws Exception {
        var id = UUID.randomUUID();

        makeDeleteRequest(API_URL + "/" + id)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteOrderItemApi_whenOrderItemIsNotFound() throws Exception {
        var id = UUID.randomUUID();

        Mockito.doThrow(new EntityNotFoundException()).when(service).deleteOrderItem(id);
        makeDeleteRequest(API_URL + "/" + id)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
