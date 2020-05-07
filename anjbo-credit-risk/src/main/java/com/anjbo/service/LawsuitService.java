package com.anjbo.service;

import com.anjbo.bean.risk.LawsuitDto;

public interface LawsuitService {

	int deleteByPrimaryKey(Integer id);

    int insert(LawsuitDto record);

    int insertSelective(LawsuitDto record);

    LawsuitDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LawsuitDto record);

    int updateByPrimaryKey(LawsuitDto record);
    
    /**
     * 根据订单号查诉讼
     * @param orderNo
     * @return
     */
    LawsuitDto selectByOrderNo(String orderNo);
    
    public int updateByOrderNo(LawsuitDto obj);

    public LawsuitDto selectByOrderNo(String orderNo,boolean isImg);
}
