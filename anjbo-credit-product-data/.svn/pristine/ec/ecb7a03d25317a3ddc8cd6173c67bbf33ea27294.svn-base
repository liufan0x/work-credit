package com.anjbo.controller.cm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.Enums.CCBTranNoEnum;
import com.anjbo.common.Enums.LoanProgressEnum;
import com.anjbo.common.ReqMappingConstants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.service.ProductDataBaseService;
import com.anjbo.service.ProductFlowBaseService;
import com.anjbo.service.ProductListBaseService;
import com.anjbo.service.cm.LoanLendingResultService;
import com.anjbo.service.cm.LoanService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;

/**
 * 任务
 * @author limh limh@anjbo.com   
 * @date 2017-3-6 下午03:08:46
 */
@Component
public class ResultTaskController extends BaseController{
	private static Log log = LogFactory.getLog(ResultTaskController.class);
	@Resource LoanService loanService;
	@Resource ProductFlowBaseService productFlowBaseService;
	@Resource LoanLendingResultService loanLendingResultService;
	@Resource ProductListBaseService productListBaseService;
	@Resource ProductDataBaseService productDataBaseService;
	/**
	 * C004 个人贷款合约状态查询
	 * 每1小时执行一次
	 */
	@Scheduled(cron = "0 0 0,2,4,6,8,10,12,14,16,18,20,22 * * ?")
//	@Scheduled(cron = "0 0/3 * * * ?")
	public void scheduleCCBLoanStatusQuery() {
		log.info("scheduleCCBLoanStatusQuery start=============================");
		List<Map<String, Object>> parmt=productListBaseService.findTask();
		for(Map<String, Object> map:parmt){
			try {
				Map<String,Object> parmMap=new HashMap<String,Object>();
 				JSONObject  jasonObject = JSONObject.fromObject(map.get("data").toString());
				Map<String, Object> objMap = (Map)jasonObject;
				String orderNo = map.get("orderNo").toString();
				
				//获取贷款信息appNo  并调用C004接口
				ProductDataDto productDataDto=new ProductDataDto();
				productDataDto.setTblDataName("tbl_cm_data");
				productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOAN.getCode());
				productDataDto.setCreateUid(Constants.JKUID);
				productDataDto.setOrderNo(orderNo);
				ProductDataDto  pdto=productDataBaseService.selectProductDataBaseDto(productDataDto);
				
				//查询订单渠道经理
				productDataDto.setTblDataName("tbl_cm_list");
				Map<String, Object> m = productListBaseService.selectProductListBaseByOrderNo(productDataDto);
				
				productDataDto.setTblDataName("tbl_cm_data");
				Map<String, Object>  loanMap=pdto.getData();
				objMap.put("subBankId", loanMap.get("subBankId"));
				objMap.put("appNo", loanMap.get("appNo"));
				parmMap.put("obj", objMap);
				parmMap.put("tranNo",CCBTranNoEnum.C004.getCode());
				RespDataObject<Map<String, Object>> respCCB = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
						ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
				if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
					continue;
				}
				Map<String, Object> resultMap = respCCB.getData();
				String progressDes = MapUtils.getString(resultMap, "StateNm");//状态描述
				String progressNo = MapUtils.getString(resultMap, "AR_StCd","");//合约状态
				//查询流水表最新流程
				Map<String,Object> flowmap=productFlowBaseService.selectProductFlowBase(orderNo);
				String progress=Enums.CM_FLOW_PROCESS_CONTRAST.getNameByCode(flowmap.get("currentProcessId").toString());
				log.info("合约状态：orderNo="+orderNo+",  jh-progressNo="+progressNo+"----kg-progressId="+progress);
				if(StringUtils.isEmpty(progressNo)||progressNo.equals(progress)){//进度未更新
					continue;
				}
				//保险判断。 流程已到预约取证，建行返回审批通过时，不做处理   // 2：取证抵押 建行不同步   需特殊处理
				if(("0305".equals(progress) && "0304".equals(progressNo)) || ("0305".equals(progressNo) && "预约抵押".equals(progress)) || ("0305".equals(progressNo) && "取证抵押".equals(progress)) ){
					continue;
				}
				
				//-----公共数据----------
				//渠道经理电话
				productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_ASSESS.getCode());
				ProductDataDto  assessMap=productDataBaseService.selectProductDataBaseDto(productDataDto);
				Map<String, Object> assess = assessMap.getData();
				UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(assess.get("channelManagerUid").toString());
				//发送短信电话
				String mobile=userDto.getMobile();
				//申请人姓名
				String name=map.get("name").toString();
				//下一处理人uid
				String uid=MapUtils.getString(m, "channelManagerUid");
				//userDto=baseController.CommonDataUtil.getUserDtoByUidAndMobile(uid);
				String nextName=MapUtils.getString(m, "channelManagerName");
				//建行
				UserDto jhDto=CommonDataUtil.getUserDtoByUidAndMobile(Constants.JKUID);
				//流水表信息
				Map<String,Object> flowMap=new HashMap<String,Object>(); 
				flowMap.put("tblDataName", Constants.CM_FLOW_TBL_NAME);
				flowMap.put("createUid", Constants.JKUID);
				flowMap.put("handleUid", Constants.JKUID);
				flowMap.put("orderNo", orderNo);
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time=format.format(new Date());
				//0301-客户经理审核
				if(LoanProgressEnum.AUDIT_CUST_MANAGER.getCode().equals(progressNo)){		
					//添加流水表
					log.info("task:JH-----客户经理审核Start------------");
					flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.MANAGERAUDIT.getCode()); //当前流程
					flowMap.put("nextProcessId", Enums.CM_FLOW_PROCESS.STORESRESERVE.getCode()); //下一流程
					productFlowBaseService.insertProductFlowBase(flowMap);
					
