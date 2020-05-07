package com.anjbo.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.ManagerAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ManagerAuditService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.StringUtil;

/**
 * 经理审批/分配订单
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/process/managerAudit/v")
public class ManagerAuditController extends BaseController{

	@Resource ManagerAuditService managerAuditService;
	
	/**
	 * 详情
	 * @param request
	 * @param notarizationDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<ManagerAuditDto> detail(HttpServletRequest request,@RequestBody  ManagerAuditDto managerAuditDto){
		 RespDataObject<ManagerAuditDto> resp=new RespDataObject<ManagerAuditDto>();
		 ManagerAuditDto dto=managerAuditService.selectManagerAudit(managerAuditDto);
		 if(dto==null){
			 dto=new ManagerAuditDto();
			 dto.setOrderNo(managerAuditDto.getOrderNo());
		 }
		 String  managerAuditUid=dto.getCreateUid();  //经理uid
		 if(managerAuditUid!=null){
			 UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(managerAuditUid);//根据uid获取名称
			 dto.setManagerAuditName(userDto.getName());
		 }
		 if(isBack(managerAuditDto.getOrderNo(), "managerAudit")){  //已退回
			 dto.setStatus(1);
		 }else{   //无退回
			 dto.setStatus(2);
		 }
		 if(dto.getRemark()==null){
			 dto.setRemark("");
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
		RespData<ManagerAuditDto> add(HttpServletRequest request,
				HttpServletResponse response,@RequestBody ManagerAuditDto managerAuditDto) {
			RespData<ManagerAuditDto> rd = new RespData<ManagerAuditDto>();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				//判断当前节点
			    if(!isSubmit(managerAuditDto.getOrderNo(), "managerAudit")){
			    	if(StringUtil.isNotBlank(managerAuditDto.getNextHandleUid())) {
			    		managerAuditDto.setUid(managerAuditDto.getNextHandleUid());
			    	}
					UserDto dto=getUserDto(request);  //获取用户信息
					managerAuditDto.setCreateUid(dto.getUid());
					managerAuditDto.setUpdateUid(dto.getUid());
					managerAuditDto.setAuditFirstUid(managerAuditDto.getUid());
					managerAuditDto.setAuditTime(new Date());
					 ManagerAuditDto aDto=managerAuditService.selectManagerAudit(managerAuditDto);
					 if(aDto==null){
						 managerAuditService.addManagerAudit(managerAuditDto);
					 }else{
						 managerAuditService.updateManagerAudit(managerAuditDto);
					 }
					 	//流程表
						OrderFlowDto orderFlowDto=new OrderFlowDto();  
						orderFlowDto.setOrderNo(managerAuditDto.getOrderNo());
						orderFlowDto.setCurrentProcessId("managerAudit");
						orderFlowDto.setCurrentProcessName("经理审批");
						orderFlowDto.setNextProcessId("auditFirst");
						orderFlowDto.setNextProcessName("风控初审");
						orderFlowDto.setUpdateUid(dto.getUid());
						orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
						orderFlowDto.setHandleName(dto.getName());
						//订单列表
						OrderListDto listDto = new OrderListDto();
						UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(managerAuditDto.getUid());//根据uid获取名称
						listDto.setCurrentHandlerUid(userDto.getUid());//下一处理人初审
						listDto.setCurrentHandler(userDto.getName());
						//更新流程方法
						goNextNode(orderFlowDto, listDto);
			    }else{
			    	rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
					rd.setMsg(RespStatusEnum.NOADOPT_ERROR.getMsg());
			    }
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
		}
	   /**
	    * 退回
	    * @param request
	    * @param response
	    * @param managerAuditDto
	    * @return
	    */
	   @RequestMapping(value = "/updwithdraw")
		public @ResponseBody
		RespStatus updwithdraw(HttpServletRequest request,
				HttpServletResponse response,@RequestBody ManagerAuditDto managerAuditDto) {
	    	RespStatus rd = new RespStatus();
			try {
				UserDto dto=getUserDto(request);   //获取用户信息
				managerAuditDto.setAuditTime(new Date());
				managerAuditDto.setStatus(2);
				managerAuditDto.setUpdateUid(dto.getUid());
				managerAuditService.updateStatus(managerAuditDto);  //更新状态为未通过
				rd.setCode(RespStatusEnum.SUCCESS.getCode());
				rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			} catch (Exception e) {
				e.printStackTrace();
				rd.setCode(RespStatusEnum.FAIL.getCode());
				rd.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			return rd;
		}
	   
	   /**
		 * 根据orderNo查询列表(统计报表用)
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/queryManagerAuditList")
		public RespDataObject<List<ManagerAuditDto>> queryManagerAuditList(HttpServletRequest request,@RequestBody ManagerAuditDto managerAuditDto){
			RespDataObject<List<ManagerAuditDto>> resp = new RespDataObject<List<ManagerAuditDto>>();
			try {
				List<ManagerAuditDto> list=managerAuditService.selectManagerAll(managerAuditDto);
				RespHelper.setSuccessDataObject(resp, list);
			} catch (Exception e) {
				RespHelper.setFailDataObject(resp, null, "查询列表失败");
				e.printStackTrace();
			}
			return resp;
		}
		
		/**
		 * 查询经理审批总数
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/managerCount")
		public RespDataObject<Integer> managerCount(HttpServletRequest request,@RequestBody ManagerAuditDto managerAuditDto){
			RespDataObject<Integer> resp = new RespDataObject<Integer>();
			try {
				int count=managerAuditService.selectManagerCount();
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
				resp.setData(count);
			} catch (Exception e) {
				RespHelper.setFailDataObject(resp, null, "查询总数失败");
				e.printStackTrace();
			}
			return resp;
		}
		
	
}
