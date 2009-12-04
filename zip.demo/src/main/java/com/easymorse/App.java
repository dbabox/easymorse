package com.easymorse;

import java.io.File;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipFile;

/**
 * Hello world!
 * 
 */
public class App {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		ZipFile file = new ZipFile(new File("疾风_00.zip"), "GBK");
		Enumeration e = file.getEntries();
		for (; e.hasMoreElements();) {
			System.out.println(e.nextElement());
		}
	}
}