					//修改tbl_cm_list列表流程
					flowMap.put("processId", Enums.CM_FLOW_PROCESS.STORESRESERVE.getCode());
					flowMap.put("state", "待审批前材料准备");
					flowMap.put("updateUid", Constants.JKUID);
					flowMap.put("currentHandlerUid", Constants.JKUID);
					flowMap.put("currentHandler", jhDto.getName());
					productListBaseService.updateProcessId(flowMap);
					
					//保存tbl_cm_date流程数据
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_MANAGERAUDIT.getCode());
					Map<String,Object>dataMap=new HashMap<String, Object>();
					dataMap.put("appNo",loanMap.get("appNo"));
					dataMap.put("createTime",time);
					productDataDto.setData(dataMap);
					productDataBaseService.updateProductDataBase(productDataDto);
					log.info("task:JH-----客户经理审核完成------------");
				}
				//0302-审批前材料准备
				if(LoanProgressEnum.AUDIT_PRE_INFO_INIT.getCode().equals(progressNo)){	
					log.info("task:JH-----审批前材料准备Start------------");
					//添加流水表
					flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.STORESRESERVE.getCode());
					flowMap.put("nextProcessId", Enums.CM_FLOW_PROCESS.AUDIT.getCode());
					productFlowBaseService.insertProductFlowBase(flowMap);
					
					//修改tbl_cm_list列表流程
					flowMap.put("processId", Enums.CM_FLOW_PROCESS.AUDIT.getCode());
					flowMap.put("state", "待审批");
					flowMap.put("updateUid", Constants.JKUID);
					flowMap.put("currentHandlerUid", Constants.JKUID);
					flowMap.put("currentHandler", jhDto.getName());
					productListBaseService.updateProcessId(flowMap);
					
					//保存tbl_cm_date流程数据
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_STORESRESERVE.getCode());
					Map<String,Object>dataMap=new HashMap<String, Object>();
					dataMap.put("appNo",loanMap.get("appNo"));
					dataMap.put("createTime",time);
					productDataDto.setData(dataMap);
					productDataBaseService.updateProductDataBase(productDataDto);
					log.info("task:JH-----审批前材料准备完成------------");
				}
				//0303-正在审批
				else if(LoanProgressEnum.AUDITING.getCode().equals(progressNo)){
					log.info("task:JH-----正在审批  无操作------------");
				}
				//0304-审批已通过
				else if(LoanProgressEnum.AUDIT_SUCCESS.getCode().equals(progressNo)){
					log.info("task:JH-----审批通过Start------------");
					//添加流水表
					flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.AUDITSUCCESS.getCode());
					flowMap.put("nextProcessId", Enums.CM_FLOW_PROCESS.TRANSFER.getCode()); 
					productFlowBaseService.insertProductFlowBase(flowMap);
					
					//修改tbl_cm_list列表流程
					flowMap.put("processId", Enums.CM_FLOW_PROCESS.TRANSFER.getCode());
					flowMap.put("state", "待准备房产过户和抵押");
					flowMap.put("updateUid", Constants.JKUID);
					flowMap.put("currentHandlerUid", Constants.JKUID);
					flowMap.put("currentHandler", jhDto.getName());
					flowMap.put("appShowValue1", "审批通过");
					flowMap.put("appShowValue2", time);
					productListBaseService.updateProcessId(flowMap);
					
					//获取审批返回信息
					parmMap.put("tranNo",CCBTranNoEnum.C015.getCode());
					respCCB = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
					         ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
					if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
						continue;
					}
					resultMap = respCCB.getData();
					//贷款信息  -查询还款方式
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOAN.getCode());
					Map<String, Object>  pMap=productDataBaseService.selectProductDataBaseDto(productDataDto).getData(); 
					
					//保存tbl_cm_date流程数据 - 获取审批成功返回数据
					Map<String,Object> lendingMap=new HashMap<String, Object>();
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_AUDITSUCCESS.getCode()); 
					lendingMap.put("appNo",loanMap.get("appNo"));
