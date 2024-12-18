package org.academy.projection;

import lombok.Getter;
import org.academy.model.Employee;

@Getter
public class EmployeeInfoImpl implements EmployeeInfo {
    private final String fullName;
    private final String position;
    private final String departmentName;

    public EmployeeInfoImpl(Employee employee) {
        this.fullName = employee.getFirstName() + " " + employee.getLastName();
        this.position = employee.getPosition();
        this.departmentName = employee.getDepartment().getName();
    }

}
