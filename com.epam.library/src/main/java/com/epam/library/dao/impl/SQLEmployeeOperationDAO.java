package com.epam.library.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import com.epam.library.dao.EmployeeOperationDAO;
import com.epam.library.dao.connectionpool.ConnectionPool;
import com.epam.library.dao.connectionpool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.impl.util.EmployeeQuery;
import com.epam.library.domain.Employee;

public class SQLEmployeeOperationDAO implements EmployeeOperationDAO {
	
	@Override
	public Map<Employee, Integer> getEmployeesWithMoreThanOneBook() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		Map<Employee, Integer> map = new LinkedHashMap<>();
		try {
			con = connectionPool.takeConnection();
			try (Statement s = con.createStatement()) {
				try (ResultSet rs = s.executeQuery(EmployeeQuery.SELECT_EMPLOYEES_HAVING_MORE_THAN_ONE_BOOK)) {
					while (rs.next()) {
						Employee e = new Employee(rs.getString(1), rs.getString(3), rs.getDate(2));
						map.put(e, rs.getInt(4));
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
		return map;
	}

	@Override
	public Map<Employee, Integer> getEmployeesWithLQThanTwoBooks() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		Map<Employee, Integer> map = new LinkedHashMap<>();
		try {
			con = connectionPool.takeConnection();
			try (Statement s = con.createStatement()) {
				try (ResultSet rs = s.executeQuery(EmployeeQuery.SELECT_EMPLOYEES_HAVING_LESS_OR_EQUALS_THAN_TWO_BOOKS)) {
					while (rs.next()) {
						Employee e = new Employee(rs.getString(1), rs.getString(3), rs.getDate(2));
						map.put(e, rs.getInt(4));
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
		return map;
	}

}
