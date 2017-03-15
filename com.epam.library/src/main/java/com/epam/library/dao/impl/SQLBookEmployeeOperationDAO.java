package com.epam.library.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.epam.library.dao.BookEmployeeOperationDAO;
import com.epam.library.dao.connectionpool.ConnectionPool;
import com.epam.library.dao.connectionpool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.impl.util.BookEmployeeQuery;

public class SQLBookEmployeeOperationDAO implements BookEmployeeOperationDAO {

	private Set<Pair> pairSet = new HashSet<>();

	/**
	 * Fill the intermediate table by 100-300 randomly generated values (user_id
	 * and book_id). There should be at least one user with no Book relations.
	 * There should be at least one user with 3+ Book relations, i.e. more than
	 * 3 books read.
	 * 
	 * @throws DAOException
	 */
	public void randomlyFillEmployeeBookTable() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		try {
			con = connectionPool.takeConnection();

			for (int i = 1; i <= 100; i++) {
				try (PreparedStatement ps = con.prepareStatement(BookEmployeeQuery.INSERT_INTO_EMPLOYEE_BOOK)) {
					Random r = new Random();
					int bookId;
					int employeeId;
					do {
						bookId = r.nextInt(52);
						employeeId = r.nextInt(11);
					} while (!checkPair(bookId, employeeId));

					ps.setInt(1, bookId);
					ps.setInt(2, employeeId);
					ps.setInt(3, i);
					ps.executeUpdate();
				} catch (SQLException e) {
					throw new DAOException("Database access error.", e);
				}
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
	}

	private boolean checkPair(Integer i1, Integer i2) {
		if (i1 == 0 || i2 == 0) {
			return false;
		}
		Pair p = new Pair(i1, i2);
		if (pairSet.contains(p)) {
			return false;
		}
		pairSet.add(p);
		return true;
	}

	private class Pair {
		private Integer i1;
		private Integer i2;

		public Pair(Integer i1, Integer i2) {
			this.i1 = i1;
			this.i2 = i2;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((i1 == null) ? 0 : i1.hashCode());
			result = prime * result + ((i2 == null) ? 0 : i2.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (i1 == null) {
				if (other.i1 != null)
					return false;
			} else if (!i1.equals(other.i1))
				return false;
			if (i2 == null) {
				if (other.i2 != null)
					return false;
			} else if (!i2.equals(other.i2))
				return false;
			return true;
		}

		private SQLBookEmployeeOperationDAO getOuterType() {
			return SQLBookEmployeeOperationDAO.this;
		}

	}
}
