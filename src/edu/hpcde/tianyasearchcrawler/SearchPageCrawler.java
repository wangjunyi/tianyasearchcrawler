package edu.hpcde.tianyasearchcrawler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 用于抓取搜索页的帖子url等信息
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
	public void save2File(ArrayList<String> urlList, String filePath){
		
	}
	public void run() throws Exception{
		String nakedurl = "http://search.tianya.cn/bbs";
		String args = "?q=" + this.keyword + "&s=" + this.sortby +"&f=" + this.searchby;	
		Parser parser = new Parser(nakedurl, args);
		ArrayList<String> urlList = parser.getUrlList();
		for(String s:urlList){
			System.out.println(s);
		}
		
	}
	/**
	 * @param args
	 * 设想参数：关键字、存储文件路径
	 */
	public static void main(String[] args){

		try {
			SearchPageCrawler spc = new SearchPageCrawler("MH370");
			spc.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
