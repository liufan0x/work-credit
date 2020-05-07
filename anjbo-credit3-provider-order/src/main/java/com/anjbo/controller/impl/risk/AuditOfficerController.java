/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.risk;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.risk.AuditFinalDto;
import com.anjbo.bean.risk.AuditOfficerDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.SMSConstants;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.risk.IAuditOfficerController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.risk.AuditFinalService;
import com.anjbo.service.risk.AuditOfficerService;
import com.anjbo.utils.AmsUtil;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@RestController
public class AuditOfficerController extends OrderBaseController implements IAuditOfficerController {

	@Resource
	private AuditOfficerService auditOfficerService;

	@Resource
	private UserApi userApi;

	@Resource
	private BaseBorrowService baseBorrowService;

	@Resource
	private BaseListService baseListService;
	
	@Resource
	private AuditFinalService auditFinalService;

	/**
	 * 提交
	 * 
	 * @author lic
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AuditOfficerDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());

			dto.setCreateUid(userDto.getUid());
			dto.setHandleUid(userDto.getUid());
			dto.setAuditTime(new Date());
			dto.setAuditStatus(1);

			AuditOfficerDto temp = new AuditOfficerDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = auditOfficerService.find(temp);
			if (temp == null) {
				auditOfficerService.insert(dto);
			} else {
				auditOfficerService.update(dto);
			}
			//更新推送金融机构操作人
			AuditFinalDto finalDto = new AuditFinalDto();
			finalDto.setOrderNo(dto.getOrderNo());
			finalDto.setAllocationFundUid(dto.getNextHandleUid());
			auditFinalService.update(finalDto);
			
			UserDto nextUser = userApi.findUserDtoByUid(dto.getNextHandleUid());

			FlowDto next = new FlowDto();
			next.setOrderNo(dto.getOrderNo());
			next.setCurrentProcessId("auditOfficer");
			next.setCurrentProcessName("首席风险官审批");
			next.setHandleUid(userDto.getUid());
			next.setHandleName(userDto.getName());
			next.setUpdateUid(userDto.getUid());
			
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			logger.info("业务类型："+baseListDto.getProductCode());
			if("05".equals(baseListDto.getProductCode())) {//提放，提交流程到签订投保单(受理员操作)
				next.setNextProcessId("signInsurancePolicy");
				next.setNextProcessName("签订投保单");
				BaseListDto orderListDto = new BaseListDto();
				orderListDto.setOrderNo(dto.getOrderNo());
				orderListDto.setCurrentHandler(baseListDto.getAcceptMemberName());
				orderListDto.setCurrentHandlerUid(baseListDto.getAcceptMemberUid());	
				goNextNode(next,orderListDto);
			}else {
				next.setNextProcessId("allocationFund");
				next.setNextProcessName("推送金融机构");
				BaseListDto orderListDto = new BaseListDto();
				orderListDto.setOrderNo(dto.getOrderNo());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
				goNextNode(next, orderListDto);
			}

			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("提交异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 详情
	 * 
	 * @author lic
	 */
	@Override
	public RespDataObject<AuditOfficerDto> processDetails(@RequestBody AuditOfficerDto obj) {
		RespDataObject<AuditOfficerDto> result = new RespDataObject<AuditOfficerDto>();
		try {
			// if(StringUtils.isBlank(obj.getOrderNo())){
			// result.setMsg("查询首席风险官审批详情异常,缺少订单编号!");
			// return result;
			// }
			obj = auditOfficerService.find(obj);
			UserDto user = null;
			if (StringUtils.isNotBlank(obj.getHandleUid())) {
				user = userApi.findUserDtoByUid(obj.getHandleUid());
			}
			if (null != user && null != obj) {
				obj.setHandleName(user.getName());
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return RespHelper.setSuccessDataObject(result, obj);
		} catch (Exception e) {
			logger.error("详情异常,参数：" + obj.toString(), e);
			return RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
		}
	}

}