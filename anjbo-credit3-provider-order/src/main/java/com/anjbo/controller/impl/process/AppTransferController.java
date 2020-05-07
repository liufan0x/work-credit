/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.process;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.AppTransferDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.process.IAppTransferController;
import com.anjbo.service.process.AppTransferService;
import com.anjbo.utils.DateUtils;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@RestController
public class AppTransferController extends OrderBaseController implements IAppTransferController {

	@Resource
	private AppTransferService appTransferService;

	@Resource
	private UserApi userApi;

	@Resource
	private DataApi dataApi;

	/**
	 * 提交
	 * 
	 * @author lic
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AppTransferDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());

			appTransferService.add(dto);

			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("transfer");
			flowDto.setCurrentProcessName("过户");
			flowDto.setNextProcessId("newlicense");
			flowDto.setNextProcessName("领新证");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid()); // 当前处理人
			flowDto.setHandleName(userDto.getName());

			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto.setAppShowValue1(DateUtils.dateToString(dto.getNewlicenseTime(), DateUtils.FMT_TYPE2));
			List<DictDto> dictDtos = dataApi.getDictDtoListByType("landBureau");
			for (DictDto dictDto : dictDtos) {
				if (dictDto.getCode().equals(dto.getNlandBureau())) {
					baseListDto.setAppShowValue2(dictDto.getName());
					break;
				}
			}

			UserDto handlerUserDto = userApi.findUserDtoByUid(dto.getNlandBureauUid());
			baseListDto.setCurrentHandler(handlerUserDto.getName());
			baseListDto.setCurrentHandlerUid(handlerUserDto.getUid());
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
	public RespDataObject<AppTransferDto> processDetails(@RequestBody AppTransferDto dto) {
		RespDataObject<AppTransferDto> resp = new RespDataObject<AppTransferDto>();
		try {
			dto = appTransferService.find(dto);
			List<DictDto> dictDtos = dataApi.getDictDtoListByType("landBureau");
			for (DictDto dictDto : dictDtos) {
				if(dictDto.getCode().equals(dto.getNlandBureau())) {
					dto.setNlandBureauName(dictDto.getName());
				}
			}
			return RespHelper.setSuccessDataObject(resp, dto);
		} catch (Exception e) {
			logger.error("详情异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}

}