package com.epam.library.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.library.dao.BookOperationDAO;
import com.epam.library.dao.connectionpool.ConnectionPool;
import com.epam.library.dao.connectionpool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.impl.util.BookQuery;
import com.epam.library.domain.Book;

public class SQLBookOperationDAO implements BookOperationDAO {

	@Override
	public boolean createBook(Book book) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(BookQuery.INSERT_INTO_BOOK)) {
				ps.setString(1, book.getTitle());
				ps.setString(2, book.getPublishYear());
				ps.setString(3, book.getAuthor());
				ps.setString(4, book.getBrief());
				if (ps.executeUpdate() != 0) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
	}

	@Override
	public List<Book> getBookByTitle(String title) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		List<Book> list = new ArrayList<>();
		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(BookQuery.SELECT_FROM_BOOK_BY_TITLE)) {
				ps.setString(1, title);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Book book = new Book(rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(2));
					list.add(book);
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
		return list;
	}

	@Override
	public boolean deleteBookByTitle(String title) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(BookQuery.DELETE_BOOK_BY_TITLE)) {
				ps.setString(1, title);
				int rows = ps.executeUpdate();

				if (rows <= 0) {
					return false;
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
			return true;

		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
	}

	@Override
	public boolean renameBook(String oldTitle, String newTitle) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(BookQuery.UPDATE_BOOK_TITLE)) {
				ps.setString(1, oldTitle);
				ps.setString(2, newTitle);

				if (ps.executeUpdate() != 0) {
					return true;
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
		return false;
	}

}
