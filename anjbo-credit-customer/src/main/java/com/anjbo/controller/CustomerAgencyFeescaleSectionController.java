package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.CustomerAgencyFeescaleSectionDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CustomerAgencyFeescaleSectionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
  * 收费设置 [Controller]
  * @ClassName: CustomerAgencyFeescaleSectionController
  * @Description: 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
@Controller
@RequestMapping("/credit/customer/agency/feescale/section/v")
public class CustomerAgencyFeescaleSectionController extends BaseController{

	private Log log = LogFactory.getLog(CustomerAgencyFeescaleSectionController.class);
	
	@Resource 
	private CustomerAgencyFeescaleSectionService customerAgencyFeescaleSectionService;

	/**
	 * 收费设置分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param request
	 * @return customerAgencyFeescaleSectionDto
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespPageData<CustomerAgencyFeescaleSectionDto> page(HttpServletRequest request,@RequestBody CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto){
		RespPageData<CustomerAgencyFeescaleSectionDto> resp = new RespPageData<CustomerAgencyFeescaleSectionDto>();
		resp.setTotal(customerAgencyFeescaleSectionService.selectCustomerAgencyFeescaleSectionCount(customerAgencyFeescaleSectionDto));
		resp.setRows(customerAgencyFeescaleSectionService.selectCustomerAgencyFeescaleSectionList(customerAgencyFeescaleSectionDto));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 收费设置编辑
	 * @Title: edit
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleSectionDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto){ 
		try {
			if(customerAgencyFeescaleSectionDto.getId()>0){
				customerAgencyFeescaleSectionDto.setUpdateUid(getUserDto(request).getUid());
				customerAgencyFeescaleSectionService.updateCustomerAgencyFeescaleSection(customerAgencyFeescaleSectionDto);
			}else{
				customerAgencyFeescaleSectionDto.setCreateUid(getUserDto(request).getUid());
				customerAgencyFeescaleSectionService.addCustomerAgencyFeescaleSection(customerAgencyFeescaleSectionDto);
			}
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("收费设置编辑失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 根据ID删除收费设置
	 * @Title: deleteCustomerAgencyFeescaleSectionById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleSectionDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerAgencyFeescaleSectionById")
	public RespStatus deleteCustomerAgencyFeescaleSectionById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto){ 
		RespStatus status = new  RespStatus();
		if(customerAgencyFeescaleSectionDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		try {
			customerAgencyFeescaleSectionService.deleteCustomerAgencyFeescaleSectionById(customerAgencyFeescaleSectionDto.getId());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("收费设置删除失败，异常信息：", e);
			return RespHelper.failRespStatus();
		}
		return status;
	}
	
	/**
	 * 根据ID查询收费设置
	 * @Title: selectCustomerAgencyFeescaleSectionById
	 * @Description: 
	 * @author mark 
	 * @date 2017-07-06 15:07:09
	 * @param customerAgencyFeescaleSectionDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomerAgencyFeescaleSectionById")
	public RespDataObject<CustomerAgencyFeescaleSectionDto> selectCustomerAgencyFeescaleSectionById(HttpServletRequest request, @RequestBody CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto){ 
		 RespDataObject<CustomerAgencyFeescaleSectionDto> status = new  RespDataObject<CustomerAgencyFeescaleSectionDto>();
		if(customerAgencyFeescaleSectionDto.getId()<=0){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		status.setData(customerAgencyFeescaleSectionService.selectCustomerAgencyFeescaleSectionById(customerAgencyFeescaleSectionDto.getId()));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
}
