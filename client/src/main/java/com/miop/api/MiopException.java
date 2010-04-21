package com.miop.api;

public class MiopException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2128213423432432432L;
	
	/**
	 * 	1	发生了一个未知错误，请重新提交请求�?	
	 *	2	目前服务不可用�?	
	 *	4	应用请求已达到最大允许请求次数�?	
	 *	5	IP 地址被禁止�?	
	 *	100	缺少参数或参数非法�?	
	 *	101	非法API Key�?
	 *	102	无效或已超时�?Session Key。请将用户引导至登陆页面以获得一个新�?Session Key�?
	 *	103	call_id没有递增�?
	 *	104	认证错误�?
	 */
	private int errorCode;
	
	/**错误的信�J*/
	private String msg;
	
	public MiopException(int code,String msg){
		super(msg);
		this.errorCode = code;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append( this.msg ).append( "<br/>" );
		switch( this.errorCode ){
			case 1: sb.append( "发生了一个未知错误，请重新提交请求�?" ); break;
			case 2: sb.append( "目前服务不可用�?" ); break;
			case 4: sb.append( "应用请求已达到最大允许请求次数�?" ); break;
			case 5: sb.append( "IP 地址被禁止�?" ); break;
			case 100: sb.append( "缺少参数或参数非法�?" ); break;
			case 101: sb.append( "非法API Key�?" ); break;
			case 102: sb.append( "无效或已超时�?Session Key。请将用户引导至登陆页面以获得一个新�的Session Key" ); break;
			case 103: sb.append( "call_id没有递增�?" ); break;
			case 104: sb.append( "认证错误" ); break;
		}
		
		
		return sb.toString();
	}
	
}
