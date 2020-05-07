package com.anjbo.service;

import com.anjbo.bean.customer.AgencyProductDto;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface AgencyProductService {

    List<AgencyProductDto> search(AgencyProductDto obj);

    void batchInsert(List<AgencyProductDto> list);

    int update(AgencyProductDto obj);
    
    List<AgencyProductDto> findAllCityProduct(AgencyProductDto obj);
}
