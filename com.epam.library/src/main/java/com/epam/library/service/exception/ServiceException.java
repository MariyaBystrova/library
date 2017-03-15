package com.epam.library.service.exception;

/**
 * Thrown when an exceptional situation with data source has occurred. For
 * example, problems with data source or connection pool has occur.
 * 
 * @author Mariya Bystrova
 *
 */
public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}
}
