package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.CustomerAgencyAcceptDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CustomerAgencyAcceptService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
  * 机构受理员 [Controller]
  * @ClassName: CustomerAgencyAcceptController
  * @Description: 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/agency/accept/v")
public class CustomerAgencyAcceptController extends BaseController{

	private Log log = LogFactory.getLog(CustomerAgencyAcceptController.class);
	
	@Resource 
	private CustomerAgencyAcceptService customerAgencyAcceptService;

	/**
	 * 机构受理员分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param request
	 * @return customerAgencyAcceptDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerAgencyAcceptDto> page(HttpServletRequest request,@RequestBody CustomerAgencyAcceptDto customerAgencyAcceptDto){
		RespPageData<CustomerAgencyAcceptDto> resp = new RespPageData<CustomerAgencyAcceptDto>();
		resp.setTotal(customerAgencyAcceptService.selectCustomerAgencyAcceptCount(customerAgencyAcceptDto));
		resp.setRows(customerAgencyAcceptService.selectCustomerAgencyAcceptList(customerAgencyAcceptDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 机构受理员编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyAcceptDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody List<CustomerAgencyAcceptDto> customerAgencyAcceptList){ 
		try {
			for (CustomerAgencyAcceptDto customerAgencyAcceptDto : customerAgencyAcceptList) {
				if(customerAgencyAcceptDto.getId()>0){
					customerAgencyAcceptDto.setUpdateUid(getUserDto(request).getUid());
					customerAgencyAcceptService.updateCustomerAgencyAccept(customerAgencyAcceptDto);
				}else{
					customerAgencyAcceptDto.setCreateUid(getUserDto(request).getUid());
					customerAgencyAcceptService.addCustomerAgencyAccept(customerAgencyAcceptDto);
				}
			}
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("机构受理员编辑失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 根据机构ID删除机构受理员
	 * @Title: deleteCustomerAgencyAcceptById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyAcceptDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerAgencyAcceptByAgencyId")
	public RespStatus deleteCustomerAgencyAcceptByAgencyId(HttpServletRequest request, @RequestBody CustomerAgencyAcceptDto customerAgencyAcceptDto){ 
		RespStatus status = new  RespStatus();
		if(customerAgencyAcceptDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerAgencyAcceptService.deleteCustomerAgencyAcceptByAgencyId(customerAgencyAcceptDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("机构受理员删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询机构受理员
	 * @Title: selectCustomerAgencyAcceptById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyAcceptDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerAgencyAcceptById")
	public RespDataObject<CustomerAgencyAcceptDto> selectCustomerAgencyAcceptById(HttpServletRequest request, @RequestBody CustomerAgencyAcceptDto customerAgencyAcceptDto){ 
		 RespDataObject<CustomerAgencyAcceptDto> status = new  RespDataObject<CustomerAgencyAcceptDto>();
		if(customerAgencyAcceptDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerAgencyAcceptService.selectCustomerAgencyAcceptById(customerAgencyAcceptDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
}
