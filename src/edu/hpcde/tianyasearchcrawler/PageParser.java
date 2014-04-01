package edu.hpcde.tianyasearchcrawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/** 
 * ��ȡһ����ҳ��nextpage��������nestpage��url 
 * @author  Wang 
 
 */

public class PageParser {
	
	public String currentUrl;
	public Document doc;
	//public String urlNestPage;
	//public String theFirstreply;
	
	public PageParser(String currentUrl) throws Exception {
		this.currentUrl = currentUrl;
		url2Doc();

	}
	
	
	public void url2Doc() throws Exception{
		this.doc = Jsoup.connect(currentUrl)
				.timeout(3000)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36")
				.get();
	}
	
	public int getLastPageNumber()throws Exception{
		String path="#main > div.long-pages > a:nth-last-child(2)";
		int n;
		Element lastPageNumber=this.doc.select(path).first();
		n=Integer.parseInt(lastPageNumber.text());
		return n;
	}

	
	//��ȡ��ǰҳ����һҳ��url��Ϣ
	public String getNextPageUrl() throws Exception
	{
		String pathNextPage = "#post_head > div:nth-child(4) > div > form > a.js-keyboard-next";//��ȡ��ǰҳ��ġ���һҳ����ť��CSS Path
		Element link = this.doc.select(pathNextPage).first();
		if(link != null){
			System.out.println("����һҳ���ӣ���http://bbs.tianya.cn"+link.attr("href"));
			System.out.println("");
			String temp=link.attr("href");
			return temp;
		}
		else{
			String temp1="����һҳ���ӣ���������";
			return temp1;
			
			}
		
	}
	
	//�����ǰҳ���һ���ظ�
	public void getFirstReply() throws Exception{
		String content;
		String pathFistReply="#bd > div.atl-main > div:nth-child(1) > div > div.atl-con-bd.clearfix > div.bbs-content.clearfix";
		Elements firstReplyr=this.doc.select(pathFistReply);
		content=firstReplyr.text();
		System.out.println("����ǰҳ���һ�����ۣ���"+content);
		System.out.println("");
		
	}

}
