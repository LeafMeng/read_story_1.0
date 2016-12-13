package SiteBean;

public class Section {
	private String title;
	private String url;
	private String content;
	
	
	public Section() {
		super();
	}

	public Section(String title, String url, String content) {
		this.title = title;
		this.url = url;
		this.content = content;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return title + " - " + url + "\n" + content;
	}
	
}
