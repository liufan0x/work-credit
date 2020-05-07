/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.process;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.AuditManagerDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.process.IAuditManagerController;
import com.anjbo.service.process.AuditManagerService;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@RestController
public class AuditManagerController extends OrderBaseController implements IAuditManagerController {

	@Resource
	private AuditManagerService auditManagerService;

	@Resource
	private UserApi userApi;

	/**
	 * 提交
	 * 
	 * @author lic
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AuditManagerDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			dto.setAuditFirstUid(dto.getNextHandleUid());
			AuditManagerDto tempDto = new AuditManagerDto();
			tempDto.setOrderNo(dto.getOrderNo());
			tempDto = auditManagerService.find(tempDto);
			if(tempDto == null) {
				auditManagerService.insert(dto);
			}else {
				auditManagerService.update(dto);
			}
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("managerAudit");
			flowDto.setCurrentProcessName("分配订单");
			flowDto.setNextProcessId("auditFirst");
			flowDto.setNextProcessName("风控初审");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid());  //当前处理人
			flowDto.setHandleName(userDto.getName());
			BaseListDto baseListDto = new BaseListDto();
			if(StringUtil.isNotEmpty(dto.getAuditFirstUid())) {
				UserDto firstUserDto = userApi.findUserDtoByUid(dto.getAuditFirstUid());
				baseListDto.setCurrentHandler(firstUserDto.getName());
				baseListDto.setCurrentHandlerUid(firstUserDto.getUid());
			}
			goNextNode(flowDto, baseListDto);
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
	public RespDataObject<AuditManagerDto> processDetails(@RequestBody AuditManagerDto dto) {
		RespDataObject<AuditManagerDto> resp = new RespDataObject<AuditManagerDto>();
		try {
			dto = auditManagerService.find(dto);
			UserDto managerAuditUserDto = userApi.findUserDtoByUid(dto.getUpdateUid());
			dto.setManagerAuditName(managerAuditUserDto.getName());
			return RespHelper.setSuccessDataObject(resp, dto);
		} catch (Exception e) {
			logger.error("详情异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}

}