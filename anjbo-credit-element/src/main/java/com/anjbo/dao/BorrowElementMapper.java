package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ElementListDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;


public interface BorrowElementMapper {
	
	//记录已借出的要件，便于计算超时未还的要件
	public int insertBorrowElementRecorde(List<Map<String,Object>> list);	
	
	
	//当已归还要件时，删除借出的要件记录
    public int deleteBorrowElementRecorde(String  id);	
	
    //查询已经借出的要件
    public List<Map<String,Object>> selectBorrowElementByOrderNo(Map<String,Object> map);
    
    /**
      * 更新借用状态为已归还
     * @param map
     */
    public void updateBorrowElement(Map<String,Object> map);
    
    /**
     * 取消超时未还检查
     * @param map
     */
    public void updateHasMarkOverTime(Map<String,Object> map);
    
    List<Map<String,Object>> selectBorrowElementByDbId(Map<String,Object> map);
}