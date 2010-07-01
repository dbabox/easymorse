import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * @author wangjun <a
 *         href="mailto:wangjun.0112@gmail.com">wangjun.0112@gmail.com</a>
 * 得到博客标签
 */
public class GetBlogCategories {
	public static void getBlogCategories()  {
		try{
            String domain = "wangjun.easymorse.com";//你网站的域名
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("http://"+domain+"/xmlrpc.php"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Object[] params = new Object[] {"技术分享", "username", "password" };
			// 得到分类列表
			Object[] ob=(Object[])client.execute("metaWeblog.getCategories", params);
			System.out.println("分类列表："+ob.length+","+ob[1]);
		}catch (Exception e) {
                       System.out.println(" UnCreated "+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		GetBlogCategories.getBlogCategories();
	}
}
