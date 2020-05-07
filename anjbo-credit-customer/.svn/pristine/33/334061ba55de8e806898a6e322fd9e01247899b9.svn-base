package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.CustomerAgencyTypeDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CustomerAgencyTypeService;
import com.anjbo.utils.StringUtil;


/**
  * 机构类型 [Controller]
  * @ClassName: CustomerAgencyTypeController
  * @Description: 
  * @date 2017-07-06 15:07:08
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/agency/type/v")
public class CustomerAgencyTypeController extends BaseController{

	private Log log = LogFactory.getLog(CustomerAgencyTypeController.class);
	
	@Resource 
	private CustomerAgencyTypeService customerAgencyTypeService;

	/**
	 * 机构类型分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:08
	 * @param request
	 * @return customerAgencyTypeDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerAgencyTypeDto> page(HttpServletRequest request,@RequestBody CustomerAgencyTypeDto customerAgencyTypeDto){
		RespPageData<CustomerAgencyTypeDto> resp = new RespPageData<CustomerAgencyTypeDto>();
		resp.setTotal(customerAgencyTypeService.selectCustomerAgencyTypeCount(customerAgencyTypeDto));
		resp.setRows(customerAgencyTypeService.selectCustomerAgencyTypeList(customerAgencyTypeDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}

	/**
	 * 机构类型列表
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:08
	 * @param request
	 * @return customerAgencyTypeDto
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public RespData<CustomerAgencyTypeDto> list(HttpServletRequest request){
		RespData<CustomerAgencyTypeDto> resp = new RespData<CustomerAgencyTypeDto>();
		resp.setData(customerAgencyTypeService.selectCustomerAgencyTypeList(new CustomerAgencyTypeDto()));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 编辑机构类型
	 * @param agencyTypeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody CustomerAgencyTypeDto customerAgencyTypeDto){ 
		if(StringUtil.isEmpty(customerAgencyTypeDto.getName())){
			return RespHelper.setFailRespStatus(new RespStatus(),RespStatusEnum.PARAMETER_ERROR.getCode());
		}
		if(customerAgencyTypeDto.getId()>0){
			customerAgencyTypeDto.setUpdateUid(getUserDto(request).getUid());
			return customerAgencyTypeService.updateCustomerAgencyType(customerAgencyTypeDto);
		}else{
			customerAgencyTypeDto.setCreateUid(getUserDto(request).getUid());
			return customerAgencyTypeService.addCustomerAgencyType(customerAgencyTypeDto);
		}
	}
	
	/**
	 * 根据ID删除机构类型
	 * @Title: deleteCustomerAgencyTypeById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:08
	 * @param customerAgencyTypeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerAgencyTypeById")
	public RespStatus deleteCustomerAgencyTypeById(HttpServletRequest request, @RequestBody CustomerAgencyTypeDto customerAgencyTypeDto){ 
		RespStatus status = new  RespStatus();
		if(customerAgencyTypeDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerAgencyTypeService.deleteCustomerAgencyTypeById(customerAgencyTypeDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("机构类型删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询机构类型
	 * @Title: selectCustomerAgencyTypeById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:08
	 * @param customerAgencyTypeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerAgencyTypeById")
	public RespDataObject<CustomerAgencyTypeDto> selectCustomerAgencyTypeById(HttpServletRequest request, @RequestBody CustomerAgencyTypeDto customerAgencyTypeDto){ 
		 RespDataObject<CustomerAgencyTypeDto> status = new  RespDataObject<CustomerAgencyTypeDto>();
		if(customerAgencyTypeDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerAgencyTypeService.selectCustomerAgencyTypeById(customerAgencyTypeDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
}
