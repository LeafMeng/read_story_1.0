package testJsoup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import XMLBean.Book;
import XMLBean.Path;
import XMLBean.WebSite;

/**
 * 使用Jsoup加载配置文件， 获取XML配置信息
 * @author WH1607076
 * @since 2016年12月9日
 */
public class GetRuleData {
	
	/**
	 * 获取 xml 配置的第一个 storyweb
	 * @param doc 文档对象
	 * @return storyweb元素
	 */
	public static Element getRoot(Document doc){
		return doc.getElementsByTag("storyweb").get(0);
	}
	
	/**
	 * 根据配置文档路径获取所有配置信息
	 * @param path 配置文档路径
	 * @return 配置信息
	 */
	public static List<WebSite> getInfo(String path){
		File file = new File(path);
		try {
			Document document = Jsoup.parse(file, "utf-8");
			return readWebsInfo(getRoot(document).getElementsByTag("website"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 读取 配置的 网页站点信息
	 * @param elements 多个网页站点元素
	 * @return 信息
	 */
	public static List<WebSite> readWebsInfo(Elements elements) {
		List<WebSite> webSites = new ArrayList<WebSite>();
		for(Element ele : elements) {
			webSites.add(readWebInfo(ele));
		}
		return webSites;
	}

	/**
	 * 读取信息
	 * @param ele 单个网页站点元素
	 * @return
	 */
	public static WebSite readWebInfo(Element ele) {
		WebSite webSite = new WebSite();
		webSite.setBaseUrl(ele.attr("base"));
		webSite.setName(ele.attr("name"));
		webSite.setBooks(readBooksInfo(ele.getElementsByTag("book")));
		return webSite;
	}
	
	/**
	 * 读取 配置  书籍 节点信息
	 * @param elements 多个书籍节点元素
	 * @return 节点数据
	 */
	private static List<Book> readBooksInfo(Elements elements) {
		List<Book> books = new ArrayList<Book>();
		for(Element ele : elements) {
			books.add(readBookInfo(ele));
		}
		return books;
	}

	/**
	 * 读取 配置  书籍 节点信息
	 * @param ele 单个书籍节点元素
	 * @return 节点数据
	 */
	public static Book readBookInfo(Element ele) {
		Book book = new Book();
		book.setName(ele.attr("name"));
		
		Element catalogPath = ele.getElementsByTag("catalog-path").get(0);
		
		book.setCatalogEndNode(catalogPath.attr("child"));
		book.setCatalogPaths(readPathsInfo(catalogPath.children()));
		book.setCatalogUrl(ele.getElementsByTag("catalog-url").get(0).text());
		book.setCatalogUrlAttr(catalogPath.attr("url"));
		book.setCatalogValAttr(catalogPath.attr("title"));
		
		Element contentPath = ele.getElementsByTag("content-path").get(0);
		
		book.setContentEndNode(contentPath.attr("child"));
		book.setContentPaths(readPathsInfo(contentPath.children()));
		book.setContentUrlAttr(contentPath.attr("url"));
		book.setContentValAttr(contentPath.attr("title"));
		return book;
	}
	
	/**
	 * 读取查询路径 信息
	 * @param elements 多个路径节点元素
	 * @return 路径信息
	 */
	public static List<Path> readPathsInfo(Elements elements) {
		List<Path> paths = new ArrayList<Path>();
		for(Element ele : elements) {
			paths.add(readPathInfo(ele));
		}
		return paths;
	}

	/**
	 * 读取查询路径 信息
	 * @param ele 单个路径节点元素
	 * @return 路径信息
	 */
	public static Path readPathInfo(Element ele) {
		Path path = new Path();
		path.setContent(ele.text());
		path.setIndex(ele.attr("index"));
		path.setType(ele.attr("type"));
		return path;
	}

}
