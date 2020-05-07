package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.CustomerAgencyFeescaleDetailDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CustomerAgencyFeescaleDetailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
  * 收费标准详细 [Controller]
  * @ClassName: CustomerAgencyFeescaleDetailController
  * @Description: 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/agency/feescale/detail/v")
public class CustomerAgencyFeescaleDetailController extends BaseController{

	private Log log = LogFactory.getLog(CustomerAgencyFeescaleDetailController.class);
	
	@Resource 
	private CustomerAgencyFeescaleDetailService customerAgencyFeescaleDetailService;

	/**
	 * 收费标准详细分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param request
	 * @return customerAgencyFeescaleDetailDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerAgencyFeescaleDetailDto> page(HttpServletRequest request,@RequestBody CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto){
		RespPageData<CustomerAgencyFeescaleDetailDto> resp = new RespPageData<CustomerAgencyFeescaleDetailDto>();
		resp.setTotal(customerAgencyFeescaleDetailService.selectCustomerAgencyFeescaleDetailCount(customerAgencyFeescaleDetailDto));
		resp.setRows(customerAgencyFeescaleDetailService.selectCustomerAgencyFeescaleDetailList(customerAgencyFeescaleDetailDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 收费标准详细编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleDetailDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto){ 
		try {
			if(customerAgencyFeescaleDetailDto.getId()>0){
				customerAgencyFeescaleDetailDto.setUpdateUid(getUserDto(request).getUid());
				customerAgencyFeescaleDetailService.updateCustomerAgencyFeescaleDetail(customerAgencyFeescaleDetailDto);
			}else{
				customerAgencyFeescaleDetailDto.setCreateUid(getUserDto(request).getUid());
				customerAgencyFeescaleDetailService.addCustomerAgencyFeescaleDetail(customerAgencyFeescaleDetailDto);
			}
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("收费标准详细编辑失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 根据ID删除收费标准详细
	 * @Title: deleteCustomerAgencyFeescaleDetailById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleDetailDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerAgencyFeescaleDetailById")
	public RespStatus deleteCustomerAgencyFeescaleDetailById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto){ 
		RespStatus status = new  RespStatus();
		if(customerAgencyFeescaleDetailDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerAgencyFeescaleDetailService.deleteCustomerAgencyFeescaleDetailById(customerAgencyFeescaleDetailDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("收费标准详细删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询收费标准详细
	 * @Title: selectCustomerAgencyFeescaleDetailById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleDetailDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerAgencyFeescaleDetailById")
	public RespDataObject<CustomerAgencyFeescaleDetailDto> selectCustomerAgencyFeescaleDetailById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto){ 
		 RespDataObject<CustomerAgencyFeescaleDetailDto> status = new  RespDataObject<CustomerAgencyFeescaleDetailDto>();
		if(customerAgencyFeescaleDetailDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerAgencyFeescaleDetailService.selectCustomerAgencyFeescaleDetailById(customerAgencyFeescaleDetailDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
}
