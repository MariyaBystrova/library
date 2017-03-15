package com.epam.library.dao.connectionpool;

import java.util.ResourceBundle;

/**
 * Represents a class designed by Singleton pattern to access a bundle file with
 * DB configurations.
 * 
 * @author Mariya Bystrova
 *
 */
public class DBResourceManager {

	private final static DBResourceManager instance = new DBResourceManager();
	private final static String BUNDLE = "bd";

	private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE);

	public static DBResourceManager getInstance() {
		return instance;
	}

	/**
	 * Returns a string for the given key from this resource bundle.
	 * 
	 * @param key
	 *            a String value for the key.
	 * @return Gets a string for the given key from this resource bundle
	 */
	public String getValue(String key) {
		return bundle.getString(key);
	}
}
