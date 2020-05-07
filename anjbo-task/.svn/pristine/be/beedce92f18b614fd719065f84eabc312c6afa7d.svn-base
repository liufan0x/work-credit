package com.anjbo.dao.fc.order;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.DictDto;


public interface ReceivableReportMapper {
	List<DictDto> findByCity(String type);
	Map<String, Object> findByreceivableHas(Map<String, Object> pareamt);
	Map<String, Object> findByOverHas(Map<String, Object> pareamt);
	Map<String, Object> findByNotLending(Map<String, Object> pareamt);
	Map<String, Object> findByLending(Map<String, Object> pareamt);
	Map<String, Object> findByNotReceivable(Map<String, Object> pareamt);
	//全国总未回款
	Map<String, Object> findByNotReceivableAll(Map<String, Object> pareamt);
	//全国逾期总未回款
	Map<String, Object> findByNotOverAll(Map<String, Object> pareamt);
	
	int findByReport(Map<String, Object> pareamt);
    void deleteReport(Map<String, Object> pareamt);
	void  addReport(Map<String, Object> pareamt);
    
}
