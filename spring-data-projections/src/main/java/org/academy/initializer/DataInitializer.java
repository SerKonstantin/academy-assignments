package org.academy.initializer;

import lombok.Getter;
import org.academy.model.Department;
import org.academy.model.Employee;
import org.academy.repository.DepartmentRepository;
import org.academy.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department testDepartment;
    private Employee testEmployee;

    @Override
    public void run(ApplicationArguments args) {
        var department = new Department();
        department.setName("department");
        testDepartment = departmentRepository.save(department);

        var employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setPosition("Lead");
        employee.setSalary("42");
        employee.setDepartment(testDepartment);
        testEmployee = employeeRepository.save(employee);
    }
}
