package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.risk.LawsuitCnDto;
public interface LawsuitCnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LawsuitCnDto record);

    int insertSelective(LawsuitCnDto record);

    LawsuitCnDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LawsuitCnDto record);

    int updateByPrimaryKey(LawsuitCnDto record);
    
    /**
     * 组合查询全国诉讼记录集合
     * @param map
     * @return
     */
    List<Map<String, Object>> getListMap(Map<String, Object> map);
    
    /**
     * 删除某一次查询的全国诉讼记录
     * @param map
     * @return
     */
    int deleteByMap(Map<String, Object> map);
}