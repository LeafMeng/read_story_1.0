package SiteBean;

import java.util.List;

import XMLBean.Path;

public class Catalog {
	
	private Integer id;
	private String href;
	private String title;
	private String contentEndNode ;
	private String contentUrlAttr ;
	private String contentValAttr ;
	private List<Path> contentPaths;

	public Catalog(Integer id, String href, String title) {
		this.id = id;
		this.href = href;
		this.title = title;
	}
	
	public Catalog(Integer id, String href, String title, String contentEndNode, String contentUrlAttr, String contentValAttr, List<Path> contentPaths) {
		this.id = id;
		this.href = href;
		this.title = title;
		this.contentEndNode = contentEndNode;
		this.contentPaths = contentPaths;
		this.contentUrlAttr = contentUrlAttr;
		this.contentValAttr = contentValAttr;
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

	public String getContentEndNode() {
		return contentEndNode;
	}

	public void setContentEndNode(String contentEndNode) {
		this.contentEndNode = contentEndNode;
	}

	public String getContentUrlAttr() {
		return contentUrlAttr;
	}

	public void setContentUrlAttr(String contentUrlAttr) {
		this.contentUrlAttr = contentUrlAttr;
	}

	public String getContentValAttr() {
		return contentValAttr;
	}

	public void setContentValAttr(String contentValAttr) {
		this.contentValAttr = contentValAttr;
	}

	public List<Path> getContentPaths() {
		return contentPaths;
	}

	public void setContentPaths(List<Path> contentPaths) {
		this.contentPaths = contentPaths;
	}
	
	
}
