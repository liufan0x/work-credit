package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.risk.LawsuitSzDto;

public interface LawsuitSzMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LawsuitSzDto record);

    int insertSelective(LawsuitSzDto record);

    LawsuitSzDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LawsuitSzDto record);

    int updateByPrimaryKey(LawsuitSzDto record);
    
    /**
     * 组合查询深圳诉讼记录集合
     * @param map
     * @return
     */
    List<Map<String, Object>> getListMap(Map<String, Object> map);
    
    /**
     * 删除某一次查询下的所有深圳诉讼
     * @param map
     * @return
     */
    int deleteByMap(Map<String, Object> map);
}