package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.finance.StatementDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.StatementService;
import com.anjbo.utils.CommonDataUtil;

/**
 * 财务制单
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/finance/statement/v")
public class StatementController extends BaseController{
	 	private Logger log = Logger.getLogger(getClass());
	 	@Resource
		private StatementService statementService;
	 	/**
	     * 详情
	     * @param request
	     * @param orderNo
	     * @return
	     */
		@RequestMapping("detail")
		@ResponseBody
		public  RespDataObject<StatementDto> detail(HttpServletRequest request,@RequestBody StatementDto statementDto){
			RespDataObject<StatementDto> resp=new RespDataObject<StatementDto>();
			try {
				resp.setData(statementService.selectStatement(statementDto.getOrderNo()));
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
	     * 添加信息
	     * @param request
	     * @param response
	     * @return
	     */
	    @RequestMapping(value = "/add")
		public @ResponseBody
		RespStatus add(HttpServletRequest request,
				HttpServletResponse response,@RequestBody StatementDto statementDto) {
	    	RespStatus rd = new RespStatus();
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
			try {
				 //判断当前节点
			    if(!isSubmit(statementDto.getOrderNo(), "financialStatement")){	
			    	UserDto dto=getUserDto(request);  //获取用户信息
					statementDto.setCreateUid(dto.getUid());
					statementDto.setUpdateUid(dto.getUid());
			    	statementService.addStatement(statementDto);
					//订单列表
					OrderListDto orderListDto=new OrderListDto();
					//流程表
					OrderFlowDto orderFlowDto=new OrderFlowDto();  
					orderFlowDto.setOrderNo(statementDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("financialStatement");
					orderFlowDto.setCurrentProcessName("财务制单");
					
					orderFlowDto.setHandleUid(dto.getUid());  //当前处理人
					orderFlowDto.setHandleName(dto.getName());
					
					orderFlowDto.setNextProcessId("financialAudit");
					orderFlowDto.setNextProcessName("财务审核");
					
					UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(statementDto.getUid());
					orderListDto.setCurrentHandlerUid(statementDto.getUid());//
					orderListDto.setCurrentHandler(nextUser.getName());
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
			return rd;
		}
	    
	 
}
