package com.hkk.demo.service;

import com.google.common.collect.Lists;
import com.hkk.demo.dto.Employee;
import com.hkk.demo.repository.EmployeeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kang
 * @date 2023/10/7
 */
@Component
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> getAllEmployees() {
        return Lists.newArrayList(new Employee("1"), new Employee("2"));
    }
}
