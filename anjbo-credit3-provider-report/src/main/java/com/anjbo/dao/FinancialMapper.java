package com.anjbo.dao;

import java.util.List;
import java.util.Map;

public interface FinancialMapper {
	 List<Map<String,Object>> financialList(Map<String,Object> map);
	 int financialCount(Map<String,Object> map);
}
