package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.FddReleaseDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ReleaseService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 解压
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/process/release/v")
public class ReleaseController extends BaseController{

	@Resource ReleaseService releaseService;
	
	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<FddReleaseDto> detail(HttpServletRequest request,@RequestBody  FddReleaseDto releaseDto){
		 RespDataObject<FddReleaseDto> resp=new RespDataObject<FddReleaseDto>();
		 FddReleaseDto fddReleaseDto=releaseService.findByRelease(releaseDto.getOrderNo());
		 if(fddReleaseDto!=null){
			 UserDto fddDto=CommonDataUtil.getUserDtoByUidAndMobile(fddReleaseDto.getReleaseUid());
			 if(fddDto!=null){
				 fddReleaseDto.setReleaseName(fddDto.getName());
			 }
		 }
		 resp.setData(fddReleaseDto);
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
				HttpServletResponse response,@RequestBody FddReleaseDto releaseDto) {
		    RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				//判断当前节点
				if(StringUtils.isBlank(releaseDto.getOrderNo())){
					rd.setMsg("保存审核信息失败,订单编号不能为空!");
					return rd;
				}
				boolean isSubmit = isSubmit(releaseDto.getOrderNo(),"release");
//				if(isSubmit){
//					rd.setMsg("该订单已经被提交");
//					return rd;
//				}
				boolean isWithdraw = isWithdraw(releaseDto.getOrderNo(),"release");
				if(isWithdraw &&!isSubmit){
					rd.setCode(RespStatusEnum.FAIL.getCode());
					rd.setMsg("该订单已经被撤回");
					return rd;
				}
				UserDto dto=getUserDto(request);  //获取用户信息
				releaseDto.setCreateUid(dto.getUid());
				releaseDto.setUpdateUid(dto.getUid());
				releaseService.addRelease(releaseDto);
				//流程表
				OrderFlowDto orderFlowDto=new OrderFlowDto();  
				//订单列表
				OrderListDto listDto = new OrderListDto();
				orderFlowDto.setOrderNo(releaseDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("release");
				orderFlowDto.setCurrentProcessName("解押");
				orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
				orderFlowDto.setHandleName(dto.getName());
				boolean isRebate=false; //是否返佣
				LendingHarvestDto harvestDto =new LendingHarvestDto();
				harvestDto.setOrderNo(releaseDto.getOrderNo());
				RespDataObject<LendingHarvestDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/finance/fddIsCharge/v/appDetail", harvestDto,LendingHarvestDto.class);
				harvestDto =  obj.getData();
				String releaseUid="";
				if(null!=harvestDto && harvestDto.getReturnMoney()>0){
					if(null!=harvestDto.getRebateUid()){
						releaseUid=harvestDto.getRebateUid();
						isRebate=true;
					}
				}
				if(isRebate){
					orderFlowDto.setNextProcessId("rebate");
					orderFlowDto.setNextProcessName("返佣");
					UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(releaseUid);
					listDto.setCurrentHandlerUid(releaseUid);
					listDto.setCurrentHandler(nextUser.getName());
				}else{
					orderFlowDto.setNextProcessId("wanjie");
					orderFlowDto.setNextProcessName("已完结");
					listDto.setCurrentHandlerUid("-");
					listDto.setCurrentHandler("-");
					listDto.setProcessId("wanjie");
					listDto.setState("已完结");
				}
				
				//更新流程方法
				 if(!isSubmit(releaseDto.getOrderNo(), "release")){		
					 goNextNode(orderFlowDto, listDto);
				 }else{
					 listDto.setOrderNo(releaseDto.getOrderNo());
					 httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/base/v/updateOrderList", listDto);
				 }
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
	   }
}
