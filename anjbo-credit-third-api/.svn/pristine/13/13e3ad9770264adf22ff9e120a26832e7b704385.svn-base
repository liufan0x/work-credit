package com.anjbo.dao.huarong;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface FileApplyMapper {
	
	int saveFileApply(List<Map<String,Object>> list);
	
	public List<Map<String, Object>> getFileApply(@Param("orderNo") String  orderNo);
	
	/**
	 * 根据路径集合，返回已处理成功的集合
	 * @Author KangLG<2017年10月30日>
	 * @param lstSuccess
	 * @return
	 */
	List<String> searchFilePaths(List<String> lstSuccess);
}
