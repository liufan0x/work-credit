package com.anjbo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Document;  
import org.dom4j.Element;  
import org.dom4j.Namespace;  
import org.dom4j.QName;  

public class XmlAndMap {

	/**
	 * 
	 * @user Object
	 * @date 2016-11-24 上午10:17:26 
	 * @param xmlString	传入XML
	 * @param map	返回带结构的Map
	 * @param map1	返回所有key的Map(适用于只有一级的XML)
	 * @throws DocumentException
	 */
	@SuppressWarnings("rawtypes")
	public static void xml2map(String xmlString,Map map,Map map1) throws DocumentException {  
		if(map == null){
			map = new HashMap();
		}
		if(map1 == null){
			map1 = new HashMap();
		}
		Document doc = DocumentHelper.parseText(xmlString);  
		Element rootElement = doc.getRootElement();  
		ele2map(map,map1,rootElement);  
		System.out.println(map);    
		System.out.println(map1);  
		// 到此xml2map完成，下面的代码是将map转成了json用来观察我们的xml2map转换的是否ok  
		String string = JSONObject.fromObject(map).toString();  
		System.out.println(string);  
	} 

	/*** 
	 * 核心方法，里面有递归调用 
	 *  
	 * @param map 
	 * @param ele 
	 */  
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void ele2map(Map map,Map map1, Element ele) {  
		System.out.println(ele);  
		// 获得当前节点的子节点  
		List<Element> elements = ele.elements();  
		if (elements.size() == 0) {  
			// 没有子节点说明当前节点是叶子节点，直接取值即可  
			map.put(ele.getName(), ele.getText());  
		} else if (elements.size() == 1) {  
			// 只有一个子节点说明不用考虑list的情况，直接继续递归即可  
			Map<String, Object> tempMap = new HashMap<String, Object>();  
			ele2map(tempMap,map1, elements.get(0));  
			map1.putAll(tempMap);
			map.put(ele.getName(), tempMap);  
		} else {  
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的  
			// 构造一个map用来去重  
			Map<String, Object> tempMap = new HashMap<String, Object>();  
			for (Element element : elements) {  
				tempMap.put(element.getName(), null);  
			}  
			Set<String> keySet = tempMap.keySet();  
			for (String string : keySet) {  
				Namespace namespace = elements.get(0).getNamespace();  
				List<Element> elements2 = ele.elements(new QName(string,namespace));  
				// 如果同名的数目大于1则表示要构建list  
				if (elements2.size() > 1) {  
					List<Map> list = new ArrayList<Map>();  
					for (Element element : elements2) {  
						Map<String, Object> tempMap1 = new HashMap<String, Object>();  
						ele2map(tempMap1,map1, element); 
						map1.putAll(tempMap1);   
						list.add(tempMap1);  
					}
					map.put(string, list);  
				} else {  
					// 同名的数量不大于1则直接递归去  
					Map<String, Object> tempMap1 = new HashMap<String, Object>();  
					ele2map(tempMap1, map1, elements2.get(0)); 
					map1.putAll(tempMap1);    
					map.put(string, tempMap1);  
				}  
			}  
		}  
	}  
}
