package com.epam.library.dao;

import java.util.Map;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.domain.Employee;

public interface EmployeeOperationDAO {
	public Map<Employee, Integer> getEmployeesWithMoreThanOneBook() throws DAOException;

	public Map<Employee, Integer> getEmployeesWithLQThanTwoBooks() throws DAOException;
}
