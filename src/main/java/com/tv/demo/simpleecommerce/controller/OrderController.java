package com.tv.demo.simpleecommerce.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tv.demo.simpleecommerce.dto.OrderDto;
import com.tv.demo.simpleecommerce.dto.Views;
import com.tv.demo.simpleecommerce.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    List<OrderDto> listOrders() {
        return service.listOrders();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    OrderDto getOrderById(@PathVariable UUID orderId) {
        return service.findOrderById(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Read.class)
    OrderDto createOrder() {
        return service.createOrder();
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    OrderDto updateOrder(@PathVariable UUID orderId, @JsonView(Views.Update.class) @RequestBody OrderDto order) {
        return service.updateOrder(orderId, order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable UUID orderId) {
        service.deleteOrder(orderId);
    }
}
