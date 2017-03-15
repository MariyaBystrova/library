package com.epam.library.dao.impl.util;

public class BookEmployeeQuery {
	private BookEmployeeQuery() {
	}

	public final static String INSERT_INTO_EMPLOYEE_BOOK = "INSERT INTO `employee_book`(`book_id`,`employee_id`,`id`) VALUES(?,?,?);";
}
