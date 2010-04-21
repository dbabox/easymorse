/**
 *
 */
package com.miop.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.miop.api.bean.PayOrderInfo;


/**
 * 
 * @author Administrator
 *
 */
public class Miop {
    public static final int PARSE_TYPE_XML = 1;
    public static final int PARSE_TYPE_JSON = 2;
	public static final String UTF8 = "UTF-8";
    private static String Miop_URL_PATTERN = "^https?://([^/]*\\.)?139\\.com(:\\d+)?/.*";
    private HttpServletRequest request;
    private HttpServletResponse response;
    protected MiopRestClient apiClient;
    protected String apiKey;
    protected String secret;
    protected Map<String,String> foParams;
    protected String userName;

    /**
     * Miop的构造函�?
     * 
     * @param request				本次请求的HttpServletRequest对象
     * @param response				本次请求的HttpServletResponse对象
     * @param apiKey				当前应用的公�?piKey，在申请应用的时候，由开放平台生�?
     * @param secret				当前应用的私钥，由开放平台生成，作为应用与平台间验证合法性时密钥，第三方应用�?��注意保护自己的私钥，�?��滞露，可以在�?��者应用中重置
     * @param tourl					当需要重新登录时，用户登录后的重定向地址，一般为当前地址@
     * 
     */
    public Miop(HttpServletRequest request, HttpServletResponse response,
        String apiKey, String secret, String tourl ) {
    	
       this.request = request;
       this.response = response;
       this.apiKey = apiKey;
       this.secret = secret;
       
       switch (PARSE_TYPE_XML) 
       {
	       case PARSE_TYPE_XML:
	           this.apiClient = new MiopXMLRestClient(this.apiKey, this.secret);
	           break;
	
	       case PARSE_TYPE_JSON:
	           System.out.println("waiting...");
       }
       
       validateFoParams();
       this.requireLogin( tourl );
       
    }

    /**
     * 返回应用的私钥Secret，申请第三方应用时，�?��平台自动生成
     * Returns the secret key used to initialize this object.
     */
    public String getSecret() {
        return secret;
    }
    
    /**
     * 返回公开的apikey，申请应用时，系统生�?
     * Returns the api key used to initialize this object.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * 获取miop当前的client实例
     * 
     * @return
     */
    public MiopRestClient<? > getApiClient() {
        return this.apiClient;
    }

    /**
     * 验证request过来的参数，若request中的参数列表为空，则验证cookie中的参数
     * validate params in request or cookie
     */
    private void validateFoParams() {
        // 分析request参数
        foParams = getValidFoParams(_getRequestParams(), 48 * 3600,
                MiopParam.SIGNATURE.toString());

        if ((foParams != null) && !foParams.isEmpty()) 
        {
            String userName = foParams.get(MiopParam.USER.getSignatureName());
            String session_key = foParams.get(MiopParam.SESSION_KEY.getSignatureName());
            String tmpSt = foParams.get(MiopParam.TIME.getSignatureName());
            String time = (tmpSt != null) ? String.valueOf(tmpSt) : null;
            setUser(userName, session_key, time);
        } 
        else 
        {
            // 分析 cookies 参数
            Map<String,String> cookieParams = _getCookiesParams();
            foParams = getValidFoParams(cookieParams, null, this.apiKey);

            if ((foParams != null) && !foParams.isEmpty()) {
                // parsing the user and session
                String userName = foParams.get(MiopParam.USER.getSignatureName());
                String session_key = foParams.get(MiopParam.SESSION_KEY.getSignatureName());
                String time = foParams.get(MiopParam.TIME.getSignatureName());
                setUser(userName, session_key, time );
            }
        }
    }
    
