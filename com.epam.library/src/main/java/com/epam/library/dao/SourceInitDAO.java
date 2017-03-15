package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;

/**
 * Represents an interface UserOperationDAO, implementation of which provides a
 * proper initialization and destruction the connection to the specific data
 * source.
 * 
 * @author Mariya Bystrova
 *
 */
public interface SourceInitDAO {
	/**
	 * Provides the proper initialization of connection to the data source.
	 * 
	 * @throws DAOException
	 *             if some problems with connection pool has occur.
	 */
	public void init() throws DAOException;

	/**
	 * Provides the proper destruction of connection.
	 */
	public void destroy();
}
