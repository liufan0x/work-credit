package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.contract.OrderContractCustomerBorrowerDto;
import com.anjbo.bean.contract.OrderContractDataDto;

public interface OrderContractDataService {

	/**
	 * 录入合同数据
	 * @param orderContractDataDto
	 * @return
	 */
	int insertOrderContractData(OrderContractDataDto orderContractDataDto);
	
	/**
	 * 更新合同数据
	 * @param orderContractDataDto
	 * @return
	 */
	int updateOrderContractData(OrderContractDataDto orderContractDataDto);
	
	/**
	 * 查询合同数据
	 * @param orderContractDataDto
	 * @return
	 */
	OrderContractDataDto selectOrderContractDataDto(OrderContractDataDto orderContractDataDto);
	
	/**
	 * 删除合同内容
	 * @param orderContractDataDto
	 * @return
	 */
	int delete(OrderContractDataDto orderContractDataDto);
	
	/**
	 * 共同借款人集合
	 * @param orderContractDataDto
	 * @return
	 */
	public List<OrderContractCustomerBorrowerDto> queryContractCustomerBorrow(OrderContractDataDto orderContractDataDto);
}
