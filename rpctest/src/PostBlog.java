import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * @author wangjun <a
 *         href="mailto:wangjun.0112@gmail.com">wangjun.0112@gmail.com</a> 发布博客
 */
public class PostBlog {
	public static void post(String title, String content) {
		try {
			// Set up XML-RPC connection to server
			String domain = "wangjun.easymorse.com";// 你网站的域名
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("http://" + domain + "/xmlrpc.php"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Map post = new HashMap();
			post.put("title", title);// 标题
			post.put("mt_keywords", "java");// 标签
			Object[] categories = new Object[] { "计算机" };// 分类
			post.put("categories", categories);
			post.put("description", content);// 内容
			Object[] params = new Object[] { "1", "username", "password",post,true }; // 1代表正式发布，0代表草稿
			String ob = (String) client.execute("metaWeblog.newPost", params);
			System.out.println("Created with blogid " + ob);
		} catch (Exception e) {
			System.out.println(" UnCreated " + e.getMessage());
		}
	}
	public static void main(String[] args) {
		PostBlog.post("发布测试博客","测试成功");
	}
}
