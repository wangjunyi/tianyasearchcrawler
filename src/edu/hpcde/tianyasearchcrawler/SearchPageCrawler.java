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
 * ����ץȡ����������ҳ������url����Ϣ
 * @author Fang
 *
 */
public class SearchPageCrawler {
	private String keyword;
	private int sortby; //����������ضȡ�ʱ��ȣ�,��Ӧ����s
	private int searchby; //������������ȫ�ġ�������ȣ�,��Ӧ����f
	
	public static final int XIANGGUANDU = 0;  
    public static final int SHIJIAN = 4;  
    public static final int ZUIXINHUIFUSHIJIAN = 6;  
    public static final int HUIFUSHU = 10;  
    public static final int DIANJISHU = 8;
    
    public static final int QUANWEN = 0;
    public static final int ZUOZHE = 2;
    public static final int BIAOTI = 3;


	
	/**
	 * ���캯����Ĭ�ϰ���ʱ�����򣬰���ȫ������
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
		
		this.keyword = java.net.URLEncoder.encode(keyword, "UTF-8"); //����keyword������ת��
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
		      System.out.println("�ļ�д�����");
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
			//System.out.println(parser.url);  ���ÿһҳ��url����ʱ�ò�����
			ArrayList<String> urlList = parser.getUrlList();
			urlListTotal.addAll(urlList);
			urlList.clear();
		}
		String filePath="C:\\Users\\wang\\Desktop\\java\\tianyasearchcrawler\\"+this.keyword+".txt";
		save2File(urlListTotal,filePath);
		
	}
	
	public void run2() throws Exception{//��ȡ��ǰҳ���nestpage URL���������ǰҳ��ĵ�һ���ظ�
		String Test="http://bbs.tianya.cn/post-funinfo-5154549-1.shtml";//��������������Ҧ�������������ӣ�
		PageParser c=new PageParser(Test);
		do{
			PageParser c=new PageParser(Test);
		    c.getFirstReply();
		    c.currentUrl=c.getNextPageUrl();
		}while(c.currentUrl!=null);
	}

	
	/**
	 * @param args
	 * ����������ؼ��֡��洢�ļ�·��
	 */
	public static void main(String[] args){

		try {
			SearchPageCrawler spc = new SearchPageCrawler("HP");
			//spc.run();          ��ʱ���θù���
			//System.out.println("�ļ�����ɹ���");
			spc.run2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
