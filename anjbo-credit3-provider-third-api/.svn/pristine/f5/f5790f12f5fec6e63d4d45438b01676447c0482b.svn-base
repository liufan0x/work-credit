/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.ordinary.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.anjbo.bean.FundDto;
import com.anjbo.common.Constants;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.ordinary.AuditFundDockingOrdinaryBusinfoMapper;
import com.anjbo.service.ordinary.AuditFundDockingOrdinaryBusinfoServer;
import com.anjbo.utils.BusinfoUtils;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-03 19:44:34
 * @version 1.0
 */
@Service
public class AuditFundDockingOrdinaryBusinfoServerImpl implements AuditFundDockingOrdinaryBusinfoServer {

	@Resource
	private UserApi userApi;
	
	@Resource
	private OrderApi orderApi;

	@Resource
	private AuditFundDockingOrdinaryBusinfoMapper auditFundDockingOrdinaryBusinfoMapper;

	@Override
	public Map<String, Object> getOrdinaryBusinfoType(Map<String, Object> map) {
		String productCode = MapUtils.getString(map, "productCode");
		int fundId = MapUtils.getIntValue(map, "ordinaryFund", 0);
		FundDto fundDto = userApi.selectCustomerFundById(fundId);
		String typeId = fundDto.getAuths();
		map.put("typeId", typeId);
		map.put("fundId", fundId);
		map.put("productCode", productCode);

		List<Map<String, Object>> parentList = orderApi.getParentBusInfoTypes(map);
		List<Map<String, Object>> sonList = orderApi.getSonType(map);
		List<Map<String, Object>> listMap = selectListAll(map);
		parentList = BusinfoUtils.packagingBusinfo(parentList, sonList, listMap, typeId);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("isAuditShow", false);
		if (!StringUtil.isBlank(typeId)) { // 如果影像资料id不为空时
			if ((typeId.indexOf("1500") != -1 && "01".equals(productCode))                //1500置换 1501畅贷
					|| (typeId.indexOf("1501") != -1 && "03".equals(productCode))
					|| (typeId.indexOf("1502") != -1 && "02".equals(productCode))
					|| (typeId.indexOf("1503") != -1 && "05".equals(productCode))) {
				data.put("isAuditShow", true);
			}
		}
		data.put("topType", parentList);
		return data;
	}

	@Override
	public List<Map<String, Object>> selectListAll(Map<String, Object> map) {
		List<Map<String, Object>> listMap = auditFundDockingOrdinaryBusinfoMapper.selectListAll(map);
		if(!(listMap != null && listMap.size() > 0)) {
			listMap = orderApi.selectAllBusInfo(MapUtils.getString(map, "orderNo"));
			for (int i = 0; i < listMap.size(); i++) {
				Map<String, Object> maps = listMap.get(i);
				maps.put("fundId",MapUtils.getString(map,"fundId"));
			}
			auditFundDockingOrdinaryBusinfoMapper.insretImg(listMap);
		}
		return listMap;
	}
	
	@Override
	public int deleteImg(Map<String, Object> map) {
		return auditFundDockingOrdinaryBusinfoMapper.deleteImg(map);
	}

	@Override
	public void insretImg(Map<String, Object> map) {
		String url = MapUtils.getString(map, "url", "");
		url = url.substring(0, url.length() - 1);
		if(StringUtils.isNotBlank(url)){
			String[] arr = url.split(",");
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> tmp = null;
			for(int i=0;i<arr.length;i++){
				tmp = new HashMap<String,Object>();
				tmp.putAll(map);
				tmp.put("url", arr[i]);
				list.add(tmp);
			}
			if(list.size()>0){
				auditFundDockingOrdinaryBusinfoMapper.insretImg(list);
			}
		}
	}


}
