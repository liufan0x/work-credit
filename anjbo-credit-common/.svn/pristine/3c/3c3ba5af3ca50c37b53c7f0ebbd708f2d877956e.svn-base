package com.anjbo.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONSerializer;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JsonHelper json对象处理帮助类
 * 
 * @author Jerry
 * @version v1.0 JsonHelper.java 2013-9-25 下午01:44:04
 */
public class JsonUtil {

	private final static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * Java对象转换为String
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午03:35:11
	 * @param dataObject
	 *            Java对象
	 * @return Json格式的字符串
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String parserObjToString(Object dataObject)
			throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.writeValueAsString(dataObject);
	}

	/**
	 * 
	 * Json格式的字符串转换为Java对象
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午03:38:14
	 * @param json
	 *            Json格式字符串
	 * @param clazz
	 *            要转换的类型
	 * @return 转换对象
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Object parseJsonToObj(String json, Class<?> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(json, clazz);
	}

	/**
	 * 
	 * Json格式字符串转化为Map
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午03:39:26
	 * @param json
	 *            Json格式字符串
	 * @return key=value
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJsonToMap(String json)
			throws JsonParseException, JsonMappingException, IOException {
		return (Map<String, Object>) parseJsonToObj(json, Map.class);
	}

	/**
	 * 
	 * 处理ams返回的结果字符串 如
	 * code=0&uid=106859684&number=18665965973&password=3f80373bc95e&email=
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午03:43:24
	 * @param result
	 *            ams的返回结果
	 * @return key=value
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseAMSResult(String result)
			throws JsonParseException, JsonMappingException, IOException {
		return (Map<String, String>) parseJsonToObj(
				StringUtil.parseAmsResultToJson(result), Map.class);
	}

	/**
	 * 返回JSONObject ，可以直接获取指定类型
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-26 下午07:17:25
	 * @param result
	 *            {"result":0,"extendurl":"http://kcoo.cn/FFFN7b"}
	 * @return JSONObject
	 * @throws JSONException
	 */
	public static JSONObject parseStringToJson(String result)
			throws JSONException {
		return new JSONObject(result);
	}

	/**
	 * 将list转换成JSON格式
	 * 
	 * @Title: listToJson
	 * @param @param list
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String listToJson(List<?> list) {
		return JSONSerializer.toJSON(list).toString();
	}

	/**
	 * 将实体Bean转换成JSON格式
	 * 
	 * @Title: BeanToJson
	 * @param @param obj
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String BeanToJson(Object obj) {
		return JSONSerializer.toJSON(obj).toString();
	}

	/**
	 * 将一个json字串转为list
	 * 
	 * @param props
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> JsonTolist(String answer) {
		if (answer == null || answer.equals(""))
			return new ArrayList<Object>();

		JSONArray jsonArray = JSONArray.fromObject(answer);
		List<Object> list = (List<Object>) JSONArray.toCollection(jsonArray,
				Object.class);
		return list;
	}

	/**
	 * 将一个map转成json
	 */
	public static String MapToJson(Map<?, ?> maps) {
		return JSONSerializer.toJSON(maps).toString();
	}

	/**
	 * 
	 * 将json转成map
	 */
	public static Map<?, ?> JsonToMap(String result) throws JsonParseException,
			JsonMappingException, IOException {
		return (Map<?, ?>) parseJsonToMap(StringUtil
				.parseAmsResultToJson(result));
	}

	/**
	 * 将json转成map
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static Map<String, Object> jsonToMap(String json)
			throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;

		while (iterator.hasNext()) {
			key = (String) iterator.next();
			if(jsonObject.get(key)!=JSONObject.NULL&&jsonObject.get(key) instanceof Integer) {
				value = String.valueOf(jsonObject.getInt(key));
			}else if(jsonObject.get(key)!=JSONObject.NULL){
				value = jsonObject.getString(key);
			}else{
				value = null;
			}
			map.put(key, value);
		}
		return map;
	}
	
	public static void nullToString(Map<String,Object> map){
		if(null==map)return;
		
		Set<String> keys = map.keySet();
		for(String key:keys){
			if("null".equals(map.get(key)+"")||JSONNull.getInstance().equals(map.get(key))){
				map.put(key, "null");
			}
		}
		
	}
}
