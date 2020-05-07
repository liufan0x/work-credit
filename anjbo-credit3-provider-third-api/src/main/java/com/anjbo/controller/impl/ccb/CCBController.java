package com.anjbo.controller.impl.ccb;

import com.anjbo.common.*;
import com.anjbo.common.Enums.CCBTranNoEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.ccb.ICCBController;
import com.anjbo.utils.SingleUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CCBController extends BaseController implements ICCBController {

	@SuppressWarnings("unchecked")
	@Override
	public RespDataObject<Map<String, Object>> getMatchCusManager(@RequestBody Map<String, Object> map) {
		try {
			Map<String,Object> parmMap=new HashMap<String,Object>();
			parmMap.put("tranNo",CCBTranNoEnum.C006.getCode());
			parmMap.put("obj", map);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(parmMap, headers);
			return SingleUtils.getRestTemplate().postForObject(Constants.LINK_ANJBO_CM_URL + "/credit/third/api/ccb/wshelper/v/post", requestEntity, RespDataObject.class);
		} catch (Exception e) {
			logger.error("匹配客户经理信息异常：",e);
			return RespHelper.setFailDataObject(new RespDataObject<Map<String, Object>>(),null,RespStatusEnum.FAIL.getMsg());
		}
	}

}
