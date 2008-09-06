package com.easymorse.util.image;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * 使用ImageJ的缩略图生成器实现
 * 
 * @author Marshal Wu
 * 
 */
public class ImageJThumbnailMaker implements ThumbnailMaker {

	private String imageType;

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	@Override
	public void generateWithHeight(InputStream inputStream,
			OutputStream outputStream, int height) {
		generateWithLength(inputStream, outputStream, -1, height);
	}

	@Override
	public void generateWithLength(InputStream inputStream,
			OutputStream outputStream, int width, int height) {
		Image originalImage = createImageFromInputStream(inputStream);
		Image newImage = generate(originalImage, width, height);

		try {
			ImageIO.write((RenderedImage) newImage, this.imageType,
					outputStream);
		} catch (IOException e) {
			throw new ImageRuntimeException(e);
		}

	}

	private Image createImageFromInputStream(InputStream inputStream) {
		try {
			return ImageIO.read(inputStream);
		} catch (IOException e) {
			throw new ImageRuntimeException(e);
		}
	}

	@Override
	public void generateWithWidth(InputStream inputStream,
			OutputStream outputStream, int width) {
		generateWithLength(inputStream, outputStream, width, -1);
	}

	@Override
	public Image generate(Image image, int width, int height) {
		assert width > 0 || height > 0;

		ImagePlus imagePlus1 = new ImagePlus("original", image);

		int originalWidth = image.getWidth(null);
		int originalHeight = image.getHeight(null);

		if (width < 0) {
			width = Math.round(originalWidth * (height*1.0f / originalHeight));
		}

		if (height < 0) {
			height =  Math.round(originalHeight * (width*1.0f / originalWidth));
		}

		ImageProcessor imageProcessor = imagePlus1.getProcessor();
		imageProcessor.convertToRGB().smooth();
		imageProcessor.setInterpolate(true);
		imageProcessor = imageProcessor.resize(width, height);

		ImagePlus imagePlus2 = new ImagePlus("thumbnail", imageProcessor);

		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.createGraphics();
		graphics.drawImage(imagePlus2.getImage().getScaledInstance(width,
				height, Image.SCALE_SMOOTH), 0, 0, null);
		graphics.dispose();

		return bufferedImage;
	}

}
