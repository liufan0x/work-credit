package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.CustomerAgencyFeescaleModeDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CustomerAgencyFeescaleModeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
  * 收费方式 [Controller]
  * @ClassName: CustomerAgencyFeescaleModeController
  * @Description: 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/agency/feescale/mode/v")
public class CustomerAgencyFeescaleModeController extends BaseController{

	private Log log = LogFactory.getLog(CustomerAgencyFeescaleModeController.class);
	
	@Resource 
	private CustomerAgencyFeescaleModeService customerAgencyFeescaleModeService;

	/**
	 * 收费方式分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param request
	 * @return customerAgencyFeescaleModeDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerAgencyFeescaleModeDto> page(HttpServletRequest request,@RequestBody CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto){
		RespPageData<CustomerAgencyFeescaleModeDto> resp = new RespPageData<CustomerAgencyFeescaleModeDto>();
		resp.setTotal(customerAgencyFeescaleModeService.selectCustomerAgencyFeescaleModeCount(customerAgencyFeescaleModeDto));
		resp.setRows(customerAgencyFeescaleModeService.selectCustomerAgencyFeescaleModeList(customerAgencyFeescaleModeDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 收费方式编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleModeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto){ 
		try {
			if(customerAgencyFeescaleModeDto.getId()>0){
				customerAgencyFeescaleModeDto.setUpdateUid(getUserDto(request).getUid());
				customerAgencyFeescaleModeService.updateCustomerAgencyFeescaleMode(customerAgencyFeescaleModeDto);
			}else{
				customerAgencyFeescaleModeDto.setCreateUid(getUserDto(request).getUid());
				customerAgencyFeescaleModeService.addCustomerAgencyFeescaleMode(customerAgencyFeescaleModeDto);
			}
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("收费方式编辑失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 根据ID删除收费方式
	 * @Title: deleteCustomerAgencyFeescaleModeById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleModeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerAgencyFeescaleModeById")
	public RespStatus deleteCustomerAgencyFeescaleModeById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto){ 
		RespStatus status = new  RespStatus();
		if(customerAgencyFeescaleModeDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerAgencyFeescaleModeService.deleteCustomerAgencyFeescaleModeById(customerAgencyFeescaleModeDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("收费方式删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询收费方式
	 * @Title: selectCustomerAgencyFeescaleModeById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleModeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerAgencyFeescaleModeById")
	public RespDataObject<CustomerAgencyFeescaleModeDto> selectCustomerAgencyFeescaleModeById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleModeDto customerAgencyFeescaleModeDto){ 
		 RespDataObject<CustomerAgencyFeescaleModeDto> status = new  RespDataObject<CustomerAgencyFeescaleModeDto>();
		if(customerAgencyFeescaleModeDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerAgencyFeescaleModeService.selectCustomerAgencyFeescaleModeById(customerAgencyFeescaleModeDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
}
