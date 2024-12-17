package org.academy.spring_mvc_object_mapper.mapper;

import org.academy.spring_mvc_object_mapper.dto.ProductDto;
import org.academy.spring_mvc_object_mapper.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrderMapper.class})
public interface ProductMapper {

    @Mapping(target = "order.id", source = "orderId")
    Product toEntity(ProductDto dto);

    @Mapping(target = "orderId", source = "order.id")
    ProductDto toProductDto(Product product);

    Product updateWithNull(ProductDto productDto, @MappingTarget Product product);
}