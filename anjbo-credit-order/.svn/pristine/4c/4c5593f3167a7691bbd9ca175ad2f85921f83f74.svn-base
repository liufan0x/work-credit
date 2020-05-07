package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBusinfoDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.dao.OrderBaseBorrowMapper;
import com.anjbo.dao.OrderBaseBorrowRelationMapper;
import com.anjbo.dao.OrderBaseMapper;
import com.anjbo.dao.OrderBusinfoMapper;
import com.anjbo.dao.OrderBusinfoTypeMapper;
import com.anjbo.service.OrderBaseBorrowService;
import com.anjbo.service.OrderBusinfoService;

@Service
public class OrderBusinfoServiceImpl implements OrderBusinfoService {

	@Resource
	private OrderBusinfoMapper orderBusinfoMapper;

	@Resource
	private OrderBusinfoTypeMapper orderBusinfoTypeMapper;

	@Resource
	private OrderBaseBorrowMapper orderBaseBorrowMapper;

	@Resource
	private OrderBaseBorrowRelationMapper orderBaseBorrowRelationMapper;
	
	@Resource
	private OrderBaseBorrowService orderBaseBorrowService;
	
	@Resource
	private OrderBaseMapper orderBaseMapper;
	
	@Override
	public int saveOrderBusinfo(OrderBusinfoDto orderBusinfoDto) {
		String orderNo =orderBusinfoDto.getOrderNo();
		OrderListDto orderListDto = orderBaseMapper.selectDetail(orderNo);
		if(!"placeOrder".equals(orderListDto.getProcessId())){
			orderBusinfoDto.setIsOrder(2);
		}
		return orderBusinfoMapper.saveOrderBusinfo(orderBusinfoDto);
	}

	@Override
	public int updateOrderBusinfo(OrderBusinfoDto orderBusinfoDto) {
		return orderBusinfoMapper.updateOrderBusinfo(orderBusinfoDto);
	}

	@Override
	public List<OrderBusinfoDto> selectOrderBusinfoByOrderNo(String orderNo) {
		return orderBusinfoMapper.selectOrderBusinfoByOrderNo(orderNo);
	}

	@Override
	public int deleteOrderBusinfo(Map<String, Object> map) {
		return orderBusinfoMapper.deleteOrderBusinfo(map);
	}

	@Override
	public int deleteImgByIds(Map<String, Object> map) {
		return orderBusinfoMapper.deleteImgByIds(map);
	}

