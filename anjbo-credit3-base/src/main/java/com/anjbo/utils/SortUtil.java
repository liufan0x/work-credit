package com.anjbo.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;

import java.util.Map.Entry;

public class SortUtil {

	public static Entry<String, Object> getSortType(Map<String,Object> paramMap,String[] ss){
		Set<Entry<String, Object>> set=paramMap.entrySet();
		Iterator<Entry<String, Object>> i=set.iterator();
		for(;;i.hasNext()) {
			Entry<String, Object> e=i.next();
			for(String s:ss) {
				if(s.equals(e.getKey())) {
					return e;
				}
			}
		}
		
	}
	public static void sort(List<Map<String, Object>> targetList,  String sortField,  String sortMode) {
		
		Collections.sort(targetList, new Comparator<Map>() {
			@Override
			public int compare(Map obj1, Map obj2) { 
				int retVal = 0;
				try {
					double d1 = MapUtils.getDoubleValue(obj1, sortField, 0d);
					double d2 = MapUtils.getDoubleValue(obj2, sortField, 0d);;
					if (sortMode != null && "desc".equals(sortMode)) {
						retVal = d2-d1>0?1:d2-d1==0?0:-1;
					} else {
						retVal = d1-d2>0?1:d1-d2==0?0:-1;
					}
				} catch (Exception e) {
					throw new RuntimeException();
				}
				return retVal;
			}
		});
		
	}
}
