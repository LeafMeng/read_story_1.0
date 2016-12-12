package testJsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import SiteBean.BookMarker;
import SiteBean.Section;
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
	
	public static void main(String[] args) {
		GetStoryData gsd = new GetStoryData();
		gsd.setConfigFilePath("src/resource/rule_path.xml");
		gsd.init();
		List<BookMarker> bookMarker = gsd.getBookMarker();
		for(BookMarker bm : bookMarker){
			System.out.println(bm.toString());
			gsd.processMenu(bm);
			System.out.println("***********************");
		}
		
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
		Book book = bookMarker.getBook();
		String end = book.getCatalogEndNode();
		List<Path> paths = book.getCatalogPaths();
		String urlAttr = book.getCatalogUrlAttr();
		String valAttr = book.getCatalogValAttr();
		Map<Integer,Section> map = new HashMap<Integer, Section>();
		
		path = book.getCatalogUrl();
		connectTo();
		Map<Integer, Section> nodes = handleCatalogEnd(getElementsByPath(paths, document), end, urlAttr, valAttr);
		for(Entry<Integer, Section> e : nodes.entrySet()){
			System.out.println(e.getKey()+", "+e.getValue().toString());
		}
	}
	
	public Map<Integer, Section> handleCatalogEnd(Elements eles, String endIndex, String urlAttr, String valAttr) {
		Map<Integer,Section> map = new HashMap<Integer, Section>();
		for (int i = 0; i < eles.size(); i++) {
			Element ele = eles.get(i);
			if(endIndex != null && !endIndex.equals("")){
				Element end = ele.child(Integer.parseInt(endIndex));
				String href = "";
				String content = "";
				if(urlAttr.equals("html") || urlAttr.equals("text")){
					href = end.text();
				} else {
					href = end.attr(urlAttr);
				}
				if(valAttr.equals("html") || valAttr.equals("text")){
					content = end.text();
				} else {
					content = end.attr(valAttr);
				}
				Section sec = new Section(i, href, content);
				map.put(i+1, sec);
			}
		}
		return map;
	}
	
	public Elements getElementsByPath(List<Path> paths, Document doc) {
		Elements result = new Elements();

		Elements eles = new Elements(doc);
		result.addAll(getElementsByPathsAndElement(paths, eles));

		return result;
	}
	
	public Elements getElementsByPathsAndElement(List<Path> paths, Elements eles) {
		Elements result = new Elements();
		
		for (int i = 0; i < paths.size(); i++) {
			result.addAll(getElementsByPathAndElements(paths.get(i), eles));
		}

		return result;
	}
	
	public Elements getElementsByPathAndElements(Path path, Elements eles) {
		Elements result = new Elements();
		for (int i = 0; i < eles.size(); i++) {
			result.addAll(getElementsByPathAndElement(path, eles.get(i)));
		}
		return result;
	}
	
	/**
	 * 根据路径和元素找到其下子元素
	 * @param path 路径
	 * @param ele 元素
	 * @return
	 */
	public Elements getElementsByPathAndElement(Path path, Element ele) {
		Elements result = new Elements();
		String content = path.getContent();
		String index = path.getIndex();
		String type = path.getType();
		if (type.equals("class")) {
			Elements eles = ele.getElementsByClass(content);
			if (index != null && !index.equals("")) {
				result.add(eles.get(Integer.parseInt(index)));
			} else {
				result = eles;
			}
		} else if (type.equals("tag")) {
			Elements eles = ele.getElementsByTag(content);
			if (index != null && !index.equals("")) {
				result.add(eles.get(Integer.parseInt(index)));
			} else {
				result = eles;
			}
		} else if (type.equals("id")) {
			result.add(ele.getElementById(content));
		}
		return result;
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
