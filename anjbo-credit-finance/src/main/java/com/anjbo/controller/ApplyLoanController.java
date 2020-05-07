package com.anjbo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.finance.FinanceLogDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderBaseHouseLendingDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ApplyLoanService;
import com.anjbo.service.FinanceLogService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;

/**
 * 待申请放款
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/finance/applyLoan/v")
public class ApplyLoanController extends BaseController{
	private Logger log = Logger.getLogger(getClass());
	
	@Resource
	private ApplyLoanService applyLoanService;
	@Resource
	private FinanceLogService financeLogService;
	
	 /**
     * 详情
     * @param request
     * @param orderNo
     * @return
     */
	@RequestMapping("init")
	@ResponseBody
	public  RespDataObject<ApplyLoanDto> init(HttpServletRequest request,@RequestBody ApplyLoanDto applyLoanDto){
		RespDataObject<ApplyLoanDto> resp=new RespDataObject<ApplyLoanDto>();
			try {
				ApplyLoanDto loanDto =applyLoanService.findByApplyLoan(applyLoanDto.getOrderNo());
				boolean isLoan=false;  //是否重新查询
				if(loanDto==null){
					loanDto=new ApplyLoanDto();
					isLoan=true;  //是否重新查询
				}
					loanDto.setOrderNo(applyLoanDto.getOrderNo());
					//借款信息（客户姓名，借款期限，借款金额，风控等级，费率，逾期费率，收费金额）
					OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
					orderBaseBorrowDto.setOrderNo(applyLoanDto.getOrderNo());
					RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
					OrderBaseBorrowDto baseBorrowDto=  obj.getData();
					if(baseBorrowDto!=null){
						if(loanDto.getBorrowerName()==null){
						  loanDto.setBorrowerName(baseBorrowDto.getBorrowerName());    //客户姓名
						}
//						if(baseBorrowDto.getIsChangLoan()==1){ //畅待
//							List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//							if(olist!=null && olist.size()>0){
//								JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//								loanDto.setLoanAmount(jsonObject.get("loanAmount")==null?0:Double.parseDouble(jsonObject.get("loanAmount").toString())); //借款金额
//								loanDto.setBorrowingDays(jsonObject.get("borrowingDays")==null?0:Integer.parseInt(jsonObject.get("borrowingDays").toString()));  //借款期限
//							}
//						}else{
							loanDto.setLoanAmount(baseBorrowDto.getLoanAmount());        //借款金额
							loanDto.setBorrowingDays(baseBorrowDto.getBorrowingDays());  //借款期限
//						}
					}
					if("04".equals(baseBorrowDto.getProductCode())  && isLoan==true){
						 //房抵贷
						OrderBaseHouseLendingDto lendingDto =new OrderBaseHouseLendingDto();
						lendingDto.setOrderNo(applyLoanDto.getOrderNo());
						RespDataObject<OrderBaseHouseLendingDto> doc=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/house/lending/v/query", lendingDto,OrderBaseHouseLendingDto.class);
						lendingDto=doc.getData();
						if(null!=lendingDto){
							loanDto.setLendingBankId(lendingDto.getLendingBankId()==null?0:lendingDto.getLendingBankId());
							loanDto.setLendingBankSubId(lendingDto.getLendingBankBranchId()==null?0:lendingDto.getLendingBankBranchId());
							loanDto.setBankName(lendingDto.getBankUserName());
							loanDto.setBankAccount(lendingDto.getBankAccount());
						}
					}else if(isLoan==true){ 
						DocumentsDto basics=new DocumentsDto();
						basics.setOrderNo(applyLoanDto.getOrderNo());
						RespDataObject<DocumentsDto> doc=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", basics,DocumentsDto.class);
						basics=doc.getData();
						if(basics!=null && basics.getForeclosureType()!=null && isLoan==true){
							loanDto.setLendingBankId(basics.getForeclosureType().getBankNameId()==null?0:basics.getForeclosureType().getBankNameId());
							loanDto.setLendingBankSubId(basics.getForeclosureType().getBankSubNameId()==null?0:basics.getForeclosureType().getBankSubNameId());
							loanDto.setBankName(basics.getForeclosureType().getBankCardMaster());
							loanDto.setBankAccount(basics.getForeclosureType().getBankNo());
						}
					}
				if("04".equals(baseBorrowDto.getProductCode())){  //房抵贷添加图片
					FinalAuditDto finalAuditDto =new FinalAuditDto();
					finalAuditDto.setOrderNo(applyLoanDto.getOrderNo());
					RespDataObject<FinalAuditDto> dataObject=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,FinalAuditDto.class);
					finalAuditDto=dataObject.getData();
					if(finalAuditDto!=null && null !=finalAuditDto.getPaymentType() && !"".equals(finalAuditDto.getPaymentType())){
						  if("凭抵押回执放款".equals(finalAuditDto.getPaymentType())){ //凭抵押回执放款
							    loanDto.setMortgageImgType(1);
						  }else if("凭抵押状态放款".equals(finalAuditDto.getPaymentType())){ //凭抵押状态放款
							    loanDto.setMortgageImgType(2);
						  }else{
							  loanDto.setMortgageImgType(0);
						  }
					}
				}	
				//获取银行-支行名称
				if(loanDto.getLendingBankId()!=null){
					BankDto bankDto =CommonDataUtil.getBankNameById(loanDto.getLendingBankId());
					loanDto.setLendingBank(bankDto==null ? "":bankDto.getName());
				}
				if(loanDto.getLendingBankSubId()!=null){
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(loanDto.getLendingBankSubId());
					loanDto.setLendingBankSub(subBankDto == null ?"":subBankDto.getName());
				}
				UserDto dto=getUserDto(request);  //获取用户信息
				loanDto.setCreateUid(dto.getUid());
				loanDto.setUpdateUid(dto.getUid());
				applyLoanService.addApplyLoan(loanDto);
				resp.setData(loanDto);
			} catch (Exception e2) {
				e2.printStackTrace();
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	
	/**
     * 详情
     * @param request
     * @param orderNo
     * @return
     */
	@RequestMapping("detail")
	@ResponseBody
	public  RespDataObject<Map<String, Object>> detail(HttpServletRequest request,@RequestBody ApplyLoanDto applyLoanDto){
		RespDataObject<Map<String, Object>> resp=new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			ApplyLoanDto loanDto = applyLoanService.findByApplyLoan(applyLoanDto.getOrderNo());
			try {
				//获取银行-支行名称
				if(loanDto.getLendingBankId()!=null ){
					BankDto bankDto =CommonDataUtil.getBankNameById(loanDto.getLendingBankId());
					loanDto.setLendingBank(bankDto==null ? "":bankDto.getName());
				}
				if(loanDto.getLendingBankSubId()!=null){
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(loanDto.getLendingBankSubId());
					loanDto.setLendingBankSub(subBankDto == null ?"":subBankDto.getName());
				}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			map.put("loanDto", loanDto);
			OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(loanDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			if(baseBorrowDto!=null && !"04".equals(baseBorrowDto.getProductCode())){
				FinanceLogDto logDto=new FinanceLogDto();
				logDto.setOrderNo(applyLoanDto.getOrderNo());
				logDto.setType(1);
				List<FinanceLogDto> logDtos=financeLogService.findByAll(logDto);
				map.put("logDtos", logDtos);
			}else{
				map.put("logDtos", new ArrayList<Object>());
			}
			
			resp.setData(map);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	/**
	 * 校验银行卡户名
	 * @param request
	 * @param response
	 * @param loanDto
	 * @return
	 */
 	@RequestMapping(value = "/findByName")
	public @ResponseBody
	RespStatus findByName(HttpServletRequest request,
			HttpServletResponse response,@RequestBody ApplyLoanDto loanDto) {
 		RespStatus status=new RespStatus();
 		try {
			status.setCode(RespStatusEnum.SYSTEM_ERROR.getCode());
			status.setMsg("你修改的银行卡户名异常，确定修改吗？");
			boolean isName=false;
			String name=loanDto.getBankName();
			//对比要件校验
			DocumentsDto basics=new DocumentsDto();
			basics.setOrderNo(loanDto.getOrderNo());
			RespDataObject<DocumentsDto> doc=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", basics,DocumentsDto.class);
			basics=doc.getData();
			if(null!=basics && basics.getForeclosureType()!=null){
				log.info("银行卡户名："+name+"---要件信息"+basics.getForeclosureType());
				if(name.equals(basics.getForeclosureType().getBankCardMaster())){
					isName=true;
					status.setCode(RespStatusEnum.SUCCESS.getCode());
					status.setMsg(RespStatusEnum.SUCCESS.getMsg());
					return status;
				}
			}
			
			//对比借款人
			OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(loanDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			if(baseBorrowDto!=null){
				 if(name.equals(baseBorrowDto.getBorrowerName())){
					isName=true;
					status.setCode(RespStatusEnum.SUCCESS.getCode());
					status.setMsg(RespStatusEnum.SUCCESS.getMsg());
					return status;
				 }
			}
			//对比还款专员
			DistributionMemberDto memberDto=new DistributionMemberDto();
			memberDto.setOrderNo(loanDto.getOrderNo());
			RespDataObject<DistributionMemberDto> mDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", memberDto,DistributionMemberDto.class);
			memberDto=mDto.getData();
			if(memberDto!=null){
				 String uid=memberDto.getForeclosureMemberUid();
				 UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(uid);
				 if(userDto!=null && name.equals(userDto.getName())){
					isName=true;
					status.setCode(RespStatusEnum.SUCCESS.getCode());
					status.setMsg(RespStatusEnum.SUCCESS.getMsg());
					return status;
				 }
			}
			//对比产权人
			OrderBaseHouseDto  orderBaseHouseDto=new OrderBaseHouseDto();
			orderBaseHouseDto.setOrderNo(loanDto.getOrderNo());
			RespDataObject<OrderBaseHouseDto> houstDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", orderBaseHouseDto,OrderBaseHouseDto.class);
			OrderBaseHouseDto hDto=  houstDto.getData();
			if(hDto!=null){
				JSONArray array= JSONArray.fromObject(hDto.getOrderBaseHousePropertyPeopleDto());
				for (Object object : array) {
					JSONObject obj1 = JSONObject.fromObject(object);
					if(name.equals(obj1.getString("propertyName"))){
						isName=true;
						break;
					}
				}
			}
			if(isName){
				status.setCode(RespStatusEnum.SUCCESS.getCode());
				status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setCode(RespStatusEnum.FAIL.getMsg());
		}
 		return status;
 	}
 	
	 /**
     * 添加信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/add")
	public @ResponseBody
	RespStatus add(HttpServletRequest request,
			HttpServletResponse response,@RequestBody ApplyLoanDto loanDto) {
    	RespStatus rd = new RespStatus();
		rd.setCode(RespStatusEnum.SUCCESS.getCode());
		rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		try {
			boolean isWithdraw = isWithdraw(loanDto.getOrderNo(),"applyLoan");
			if(isWithdraw){
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg("该订单已经被撤回");
				return rd;
			}
			 //判断当前节点
		    if(!isSubmit(loanDto.getOrderNo(), "applyLoan")){	
		    	OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
				orderBaseBorrowDto.setOrderNo(loanDto.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  obj.getData();
				if(baseBorrowDto==null){
					rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
					rd.setMsg("数据存在问题，请验证");
					return rd;
				}
				//============断接口金额是否大于授信额度Start===========================
				//获取合作机构配置
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("agencyId", baseBorrowDto.getCooperativeAgencyId());//合作机构ID
				AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
				if(agencyDto!=null && agencyDto.getCooperativeModeId()==1 && agencyDto.getCreditLimit()>0){//判断 是否有保证金  有则判断授信  无则继续流程
					log.info("兜底模式："+agencyDto.getCooperativeModeId());
					Double fxbl=agencyDto.getSurplusQuotaRemind()==null?0:agencyDto.getSurplusQuotaRemind();  //风险比例 （%）
					Double yjAmount=fxbl/100*agencyDto.getCreditLimit();  //风险比例（%） * 授信额度  = 预警额度
					Double  moneyCount=agencyDto.getCreditLimit() - agencyDto.getSurplusQuota() + baseBorrowDto.getLoanAmount();  //授信额度 - 剩余额度 + 借款金额 = 借款总额度 
					String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
					log.info("申请放款：借款总额度 ："+moneyCount+".授信额度:"+agencyDto.getCreditLimit()+".预警额度："+yjAmount);
					if(moneyCount>agencyDto.getCreditLimit()){  //借款总额度 > 授信额度  - 暂停
//						//发送短信 
//						AmsUtil.smsSend("15112347841", ipWhite, Constants.SMS_ZTAPPLYLOAN, agencyDto.getName()); //渠道经理  - 李佳泽
						String cwphone=ConfigUtil.getStringValue(Constants.FINANCE_LENDING_CWPHONE,ConfigUtil.CONFIG_BASE);
						String qdphone=ConfigUtil.getStringValue(Constants.FINANCE_LENDING_QDPHONE,ConfigUtil.CONFIG_BASE);
						log.info("暂停发送短信号码："+cwphone+"---"+qdphone);
						AmsUtil.smsSend(cwphone, ipWhite, Constants.SMS_ZTAPPLYLOAN, agencyDto.getName()); //渠道经理  - 李佳泽
						AmsUtil.smsSend(qdphone, ipWhite, Constants.SMS_BJAPPLYLOAN, agencyDto.getName());  //财务 - 李丽霞
						rd.setCode(RespStatusEnum.APPLYLOAN_FAIL.getCode());
						rd.setMsg("合作机构 "+agencyDto.getName()+"在快鸽按揭的授信额度为："+agencyDto.getCreditLimit()+"万元，剩余金额为："+agencyDto.getSurplusQuota()+"万元，当前借款金额为："+baseBorrowDto.getLoanAmount()+"万元，由于贷款余额＞授信额度，业务合作已暂停，请及时补充保证金。");
						//app推送信息
						Map<String, Object> tsmap=new HashMap<String, Object>();
						tsmap.put("agencyId", baseBorrowDto.getCooperativeAgencyId());
						tsmap.put("creditLimit", agencyDto.getCreditLimit());
						tsmap.put("LoanAmount", baseBorrowDto.getLoanAmount());
						tsmap.put("type", 2);
						httpUtil.getObject(Constants.LINK_ANJBO_APP_URL, "/mortgage/agency/sendAgencyMsg", tsmap, Map.class);
						return rd;
					}else if(moneyCount>yjAmount){  // 借款总额度 > 授信额度*（后台配置百分百） - 预警
//						AmsUtil.smsSend("15112347841", ipWhite, Constants.SMS_ZTAPPLYLOAN, agencyDto.getName()); //渠道经理  - 李佳泽
						String cwphone=ConfigUtil.getStringValue(Constants.FINANCE_LENDING_CWPHONE,ConfigUtil.CONFIG_BASE);
						String qdphone=ConfigUtil.getStringValue(Constants.FINANCE_LENDING_QDPHONE,ConfigUtil.CONFIG_BASE);
						log.info("预警发送短信号码："+cwphone+"---"+qdphone);
						AmsUtil.smsSend(cwphone, ipWhite, Constants.SMS_ZTAPPLYLOAN, agencyDto.getName()); //渠道经理  - 李佳泽
						AmsUtil.smsSend(qdphone, ipWhite, Constants.SMS_BJAPPLYLOAN, agencyDto.getName());  //财务 - 李丽霞
						rd.setCode(RespStatusEnum.APPLYLOAN_SUCCESS.getCode());
						rd.setMsg("合作机构 "+agencyDto.getName()+"在快鸽按揭的授信额度为："+agencyDto.getCreditLimit()+"万元，剩余金额为："+agencyDto.getSurplusQuota()+"万元，当前借款金额为："+baseBorrowDto.getLoanAmount()+"万元，业务合作正常开展中，为避免余额不足影响业务合作，请及时补充保证金。");						
						//app推送信息
						Map<String, Object> tsmap=new HashMap<String, Object>();
						tsmap.put("agencyId", baseBorrowDto.getCooperativeAgencyId());
						tsmap.put("creditLimit", agencyDto.getCreditLimit());
						tsmap.put("LoanAmount", baseBorrowDto.getLoanAmount());
						tsmap.put("type", 2);
						httpUtil.getObject(Constants.LINK_ANJBO_APP_URL, "/mortgage/agency/sendAgencyMsg", tsmap, Map.class);
					}
				}
				//================判断接口金额是否大于授信额度end=================
				
				ApplyLoanDto applyLoanDto=applyLoanService.findByApplyLoan(loanDto.getOrderNo());
				UserDto dto=getUserDto(request);  //获取用户信息
				loanDto.setCreateUid(dto.getUid());
				loanDto.setUpdateUid(dto.getUid());
				applyLoanService.addApplyLoan(loanDto);
				if(applyLoanDto!=null){
					FinanceLogDto logDto=new FinanceLogDto();
					logDto.setCreateUid(dto.getUid());
					logDto.setUpdateUid(dto.getUid());
					logDto.setOrderNo(loanDto.getOrderNo());
					logDto.setType(1);
//					financeLogService.delete(logDto);
					if(!loanDto.getBorrowerName().equals(applyLoanDto.getBorrowerName())){ 
						logDto.setBeanColumn("borrowerName");
						logDto.setColName("客户姓名");
						logDto.setStartVal(applyLoanDto.getBorrowerName());
						logDto.setEndVal(loanDto.getBorrowerName());
						financeLogService.insert(logDto);
					}
					if(loanDto.getBorrowingDays()!=applyLoanDto.getBorrowingDays()){ 
						logDto.setBeanColumn("borrowingDays");
						logDto.setColName("借款期限");
						logDto.setStartVal(applyLoanDto.getBorrowingDays()+"天");
						logDto.setEndVal(loanDto.getBorrowingDays()+"天");
						financeLogService.insert(logDto);
					}
					if(null!=loanDto.getBankName()&&!loanDto.getBankName().equals(applyLoanDto.getBankName())){ 
						logDto.setBeanColumn("bankName");
						logDto.setColName("银行卡户名");
						logDto.setStartVal(applyLoanDto.getBankName());
						logDto.setEndVal(loanDto.getBankName());
						financeLogService.insert(logDto);
					}
					if(null!=loanDto.getBankAccount()&&!loanDto.getBankAccount().equals(applyLoanDto.getBankAccount())){ 
						logDto.setBeanColumn("bankAccount");
						logDto.setColName("银行卡账号");
						logDto.setStartVal(applyLoanDto.getBankAccount());
						logDto.setEndVal(loanDto.getBankAccount());
						financeLogService.insert(logDto);
					}
					log.info("申请放款银行支行：新"+loanDto.getLendingBankId() +"-"+loanDto.getLendingBankSubId()+"旧"+applyLoanDto.getLendingBankId()+"-"+applyLoanDto.getLendingBankSubId());
					if(loanDto.getLendingBankId()!=applyLoanDto.getLendingBankId() && !(loanDto.getLendingBankId()).equals(applyLoanDto.getLendingBankId())){ 
						logDto.setBeanColumn("lendingBankId");
						logDto.setColName("放款银行");
						if(null!=applyLoanDto.getLendingBankId()){
							BankDto bankDto =CommonDataUtil.getBankNameById(applyLoanDto.getLendingBankId());
							if(bankDto!=null)
							logDto.setStartVal(bankDto.getName());
						}
						if(null != loanDto.getLendingBankId()){
							BankDto bankDto =CommonDataUtil.getBankNameById(loanDto.getLendingBankId());
							if(bankDto!=null)
							logDto.setEndVal(bankDto.getName());
						}
						financeLogService.insert(logDto);
					}
					if(loanDto.getLendingBankSubId()!=applyLoanDto.getLendingBankSubId() && !loanDto.getLendingBankSubId().equals(applyLoanDto.getLendingBankSubId())){
						logDto.setBeanColumn("lendingBankSubId");
						logDto.setColName("放款支行");
						if(null!= applyLoanDto.getLendingBankSubId()){
							SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(applyLoanDto.getLendingBankSubId());
							if(subBankDto!=null)
							logDto.setStartVal(subBankDto.getName());
						}
						if(null!=loanDto.getLendingBankSubId()){
							SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(loanDto.getLendingBankSubId());
							if(subBankDto!=null)
							logDto.setEndVal(subBankDto.getName());
						}
						financeLogService.insert(logDto);
					}
				}
				
				//订单列表
				OrderListDto orderListDto=new OrderListDto();
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(loanDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("applyLoan");
				orderFlowDto.setCurrentProcessName("申请放款");
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				//放款
				AllocationFundDto fundDto =new AllocationFundDto();
				fundDto.setOrderNo(loanDto.getOrderNo());
				boolean isYunNan=false;
				RespData<Map<String,Object>> respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fundDto, Map.class);
				List<Map<String,Object>> orderList=respData.getData();
				if(orderList!=null&&orderList.size()>0) {
					for(Map<String,Object> o:orderList) {
						if("114".equals(MapUtils.getString(o, "fundCode")) || "115".equals(MapUtils.getString(o, "fundCode"))){
									isYunNan=true;
						}
					}
				}
				
				//查询获取资金方选择的财务
				HttpUtil http = new HttpUtil();
				AllocationFundDto fund = new AllocationFundDto();
				fund.setOrderNo(loanDto.getOrderNo());
				List<AllocationFundDto> list = http.getList(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fund,AllocationFundDto.class);
				if(null!=list&&list.size()>0){
					fund = list.get(0);
				};
				UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(fund.getFinanceUid());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());//下一处理人财务
				orderListDto.setCurrentHandler(nextUser.getName());
				
				if("03".equals(baseBorrowDto.getProductCode())){ //畅贷
					//收利息
					orderFlowDto.setNextProcessId("isLendingHarvest");
					orderFlowDto.setNextProcessName("收利息"); 
					// ==============发送短信Start===================
					if (StringUtil.isNotEmpty(nextUser.getMobile())) {
						String ipWhite = ConfigUtil.getStringValue(
								Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
						String cont = baseBorrowDto.getBorrowerName() + " "
								+ baseBorrowDto.getLoanAmount();
						AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_DEBT_SUBSTITUTION,"畅贷",cont, "待收利息");
					}
					// ==============发送短信end===================
				}else if("04".equals(baseBorrowDto.getProductCode())){ //房抵贷
//					FinalAuditDto finalAuditDto=new FinalAuditDto();
//					finalAuditDto.setOrderNo(loanDto.getOrderNo());
//					RespDataObject<FinalAuditDto> auditDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,FinalAuditDto.class);
//					String paymentType=Enums.AuditFinalPaymentType.AUDIT_Final_PAYMENT_TYPE_THREE.getName(); //凭他项权利证放款
//					String paymentTypeName="";
//					if(null!=auditDto.getData()){
//						paymentTypeName=auditDto.getData().getPaymentType();
//					}
//					if(paymentType.equals(paymentTypeName)){ 
						orderFlowDto.setNextProcessId("charge");
						orderFlowDto.setNextProcessName("收费");
//					}else{
//						orderFlowDto.setNextProcessId("fddForensics");
//						orderFlowDto.setNextProcessName("取证");
//						//获取取证处理人
//						ForensicsDto forensicsDto =new ForensicsDto();
//						forensicsDto.setOrderNo(loanDto.getOrderNo());
//						RespDataObject<ForensicsDto> fdataObject=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/fddForensics/v/detail", forensicsDto,ForensicsDto.class);
//						if(null!=fdataObject){
//							forensicsDto=fdataObject.getData();
//							if(null!=forensicsDto.getLicenseReverUid()){
//								UserDto fdto = CommonDataUtil.getUserDtoByUidAndMobile(forensicsDto.getLicenseReverUid());
//								orderListDto.setCurrentHandlerUid(forensicsDto.getLicenseReverUid());
//								orderListDto.setCurrentHandler(fdto.getName());
//							}
//						}
//					}
						
						// ==============发送短信Start===================
						if (StringUtil.isNotEmpty(nextUser.getMobile())) {
							String ipWhite = ConfigUtil.getStringValue(
									Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
							String cont = baseBorrowDto.getBorrowerName() + " "
									+ baseBorrowDto.getLoanAmount();
							AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_DEBT_SUBSTITUTION,"房抵贷",cont, "待收费");
						}
						// ==============发送短信end===================
				}else{ //置换贷款
					//判断费用前置或后置
					if(baseBorrowDto.getPaymentMethod()==1){ //费用前置
						//收利息
						orderFlowDto.setNextProcessId("isLendingHarvest");
						orderFlowDto.setNextProcessName("收利息"); 
						
						// ==============发送短信Start===================
						if (StringUtil.isNotEmpty(nextUser.getMobile())) {
							String ipWhite = ConfigUtil.getStringValue(
									Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
							String cont = baseBorrowDto.getBorrowerName() + " "
									+ baseBorrowDto.getLoanAmount();
							AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_DEBT_SUBSTITUTION,"债务置换",cont, "待收利息");
						}
						// ==============发送短信end===================
					}else{//费用后置
						if(isYunNan){
							orderFlowDto.setNextProcessId("financialStatement");
							orderFlowDto.setNextProcessName("财务制单");
//							UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(loanDto.getUid());
//							orderListDto.setCurrentHandlerUid(nextUser.getUid());//下一处理人财务
//							orderListDto.setCurrentHandler(nextUser.getName());
						}else{
							orderFlowDto.setNextProcessId("lending");
							orderFlowDto.setNextProcessName("放款");
						}
					}
				}
				//更新流程方法
				goNextNode(orderFlowDto, orderListDto);  
		    }else{
		    	rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
				rd.setMsg("该订单已被处理，请刷新列表查看");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			log.info("lendingHarvest-add Exception ==>", e);
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		//查询授信额度并发送短信
		
		
		return rd;
	}
    
    /**
     * 关闭订单
     * @param request
     * @param response
     * @param loanDto
     * @return
     */
    @RequestMapping(value = "/closeLoan")
	public @ResponseBody
	RespStatus closeLoan(HttpServletRequest request,
			HttpServletResponse response,@RequestBody ApplyLoanDto loanDto) {
    	RespStatus resp =new RespStatus();
    	resp.setCode(RespStatusEnum.FAIL.getCode());
    	resp.setMsg(RespStatusEnum.FAIL.getMsg());
    	try {
    		 //判断当前节点
//		    if(!isSubmit(loanDto.getOrderNo(), "applyLoan")){	
	    		UserDto dto=getUserDto(request);
	    		//订单列表
				OrderListDto orderListDto=new OrderListDto();
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(loanDto.getOrderNo());

				OrderListDto listbase = new OrderListDto();
				listbase.setOrderNo(loanDto.getOrderNo());
				listbase = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listbase,OrderListDto.class);
				orderFlowDto.setCurrentProcessId(listbase.getProcessId());
				orderFlowDto.setCurrentProcessName(listbase.getState().replace("待", ""));
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				orderFlowDto.setNextProcessId("closeOrder");
				orderFlowDto.setNextProcessName("订单已停止");
				orderListDto.setCurrentHandlerUid("-");//下一处理人财务
				orderListDto.setCurrentHandler("-");
				orderListDto.setProcessId("closeOrder");
				orderListDto.setState("订单已停止");
				//更新流程方法
				goNextNode(orderFlowDto, orderListDto);  
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
//		    }else{
//		    	resp.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
//		    	resp.setMsg("该订单已被处理，请刷新列表查看");
//		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return resp;
    }
    
}
