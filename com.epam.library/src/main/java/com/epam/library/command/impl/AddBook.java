package com.epam.library.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.library.bean.Request;
import com.epam.library.bean.Response;
import com.epam.library.command.Command;
import com.epam.library.domain.Book;
import com.epam.library.service.BookService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.factory.ServiceFactory;

public class AddBook implements Command {
	private final static Logger logger = LogManager.getLogger(AddBook.class.getName());

	private final static String TITLE = "title";
	private final static String AUTHOR = "author";
	private final static String BRIEF = "brief";
	private final static String PUBLISH_YEAR = "publishYear";
	private final static String MESSAGE = "Book was successfully added.";
	private final static String ERROR_MESSAGE = "Book was not added. Check data.";

	@Override
	public Response execute(Request request) {
		ServiceFactory factory = ServiceFactory.getInstance();
		BookService bookService = factory.getBookService();
		Response response = new Response();

		String titleBook;
		String authorBook;
		String briefBook;
		String yearBook;

		if (request.getParameter(TITLE) != null && request.getParameter(AUTHOR) != null
				&& request.getParameter(PUBLISH_YEAR) != null) {
			titleBook = request.getParameter(TITLE).toString();
			authorBook = request.getParameter(AUTHOR).toString();
			yearBook = request.getParameter(PUBLISH_YEAR).toString();
			briefBook = request.getParameter(BRIEF) == null ? null : request.getParameter(BRIEF).toString();
		} else {
			response.setMessage(ERROR_MESSAGE);
			response.setErrorStatus(true);
			return response;
		}

		Book book = new Book(titleBook, authorBook, briefBook, yearBook);
		try {
			boolean result = bookService.createBook(book);
			response.setMessage(result == true ? MESSAGE : ERROR_MESSAGE);
		} catch (ServiceException e) {
			logger.error(e);
			response.setMessage(ERROR_MESSAGE);
			response.setErrorStatus(true);
		}
		return response;
	}

}
