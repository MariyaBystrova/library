package com.epam.library.dao.connectionpool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.library.dao.connectionpool.exception.ConnectionPoolException;

/**
 * Represents a thread-safe connection pool with an insure capacity 5 which
 * might be increased to the determined number. Store the open connections to
 * have an ability .
 * 
 * @author Mariya Bystrova
 *
 */
public final class ConnectionPool {
	private final static Logger logger = LogManager.getLogger(ConnectionPool.class.getName());
	/**
	 * A thread-safe queue which contains a range of available connections.
	 */
	private BlockingQueue<Connection> availableConnectionQueue;
	/**
	 * A thread-safe queue which contains a range of busy(given away)
	 * connections.
	 */
	private BlockingQueue<Connection> givenAwayConnectionQueue;
	/**
	 * Driver's name for database connectivity.
	 */
	private String driverName;
	/**
	 * A database url of the form <code>jdbc:subprotocol:subname</code>.
	 */
	private String url;
	/**
	 * The database user on whose behalf the connection is being made.
	 */
	private String user;
	/**
	 * The user's password.
	 */
	private String password;
	/**
	 * Pool's capacity.
	 */
	private int poolSize;
	/**
	 * An instance of ConnectionPool object.
	 */
	private static ConnectionPool instance;

	/**
	 * Creates an object of the ConnectionPool configured according to the
	 * {@link by.tr.totalizator.dao.connectionpool.DBResourceManager}. In case
	 * of {@link java.lang.NumberFormatException} while parsing a String value
	 * as an Integer, sets <code>poolSize</code> as 5.
	 */
	private ConnectionPool() {
		DBResourceManager dbResourseManager = DBResourceManager.getInstance();
		this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);
		this.url = dbResourseManager.getValue(DBParameter.DB_URL);
		this.user = dbResourseManager.getValue(DBParameter.DB_USER);
		this.password = dbResourseManager.getValue(DBParameter.DB_PASSWORD);
		try {
			this.poolSize = Integer.parseInt(dbResourseManager.getValue(DBParameter.DB_POOL_SIZE));
		} catch (NumberFormatException e) {
			poolSize = 5;
		}
	}

	/**
	 * Returns an instance of the ConnectionPool.
	 * 
	 * @return an instance of the ConnectionPool.
	 */
	public final static ConnectionPool getInstance() {
		if (null == instance) {
			synchronized (ConnectionPool.class) {
				if (null == instance) {
					instance = new ConnectionPool();
				}
			}
		}
		return instance;

	}

	/**
	 * Initializes the connection pool's fields.
	 * <p>
	 * Loads the driver to the memory.
	 * </p>
	 * <p>
	 * Creates an object of the {@link java.sql.Connection} and wraps it into
	 * the
	 * {@link by.tr.totalizator.dao.connectionpool.ConnectionPool.PooledConnection}.
	 * Sets all available connections into the
	 * <code>availableConnectionQueue</code>.
	 * </p>
	 * 
	 * @throws ConnectionPoolException
	 *             if a database access error occurs or the url is null.
	 */
	public void initPoolData() throws ConnectionPoolException {
		Locale.setDefault(Locale.ENGLISH);
		try {
			Class.forName(driverName);
			givenAwayConnectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			availableConnectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				PooledConnection pooledConnection = new PooledConnection(connection);
				availableConnectionQueue.add(pooledConnection);
			}
		} catch (SQLException e) {
			throw new ConnectionPoolException("SQLException in ConnectionPool", e);
		} catch (ClassNotFoundException e) {
			throw new ConnectionPoolException("Can't find database driver class", e);
		}
	}

	/**
	 * Disposes the pool's connection queue.
	 */
	public void dispose() {
		clearConnectionQueue();
	}

	/**
	 * Closes and removes all connections from both queues.
	 */
	private void clearConnectionQueue() {
		try {
			closeConnectionsQueue(givenAwayConnectionQueue);
			closeConnectionsQueue(availableConnectionQueue);
		} catch (SQLException e) {
			logger.error("Error closing the connection.", e);
		}
	}

	/**
	 * Returns an available connection.
	 * <p>
	 * Removes the connection from available connection's queue and inserts into
	 * the given away connection's queue.
	 * </p>
	 * <p>
	 * A thread-safe queues are waiting if necessary until an element becomes
	 * available.
	 * </p>
	 * 
	 * @return an available connection.
	 * @throws ConnectionPoolException
	 *             if {@link java.util.concurrent.BlockingQueue} is interrupted
	 *             while waiting.
	 */
	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection = null;
		try {
			connection = availableConnectionQueue.take();
			givenAwayConnectionQueue.add(connection);
		} catch (InterruptedException e) {
			throw new ConnectionPoolException("Error connecting to the data source.", e);
		}
		return connection;
	}

	/**
	 * Returns the connection to the available connection's queue.
	 * 
	 * @param con
	 *            the connection to be returned.
	 */
	public void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			logger.error("Connection isn't return to the pool.");
		}
	}

	/**
	 * Truly closes and removes all connections, in the specified queue.
	 * 
	 * @param queue
	 *            a value of BlockingQueue containing connections to be closed.
	 * @throws SQLException
	 *             if a database access error occurs.
	 */
	private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
		Connection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).reallyClose();
		}
	}

	/**
	 * Represents an inner class which implements the
	 * {@link java.sql.Connection} and changes the behavior of close method.
	 * 
	 * The method close() removes the connection from given away connection's
	 * queue and returns the connection to the connection pool's queue for the
	 * available connections.
	 * 
	 * Adds the method for truly closing the connection
	 * (@{by.tr.totalizator.dao.connectionpool.ConnectionPool.PooledConnection#reallyClose}).
	 * 
	 * @author Mariya Bystrova
	 *
	 */
	private class PooledConnection implements Connection {
		private Connection connection;

		/**
		 * Creates an object of PooledConnection, which wraps
		 * {@link java.sql.Connection}. Sets this connection's auto-commit mode
		 * to the active state.
		 * 
		 * @param c
		 *            a value of the {@link java.sql.Connection}.
		 * @throws SQLException
		 *             if a database access error occurs.
		 */
		public PooledConnection(Connection c) throws SQLException {
			this.connection = c;
			this.connection.setAutoCommit(true);
		}

		/**
		 * Truly closes the connection, not passes it to the active connection's
		 * queue.
		 * 
		 * @throws SQLException
		 *             if a database access error occurs.
		 */
		public void reallyClose() throws SQLException {
			connection.close();
		}

		/**
		 * Overriding version of this method which releases the connection
		 * without closing it.
		 * <p>
		 * Removes the connection from given away connection's queue and returns
		 * the connection to the connection pool's queue for the available
		 * connections
		 * </p>
		 * <p>
		 * Puts this connection in read-only mode.
		 * </p>
		 * 
		 * @throws SQLException
		 *             if this method is called on a closed connection or this
		 *             method is called during a transaction or if the problems
		 *             with removing or inserting the connection to the
		 *             appropriate queue.
		 */
		@Override
		public void close() throws SQLException {
			if (connection.isClosed()) {
				throw new SQLException("Attempting to close closed connection.");
			}
			if (connection.isReadOnly()) {
				connection.setReadOnly(false);
			}
			if (!givenAwayConnectionQueue.remove(this)) {
				throw new SQLException("Error deleting connection from the given away connections pool.");
			}
			if (!availableConnectionQueue.offer(this)) {
				throw new SQLException("Error allocating connection in the pool.");
			}
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return connection.createStruct(typeName, attributes);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public void abort(Executor arg0) throws SQLException {
			connection.abort(arg0);
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public void releaseSavepoint(Savepoint arg0) throws SQLException {
			connection.releaseSavepoint(arg0);
		}

		@Override
		public void rollback(Savepoint arg0) throws SQLException {
			connection.rollback(arg0);
		}

		@Override
		public void setClientInfo(Properties arg0) throws SQLClientInfoException {
			connection.setClientInfo(arg0);
		}

		@Override
		public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
			connection.setNetworkTimeout(arg0, arg1);
		}

		@Override
		public void setSchema(String arg0) throws SQLException {
			connection.setSchema(arg0);
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
			connection.setTypeMap(arg0);
		}
	}
}