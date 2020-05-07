package com.anjbo.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.ForeclosureDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ForeclosureService;
import com.anjbo.service.ForensicsService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 结清原贷款
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/process/foreclosure/v")
public class ForeclosureController extends BaseController{

	@Resource ForeclosureService foreclosureService;
	@Resource ForensicsService forensicsService;
	
	/**
	 * h5添加页头部详情公共页
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/commentDetail") 
	public RespDataObject<Map<String, Object>> commentDetail(HttpServletRequest request,@RequestBody  ForeclosureDto foreclosureDto){
		 RespDataObject<Map<String, Object>> resp=new RespDataObject<Map<String, Object>>();
		 Map<String, Object> map = new HashMap<String, Object>();
		 ForeclosureDto dto=foreclosureService.selectForeclosure(foreclosureDto);
		 if(dto==null){
			 dto=new ForeclosureDto();
		 }
		 map.put("foreclosureTimeStr", dto.getForeclosureTimeStr());
		 OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
		 borrowDto.setOrderNo(foreclosureDto.getOrderNo());
		 RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrowDto,OrderBaseBorrowDto.class);
		 OrderBaseBorrowDto baseBorrowDto=  obj.getData();
		 map.put("baseBorrowDto", baseBorrowDto);
		 resp.setData(map);
		 resp.setCode(RespStatusEnum.SUCCESS.getCode());
		 resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		 return resp;
	}
	
	
	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<ForeclosureDto> detail(HttpServletRequest request,@RequestBody  ForeclosureDto foreclosureDto){
		 RespDataObject<ForeclosureDto> resp=new RespDataObject<ForeclosureDto>();
		 ForeclosureDto dto=foreclosureService.selectForeclosure(foreclosureDto);
		 //获取原贷款金额
		 OrderBaseBorrowDto borrowDto=new OrderBaseBorrowDto();
		 borrowDto.setOrderNo(foreclosureDto.getOrderNo());
		 RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrowDto,OrderBaseBorrowDto.class);
		 OrderBaseBorrowDto baseBorrowDto=  obj.getData();
		 if(dto==null){
			 dto=new ForeclosureDto();
			 dto.setOrderNo(foreclosureDto.getOrderNo());
			 if(baseBorrowDto!=null){
				 Integer isBankEnd=baseBorrowDto.getIsOldLoanBank();
				 if(isBankEnd!=null && isBankEnd==1){  //是
					 dto.setIsBankEnd(0);
					 dto.setForeclosureBankNameId(baseBorrowDto.getOldLoanBankNameId());
					 dto.setForeclosureBankName(baseBorrowDto.getOldLoanBankName());  //提单 -原贷款银行
					 dto.setForeclosureBankSubNameId(baseBorrowDto.getOldLoanBankSubNameId());
					 dto.setForeclosureBankSubName(baseBorrowDto.getOldLoanBankSubName());
				 }else if(isBankEnd!=null && isBankEnd==2){
					 dto.setIsBankEnd(1);
					 dto.setForeclosureAddress(baseBorrowDto.getOldLoanBankName());
				 }
			 }
			 
		 }else{
			//获取银行-支行名称
			if(dto.getForeclosureBankNameId()!=null){
				BankDto bankDto =CommonDataUtil.getBankNameById(dto.getForeclosureBankNameId());
				dto.setForeclosureBankName(bankDto==null ? "":bankDto.getName());
			}
			if( dto.getForeclosureBankSubNameId()!=null){
				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(dto.getForeclosureBankSubNameId());
				dto.setForeclosureBankSubName(subBankDto == null ?"":subBankDto.getName());
			}
		 }
		 if(baseBorrowDto!=null && baseBorrowDto.getLoanAmount()!=null){
			 dto.setLoanAmount(baseBorrowDto.getLoanAmount()); //金额
		 }
		 ForensicsDto forensicsDto=new ForensicsDto();
		 forensicsDto.setOrderNo(foreclosureDto.getOrderNo());
		 forensicsDto=forensicsService.selectForensics(forensicsDto);
		 //根据uid获取取证员名称
		 if(forensicsDto!=null){
			//获取取证银行-支行
			dto.setLicenseRevBankSub(forensicsDto.getLicenseRevBankSub());
			dto.setLicenseRevBank(forensicsDto.getLicenseRevBank());
			//获取银行-支行名称
			if(forensicsDto.getLicenseRevBank()!=null && forensicsDto.getLicenseRevBank()>0){
				BankDto bankDto =CommonDataUtil.getBankNameById(dto.getLicenseRevBank());
				dto.setLicenseRevBankName(bankDto==null ? "":bankDto.getName());
			}
			if(forensicsDto.getLicenseRevBankSub()!=null && forensicsDto.getLicenseRevBankSub()>0){
				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(dto.getLicenseRevBankSub());
				dto.setLicenseRevBankSubName(subBankDto == null ?"":subBankDto.getName());
			}
			 dto.setLicenseRevTimeStr(forensicsDto.getLicenseRevTimeStr());
			 dto.setLicenseReverUid(forensicsDto.getLicenseReverUid());
			 UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(forensicsDto.getLicenseReverUid());//根据uid获取名称
			 if(userDto!=null)
			 dto.setLicenseRever(userDto.getName());
		 }
		 UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(dto.getCreateUid());
		 if(userDto!=null){
			 dto.setForeclosureMemberName(userDto.getName());
		 }
		 try {
			 SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			 if(dto.getLicenseRevTime()!=null){
				 String times=dateFormat.format(dto.getLicenseRevTime());
				 dto.setLicenseRevTime(dateFormat.parse(times));
			 }
			 if(dto.getForeclosureTime()!=null){
				 String times=dateFormat.format(dto.getForeclosureTime());
				 dto.setForeclosureTime(dateFormat.parse(times));
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 resp.setData(dto);
		 resp.setCode(RespStatusEnum.SUCCESS.getCode());
		 resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		 return resp;
	}
	
	/**
	 * 添加
	 * @param request
	 * @param response
	 * @return
	 */
	   @RequestMapping(value = "/add")
		public @ResponseBody
		RespStatus add(HttpServletRequest request,
				HttpServletResponse response,@RequestBody ForeclosureDto foreclosureDto) {
		    RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				 //判断当前节点
//			    if(!isWithdraw(foreclosureDto.getOrderNo(), "foreclosure")){		
	 				UserDto dto=getUserDto(request);  //获取用户信息
					foreclosureDto.setCreateUid(dto.getUid());
					foreclosureDto.setUpdateUid(dto.getUid());
					foreclosureService.addForeclosure(foreclosureDto);
					//流程表
					OrderFlowDto orderFlowDto=new OrderFlowDto();  
					orderFlowDto.setOrderNo(foreclosureDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("foreclosure");
					orderFlowDto.setCurrentProcessName("结清原贷款");
					orderFlowDto.setNextProcessId("forensics");
					orderFlowDto.setNextProcessName("取证");
					orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
					orderFlowDto.setHandleName(dto.getName());
					//订单列表
					OrderListDto listDto = new OrderListDto();
					listDto.setCurrentHandlerUid(foreclosureDto.getLicenseReverUid());//下一处理人
					UserDto dto2= CommonDataUtil.getUserDtoByUidAndMobile(foreclosureDto.getLicenseReverUid());
					if(dto2!=null)
					   listDto.setCurrentHandler(dto2.getName());
					
					//填充列表预计取证,预计取证地点
					OrderBaseBorrowDto borrow=new OrderBaseBorrowDto();
					borrow.setOrderNo(foreclosureDto.getOrderNo());
					borrow=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow, OrderBaseBorrowDto.class);
					if(borrow.getIsLoanBank()!=null&&borrow.getIsLoanBank()==2){
						listDto.setAppShowValue2(borrow.getLoanBankName());
					}else{
						listDto.setAppShowValue2(CommonDataUtil.getBankNameById(borrow.getLoanBankNameId()).getName()+"-"+CommonDataUtil.getSubBankNameById(borrow.getLoanSubBankNameId()).getName());
					}
					listDto.setAppShowValue1(DateUtil.getDateByFmt(foreclosureDto.getLicenseRevTime(), DateUtil.FMT_TYPE2));
					
					//更新流程方法
					if(!isSubmit(foreclosureDto.getOrderNo(), "foreclosure")){
						goNextNode(orderFlowDto, listDto);  
					}else{
						listDto.setOrderNo(foreclosureDto.getOrderNo());
						httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", listDto);
					}
//			    }else{
//			    	rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
//					rd.setMsg(RespStatusEnum.NOADOPT_ERROR.getMsg());
//			    }
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
		}
	   
	   
	   /**
	    * 撤回
	    * @param request
	    * @param response
	    * @param managerAuditDto
	    * @return
	    */
	   @RequestMapping(value = "/updwithdraw")
		public @ResponseBody
		RespStatus updwithdraw(HttpServletRequest request,
				HttpServletResponse response,@RequestBody ForeclosureDto foreclosureDto) {
	    	RespStatus rd = new RespStatus();
			try {
				
				rd.setCode(RespStatusEnum.SUCCESS.getCode());
				rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
		}
}
