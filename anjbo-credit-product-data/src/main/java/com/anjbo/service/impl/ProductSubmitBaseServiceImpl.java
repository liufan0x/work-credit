package com.anjbo.service.impl;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.controller.ProductSubmitBaseController;
import com.anjbo.dao.ProductDataBaseMapper;
import com.anjbo.dao.ProductFlowBaseMapper;
import com.anjbo.dao.ProductListBaseMapper;
import com.anjbo.service.ProductSubmitBaseService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.SpringContextUtil;
@Transactional
@Service
public class ProductSubmitBaseServiceImpl implements ProductSubmitBaseService {
	
	Logger log = Logger.getLogger(ProductSubmitBaseServiceImpl.class);

	@Resource
	private ProductListBaseMapper productListBaseMapper;
	@Resource
	private ProductFlowBaseMapper productFlowBaseMapper;
	@Resource
	private ProductSubmitBaseController productSubmitBaseController;
	@Resource
	private ProductDataBaseMapper productDataBaseMapper;
	
	@Override
	@Transactional
	public RespStatus submit(Map<String, Object> map){
		RespStatus respStatus = new RespStatus(); 
		try {
			String tblName = MapUtils.getString(map, "tblName");
			String packageClassMethodName= MapUtils.getString(map, "packageClassMethodName");
			String packageClass = packageClassMethodName.substring(0, packageClassMethodName.lastIndexOf("."));
			String methodStr = packageClassMethodName.substring(packageClassMethodName.lastIndexOf(".")+1,packageClassMethodName.length());
			String serviceName = packageClass.substring(packageClass.lastIndexOf(".")+1, packageClass.length());
			serviceName = toLowerCaseFirstOne(serviceName);
			Class cls = Class.forName(packageClass);
			Method method = cls.getDeclaredMethod(methodStr, Map.class);
			Object obj = SpringContextUtil.getBean(serviceName);
			Object result = method.invoke(obj, map);
			if(result instanceof Integer){//关闭订单，方法里已经处理业务了
				if((Integer)result>0){
					RespHelper.setSuccessRespStatus(respStatus);
					return respStatus;
				}
			}
			RespDataObject<Map<String,Object>>  resp = (RespDataObject<Map<String, Object>>) result;
			if(resp.getCode().equals("SUCCESS")){
				Map<String, Object> retusnMap=resp.getData();
				if(retusnMap.containsKey("agencyId")){
					map.put("agencyId", retusnMap.get("agencyId"));
				}
				if(retusnMap.containsKey("accountUid")){
					map.put("accountUid", retusnMap.get("accountUid"));
				}
				if(retusnMap.containsKey("expandChiefUid")){
					map.put("expandChiefUid",MapUtils.getString(retusnMap,"expandChiefUid"));
				}
				map.put("currentProcessId", retusnMap.get("currentProcessId"));
				map.put("nextProcessId", retusnMap.get("nextProcessId"));
				map.put("handleUid", map.get("updateUid"));
				map.put("createUid", map.get("createUid"));
				//查询下个节点更新列表
				String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
				map.put("tblDataName", tblDataName);
				map.put("state", retusnMap.get("state"));
				map.put("processId", retusnMap.get("nextProcessId"));
				map.put("previousHandleTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				String currentHandlerUid = retusnMap.get("currentHandlerUid")!=null?MapUtils.getString(retusnMap, "currentHandlerUid"):map.get("updateUid").toString();
				UserDto user = CommonDataUtil.getUserDtoByUidAndMobile(currentHandlerUid);
				if(tblName.contains("tbl_sm")) {
					map.put("previousHandlerUid",MapUtils.getString(retusnMap,"previousHandlerUid"));
					map.put("previousHandler",MapUtils.getString(retusnMap,"previousHandler"));
				} else {
					map.put("previousHandlerUid", map.get("updateUid"));
				}
				map.put("currentHandlerUid", currentHandlerUid);
				map.put("previousHandler", map.get("userName"));
				map.put("currentHandler", user.getName());
				if(null!=retusnMap.get("appShowValue1")){
					map.put("appShowValue1", MapUtils.getString(retusnMap, "appShowValue1"));
				}
				if(null!=retusnMap.get("appShowValue2")){
					map.put("appShowValue2", MapUtils.getString(retusnMap, "appShowValue2"));
				}
				if(null!=retusnMap.get("appShowValue3")){
					map.put("appShowValue3", MapUtils.getString(retusnMap, "appShowValue3"));
				}
				productListBaseMapper.updateProductListBase(map);
				//查询下个节点录入流水
				tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_flow";
				map.put("tblDataName", tblDataName);
				productFlowBaseMapper.insertProductFlowBase(map);
				//流程完结
				if("agencySignSuccess".equals(retusnMap.get("nextProcessId"))){
					map.put("currentProcessId",retusnMap.get("nextProcessId"));
					map.put("nextProcessId","-");
					productFlowBaseMapper.insertProductFlowBase(map);
				}
				RespHelper.setSuccessRespStatus(respStatus);
			}else{
				log.info("返回："+resp.getMsg()); 
				if("审核不通过".equals(resp.getMsg())){
					RespHelper.setSuccessRespStatus(respStatus);
				} else {
					RespHelper.setFailRespStatus(respStatus, resp.getMsg());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return respStatus;
	}

	@Override
	@Transactional
	public void check(Map<String, Object> map) {

	}
	
	public static void main(String[] args) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("ownerName", "ownerName");
			map.put("ownerCertificateType", "ownerCertificateType");
			map.put("ownerCertificateNo", "ownerCertificateNo");
			map.put("estateNo", "1234567");
			map.put("custManagerMobile", "custManagerMobile");
			map.put("estateType", "estateType");
			map.put("yearNo", "yearNo");
			map.put("actPrice", "actPrice");
			map.put("remark", "remark");
			Class cls = Class.forName("com.anjbo.service.cm.impl.AssessServiceImpl");
			Method method = cls.getDeclaredMethod("addAssess", Map.class);
			Object obj = method.invoke(cls.newInstance(), map);
			RespDataObject<Map<String,Object>>  resp = (RespDataObject<Map<String, Object>>) obj;
			System.out.println(resp.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//首字母转小写
	public String toLowerCaseFirstOne(String s){
	  if(Character.isLowerCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	@Override
	public void submitFailDelete(Map<String, Object> map) {
		Map<String, Object> de = new HashMap<String, Object>();
		String tblName = MapUtils.getString(map, "tblName");
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_data";
        de.put("orderNo", MapUtils.getString(map, "orderNo"));
        de.put("tblDataName", tblDataName);
        productListBaseMapper.delete(de);
        tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
        de.put("tblDataName", tblDataName);
        productDataBaseMapper.delete(de);
	}

}
