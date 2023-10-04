package com.tv.demo.simpleecommerce.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tv.demo.simpleecommerce.dto.ErrorResponse;
import com.tv.demo.simpleecommerce.dto.OrderDto;
import com.tv.demo.simpleecommerce.dto.Views;
import com.tv.demo.simpleecommerce.service.OrderService;
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

@Tag(name = "Orders API")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService service;

    @Operation(summary = "List orders", description = "Returns a list with all persisted orders.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully listed.")
    })
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    List<OrderDto> listOrders() {
        return service.listOrders();
    }

    @Operation(summary = "Get order", description = "Returns an order by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully obtained."),
            @ApiResponse(responseCode = "404", description = "The order was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping(value = "/{orderId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    OrderDto getOrderById(@PathVariable UUID orderId) {
        return service.findOrderById(orderId);
    }

    @Operation(summary = "Create order", description = "Creates a new order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created.")
    })
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Read.class)
    OrderDto createOrder() {
        return service.createOrder();
    }

    @Operation(summary = "Update order", description = "Updates order data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated."),
            @ApiResponse(responseCode = "404", description = "The order was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PutMapping(value = "/{orderId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    OrderDto updateOrder(@PathVariable UUID orderId, @JsonView(Views.Update.class) @RequestBody OrderDto order) {
        return service.updateOrder(orderId, order);
    }

    @Operation(summary = "Delete order", description = "Deletes an order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted."),
            @ApiResponse(responseCode = "404", description = "The order was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable UUID orderId) {
        service.deleteOrder(orderId);
    }
}
