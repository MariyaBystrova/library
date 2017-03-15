package com.epam.library.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epam.library.dao.BookOperationDAO;
import com.epam.library.dao.connectionpool.ConnectionPool;
import com.epam.library.dao.connectionpool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.domain.Book;

public class SQLBookOperationDAO implements BookOperationDAO {
	public final static String INSERT_INTO_BOOK = "INSERT INTO `book_library`.`book` (`title`,`publish_year`,`author`, `brief`) VALUES (?,?,?,?);";
	public final static String SELECT_FROM_BOOK_BY_TITLE = "SELECT b.`title`, b.`publish_year`, b.`author`, b.`brief` FROM `book` AS b WHERE b.`title` = ?;";
	public final static String DELETE_BOOK_BY_TITLE = "DELETE FROM `book` WHERE `title` = ?;";
	public final static String UPDATE_BOOK_TITLE = "UPDATE `book` AS b JOIN (SELECT ? AS old_title, ? AS new_title) AS q ON b.`title` = q.old_title SET b.`title` = q.new_title;";

	@Override
	public boolean createBook(Book book) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_BOOK)) {
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
			try (Statement s = con.createStatement()) {
				try (PreparedStatement ps = con.prepareStatement(SELECT_FROM_BOOK_BY_TITLE)) {
					ps.setString(1, title);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Book book = new Book(rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(2));
						list.add(book);
					}
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
			try (PreparedStatement ps = con.prepareStatement(DELETE_BOOK_BY_TITLE)) {
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
			try (PreparedStatement ps = con.prepareStatement(UPDATE_BOOK_TITLE)) {
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
