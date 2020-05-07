package com.anjbo.service.impl;

import com.anjbo.bean.risk.LawsuitDto;
import com.anjbo.dao.LawsuitMapper;
import com.anjbo.dao.RiskBaseMapper;
import com.anjbo.service.LawsuitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 诉讼
 * @author huanglj
 *
 */
@Transactional
@Service
public class LawsuitServiceImpl implements LawsuitService{
	
	@Resource
	private LawsuitMapper lawsuitMapper;
	
	@Resource
	private RiskBaseMapper riskBaseMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return lawsuitMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LawsuitDto record) {
		return lawsuitMapper.insert(record);
	}

	@Override
	public int insertSelective(LawsuitDto record) {
		return lawsuitMapper.insertSelective(record);
	}

	@Override
	public LawsuitDto selectByPrimaryKey(Integer id) {
		LawsuitDto obj =  lawsuitMapper.selectByPrimaryKey(id);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderNo", obj.getOrderNo());
		param.put("imgType", "archive");
		List<Map<String,Object>> list = riskBaseMapper.listImg(param);
		obj.setLawsuitImgs(list);
		return obj;
	}

	@Override
	public int updateByPrimaryKeySelective(LawsuitDto record) {
		return lawsuitMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LawsuitDto record) {
		return lawsuitMapper.updateByPrimaryKey(record);
	}

	@Override
	public LawsuitDto selectByOrderNo(String orderNo) {
		LawsuitDto obj =  lawsuitMapper.selectByOrderNo(orderNo);
		if(null==obj){
			obj = new LawsuitDto();
			obj.setOrderNo(orderNo);
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderNo", orderNo);
		param.put("imgType", "lawsuit");
		List<Map<String,Object>> list = riskBaseMapper.listImg(param);
		obj.setLawsuitImgs(list);
		return obj;
	}
	@Override
	public LawsuitDto selectByOrderNo(String orderNo,boolean isImg) {
		LawsuitDto obj =  lawsuitMapper.selectByOrderNo(orderNo);
		return obj;
	}
	
	@Override
	public int updateByOrderNo(LawsuitDto obj){
		List<LawsuitDto> list = lawsuitMapper.detailByOrderNo(obj);
		if(null==list||list.size()<=0){
			return lawsuitMapper.insert(obj);
		}
		return lawsuitMapper.updateByOrderNo(obj);
	}
}
