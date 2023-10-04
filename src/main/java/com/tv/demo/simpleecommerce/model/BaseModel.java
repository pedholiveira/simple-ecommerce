package com.tv.demo.simpleecommerce.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @UuidGenerator
    UUID id;

    @CreationTimestamp
    LocalDateTime createdOn;

    @UpdateTimestamp
    LocalDateTime lastUpdatedOn;
}
