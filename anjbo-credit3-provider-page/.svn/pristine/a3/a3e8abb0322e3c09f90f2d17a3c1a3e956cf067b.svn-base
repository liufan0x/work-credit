/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.AgencyDto;
import com.anjbo.bean.AgencyIncomeModeDto;
import com.anjbo.bean.AgencyProductDto;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.config.PageConfigDto;
import com.anjbo.bean.config.PageTabConfigDto;
import com.anjbo.bean.data.PageDataDto;
import com.anjbo.bean.data.PageFlowDto;
import com.anjbo.bean.data.PageListDto;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.dao.PageDataMapper;
import com.anjbo.service.PageConfigService;
import com.anjbo.service.PageDataService;
import com.anjbo.service.PageFlowService;
import com.anjbo.service.PageListService;
import com.anjbo.utils.DateUtils;

import net.sf.json.JSONObject;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-06-28 20:20:05
 * @version 1.0
 */
@Service
public class PageDataServiceImpl extends BaseServiceImpl<PageDataDto> implements PageDataService {
	@Autowired
	private PageDataMapper pageDataMapper;

	@Resource
	private PageListService pageListService;

	@Resource
	private PageConfigService pageConfigService;
	
	@Resource 
	private PageFlowService pageFlowService;

	@Resource
	private UserApi userApi;
	
	@Resource
	private DataApi dataApi;
	
