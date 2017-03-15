package com.epam.library.dao;

import java.util.List;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.domain.Book;

public interface BookOperationDAO {
	public boolean createBook(Book book) throws DAOException;

	public List<Book> getBookByTitle(String title) throws DAOException;

	public boolean deleteBookByTitle(String title) throws DAOException;

	public boolean renameBook(String oldTitle, String newTitle) throws DAOException;
}
