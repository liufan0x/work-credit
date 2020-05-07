package com.anjbo.monitor.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil
{
  private static final ObjectMapper mapper = new ObjectMapper();

  public static String parserObjToString(Object dataObject)
    throws JsonGenerationException, JsonMappingException, IOException
  {
    return mapper.writeValueAsString(dataObject);
  }

  public static Object parseJsonToObj(String json, Class<?> clazz)
    throws JsonParseException, JsonMappingException, IOException
  {
    return mapper.readValue(json, clazz);
  }

  public static Map<String, Object> parseJsonToMap(String json)
    throws JsonParseException, JsonMappingException, IOException
  {
    return (Map)parseJsonToObj(json, Map.class);
  }

  public static Map<String, String> parseAMSResult(String result)
    throws JsonParseException, JsonMappingException, IOException
  {
    return (Map)parseJsonToObj(
      StringUtil.parseAmsResultToJson(result), 
      Map.class);
  }

  public static JSONObject parseStringToJson(String result)
    throws JSONException
  {
    return new JSONObject(result);
  }

  public static String listToJson(List<?> list)
  {
    return JSONSerializer.toJSON(list).toString();
  }

  public static String BeanToJson(Object obj)
  {
    return JSONSerializer.toJSON(obj).toString();
  }

  public static List<Object> JsonTolist(String answer)
  {
    if ((answer == null) || (answer.equals(""))) {
      return new ArrayList();
    }
    JSONArray jsonArray = JSONArray.fromObject(answer);
    List list = (List)JSONArray.toCollection(jsonArray, Object.class);

    return list;
  }

  public static String MapToJson(Map<?, ?> maps)
  {
    return JSONSerializer.toJSON(maps).toString();
  }

  public static Map<?, ?> JsonToMap(String result)
    throws JsonParseException, JsonMappingException, IOException
  {
    return parseJsonToMap(
      StringUtil.parseAmsResultToJson(result));
  }

  public static Map<String, Object> jsonToMap(String json)
    throws JSONException
  {
    JSONObject jsonObject = new JSONObject(json);
    Map map = new HashMap();

    Iterator iterator = jsonObject.keys();
    String key = null;
    String value = null;

    while (iterator.hasNext()) {
      key = (String)iterator.next();
      value = jsonObject.getString(key);
      map.put(key, value);
    }
    return map;
  }
}