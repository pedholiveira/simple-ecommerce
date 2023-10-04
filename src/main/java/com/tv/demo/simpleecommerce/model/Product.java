package com.tv.demo.simpleecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(schema = "simple_ecommerce", name = "products")
public class Product extends BaseModel {

    @Column(nullable = false, length = 50)
    String name;
    @Column(nullable = false, length = 500)
    String description;
    @Column(nullable = false, scale = 2)
    @Positive
    BigDecimal price;
    @OneToMany(mappedBy="product", cascade = REMOVE, orphanRemoval = true)
    List<OrderItem> orderItems;
}
