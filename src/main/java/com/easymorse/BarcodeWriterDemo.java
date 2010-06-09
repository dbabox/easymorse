package com.easymorse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class BarcodeWriterDemo {

	public static void main(String[] args) throws FileNotFoundException {
		BarcodeWriterDemo demo = new BarcodeWriterDemo();
		demo.setWidth(128);
		demo.setHeight(128);
		demo.setContent("春夜楼台花宴开，楼台花宴开，金樽高举月影来，月影来，清辉透射古松林，透射古松林，此情此景今何在？今何在？");
		demo.generate(new FileOutputStream(new File("barcode.png")));
	}

	private int width;

	private int height;

	private String content;

	Hashtable<EncodeHintType, String> hints;

	{
		hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	}

	public void generate(OutputStream outputStream) {
		QRCodeWriter writer = new QRCodeWriter();

		try {
			ByteMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE,
					width, height, hints);
			byte[][] matrixByte = matrix.getArray();

			BufferedImage bimg = new BufferedImage(width, height,
					BufferedImage.TYPE_BYTE_GRAY);
			byte[] linearbuffer = new byte[width * height];

			for (int y = 0,i=0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					linearbuffer[i++] = matrixByte[y][x];
				}
			}
			bimg.getRaster().setDataElements(0, 0, width, height, linearbuffer);

			ImageIO.write(bimg, "png", outputStream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getContent() {
		return content;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
