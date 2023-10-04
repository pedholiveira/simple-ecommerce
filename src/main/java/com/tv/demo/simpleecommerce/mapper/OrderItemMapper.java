package com.tv.demo.simpleecommerce.mapper;

import com.tv.demo.simpleecommerce.dto.OrderItemDto;
import com.tv.demo.simpleecommerce.dto.OrderItemUpdateDto;
import com.tv.demo.simpleecommerce.dto.ProductDto;
import com.tv.demo.simpleecommerce.model.Order;
import com.tv.demo.simpleecommerce.model.OrderItem;
import com.tv.demo.simpleecommerce.model.Product;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "lastUpdatedOn", ignore = true)
    OrderItem createModelFromDto(OrderItemDto dto, Product product, Order order);

    @Mapping(source = ".", target = "totalPrice", qualifiedByName = "calculateTotalPrice")
    @Mapping(source = "orderItem.product.id", target = "productId")
    @Mapping(source = "orderItem.order.id", target = "orderId")
    OrderItemDto createDtoFromModel(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(OrderItemUpdateDto dto, @MappingTarget OrderItem orderItem);

    @Named("calculateTotalPrice")
    default BigDecimal calculateTotalPrice(OrderItem orderItem) {
        var productQuantity = orderItem.getProductQuantity();
        return orderItem.getProduct().getPrice().multiply(new BigDecimal(productQuantity));
    }
}
