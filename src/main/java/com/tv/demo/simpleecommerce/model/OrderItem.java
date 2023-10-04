package com.tv.demo.simpleecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(schema = "simple_ecommerce", name = "order_items")
public class OrderItem extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
    @Column(nullable = false)
    @Min(value = 0)
    Integer productQuantity;
}
