package testJsoup;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * jsoup网页扒取 测试
 * @author WH1607076
 * @since 2016年12月7日
 */
public class GetStory {
	
	private static final String menuText = "章节目录";
	private static final String prevText = "上一章";
	private static final String nextText = "下一章";
	
	private static Document document = null;

	private static String path = "";
	private static String fileName = "";

//	private static String url = "http://www.biqugezw.com/3_3096/";//Yi Nian Yong Heng
//	private static String url = "http://www.biqugezw.com/0_48/";//Long Wang Chuang Shuo
//	private static String url = "http://www.biqugezw.com/1_1066/1987656.html";
//	private static String url = "http://www.biqugezw.com/3_3979/";//Bu Xiu Fan Ren
	private static String url = "http://www.biqugezw.com/1_1601/";//Zao Hua Zhi Men

	private static String root = "http://www.biqugezw.com";
	private static String nextUrl = "";
	private static String menuUrl = "";
	private static String prevUrl = "";
	
	private static String currentTitle = "";
	private static String storyContent = "";
	
	private static Map<Integer, Section> menu;
	
	private static int eventFlag = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = "";
		while(true){
			switch (eventFlag) {
				case 0://程序运行
					getStoryMenu(url);
					logStory();
					break;
				case 1://上一章
					if(prevUrl.equals("")) break;
					url = prevUrl;
					getStoryContent();
					logStory();
					break;
				case 2://目录
					if(menuUrl.equals("")) break;
					url = menuUrl;
					getStoryMenu(url);
					logStory();
					break;
				case 3://下一章
					if(nextUrl.equals("")) break;
					url = nextUrl;
					getStoryContent();
					logStory();
					break;
				case 4://第几章
					if(menu == null || menu.isEmpty()) break;
					url = menu.get(Integer.parseInt(input)).getHref();
					getStoryContent();
					logStory();
					break;
				case 5://输入错误
					break;
			}
			input= "";
			System.out.print("operation : ");
			input = sc.nextLine();
			if("next".indexOf(input) >= 0 ){
				eventFlag = 3;
			}else if("prev".indexOf(input) >= 0 ){
				eventFlag = 1;
			}else if("menu".indexOf(input) >= 0){
				eventFlag = 2;
			}else {
				try{
					Integer.parseInt(input);
					eventFlag = 4;
				}catch(Exception e){
					eventFlag = 5;
				}
			}
		}
	}
	
	public static void test2() throws IOException{
		File file = new File(path);
		if( !file.exists()){
			file.mkdir();
		}
		if(file.isDirectory()){
			File newFile = new File(file,"ming.txt");
			if( !newFile.exists() ){
				newFile.createNewFile();
				System.out.println(newFile.getName());
				System.out.println(newFile.getAbsoluteFile());
			}
		}
	}
	
	public static void logStory(){
		System.out.println("当前页url：" + url);
		System.out.println(currentTitle);
		System.out.println(storyContent);
	}
	
	public static void getStoryContent(){
		try {
			document = Jsoup.connect(url).get();
			currentTitle = "";
			storyContent = "";
			
			if(document == null) return;
			
			
			Element all = document.getElementsByClass("content_read").get(0).getElementsByClass("box_con").get(0);
			Element bookName = all.getElementsByClass("bookname").get(0);
			Element content = all.getElementById("content");
			
			storyContent = solveContent(content.html());
			currentTitle = bookName.getElementsByTag("h1").get(0).text();
			
			Elements hrefs = bookName.getElementsByClass("bottem1").get(0).getElementsByTag("a");
			for(Element e : hrefs){
				String text = e.text();
				String href = e.attr("href");
				if(href.indexOf("http") != 0){
					href = root + href;
				}
				if(text.equals(menuText)){
					menuUrl = href;
				}else if(text.equals(prevText)){
					prevUrl = href;
				}else if(text.equals(nextText)){
					nextUrl = href;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void getStoryMenu(String url){
		try {
			document = Jsoup.connect(url).get();
			Element list = document.getElementById("list");
			Elements eles = list.getElementsByTag("dd");
			Map<Integer,Section> map = new HashMap<Integer, Section>();
			int i = 0;
			StringBuffer sb = new StringBuffer();
			for(Element ele : eles){
				i ++;
				Element a = ele.child(0);
				String href =a.attr("href").indexOf("http") == -1 ? (root + a.attr("href")) : a.attr("href");
				Section sec = new Section(i, href , a.text());
				map.put(i, sec);
				if(i%5 ==0)
					sb.append("\n");
				sb.append(i + ". " +a.text()+"\t\t");
			}
			menu = map;
			currentTitle = menuText;
			storyContent = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String solveContent(String contentHtml){
		String result = new String();
		result = contentHtml.substring(contentHtml.indexOf("<br />") + 6,
				contentHtml.lastIndexOf("<br />"));
		result = result.replaceAll("<br />", "").replaceAll(" ", "").replaceAll("\n\n", "\n").replaceAll("&nbsp;", "  ");
		String[] split = result.split("\n");
		StringBuilder sb = new StringBuilder();
		for(String s : split){
			while(s.length() > 50){
				sb.append(s.substring(0, 50)+"\n");
				s = s.substring(50);
			}
			sb.append(s+"\n");
		}
		return sb.toString();
	}
}

class Section {
	
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
}