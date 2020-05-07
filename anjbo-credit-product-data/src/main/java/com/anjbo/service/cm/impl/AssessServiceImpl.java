package com.anjbo.service.cm.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.anjbo.bean.cm.AssessDto;
import com.anjbo.bean.cm.AssessResultDto;
import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.Enums.BankEnum;
import com.anjbo.common.Enums.CCBTranNoEnum;
import com.anjbo.common.ReqMappingConstants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.cm.AssessMapper;
import com.anjbo.service.ProductDataBaseService;
import com.anjbo.service.ProductListBaseService;
import com.anjbo.service.cm.AssessService;
import com.anjbo.service.cm.ProgressService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.IdcardUtils;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @author chenzm    
 * @date 2017-8-23 下午05:29:08
 */

@Service
public class AssessServiceImpl implements AssessService {
	protected final Log log = LogFactory.getLog(this.getClass());  
	@Resource ProgressService progressService;
	@Resource AssessMapper assessMapper;
	@Resource ProductDataBaseService productDataBaseService;
	@Resource ProductListBaseService productListBaseService;
	
	/**
	 * 新增建行评估记录
	 * 调用建行C008接口
	 * @return
	 */
	@Override
	@Transactional
	public RespDataObject<Map<String,Object>> addAssess(Map<String,Object> params) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			ProductDataDto productDataDto=new ProductDataDto();
			productDataDto.setTblDataName("tbl_cm_data");//表名
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESS.getCode());
			productDataDto.setOrderNo(params.get("orderNo").toString());
			ProductDataDto  baseMap=productDataBaseService.selectProductDataBaseDto(productDataDto);
			Map<String, Object> map = baseMap.getData();
			//身份证校验
			if("A".equals(map.get("certificateType"))&&
					!IdcardUtils.validateCard(map.get("certificateNo").toString())){
				return RespHelper.setFailDataObject(resp, null,"证件号码(买方)输入有误，请检查");
			}
			//业主身份证校验
			if("A".equals(map.get("ownerCertificateType"))&&
					!IdcardUtils.validateCard(map.get("ownerCertificateNo").toString())){
				return RespHelper.setFailDataObject(resp, null,"业主证件号码输入有误，请检查");
			}
			//房产证校验
			String estateTypeCode=map.get("estateType")+"";
			if("房地产权证书".equals(estateTypeCode)){
				map.put("estateType", 0);
				if(!StringUtils.isNumeric(map.get("estateNo").toString())|| (map.get("estateNo").toString().length()!=10 && map.get("estateNo").toString().length()!=7)){
					return RespHelper.setFailDataObject(resp, null,"房产证号只能为7位或10位数字，请检查");
				}
				map.put("yearNo", "");
			}else{
				map.put("estateType", 1);
				if(!StringUtils.isNumeric(map.get("estateNo").toString())||map.get("estateNo").toString().length()!=7){
					return RespHelper.setFailDataObject(resp, null,"不动产证号只能为7位数字，请检查");
				}
				int index=estateTypeCode.indexOf("2");
				String yearNo=estateTypeCode.substring(index,estateTypeCode.length()-1);
				map.put("yearNo", yearNo);
			}
			/**非建设银行评估流程**/
			if(BankEnum.CCB.getCode()!=Integer.valueOf(map.get("bankId").toString())){
				return tzcAssess(map);
			}
			String actPrice=map.get("actPrice")+"";
			if(StringUtil.isNotEmpty(actPrice) && "" !=actPrice){
				if(StringUtil.isNotEmpty(actPrice) && !"null".equals(actPrice)){
					DecimalFormat    df   = new DecimalFormat("######0.00");   
					double actPrices=Double.parseDouble(actPrice);
					map.put("actPrice", df.format(actPrices));
				}
			}else{
				map.put("actPrice", null);
			}
			
			String index=null;
			//查档（1、通过业主姓名+房产信息验证；2、通过业主身份证+房产信息验证；）
			String identityNo = MapUtils.getString(map, "ownerCertificateNo");
			if(!queryArchive(map,identityNo,MapUtils.getString(params, "createUid")))
				return RespHelper.setFailDataObject(resp, null,"未查到物业信息，请检查业主证件号码和房产信息是否输入正确。");
			identityNo = MapUtils.getString(map, "ownerName");
			if(!queryArchive(map,identityNo,MapUtils.getString(params, "createUid")))
				return RespHelper.setFailDataObject(resp, null,"未查到物业信息，请检查业主姓名是否输入正确。");
			/*建行评估*/
			//-----------先推送图片资料获取索引Start--------
			Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("url", map.get("estateImg"));
			if(MapUtils.getString(iMap, "url").contains(",")){
				return RespHelper.setFailDataObject(resp, null,"提交失败，房产证只能传一张。");
			}
			iMap.put("orderNo", params.get("orderNo"));
			iMap.put("subBankId", map.get("subBankId"));			
			RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(
					Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_BUSINFOSYNC, iMap, Map.class);
			if("SUCCESS".equals(respc.getCode())){//需要判断返回结果
				 index=(String) respc.getData().get("INDEX");
			}else{
				log.info("评估推送房产证信息："+respc.getMsg());
				return RespHelper.setFailDataObject(respc, null,"房产证图片上传失败！");
			}
			//-----推送图片end-----------------------
			//-----推送评估信息start-------------------			
			map.put("index", index);
			Map<String,Object> parmMap=new HashMap<String,Object>();
			parmMap.put("tranNo",CCBTranNoEnum.C008.getCode());
			
			parmMap.put("obj", map);
			RespDataObject<Map<String, Object>> respCCB = new HttpUtil().getRespDataObject(
					Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
			if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
				log.info("推送评估信息返回："+respCCB);
				return RespHelper.setFailDataObject(resp,null,respCCB.getMsg());
			}		
			//-----推送评估信息end-------------------
			
			//修改基础表
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESS.getCode());
			baseMap=productDataBaseService.selectProductDataBaseDto(productDataDto);
			Map<String,Object>dataMap=baseMap.getData();
			dataMap.put("appNo",MapUtils.getString(respCCB.getData(),"Appno"));
			productDataDto.setData(dataMap);
			productDataBaseService.updateProductDataBase(productDataDto);
			//初始化卖方信息1
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ROUNDTURN.getCode());
			JSONObject roundTurnData = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject sellData = new JSONObject();
			sellData.put("name", MapUtils.getString(map, "ownerName"));
			sellData.put("certificateTypeCode",  MapUtils.getString(map, "ownerCertificateTypeCode"));
			sellData.put("certificateType",  MapUtils.getString(map, "ownerCertificateType"));
			sellData.put("certificateNo",  MapUtils.getString(map, "ownerCertificateNo"));
			jsonArray.add(sellData);
			roundTurnData.put("sellList", jsonArray);
			System.out.println("评估信息："+params);
			System.out.println("初始化买卖双方信息："+roundTurnData.toString());
			productDataDto.setDataStr(roundTurnData.toString());
			productDataBaseService.insertProductDataBase(productDataDto);
			//评估基本信息入库
			AssessDto assesstDto = assessMapper.selectOrderNoByAssessOrderNo(params.get("orderNo").toString());
			UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(map.get("channelManagerUid").toString());
			if(assesstDto==null){
				map.put("appNo",MapUtils.getString(respCCB.getData(),"Appno"));
				map.put("status",Enums.AssessStatusEnum.ASSESSING.getCode());
				map.put("agentMobile", userDto.getMobile());  //合作方经办人手机号-渠道经理
				map.put("agentName", map.get("channelManagerName"));
				map.put("orderNo", params.get("orderNo"));
				map.put("uid", params.get("updateUid"));
				assessMapper.addAssess(map);	
			}else{
				assesstDto.setAppNo(MapUtils.getString(respCCB.getData(),"Appno"));
				assessMapper.updateAssessAppNo(assesstDto);
			}
			//发送短信
