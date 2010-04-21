package com.miop.api.bean;

import java.lang.reflect.Field;

public class PayOrderInfo {

	private int AppCommodityID = -1;
	private int count = -1;
	private int DisCount = -1;
	
	
	
	public int getAppCommodityID() {
		return AppCommodityID;
	}



	public void setAppCommodityID(int appCommodityID) {
		AppCommodityID = appCommodityID;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}



	public int getDisCount() {
		return DisCount;
	}



	public void setDisCount(int disCount) {
		DisCount = disCount;
	}

	public String toJsonString(){
		StringBuffer sb = new StringBuffer( "{" );
		
		sb.append( "\"AppCommodityID\":").append( AppCommodityID );
		sb.append( "," );
		sb.append( "\"count\":").append( count );
		sb.append( "," );
		sb.append( "\"DisCount\":").append( DisCount );		
		sb.append( "}" );
		
		return sb.toString();
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		Field[] fields = PayOrderInfo.class.getDeclaredFields();// .getFields();
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
