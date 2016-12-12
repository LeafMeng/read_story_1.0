package SiteBean;

public class Section {
	
	private Integer id;
	private String href;
	private String title;
	
	public Section(Integer id, String href, String title) {
		this.id = id;
		this.href = href;
		this.title = title;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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

	@Override
	public String toString() {
		return "{ id=" + id + ",href=" + href + ",title=" + title + "}";
	}
	
	
}
