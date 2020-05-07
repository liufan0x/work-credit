package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.OrderReceivablleReportVo;


public interface ReceivableReportMapper {
	List<OrderReceivablleReportVo> findByAll(Map<String, Object> pareamt);
}
