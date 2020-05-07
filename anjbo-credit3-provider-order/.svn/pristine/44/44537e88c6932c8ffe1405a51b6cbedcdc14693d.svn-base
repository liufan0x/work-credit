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
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.AppCancellationDto;
import com.anjbo.bean.process.AppMortgageDto;
import com.anjbo.bean.process.AppTransferDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.process.IAppCancellationController;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.process.AppCancellationService;
import com.anjbo.service.process.AppMortgageService;
import com.anjbo.service.process.AppTransferService;
import com.anjbo.utils.DateUtils;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:32
 * @version 1.0
 */
@RestController
public class AppCancellationController extends OrderBaseController implements IAppCancellationController{

	@Resource private AppCancellationService appCancellationService;
	
	@Resource private AppMortgageService appMortgageService;
	
	@Resource private AppTransferService appTransferService;
	
	@Resource private BaseListService baseListService;
	
	@Resource private UserApi userApi;
	
	@Resource private DataApi dataApi;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AppCancellationDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			appCancellationService.add(dto);
			
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("cancellation");
			flowDto.setCurrentProcessName("注销");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid());  //当前处理人
			flowDto.setHandleName(userDto.getName());
			
			if("02".equals(baseListDto.getProductCode())||"05".equals(baseListDto.getProductCode())) {
				AppMortgageDto appMortgageDto = new AppMortgageDto();
				appMortgageDto.setOrderNo(dto.getOrderNo());
				appMortgageDto.setCreateUid(dto.getCreateUid());
				appMortgageDto.setUpdateUid(dto.getCreateUid());
				appMortgageDto.setMortgageTime(dto.getTransferTime());
				appMortgageDto.setMlandBureau(dto.getTlandBureau());  //国土局
				appMortgageDto.setMlandBureauName(dto.getTlandBureauName());
				appMortgageDto.setMlandBureauUid(dto.getTlandBureauUid()); //驻点
				appMortgageDto.setMlandBureauUserName(dto.getTlandBureauUserName());
				appMortgageService.add(appMortgageDto);
				flowDto.setNextProcessId("mortgage");
				flowDto.setNextProcessName("抵押");
			}else {
				AppTransferDto appTransferDto = new AppTransferDto();
				appTransferDto.setOrderNo(dto.getOrderNo());
				appTransferDto.setCreateUid(dto.getCreateUid());
				appTransferDto.setUpdateUid(dto.getCreateUid());
				appTransferDto.setTransferTime(dto.getTransferTime());
				appTransferDto.setTlandBureau(dto.getTlandBureau());  //国土局
				appTransferDto.setTlandBureauName(dto.getTlandBureauName());
				appTransferDto.setTlandBureauUid(dto.getTlandBureauUid()); //驻点
				appTransferDto.setTlandBureauUserName(dto.getTlandBureauUserName());
				appTransferService.add(appTransferDto);
				flowDto.setNextProcessId("transfer");
				flowDto.setNextProcessName("过户");
			}
			
			baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto.setAppShowValue1(DateUtils.dateToString(dto.getTransferTime(), DateUtils.FMT_TYPE2));
			List<DictDto> dictDtos = dataApi.getDictDtoListByType("landBureau");
			for (DictDto dictDto : dictDtos) {
				if(dictDto.getCode().equals(dto.getTlandBureau())) {
					baseListDto.setAppShowValue2(dictDto.getName());
					break;
				}
			}
			UserDto handlerUserDto = userApi.findUserDtoByUid(dto.getTlandBureauUid());
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
	public RespDataObject<AppCancellationDto> processDetails(@RequestBody AppCancellationDto dto) {
		RespDataObject<AppCancellationDto> resp = new RespDataObject<AppCancellationDto>();
		try {
			dto = appCancellationService.find(dto);
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			if("02".equals(baseListDto.getProductCode())||"05".equals(baseListDto.getProductCode())) {
				AppMortgageDto appMortgageDto = new AppMortgageDto();
				appMortgageDto.setOrderNo(dto.getOrderNo());
				appMortgageDto = appMortgageService.find(appMortgageDto);
				dto.setTransferTime(appMortgageDto.getMortgageTime());
				dto.setTlandBureau(appMortgageDto.getMlandBureau());
				dto.setTlandBureauName(appMortgageDto.getMlandBureauName());
				dto.setTlandBureauUid(appMortgageDto.getMlandBureauUid());
				dto.setTlandBureauUserName(appMortgageDto.getMlandBureauUserName());
			}else {
				AppTransferDto appTransferDto = new AppTransferDto();
				appTransferDto.setOrderNo(dto.getOrderNo());
				appTransferDto = appTransferService.find(appTransferDto);
				dto.setTransferTime(appTransferDto.getTransferTime());
				dto.setTlandBureau(appTransferDto.getTlandBureau());
				dto.setTlandBureauName(appTransferDto.getTlandBureauName());
				dto.setTlandBureauUid(appTransferDto.getTlandBureauUid());
				dto.setTlandBureauUserName(appTransferDto.getTlandBureauUserName());
			}
			List<DictDto> dictDtos = dataApi.getDictDtoListByType("landBureau");
			for (DictDto dictDto : dictDtos) {
				if(dictDto.getCode().equals(dto.getClandBureau())) {
					dto.setClandBureauName(dictDto.getName());
				}
				if(dictDto.getCode().equals(dto.getTlandBureau())) {
					dto.setTlandBureauName(dictDto.getName());
				}
			}
			return RespHelper.setSuccessDataObject(resp,dto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}