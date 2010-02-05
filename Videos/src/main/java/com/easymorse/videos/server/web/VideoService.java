package com.easymorse.videos.server.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.MessageFormat;

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

	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED)
	public void upload(VideoItem videoItem,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		this.videoItemDao.saveOrUpdate(videoItem);
		// response.setContentType("text/plain");
		if (file != null) {
			try {
				this.saveUploadFile(file, request, videoItem.getId());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		this.convertVideo(videoItem.getId(), request);
		try {
			response.getWriter().write(videoItem.getId());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void convertVideo(final String id, final HttpServletRequest request) {
		File videoSourceFile = getSourceFile(getDir(request, id));
		File videoDir = new File(request.getSession().getServletContext()
				.getRealPath("videos"), id);
		if (!videoDir.exists()) {
			videoDir.mkdirs();
		}
		File videoFile = new File(videoDir, "video.mp4");

		// Runtime
		// .getRuntime()
		// .exec(
		// MessageFormat
		// .format(
		// "ffmpeg -i {0} -an -pass 1 -threads 2 -vcodec libx264 -b 512k -flags +loop+mv4 -cmp 256 -partitions +parti4x4+parti8x8+partp4x4+partp8x8+partb8x8 -me_method hex -subq 7 -trellis 1 -refs 5 -bf 3 -flags2 +bpyramid+wpred+mixed_refs+dct8x8 -coder 1 -me_range 16 -g 250 -keyint_min 25 -sc_threshold 40 -i_qfactor 0.71 -qmin 10 -qmax 51 -qdiff 4 -y {1}",
		// videoSourceFile.getAbsolutePath(),
		// videoFile.getAbsolutePath()));
		try {
			final Process process = new ProcessBuilder(
					new File(request.getSession().getServletContext()
							.getRealPath("WEB-INF/shell/convertvideo.sh"))
							.getAbsolutePath(), videoSourceFile
							.getAbsolutePath(), videoFile.getAbsolutePath())
					.directory(
							new File(new File(request.getSession()
									.getServletContext().getRealPath(
											"videos/" + id)).getAbsolutePath()))
					.start();

			new Thread() {
				@Override
				public void run() {
					BufferedReader inputReader = new BufferedReader(
							new InputStreamReader(process.getErrorStream()));
					try {
						BufferedWriter writer = new BufferedWriter(
								new FileWriter(new File(request.getSession()
										.getServletContext().getRealPath(
												"videos/" + id),"convert.log")));

						for (String s = inputReader.readLine(); s != null; s = inputReader
								.readLine()) {
							writer.write(s);
						}
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}.start();

		} catch (Exception e) {
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
			request.getRequestDispatcher("/image.do")
					.forward(request, response);
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

	private static void printMessages(InputStream inputStream)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		for (String s = reader.readLine(); s != null; s = reader.readLine()) {
			System.out.println(">>>" + s);
		}
		reader.close();
	}

	private static void printProcessResults(Process process)
			throws InterruptedException, IOException {
		if (process.waitFor() == 0) {
			printMessages(process.getInputStream());
		} else {
			printMessages(process.getErrorStream());
		}
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

	@RequestMapping("/video.do")
	public void getVideo(String id, HttpServletRequest request,
			HttpServletResponse response) {
		if (id == null) {
//			try {
//				response.sendRedirect("default.mp4");
//				return;
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
			id="";
		}
		String message = getVideoPath(id, request);
		if (message == null) {
			message = "wait";
		}
		try {
			response.getWriter().write(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getVideoPath(String id, HttpServletRequest request) {
		File dir = new File(request.getSession().getServletContext()
				.getRealPath("videos"));

		if (dir.exists()) {
			File videoDir = new File(dir, id);
			if (videoDir.exists()) {
				File videoFile = new File(videoDir, "video.mp4");
				if (videoFile.exists()) {
					File completeFlagFile = new File(videoDir, "completeFlag");
					if (completeFlagFile.exists()) {
						return "../videos/" + id + "/video.mp4";
					} else {
						long size = videoFile.length();
						try {
							Thread.sleep(1000 * 2);
						} catch (InterruptedException e) {
						}
						if (videoFile.length() == size) {
							try {
								completeFlagFile.createNewFile();
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
							return "../videos/" + id + "/video.mp4";
						}
					}
				}
			}
		}

		return null;
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// Process process = new
		// ProcessBuilder("/home/marshal/workspace5/Videos/war/WEB-INF/shell/convertvideo.sh",
		// "/home/marshal/workspace5/Videos/war/WEB-INF/upload/ff808181269e275501269e2b31db0001/source.mp4",
		// "/home/marshal/桌面/mm/q7.mp4").directory(new
		// File("/home/marshal/桌面/mm")).start();
		// if (process.waitFor() == 0) {
		// printMessages(process.getInputStream());
		// } else {
		// printMessages(process.getErrorStream());
		// }
		final Process process = Runtime
				.getRuntime()
				.exec(
						new String[] {
								"/home/marshal/workspace5/Videos/war/WEB-INF/shell/convertvideo.sh",
								"/home/marshal/workspace5/Videos/war/WEB-INF/upload/ff808181269e275501269e2b31db0001/source.mp4",
								"/home/marshal/桌面/mm/q7.mp4" }, null,
						new File("/home/marshal/桌面/mm"));
		new Thread() {
			@Override
			public void run() {
				System.out.println("start...");
				BufferedReader inputReader = new BufferedReader(
						new InputStreamReader(process.getErrorStream()));
				try {
					for (String s = inputReader.readLine(); s != null; s = inputReader
							.readLine()) {
						System.out.println(s);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("end.");
			}
		}.start();

	}
}
