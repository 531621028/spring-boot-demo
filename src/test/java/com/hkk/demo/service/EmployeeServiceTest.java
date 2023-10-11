package com.hkk.demo.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.hkk.demo.dto.Employee;
import com.hkk.demo.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public EmployeeService employeeService() {
            return new EmployeeService();
        }
    }

    @SpyBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        Employee alex = new Employee("mock返回的对象");

        Mockito.when(employeeRepository.findByName(alex.getName()))
            .thenReturn(alex);
        //方法入参的校验和使用参考这篇文章 https://blog.51cto.com/u_15077533/2583510
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "mock返回的对象";
        Employee found = employeeService.getEmployeeByName(name);
        Assert.assertEquals(name, found.getName());
    }

    @Test
    public void whenFilterByName() {
        Employee found = employeeService.filterByName("1");
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(employeeService, times(1)).filterByName(argumentCaptor.capture());
        Assert.assertEquals("1", argumentCaptor.getValue());
        Assert.assertNotNull(found);

        Employee notFound = employeeService.filterByName("3");
        // MockBean也可以执行执行方法
        verify(employeeService, times(2)).filterByName(argumentCaptor.capture());
        verify(employeeService, times(2)).getAllEmployees();
        Assert.assertEquals("3", argumentCaptor.getValue());
        Assert.assertNull(notFound);
    }
}