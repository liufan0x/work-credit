package com.anjbo.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.customer.CustomerFundCostDiscountDto;
import com.anjbo.bean.customer.CustomerFundCostDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CustomerFundCostDiscountService;
import com.anjbo.service.CustomerFundCostService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
  * 合作资金方业务产品 [Controller]
  * @ClassName: CustomerFundCostController
  * @Description: 
  * @date 2017-07-06 15:07:08
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/fund/cost/v")
public class CustomerFundCostController extends BaseController{

	private Log log = LogFactory.getLog(CustomerFundCostController.class);
	
	@Resource 
	private CustomerFundCostService customerFundCostService;
	
	@Resource
	private CustomerFundCostDiscountService customerFundCostDiscountService;
	
	/**
	 * 合作资金方业务产品分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:08
	 * @param request
	 * @return customerFundCostDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerFundCostDto> page(HttpServletRequest request,@RequestBody CustomerFundCostDto customerFundCostDto){
		RespPageData<CustomerFundCostDto> resp = new RespPageData<CustomerFundCostDto>();
		List<ProductDto> productDtos = getProductDtos();
		List<CustomerFundCostDto> list = customerFundCostService.selectCustomerFundCostList(customerFundCostDto);
		List<CustomerFundCostDiscountDto> customerFundCostDiscountList = customerFundCostDiscountService.selectCustomerFundCostDiscountList(null);
		List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
		for (CustomerFundCostDto customerFundCost : list) {
			if(customerFundCost.getProductId() != 0){
				String cityCode = (customerFundCost.getProductId()+"").substring(0, 4);
				for (DictDto dictDto : dictList) {
					if(StringUtils.isNotEmpty(dictDto.getCode())){
						if(dictDto.getCode().equals(cityCode)){
							customerFundCost.setProductName(dictDto.getName());
							break;
						}
					}
				}
			}
			
			for (ProductDto productDto : productDtos) {
				if(customerFundCost.getProductId() == productDto.getId()){
					customerFundCost.setProductName(customerFundCost.getProductName()+"-"+productDto.getProductName());
					break;
				}
			}
			if(customerFundCost.getDiscountHas() == 1){
				String discountHasStr = "";
				for (CustomerFundCostDiscountDto customerFundCostDiscountDto : customerFundCostDiscountList) {
					if(customerFundCostDiscountDto.getFundCostId() == customerFundCost.getId()){
						discountHasStr += "满"+customerFundCostDiscountDto.getMoney()+"万"+customerFundCostDiscountDto.getRate()+"%</br>";
					}
				}
				customerFundCost.setDiscountHasStr(discountHasStr);
			}
		}
		resp.setTotal(customerFundCostService.selectCustomerFundCostCount(customerFundCostDto));
		resp.setRows(list);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 合作资金方业务产品编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:08
	 * @param customerFundCostDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody CustomerFundCostDto customerFundCostDto){ 
		try {
			if(customerFundCostDto.getId()>0){
				customerFundCostDto.setUpdateUid(getUserDto(request).getUid());
				customerFundCostService.updateCustomerFundCost(customerFundCostDto);
				customerFundCostDiscountService.deleteCustomerFundCostDiscountByFundCostId(customerFundCostDto.getId());
				if(customerFundCostDto.getDiscountHas() == 1){
					for (CustomerFundCostDiscountDto customerFundCostDiscountDto : customerFundCostDto.getCustomerFundCostDiscountDtoList()) {
						customerFundCostDiscountDto.setFundCostId(customerFundCostDto.getId());
						customerFundCostDiscountService.addCustomerFundCostDiscount(customerFundCostDiscountDto);
					}
				} 
			}else{
				customerFundCostDto.setCreateUid(getUserDto(request).getUid());
				customerFundCostService.addCustomerFundCost(customerFundCostDto);
				if(customerFundCostDto.getDiscountHas() == 1){
					for (CustomerFundCostDiscountDto customerFundCostDiscountDto : customerFundCostDto.getCustomerFundCostDiscountDtoList()) {
						customerFundCostDiscountDto.setFundCostId(customerFundCostDto.getId());
						customerFundCostDiscountService.addCustomerFundCostDiscount(customerFundCostDiscountDto);
					}
				}
			}
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("合作资金方业务产品编辑失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 根据ID删除合作资金方业务产品
	 * @Title: deleteCustomerFundCostById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:08
	 * @param customerFundCostDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerFundCostById")
	public RespStatus deleteCustomerFundCostById(HttpServletRequest request, @RequestBody CustomerFundCostDto customerFundCostDto){ 
		RespStatus status = new  RespStatus();
		if(customerFundCostDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerFundCostService.deleteCustomerFundCostById(customerFundCostDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("合作资金方业务产品删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询合作资金方业务产品
	 * @Title: selectCustomerFundCostById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:08
	 * @param customerFundCostDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerFundCostById")
	public RespDataObject<CustomerFundCostDto> selectCustomerFundCostById(HttpServletRequest request, @RequestBody CustomerFundCostDto customerFundCostDto){ 
		 RespDataObject<CustomerFundCostDto> status = new  RespDataObject<CustomerFundCostDto>();
		if(customerFundCostDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerFundCostService.selectCustomerFundCostById(customerFundCostDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
	
	/**
	 * 根据资金方Id和业务品种查询查询业务产品
	 * @param request
	 * @param customerFundCostDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerFundCostByFundId")
	public RespDataObject<CustomerFundCostDto> selectCustomerFundCostByFundId(HttpServletRequest request, @RequestBody CustomerFundCostDto customerFundCostDto){ 
		 RespDataObject<CustomerFundCostDto> status = new  RespDataObject<CustomerFundCostDto>();
		if(customerFundCostDto.getFundId()<=0 || customerFundCostDto.getProductId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerFundCostService.selectCustomerFundCostByFundId(customerFundCostDto));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
}
