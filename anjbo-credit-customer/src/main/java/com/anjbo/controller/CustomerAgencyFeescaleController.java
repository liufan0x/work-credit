package com.anjbo.controller;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.customer.CustomerAgencyFeescaleDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.common.*;
import com.anjbo.service.CustomerAgencyFeescaleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
  * 收费标准 [Controller]
  * @ClassName: CustomerAgencyFeescaleController
  * @Description: 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/agency/feescale/v")
public class CustomerAgencyFeescaleController extends BaseController{

	private Log log = LogFactory.getLog(CustomerAgencyFeescaleController.class);
	
	@Resource 
	private CustomerAgencyFeescaleService customerAgencyFeescaleService;

	/**
	 * 收费标准分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param request
	 * @return customerAgencyFeescaleDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerAgencyFeescaleDto> page(HttpServletRequest request,@RequestBody CustomerAgencyFeescaleDto customerAgencyFeescaleDto){
		RespPageData<CustomerAgencyFeescaleDto> resp = new RespPageData<CustomerAgencyFeescaleDto>();
		List<CustomerAgencyFeescaleDto> list = customerAgencyFeescaleService.selectCustomerAgencyFeescaleList(customerAgencyFeescaleDto);
		List<ProductDto> productDtos = getProductDtos();
		List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
		for (CustomerAgencyFeescaleDto customerAgencyFeescale : list) {
			if(customerAgencyFeescale.getProductionid() != 0){
				String cityCode = (customerAgencyFeescale.getProductionid()+"").substring(0, 4);
				for (DictDto dictDto : dictList) {
					if(StringUtils.isNotEmpty(dictDto.getCode())){
						if(dictDto.getCode().equals(cityCode)){
							customerAgencyFeescale.setProductName(dictDto.getName());
							break;
						}
					}
				}
			}
			for (ProductDto productDto : productDtos) {
				if(productDto.getId() == customerAgencyFeescale.getProductionid()){
					customerAgencyFeescale.setProductName(customerAgencyFeescale.getProductName()+"-"+productDto.getProductName());
				}
			}
		}
		resp.setTotal(customerAgencyFeescaleService.selectCustomerAgencyFeescaleCount(customerAgencyFeescaleDto));
		resp.setRows(list);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 收费标准编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleDto customerAgencyFeescaleDto){ 
		try {
			if(customerAgencyFeescaleDto.getId()>0){
				customerAgencyFeescaleDto.setUpdateUid(getUserDto(request).getUid());
				customerAgencyFeescaleService.updateCustomerAgencyFeescale(customerAgencyFeescaleDto);
			}else{
				customerAgencyFeescaleDto.setCreateUid(getUserDto(request).getUid());
				customerAgencyFeescaleService.addCustomerAgencyFeescale(customerAgencyFeescaleDto);
			}
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("收费标准编辑失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 根据ID删除收费标准
	 * @Title: deleteCustomerAgencyFeescaleById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerAgencyFeescaleById")
	public RespStatus deleteCustomerAgencyFeescaleById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleDto customerAgencyFeescaleDto){ 
		RespStatus status = new  RespStatus();
		if(customerAgencyFeescaleDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerAgencyFeescaleService.deleteCustomerAgencyFeescaleById(customerAgencyFeescaleDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("收费标准删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询收费标准
	 * @Title: selectCustomerAgencyFeescaleById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerAgencyFeescaleById")
	public RespDataObject<CustomerAgencyFeescaleDto> selectCustomerAgencyFeescaleById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleDto customerAgencyFeescaleDto){ 
		 RespDataObject<CustomerAgencyFeescaleDto> status = new  RespDataObject<CustomerAgencyFeescaleDto>();
		if(customerAgencyFeescaleDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerAgencyFeescaleService.selectCustomerAgencyFeescaleById(customerAgencyFeescaleDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
	/**
	 * 修改 收费信息（包括收费标准、风控等级、收费设置、收费详细）
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateFeescaleInfo")
	public RespStatus updateFeescaleInfo(HttpServletRequest request, @RequestBody List<CustomerAgencyFeescaleDto> customerAgencyFeescaleList){
		RespStatus respStatus = new RespStatus();
		try {
			if (customerAgencyFeescaleList == null || customerAgencyFeescaleList.size() <= 0) {
				return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.PARAMETER_ERROR.getMsg());
			}
			for (CustomerAgencyFeescaleDto obj : customerAgencyFeescaleList) {
				obj.setCreateUid(getUserDto(request).getUid());
			}
			respStatus = customerAgencyFeescaleService.updateFeescaleInfo(customerAgencyFeescaleList);
		} catch (Exception e){
			logger.error("修改机构产品收费配置异常:",e);
			RespHelper.setFailRespStatus(respStatus,RespStatusEnum.FAIL.getMsg());
		}
		return respStatus;
	}


	/**
	 * 根据机构id与产品查询收费配置
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectCustomerAgencyFeescaleByAgencyIdAndProductionid")
	public RespDataObject<List<CustomerAgencyFeescaleDto>>
				selectCustomerAgencyFeescaleByAgencyIdAndProductionid(HttpServletRequest request,@RequestBody CustomerAgencyFeescaleDto obj){
		RespDataObject<List<CustomerAgencyFeescaleDto>> respDataObject = new RespDataObject<List<CustomerAgencyFeescaleDto>>();
		try {
			if(0==obj.getAgencyTypeId()||0==obj.getProductionid()){
				RespHelper.setFailDataObject(respDataObject,null,"查询收费配置缺少参数!");
				return respDataObject;
			}
			List<CustomerAgencyFeescaleDto> list = customerAgencyFeescaleService.selectCustomerAgencyFeescaleByAgencyIdAndProductionid(obj);
			RespHelper.setSuccessDataObject(respDataObject,list);
		} catch (Exception e){
			RespHelper.setFailDataObject(respDataObject,null,RespStatusEnum.FAIL.getMsg());
			logger.error("查询收费配置异常:",e);
		}
		return respDataObject;
	}
}
