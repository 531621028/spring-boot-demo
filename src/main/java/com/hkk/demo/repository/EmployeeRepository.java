package com.hkk.demo.repository;

import com.hkk.demo.dto.Employee;

/**
 * @author kang
 * @date 2023/10/7
 */
public class EmployeeRepository {

    public Employee findByName(String name) {
        return new Employee("真是的返回");
    }
}
