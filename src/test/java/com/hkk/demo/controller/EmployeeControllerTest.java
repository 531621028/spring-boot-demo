package com.hkk.demo.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hkk.demo.controller.EmployeeRestController;
import com.hkk.demo.dto.Employee;
import com.hkk.demo.service.EmployeeService;
import java.util.Collections;
import java.util.List;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeRestController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService service;

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
        throws Exception {

        Employee alex = new Employee("alex");

        List<Employee> allEmployees = Collections.singletonList(alex);

        given(service.getAllEmployees()).willReturn(allEmployees);

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].name", IsEqualIgnoringCase.equalToIgnoringCase(alex.getName())));
    }
}