//					double amt=MapUtils.getDoubleValue(resultMap, "LOANAMT",0d);
//					amt=(amt/10000)*1;
					lendingMap.put("loanamt", resultMap.get("LOANAMT"));  //贷款金额
					lendingMap.put("loantrm", resultMap.get("LOANTRM")); //贷款期限
					lendingMap.put("repaymentWay", pMap.get("repaymentWay"));
					lendingMap.put("repaymentWayCode", pMap.get("repaymentWayCode"));
					lendingMap.put("createTime",time);
					productDataDto.setData(lendingMap);
					log.info("审批通过信息："+lendingMap);
					productDataBaseService.updateProductDataBase(productDataDto);
					//发送短信
					AmsUtil.smsSendNoLimit(mobile, Constants.SMS_LOANAUDITSUCCESS,name);
					log.info("task:JH-----审批已通过end------------");
				}
				//03AF-审批未通过
				else if(LoanProgressEnum.AUDIT_FAIL.getCode().equals(progressNo)){
					//增加流水
					log.info("task-JH---progressNo:"+progressNo+" ----定时任务审批未通过Start");
					flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.AUDITFAIL.getCode());
					flowMap.put("nextProcessId", Enums.CM_FLOW_PROCESS.AUDITFAIL.getCode()); //下一流程
					productFlowBaseService.insertProductFlowBase(flowMap);
					
					//修改tbl_cm_list列表流程
					flowMap.put("processId", Enums.CM_FLOW_PROCESS.AUDITFAIL.getCode());
					flowMap.put("state", "审批失败");
					flowMap.put("updateUid", Constants.JKUID);
					flowMap.put("currentHandlerUid", "-");
					flowMap.put("currentHandler", "-");
					flowMap.put("appShowValue1", "审批失败");
					flowMap.put("appShowValue2", "暂无.");
					flowMap.put("appShowValue3", time);
					productListBaseService.updateProcessId(flowMap);
					
					//tbl_cm_data表
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_AUDITFAIL.getCode());
					Map<String,Object>dataMap=new HashMap<String, Object>();
					dataMap.put("appNo",loanMap.get("appNo"));
					dataMap.put("feedSign","暂无.");
					dataMap.put("result","N");
					dataMap.put("createTime",time);
					productDataDto.setData(dataMap);
					productDataBaseService.updateProductDataBase(productDataDto);
					log.info("task-JH---progressNo:"+progressNo+" ----定时任务审批未通过-未发生短信end");
					//发送短信
