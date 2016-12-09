package SiteBean;

public class Section {
	
	private Long id;
	private String href;
	private String title;
	
	public Section(Long id, String href, String title) {
		this.id = id;
		this.href = href;
		this.title = title;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
