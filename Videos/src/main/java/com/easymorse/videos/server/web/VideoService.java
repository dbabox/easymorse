package com.easymorse.videos.server.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.easymorse.videos.server.dao.VideoItemDao;
import com.easymorse.videos.server.model.VideoItem;

@Controller
public class VideoService {

	private String uploadPath = "WEB-INF" + File.separator + "upload";

	private int uploadBuffer = 1024 * 1024;

	@Autowired
	private VideoItemDao videoItemDao;

	@RequestMapping("/isLogined.json")
	public void isLogined() {
	}

	@RequestMapping("/browse.json")
	public void browse(ModelMap modelMap) {
		modelMap.put("result", "browse");
	}

	@RequestMapping(value = "/upload.json", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED)
	public void upload(VideoItem videoItem,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		this.videoItemDao.saveOrUpdate(videoItem);
		response.setContentType("text/plain");
		if (file != null) {
			try {
				this
						.saveUploadFile(file, request.getSession()
								.getServletContext().getRealPath(""), videoItem
								.getId());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void saveUploadFile(MultipartFile file, String relationPath,
			String id) throws IOException {
		File dir = new File(relationPath + File.separator + this.uploadPath, id);

		if (dir.exists()) {
			dir.delete();
		}

		dir.mkdirs();

		byte[] data = new byte[this.uploadBuffer];
		BufferedOutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(new File(dir, "source."
						+ file.getOriginalFilename()
								.substring(
										file.getOriginalFilename().lastIndexOf(
												".") + 1))));
		InputStream inputStream = file.getInputStream();

		for (int i = inputStream.read(data); i > 0; i = inputStream.read(data)) {
			outputStream.write(data, 0, i);
		}

		outputStream.close();
	}
}
