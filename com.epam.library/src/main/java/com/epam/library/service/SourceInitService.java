package com.epam.library.service;

import com.epam.library.service.exception.ServiceException;

/**
 * Represents an interface SourceInitService, implementation of which provides a
 * proper initialization and destruction the data source.
 * 
 * @author Mariya Bystrova
 *
 */
public interface SourceInitService {
	/**
	 * Provides initialization the data source service.
	 * 
	 * @throws ServiceException
	 *             if some problems with initialization has occur.
	 */
	public void init() throws ServiceException;

	/**
	 * Provides destruction of the connection to the data source.
	 */
	public void destroy();
}
