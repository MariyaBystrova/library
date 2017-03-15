package com.epam.library.service;

import java.util.List;

import com.epam.library.domain.Book;
import com.epam.library.service.exception.ServiceException;

public interface BookService {
	public boolean createBook(Book book) throws ServiceException;

	public List<Book> getBookByTitle(String title) throws ServiceException;

	public boolean deleteBookByTitle(String title) throws ServiceException;

	public boolean renameBook(String oldTitle, String newTitle) throws ServiceException;

}
