package com.easymorse.videos.server.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		try {
			final Process process = new ProcessBuilder("bash",
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
												"videos/" + id), "convert.log")));

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
		// if (process.waitFor() == 0) {
		// printMessages(process.getInputStream());
		// } else {
		printMessages(process.getErrorStream());
		// }
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
			// try {
			// response.sendRedirect("default.mp4");
			// return;
			// } catch (IOException e) {
			// throw new RuntimeException(e);
			// }
			id = "";
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
					String videoSizeInfo = getVideoSizeInfo(videoDir);
					if (completeFlagFile.exists()) {
						return "http://" + request.getServerName() + ":"
								+ request.getServerPort()
								+ request.getContextPath() + "/videos/" + id
								+ "/video.mp4?width=" + videoSizeInfo;
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
							return "http://" + request.getServerName() + ":"
									+ request.getServerPort()
									+ request.getContextPath() + "/videos/"
									+ id + "/video.mp4?width=" + videoSizeInfo;
						}
					}
				}
			}
		}

		return null;
	}

	private String getVideoSizeInfo(File videoDir) {
		File convertLog = new File(videoDir, "convert.log");
		String info = "";

		if (convertLog.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						convertLog));
				Pattern pattern = Pattern.compile("\\d{2,4}x\\d{2,4}");
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					Matcher matcher = pattern.matcher(s);
					if (matcher.find()) {
						info = s.substring(matcher.start(), matcher.end());
					}
				}
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return info;
	}

	// public static void main(String[] args) throws IOException,
	// InterruptedException {
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
	// final Process process = Runtime
	// .getRuntime()
	// .exec(
	// new String[] {
	// "/home/marshal/workspace5/Videos/war/WEB-INF/shell/convertvideo.sh",
	// "/home/marshal/workspace5/Videos/war/WEB-INF/upload/ff808181269e275501269e2b31db0001/source.mp4",
	// "/home/marshal/桌面/mm/q7.mp4" }, null,
	// new File("/home/marshal/桌面/mm"));
	// new Thread() {
	// @Override
	// public void run() {
	// System.out.println("start...");
	// BufferedReader inputReader = new BufferedReader(
	// new InputStreamReader(process.getErrorStream()));
	// try {
	// for (String s = inputReader.readLine(); s != null; s = inputReader
	// .readLine()) {
	// System.out.println(s);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// System.out.println("end.");
	// }
	// }.start();
	// }

	public static void main(String[] args) throws Exception {
		List<String> commands = new ArrayList<String>();
		commands.add("bash");
		commands.add("/home/marshal/桌面/convertvideo.sh");
		// commands.add("ffmpeg -formats");
		// Runtime.getRuntime().exec("ffmpeg -version");
		// printProcessResults(Runtime.getRuntime().exec("ffmpeg -version"));
		printProcessResults(new ProcessBuilder(commands).start());
	}
}
