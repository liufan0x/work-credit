package com.anjbo.service.ordinary;

import java.util.Map;

import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.ordinary.AuditFundDockingOrdinaryDto;

public interface AuditFundDockingOrdinaryService {
    public int insert(Map<String, Object> parament);    //添加
	
	public int delete(Map<String, Object> parament);    //删除
	
	public AuditFundDockingOrdinaryDto findByOrdinary(Map<String, Object> parament);   //查找
}
