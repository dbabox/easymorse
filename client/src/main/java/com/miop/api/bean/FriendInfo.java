package com.miop.api.bean;

import java.lang.reflect.Field;

public class FriendInfo {
	private String uid1;
	private String uid2;
	private String are_friends;
	
	public String getUid1() {
		return uid1;
	}
	public void setUid1(String uid1) {
		this.uid1 = uid1;
	}
	public String getUid2() {
		return uid2;
	}
	public void setUid2(String uid2) {
		this.uid2 = uid2;
	}
	public String getAre_friends() {
		return are_friends;
	}
	public void setAre_friends(String are_friends) {
		this.are_friends = are_friends;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		Field[] fields = FriendInfo.class.getDeclaredFields();// .getFields();
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
