package org.academy.spring_mvc_object_mapper.mapper;

import org.academy.spring_mvc_object_mapper.dto.CustomerDto;
import org.academy.spring_mvc_object_mapper.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    Customer toEntity(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);

    Customer updateWithNull(CustomerDto customerDto, @MappingTarget Customer customer);
}