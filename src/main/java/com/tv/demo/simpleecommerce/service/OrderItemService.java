package com.tv.demo.simpleecommerce.service;

import com.tv.demo.simpleecommerce.dto.OrderItemDto;
import com.tv.demo.simpleecommerce.dto.OrderItemUpdateDto;
import com.tv.demo.simpleecommerce.exception.ProductAlreadyExistsException;
import com.tv.demo.simpleecommerce.exception.RelationshipEntityNotFoundException;
import com.tv.demo.simpleecommerce.mapper.OrderItemMapper;
import com.tv.demo.simpleecommerce.model.Order;
import com.tv.demo.simpleecommerce.model.OrderItem;
import com.tv.demo.simpleecommerce.model.Product;
import com.tv.demo.simpleecommerce.repository.OrderItemRepository;
import com.tv.demo.simpleecommerce.repository.OrderRepository;
import com.tv.demo.simpleecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemService {

    OrderItemMapper mapper;
    ProductRepository productRepository;
    OrderRepository orderRepository;
    OrderItemRepository repository;

    public List<OrderItemDto> listOrderItems() {
        return repository.findAll()
                .stream()
                .map(mapper::createDtoFromModel)
                .toList();
    }

    public OrderItemDto findOrderItemById(UUID orderItemId) {
        return repository.findById(orderItemId)
                .map(mapper::createDtoFromModel)
                .orElseThrow(() -> new EntityNotFoundException("No order item was found with the given ID."));
    }

    @Transactional
    public OrderItemDto createOrderItem(OrderItemDto dto) {
        var product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RelationshipEntityNotFoundException("The related product does not exist."));
        var order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RelationshipEntityNotFoundException("The related order does not exist."));

        validateProduct(product, order);
        var orderItem = mapper.createModelFromDto(dto, product, order);
        return mapper.createDtoFromModel(repository.save(orderItem));
    }

    @Transactional
    public OrderItemDto updateOrderItem(UUID id, @Valid OrderItemUpdateDto dto) {
        var orderItem = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No order item was found with the given ID."));
        mapper.updateModelFromDto(dto, orderItem);

        return mapper.createDtoFromModel(repository.save(orderItem));
    }

    @Transactional
    public void deleteOrderItem(UUID id) {
        var orderItem = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No order item was found with the given ID."));
        repository.delete(orderItem);
    }

    private void validateProduct(Product product, Order order) {
        if(Optional.ofNullable(order.getItems())
                .stream()
                .flatMap(Collection::stream)
                .map(OrderItem::getProduct)
                .anyMatch(orderItemProduct -> orderItemProduct.getId().equals(product.getId()))) {
            throw new ProductAlreadyExistsException("The product is already present in the order.");
        }
    }
}
