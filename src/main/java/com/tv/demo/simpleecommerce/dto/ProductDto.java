package com.tv.demo.simpleecommerce.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tv.demo.simpleecommerce.dto.Views.Create;
import com.tv.demo.simpleecommerce.dto.Views.Read;
import com.tv.demo.simpleecommerce.dto.Views.Update;
import jakarta.validation.constraints.*;
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
public class ProductDto extends BaseDto {
    @JsonView({Read.class, Create.class, Update.class})
    @NotBlank(message = "Name cannot be blank.")
    @Size(max = 50, message = "Name cannot exceed 50 characters.")
    String name;

    @JsonView({Read.class, Create.class, Update.class})
    @NotBlank(message = "Description cannot be blank.")
    @Size(max = 500, message = "Description cannot exceed 500 characters.")
    String description;

    @JsonView({Read.class, Create.class, Update.class})
    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price must be a positive value.")
    BigDecimal price;
}
