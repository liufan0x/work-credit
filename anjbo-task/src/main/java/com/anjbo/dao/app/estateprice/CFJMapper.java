package com.anjbo.dao.app.estateprice;

import com.anjbo.bean.estateprice.SZCFJDto;
import com.anjbo.bean.estateprice.TZCPropertyDto;


import java.util.List;


public interface CFJMapper {

    int insertShenZhenCFJDtoBatch(List<SZCFJDto> dtos);

    SZCFJDto selectShenZhenCFJDtoByPropertyNameCurrentDay(String propertyName, String date);

    List<SZCFJDto> selectShenZhenCFJDtoAllCurrentDay(String date);

    int insertTZCPropertyDtoBatch(List<TZCPropertyDto> dtos);

    int deleteTZCPropertyDtoByDate(String date);

    int deleteShenZhenCFJDtoAll();

    int deleteTZCPropertyDtoAll();

    void insertTZCPropertyDtoBatch2(List<TZCPropertyDto> tzcPropertyDtos);

    void deleteTZCPropertyDtoByDateAndArea(String dateByFmt, String areaRange);

    Integer selectTZCPropertyDtoCount(TZCPropertyDto tzcPropertyDto);
}
