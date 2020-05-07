package com.anjbo.chromejs.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 * 权重分配
 * @author Administrator
 *
 * @date 2017-7-31 下午05:44:14
 */
public class WeightUtil {
	//测试
    public static void main(String[] args) {
        String[] str1 = {"10","jk01"};
        String[] str2 = {"9","jk02"};
        String[] str3 = {"5","jk03"}; 
        String[] str4 = {"1","jk04"};
        List<String[]> list = new ArrayList<String[]>();
        list.add(str1);
        list.add(str2);
        list.add(str3);
        list.add(str4);
        Collections.sort(list, new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				return Integer.parseInt(o1[0])-Integer.parseInt(o2[0]);
			}
        });
        Map<String,Integer> map = new HashMap<String,Integer>();
        for(int i=0;i<1000;i++){
            String str = new WeightUtil().getMax(list);
            if(map.containsKey(str)){
            	map.put(str, map.get(str)+1);
            }else{
            	map.put(str,1);
            }
        }
        for (String key: map.keySet()) {
			System.out.println(key+"   "+map.get(key));
		}
    }

    /**
     * 获得给定List集合里权重大的结果
     * @param list
     * @return
     * @author Peter
     */
    public String getMax(List<String[]> list){      
        int len = list.size();
        int total = 0;//总权重
        //以权重区间段的后面的值作为key存当前信息
        TreeMap<Integer,String> map = new TreeMap<Integer, String>(); 
        for(int i=0; i<len; i++){
            String[] array = list.get(i);
            int weight = Integer.parseInt(array[0]);
            if(weight<=0){
            	continue;
            }
            total += weight;
            map.put(total, array[1]); 
        }
        if(map.size()==0){
        	return null;
        }
        int random = (int)(Math.random()*total);
        Integer key = map.higherKey(random);
        return map.get(key);
    }
}
