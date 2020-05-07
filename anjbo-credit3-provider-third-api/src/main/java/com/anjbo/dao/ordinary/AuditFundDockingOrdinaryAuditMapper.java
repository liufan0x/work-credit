package com.anjbo.dao.ordinary;

import java.util.Map;

import com.anjbo.bean.ordinary.AuditFundDockingOrdinaryAuditDto;
import com.sun.star.task.InteractionRequestStringResolver;

public interface AuditFundDockingOrdinaryAuditMapper {
   
	public AuditFundDockingOrdinaryAuditDto findByOrdinaryAudit(Map<String, Object> parament);   //查询
	
	public int  insert(Map<String, Object> parament);       //添加

    public int  delete(Map<String, Object> parament);      //删除  
}