	@SuppressWarnings("unchecked")
	@Override
	public void savePageTabConfigDto(PageDataDto pageDataDto, PageConfigDto pageConfigDto) {
		Map<String, Object> dataMap = pageDataDto.getDataMap();
		for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
			if ((StringUtils.isNotEmpty(pageDataDto.getTblName())
					&& !pageDataDto.getTblName().equals(pageTabConfigDto.getTblName()))
					|| pageTabConfigDto.getTitle().contains("影像资料")) {
				continue;
			}
			Map<String, Object> tblMap = MapUtils.getMap(dataMap, pageTabConfigDto.getTblName());
			pageDataDto.setOtherData(pageConfigService.setOtherData(pageTabConfigDto, tblMap));
			pageDataDto.setTblName(pageTabConfigDto.getTblName());
			pageDataDto.setDataMap(tblMap);
			insert(pageDataDto);
		}
	}

	@Override
	public void submitPageConfigDto(PageDataDto pageDataDto) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("productCode", pageDataDto.getProductCode());
		params.put("cityCode", pageDataDto.getCityCode());
		params.put("processId", pageDataDto.getProcessId());
		ProcessDto processDto = dataApi.getNextProcess(params);
		
		String currentHandlerUid = "123456";
		String currentHandlerName = userApi.findUserDtoByUid(currentHandlerUid).getName();
		String previousHandler = userApi.findUserDtoByUid(pageDataDto.getUpdateUid()).getName();
				
		// 新增流水
		PageFlowDto pageFlowDto = new PageFlowDto();
		pageFlowDto.setUpdateUid(pageDataDto.getUpdateUid());
		pageFlowDto.setHandleUid(pageDataDto.getUpdateUid());
		pageFlowDto.setCurrentProcessId(pageDataDto.getProcessId());
		pageFlowDto.setNextProcessId(processDto.getProcessId());
		pageFlowDto.setOrderNo(pageDataDto.getOrderNo());
		pageFlowService.insert(pageFlowDto);

		// 更新列表状态和处理人
		PageListDto pageListDto = new PageListDto();
		pageListDto.setOrderNo(pageDataDto.getOrderNo());
		pageListDto.setUpdateUid(pageDataDto.getUpdateUid());
		pageListDto.setCityCode(pageDataDto.getCityCode());
		pageListDto.setProductCode(pageDataDto.getProductCode());
		if(processDto.getProcessName().contains("已")) {
			pageListDto.setState(processDto.getProcessName());
		}else {
			pageListDto.setState("待" + processDto.getProcessName());
		}
		pageListDto.setProcessId(processDto.getProcessId());
		pageListDto.setPreviousHandlerUid(pageDataDto.getUpdateUid());
		pageListDto.setPreviousHandler(previousHandler);
		pageListDto.setCurrentHandler(currentHandlerUid);
		pageListDto.setCurrentHandlerUid(currentHandlerUid);
		pageListDto.setCurrentHandler(currentHandlerName);
		pageListDto.setTblDataName(pageDataDto.getTblName()+"list");
		pageListService.update(pageListDto);
		
		special(pageDataDto);
	}

	
	@SuppressWarnings("unchecked")
	private void special(PageDataDto pageDataDto) {
		if("100".equals(pageDataDto.getProductCode())) {
			if(StringUtils.isNotEmpty(pageDataDto.getProcessId()) && pageDataDto.getProcessId().equals("agencyWaitSign")) {
				System.out.println("已签约");
				Map<String, Object> params = new HashMap<String,Object>();
				params.put("orderNo", pageDataDto.getOrderNo());
				params.put("tblName", pageDataDto.getTblName());
				Map<String,Object> agenctListMap =  pageListService.pageListData(params);
				Map<String,Object> agenctDataMap = pageDataDto.getDataMap();
				
				//设置机构值
				String agencyName = MapUtils.getString(agenctListMap,"agencyName");
				AgencyDto agencyDto = new AgencyDto();
				agencyDto.setName(agencyName);
				agencyDto.setSimName(agencyName);
				agencyDto.setChanlMan(MapUtils.getString(agenctListMap,"channelManagerUid",""));
				agencyDto.setContactMan(MapUtils.getString(agenctListMap,"contactsName",""));
				agencyDto.setContactTel(MapUtils.getString(agenctListMap,"contactsPhone",""));
				agencyDto.setExpandChiefUid(MapUtils.getString(agenctListMap,"expandChiefUid",""));
				
				agencyDto.setStatus(1);
				agencyDto.setChargeStandard(MapUtils.getDouble(agenctDataMap,"chargeStandard",null));
				agencyDto.setIsBond(MapUtils.getString(agenctDataMap,"isBond",""));
				agencyDto.setProportionResponsibility(MapUtils.getString(agenctDataMap,"proportionResponsibility",null));
				agencyDto.setBond(MapUtils.getDouble(agenctDataMap,"bond",null));
				agencyDto.setMinBond(MapUtils.getDouble(agenctDataMap,"minBond",null));
				agencyDto.setCreditLimit(MapUtils.getDouble(agenctDataMap,"creditLimit",null));
				agencyDto.setCreateUid(MapUtils.getString(agenctDataMap,"createUid",""));
				agencyDto.setCooperativeModeId(MapUtils.getInteger(agenctDataMap,"cooperativeModeId",0));
				agencyDto.setAgencyType(MapUtils.getString(agenctDataMap,"agencyType",""));
				agencyDto.setSurplusQuotaRemind(MapUtils.getDouble(agenctDataMap,"surplusQuotaRemind",95d));
				agencyDto.setSurplusQuota(MapUtils.getDouble(agenctDataMap,"creditLimit",null));//初始化 剩余额度=授信额度
				agencyDto.setExpandManagerUid(MapUtils.getString(agenctDataMap,"expandManagerUid"));
				agencyDto.setAcceptManagerUid(MapUtils.getString(agenctDataMap,"acceptManagerUid"));
				agencyDto.setRiskBearMultiple(MapUtils.getDouble(agenctDataMap,"riskBearMultiple",null));
				agencyDto.setManageAccount(MapUtils.getString(agenctDataMap,"contactsPhone",null));
				agencyDto.setSignStatus(1);
				if(agenctDataMap.containsKey("applyDate")){
					agencyDto.setApplyDate(DateUtils.StringToDate(MapUtils.getString(agenctDataMap,"applyDate"), DateUtils.FMT_TYPE1));
				}
				String  openCity = "";
				//设置机构产品
				List<AgencyProductDto> agencyProductDtos = new ArrayList<AgencyProductDto>();
				List<Map<String,Object>> list = (List<Map<String,Object>>)MapUtils.getObject(agenctDataMap, "productList");
				List<DictDto> productList = dataApi.getDictDtoListByType("product");
				for (Map<String, Object> map : list) {
					String[] codes = MapUtils.getString(map,"finalApplyProductCode").split(",");
                    for (DictDto d:productList){
                        for (String c:codes){
                            if(d.getCode().equals(c)){
                            	AgencyProductDto productDto = new AgencyProductDto();
                                productDto.setProductCode(d.getCode());
                                productDto.setProductName(d.getName());
                                productDto.setCityCode(MapUtils.getString(map,"finalApplyCity"));
                                productDto.setCityName(MapUtils.getString(map,"finalApplyCityName"));
                                productDto.setAgencyId(agencyDto.getId());
                                productDto.setCreateUid(MapUtils.getString(map,"createUid"));
                                agencyProductDtos.add(productDto);
                                
                                if(StringUtils.isBlank(openCity)){
                                    openCity = productDto.getCityName();
                                } else if(!openCity.contains(productDto.getCityName())){
                                    openCity += ","+productDto.getCityName();
                                }
                            }
                        }
                    }
				}
				agencyDto.setOpenCity(openCity);
				agencyDto.setAgencyProductDtos(agencyProductDtos);
				
				
				//设置费用支付方式
				List<AgencyIncomeModeDto> agencyIncomeModeDtos = new ArrayList<AgencyIncomeModeDto>();
				String incomeMode = MapUtils.getString(agenctDataMap,"incomeMode");
				String incomeModeValue = MapUtils.getString(agenctDataMap,"incomeModeValue");
				String[] in = incomeMode.split(",");
				String[] inv = incomeModeValue.split(",");
				for (int i=0;i<in.length;i++){
					AgencyIncomeModeDto agencyIncomeModeDto = new AgencyIncomeModeDto();
					agencyIncomeModeDto.setIncomeMode(in[i]);
					agencyIncomeModeDto.setName(inv[i]);
					agencyIncomeModeDto.setAgencyId(agencyDto.getId());
					agencyIncomeModeDto.setCreateUid(agencyDto.getCreateUid());
					agencyIncomeModeDtos.add(agencyIncomeModeDto);
				}
				agencyDto.setAgencyIncomeModeDtos(agencyIncomeModeDtos);
				
				
				userApi.insertAgency(agencyDto);
			}
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PageDataDto insert(PageDataDto pageDataDto) {
		pageDataDto.setData(JSONObject.fromObject(pageDataDto.getDataMap()).toString());
		if (find(pageDataDto) != null) {
			super.update(pageDataDto);
		} else {
			super.insert(pageDataDto);
		}
		
		
		//保存其他关联的表
		Map<String, Object> otherData = pageDataDto.getOtherData();
		for (String tblName : otherData.keySet()) {
			if (tblName.contains("_list")) {
				Map<String, Object> map = MapUtils.getMap(otherData, tblName);
				map.put("orderNo", pageDataDto.getOrderNo());
				map.put("tblName", tblName);
				pageListService.insertPageList(map);
			} else {
				PageDataDto page = new PageDataDto();
				page.setTblName(pageDataDto.getTblName());
				page.setData(JSONObject.fromObject(MapUtils.getMap(otherData, tblName)).toString());
				page.setCreateUid(pageDataDto.getCreateUid());
				page.setOrderNo(pageDataDto.getOrderNo());
				if (find(pageDataDto) == null) {
					pageDataMapper.insert(page);
				} else {
					pageDataMapper.update(page);
				}
			}
		}
		return pageDataDto;
	}

}
