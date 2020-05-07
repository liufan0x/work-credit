package com.anjbo.service.impl;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.config.PageConfigDto;
import com.anjbo.bean.config.PageConfigSelectValuesDto;
import com.anjbo.bean.config.PageListColumnsConfigDto;
import com.anjbo.bean.config.PageListConfigDto;
import com.anjbo.bean.config.PageTabConfigDto;
import com.anjbo.bean.config.PageTabRegionConfigDto;
import com.anjbo.bean.config.PageTabRegionFormConfigDto;
import com.anjbo.bean.data.PageBusinfoTypeDto;
import com.anjbo.bean.data.PageDataDto;
import com.anjbo.bean.data.PageFlowDto;
import com.anjbo.bean.data.PageListDto;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.PageConfigMapper;
import com.anjbo.service.PageConfigService;
import com.anjbo.service.PageDataService;
import com.anjbo.service.PageFlowService;
import com.anjbo.service.PageListService;
import com.anjbo.utils.BeanToMapUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 页面配置
 * 
 * @author lic
 * @date 2017-8-18
 */
@Service
public class PageConfigServiceImpl implements PageConfigService {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private PageConfigMapper pageConfigMapper;

	@Resource
	private PageDataService pageDataService;

	@Resource
	private PageFlowService pageFlowService;

	@Resource
	private PageListService pageListService;

	@Resource
	private DataApi dataApi;

	@Resource
	private UserApi userApi;

