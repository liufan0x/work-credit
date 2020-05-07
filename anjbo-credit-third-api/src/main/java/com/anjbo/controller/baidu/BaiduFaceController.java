package com.anjbo.controller.baidu;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.service.baidu.BaiduFaceService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.common.ThirdConfigUtil;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/credit/third/api/baidu/face")
public class BaiduFaceController extends BaseController {
	protected final Log logger = LogFactory.getLog(this.getClass());  
	static final String jrwsUrl = ThirdConfigUtil.getProperty("face.url");
	static final String sp_no = ThirdConfigUtil.getProperty("queryRiskList.sp_no");
	static final String sign_type = "1";
	static final String key =ThirdConfigUtil.getProperty("queryRiskList.key");
	@Resource
	private BaiduFaceService baiduFaceService;
	
	@ResponseBody
	@RequestMapping("/query")
	public RespDataObject<JSONObject> query(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<JSONObject> resp = new RespDataObject<JSONObject>();
		String callbackkey = MapUtils.getString(map, "callbackkey");
		String uid = MapUtils.getString(map, "exuid");
		if(StringUtils.isEmpty(callbackkey)||StringUtils.isEmpty(uid)){
			return RespHelper.setFailDataObject(resp,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("callbackkey=").append(callbackkey);
			sb.append("&datetime=").append(new Date().getTime());
			sb.append("&reqid=").append((int)(Math.random()*900)+100);
			sb.append("&sign_type=").append(sign_type);
			sb.append("&sp_no=").append(sp_no);
			sb.append("&uid=").append(uid);
			String sign = MD5Utils.MD5Encode(sb.toString()+"&key="+key);
			String json = HttpUtil.get(jrwsUrl+"?"+sb.append("&sign=").append(sign).toString());
			JSONObject obj = JSONObject.fromObject(json);
//			logger.info("请求百度金融磐石系统活体结果查询接口返回："+obj);
			if("0".equals(obj.getString("retCode"))){
				JSONObject resultJson = obj.getJSONObject("result");
				String score = resultJson.getString("score");
				String imageBase64 = resultJson.getString("image");
				String imageUrl = fsUpload(imageBase64);
				String appSource = request.getHeader("appSource");
				String city  = request.getHeader("city");
				String device  = request.getHeader("device");
				String osType = request.getHeader("osType");
				String terminalType  = request.getHeader("terminalType");
				String version   = request.getHeader("version");
				String idCardNo = MapUtils.getString(map, "idCardNo");
				String realName = MapUtils.getString(map, "realName");
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("uid", uid);
				paramMap.put("idCardNo", idCardNo);
				paramMap.put("realName", realName);
				paramMap.put("imageUrl", imageUrl);
				paramMap.put("score", score);
				paramMap.put("imageBase64", imageBase64);
				paramMap.put("appSource", appSource);
				paramMap.put("city", StringUtils.isNotBlank(city)?URLDecoder.decode(city, "utf-8"):city);
				paramMap.put("device", device);
				paramMap.put("terminalType", terminalType);
				paramMap.put("osType", osType);
				paramMap.put("version", version);
				baiduFaceService.addBaiduFaceData(paramMap);
				resultJson.put("imageUrl",imageUrl);
				Iterator<String> keys = resultJson.keys();
				while (keys.hasNext()){
					String key = keys.next();
					if(JSONNull.getInstance().equals(resultJson.getString(key))){
						resultJson.put(key,"");
					}
				}
				return RespHelper.setSuccessDataObject(resp,resultJson);
			}else{
				return RespHelper.setFailDataObject(resp,null, obj.getString("retMsg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.setFailDataObject(resp,null,"请求第三方服务器异常，请稍后再试");
		}
	}
	/**
	 * 百度获取的图片base64存储
	 * @param imageByte
	 * @return
	 */
	private String fsUpload(String imageByte) {
		try {
			String FS_URL = ConfigUtil.getStringValue(Constants.LINK_FS_URL,ConfigUtil.CONFIG_LINK); 
			Map<String, String> params = new HashMap<String, String>();
			params.put("imageByte", imageByte);
			params.put("photoPath", "/photo/baidu/face/");
			params.put("serverName", FS_URL);
			String result = HttpUtil.post(FS_URL+"/fs/base64Decode/upload",params);
			logger.info("img file upload return:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject obj = JSONObject.fromObject(result);
				if (obj.getInt("status") == 1) {
					return obj.getString("url");
				}
			}
		} catch (Exception e) {
			logger.error("img file upload exception", e);
		}
		return null;
	}
}
