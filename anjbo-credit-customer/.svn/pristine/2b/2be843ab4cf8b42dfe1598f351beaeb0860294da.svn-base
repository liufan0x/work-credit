package com.anjbo.service.impl;

import com.anjbo.bean.customer.AgencyProductDto;
import com.anjbo.dao.AgencyProductMapper;
import com.anjbo.service.AgencyProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
public class AgecnyProductServiceImpl implements AgencyProductService{

    @Resource
    private AgencyProductMapper agencyProductMapper;

    public List<AgencyProductDto> search(AgencyProductDto obj){
        return agencyProductMapper.search(obj);
    }

    public void batchInsert(List<AgencyProductDto> list){
        agencyProductMapper.batchInsert(list);
    }

    public int update(AgencyProductDto obj){
        return agencyProductMapper.update(obj);
    }
    
    public List<AgencyProductDto> findAllCityProduct(AgencyProductDto obj){
        return agencyProductMapper.findAllCityProduct(obj);
    }
}
