package com.anjbo.service.impl;

import com.anjbo.dao.ProductFileBaseMapper;
import com.anjbo.service.ProductFileBaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class ProductFileBaseServiceImpl implements ProductFileBaseService{
	@Resource
	private ProductFileBaseMapper productFileBaseMapper;
	/**
	 * 保存上传的文件信息
	 *
	 * @param map
	 * @return
	 */
	@Override
	public int insertFile(Map<String, Object> map) {
		return productFileBaseMapper.insertFile(map);
	}

	/**
	 * 批量保存上传的文件信息
	 *
	 * @param list
	 * @return
	 */
	@Override
	public int batchFile(List<Map<String, Object>> list) {
		return productFileBaseMapper.batchFile(list);
	}

	/**
	 * 查询保存的文件信息
	 *
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> listFile(Map<String, Object> map) {
		return productFileBaseMapper.listFile(map);
	}

	/**
	 * 根据id删除文件信息
	 *
	 * @param map
	 */
	@Override
	public void deleteFileById(Map<String,Object> map) {
		productFileBaseMapper.deleteFileById(map);
	}

	/**
	 * 根据订单号或和TblName删除文件信息
	 *
	 * @param map
	 */
	@Override
	public void deleteFileByOrderNoAndTblName(Map<String, Object> map) {
		productFileBaseMapper.deleteFileByOrderNoAndTblName(map);
	}
	/**
	 * 根据id查询保存的文件
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String,Object>> listFileByIds(Map<String,Object> map){
		return productFileBaseMapper.listFileByIds(map);
	}
}
