package com.anjbo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.finance.LendingInterestDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.LendingInterestService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;

/**
 * 房抵贷-收费
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/finance/fddCharge/v")
public class FddChargeController  extends BaseController{
	private Logger log = Logger.getLogger(getClass());
	@Resource LendingInterestService lendingInterestService;
	 /**
     * 详情
     * @param request
     * @param orderNo
     * @return
     */
	@RequestMapping("detail")
	@ResponseBody
	public  RespDataObject<Map<String, Object>> queryBalance(HttpServletRequest request,@RequestBody LendingInterestDto interestDto){
		RespDataObject<Map<String, Object>> resp=new RespDataObject<Map<String, Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		LendingInterestDto lendingInterestDto = lendingInterestService.findByInterest(interestDto);
		if(lendingInterestDto==null){
			lendingInterestDto=new LendingInterestDto();
			lendingInterestDto.setOrderNo(interestDto.getOrderNo());
		}
		map.put("interest", lendingInterestDto);
		resp.setData(map);
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
	RespData<LendingInterestDto> add(HttpServletRequest request,
			HttpServletResponse response,@RequestBody LendingInterestDto interestDto) {
		RespData<LendingInterestDto> rd = new RespData<LendingInterestDto>();
		rd.setCode(RespStatusEnum.FAIL.getCode());
		rd.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			if(StringUtils.isBlank(interestDto.getUid())){
				rd.setMsg("下个处理人不能为空");
				return rd;
			}
			 //判断当前节点
		    if(!isSubmit(interestDto.getOrderNo(), "charge")){
				UserDto dto=getUserDto(request);  //获取用户信息
				interestDto.setCreateUid(dto.getUid());
				interestDto.setUpdateUid(dto.getUid());
				LendingInterestDto lendingInterestDto = lendingInterestService.findByInterest(interestDto);
				if(lendingInterestDto!=null){  
					lendingInterestService.updateInterest(interestDto);
				}else{
					lendingInterestService.addLendingInterest(interestDto);
				}
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				orderFlowDto.setOrderNo(interestDto.getOrderNo());
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				//订单列表
				OrderListDto listDto = new OrderListDto();
				orderFlowDto.setCurrentProcessId("charge");
				orderFlowDto.setCurrentProcessName("收费");
				orderFlowDto.setNextProcessId("isCharge");
				orderFlowDto.setNextProcessName("核实收费");
				listDto.setCurrentHandlerUid(interestDto.getUid());//下一处理人
				UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(interestDto.getUid());
				listDto.setCurrentHandler(userDto.getName());
				//更新流程方法
				goNextNode(orderFlowDto, listDto);  
				rd.setCode(RespStatusEnum.SUCCESS.getCode());
				rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
				//发送短信
				OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
				orderBaseBorrowDto.setOrderNo(interestDto.getOrderNo());
				HttpUtil http = new HttpUtil();
				RespDataObject<OrderBaseBorrowDto> orderobj=http.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  orderobj.getData();
				String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
				String ProductName="债务置换";
				if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
					ProductName=baseBorrowDto.getProductName();
				}
				//发送给操作人
				AmsUtil.smsSend(userDto.getMobile(), ipWhite, Constants.SMS_TEMPLATE_MANAGER, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"核实收费");
			
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
