package com.tv.demo.simpleecommerce.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tv.demo.simpleecommerce.dto.OrderItemDto;
import com.tv.demo.simpleecommerce.dto.OrderItemUpdateDto;
import com.tv.demo.simpleecommerce.dto.Views;
import com.tv.demo.simpleecommerce.service.OrderItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemController {

    OrderItemService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    List<OrderItemDto> listOrderItems() {
        return service.listOrderItems();
    }

    @GetMapping("/{orderItemId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    OrderItemDto getOrderItemById(@PathVariable UUID orderItemId) {
        return service.findOrderItemById(orderItemId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Read.class)
    OrderItemDto createOrderItem(@JsonView(Views.Create.class) @RequestBody OrderItemDto orderItem) {
        return service.createOrderItem(orderItem);
    }

    @PutMapping("/{orderItemId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    OrderItemDto updateOrderItem(@PathVariable UUID orderItemId, @JsonView(Views.Update.class) @RequestBody OrderItemUpdateDto orderItem) {
        return service.updateOrderItem(orderItemId, orderItem);
    }

    @DeleteMapping("/{orderItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrderItem(@PathVariable UUID orderItemId) {
        service.deleteOrderItem(orderItemId);
    }
}
