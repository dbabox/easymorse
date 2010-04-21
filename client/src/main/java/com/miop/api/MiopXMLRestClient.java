package com.miop.api;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



public class MiopXMLRestClient extends MiopRestClient {
	

	public MiopXMLRestClient(String appKey, String appScret) {
        super(appKey, appScret);
    }

    public MiopXMLRestClient(String appKey, String appScret,
        String sessionKey, String username, String time) {
        super(appKey, appScret, sessionKey, username, time);
    }

    public MiopXMLRestClient(String appKey, String appScret,
        String sessionKey, String username, String time, int connectionTimeout,
        int readTimeout) {
        super(appKey, appScret, sessionKey, username, time, connectionTimeout,
            readTimeout);
    }

    protected Document parseResult(InputStream data,String method) throws MiopException, IOException {
        if(data == null)
        	return null;
    	try {
        	SAXReader saxReader = new SAXReader();
            Document doc;
            doc = saxReader.read(data);
            Element root = doc.getRootElement();
            if (root.getName().equals(ERROR_TAG)) {
            	Element code = root.element(ERROR_CODE);
                int errorCode = Integer.parseInt(code.getTextTrim());
                Element msgElement = root.element(ERROR_MSG);
                String message = null;
                if( msgElement != null )
                	message = msgElement.getTextTrim();
                throw new MiopException(errorCode, message);
            }

            return doc;
            
        }catch (DocumentException e) {
			e.printStackTrace();
		}

        return null;
    }
    
    public boolean getExecuteResult(Object document){
    	if(document == null)
    		return false;
    	Document doc = null;
    	if(document instanceof Document)
    		doc = (Document)document;
    	Element result = doc.getRootElement().element(RESULT);
    	if(result.getTextTrim() != null && result.getTextTrim().equals(""))
    		return result.getTextTrim().equals(RESULT_SUCCESS);
    	return false;
    }
    
    /** 
     * 解析xml并封装到指定类Class的对象中，并将这些生成的对象放入结果集中返回
     * 封装原则:Class 名对应xml 中的节点名，xml该节点下的element值映射到此Class的属性中(属�?的名称与element的TagName�?��).
     * parse xml ,and generate the specify Class Objects ,return them in a List
     * @return 由指定Class生成的对象的集合(List)
	 * @return A List contains the specify Class Objects.
     */
    public List getParsedList(Object document,Class clazz) {
    	if(document == null)
    		return null;
    	Document doc = null;
    	if(document instanceof Document)
    		doc = (Document)document;
        Element rootElement = doc.getRootElement();
        String elementName = MiopUtils.className2ElementName(clazz);
        List resultList = new ArrayList();
        if(rootElement.element(elementName) != null ){
	        rootElement = rootElement.element(elementName).getParent();
	        for(Iterator it = rootElement.elementIterator(); it.hasNext();){
	        	Object eleTmp = it.next();
	        	if(eleTmp instanceof Element){
	        		resultList.add(loopElement(null,(Element)eleTmp));
	        	}
	        }
        }
        return resultList;
    }
    
    /**
     * 获取�?��返回值，以List返回，用于解析查询好友Id列表等接�?
     * 
     * @param document
     * @return
     * @throws MiopException
     */
    public List getParsedPropertyList( Object document )throws MiopException{
    	if(document == null)
    		return null;
    	Document doc = null;
    	if(document instanceof Document)
    		doc = (Document)document;
    	
    	Element rootElement = doc.getRootElement();
    	List resultList = new ArrayList();
    	Iterator it = rootElement.elementIterator();
    	if( it.hasNext() ){ 
    		while( it.hasNext() ){
    			Element node = (Element)it.next();
    			resultList.add( node.getText() );
    		}
    	}else
    		resultList.add( rootElement.getText() );
 
		return resultList;
    }
    
    /**
     * 对传入的Element进行递归，将属�?或类对象setter到parentObj
     * @param parentObj
     * @param element
     * @return
     */
    private  Object loopElement(Object parentObj, Element element) {
        Object obj = null;

        try {
            if (isProperty(element)) {
                //取出节点名和节点名称
                String field = element.getName();
                String attribute = element.attributeValue("enc");
                String value = element.getTextTrim();

                if ((attribute != null) &&
                        attribute.equals(MiopUtils.ENC_BASE64)) {
                    value = MiopUtils.getBASE64DecoderValue(value);
                }
                //判断此field在类中存在不存在
                if(MiopUtils.getField(parentObj,element.getName()) != null){
	                PropertyDescriptor property = new PropertyDescriptor(field,
	                        parentObj.getClass());
	                property.getWriteMethod().invoke(parentObj, value);
                }
            } else {
                obj = MiopUtils.createObject(element.getName());

                //判别此obj是否已生成，当model中没定义此obj类时，会报异常返回null,为空则表示对此分支解析无意义，返回null
                if (obj != null) {
                    //父类对象为空就不setter 属�?名为element.getName()的list或�?对象
                    if (parentObj != null) {
                        //判断element的名字是否是此父类parentObj的属�?
                        if (MiopUtils.getField(parentObj, element.getName()) != null) {
                            PropertyDescriptor property = new PropertyDescriptor(element.getName(),
                                    parentObj.getClass());

                            //判断属�?类型是否为List
                            if (MiopUtils.isFieldList(parentObj, element.getName())) {
                                List list = (List) property.getReadMethod().invoke(parentObj);

                                if (list == null) {
                                    list = new ArrayList();
                                }

                                list.add(obj);
                                property.getWriteMethod().invoke(parentObj, list);
                            } else {
                                property.getWriteMethod().invoke(parentObj, obj);
                            }
                        }
                    }

                    Iterator iter = element.elementIterator();

                    while (iter.hasNext()) {
                        Element subElement = (Element) iter.next();
                        loopElement(obj, subElement);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            System.out.println("Have no field:"+element.getName());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * 判断Element是否是类的属�?
     * 判断原则:element是否包含子element
     * @param element
     * @return true:不包含子element;false:还包含子element;
     */
    public  boolean isProperty(Element element) {
        //获得子节�?
       Iterator it = element.elementIterator();
       return !it.hasNext();
    }

	


}
