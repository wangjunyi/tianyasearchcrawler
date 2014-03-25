package edu.hpcde.tianyasearchcrawler;

import java.util.Date;

/** 
 * 类说明 
 * @author  Fang 
 */

public class HeadInfo {
	public String title; //帖子标题
	public String url; //帖子url
	public String userUrl; //发帖用户url
	public String board; //板块
	public Date datetime; //发帖时间
	@Override
	public String toString() {
		return "HeadInfo [title=" + title + ", url=" + url + ", userUrl="
				+ userUrl + ", board=" + board + ", datetime=" + datetime + "]";
	}
	

}
