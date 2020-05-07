package com.anjbo.ws.ccb;

import com.anjbo.common.*;
import com.anjbo.common.Enums.CCBTranNoEnum;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.ccb.CCBUtil;
import com.anjbo.utils.ccb.DESPlus;
import com.anjbo.utils.ccb.XmlUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.ws.BindingProvider;
import java.util.HashMap;
import java.util.Map;

/**
 * 建行webservice接口调用帮助类
 * @author limh limh@anjbo.com   
 * @date 2016-12-30 下午12:48:04
 */
public class CCBWsHelper {
	protected static final Log logger = LogFactory.getLog(CCBWsHelper.class);
	/**
	 * 接口调用并解析结果
	 * @param tranNo
	 * @return
	 */
	private static RespDataObject<Map<String,Object>> post(String tranNo, Object obj){
		String bankNo = ThirdApiConstants.CCB_BANKNO;
		RespDataObject<Map<String,Object>> resp = postOp(CCBTranNoEnum.C006.getCode(), obj, bankNo);
		logger.info(String.format("请求接口[经办人信息查询交易]返回结果：%s", resp.getData().toString()));
		if(CCBTranNoEnum.C006.getCode().equals(tranNo)||!RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
			return resp;
		}
		bankNo = MapUtils.getString(resp.getData(),"RspbPsn_BnkNo");
		if(StringUtils.isEmpty(bankNo)){
			return RespHelper.setFailDataObject(resp,null,"未查询到客户经理的机构号，请联系管理员！");
		}
		resp = postOp(tranNo, obj, bankNo);
		logger.info(String.format("请求接口[%s]返回结果：%s", CCBTranNoEnum.getNameByCode(tranNo),resp.getData().toString()));
		return resp;
	}

	public static RespDataObject<Map<String,Object>> postOp(String tranNo, Object obj, String bankNo){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String ftlName = "";
		if(CCBTranNoEnum.C008.getCode().equals(tranNo)){
			ftlName="ccb_assess_apply.ftl";
		}else if(CCBTranNoEnum.C005.getCode().equals(tranNo)){
			ftlName="ccb_customer_query.ftl";
		}else if(CCBTranNoEnum.C001.getCode().equals(tranNo)){
			ftlName="ccb_customer_add.ftl";
		}else if(CCBTranNoEnum.C002.getCode().equals(tranNo)){
			ftlName="ccb_loan_add.ftl";
		}else if(CCBTranNoEnum.C006.getCode().equals(tranNo)){
			ftlName="ccb_customer_manager_query.ftl";
		}else if(CCBTranNoEnum.C004.getCode().equals(tranNo)){
			ftlName="ccb_loan_status_query.ftl";
		}else if(CCBTranNoEnum.C015_NEW.getCode().equals(tranNo)){
			ftlName="ccb_loan_audit_query.ftl";
		}else if(CCBTranNoEnum.C011.getCode().equals(tranNo)){
			ftlName="ccb_loan_lending_query.ftl";
		}else if(CCBTranNoEnum.C007.getCode().equals(tranNo)){
			ftlName="ccb_loan_businfo_upload.ftl";
		}else if(CCBTranNoEnum.C019.getCode().equals(tranNo)){
			ftlName="ccb_signature_appno_apply.ftl";
		}else if(CCBTranNoEnum.C020.getCode().equals(tranNo)){
			ftlName="ccb_signature_upload.ftl";
		}else if(CCBTranNoEnum.C024.getCode().equals(tranNo)){
			ftlName="ccb_house_apply.ftl";
		}else if(CCBTranNoEnum.C025.getCode().equals(tranNo)){
			ftlName="ccb_mortgage_appointment.ftl";
		}else if(CCBTranNoEnum.C026.getCode().equals(tranNo)){
			ftlName="ccb_mortgage_forensics.ftl";
		}else if(CCBTranNoEnum.C027.getCode().equals(tranNo)){
			ftlName="ccb_loan_businfo_add.ftl";
		}else{
			return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),resultMap, "未匹配到交易号");
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(obj);
			jsonObj.put("tranNo",tranNo);
			jsonObj.put("comNo",ThirdApiConstants.CCB_COMNO);
			jsonObj.put("bankNo",bankNo);
			jsonObj.put("channelNo",ThirdApiConstants.CCB_CHANNELNO);
			jsonObj.put("isn", CCBUtil.getID());
			String queryCondition = FreemarkerHelper.getIns().processTemplate("/ccb/"+ftlName, jsonObj);     //加载模板，返回结果
			int encryptFlag = 1;//加密形式
			String context = DESPlus.encrypt(queryCondition);//加密xml数据
	        AgencyCompanyServer_Service ss = new AgencyCompanyServer_Service();
	        AgencyCompanyServer port = ss.getAgencyCompanyServerPort();  
	        ((BindingProvider)port).getRequestContext().put("com.sun.xml.ws.connect.timeout",150000);
	        ((BindingProvider)port).getRequestContext().put("com.sun.xml.ws.request.timeout",150000);
	        SecondHouseLoadResp result = port.agencyCompanyLoadReq(tranNo, encryptFlag, context);
	        String resultContent = result.getContext();
	        resultContent = StringUtil.replaceBlank(resultContent);
	        String resultDesc = DESPlus.decryptForCharset(resultContent,"utf-8");
	        logger.info("DESPlus解密信息："+resultDesc);
	        resultDesc = resultDesc.replace("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>","").replace("]]>","");
	        resultMap = XmlUtils.Dom2Map(resultDesc);
	        String code = MapUtils.getString(resultMap, "RETCODE");
	        if("0000".equals(code)){
	        	return RespHelper.setSuccessDataObject(new RespDataObject<Map<String,Object>>(), resultMap);
	        }else if("1004".equals(code)){
	        	return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),
	    				resultMap, String.format(RespStatusEnum.THIRD_PARAM_ERROR.getMsg(), CCBTranNoEnum.getNameByCode(tranNo)));
	        }else if("9999".equals(code)){
	        	return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),
	    				resultMap, String.format(RespStatusEnum.THIRD_SERVER_ERROR.getMsg(), CCBTranNoEnum.getNameByCode(tranNo)));
	        }
		} catch (Exception e) {
			e.printStackTrace();
	    	return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),
					resultMap, String.format(RespStatusEnum.THIRD_ERROR.getMsg(), CCBTranNoEnum.getNameByCode(tranNo)));
		}
    	return RespHelper.setFailDataObject(new RespDataObject<Map<String,Object>>(),
				resultMap, MapUtils.getString(resultMap, "NOTE", String.format(RespStatusEnum.THIRD_ERROR.getMsg(), CCBTranNoEnum.getNameByCode(tranNo))));
	}
	/*public static void main(String[] args) throws Exception {
		//RespDataObject<Map<String,Object>> resp =post("C002",new HashMap<String,Object>());
		//String path = Thread.currentThread().getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println(Thread.currentThread().getClass().getResource("/").getFile().toString());
	}*/
}
