/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.contract.ContractListDto;
import com.anjbo.bean.contract.FieldInputDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.contract.IContractListController;
import com.anjbo.service.contract.ContractListService;
import com.anjbo.service.contract.FieldInputService;
import com.anjbo.utils.SingleUtils;

import org.apache.commons.lang3.StringUtils;



/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-09-20 18:11:02
 * @version 1.0
 */
@RestController
public class ContractListController extends BaseController implements IContractListController {

	@Resource
	private ContractListService contractListService;
	
	@Resource
	private FieldInputService fieldInputService;

	@Resource
	private UserApi userApi;
	
	@Resource 
	private OrderApi orderApi;
	
	

	@SuppressWarnings("unchecked")
	@Override
	public RespDataObject<String> showPDF(@RequestBody ContractListDto dto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			List<FieldInputDto> fieldInputDtos = fieldInputService.search(null);
			Map<String, String> map = dto.getJsonOject();
			for (FieldInputDto fieldInputDto : fieldInputDtos) {
				if(StringUtils.isNotEmpty(fieldInputDto.getValue()) && !map.containsKey(fieldInputDto.getValue()) ) {
					map.put(fieldInputDto.getValue(), "");
				}
			}
			dto.setJsonOject(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SingleUtils.getRestTemplate(60).postForObject(Constants.LINK_ANJBO_FS_URL + "/fs/pdf/showPDF", SingleUtils.getHttpEntity(dto,headers), RespDataObject.class);
	 }
	/**
	 * 分页查询
	 * 
	 * @author Generator
	 */
	@Override
	public RespPageData<ContractListDto> page(@RequestBody ContractListDto dto) {
		RespPageData<ContractListDto> resp = new RespPageData<ContractListDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(contractListService.search(dto));
			resp.setTotal(contractListService.count(dto));
		} catch (Exception e) {
			logger.error("分页异常,参数：" + dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询
	 * 
	 * @author Generator
	 */
	@Override
	public RespData<ContractListDto> search(@RequestBody ContractListDto dto) {
		RespData<ContractListDto> resp = new RespData<ContractListDto>();
		try {
			return RespHelper.setSuccessData(resp, contractListService.search(dto));
		} catch (Exception e) {
			logger.error("查询异常,参数：" + dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<ContractListDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * 
	 * @author Generator
	 */
	@Override
	public RespDataObject<ContractListDto> find(@RequestBody ContractListDto dto) {
		RespDataObject<ContractListDto> resp = new RespDataObject<ContractListDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, contractListService.find(dto));
		} catch (Exception e) {
			logger.error("查找异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ContractListDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * 
	 * @author Generator
	 */
	@Override
	public RespDataObject<ContractListDto> add(@RequestBody ContractListDto dto) {
		RespDataObject<ContractListDto> resp = new RespDataObject<ContractListDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, contractListService.insert(dto));
		} catch (Exception e) {
			logger.error("新增异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ContractListDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * 
	 * @author Generator
	 */
	@Override
	public RespStatus edit(@RequestBody ContractListDto dto) {
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			contractListService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("编辑异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 删除
	 * 
	 * @author Generator
	 */
	@Override
	public RespStatus delete(@RequestBody ContractListDto dto) {
		RespStatus resp = new RespStatus();
		try {
			contractListService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("删除异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 重置
	 * 
	 * @author Generator
	 */
	@Override
	public RespStatus reset(@RequestBody ContractListDto dto) {
		RespStatus resp = new RespStatus();
		try {
			if(StringUtils.isNotEmpty(dto.getOrderNo())) {
				BaseListDto baseListDto = orderApi.findByListOrderNo(dto.getOrderNo());
				dto.setAgencyId(baseListDto.getAgencyId());
				dto.setCityCode(baseListDto.getCityCode());
				dto.setCityName(baseListDto.getCityName());
				dto.setProductCode(baseListDto.getProductCode());
				dto.setProductName(baseListDto.getProductName());
				dto.setBorrowingAmount(baseListDto.getBorrowingAmount());
				dto.setBorrowingDay(baseListDto.getBorrowingDay());
				dto.setOrderNo(baseListDto.getOrderNo());
				dto.setRelationOrderNo(dto.getRelationOrderNo());
				dto.setData(contractListService.getDataBySource(dto.getOrderNo()));
			} else {
				dto.setData("{\"borrowerName\":\""+dto.getCustomerName()+"\"}");
			}
			contractListService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("删除异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/*@Override
	public RespDataObject<ContractListDto> associatedOrder(@RequestBody ContractListDto dto) {
		RespDataObject<ContractListDto> resp = new RespDataObject<ContractListDto>();
			if (StringUtils.isNotEmpty(dto.getOrderNo())) {
				dto.setData(contractListService.getDataBySource(dto.getOrderNo()));
			}	
			return RespHelper.setSuccessDataObject(resp, dto);
    }*/
	
}