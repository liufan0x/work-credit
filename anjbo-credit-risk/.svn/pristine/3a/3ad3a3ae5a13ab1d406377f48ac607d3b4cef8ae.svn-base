package com.anjbo.service.impl;

import com.alibaba.druid.support.logging.Log;
import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.risk.BusinfoDto;
import com.anjbo.bean.risk.HuaanDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.AllocationFundController;
import com.anjbo.dao.FundDockingMapper;
import com.anjbo.dao.HuaanMapper;
import com.anjbo.service.HuaanService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
@Transactional
@Service
public class HuaanServiceImpl implements HuaanService{

	@Resource
	private HuaanMapper huaanMapper;
	@Resource
	private FundDockingMapper fundDockingMapper;
	
	public HuaanDto detail(String orderNo){
		HuaanDto obj = huaanMapper.detail(orderNo);
		if(null!=obj){
			Map<String,Object> bankMap = AllocationFundController.getBank(obj.getHouseLoanBankId(),obj.getHouseLoanSubBankId());
			obj.setHouseLoanBankName(MapUtils.getString(bankMap, "bankId", ""));
			obj.setHouseLoanSubBankName(MapUtils.getString(bankMap, "subBankId", ""));
		}
		return obj;
	}
	
	public int delete(String orderNo){
		return huaanMapper.delete(orderNo);
	}
	
	public int insert(HuaanDto obj){
		return huaanMapper.insert(obj);
	}
	
	public int update(HuaanDto obj){
		HuaanDto tmp = huaanMapper.detail(obj.getOrderNo());
		if(null==tmp){
			return huaanMapper.insert(obj);
		} else {
			return huaanMapper.update(obj);
		}
	}
	
