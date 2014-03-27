package edu.hpcde.tianyasearchcrawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.org.apache.bcel.internal.generic.LASTORE;

import sun.net.idn.Punycode;

/** 
 * 将url解析并返回结果 
 * @author  Fang 
 
 */
public class Parser {
	
	public String url;

	/**
	 * @param url
	 */
	public Parser(String url, String args) {
		this.url = url + args;

	}
	
	
	public Document url2Doc() throws Exception{
		Document doc = Jsoup.connect(this.url)
				.timeout(3000)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36")
				.get();
		return doc;
	}
	
	
	public ArrayList<String> getUrlList() throws Exception{
		ArrayList<String> urlList = new ArrayList<String>();
		Document doc = url2Doc();
		String path = "#main > div.searchListOne > ul > li > div > h3 > a";
		Elements links = doc.select(path);
		for(Element link : links){
			urlList.add(link.attr("href"));
		}
		return urlList;
	}
	
	public int getLastPageNumber()throws Exception{
		String path="#main > div.long-pages > a:nth-last-child(2)";
		int n;
		Document doc = url2Doc();
		Element lastPageNumber=doc.select(path).first();
		n=Integer.parseInt(lastPageNumber.text());
		return n;
	}
	
	
	public ArrayList<HeadInfo> getHeadInfos() throws Exception{
		ArrayList<HeadInfo> headinfos = new ArrayList<HeadInfo>();
		Document doc = url2Doc();
		String path = "#main > div.searchListOne > ul > li > div > h3 > a";
		Elements links = doc.select(path);
		for(Element link : links){
			HeadInfo headinfo = new HeadInfo();
			headinfo.title = link.text();
			headinfo.url = link.attr("href");
			headinfos.add(headinfo);
			System.out.println(headinfo);
		}
		return headinfos;
		
	}
}
