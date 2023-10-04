package com.tv.demo.simpleecommerce.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
public class OrderItemUpdateDto extends BaseDto {

    @JsonView(Views.Update.class)
    @NotNull(message = "Product quantity cannot be null.")
    @Positive(message = "Product quantity needs to be greater than zero.")
    Integer productQuantity;
}