	/**
	 * @author Liuf
	 * @rewrite KangLG<2018年1月30日> 畅贷关联影响资料
	 */
	@Override
	public Map<String, Object> getBusinfoTypeTree(Map<String, Object> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		String orderNo = MapUtils.getString(map, "orderNo");
		OrderListDto orderListDto = orderBaseMapper.selectDetail(orderNo);
		map.put("orderNo", orderNo);
		map.put("productCode", orderListDto.getProductCode());
		//畅贷且关联订单
		if ("03".equals(orderListDto.getProductCode()) && StringUtils.isNotBlank(orderListDto.getRelationOrderNo())) { 
			map.put("isChangLoan", 1);
		}	
		
		List<Map<String, Object>> parentTypes = orderBusinfoTypeMapper.getParentBusInfoTypes(map);
		List<Map<String, Object>> listMap = orderBusinfoMapper.selectListMap(map);
		List<Map<String, Object>> sonTypesTemp = orderBusinfoTypeMapper.getSonBusInfoTypes(map);
		List<Map<String, Object>> sonTypes = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> parentType : parentTypes) {
			int ppid = new Integer(parentType.get("id").toString());
			sonTypes = new ArrayList<Map<String,Object>>();
			for(int k=0;k<sonTypesTemp.size();k++){
				int pid = new Integer(sonTypesTemp.get(k).get("pid").toString());
				if(pid==ppid){
					sonTypes.add(sonTypesTemp.get(k));
				}
			}
			for (int i = 0; i < sonTypes.size(); i++) {
				int ids = Integer
						.parseInt(sonTypes.get(i).get("id").toString());
				List<Map<String, Object>> imgMap = new ArrayList<Map<String, Object>>();
				for (int j = 0; j < listMap.size(); j++) {
					String url = listMap.get(j).get("url") + "";
					url = url.replace("_18", "_48");
					listMap.get(j).put("url", url);
					int id = MapUtils.getIntValue(listMap.get(j), "typeId");
					if (id == ids) {
						imgMap.add(listMap.get(j));
					}
				}
				sonTypes.get(i).put("listMap", imgMap);
				/*if("facesign".equals(orderListDto.getProcessId())){
					if(("10000".equals(sonTypes.get(i).get("pid").toString())||"20000".equals(sonTypes.get(i).get("pid").toString()))
							&&!"投保单".equals(sonTypes.get(i).get("name"))){//面签资料
						sonTypes.get(i).put("isMust", 1);
					}
				}*/
				//判断非面签节点时设置面签资料为非必传
				if(!"facesign".equals(orderListDto.getProcessId())){
					if(("10000".equals(sonTypes.get(i).get("pid").toString())
						||"20000".equals(sonTypes.get(i).get("pid").toString())
						||"60000".equals(sonTypes.get(i).get("pid").toString())
						||"70000".equals(sonTypes.get(i).get("pid").toString())
						||"80000".equals(sonTypes.get(i).get("pid").toString()))){//面签资料
						//提单时校验所有资料
						if(orderListDto.getAuditSort()!=2){
							sonTypes.get(i).put("isMust", 2);
						}
					}
					if("04".equals(orderListDto.getProductCode())&&"notarization".equals(orderListDto.getProcessId())){
						if("公证委托书".equals(sonTypes.get(i).get("name").toString())){
							sonTypes.get(i).put("isMust", 1);
						}
					}
					//先面签后审批- 【公证】节点此处的影像资料改为非必传
					if("notarization".equals(orderListDto.getProcessId())&&orderListDto.getAuditSort()==2){
						sonTypes.get(i).put("isMust", 2);
					}
				}else{
					if("04".equals(orderListDto.getProductCode())){//房抵贷先面签再公证
						if("公证委托书".equals(sonTypes.get(i).get("name").toString())){
							sonTypes.get(i).put("isMust", 2);
						}
					}
					//先面签后审批- 【面签】节点此处的影像资料改为非必传
					if(orderListDto.getAuditSort()==2){
						sonTypes.get(i).put("isMust", 2);
					}
				}
			}
			parentType.put("sonTypes", sonTypes);
		}
		data.put("creditLoan", parentTypes);
		// 有畅贷关联单
		if ("03".equals(orderListDto.getProductCode()) && StringUtils.isNotBlank(orderListDto.getRelationOrderNo())) {
			map.put("isChangLoan", 0);
			map.put("orderNo", orderListDto.getRelationOrderNo());
			OrderListDto orderRelation = orderBaseMapper.selectDetail(map.get("orderNo").toString());
			map.put("productCode", orderRelation.getProductCode());
						
			parentTypes = orderBusinfoTypeMapper.getParentBusInfoTypes(map);
			listMap = orderBusinfoMapper.selectListMap(map);
			sonTypesTemp = orderBusinfoTypeMapper.getSonBusInfoTypes(map);
			for (Map<String, Object> parentType : parentTypes) {
				int ppid = new Integer(parentType.get("id").toString());
				sonTypes = new ArrayList<Map<String,Object>>();
				for(int k=0;k<sonTypesTemp.size();k++){
					int pid = new Integer(sonTypesTemp.get(k).get("pid").toString());
					if(pid==ppid){
						sonTypes.add(sonTypesTemp.get(k));
					}
				}
				for (int i = 0; i < sonTypes.size(); i++) {
					int ids = Integer.parseInt(sonTypes.get(i).get("id")
							.toString());
					List<Map<String, Object>> imgMap = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < listMap.size(); j++) {
						String url = listMap.get(j).get("url") + "";
						url = url.replace("_18", "_48");
						listMap.get(j).put("url", url);
						int id = MapUtils.getIntValue(listMap.get(j), "typeId");
						if (id == ids) {
							imgMap.add(listMap.get(j));
						}
					}
					sonTypes.get(i).put("listMap", imgMap);
				}
				parentType.put("sonTypes", sonTypes);
				parentType.put("relationOrderNo", orderListDto.getRelationOrderNo());
			}

