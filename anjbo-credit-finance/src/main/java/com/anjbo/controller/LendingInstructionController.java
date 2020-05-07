package com.anjbo.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.finance.LendingInstructionsDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.LendingHarvestService;
import com.anjbo.service.LendingInstructionService;
import com.anjbo.utils.CommonDataUtil;
/**
 * 待发放款指令
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/finance/lendingInstruction/v")
public class LendingInstructionController extends BaseController{
	private Logger log = Logger.getLogger(getClass());
	
	
	@Resource LendingInstructionService lendingInstructionService;
	@Resource LendingHarvestService harvestService;
	
	 /**
     * 详情
     * @param request
     * @param orderNo
     * @return
     */
	@RequestMapping("detail")
	@ResponseBody
	public  RespDataObject<Map<String, Object>> queryBalance(HttpServletRequest request,@RequestBody LendingInstructionsDto instructionsDto){
		RespDataObject<Map<String, Object>> resp=new RespDataObject<Map<String, Object>>();
		Map<String, Object>map=new HashMap<String, Object>();
		//放款指令信息
		try {
			LendingInstructionsDto insDto = lendingInstructionService.findByInstruction(instructionsDto.getOrderNo());
			try {
				if(insDto==null){
					insDto=new LendingInstructionsDto();
					insDto.setOrderNo(instructionsDto.getOrderNo());
					DocumentsDto basics=new DocumentsDto();
					basics.setOrderNo(instructionsDto.getOrderNo());
					RespDataObject<DocumentsDto> doc=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", basics,DocumentsDto.class);
					basics=doc.getData();
					if(basics!=null && basics.getForeclosureType()!=null){
						insDto.setLendingBankId(basics.getForeclosureType().getBankNameId()==null?0:basics.getForeclosureType().getBankNameId());
						insDto.setOpeningBankId(basics.getForeclosureType().getBankSubNameId()==null?0:basics.getForeclosureType().getBankSubNameId());
						insDto.setBankName(basics.getForeclosureType().getBankCardMaster());
						insDto.setBankAccount(basics.getForeclosureType().getBankNo());
					}
				}
				//获取银行-支行名称
				if(insDto.getLendingBankId()!=null){
					BankDto bankDto =CommonDataUtil.getBankNameById(insDto.getLendingBankId());
					insDto.setLendingBank(bankDto==null ? "":bankDto.getName());
				}
				if(insDto.getOpeningBankId()!=null){
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(insDto.getOpeningBankId());
					insDto.setOpeningBank(subBankDto == null ?"":subBankDto.getName());
				}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			//借款信息（客户姓名，借款期限，借款金额，风控等级，费率，逾期费率，收费金额）
			try {
				OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
				orderBaseBorrowDto.setOrderNo(instructionsDto.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  obj.getData();
				if(baseBorrowDto!=null){
					insDto.setBorrowerName(baseBorrowDto.getBorrowerName());    //客户姓名
					insDto.setIsOnePay(baseBorrowDto.getIsOnePay());  //是否一次性付款
				    insDto.setProductCode(baseBorrowDto.getProductCode());  //产品编码
				    insDto.setCityCode(baseBorrowDto.getCityCode());
				    insDto.setCooperativeAgencyId(baseBorrowDto.getCooperativeAgencyId());  //合作机构Id
//				    insDto.setIsChangLoan(baseBorrowDto.getIsChangLoan());
//					if(baseBorrowDto.getIsChangLoan()==1){ //畅待
//						List<OrderBaseBorrowRelationDto> olist=baseBorrowDto.getOrderBaseBorrowRelationDto();
//						if(olist!=null && olist.size()>0){
//							JSONObject jsonObject=JSONObject.fromObject(olist.get(0));
//							insDto.setLoanAmount(jsonObject.get("loanAmount")==null?0:Double.parseDouble(jsonObject.get("loanAmount").toString())); //借款金额
//							insDto.setBorrowingDays(jsonObject.get("borrowingDays")==null?0:Integer.parseInt(jsonObject.get("borrowingDays").toString()));  //借款期限
//							insDto.setRate(jsonObject.get("rate")==null?0:Double.parseDouble(jsonObject.get("rate").toString()));					//费率
//						    insDto.setOverdueRate(jsonObject.get("overdueRate")==null?0:Double.parseDouble(jsonObject.get("overdueRate").toString()));		//逾期费率
//						    insDto.setChargeMoney(jsonObject.get("chargeMoney")==null?0:Double.parseDouble(jsonObject.get("chargeMoney").toString()));		//收费金额
//						    insDto.setRiskGradeId(jsonObject.get("riskGradeId")==null?0:Integer.parseInt(jsonObject.get("riskGradeId").toString()));  	//风控等级
//						}
//					}else{
						insDto.setLoanAmount(baseBorrowDto.getLoanAmount());        //借款金额
						insDto.setBorrowingDays(baseBorrowDto.getBorrowingDays());  //借款期限
						insDto.setRate(baseBorrowDto.getRate());					//费率
					    insDto.setOverdueRate(baseBorrowDto.getOverdueRate());		//逾期费率
					    insDto.setChargeMoney(baseBorrowDto.getChargeMoney());		//收费金额
					    insDto.setRiskGradeId(baseBorrowDto.getRiskGradeId());  	//风控等级
//					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//资金方信息 （资金方编号）
			String fundName="";
			try {
				AllocationFundDto fundDto =new AllocationFundDto();
				fundDto.setOrderNo(instructionsDto.getOrderNo());
				RespData<AllocationFundDto> ob1=httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fundDto,AllocationFundDto.class);
				List<AllocationFundDto> flist=ob1.getData();
				if(flist!=null){
					for (int i = 0; i < flist.size(); i++) {
						fundName+=flist.get(i).getFundCode()+",";
					}
					fundName=fundName.substring(0, fundName.length()-1);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("fundCode", fundName);
			map.put("instructionsDto",insDto); 
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		resp.setData(map);
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
	RespData<LendingInstructionsDto> add(HttpServletRequest request,
			HttpServletResponse response,@RequestBody LendingInstructionsDto instructionsDto) {
		RespData<LendingInstructionsDto> rd = new RespData<LendingInstructionsDto>();
		try {
			 //判断当前节点
		    if(!isSubmit(instructionsDto.getOrderNo(), "lendingInstructions")){	
		    	String uid= instructionsDto.getCreateUid();  //下一处理人财务
				UserDto dto=getUserDto(request);  //获取用户信息
				instructionsDto.setCreateUid(dto.getUid());
				instructionsDto.setUpdateUid(dto.getUid());
				LendingInstructionsDto insDto = lendingInstructionService.findByInstruction(instructionsDto.getOrderNo());
				if(insDto!=null){
					lendingInstructionService.updateLendingInstruction(instructionsDto);
				}else{
					lendingInstructionService.addLendingInstruction(instructionsDto);
				}
				//订单列表
				OrderListDto orderListDto=new OrderListDto();
				
				//修改订单借款信息
				if(instructionsDto.getType()==1){
					//修改订单信息（客户姓名，借款期限，借款金额，风控等级，费率，逾期费率，收费金额）
					OrderBaseBorrowDto baseBorrowDto=new OrderBaseBorrowDto();
					baseBorrowDto.setOrderNo(instructionsDto.getOrderNo());
//					baseBorrowDto.setIsChangLoan(instructionsDto.getIsChangLoan());
					baseBorrowDto.setBorrowingDays(instructionsDto.getBorrowingDays());
					baseBorrowDto.setLoanAmount(instructionsDto.getLoanAmount());
					baseBorrowDto.setRate(instructionsDto.getRate());
					baseBorrowDto.setOverdueRate(instructionsDto.getOverdueRate());
					baseBorrowDto.setChargeMoney(instructionsDto.getChargeMoney());
					baseBorrowDto.setRiskGradeId(instructionsDto.getRiskGradeId());
					baseBorrowDto.setUpdateUid(dto.getUid());
					RespStatus staus=httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/borrowother/v/updateBorrow", baseBorrowDto);
					if("FAIL".equals(staus.getCode())){
						rd.setCode(staus.getCode());
						rd.setMsg(staus.getMsg());
						return rd;
					}
					orderListDto.setBorrowingAmount(instructionsDto.getLoanAmount()); //更新订单列表借款金额，期限
					orderListDto.setBorrowingDay(instructionsDto.getBorrowingDays());
				}
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(instructionsDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("lendingInstructions");
				orderFlowDto.setCurrentProcessName("发放款指令");
				orderFlowDto.setNextProcessId("lending");
				orderFlowDto.setNextProcessName("放款");
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				//订单列表
//				LendingHarvestDto harvestDto= harvestService.findByHarvest(instructionsDto.getOrderNo()); //下一处理人会计
				UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(uid);
				orderListDto.setCurrentHandlerUid(uid);//下一处理人会计
				orderListDto.setCurrentHandler(userDto.getName());
				//更新流程方法
				goNextNode(orderFlowDto, orderListDto);  
				
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
  
}
