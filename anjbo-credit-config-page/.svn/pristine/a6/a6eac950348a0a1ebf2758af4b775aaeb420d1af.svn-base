package com.anjbo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.config.page.PageConfigDto;
import com.anjbo.bean.config.page.PageTabConfigDto;
import com.anjbo.bean.config.page.PageTabRegionConfigDto;
import com.anjbo.bean.config.page.PageTabRegionFormConfigDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.finance.ReportDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.PageConfigService;
import com.anjbo.utils.UidUtil;

@Controller
@RequestMapping("/credit/config/page/pc/base/v")
public class PcPageConfigController extends BaseController{

	private Log log = LogFactory.getLog(getClass());

	@Resource
	private PageConfigService pageConfigService;

	
	/**
	 * 根据产品code获取系统名称
	 * @param params
	 * @return
	 */
	private String setProductSystemName(Map<String, Object> params){
		if("04".equals(MapUtils.getString(params, "productCode",""))){
			return "tbl_fdd";
		}else if("06".equals(MapUtils.getString(params, "productCode",""))) {
			return "tbl_pth";
		}else if("07".equals(MapUtils.getString(params, "productCode",""))) {
			return "tbl_tdl";
		}else{
			return "tbl_sl";
		}
	}
	
	/**
	 * 根据产品code,processId获取pageClass
	 * @param params
	 * @return
	 */
	private String setPageClass(Map<String, Object> params){
		if(StringUtils.isEmpty(MapUtils.getString(params, "orderNo",""))&&"kgAddOrder".equals(MapUtils.getString(params, "processId",""))){
			return "tbl_order_kgAddOrder_page";
		}else if(StringUtils.isEmpty(MapUtils.getString(params, "orderNo",""))&&!"editreport".equals(MapUtils.getString(params, "processId",""))){
			return "tbl_order_addOrder_page";
		}else{
			String pageClass = setProductSystemName(params)+"_"+MapUtils.getString(params, "processId","")+"_page";
			return pageClass;
		}
	}
	
