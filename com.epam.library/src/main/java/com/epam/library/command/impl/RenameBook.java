package com.epam.library.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.library.bean.Request;
import com.epam.library.bean.Response;
import com.epam.library.command.Command;
import com.epam.library.service.BookService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.factory.ServiceFactory;

public class RenameBook implements Command {
	private final static Logger logger = LogManager.getLogger(RenameBook.class.getName());

	private final static String OLD_TITLE = "oldTitle";
	private final static String NEW_TITLE = "newTitle";
	private final static String MESSAGE = "Book was successfully renamed.";
	private final static String ERROR_MESSAGE = "Book was not renamed. Check data.";

	@Override
	public Response execute(Request request) {
		ServiceFactory factory = ServiceFactory.getInstance();
		BookService bookService = factory.getBookService();
		Response response = new Response();

		String oldTitleBook;
		String newTitleBook;
		if (request.getParameter(OLD_TITLE) != null && request.getParameter(NEW_TITLE) != null) {
			oldTitleBook = request.getParameter(OLD_TITLE).toString();
			newTitleBook = request.getParameter(NEW_TITLE).toString();
		} else {
			response.setMessage(ERROR_MESSAGE);
			response.setErrorStatus(true);
			return response;
		}

		try {
			boolean result = bookService.renameBook(oldTitleBook, newTitleBook);
			response.setErrorStatus(!result);
			response.setMessage(result == true ? MESSAGE : ERROR_MESSAGE);
		} catch (ServiceException e) {
			logger.error(e);
			response.setMessage(ERROR_MESSAGE);
			response.setErrorStatus(true);
		}
		return response;
	}

}
