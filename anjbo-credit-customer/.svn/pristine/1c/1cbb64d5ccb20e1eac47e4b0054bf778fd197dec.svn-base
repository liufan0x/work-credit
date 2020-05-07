package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.CustomerAgencyFeescaleRiskcontrolDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CustomerAgencyFeescaleRiskcontrolService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
  * 风控配置 [Controller]
  * @ClassName: CustomerAgencyFeescaleRiskcontrolController
  * @Description: 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/agency/feescale/riskcontrol/v")
public class CustomerAgencyFeescaleRiskcontrolController extends BaseController{

	private Log log = LogFactory.getLog(CustomerAgencyFeescaleRiskcontrolController.class);
	
	@Resource 
	private CustomerAgencyFeescaleRiskcontrolService customerAgencyFeescaleRiskcontrolService;

	/**
	 * 风控配置分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param request
	 * @return customerAgencyFeescaleRiskcontrolDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerAgencyFeescaleRiskcontrolDto> page(HttpServletRequest request,@RequestBody CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto){
		RespPageData<CustomerAgencyFeescaleRiskcontrolDto> resp = new RespPageData<CustomerAgencyFeescaleRiskcontrolDto>();
		resp.setTotal(customerAgencyFeescaleRiskcontrolService.selectCustomerAgencyFeescaleRiskcontrolCount(customerAgencyFeescaleRiskcontrolDto));
		resp.setRows(customerAgencyFeescaleRiskcontrolService.selectCustomerAgencyFeescaleRiskcontrolList(customerAgencyFeescaleRiskcontrolDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 风控配置编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleRiskcontrolDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto){ 
		try {
			if(customerAgencyFeescaleRiskcontrolDto.getId()>0){
				customerAgencyFeescaleRiskcontrolDto.setUpdateUid(getUserDto(request).getUid());
				customerAgencyFeescaleRiskcontrolService.updateCustomerAgencyFeescaleRiskcontrol(customerAgencyFeescaleRiskcontrolDto);
			}else{
				customerAgencyFeescaleRiskcontrolDto.setCreateUid(getUserDto(request).getUid());
				customerAgencyFeescaleRiskcontrolService.addCustomerAgencyFeescaleRiskcontrol(customerAgencyFeescaleRiskcontrolDto);
			}
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("风控配置编辑失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 根据ID删除风控配置
	 * @Title: deleteCustomerAgencyFeescaleRiskcontrolById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleRiskcontrolDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerAgencyFeescaleRiskcontrolById")
	public RespStatus deleteCustomerAgencyFeescaleRiskcontrolById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto){ 
		RespStatus status = new  RespStatus();
		if(customerAgencyFeescaleRiskcontrolDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerAgencyFeescaleRiskcontrolService.deleteCustomerAgencyFeescaleRiskcontrolById(customerAgencyFeescaleRiskcontrolDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("风控配置删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询风控配置
	 * @Title: selectCustomerAgencyFeescaleRiskcontrolById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleRiskcontrolDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerAgencyFeescaleRiskcontrolById")
	public RespDataObject<CustomerAgencyFeescaleRiskcontrolDto> selectCustomerAgencyFeescaleRiskcontrolById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto){ 
		 RespDataObject<CustomerAgencyFeescaleRiskcontrolDto> status = new  RespDataObject<CustomerAgencyFeescaleRiskcontrolDto>();
		if(customerAgencyFeescaleRiskcontrolDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerAgencyFeescaleRiskcontrolService.selectCustomerAgencyFeescaleRiskcontrolById(customerAgencyFeescaleRiskcontrolDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
}