	/**
	 * 获取App页面配置
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageConfig") 
	public RespDataObject<PageConfigDto> pageConfig(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<PageConfigDto> resp = new RespDataObject<PageConfigDto>();
		try {
			//测试代码开始
			String processId = MapUtils.getString(params, "processId");
			Map<String,Object> orderBaseInfoMap = getOrderBaseInfo(MapUtils.getString(params, "orderNo"));
			if(StringUtils.isNotEmpty(processId)){
				orderBaseInfoMap.put("processId", processId);
			}
			params.putAll(orderBaseInfoMap);
			params.put("pageClass", setPageClass(params));
			PageConfigDto pageConfigDto = pageConfigService.pageConfig(params);
			Iterator<PageTabConfigDto> it = pageConfigDto.getPageTabConfigDtos().iterator();
			while (it.hasNext()) {
				PageTabConfigDto pageTabConfigDto = it.next();
				if(pageTabConfigDto.getTitle().equals("1.完善订单") || pageTabConfigDto.getTitle().equals("2.审批人") || pageTabConfigDto.getTitle().equals("城市")  || pageTabConfigDto.getTitle().equals("订单分配员") ){
					it.remove();
				}
				if(pageTabConfigDto.getTitle().equals("询价/查档")){
					pageTabConfigDto.setTitle("询价/查档/诉讼");
				}
				setData(pageTabConfigDto, params);
			}
			RespHelper.setSuccessDataObject(resp, pageConfigDto);
		} catch (Exception e) {
			log.error("获取"+MapUtils.getString(params, "pageClass")+"，App页面配置失败", e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	private void setData(PageTabConfigDto pageTabConfigDto,Map<String, Object> params){
		if(!"default".equals(pageTabConfigDto.getDataUrl())){
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("orderNo", MapUtils.getString(params, "orderNo"));
			Map<String, Object> selectMap = (Map<String, Object>) httpUtil.getRespDataObject(Constants.LINK_CREDIT, pageTabConfigDto.getDataUrl(), tempMap,Map.class).getData();
			if(selectMap !=null && !selectMap.isEmpty()){
				for(PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					if(pageTabRegionConfigDto.getType() == 2){
						//循环区域赋值
						
						if(selectMap.get(pageTabRegionConfigDto.getKey()) == null){
							continue;
						}
						
						List<Map<String, Object>> tempDataList = JSONObject.fromObject(selectMap).getJSONArray(pageTabRegionConfigDto.getKey());
						List<PageTabRegionFormConfigDto> formList = pageTabRegionConfigDto.getValueList().get(0);
						for (Map<String, Object> tempData : tempDataList) {
							List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
							for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
								try {
									tempList.add((PageTabRegionFormConfigDto)BeanUtils.cloneBean(pageTabRegionFormConfigDto));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							setValues(tempList,tempData,params);
							pageTabRegionConfigDto.getValueList().add(tempList);
						}
						pageTabRegionConfigDto.getValueList().remove(0);
					}else{
						for (List<PageTabRegionFormConfigDto>  formList: pageTabRegionConfigDto.getValueList()) {
							setValues(formList, selectMap,params);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 设置表单值
	 * @param formList
	 * @param data
	 */
	private void setValues(List<PageTabRegionFormConfigDto> formList,Map<String, Object> data,Map<String, Object> params){
		//匹配小数
		Pattern p = Pattern.compile("(^[1-9](\\.\\d{0,})?)|(^[0-9]\\.\\d{1,})\\d*$");
		
		for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {

			String key = pageTabRegionFormConfigDto.getKey();
			String specialKey = pageTabRegionFormConfigDto.getSpecialKey();
			
			if(1 == pageTabRegionFormConfigDto.getType()){
				if("cityName".equals(pageTabRegionFormConfigDto.getTypeDepend())){
					pageTabRegionFormConfigDto.setValue(MapUtils.getString(params, "cityName"));
				}
			}else if(2 == pageTabRegionFormConfigDto.getType()){
				if(!(StringUtils.isNotEmpty(MapUtils.getString(data, specialKey,"")) && !"null".equals(MapUtils.getString(data, specialKey,"")))){
					data.put(specialKey, MapUtils.getString(data, key,""));
				}
			}else if(3 == pageTabRegionFormConfigDto.getType()){
				if(pageTabRegionFormConfigDto.getTypeDepend().contains("/credit/user/user/v/searchByType2?name=")){
					pageTabRegionFormConfigDto.setTypeDepend(String.format(pageTabRegionFormConfigDto.getTypeDepend(),MapUtils.getString(params, "cityCode"),MapUtils.getString(params, "productCode")));
				}
				
				if(pageTabRegionFormConfigDto.getTypeDepend().contains("/credit/data/dict/v/searchMap?type=bookingSzAreaOid&pcode=")){
					pageTabRegionFormConfigDto.setTypeDepend(String.format(pageTabRegionFormConfigDto.getTypeDepend(),MapUtils.getString(params, "cityCode")));
				}
			}
			
			
			if(StringUtils.isNotEmpty(MapUtils.getString(data, key,"")) && !"null".equals(MapUtils.getString(data, key,""))){
				try{
					if(data.get(key) instanceof Double) {
						BigDecimal bigDecimal = new BigDecimal(MapUtils.getString(data, key, ""));
						pageTabRegionFormConfigDto.setValue(bigDecimal.toPlainString());
					} else if(p.matcher(MapUtils.getString(data,key,"")).find()){
						BigDecimal bigDecimal = new BigDecimal(MapUtils.getString(data, key, ""));
						pageTabRegionFormConfigDto.setValue(bigDecimal.toPlainString());
					}else{
						pageTabRegionFormConfigDto.setValue(MapUtils.getString(data, key,""));
					}
				}catch(Exception e){
					pageTabRegionFormConfigDto.setValue(MapUtils.getString(data, key,""));
				}
			}
			
			if(StringUtils.isNotEmpty(MapUtils.getString(data, specialKey,"")) && !"null".equals(MapUtils.getString(data, specialKey,"")) ){
				pageTabRegionFormConfigDto.setSpecialValue(MapUtils.getString(data, specialKey,""));
			}
		}
	}
	
