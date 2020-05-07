package com.anjbo.service.tools.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.tools.DegreeBuildingDto;
import com.anjbo.bean.tools.DegreeHouseNumDto;
import com.anjbo.dao.mort.DegreeHouseNumberMapper;
import com.anjbo.service.tools.DegreeHouseNumberService;


@Service
public class DegreeHouseNumberServiceImpl implements DegreeHouseNumberService {

    @Resource
    private DegreeHouseNumberMapper degreeHouseNumberMapper;

    @Override
    public boolean isIncludedHouseNumber(String houseNum) {
        List<DegreeHouseNumDto> houseNumDtos = degreeHouseNumberMapper.queryByHouseNum(houseNum);
        if(houseNumDtos==null||houseNumDtos.size()==0){
            return false;
        }
        return true;
    }

    @Override
    public void insert(DegreeHouseNumDto dto) {
        degreeHouseNumberMapper.insert(dto);
    }

    @Override
    public void insertBuilding(DegreeBuildingDto dto) {
        degreeHouseNumberMapper.insertBuilding(dto);
    }

    @Override
    public List<DegreeBuildingDto> queryBuildingDtoByKeyWord(String propertyName) {
        return degreeHouseNumberMapper.queryBuildingDtoByKeyWord(propertyName);
    }

    @Override
    public List<DegreeBuildingDto> queryByBuildingId(String buildingID) {
        return degreeHouseNumberMapper.queryByBuildingId(buildingID);
    }
}
