package com.anjbo.monitor.util;

import com.anjbo.monitor.common.RespData;
import com.anjbo.monitor.common.RespDataObject;
import com.anjbo.monitor.common.RespHelper;
import com.anjbo.monitor.common.RespStatus;
import com.anjbo.monitor.common.RespStatusEnum;
import com.anjbo.monitor.entity.BaseDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil
{
  private static int CONNTIMEOUT = 10000;

  public static String getRandomIp()
  {
    int[][] range = { { 607649792, 608174079 }, { 1038614528, 1039007743 }, { 1783627776, 1784676351 }, { 2035023872, 2035154943 }, { 2078801920, 2079064063 }, { -1950089216, -1948778497 }, { -1425539072, -1425014785 }, { -1236271104, -1235419137 }, { -770113536, -768606209 }, { -569376768, -564133889 } };

    Random rdint = new Random();
    int index = rdint.nextInt(10);
    String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
    return ip;
  }

  public static String num2ip(int ip)
  {
    int[] b = new int[4];
    String x = "";

    b[0] = (ip >> 24 & 0xFF);
    b[1] = (ip >> 16 & 0xFF);
    b[2] = (ip >> 8 & 0xFF);
    b[3] = (ip & 0xFF);
    x = new StringBuilder().append(Integer.toString(b[0])).append(".").append(Integer.toString(b[1])).append(".").append(Integer.toString(b[2])).append(".").append(Integer.toString(b[3])).toString();

    return x;
  }

  public static String get(String url, String charset, int readTimeOut, String cookieValue, String Referer)
    throws Exception
  {
    GetMethod m = new GetMethod(url);
    StringBuilder rsp = new StringBuilder();
    HttpClient httpClient = null;
    try {
      m.setRequestHeader("Connection", "close");
      if (StringUtils.isNotEmpty(cookieValue)) {
        m.setRequestHeader("Cookie", cookieValue);
      }
      if (StringUtils.isNotEmpty(Referer)) {
        m.setRequestHeader("Referer", Referer);
      }
      String ip = getRandomIp();
      m.addRequestHeader("X-Forwarded-For", ip);
      m.addRequestHeader("Proxy-Client-IP", ip);
      m.addRequestHeader("WL-Proxy-Client-IP", ip);
      m.addRequestHeader("HTTP_CLIENT_IP", ip);
      m.addRequestHeader("X-Real-IP", ip);
      httpClient = new HttpClient();
      httpClient.getHttpConnectionManager().getParams()
        .setConnectionTimeout(CONNTIMEOUT);

      httpClient.getHttpConnectionManager().getParams()
        .setSoTimeout(readTimeOut);
      BufferedReader br;
      if (httpClient.executeMethod(m) == 200)
      {
        br = new BufferedReader(new InputStreamReader(m
          .getResponseBodyAsStream(), charset));
        boolean first = true;
        try
        {
          String line;
          while ((line = br.readLine()) != null) {
            if (first)
              first = false;
            else {
              rsp.append('\n');
            }
            rsp.append(line);
          }
        } catch (Exception e) {
          throw e;
        } finally {
          try {
            br.close();
          }
          catch (Exception localException2)
          {
          }
        }
        String line;
        SimpleHttpConnectionManager simpleHttpConnectionManager;
        return rsp.toString();
      }
      SimpleHttpConnectionManager simpleHttpConnectionManager;
      return null;
    }
    catch (Exception e) {
      throw e;
    } finally {
      try {
        m.releaseConnection();

        SimpleHttpConnectionManager simpleHttpConnectionManager = (SimpleHttpConnectionManager)httpClient
          .getHttpConnectionManager();
        if (simpleHttpConnectionManager != null)
          simpleHttpConnectionManager.shutdown();
      }
      catch (Exception localException5)
      {
      }
    }
  }

  public static String post(String url, String hcharset, String charset, Map<String, String> map, int readTimeOut)
    throws Exception
  {
    PostMethod m = new PostMethod(url);
    StringBuilder rsp = new StringBuilder();
    HttpClient httpClient = null;
    try {
      m.setRequestHeader("Connection", "close");
      String cookieValue = (String)map.get("cookieValue");
      if (StringUtils.isNotEmpty(cookieValue)) {
        m.setRequestHeader("Cookie", cookieValue);
        map.remove("cookieValue");
      }
      String requested = (String)map.get("requested");
      if (StringUtils.isNotEmpty(requested)) {
        m.setRequestHeader("X-Requested-With", requested);
        map.remove("requested");
      }
      String referer = (String)map.get("referer");
      if (StringUtils.isNotEmpty(referer)) {
        m.setRequestHeader("Referer", referer);
        map.remove("referer");
      }
      if (StringUtils.isNotEmpty(hcharset)) {
        m.getParams().setParameter("http.protocol.content-charset", hcharset);
      }

      String ip = getRandomIp();
      m.addRequestHeader("X-Forwarded-For", ip);
      m.addRequestHeader("Proxy-Client-IP", ip);
      m.addRequestHeader("WL-Proxy-Client-IP", ip);
      m.addRequestHeader("HTTP_CLIENT_IP", ip);
      m.addRequestHeader("X-Real-IP", ip);
      httpClient = new HttpClient();
      httpClient.getHttpConnectionManager().getParams()
        .setConnectionTimeout(CONNTIMEOUT);

      httpClient.getHttpConnectionManager().getParams()
        .setSoTimeout(readTimeOut);

      if ((map != null) && (!map.isEmpty())) {
        NameValuePair[] nameVP = new NameValuePair[map.size()];
        int ii = 0;
        for (Map.Entry entry : map.entrySet()) {
          nameVP[ii] = new NameValuePair((String)entry.getKey(), 
            (String)entry
            .getValue());
          ii++;
        }
        m.setRequestBody(nameVP);
      }
      BufferedReader br;
      if (httpClient.executeMethod(m) == 200)
      {
        br = new BufferedReader(new InputStreamReader(m
          .getResponseBodyAsStream(), charset));
        boolean first = true;
        try
        {
          String line;
          while ((line = br.readLine()) != null) {
            if (first)
              first = false;
            else {
              rsp.append('\n');
            }
            rsp.append(line);
          }
        } catch (Exception e) {
          throw e;
        } finally {
          try {
            br.close();
          }
          catch (Exception localException2)
          {
          }
        }
        String line;
        SimpleHttpConnectionManager simpleHttpConnectionManager;
        return rsp.toString();
      }
      SimpleHttpConnectionManager simpleHttpConnectionManager;
      return null;
    }
    catch (Exception e) {
      throw e;
    } finally {
      try {
        m.releaseConnection();

        SimpleHttpConnectionManager simpleHttpConnectionManager = (SimpleHttpConnectionManager)httpClient
          .getHttpConnectionManager();
        if (simpleHttpConnectionManager != null)
          simpleHttpConnectionManager.shutdown();
      }
      catch (Exception localException5)
      {
      }
    }
  }

  public static String post(String url, Map<String, String> map, int timeout)
    throws Exception
  {
    if (timeout == 0) {
      timeout = 10000;
    }
    return post(url, "", "UTF-8", map, timeout);
  }

  public static String post(String url, Map<String, String> map)
    throws Exception
  {
    return post(url, "", "UTF-8", map, 10000);
  }

  public static String post(String url, Map<String, String> map, String hcharset)
    throws Exception
  {
    return post(url, hcharset, "UTF-8", map, 10000);
  }

  public static String post(String url, Map<String, String> map, String hcharset, String charset)
    throws Exception
  {
    return post(url, hcharset, charset, map, 10000);
  }

  public static String get(String url, int timeout)
    throws Exception
  {
    if (timeout == 0) {
      timeout = 10000;
    }
    return get(url, "UTF-8", timeout, null, null);
  }

  public static String get(String url)
    throws Exception
  {
    return get(url, "UTF-8", 10000, null, null);
  }
  public static String get(String url, String cookieValue, String Referer) throws Exception {
    return get(url, "UTF-8", 10000, cookieValue, Referer);
  }
  public static boolean httpClientGet(String uri) throws Exception {
    try {
      HttpClient client = new HttpClient();

      HttpMethod method = new GetMethod(uri);

      int result = client.executeMethod(method);
      if (result == 200) {
        return true;
      }
      return false; } catch (Exception e) {
    }
    return false;
  }

  public static String jsonPost(String strURL, Map<String, Object> map)
  {
    try
    {
      URL url = new URL(strURL);

      HttpURLConnection connection = (HttpURLConnection)url
        .openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setUseCaches(false);
      connection.setInstanceFollowRedirects(true);
      connection.setConnectTimeout(CONNTIMEOUT);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Accept", "application/json");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.connect();

      OutputStreamWriter out = new OutputStreamWriter(connection
        .getOutputStream(), "UTF-8");

      String params = JSONSerializer.toJSON(map).toString();
      out.append(params);
      out.flush();
      out.close();

      InputStream is = connection.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
      String line = null;
      StringBuilder sb = new StringBuilder();
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      br.close();
      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "error";
  }

  public static RespStatus getRespStatus(String modular, String requestMapping, Object object) {
    JSONObject json = getData(modular, requestMapping, object);
    RespStatus resp = (RespStatus)JSONObject.toBean(json, RespStatus.class);
    return resp;
  }

	/*
	 * public static <T> T getObject(String modular, String requestMapping, Object
	 * object, Class clazz) { JSONObject json = getData(modular, requestMapping,
	 * object); RespDataObject resp = new RespDataObject();
	 * 
	 * resp = (RespDataObject)JSONObject.toBean(json, resp.getClass()); if
	 * (resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())) { if
	 * (!isWrapClass(clazz)) { JSONUtils.getMorpherRegistry().registerMorpher(new
	 * TimestampToDateMorpher());
	 * 
	 * return JSONObject.toBean(JSONObject.fromObject(resp.getData()), clazz); }
	 * return resp.getData(); }
	 * 
	 * return null; }
	 */

  public static <T> RespDataObject<T> getRespDataObject(String modular, String requestMapping, Object object, Class clazz)
  {
    JSONObject json = getData(modular, requestMapping, object);
    RespDataObject resp = new RespDataObject();
    resp = (RespDataObject)JSONObject.toBean(json, resp.getClass());
    if (resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
      if (!isWrapClass(clazz))
        resp.setData(JSONObject.toBean(JSONObject.fromObject(resp.getData()), clazz));
      else {
        return resp;
      }
    }

    return resp;
  }

  public static <T> List<T> getList(String modular, String requestMapping, Object object, Class clazz)
  {
    JSONObject json = getData(modular, requestMapping, object);
    RespData resp = (RespData)JSONObject.toBean(json, RespData.class);
    if (resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
      List list = new ArrayList();
      for (Iterator localIterator = resp.getData().iterator(); localIterator.hasNext(); ) { Object t = localIterator.next();
        if (!isWrapClass(clazz)) {
          JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher());
          list.add(JSONObject.toBean(JSONObject.fromObject(t), clazz));
        } else {
          list.add(t);
        }
      }
      return list;
    }
    return null;
  }

  public static <T> RespData<T> getRespData(String modular, String requestMapping, Object object, Class clazz)
  {
    JSONObject json = getData(modular, requestMapping, object);
    RespData resp = (RespData)JSONObject.toBean(json, RespData.class);
    if (resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
      List list = new ArrayList();
      for (Iterator localIterator = resp.getData().iterator(); localIterator.hasNext(); ) { Object t = localIterator.next();
        if (!isWrapClass(clazz)) {
          JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher());
          list.add(JSONObject.toBean(JSONObject.fromObject(t), clazz));
        } else {
          list.add(t);
        }
      }
      resp.setData(list);
    }
    return resp;
  }

  public static <T> JSONObject getData(String modular, String requestMapping, Object object) {
    if (object == null) object = new Object();
    String url = new StringBuilder().append(modular).append("/").append(requestMapping).toString();
    return jsonPost(url, object);
  }

  private static boolean isWrapClass(Class clz)
  {
    try {
      return ((Class)clz.getField("TYPE").get(null)).isPrimitive(); } catch (Exception e) {
    }
    return false;
  }

  private static <T> JSONObject jsonPost(String url, Object object)
  {
    JSONObject json = null;
    RespStatus resp = new RespStatus();
    RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
    try {
      HttpPost httpPost = new HttpPost(url);
      CloseableHttpClient client = HttpClients.createDefault();
      StringEntity entity = new StringEntity(JSONSerializer.toJSON(object).toString(), "utf-8");
      entity.setContentEncoding("UTF-8");
      entity.setContentType("application/json");
      httpPost.setEntity(entity);
      httpPost.addHeader("key", new BaseDto().getKey());
      HttpResponse response = client.execute(httpPost);
      if (response.getStatusLine().getStatusCode() == 200) {
        HttpEntity he = response.getEntity();
        return JSONObject.fromObject(EntityUtils.toString(he, "UTF-8"));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      RespHelper.setFailRespStatus(resp, "请求接口异常");
    }
    json = JSONObject.fromObject(resp);
    return json;
  }
}