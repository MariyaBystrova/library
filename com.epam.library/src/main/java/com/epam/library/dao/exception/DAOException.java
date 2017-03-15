package com.epam.library.dao.exception;

/**
 * Thrown when an exceptional situation with data source has occurred. For
 * example, problems with data source or connection pool has occur.
 * 
 * @author Mariya Bystrova
 *
 */
public class DAOException extends Exception {
	private static final long serialVersionUID = 1L;

	public DAOException() {
		super();
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Exception e) {
		super(e);
	}

	public DAOException(String message, Exception e) {
		super(message, e);
	}
}
