package com.easymorse.util.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * 默认水印生成器实现
 * 
 * @author Marshal Wu
 * 
 */
public class DefaultImageWatermarker implements ImageWatermarker {

	private String formatName;// 生成的文件格式

	private float alpha = 1;// 有关透明度的alpha值

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	@Override
	public BufferedImage generate(BufferedImage originalImage,
			BufferedImage watermarker, int x, int y) {
		BufferedImage image = new BufferedImage(originalImage.getWidth(),
				originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = image.createGraphics();

		graphics2D.drawImage(originalImage, 0, 0, null);
		graphics2D.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_ATOP, alpha));
		graphics2D.drawImage(watermarker, x, y, null);
		graphics2D.dispose();
		
		return image;
	}

	@Override
	public void generateOutputStream(InputStream originalImage,
			InputStream watermarker, int x, int y, OutputStream outputStream) {
		try {
			BufferedImage originalBufferedImage = ImageIO.read(originalImage);
			BufferedImage watermarkerBufferedImage = ImageIO.read(watermarker);
			ImageIO.write(this.generate(originalBufferedImage,
					watermarkerBufferedImage, x, y), formatName, outputStream);
		} catch (IOException e) {
			throw new ImageRuntimeException(e);
		}

	}
}
