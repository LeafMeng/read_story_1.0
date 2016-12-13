package SiteBean;

import java.util.List;

import XMLBean.Book;
import XMLBean.Path;

public class BookMarker {
	
	private String siteName;
	private String siteUrl;
	private Book book;
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	@Override
	public String toString() {
		return "{ " + this.book.getName() + "," + this.siteName + "," + this.siteUrl + "}";
	}
	
}
