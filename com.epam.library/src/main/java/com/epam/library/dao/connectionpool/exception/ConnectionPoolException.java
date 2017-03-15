package com.epam.library.dao.connectionpool.exception;

/**
 * Thrown when an exceptional situation with connection pool has occurred. For
 * example, problems with getting connection has occur.
 * 
 * @author Mariya Bystrova
 *
 */
public class ConnectionPoolException extends Exception {
	private static final long serialVersionUID = 1L;

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Exception e) {
		super(e);
	}

	public ConnectionPoolException(String message, Exception e) {
		super(message, e);
	}

}