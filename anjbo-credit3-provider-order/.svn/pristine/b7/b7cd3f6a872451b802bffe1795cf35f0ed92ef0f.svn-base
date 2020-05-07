/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.order.BaseCustomerDto;
import com.anjbo.bean.order.BaseHousePropertypeopleDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.order.IBaseCustomerController;
import com.anjbo.service.order.BaseCustomerService;
import com.anjbo.service.order.BaseHousePropertypeopleService;
import com.anjbo.service.order.BaseListService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:45
 * @version 1.0
 */
@RestController
public class BaseCustomerController extends BaseController implements IBaseCustomerController{

	@Resource private BaseCustomerService baseCustomerService;
	
	@Resource private BaseHousePropertypeopleService baseHousePropertypeopleService;
	
	@Resource private BaseListService baseListService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BaseCustomerDto> page(@RequestBody BaseCustomerDto dto){
		RespPageData<BaseCustomerDto> resp = new RespPageData<BaseCustomerDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(baseCustomerService.search(dto));
			resp.setTotal(baseCustomerService.count(dto));
		}catch (Exception e) {
			logger.error("分页异常,参数："+dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 查询
	 * @author Generator 
	 */
	@Override
	public RespData<BaseCustomerDto> search(@RequestBody BaseCustomerDto dto){ 
		RespData<BaseCustomerDto> resp = new RespData<BaseCustomerDto>();
		try {
			return RespHelper.setSuccessData(resp, baseCustomerService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BaseCustomerDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseCustomerDto> find(@RequestBody BaseCustomerDto dto){ 
		RespDataObject<BaseCustomerDto> resp = new RespDataObject<BaseCustomerDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, baseCustomerService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseCustomerDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseCustomerDto> add(@RequestBody BaseCustomerDto dto){ 
		RespDataObject<BaseCustomerDto> resp = new RespDataObject<BaseCustomerDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, baseCustomerService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseCustomerDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BaseCustomerDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			baseCustomerService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("编辑异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 删除
	 * @author Generator 
	 */
	@Override
	public RespStatus delete(@RequestBody BaseCustomerDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			baseCustomerService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}


	@Override
	public List<Map<String ,Object>> allCustomerNos(@RequestBody Map<Object, String> map) {
		String orderNo = MapUtils.getString(map,"orderNo");
		if(orderNo==null||orderNo.equals("")) {
			return null;
		}
		BaseCustomerDto  baseCustomerDto = new BaseCustomerDto();
		baseCustomerDto.setOrderNo(orderNo);
		List<BaseCustomerDto> baseCustomerDtos = baseCustomerService.search(baseCustomerDto);
		if(baseCustomerDtos==null||baseCustomerDtos.size()==0) {
			return null;
		}
		
		BaseHousePropertypeopleDto baseHousePropertypeopleDto = new BaseHousePropertypeopleDto();
		baseHousePropertypeopleDto.setOrderNo(orderNo);
		List<BaseHousePropertypeopleDto> baseHousePropertypeopleDtos = baseHousePropertypeopleService.search(baseHousePropertypeopleDto);
		
		BaseListDto baseListDto = new BaseListDto();
		baseListDto.setOrderNo(orderNo);
		BaseListDto baseListDtos = baseListService.find(baseListDto);  // 客户类型实体类定义的是布尔类型
		if (0==baseListDtos.getCustomerType()) {
			baseListDtos.setCustomerType(1);
		}
		
		
		List<Map<String ,Object>> listMap = new ArrayList<Map<String ,Object>>();
		Map<String ,Object> maps=new HashMap<String, Object>();
		
		
		for (BaseCustomerDto baseCustomerDto2 : baseCustomerDtos) {
			if(StringUtils.isNotEmpty(baseCustomerDto2.getCustomerName())) {
			maps.put("customerName",baseCustomerDto2.getCustomerName());
			maps.put("customerCardNumber",baseCustomerDto2.getCustomerCardNumber());
			maps.put("customerType",baseListDtos.getCustomerType());
			listMap.add(maps);
			}
			if(StringUtils.isNotEmpty(baseCustomerDto2.getCustomerWifeName())) {
				maps=new HashMap<String, Object>();
				maps.put("customerName",baseCustomerDto2.getCustomerWifeName());
				maps.put("customerCardNumber",baseCustomerDto2.getCustomerWifeCardNumber());
				listMap.add(maps);
			}
		}
		
		for (BaseHousePropertypeopleDto baseHousePropertypeopleDtos2 : baseHousePropertypeopleDtos) {
			maps=new HashMap<String, Object>();
			if (StringUtils.isNotEmpty(baseHousePropertypeopleDtos2.getPropertyName())) {
				maps.put("customerName",baseHousePropertypeopleDtos2.getPropertyName());
				maps.put("customerCardNumber",baseHousePropertypeopleDtos2.getPropertyCardNumber());
				listMap.add(maps);
			}
		}    
		  
			 Map<String ,Object> map1= listMap.get(0);           //借款人
			 if (listMap.size()==2) {
				 Map<String ,Object> map2= listMap.get(1);   
				 String customerName = MapUtils.getString(map1, "customerName");
			     String all = MapUtils.getString(map2, "customerName");
			      if(customerName!=null||!customerName.equals("")||all!=null||!all.equals("")) {
					if (customerName.equals(all)) {
						listMap.remove(listMap.get(1));
					}
				}
			     
			}
			 else if(listMap.size()>2){
				     Map<String ,Object> map2= listMap.get(1);    //配偶
				     String customerName = MapUtils.getString(map1,"customerName");
				     String allName = MapUtils.getString(map2,"customerName");
				     String str = null;
				     if(customerName!=null||!customerName.equals("")||allName!=null||!allName.equals("")) {
				     for (int i = 2; i < listMap.size(); i++) {
				     str = MapUtils.getString(listMap.get(i),"customerName"); 
						if (customerName.equals(str)) {
							listMap.remove(listMap.get(i));
							i--;
						}
						 if (listMap.size()==2) {
							 if (customerName.equals(allName)) {
									listMap.remove(listMap.get(i));
								}
						 }
					}
				   
				     for (int i = 2; i < listMap.size(); i++) {
				    	 str = MapUtils.getString(listMap.get(i),"customerName"); 
				    	 if (allName.equals(str)) {
								listMap.remove(listMap.get(i));
								i--;
							}
				    	 if (listMap.size()==2) {
							 if (customerName.equals(allName)) {
									listMap.remove(listMap.get(i));
								}
						 }
				     }
				     if (customerName.equals(allName)) {
							listMap.remove(listMap.get(1));
						}
				     }
			 }
			 return listMap;
	}
}
		