package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.CustomerAgencyFeescaleDetailMapper;
import com.anjbo.dao.CustomerAgencyFeescaleSectionMapper;
import com.anjbo.service.CustomerAgencyFeescaleSectionService;
import com.anjbo.bean.customer.CustomerAgencyFeescaleDetailDto;
import com.anjbo.bean.customer.CustomerAgencyFeescaleSectionDto;

/**
  * 收费设置 [Service实现类]
  * @ClassName: CustomerAgencyFeescaleSectionServiceImpl
  * @Description: 收费设置业务服务
  * @author 
  * @date 2017-07-06 15:03:11
  * @version V3.0
 */
@Service
public class CustomerAgencyFeescaleSectionServiceImpl  implements CustomerAgencyFeescaleSectionService
{
	
	@Resource
	private CustomerAgencyFeescaleSectionMapper customerAgencyFeescaleSectionMapper;
	
	@Resource 
	private CustomerAgencyFeescaleDetailMapper customerAgencyFeescaleDetailMapper;

	@Override
	public List<CustomerAgencyFeescaleSectionDto> selectCustomerAgencyFeescaleSectionList(CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto){
		List<CustomerAgencyFeescaleSectionDto> customerAgencyFeescaleSectionDtoList = customerAgencyFeescaleSectionMapper.selectCustomerAgencyFeescaleSectionList(customerAgencyFeescaleSectionDto);
		List<CustomerAgencyFeescaleDetailDto> list = customerAgencyFeescaleDetailMapper.selectCustomerAgencyFeescaleDetailList(null);
		if(customerAgencyFeescaleSectionDto == null){
			for (CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSection : customerAgencyFeescaleSectionDtoList) {
				List<CustomerAgencyFeescaleDetailDto> tempList = new ArrayList<CustomerAgencyFeescaleDetailDto>();
				for (CustomerAgencyFeescaleDetailDto customerAgencyFeescaleDetailDto : list) {
					if(customerAgencyFeescaleDetailDto.getSectionid() == customerAgencyFeescaleSection.getId()){
						tempList.add(customerAgencyFeescaleDetailDto);
					}
				}
				customerAgencyFeescaleSection.setCustomerAgencyFeescaleDetailList(tempList);
			}
		}
		return customerAgencyFeescaleSectionDtoList;
	}

	@Override
	public int selectCustomerAgencyFeescaleSectionCount(CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto) {
		return customerAgencyFeescaleSectionMapper.selectCustomerAgencyFeescaleSectionCount(customerAgencyFeescaleSectionDto);
	}
	
	@Override
	public int addCustomerAgencyFeescaleSection(CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto) {
		return customerAgencyFeescaleSectionMapper.addCustomerAgencyFeescaleSection(customerAgencyFeescaleSectionDto);
	}
	
	@Override
	public int updateCustomerAgencyFeescaleSection(CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto) {
		return customerAgencyFeescaleSectionMapper.updateCustomerAgencyFeescaleSection(customerAgencyFeescaleSectionDto);
	}
	
	@Override
	public int deleteCustomerAgencyFeescaleSectionById(int id){
		return customerAgencyFeescaleSectionMapper.deleteCustomerAgencyFeescaleSectionById(id);
	}
	
	@Override
	public CustomerAgencyFeescaleSectionDto selectCustomerAgencyFeescaleSectionById(int id){
		CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto = customerAgencyFeescaleSectionMapper.selectCustomerAgencyFeescaleSectionById(id);
		
		return customerAgencyFeescaleSectionDto;
	}
	
}