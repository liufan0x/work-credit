package com.anjbo.controller.impl.lineparty;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.anjbo.bean.lineparty.PlatformDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.ThirdApiConstants;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.ccb.ICCBController;
import com.anjbo.controller.lineparty.IPlatformController;
import com.anjbo.service.lineparty.PlatformService;
import com.anjbo.utils.erongsuo.AESUtils;
import com.anjbo.utils.huarong.HrRSA;
import com.mysql.fabric.xmlrpc.base.Array;

import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

@RestController
public class PlatformController extends BaseController implements IPlatformController{
    @Resource
    private PlatformService platformService;
    public static final String PLAIN_TEXT = "123322";
    public static final String PRIVATE_KEYS1 =
            "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCnVHsmeiDLlGOl1WWZ/AdvKKR6\r\n" + 
            "bLiRbqxw0nYXLq/OKNKHFJx9ZhepqnT5QXnG3P0RstoajYDmiPtganT2NPX/HW94OwJJGDFVyyAj\r\n" + 
            "oLnHm3hZEB/HDXn3QMIkZl6AAWhdrf/oyZOEPhP6Dk1QIoELxBEwtDoztjxVd3KrkzmVHMjvvSBi\r\n" + 
            "Sd8wg862FotkanowtIXSnpzVhXdXym9feslPXLUh1Sct0fnhDfoeqhmDCgqG4mbvFOeWAxKcpJtg\r\n" + 
            "5zaY21GI6tOLTV4Sgy2EBxFpRhrRml5O9v4ps0CF1OOIfjLf8v9MPU0kB/+b/7QctXStfdBESaLm\r\n" + 
            "HuxzDmQqWmipAgMBAAECggEANk1AOdhgVDCvtd1uv23+q6Aw4Fnv+6JC68og8J0CCJFO4O9baYZ8\r\n" + 
            "H19l3o3VCygkKF42UW+iaS4DBeMGWfAN9OCEkCv2Lepf+EuW/njmUVSu1ZhZ96rz7b1iQ5XFDR1n\r\n" + 
            "1R7mcZEZAk1zBi8l/99yqXLTcU7LpIR7R/EfkXaRtUW9dY6AdIWdacp7/0Uf5K1QGAUPHOy3x+W8\r\n" + 
            "9T4ggc+QDSoedBXX4CU77xMNZSQ1S5wv0eGccVWkVc4XSrk7lFRC54WwIS6Gl5TY/GHj8irUd7xe\r\n" + 
            "T2e7m8/IY3bsl4w4Bs5y/h/zABKF/02cpN/Az/kdzM1IAlAe89rmd07sN6cokQKBgQD5SauJYB3+\r\n" + 
            "F3sLviKvzeQP3j67t9YDJw0YasJw8Znh+nJEcaSMYwWVo4XETxKcKgtTFo/ELhJmIhlbMd/aYHet\r\n" + 
            "l7v9gWJhDhQVfLbPBIoYp2Kv4XoAvgWMMDHC/bEOXbpOg/58fPzhQfzMAO7alqAsVM6l5B/jI3PI\r\n" + 
            "jZQc2ixLZwKBgQCr1eEtbtC2fpQULBUvlWc03MYcH76sZ0o8OU7MNlDGoX4ESCO/8CXAlV1/bRAR\r\n" + 
            "1ikX2mwskIxhP7EhSJPbRXsl3xMzA42NtxqPApOyFM7jD2VLgBxhkWJAlZuukHWwP+uXjp51O1MH\r\n" + 
            "keIJQRLNrsrr7ancC2wIakuvHLv1GDExbwKBgQDbnVNJiyvhFb6I8dCNIM2yTRDnP2WDEXwv7p2t\r\n" + 
            "8qqmsoTIV3WnV/Urhwvpjd/PRBsF7/Jn2k7LO28rMB0Nvb6xZ+NtyfZpmoWHCH/kkRM39P60A+f/\r\n" + 
            "j2hDwJGWPvkktt3THeWstk/yArg64PeMCvbBF1WWQAr3h9wQ6ul5IZu0MQKBgQClUpBj0sMRYXT8\r\n" + 
            "d3Emp7p1HJeqBUzAuFuu14Adb4CFqmgAnJtwlg7sW4hqCbc1QfOlVGtQ8P1urwWvPRGsHAwgYqAp\r\n" + 
            "K5jsKKdvwg3xfp2RzYUqf6ZOpL5/3rC64ZepK8ZxuTBxH9OBa/Sp7Ka3pxzqom0THRYifoo0fxPC\r\n" + 
            "+jSSFwKBgQC4dD0IP49Cy6f+k78FysBXxZLisJZTB09o3LHSN1dRwkYkHl9ruJqlfOeZ4e8IK1yx\r\n" + 
            "/vj4Q+24tHzT6eQvrI2RjVTMPEv1yPpI7g198Ms9u7ciWMpQxX6hcphsWJ/UBij62GnBa59u5j+t\r\n" + 
            "l84NwJ0ohRZMNjS9cebvmmKDV/V8dQ==";
	@SuppressWarnings("unchecked")
	@Override
	public String getInsuranceFile(@RequestBody Map<String,Object> map) {
		String strings1 =null;
		try {
			String data1=MapUtils.getString(map,"data");
			String sign=MapUtils.getString(map,"sign");
			String lang="channelCode="+MapUtils.getString(map,"channelCode")+"&data="+data1+"&timeStamp="+MapUtils.getString(map,"timeStamp")+"&serviceName="+MapUtils.getString(map,"serviceName");
			boolean flat = HrRSA.verify(lang.getBytes(),ThirdApiConstants.PUBLIC_KEYS, sign);
			if (!flat) {
				return "非法访问";
			}
		strings1 = HrRSA.decryptByPrivateKey(data1,PRIVATE_KEYS1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 Map<String,Object> contents = JSON.parseObject(strings1);
		
	     PlatformDto platformDto = platformService.selectOne(MapUtils.getString(contents,"idCardNumber"));
		 Map<String,Object> maps = new HashMap<String, Object>();
		 Map<String,Object> data = new HashMap<String, Object>();
		 maps.put("retCode","0000");
		 maps.put("retMsg","成功");
		 if (platformDto==null) {
			 data.put("status","0");
			 maps.put("data", data);
		}
	         else {
				if (!StringUtils.isEmpty(platformDto.getStatus())) {
					   if (platformDto.getStatus().equals("1")) {
						   data.put("status","1");
					    } if (platformDto.getStatus().equals("2")) {
							   data.put("status","2");
						    } if (platformDto.getStatus().equals("3")) {
								   data.put("status","3");
								   String [] a = platformDto.getInsuranceFile().split(",");    //文件流长度
								   List<Map<String,Object>> insuranceFiles = new ArrayList<Map<String,Object>>();
								   for (int i = 0; i < a.length; i++) {
									   try {
										 Map<String,Object> insuranceFile = new HashMap<String, Object>();
										 byte [] s= toByteArray(a[i]);
										 String aString = HrRSA.rsaEncrypt(s,ThirdApiConstants.PUBLIC_KEYS);
										 insuranceFile.put("insuranceFile",aString);
										 insuranceFiles.add(insuranceFile);
										 data.put("insuranceFiles",insuranceFiles);
										 } catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									
								  }
								
							  }
						    maps.put("data", data);
				}
			}
		 return JSONObject.fromObject(maps).toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RespStatus getInsertFile(@RequestBody Map<String, Object> map) {
		// TODO Auto-generated method stub
		RespStatus resp = new RespStatus();
		if (StringUtils.isEmpty(MapUtils.getString(map, "customerName"))) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("姓名为空");
			return resp;
		}
	if (StringUtils.isEmpty(MapUtils.getString(map, "idCardType"))) {
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg("证件类型为空");
		return resp;
	}
		if (StringUtils.isEmpty(MapUtils.getString(map, "idCardNumber"))) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("证件号码为空");
			return resp;
		}
		if (MapUtils.getString(map, "idCardNumber").length()!=18) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("身份证格式不正确");
			return resp;
		}
		PlatformDto platformDto = new PlatformDto();
		platformDto.setIdCardNumber(MapUtils.getString(map, "idCardNumber"));
		platformDto.setCustomerName(MapUtils.getString(map, "customerName"));
		platformDto.setIdCardType(MapUtils.getString(map,"idCardType"));
		platformDto.setInsuranceFile(MapUtils.getString(map,"insuranceFile"));
		platformDto.setStatus("3");
		int i = 0;
		i = platformService.insertOne(platformDto);
		if (i!=0) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("成功");
		}
		return resp;
	}
	
	 public static byte[] toByteArray(String urlStr) throws IOException {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			DataInputStream in = new DataInputStream(conn.getInputStream());
	        ByteArrayOutputStream out=new ByteArrayOutputStream();
	        byte[] buffer=new byte[1024*4];
	        int n=0;
	        while ( (n=in.read(buffer)) !=-1) {
	            out.write(buffer,0,n);
	        }
	        return out.toByteArray();
		}
	
	 //数组转成文件
	 public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
		          BufferedOutputStream bos = null;
		          FileOutputStream fos = null;
		          File file = null;
		          try {
		              File dir = new File(filePath);
		              if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
		                  dir.mkdirs();
		              }
		              file = new File(filePath + "\\" + fileName);
		              fos = new FileOutputStream(file);
		              bos = new BufferedOutputStream(fos);
		              bos.write(bytes);
		          } catch (Exception e) {
		              e.printStackTrace();
		          } finally {
		              if (bos != null) {
		                  try {
		                      bos.close();
		                  } catch (IOException e) {
		                      e.printStackTrace();
		                  }
		              }
		              if (fos != null) {
		                  try {
		                      fos.close();
		                  } catch (IOException e) {
		                      e.printStackTrace();
		                  }
		              }
		          }
		      }
}
