package com.anjbo.service.impl;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.config.page.*;
import com.anjbo.common.Constants;
import com.anjbo.common.RedisOperator;
import com.anjbo.dao.PageConfigMapper;
import com.anjbo.service.PageConfigService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 页面配置 
 * @author lic
 * @date 2017-8-18
 */
@Service
public class PageConfigServiceImpl implements PageConfigService {
	
	@Resource
	private PageConfigMapper pageConfigMapper;

	public List<PageConfigDto> selectPageConfigDtoList(){
		return pageConfigMapper.selectPageConfigDtoList();
	}
	
	public List<PageConfigSelectValuesDto> selectPageConfigSelectValuesDtoList() {
		return pageConfigMapper.selectPageConfigSelectValuesDtoList();
	}

	public List<PageTabConfigDto> selectPageTabConfigDtoList(){
		return pageConfigMapper.selectPageTabConfigDtoList();
	}
	
	public List<PageTabRegionConfigDto> selectPageTabRegionConfigDtoList(){
		return pageConfigMapper.selectPageTabRegionConfigDtoList();
	}

	public List<PageTabRegionFormConfigDto> selectPageTabRegionFormConfigDtoList(){
		return pageConfigMapper.selectPageTabRegionFormConfigDtoList();
	}
	
	@Override
	public void initPageConfig() {
		//表单页面
		List<PageConfigDto> pageConfigDtos = selectPageConfigDtoList();

		//表单标签
		List<PageTabConfigDto> pageTabConfigDtos = selectPageTabConfigDtoList();

		//表单标签区域
		List<PageTabRegionConfigDto> pageTabRegionConfigDtos = selectPageTabRegionConfigDtoList();

		//表单标签区域表单
		List<PageTabRegionFormConfigDto> pageTabRegionFormConfigDtos  = selectPageTabRegionFormConfigDtoList();

		//表单页面
		for (PageConfigDto pageConfigDto : pageConfigDtos) {

			//获取页面下的标签
			String[] tabClasses = pageConfigDto.getTabClasses().split(",");

			List<PageTabConfigDto> tempTabList = new ArrayList<PageTabConfigDto>();
			for (String tabClass : tabClasses) {

				//表单标签
				for (PageTabConfigDto pageTabConfigDto : pageTabConfigDtos) {
					if(tabClass.equals(pageTabConfigDto.getTabClass())){

						//获取标签下面的区域
						String[] regionClasses = pageTabConfigDto.getRegionClasses().split(",");

						List<PageTabRegionConfigDto> tempRegionList = new ArrayList<PageTabRegionConfigDto>();

						for (String regionClass : regionClasses) {

							//表单标签区域
							for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabRegionConfigDtos) {

								if(pageTabRegionConfigDto.getRegionClass().equals(regionClass)){
									//获取区域下面的表单
									String[] formClasses = pageTabRegionConfigDto.getFormClasses().split(",");

									List<List<PageTabRegionFormConfigDto>> tempValueList = new ArrayList<List<PageTabRegionFormConfigDto>>();

									for (String formClass : formClasses) {
										//表单集合
										List<PageTabRegionFormConfigDto> tempFormList = new ArrayList<PageTabRegionFormConfigDto>(); 
										//表单标签区域表单
										for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionFormConfigDtos) {

											if(pageTabRegionFormConfigDto.getFormClass().equals(formClass)){
												
												//显示隐藏参数转换json
												if(StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getParamsValues())){
													try {
														pageTabRegionFormConfigDto.setParamsValuesJosn(JSONObject.fromObject(pageTabRegionFormConfigDto.getParamsValues()));
													} catch (Exception e) {
														e.printStackTrace();
														System.out.println(pageTabRegionFormConfigDto.getTitle());
													}
												}
												tempFormList.add(pageTabRegionFormConfigDto);
											}
											
										}
										tempValueList.add(tempFormList);
									}
									pageTabRegionConfigDto.setValueList(tempValueList);
									tempRegionList.add(pageTabRegionConfigDto);
								}
							}
						}
						pageTabConfigDto.setPageTabRegionConfigDtos(tempRegionList);
						tempTabList.add(pageTabConfigDto);
					}
				}
			}
			pageConfigDto.setPageTabConfigDtos(tempTabList);
			RedisOperator.set(pageConfigDto.getPageClass(), pageConfigDto);
		}
	}

	public void initSelectConfig(){
		List<PageConfigSelectValuesDto> pageConfigSelectValuesDtos =  selectPageConfigSelectValuesDtoList();
		for (PageConfigSelectValuesDto pageConfigSelectValuesDto : pageConfigSelectValuesDtos) {
			Map<String, Object> configMap = new HashMap<String, Object>();
			if(pageConfigSelectValuesDto.getType() == 1){
				List<DictDto> listDictDtos = CommonDataUtil.getDictDtoByType(pageConfigSelectValuesDto.getSelectTypeClass());
				List<Map<String, String>> list = new ArrayList<Map<String,String>>();
				List<Map<String, String>> listName = new ArrayList<Map<String,String>>();
				Map<String, String>  tempMap = new HashMap<String, String>();
				Map<String, String>  tempMapName = new HashMap<String, String>();
//				System.out.println(pageConfigSelectValuesDto.getSelectTypeClass());
				for (DictDto dictDto : listDictDtos) {
					tempMap = new HashMap<String, String>();
					tempMap.put("id", dictDto.getCode());
					tempMap.put("name", dictDto.getName());
					tempMapName = new HashMap<String, String>();
					tempMapName.put("id", dictDto.getName());
					tempMapName.put("name", dictDto.getName());
					list.add(tempMap);
					listName.add(tempMapName);
				}
				//循环存储到redis中
				configMap.put("list", list);
				configMap.put("list_name", listName);
			}else{
				configMap.put("url", pageConfigSelectValuesDto.getUrl());
			}
			RedisOperator.set("page_config_"+pageConfigSelectValuesDto.getSelectTypeClass(), configMap);
		}
	}
	
	
	public PageConfigDto pageConfig(Map<String, Object> params){
		String pageClass = MapUtils.getString(params, "pageClass");
		PageConfigDto pageConfigDto = getPageConfigDto(pageClass);
		for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
			for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
				showOrHide(pageClass,pageConfigDto,pageTabRegionConfigDto);
				/*
				for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
					setPageTabRegionFormSelectList(pageTabRegionFormConfigDto);
				}*/
//				setPageTabRegionFormValue(pageTabRegionConfigDto, new HashMap<String, Object>());
			}				
		}
		return pageConfigDto;
	}
	public void showOrHide(String pageClass,PageConfigDto pageConfigDto,PageTabRegionConfigDto pageTabRegionConfigDto){

		Iterator<PageTabRegionFormConfigDto> it = pageTabRegionConfigDto.getValueList().get(0).iterator();
		while (it.hasNext()){
			PageTabRegionFormConfigDto pageTabRegionFormConfigDto = it.next();
			setPageTabRegionFormSelectList(pageTabRegionFormConfigDto);

		}

	}

	public List<PageTabRegionFormConfigDto> getPageTabRegionConfigDto(Map<String, Object> params){
		String pageClass = MapUtils.getString(params, "pageClass");
		String regionClass = MapUtils.getString(params, "regionClass");
		PageConfigDto pageConfigDto = getPageConfigDto(pageClass);
		for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
			for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
				if(regionClass.equals(pageTabRegionConfigDto.getRegionClass())){
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
						if(("slHouse".equals(pageTabRegionFormConfigDto.getFormClass())||"fdd_house".equals(pageTabRegionFormConfigDto.getFormClass())||"pthHouse".equals(pageTabRegionFormConfigDto.getFormClass())
								||"tdlHouse".equals(pageTabRegionFormConfigDto.getFormClass()))&&"city".equals(pageTabRegionFormConfigDto.getSpecialKey())){
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							pageTabRegionFormConfigDto.setValue(MapUtils.getString(params, "cityName"));
							pageTabRegionFormConfigDto.setSpecialValue(MapUtils.getString(params, "cityCode"));;
						}
						setPageTabRegionFormSelectList(pageTabRegionFormConfigDto);
					}
					return pageTabRegionConfigDto.getValueList().get(0);
				}
			}
		}
		return new ArrayList<PageTabRegionFormConfigDto>();
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean checkPageTabConfigDto(PageTabConfigDto pageTabConfigDto,Map<String, Object> params){
		int type = MapUtils.getIntValue(params, "type",0);
		String tblName = MapUtils.getString(params,"tblName");
		Map<String, Object> otherTblNameMap = new HashMap<String, Object>();
		Map<String, Object> dataParams = null;
		if(tblName!=null){
			dataParams = MapUtils.getMap(params, pageTabConfigDto.getTblName());
		} else {
			dataParams = params;
		}
		for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
			setPageTabRegionFormValue(pageTabRegionConfigDto, dataParams);
			for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
				//保存信息到列表
//				if(pageTabRegionFormConfigDto.getIsList() == 2){
//					String listKey = MapUtils.getString(params, "key","");
//					if(StringUtils.isEmpty(listKey)){
//						if(StringUtils.isNotEmpty(MapUtils.getString(dataParams, pageTabRegionFormConfigDto.getKey(),""))){
//							listKey += pageTabRegionFormConfigDto.getKey();
//						}
//					}else{
//						if(StringUtils.isNotEmpty(MapUtils.getString(dataParams, pageTabRegionFormConfigDto.getKey(),""))){
//							listKey += "," + pageTabRegionFormConfigDto.getKey();
//						}
//					}
//					if(StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getSpecialKey()) && StringUtils.isNotEmpty(MapUtils.getString(dataParams, pageTabRegionFormConfigDto.getSpecialKey(),""))){
//						listKey += "," + pageTabRegionFormConfigDto.getSpecialKey();
//					}
//					params.put("key", listKey);
//				}
				//保存到其他表
				if(StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getOtherTblName())){
					String[] otherTblNames = pageTabRegionFormConfigDto.getOtherTblName().split(",");
					for (String otherTblName : otherTblNames) {
						Map<String, Object> temp = MapUtils.getMap(otherTblNameMap, otherTblName,new HashMap<String, Object>());
						if(StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getKey())){
							temp.put(pageTabRegionFormConfigDto.getKey(), getValueByKeys(pageTabRegionConfigDto.getKey(), pageTabRegionFormConfigDto.getKey(), dataParams));
						}
						if(StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getSpecialKey())){
							temp.put(pageTabRegionFormConfigDto.getSpecialKey(), getValueByKeys(pageTabRegionConfigDto.getKey(), pageTabRegionFormConfigDto.getSpecialKey(), dataParams));
						}
						otherTblNameMap.put(otherTblName, temp);
					}
				}
				if(StringUtils.isNotBlank(tblName)
						&&tblName.contains("tbl_sm")
						&&1==pageTabRegionFormConfigDto.getType()){
					pageTabRegionFormConfigDto.setType(0);
				}
				if(!checkForm(pageTabRegionFormConfigDto, type)){
					return false;
				}
			}
		}
		params.put("otherTblName", otherTblNameMap);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private Object getValueByKeys(String regionKey,String key,Map<String, Object> dataParams){
		if(StringUtils.isNotEmpty(regionKey)){
			System.out.println("regionKey："+regionKey);
			List<Map<String, Object>> tempListMap = (List<Map<String, Object>>) MapUtils.getObject(dataParams, regionKey);
			String values ="";
			if(tempListMap!=null&&tempListMap.size()>0){
				for (int i = 0; i < tempListMap.size(); i++) {
					Map<String, Object> map = tempListMap.get(i);
					values = MapUtils.getString(map, key) ;
					
					//特殊处理只保存第一个
					if("buyList".equals(regionKey) && ("name".equals(key)
							||"certificateTypeCode".equals(key)||"certificateNo".equals(key)||"mobile".equals(key))){
						break;
					}
					if(i != tempListMap.size()-1){
						values += ",";
					}
				}
			}
			return values;
		}else{
			return MapUtils.getObject(dataParams, key);
		}
	}
	
	/**
	 * 校验表单
	 * @return
	 */
	private boolean checkForm(PageTabRegionFormConfigDto pageTabRegionFormConfigDto,int type){
		//非空校验
		if(pageTabRegionFormConfigDto.getIsNeed() == 2 && type !=0){
			if(StringUtils.isEmpty(pageTabRegionFormConfigDto.getValue())){
				return false;
			}
		}
		//正则校验
		if(StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getValue()) && pageTabRegionFormConfigDto.getType() == 1 && StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getTypeDepend())){
			String regular = pageTabRegionFormConfigDto.getTypeDepend().replace("/^", "").replace("$/", "");
			Pattern pattern = Pattern.compile(regular);
			return pattern.matcher(pageTabRegionFormConfigDto.getValue()).matches();
		}
		return true;
	}
	
	/**
	 * 设置区域下表单的value
	 * @param pageTabRegionConfigDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void setPageTabRegionFormValue(PageTabRegionConfigDto pageTabRegionConfigDto,Map<String, Object> dataMap){
		if(pageTabRegionConfigDto.getType() == 1){
			//普通区域赋值
			for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
				String value = MapUtils.getString(dataMap, pageTabRegionFormConfigDto.getKey());
				if(StringUtils.isNotEmpty(value)){
					pageTabRegionFormConfigDto.setValue(value);
				}
			}
		}else if(pageTabRegionConfigDto.getType() == 2
				|| 25 == pageTabRegionConfigDto.getType()){
			//循环区域赋值
			String key =  pageTabRegionConfigDto.getKey();
			List<Map<String, Object>> keyList = (List<Map<String, Object>>) MapUtils.getObject(dataMap, key ,new ArrayList<Map<String, Object>>());
			for (Map<String, Object> map : keyList) {
				List<PageTabRegionFormConfigDto> tempLisr = new ArrayList<PageTabRegionFormConfigDto>(); 
				tempLisr.addAll(pageTabRegionConfigDto.getValueList().get(0));
				for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : tempLisr) {
					String value = MapUtils.getString(map, pageTabRegionFormConfigDto.getKey());
					if(StringUtils.isNotEmpty(value)){
						pageTabRegionFormConfigDto.setValue(value);
					}
				}
			}
		}
	}
	
	/**
	 * 设置区域下表单的select数据
	 * @param pageTabRegionFormConfigDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void setPageTabRegionFormSelectList(PageTabRegionFormConfigDto pageTabRegionFormConfigDto){
		//如果是自带数据下拉框则需要配置初始化数据
		if(pageTabRegionFormConfigDto.getType() == 2
				|| 20==pageTabRegionFormConfigDto.getType()
				|| 25==pageTabRegionFormConfigDto.getType()
				|| 26==pageTabRegionFormConfigDto.getType()){
			Map<String, Object> dataMap2 = (Map<String, Object>) RedisOperator.get("page_config_"+pageTabRegionFormConfigDto.getTypeDepend());
			if(dataMap2 != null && dataMap2.containsKey("list")){
				if(pageTabRegionFormConfigDto.getSelectType() == 1){
					pageTabRegionFormConfigDto.setDataList((List<Map<String,String>>)dataMap2.get("list_name"));
				}else{
					pageTabRegionFormConfigDto.setDataList((List<Map<String,String>>)dataMap2.get("list"));
				}
			}else if(dataMap2 != null && dataMap2.containsKey("url")){
				pageTabRegionFormConfigDto.setDataList(getSelectList(MapUtils.getString(dataMap2, "url")));
			}
		}
	}
	
	/**
	 * 获取redis中的pageClass页面配置
	 * @param pageClass
	 * @return
	 */
	private PageConfigDto getPageConfigDto(String pageClass){
		PageConfigDto pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);
		if(pageConfigDto == null){
			initPageConfig();
			pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);
		}
		return pageConfigDto;
	}
	
	/**
	 * 通过url请求接口获取List
	 * List结构必须为List<Map<String, String>>
	 * Map的 key为id,value为name
	 * @param url
	 * @return
	 */
	private List<Map<String, String>> getSelectList(String url){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		int index = url.length();
		String tempUrl = url;
		Map<String, Object> params = new HashMap<String, Object>();
		if(url.indexOf("?")>=0){
			index = url.indexOf("?");
			tempUrl = url.substring(0, index);
			String[] tempParams = url.substring(index+1, url.length()).split("&");
			for (String str : tempParams) {
				String[] strs = str.split("=");
				params.put(strs[0], strs[1]);
			}
		}
		HttpUtil httpUtil = new HttpUtil();
		try {
			list = httpUtil.getList(Constants.LINK_CREDIT, tempUrl, params, Map.class);
		} catch (Exception e) {
			list = new ArrayList<Map<String,String>>();
		}
		return list;
	}
}
