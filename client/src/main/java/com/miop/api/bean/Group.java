package com.miop.api.bean;

import java.lang.reflect.Field;

public class Group {
	private String groupid = null;
	private String creatorid = null;
	private String creatorname = null;
	private String groupname = null;
	private String descript = null;
	private String tags = null;
	private String category = null;
	private String members = null;
	private String topics = null;
	private String pictures = null;
	private String createtime = null;
	private String administrators = null;
	private String biglogo = null;
	private String smalllogo = null;
	
	
	
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getAdministrators() {
		return administrators;
	}
	public void setAdministrators(String administrators) {
		this.administrators = administrators;
	}
	public String getBiglogo() {
		return biglogo;
	}
	public void setBiglogo(String biglogo) {
		this.biglogo = biglogo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}
	public String getCreatorname() {
		return creatorname;
	}
	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public String getSmalllogo() {
		return smalllogo;
	}
	public void setSmalllogo(String smalllogo) {
		this.smalllogo = smalllogo;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getTopics() {
		return topics;
	}
	public void setTopics(String topics) {
		this.topics = topics;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		Field[] fields = Group.class.getDeclaredFields();// .getFields();
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
