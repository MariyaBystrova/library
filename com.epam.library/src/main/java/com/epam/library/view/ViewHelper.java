package com.epam.library.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.library.domain.Book;
import com.epam.library.domain.Employee;

public class ViewHelper {
	private final static Logger logger = LogManager.getLogger(ViewHelper.class.getName());

	private ViewHelper() {
	}

	public static void printBookList(List<Book> list) {
		for (Book b : list) {
			logger.info(b.toString());
		}
	}

	public static void printEmployeeMap(Map<Employee, Integer> map) {
		Set<Employee> keySet = map.keySet();
		for (Employee e : keySet) {
			logger.info(e.getName() + ": " + map.get(e));
		}
	}

}
