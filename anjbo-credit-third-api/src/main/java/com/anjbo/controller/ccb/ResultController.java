package com.anjbo.controller.ccb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.anjbo.common.Constants;
import com.anjbo.common.ReqMappingConstants;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.controller.BaseController;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.ccb.CCBEnums;
import com.anjbo.utils.ccb.DESPlus;
import com.anjbo.common.Enums.CCBTranNoEnum;
import com.anjbo.utils.ccb.XmlUtils;
import com.anjbo.utils.common.ThirdConfigUtil;

/**
 * 建行反馈
 * @author limh limh@anjbo.com   
 * @date 2017-1-2 下午10:14:08
 */
@Controller
@RequestMapping("/cm/ccb/result")
public class ResultController extends BaseController{
	protected final Log logger = LogFactory.getLog(this.getClass());  
	
	/**
	 * 添加反馈结果
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/add")
	public RespStatus add(HttpServletRequest request,@RequestBody Map<String,Object> param){
		logger.info(String.format("建行回调接口参数：%s",param.toString()));
		RespStatus resp = new RespStatus();
		String tranCode = MapUtils.getString(param, "tranCode");
		String appNo = MapUtils.getString(param, "appNo");
		String context = MapUtils.getString(param, "context");
		String origin = MapUtils.getString(param, "origin");
		if(StringUtils.isEmpty(tranCode)||StringUtils.isEmpty(appNo)||
				StringUtils.isEmpty(context)||StringUtils.isEmpty(origin)){
			return RespHelper.setFailRespStatus(resp, "参数异常");
		}
		try {
			if(!getOrigin(tranCode,appNo,context).equals(origin)){
				return RespHelper.setFailRespStatus(resp, "身份验证失败");
			}
			if(StringUtils.isEmpty(CCBTranNoEnum.getNameByCode(tranCode))){
				return RespHelper.setFailRespStatus(resp, "申请编号无效");
			}
			context = StringUtil.replaceBlank(context);
			String contextDesc = DESPlus.decryptForCharset(context,"utf-8");
			contextDesc = contextDesc.replace("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>","").replace("]]>","");
			Map<String,Object> contextMap = XmlUtils.Dom2Map(contextDesc);
			logger.info(String.format("建行回调接口参数context解析：%s",contextDesc));
			logger.info(String.format("建行回调接口参数context解析map集合：%s",contextMap.toString()));
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("tranCode",tranCode);
			paramMap.put("appNo",appNo);
			paramMap.put("contextMap",contextMap);
			RespStatus respStatus = new HttpUtil().getRespStatus(Constants.LINK_CMCREDIT,
					ReqMappingConstants.CREDIT_CM_RESULT_ADD, paramMap);
			resp = respStatus;
		} catch (Exception e) {
			e.printStackTrace();
			resp = RespHelper.failRespStatus();
		}
		return resp;
	}
	/**
	 * 身份校验码=约定MD5加密key+有序的参数追加+当天日期
	 *         并用MD5加密后结果转大写
	 * @param origin 约定MD5加密key
	 * @param param 按设置顺序的参数
	 * @return
	 */
	private String getOrigin(String ...param){
		StringBuffer sb = new StringBuffer(ThirdConfigUtil.getProperty(CCBEnums.GLOBAL_CONFIG_KEY.CCB_SECRET_KEY.toString()));
		for (String str : param) {
			sb.append(str);
		}     
		sb.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return MD5Utils.MD5Encode(sb.toString()).toUpperCase();
	}
	
}
