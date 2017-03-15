package com.epam.library.service.impl;

import java.util.List;

import com.epam.library.dao.BookOperationDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.factory.DAOFactory;
import com.epam.library.domain.Book;
import com.epam.library.service.BookService;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.impl.util.Validator;

public class EditBook implements BookService {

	@Override
	public boolean createBook(Book book) throws ServiceException {
		if (!Validator.validateBook(book)) {
			throw new ServiceException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		BookOperationDAO bookDAO = factory.getBookOperationDAO();
		try {
			if (!bookDAO.createBook(book)) {
				return false;
			}
		} catch (DAOException e) {
			throw new ServiceException("Add book failed. ", e);
		}
		return true;
	}

	@Override
	public List<Book> getBookByTitle(String title) throws ServiceException {
		if (!Validator.validateTitle(title)) {
			throw new ServiceException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		BookOperationDAO bookDAO = factory.getBookOperationDAO();
		try {
			List<Book> list = bookDAO.getBookByTitle(title);
			return list;
		} catch (DAOException e) {
			throw new ServiceException("Get book failed. ", e);
		}
	}

	@Override
	public boolean deleteBookByTitle(String title) throws ServiceException {
		if (!Validator.validateTitle(title)) {
			throw new ServiceException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		BookOperationDAO bookDAO = factory.getBookOperationDAO();
		try {
			if (!bookDAO.deleteBookByTitle(title)) {
				return false;
			}
		} catch (DAOException e) {
			throw new ServiceException("Delete book failed. ", e);
		}
		return true;
	}

	@Override
	public boolean renameBook(String oldTitle, String newTitle) throws ServiceException {
		if (!Validator.validateTitle(oldTitle) || !Validator.validateTitle(newTitle)) {
			throw new ServiceException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		BookOperationDAO bookDAO = factory.getBookOperationDAO();
		try {
			if (!bookDAO.renameBook(oldTitle, newTitle)) {
				return false;
			}
		} catch (DAOException e) {
			throw new ServiceException("Rename book failed. ", e);
		}
		return true;
	}

}
