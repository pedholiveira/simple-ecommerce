package com.tv.demo.simpleecommerce.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder(toBuilder = true)
public class BaseDto {
    @JsonView(Views.Read.class)
    UUID id;
}
