package com.anjbo.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.anjbo.bean.AdministrationDivideDto;
import com.anjbo.bean.AuthorityDto;
import com.anjbo.bean.BankDto;
import com.anjbo.bean.BankSubDto;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.ProductDto;
import com.anjbo.bean.RespBean;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/v")
@Api(value = "common对外查询api")
public interface IDataApiController {

	@ApiOperation(value = "查找流程", httpMethod = "POST", response = ProcessDto.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "productId", value = "产品Id", required = true), })
	@RequestMapping(value = "findProcessDto_{productId}", method = { RequestMethod.GET })
	public abstract List<ProcessDto> findProcessDto(@PathVariable("productId") Integer productId);

	@ApiOperation(value = "根据type获取字典集合", httpMethod = "GET", response = DictDto.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "字典类型", required = true), })
	@RequestMapping(value = "/selectDictDtoListByType_{type}", method = { RequestMethod.POST })
	public abstract List<DictDto> getDictDtoListByType(@PathVariable("type") String type);

	@ApiOperation(value = "获取所有产品", httpMethod = "POST", response = ProductDto.class)
	@RequestMapping(value = "/getProductList", method = { RequestMethod.GET })
	public abstract List<ProductDto> getProductList();
	
	@ApiOperation(value = "查询所有银行", httpMethod = "POST", response = DictDto.class)
	@RequestMapping(value = "/selectBankList")
	public abstract RespData<BankDto> selectBankList();

	@ApiOperation(value = "根据银行Id获取支行", httpMethod = "POST", response = DictDto.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "bankId", value = "银行Id", required = true), })
	@RequestMapping(value = "selectSubBankListByBankId_{bankId}", method = { RequestMethod.POST })
	public abstract RespData<BankSubDto> selectSubBankListByBankId(@PathVariable("bankId") int bankId);

	@ApiOperation(value = "根据参数获取下一个节点", httpMethod = "POST", response = ProcessDto.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "productCode", value = "产品Code", required = true),
			@ApiImplicitParam(name = "cityCode", value = "城市Code", required = true),
			@ApiImplicitParam(name = "processId", value = "流程节点Id", required = true), })
	@RequestMapping(value = "getNextProcess")
	public abstract ProcessDto getNextProcess(@RequestBody Map<String, Object> params);

	@ApiOperation(value = "查询云南城市编号", httpMethod = "POST" ,response = AdministrationDivideDto.class)
	@RequestMapping(value = "/findAdministrationDivideDto", method= {RequestMethod.POST})
	public AdministrationDivideDto findAdministrationDivideDto(AdministrationDivideDto dto);
	
	@ApiOperation(value = "获取权限集合", httpMethod = "POST", response = AuthorityDto.class)
	@RequestMapping(value = "findAuthority")
	public abstract AuthorityDto findAuthority(@RequestBody AuthorityDto authorityDto);
	
	@RequestMapping(value = "send")
	public abstract RespBean<String> send();
	
	@ApiOperation(value = "查找银行", httpMethod = "POST" ,response = BankDto.class)
	@RequestMapping(value = "getBankNameById", method= {RequestMethod.POST})
	public abstract RespDataObject<BankDto> getBankNameById(@RequestBody BankDto dto);
	
	@ApiOperation(value = "查找支行", httpMethod = "POST" ,response = BankSubDto.class)
	@RequestMapping(value = "getSubBankNameById", method= {RequestMethod.POST})
	public abstract RespDataObject<BankSubDto> getSubBankNameById(@RequestBody BankSubDto dto);
	
	@ApiOperation(value = "查找城市", httpMethod = "POST" ,response = DictDto.class)
	@RequestMapping(value = "getCityById", method= {RequestMethod.POST})
	public abstract DictDto getCityById(@RequestBody DictDto dto);
	
	@ApiOperation(value = "跟进ProductId查询权限", httpMethod = "POST" ,response = AuthorityDto.class)
	@RequestMapping(value = "/selectAuthorityByProductId")
	public RespDataObject<Map<String, Object>> selectAuthorityByProductId(@RequestBody Map<String, Object> params);
	
}