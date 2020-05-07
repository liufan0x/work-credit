package com.anjbo.service.estateprice;



import com.anjbo.bean.estateprice.SZCFJDto;
import com.anjbo.bean.estateprice.TZCPropertyDto;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
public interface CFJService {

    /**
     * 批量插入查房价数据
     * @param dtos
     * @return
     */
    int insertShenZhenCFJDtoBatch(List<SZCFJDto> dtos);

    /**
     * 更新查房价数据
     * @param dtos
     * @return
     */
    int updateShenZhenCFJDtoBatch(List<SZCFJDto> dtos);

    /**
     * 清空查房价数据
     * @return
     */
    int deleteShenZhenCFJDtoAll();

    /**
     * 根据物业名称查询当天的查房价数据
     * @param propertyName
     * @return
     */
    SZCFJDto selectShenZhenCFJDtoByPropertyNameCurrentDay(String propertyName);

    /**
     * 查询当天所有的查房价数据
     * @return
     */
    List<SZCFJDto> selectShenZhenCFJDtoAllCurrentDay();

    /**
     * 批量插入计算后的房屋均价数据
     * @param dtos
     * @return
     */
    int insertTZCPropertyDtoBatch(List<TZCPropertyDto> dtos);

    /**
     * 按日期删除同志诚数据
     * @param date
     * @return
     */
    int deleteTZCPropertyDtoByDate(String date);

    /**
     * 清空同志诚数据
     * @param date
     * @return
     */
    int deleteTZCPropertyDtoAll();
    /**
     * 更新同志诚数据
     * @param dtos
     */
    void updateTZCPropertyDtoByDate(List<TZCPropertyDto> dtos);

    void insertTZCPropertyDtoByDate(List<TZCPropertyDto> tzcPropertyDtos);

    Integer selectTZCPropertyDtoCount(TZCPropertyDto tzcPropertyDto);

}
