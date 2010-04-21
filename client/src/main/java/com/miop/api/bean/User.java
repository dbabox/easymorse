package com.miop.api.bean;

import java.lang.reflect.Field;

public class User {
	/** uid 用户的唯�?D*/
	private String uid;
	/** username 用户的名�?*/
	private String username;
	/** 是否在线  **/
	private String online;
	/**性别*/
	private String gender = null;
	/**出生日期，yyyy-mm-dd*/
	private String birthday = null;
	/**�?��*/
	private String blood = null;
	/**用户的小头像�?6x16px*/
	private String tinyurl = null;
	/**用户的中头像�?8x48px*/
	private String middleurl = null;
	/**用户的大头像�?8x68px*/
	private String bigurl = null;
	/**用户的出生省�*/
	private String homeprovince = null;
	/**用户的出生城�*/
	private String homecity = null;
	/**用户的居住省�*/
	private String liveprovince = null;
	/**用户的居住城�*/
	private String livecity = null;
	/**用户的空间首页地�*/
	private String profileurl = null;
	/**用户的兴趣爱好**/
	private String interest = null;
	
	
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}	
	public String getBigurl() {
		return bigurl;
	}
	public void setBigurl(String bigurl) {
		this.bigurl = bigurl;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHomecity() {
		return homecity;
	}
	public void setHomecity(String homecity) {
		this.homecity = homecity;
	}
	public String getHomeprovince() {
		return homeprovince;
	}
	public void setHomeprovince(String homeprovince) {
		this.homeprovince = homeprovince;
	}
	public String getLivecity() {
		return livecity;
	}
	public void setLivecity(String livecity) {
		this.livecity = livecity;
	}
	public String getLiveprovince() {
		return liveprovince;
	}
	public void setLiveprovince(String liveprovince) {
		this.liveprovince = liveprovince;
	}
	public String getMiddleurl() {
		return middleurl;
	}
	public void setMiddleurl(String middleurl) {
		this.middleurl = middleurl;
	}
	public String getProfileurl() {
		return profileurl;
	}
	public void setProfileurl(String profileurl) {
		this.profileurl = profileurl;
	}
	public String getTinyurl() {
		return tinyurl;
	}
	public void setTinyurl(String tinyurl) {
		this.tinyurl = tinyurl;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		Field[] fields = User.class.getDeclaredFields();// .getFields();
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
