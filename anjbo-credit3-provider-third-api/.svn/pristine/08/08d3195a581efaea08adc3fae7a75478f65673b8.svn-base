package com.anjbo.controller.impl.umeng;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.ThirdApiBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.umeng.IUmengController;
import com.anjbo.service.umeng.UmengService;

@RestController
public class UmengController extends ThirdApiBaseController implements IUmengController{
	
	@Resource private UmengService umengService;
	
	@Resource private UserApi userApi;

	public RespStatus pushText(@RequestBody Map<String, Object> params) {
		RespStatus resp = new RespStatus();
		try {
			String uid = MapUtils.getString(params, "uid","");
			String message = MapUtils.getString(params, "message","");
			if(StringUtils.isEmpty(uid) || StringUtils.isEmpty(message)) {
				resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
				resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			String token = userApi.findUserDtoByUid(uid).getToken();
			if(StringUtils.isNotEmpty(token)) {
				umengService.pushText(token, message);
			}
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("编辑异常,参数："+params, e);
			return RespHelper.failRespStatus();
		}
	}
	
}
