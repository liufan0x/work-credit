package com.anjbo.controller.impl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.RiskAuditVo;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IRiskAuditController;
import com.anjbo.controller.ReportBaseController;
import com.anjbo.service.RiskAuditService;

/**
 * 订单风控统计
 * @author yis
 *
 */
@RestController
public class RiskAuditController extends ReportBaseController implements IRiskAuditController{

	@Resource RiskAuditService riskAuditService;

	private final String AUDIT_FIRST = "auditFirst";

	private final String AUDIT_TYPE = "auditType";


	@Override
	public RespPageData<Map<String, Object>> queryRiskAuditRate(@RequestBody  Map<String, Object> paramMap) {
		RespPageData respPageData =RespPageData.getInstance();
		try{
			if(MapUtils.isEmpty(paramMap)||org.springframework.util.StringUtils.isEmpty(paramMap.get(AUDIT_TYPE))){
				paramMap.put(AUDIT_TYPE,AUDIT_FIRST);
			}
			List<RiskAuditVo> riskAuditList = riskAuditService.queryRiskAuditRateByAuditId(paramMap);
			if(CollectionUtils.isEmpty(riskAuditList)) {
				riskAuditList = new ArrayList<RiskAuditVo>(){{
					add(new RiskAuditVo(MapUtils.getString(paramMap, "cityName"),0,0,0,0));
				}};
			}
			respPageData.getRespPageData(riskAuditList.size(),riskAuditList,RespStatusEnum.SUCCESS.getMsg(),RespStatusEnum.SUCCESS.getCode());
		}catch (Exception e){
			respPageData.getRespPageData(RespStatusEnum.FAIL.getMsg(),RespStatusEnum.FAIL.getCode());
			logger.error("查询风控退回率统计失败!!!,",e);
		}
		return respPageData;
	}

