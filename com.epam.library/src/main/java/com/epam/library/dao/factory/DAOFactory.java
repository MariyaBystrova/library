package com.epam.library.dao.factory;

import com.epam.library.dao.BookEmployeeOperationDAO;
import com.epam.library.dao.BookOperationDAO;
import com.epam.library.dao.EmployeeOperationDAO;
import com.epam.library.dao.SourceInitDAO;
import com.epam.library.dao.impl.SQLBookEmployeeOperationDAO;
import com.epam.library.dao.impl.SQLBookOperationDAO;
import com.epam.library.dao.impl.SQLEmployeeOperationDAO;
import com.epam.library.dao.impl.SQLInit;

/**
 * Represents DAO layer factory designed by Factory pattern, based on Singleton,
 * which provides an abstraction.
 * 
 * @author Mariya Bystrova
 *
 */
public class DAOFactory {
	private static final DAOFactory INSTANCE = new DAOFactory();

	private BookOperationDAO bookOperationDAO = new SQLBookOperationDAO();
	private EmployeeOperationDAO employeeOperationDAO = new SQLEmployeeOperationDAO();
	private BookEmployeeOperationDAO bookEmployeeOperationDAO = new SQLBookEmployeeOperationDAO();
	private SourceInitDAO sourceInitDAO = new SQLInit();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public BookOperationDAO getBookOperationDAO() {
		return bookOperationDAO;
	}

	public EmployeeOperationDAO getEmployeeOperationDAO() {
		return employeeOperationDAO;
	}

	public SourceInitDAO getSourceInitDAO() {
		return sourceInitDAO;
	}

	public BookEmployeeOperationDAO getBookEmployeeOperationDAO() {
		return bookEmployeeOperationDAO;
	}

	public void setBookEmployeeOperationDAO(BookEmployeeOperationDAO bookEmployeeOperationDAO) {
		this.bookEmployeeOperationDAO = bookEmployeeOperationDAO;
	}

}