	/**
	 * 获取普通资方影响分类
	 * @param map
	 * @return
	 */
	@Override
	public RespDataObject<Map<String,Object>> getOrdinaryBusinfoType(Map<String,Object> map) {
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		String orderNo = MapUtils.getString(map, "orderNo");
		String fundId = MapUtils.getString(map, "ordinaryFund");
		String productCode=MapUtils.getString(map, "productCode");
		String isAddBusInfo =MapUtils.getString(map, "isAddBusInfo");
		CustomerFundDto customerFundDto = new CustomerFundDto();
		customerFundDto.setId(Integer.valueOf(fundId));
		HttpUtil http = new HttpUtil();
		customerFundDto = http.getObject(Constants.LINK_CREDIT, "/credit/customer/fund/new/v/selectCustomerFundById",customerFundDto, CustomerFundDto.class);
		String typeId = customerFundDto.getAuths();  //资方影响分类id
		System.out.println("订单："+orderNo+"-推送配置id=typeId:"+typeId);
		map.put("typeId",typeId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("isAuditShow", false);
		if(!StringUtil.isBlank(typeId)){
			int jy=typeId.indexOf("1500"); 
			int cd=typeId.indexOf("1501"); 
			int fjy=typeId.indexOf("1502"); 
			int tf = typeId.indexOf("1503");
			if((jy!=-1&& "01".equals(productCode)) || (cd!=-1 && "03".equals(productCode)) || (fjy!=-1 && "02".equals(productCode))
					|| (tf!=-1 && "05".equals(productCode))){
				data.put("isAuditShow", true);
			}
		}
		data.put("orderNo", orderNo);
		OrderBaseBorrowDto obj = fundDockingMapper.findByOrdinary(orderNo);
		
		JSONObject jsons = http.getData(Constants.LINK_CREDIT,"/credit/order/businfo/v/getBusinfoAndType",map);
		Map<String,Object> allData = new Gson().fromJson(jsons.toString(), Map.class);
		
		if(RespStatusEnum.SUCCESS.getCode().equals(MapUtils.getString(allData, "code", ""))){
			Object allObj = MapUtils.getObject(allData, "data",null);
			Map<String,Object> allMap = null;
			if(null!=allObj&&allObj instanceof Map){
				allMap = (Map<String,Object>)allObj;
			}
			if(null==allMap||MapUtils.isEmpty(allMap)){
				result.setMsg(RespStatusEnum.FAIL.getMsg());
				result.setCode(RespStatusEnum.FAIL.getCode());
				return result;
			}
			Object parentObj = MapUtils.getObject(allMap,"parentTypes",null);
			Object sonObj = MapUtils.getObject(allMap, "sonTypes", null);
			Object imgObj = MapUtils.getObject(allMap, "listMap",null);
			
			List<Map<String,Object>> parentList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> imgList = new ArrayList<Map<String,Object>>();
			
			if(null!=parentObj&&parentObj instanceof List){
				parentList = (List<Map<String,Object>>)parentObj;
			}
			
			if(null!=sonObj&&sonObj instanceof List){
				 sonList = (List<Map<String,Object>>)sonObj;
			}
			
			if(null!=imgObj&&imgObj instanceof List){
				imgList = (List<Map<String,Object>>)imgObj;
//				if((null==listMap||listMap.size()<=0)&&imgList.size()>0&&(null==obj||null==obj.getUpdateTime())){
//						huaanMapper.insretImg(imgList);
//				}
			    if(imgList!=null && imgList.size()>0){
					huaanMapper.delete(orderNo);
					huaanMapper.insretImg(imgList);
			    }
			}
			List<BusinfoDto> listMap = huaanMapper.selectListMap(map);
			if(null!=listMap&&listMap.size()>0){
				parentList = encapsulationByBean(parentList,sonList,listMap,typeId);
			} else {
				parentList = encapsulationByMap(parentList,sonList,imgList,typeId,false);
			}
			data.put("topType", parentList);
		}
		result.setData(data);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public RespDataObject<Map<String,Object>> getBusinfoTypeTree(Map<String,Object> map) {
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		String orderNo = MapUtils.getString(map, "orderNo");
		
		OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
		borrow.setOrderNo(orderNo);
		HttpUtil http = new HttpUtil();
		borrow = http.getObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow",borrow, OrderBaseBorrowDto.class);
		String typeId = ConfigUtil.getStringValue(Constants.BASE_TYPE_ID_TRADED,ConfigUtil.CONFIG_BASE);
		if(null!=borrow&&borrow.getProductCode().equals("02")){
			typeId = ConfigUtil.getStringValue(Constants.BASE_TYPE_ID_NON_TRADED,ConfigUtil.CONFIG_BASE);
		} 
		
		map.put("typeId",typeId);
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("orderNo", orderNo);
		List<BusinfoDto> listMap = huaanMapper.selectListMap(map);
		HuaanDto obj = huaanMapper.detail(orderNo);
		
		JSONObject jsons = http.getData(Constants.LINK_CREDIT,"/credit/order/businfo/v/getBusinfoAndType",map);
		Map<String,Object> allData = new Gson().fromJson(jsons.toString(), Map.class);
		
		if(RespStatusEnum.SUCCESS.getCode().equals(MapUtils.getString(allData, "code", ""))){
			Object allObj = MapUtils.getObject(allData, "data",null);
			Map<String,Object> allMap = null;
			if(null!=allObj&&allObj instanceof Map){
				allMap = (Map<String,Object>)allObj;
			}
			if(null==allMap||MapUtils.isEmpty(allMap)){
				result.setMsg(RespStatusEnum.FAIL.getMsg());
				result.setCode(RespStatusEnum.FAIL.getCode());
				return result;
			}
			Object parentObj = MapUtils.getObject(allMap,"parentTypes",null);
			Object sonObj = MapUtils.getObject(allMap, "sonTypes", null);
			Object imgObj = MapUtils.getObject(allMap, "listMap",null);
			
			List<Map<String,Object>> parentList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> imgList = new ArrayList<Map<String,Object>>();
			
			if(null!=parentObj&&parentObj instanceof List){
				parentList = (List<Map<String,Object>>)parentObj;
			}
			
			if(null!=sonObj&&sonObj instanceof List){
				 sonList = (List<Map<String,Object>>)sonObj;
			}
			
			if(null!=imgObj&&imgObj instanceof List){
				imgList = (List<Map<String,Object>>)imgObj;
				if((null==listMap||listMap.size()<=0)&&imgList.size()>0&&(null==obj||null==obj.getUpdateTime())){
					huaanMapper.insretImg(imgList);
				}
			}
			
			if(null!=listMap&&listMap.size()>0){
				parentList = encapsulationByBean(parentList,sonList,listMap,typeId);
			} else {
				parentList = encapsulationByMap(parentList,sonList,imgList,typeId,false);
			}
			data.put("topType", parentList);
		}
		result.setData(data);
		return result;
	}

	public RespDataObject<Map<String,Object>> getBusinfoTypeTree(Map<String,Object> map,String key,List<Map<String,Object>> listTmp,List<Map<String,Object>> imgListTmp) {
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		String orderNo = MapUtils.getString(map, "orderNo");

		HttpUtil http = new HttpUtil();
		String typeId = ConfigUtil.getStringValue(key,ConfigUtil.CONFIG_BASE);
		//测试
		//String typeId = "10181,10182,10183,10184,10002,10006,10203,10185,10201,10202,10211,10009,10205,10209,10204,90000";

		map.put("typeId",typeId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderNo", orderNo);

		JSONObject jsons = http.getData(Constants.LINK_CREDIT,"/credit/order/businfo/v/getBusinfoAndType",map);
		Map<String,Object> allData = new Gson().fromJson(jsons.toString(), Map.class);

		if(RespStatusEnum.SUCCESS.getCode().equals(MapUtils.getString(allData, "code", ""))){
			Object allObj = MapUtils.getObject(allData, "data",null);
			Map<String,Object> allMap = null;
			if(null!=allObj&&allObj instanceof Map){
				allMap = (Map<String,Object>)allObj;
			}
			if(null==allMap||MapUtils.isEmpty(allMap)){
				result.setMsg(RespStatusEnum.FAIL.getMsg());
				result.setCode(RespStatusEnum.FAIL.getCode());
				return result;
			}
			Object parentObj = MapUtils.getObject(allMap,"parentTypes",null);
			Object sonObj = MapUtils.getObject(allMap, "sonTypes", null);
			Object imgObj = MapUtils.getObject(allMap, "listMap",null);

			List<Map<String,Object>> parentList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> imgList = new ArrayList<Map<String,Object>>();

			if(null!=parentObj&&parentObj instanceof List){
				parentList = (List<Map<String,Object>>)parentObj;
			}

			if(null!=sonObj&&sonObj instanceof List){
				sonList = (List<Map<String,Object>>)sonObj;
				if(null!=listTmp&&listTmp.size()>0){
					sonList.addAll(listTmp);
				}
			}
			if(null!=imgObj&&imgObj instanceof List){
				imgList = (List<Map<String,Object>>)imgObj;
			}
			if(null!=imgListTmp&&imgListTmp.size()>0) {
				parentList = encapsulationByMap(parentList, sonList, imgListTmp, typeId,true);
			} else {
				parentList = encapsulationByMap(parentList, sonList, imgList, typeId,false);
			}
			data.put("topType", parentList);
		}
		result.setData(data);
		return result;
	}
	
	public List<Map<String, Object>> encapsulationByMap(List<Map<String, Object>> outer,List<Map<String, Object>> type,List<Map<String,Object>> listMap,String typeIds,boolean isHuarong){
		
		Map<String,Object> tmp = null;
		Map<String,Object> obj = null;
		List<Map<String,Object>> listImg = null;
		List<Map<String,Object>> tmpType = null;
		Integer id = 0;
		Integer parentId = -1;
		Integer pid = -1;
		Integer sonId = -1;
		String typeIdKey = "typeId";
		if(isHuarong){
			typeIdKey = "docFileType";
		}
		//封装业务类型与关联图片
		for(int i=0;i<type.size();i++){
			tmp = type.get(i);
			id = MapUtils.getInteger(tmp, "id", 0);
			Iterator<Map<String,Object>> it = listMap.iterator();
			listImg = new ArrayList<Map<String,Object>>();
			while(it.hasNext()){
				 obj = it.next();
				sonId = MapUtils.getInteger(obj, typeIdKey, -1);
				if(id==sonId||id.equals(sonId)){
					String url = MapUtils.getString(obj, "url", "").replaceAll("_18", "_48");
					obj.put("url", url);
					listImg.add(obj);
					it.remove();
				}
			}
			tmp.put("listImgs", listImg);
			if(null==listMap||listMap.size()<=0){
				break;
			}
		}
		
		//大分类关联子分类
		for(int i=0;i<outer.size();i++){
			tmp = outer.get(i);
			parentId = MapUtils.getInteger(tmp, "id",0);
			Iterator<Map<String,Object>> it = type.iterator();
			tmpType = new ArrayList<Map<String,Object>>();
			while(it.hasNext()){
				Map<String,Object> sonMap = it.next();
				pid = MapUtils.getInteger(sonMap, "pid",-1);
				if(typeIds.indexOf(MapUtils.getInteger(sonMap, "id",0)+"")==-1){
					it.remove();
					continue;
				}
				if(pid==parentId||pid.equals(parentId)){
					tmpType.add(sonMap);
					it.remove();
				}
			}
			tmp.put("childrenType", tmpType);
			if(null==type||type.size()<=0){
				break;
			}
		}
		return outer;
	}
	
	
	public List<Map<String, Object>> encapsulationByBean(List<Map<String, Object>> outer,List<Map<String, Object>> type,List<BusinfoDto> listMap,String typeIds){
		
		Map<String,Object> tmp = null;
		BusinfoDto obj = null;
		List<BusinfoDto> listImg = null;
		List<Map<String,Object>> tmpType = null;
		int id = 0;
		Integer parentId = -1;
		Integer pid = -1;;
		//封装业务类型与关联图片
		for(int i=0;i<type.size();i++){
			tmp = type.get(i);
			id = MapUtils.getInteger(tmp, "id", 0);
			Iterator<BusinfoDto> it = listMap.iterator();
			listImg = new ArrayList<BusinfoDto>();
			while(it.hasNext()){
				obj = it.next();
				if(id==obj.getTypeId()){
					String url = obj.getUrl().replaceAll("_18", "_48");
					obj.setUrl(url);
					listImg.add(obj);
					it.remove();
				}
			}
			tmp.put("listImgs", listImg);
			if(null==listMap||listMap.size()<=0){
				break;
			}
		}
		
		//大分类关联子分类
		for(int i=0;i<outer.size();i++){
			tmp = outer.get(i);
			parentId = MapUtils.getInteger(tmp, "id",0);
			Iterator<Map<String,Object>> it = type.iterator();
			tmpType = new ArrayList<Map<String,Object>>();
			while(it.hasNext()){
				Map<String,Object> sonMap = it.next();
				pid = MapUtils.getInteger(sonMap, "pid",-1);
				if(typeIds.indexOf(MapUtils.getInteger(sonMap, "id",0)+"")==-1){
					it.remove();
					continue;
				}
				if(pid==parentId||pid.equals(parentId)){
					tmpType.add(sonMap);
					it.remove();
				}
			}
			tmp.put("childrenType", tmpType);
			if(null==type||type.size()<=0){
				break;
			}
		}
		return outer;
	}
	
	public void insretImg(Map<String,Object> map){
		String url = MapUtils.getString(map, "url", "");
		if(StringUtils.isNotBlank(url)){
			String[] arr = url.split(Constants.IMG_SEPARATE);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> tmp = null;
			for(int i=0;i<arr.length;i++){
				tmp = new HashMap<String,Object>();
				tmp.putAll(map);
				tmp.put("url", arr[i]);
				list.add(tmp);
			}
			if(list.size()>0){
				huaanMapper.insretImg(list);
			}
		}
	}
	
	public List<BusinfoDto> selectListMap(Map<String,Object> map){
		return huaanMapper.selectListMap(map);
	}
	public List<BusinfoDto> lookOver(Map<String, Object> map){
		 List<BusinfoDto> list = huaanMapper.selectListMap(map);
		 String url = "";
		 for(BusinfoDto tmp:list){
			  url = tmp.getUrl().replaceAll("_18", "_48");
			  tmp.setUrl(url);
		 }
		 return list;
	}
	
	public  int deleteImg(Map<String,Object> map){
		int success = huaanMapper.deleteImg(map);
		return success;
	}
	
	public static void main(String[] args) {
		String str = "10158,10174,10023,10151,10007,10175,10176,10009,10172,10052,10010,10022,10051,10159,10177,10002";
		String[] arr = str.split(",");
		List<String> list = Arrays.asList(arr);
		System.out.println(list.contains("10158"));
	}
}
