package com.epam.library.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.library.bean.Request;
import com.epam.library.bean.Response;
import com.epam.library.command.CommandName;
import com.epam.library.controller.Controller;
import com.epam.library.service.SourceInitService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.impl.SourceInit;

public class View {
	private final static Logger logger = LogManager.getLogger(View.class.getName());

	public static void main(String[] args) {
		Controller controller = new Controller();
		SourceInitService sourceInit = new SourceInit();
		try {
			sourceInit.init();
		} catch (ServiceException e) {
			logger.error(e);
		}

		// /////////////////////////////////////////////////////////
		// FILL EMPLOYEE_BOOK TABLE (this is to run only once)
//		Request request1 = new Request();
//		request1.setCommandName(CommandName.FILL_EMPLOYEE_BOOK_TABLE);
//
//		Response response1 = controller.doAction(request1);
//		if (response1.isErrorStatus()) {
//			logger.info(response1.getMessage());
//		}
		// /////////////////////////////////////////////////////////
		// ADD BOOK
		Request request = new Request();
		request.setCommandName(CommandName.ADD_BOOK);
		request.setParameter("title", "Alice in Wonderland");
		request.setParameter("author", "Lewis Carroll");
		request.setParameter("publishYear", "1996");

		Response response = controller.doAction(request);
		logger.info(response.getMessage());
		// ////////////////////////////////////////////////////////////
		// GET PRODUCT
		request = new Request();
		request.setCommandName(CommandName.GET_BOOK);
		request.setParameter("title", "Alice in Wonderland");

		response = controller.doAction(request);
		if (!response.isErrorStatus()) {
			ViewHelper.printBookList(response.getBookList());
		} else {
			logger.info(response.getMessage());
		}
		// /////////////////////////////////////////////////////////////
		// DELETE BOOK
		request = new Request();
		request.setCommandName(CommandName.DELETE_BOOK);
		request.setParameter("title", "Alice in Wonderland");

		response = controller.doAction(request);
		logger.info(response.getMessage());
		// ////////////////////////////////////////////////////////////
		// RENAME BOOK
		request = new Request();
		request.setCommandName(CommandName.RENAME_BOOK);
		request.setParameter("oldTitle", "Java in action 3-d edition");
		request.setParameter("newTitle", "Java in action 3-d edition-1");

		response = controller.doAction(request);
		logger.info(response.getMessage());

		// ///////////////////////////////////////////////////////////
		// GET EMPLOYEES WITH MORE THEN ONE BOOKS
		request = new Request();
		request.setCommandName(CommandName.GET_EMPLOYEES_WITH_MORE_THAN_ONE_BOOK);

		response = controller.doAction(request);

		logger.info("\n\nGET EMPLOYEES WITH MORE THEN ONE BOOKS:");
		if (!response.isErrorStatus()) {
			ViewHelper.printEmployeeMap(response.getEmployeeMap());
		} else {
			logger.info(response.getMessage());
		}

		// ///////////////////////////////////////////////////////////
		// GET EMPLOYEES WITH LESS THAN OR EQUAL TO 2 BOOKS
		request = new Request();
		request.setCommandName(CommandName.GET_EMPLOYEES_WITH_LESS_EQUALS_TWO_BOOKS);

		response = controller.doAction(request);

		logger.info("\n\nGET EMPLOYEES WITH LESS THAN OR EQUAL TO 2 BOOKS:");
		if (!response.isErrorStatus()) {
			ViewHelper.printEmployeeMap(response.getEmployeeMap());
		} else {
			logger.info(response.getMessage());
		}

		sourceInit.destroy();
	}

}
