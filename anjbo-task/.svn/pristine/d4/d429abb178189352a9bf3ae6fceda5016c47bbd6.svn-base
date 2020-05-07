package com.anjbo.task.estateprice;

import com.anjbo.bean.estateprice.SZCFJDto;
import com.anjbo.common.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class CFJUtil {

	
		public static List<SZCFJDto> getLatestSZCFJDtos() throws Exception {
        HttpUtil httpUtil = new HttpUtil();
        ArrayList<SZCFJDto> dtos = new ArrayList<SZCFJDto>();

        String AreaUrl="http://cfj.szhome.com/Project/GeAreaList/";
		String DataUrl="http://cfj.szhome.com/Project/GetAreaProjectList/";
		Map<String,String> areaMap = new LinkedHashMap<String,String>();//区域数据
		Map<String,String> areaParamMap = new HashMap<String,String>();//查询片区参数
		Map<String,String> areaChildMap = new LinkedHashMap<String,String>();//片区数据
		Map<String,String> dataParamMap = new HashMap<String,String>();//查询物业数据
		Set<String> sqlSet = new LinkedHashSet<String>();//实际需要的数据
		areaMap.put("1000", "罗湖区");
		areaMap.put("2000", "福田区");
		areaMap.put("3000", "南山区");
		areaMap.put("4000", "盐田区");
		areaMap.put("5000", "宝安区");
		areaMap.put("6000", "龙岗区");
		areaMap.put("9000", "龙华新区");
		areaMap.put("9200", "光明新区");
		areaMap.put("9400", "坪山新区");
		areaMap.put("9600", "大鹏新区");
		for (String key : areaMap.keySet()) {
			areaParamMap.put("AreaId", key);
			String areaResult = httpUtil.post(AreaUrl, areaParamMap);
			JSONObject areaJson = JSONObject.fromObject(areaResult);
			JSONArray areaList = areaJson.getJSONArray("Data");
			for (Object obj : areaList) {
				JSONObject areaChildJson = JSONObject.fromObject(obj);
				areaChildMap.put(key+"-"+areaChildJson.getInt("AreaId"), areaChildJson.getString("AreaName"));
			}
		}
		int PageIndex=1;//页索引
		int PageCount=100;//总页数
		for (String key : areaChildMap.keySet()) {
			while(PageIndex<=PageCount){
				String []keyArr = key.split("-");
				dataParamMap.put("page",String.valueOf(PageIndex));
				dataParamMap.put("areaId",keyArr[1]);
				String dataResult = httpUtil.post(DataUrl, dataParamMap);
				JSONObject dataResultJson = JSONObject.fromObject(dataResult);
				JSONObject dataJson = JSONObject.fromObject(dataResultJson.get("Data"));
				JSONArray dataList = dataJson.getJSONArray("list");
				for (Object obj : dataList) {
					JSONObject dataDetailJson = JSONObject.fromObject(obj);
					String sql = String.format("insert into tbl_cfj(area,areaChild,propertyName,avgPrice) values('%s','%s','%s','%s');",
							areaMap.get(keyArr[0]),
							areaChildMap.get(key),
							dataDetailJson.get("YituProjectName"),
							dataDetailJson.get("SellPrice"));
					sqlSet.add(sql);
					SZCFJDto dto = new SZCFJDto();
					dto.setDate(new Date());
                    dto.setArea(areaMap.get(keyArr[0]));
                    dto.setAreaChild(areaChildMap.get(key));
                    dto.setPropertyName((String) dataDetailJson.get("YituProjectName"));
                    dto.setAvgPrice(Double.valueOf((String) dataDetailJson.get("SellPrice")));
                    dtos.add(dto);
                }
				JSONObject pageSetJson = JSONObject.fromObject(dataJson.get("pageSet"));
				PageIndex++;//下一页
				PageCount = pageSetJson.getInt("PageCount");//总页数（同一区域总页数不变）
			}
			PageIndex=1;
			PageCount=100;
		}
		return dtos;
	}


	public static void StringBufferDemo(Set<String> setData) throws IOException{
        File file=new File("D:/cfj.sql");
        if(file.exists())
        	file.delete();
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,true);        
        for(String str : setData){
            StringBuffer sb=new StringBuffer();
            sb.append(str+"\r\n");
            out.write(sb.toString().getBytes("utf-8"));
            System.out.println("写入完成："+str);
        }        
        out.close();
    }

}
