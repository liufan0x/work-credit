package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.risk.LawsuitDto;

public interface LawsuitMapper {
	int deleteByPrimaryKey(Integer id);

    int insert(LawsuitDto record);

    int insertSelective(LawsuitDto record);

    LawsuitDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LawsuitDto record);

    int updateByPrimaryKey(LawsuitDto record);
    
    LawsuitDto selectByOrderNo(String orderNo);
    
    public int updateByOrderNo(LawsuitDto obj);
    
    public List<LawsuitDto> detailByOrderNo(LawsuitDto obj);
}
