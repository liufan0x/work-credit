package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.anjbo.bean.customer.CustomerAgencyTypeDto;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.CustomerAgencyTypeMapper;
import com.anjbo.service.CustomerAgencyTypeService;

/**
  * 机构类型 [Service实现类]
  * @ClassName: CustomerAgencyTypeServiceImpl
  * @Description: 机构类型业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
@Service
public class CustomerAgencyTypeServiceImpl  implements CustomerAgencyTypeService
{


	private static final Log log = LogFactory.getLog(CustomerAgencyTypeServiceImpl.class);
	
	@Resource
	private CustomerAgencyTypeMapper customerAgencyTypeMapper;

	@Override
	public List<CustomerAgencyTypeDto> selectCustomerAgencyTypeList(CustomerAgencyTypeDto customerAgencyTypeDto){
		return customerAgencyTypeMapper.selectCustomerAgencyTypeList(customerAgencyTypeDto);
	}

	@Override
	public int selectCustomerAgencyTypeCount(CustomerAgencyTypeDto customerAgencyTypeDto) {
		return customerAgencyTypeMapper.selectCustomerAgencyTypeCount(customerAgencyTypeDto);
	}
	
	@Override
	public RespStatus addCustomerAgencyType(CustomerAgencyTypeDto customerAgencyTypeDto) {
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			CustomerAgencyTypeDto typeDto=customerAgencyTypeMapper.selectCustomerAgencyTypeByName(customerAgencyTypeDto);
			if(typeDto==null){
				customerAgencyTypeMapper.addCustomerAgencyType(customerAgencyTypeDto);
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}else{
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("该机构类型已存在！");
			}
		} catch (Exception e) {
			log.info("新增机构类型异常",e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@Override
	public RespStatus updateCustomerAgencyType(CustomerAgencyTypeDto customerAgencyTypeDto) {
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			CustomerAgencyTypeDto typeDto=customerAgencyTypeMapper.selectCustomerAgencyTypeByName(customerAgencyTypeDto);
			if(typeDto==null){
				customerAgencyTypeMapper.updateCustomerAgencyType(customerAgencyTypeDto);
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}else{
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("该机构类型已存在！");
			}
		} catch (Exception e) {
			log.info("修改机构类型异常,id:",e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@Override
	public int deleteCustomerAgencyTypeById(int id){
		return customerAgencyTypeMapper.deleteCustomerAgencyTypeById(id);
	}
	
	@Override
	public CustomerAgencyTypeDto selectCustomerAgencyTypeById(int id){
		return customerAgencyTypeMapper.selectCustomerAgencyTypeById(id);
	}
	
}