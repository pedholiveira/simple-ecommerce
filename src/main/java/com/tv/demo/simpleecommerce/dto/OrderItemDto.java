package com.tv.demo.simpleecommerce.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tv.demo.simpleecommerce.model.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@SuperBuilder
@Jacksonized
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemDto extends BaseDto {

    @JsonView({Views.Create.class, Views.Read.class})
    @NotNull(message = "The order id cannot be null.")
    UUID orderId;
    @JsonView({Views.Create.class, Views.Read.class})
    @NotNull(message = "The product id cannot be null.")
    UUID productId;
    @JsonView({Views.Create.class, Views.Update.class, Views.Read.class})
    @NotNull(message = "Product quantity cannot be null.")
    @Positive(message = "Product quantity needs to be greater than zero.")
    Integer productQuantity;
    @JsonView(Views.Read.class)
    BigDecimal totalPrice;
}