//			productDataDto.setTblDataName("tbl_cm_list");
//		    Map<String, Object>listmap=productListBaseService.selectProductListBaseByOrderNo(productDataDto);
//			AmsUtil.smsSendNoLimit(userDto.getMobile(), Constants.SMSCOMEFROM_ASSESSAPPLY,listmap.get("source"),map.get("ownerName"));
			//提交评估
			map.put("state", "待评估");
			map.put("currentProcessId",Enums.CM_FLOW_PROCESS.ASSESS.getCode()); //当前流程-提交评估
			map.put("nextProcessId",Enums.CM_FLOW_PROCESS.ASSESSSUCCESS.getCode()); //下一流程 评估
			map.put("currentHandlerUid", Constants.JKUID);
			RespHelper.setSuccessDataObject(resp,map);
		} catch (Exception e) {
			log.error("申请评估失败"+e.toString(),e);
			return RespHelper.failDataObject(null);
		}
		return resp;
	}	
	
	/**
	 * 同致诚评估
	 * @param assess
	 * @return
	 * @throws Exception
	 */
	private RespDataObject<Map<String,Object>> tzcAssess(Map<String,Object> map) throws Exception{
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		AssessResultDto ar = new AssessResultDto();
//		if(StringUtils.isNotEmpty(map.get("propertyId").toString())&&
//				StringUtils.isNotEmpty(map.get("buildingId").toString())&&
//						StringUtils.isNotEmpty(map.get("roomId").toString())){
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("type","2");//评估公司（2同致诚）
//			params.put("uid", map.get("uid"));
//			params.put("propertyName", map.get("propertyName"));
//			params.put("propertyId", map.get("propertyId"));
//			params.put("buildingName", map.get("buildingName"));
//			params.put("buildingId", map.get("buildingId"));
//			params.put("roomName", map.get("roomName"));
//			params.put("roomId", map.get("roomId"));
//			params.put("area", map.get("area"));
//			params.put("isGetNetPriceTax",1);//是否获取净值/税费（1不获取 ）
//			params.put("source", "4");//来源（4云按揭）
//			params.put("device", map.get("device"));
//			params.put("version", map.get("version"));
//			//String toolsUrl = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.TOOLS_URL.toString());
//			String toolsUrl=Enums.MODULAR_URL.TOOl.toString();
//			String result = HttpUtil.jsonPost(toolsUrl+"/tools/enquiry/v/createEnquiry", params);
//			logger.debug("调用工具询价接口[createEnquiry]结果"+result);
//			if(!result.equals("error")){
//				JSONObject jsonObj = JSONObject.fromObject(result);
//				if("SUCCESS".equals(jsonObj.getString("code"))){
//					JSONObject jsonObjData = jsonObj.getJSONObject("data");
//					String enquiryId = jsonObjData.getString("enquiryid");
//					params = new HashMap<String, Object>();
//					params.put("enquiryId", enquiryId);
//					result = HttpUtil.jsonPost(toolsUrl+"/tools/enquiry/v/getEnquiryInfo", params);
//					logger.debug("调用工具询价接口[getEnquiryInfo]结果"+result);
//					jsonObj = JSONObject.fromObject(result);
//					if("SUCCESS".equals(jsonObj.getString("code"))){
//						jsonObjData = jsonObj.getJSONObject("data");
//						JSONArray jsonObjDataList = jsonObjData.getJSONArray("enquiryReportList");
//						JSONObject jsonObjDataListData = jsonObjDataList.getJSONObject(0);
//						Double totalPrice = jsonObjDataListData.getDouble("totalPrice");//评估总价
//						Double netPrice = jsonObjDataListData.getDouble("netPrice");//评估净值
//						ar.setTotalAmount(totalPrice);
//						ar.setNetDeedTax(netPrice);
//					}
//				}
//			}
//		}
//		progressService.addOrderProgressFlow(map.get("orderNo").toString(), CMOrderProgressEnum.ASSESSSUCCESS.getCode());
		resp.setData(map);
		return RespHelper.setSuccessDataObject(resp,map);
	}

	/**
	 * 买卖双方信息
	 * 调用建行C024接口
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> busInfo(Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo=map.get("orderNo").toString();
			ProductDataDto productDataDto=new ProductDataDto();
			productDataDto.setTblDataName("tbl_cm_data");//表名
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ROUNDTURN.getCode());
			productDataDto.setOrderNo(orderNo);
			Map<String, Object>  pMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			//买方信息
			if(pMap.containsKey("buyList")){
				JSONArray jsonArray = JSONArray.fromObject(pMap.get("buyList"));
				List<Map<String, Object>> buyList = jsonArray;
				for (Map<String, Object> bugMap : buyList) {
					if("A".equals(bugMap.get("certificateType"))&&
							!IdcardUtils.validateCard(bugMap.get("certificateNo").toString())){
						return RespHelper.setFailDataObject(resp, null,"证件号码(买方)输入有误，请检查");
					}
				}
			}
			//卖方信息
			if(pMap.containsKey("sellList")){
				JSONArray jsonArray1 = JSONArray.fromObject(pMap.get("sellList"));
				List<Map<String, Object>> sellList = jsonArray1;
				for (Map<String, Object> bugMap : sellList) {
					if("A".equals(bugMap.get("certificateType"))&&
							!IdcardUtils.validateCard(bugMap.get("certificateNo").toString())){
						return RespHelper.setFailDataObject(resp, null,"证件号码(卖方)输入有误，请检查");
					}
				}
			}
			AssessDto assessDto=assessMapper.selectOrderNoByAssessOrderNo(orderNo);
			Map<String,Object> parmMap=new HashMap<String,Object>();
			parmMap.put("tranNo",CCBTranNoEnum.C024.getCode());
			//基本信息
			pMap.put("subBankId", assessDto.getSubBankId());
			pMap.put("appNo", assessDto.getAppNo());
			pMap.put("estateNo", assessDto.getEstateNo());
			pMap.put("custManagerMobile", assessDto.getCustManagerMobile());
			pMap.put("estateType", assessDto.getEstateType());
			pMap.put("yearNo", assessDto.getYearNo()==""?null:assessDto.getYearNo());
			pMap.put("actPrice", assessDto.getActPrice());
			parmMap.put("obj", pMap);
			RespDataObject<Map<String, Object>> respCCB = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
			if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
				log.info("推送买卖双方信息返回："+respCCB);
				return RespHelper.setFailDataObject(resp,null,respCCB.getMsg());
			}
			cusQuery(map);  //查询是否存量客户
			Map<String, Object> status = new  HashMap<String, Object>();//MapUtils.getMap(respCCB.getData(),"RESULT");
			status.put("state", "待提交申请按揭");
			status.put("currentProcessId",Enums.CM_FLOW_PROCESS.ROUNDTURN.getCode()); //当前流程-买卖方
			status.put("nextProcessId",Enums.CM_FLOW_PROCESS.SUBMORTGAGE.getCode()); //下一流程 申请按揭
			RespHelper.setSuccessDataObject(resp,status);
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failDataObject(null);
		}
		return resp;
	}

	/**
	 * 存量客户查询
	 * 调用建行C005接口
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> cusQuery(Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo=map.get("orderNo").toString();
			ProductDataDto productDataDto=new ProductDataDto();
			productDataDto.setTblDataName("tbl_cm_data");//表名
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESS.getCode());
			productDataDto.setOrderNo(orderNo);
			Map<String, Object>  baseMap=productDataBaseService.selectProductDataBase(productDataDto);
			JSONObject  jasonObject = JSONObject.fromObject(baseMap.get("data").toString());
			Map<String, Object> pMap = (Map)jasonObject;
			Map<String, Object> listMap=(Map<String, Object>) pMap.get("buyList");
			
			
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ROUNDTURN.getCode());
			Map<String, Object>  roundMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			List<Map<String, Object>> listmap=(List<Map<String, Object>>) roundMap.get("buyList");
			Map<String, Object> rMap=listmap.get(0);
			rMap.put("subBankId", pMap.get("subBankId"));
			Map<String,Object> parmMap=new HashMap<String,Object>();
			parmMap.put("tranNo",CCBTranNoEnum.C005.getCode());
			parmMap.put("obj", rMap);
			RespDataObject<Map<String, Object>> respCCB = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
			if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
				log.info("推送查询存量客户信息返回："+respCCB.getMsg());
				return RespHelper.setFailDataObject(resp,null,respCCB.getMsg());
			}
			Map<String, Object> status = respCCB.getData();
			if("SUCCESS".equals(respCCB.getCode()) &&"Y".equals(status.get("Stk_Cst_Ind"))){
				String showText="<span class='text-danger'>此客户为建行存量客户，只需完善贷款信息和影像资料。</span>";
				productDataDto.setCreateUid(Constants.JKUID);
				productDataDto.setUpdateUid(Constants.JKUID);
				productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_CUSTOMER.getCode());
				productDataDto.setShowText(showText);
				productDataBaseService.updateProductDataBase(productDataDto);
				log.info("此客户为存量客户");
			}
			RespHelper.setSuccessDataObject(resp,status);
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failDataObject(null);
		}
		return resp;
	}

	/**
	 * 提交银行审核客户信息
	 * 调用建行C001,c002,c027接口
	 * @return
	 * @param 客户信息,贷款信息,影像资料
	 */
	@Override
	public RespDataObject<Map<String, Object>> cusInfo(Map<String,Object> map) {		
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		Map<String,Object> parmMap=new HashMap<String,Object>();
		try {
			String orderNo=map.get("orderNo").toString();
			ProductDataDto productDataDto=new ProductDataDto();
			productDataDto.setTblDataName("tbl_cm_data");//表名
			productDataDto.setOrderNo(orderNo);
			//查询评估信息
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESS.getCode());
			Map<String, Object>  order=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			//查询客户信息
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_CUSTOMER.getCode());
			ProductDataDto  baseMap=productDataBaseService.selectProductDataBaseDto(productDataDto);
			Map<String, Object> objMap=baseMap.getData();
			//查询买卖双方客户姓名
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ROUNDTURN.getCode());
			Map<String, Object>  roundMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			List<Map<String, Object>> listmap=(List<Map<String, Object>>) roundMap.get("buyList");
			Map<String, Object> rMap=listmap.get(0);
			productDataDto.setTblDataName("tbl_cm_data");//表名
			DecimalFormat df = new DecimalFormat("0.00"); 
			//==========非存量客户保存客户信息===================
			if(StringUtil.isEmpty(baseMap.getShowText())){
				log.info("客户信息："+objMap);
				if(objMap.get("maritalCode")!=null&&objMap.get("personIncome")!=null&&objMap.get("familyIncome")!=null){
					String maritalCode = MapUtils.getString(objMap, "maritalCode");
					Double personIncome = MapUtils.getDouble(objMap, "personIncome");
					Double familyIncome = MapUtils.getDouble(objMap, "familyIncome");
					if("已婚".equals(maritalCode)){
						if(personIncome>familyIncome){
							RespHelper.setFailRespStatus(resp, "当婚姻为已婚时，家庭月收入需大于等于本人月收入");
							return resp;
						}
					}else if("未婚".equals(maritalCode)){
						if(!MapUtils.getString(objMap, "personIncome").equals(MapUtils.getString(objMap, "familyIncome"))){
							RespHelper.setFailRespStatus(resp, "当婚姻为未婚时，家庭月收入需等于本人月收入");
							return resp;
						}
					}
				}
				if(MapUtils.isNotEmpty(order)){
					objMap.put("subBankId", order.get("subBankId"));
					objMap.put("agentName", order.get("channelManagerName"));   //合作方经办人姓名
					UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(order.get("channelManagerUid").toString());
					objMap.put("agentMobile", userDto.getMobile());
					objMap.put("jhphone", order.get("custManagerMobile"));   //经办人手机号
				}
				parmMap.put("tranNo",CCBTranNoEnum.C001.getCode());
				String personIncomes=objMap.get("personIncome")+""==""?null:objMap.get("personIncome")+"";
				
				if(StringUtil.isNotEmpty(personIncomes) && !"null".equals(personIncomes)){ //本人月收入
					Double personIncome=Double.valueOf(objMap.get("personIncome").toString());
					String per=df.format(personIncome);
					objMap.put("personIncome", per);
				}
				String familyIncomes=objMap.get("familyIncome")+""==""?null:objMap.get("familyIncome")+"";
				if(StringUtil.isNotEmpty(familyIncomes) && !"null".equals(familyIncomes)){//家庭月收入
					Double personIncome=Double.valueOf(objMap.get("familyIncome").toString());
					String per=df.format(personIncome);
					objMap.put("familyIncome", per);
				}
				String familyExpends=objMap.get("familyExpend")+""==""?null:objMap.get("familyExpend")+"";
				if(StringUtil.isNotEmpty(familyExpends) && !"null".equals(familyExpends)){ //家庭月支出
					Double personIncome=Double.valueOf(objMap.get("familyExpend").toString());
					String per=df.format(personIncome);
					objMap.put("familyExpend", per);
				}
				String familyAssetss=objMap.get("familyAsset")+""==""?null:objMap.get("familyAssets")+"";
				if(StringUtil.isNotEmpty(familyAssetss) && !"null".equals(familyAssetss)){  //家庭总资产
					Double personIncome=Double.valueOf(objMap.get("familyAssets").toString());
					String per=df.format(personIncome);
					objMap.put("familyAssets", per);
				}
				String familyLiabilitiess=objMap.get("familyLiabilities")+""==""?null:objMap.get("familyLiabilities")+"";
				if(StringUtil.isNotEmpty(familyLiabilitiess) && !"null".equals(familyLiabilitiess)){  //家庭总负债
					Double personIncome=Double.valueOf(objMap.get("familyLiabilities").toString());
					String per=df.format(personIncome);
					objMap.put("familyLiabilities", per);
				}
				String personPaymentss=objMap.get("personPayments")+""==""?null:objMap.get("personPayments")+"";
				if(StringUtil.isNotEmpty(personPaymentss) && !"null".equals(personPaymentss)){ //个人月还款额
					Double personIncome=Double.valueOf(objMap.get("personPayments").toString());
					String per=df.format(personIncome);
					objMap.put("personPayments", per);
				}
				String unitTime=objMap.get("unitTime")+""==""?null:objMap.get("unitTime")+"";
				if(StringUtil.isNotEmpty(unitTime) && !"null".equals(unitTime)){ //进入单位时间
					objMap.put("unitTime", objMap.get("unitTime").toString().replaceAll("-",""));
				}
				objMap.put("dateBirth", objMap.get("dateBirth").toString().replaceAll("-",""));
				parmMap.put("obj", objMap);
				RespDataObject<Map<String, Object>> respCCB = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
				ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
				if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
					log.info("推送客户信息返回："+respCCB);
					return RespHelper.setFailDataObject(resp,null,respCCB.getMsg());
				}
			}
			//============推送贷款信息=====================
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOAN.getCode());
			ProductDataDto  proDto=productDataBaseService.selectProductDataBaseDto(productDataDto);
			Map<String, Object>  pMap=proDto.getData();
			if(order!=null){
				UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(order.get("channelManagerUid").toString());
				pMap.put("agentMobile", userDto.getMobile());  //合作方经办人手机号-渠道经理
				pMap.put("agentName", order.get("channelManagerName"));
				pMap.put("subBankId", order.get("subBankId"));
				pMap.put("custManagerMobile", order.get("custManagerMobile"));
				pMap.put("assessAppNo", order.get("appNo"));
			}
			parmMap.put("tranNo",CCBTranNoEnum.C002.getCode());
			Double firstPayment= 0.00;
			if(MapUtils.isNotEmpty(pMap)){
				Double loanAmount=0.00;
				if(StringUtils.isNotEmpty(pMap.get("loanAmount").toString())){  //贷款金额
					 loanAmount=Double.valueOf(pMap.get("loanAmount").toString());
					String per=df.format(loanAmount);
					pMap.put("loanAmount", per);
				}
				if(StringUtils.isNotEmpty(pMap.get("firstPayment").toString())){  
					firstPayment=Double.valueOf(pMap.get("firstPayment").toString());
					String per=df.format(firstPayment);
					pMap.put("firstPayment", per);
				}
				String payeeBankName=pMap.get("payeeBankName")+""==""?null:pMap.get("payeeBankName")+"";
				if(StringUtil.isNotEmpty(payeeBankName) && !"null".equals(payeeBankName)){
					if("建设银行".equals(pMap.get("payeeBankName").toString())){
						pMap.put("payeeIsOurBank", "1");  //建行
					}else{
						pMap.put("payeeIsOurBank", "0");  //非建行
					}
				}
				String loanUsedCode=pMap.get("loanUsedCode").toString();
				if("购买二手住房".equals(loanUsedCode)){
					 pMap.put("productCode", "2022");  //住房贷款
			    }else if("购买二手商铺".equals(loanUsedCode)){
					 pMap.put("productCode", "2042");  //商业贷款         
			    }
				String assCertificateNo=pMap.get("assCertificateNo")+""==""?null:pMap.get("assCertificateNo")+"";
				if(StringUtil.isNotEmpty(assCertificateNo) && !"null".equals(assCertificateNo)){
					//身份证校验
					if("A".equals(pMap.get("assCertificateType"))&&
							!IdcardUtils.validateCard(pMap.get("assCertificateNo").toString())){
						return RespHelper.setFailDataObject(resp, null,"关联客户证件号码输入有误，请检查");
					}
				}
			}
			//评估返回数据
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESSSUCCESS.getCode());
			Map<String, Object>  resultMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			pMap.put("totalAmount", resultMap.get("totalAmount"));
			pMap.put("custName", rMap.get("name")); 
			pMap.put("certificateType", rMap.get("certificateType")); 
			pMap.put("certificateNo", rMap.get("certificateNo")); 
			parmMap.put("obj", pMap);
			RespDataObject<Map<String, Object>> respCCB = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
			if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
				log.info("推送贷款信息返回："+respCCB);
				return RespHelper.setFailDataObject(resp,null,respCCB.getMsg());
			}
			//tbl_cm_loan添加appNo数据
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOAN.getCode());
			objMap=proDto.getData();
			objMap.put("appNo", respCCB.getData().get("Appno"));
			objMap.put("subBankId", order.get("subBankId"));
			productDataDto.setData(objMap);
			productDataBaseService.updateProductDataBase(productDataDto);
			//保存APP 
			AssessDto assesstDto = assessMapper.selectOrderNoByAssessAppNo(respCCB.getData().get("Appno").toString());
			//用于记录最近APPNo,贷款信息每次提交建行都会返回新的APPNo
			if(assesstDto==null){
				map.put("appNo", respCCB.getData().get("Appno"));
				UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(order.get("channelManagerUid").toString());
				map.put("agentMobile", userDto.getMobile());  //合作方经办人手机号-渠道经理
				map.put("agentName", order.get("channelManagerName"));
				map.put("orderNo", orderNo);
				map.put("subBankId", order.get("subBankId"));
				map.put("custName", rMap.get("name"));
				map.put("certificateType",rMap.get("certificateType"));
				map.put("certificateNo", rMap.get("certificateNo"));
				String estateTypeCode=order.get("estateType")+"";
				if("房地产权证书".equals(estateTypeCode) || "0".equals(estateTypeCode)){
					map.put("estateType", 0);
					map.put("yearNo", "");
				}else{
					map.put("estateType", 1);
					int index=estateTypeCode.indexOf("2");
					String yearNo=estateTypeCode.substring(index,estateTypeCode.length()-1);
					map.put("yearNo", yearNo);
				}
//				map.put("estateType", order.get("estateType"));
//				map.put("yearNo", order.get("yearNo"));
				map.put("estateNo", order.get("estateNo"));
				map.put("custManagerName", order.get("custManagerName"));
				map.put("custManagerMobile", order.get("custManagerMobile"));
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time=format.format(new Date());
				map.put("createTime",time);
				map.put("status", "4");
				map.put("source", "1");
				assessMapper.addAssess(map);	
			}else{
				assesstDto.setStatus(4);
				assesstDto.setAppNo(MapUtils.getString(respCCB.getData(),"Appno"));
				assessMapper.updateAssessAppNo(assesstDto);
			}
			//=====修改列表贷款金额========
			Map<String,Object> flowMap=new HashMap<String,Object>(); 
			flowMap.put("orderNo", orderNo);
			flowMap.put("borrowingAmount",pMap.get("loanAmount"));
			productListBaseService.updataLoanAmount(flowMap);
			//=============推送影像资料=====================	
			parmMap =new HashMap<String, Object>();
			parmMap.put("orderNo", orderNo);
			parmMap.put("appNo", respCCB.getData().get("Appno"));
			parmMap.put("subBankId", order.get("subBankId"));
			parmMap.put("code", "001"); //首次推送全部
			RespDataObject<Map<String, Object>> imgresp = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_ORDERTASK, parmMap, Map.class);
			if(!RespStatusEnum.SUCCESS.getCode().equals(imgresp.getCode())){
				log.info("推送影像资料返回："+imgresp);
				return RespHelper.setFailDataObject(resp,null,imgresp.getMsg());
			}
			Map<String, Object> status = new  HashMap<String, Object>();//MapUtils.getMap(respCCB.getData(),"RESULT");
			status.put("currentProcessId",Enums.CM_FLOW_PROCESS.SUBMORTGAGE.getCode()); //当前流程-申请按揭
			status.put("nextProcessId",Enums.CM_FLOW_PROCESS.MANAGERAUDIT.getCode()); //下一流程 客户经理审核
			status.put("state", "待客户经理审核");
			status.put("currentHandlerUid", Constants.JKUID);
			status.put("appShowValue1", firstPayment+"万");//首付款金额
			RespHelper.setSuccessDataObject(resp,status);
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failDataObject(null);
		}
		return resp;
	}
	
	/**
	 * 预约抵押
	 * 调用建行C025接口
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> preMortgage(Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo=map.get("orderNo").toString();
			ProductDataDto productDataDto=new ProductDataDto();
			productDataDto.setTblDataName("tbl_cm_data");//表名
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_RESERVEMORTGAGE.getCode());
			productDataDto.setOrderNo(orderNo);
			Map<String, Object>  pMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
//			JSONObject  jasonObject = JSONObject.fromObject(baseMap.get("data").toString());
//			Map<String, Object> pMap = (Map)jasonObject;
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOAN.getCode());
			Map<String, Object>  loanMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			if(loanMap!=null){
					pMap.put("appNo",loanMap.get("appNo"));
				String subBankId=loanMap.get("subBankId")+""==""?null:loanMap.get("subBankId")+"";
				if(StringUtil.isNotEmpty(subBankId) && !"null".equals(subBankId)){
					pMap.put("subBankId", loanMap.get("subBankId"));
				}else{
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESS.getCode());
					Map<String, Object>  assess=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
					pMap.put("subBankId", assess.get("subBankId"));
				}
			}
			Map<String,Object> parmMap=new HashMap<String,Object>();
			parmMap.put("tranNo",CCBTranNoEnum.C025.getCode());
			String time=pMap.get("reserveDate").toString().replaceAll("-", "");
			pMap.put("reserveDate",time);
			parmMap.put("obj", pMap);
			RespDataObject<Map<String, Object>> respCCB = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
			if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
				log.info("推送预约抵押信息返回："+respCCB.getMsg());
				return RespHelper.setFailDataObject(resp,null,respCCB.getMsg());
			}
			Map<String, Object> status = new  HashMap<String, Object>();//MapUtils.getMap(respCCB.getData(),"RESULT");
			status.put("currentProcessId",Enums.CM_FLOW_PROCESS.RESERVEMORTGAGE.getCode()); //当前流程-预约抵押
			status.put("nextProcessId",Enums.CM_FLOW_PROCESS.FORENSICSMORTGAGE.getCode()); //下一流程取证抵押
			status.put("state", "待取证抵押");
			status.put("appShowValue1", time+" "+MapUtils.getString(pMap, "reserveTimeCode"));
			status.put("appShowValue2", MapUtils.getString(pMap, "reserveAddressCode"));
			RespHelper.setSuccessDataObject(resp,status);
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failDataObject(null);
		}
		return resp;
	}

	/**
	 * 取证抵押
	 * 调用建行C026接口
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> takeMortgage(
			Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String orderNo=map.get("orderNo").toString();
			ProductDataDto productDataDto=new ProductDataDto();
			productDataDto.setTblDataName("tbl_cm_data");//表名
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_FORENSICSMORTGAGE.getCode());
			productDataDto.setOrderNo(orderNo);
			Map<String, Object>  pMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
//			JSONObject  jasonObject = JSONObject.fromObject(baseMap.get("data").toString());
//			Map<String, Object> pMap = (Map)jasonObject;
//			AssessDto assessDto=assessMapper.selectOrderNoByAssessOrderNo(orderNo);
			productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOAN.getCode());
			Map<String, Object>  loanMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
			if(loanMap!=null){
				pMap.put("appNo",loanMap.get("appNo"));
				String subBankId=loanMap.get("subBankId")+""==""?null:loanMap.get("subBankId")+"";
				if(StringUtil.isNotEmpty(subBankId) && !"null".equals(subBankId)){
					pMap.put("subBankId", loanMap.get("subBankId"));
				}else{
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESS.getCode());
					Map<String, Object>  assess=productDataBaseService.selectProductDataBaseDto(productDataDto).getData();
					pMap.put("subBankId", assess.get("subBankId"));
				}
			}
			//-----------先推送图片资料获取索引Start--------
			String index=null;
			Map<String,Object> iMap=new HashMap<String,Object>();
			iMap.put("url",pMap.get("newPropertyLicensePic").toString());
			if(MapUtils.getString(iMap, "url").contains(",")){
				return RespHelper.setFailDataObject(resp, null,"提交失败，新房产证照片只能传一张。");
			}
			if(pMap.get("mortgageReceiptPic")!=null&&MapUtils.getString(pMap, "mortgageReceiptPic").contains(",")){
				return RespHelper.setFailDataObject(resp, null,"提交失败，取证抵押照片只能传一张。");
			}
			iMap.put("orderNo", orderNo);
			iMap.put("subBankId", pMap.get("subBankId"));			
			RespDataObject<Map<String, Object>> respc = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_BUSINFOSYNC, iMap, Map.class);
			if("SUCCESS".equals(respc.getCode())){//需要判断返回结果
				 index=(String) respc.getData().get("INDEX");
			}else{
				return RespHelper.setFailDataObject(respc, null,"取证抵押图片上传失败！");
			}
			//-----推送图片end-----------------------
			pMap.put("index", index);
			Map<String,Object> parmMap=new HashMap<String,Object>();
			parmMap.put("tranNo",CCBTranNoEnum.C026.getCode());
			parmMap.put("obj", pMap);
			RespDataObject<Map<String, Object>> respCCB = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
			if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
				log.info("推送取证抵押信息返回："+respCCB.getMsg());
				return RespHelper.setFailDataObject(resp,null,respCCB.getMsg());
			}
			Map<String, Object> status = new  HashMap<String, Object>();//MapUtils.getMap(respCCB.getData(),"RESULT");
			status.put("currentProcessId",Enums.CM_FLOW_PROCESS.FORENSICSMORTGAGE.getCode()); //当前流程-取证抵押
			status.put("nextProcessId",Enums.CM_FLOW_PROCESS.LOAN.getCode()); //下一流程等待放款
			status.put("state", "待放款");
			status.put("currentHandlerUid", Constants.JKUID);
			status.put("appShowValue1", sdf.format(new Date()));//抵押时间
			ProductDataDto productData=new ProductDataDto();
			productData.setTblDataName("tbl_cm_data");//表名
			//新房产证号
			productData.setOrderNo(orderNo);
			productData.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESS.getCode());
			Map<String, Object>  assess=productDataBaseService.selectProductDataBaseDto(productData).getData();
			String appShowValue2;
			if(MapUtils.getString(assess, "estateType").contains("2015")){
				appShowValue2 = "不动产证-2015-"+assess.get("estateNo");
			}else if(MapUtils.getString(assess, "estateType").contains("2016")){
				appShowValue2 = "不动产证-2016-"+assess.get("estateNo");
			}else if(MapUtils.getString(assess, "estateType").contains("2017")){
				appShowValue2 = "不动产证-2017-"+assess.get("estateNo");
			}else{
				appShowValue2 = "房地产权证-"+assess.get("estateNo");
			}
			status.put("appShowValue2", appShowValue2);//新房产证
			RespHelper.setSuccessDataObject(resp,status);
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failDataObject(null);
		}
		return resp;
	}

	/**
	 * 匹配客户经理信息
	 * 调用建行C006接口
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> matchCusManager(Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			Map<String,Object> parmMap=new HashMap<String,Object>();
			parmMap.put("tranNo",CCBTranNoEnum.C006.getCode());
			parmMap.put("obj", map);
			resp= new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
			if(!RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
				return RespHelper.setFailDataObject(resp,null,resp.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.failDataObject(null);
		}
		return resp;
	}
	/**
	 * 查档检查房产信息是否有效
	 * @param params
	 * @return
	 */
	public boolean queryArchive(Map<String,Object> params,String identityNo,String uid){
		//先查档
		Map<String,Object> p = new HashMap<String, Object>();
		p.put("uid", uid);
		if(params.get("mobile")!=null){
			p.put("uMobile", MapUtils.getString(params, "mobile"));
		}
		p.put("type", 1);
		p.put("estateType", MapUtils.getString(params, "estateTypeCode"));
		p.put("estateNo", MapUtils.getString(params, "estateNo"));
		p.put("identityNo", identityNo);
		log.info("参数：房产证号"+MapUtils.getString(params, "estateNo")+" ,"+"身份证号"+identityNo);
		String toolsUrl = ConfigUtil.getStringValue(Constants.LINK_ANJBO_TOOl_URL,ConfigUtil.CONFIG_LINK);
		toolsUrl += "/tools/archive/v/addArchive";
		String dataMsg="";
		try {
			p.put("noLimitCountHour",true);
			p.put("noLimitCount", true);
			String statusString = HttpUtil.jsonPost(toolsUrl, p);
			String archiveId=null;
			JSONObject jsonObject = JSONObject.fromObject(statusString);
			log.info("云按揭提单，查档结果："+jsonObject.getString("msg"));
			if("相同房号请在记录详情再次查询".equals(jsonObject.getString("msg"))){
				archiveId =jsonObject.getJSONObject("data").getString("archiveId");
				log.info("查档id，archiveId："+archiveId);
				//通过查档id查档
				toolsUrl = ConfigUtil.getStringValue(Constants.LINK_ANJBO_TOOl_URL,ConfigUtil.CONFIG_LINK)+ "/tools/archive/v/getArchiveById";
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("noLimitCountHour",true);
				param.put("noLimitCount", true);
				param.put("id", archiveId);
				String result = HttpUtil.jsonPost(toolsUrl, param);
				JSONObject jsonObj = JSONObject.fromObject(result);
				log.info("查询查档记录结果："+jsonObj);
				if(jsonObj.containsKey("data")&&null!=jsonObj.get("data")&&!jsonObj.get("code").equals("FAIL")){
					JSONObject tmp = jsonObj.getJSONObject("data");
					dataMsg=String.valueOf(tmp.getString("message"));
					log.info("云按揭提单，相同记录查档结果："+dataMsg);
				}
			}else if(!"相同房号请在记录详情再次查询".equals(jsonObject.getString("msg"))&&"SUCCESS".equals(jsonObject.getString("code"))||
					"SAME_ROOM_NUMBER".equals(jsonObject.getString("code"))||
					"SYSTEM_UPDATE".equals(jsonObject.getString("code"))){
				archiveId =jsonObject.getJSONObject("data").getString("archiveId");
				if(jsonObject.getString("msg")!=""){
					dataMsg = jsonObject.getString("msg");
				}
				if("SUCCESS".equals(jsonObject.getString("code"))){
					dataMsg = jsonObject.getJSONObject("data").getString("msg");
					log.info("云按揭提单，再次查档结果："+dataMsg);
				}
			}else{
				dataMsg="没有找到匹配的房产记录";
			}
			//校验dataMsg
			if(StringUtils.isBlank(dataMsg)||dataMsg.contains(Enums.PropertyStatusEnum.L5.getName())||dataMsg.contains("没有找到")){
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	
}
