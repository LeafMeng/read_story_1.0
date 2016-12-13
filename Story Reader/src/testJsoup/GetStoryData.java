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
import SiteBean.Catalog;
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
			Map<Integer, Catalog> nodes = gsd.processMenu(bm);

			for(Entry<Integer, Catalog> e : nodes.entrySet()){
				System.out.println(e.getKey()+", "+e.getValue().toString());
			}
			System.out.println("***********************\n");
			System.out.println(gsd.processCurSection(nodes.get(10)));
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
	
	public Map<Integer, Catalog> processMenu(BookMarker bookMarker){
		Book book = bookMarker.getBook();
		List<Path> paths = book.getCatalogPaths();
		
		path = book.getCatalogUrl();
		connectTo();
		
		Map<Integer, Catalog> nodes = handleCatalogEnd(getElementsByPath(paths, document), book,bookMarker.getSiteUrl());
		return nodes;
	}
	
	public Map<Integer, Catalog> handleCatalogEnd(Elements eles, Book book, String baseUrl) {
		String endIndex = book.getCatalogEndNode();
		String urlAttr = book.getCatalogUrlAttr();
		String valAttr = book.getCatalogValAttr();
		Map<Integer,Catalog> map = new HashMap<Integer, Catalog>();
		for (int i = 0; i < eles.size(); i++) {
			Element ele = eles.get(i);
			Map<String, String> tmp = handleEnd(ele, endIndex, urlAttr, valAttr);
			String href = tmp.get("href");
			if (!href.equals("") && href.indexOf("http") < 0) {
				href = baseUrl + href;
			}
			Catalog cat = new Catalog(i, href, tmp.get("content"), book.getContentEndNode(),book.getContentUrlAttr(),book.getContentValAttr(),book.getContentPaths());
			map.put(i+1, cat);
		}
		return map;
	}
	
	public Section processCurSection(Catalog cat) {
		List<Path> paths = cat.getContentPaths();
		
		setPath(cat.getHref());
		connectTo();
		
		return handleSectionEnd(getElementsByPath(paths, getDocument()),cat);
	}
	
	public Section handleSectionEnd(Elements elements, Catalog cat) {
		String end = cat.getContentEndNode();
		String urlAttr = cat.getContentUrlAttr();
		String valAttr = cat.getContentValAttr();

		StringBuffer content = new StringBuffer();
		for (Element ele : elements) {
			Map<String, String> map = handleEnd(ele, end, urlAttr, valAttr);
			content.append(map.get("content"));
		}
		
		return new Section(cat.getTitle(), cat.getHref(), content.toString());
	}
	
	public Map<String, String> handleEnd(Element ele, String endIndex, String urlAttr, String valAttr) {
		Map<String, String> result = new HashMap<String, String>();
		Element end = ele;
		if(endIndex != null && !endIndex.equals("")){
			end = ele.child(Integer.parseInt(endIndex));
		}
		
		if(urlAttr.equals("html") || urlAttr.equals("text")){
			result.put("href", end.text());
		} else if(!urlAttr.equals("")){
			result.put("href", end.attr(urlAttr));
		}
		
		if(valAttr.equals("html") || valAttr.equals("text")){
			result.put("content", end.text());
		} else if(!valAttr.equals("")){
			result.put("content", end.attr(valAttr));
		}
		
		return result;
	}
	
	public Elements getElementsByPath(List<Path> paths, Document doc) {
		Elements result = new Elements();

		Elements eles = new Elements(doc);
		result.addAll(getElementsByPathsAndElement(paths, eles));

		return result;
	}
	
	public Elements getElementsByPathsAndElement(List<Path> paths, Elements eles) {
		for (int i = 0; i < paths.size(); i++) {
			eles = getElementsByPathAndElements(paths.get(i), eles);
		}
		return eles;
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
