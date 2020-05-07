package com.anjbo.service.impl;

import java.text.MessageFormat;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.risk.RiskModelConfigDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.dao.RiskModelConfigMapper;
import com.anjbo.service.RiskModelConfigService;
@Transactional
@Service
public class RiskModelConfigServiceImpl implements RiskModelConfigService{

	private static final Log log = LogFactory.getLog(RiskModelConfigServiceImpl.class);
	@Resource
	private RiskModelConfigMapper riskModelConfigMapper;
	@Override
	public List<RiskModelConfigDto> selectCreditList(Map<String, Object> params) {
		List<RiskModelConfigDto> list = riskModelConfigMapper.selectCreditList(params);
		return list;
	}
	
	@Override
	public Integer selectCreditCount(Map<String, Object> params) {
		return riskModelConfigMapper.selectCreditCount(params);
	}
	
	@Override
	public RespStatus updateCredit(Map<String, Object> params) {
		RespStatus resp = new RespStatus();
		try {
			riskModelConfigMapper.updateCredit(params);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			log.info("修改风控模型失败",e);
			RespHelper.setFailRespStatus(resp, "修改风控模型失败");
		}
		return resp;
	}
	
}
