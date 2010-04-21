package com.miop.api;

public enum MiopParam implements CharSequence {
	//BASE
	SIGNATURE,
	USER("user"),
	SESSION_KEY("session_key"),
	SDK_FROM("sdk_from"),
	TIME("time"),
	METHOD("method"),
	APP_KEY("api_key"),
	CALL_ID( "call_id" ),
	
	//URL
	SUBDOMAIN_API("api"),
	SUBDOMAIN_APP("app"),
	VERSION("1.0"),
	API_VERSION("version")
	;
	
	private String _paramName;
	private String _signatureName;
	
	private MiopParam() {
		this._paramName = "mi_sig";
		}
		
	private MiopParam(String name) {
		this._signatureName = name;
		this._paramName = "mi_sig_" + name;
		}
		
	/* Implementing CharSequence */
	public char charAt(int index) {
		return this._paramName.charAt(index);
	}
	
	public int length() {
		return this._paramName.length();
	}
	
	public CharSequence subSequence(int start, int end) {
		return this._paramName.subSequence(start, end);
	}
	
	public String toString() {
		return this._paramName;
	}
	  
	/**
	 * @return the signature name of this parameter
	 */
	public String getSignatureName() {
		return this._signatureName;
	}
	  
	/**
	 * Remove the MIOP signature prefix from the specified parameter.
	 * 
	 * @param paramName the name to remove the prefix from.
	 * 
	 * @return the specified name, with the MIOP signature prefix removed, if necessary.
	 */
	public static String stripSignaturePrefix(String paramName) {
		if (paramName != null && paramName.startsWith("mi_sig_")) {
			return paramName.substring(7);
		}
		return paramName;
	}

}