	@Override
	public PageListConfigDto selectPageListConfig(PageListConfigDto listConfigDto) {
		listConfigDto = pageConfigMapper.selectPageListConfig(listConfigDto);
		PageListColumnsConfigDto pageListColumnsConfigDto = new PageListColumnsConfigDto();
		pageListColumnsConfigDto.setListName(listConfigDto.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageListColumnsConfigDto> pageListColumnsConfigDtos = pageConfigMapper
				.selectPageListColumnsConfig(pageListColumnsConfigDto);
		for (PageListColumnsConfigDto column : pageListColumnsConfigDtos) {
			map.put(column.getField(), column.isVisible());
		}
		listConfigDto.setColumnSwitch(map);
		listConfigDto.setPageListColumnsConfigDtos(pageListColumnsConfigDtos);
		return listConfigDto;
	}

	/**
	 * 获取redis中的pageClass页面配置
	 * 
	 * @param pageClass
	 * @return
	 */
	private PageConfigDto getPageConfigDto(String pageClass) {
		PageConfigDto pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);
		if (pageConfigDto == null) {
			initPageConfig();
			pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);
		}
		return pageConfigDto;
	}

	@SuppressWarnings("unchecked")
	public PageConfigDto pageConfig(Map<String, Object> params) {
		PageConfigDto pageConfigDto = getPageConfigDto(MapUtils.getString(params, "pageClass", ""));
		Iterator<PageTabConfigDto> pageTabConfigDtos = pageConfigDto.getPageTabConfigDtos().iterator();
		// 循环标签
		while (pageTabConfigDtos.hasNext()) {
			PageTabConfigDto pageTabConfigDto = pageTabConfigDtos.next();
			if (pageTabConfigDto.getTitle().equals("1.完善订单") || pageTabConfigDto.getTitle().equals("2.审批人")
					|| pageTabConfigDto.getTitle().equals("城市") || pageTabConfigDto.getTitle().equals("订单分配员")) {
				pageTabConfigDtos.remove();
			}
			if (pageTabConfigDto.getTitle().equals("询价/查档")) {
				pageTabConfigDto.setTitle("询价/查档/诉讼");
			}
			boolean hasData = false;
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 默认链接
			if ("default".equals(pageTabConfigDto.getDataUrl())
					&& StringUtils.isNotBlank(pageTabConfigDto.getTblName())) {
				PageDataDto pageDataDto = new PageDataDto();
				pageDataDto.setOrderNo(MapUtils.getString(params, "orderNo"));
				pageDataDto.setTblName(pageTabConfigDto.getTblName());
				pageDataDto = pageDataService.find(pageDataDto);
				if (pageDataDto != null) {
					dataMap = JSONObject.fromObject(pageDataService.find(pageDataDto).getData());
				}
			} else {

			}

			hasData = dataMap != null && !dataMap.isEmpty();

			// 循环区域
			Iterator<PageTabRegionConfigDto> pageTabRegionConfigDtos = pageTabConfigDto.getPageTabRegionConfigDtos()
					.iterator();
			while (pageTabRegionConfigDtos.hasNext()) {
				PageTabRegionConfigDto pageTabRegionConfigDto = pageTabRegionConfigDtos.next();

				if (hasData && pageTabRegionConfigDto.getType() == 2) {
					if (dataMap.get(pageTabRegionConfigDto.getKey()) == null) {
						continue;
					}
					List<Map<String, Object>> tempDataList = JSONObject.fromObject(dataMap)
							.getJSONArray(pageTabRegionConfigDto.getKey());
					List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
					try {
						//解决深浅拷贝问题。
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							formList.add((PageTabRegionFormConfigDto) BeanUtils.cloneBean(pageTabRegionFormConfigDto));
						}
						pageTabRegionConfigDto.setValueList(new ArrayList<List<PageTabRegionFormConfigDto>>());
						for (Map<String, Object> tempData : tempDataList) {
							List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
							for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
								setPageTabRegionFormSelectList(pageTabRegionFormConfigDto);
								setValues(pageTabRegionFormConfigDto, tempData, params);
								tempList.add((PageTabRegionFormConfigDto) BeanUtils.cloneBean(pageTabRegionFormConfigDto));

							}
							pageTabRegionConfigDto.getValueList().add(tempList);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// 循环表单
				Iterator<PageTabRegionFormConfigDto> pageTabRegionFormConfigDtos = pageTabRegionConfigDto.getValueList()
						.get(0).iterator();
				while (pageTabRegionFormConfigDtos.hasNext()) {
					PageTabRegionFormConfigDto pageTabRegionFormConfigDto = pageTabRegionFormConfigDtos.next();
					// 设置表单值
					setPageTabRegionFormSelectList(pageTabRegionFormConfigDto);
					if (hasData && pageTabRegionConfigDto.getType() == 1) {
						setValues(pageTabRegionFormConfigDto, dataMap, params);
					}
				}
			}
		}
		return pageConfigDto;
	}

	public List<PageTabRegionFormConfigDto> getPageTabRegionConfigDto(Map<String, Object> params) {
		PageConfigDto pageConfigDto = getPageConfigDto(
				MapUtils.getString(params, "tblName", "") + MapUtils.getString(params, "processId", "") + "_page");
		String regionClass = MapUtils.getString(params, "regionClass");
		for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
			for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
				if (regionClass.equals(pageTabRegionConfigDto.getRegionClass())) {
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList()
							.get(0)) {
						if (("slHouse".equals(pageTabRegionFormConfigDto.getFormClass())
								|| "fdd_house".equals(pageTabRegionFormConfigDto.getFormClass()))
								&& "city".equals(pageTabRegionFormConfigDto.getSpecialKey())) {
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							pageTabRegionFormConfigDto.setValue(MapUtils.getString(params, "cityName"));
							pageTabRegionFormConfigDto.setSpecialValue(MapUtils.getString(params, "cityCode"));
							;
						}
						setPageTabRegionFormSelectList(pageTabRegionFormConfigDto);
					}
					return pageTabRegionConfigDto.getValueList().get(0);
				}
			}
		}
		return new ArrayList<PageTabRegionFormConfigDto>();
	}

	private void setValues(PageTabRegionFormConfigDto pageTabRegionFormConfigDto, Map<String, Object> data,
			Map<String, Object> params) {
		// 匹配小数
		Pattern p = Pattern.compile("(^[1-9](\\.\\d{0,})?)|(^[0-9]\\.\\d{1,})\\d*$");

		String key = pageTabRegionFormConfigDto.getKey();
		String specialKey = pageTabRegionFormConfigDto.getSpecialKey();
		String keyValue = MapUtils.getString(data, key, "");

		if (1 == pageTabRegionFormConfigDto.getType()) {
			if ("cityName".equals(pageTabRegionFormConfigDto.getTypeDepend())) {
				pageTabRegionFormConfigDto.setValue(MapUtils.getString(params, "cityName"));
			}
		} else if (2 == pageTabRegionFormConfigDto.getType()) {
			if (!(StringUtils.isNotEmpty(MapUtils.getString(data, specialKey, ""))
					&& !"null".equals(MapUtils.getString(data, specialKey, "")))) {
				data.put(specialKey, MapUtils.getString(data, key, ""));
			}
		} else if (3 == pageTabRegionFormConfigDto.getType()) {
			if (pageTabRegionFormConfigDto.getTypeDepend()
					.contains("/credit/user/base/v/choicePersonnelByAuthName?choicePersonnel=")) {
				pageTabRegionFormConfigDto.setTypeDepend(String.format(pageTabRegionFormConfigDto.getTypeDepend(),
						MapUtils.getString(params, "cityCode"), MapUtils.getString(params, "productCode")));
			}

			if (pageTabRegionFormConfigDto.getTypeDepend()
					.contains("/credit/common/base/v/choiceDictName?type=bookingSzAreaOid&pcode=")) {
				pageTabRegionFormConfigDto.setTypeDepend(String.format(pageTabRegionFormConfigDto.getTypeDepend(),
						MapUtils.getString(params, "cityCode")));
			}
		} else if (5 == pageTabRegionFormConfigDto.getType()) {
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (StringUtils.isNotBlank(keyValue)) {
				Map<String, Object> tempMap = null;
				for (String str : keyValue.split(",")) {
					tempMap = new HashMap<String, Object>();
					tempMap.put("img", str);
					dataList.add(tempMap);
				}
			}
			pageTabRegionFormConfigDto.setDataList(dataList);
		} else if (20 == pageTabRegionFormConfigDto.getType()) {
			if (pageTabRegionFormConfigDto.getDataList() != null) {
				for (Map<String, Object> map : pageTabRegionFormConfigDto.getDataList()) {
					map.put("check", false);
					for (String str : keyValue.split(",")) {
						if (MapUtils.getString(map, "id").equals(str) || MapUtils.getString(map, "name").equals(str)) {
							map.put("check", true);
						}
					}
				}
			}
		}

		if (StringUtils.isNotEmpty(MapUtils.getString(data, key, ""))
				&& !"null".equals(MapUtils.getString(data, key, ""))) {
			try {
				if (data.get(key) instanceof Double) {
					BigDecimal bigDecimal = new BigDecimal(MapUtils.getString(data, key, ""));
					pageTabRegionFormConfigDto.setValue(bigDecimal.toPlainString());
				} else if (p.matcher(MapUtils.getString(data, key, "")).find()) {
					BigDecimal bigDecimal = new BigDecimal(MapUtils.getString(data, key, ""));
					pageTabRegionFormConfigDto.setValue(bigDecimal.toPlainString());
				} else {
					pageTabRegionFormConfigDto.setValue(MapUtils.getString(data, key, ""));
				}
			} catch (Exception e) {
				pageTabRegionFormConfigDto.setValue(MapUtils.getString(data, key, ""));
			}
		}

		if (StringUtils.isNotEmpty(MapUtils.getString(data, specialKey, ""))
				&& !"null".equals(MapUtils.getString(data, specialKey, ""))) {
			pageTabRegionFormConfigDto.setSpecialValue(MapUtils.getString(data, specialKey, ""));
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> setOtherData(PageTabConfigDto pageTabConfigDto, Map<String, Object> dataParams) {
		Map<String, Object> otherData = new HashMap<String, Object>();
		for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
			setPageTabRegionFormValue(pageTabRegionConfigDto, dataParams);
			for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
				// 保存到其他表
				if (StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getOtherTblName())) {
					String[] otherTblNames = pageTabRegionFormConfigDto.getOtherTblName().split(",");
					for (String otherTblName : otherTblNames) {
						Map<String, Object> temp = MapUtils.getMap(otherData, otherTblName,
								new HashMap<String, Object>());
						if (StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getKey())) {
							temp.put(pageTabRegionFormConfigDto.getKey(), getValueByKeys(
									pageTabRegionConfigDto.getKey(), pageTabRegionFormConfigDto.getKey(), dataParams));
						}
						if (StringUtils.isNotEmpty(pageTabRegionFormConfigDto.getSpecialKey())) {
							temp.put(pageTabRegionFormConfigDto.getSpecialKey(),
									getValueByKeys(pageTabRegionConfigDto.getKey(),
											pageTabRegionFormConfigDto.getSpecialKey(), dataParams));
						}
						otherData.put(otherTblName, temp);
					}
				}
			}
		}
		return otherData;
	}

	@SuppressWarnings("unchecked")
	private Object getValueByKeys(String regionKey, String key, Map<String, Object> dataParams) {
		if (StringUtils.isNotEmpty(regionKey)) {
			List<Map<String, Object>> tempListMap = (List<Map<String, Object>>) MapUtils.getObject(dataParams,
					regionKey);
			String values = "";
			if (tempListMap != null && tempListMap.size() > 0) {
				for (int i = 0; i < tempListMap.size(); i++) {
					Map<String, Object> map = tempListMap.get(i);
					values = MapUtils.getString(map, key);

					// 特殊处理只保存第一个
					if ("buyList".equals(regionKey) && ("name".equals(key) || "certificateTypeCode".equals(key)
							|| "certificateNo".equals(key) || "mobile".equals(key))) {
						break;
					}
					if (i != tempListMap.size() - 1) {
						values += ",";
					}
				}
			}
			return values;
		} else {
			return MapUtils.getObject(dataParams, key);
		}
	}

	/**
	 * 设置区域下表单的value
	 * 
	 * @param pageTabRegionConfigDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void setPageTabRegionFormValue(PageTabRegionConfigDto pageTabRegionConfigDto, Map<String, Object> dataMap) {
		if (pageTabRegionConfigDto.getType() == 1) {
			// 普通区域赋值
			for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
				String value = MapUtils.getString(dataMap, pageTabRegionFormConfigDto.getKey());
				if (StringUtils.isNotEmpty(value)) {
					pageTabRegionFormConfigDto.setValue(value);
				}
			}
		} else if (pageTabRegionConfigDto.getType() == 2 || 25 == pageTabRegionConfigDto.getType()) {
			// 循环区域赋值
			String key = pageTabRegionConfigDto.getKey();
			List<Map<String, Object>> keyList = (List<Map<String, Object>>) MapUtils.getObject(dataMap, key,
					new ArrayList<Map<String, Object>>());
			for (Map<String, Object> map : keyList) {
				List<PageTabRegionFormConfigDto> tempLisr = new ArrayList<PageTabRegionFormConfigDto>();
				tempLisr.addAll(pageTabRegionConfigDto.getValueList().get(0));
				for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : tempLisr) {
					String value = MapUtils.getString(map, pageTabRegionFormConfigDto.getKey());
					if (StringUtils.isNotEmpty(value)) {
						pageTabRegionFormConfigDto.setValue(value);
					}
				}
			}
		}
	}

	/**
	 * 设置区域下表单的select数据
	 * 
	 * @param pageTabRegionFormConfigDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void setPageTabRegionFormSelectList(PageTabRegionFormConfigDto pageTabRegionFormConfigDto) {
		// 如果是自带数据下拉框则需要配置初始化数据
		if (pageTabRegionFormConfigDto.getType() == 2 || 20 == pageTabRegionFormConfigDto.getType()
				|| 25 == pageTabRegionFormConfigDto.getType() || 26 == pageTabRegionFormConfigDto.getType()) {
			Map<String, Object> dataMap2 = (Map<String, Object>) RedisOperator
					.get("page_config_" + pageTabRegionFormConfigDto.getTypeDepend());
			if (dataMap2 != null && dataMap2.containsKey("list")) {
				if (pageTabRegionFormConfigDto.getSelectType() == 1) {
					pageTabRegionFormConfigDto.setDataList((List<Map<String, Object>>) dataMap2.get("list_name"));
				} else {
					pageTabRegionFormConfigDto.setDataList((List<Map<String, Object>>) dataMap2.get("list"));
				}
			}
		}
	}

	@Override
	public void initPageConfig() {
		// 表单页面
		List<PageConfigDto> pageConfigDtos = selectPageConfigDtoList();

		// 表单标签
		List<PageTabConfigDto> pageTabConfigDtos = selectPageTabConfigDtoList();

		// 表单标签区域
		List<PageTabRegionConfigDto> pageTabRegionConfigDtos = selectPageTabRegionConfigDtoList();

		// 表单标签区域表单
		List<PageTabRegionFormConfigDto> pageTabRegionFormConfigDtos = selectPageTabRegionFormConfigDtoList();

		// 表单页面
		for (PageConfigDto pageConfigDto : pageConfigDtos) {

			// 获取页面下的标签
			String[] tabClasses = pageConfigDto.getTabClasses().split(",");

			List<PageTabConfigDto> tempTabList = new ArrayList<PageTabConfigDto>();
			for (String tabClass : tabClasses) {

				// 表单标签
				for (PageTabConfigDto pageTabConfigDto : pageTabConfigDtos) {
					if (tabClass.equals(pageTabConfigDto.getTabClass())) {

						// 获取标签下面的区域
						String[] regionClasses = pageTabConfigDto.getRegionClasses().split(",");

						List<PageTabRegionConfigDto> tempRegionList = new ArrayList<PageTabRegionConfigDto>();

						for (String regionClass : regionClasses) {

							// 表单标签区域
							for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabRegionConfigDtos) {

								if (pageTabRegionConfigDto.getRegionClass().equals(regionClass)) {
									// 获取区域下面的表单
									String[] formClasses = pageTabRegionConfigDto.getFormClasses().split(",");

									List<List<PageTabRegionFormConfigDto>> tempValueList = new ArrayList<List<PageTabRegionFormConfigDto>>();

									for (String formClass : formClasses) {
										// 表单集合
										List<PageTabRegionFormConfigDto> tempFormList = new ArrayList<PageTabRegionFormConfigDto>();
										// 表单标签区域表单
										for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionFormConfigDtos) {

											if (pageTabRegionFormConfigDto.getFormClass().equals(formClass)) {

												// 显示隐藏参数转换Map
												if (StringUtils
														.isNotEmpty(pageTabRegionFormConfigDto.getParamsValues())) {
													try {
														pageTabRegionFormConfigDto
														.setParamsValuesJosn(JSONObject.fromObject(
																pageTabRegionFormConfigDto.getParamsValues()));
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

	public void initSelectConfig() {
		List<PageConfigSelectValuesDto> pageConfigSelectValuesDtos = selectPageConfigSelectValuesDtoList();
		for (PageConfigSelectValuesDto pageConfigSelectValuesDto : pageConfigSelectValuesDtos) {
			Map<String, Object> configMap = new HashMap<String, Object>();
			if (pageConfigSelectValuesDto.getType() == 1) {
				List<DictDto> listDictDtos = dataApi
						.getDictDtoListByType(pageConfigSelectValuesDto.getSelectTypeClass());
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				List<Map<String, String>> listName = new ArrayList<Map<String, String>>();
				Map<String, String> tempMap = new HashMap<String, String>();
				Map<String, String> tempMapName = new HashMap<String, String>();
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
				configMap.put("list", list);
				configMap.put("list_name", listName);
			} else {
				configMap.put("url", pageConfigSelectValuesDto.getUrl());
			}
			RedisOperator.set("page_config_" + pageConfigSelectValuesDto.getSelectTypeClass(), configMap);
		}
	}

	public List<PageConfigDto> selectPageConfigDtoList() {
		return pageConfigMapper.selectPageConfigDtoList();
	}

	public List<PageConfigSelectValuesDto> selectPageConfigSelectValuesDtoList() {
		return pageConfigMapper.selectPageConfigSelectValuesDtoList();
	}

	public List<PageTabConfigDto> selectPageTabConfigDtoList() {
		return pageConfigMapper.selectPageTabConfigDtoList();
	}

	public List<PageTabRegionConfigDto> selectPageTabRegionConfigDtoList() {
		return pageConfigMapper.selectPageTabRegionConfigDtoList();
	}

	public List<PageTabRegionFormConfigDto> selectPageTabRegionFormConfigDtoList() {
		return pageConfigMapper.selectPageTabRegionFormConfigDtoList();
	}

	@Override
	public List<PageBusinfoTypeDto> selectPageBusinfoConfig(PageBusinfoTypeDto pageBusinfoTypeDto) {
		return pageConfigMapper.selectPageBusinfoConfig(pageBusinfoTypeDto);
	}

}
