/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.TblOrderBaseHouseLendingDto;
import com.anjbo.dao.TblOrderBaseHouseLendingMapper;
import com.anjbo.service.TblOrderBaseHouseLendingService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-29 09:37:27
 * @version 1.0
 */
@Service
public class TblOrderBaseHouseLendingServiceImpl extends BaseServiceImpl<TblOrderBaseHouseLendingDto>  implements TblOrderBaseHouseLendingService {
	@Autowired private TblOrderBaseHouseLendingMapper tblOrderBaseHouseLendingMapper;

}
