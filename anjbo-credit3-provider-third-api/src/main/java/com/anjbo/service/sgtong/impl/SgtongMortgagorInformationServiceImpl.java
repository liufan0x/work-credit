/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.sgtong.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.sgtong.SgtongMortgagorInformationDto;
import com.anjbo.dao.sgtong.SgtongMortgagorInformationMapper;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.service.sgtong.SgtongMortgagorInformationService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:32
 * @version 1.0
 */
@Service
public class SgtongMortgagorInformationServiceImpl extends BaseServiceImpl<SgtongMortgagorInformationDto>  implements SgtongMortgagorInformationService {
	@Autowired private SgtongMortgagorInformationMapper sgtongMortgagorInformationMapper;

}
