/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.BusinfoDto;
import com.anjbo.dao.order.BusinfoMapper;
import com.anjbo.dao.order.BusinfoTypeMapper;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.service.order.BusinfoService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:47
 * @version 1.0
 */
@Service
public class BusinfoServiceImpl extends BaseServiceImpl<BusinfoDto>  implements BusinfoService {
	
	@Resource private BusinfoMapper businfoMapper;
	@Resource private BusinfoTypeMapper businfoTypeMapper;

	@Override
	public boolean faceBusinfoCheck(String orderNo, String productCode, int auditSort) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("productCode", productCode);
		map.put("auditSort", auditSort);
		int a = businfoMapper.hasFaceBusInfoCount(map);
		int b = businfoTypeMapper.mustFaceBusInfoCount(map);
		System.out.println("需要上传的面签资料数："+b);
		System.out.println("上传的面签资料数："+a);
		return a==b;
	}

	@Override
	public boolean notarizationBusinfoCheck(String orderNo, String productCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("typeId", "70008");
		map.put("orderNo", orderNo);
		List<Map<String, Object>> list = businfoMapper.selectListMap(map);
		return list!=null&&list.size()>0?true:false;
	}
	
}
