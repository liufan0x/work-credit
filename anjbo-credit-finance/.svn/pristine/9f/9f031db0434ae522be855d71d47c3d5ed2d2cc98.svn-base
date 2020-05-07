package com.anjbo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.finance.FinanceLogDto;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
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

/**
 * 房抵贷-核实收费
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/finance/fddIsCharge/v")
public class FddIsChargeController  extends BaseController{
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
//			    lendingHarvestDto.setServiceCharge(baseBorrowDto.getServiceCharge());//服务费
//			    lendingHarvestDto.setCustomsPoundage(baseBorrowDto.getCustomsPoundage()); //关外手续费
//			    lendingHarvestDto.setOtherPoundage(baseBorrowDto.getOtherPoundage());  //其他费用
			    lendingHarvestDto.setModeid(baseBorrowDto.getModeid());
				lendingHarvestDto.setLoanAmount(baseBorrowDto.getLoanAmount());        //借款金额
				lendingHarvestDto.setBorrowingDays(baseBorrowDto.getBorrowingDays());  //借款期限
				lendingHarvestDto.setRate(baseBorrowDto.getRate());					//费率
			    lendingHarvestDto.setOverdueRate(baseBorrowDto.getOverdueRate());		//逾期费率
			    lendingHarvestDto.setChargeMoney(baseBorrowDto.getChargeMoney());		//收费金额
			}
		} catch (Exception e1) {
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
		    if(!isSubmit(harvestDto.getOrderNo(), "isCharge")){
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
//				OrderBaseBorrowDto baseBorrowDto = new OrderBaseBorrowDto();
//				baseBorrowDto.setOrderNo(harvestDto.getOrderNo());
//				RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", baseBorrowDto,OrderBaseBorrowDto.class);
//				baseBorrowDto=  obj.getData();
//				FinanceLogDto logDto=new FinanceLogDto();
//				logDto.setCreateUid(dto.getUid());
//				logDto.setUpdateUid(dto.getUid());
//				logDto.setOrderNo(harvestDto.getOrderNo());
//				logDto.setType(4);
//				if(baseBorrowDto.getServiceCharge()!=null && !equal(harvestDto.getServiceCharge(),baseBorrowDto.getServiceCharge())){
//					logDto.setBeanColumn("serviceCharge");
//					logDto.setColName("服务费");
//					logDto.setStartVal(baseBorrowDto.getServiceCharge()+"元");
//					logDto.setEndVal(harvestDto.getServiceCharge()+"元");
//					financeLogService.insert(logDto);
//				}
//				Map<String, Object> map=new HashMap<String, Object>();
//				map.put("cooperativeAgencyId", baseBorrowDto.getCooperativeAgencyId());
//				map.put("productId", baseBorrowDto.getCityCode()+baseBorrowDto.getProductCode());
//				map.put("borrowingDays", baseBorrowDto.getBorrowingDays());
//				map.put("riskGradeId", harvestDto.getRiskGradeId());
//				map.put("loanAmount", baseBorrowDto.getLoanAmount());
//				RespDataObject<Map<String, Object>> cusMap=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/customer/risk/v/findStageRate", map,Map.class);
//				Map<String, Object>rateMap=cusMap.getData();
//				String modeid=MapUtils.getString(rateMap,"modeid","0");
//				if(!equal(harvestDto.getRate(),baseBorrowDto.getRate())){
//					logDto.setBeanColumn("rate");
//					logDto.setColName("费率");
//					if("1".equals(modeid) && 0!=harvestDto.getRiskGradeId()){
//						logDto.setStartVal(baseBorrowDto.getRate()+"%");
//						logDto.setEndVal(harvestDto.getRate()+"%");
//					}else{  //按天
//						logDto.setStartVal(baseBorrowDto.getRate()+"%/天");
//						logDto.setEndVal(harvestDto.getRate()+"%/天");
//					}
//					financeLogService.insert(logDto);
//				}
//				if(!equal(harvestDto.getOverdueRate(),baseBorrowDto.getOverdueRate())){
//					logDto.setBeanColumn("overdueRate");
//					logDto.setColName("逾期费率");
//					if("1".equals(modeid) && 0!=harvestDto.getRiskGradeId()){
//						logDto.setStartVal(baseBorrowDto.getOverdueRate()+"%");
//						logDto.setEndVal(harvestDto.getOverdueRate()+"%");
//					}else{  //按天
//						logDto.setStartVal(baseBorrowDto.getOverdueRate()+"%/天");
//						logDto.setEndVal(harvestDto.getOverdueRate()+"%/天");
//					}
//					financeLogService.insert(logDto);
//				}
				//------判断是否修改字段end----------
				//修改订单借款信息
				if(harvestDto.getIsUpdata()==1){
					//修改订单信息（客户姓名，借款期限，借款金额，风控等级，费率，逾期费率，收费金额）
					OrderBaseBorrowDto baseBorrowDto=new OrderBaseBorrowDto();
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
				
				orderFlowDto.setCurrentProcessId("isCharge");
				orderFlowDto.setCurrentProcessName("核实收费");
				
				AllocationFundDto fundDto =new AllocationFundDto();
				fundDto.setOrderNo(harvestDto.getOrderNo());
				boolean isSGT=false;
				RespData<Map<String,Object>> respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fundDto, Map.class);
				List<Map<String,Object>> orderList=respData.getData();
				if(orderList!=null&&orderList.size()>0) {
					for(Map<String,Object> o:orderList) {
						if("1000".equals(MapUtils.getString(o, "fundCode"))){
							isSGT=true;
						}
					}
				}
				if(isSGT) {
					orderFlowDto.setNextProcessId("financialStatement");
					orderFlowDto.setNextProcessName("财务制单");
				}else {
					orderFlowDto.setNextProcessId("lending");
					orderFlowDto.setNextProcessName("放款");
				}
				listDto.setCurrentHandlerUid(harvestDto.getUid());//下一处理人
				UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(harvestDto.getUid());
				listDto.setCurrentHandler(userDto.getName());
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
