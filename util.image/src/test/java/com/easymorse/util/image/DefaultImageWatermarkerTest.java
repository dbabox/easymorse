package com.easymorse.util.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefaultImageWatermarkerTest {
	private DefaultImageWatermarker imageWatermarker;

	private File file;

	@Before
	public void init() {
		this.imageWatermarker = new DefaultImageWatermarker();
		imageWatermarker.setFormatName("jpeg");
		imageWatermarker.setAlpha(0.3f);
		file = new File("target/result_watermarker.jpeg");
	}

	@After
	public void close() {
		this.file.delete();
	}

	@Test
	public void test() throws Exception {
		for (int i = 0; i < 3; i++) {
			long time = System.currentTimeMillis();
			generate();
			System.out.println("生成水印耗时>>>" + (System.currentTimeMillis() - time));
		}
	}

	private void generate() throws FileNotFoundException {
		FileOutputStream outputStream = new FileOutputStream(this.file);
		this.imageWatermarker.generateOutputStream(this.getClass()
				.getResourceAsStream("lion.png"), this.getClass()
				.getResourceAsStream("logo.png"), 0, 0, outputStream);
		try {
			outputStream.close();
		} catch (IOException e) {
			throw new ImageRuntimeException(e);
		}
	}
}