    /**
     * 返回Map<String, String> 的请求参数表，包括GET和POST�?
     *
     * @return Map<String, String> request中的getParameterMap
     */
    @SuppressWarnings("unchecked")
	private Map<String,String> _getRequestParams() {
        Map<String,String> results = new HashMap<String,String>();
        Map<String,String[]> map = request.getParameterMap();

        for (Entry<String,String[]> entry : map.entrySet()) {
            results.put(entry.getKey(), entry.getValue()[0]);
        }

        return results;
    }

    /**
     * 获取验证mi_sig的参数，mi_sig的�?为md5( k1=v1k2=v2k3=v3…�?…�?kn=vnsecret )，只有�?过此方式计算出来的mi_sig平台传�?过来的mi_sig参数值一致时，此请求的来源才有可能是139�?��平台
     * 
     * @param params				本次传�?过来的所有的参数
     * @param timeout				超时时间，如果当前时间已经超过此时间，则说明此请求已过期，不予处�?
     * @param namespace				mi_sig
     * 
     * @return						要被列入mi_sig计算的参数列�?
     */
    private Map<String,String> getValidFoParams(Map<String,String> params,
            Integer timeout, String namespace) {
            if (namespace == null) {
                namespace = "mi_sig";
            }

            String prefix = namespace + "_";
            int prefixLen = prefix.length();
            Map<String,String> miopParams = new HashMap<String,String>();

            for (Entry<String,String> requestParam : params.entrySet()) {
                if (requestParam.getKey().startsWith( prefix ) ) {
                    miopParams.put( requestParam.getKey().substring(prefixLen), requestParam.getValue() );
                }
            }

            if (timeout != null) {
                if (!miopParams.containsKey(MiopParam.TIME.getSignatureName())) {
                    return new HashMap<String,String>();
                }

                String tmpTime = miopParams.get(MiopParam.TIME.getSignatureName());

                if (tmpTime.indexOf('.') > 0) {
                    tmpTime = tmpTime.substring(0, tmpTime.indexOf('.'));
                }

                long time = Long.parseLong(tmpTime);

                if (((System.currentTimeMillis() / 1000) - time) > timeout) {
                    return new HashMap<String,String>();
                }
            }

            // 若传递的参数中包含了sig，则�?��验证合法�?
            if (!params.containsKey(namespace) ||
                    !verifySignature(miopParams, params.get(namespace))) {
                return new HashMap<String,String>();
            }

            return miopParams;
        }

    /**
     * 用户点击applist的链接进入应用时，重写cookie
     * 
     * @param userName
     * @param sessionKey
     * @param time
     */
    private void setUser(String userName, String sessionKey, String time) {

        Map<String,String> cookies = new HashMap<String,String>();
        cookies.put("user", userName);
        cookies.put("session_key", sessionKey);
        cookies.put("time", time );

        // 生成sig
        String sig = generateSig(cookies, this.secret);
        int age = 0;
        
        response.setHeader("P3P", "CP=\"NOI ADM DEV PSAi COM NAV OUR OTR STP IND DEM\"");
        for (Map.Entry<String,String> entry : cookies.entrySet()) {
            addCookie(this.apiKey + "_" + entry.getKey(), entry.getValue(),
                age);
        }
        
        addCookie(this.apiKey, sig, age);

        this.userName = userName;
        this.apiClient.setSessionKey(sessionKey);
        this.apiClient.setTime(time);
        this.apiClient.setUser(userName);
    }