	/**
	 * 获取区域表单
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPageTabRegionConfigDto") 
	public RespData<PageTabRegionFormConfigDto> getPageTabRegionConfigDto(@RequestBody Map<String, Object> params){
		RespData<PageTabRegionFormConfigDto> resp = new RespData<PageTabRegionFormConfigDto>();
		try {
			String processId = null;
			if(params.get("processId")!=null){
			    processId=MapUtils.getString(params, "processId");
			}
			params.putAll(getOrderBaseInfo(MapUtils.getString(params, "orderNo")));
			if(processId!=null){
				params.put("processId", processId);
			}
			params.put("pageClass", setPageClass(params));
			List<PageTabRegionFormConfigDto> list = pageConfigService.getPageTabRegionConfigDto(params);
			RespHelper.setSuccessData(resp,list);
		} catch (Exception e) {
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
			log.error("获取"+MapUtils.getString(params, "pageClass")+"-"+MapUtils.getString(params, "regionClass")+"页面区域配置失败", e);
		}
		return resp;
	}

	/**
	 * 保存标签
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/savePageTabConfigDto")
	public RespStatus savePageTabConfigDto(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus(); 
		try {
			String tblName = MapUtils.getString(params, "tblName","");
			Map<String, Object> dataMap = MapUtils.getMap(params, tblName);
			if(dataMap.isEmpty()){
				RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			String saveButUrl = MapUtils.getString(params, "saveButUrl","");
			if(StringUtils.isEmpty(saveButUrl)){
				RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
				return resp;
			}
			String uid = getUserDto(request).getUid();
			dataMap.put("createUid", uid);
			dataMap.put("updateUid", uid);
			dataMap.put("orderNo", MapUtils.getString(params, "orderNo",""));
			resp = httpUtil.getRespStatus(Constants.LINK_CREDIT, saveButUrl, dataMap);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
			log.error("保存"+MapUtils.getString(params, "tblName")+"页面标签数据失败", e);
		}
		return resp;
	}

	/**
	 * 提交页面(即保存页面下所有标签数据)
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submitPageConfigDto")
	public RespStatus submitPageConfigDto(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus(); 
		try {
			log.info("提交params:"+params);
			return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/borrow/v/submitAudit",params);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
			log.error("提交页面数据失败", e);
		}
		return resp;
	}
	
	public ReportDto getReport(OrderListDto orderListDto){
		ReportDto dto = new ReportDto();
		if(StringUtils.isNotBlank(orderListDto.getOrderNo())){
			DocumentsDto doc = new DocumentsDto();
			doc.setOrderNo(orderListDto.getOrderNo());
			doc = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", doc,DocumentsDto.class);
			dto.setCityCode(orderListDto.getCityCode());
			dto.setCityName(orderListDto.getCityName());
			dto.setAcceptMemberName(orderListDto.getAcceptMemberName());
			dto.setAcceptMemberUid(orderListDto.getAcceptMemberUid());
			dto.setProductCode(orderListDto.getProductCode());
			dto.setProductName(orderListDto.getProductName());
			dto.setCooperativeAgencyId(orderListDto.getCooperativeAgencyId());
			dto.setCooperativeAgencyName(orderListDto.getCooperativeAgencyName());
			dto.setChannelManagerName(orderListDto.getChannelManagerName());
			dto.setChannelManagerUid(orderListDto.getChannelManagerUid());
			dto.setCustomerName(orderListDto.getCustomerName());
			dto.setLoanAmount(orderListDto.getBorrowingAmount());
			dto.setBorrowingDays(orderListDto.getBorrowingDay());
			if(null!=doc&&null!=doc.getForeclosureType()){
				dto.setPaymentType(doc.getForeclosureType().getForeclosureType());
			}
		}
		return dto;
	}
	
	  /**
     * 去除字符開頭和末尾逗號
     * @param img
     * @return
     */
	public String cleanImgUrl(String img){
        if(StringUtils.isBlank(img))return img;
        img = img.replaceAll(";",",");
        if(img.startsWith(",")&&img.length()>1){
            img = img.substring(1,img.length());
        }
        if(img.endsWith(",")&&img.length()>1){
            img = img.substring(0,img.length()-1);
        }
        return img;
    }
	
}
