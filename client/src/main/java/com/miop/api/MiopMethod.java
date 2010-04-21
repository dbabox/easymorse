package com.miop.api;

/**
 * 139.com网站为第三方应用程序提供的SDK�?��包中�?��可用的api方法定义
 */
public enum MiopMethod {
	CREATE_TOKEN( "miop.auth.createToken" ),
	GET_SESSION( "miop.auth.getSession" ),
	IS_APP_USER( "miop.users.isAppUser" ),
	GET_USERS_INFO( "miop.users.getInfo" ),
	GET_USER_EDU( "miop.users.getEdu" ),
	GET_LOGIN_USER( "miop.users.getLoggedInUser" ),
	UPDATE_USER_POINT( "miop.users.updatePoint" ),
	GET_FRIENDS( "miop.friends.get" ),
	GET_APP_FRIENDS( "miop.friends.getAppUsers" ),
	IS_FRIENDS( "miop.friends.areFriends" ),
	GET_USER_ID_BY_MOBILE( "miop.users.getIdByMobile"),
	
	PUBLISH_TEMPLATIZED_ACTION( "miop.feed.publishTemplatizedAction" ),
	SEND_NOTIFICATION( "miop.notifications.send" ),
	SEND_SMS( "miop.sms.send" ),
	SMS_REGISTER( "miop.sms.register" ),
	CONTENT_FILTER( "miop.content.filter" ),
	PAY_GET_ORDER( "miop.pay.getOrder" ),
	
	GROUPS_GET_APPGROUP( "miop.groups.getAppGroup" ),
	GROUPS_GET( "miop.groups.get" ),
	GROUPS_GET_TOPICS( "miop.groups.getTopics" ),
	GROUPS_GET_PICTURES( "miop.groups.getPictures" )
    ;
    private String methodName;
    private boolean isTakeFile;

    private MiopMethod(String name) {
        this(name, false);
    }

    private MiopMethod(String name, boolean isTakeFile) {
        this.methodName = name;
        this.isTakeFile = isTakeFile;
    }

    /**
     * 是否上传文件，目前不支持
     * @return true: 是否上传文件
     */
    public boolean takeFile() {
        return false;
    }

    public String getMethodName() {
        return this.methodName;
    }
}