    private void addCookie(String key, String value, int age) {
        Cookie cookie = new Cookie(key, value);

        if (age > 0) {
            cookie.setMaxAge(age);
        }

        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /***
     * 以javascript脚本的方式进行页面重定向，MIML的应用�?�?
     * 
     * @param url		重定向地�?
     */
    private void redirect(String url) {
        try {
            if (url.matches(Miop_URL_PATTERN)) {
                String out =
                    "<script type=\"text/javascript\">\nwindow.location.href = \"" +
                    url + "\";\n</script>";
                response.getWriter().write(out);
                response.flushBuffer();
            } else {
                response.sendRedirect(url);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * �?��当前用户是否处于登录状�?
     * 
     * @return		用户的登录状态，true or false
     */
    public boolean isLogin() {
        return getUser() != null;
    }

    /**
     * 取得当前登录用户的用户ID
     * 
     * @return		登录用户的用户ID
     */
    public String getLoginUser() {
        return getUser();
    }

    /**
     * Returns the user id of the logged in user associated with this object
     *
     * @return
     */
    public String getUser() {
        return this.userName;
    }
    
    /**
     * 获取本次请求的参数，指定参数名称
     * 
     * @param key			指定的参数名�?
     * 
     * @return				指定参数名称的�?
     */
    public String getParam(String key) {
    	if(foParams != null) {
    		return this.foParams.get(key);
    	}
    	
    	return null;
    }

    /**
     * �?��用户是否登录，如果未登录，则强迫进行登录，并在登录后重定向到指定的URL
     *
     * @param tourl				用户登录后要重定向的地址
     *           
     * @return 					当前用户的登录状态，true or false
     */
    public boolean requireLogin(String tourl ) {
        if (getUser() != null) {
            return false;
        }

        redirect(getLoginUrl(tourl ));

        return true;
    }

    /**
     * 获取平台的登录入口地�?��可传递一个目标地�?��使用户登录成功后，跳转到指定的地�?
     *
     * @param tourl					用户登录成功后的目标地址
     * 
     * @return						生成的用户登录地�?
     */
    public String getLoginUrl(String tourl ) {
        String url = getMiopUrl( null ) + "/login.php?";

        try {
            url += ( (tourl  != null)?("&tourl=" + URLEncoder.encode(tourl , "UTF-8")) : "" );
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return url;
    }

    /**
     * 根据本次请求的参数和应用的私钥来生成MD5签名
     * 
     * @param params			本次请求的参数列�?
     * @param secret			当前应用的私�?
     * 
     * @return					生成mi_sig�?
     */
    public static String generateSig(Map<String,String> params, String secret) {
        SortedSet<String> keys = new TreeSet<String>(params.keySet());

        keys.remove( MiopParam.SIGNATURE.toString() );

        String str = "";

        for (String key : keys) {
            str += (key + "=" + params.get(key));
        }

        str += secret;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
            
            StringBuilder result = new StringBuilder();

            for (byte b : md.digest()) {
                result.append(Integer.toHexString((b & 0xf0) >>> 4));
                result.append(Integer.toHexString(b & 0x0f));
            }

            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证请求的合法�?，根据本次请求的参数表与应用私钥，生成加密串，把�?��平台传�?过来的mi_sig值作为预期结果，如果比较�?��，则验证通过
     *
     * @param params				本次请求的参数表
     * @param expected_sig			�?��期的结果
     *            
     * @return						验证通过与否，true or false
     */
    public boolean verifySignature(Map<String,String> params,
        String expected_sig) {
        return generateSig(params, this.secret).equals(expected_sig);
    }

    

    private Map<String,String> _getCookiesParams() {
        Map<String,String> results = new HashMap<String,String>();
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                results.put(cookie.getName(), cookie.getValue());
            }
        }

        return results;
    }

    /**
     * 获取139.com的地�?��可以指定二级域名
     * 
     * @param subDomain				指定的二级域名，如：subDomain="app"，则本函数将返加http://app.139.com
     * @return
     */
    public static String getMiopUrl(String subDomain) {
        if ((subDomain == null) || subDomain.equals("")) {
            subDomain = "www";
        }

        return "http://"+ subDomain + ".139.com";
    }

    /**
     * 获取当前登录用户已经安装了当前应用的好友列表
     * 
     * @return
     * @throws MiopException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public Object getFriendsInappByUser() throws MiopException, IOException {
        return this.apiClient.callMethod(MiopMethod.GET_APP_FRIENDS);
    }

    /**
    * 获取好友列表
    * uid为空，获取当前登录用户的好友ID
    * uid不为空，则返回指定uid的好友ID列表
    * 指定的uid必须是当前登录用户或当前登录用户的好�?
    * @param							指定的用户ID
    * @return 							以xml的格式返回好友的UID列表
    */
    @SuppressWarnings("unchecked")
	public Object getFriendIds(String uid) throws MiopException, IOException {
        uid = ((uid == null) || uid.equals(""))? this.userName.trim().toLowerCase():uid.trim().toLowerCase();

        return this.apiClient.callMethod(MiopMethod.GET_FRIENDS,
        									new Pair("uid", uid));
    }

    /**
     * 通过手机号获取用户的ID号
     * 
     * @param mobile
     * @return
     * @throws MiopException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public Object getUserIdByMobile( String mobile )throws MiopException, IOException{
    	if( mobile == null || "".equals( mobile.trim() ) )
    		throw new MiopException( 100, "手机号错误" );
    	
    	return this.apiClient.callMethod( MiopMethod.GET_USER_ID_BY_MOBILE, new Pair( "mobile", mobile ) );
    }
    
    /**
        * 比较两组用户的关系，两个数组里的用户必须是当前登录用户或当前登录用户的好友，两组列表的长度必须相�?
        * @param	uids1 第一组用�?
        * @param	uids2 第二组用�?
        *
        * @return
        */
    @SuppressWarnings("unchecked")
	public Object friendsAreFriends(Collection<String> uids1,
        Collection<String> uids2) throws MiopException, IOException {
        return this.apiClient.callMethod(MiopMethod.IS_FRIENDS,
            new Pair<String,CharSequence>("uids1", MiopUtils.delimit(uids1)),
            new Pair<String,CharSequence>("uids2", MiopUtils.delimit(uids2)));
    }

    /***
     * 
     * @param uid				指定用户名，不填则默认查询当前用户，指定用户需要特殊授权
     * @param degree			指定所要的学位
     * @return
     * @throws MiopException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public Object getUserEdu( String uid, String degree ) throws MiopException, IOException {
    	return this.apiClient.callMethod( MiopMethod.GET_USER_EDU,
    			new Pair("uid", uid ),
                new Pair("degree", degree )
    			);
    }
    
    /**
    * 返回指定用户组的的资料，可以指定要获取的内容
    * @param	uids 							是用户Collection
    * @param	fields 							是指定的用户资料字段Collection
    *
    * Returns the requested info fields for the requested set of users
    * @param Collection $uids an array of user ids
    * @param Collection $fields an array of strings describing the info fields desired
    * @return
    */
    @SuppressWarnings("unchecked")
	public Object getUsersInfos(Collection<String> uids,
        Collection<String> fields) throws MiopException, IOException {
        return this.apiClient.callMethod(MiopMethod.GET_USERS_INFO,
            new Pair<String,CharSequence>( "uids", MiopUtils.delimit(uids) ),
            new Pair<String,CharSequence>( "fields", MiopUtils.delimit(fields)) );
    }

    /**
     * 判断�?��用户是否已经安装了应�?
     * 
     * @param uid				要验证的用户Id，如果不存在，则默认为当前用�?
     * @return
     * @throws MiopException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public Object IsAppUser( String uid ) throws MiopException, IOException{
    	if( uid == null || "".equals( uid.trim() ) )
    		uid = this.userName;
    	
    	return this.apiClient.callMethod(MiopMethod.IS_APP_USER, new Pair("uid", uid));
    }
    
    /**
     * 发�?通知信，可一次发送给多个用户�?
     * 通知信分为两种，�?��为app_to_user，将被表现为系统通知，一种为user_to_user，将表现为用户互�?
     * 
     * @param type					通知信的类型，�?值类型为"app_to_user" 或�? "user_to_user"
     * @param toIds					发�?通知信的目标用户，可以有多个
     * @param notification			发�?的�?知信内容，不可超�?00个字，如果超�?00字，将被强行截取，不可为空，如果为空，或者为空串，将抛出MiopException
     * @param messageType			通知信的互动类型�?：只读�?知信�?：需要回复，3:�?��用户确认，若取�?范围不在1-3取�?，则将默认为1
     * @return
     * @throws MiopException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public Object SendNotifications( String type, Collection<String> toIds, String notification, int messageType)
    throws MiopException, IOException{
    	
    	if( type == null || "".equals( type.trim() ) )
    		type = "app_to_user";
    	
    	if( messageType >3 || messageType < 1 )
    		messageType = 1;
    	
    	if( notification == null || "".equals( notification.trim() ) )
    		throw new MiopException( 100, "通知信内容不可为�空" );
    	
    	notification = notification.trim();
    	if( notification != null && notification.length() > 500 )
    		notification = notification.substring( 0, 500 );
    	
    	return this.apiClient.callMethod( MiopMethod.SEND_NOTIFICATION,  
    										new Pair("type", type ), 
    										new Pair("to_ids", MiopUtils.delimit( toIds ) ),
    										new Pair( "notification", notification ),
    										new Pair( "message_type", String.valueOf( messageType ) )
    	);
    }
    
    /**
     * 发�?�?��新鲜事Feed
     * 
     * @param templateId				发�?Feed时所使用的模板ID，可不填，将使用默认模板
     * @param templateData				发�?Feed时所使用的模板内容，不可超过500个字，如果超�?00个字，将被强行截�?
     * @return
     * @throws MiopException
     * @throws IOException
     */
	@SuppressWarnings("unchecked")
	public Object SendFeed( String templateId, String templateData )
    throws MiopException, IOException{

    	if( templateData == null || "".equals( templateData.trim() ) )
    		throw new MiopException( 100, "新鲜事内容不可为空" );
    	
    	if( templateId == null )
    		templateId = "";
    	
    	templateId = templateId.trim();
    	
    	templateData = templateData.trim();
    	if( templateData != null && templateData.length() > 500 )
    		templateData = templateData.substring( 0, 500 );
    	
    	return this.apiClient.callMethod( MiopMethod.PUBLISH_TEMPLATIZED_ACTION,  
    										new Pair("template_id", templateId ), 
    										new Pair( "template_data", templateData )
    	);
    }
    
	/**
	 * 发�?短信
	 * 
	 * @param content				要发送的短信内容，每条短信不可超�?0个字符，若超过，将被强行截断
	 * @param toUserId				要发送的用户ID，若不指�?null)，将默认为当前用�?
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object SendSms( String content, String toUserId )throws MiopException, IOException{
		if( content == null || "".equals( content.trim() ) )
    		throw new MiopException( 100, "短信内容不可为空" );
		
		content = content.trim();
		if( toUserId == null || "".equals( toUserId.trim() ) )
			toUserId = this.userName;
		
		return this.apiClient.callMethod( MiopMethod.SEND_SMS,  
				new Pair("content", content ), 
				new Pair( "to_id", toUserId )
		);
	}

	/**
	 * �?��内容是否有违禁词，提供给具备UGC功能的第三方应用，来保证用户�?��送的内容不包含违�?39�?��平台限制的内�?
	 * 
	 * @param content				要检查的内容
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object contentFilter( String content )throws MiopException, IOException{
		if( content == null || "".equals( content.trim() ) )
    		throw new MiopException( 100, "待验证内容不可为�空" );
		
		content = content.trim();
		
		return this.apiClient.callMethod( MiopMethod.CONTENT_FILTER,  
											new Pair( "content", content )
		);
	}
	
	/**
	 * 更新当前用户在139社区的经验积分（需要特殊授权）。 
	 * 
	 * @param rule_ids					积分规则标识，多个规则以逗号分开；例如圈子发帖为1001，圈子删贴为1011 
	 * @param uid						认证当前App的UserId,如果该参数没有指定,则默认是当前会话的用户 
	 * @param times						更新经验积分次数，默认1次 
	 * @param updateRank				积分更新是否关联等级，1：关联；0：不关联，默认关联。
	 * 
	 * @return
	 * @throws MiopException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Object updateUserPoint( String rule_ids, String uid, int times, String updateRank )throws MiopException, IOException{
		if( rule_ids == null )
			throw new MiopException( 100, "积分规则标识不可为�空" );
		
		if( times <= 0 )
			times = 1;
		
		return this.apiClient.callMethod( MiopMethod.UPDATE_USER_POINT, 
													new Pair( "rule_ids", rule_ids ),
													new Pair( "uid", uid ),
													new Pair( "times", String.valueOf( times ) ),
													new Pair( "updateRank", updateRank )
		);
	}
	
	/**
	 * 给指定手机发送激活139账号短信 
	 * 
	 * @param mobile					发送激活短信的手机号 
	 * @return
	 * 
	 * @throws MiopException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Object smsRegister( String mobile )throws MiopException, IOException{
		if( mobile == null || "".equals( mobile.trim() ) )
			throw new MiopException( 100, "手机号码不可为�空" );
		
		return this.apiClient.callMethod( MiopMethod.SMS_REGISTER, new Pair( "mobile", mobile ) );
	}
	
	/**
	 * 获取指定应用的圈子信息，如果不指定应用ID，默认取本应用的圈子信息
	 * 
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getAppGroup( int appId )throws MiopException, IOException{
		if( appId <= 0 )
			return this.apiClient.callMethod( MiopMethod.GROUPS_GET_APPGROUP );
		else
			return this.apiClient.callMethod( MiopMethod.GROUPS_GET, new Pair( "group_id", String.valueOf( appId ) ) );
	}
	
	
	/**
	 * 返回指定圈子的最新话题信息
	 * 
	 * @param group_id				指定圈子ID，如果不指定，则默认为应用对应的圈子
	 * @param count					获取的话题数量，最多获取20个话题信息；默认获取20个话题信息。
	 * @param offset				获取话题的开始记录数，从0开始 
	 * @return
	 * @throws MiopException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Object getGroupsTopics( int group_id, int count, int offset )throws MiopException, IOException{
		if( offset < 0 )
			offset = 0;
		if( count <= 0 )
			count = 20;
		
		if( group_id <= 0 )
			return this.apiClient.callMethod( MiopMethod.GROUPS_GET_TOPICS, new Pair( "offset", String.valueOf( offset ) ), new Pair( "count", String.valueOf( count ) ) );
		else
			return this.apiClient.callMethod( MiopMethod.GROUPS_GET_TOPICS, new Pair( "offset", String.valueOf( offset ) ), 
																			new Pair( "count", String.valueOf( count ) ),
																			new Pair( "group_id", String.valueOf( group_id ) ) 
			);
		
	}
	
	/**
	 * 返回指定圈子的最新相册图片信息
	 * 
	 * @param group_id					指定圈子ID
	 * @param count						获取的图片数量，最多获取20张；默认获取20张图片。 
	 * @param offset					获取话题的开始记录数，从0开始 
	 * @return
	 * @throws MiopException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Object getGroupPictures( int group_id, int count, int offset )throws MiopException, IOException{
		if( offset < 0 )
			offset = 0;
		if( count <= 0 )
			count = 20;
		
		if( group_id <= 0 )
			return this.apiClient.callMethod( MiopMethod.GROUPS_GET_PICTURES, new Pair( "offset", String.valueOf( offset ) ), new Pair( "count", String.valueOf( count ) ) );
		else
			return this.apiClient.callMethod( MiopMethod.GROUPS_GET_PICTURES, new Pair( "offset", String.valueOf( offset ) ), 
																			new Pair( "count", String.valueOf( count ) ),
																			new Pair( "group_id", String.valueOf( group_id ) ) 
			);
	}
	
	/**
	 * �?��内容是否有违禁词，提供给具备UGC功能的第三方应用，来保证用户�?��送的内容不包含违�?39�?��平台限制的内�?
	 * 
	 * @param content				要检查的内容
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPayOrder( String callUrl, String returnUrl, String appUrl, String extraInfo, List<PayOrderInfo> orders )throws MiopException, IOException{
		if( callUrl == null || "".equals( callUrl.trim() ) )
    		throw new MiopException( 100, "callUrl error :" + callUrl );
		if( returnUrl == null || "".equals( returnUrl.trim() ) )
    		throw new MiopException( 100, "returnUrl error :" + returnUrl );
		if( appUrl == null || "".equals( appUrl.trim() ) )
    		throw new MiopException( 100, "appUrl error :" + appUrl );
		if( extraInfo == null || "".equals( extraInfo.trim() ) )
    		throw new MiopException( 100, "extraInfo error :" + extraInfo );
		
		if( orders == null || orders.isEmpty() )
			throw new MiopException( 100, "orders error :" + orders );
		
		List<String> orderJsonStrList = new ArrayList<String>( orders.size() );
		for( PayOrderInfo o:orders )
			orderJsonStrList.add( o.toJsonString() );
		
		return this.apiClient.callMethod( MiopMethod.PAY_GET_ORDER,  
											new Pair( "CallUrl", callUrl ),
											new Pair( "ReturnUrl", returnUrl ),
											new Pair( "AppUrl", appUrl ),
											new Pair( "ExtraInfo", extraInfo ),
											new Pair( "Commodities", "[" + MiopUtils.delimit( orderJsonStrList ) + "]" )
		);
	}
	
    /**
     * 执行远程调用接口
     * 
     * @param method			被调用的api
     * @param params			调用参数
     * @return
     * @throws MiopException
     * @throws IOException
     */
    public Object callMethod(String method, Map<String,Object> params)
        throws MiopException, IOException {
        List<Pair<String,CharSequence>> pairList = new ArrayList<Pair<String,CharSequence>>();

        for (Map.Entry<String,Object> entry : params.entrySet()) {
            if ((entry.getKey() == null) || (entry.getValue() == null)) {
                continue;
            }

            if (entry.getValue() instanceof Collection) {
                pairList.add(new Pair<String,CharSequence>(entry.getKey(),
                        MiopUtils.delimit((Collection) entry.getValue())));
            } else {
                pairList.add(new Pair<String,CharSequence>(entry.getKey(),
                        entry.getValue().toString()));
            }
        }

        return this.apiClient.callMethod(method, pairList);
    }
    
    /**
     * 创建api调用请求
     * 
     * @param method	被调用的api
     * @param params	被调用api的参�?
     * @return
     */
    public String createPostString(String method,Map<String,Object> params) {
    	 List<Pair<String,CharSequence>> pairList = new ArrayList<Pair<String,CharSequence>>();

         for (Map.Entry<String,Object> entry : params.entrySet()) {
             if ((entry.getKey() == null) || (entry.getValue() == null)) {
                 continue;
             }

             if (entry.getValue() instanceof Collection) {
                 pairList.add(new Pair<String,CharSequence>(entry.getKey(),
                         MiopUtils.delimit((Collection) entry.getValue())));
             } else {
                 pairList.add(new Pair<String,CharSequence>(entry.getKey(),
                         entry.getValue().toString()));
             }
         }
         
         return this.apiClient.createPostString(method, pairList);
    }
    
    /**
     * 设置调试标记
     * 
     * @param isDebug
     */
    public void setDebug(boolean isDebug){
    	this.apiClient.DEBUG = isDebug;
    }
    
    /**
     * 设置编码格式，默认为UTF-8，建议第三方应用也采用UTF-8，特别是MIML的应�?
     * 
     * @param encoder			指定的编码格�?
     */
    public void setEncoder(String encoder){
    	this.apiClient.setFinalEncode(encoder);
    }
}
