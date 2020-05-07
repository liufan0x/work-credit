package com.anjbo.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.finance.StatementDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseCustomerBorrowerDto;
import com.anjbo.bean.order.BaseCustomerDto;
import com.anjbo.bean.order.BaseCustomerGuaranteeDto;
import com.anjbo.bean.order.BaseHouseDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.risk.RiskEnquiryDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/v")
@Api(value = "order对外查询api")
public interface IOrderApiController {
	
	@ApiOperation(value = "根据orderNo获取列表Dto", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "/findByOrderNo_{orderNo}", method=RequestMethod.POST)
	public BaseListDto findByOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取集合列表Dto", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "/findByListOrderNo_{orderNo}", method=RequestMethod.POST)
	public BaseListDto findByListOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取订单借款信息", httpMethod = "POST" ,response = BaseBorrowDto.class)
	@RequestMapping(value = "/findBorrowByOrderNo_{orderNo}", method=RequestMethod.POST)
	public BaseBorrowDto findBorrowByOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取订单回款信息", httpMethod = "POST" ,response = ReceivableForDto.class)
	@RequestMapping(value = "/findReceivableForOrderNo_{orderNo}", method=RequestMethod.POST)
	public List<ReceivableForDto> findReceivableForOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取订单客户信息", httpMethod = "POST" ,response = BaseCustomerDto.class)
	@RequestMapping(value = "/findCustomerByOrderNo_{orderNo}", method=RequestMethod.POST)
	public BaseCustomerDto findCustomerByOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取订单担保人信息", httpMethod = "POST" ,response = BaseCustomerGuaranteeDto.class)
	@RequestMapping(value = "/findCustomerGuaranteeByOrderNo_{orderNo}", method=RequestMethod.POST)
	public BaseCustomerGuaranteeDto findCustomerGuaranteeByOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取订单共同借款人信息", httpMethod = "POST" ,response = BaseCustomerBorrowerDto.class)
	@RequestMapping(value = "/findCustomerBorrowerByOrderNo_{orderNo}", method=RequestMethod.POST)
	public BaseCustomerBorrowerDto findCustomerBorrowerByOrderNo(@PathVariable("orderNo") String orderNo);
	
	
	@ApiOperation(value = "根据orderNo获取订单房产信息", httpMethod = "POST" ,response = BaseHouseDto.class)
	@RequestMapping(value = "/findHouseByOrderNo_{orderNo}", method=RequestMethod.POST)
	public BaseHouseDto findHouseByOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取房产信息", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/findHousePropertyOrderNo_{orderNo}", method=RequestMethod.POST)
	public List<Map<String, Object>> findHousePropertyOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取产权人信息", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "/findHousePropertyPeopleOrderNo_{orderNo}", method=RequestMethod.POST)
	public List<Map<String, Object>> findHousePropertyPeopleOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取订单要件信息", httpMethod = "POST" ,response = DocumentsDto.class)
	@RequestMapping(value = "/findDocumentsByOrderNo_{orderNo}", method=RequestMethod.POST)
	public DocumentsDto findDocumentsByOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取订单回款信息", httpMethod = "POST" ,response = ReceivableForDto.class)
	@RequestMapping(value = "/findReceivableForByOrderNo_{orderNo}", method=RequestMethod.POST)
	public List<ReceivableForDto> findReceivableForByOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取要件出款信息", httpMethod = "POST" ,response = ForeclosureTypeDto.class)
	@RequestMapping(value = "/findForeclosureByOrderNo_{orderNo}", method=RequestMethod.POST)
	public ForeclosureTypeDto findForeclosureByOrderNo(@PathVariable("orderNo") String orderNo);
	
	@ApiOperation(value = "根据orderNo获取要件回款信息", httpMethod = "POST" ,response = PaymentTypeDto.class)
	@RequestMapping(value = "/findPaymentByOrderNo_{orderNo}", method=RequestMethod.POST)
	public PaymentTypeDto findPaymentByOrderNo(@PathVariable("orderNo") String orderNo);

	@ApiOperation(value = "获取影像资料", httpMethod = "POST",response=Map.class)
	@RequestMapping(value = "/selectAllBusInfo_{orderNo}", method=RequestMethod.POST)
	List<Map<String, Object>> selectAllBusInfo(@PathVariable("orderNo")  String orderNo);
	
	@ApiOperation(value = "获取影像资料父分类", httpMethod = "POST",response=Map.class)
	@RequestMapping(value = "/getParentBusInfoTypes", method=RequestMethod.POST)
	List<Map<String, Object>> getParentBusInfoTypes(@RequestBody Map<String, Object> map);
	
	@ApiOperation(value = "获取影像资料子分类", httpMethod = "POST",response=Map.class)
	@RequestMapping(value = "/getSonType", method=RequestMethod.POST)
	List<Map<String, Object>> getSonType(@RequestBody Map<String, Object> map);

	@ApiOperation(value = "查询", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "/selectDetailByOrderNo",method= {RequestMethod.POST}) 
	public BaseListDto selectDetailByOrderNo(@RequestBody BaseListDto baseListDto);
	
	@ApiOperation(value = "申请放款详情", httpMethod = "POST" ,response = Map.class)
	@RequestMapping(value = "applyLoanProcessDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<Map<String, Object>> applyLoanProcessDetails(@RequestBody ApplyLoanDto dto);
	
	@ApiOperation(value = "根据orderNo获取订单征信信息", httpMethod = "POST" ,response = CreditDto.class)
	@RequestMapping(value = "/findOrderCredit_{orderNo}", method=RequestMethod.POST)
	public CreditDto findOrderCredit(@PathVariable("orderNo") String orderNo);
	
	
	@ApiOperation(value = "根据orderNo获取订单征询价", httpMethod = "POST" ,response = RiskEnquiryDto.class)
	@RequestMapping(value = "/findOrderRiskEnquiry_{orderNo}", method=RequestMethod.POST)
	public List<RiskEnquiryDto> findOrderRiskEnquiry(@PathVariable("orderNo") String orderNo); 
	
	@ApiOperation(value = "根据orderNo获取财务制单信息", httpMethod = "POST" ,response = StatementDto.class)
	@RequestMapping(value = "/findStatementDetails", method=RequestMethod.POST)
	public StatementDto findStatementDetails(StatementDto dto);
	
	@ApiOperation(value = "编辑订单列表", httpMethod = "POST" ,response = BaseListDto.class)
	@RequestMapping(value = "editOrderList", method= {RequestMethod.POST})
	public abstract RespStatus editOrderList(@RequestBody BaseListDto dto);
	
	@ApiOperation(value = "新增订单流水", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "addOrderFlow", method= {RequestMethod.POST})
	public abstract RespDataObject<FlowDto> addOrderFlow(@RequestBody FlowDto dto);
	
	@ApiOperation(value = "查询订单流水(无重复的)", httpMethod = "POST" ,response = FlowDto.class)
	@RequestMapping(value = "/selectOrderFlowList")
	public RespData<FlowDto> selectOrderFlowList(@RequestBody FlowDto dto);
}