package com.epam.library.service.factory;

import com.epam.library.service.BookEmployeeService;
import com.epam.library.service.BookService;
import com.epam.library.service.EmployeeService;
import com.epam.library.service.SourceInitService;
import com.epam.library.service.impl.EditBook;
import com.epam.library.service.impl.EditBookEmployee;
import com.epam.library.service.impl.EditEmployee;
import com.epam.library.service.impl.SourceInit;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();

	private EmployeeService employeeService = new EditEmployee();
	private BookService bookService = new EditBook();
	private BookEmployeeService bookEmployeeSevice = new EditBookEmployee();
	private SourceInitService sourceInit = new SourceInit();

	private ServiceFactory() {
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public BookService getBookService() {
		return bookService;
	}

	public SourceInitService getSourceInit() {
		return sourceInit;
	}

	public BookEmployeeService getBookEmployeeSevice() {
		return bookEmployeeSevice;
	}

	public void setBookEmployeeSevice(BookEmployeeService bookEmployeeSevice) {
		this.bookEmployeeSevice = bookEmployeeSevice;
	}

}