			data.put("changRelationLoan", parentTypes);
		}
		return data;
	}
	
	@Override
	public List<Map<String, Object>> searchByProductCode(String productCode){
		List<Map<String,Object>> lstTypes = orderBusinfoTypeMapper.searchByProductCode(productCode);
		
		List<Map<String, Object>> lstReturns = new LinkedList<Map<String, Object>>(); 
		List<Map<String, Object>> childs;
		for(Map<String,Object> parent : lstTypes){
			if(MapUtils.getIntValue(parent, "pid") <= 0){
				childs = new LinkedList<Map<String,Object>>();
				for (Map<String, Object> child : lstTypes) {
					if(MapUtils.getIntValue(child, "pid") == MapUtils.getIntValue(parent, "id")){
						childs.add(child);
					}
				}
				parent.put("childs", childs);
				lstReturns.add(parent);
			}
		}		
		return lstReturns;
	}

	/**
	 * 查询债务置换贷款影像资料，或者畅贷业务资料
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> getAppBusInfoByOrderNo(Map<String, Object> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		String orderNo = MapUtils.getString(map, "orderNo");
		OrderListDto orderListDto = orderBaseMapper.selectDetail(orderNo);
		map.put("orderNo", orderNo);
		map.put("productCode", orderListDto.getProductCode());
		if(!"03".equals(orderListDto.getProductCode())){//畅贷查询关联订单影像资料，不加畅贷面签条件
			map.remove("isChangLoan");
		}
		List<Map<String, Object>> parentTypes = orderBusinfoTypeMapper
				.getParentBusInfoTypes(map);
		List<Map<String, Object>> listMap = orderBusinfoMapper
				.selectListMap(map);
		List<Map<String, Object>> sonTypesTemp = orderBusinfoTypeMapper
				.getSonBusInfoTypes(map);
		List<Map<String, Object>> sonTypes = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> parentType : parentTypes) {
			int ppid = new Integer(parentType.get("id").toString());
			sonTypes = new ArrayList<Map<String,Object>>();
			for(int k=0;k<sonTypesTemp.size();k++){
				int pid = new Integer(sonTypesTemp.get(k).get("pid").toString());
				if(pid==ppid){
					sonTypes.add(sonTypesTemp.get(k));
				}
			}
			for (int i = 0; i < sonTypes.size(); i++) {
				int ids = Integer
						.parseInt(sonTypes.get(i).get("id").toString());
				List<Map<String, Object>> imgMap = new ArrayList<Map<String, Object>>();
				for (int j = 0; j < listMap.size(); j++) {
					String url = listMap.get(j).get("url") + "";
					url = url.replace("_18", "_48");
					listMap.get(j).put("url", url);
					int id = MapUtils.getIntValue(listMap.get(j), "typeId");
					if (id == ids) {
						imgMap.add(listMap.get(j));
					}
				}
				sonTypes.get(i).put("listMap", imgMap);
				
				//判断非面签节点时设置面签资料为非必传
				if(!"facesign".equals(orderListDto.getProcessId())){
					if(("10000".equals(sonTypes.get(i).get("pid").toString())
						||"20000".equals(sonTypes.get(i).get("pid").toString())
						||"60000".equals(sonTypes.get(i).get("pid").toString())
						||"70000".equals(sonTypes.get(i).get("pid").toString()))){//面签资料
						//提单时校验所有资料
						if(orderListDto.getAuditSort()!=2){
							sonTypes.get(i).put("isMust", 2);
						}
					}
					if("04".equals(orderListDto.getProductCode())&&"notarization".equals(orderListDto.getProcessId())){
						if("公证委托书".equals(sonTypes.get(i).get("name").toString())){
							sonTypes.get(i).put("isMust", 1);
						}
					}
					//先面签后审批- 【公证】节点此处的影像资料改为非必传
					if("notarization".equals(orderListDto.getProcessId())&&orderListDto.getAuditSort()==2){
						sonTypes.get(i).put("isMust", 2);
					}
				}else{
					if("04".equals(orderListDto.getProductCode())){//房抵贷先面签再公证
						if("公证委托书".equals(sonTypes.get(i).get("name").toString())){
							sonTypes.get(i).put("isMust", 2);
						}
					}
					//先面签后审批- 【面签】节点此处的影像资料改为非必传
					if(orderListDto.getAuditSort()==2){
						sonTypes.get(i).put("isMust", 2);
					}
				}
			}
			parentType.put("sonTypes", sonTypes);
		}
		data.put("parentTypes", parentTypes);

		return data;
	}

	@Override
	public void addBusinfo(Map<String, Object> map) {
		String uid = map.get("uid").toString();
		String orderNo = map.get("orderNo").toString();
		Integer typeId = new Integer(map.get("typeId").toString());
		String urls = map.get("urls").toString();
		String pid = map.get("progressId") + "";
		int isOrder = 1;
		// 不是提单
		if (!"placeOrder".equals(pid)) {
			isOrder = 2;
		}
		String[] urlss = urls.split(",");
		int index = 0;
		try {
			index = orderBusinfoMapper.selectLastIndex(map);
		} catch (Exception e) {
			index = 0;
		}
		for (String tempUrl : urlss) {
			OrderBusinfoDto busInfoDto = new OrderBusinfoDto();
			busInfoDto.setCreateUid(uid);
			busInfoDto.setOrderNo(orderNo);
			busInfoDto.setTypeId(typeId);
			busInfoDto.setUrl(tempUrl);
			busInfoDto.setCreateTime(new Date());
			busInfoDto.setIsOrder(isOrder);
			index += 1;
			busInfoDto.setIndex(index);
			orderBusinfoMapper.saveOrderBusinfo(busInfoDto);
		}
	}

	@Override
	public void moveBusinfo(Map<String, Object> map) {
		Integer[] businfoIds = (Integer[]) map.get("businfoIds");
		Integer toTypeId = new Integer(map.get("toTypeId").toString());
		int index = 0;
		try {
			map.put("typeId", toTypeId);
			index = orderBusinfoMapper.selectLastIndex(map);
		} catch (Exception e) {
			index = 0;
		}
		for (Integer businfoId : businfoIds) {
			OrderBusinfoDto busInfoDto = new OrderBusinfoDto();
			busInfoDto.setId(businfoId);
			busInfoDto.setTypeId(toTypeId);
			index += 1;
			busInfoDto.setIndex(index);
			orderBusinfoMapper.updateOrderBusinfo(busInfoDto);
		}
	}

	@Override
	public void delBusinfo(Map<String, Object> map) {
		// Integer typeId = (Integer)map.get("typeId");
		String pid = map.get("progressId") + "";
		// 是提单
		if ("placeOrder".equals(pid)) {
			// 删除所有
			map.put("progressId", 0);
		}
		orderBusinfoMapper.deleteOrderBusinfo(map);
	}

	@Override
	public Map<String, Object> lookOver(Map<String, Object> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = orderBusinfoMapper
				.selectListMap(map);
		for (Map<String, Object> map2 : listMap) {
			String url = map2.get("url") + "";
			url = url.replace("_18", "_48");
			map2.put("url", url);
		}
		data.put("listMap", listMap);
		return data;
	}

	@Override
	public Map<String, Object> getAllType(Map<String, Object> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = orderBusinfoTypeMapper
				.getAllType(map);
		data.put("listMap", listMap);
		return data;
	}

	@Override
	public Map<String, Object> getInfoTypeByName(String name) {
		return orderBusinfoMapper.getInfoTypeByName(name);
	}

	@Override
	public Map<String, Object> getBusinfoAndType(Map<String, Object> map) {
		Map<String, Object> maps = new HashMap<String, Object>();
		// 默认查询债务置换贷款交易类资料类型
		String orderNo = MapUtils.getString(map, "orderNo");
//		if(isChangLoan(orderNo)){
//			OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationMapper
//					.selectRelationByOrderNo(orderNo);
//			String proudctCode = getProductCode(orderBaseBorrowRelationDto.getRelationOrderNo());
//			map.put("productCode", proudctCode+"03");
//		}else{
			map.put("productCode", getProductCode(orderNo));
//		}
		List<Map<String, Object>> parentTypes = orderBusinfoTypeMapper
				.getParentBusInfoTypes(map);
		List<Map<String, Object>> listMap = orderBusinfoMapper
				.selectListMap(map);
		List<Map<String, Object>> sonTypes = orderBusinfoTypeMapper
				.getSonType(map);
		maps.put("parentTypes", parentTypes);
		maps.put("listMap", listMap);
		maps.put("sonTypes", sonTypes);
		return maps;
	}
	/**
	 * 是否是畅贷订单号
	 * @param orderNo
	 * @return
	 */
