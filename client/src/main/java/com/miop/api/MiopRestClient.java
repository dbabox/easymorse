package com.miop.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public abstract class MiopRestClient<T> {
    public static final String ERROR_TAG = "error_response";
    public static final String ERROR_CODE = "error_code";
    public static final String ERROR_MSG = "error_msg";
    public static final String RESULT = "result";
    public static final String RESULT_SUCCESS = "1";
    public static final String CURRENT_VERSION = "1.0";
    public static boolean DEBUG = false;
    protected String appKey;
    protected String appSecret;
    protected String sessionKey;
    protected String username;
    protected String time;
    protected Long lastCallId;
    protected String friendsList;
    protected String finalEncode;
    protected String requestFile;
    protected String serverAddr;
    protected Boolean _debug = null;
    protected int _timeout = 15*1000;
    protected int _readTimeout = 10*1000;
    protected String rawResponse;
    protected URL serverURL = null;

    public void setServerAddr( String url )throws Exception{
    	this.serverAddr = url + "/" + this.requestFile;
    	// System.err.println( "reset : " + this.serverAddr );
    	
    	this.serverURL = new URL(this.serverAddr);
    }
    
    public MiopRestClient(String appKey, String appScret, String sessionKey,
        String username, String time) {
        this.appKey = appKey;
        this.appSecret = appScret;
        this.sessionKey = sessionKey;
        this.requestFile = "restserver.php";
        this.finalEncode = "utf-8";
        this._timeout = -1;
        this._readTimeout = -1;
        this.serverAddr = Miop.getMiopUrl(MiopParam.SUBDOMAIN_API.getSignatureName()) +
            "/" + this.requestFile;
        
        try {
            this.serverURL = new URL(this.serverAddr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.time = time;
        this.lastCallId = 0L;
    }

    public MiopRestClient(String appKey, String appScret, String sessionKey,
        String username, String time, int connectionTimeout, int readTimeout) {
        this(appKey, appScret, sessionKey, username, time);
        this._timeout = connectionTimeout + readTimeout;
        this._readTimeout = readTimeout;
    }

    public MiopRestClient(String appKey, String appScret) {
        this(appKey, appScret, null, null, null);
    }

    protected T callMethod(MiopMethod method, Pair<String,CharSequence>...paramPairs)throws MiopException, IOException 
    {
        return callMethod(method.getMethodName(), Arrays.asList(paramPairs));
    }
    
    private Map createPostMap(String method,Collection<Pair<String,CharSequence>> paramPairs) {
    	String nameSpace = MiopParam.SIGNATURE.toString();

        TreeMap<String,CharSequence> params = new TreeMap<String,CharSequence>();
        long callId = System.currentTimeMillis();

        CharSequence oldVal;

        for (Pair<String,CharSequence> p : paramPairs) {
            oldVal = params.put(p.getFirst(), p.getSecond() );

            if (oldVal != null) {
                System.out.println("For parameter " + p.getFirst() +
                    ", overwrote old value " + oldVal + " with new value " +
                    p.getSecond() + ".");
            }
        }

        if (callId < this.lastCallId) {
            callId = this.lastCallId + 1;
        }

        this.lastCallId = callId;
        params.put(MiopParam.SDK_FROM.getSignatureName(), "java");
        params.put(MiopParam.USER.getSignatureName(), this.username);
        
        if( this.sessionKey != null )
        	params.put(MiopParam.SESSION_KEY.getSignatureName(), this.sessionKey);
        
        params.put(MiopParam.APP_KEY.getSignatureName(), this.appKey);
        params.put(MiopParam.TIME.getSignatureName(),this.time );
        params.put(MiopParam.API_VERSION.getSignatureName(), CURRENT_VERSION);
        params.put(MiopParam.METHOD.getSignatureName(), method);
        params.put(MiopParam.CALL_ID.getSignatureName(), Long.toString(callId));

        TreeMap<String,CharSequence> foParams = new TreeMap<String,CharSequence>();

        for (Entry<String,CharSequence> entry : params.entrySet()) {
            // foParams.put( nameSpace.concat("_").concat(entry.getKey()), entry.getValue() );
        	if( entry.getValue() == null )
        		continue;
        	
            foParams.put( entry.getKey(), entry.getValue() );
        }

        String signature = MiopUtils.generateSignature(MiopUtils.convert(
                    foParams.entrySet()), this.appSecret);
        foParams.put(MiopParam.SIGNATURE.toString(), signature);
        
        return foParams;
    }

    public String createPostString(String method,Collection<Pair<String,CharSequence>> paramPairs) {
    	Map<String,CharSequence> foParams = createPostMap(method,paramPairs);
    	CharSequence buffer = (null == foParams) ? ""
                : MiopUtils.delimit(foParams.entrySet(),"&", "=", true, this.finalEncode);
    	 if (isDebug()) {
    		 System.out.println("CREATE POST STRING::");
             System.out.println(buffer);
         }
    	return buffer.toString();
    }
    
    public T callMethod(String method, Collection<Pair<String,CharSequence>> paramPairs) throws MiopException, IOException {
        this.rawResponse = null;

        Map foParams = this.createPostMap(method, paramPairs);

        try {
            InputStream data = postRequest(method, foParams, /* no Encode, for php sig result */
                    false);

            BufferedReader in = new BufferedReader(new InputStreamReader(data,
                        "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            boolean insideTagBody = false;

            while ((line = in.readLine()) != null) {
                if (line.trim().startsWith("<") && line.contains(">")) {
                    insideTagBody = true;
                }

                if (line.trim().endsWith(">")) {
                    insideTagBody = false;
                }

                if (insideTagBody) {
                    line += ",";
                }

                buffer.append(line);
            }

            String respStr = new String(buffer);
            this.rawResponse = respStr;
            
            if(this.isDebug()){
            	System.out.println(rawResponse);
            }
            return parseResult(new ByteArrayInputStream(respStr.getBytes(
                        "UTF-8")), method);
        } catch (java.net.SocketException ex) {
            System.err.println(
                "Socket exception when calling Miop method: " +
                ex.getMessage());
        }

        return null;
    }

    /**
     * 解析xml或其他格�?
     * 如识别返回的代码是error_response.则抛出一个包含错误代码和错误信息的MiopException异常
     * @param data
     * @param method
     * @return xml格式返回dom4j的Document对象;
     * 
     * @throws MiopException
     * @throws IOException
     */
    protected abstract T parseResult(InputStream data, String method)
        throws MiopException, IOException;
    
    public abstract List getParsedList(Object object,Class clazz);

    public abstract boolean getExecuteResult(Object document);
    
    private InputStream postRequest(CharSequence method,
        Map<String,CharSequence> params, boolean doEncode)
        throws IOException {
        CharSequence buffer = (null == params) ? ""
                                               : MiopUtils.delimit(params.entrySet(),
                "&", "=", doEncode, this.finalEncode);
        URL serverUrl = this.serverURL;

        if (isDebug()) {
            System.out.println(method);
            System.out.println(" POST: ");
            System.out.println(serverUrl.toString());
            System.out.println("/");
            System.out.println(buffer);
        }

        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

        if (this._timeout != -1) {
            conn.setConnectTimeout(this._timeout);
        }

        if (this._readTimeout != -1) {
            conn.setReadTimeout(this._readTimeout);
        }

        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException ex) {
            System.err.println("MiopRestClient::postRequest(): ");
            ex.printStackTrace();
        }

        conn.setDoOutput(true);
        conn.connect();
        conn.getOutputStream().write(buffer.toString().getBytes( this.finalEncode ));

        return conn.getInputStream();
    }

    /**
     * Set debugging on for this instance only.
     *
     * @param isDebug
     *            true to enable debugging false to disable debugging
     */
    public void setDebug(boolean isDebug) {
        _debug = isDebug;
    }

    /**
     * Check to see if debug mode is enabled.
     *
     * @return true if debugging is enabled false otherwise
     */
    public boolean isDebug() {
        return (null == _debug) ? MiopRestClient.DEBUG : _debug.booleanValue();
    }

    /**
     * Prints out the DOM tree.
     *
     * @param n
     *            the parent node to start printing from
     * @param prefix
     *            string to append to output, should not be null
     */
    public void printDom(Node n, String prefix) {
        if (!isDebug()) {
            return;
        }

        String outString = prefix;

        if (n.getNodeType() == Node.TEXT_NODE) {
            outString += ("'" + n.getTextContent().trim() + "'");
        } else {
            outString += n.getNodeName();
        }

        System.out.println(outString);

        NodeList children = n.getChildNodes();
        int length = children.getLength();

        for (int i = 0; i < length; i++) {
            printDom(children.item(i), prefix + "  ");
        }
    }

    public String getTime() {
        return this.time;
    }

    public String getSessionKey() {
        return this.sessionKey;
    }

    public String getUser() {
        return this.username;
    }

    public void setSessionKey(String session_key) {
        this.sessionKey = session_key;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(String user) {
        this.username = user;
    }

	public void setFinalEncode(String finalEncode) {
		this.finalEncode = finalEncode;
	}
	
	public String getCurrentVersion(){
		return CURRENT_VERSION;
	}
}
