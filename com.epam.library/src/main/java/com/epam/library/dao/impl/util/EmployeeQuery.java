package com.epam.library.dao.impl.util;

public class EmployeeQuery {
	private EmployeeQuery() {
	}

	public final static String SELECT_EMPLOYEES_HAVING_MORE_THAN_ONE_BOOK = "SELECT e.`name`, e.`date_of_birth`, e.`email`, COUNT(*) "
																			+ "FROM `employee` AS e  "
																			+ "JOIN `employee_book` AS eb ON eb.`employee_id` = e.`id` "
																			+ "GROUP BY eb.`employee_id`, e.`name`, e.`date_of_birth`, e.`email` "
																			+ "HAVING COUNT(*)>1 "
																			+ "ORDER BY COUNT(*) DESC;";
	public final static String SELECT_EMPLOYEES_HAVING_LESS_OR_EQUALS_THAN_TWO_BOOKS = "SELECT e.`name`, e.`date_of_birth`, e.`email`, COUNT(eb.book_id) "
																						+ "FROM `employee` AS e "
																						+ "LEFT JOIN `employee_book` AS eb ON eb.`employee_id` = e.`id` "
																						+ "GROUP BY eb.`employee_id` , e.`name`, e.`date_of_birth`, e.`email` "
																						+ "HAVING COUNT(*)<=2 "
																						+ "ORDER BY e.`date_of_birth`, COUNT(*)  DESC;;";

}
