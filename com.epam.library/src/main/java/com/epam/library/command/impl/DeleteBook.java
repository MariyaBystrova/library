package com.epam.library.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.library.bean.Request;
import com.epam.library.bean.Response;
import com.epam.library.command.Command;
import com.epam.library.service.BookService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.factory.ServiceFactory;

public class DeleteBook implements Command {
	private final static Logger logger = LogManager.getLogger(DeleteBook.class.getName());

	private final static String TITLE = "title";
	private final static String MESSAGE = "Book was successfully deleted.";
	private final static String ERROR_MESSAGE = "Book was not deleted. Check data.";

	@Override
	public Response execute(Request request) {
		ServiceFactory factory = ServiceFactory.getInstance();
		BookService bookService = factory.getBookService();
		Response response = new Response();
		
		String titleBook;
		if (request.getParameter(TITLE) != null) {
			titleBook = request.getParameter(TITLE).toString();
		} else {
			response.setMessage(ERROR_MESSAGE);
			response.setErrorStatus(true);
			return response;
		}

		try {
			boolean result = bookService.deleteBookByTitle(titleBook);
			response.setMessage(result == true ? MESSAGE : ERROR_MESSAGE);
		} catch (ServiceException e) {
			logger.error(e);
			response.setMessage(ERROR_MESSAGE);
			response.setErrorStatus(true);
		}
		return response;
	}

}
