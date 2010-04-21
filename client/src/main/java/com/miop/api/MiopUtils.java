package com.miop.api;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Field;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;



public class MiopUtils {
    public static final String PREF = "--";
    public static final Character UNDERLINE = 95;
    public static final String CRLF = "\r\n";
    public static final String ENC_BASE64 = "base64";
    public static final String PACKAGE_NAMESPACE = "com.miop.api.bean";

    public static CharSequence delimit(Collection<? > iterable) {
        // could add a thread-safe version that uses StringBuffer as well
        if ((iterable == null) || iterable.isEmpty()) {
            return null;
        }

        StringBuilder buffer = new StringBuilder();
        boolean notFirst = false;

        for (Object item : iterable) {
            if (notFirst) {
                buffer.append(",");
            } else {
                notFirst = true;
            }

            buffer.append(item.toString());
        }

        return buffer;
    }

    public static CharSequence delimit(
        Collection<Map.Entry<String,CharSequence>> entries,
        CharSequence delimiter, CharSequence equals, boolean doEncode, String finalEncoder) {
        if ((entries == null) || entries.isEmpty()) {
            return null;
        }

        StringBuilder buffer = new StringBuilder();
        boolean notFirst = false;

        for (Map.Entry<String,CharSequence> entry : entries) {
            if (notFirst) {
                buffer.append(delimiter);
            } else {
                notFirst = true;
            }

            CharSequence value = entry.getValue();
            buffer.append(entry.getKey()).append(equals)
                  .append(doEncode ? encode(value,finalEncoder) : value);
        }
//        buffer.append("&");
        return buffer;
    }

    public static String encode(CharSequence target,String finalEncoder) {
        String result = (target != null) ? target.toString() : "";
        try {
	        
            result = URLEncoder.encode(result, "UTF8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsuccessful attempt to encode '" + result +
                "' into UTF8");
        } 

        return result;
    }

    /**
       * Converts a Map of key-value pairs into the form expected by generateSignature
       * @param entries a collection of Map.Entry's, such as can be obtained using
       *   myMap.entrySet()
       * @return a List suitable for being passed to generateSignature
       */
    public static List<String> convert(
        Collection<Map.Entry<String,CharSequence>> entries) {
        List<String> result = new ArrayList<String>(entries.size());

        for (Map.Entry<String,CharSequence> entry : entries)
            result.add(MiopParam.stripSignaturePrefix(entry.getKey()) + "=" +
                entry.getValue());

        return result;
    }

    public static String getBASE64DecoderValue(String value) {
        String newValue = "";

        try {
            newValue = new String(new BASE64Decoder().decodeBuffer(value),
                    "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newValue;
    }

    /**
     *
     * 创建指定类名与strName匹配的类对象
     * 若有新类�?��添加，用户只�?��本地中com.Miop.api.bean下创建即�?
     * @param elementName
     * @return 在com.Miop.api.bean下与elementName名字对应类的实例
     */
    public static Object createObject(String strName) {
        try {
            return Class.forName(MiopUtils.PACKAGE_NAMESPACE + "." +
                elementName2ClassName(strName)).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            return null;
        }

        return null;
    }

    public static String elementName2ClassName(String elementName) {
        String newClassName = upperFirstChar(elementName);

        return removeUnderLine(newClassName);
    }
    
    /**
     * 类名转换为含有下划线的小写字符串(除首字母外，有大写的地方增加下划线，并将大写字母转换成小�?
     * @param clazz
     * @return
     */
    public static String className2ElementName(Class clazz) {
        String className = clazz.getSimpleName();
        String elementName = className.substring(0, 1).toLowerCase();

        if (className.length() > 1) {
            elementName += className.substring(1, className.length());
        }

        return addUnderLine(elementName);
    }

    /**
     * 首字母大�?
     * @param elementName
     * @return
     */
    public static String upperFirstChar(String elementName) {
        String newClassName = elementName.substring(0, 1).toUpperCase();

        if (elementName.length() > 1) {
            newClassName += elementName.substring(1, elementName.length());
        }

        return newClassName;
    }

    /**
     * 去掉下划线，并将下划线后的第�?��字母变成大写
     * @param className
     * @return
     */
    public static String removeUnderLine(String className) {
        int index = className.indexOf(MiopUtils.UNDERLINE);

        if (index == -1) {
            return className;
        }

        StringBuffer sb = new StringBuffer();
        String noContainStr = className.substring(0, index);
        String containStr = className.substring(++index, className.length());

        if ((containStr == null) || (containStr.length() == 0)) {
            return noContainStr;
        } else {
            sb.append(noContainStr)
              .append(removeUnderLine(upperFirstChar(containStr)));
        }

        return sb.toString();
    }



    /**
     * 增加下划�?
     * @param elementName
     * @return
     */
    public static String addUnderLine(String elementName) {
        int length = elementName.length();
        char[] charAry = elementName.toCharArray();
        StringBuffer newElementSB = new StringBuffer();

        for (int index = 0; index < length; index++) {
            char ch = charAry[index];

            if ((ch >= 65) && (ch <= 90)) {
                newElementSB.append((char) MiopUtils.UNDERLINE)
                            .append(Character.toLowerCase(ch));
            } else {
                newElementSB.append(ch);
            }
        }

        return newElementSB.toString();
    }

    /**
     * Calculates the signature for the given set of params using the supplied secret
     * @param params Strings of the form "key=value"
     * @param secret
     * @return the signature
     */
    public static String generateSignature(List<String> params, String secret) {
        StringBuffer buffer = new StringBuffer();
        Collections.sort(params);

        for (String param : params) {
            buffer.append(param);
        }

        buffer.append(secret);
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(
                    "MD5");
            StringBuffer result = new StringBuffer();

            for (byte b : md.digest(buffer.toString().getBytes( "UTF-8" ))) {
                result.append(Integer.toHexString((b & 0xf0) >>> 4));
                result.append(Integer.toHexString(b & 0x0f));
            }
            

            return result.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            System.err.println("MD5 does not appear to be supported" + ex);

            return "";
        } catch (UnsupportedEncodingException e) {
        	System.err.println("MD5 does not appear to be supported" + e);
			return "";
		}
    }
    
    public static Field getField(Object obj, String fieldName) {
        try {
            return obj.getClass().getDeclaredField(fieldName);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            return null;
        }

        return null;
    }

    public static boolean isFieldList(Object obj, String fieldName) {
        Field field = getField(obj, fieldName);

        return List.class.isAssignableFrom(field.getType());
    }

}
