package com.anjbo.controller;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.finance.AfterLoanListDto;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.ReportDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseCustomerDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.product.FddMortgageStorageDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.AfterLoanListService;
import com.anjbo.service.ApplyLoanService;
import com.anjbo.service.LendingInstructionService;
import com.anjbo.service.LendingService;
import com.anjbo.service.ReportService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.PinYin4JUtil;
import com.anjbo.utils.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待放款
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/finance/lending/v")
public class LendingController extends BaseController{
	private Logger log = Logger.getLogger(getClass());
	
	@Resource LendingService lendingService;
	@Resource LendingInstructionService lendingInstructionService;
	@Resource
	private ReportService reportService;
	@Resource
	private ApplyLoanService applyLoanService;
	@Resource  AfterLoanListService afterLoanListService;
	 /**
     * 详情
     * @param request
     * @param lendingDto
     * @return
     */
	@RequestMapping("detail")
	@ResponseBody
	public  RespDataObject<LendingDto> queryBalance(HttpServletRequest request,@RequestBody LendingDto lendingDto){
		RespDataObject<LendingDto> resp=new RespDataObject<LendingDto>();
		String orderNo=lendingDto.getOrderNo();
		LendingDto leDto = lendingService.findByLending(lendingDto);
		if(leDto==null){
			leDto=new LendingDto();
			leDto.setOrderNo(lendingDto.getOrderNo());
		}
		try {
			ApplyLoanDto applyLoanDto=applyLoanService.findByApplyLoan(orderNo);
			if(applyLoanDto!=null){
				leDto.setLendingBankId(applyLoanDto.getLendingBankId());
				leDto.setOpeningBankId(applyLoanDto.getLendingBankSubId());
				leDto.setBankName(applyLoanDto.getBankName());
				leDto.setBankAccount(applyLoanDto.getBankAccount());
				leDto.setLoanAmount(applyLoanDto.getLoanAmount());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		DocumentsDto basics=new DocumentsDto();
//		basics.setOrderNo(lendingDto.getOrderNo());
//		RespDataObject<DocumentsDto> doc=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", basics,DocumentsDto.class);
//		basics=doc.getData();
//		if(basics!=null && basics.getForeclosureType()!=null){
//			leDto.setLendingBankId(basics.getForeclosureType().getBankNameId()==null?0:basics.getForeclosureType().getBankNameId());
//			leDto.setOpeningBankId(basics.getForeclosureType().getBankSubNameId()==null?0:basics.getForeclosureType().getBankSubNameId());
//			leDto.setBankName(basics.getForeclosureType().getBankCardMaster());
//			leDto.setBankAccount(basics.getForeclosureType().getBankNo());
//		}
		//获取银行-支行名称
		if(leDto.getLendingBankId()!=null){
			BankDto bankDto =CommonDataUtil.getBankNameById(leDto.getLendingBankId());
			leDto.setLendingBank(bankDto==null ? "":bankDto.getName());
		}
		if(leDto.getOpeningBankId()!=null){
			SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(leDto.getOpeningBankId());
			leDto.setOpeningBank(subBankDto == null ?"":subBankDto.getName());
		}
		//借款信息（借款金额）
		try {
			OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(lendingDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			if(baseBorrowDto!=null){
//				leDto.setBorrowerName(baseBorrowDto.getBorrowerName());    //客户姓名
//				if(baseBorrowDto.getIsChangLoan()==1){ //畅待
//					List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//					if(olist!=null && olist.size()>0){
//						JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//						leDto.setLoanAmount(jsonObject.get("loanAmount")==null?0:Double.parseDouble(jsonObject.get("loanAmount").toString())); //借款金额
//					}
//				}else{
//					leDto.setLoanAmount(baseBorrowDto.getLoanAmount());        //借款金额
//				}
				leDto.setIsPaymentMethod(baseBorrowDto.getPaymentMethod());  //费用支付方式
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		resp.setData(leDto);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	 /**
     * 添加信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/add")
	public @ResponseBody
	RespData<LendingDto> add(HttpServletRequest request,
			HttpServletResponse response,@RequestBody LendingDto lendingDto) {
		RespData<LendingDto> rd = new RespData<LendingDto>();
		try {
			    //判断当前节点
			    if(!isSubmit(lendingDto.getOrderNo(), "lending")){
					//计算预计回款时间
				    OrderBaseBorrowDto bdto=new OrderBaseBorrowDto();
				    bdto.setOrderNo(lendingDto.getOrderNo());
					RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", bdto,OrderBaseBorrowDto.class);
					OrderBaseBorrowDto baseBorrowDto=obj.getData();
//					if(baseBorrowDto!=null && baseBorrowDto.getIsChangLoan()==1){ //畅待
//						baseBorrowDto.setProductCode("03");
//						List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//						if(olist!=null && olist.size()>0){
//							JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//							baseBorrowDto.setLoanAmount(jsonObject.get("loanAmount")==null?0:Double.parseDouble(jsonObject.get("loanAmount").toString())); //借款金额
//						}
//					}
					int borrowingDays=baseBorrowDto.getBorrowingDays();  //借款期限
					Date newDate = DateUtils.addDate(lendingDto.getLendingTime(), (borrowingDays-1)); 
					lendingDto.setCustomerPaymentTime(newDate);  //预计回款时间
					String lendingTime = DateUtil.getNowyyyyMMdd(lendingDto.getLendingTime());
					
					UserDto dto=getUserDto(request);  //获取用户信息
					lendingDto.setCreateUid(dto.getUid());
					lendingDto.setUpdateUid(dto.getUid());
					LendingDto leDto = lendingService.findByLending(lendingDto);	
					
					if(leDto!=null){
						 lendingService.updateLending(lendingDto);
					}else{
						 lendingService.addLending(lendingDto);
					}
					
					//==============生成合同编号start================
					
//					String borrowerName = baseBorrowDto.getBorrowerName();
//					
//					int i = 001;
//					
//					String contractNo = "("+DateUtil.getYear(baseBorrowDto.getCreateTime())+") 借字第"+PinYin4JUtil.getFirstSpell(borrowerName) + String.format("%02d", DateUtil.getMonth(baseBorrowDto.getCreateTime()))  + DateUtil.getDay(baseBorrowDto.getCreateTime()) +"号 ";
//					OrderListDto listDto=new OrderListDto();
//					listDto.setContractNo(contractNo);
//					RespDataObject<OrderListDto> respData = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectOrderListByContractNo", listDto,OrderListDto.class);
//					if(RespStatusEnum.SUCCESS.getCode().equals(respData.getCode()) && respData.getData() != null) {
//						String  orderContractNo = respData.getData().getContractNo();
//						log.info("orderContractNo:"+orderContractNo);
//						if(StringUtil.isNotEmpty(orderContractNo)) {
//							log.info("contractNo:"+contractNo + String.format("%03d",i));
//							if(!contractNo.equals(orderContractNo)) {
//								while (!(contractNo + String.format("%03d",i)).equals(orderContractNo)){
//									 i++;
//								}
//								i++;
//							}else{
//								i = 0;
//							}
//						}
//					}
//					if(i > 0) {
//						contractNo += String.format("%03d",i);	
//					}
					
					Map<String,Object> to = new HashMap<String,Object>();
					to.put("type","citySimName");
					to.put("pcode", "");
//					RespData<DictDto>  dict =httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/common/base/v/choiceDict", to,DictDto.class);  //所有城市
//					List<DictDto> dictList =dict.getData();
//					String citySimName = "";
//					for (DictDto d:dictList) {
//						if(d.getPcode().equals(baseBorrowDto.getCityCode())){
//							citySimName=d.getName();
//							break;
//						}
//					}
//					OrderListDto listDto=new OrderListDto();
//					listDto.setCityCode(baseBorrowDto.getCityCode());
//					listDto.setProductCode(baseBorrowDto.getProductCode());
//					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//					String lendingTime = format.format(lendingDto.getLendingTime());
//					listDto.setLendingTime(lendingTime);
//					RespDataObject<Integer>  lendingCount =httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectlendingCount", listDto,Integer.class);  //交易数
//					int count=0;
//					String tmpCount = "";
//					if(lendingCount.getData()!=null){
//						count =lendingCount.getData();
//					}
//					count += 1;
//					if(count<10){
//						tmpCount = "000"+count;
//					}
//					else if(count<100){
//						tmpCount = "00"+count;
//					}
//					else if(count<1000){
//						tmpCount = "0"+count;
//					}
//					else{
//						tmpCount = count+"";
//					}
//					String productCode = baseBorrowDto.getProductCode(); //业务类型代号
//					format = new SimpleDateFormat("yyyyMM");
//					String contractNoTime = format.format(lendingDto.getLendingTime());;
//					String contractNo = citySimName+contractNoTime+productCode+tmpCount;   //城市代码+放款时间年月+业务产品代号+当月第X笔该业务产品的订单笔数
					
					//==============生成合同编号end==================
//					log.info("合同编号------contractNo："+contractNo);
					//==============发送短信Start===================
					String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
					String cont=baseBorrowDto.getBorrowerName()+" "+baseBorrowDto.getLoanAmount();
					//受理经理
					UserDto acceptMemberUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
					//渠道经理
					UserDto channelManagerUid = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getChannelManagerUid());
					if(baseBorrowDto!=null && "03".equals(baseBorrowDto.getProductCode())){ //畅待
						if(StringUtil.isNotEmpty(acceptMemberUser.getMobile())){
							  AmsUtil.smsSend(acceptMemberUser.getMobile(), ipWhite, Constants.SMS_CDLENDING, cont);
						}
						if(StringUtil.isNotEmpty(channelManagerUid.getMobile())){
							  AmsUtil.smsSend(channelManagerUid.getMobile(), ipWhite, Constants.SMS_CDLENDING, cont);
						}
					}else{
						if(StringUtil.isNotEmpty(acceptMemberUser.getMobile())){
							  AmsUtil.smsSend(acceptMemberUser.getMobile(), ipWhite, Constants.SMS_LENDING, cont);
						}
						if(StringUtil.isNotEmpty(channelManagerUid.getMobile())){
							  AmsUtil.smsSend(channelManagerUid.getMobile(), ipWhite, Constants.SMS_LENDING, cont);
						}
					}
					if(baseBorrowDto!=null && "04".equals(baseBorrowDto.getProductCode())){ //房抵贷
						if(StringUtil.isNotEmpty(baseBorrowDto.getPhoneNumber())){  
							String name=baseBorrowDto.getBorrowerName();
							double money=baseBorrowDto.getLoanAmount();
							DecimalFormat decimalFormat=new DecimalFormat("0.00");
							String loanAmount=decimalFormat.format(money*10000);
							int borrowingDay=baseBorrowDto.getBorrowingDays();
							String day=lendingTime.substring(8);
							log.info("房抵贷放款发送短信信息："+name+" ---"+loanAmount+"---"+borrowingDay+" ---"+day);
							try {
								AmsUtil.smsSend2(baseBorrowDto.getPhoneNumber(), ipWhite, Constants.SMS_TEMPLATE_LENDING_BORROWER,"hyt", name,loanAmount,borrowingDay,day);
//								AmsUtil.smsSend("15112347841", ipWhite, Constants.SMS_TEMPLATE_LENDING_BORROWER, name,loanAmount,borrowingDay,day);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					//==============发送短信end===================
					
					//============修改机构剩余额度Start=============
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("agencyId", baseBorrowDto.getCooperativeAgencyId());//合作机构ID
					AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
					if(agencyDto!=null && agencyDto.getCooperativeModeId()==1 && agencyDto.getCreditLimit()>0){//判断 是否有保证金  有则判断授信  无则继续流程
						Double surplusQuota = agencyDto.getSurplusQuota()==null?0:agencyDto.getSurplusQuota();  //剩余额度
						Double  moneyCount=agencyDto.getCreditLimit() - surplusQuota + baseBorrowDto.getLoanAmount();  //授信额度 - 剩余额度 + 借款金额 = 借款总额度 
						log.info("放款：借款总额度 ："+moneyCount+".授信额度:"+agencyDto.getCreditLimit());
						if(moneyCount>agencyDto.getCreditLimit()){  //借款总额度 > 授信额度  - 暂停
//							//发送短信 
//							AmsUtil.smsSend("15112347841", ipWhite, Constants.SMS_ZTAPPLYLOAN, agencyDto.getName()); //渠道经理  - 李佳泽
							String cwphone=ConfigUtil.getStringValue(Constants.FINANCE_LENDING_CWPHONE,ConfigUtil.CONFIG_BASE);
							String qdphone=ConfigUtil.getStringValue(Constants.FINANCE_LENDING_QDPHONE,ConfigUtil.CONFIG_BASE);
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
						}
						Double loanAmount=surplusQuota-baseBorrowDto.getLoanAmount();  //剩余额度 - 借款金额 = 新剩余额度
						//修改机构剩余额度
						Map<String,Object> updMap = new HashMap<String, Object>();
						updMap.put("id", baseBorrowDto.getCooperativeAgencyId());
						updMap.put("surplusQuota", loanAmount);
						RespDataObject<Integer>  resp =httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/updSurplusQuota", updMap,Integer.class);  
						log.info("放款-修改机构剩余额度参数:"+updMap.toString());
						if("FAIL".equals(resp.getCode())){
					    	log.info("放款-修改机构剩余额度失败！参数="+updMap.toString());
					    }
					}
					//=============修改机构剩余额度end=================
					//==============房抵押转贷后系统start==============
					if(baseBorrowDto!=null && "04".equals(baseBorrowDto.getProductCode())){ //房抵贷
						try {
							AfterLoanListDto aftDto=new AfterLoanListDto();
							aftDto.setOrderNo(lendingDto.getOrderNo());
							aftDto.setCityCode(baseBorrowDto.getCityCode());
							aftDto.setCityName(baseBorrowDto.getCityName());
							aftDto.setProductCode(baseBorrowDto.getProductCode());
							aftDto.setProductName(baseBorrowDto.getProductName());

							OrderListDto  orderListDto  = new OrderListDto();
							orderListDto.setOrderNo(lendingDto.getOrderNo());
							orderListDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderListDto, OrderListDto.class);
							aftDto.setContractNo(orderListDto.getContractNo()); //合同编码
							aftDto.setBorrowerName(baseBorrowDto.getBorrowerName());
							aftDto.setLoanAmount(baseBorrowDto.getLoanAmount());
							aftDto.setBorrowingPeriods(baseBorrowDto.getBorrowingDays());
							aftDto.setLendingTime(lendingDto.getLendingTime());
							aftDto.setChannelManagerUid(baseBorrowDto.getChannelManagerUid());
							UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getChannelManagerUid());
							aftDto.setChannelManagerName(userDto.getName());
							aftDto.setChannelManagerPhone(userDto.getMobile());
							aftDto.setRepaymentType(baseBorrowDto.getPaidType());  //还款方式
							aftDto.setAcceptMemberUid(baseBorrowDto.getAcceptMemberUid());
							aftDto.setAcceptMemberName(baseBorrowDto.getAcceptMemberName());
							aftDto.setRate(baseBorrowDto.getRate());
							aftDto.setOverdueRate(baseBorrowDto.getOverdueRate());
							if(null==baseBorrowDto.getAcceptMemberName()){
								userDto=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
								aftDto.setAcceptMemberName(userDto.getName());
							}
							aftDto.setPhoneNumber(baseBorrowDto.getPhoneNumber());
							aftDto.setBranchCompany(baseBorrowDto.getBranchCompany());
							aftDto.setCreateUid(dto.getUid());
							aftDto.setUpdateUid(dto.getUid());
							afterLoanListService.insert(aftDto);
	//						RespStatus  resp =httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/finance/afterLoanList/v/insertAfterLoanList", aftDto);
	//						log.info("放款转贷后系统："+resp);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					//==============房抵押转贷后系统end==============
					//流程表
					OrderFlowDto orderFlowDto=new OrderFlowDto();  
					orderFlowDto.setOrderNo(lendingDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("lending");
					orderFlowDto.setCurrentProcessName("放款");
					orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
					orderFlowDto.setHandleName(dto.getName());
					//订单列表修改信息
					OrderListDto orderListDto=new OrderListDto();
					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String planPaymentTime = format2.format(newDate);
					orderListDto.setPlanPaymentTime(planPaymentTime);  //预计回款时间
					String time=format2.format(lendingDto.getLendingTime());
					orderListDto.setLendingTime(time.toString());//放款时间
					//下一处理人 结清还款专员
					to.put("orderNo", lendingDto.getOrderNo());
					if("03".equals(baseBorrowDto.getProductCode())){ //畅贷 并且不关联订单
						if(!"0".equals(lendingDto.getRelationOrderNo()) && null != lendingDto.getRelationOrderNo()){ //关联
							//V3.0版本
							orderFlowDto.setNextProcessId("receivableFor");
							orderFlowDto.setNextProcessName("回款");
							orderListDto.setCurrentHandlerUid(lendingDto.getReceivableForUid());//下一处理人为当前处理人会计
							UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(lendingDto.getReceivableForUid());
							orderListDto.setCurrentHandler(userDto.getName());
						}else{//不关联置换贷
							//v3.2版本
							orderFlowDto.setNextProcessId("mortgage");
							orderFlowDto.setNextProcessName("抵押");
							orderListDto.setCurrentHandlerUid(baseBorrowDto.getAcceptMemberUid());//下一处理人默认受理员  
							UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
							orderListDto.setCurrentHandler(userDto.getName());
						}
					}else if("06".equals(baseBorrowDto.getProductCode())||"07".equals(baseBorrowDto.getProductCode())){
						orderFlowDto.setNextProcessId("receivableFor");
						orderFlowDto.setNextProcessName("回款");
						orderListDto.setCurrentHandlerUid(lendingDto.getReceivableForUid());//下一处理人为当前处理人会计
						UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(lendingDto.getReceivableForUid());
						orderListDto.setCurrentHandler(userDto.getName());
					}else if("04".equals(baseBorrowDto.getProductCode())){ //房抵贷
//						orderFlowDto.setNextProcessId("fddRepayment");
//						orderFlowDto.setNextProcessName("还款");
						FinalAuditDto finalAuditDto=new FinalAuditDto();
						finalAuditDto.setOrderNo(lendingDto.getOrderNo());
						RespDataObject<FinalAuditDto> auditDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,FinalAuditDto.class);
						String paymentType=Enums.AuditFinalPaymentType.AUDIT_Final_PAYMENT_TYPE_THREE.getName(); //凭他项权利证放款
						String paymentTypeName="";
						if(null!=auditDto.getData()){ 
							paymentTypeName=auditDto.getData().getPaymentType();
						}
						if(paymentType.equals(paymentTypeName)){ //凭他项
							orderFlowDto.setNextProcessId("fddMortgageStorage");
							orderFlowDto.setNextProcessName("抵押品入库");
							FddMortgageStorageDto fddMortgageStorageDto =new FddMortgageStorageDto();
							fddMortgageStorageDto.setOrderNo(lendingDto.getOrderNo());
							RespDataObject<FddMortgageStorageDto>  respDataObject =httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/fddMortgageStorage/v/detail", fddMortgageStorageDto,FddMortgageStorageDto.class);
							if(null!=respDataObject.getData()  || null!=respDataObject.getData().getCollateralUid()){
								orderListDto.setCurrentHandlerUid(respDataObject.getData().getCollateralUid());//下一处理人入库操作人
								UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(respDataObject.getData().getCollateralUid());
								orderListDto.setCurrentHandler(userDto.getName());
							}else{
								orderListDto.setCurrentHandlerUid(baseBorrowDto.getAcceptMemberUid());//下一处理人默认受理员  
								UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
								orderListDto.setCurrentHandler(userDto.getName());
							}
						}else{
							orderFlowDto.setNextProcessId("fddForensics");
							orderFlowDto.setNextProcessName("取证");
							//获取取证处理人
							ForensicsDto forensicsDto =new ForensicsDto();
							forensicsDto.setOrderNo(lendingDto.getOrderNo());
							RespDataObject<ForensicsDto> fdataObject=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/fddForensics/v/detail", forensicsDto,ForensicsDto.class);
							if(null!=fdataObject){
								forensicsDto=fdataObject.getData();
								if(null!=forensicsDto.getLicenseReverUid()){
									UserDto fdto = CommonDataUtil.getUserDtoByUidAndMobile(forensicsDto.getLicenseReverUid());
									orderListDto.setCurrentHandlerUid(forensicsDto.getLicenseReverUid());
									orderListDto.setCurrentHandler(fdto.getName());
								}
							}
						}
						
					}else{
						//判断费用前置或后置
						RespDataObject<DistributionMemberDto>  menber =httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", to,DistributionMemberDto.class);
						if(baseBorrowDto.getPaymentMethod()==1){ //费用前置
							orderFlowDto.setNextProcessId("foreclosure");
							orderFlowDto.setNextProcessName("结清原贷款");
							if(menber.getData()!=null){
								orderListDto.setCurrentHandlerUid(menber.getData().getForeclosureMemberUid());//下一处理人结清还款专员
								UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(orderListDto.getCurrentHandlerUid());
								orderListDto.setCurrentHandler(userDto.getName());
//								//发送短信
//								if(baseBorrowDto!=null && "03".equals(baseBorrowDto.getProductCode())){ //畅待
//									if(StringUtil.isNotEmpty(userDto.getMobile())){
//										  AmsUtil.smsSend(userDto.getMobile(), ipWhite, Constants.SMS_CDLENDING, cont);
//									}
//								}else{
//									if(StringUtil.isNotEmpty(userDto.getMobile())){
//										  AmsUtil.smsSend(userDto.getMobile(), ipWhite, Constants.SMS_LENDING, cont);
//									}
//								}
							}
							
						}else{//费用后置
							orderFlowDto.setNextProcessId("isBackExpenses");
							orderFlowDto.setNextProcessName("扣回后置费用");
							UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(lendingDto.getReceivableForUid()); 
							orderListDto.setCurrentHandlerUid(userDto.getUid());
							orderListDto.setCurrentHandler(userDto.getName());
						}
						if(menber.getData()!=null){
							UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(menber.getData().getForeclosureMemberUid());
							AmsUtil.smsSend(userDto.getMobile(), ipWhite, Constants.SMS_LENDING, cont);
							log.info("发送短信结清原贷款"+userDto.getMobile()+" ，"+cont);
						}
						
					}
					goNextNode(orderFlowDto, orderListDto);  //流程方法
					//更新出款报备状态
					ReportDto reportDto = new ReportDto();
					reportDto.setOrderNo(lendingDto.getOrderNo());
					reportDto.setStatus(1);
					reportService.updateByStatus(reportDto);

					rd.setCode(RespStatusEnum.SUCCESS.getCode());
					rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
					
					Map<String, Object> downloadMap=new HashMap<String, Object>();
					downloadMap.put("orderNo", lendingDto.getOrderNo());
					if(baseBorrowDto!=null && !"04".equals(baseBorrowDto.getProductCode())){
					   	downloadPlan(request, downloadMap);
					}
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
		return rd;
	}
    
    /**
     * 推送华融数据
     * @param request
     * @param paramt
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/toAddHarong")
	public RespStatus toAddHarong(HttpServletRequest request,@RequestBody Map<String, Object> paramt){
    	RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.SUCCESS.getCode());
		result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		try{
			if(MapUtils.isEmpty(paramt)){
				result.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
				result.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			log.info("推送华融数据："+paramt);
			RespStatus status=httpUtil.getRespStatus(Constants.LINK_CREDIT, ReqMappingConstants.CREDIT_THIRD_API_REPAYMENT, paramt.get("auditDto"));  //流程项目
			log.info("推送华融："+status);
			if("FAIL".equals(status.getCode())){
				result.setCode(status.getCode());
				result.setMsg("推送融安信息失败！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setCode(RespStatusEnum.FAIL.getCode());
			result.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return result;
    }
    
    /**
     * 查询放款orderNo 报表用
     * @param request
     * @param Dto
     * @return
     */
    @RequestMapping("orderNoList")
	@ResponseBody
	public  RespData<String> orderNoList(HttpServletRequest request,@RequestBody LendingDto Dto){
    	RespData<String> resp=new RespData<String>();
    	List<String> list=lendingService.selectOrderNo();
		resp.setData(list);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
    

    /**
     * 查询回款处理人
     * @param request
     * @param Dto
     * @return
     */
    @RequestMapping("selectReForUid")
	@ResponseBody
	public  RespDataObject<LendingDto> selectReForUid(HttpServletRequest request,@RequestBody LendingDto Dto){
    	RespDataObject<LendingDto> resp=new RespDataObject<LendingDto>();
    	LendingDto lendingDto=lendingService.findByLending(Dto);
    	resp.setData(lendingDto);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
    
    /**
     * 下载应还款计划
     * @param request
     * @param Dto
     * @return
     */
    @RequestMapping("downloadPlan")
	@ResponseBody
	public  RespDataObject<String> downloadPlan(HttpServletRequest request,@RequestBody Map<String, Object> map){
    	 RespDataObject<String> resp=new RespDataObject<String>();
    	 //基本信息
    	 String orderNo=map.get("orderNo")+"";
    	 OrderBaseBorrowDto bdto=new OrderBaseBorrowDto();
	     bdto.setOrderNo(orderNo);
		 RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", bdto,OrderBaseBorrowDto.class);
		 OrderBaseBorrowDto baseBorrowDto=obj.getData();
		 //放款信息
		 LendingDto lendingDto=  lendingService.selectLendingTime(orderNo);
		 if(lendingDto==null){
			 lendingDto=new LendingDto(); 
		 }
		 OrderBaseCustomerDto customerDto=new OrderBaseCustomerDto();
		 customerDto.setOrderNo(orderNo);
		 
		 if("03".equals(baseBorrowDto.getProductCode())){ //畅贷且关联
			 OrderListDto orderListDto=new OrderListDto();
			 orderListDto.setOrderNo(orderNo);
			 RespDataObject<OrderListDto> listObj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderListDto,OrderListDto.class);
			 orderListDto = listObj.getData();
			 if(orderListDto!=null && orderListDto.getRelationOrderNo()!=null && !"".equals(orderListDto.getRelationOrderNo())){
				 customerDto.setOrderNo(orderListDto.getRelationOrderNo());
			 }
		 }
		 
		 RespDataObject<OrderBaseCustomerDto> dataObject=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/customer/v/query", customerDto,OrderBaseCustomerDto.class);
		 customerDto =dataObject.getData();
    	 Map<String,String> dataMap = new HashMap<String,String>();
    	 dataMap.put("borrowerName",baseBorrowDto.getBorrowerName()+"");
    	 if(customerDto!=null){
	    	 dataMap.put("customerCardType", customerDto.getCustomerCardType()==null?"":customerDto.getCustomerCardType());   //身份证类型
	    	 dataMap.put("customerCardNumber", customerDto.getCustomerCardNumber()); //身份证号码
    	 }else{
    		 dataMap.put("customerCardType", "-");   //身份证类型
	    	 dataMap.put("customerCardNumber", "-"); //身份证号码
    	 }
    	 dataMap.put("productName", baseBorrowDto.getProductName()+"");
    	 dataMap.put("loanAmount", baseBorrowDto.getLoanAmount()+"");
    	 dataMap.put("borrowingDays", baseBorrowDto.getBorrowingDays()+"");
    	 dataMap.put("lendingTime", lendingDto.getLendingTimeStr()+"");
    	 dataMap.put("planPaymentTime", lendingDto.getCustomerPaymentTimeStr()+"");
    	 dataMap.put("time", "1");
    	 dataMap.put("planPaymentTime",lendingDto.getCustomerPaymentTimeStr()+"");
    	 dataMap.put("loanAmount",baseBorrowDto.getLoanAmount()+"");
    	 dataMap.put("rate", baseBorrowDto.getRate()+"");
    	 dataMap.put("overdueRate", baseBorrowDto.getOverdueRate()+"");
    	 log.info("下载还款计划参数："+dataMap.toString());
		try {
		    //Configuration用于读取ftl文件  
			log.info("=============还款计划开始下载===========");
	    	Configuration configuration = new Configuration();  
	        configuration.setDefaultEncoding("utf-8");
	        //文件绝对路径
	        String path = request.getSession().getServletContext().getRealPath("/")+"credit/finance/";
			configuration.setDirectoryForTemplateLoading(new File(path));
			
			String p=path+"/"+orderNo+".doc";
			log.info("应还款下载路径："+p);
			File outFile = new File(p);  
			Template t =  configuration.getTemplate("repaymentSchedule.ftl","utf-8");  
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);  
			t.process(dataMap, out);  
			out.close();
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("下载完成！请在桌面查看！");
			String url = ConfigUtil.getStringValue(Constants.LINK_CREDIT,ConfigUtil.CONFIG_LINK);
			resp.setData(url+"/credit/finance/"+orderNo+".doc");
			log.info("=============还款计划下载结束===========");
			log.info("跳转链接："+url+"/credit/finance/"+orderNo+".doc");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("下载失败！请稍后重试！");
		} 
		return resp;
	}
    
    public static void main(String[] args) {
    	
//		try {
//			System.out.println(new File("repaymentSchedule.ftl").getPath());
//			System.out.println( new File("repaymentSchedule.ftl").getAbsolutePath());
//			System.err.println(new File("repaymentSchedule.ftl").getCanonicalPath());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
    /**
     * 同步旧数据下载
     */
    @RequestMapping("downloadPlanAll")
	@ResponseBody
	public  RespDataObject<String> downloadPlanAll(HttpServletRequest request){
    	 RespDataObject<String> resp=new RespDataObject<String>();
    	 List<String> lendingList=lendingService.selectOrderNo();
    	 //基本信息
    	 for (int i = 0; i < lendingList.size(); i++) {
	    	 String orderNo=lendingList.get(i);
	    	 OrderBaseBorrowDto bdto=new OrderBaseBorrowDto();
		     bdto.setOrderNo(orderNo);
			 RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", bdto,OrderBaseBorrowDto.class);
			 OrderBaseBorrowDto baseBorrowDto=obj.getData();
			 if(baseBorrowDto==null)
				 baseBorrowDto=new OrderBaseBorrowDto();
			
			 //放款信息
			 LendingDto lendingDto=  lendingService.selectLendingTime(orderNo);
			 if(lendingDto==null){
				 lendingDto=new LendingDto(); 
			 }
			 OrderBaseCustomerDto customerDto=new OrderBaseCustomerDto();
			 customerDto.setOrderNo(orderNo);
			 if("03".equals(baseBorrowDto.getProductCode())){ //畅贷且关联
				 OrderListDto orderListDto=new OrderListDto();
				 orderListDto.setOrderNo(orderNo);
				 RespDataObject<OrderListDto> listObj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderListDto,OrderListDto.class);
				 orderListDto = listObj.getData();
				 if(orderListDto!=null && orderListDto.getRelationOrderNo()!=null && !"".equals(orderListDto.getRelationOrderNo())){
					 customerDto.setOrderNo(orderListDto.getRelationOrderNo());
				 }
			 }
			 RespDataObject<OrderBaseCustomerDto> dataObject=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/customer/v/query", customerDto,OrderBaseCustomerDto.class);
			 customerDto =dataObject.getData();
			 if(customerDto==null)
				 customerDto = new OrderBaseCustomerDto();
	    	 Map<String,String> dataMap = new HashMap<String,String>();
	    	 dataMap.put("borrowerName",baseBorrowDto.getBorrowerName()+"");
	    	 dataMap.put("customerCardType", customerDto.getCustomerCardType()==null?"-":customerDto.getCustomerCardType());   //身份证类型
	    	 dataMap.put("customerCardNumber", customerDto.getCustomerCardNumber()==null?"-":customerDto.getCustomerCardNumber()); //身份证号码
	    	 dataMap.put("productName", baseBorrowDto.getProductName()+"");
	    	 dataMap.put("loanAmount", baseBorrowDto.getLoanAmount()+"");
	    	 dataMap.put("borrowingDays", baseBorrowDto.getBorrowingDays()+"");
	    	 dataMap.put("lendingTime", lendingDto.getLendingTimeStr()+"");
	    	 dataMap.put("planPaymentTime", lendingDto.getCustomerPaymentTimeStr()+"");
	    	 dataMap.put("time", "1");
	    	 dataMap.put("planPaymentTime",lendingDto.getCustomerPaymentTimeStr()+"");
	    	 dataMap.put("loanAmount",baseBorrowDto.getLoanAmount()+"");
	    	 dataMap.put("rate", baseBorrowDto.getRate()+"");
	    	 dataMap.put("overdueRate", baseBorrowDto.getOverdueRate()+"");
	    	 log.info("下载还款计划参数："+dataMap.toString());
			try {
			    //Configuration用于读取ftl文件  
				log.info("=============还款计划开始下载===========");
		    	Configuration configuration = new Configuration();  
		        configuration.setDefaultEncoding("utf-8");
		        //文件绝对路径
		        String path = request.getSession().getServletContext().getRealPath("/")+"credit/finance/";
				configuration.setDirectoryForTemplateLoading(new File(path));
				
				String p=path+"/"+orderNo+".doc";
				log.info("应还款下载路径："+p);
				File outFile = new File(p);  
				Template t =  configuration.getTemplate("repaymentSchedule.ftl","utf-8");  
				Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);  
				t.process(dataMap, out);  
				out.close();
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg("下载完成！请在桌面查看！");
				String url = ConfigUtil.getStringValue(Constants.LINK_CREDIT,ConfigUtil.CONFIG_LINK);
				resp.setData(url+"/credit/finance/"+orderNo+".doc");
				log.info("=============还款计划下载结束===========");
				log.info("跳转链接："+url+"/credit/finance/"+orderNo+".doc");
			} catch (Exception e) {
				e.printStackTrace();
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("下载失败！请稍后重试！orderNo="+orderNo);
			} 
    	}
		return resp;
	}
    
	
}
