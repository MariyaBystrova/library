package com.epam.library.service.impl;

import java.util.Map;

import com.epam.library.dao.EmployeeOperationDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.factory.DAOFactory;
import com.epam.library.domain.Employee;
import com.epam.library.service.EmployeeService;
import com.epam.library.service.exception.ServiceException;

public class EditEmployee implements EmployeeService {

	public Map<Employee, Integer> getEmployeesWithMoreThanOneBook() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		EmployeeOperationDAO employeeDAO = factory.getEmployeeOperationDAO();
		try {
			Map<Employee, Integer> map = employeeDAO.getEmployeesWithMoreThanOneBook();
			return map;
		} catch (DAOException e) {
			throw new ServiceException("Get books failed. ", e);
		}
	}

	public Map<Employee, Integer> getEmployeesWithLQThanTwoBooks() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		EmployeeOperationDAO employeeDAO = factory.getEmployeeOperationDAO();
		try {
			Map<Employee, Integer> map = employeeDAO.getEmployeesWithLQThanTwoBooks();
			return map;
		} catch (DAOException e) {
			throw new ServiceException("Get books failed. ", e);
		}
	}

}
