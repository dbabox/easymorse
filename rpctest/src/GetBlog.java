import java.net.URL;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
/**
 * @author wangjun <a
 *         href="mailto:wangjun.0112@gmail.com">wangjun.0112@gmail.com</a>
 * 得到博客内容
 */
public class GetBlog {
	public static void post()  {
		try{
            String domain = "wangjun.easymorse.com";//你网站的域名
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("http://"+domain+"/xmlrpc.php"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Object[] params = new Object[] {"485", "username", "password",10};
			Object[] ob=(Object[])client.execute("metaWeblog.getRecentPosts", params);
		System.out.println("得到博客 " + ob[7]);
		}catch (Exception e) {
                       System.out.println(" UnCreated "+e.getMessage());
		}
	}
	public static void main(String[] args) {
		GetBlog.post();
	}
}
