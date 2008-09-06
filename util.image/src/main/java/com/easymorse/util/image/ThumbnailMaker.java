package com.easymorse.util.image;

import java.awt.Image;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 缩略图生成器
 * 
 * @author Marshal Wu
 * 
 */
public interface ThumbnailMaker {
	/**
	 * 将指定的Image对象生成新的缩略图Image对象
	 * 
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public Image generate(Image image, int width, int height);

	/**
	 * 根据宽度生成新的图片输出流
	 * 
	 * @param inputStream
	 * @param width
	 * @return
	 */
	public void generateWithWidth(InputStream inputStream,OutputStream outputStream, int width);

	/**
	 * 根据高度生成新的图片输出流
	 * 
	 * @param inputStream
	 * @param height
	 * @return
	 */
	public void generateWithHeight(InputStream inputStream,OutputStream outputStream, int height);

	/**
	 * 根据指定长度生成图片的输出流
	 * 
	 * @param inputStream
	 * @param width
	 * @param height
	 * @return
	 */
	public void generateWithLength(InputStream inputStream,OutputStream outputStream, int width,
			int height);

}
