package com.epam.library.service.impl;

import com.epam.library.dao.SourceInitDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.factory.DAOFactory;
import com.epam.library.service.SourceInitService;
import com.epam.library.service.exception.ServiceException;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.service.SourceInitService}.
 * 
 * @author Mariya Bystrova
 *
 */
public class SourceInit implements SourceInitService {

	/**
	 * Provides initialization the data source service.
	 * 
	 * @throws ServiceException
	 *             if some problems with initialization has occur.
	 */
	@Override
	public void init() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		SourceInitDAO sourceDAO = factory.getSourceInitDAO();
		try {
			sourceDAO.init();
		} catch (DAOException e) {
			throw new ServiceException("Init source failed", e);
		}
	}

	/**
	 * Provides destruction of the connection to the data source.
	 */
	@Override
	public void destroy() {
		DAOFactory factory = DAOFactory.getInstance();
		SourceInitDAO sourceDAO = factory.getSourceInitDAO();
		sourceDAO.destroy();
	}

}
