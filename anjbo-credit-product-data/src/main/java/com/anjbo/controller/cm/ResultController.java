package com.anjbo.controller.cm;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.anjbo.bean.cm.AssessDto;
import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.Enums.CCBTranNoEnum;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.controller.BaseController;
import com.anjbo.service.ProductDataBaseService;
import com.anjbo.service.ProductFlowBaseService;
import com.anjbo.service.ProductListBaseService;
import com.anjbo.service.cm.AssessResultService;
import com.anjbo.service.cm.AssessService;
import com.anjbo.service.cm.ProgressService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;

/**
 * 建行反馈
 * @author limh limh@anjbo.com   
 * @date 2017-1-2 下午10:14:08
 */
@Controller
@RequestMapping("/credit/product/data/cm/result")
public class ResultController extends BaseController{
	protected final Log log = LogFactory.getLog(this.getClass());  

	@Resource AssessService assessService;
	@Resource AssessResultService assessResultService;
	@Resource ProductFlowBaseService productFlowBaseService;
	@Resource ProgressService progressService;
	@Resource ProductDataBaseService productDataBaseService;
	@Resource ProductListBaseService productListBaseService;
	
	/**
	 * 添加反馈结果
	 * @return
	 */
	//@SuppressWarnings("unchecked")
	@ResponseBody 
	@RequestMapping(value = "/add")
	public RespStatus add(HttpServletRequest request,@RequestBody Map<String,Object> param){
		RespStatus resp = new RespStatus();
		String tranCode = MapUtils.getString(param, "tranCode");
		String appNo = MapUtils.getString(param, "appNo");
		try {
			AssessDto dto = assessResultService.selectOrderNoByAssessAppNo(appNo); 
			if(dto==null){
				return RespHelper.setFailRespStatus(resp, "交易记录不存在");
			}
			Map<String,Object> contextMap = MapUtils.getMap(param, "contextMap");
			String orderNo = dto.getOrderNo();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=format.format(new Date());
			//查询下一处理人
			ProductDataDto productDataDto=new ProductDataDto();
			productDataDto.setTblDataName("tbl_cm_list");
			productDataDto.setOrderNo(dto.getOrderNo());
			Map<String, Object> orderMap=productListBaseService.selectProductListBaseByOrderNo(productDataDto);
			String uid=orderMap.get("channelManagerUid").toString();
			UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(uid);
			if(CCBTranNoEnum.C009.getCode().equals(tranCode)){//评估结果反馈
				String houseUse = MapUtils.getString(contextMap, "HouseUse");//房屋用途
				double totalAmount = MapUtils.getDoubleValue(contextMap, "Total_Amt",0d);//全值,评估总值,更新到列表
				double netDeedTax = MapUtils.getDoubleValue(contextMap, "Balance1",0d);//扣契税后的余额
				double netAllTax = MapUtils.getDoubleValue(contextMap, "Balance2",0d);//扣全税的余额
				String result = MapUtils.getString(contextMap, "Result");//评估是否成功
				String reason = MapUtils.getString(contextMap, "Reason","-");//原因
				//tbl_cm_data表
				productDataDto=new ProductDataDto();
				productDataDto.setCreateUid(Constants.JKUID);
				productDataDto.setUpdateUid(Constants.JKUID);
				productDataDto.setTblDataName("tbl_cm_data");
				if("Y".equals(result)){
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESSSUCCESS.getCode());
				}else{
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESSFAIL.getCode());
				}
				productDataDto.setOrderNo(orderNo);
				Map<String,Object>dataMap=new HashMap<String, Object>();
				dataMap.put("appNo",appNo);
				dataMap.put("result",result);
				dataMap.put("reason",reason);
				dataMap.put("houseUse",houseUse);
				dataMap.put("totalAmount",totalAmount);
				dataMap.put("netDeedTax",netDeedTax);
				dataMap.put("netAllTax",netAllTax);
				dataMap.put("createTime",time);
				
				//=====查询是否有成功记录Start========
				ProductDataDto prDataDto=new ProductDataDto();
				prDataDto.setTblDataName("tbl_cm_data");//表名
				prDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESSSUCCESS.getCode()); 
				prDataDto.setOrderNo(dto.getOrderNo());
				ProductDataDto  baseMap=productDataBaseService.selectProductDataBaseDto(productDataDto);
				//=====查询是否有成功记录end========
				
