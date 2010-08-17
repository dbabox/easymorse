package com.easymorse.inputstream;

import java.io.FileOutputStream;
import java.io.InputStream;

public class InputStreamDemo {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		InputStream inputStream = InputStreamDemo.class
				.getResourceAsStream("p.png");// 模拟得到一个输入流
		int bufferSize = 1024;//缓存大小
		byte[] data = new byte[bufferSize];
		FileOutputStream outputStream = new FileOutputStream("cp.png");
		
		for (int i = inputStream.read(data); i > 0; i = inputStream.read(data)) {
			outputStream.write(data, 0, i);
		}
		
		outputStream.close();
		inputStream.close();
	}

}
