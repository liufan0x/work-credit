package com.anjbo.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.bean.ModelConfigDto;
import com.anjbo.bean.TblFinanceAfterloanEqualInterestDto;
import com.anjbo.bean.TblFinanceAfterloanFirstInterestDto;
import com.anjbo.bean.TblFinanceAfterloanListDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/v")
@Api(value = "tools对外查询api")
public interface IToolsApiController {
	
	@ApiOperation(value = "查询资方模型", httpMethod = "POST" ,response = ModelConfigDto.class)
	@RequestMapping(value = "searchFundModel", method= {RequestMethod.POST})
	List<ModelConfigDto> searchFundModel(@RequestBody ModelConfigDto dto);
	
	@ApiOperation(value = "查询出款报表", httpMethod = "GET" ,response = Map.class)
	@RequestMapping(value = "loanPreparationListMap_{orderNos}", method= {RequestMethod.GET})
	Map<String,Object> loanPreparationListMap(@PathVariable("orderNos") String orderNos);
	
	@ApiOperation(value = "查询回款报表", httpMethod = "GET" ,response = Map.class)
	@RequestMapping(value = "repaymentPreparationListMap_{orderNos}", method= {RequestMethod.GET})
	Map<String,Object> repaymentPreparationListMap(@PathVariable("orderNos") String orderNos);
	
	@ApiOperation(value = "推送h5消息", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "pushH5", method= {RequestMethod.POST})
	void pushH5(@RequestBody Map<String, Object> params);
	
	@ApiOperation(value = "查找贷后信息", httpMethod = "POST" ,response = TblFinanceAfterloanListDto.class)
	@RequestMapping(value = "findFinanceAfterloanList", method= {RequestMethod.POST})
	public abstract RespDataObject<TblFinanceAfterloanListDto> findFinanceAfterloanList(@RequestBody TblFinanceAfterloanListDto dto);
	
	@ApiOperation(value = "查询先息后本", httpMethod = "POST" ,response = TblFinanceAfterloanFirstInterestDto.class)
	@RequestMapping(value = "searchFinanceAfterloanFirstInterestList", method= {RequestMethod.POST})
	public abstract RespData<TblFinanceAfterloanFirstInterestDto> searchFinanceAfterloanFirstInterestList(@RequestBody TblFinanceAfterloanFirstInterestDto dto);
	
	@ApiOperation(value = "查询先息后本总数", httpMethod = "POST" ,response = TblFinanceAfterloanFirstInterestDto.class)
	@RequestMapping(value = "financeAfterloanFirstCount", method= {RequestMethod.POST})
	public abstract RespDataObject<Integer> financeAfterloanFirstCount(@RequestBody TblFinanceAfterloanFirstInterestDto dto);
	
	@ApiOperation(value = "查询等额本息", httpMethod = "POST" ,response = TblFinanceAfterloanEqualInterestDto.class)
	@RequestMapping(value = "searchFinanceAfterloanEqualInterestList", method= {RequestMethod.POST})
	public abstract RespData<TblFinanceAfterloanEqualInterestDto> searchFinanceAfterloanEqualInterestList(@RequestBody TblFinanceAfterloanEqualInterestDto dto);
	
	@ApiOperation(value = "查询等额本息总数", httpMethod = "POST" ,response = TblFinanceAfterloanEqualInterestDto.class)
	@RequestMapping(value = "financeAfterloanEqualCount", method= {RequestMethod.POST})
	public abstract RespDataObject<Integer> financeAfterloanEqualCount(@RequestBody TblFinanceAfterloanEqualInterestDto dto);
}