package com.tv.demo.simpleecommerce.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@SuperBuilder
@Jacksonized
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDto extends BaseDto {

    @JsonView({Views.Read.class, Views.Update.class})
    @NotNull(message = "The order status cannot be null.")
    String status;
    @JsonView(Views.Read.class)
    @Builder.Default
    List<OrderItemDto> items = Collections.emptyList();
    @JsonView(Views.Read.class)
    BigDecimal totalPrice;
}
