package com.anjbo.dao;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.common.SubBankDto;

import java.util.List;
import java.util.Map;

/**
 * 数据通用
 * @author lic
 * @date 2017-6-1
 */
public interface CommonMapper {

	List<BankDto> selectBankList();
	List<BankDto> findByBankList(Map<String, Object> map);
	int findByBankListCount(Map<String, Object> map);
	DictDto findbyDict(int id);
	int addBank(Map<String, Object> map);
	int updBank(Map<String, Object> map);
	
	
	List<SubBankDto> selectSubBankList();
	List<SubBankDto> findByBankSubList(Map<String, Object> map);
	int findByBankSubListCount(Map<String, Object> map);
	int addBankSub(Map<String, Object> map);
	int updBankSub(Map<String, Object> map);
	
	
	List<DictDto> selectDictList();
	List<DictDto> findByDictList(Map<String, Object> map);
	int findByDictListCount(Map<String, Object> map);
	int addDict(Map<String, Object> map);
	int updDict(Map<String, Object> map);
	
	List<Map<String, Object>> findByYnBankAll();
	
	List<Map<String, Object>> selectAdministrationDivide();

	Map<String,Object> selectAdministrationDivideByName(Map<String,Object> map);
}