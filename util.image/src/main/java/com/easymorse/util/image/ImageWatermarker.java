package com.easymorse.util.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片水印生成器
 * 
 * @author Marshal Wu
 * 
 */
public interface ImageWatermarker {
	/**
	 * 水印生成到(x,y)坐标
	 * 
	 * @param originalImage
	 * @param watermarker
	 * @param x
	 * @param y
	 * @return
	 */
	public Image generate(BufferedImage originalImage, BufferedImage watermarker, int x, int y);

	/**
	 * 水印生成到(x,y)坐标，并输出流
	 * 
	 * @param originalImage
	 * @param watermarker
	 * @param x
	 * @param y
	 * @param outputStream
	 */
	public void generateOutputStream(InputStream originalImage,
			InputStream watermarker, int x, int y, OutputStream outputStream);
}
