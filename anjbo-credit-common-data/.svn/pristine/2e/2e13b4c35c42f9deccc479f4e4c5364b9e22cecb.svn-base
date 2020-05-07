package com.anjbo.service.impl;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.common.RedisOperator;
import com.anjbo.dao.CommonMapper;
import com.anjbo.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {
	
	@Resource
	private CommonMapper commonMapper;
	
	@Override
	public void initData(){
		initBank();
		initSubBank();
		initDict();
		selectBankList();
		selectSubBankList();
	}
	
	/**
	 * 初始化银行
	 */
	private void initBank(){
		List<BankDto> bankDtos = selectBankList();
		List<SubBankDto> subBankDtos = selectSubBankList();
		for (BankDto bankDto : bankDtos){
			List<SubBankDto> tempList = new ArrayList<SubBankDto>();
			for (SubBankDto subBankDto : subBankDtos) {
				if(subBankDto.getPid() == bankDto.getId()){
					tempList.add(subBankDto);
				}
			}
			bankDto.setSubBankDtos(tempList);
		}
		RedisOperator.set("bankList", bankDtos);
	}
	
	/**
	 * 初始化支行
	 */
	private void initSubBank(){
		RedisOperator.set("subBankList", selectSubBankList());
	}
	
	/**
	 * 初始化字典（以type为key）
	 */
	private void initDict(){
		List<DictDto> dictDtos = selectDictList();
		if(!dictDtos.isEmpty()&&dictDtos.size()>0){
			String tempStr = dictDtos.get(0).getType();
			List<DictDto> tempList = new ArrayList<DictDto>();
			Map<Object, Object> tempMap = new HashMap<Object, Object>();
			for (int i=0;i< dictDtos.size() ;i++) {
				tempStr = null==tempStr?"":tempStr;
				if(i==dictDtos.size()-1){
					tempList.add(dictDtos.get(i));
					tempMap.put(dictDtos.get(i).getCode(), dictDtos.get(i));
					if(!tempStr.equals("")){
						RedisOperator.set(tempStr, tempList);
						RedisOperator.putToMap(tempStr,tempMap);
					}
				}else if(tempStr.equals(dictDtos.get(i).getType())){
					tempMap.put(dictDtos.get(i).getCode(), dictDtos.get(i));
					tempList.add(dictDtos.get(i));
				}else{
					if(!tempStr.equals("")){
						RedisOperator.set(tempStr, tempList);
						RedisOperator.putToMap(tempStr,tempMap);
					}
					tempStr = dictDtos.get(i).getType();
					tempList = new ArrayList<DictDto>();
					tempMap = new HashMap<Object, Object>();
					tempList.add(dictDtos.get(i));
					tempMap.put(dictDtos.get(i).getCode(), dictDtos.get(i));
				}
			}
		}
	}
	
	@Override
	public List<BankDto> selectBankList(){
		List<BankDto> bankList = commonMapper.selectBankList();
		RedisOperator.set("allBankList", bankList);
		Map<Object, Object> tempMap = new HashMap<Object, Object>();
		for (BankDto bankDto : bankList) {
			tempMap.put(bankDto.getId(), bankDto);
		}
		RedisOperator.putToMap("bankListMap",tempMap);
		return bankList;
	}
	
	@Override
	public List<SubBankDto> selectSubBankList() {
		List<SubBankDto> subBankList = commonMapper.selectSubBankList();
		RedisOperator.set("allSubBankList", subBankList);
		Map<Object, Object> tempMap = new HashMap<Object, Object>();
		for (SubBankDto subBankDto : subBankList) {
			tempMap.put(subBankDto.getId(), subBankDto);
		}
		RedisOperator.putToMap("subBankListMap",tempMap);
		return subBankList;
	}
	
	@Override
	public List<DictDto> selectDictList(){
		List<DictDto> dictList = commonMapper.selectDictList();
		RedisOperator.set("dictList", dictList);
		String typeStr = "";
		Map<Object, Object> tempMap = new HashMap<Object, Object>();
		List<DictDto> tempList = new ArrayList<DictDto>();
		for (DictDto dictDto : dictList) {
			typeStr = null==typeStr?"":typeStr;
			if(typeStr.equals(dictDto.getType())){
				tempList.add(dictDto);
			}else{
				tempMap.put(typeStr, tempList);
				Map<Object, Object> tempMap2 = new HashMap<Object, Object>();
				for (DictDto dictDto2 : tempList) {
					tempMap2.put(dictDto2.getCode(), dictDto2);
				}
				RedisOperator.putToMap(typeStr,tempMap2);
				typeStr = dictDto.getType();
				tempList = new ArrayList<DictDto>(); 
			}
		}
		RedisOperator.putToMap("dictListMap",tempMap);
		return dictList;
	}
	/**
	 * 银行列表操作
	 */
	@Override
	public List<BankDto> findByBankList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.findByBankList(map);
	}
	@Override
	public int findByBankListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.findByBankListCount(map);
	}
	@Override
	public DictDto findbyDict(int id){
		return commonMapper.findbyDict(id);
	}
	@Override
	public int addBank(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.addBank(map);
	}

	@Override
	public int updBank(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.updBank(map);
	}
	
	/**
	 * 支行
	 */
	@Override
	public List<SubBankDto> findByBankSubList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.findByBankSubList(map);
	}
	@Override
	public int findByBankSubListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.findByBankSubListCount(map);
	}
	@Override
	public int addBankSub(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.addBankSub(map);
	}
	@Override
	public int updBankSub(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.updBankSub(map);
	}
	/**
	 * 字典列表操作
	 */
	@Override
	public List<DictDto> findByDictList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.findByDictList(map);
	}
	@Override
	public int findByDictListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.findByDictListCount(map);
	}
	@Override
	public int addDict(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.addDict(map);
	}
	@Override
	public int updDict(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return commonMapper.updDict(map);
	}

	@Override
	public List<Map<String, Object>> findByYnBankAll() {
		// TODO Auto-generated method stub
		return commonMapper.findByYnBankAll();
	}

	@Override
	public List<Map<String, Object>> selectAdministrationDivide() {
		return commonMapper.selectAdministrationDivide();
	}
	@Override
	public Map<String,Object> selectAdministrationDivideByName(Map<String,Object> map){
		return commonMapper.selectAdministrationDivideByName(map);
	}

}
