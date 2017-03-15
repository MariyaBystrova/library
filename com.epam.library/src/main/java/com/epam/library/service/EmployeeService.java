package com.epam.library.service;

import java.util.Map;

import com.epam.library.domain.Employee;
import com.epam.library.service.exception.ServiceException;

public interface EmployeeService {
	public Map<Employee, Integer> getEmployeesWithMoreThanOneBook() throws ServiceException;

	public Map<Employee, Integer> getEmployeesWithLQThanTwoBooks() throws ServiceException;
}
