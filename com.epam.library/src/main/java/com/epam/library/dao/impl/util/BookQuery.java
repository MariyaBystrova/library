package com.epam.library.dao.impl.util;

public class BookQuery {
	private BookQuery() {
	}

	public final static String INSERT_INTO_BOOK = "INSERT INTO `book_library`.`book` (`title`,`publish_year`,`author`, `brief`) VALUES (?,?,?,?);";
	public final static String SELECT_FROM_BOOK_BY_TITLE = "SELECT b.`title`, b.`publish_year`, b.`author`, b.`brief` "
															+ "FROM `book` AS b "
															+ "WHERE b.`title` = ?;";
	public final static String DELETE_BOOK_BY_TITLE = "DELETE FROM `book` WHERE `title` = ?;";
	public final static String UPDATE_BOOK_TITLE = "UPDATE `book` AS b "
													+ "JOIN (SELECT ? AS old_title, ? AS new_title) AS q ON b.`title` = q.old_title "
													+ "SET b.`title` = q.new_title;";

}
