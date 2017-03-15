package com.epam.library.service.impl.util;

import com.epam.library.domain.Book;

public class Validator {
	private Validator() {
	}

	public static boolean validateBook(Book b) {
		if (b.getAuthor() == null) {
			return false;
		}
		if (b.getPublishYear() == null) {
			return false;
		}
		if (b.getTitle() == null) {
			return false;
		}
		return true;
	}

	public static boolean validateTitle(String title) {
		if (title == null) {
			return false;
		}
		return true;
	}
}
