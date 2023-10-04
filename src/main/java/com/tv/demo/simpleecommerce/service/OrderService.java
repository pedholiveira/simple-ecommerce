package com.tv.demo.simpleecommerce.service;

import com.tv.demo.simpleecommerce.dto.OrderDto;
import com.tv.demo.simpleecommerce.mapper.OrderMapper;
import com.tv.demo.simpleecommerce.model.OrderStatus;
import com.tv.demo.simpleecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderMapper mapper;
    OrderRepository repository;

    public List<OrderDto> listOrders() {
        return repository.findAll()
                .stream()
                .map(mapper::createDtoFromModel)
                .toList();
    }

    public OrderDto findOrderById(UUID orderId) {
        return repository.findById(orderId)
                .map(mapper::createDtoFromModel)
                .orElseThrow(() -> new EntityNotFoundException("No order was found with the given ID."));
    }

    @Transactional
    public OrderDto createOrder() {
        var order = mapper.createModelFromDto(
                OrderDto.builder()
                        .status(OrderStatus.IN_CREATION.toString())
                        .build());

        return mapper.createDtoFromModel(repository.save(order));
    }

    @Transactional
    public OrderDto updateOrder(UUID id, @Valid OrderDto dto) {
        var order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No order was found with the given ID."));
        mapper.updateModelFromDto(dto, order);

        return mapper.createDtoFromModel(repository.save(order));
    }

    @Transactional
    public void deleteOrder(UUID id) {
        var order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No order was found with the given ID."));
        repository.delete(order);
    }
}
