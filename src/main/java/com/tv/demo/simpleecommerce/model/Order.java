package com.tv.demo.simpleecommerce.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.REMOVE;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(schema = "simple_ecommerce", name = "orders")
public class Order extends BaseModel {

    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    List<OrderItem> items;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;
}
