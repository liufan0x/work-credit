package com.anjbo.dao.mort;

import java.util.List;

import com.anjbo.bean.tools.DegreeBuildingDto;
import com.anjbo.bean.tools.DegreeHouseNumDto;

/**
 * Created by Administrator on 2016/11/9.
 */
public interface DegreeHouseNumberMapper {
    List<DegreeHouseNumDto> queryByHouseNum(String houseNum);

    void insert(DegreeHouseNumDto dto);

    void insertBuilding(DegreeBuildingDto dto);

    List<DegreeBuildingDto> queryBuildingDtoByKeyWord(String propertyName);

    List<DegreeBuildingDto> queryByBuildingId(String buildingID);
}
