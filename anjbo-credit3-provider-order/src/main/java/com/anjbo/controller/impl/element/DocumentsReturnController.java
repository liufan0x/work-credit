/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.element;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.element.IDocumentsReturnController;
import com.anjbo.service.element.DocumentsReturnService;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-03 18:24:30
 * @version 1.0
 */
@RestController
public class DocumentsReturnController extends OrderBaseController implements IDocumentsReturnController {

	@Resource
	private DocumentsReturnService documentsReturnService;

	@Resource
	private UserApi userApi;

	/**
	 * 提交
	 * 
	 * @author lic
	 */
	@Override
	public RespStatus processSubmit(@RequestBody DocumentsReturnDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());

			boolean isRebate = true;
			if (StringUtils.isEmpty(dto.getHandleUid())) {
				dto.setHandleUid(userDto.getUid());
				isRebate = false;
			}

			DocumentsReturnDto temp = new DocumentsReturnDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = documentsReturnService.find(temp);
			if (temp == null) {
				documentsReturnService.insert(dto);
			} else {
				documentsReturnService.update(dto);
			}

			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("elementReturn");
			flowDto.setCurrentProcessName("要件退还");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid()); // 当前处理人
			flowDto.setHandleName(userDto.getName());

			BaseListDto baseListDto = new BaseListDto();
			
			if(isRebate) {
				flowDto.setNextProcessId("rebate");
				flowDto.setNextProcessName("返佣");
				UserDto handlerUserDto = userApi.findUserDtoByUid(dto.getHandleUid());
				baseListDto.setCurrentHandler(handlerUserDto.getName());
				baseListDto.setCurrentHandlerUid(handlerUserDto.getUid());
			}else {
				flowDto.setNextProcessId("wanjie");
				flowDto.setNextProcessName("已完结");
			}
			baseListDto.setOrderNo(dto.getOrderNo());
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
	public RespDataObject<DocumentsReturnDto> processDetails(@RequestBody DocumentsReturnDto dto) {
		RespDataObject<DocumentsReturnDto> resp = new RespDataObject<DocumentsReturnDto>();
		try {
			dto = documentsReturnService.find(dto);
			return RespHelper.setSuccessDataObject(resp, dto);
		} catch (Exception e) {
			logger.error("详情异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}

}