//					AmsUtil.smsSendNoLimit(mobile, Constants.SMSCOMEFROM_LOANAUDITFAIL,name,progressDes);
				}
				//0305-准备房产过户和抵押
				else if(LoanProgressEnum.LENDING_MORTGAGE.getCode().equals(progressNo)){
					log.info("task:JH-----准备房产过户和抵押start------------");
					//添加流水表
					flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.TRANSFER.getCode());
					flowMap.put("nextProcessId", Enums.CM_FLOW_PROCESS.RESERVEMORTGAGE.getCode());
					productFlowBaseService.insertProductFlowBase(flowMap);
					
					//修改tbl_cm_list列表流程
					flowMap.put("processId", Enums.CM_FLOW_PROCESS.RESERVEMORTGAGE.getCode());
					flowMap.put("state", "待预约抵押登记");
					flowMap.put("updateUid", Constants.JKUID);
					flowMap.put("currentHandlerUid",uid);
					flowMap.put("currentHandler", nextName);
					productListBaseService.updateProcessId(flowMap);
					
					//保存tbl_cm_date流程数据
					productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_TRANSFER.getCode());
					Map<String,Object>dataMap=new HashMap<String, Object>();
					dataMap.put("appNo",loanMap.get("appNo"));
					dataMap.put("createTime",time);
					productDataDto.setData(dataMap);
					productDataBaseService.updateProductDataBase(productDataDto);
					log.info("task:JH-----房产过户和抵押end------------");
					
				}
				//0399-贷款已发放  - 03ZZ-贷款终止
				else if(LoanProgressEnum.LENDING_SUCCESS.getCode().equals(progressNo) || LoanProgressEnum.LENDING_FAIL.getCode().equals(progressNo)){
					//获取放款结果信息
					log.info("task:jh-----------放款Start--------------");
					parmMap.put("tranNo",CCBTranNoEnum.C011.getCode());
					respCCB = new HttpUtil().getRespDataObject(Constants.LINK_CM_CCB_URL,
							ReqMappingConstants.CREDIT_THIRD_API_WSHELPER_POST, parmMap, Map.class);
					if(!RespStatusEnum.SUCCESS.getCode().equals(respCCB.getCode())){
						log.info("task:jh-----调用C011接口异常返回:-----"+respCCB);
						continue;
					}
					resultMap = respCCB.getData();
					log.info("task:jh-----C011放款结果:-----"+respCCB);
					//保存tbl_cm_data表
					if(LoanProgressEnum.LENDING_SUCCESS.getCode().equals(progressNo)){  //放款成功
					    productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOANSUCCESS.getCode()); 
					}else if(LoanProgressEnum.LENDING_FAIL.getCode().equals(progressNo)){ //放款失败
						productDataDto.setTblName(Enums.CM_CONFIG_PAGE_TAB.TBL_CM_LOANFAIL.getCode()); 
					}
					Map<String,Object> lendingMap=new HashMap<String, Object>();
					lendingMap.put("appNo",loanMap.get("appNo"));
					lendingMap.put("isLending",CharUtils.toChar(MapUtils.getString(resultMap, "LOAN_FLG","Y")));//是否已放款
					lendingMap.put("lendingAccount",MapUtils.getString(resultMap, "FP_FUND_ACCT"));//放款账号
					lendingMap.put("custName",MapUtils.getString(resultMap, "CUST_NAME"));//客户姓名
					lendingMap.put("certificateType",MapUtils.getString(resultMap, "CERT_TYP"));//证件类型
					lendingMap.put("certificateNo",MapUtils.getString(resultMap, "CERT_ID"));//证件号码
					lendingMap.put("loanAccount",MapUtils.getString(resultMap, "RT_ACCT_NUM"));//贷款账号
