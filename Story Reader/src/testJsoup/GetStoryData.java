package testJsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import SiteBean.BookMarker;
import XMLBean.Book;
import XMLBean.Path;
import XMLBean.WebSite;

import common.SoupEntry;

/**
 * 根据配置读取网页信息
 * @author WH1607076
 * @since 2016年12月9日
 */
public class GetStoryData extends SoupEntry {
	
	private String configFilePath = "";
	
	private List<WebSite> webSites;

	public void init(){
		webSites = GetRuleData.getInfo(configFilePath);
	}
	
	/**
	 * 生成书签
	 * @return 书签列表
	 */
	public List<BookMarker> getBookMarker(){
		List<BookMarker> bookMarkers = new ArrayList<BookMarker>();
		for (int webIndex = 0, webCount = webSites.size(); webIndex < webCount; webIndex++) {
			WebSite web = webSites.get(webIndex);
			String webName = web.getName();
			String baseUrl = web.getBaseUrl();
			List<Book> books = web.getBooks();
			for (int bookIndex = 0, bookCount = books.size(); bookIndex < bookCount; bookIndex++) {
				BookMarker bookMarker = new BookMarker();
				bookMarker.setSiteName(webName);
				bookMarker.setSiteUrl(baseUrl);
				bookMarker.setBook(books.get(bookIndex));
				bookMarkers.add(bookMarker);
			}
		}
		return bookMarkers;
	}
	
	public void processMenu(BookMarker bookMarker){
		
	}
	
	public Map<Integer, Section> getStoryMenu(Book book){
		path = book.getCatalogUrl();
		connectTo();
		Element ele = document.child(0);
		for(Path path : book.getCatalogPaths()) {
			String type = path.getType();
			if(type.equals("tag")){
				ele.getElementsByTag(path.getContent());
			}
		}
		Element list = document.getElementById("list");
		Elements eles = list.getElementsByTag("dd");
		Map<Integer,Section> map = new HashMap<Integer, Section>();
		int i = 0;
		for(Element e : eles){
			i ++;
			Element a = e.child(0);
			Section sec = new Section(i, a.attr("href"), a.text());
			map.put(i, sec);
		}
		return map;
	}
	
	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	public List<WebSite> getWebSites() {
		return webSites;
	}

	public void setWebSites(List<WebSite> webSites) {
		this.webSites = webSites;
	}
	
}
