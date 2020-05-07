package com.anjbo.service;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.common.SubBankDto;

import java.util.List;
import java.util.Map;

public interface CommonService {

	/**
	 * 初始化数据
	 */
	void initData();
	
	/**
	 * 查询所有银行
	 * @return
	 */
	List<BankDto> selectBankList();
	List<BankDto> findByBankList(Map<String, Object> map);
	int findByBankListCount(Map<String, Object> map);
	int addBank(Map<String, Object> map);
	int updBank(Map<String, Object> map);
	/**
	 * 查询所有支行
	 * @return
	 */
	List<SubBankDto> selectSubBankList();
	List<SubBankDto> findByBankSubList(Map<String, Object> map);
	int findByBankSubListCount(Map<String, Object> map);
	int addBankSub(Map<String, Object> map);
	int updBankSub(Map<String, Object> map);
	
	/**
	 * 查询所有字典
	 * @return
	 */
	List<DictDto> selectDictList();
	
	List<DictDto> findByDictList(Map<String, Object> map);
	int findByDictListCount(Map<String, Object> map);
	DictDto findbyDict(int id);
	int addDict(Map<String, Object> map);
	int updDict(Map<String, Object> map);
	
	List<Map<String, Object>> findByYnBankAll();
	
	public List<Map<String, Object>> selectAdministrationDivide();

	Map<String,Object> selectAdministrationDivideByName(Map<String,Object> map);
}
