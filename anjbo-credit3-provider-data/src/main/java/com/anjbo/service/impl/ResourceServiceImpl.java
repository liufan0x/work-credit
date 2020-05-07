/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.ResourceDto;
import com.anjbo.dao.ResourceMapper;
import com.anjbo.service.ResourceService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:28
 * @version 1.0
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourceDto>  implements ResourceService {
	@Autowired private ResourceMapper resourceMapper;

}
