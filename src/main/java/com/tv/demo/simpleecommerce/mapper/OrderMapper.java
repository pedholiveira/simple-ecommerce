package com.tv.demo.simpleecommerce.mapper;

import com.tv.demo.simpleecommerce.dto.OrderDto;
import com.tv.demo.simpleecommerce.dto.OrderItemDto;
import com.tv.demo.simpleecommerce.dto.ProductDto;
import com.tv.demo.simpleecommerce.model.Order;
import com.tv.demo.simpleecommerce.model.OrderItem;
import com.tv.demo.simpleecommerce.model.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    Order createModelFromDto(OrderDto dto);

    @Mapping(source = "order.items", target = "totalPrice", qualifiedByName = "calculateTotalPrice")
    OrderDto createDtoFromModel(Order order);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(OrderDto dto, @MappingTarget Order order);

    default OrderItemDto map(OrderItem orderItem) {
        return Mappers.getMapper(OrderItemMapper.class).createDtoFromModel(orderItem);
    }

    @Named("calculateTotalPrice")
    default BigDecimal calculateTotalPrice(List<OrderItem> items) {
        return Optional.ofNullable(items)
                .stream()
                .flatMap(Collection::stream)
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getProductQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
