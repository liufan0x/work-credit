/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.RiskLogDto;
import com.anjbo.dao.risk.RiskLogMapper;
import com.anjbo.service.risk.RiskLogService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:34
 * @version 1.0
 */
@Service
public class RiskLogServiceImpl extends BaseServiceImpl<RiskLogDto>  implements RiskLogService {
	@Autowired private RiskLogMapper logMapper;

}