				if(baseMap==null){  //没有则插入
					productDataDto.setData(dataMap);
					productDataBaseService.updateProductDataBase(productDataDto);
					//添加流水
					Map<String,Object> flowMap=new HashMap<String,Object>(); 
					flowMap.put("tblDataName", Constants.CM_FLOW_TBL_NAME);
					flowMap.put("createUid", Constants.JKUID);//此处需要获取uid
					flowMap.put("handleUid", Constants.JKUID);
					flowMap.put("orderNo", orderNo);
	//				dto.setAgentMobile("15112347841");
					//发送短信
					if("Y".equals(result)){
						//评估成功
						flowMap.put("nextProcessId", Enums.CM_FLOW_PROCESS.ROUNDTURN.getCode()); //下一流程 买卖双方
						flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.ASSESSSUCCESS.getCode());
						productFlowBaseService.insertProductFlowBase(flowMap);
						//修改订单流程
						flowMap.put("processId", Enums.CM_FLOW_PROCESS.ROUNDTURN.getCode());
						flowMap.put("state", "待完善买卖双方信息");
						AmsUtil.smsSendNoLimit(dto.getAgentMobile(), Constants.SMS_ASSESSSUCCESS,dto.getCustName(),totalAmount);
	//							new BigDecimal(totalAmount).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
						log.info("JH-result-回调：评估成功-------");
						flowMap.put("currentHandlerUid", uid);
						flowMap.put("currentHandler", userDto.getName());
						flowMap.put("pgType", 1); //评估成功修改列表总值
						flowMap.put("totalPrice",totalAmount);
						DecimalFormat df = new DecimalFormat("#,###");
						flowMap.put("appShowValue3",df.format(totalAmount)+"元");
						flowMap.put("netPrice", netDeedTax);
						log.info("JH-result-回调：评估完成 修改列表总值-------");
					}else{
						//评估失败
						flowMap.put("nextProcessId", Enums.CM_FLOW_PROCESS.ASSESSFAIL.getCode()); //下一流程 买卖双方
						flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.ASSESSFAIL.getCode());
						productFlowBaseService.insertProductFlowBase(flowMap);
						//修改订单流程
						flowMap.put("processId", Enums.CM_FLOW_PROCESS.ASSESSFAIL.getCode());
						flowMap.put("state", "评估失败");
						AmsUtil.smsSendNoLimit(dto.getAgentMobile(), Constants.SMS_ASSESSFAIL,dto.getCustName());
						log.info("JH-result-回调：评估失败--------");
						flowMap.put("currentHandlerUid", "-");
						flowMap.put("currentHandler", "-");
					}
					flowMap.put("updateUid", Constants.JKUID);
					productListBaseService.updateProcessId(flowMap);
				}else{
					//线下修改-保存历史记录
					Map<String, Object> map = baseMap.getData();
					List<Map<String, Object>> list = null;
					if(map.containsKey("assessHistoryList")){
						list = (List<Map<String, Object>>) map.get("assessHistoryList");
						map.remove("assessHistoryList");
					}else{
						list=new ArrayList<Map<String,Object>>();
					}
					list.add(map);
					dataMap.put("assessHistoryList", list);
					
					productDataDto.setData(dataMap);
					productDataBaseService.updateProductDataBase(productDataDto);
				}
				//修改列表评估总值 评估净值
				Map<String,Object> listMap=new HashMap<String,Object>(); 
				listMap.put("totalPrice",totalAmount);
				listMap.put("netPrice", netDeedTax);
				DecimalFormat df = new DecimalFormat("#,###");
				listMap.put("appShowValue3",df.format(totalAmount)+"元");
				listMap.put("orderNo", orderNo);
				listMap.put("updateUid", Constants.JKUID);
				productListBaseService.updatePrice(listMap);
			}else if(CCBTranNoEnum.C012.getCode().equals(tranCode)){//意见反馈
					String feedSign = MapUtils.getString(contextMap, "NOTATION","-");//签署意见
					//tbl_cm_data表
					productDataDto=new ProductDataDto();
					productDataDto.setCreateUid(Constants.JKUID);
					productDataDto.setUpdateUid(Constants.JKUID);
					productDataDto.setTblDataName("tbl_cm_data");
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_AUDITFAIL.getCode());
					productDataDto.setOrderNo(orderNo);
					Map<String,Object>dataMap=new HashMap<String, Object>();
					dataMap.put("appNo",appNo);
					dataMap.put("feedSign",feedSign);
					String feedType=MapUtils.getString(contextMap, "FEEDTYPE");
					if("1".equals(feedType)){
						dataMap.put("feedType","补充申请信息");
					}else if("2".equals(feedType)){
						dataMap.put("feedType","补充影像资料");
					}else if("3".equals(feedType)){
						dataMap.put("feedType","补充申请信息和影像资料");
					}else if("4".equals(feedType)){
						dataMap.put("feedType","贷款拒绝通知");
					}else if("5".equals(feedType)){
						dataMap.put("feedType","贷款删除通知");
					}else if("6".equals(feedType)){
						dataMap.put("feedType","贷款退回通知");
					}
					dataMap.put("result","N");
					dataMap.put("createTime",time);
					dataMap.put("orderNo", orderNo);
					productDataDto.setData(dataMap);
					productDataBaseService.updateProductDataBase(productDataDto);
					
					//添加流水
					Map<String,Object> flowMap=new HashMap<String,Object>(); 
					flowMap.put("tblDataName", Constants.CM_FLOW_TBL_NAME);
					flowMap.put("createUid", Constants.JKUID);//此处需要获取uid
					flowMap.put("handleUid", Constants.JKUID);
					flowMap.put("orderNo", orderNo);
					flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.AUDITFAIL.getCode());//审批不通过
					String type=MapUtils.getString(contextMap, "FEEDTYPE");
					if("3".equals(type) || "1".equals(type) || "2".equals(type)){ //退回申请按揭
						flowMap.put("nextProcessId",  Enums.CM_FLOW_PROCESS.SUBMORTGAGE.getCode()); 
						flowMap.put("processId", Enums.CM_FLOW_PROCESS.SUBMORTGAGE.getCode());
						flowMap.put("state", "待重新提交申请按揭");
						flowMap.put("currentHandlerUid", uid);
						flowMap.put("currentHandler", userDto.getName());
						
					}else if("0".equals(type) ||"4".equals(type) || "5".equals(type) || "6".equals(type)){ 
						flowMap.put("nextProcessId",  Enums.CM_FLOW_PROCESS.AUDITFAIL.getCode()); 
						flowMap.put("processId", Enums.CM_FLOW_PROCESS.AUDITFAIL.getCode());
						flowMap.put("state", "审批失败");
						flowMap.put("currentHandlerUid", "-");
						flowMap.put("currentHandler", "-");
					}
					productFlowBaseService.insertProductFlowBase(flowMap);
					//修改订单流程
					flowMap.put("updateUid", Constants.JKUID);
					productListBaseService.updateProcessId(flowMap);
					//发送短信
					productDataDto=new ProductDataDto();
					productDataDto.setTblDataName("tbl_cm_list");
					productDataDto.setOrderNo(orderNo);
					Map<String, Object>  pdto=productListBaseService.selectProductListBaseByOrderNo(productDataDto);
					AmsUtil.smsSendNoLimit(dto.getAgentMobile(), Constants.SMS_LOANAUDITFAIL,feedSign,pdto.get("name"));
					log.info("JH-result-回调：意见反馈审批失败。意见类型"+type+"。签署意见："+tranCode);
			}
			RespHelper.setSuccessRespStatus(resp);
			//保存订单基本信息到redis
			setOrderBaseInfo("10000",orderNo);
		} catch (Exception e) {
			String error = e.getMessage();
			if (error != null
					&& error.contains("MySQLIntegrityConstraintViolationException")) {
				log.info(String.format("MySQLIntegrityConstraintViolationException：交易号%s已存在",appNo));
				return RespHelper.setFailRespStatus(resp, "该交易号已存在");
			}
			e.printStackTrace();
			resp = RespHelper.failRespStatus();
		}
		return resp;
	}
}
