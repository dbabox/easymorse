package com.easymorse.videos.server.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import util.dao.Pagination;

import com.easymorse.videos.server.dao.VideoItemDao;
import com.easymorse.videos.server.model.VideoItem;

@Controller
public class VideoService {

	private String uploadPath = "WEB-INF" + File.separator + "upload";

	private int uploadBuffer = 1024 * 1024;

	private int pageSize = 1;

	@Autowired
	private VideoItemDao videoItemDao;

	@RequestMapping("/isLogined.json")
	public void isLogined() {
	}

	@RequestMapping("/browse.json")
	public void browse(Pagination<VideoItem> pagination, ModelMap modelMap) {
		pagination.setSize(this.pageSize);
		pagination.setOrderFieldName("updateTime");
		pagination.setDesc(true);
		this.videoItemDao.browse(pagination);
		modelMap.put("pagination", pagination);
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
				this.saveUploadFile(file, request, videoItem.getId());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		try {
			response.getWriter().write(videoItem.getId());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping("/extract.json")
	public void extractImage(String id, Long second,
			HttpServletRequest request, HttpServletResponse response) {
		File dir = getDir(request, id);
		if (dir.exists()) {
			File file = getSourceFile(dir);

			if (file.exists()) {
				if (second == null) {
					second = 0L;
				}
				extract(getSourceFile(dir), getImageFile(dir), second);
			}
		}

		try {
			request.getRequestDispatcher("/image.do").forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping("/image.do")
	public void getImage(String id, HttpServletRequest request,
			HttpServletResponse response) {
		if (id == null) {
			id = "";
		}

		File imageFile = getImageFile(getDir(request, id));

		if (!imageFile.exists()) {
			imageFile = new File(request.getSession().getServletContext()
					.getRealPath(""), "1.jpg");
		}

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			OutputStream outputStream = response.getOutputStream();

			BufferedInputStream inputStream = new BufferedInputStream(
					new FileInputStream(imageFile));
			byte[] data = new byte[1024];
			for (int i = inputStream.read(data); i > 0; i = inputStream
					.read(data)) {
				outputStream.write(data, 0, i);
			}
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void extract(File sourceFile, File imageFile, Long second) {
		long time = System.currentTimeMillis();
		try {
			Process process = Runtime.getRuntime().exec(
					MessageFormat.format(
							"ffmpeg -i {0} -ss {2}  -t 0.001 -s 200x150 {1}",
							sourceFile.getAbsolutePath(), imageFile
									.getAbsolutePath(), second));

			process.waitFor();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("time: " + (System.currentTimeMillis() - time));
	}

	private void printFfmpegFormats() {
		try {
			Process process = new ProcessBuilder("ffmpeg", "-formats").start();
			process.waitFor();
			printMessages(process.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printMessages(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		for (String s = reader.readLine(); s != null; s = reader.readLine()) {
			System.out.println(">>>" + s);
		}
		reader.close();
	}

	private File getImageFile(File dir) {
		return new File(dir, "image.jpg");
	}

	private File getSourceFile(File dir) {
		for (File file : dir.listFiles()) {
			if (file.getName().startsWith("source.")) {
				return file;
			}
		}
		return null;
	}

	private File getDir(HttpServletRequest request, String id) {
		return new File(request.getSession().getServletContext()
				.getRealPath("")
				+ File.separator + this.uploadPath, id);
	}

	private void saveUploadFile(MultipartFile file, HttpServletRequest request,
			String id) throws IOException {
		File dir = getDir(request, id);

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
