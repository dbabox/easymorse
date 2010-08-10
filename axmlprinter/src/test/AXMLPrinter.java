/*
 * Copyright 2008 Android4ME
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.AXmlResourceParser;
import android.util.TypedValue;

/**
 * @author Dmitry Skiba
 * 
 *         This is example usage of AXMLParser class.
 * 
 *         Prints xml document from Android's binary xml file.
 */
public class AXMLPrinter {

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			log("Usage: AXMLPrinter <binary res directory> <target directory>");
			return;
		}

		File sourceDir = new File(args[0]);
		File targetDir = new File(args[1]);

		if (!sourceDir.exists()) {
			log("Error: res directory is not exist!");
			return;
		}

		if (targetDir.exists() && !targetDir.isDirectory()) {
			log("Error: res directory must be a directory!");
		}

		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}

		generateXmlFiles(sourceDir, targetDir);
	}

	private static void generateXmlFiles(File sourceDir, File targetDir)
			throws IOException {
		File[] files = sourceDir.listFiles();
		targetDir.mkdirs();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				generateXmlFiles(file, new File(targetDir, file.getName()));
			} else {
				if (file.getName().endsWith(".xml")) {
					generateXml(targetDir, file);
				} else {
					copy(file, new File(targetDir, file.getName()));
				}
			}
		}
	}

	private static void copy(File oldfile, File newFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
	}

	private static void generateXml(File targetDir, File axml)
			throws IOException {
		PrintStream printStream = new PrintStream(new File(targetDir, axml
				.getName()));
		System.setOut(printStream);
		generateText(axml);
		System.setOut(out);
	}

	private static PrintStream out = System.out;

	public static void generateText(File axml) {

		try {
			StringBuilder indent = new StringBuilder(10);
			AXmlResourceParser parser = new AXmlResourceParser();
			parser.open(new FileInputStream(axml));

			final String indentStep = "	";
			while (true) {
				int type = parser.next();
				if (type == XmlPullParser.END_DOCUMENT) {
					break;
				}
				switch (type) {
				case XmlPullParser.START_DOCUMENT: {
					log("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
					break;
				}
				case XmlPullParser.START_TAG: {
					log("%s<%s%s", indent, getNamespacePrefix(parser
							.getPrefix()), parser.getName());
					indent.append(indentStep);

					int namespaceCountBefore = parser.getNamespaceCount(parser
							.getDepth() - 1);
					int namespaceCount = parser.getNamespaceCount(parser
							.getDepth());
					for (int i = namespaceCountBefore; i != namespaceCount; ++i) {
						log("%sxmlns:%s=\"%s\"", indent, parser
								.getNamespacePrefix(i), parser
								.getNamespaceUri(i));
					}

					for (int i = 0; i != parser.getAttributeCount(); ++i) {
						log("%s%s%s=\"%s\"", indent, getNamespacePrefix(parser
								.getAttributePrefix(i)), parser
								.getAttributeName(i), getAttributeValue(parser,
								i));
					}
					log("%s>", indent);
					break;
				}
				case XmlPullParser.END_TAG: {
					indent.setLength(indent.length() - indentStep.length());
					log("%s</%s%s>", indent, getNamespacePrefix(parser
							.getPrefix()), parser.getName());
					break;
				}
				case XmlPullParser.TEXT: {
					log("%s%s", indent, parser.getText());
					break;
				}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private static String getNamespacePrefix(String prefix) {
		if (prefix == null || prefix.length() == 0) {
			return "";
		}
		return prefix + ":";
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == TypedValue.TYPE_STRING) {
			return parser.getAttributeValue(index);
		}
		if (type == TypedValue.TYPE_ATTRIBUTE) {
			return String.format("?%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_REFERENCE) {
			return String.format("@%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_FLOAT) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == TypedValue.TYPE_INT_HEX) {
			return String.format("0x%08X", data);
		}
		if (type == TypedValue.TYPE_INT_BOOLEAN) {
			return data != 0 ? "true" : "false";
		}
		if (type == TypedValue.TYPE_DIMENSION) {
			return Float.toString(complexToFloat(data))
					+ DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type == TypedValue.TYPE_FRACTION) {
			return Float.toString(complexToFloat(data))
					+ FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type >= TypedValue.TYPE_FIRST_COLOR_INT
				&& type <= TypedValue.TYPE_LAST_COLOR_INT) {
			return String.format("#%08X", data);
		}
		if (type >= TypedValue.TYPE_FIRST_INT
				&& type <= TypedValue.TYPE_LAST_INT) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0x%02X>", data, type);
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		return "";
	}

	private static void log(String format, Object... arguments) {
		System.out.printf(format, arguments);
		System.out.println();
	}

	// ///////////////////////////////// ILLEGAL STUFF, DONT LOOK :)

	public static float complexToFloat(int complex) {
		return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
	}

	private static final float RADIX_MULTS[] = { 0.00390625F, 3.051758E-005F,
			1.192093E-007F, 4.656613E-010F };
	private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt",
			"in", "mm", "", "" };
	private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "",
			"", "" };
}