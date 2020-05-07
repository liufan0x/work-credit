package com.anjbo.utils.ccb;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class XmlUtils {
	public static Map<String, Object> Dom2Map(String xml){
		try {
			Document doc = DocumentHelper.parseText(xml);
	        return XmlUtils.Dom2Map(doc);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return new HashMap<String, Object>();
	}
  @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, Object> Dom2Map(Document doc){
        Map<String, Object> map = new HashMap<String, Object>();
        if(doc == null) 
            return map; 
        Element root = doc.getRootElement(); 
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
            Element e = (Element) iterator.next(); 
            List list = e.elements();
            if(list.size() > 0){ 
                //map.put(e.getName(), Dom2Map(e)); 
                map.putAll(Dom2Map(e)); 
            }else 
                map.put(e.getName(), e.getText()); 
        } 
        return map; 
    } 
     @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map Dom2Map(Element e){
        Map map = new HashMap();
        List list = e.elements();
        if(list.size() > 0){ 
            for (int i = 0;i < list.size(); i++) { 
                Element iter = (Element) list.get(i); 
                List mapList = new ArrayList();
                 
                if(iter.elements().size() > 0){ 
                    Map m = Dom2Map(iter);
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName());
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList();
                            mapList.add(obj); 
                            mapList.add(m); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj;
                            mapList.add(m); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.putAll(m); 
                } 
                else{ 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName());
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList();
                            mapList.add(obj); 
                            mapList.add(iter.getText()); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj;
                            mapList.add(iter.getText()); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), iter.getText()); 
                } 
            } 
        }else 
            map.put(e.getName(), e.getText()); 
        return map; 
    } 
} 