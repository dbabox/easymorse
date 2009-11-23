package com.easymorse;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;

/**
 * 演示vfs的调用
 * 
 * @author marshal
 * 
 */
public class VfsDemo {
	public static void main(String[] args) throws Exception {
		FileSystemManager manager = VFS.getManager();
		FileObject fileObject = manager.resolveFile("zip:"
				+ new File("").getAbsolutePath() + File.separator
				+ "test_package.zip");
		printFilePath(fileObject);
	}

	/**
	 * 根据文件路径遍历（打印）文件对象。
	 * @param fileObject
	 * @throws Exception
	 */
	private static void printFilePath(FileObject fileObject) throws Exception {
		if (fileObject.getType().hasChildren()) {
			System.out.println("is directory>>" + fileObject);
			for (FileObject o : fileObject.getChildren()) {
				printFilePath(o);
			}
		} else {
			System.out.println("is file>>" + fileObject);
			if (fileObject.toString().endsWith(".xml")) {
				System.out.println("is a xml file.");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(fileObject.getContent()
								.getInputStream()));
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					System.out.println(s);
				}
			}
		}
	}
}
