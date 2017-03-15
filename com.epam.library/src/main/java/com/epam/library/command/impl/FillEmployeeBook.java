package com.epam.library.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.library.bean.Request;
import com.epam.library.bean.Response;
import com.epam.library.command.Command;
import com.epam.library.service.BookEmployeeService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.factory.ServiceFactory;

public class FillEmployeeBook implements Command {
	private final static Logger logger = LogManager.getLogger(AddBook.class.getName());

	private final static String ERROR_MESSAGE = "Book was not added. Check data.";

	@Override
	public Response execute(Request request) {
		Response response = new Response();
		ServiceFactory factory = ServiceFactory.getInstance();
		BookEmployeeService bookEmplService = factory.getBookEmployeeSevice();
		try {
			bookEmplService.randomlyFillEmployeeBookTable();
			response.setErrorStatus(false);
		} catch (ServiceException e) {
			logger.error(e);
			response.setMessage(ERROR_MESSAGE);
			response.setErrorStatus(true);
		}
		return response;
	}

}
