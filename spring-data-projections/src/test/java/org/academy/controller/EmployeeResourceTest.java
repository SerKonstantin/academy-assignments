package org.academy.controller;

import org.academy.model.Employee;
import org.academy.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link EmployeeResource}
 */
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Transactional
    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].fullName").value("John Smith"))
                .andExpect(jsonPath("$[0].departmentName").value("department"));
    }

    @Transactional
    @Test
    public void getOne() throws Exception {
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Smith"))
                .andExpect(jsonPath("$.departmentName").value("department"));
    }

    @Transactional
    @Test
    public void create() throws Exception {
        String employee = """
                {
                    "firstName": "Marie",
                    "lastName": "Curie",
                    "position": "Lab",
                    "salary": "some number",
                    "department": {
                        "id": 1,
                        "name": "department"
                    }
                }""";

        mockMvc.perform(post("/employees")
                        .content(employee)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Employee foundEmployee = employeeRepository.findByFirstName("Marie")
                .orElseThrow(() -> new RuntimeException("Cant find employee after CREATE"));
        assert foundEmployee.getLastName().equals("Curie");
        assert foundEmployee.getDepartment().getName().equals("department");

        employeeRepository.deleteById(foundEmployee.getId());
    }

}
