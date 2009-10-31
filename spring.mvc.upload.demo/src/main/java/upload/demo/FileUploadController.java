package upload.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * '
 * 
 * @author 邓彦辉 <a href="mailto:jiessiedyh@gmail.com">jiessiedyh@gmail.com</a>
 * 
 */
@Controller
public class FileUploadController {
	private static final Logger logger = Logger
			.getLogger(FileUploadController.class);

	//文件的保存目录
	private String theFilePath = null;
	@RequestMapping("/demo/upload.do")
	public String showpage(ModelMap modelMap, HttpServletRequest request) {

		return "/demo/uploadpage";
	}

	@RequestMapping("/demo/submit.do")
	public String dealTheFile(@RequestParam("files") MultipartFile file,
			ModelMap modelMap, HttpServletRequest request) {

		if (this.saveTheFile(file, request)) {
			return "redirect:/demo/showall.do";

		} else {
			return "/demo/uploadpage";
		}

	}

	/**
	 * 保存文件到本地的文件夹imagefactory下
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	public boolean saveTheFile(MultipartFile file, HttpServletRequest request) {
		InputStream inputStream;
		try {
			inputStream = file.getInputStream();
			int total = inputStream.available();
			byte[] bs = new byte[total];
			inputStream.read(bs);
			String path = request.getRealPath("/");
			// 文件的路径问题，这样可以跨OS运行，没问题
			char xg = java.io.File.separatorChar;
			String filePath = null;
			theFilePath = path + "imagefactory";
			filePath = path + "imagefactory" + xg + file.getOriginalFilename();

			File files = new File(filePath);
			OutputStream outputStream = new FileOutputStream(files);
			outputStream.write(bs);
			outputStream.flush();
			outputStream.close();
			logger.debug("文件的保存路径:" + filePath);
			return true;

		} catch (Exception e) {
			// 将异常打印到日志
			logger.error(e);
			return false;

		}

	}

	/**
	 * 
	 * @param filepath
	 */
	@RequestMapping("/demo/showall.do")
	public String readTheFolder(String theFilePaths, ModelMap modelMap,
			HttpServletRequest request) {
		char xg = java.io.File.separatorChar;
		List lists = new ArrayList();
		File file = new File(theFilePath);
		if (!file.isDirectory()) {
			System.out.println("文件");
			System.out.println("path=" + file.getPath());
			System.out.println("absolutepath=" + file.getAbsolutePath());
			System.out.println("name=" + file.getName());

		} else if (file.isDirectory()) {
			System.out.println("文件夹");
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(theFilePath + xg + filelist[i]);
				if (!readfile.isDirectory()) {
					System.out.println("path=" + readfile.getPath());
					System.out.println("absolutepath="
							+ readfile.getAbsolutePath());
					System.out.println("name=" + readfile.getName());
					lists.add(readfile.getName());

				} else if (readfile.isDirectory()) {
					
				}
			}

		}
		modelMap.put("filelist",lists );
		return "/demo/show";
	}
}
