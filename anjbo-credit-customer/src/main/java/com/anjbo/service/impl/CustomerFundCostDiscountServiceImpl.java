package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.anjbo.dao.CustomerFundCostDiscountMapper;
import com.anjbo.service.CustomerFundCostDiscountService;
import com.anjbo.bean.customer.CustomerFundCostDiscountDto;

/**
  * 合作资金方业务产品优惠 [Service实现类]
  * @ClassName: CustomerFundCostDiscountServiceImpl
  * @Description: 合作资金方业务产品优惠业务服务
  * @author 
  * @date 2017-07-06 15:53:15
  * @version V3.0
 */
@Service
public class CustomerFundCostDiscountServiceImpl  implements CustomerFundCostDiscountService
{
	
	@Resource
	private CustomerFundCostDiscountMapper customerFundCostDiscountMapper;

	@Override
	public List<CustomerFundCostDiscountDto> selectCustomerFundCostDiscountList(CustomerFundCostDiscountDto customerFundCostDiscountDto){
		return customerFundCostDiscountMapper.selectCustomerFundCostDiscountList(customerFundCostDiscountDto);
	}

	@Override
	public int selectCustomerFundCostDiscountCount(CustomerFundCostDiscountDto customerFundCostDiscountDto) {
		return customerFundCostDiscountMapper.selectCustomerFundCostDiscountCount(customerFundCostDiscountDto);
	}
	
	@Override
	public int addCustomerFundCostDiscount(CustomerFundCostDiscountDto customerFundCostDiscountDto) {
		return customerFundCostDiscountMapper.addCustomerFundCostDiscount(customerFundCostDiscountDto);
	}
	
	@Override
	public int updateCustomerFundCostDiscount(CustomerFundCostDiscountDto customerFundCostDiscountDto) {
		return customerFundCostDiscountMapper.updateCustomerFundCostDiscount(customerFundCostDiscountDto);
	}
	
	@Override
	public int deleteCustomerFundCostDiscountById(int id){
		return customerFundCostDiscountMapper.deleteCustomerFundCostDiscountById(id);
	}
	
	@Override
	public CustomerFundCostDiscountDto selectCustomerFundCostDiscountById(int id){
		return customerFundCostDiscountMapper.selectCustomerFundCostDiscountById(id);
	}
	
	@Override
	public void deleteCustomerFundCostDiscountByFundCostId(int id) {
		customerFundCostDiscountMapper.deleteCustomerFundCostDiscountByFundCostId(id);
	}
}