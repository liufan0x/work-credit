package com.anjbo.controller.impl.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.ModelConfigDto;
import com.anjbo.bean.TblFinanceAfterloanEqualInterestDto;
import com.anjbo.bean.TblFinanceAfterloanFirstInterestDto;
import com.anjbo.bean.TblFinanceAfterloanListDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.ToolsBaseController;
import com.anjbo.controller.api.IToolsApiController;
import com.anjbo.service.LoanPreparationService;
import com.anjbo.service.ModelConfigService;
import com.anjbo.service.RepaymentPreparationService;
import com.anjbo.service.TblFinanceAfterloanEqualInterestService;
import com.anjbo.service.TblFinanceAfterloanFirstInterestService;
import com.anjbo.service.TblFinanceAfterloanListService;
import com.anjbo.websocket.WebSocketServer;

@RestController
public class ToolsApiController extends ToolsBaseController implements IToolsApiController{

	@Resource private ModelConfigService modelConfigService;
	
	@Resource private LoanPreparationService loanPreparationService;
	
	@Resource private RepaymentPreparationService repaymentPreparationService;
	
	@Resource private TblFinanceAfterloanListService tblFinanceAfterloanListService;
	
	@Resource private TblFinanceAfterloanFirstInterestService tblFinanceAfterloanFirstInterestService;
	
	@Resource private TblFinanceAfterloanEqualInterestService tblFinanceAfterloanEqualInterestService;

	@Override
	public List<ModelConfigDto> searchFundModel(@RequestBody ModelConfigDto dto) {
		return modelConfigService.search(dto);
	}
	
	@Override
	public Map<String, Object> loanPreparationListMap(@PathVariable("orderNos") String orderNos) {
		return loanPreparationService.loanPreparationListMap(orderNos);
	}
	
	@Override
	public Map<String, Object> repaymentPreparationListMap(@PathVariable("orderNos") String orderNos) {
		return repaymentPreparationService.repaymentPreparationListMap(orderNos);
	}
	
	@Override
	public void pushH5(@RequestBody Map<String, Object> params) {
		String uid = MapUtils.getString(params, "uid","");
		String message = MapUtils.getString(params, "message","");
		try {
			if(StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(message)) {
				WebSocketServer.sendInfo(uid, message);
			}
		} catch (Exception e) {
			logger.error("推送失败:"+uid, e);
		}
	}

	/**
	 * 查询贷后信息
	 */
	@Override
	public RespDataObject<TblFinanceAfterloanListDto> findFinanceAfterloanList(@RequestBody TblFinanceAfterloanListDto dto) {
		RespDataObject<TblFinanceAfterloanListDto> resp = new RespDataObject<TblFinanceAfterloanListDto>();
		try {
			dto =  tblFinanceAfterloanListService.find(dto);
			return RespHelper.setSuccessDataObject(resp, dto);
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblFinanceAfterloanListDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	@Override
	public RespData<TblFinanceAfterloanFirstInterestDto> searchFinanceAfterloanFirstInterestList(@RequestBody 
			TblFinanceAfterloanFirstInterestDto dto) {
		RespData<TblFinanceAfterloanFirstInterestDto> resp = new RespData<TblFinanceAfterloanFirstInterestDto>();
		try {
			return RespHelper.setSuccessData(resp, tblFinanceAfterloanFirstInterestService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<TblFinanceAfterloanFirstInterestDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	@Override
	public RespDataObject<Integer> financeAfterloanFirstCount(@RequestBody TblFinanceAfterloanFirstInterestDto dto) {
		RespDataObject<Integer>  resp = new RespDataObject<Integer> ();
		try {
			int i = tblFinanceAfterloanFirstInterestService.count(dto);
			return RespHelper.setSuccessDataObject(resp, i);
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}

	@Override
	public RespData<TblFinanceAfterloanEqualInterestDto> searchFinanceAfterloanEqualInterestList(@RequestBody 
			TblFinanceAfterloanEqualInterestDto dto) {
		RespData<TblFinanceAfterloanEqualInterestDto> resp = new RespData<TblFinanceAfterloanEqualInterestDto>();
		try {
			return RespHelper.setSuccessData(resp, tblFinanceAfterloanEqualInterestService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<TblFinanceAfterloanEqualInterestDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	@Override
	public RespDataObject<Integer> financeAfterloanEqualCount(@RequestBody TblFinanceAfterloanEqualInterestDto dto) {
		RespDataObject<Integer>  resp = new RespDataObject<Integer> ();
		try {
			int i = tblFinanceAfterloanEqualInterestService.count(dto);
			return RespHelper.setSuccessDataObject(resp, i);
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}

	
}
