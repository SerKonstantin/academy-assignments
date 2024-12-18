package org.academy.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.academy.model.Employee;
import org.academy.projection.EmployeeInfoImpl;
import org.academy.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeResource {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<EmployeeInfoImpl> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeInfoImpl::new).toList();
    }

    @GetMapping("/{id}")
    public EmployeeInfoImpl getOne(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Entity with id `%s` not found".formatted(id)));
        return new EmployeeInfoImpl(employee);

    }

    @PostMapping
    public Employee create(@RequestBody @Valid Employee employee) {
        return employeeRepository.save(employee);
    }

    @PatchMapping("/{id}")
    public Employee patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(employee).readValue(patchNode);

        return employeeRepository.save(employee);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employeeRepository.delete(employee);
        }
        return employee;
    }
}
