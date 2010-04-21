package com.miop.api.bean;

import java.lang.reflect.Field;

public class Picture {

	private String pictureid  = null;
	private String title  = null;
	private String ownerid  = null;
	private String ownername  = null;
	private String views  = null;
	private String createtime  = null;
	private String smallurl  = null;
	private String bigurl  = null;
	
	
	public String getBigurl() {
		return bigurl;
	}
	public void setBigurl(String bigurl) {
		this.bigurl = bigurl;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public String getPictureid() {
		return pictureid;
	}
	public void setPictureid(String pictureid) {
		this.pictureid = pictureid;
	}
	public String getSmallurl() {
		return smallurl;
	}
	public void setSmallurl(String smallurl) {
		this.smallurl = smallurl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		Field[] fields = Picture.class.getDeclaredFields();// .getFields();
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
