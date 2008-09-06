package com.easymorse.util.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageJThumbnailMakerTest {

	private ImageJThumbnailMaker thumbnailMaker;

	private File file;

	@Before
	public void init() {
		thumbnailMaker = new ImageJThumbnailMaker();
		file = new File("target/result_thumbnail.jpeg");
	}

	@After
	public void close() {
		this.file.delete();
	}

	@Test
	public void testGenerateWithWidth() throws Exception {

		for (int i = 0; i < 3; i++) {
			thumbnailMaker.setImageType("jpeg");
			String errorMessage = null;
			FileOutputStream outputStream = new FileOutputStream(this.file);
			long time = System.currentTimeMillis();

			try {
				InputStream inputStream = ImageJThumbnailMakerTest.class
						.getResourceAsStream("lion.png");
				this.thumbnailMaker.generateWithWidth(inputStream,
						outputStream, 100);
				outputStream.close();
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}

			assert errorMessage == null;

			System.out.println("根据宽度生成缩略图耗时>>>"
					+ (System.currentTimeMillis() - time));
		}
	}

	@Test
	public void testGenerateWithHeight() throws Exception {
		for (int i = 0; i < 3; i++) {
			thumbnailMaker.setImageType("jpeg");
			String errorMessage = null;
			FileOutputStream outputStream = new FileOutputStream(this.file);
			long time = System.currentTimeMillis();

			try {
				InputStream inputStream = ImageJThumbnailMakerTest.class
						.getResourceAsStream("lion.png");

				this.thumbnailMaker.generateWithHeight(inputStream,
						outputStream, 100);
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}

			assert errorMessage == null;
			System.out.println("根据高度生成缩略图耗时>>>"
					+ (System.currentTimeMillis() - time));
		}
	}
}
