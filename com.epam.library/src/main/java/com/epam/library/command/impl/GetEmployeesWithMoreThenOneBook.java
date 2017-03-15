package com.epam.library.command.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.library.bean.Request;
import com.epam.library.bean.Response;
import com.epam.library.command.Command;
import com.epam.library.domain.Employee;
import com.epam.library.service.EmployeeService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.factory.ServiceFactory;

public class GetEmployeesWithMoreThenOneBook implements Command {
	private final static Logger logger = LogManager.getLogger(GetEmployeesWithMoreThenOneBook.class.getName());

	private final static String ERROR_MESSAGE = "Employees were not returned. Check data.";

	@Override
	public Response execute(Request request) {
		ServiceFactory factory = ServiceFactory.getInstance();
		EmployeeService employeeService = factory.getEmployeeService();
		Response response = new Response();
		try {
			Map<Employee, Integer> map = employeeService.getEmployeesWithMoreThanOneBook();
			response.setEmployeeMap(map);
			response.setErrorStatus(false);
		} catch (ServiceException e) {
			logger.error(e);
			response.setMessage(ERROR_MESSAGE);
			response.setErrorStatus(true);
		}
		return response;
	}

}
