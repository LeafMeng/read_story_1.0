package XMLBean;

import java.util.List;

public class Book {
	private String name;
	private String catalogUrl ;
	private String catalogEndNode ;
	private String catalogUrlAttr ;
	private String catalogValAttr ;
	private List<Path> catalogPaths;
	private String contentEndNode ;
	private String contentUrlAttr ;
	private String contentValAttr ;
	private List<Path> contentPaths;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatalogUrl() {
		return catalogUrl;
	}
	public void setCatalogUrl(String catalogUrl) {
		this.catalogUrl = catalogUrl;
	}
	public String getCatalogEndNode() {
		return catalogEndNode;
	}
	public void setCatalogEndNode(String catalogEndNode) {
		this.catalogEndNode = catalogEndNode;
	}
	public String getCatalogUrlAttr() {
		return catalogUrlAttr;
	}
	public void setCatalogUrlAttr(String catalogUrlAttr) {
		this.catalogUrlAttr = catalogUrlAttr;
	}
	public String getCatalogValAttr() {
		return catalogValAttr;
	}
	public void setCatalogValAttr(String catalogValAttr) {
		this.catalogValAttr = catalogValAttr;
	}
	public List<Path> getCatalogPaths() {
		return catalogPaths;
	}
	public void setCatalogPaths(List<Path> catalogPaths) {
		this.catalogPaths = catalogPaths;
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
