package org.academy.spring_mvc_object_mapper.mapper;

import org.academy.spring_mvc_object_mapper.dto.OrderDto;
import org.academy.spring_mvc_object_mapper.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CustomerMapper.class})
public interface OrderMapper {

    @Mapping(target = "customer.id", source = "customerId")
    Order toEntity(OrderDto orderDto);

    @Mapping(target = "customerId", source = "customer.id")
    OrderDto toOrderDto(Order order);

    Order updateWithNull(OrderDto orderDto, @MappingTarget Order order);
}