//					double amt=MapUtils.getDoubleValue(resultMap, "LOANAMT",0d);
//					amt=(amt/10000)*1;
//					lendingMap.put("lendingAmount",amt);//放款金额
					lendingMap.put("lendingAmount",MapUtils.getDoubleValue(resultMap, "LOANAMT",0d));//放款金额
					double rate=MapUtils.getDoubleValue(resultMap, "RATE",0d);
					rate=rate*4.9;
					lendingMap.put("rate",rate);//利率
					lendingMap.put("lendingDate",MapUtils.getString(resultMap, "FADAT","-"));//放款日
					lendingMap.put("loanLimit",MapUtils.getString(resultMap, "MIS_RT_TERM_INCR"));//贷款期限
					lendingMap.put("maturityDate",MapUtils.getString(resultMap, "EXPIRE","-"));//到期日
					lendingMap.put("repaymentWay",MapUtils.getString(resultMap, "RFN_STY"));//还款方式
					lendingMap.put("reason",MapUtils.getString(resultMap, "REASON","-"));//原因
					lendingMap.put("createTime",time);
					productDataDto.setData(lendingMap);
					log.info("放款结果信息："+lendingMap);
					productDataBaseService.updateProductDataBase(productDataDto);
					
					if(LoanProgressEnum.LENDING_SUCCESS.getCode().equals(progressNo)){  //放款成功
						//添加流水表
						flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.LOANSUCCESS.getCode());
						flowMap.put("nextProcessId", Enums.CM_FLOW_PROCESS.WANJIE.getCode()); //下一流程
						productFlowBaseService.insertProductFlowBase(flowMap);
						//修改tbl_cm_list列表流程
						flowMap.put("processId", "wanjie");
						flowMap.put("state", "已完结");
						flowMap.put("updateUid", Constants.JKUID);
						flowMap.put("currentHandlerUid", "-");
						flowMap.put("currentHandler", "-");
						flowMap.put("appShowValue1", time);
						productListBaseService.updateProcessId(flowMap);
						//发送短信
						AmsUtil.smsSendNoLimit(mobile, Constants.SMS_LOANLENDINGSUCCESS,name);
						log.info("task:jh-----------放款成功--------------");
					}else if(LoanProgressEnum.LENDING_FAIL.getCode().equals(progressNo)){ //放款失败
						//增加流水
						flowMap.put("currentProcessId", Enums.CM_FLOW_PROCESS.LOANFAIL.getCode());
						flowMap.put("nextProcessId", Enums.CM_FLOW_PROCESS.LOANFAIL.getCode()); //下一流程
						flowMap.put("updateUid", Constants.JKUID);
						flowMap.put("currentHandlerUid", "-");
						flowMap.put("currentHandler", "-");
						productListBaseService.updateProcessId(flowMap);
						productFlowBaseService.insertProductFlowBase(flowMap);
						//修改tbl_cm_list列表流程
						flowMap.put("processId", "loanFail");
						flowMap.put("state", "放款失败");
						productListBaseService.updateProcessId(flowMap);
//						//发送短信
						AmsUtil.smsSendNoLimit(mobile, Constants.SMS_LOANLENDINGFAIL,name,MapUtils.getString(resultMap, "REASON"));
						log.info("task:jh---放款失败-------原因："+MapUtils.getString(resultMap, "REASON"));
					}
					log.info("task:jh---放款完成");
				}else{
					continue;
				}
				//保存订单基本信息到redis
				setOrderBaseInfo("10000",orderNo);
			} catch (Exception e) {
				log.info(String.format("订单编号为%s的操作有异常",map.get("orderNo")));
				e.printStackTrace();
			}
		}
		log.info("scheduleCCBLoanStatusQuery end=============================");
	}
	
}
