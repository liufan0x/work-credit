package com.anjbo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.element.ElementListDto;
import com.anjbo.bean.finance.FinanceLogDto;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.FinanceLogService;
import com.anjbo.service.LendingHarvestService;
import com.anjbo.service.LendingService;
import com.anjbo.utils.CommonDataUtil;
/***
 * 待核实利息/扣回后置的订单
 * @author YS
 *
 */
@Controller
@RequestMapping("/credit/finance/lendingHarvest/v")
public class LendingHarvestController extends BaseController{
	private Logger log = Logger.getLogger(getClass());
	@Resource  LendingHarvestService lendingHarvestService;
	@Resource LendingService lendingService;
	@Resource
	private FinanceLogService financeLogService;
	 /**
     * 详情
     * @param request
     * @param orderNo
     * @return
     */
	@RequestMapping("detail")
	@ResponseBody
	public  RespDataObject<Map<String, Object>> queryBalance(HttpServletRequest request,@RequestBody LendingHarvestDto harvestDto){
		RespDataObject<Map<String, Object>> resp=new RespDataObject<Map<String, Object>>();
		//借款信息（客户姓名，借款期限，借款金额，风控等级，费率，逾期费率，收费金额）
		Map<String, Object> map=new HashMap<String, Object>();
		LendingHarvestDto lendingHarvestDto =new LendingHarvestDto();
		int type=harvestDto.getType();
		try {
			OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(harvestDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			//获取费用支付方式
			harvestDto.setType(baseBorrowDto.getPaymentMethod());
			lendingHarvestDto = lendingHarvestService.findByHarvest(harvestDto);	
			if(lendingHarvestDto==null){
				lendingHarvestDto=new LendingHarvestDto();
				lendingHarvestDto.setOrderNo(harvestDto.getOrderNo());
			}
			if(baseBorrowDto!=null){
				lendingHarvestDto.setIsOnePay(baseBorrowDto.getIsOnePay());  //是否一次性付款
			    lendingHarvestDto.setProductCode(baseBorrowDto.getProductCode());  //产品编码
			    lendingHarvestDto.setCityCode(baseBorrowDto.getCityCode());
			    lendingHarvestDto.setCooperativeAgencyId(baseBorrowDto.getCooperativeAgencyId());  //合作机构Id
//			    lendingHarvestDto.setIsChangLoan(baseBorrowDto.getIsChangLoan());
			    lendingHarvestDto.setServiceCharge(baseBorrowDto.getServiceCharge());//服务费
			    lendingHarvestDto.setCustomsPoundage(baseBorrowDto.getCustomsPoundage()); //关外手续费
			    lendingHarvestDto.setOtherPoundage(baseBorrowDto.getOtherPoundage());  //其他费用
			    lendingHarvestDto.setModeid(baseBorrowDto.getModeid());
//				if(baseBorrowDto.getIsChangLoan()==1){ //畅待
//					List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//					if(olist!=null && olist.size()>0){
//						JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//						lendingHarvestDto.setLoanAmount(jsonObject.get("loanAmount")==null?0:Double.parseDouble(jsonObject.get("loanAmount").toString())); //借款金额
//						lendingHarvestDto.setBorrowingDays(jsonObject.get("borrowingDays")==null?0:Integer.parseInt(jsonObject.get("borrowingDays").toString()));  //借款期限
//						lendingHarvestDto.setRate(jsonObject.get("rate")==null?0:Double.parseDouble(jsonObject.get("rate").toString()));					//费率
//					    lendingHarvestDto.setOverdueRate(jsonObject.get("overdueRate")==null?0:Double.parseDouble(jsonObject.get("overdueRate").toString()));		//逾期费率
//					    lendingHarvestDto.setChargeMoney(jsonObject.get("chargeMoney")==null?0:Double.parseDouble(jsonObject.get("chargeMoney").toString()));		//收费金额
//					    lendingHarvestDto.setRiskGradeId(jsonObject.get("riskGradeId")==null?0:Integer.parseInt(jsonObject.get("riskGradeId").toString()));  	//风控等级
//					}
//				}else{
					lendingHarvestDto.setLoanAmount(baseBorrowDto.getLoanAmount());        //借款金额
					lendingHarvestDto.setBorrowingDays(baseBorrowDto.getBorrowingDays());  //借款期限
					lendingHarvestDto.setRate(baseBorrowDto.getRate());					//费率
				    lendingHarvestDto.setOverdueRate(baseBorrowDto.getOverdueRate());		//逾期费率
				    lendingHarvestDto.setChargeMoney(baseBorrowDto.getChargeMoney());		//收费金额
				    lendingHarvestDto.setRiskGradeId(baseBorrowDto.getRiskGradeId());  	//风控等级
				    if(baseBorrowDto.getRiskGradeId()>0){
				    	//风控等级名称
						List<DictDto> dicts = getDictDtoByType("riskControl");
						if(dicts!=null&&dicts.size()>0){
							for (DictDto dictDto : dicts) {
								if(dictDto.getCode().equals(String.valueOf(baseBorrowDto.getRiskGradeId()))){
									lendingHarvestDto.setRiskGrade(dictDto.getName());
								}
								if(baseBorrowDto.getRiskGradeId()!=null&&baseBorrowDto.getRiskGradeId()==0){
									lendingHarvestDto.setRiskGrade("其他");
								}
							}
						}
				    	
				    }
//				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		map.put("harvest", lendingHarvestDto);
		FinanceLogDto logDto=new FinanceLogDto();
		logDto.setOrderNo(harvestDto.getOrderNo());
		logDto.setType(type);
		List<FinanceLogDto> logDtos=financeLogService.findByAll(logDto);
		map.put("logDtos", logDtos);
		resp.setData(map);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	@RequestMapping("appDetail")
	@ResponseBody
	public  RespDataObject<LendingHarvestDto> appDetail(HttpServletRequest request,@RequestBody LendingHarvestDto harvestDto){
		RespDataObject<LendingHarvestDto> resp=new RespDataObject<LendingHarvestDto>();
		LendingHarvestDto lendingHarvestDto = lendingHarvestService.findByHarvest(harvestDto);	
		if(lendingHarvestDto==null){
			lendingHarvestDto=new LendingHarvestDto();
			lendingHarvestDto.setOrderNo(harvestDto.getOrderNo());
		}
		resp.setData(lendingHarvestDto);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	 /**
     * 添加收利息信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/add")
	public @ResponseBody
	RespData<LendingHarvestDto> add(HttpServletRequest request,
			HttpServletResponse response,@RequestBody LendingHarvestDto harvestDto) {
		RespData<LendingHarvestDto> rd = new RespData<LendingHarvestDto>();
		try {
			 //判断当前节点
			boolean isWithdraw = isWithdraw(harvestDto.getOrderNo(),"lendingHarvest");
			if(harvestDto.getType()==1 &&isWithdraw){
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg("该订单已经被撤回");
				return rd;
			}
		    if((harvestDto.getType()==1 &&!isSubmit(harvestDto.getOrderNo(), "lendingHarvest")) || (harvestDto.getType()==2 && !isSubmit(harvestDto.getOrderNo(), "backExpenses"))){
				UserDto dto=getUserDto(request);  //获取用户信息
				harvestDto.setCreateUid(dto.getUid());
				harvestDto.setUpdateUid(dto.getUid());
				LendingHarvestDto lendingHarvestDto = lendingHarvestService.findByHarvest(harvestDto);	
				if(lendingHarvestDto!=null){  
					lendingHarvestService.updateHarves(harvestDto);
				}else{
					lendingHarvestService.addLendingHarvest(harvestDto);
				}
				//-------判断是否修改字段START-----------
				OrderBaseBorrowDto baseBorrowDto = new OrderBaseBorrowDto();
				baseBorrowDto.setOrderNo(harvestDto.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", baseBorrowDto,OrderBaseBorrowDto.class);
				baseBorrowDto=  obj.getData();
				FinanceLogDto logDto=new FinanceLogDto();
				logDto.setCreateUid(dto.getUid());
				logDto.setUpdateUid(dto.getUid());
				logDto.setOrderNo(harvestDto.getOrderNo());
				if(harvestDto.getType()==1){
					logDto.setType(2);
				}else{
					logDto.setType(3);
				}
//				financeLogService.delete(logDto);
				log.info("新收费类型："+harvestDto.getRiskGradeId()+""+"原收费类型："+baseBorrowDto.getRiskGradeId()+"");
				if(baseBorrowDto.getRiskGradeId()!=null && !(harvestDto.getRiskGradeId()+"").equals(baseBorrowDto.getRiskGradeId()+"")){
					logDto.setBeanColumn("riskGradeId");
					logDto.setColName("收费类型");
					List<DictDto> dicts = getDictDtoByType("riskControl");
					if(dicts!=null&&dicts.size()>0){
						for (DictDto dictDto : dicts) {
							//畅贷风控等级名称
//							if(baseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&baseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
//								List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//								if(olist!=null && olist.size()>0){
//									JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//									baseBorrowDto.setRiskGradeId(jsonObject.get("riskGradeId")==null?0:Integer.parseInt(jsonObject.get("riskGradeId").toString()));  	//风控等级
//								}
//							}
							if(dictDto.getCode().equals(String.valueOf(baseBorrowDto.getRiskGradeId()))){
								logDto.setStartVal(dictDto.getName());
							}
							if(baseBorrowDto.getRiskGradeId()!=null&&baseBorrowDto.getRiskGradeId()==0){
								logDto.setStartVal("其他");
							}
 							if(dictDto.getCode().equals(String.valueOf(harvestDto.getRiskGradeId()))){
								logDto.setEndVal(dictDto.getName());
							}
							if(harvestDto.getRiskGradeId()!=null&&harvestDto.getRiskGradeId()==0){
								logDto.setEndVal("其他");
							}
						}
					}
					financeLogService.insert(logDto);
				}
				if(baseBorrowDto.getChargeMoney()!=null && !equal(harvestDto.getChargeMoney(),baseBorrowDto.getChargeMoney())){
					logDto.setBeanColumn("chargeMoney");
					logDto.setColName("收费金额");
					//畅贷
//					if(baseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&baseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
//						List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//						if(olist!=null && olist.size()>0){
//							JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//							baseBorrowDto.setChargeMoney(jsonObject.get("chargeMoney")==null?0:Double.parseDouble(jsonObject.get("chargeMoney").toString()));
//						}
//					}
					logDto.setStartVal(baseBorrowDto.getChargeMoney()+"元");
					logDto.setEndVal(harvestDto.getChargeMoney()+"元");
					financeLogService.insert(logDto);
				}
				if(baseBorrowDto.getServiceCharge()!=null && !equal(harvestDto.getServiceCharge(),baseBorrowDto.getServiceCharge())){
					logDto.setBeanColumn("serviceCharge");
					logDto.setColName("服务费");
					//畅贷
//					if(baseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&baseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
//						List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//						if(olist!=null && olist.size()>0){
//							JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//							baseBorrowDto.setServiceCharge(jsonObject.get("serviceCharge")==null?0:Double.parseDouble(jsonObject.get("serviceCharge").toString()));
//						}
//					}
					logDto.setStartVal(baseBorrowDto.getServiceCharge()+"元");
					logDto.setEndVal(harvestDto.getServiceCharge()+"元");
					financeLogService.insert(logDto);
				}
				if(!"03".equals(baseBorrowDto.getProductCode()) && baseBorrowDto.getCustomsPoundage()!=null && !equal(harvestDto.getCustomsPoundage(),baseBorrowDto.getCustomsPoundage())){
					logDto.setBeanColumn("customsPoundage");
					logDto.setColName("关外手续费");
					//畅贷
//					if(baseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&baseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
//						List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//						if(olist!=null && olist.size()>0){
//							JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//							baseBorrowDto.setCustomsPoundage(jsonObject.get("customsPoundage")==null?0:Double.parseDouble(jsonObject.get("customsPoundage").toString()));
//						}
//					}
					logDto.setStartVal(baseBorrowDto.getCustomsPoundage()+"元");
					logDto.setEndVal(harvestDto.getCustomsPoundage()+"元");
					financeLogService.insert(logDto);
				}
				if(baseBorrowDto.getOtherPoundage()!=null && !equal(harvestDto.getOtherPoundage(),baseBorrowDto.getOtherPoundage())){
					logDto.setBeanColumn("otherPoundage");
					logDto.setColName("其他费用");
					//畅贷
//					if(baseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&baseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
//						List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//						if(olist!=null && olist.size()>0){
//							JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//							baseBorrowDto.setOtherPoundage(jsonObject.get("otherPoundage")==null?0:Double.parseDouble(jsonObject.get("otherPoundage").toString()));
//						}
//					}
					logDto.setStartVal(baseBorrowDto.getOtherPoundage()+"元");
					logDto.setEndVal(harvestDto.getOtherPoundage()+"元");
					financeLogService.insert(logDto);
				}
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("cooperativeAgencyId", baseBorrowDto.getCooperativeAgencyId());
				map.put("productId", baseBorrowDto.getCityCode()+baseBorrowDto.getProductCode());
				map.put("borrowingDays", baseBorrowDto.getBorrowingDays());
				map.put("riskGradeId", harvestDto.getRiskGradeId());
				map.put("loanAmount", baseBorrowDto.getLoanAmount());
				RespDataObject<Map<String, Object>> cusMap=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/customer/risk/v/findStageRate", map,Map.class);
				Map<String, Object>rateMap=cusMap.getData();
				String modeid=MapUtils.getString(rateMap,"modeid","0");
				if(!equal(harvestDto.getRate(),baseBorrowDto.getRate())){
					logDto.setBeanColumn("rate");
					logDto.setColName("费率");
					if("1".equals(modeid) && 0!=harvestDto.getRiskGradeId()){
						logDto.setStartVal(baseBorrowDto.getRate()+"%");
						logDto.setEndVal(harvestDto.getRate()+"%");
					}else{  //按天
						logDto.setStartVal(baseBorrowDto.getRate()+"%/天");
						logDto.setEndVal(harvestDto.getRate()+"%/天");
					}
					financeLogService.insert(logDto);
				}
				if(!equal(harvestDto.getOverdueRate(),baseBorrowDto.getOverdueRate())){
					logDto.setBeanColumn("overdueRate");
					logDto.setColName("逾期费率");
					if("1".equals(modeid) && 0!=harvestDto.getRiskGradeId()){
						logDto.setStartVal(baseBorrowDto.getOverdueRate()+"%");
						logDto.setEndVal(harvestDto.getOverdueRate()+"%");
					}else{  //按天
						logDto.setStartVal(baseBorrowDto.getOverdueRate()+"%/天");
						logDto.setEndVal(harvestDto.getOverdueRate()+"%/天");
					}
					financeLogService.insert(logDto);
				}
				//------判断是否修改字段end----------
				//修改订单借款信息
				if(harvestDto.getIsUpdata()==1){
					//修改订单信息（客户姓名，借款期限，借款金额，风控等级，费率，逾期费率，收费金额）
					baseBorrowDto=new OrderBaseBorrowDto();
					baseBorrowDto.setOrderNo(harvestDto.getOrderNo());
//					baseBorrowDto.setIsChangLoan(harvestDto.getIsChangLoan());
					baseBorrowDto.setRate(harvestDto.getRate());
					baseBorrowDto.setOverdueRate(harvestDto.getOverdueRate());
					baseBorrowDto.setChargeMoney(harvestDto.getChargeMoney());
					baseBorrowDto.setRiskGradeId(harvestDto.getRiskGradeId());
					baseBorrowDto.setUpdateUid(dto.getUid());
					baseBorrowDto.setServiceCharge(harvestDto.getServiceCharge());
					baseBorrowDto.setCustomsPoundage(harvestDto.getCustomsPoundage()); //关外手续费
					baseBorrowDto.setOtherPoundage(harvestDto.getOtherPoundage());  //其他费用
					RespStatus staus=httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/borrowother/v/updateBorrow", baseBorrowDto);
					if("FAIL".equals(staus.getCode())){
						rd.setCode(staus.getCode());
						rd.setMsg(staus.getMsg());
						return rd;
					}
				}
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(harvestDto.getOrderNo());
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				//订单列表
				OrderListDto listDto = new OrderListDto();
				if(harvestDto.getType()==2){//费用后置
					orderFlowDto.setCurrentProcessId("backExpenses");
					orderFlowDto.setCurrentProcessName("扣回后置费用");
					orderFlowDto.setNextProcessId("foreclosure");
					orderFlowDto.setNextProcessName("结清原贷款");
					DistributionMemberDto to=new DistributionMemberDto();
					to.setOrderNo(harvestDto.getOrderNo());
					RespDataObject<DistributionMemberDto>  menber =httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", to,DistributionMemberDto.class);  
					if(menber.getData()!=null){
						listDto.setCurrentHandlerUid(menber.getData().getForeclosureMemberUid());//下一处理人结清还款专员
						UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(listDto.getCurrentHandlerUid());
						listDto.setCurrentHandler(userDto.getName());
					}
					//更新放款表 回款处理人
					LendingDto lendingDto=new LendingDto();
					lendingDto.setOrderNo(harvestDto.getOrderNo());
					lendingDto.setReceivableForUid(harvestDto.getUid());  //回款处理人
					lendingService.updatereceivableForUid(lendingDto);
				}else{
					orderFlowDto.setCurrentProcessId("lendingHarvest");
					orderFlowDto.setCurrentProcessName("收利息");
					AllocationFundDto fundDto =new AllocationFundDto();
					fundDto.setOrderNo(harvestDto.getOrderNo());
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
					if(isYunNan){
						orderFlowDto.setNextProcessId("financialStatement");
						orderFlowDto.setNextProcessName("财务制单");
					}else{
						orderFlowDto.setNextProcessId("lending");
						orderFlowDto.setNextProcessName("放款");
					}
					listDto.setCurrentHandlerUid(harvestDto.getUid());//下一处理人
					UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(harvestDto.getUid());
					listDto.setCurrentHandler(userDto.getName());
				}
				//更新流程方法
				goNextNode(orderFlowDto, listDto);  
				rd.setCode(RespStatusEnum.SUCCESS.getCode());
				rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
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
    public boolean equal(double a, double b) {
        if ((a- b> -0.000001) && (a- b) < 0.000001)
            return true;
        else
            return false;
    }
}
