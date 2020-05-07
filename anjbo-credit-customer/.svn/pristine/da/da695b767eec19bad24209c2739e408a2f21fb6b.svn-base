package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.CustomerFundCostDiscountDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CustomerFundCostDiscountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
  * 合作资金方业务产品优惠 [Controller]
  * @ClassName: CustomerFundCostDiscountController
  * @Description: 
  * @date 2017-07-06 15:53:15
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/fund/cost/discount/v")
public class CustomerFundCostDiscountController extends BaseController{

	private Log log = LogFactory.getLog(CustomerFundCostDiscountController.class);
	
	@Resource 
	private CustomerFundCostDiscountService customerFundCostDiscountService;

	/**
	 * 合作资金方业务产品优惠分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:53:15
	 * @param request
	 * @return customerFundCostDiscountDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerFundCostDiscountDto> page(HttpServletRequest request,@RequestBody CustomerFundCostDiscountDto customerFundCostDiscountDto){
		RespPageData<CustomerFundCostDiscountDto> resp = new RespPageData<CustomerFundCostDiscountDto>();
		resp.setTotal(customerFundCostDiscountService.selectCustomerFundCostDiscountCount(customerFundCostDiscountDto));
		resp.setRows(customerFundCostDiscountService.selectCustomerFundCostDiscountList(customerFundCostDiscountDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 合作资金方业务产品优惠编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:53:15
	 * @param customerFundCostDiscountDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody List<CustomerFundCostDiscountDto> customerFundCostDiscountDto){ 
		try {
			for (CustomerFundCostDiscountDto customerFundCostDiscount : customerFundCostDiscountDto) {
				if(customerFundCostDiscount.getId()>0){
					customerFundCostDiscount.setUpdateUid(getUserDto(request).getUid());
					customerFundCostDiscountService.updateCustomerFundCostDiscount(customerFundCostDiscount);
				}else{
					customerFundCostDiscount.setCreateUid(getUserDto(request).getUid());
					customerFundCostDiscountService.addCustomerFundCostDiscount(customerFundCostDiscount);
				}
			}
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("合作资金方业务产品优惠编辑失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 根据ID删除合作资金方业务产品优惠
	 * @Title: deleteCustomerFundCostDiscountById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:53:15
	 * @param customerFundCostDiscountDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerFundCostDiscountById")
	public RespStatus deleteCustomerFundCostDiscountById(HttpServletRequest request, @RequestBody List<CustomerFundCostDiscountDto> customerFundCostDiscountDtoList){ 
		RespStatus status = new  RespStatus();
		for (CustomerFundCostDiscountDto customerFundCostDiscountDto : customerFundCostDiscountDtoList) {
			if(customerFundCostDiscountDto.getId()<=0){
				status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
				status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
				return status;
			}
			try {
				customerFundCostDiscountService.deleteCustomerFundCostDiscountById(customerFundCostDiscountDto.getId());
				status.setCode(RespStatusEnum.SUCCESS.getCode());
				status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			} catch (Exception e) {
				log.error("合作资金方业务产品优惠删除失败，异常信息：", e);
				return RespHelper.failRespStatus();
			}
		}
		return status;
	}
	
	/**
	 * 根据ID查询合作资金方业务产品优惠
	 * @Title: selectCustomerFundCostDiscountById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:53:15
	 * @param customerFundCostDiscountDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerFundCostDiscountById")
	public RespDataObject<CustomerFundCostDiscountDto> selectCustomerFundCostDiscountById(HttpServletRequest request, @RequestBody CustomerFundCostDiscountDto customerFundCostDiscountDto){ 
		 RespDataObject<CustomerFundCostDiscountDto> status = new  RespDataObject<CustomerFundCostDiscountDto>();
		if(customerFundCostDiscountDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerFundCostDiscountService.selectCustomerFundCostDiscountById(customerFundCostDiscountDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
}
