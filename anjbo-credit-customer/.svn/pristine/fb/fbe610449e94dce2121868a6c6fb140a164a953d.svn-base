package com.anjbo.service.impl;

import com.anjbo.bean.customer.AgencyIncomeModeDto;
import com.anjbo.bean.customer.AgencyProductDto;
import com.anjbo.bean.customer.CustomerAgencyAcceptDto;
import com.anjbo.bean.customer.CustomerAgencyDto;
import com.anjbo.dao.AgencyIncomeModeMapper;
import com.anjbo.dao.AgencyProductMapper;
import com.anjbo.dao.CustomerAgencyAcceptMapper;
import com.anjbo.dao.CustomerAgencyMapper;
import com.anjbo.service.CustomerAgencyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
  * 机构 [Service实现类]
  * @ClassName: CustomerAgencyServiceImpl
  * @Description: 机构业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
@Service
public class CustomerAgencyServiceImpl  implements CustomerAgencyService
{
	
	@Resource
	private CustomerAgencyMapper customerAgencyMapper;
	@Resource
	private CustomerAgencyAcceptMapper customerAgencyAcceptMapper;
	@Resource
	private AgencyIncomeModeMapper agencyIncomeModeMapper;
	@Resource
	private AgencyProductMapper agencyProductMapper;

	@Override
	public List<CustomerAgencyDto> selectCustomerAgencyList(CustomerAgencyDto customerAgencyDto){
		return customerAgencyMapper.selectCustomerAgencyList(customerAgencyDto);
	}

	@Override
	public int selectCustomerAgencyCount(CustomerAgencyDto customerAgencyDto) {
		return customerAgencyMapper.selectCustomerAgencyCount(customerAgencyDto);
	}
	
	@Override
	public int addCustomerAgency(CustomerAgencyDto customerAgencyDto) {
		return customerAgencyMapper.addCustomerAgency(customerAgencyDto);
	}
	
	@Override
	public int updateCustomerAgency(CustomerAgencyDto customerAgencyDto) {
		List<AgencyIncomeModeDto> listIncome = customerAgencyDto.getListIncome();
		int success = customerAgencyMapper.updateCustomerAgency(customerAgencyDto);
		if(null!=listIncome&&listIncome.size()>0){
			//删除旧的费用支付方式
			AgencyIncomeModeDto in = new AgencyIncomeModeDto();
			in.setAgencyId(customerAgencyDto.getId());
			agencyIncomeModeMapper.delete(in);
			//新增新的费用支付方式
			agencyIncomeModeMapper.batchInsert(listIncome);
		}
		return success;
	}
	
	@Override
	public int deleteCustomerAgencyById(int id){
		return customerAgencyMapper.deleteCustomerAgencyById(id);
	}
	
	@Override
	public CustomerAgencyDto selectCustomerAgencyById(int id){
		List<CustomerAgencyAcceptDto> list = customerAgencyAcceptMapper.selectCustomerAgencyAcceptList(null);
		List<CustomerAgencyAcceptDto> listTemp = new ArrayList<CustomerAgencyAcceptDto>();
		CustomerAgencyDto customerAgencyDto = customerAgencyMapper.selectCustomerAgencyById(id);
		for (CustomerAgencyAcceptDto customerAgencyAcceptDto : list) {
			if(customerAgencyAcceptDto.getAgencyId() == id){
				listTemp.add(customerAgencyAcceptDto);
			}
		}

		customerAgencyDto.setCustomerAgencyAcceptDtos(listTemp);
		//费用支付方式
		AgencyIncomeModeDto incomeModeDto = new AgencyIncomeModeDto();
		incomeModeDto.setAgencyId(customerAgencyDto.getId());
		List<AgencyIncomeModeDto> listIncome = agencyIncomeModeMapper.search(incomeModeDto);
		customerAgencyDto.setListIncome(listIncome);
		//机构关联产品
		AgencyProductDto productDto = new AgencyProductDto();
		productDto.setStatus(1);
		productDto.setAgencyId(customerAgencyDto.getId());
		List<AgencyProductDto> listProduct = agencyProductMapper.search(productDto);
		customerAgencyDto.setListProduct(listProduct);
		return customerAgencyDto;
	}
	
}