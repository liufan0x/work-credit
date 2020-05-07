/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.sgtong.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.sgtong.SgtongBorrowerInformationDto;
import com.anjbo.dao.sgtong.SgtongBorrowerInformationMapper;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.service.sgtong.SgtongBorrowerInformationService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
@Service
public class SgtongBorrowerInformationServiceImpl extends BaseServiceImpl<SgtongBorrowerInformationDto>  implements SgtongBorrowerInformationService {
	@Autowired private SgtongBorrowerInformationMapper sgtongBorrowerInformationMapper;

}
