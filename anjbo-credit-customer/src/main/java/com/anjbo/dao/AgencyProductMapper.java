package com.anjbo.dao;

import com.anjbo.bean.customer.AgencyProductDto;

import java.util.List;

public interface AgencyProductMapper {

	List<AgencyProductDto> search(AgencyProductDto obj);

	void batchInsert(List<AgencyProductDto> list);

	int update(AgencyProductDto obj);

	int delete(AgencyProductDto obj);

	List<AgencyProductDto> findAllCityProduct(AgencyProductDto obj);

	List<AgencyProductDto> allAgencyProduct();
}