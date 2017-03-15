package com.epam.library.command.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.library.bean.Request;
import com.epam.library.bean.Response;
import com.epam.library.command.Command;
import com.epam.library.domain.Book;
import com.epam.library.service.BookService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.factory.ServiceFactory;

public class GetBook implements Command {
	private final static Logger logger = LogManager.getLogger(GetBook.class.getName());

	private final static String TITLE = "title";
	private final static String ERROR_MESSAGE = "Book was not returned. Check data.";

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
			List<Book> list = bookService.getBookByTitle(titleBook);
			response.setBookList(list);
			response.setErrorStatus(false);

		} catch (ServiceException e) {
			logger.error(e);
			response.setMessage(ERROR_MESSAGE);
			response.setErrorStatus(true);
		}
		return response;
	}

}
