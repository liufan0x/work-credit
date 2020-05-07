package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.anjbo.bean.data.PageFlowDto;
import com.anjbo.bean.data.PageListDto;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.PageListMapper;
import com.anjbo.service.PageFlowService;
import com.anjbo.service.PageListService;

@Service
public class PageListServiceImpl extends BaseServiceImpl<PageListDto> implements PageListService{

	private Log logger = LogFactory.getLog(this.getClass());
	
	@Resource private PageListMapper pageListMapper;
	
	@Resource private PageFlowService pageFlowService;
	
	@Resource private UserApi userApi;
	
	private void setTbl(Map<String, Object> params) {
		String tblName = MapUtils.getString(params, "tblName");
		if(!tblName.contains("_list")) {
			params.put("tblDataName", tblName+"list");
		}else {
			params.put("tblDataName", tblName);
		}
	}
	
	@Override
	public Map<String, Object> pageListData(Map<String, Object> params) {
		setTbl(params);
		return pageListMapper.pageListData(params);
	}
	
	@Override
	public int pageCount(Map<String, Object> params) {
		setTbl(params);
		setSelectSql(params, false);
		return pageListMapper.pageListCount(params);
	}
	
	@Override
	public List<Map<String, Object>> pageList(Map<String, Object> params) {
		setTbl(params);
		setSelectSql(params, true);
		return pageListMapper.pageListList(params);
	}
	
	@Override
	public void insertPageList(Map<String, Object> params) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.putAll(params);
		setTbl(tempMap);
		if(pageListData(tempMap) != null) {
			pageListMapper.updateListByKey(setUpdateSql(tempMap));
		}else {
			pageListMapper.insertListByKey(setInsertSql(tempMap));
		}
	}
	
	@Override
	public void withdraw(PageListDto pageListDto) {
		PageFlowDto pageFlowDto = new PageFlowDto();
		pageFlowDto.setOrderNo(pageListDto.getOrderNo());
		List<PageFlowDto> pageFlowDtos = pageFlowService.search(pageFlowDto);
		if(pageFlowDtos.size() > 0) {
			pageFlowDto = pageFlowDtos.get(pageFlowDtos.size()-1);	
			pageFlowService.delete(pageFlowDto);
			pageFlowDtos.remove(pageFlowDto);
		}
		if(pageFlowDtos.size() > 0) {
			pageFlowDto = pageFlowDtos.get(pageFlowDtos.size()-1);	
			pageListDto.setProcessId(pageFlowDto.getNextProcessId());
			pageListDto.setState("待"+pageFlowDto.getNextProcessName());
		}
		pageListMapper.update(pageListDto);
	}
	
	public Map<String, Object> setInsertSql(Map<String, Object> params){
		List<String> columns = pageListMapper.selectTableColumns(MapUtils.getString(params, "tblDataName"));
		String keys = "";
		String values = "";
		for (String columnName : columns) {
			if(params.containsKey(columnName)) {
				String columnValue = MapUtils.getString(params, columnName);
				if(StringUtils.isNotEmpty(columnValue)) {
					keys += ",`" +columnName + "`";
					values += ",'" +columnValue + "'";
				}
			}
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("tblDataName", MapUtils.getString(params, "tblDataName"));
		map.put("keys", keys);
		map.put("values", values);
		return map;
	}
	
	public Map<String, Object> setUpdateSql(Map<String, Object> params){
		List<String> columns = pageListMapper.selectTableColumns(MapUtils.getString(params, "tblDataName"));
		String sql = "";
		for (String columnName : columns) {
			if(params.containsKey(columnName)) {
				String columnValue = MapUtils.getString(params, columnName);
				if(StringUtils.isNotEmpty(columnValue)) {
					sql += columnName + "='" + columnValue + "',";
				}
			}
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("tblDataName", MapUtils.getString(params, "tblDataName"));
		map.put("orderNo", MapUtils.getString(params, "orderNo"));
		map.put("keyValue", sql);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> setSelectSql(Map<String,Object> params,boolean isSort){
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
		logger.info("select where order by:"+whereOrderBy);
		params.put("whereOrderBy", whereOrderBy);
		return params;
	}
	
}