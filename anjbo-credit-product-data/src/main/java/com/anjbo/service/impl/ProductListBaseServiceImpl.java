package com.anjbo.service.impl;

import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.common.Constants;
import com.anjbo.dao.ProductDataBaseMapper;
import com.anjbo.dao.ProductFlowBaseMapper;
import com.anjbo.dao.ProductListBaseMapper;
import com.anjbo.service.ProductListBaseService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.JsonUtil;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ProductListBaseServiceImpl implements ProductListBaseService {
	
	Logger log = Logger.getLogger(ProductListBaseServiceImpl.class);

	@Resource
	private ProductListBaseMapper productListBaseMapper;
	
	@Resource
	private ProductDataBaseMapper productDataBaseMapper;
	
	@Resource
	private ProductFlowBaseMapper productFlowBaseMapper;

	@Override
	@Transactional
	public int insertProductListBase(Map<String, Object> params) {
		return productListBaseMapper.insertProductListBase(params);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> selectProductListBase(
			Map<String, Object> params) {
		params = setSql(params,true);
		List<Map<String, Object>> flowAllList = productFlowBaseMapper.selectProductFlowBaseAll();
		Map<String,Object> reopen = new HashMap<String, Object>();
		for (Map<String, Object> map : flowAllList) {
			reopen.put(MapUtils.getString(map, "orderNo"), true);
		}
		//查询能撤回到刘俊龙操作的订单
		List<Map<String,Object>> withdrawOrderNos = productFlowBaseMapper.selectWithdrawAll();
		Map<String,Object> ableWithdraw = new HashMap<String, Object>();
		for (Map<String, Object> map : withdrawOrderNos) {
			ableWithdraw.put(MapUtils.getString(map, "orderNo"), true);
		}
		List<Map<String, Object>> list = productListBaseMapper.selectProductListBase(params);
		for (Map<String, Object> map : list) {
			if(reopen.containsKey(MapUtils.getString(map, "orderNo"))){
				map.put("reopen", true);
				//重新开启由渠道经理操作
				map.put("currentHandlerUid", MapUtils.getString(map, "channelManagerUid"));
			}
			if(ableWithdraw.containsKey(MapUtils.getString(map, "orderNo"))){
				map.put("repaymentMemberUid", ConfigUtil.getStringValue(Constants.BASE_CM_CHANNELMANAGERUID,ConfigUtil.CONFIG_BASE));
			}
		}
		return list;
	}

	@Override
	@Transactional
	public int selectProductListBaseCount(Map<String, Object> params) {
		params = setSql(params,false);
		return productListBaseMapper.selectProductListBaseCount(params);
	}

	@Override
	@Transactional
	public Map<String, Object> selectProductListBaseByOrderNo(ProductDataDto productDataDto) {
		return productListBaseMapper.selectProductListBaseByOrderNo(productDataDto);
	} 
	
	public Map<String,Object> setSql(Map<String,Object> params,boolean isSort){
		//设置查询条件
		String whereOrderBy=" where 1=1 ";
		String sort = " order by 1=1 ";
		Object obj = params.get("condition");
		Object sortobj = params.get("orderBy");
		List<Map<String,Object>> condition = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> orderBy = new ArrayList<Map<String,Object>>();
		if(obj!=null){
			condition = (List<Map<String, Object>>) obj;
		}
		if(sortobj!=null){
			orderBy = (List<Map<String, Object>>) sortobj;
		}
		String key="";
		String value="";
		String startValue="";
		String endValue="";
		String type="";
		for (Map<String, Object> map : condition) {
			key = MapUtils.getString(map, "key");
			type = MapUtils.getString(map, "type");
			if("2".equals(type)||"5".equals(type)){
				type="like";
			}else if("3".equals(type)){
				type="bewteen";
			}else if("4".equals(type)){
				type = "timeSection";
			}else {
				type="=";
			}
			if(map.get("value")!=null){
				value= MapUtils.getString(map, "value");
				if(!"".equals(value)){
					if("like".equals(type)){
						if(value.contains("%")){
							value = value.replaceAll("%","''");
						}
						whereOrderBy +=" and "+ key+" "+type+" '%"+value+"%'";
					}else{
						whereOrderBy +=" and "+ key+" "+type+" '"+value+"'";
					}
				}
			}else if(map.get("value")==null && "bewteen".equals(type)){
				startValue =MapUtils.getString(map, "startValue");
				endValue =MapUtils.getString(map, "endValue");
				whereOrderBy += " and " +key +" "+type +" '"+startValue+"' and '"+endValue+"'";
			} else if(null==map.get("value") && "timeSection".equals(type)){
				startValue =MapUtils.getString(map, "startValue","");
				endValue =MapUtils.getString(map, "endValue","");
				if(StringUtils.isNotBlank(startValue)){
					whereOrderBy += " and " +key +" >= '"+startValue+"'";
				}
				if(StringUtils.isNotBlank(endValue)){
					whereOrderBy += " and DATE_FORMAT(" +key +",'%Y-%m-%d') <= DATE_FORMAT('"+endValue+"','%Y-%m-%d')";
				}

			}
		}
		if(StringUtils.isNotEmpty(MapUtils.getString(params, "deptAllUid",""))
				&&MapUtils.getString(params,"tblName").contains("tbl_sm")){
			String uids = MapUtils.getString(params,"deptAllUid","");
			whereOrderBy+= " and ( currentHandlerUid in ("+uids+") " +
					"or createUid in ("+uids+") " +
					"or channelManagerUid in ("+uids+") " +
					"or expandManagerUid in ("+uids+") " +
					"or investigationManagerUid in ("+uids+")" +
					"or expandChiefUid in ("+uids+") ) ";
		} else if(StringUtils.isNotEmpty(MapUtils.getString(params, "deptAllUid",""))){
			whereOrderBy+= " and ( currentHandlerUid in ("+MapUtils.getString(params, "deptAllUid","")+") or createUid in ("+MapUtils.getString(params, "deptAllUid","")+") or channelManagerUid in ("+MapUtils.getString(params, "deptAllUid","")+") )";
		}
		if(isSort){
			String sortName ="";
			String sortRule ="";
			for(Map<String,Object> tMap: orderBy){
				if(tMap.get("sortName")!=null){
					sortName = MapUtils.getString(tMap, "sortName");
					sortRule = MapUtils.getString(tMap, "sortRule");
					sort += ","+sortName +" "+ sortRule;
				}
			}
			if(" order by 1=1 ".equals(sort)){
				sort = " order by createTime desc ";
			}
			whereOrderBy+=sort;
		}
		log.info("select where order by:"+whereOrderBy);
		params.put("whereOrderBy", whereOrderBy);
		return params;
	}

	@Override
	public List<Map<String, Object>> findTask() {
		return productListBaseMapper.findTask();
	}

	@Override
	public int updateProcessId(Map<String, Object> params) {
		return productListBaseMapper.updateProcessId(params);
	}

	@Override
	public int updateProductListBase(Map<String, Object> params) {
		return productListBaseMapper.updateProductListBase(params);
	}

	@Override
	public int close(Map<String, Object> map) {
		//更新订单状态
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tblName = MapUtils.getString(map, "tblName");
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
		map.put("tblDataName", tblDataName);
		map.put("currentHandlerUid", "-");
		map.put("currentHandler", "-");
		map.put("previousHandleTime", sdf.format(new Date()));
		map.put("state", "已关闭");
		map.put("processId", "closeOrder");
		productListBaseMapper.updateProductListBase(map);
		//往data表录入关闭数据
		tblDataName =  tblName.substring(0, tblName.indexOf("_",5))+"_data";
		ProductDataDto productDataDto = new ProductDataDto();
		String updateUid = MapUtils.getString(map, "updateUid");
		String orderNo = MapUtils.getString(map, "orderNo");
		String closeReason = MapUtils.getString(map, "closeReason");
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("closeReason", closeReason);
		maps.put("closeTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		String dataStr = JSONObject.fromObject(maps).toString();
		productDataDto.setCreateUid(updateUid);
		productDataDto.setDataStr(dataStr);
		productDataDto.setTblName(tblName);
		productDataDto.setTblDataName(tblDataName);
		productDataDto.setOrderNo(orderNo);
		productDataBaseMapper.insertProductDataBase(productDataDto);
		//插入关闭流水
		Map<String,Object> mapFlow = new HashMap<String, Object>();
		tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_flow";
		mapFlow.put("tblDataName", tblDataName);
		mapFlow.put("createUid", updateUid);
		mapFlow.put("currentProcessId", MapUtils.getString(map, "processId"));
		mapFlow.put("orderNo", orderNo);
		mapFlow.put("handleUid", updateUid);
		productFlowBaseMapper.insertProductFlowBase(mapFlow);
		return 1;
	}

	@Override
	public int updataLoanAmount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return productListBaseMapper.updataLoanAmount(params);
	}

	@Override
	public int reopen(Map<String, Object> map) {
		//更新订单状态
		String tblName = MapUtils.getString(map, "tblName");
		if(StringUtils.isBlank(tblName)){
			tblName = "tbl_cm_reopen";
		}
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
		map.put("tblDataName", tblDataName);
		map.put("state", "待提交申请按揭");
		map.put("processId", "subMortgage");
		productListBaseMapper.updateProductListBase(map);
		return 1;
	}
	/**
	 * 根据机构码查询机构列表信息
	 * @param params
	 * @return
	 */
	public Map<String,Object> selectSmList(Map<String, Object> params){
		return productListBaseMapper.selectSmList(params);
	}
	@Override
	public Map<String, Object> selectOrderList(Map<String, Object> params) {
		return productListBaseMapper.selectOrderList(params);
	}

	public int updateProductListBaseByKey(ProductDataDto productDataDto){
		return productListBaseMapper.updateProductListBaseByKey(productDataDto);
	}
	/**
	 * 自定义条件查询list表
	 * @param map(key=tblDataName:表名,key=whereCondition:条件)
	 * @return
	 */
	@Override
	public List<Map<String,Object>> selectProductListCustomWhereCondition(Map<String,Object> map){
		return productListBaseMapper.selectProductListCustomWhereCondition(map);
	}
	@Override
	public int updatePrice(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return productListBaseMapper.updatePrice(params);
	}

	@Override
	public List<Map<String, Object>> selectAllOrder(Map<String, Object> map) {
		return productListBaseMapper.selectAllOrder(map);
	}

	/**
	 * 指派渠道经理
	 */
	@Override
	public int repaymentChannelManager(Map<String, Object> map) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//往data表录入渠道经理数据
		String tblName = "tbl_cm_repaymentChannelManager";
		String tblDataName =  tblName.substring(0, tblName.indexOf("_",5))+"_data";
		ProductDataDto productDataDto = new ProductDataDto();
		String updateUid = MapUtils.getString(map, "updateUid");
		String orderNo = MapUtils.getString(map, "orderNo");
		Map<String,Object> maps = new HashMap<String, Object>();
		String channelManagerUid = "";
		String channelManagerName = "";
		String remark = "";
		if(!map.containsKey("channelManagerUid")){//PC端指派渠道经理
			channelManagerUid = MapUtils.getMap(map, "data").get("channelManagerUid")==null?"":MapUtils.getMap(map, "data").get("channelManagerUid").toString();
			channelManagerName = MapUtils.getMap(map, "data").get("channelManagerName")==null?"":MapUtils.getMap(map, "data").get("channelManagerName").toString();
			remark = MapUtils.getMap(map, "data").get("remark")==null?"":MapUtils.getMap(map, "data").get("remark").toString();
		}else{
			channelManagerUid = MapUtils.getString(map, "channelManagerUid","");
			channelManagerName = MapUtils.getString(map, "channelManagerName","");
			remark = MapUtils.getString(map, "remark","");
		}
		maps.put("channelManagerUid", channelManagerUid);
		maps.put("channelManagerName", channelManagerName);
		maps.put("remark", remark);
		String dataStr = JSONObject.fromObject(maps).toString();
		productDataDto.setCreateUid(updateUid);
		productDataDto.setDataStr(dataStr);
		productDataDto.setTblName(tblName);
		productDataDto.setTblDataName(tblDataName);
		productDataDto.setOrderNo(orderNo);
		productDataBaseMapper.insertProductDataBase(productDataDto);
		//往data表更新渠道经理数据
		productDataDto.setTblName("tbl_cm_assess");
		Map<String,Object> m = productDataBaseMapper.selectProductDataBase(productDataDto);
		String str = MapUtils.getString(m, "data");
		Map<String,Object> data = new HashMap<String, Object>();
		try {
			data = JsonUtil.jsonToMap(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		data.put("channelManagerUid", channelManagerUid);
		data.put("channelManagerName", channelManagerName);
		dataStr = JSONObject.fromObject(data).toString();
		productDataDto.setDataStr(dataStr);
		productDataBaseMapper.updateProductDataBase(productDataDto);
		//插入指派流水
		Map<String,Object> mapFlow = new HashMap<String, Object>();
		tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_flow";
		mapFlow.put("tblDataName", tblDataName);
		mapFlow.put("createUid", updateUid);
		mapFlow.put("currentProcessId", "repaymentChannelManager");
		mapFlow.put("nextProcessId", "assess");
		mapFlow.put("orderNo", orderNo);
		mapFlow.put("handleUid", updateUid);
		//更新订单状态
		tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
		productFlowBaseMapper.insertProductFlowBase(mapFlow);
		map.put("tblDataName", tblDataName);
		map.put("currentHandlerUid", channelManagerUid);
		map.put("currentHandler", channelManagerName);
		map.put("channelManagerUid", channelManagerUid);
		map.put("channelManagerName", channelManagerName);
		map.put("previousHandleTime", sdf.format(new Date()));
		map.put("state", "待提交评估");
		map.put("processId", "assess");
		productListBaseMapper.updateProductListBase(map);
		return 1;
	}

	/**
	 * 撤回指派渠道经理
	 */
	@Override
	public int withdraw(Map<String, Object> map) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String orderNo = MapUtils.getString(map, "orderNo");
		String tblName = "tbl_cm_repaymentChannelManager";
		String tblDataName =  tblName.substring(0, tblName.indexOf("_",5))+"_data";
		map.put("tblName", tblName);
		map.put("tblDataName", tblDataName);
		productDataBaseMapper.delete(map);
		//往data表更新渠道经理数据
		ProductDataDto productDataDto = new ProductDataDto();
		productDataDto.setTblName("tbl_cm_assess");
		productDataDto.setOrderNo(orderNo);
		productDataDto.setTblDataName(tblDataName);
		Map<String,Object> m = productDataBaseMapper.selectProductDataBase(productDataDto);
		String str = MapUtils.getString(m, "data");
		Map<String,Object> data = new HashMap<String, Object>();
		try {
			data = JsonUtil.jsonToMap(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		data.put("channelManagerUid", null);
		data.put("channelManagerName", null);
		String dataStr = JSONObject.fromObject(data).toString();
		productDataDto.setDataStr(dataStr);
		productDataBaseMapper.updateProductDataBase(productDataDto);
		//删除最后一条流水
		tblDataName =  tblName.substring(0, tblName.indexOf("_",5))+"_flow";
		map.put("tblDataName", tblDataName);
		productFlowBaseMapper.deleteLastFlow(map);
		//更新订单状态
		tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
		map.put("tblDataName", tblDataName);
		map.put("currentHandlerUid", ConfigUtil.getStringValue(Constants.BASE_CM_CHANNELMANAGERUID,ConfigUtil.CONFIG_BASE));
		map.put("currentHandler", "刘俊龙");
		map.put("channelManagerUid", null);
		map.put("channelManagerName", null);
		map.put("previousHandleTime", sdf.format(new Date()));
		map.put("state", "待指派渠道经理");
		map.put("processId", "repaymentChannelManager");
		productListBaseMapper.updateProductListBaseNull(map);
		return 1;
	}
	
}