//	public boolean isChangLoan(String orderNo) {
//		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationMapper
//				.selectRelationByOrderNo(orderNo);
//		if (orderBaseBorrowRelationDto != null) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	/**
	 * 是否有畅贷
	 * @param orderNo
	 * @return
	 */
	public boolean hasChangLoan(String orderNo){
		OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
		if(orderBaseBorrowDto!=null && orderBaseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&orderBaseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
			return true;
		}
		return false;
	}
	
	public String getProductCode(String orderNo) {
		OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowMapper.selectOrderBorrowByOrderNo(orderNo);
		return orderBaseBorrowDto.getProductCode();
	}

	@Override
	public int hasBusInfoCount(Map<String,Object> map) {
		return orderBusinfoMapper.hasBusInfoCount(map);
	}

	@Override
	public boolean faceBusinfoCheck(String orderNo, String productCode,int auditSort) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("productCode", productCode);
		map.put("auditSort", auditSort);
		int a = orderBusinfoMapper.hasFaceBusInfoCount(map);
		int b = orderBusinfoTypeMapper.mustFaceBusInfoCount(map);
		System.out.println("需要上传的面签资料数："+b);
		System.out.println("上传的面签资料数："+a);
		return a==b;
	}

	/**
	 * 批量上传影像资料
	 * @param list
	 * @param orderNo
	 * @param user
	 * @return
	 */
	public int batchBusinfo(List<Map<String,Object>> list, String orderNo, UserDto user){
		Map<String,Object> map = list.get(0);
		String uid = user.getUid();
		String pid = map.get("progressId") + "";
		int isOrder = 1;
		// 不是提单
		if (!"placeOrder".equals(pid)) {
			isOrder = 2;
		}
		int index = 0;
		try {
			index = orderBusinfoMapper.selectLastIndex(map);
		} catch (Exception e) {
			index = 0;
		}
		for (Map<String,Object> m:list){
			index += 1;
			m.put("createUid",uid);
			m.put("isOrder",isOrder);
			m.put("index",index);
		}
		int success = orderBusinfoMapper.batchBusinfo(list);
		return success;
	}

	@Override
	public boolean notarizationBusinfoCheck(String orderNo, String productCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("typeId", "70008");
		map.put("orderNo", orderNo);
		List<Map<String, Object>> list = orderBusinfoMapper.selectListMap(map);
		return list!=null&&list.size()>0?true:false;
	}
}
