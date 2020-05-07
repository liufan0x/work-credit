package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.anjbo.dao.CustomerAgencyFeescaleRiskcontrolMapper;
import com.anjbo.service.CustomerAgencyFeescaleRiskcontrolService;
import com.anjbo.service.CustomerAgencyFeescaleSectionService;
import com.anjbo.bean.customer.CustomerAgencyFeescaleDetailDto;
import com.anjbo.bean.customer.CustomerAgencyFeescaleRiskcontrolDto;
import com.anjbo.bean.customer.CustomerAgencyFeescaleSectionDto;

/**
  * 风控配置 [Service实现类]
  * @ClassName: CustomerAgencyFeescaleRiskcontrolServiceImpl
  * @Description: 风控配置业务服务
  * @author 
  * @date 2017-07-06 15:03:11
  * @version V3.0
 */
@Service
public class CustomerAgencyFeescaleRiskcontrolServiceImpl  implements CustomerAgencyFeescaleRiskcontrolService
{
	
	@Resource
	private CustomerAgencyFeescaleRiskcontrolMapper customerAgencyFeescaleRiskcontrolMapper;
	
	@Resource
	private CustomerAgencyFeescaleSectionService customerAgencyFeescaleSectionService;
	
	@Override
	public List<CustomerAgencyFeescaleRiskcontrolDto> selectCustomerAgencyFeescaleRiskcontrolList(CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto){
		List<CustomerAgencyFeescaleRiskcontrolDto> customerAgencyFeescaleRiskcontrolList = customerAgencyFeescaleRiskcontrolMapper.selectCustomerAgencyFeescaleRiskcontrolList(customerAgencyFeescaleRiskcontrolDto);
		if(customerAgencyFeescaleRiskcontrolDto == null){
			List<CustomerAgencyFeescaleSectionDto> list = customerAgencyFeescaleSectionService.selectCustomerAgencyFeescaleSectionList(null);
			for (CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrol : customerAgencyFeescaleRiskcontrolList) {
				List<CustomerAgencyFeescaleSectionDto> tempList = new ArrayList<CustomerAgencyFeescaleSectionDto>();
				for (CustomerAgencyFeescaleSectionDto customerAgencyFeescaleSectionDto : list) {
					if(customerAgencyFeescaleSectionDto.getRaskcontrolid() == customerAgencyFeescaleRiskcontrol.getId()){
						tempList.add(customerAgencyFeescaleSectionDto);
					}
				}
				customerAgencyFeescaleRiskcontrol.setCustomerAgencyFeescaleSectionList(tempList);
			}
		}
		return customerAgencyFeescaleRiskcontrolList;
	}

	@Override
	public int selectCustomerAgencyFeescaleRiskcontrolCount(CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto) {
		return customerAgencyFeescaleRiskcontrolMapper.selectCustomerAgencyFeescaleRiskcontrolCount(customerAgencyFeescaleRiskcontrolDto);
	}
	
	@Override
	public int addCustomerAgencyFeescaleRiskcontrol(CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto) {
		return customerAgencyFeescaleRiskcontrolMapper.addCustomerAgencyFeescaleRiskcontrol(customerAgencyFeescaleRiskcontrolDto);
	}
	
	@Override
	public int updateCustomerAgencyFeescaleRiskcontrol(CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto) {
		return customerAgencyFeescaleRiskcontrolMapper.updateCustomerAgencyFeescaleRiskcontrol(customerAgencyFeescaleRiskcontrolDto);
	}
	
	@Override
	public int deleteCustomerAgencyFeescaleRiskcontrolById(int id){
		return customerAgencyFeescaleRiskcontrolMapper.deleteCustomerAgencyFeescaleRiskcontrolById(id);
	}
	
	@Override
	public CustomerAgencyFeescaleRiskcontrolDto selectCustomerAgencyFeescaleRiskcontrolById(int id){
		return customerAgencyFeescaleRiskcontrolMapper.selectCustomerAgencyFeescaleRiskcontrolById(id);
	}
	
}