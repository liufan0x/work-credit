package com.anjbo.service;

import java.util.List;
import java.util.Map;

public interface ProductFileBaseService {

	/**
	 * 保存上传的文件信息
	 * @param map
	 * @return
	 */
	int insertFile(Map<String,Object> map);

	/**
	 * 批量保存上传的文件信息
	 * @param list
	 * @return
	 */
	int batchFile(List<Map<String,Object>> list);

	/**
	 * 查询保存的文件信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> listFile(Map<String,Object> map);

	/**
	 * 根据id删除文件信息
	 * @param map
	 */
	void deleteFileById(Map<String,Object> map);

	/**
	 * 根据订单号或和TblName删除文件信息
	 * @param map
	 */
	void deleteFileByOrderNoAndTblName(Map<String,Object> map);

	/**
	 * 根据id查询保存的文件
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> listFileByIds(Map<String,Object> map);
}
