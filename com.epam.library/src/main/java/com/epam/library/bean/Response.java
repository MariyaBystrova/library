package com.epam.library.bean;

import java.util.List;
import java.util.Map;

import com.epam.library.domain.Book;
import com.epam.library.domain.Employee;

public class Response {
	private boolean errorStatus;
	private String message;
	private List<Book> bookList;
	private Map<Employee, Integer> employeeMap;

	public Response() {
	}

	public boolean isErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(boolean errorStatus) {
		this.errorStatus = errorStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public Map<Employee, Integer> getEmployeeMap() {
		return employeeMap;
	}

	public void setEmployeeMap(Map<Employee, Integer> employeeMap) {
		this.employeeMap = employeeMap;
	}

}
