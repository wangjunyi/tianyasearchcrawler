package edu.hpcde.tianyasearchcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 用于抓取并保存搜索页的帖子url等信息
 * @author Fang
 *
 */
public class SearchPageCrawler {
	private String keyword;
	private int sortby; //搜索排序（相关度、时间等）,对应参数s
	private int searchby; //搜索方法（按全文、按标题等）,对应参数f
	
	public static final int XIANGGUANDU = 0;  
    public static final int SHIJIAN = 4;  
    public static final int ZUIXINHUIFUSHIJIAN = 6;  
    public static final int HUIFUSHU = 10;  
    public static final int DIANJISHU = 8;
    
    public static final int QUANWEN = 0;
    public static final int ZUOZHE = 2;
    public static final int BIAOTI = 3;


	
	/**
	 * 构造函数，默认按照时间排序，按照全文搜索
	 * @throws UnsupportedEncodingException 
	 */
	public SearchPageCrawler(String keyword) throws UnsupportedEncodingException {
		this(keyword, SHIJIAN, QUANWEN);
	}

	/**
	 * @param keyword
	 * @param sortby
	 * @param searchby
	 * @throws UnsupportedEncodingException 
	 */
	public SearchPageCrawler(String keyword, int sortby, int searchby) throws UnsupportedEncodingException {
		
		this.keyword = java.net.URLEncoder.encode(keyword, "UTF-8"); //中文keyword必须先转码
		this.sortby = sortby;
		this.searchby = searchby;
		
	}
	
	
	public void save2DB(ArrayList<String>  urlList){
		
	}
	
	
	public void save2File(ArrayList<String> urlList, String filePath) throws IOException{
		try {
		  File f=new File(filePath);
		  BufferedWriter bw=new BufferedWriter(new FileWriter(f));
		  for(int i=0;i<urlList.size();i++){
	            bw.write(urlList.get(i));
	            bw.newLine();
	        }
	        bw.close();
		      } catch (IOException e1) {
		    	e1.printStackTrace();
		      System.out.println("文件写入错误");
		      System.exit(-1);
		    }
	}
	
	
	public void run() throws Exception{
		String nakedurl = "http://search.tianya.cn/bbs";
		ArrayList<String> urlListTotal=new ArrayList<String>();
		int count;
		String args = "?q=" + this.keyword +"&pn=1" + "&s=" + this.sortby +"&f=" + this.searchby;	
		Parser a=new Parser(nakedurl, args);
		count=a.getLastPageNumber();
		int i;
		for(i=1;i<=count;i++)
		{ 
			args = "?q=" + this.keyword +"&pn="+i + "&s=" + this.sortby +"&f=" + this.searchby;	
			Parser parser = new Parser(nakedurl, args);
			//System.out.println(parser.url);  输出每一页的url，暂时用不到；
			ArrayList<String> urlList = parser.getUrlList();
			urlListTotal.addAll(urlList);
			urlList.clear();
		}
		String filePath="C:\\Users\\wang\\Desktop\\java\\tianyasearchcrawler\\"+this.keyword+".txt";
		save2File(urlListTotal,filePath);
		
	}
	
	public void run2() throws Exception{//获取当前页面的nestpage URL，并输出当前页面的第一条回复
		String p="http://bbs.tianya.cn/post-funinfo-5154549-1.shtml";//测试用例，文章姚笛天涯帖子链接；
		do{
			PageParser c=new PageParser(p);
		    c.getFirstReply();
		    p=c.getNextPageUrl();
		}while(p!=null);
	}

	
	/**
	 * @param args
	 * 设想参数：关键字、存储文件路径
	 */
	public static void main(String[] args){

		try {
			SearchPageCrawler spc = new SearchPageCrawler("HP");
			//spc.run();          暂时屏蔽该功能
			//System.out.println("文件保存成功！");
			spc.run2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
