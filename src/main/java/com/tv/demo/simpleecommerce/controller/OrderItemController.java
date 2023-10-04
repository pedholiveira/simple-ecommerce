package com.tv.demo.simpleecommerce.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tv.demo.simpleecommerce.dto.ErrorResponse;
import com.tv.demo.simpleecommerce.dto.OrderItemDto;
import com.tv.demo.simpleecommerce.dto.OrderItemUpdateDto;
import com.tv.demo.simpleecommerce.dto.Views;
import com.tv.demo.simpleecommerce.service.OrderItemService;
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

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Tag(name = "Order Items API")
@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemController {

    OrderItemService service;

    @Operation(summary = "List order items", description = "Returns a list with all persisted order items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully listed.")
    })
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    List<OrderItemDto> listOrderItems() {
        return service.listOrderItems();
    }

    @Operation(summary = "Get order item", description = "Returns an order item by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully obtained."),
            @ApiResponse(responseCode = "404", description = "The order item was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping(value = "/{orderItemId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    OrderItemDto getOrderItemById(@PathVariable UUID orderItemId) {
        return service.findOrderItemById(orderItemId);
    }

    @Operation(summary = "Create order item", description = "Creates a new order item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created."),
            @ApiResponse(responseCode = "400", description = "The related order or product does not exist.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "The product is already present in the order.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Read.class)
    OrderItemDto createOrderItem(@JsonView(Views.Create.class) @RequestBody OrderItemDto orderItem) {
        return service.createOrderItem(orderItem);
    }

    @Operation(summary = "Update order item", description = "Updates order item data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated."),
            @ApiResponse(responseCode = "404", description = "The order item was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PutMapping(value = "/{orderItemId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Read.class)
    OrderItemDto updateOrderItem(@PathVariable UUID orderItemId, @JsonView(Views.Update.class) @RequestBody OrderItemUpdateDto orderItem) {
        return service.updateOrderItem(orderItemId, orderItem);
    }

    @Operation(summary = "Delete order item", description = "Deletes an order item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted."),
            @ApiResponse(responseCode = "404", description = "The order item was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @DeleteMapping("/{orderItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrderItem(@PathVariable UUID orderItemId) {
        service.deleteOrderItem(orderItemId);
    }
}
