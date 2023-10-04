package com.tv.demo.simpleecommerce.mapper;

import com.tv.demo.simpleecommerce.dto.ProductDto;
import com.tv.demo.simpleecommerce.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product createModelFromDto(ProductDto dto);

    ProductDto createDtoFromModel(Product product);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(ProductDto dto, @MappingTarget Product product);
}
