package common;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SoupEntry implements ConnectionEntry {
	protected String path;
	protected Document document;

	/**
	 * 根据路径获取文档对象
	 * @param path
	 * @return
	 */
	public void connectTo(){
		try {
			document = Jsoup.connect(path).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}
