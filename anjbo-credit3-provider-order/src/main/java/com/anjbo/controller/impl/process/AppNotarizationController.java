/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.process;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.AppNotarizationDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.AuditFinalDto;
import com.anjbo.bean.risk.AuditOfficerDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.process.IAppNotarizationController;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.order.BusinfoService;
import com.anjbo.service.process.AppNotarizationService;
import com.anjbo.service.risk.AllocationFundService;
import com.anjbo.service.risk.AuditFinalService;
import com.anjbo.service.risk.AuditOfficerService;
import com.anjbo.utils.DateUtils;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@RestController
public class AppNotarizationController extends OrderBaseController implements IAppNotarizationController {

	@Resource
	private AppNotarizationService appNotarizationService;

	@Resource
	private UserApi userApi;

	@Resource
	private DataApi dataApi;

	@Resource
	private BaseListService baseListService;

	@Resource
	private AuditOfficerService auditOfficerService;

	@Resource
	private AuditFinalService auditFinalService;

	@Resource
	private AllocationFundService allocationFundService;
	
	@Resource
	private BusinfoService businfoService;

	/**
	 * 提交
	 * 
	 * @author lic
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AppNotarizationDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());

			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			if ("04".equals(baseListDto.getProductCode()) && !"不公证".equals(dto.getNotarizationType())) {
				if(!businfoService.notarizationBusinfoCheck(baseListDto.getOrderNo(), baseListDto.getProductCode())){
					resp.setCode(RespStatusEnum.FAIL.getCode());
					resp.setMsg("公证委托书未传");
				}
			}
			AppNotarizationDto temp = new AppNotarizationDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = appNotarizationService.find(temp);
			if (temp == null) {
				appNotarizationService.insert(dto);
			} else {
				appNotarizationService.update(dto);
			}

			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("notarization");
			flowDto.setCurrentProcessName("公证");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid()); // 当前处理人
			flowDto.setHandleName(userDto.getName());

			String handlerUid = "";
			// 房抵贷流程 审批前置 面签-公证-法务（大于1000W才有）-资料推送
			// 房抵贷流程 审批后置 面签-公证-提单
			// 债务置换 公证-面签
			if ("04".equals(baseListDto.getProductCode())) {
				if (baseListDto.getAuditSort() == 1) {
					if (baseListDto.getBorrowingAmount() >= 3000) {
						AuditOfficerDto auditOfficerDto = new AuditOfficerDto();
						auditOfficerDto.setOrderNo(dto.getOrderNo());
						auditOfficerDto = auditOfficerService.find(auditOfficerDto);
						if (auditOfficerDto != null) {
							handlerUid = auditOfficerDto.getJusticeUid();
						} else {
							AuditFinalDto auditFinalDto = new AuditFinalDto();
							auditFinalDto.setOrderNo(dto.getOrderNo());
							auditFinalDto = auditFinalService.find(auditFinalDto);
							handlerUid = auditFinalDto.getOfficerUid();
						}
						flowDto.setNextProcessId("auditJustice");
						flowDto.setNextProcessName("法务审批");
					} else {
						AllocationFundDto allocationFundDto = new AllocationFundDto();
						allocationFundDto.setOrderNo(dto.getOrderNo());
						List<AllocationFundDto> list = allocationFundService.search(allocationFundDto);
						handlerUid = list == null ? null : list.get(0).getCreateUid();
						flowDto.setNextProcessId("fundDocking");
						flowDto.setNextProcessName("资料推送");
					}
				} else {
					handlerUid = baseListDto.getAcceptMemberUid();
					flowDto.setNextProcessId("placeOrder");
					flowDto.setNextProcessName("提单");
				}
			} else {
				handlerUid = baseListDto.getFacesignUid();
				flowDto.setNextProcessId("facesign");
				flowDto.setNextProcessName("面签");
			}
			UserDto handlerUserDto = userApi.findUserDtoByUid(handlerUid);
			baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			//baseListDto.setAppShowValue1(DateUtils.dateToString(dto.getEstimatedTime(), DateUtils.FMT_TYPE2));
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
	public RespDataObject<AppNotarizationDto> processDetails(@RequestBody AppNotarizationDto dto) {
		RespDataObject<AppNotarizationDto> resp = new RespDataObject<AppNotarizationDto>();
		try {
			dto = appNotarizationService.find(dto);
			if (dto != null && StringUtils.isNotEmpty(dto.getNotarizationAddressCode())) {
				List<DictDto> dictDtos = dataApi.getDictDtoListByType("notaryoffice");
				for (DictDto dictDto : dictDtos) {
					if(dictDto.getCode().equals(dto.getNotarizationAddressCode())) {
						dto.setNotarizationAddress(dictDto.getName());
					}
				}
			}
			return RespHelper.setSuccessDataObject(resp, dto);
		} catch (Exception e) {
			logger.error("详情异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}

}