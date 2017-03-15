package com.epam.library.domain;

import java.io.Serializable;

public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private String author;
	private String brief;
	private String publishYear;

	public Book() {
	}

	public Book(String title, String author, String brief, String publishYear) {
		this.title = title;
		this.author = author;
		this.brief = brief;
		this.publishYear = publishYear;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", brief=" + brief + ", publishYear=" + publishYear
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((brief == null) ? 0 : brief.hashCode());
		result = prime * result + ((publishYear == null) ? 0 : publishYear.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null) {
				return false;
			}
		} else if (!author.equals(other.author)) {
			return false;
		}
		if (brief == null) {
			if (other.brief != null) {
				return false;
			}
		} else if (!brief.equals(other.brief)) {
			return false;
		}
		if (publishYear == null) {
			if (other.publishYear != null) {
				return false;
			}
		} else if (!publishYear.equals(other.publishYear)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(String publishYear) {
		this.publishYear = publishYear;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
