package com.epam.library.service.impl;

import com.epam.library.dao.BookEmployeeOperationDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.factory.DAOFactory;
import com.epam.library.service.BookEmployeeService;
import com.epam.library.service.exception.ServiceException;

public class EditBookEmployee implements BookEmployeeService {

	@Override
	public void randomlyFillEmployeeBookTable() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		BookEmployeeOperationDAO bookEmplDAO = factory.getBookEmployeeOperationDAO();
		try {
			bookEmplDAO.randomlyFillEmployeeBookTable();
		} catch (DAOException e) {
			throw new ServiceException("Fill table failed. ", e);
		}
	}

}
