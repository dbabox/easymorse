package com.easymorse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class UnZipDemo {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		long time = System.currentTimeMillis();
		ZipFile zipFile = new ZipFile(new File("疾风_00.zip"), "GBK");
		Enumeration e = zipFile.getEntries();
		List<ZipArchiveEntry> entries = new ArrayList<ZipArchiveEntry>();

		while (e.hasMoreElements()) {
			ZipArchiveEntry entry = (ZipArchiveEntry) e.nextElement();
			if (entry.isDirectory()) {
				new File(entry.getName()).mkdirs();
			} else {
				entries.add(entry);// 将文件条目保留到列表中
			}
		}

		System.out.println(">>生成所有目录");

		for (ZipArchiveEntry entry : entries) {
			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(new File(entry.getName())));
			InputStream inputStream = zipFile.getInputStream(entry);
			byte[] data = new byte[1024 * 1024];
			inputStream.read(data);

			for (int i = inputStream.available(); i > 0; inputStream.read(data), i = inputStream
					.available()) {
				outputStream.write(data, 0, i);
			}

			outputStream.close();
		}

		System.out.println(">>解压缩所有文件条目");

		zipFile.close();

		System.out.println(">>耗时(ms)：" + (System.currentTimeMillis() - time));
	}
}
