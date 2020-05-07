/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.process;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.DistributionMemberDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.process.IDistributionMemberController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.process.DistributionMemberService;
import com.anjbo.utils.DateUtils;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@RestController
public class DistributionMemberController extends OrderBaseController implements IDistributionMemberController{

	@Resource private DistributionMemberService distributionMemberService;

	@Resource private BaseListService baseListService;
	
	@Resource private BaseBorrowService baseBorrowService;
	
	@Resource private UserApi userApi;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody DistributionMemberDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			DistributionMemberDto temp = new DistributionMemberDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = distributionMemberService.find(temp);
			if(temp == null) {
				distributionMemberService.insert(dto);
			}else {
				distributionMemberService.update(dto);
			}
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("repaymentMember");
			flowDto.setCurrentProcessName("指派还款专员");
			flowDto.setNextProcessId("element");
			flowDto.setNextProcessName("要件校验");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid());  //当前处理人
			flowDto.setHandleName(userDto.getName());
			
			BaseBorrowDto baseBorrowDto = new BaseBorrowDto();
			baseBorrowDto.setOrderNo(dto.getOrderNo());
			baseBorrowDto = baseBorrowService.find(baseBorrowDto);
			UserDto handlerUserDto = userApi.findUserDtoByUid(baseBorrowDto.getElementUid());
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto.setCurrentHandler(handlerUserDto.getName());
			baseListDto.setCurrentHandlerUid(handlerUserDto.getUid());
			goNextNode(flowDto, baseListDto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<DistributionMemberDto> processDetails(@RequestBody DistributionMemberDto dto) {
		RespDataObject<DistributionMemberDto> resp = new RespDataObject<DistributionMemberDto>();
		try {
			dto = distributionMemberService.find(dto); 
			if(StringUtils.isNotBlank(dto.getForeclosureMemberUid())) {
				UserDto userDto = userApi.findUserDtoByUid(dto.getForeclosureMemberUid());
				dto.setForeclosureMemberName(userDto.getName());
			}
			return RespHelper.setSuccessDataObject(resp,dto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}