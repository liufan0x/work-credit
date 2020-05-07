package com.anjbo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.finance.LendingPayDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.LendingHarvestService;
import com.anjbo.service.LendingPayService;
import com.anjbo.utils.CommonDataUtil;
/**
 * 待付利息的订单
 * @author YS
 *
 */
@Controller
@RequestMapping("/credit/finance/lendingPay/v")
public class LendingPayController extends BaseController {
	private Logger log = Logger.getLogger(getClass());
	@Resource LendingPayService lendingPayService;
	@Resource LendingHarvestService harvestService;
	 /**
     * 详情
     * @param request
     * @param orderNo
     * @return
     */
	@RequestMapping("detail")
	@ResponseBody
	public  RespDataObject<LendingPayDto> queryBalance(HttpServletRequest request,@RequestBody LendingPayDto payDto){
		RespDataObject<LendingPayDto> resp=new RespDataObject<LendingPayDto>();
		LendingPayDto lendingPayDto = lendingPayService.findByPay(payDto);
		if(lendingPayDto==null){
			lendingPayDto=new LendingPayDto();
			lendingPayDto.setOrderNo(payDto.getOrderNo());
		}
		LendingHarvestDto harvest=new LendingHarvestDto();
		harvest.setType(1);
		harvest.setOrderNo(payDto.getOrderNo());
		LendingHarvestDto harvestDto=harvestService.findByHarvest(harvest);
		if(harvestDto!=null)
		lendingPayDto.setPayInterestMoney(harvestDto.getPayInterestMoney());
		resp.setData(lendingPayDto);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
    /**
     * 添加信息
     * @param request
     * @param lendingPayDto
     * @return
     */
    @RequestMapping(value = "/add")
	public @ResponseBody
	RespData<LendingPayDto> add(HttpServletRequest request,@RequestBody LendingPayDto lendingPayDto) {
		RespData<LendingPayDto> rd = new RespData<LendingPayDto>();
		try {
			 //判断当前节点
		    if(!isSubmit(lendingPayDto.getOrderNo(), "lendingPay")){			
			
				UserDto dto=getUserDto(request);  //获取用户信息
				lendingPayDto.setCreateUid(dto.getUid());
				lendingPayDto.setUpdateUid(dto.getUid());
				LendingPayDto payDto = lendingPayService.findByPay(lendingPayDto);	
				if(payDto!=null){
				    lendingPayService.updatePay(lendingPayDto);
				}else{
					lendingPayService.addLendingPay(lendingPayDto);
				}
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(lendingPayDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("lendingPay");
				orderFlowDto.setCurrentProcessName("付利息");
				orderFlowDto.setNextProcessId("lendingInstructions");
				orderFlowDto.setNextProcessName("发放款指令");
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				//订单列表
				OrderListDto listDto = new OrderListDto();
				//资金方发放款指令uid
				AllocationFundDto fundDto =new AllocationFundDto();
				fundDto.setOrderNo(lendingPayDto.getOrderNo());
				RespData<AllocationFundDto> ob1=httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fundDto,AllocationFundDto.class);
				List<AllocationFundDto> flist=ob1.getData();
				if(flist!=null && flist.size()>0){
					String uid=flist.get(0).getLoanDirectiveUid();
					listDto.setCurrentHandlerUid(uid);//
					UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(uid);
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
			log.info("lendingPay-add Exception ==>", e);
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
    
}
