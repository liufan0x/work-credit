package com.anjbo.controller.cm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.anjbo.bean.cm.CMBusInfoDto;
import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.ReqMappingConstants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.service.ProductDataBaseService;
import com.anjbo.service.cm.AssessService;
import com.anjbo.service.cm.BusInfoService;
import com.anjbo.service.cm.ProgressService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.StringUtil;

/**
 * 调用建行接口
 * @author chenzm   
 * @date 2017-8-23 上午10:53:00
 */

@Controller
@RequestMapping("/credit/product/data/cm/assess/v")
public class AssessController extends BaseController{
	protected final Log logger = LogFactory.getLog(this.getClass());  
	

	@Resource ProgressService progressService;
	@Resource BusInfoService busInfoService;
	@Resource AssessService assessService;
	@Resource ProductDataBaseService productDataBaseService;
	/**
	 * 根据订单编号和编码获取影像资料
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/getByOrderAndCode")
	public RespDataObject<List<CMBusInfoDto>> getByOrderAndCode(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<List<CMBusInfoDto>> resp = new RespDataObject<List<CMBusInfoDto>>();
		try {
			List<CMBusInfoDto> list =new ArrayList<CMBusInfoDto>();
			list=busInfoService.getByOrderAndCode(MapUtils.getString(map,"orderNo"), MapUtils.getString(map,"code"));
			
			if(list==null){
				logger.info("getByOrderAndCode：----------list==null");
				return RespHelper.setFailDataObject(resp, list, "该订单编号和编码下无影像资料");
			}
			logger.info("getByOrderAndCode：----------length:"+list.size()+",code="+MapUtils.getString(map,"code")+",orderNo="+MapUtils.getString(map,"orderNo"));
			RespHelper.setSuccessDataObject(resp,list);
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failDataObject(null);
		}
		return resp;
	}
	
	
	
	/**
	 * 同步单个影响资料上传
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/busInfoTask")
	public RespDataObject<Map<String, Object>> busInfoTask(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			logger.info("单个影响资料上传Start-------");
			logger.info("orderNo="+map.get("orderNo")+"");
			Map<String, Object> parmMap =new HashMap<String, Object>();
			String orderNo=map.get("orderNo")+"";
			ProductDataDto productDataDto=new ProductDataDto();
			productDataDto.setTblDataName("tbl_cm_data");//表名
			productDataDto.setOrderNo(orderNo);
			//查询评估信息
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOAN.getCode());
			Map<String, Object>  load=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			parmMap.put("orderNo", orderNo);
			String subBankId=load.get("subBankId")+""==""?null:load.get("subBankId")+"";
			if(StringUtil.isNotEmpty(subBankId) && !"null".equals(subBankId)){
				parmMap.put("subBankId", load.get("subBankId"));
			}else{
				productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESS.getCode());
				Map<String, Object>  assess=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
				parmMap.put("subBankId", assess.get("subBankId"));
			}
			String code=map.get("typeId")+"";
			parmMap.put("code", code);
			parmMap.put("appNo", load.get("appNo")+"");
			logger.info("typeId:"+code +",appNo:"+load.get("appNo"));
			logger.info("参数打印：orderNO:"+orderNo+",code="+parmMap.get("code")+",appNo="+parmMap.get("appNo")+",subBankId="+parmMap.get("subBankId"));
			resp = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_ORDERTASK, parmMap, Map.class);
			if(!RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
				logger.info("推送影像资料返回："+resp.getMsg());
				return RespHelper.setFailDataObject(resp,null,resp.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failDataObject(null);
		}
		logger.info("单个影响资料上传end-------");
		return resp;
	}
	
	/**
	 * 匹配客户经理信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/getMatchCusManager")
	public RespDataObject<Map<String, Object>> getMatchCusManager(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
//			Map<String, Object> param =new HashMap<String, Object>();
//			param.put("tranCode", "C009");
//			param.put("appNo", "0000001197");
//			Map<String, Object> maps=new HashMap<String, Object>();
//			maps.put("HouseUse", "住宅");
//			maps.put("Total_Amt", "3875000.00");
//			maps.put("Balance1", "3048197.00");
//			maps.put("Balance2", "0.00");
//			maps.put("Result", "Y");
//			maps.put("Reason", "");
////			maps.put("NOTATION", "测试");
////			maps.put("FEEDTYPE", "0");
//			param.put("contextMap", maps);
//			HttpUtil httpUtil = new HttpUtil();
//			httpUtil.getObject(Enums.MODULAR_URL.CREDIT.toString(), "credit/product/data/cm/result/add", param,Map.class);
			resp=assessService.matchCusManager(map);
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failDataObject(null);
		}
		return resp;
	}
	
	/**
	 * 查询还款计划
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectRepaymentPlan")
	public RespDataObject<Map<String, Object>> selectRepaymentPlan(@RequestBody Map<String, Object> params) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			ProductDataDto productDataDto=new ProductDataDto();
			productDataDto.setTblDataName("tbl_cm_data");//表名
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOANSUCCESS.getCode());
			productDataDto.setOrderNo(params.get("orderNo").toString());
			Map<String, Object>  assessMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			//查询还款方式
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOAN.getCode());
			Map<String, Object>  loadMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("loanType", 1);
			jsonMap.put("syTotal", MapUtils.getDoubleValue(assessMap, "lendingAmount"));  //放款金额
			jsonMap.put("syRates", MapUtils.getDoubleValue(assessMap, "rate"));    //利率
			jsonMap.put("months", MapUtils.getDoubleValue(assessMap, "loanLimit"));  //贷款期限
			if(StringUtil.isNotEmpty(MapUtils.getString(assessMap, "maturityDate"))){
				jsonMap.put("firstDate", MapUtils.getString(assessMap, "maturityDate"));// 到期日
			}else{
//				String createTime=MapUtils.getString(assessMap, "createTime");
//				String[] time= createTime.split("-");
//				Integer t=Integer.parseInt(time[1]);
//				int tt=(t+1)*1;
//				String date=createTime.replace("-"+t+"-", "-"+tt+"-");
				jsonMap.put("firstDate", MapUtils.getString(assessMap, "createTime"));// 完成时间
				assessMap.put("maturityDate", MapUtils.getString(assessMap, "createTime").replaceAll("-", "").subSequence(0, 8));
			}
			
			jsonMap.put("calcType", 1);
			String result = "";
			String appUrl = ConfigUtil.getStringValue(Constants.LINK_ANJBO_APP_URL,ConfigUtil.CONFIG_LINK);
			System.out.println(appUrl);
			System.out.println(jsonMap);
			if (MapUtils.getString(loadMap, "repaymentWayCode").equals("等额本息")) {
				result = HttpUtil.jsonPost(appUrl + "/mortgage/loan/samebx", jsonMap);
			} else {
				result = HttpUtil.jsonPost(appUrl + "/mortgage/loan/samebj", jsonMap);
			}
			System.out.println(result);
			Map<String, Object> retMap = JsonUtil.jsonToMap(MapUtils.getString(JsonUtil.jsonToMap(result), "data"));
			retMap.put("maturityDate", MapUtils.getString(assessMap, "maturityDate"));
			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
}
