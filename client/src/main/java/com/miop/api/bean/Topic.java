package com.miop.api.bean;

import java.lang.reflect.Field;

public class Topic {

	private String topicid = null;
	private String title  = null;
	private String url = null;
	private String author  = null;
	private String authorname   = null;
	private String status  = null;
	private String replys  = null;
	private String views   = null;
	private String lastreply  = null;
	
	private String lastreplyname   = null;
	private String lastreplytime    = null;
	private String createtime   = null;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorname() {
		return authorname;
	}
	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLastreply() {
		return lastreply;
	}
	public void setLastreply(String lastreply) {
		this.lastreply = lastreply;
	}
	public String getLastreplyname() {
		return lastreplyname;
	}
	public void setLastreplyname(String lastreplyname) {
		this.lastreplyname = lastreplyname;
	}
	public String getLastreplytime() {
		return lastreplytime;
	}
	public void setLastreplytime(String lastreplytime) {
		this.lastreplytime = lastreplytime;
	}
	public String getReplys() {
		return replys;
	}
	public void setReplys(String replys) {
		this.replys = replys;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTopicid() {
		return topicid;
	}
	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		Field[] fields = Topic.class.getDeclaredFields();// .getFields();
		for( Field field:fields ){
			try{
				sb.append( field.getName() ).append( ": " ).append( field.get( this ) ).append( "<br/>" );
			}catch( IllegalAccessException iae ){
				sb.append( field.getName() ).append( ": " ).append( "<br/>" );
			}
		}
		
		return sb.toString();
	}
}
