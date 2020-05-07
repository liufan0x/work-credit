package com.anjbo.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

public class BusinfoUtils {

	/**
	 * 组装影像资料
	 * @param parentList	父类型集合
	 * @param typeList		子类型集合
	 * @param listMap		影像资料集合
	 * @param typeIds		子类型权限	-1 为所有权限  (只需要那些类型)
	 * @return
	 * [{"childrenType": [{ "listImgs" : [{"name":"借据","url":"http://fs.anjbo.com"}] }] }]
	 * 
	 */
	public static List<Map<String, Object>> packagingBusinfo(List<Map<String, Object>> parentList, List<Map<String, Object>> typeList, List<Map<String, Object>> listMap,String typeIds) {
		//父类型
		for (Map<String, Object> parentMap : parentList) {
			String parentId = MapUtils.getString(parentMap, "id", "");
			List<Map<String, Object>> childrenList = new ArrayList<Map<String, Object>>();
			
			//子类型
			for (Map<String, Object> typeMap : typeList) {
				String typeId = MapUtils.getString(typeMap, "id", "");
				if (!"-1".equals(typeIds) && !typeIds.contains(typeId)) {
					break;
				}
				String pid = MapUtils.getString(typeMap, "pid", "");
				if (parentId.equals(pid)) {
					List<Map<String, Object>> listImg = new ArrayList<Map<String, Object>>();
					//数据
					Iterator<Map<String, Object>> it = listMap.iterator();
					while (it.hasNext()) {// 遍历影像资料集合赋值到typeMap
						Map<String, Object> imgMap = it.next();
						if (typeId.equals(MapUtils.getString(imgMap, "typeId", ""))) {
							imgMap.put("url", MapUtils.getString(imgMap, "url", "").replaceAll("_18", "_48"));
							listImg.add(imgMap);
							it.remove();
						}
					}
					typeMap.put("listImgs", listImg);
					childrenList.add(typeMap);
				}
			}
			parentMap.put("childrenType", childrenList);
		}
		return parentList;
	}
	
	/**
	 * 判断必填是否上传
	 * @return
	 */
	public static boolean checkBusinfo(){
		return false;
	}
	
}