	@Override
	public   RespPageData<Map<String, Object>> query(@RequestBody Map<String,Object> paramMap){
		RespPageData<Map<String, Object>> resp = new RespPageData<>();
		try {
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			int firstCount=0;
			int finalCount=0;
			int officerCount=0;
			int auditCount=0;
			int backCount=0;
			int closeCount=0;
			String cityCode=paramMap.get("cityCode").toString();
			String productCode=paramMap.get("productCode").toString();
			if("".equals(cityCode))
				cityCode=null;
			if("".equals(productCode))
				productCode=null;
			//查询参数
			Map<String, Object> pareamt=new HashMap<String, Object>();
			pareamt.put("cityCode", cityCode);
			pareamt.put("productCode",productCode);
			pareamt.put("startTime", paramMap.get("startTime"));
			pareamt.put("endTime", paramMap.get("endTime"));
			String startTime=paramMap.get("startTime")+"";
			String endTime=paramMap.get("endTime")+"";
			if((startTime==null || "null".equals(startTime) || "".equals(startTime))&& (endTime==null || "null".equals(endTime) || "".equals(endTime))){
				pareamt.put("createTime", paramMap.get("createTime").toString());
				pareamt.put("startTime", null);
				pareamt.put("endTime", null);
			}else{
				pareamt.put("createTime", null);
			}
			String cityCodeList ="";
			if(cityCode!=null && cityCode!=""){  //条件查询
				List<DictDto> dictlist=riskAuditService.findByCity();
				String name="";
				for(DictDto city:dictlist){
					if(city.getCode().equals(cityCode)){
						name=city.getName();
						break;
					}
				}
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("cityName", name);
				int cityFirst=riskAuditService.findByFistCount(pareamt);//初审总订单
				map.put("firstCount", cityFirst);
				firstCount+=cityFirst;
				int cityFinal=riskAuditService.findByFinalCount(pareamt); //终审总订单
				map.put("finalCount", cityFinal);
				finalCount+=cityFinal;
				int cityOfficer=riskAuditService.findByOfficerCount(pareamt); //首席总订单
				map.put("officerCount", cityOfficer);
				officerCount+=cityOfficer;
				List<String> cityAudit=riskAuditService.findByAuditCount(pareamt); //审批通过
				map.put("auditCount", cityAudit.size());
				auditCount+=cityAudit.size();
				List<String> cityBack=riskAuditService.findByBackCount(pareamt); //退回
				map.put("backCount", cityBack.size());
				backCount+=cityBack.size();
				List<String> cityClose=riskAuditService.findByCloseCount(pareamt); //否决
				map.put("closeCount", cityClose.size());
				closeCount+=cityClose.size();
				list.add(map);
				
			}else{  //查询所有
				List<DictDto> dictlist=riskAuditService.findByCity();
				for(DictDto city:dictlist){
					cityCodeList+=city.getCode()+",";
				}
				pareamt.put("cityCodeList", cityCodeList.substring(0, cityCodeList.length()-1));
				List<Map<String, Object>> cityFirst=riskAuditService.findByFistCountAll(pareamt);//初审总订单
				
				List<Map<String, Object>> cityFinal=riskAuditService.findByFinalCountAll(pareamt);//终审总订单
				
				List<Map<String, Object>> cityOfficer=riskAuditService.findByOfficerCountAll(pareamt);
				
				List<Map<String, Object>> cityAudit=riskAuditService.findByAuditCountAll(pareamt);
				
				List<Map<String, Object>> cityBack=riskAuditService.findByBackCountAll(pareamt);
				
				List<Map<String, Object>> cityClose=riskAuditService.findByCloseCountAll(pareamt);
				for (int i = 0; i < dictlist.size(); i++) {
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("cityName", dictlist.get(i).getName());
					map.put("firstCount", "0");
					map.put("finalCount", "0");
					map.put("officerCount", "0");
					map.put("auditCount", "0");
					map.put("backCount", "0");
					map.put("closeCount", "0");
					for (int j = 0; j < cityFirst.size(); j++) {
						String fCode=cityFirst.get(j).get("cityCode")+"";
						if(dictlist.get(i).getCode().equals(fCode)){
							if(!"null".equals(cityFirst.get(j).get("count")+"")) {
								map.put("firstCount", cityFirst.get(j).get("count"));
								firstCount+=Integer.parseInt(cityFirst.get(j).get("count")+"");
							}
							break;
						}
					}
					for (int j = 0; j < cityFinal.size(); j++) {
						String fCode=cityFinal.get(j).get("cityCode")+"";
						if(dictlist.get(i).getCode().equals(fCode)){
							if(!"null".equals(cityFinal.get(j).get("count")+"")) {
								map.put("finalCount", cityFinal.get(j).get("count"));
								finalCount+=Integer.parseInt(cityFinal.get(j).get("count")+"");
							}
							break;
						}
					}
					for (int j = 0; j < cityOfficer.size(); j++) {
						String fCode=cityOfficer.get(j).get("cityCode")+"";
						if(dictlist.get(i).getCode().equals(fCode)){
							if(!"null".equals(cityOfficer.get(j).get("count")+"")) {
								map.put("officerCount", cityOfficer.get(j).get("count"));
								officerCount+=Integer.parseInt(cityOfficer.get(j).get("count")+"");
							}
							break;
						}
					}	
					for (int j = 0; j < cityAudit.size(); j++) {
						String fCode=cityAudit.get(j).get("cityCode")+"";
						if(dictlist.get(i).getCode().equals(fCode)){
							if(!"null".equals(cityAudit.get(j).get("count")+"")) {
								map.put("auditCount", cityAudit.get(j).get("count"));
								auditCount+=Integer.parseInt(cityAudit.get(j).get("count")+"");
							}
							break;
						}
					}
					for (int j = 0; j < cityBack.size(); j++) {
						String fCode=cityBack.get(j).get("cityCode")+"";
						if(dictlist.get(i).getCode().equals(fCode)){
							if(!"null".equals(cityBack.get(j).get("count")+"")) {
								map.put("backCount", cityBack.get(j).get("count"));
								backCount+=Integer.parseInt(cityBack.get(j).get("count")+"");
							}
							break;
						}
					}
					for (int j = 0; j < cityClose.size(); j++) {
						String fCode=cityClose.get(j).get("cityCode")+"";
						if(dictlist.get(i).getCode().equals(fCode)){
							if(!"null".equals(cityClose.get(j).get("count")+"")) {
								map.put("closeCount", cityClose.get(j).get("count"));
								closeCount+=Integer.parseInt(cityClose.get(j).get("count")+"");
							}
							break;
						}
					}
					list.add(map);
				}
			}
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("cityName", "总计");
			map.put("firstCount", firstCount);
			map.put("finalCount", finalCount);
			map.put("officerCount", officerCount);
			map.put("auditCount", auditCount);
			map.put("backCount", backCount);
			map.put("closeCount", closeCount);
			List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
			list2.add(map);
			list2.addAll(list);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(list2);
			resp.setTotal(list.size());
		} catch (Exception e){
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			logger.error("订单风控统计异常:",e);
		}
		return resp;
	}
	
	
}
