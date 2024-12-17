package org.academy.spring_mvc_object_mapper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_object_mapper.dto.CustomerDto;
import org.academy.spring_mvc_object_mapper.mapper.CustomerMapper;
import org.academy.spring_mvc_object_mapper.model.Customer;
import org.academy.spring_mvc_object_mapper.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService{

    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    private final ObjectMapper objectMapper;

    public List<CustomerDto> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toCustomerDto)
                .toList();
    }

    public CustomerDto getOne(UUID id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerMapper.toCustomerDto(customerOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public CustomerDto create(CustomerDto dto) {
        Customer customer = customerMapper.toEntity(dto);
        Customer resultCustomer = customerRepository.save(customer);
        return customerMapper.toCustomerDto(resultCustomer);
    }

    public CustomerDto patch(UUID id, JsonNode patchNode) throws IOException {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        CustomerDto customerDto = customerMapper.toCustomerDto(customer);
        objectMapper.readerForUpdating(customerDto).readValue(patchNode);
        customerMapper.updateWithNull(customerDto, customer);

        Customer resultCustomer = customerRepository.save(customer);
        return customerMapper.toCustomerDto(resultCustomer);
    }

    public CustomerDto delete(UUID id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customerRepository.delete(customer);
        }
        return customerMapper.toCustomerDto(customer);
    }
}
