package com.anjbo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.anjbo.bean.element.AuditBaseDto;
import com.anjbo.bean.element.AuditFlowDto;
import com.anjbo.bean.element.vo.AuditInfoVo;
import com.anjbo.bean.element.vo.ElementOrderVo;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.AnjboException;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AuditBaseService;
import com.anjbo.service.AuditConfigWebService;
import com.anjbo.service.ElementFileService;
import com.anjbo.util.DingtalkUtil;
import com.anjbo.utils.CommonDataUtil;
/**
 * 钉钉审批H5控制器[Controller]
 * @ClassName: DingtalkController
 * @Description: 
 * @date 2017-12-20 18:04:58
 * @version V3.0
*/
@Controller
@RequestMapping("/credit/element/dingtalk")
public class DingtalkController extends BaseController{

	
//	@ExceptionHandler
//	public void handle(Exception e){
//		e.printStackTrace();
//	}
	
	private Log log = LogFactory.getLog(DingtalkController.class);
	
	@Resource 
	private AuditBaseService auditBaseService;
	@Resource
	private ElementFileService elementFileService;
	@Resource
	private AuditConfigWebService auditConfigWebService;
	
	/**
	 * 
	 * @Title: orderDetail 
	 * @Description: 订单详情
	 * @param @return    设定文件 
	 * @return RespDataObject<Object>    返回类型 
	 * @throws
	 */
	@RequestMapping("/orderDetail")
	@ResponseBody
	public RespDataObject<Object> orderDetail(String orderNo){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		try {
			Map<String,Object> result = auditBaseService.getOrderInfo(orderNo); 
			return RespHelper.setSuccessDataObject(resp, result);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
	}
	/**
	 * 
	 * @Title: applayPage 
	 * @Description: 借要件审批申请页面 数据
	 * @param @return    设定文件 
	 * @return RespData<Object>    返回类型 
	 * @throws
	 */
	@RequestMapping("/applayPage")
	@ResponseBody
	public RespDataObject<Object> applayPage(String orderNo,HttpServletRequest request,HttpServletResponse response,int auditType,@RequestParam(value="id",required = false,defaultValue="0")int id){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		try{
			request.setAttribute("getUserByMysql", "yes");
			UserDto userDto = getUserDto(request);
			Map<String,Object> resultMap = auditBaseService.getApplayPageInfo(orderNo,userDto,auditType,id);
			return RespHelper.setSuccessDataObject(resp, resultMap);
		}catch(Exception e){
			return setFailResponse(resp, e);
		}
	}

	/**
	 * 
	 * @Title: getCopyUsers 
	 * @Description: 获取抄送人
	 * @param @return    设定文件 
	 * @return RespData<Object>    返回类型 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getCopyUsers")
	@ResponseBody
	public RespDataObject<Object> getCopyUsers(String emlpid, String deptid,HttpServletRequest request){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		try{
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Set<String> userIdSet = new HashSet<String>();
			if(StringUtils.isNotBlank(emlpid)){
				for (String userId : emlpid.split(",")) {
					userIdSet.add(userId);
				}
			}
			if(StringUtils.isNotBlank(deptid)){
				String[] deptids = deptid.split(",");
				for (String dept : deptids) {
					List<Map> listByDeptId = DingtalkUtil.getUserListByDeptId(dept);
					for (Map map : listByDeptId) {
						userIdSet.add(MapUtils.getString(map, "userid"));
					}
				}
			}
			List<UserDto> allUserDtoList = getAllUserDtoList();
			for (UserDto userDto : allUserDtoList) {
				if(userIdSet.contains(userDto.getDingtalkUid())){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("uid", userDto.getUid());
					map.put("name", userDto.getName());
					list.add(map);
				}
			}
			return RespHelper.setSuccessDataObject(resp, list);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
	/**
	 * 
	 * @Title: addAudit 
	 * @Description: 新增审批 
	 * @param @return    设定文件 
	 * @return RespStatus    返回类型 
	 * @throws
	 */
	@RequestMapping("/addAudit")
	@ResponseBody
	public RespStatus addAudit(AuditBaseDto paramDto,String auditorUidListStr,String copyToUidListStr,HttpServletRequest request){
		RespStatus resp = new RespStatus ();
		try{
			String orderNo = paramDto.getOrderNo();//订单号
			Integer type = paramDto.getType();//审批类型
			String customerName = paramDto.getCustomerName();//客户姓名
			String riskElementIds = paramDto.getRiskElement();//风控要件id集合
			String receivableElementIds = paramDto.getReceivableElement();//回款要件id集合
			String publicSealIds = paramDto.getPublicSeal();//公章id集合
			String sealDepartment = paramDto.getSealDepartment();//公章所属部门
			Date beginTime = paramDto.getBeginTime();//借用开始时间
			Date endTime = paramDto.getEndTime();//借用结束时间
			String reason = paramDto.getReason();//借用原因
			if(reason.length()>500) {
				RespHelper.setFailRespStatus(resp, "借用原因不能超过500个字");
				return resp;
			}
			List<Integer> riskElementIdList = null;
			List<Integer> receivableElementIdList = null;
			List<Integer> publicSealIdList = null;
			if(StringUtils.isNotBlank(riskElementIds)){
				riskElementIdList = JSONArray.parseArray(riskElementIds,Integer.class);
			}
			if(StringUtils.isNotBlank(receivableElementIds)){
				receivableElementIdList = JSONArray.parseArray(receivableElementIds,Integer.class);
			}
			if(StringUtils.isNotBlank(publicSealIds)){
				publicSealIdList = JSONArray.parseArray(publicSealIds,Integer.class);
			}
			StringBuilder elementIds = new StringBuilder(60);
			if(riskElementIdList!=null){
				for (Integer id : riskElementIdList) {
					elementIds.append(id).append(",");//多个要件id以逗号隔开
				}
			}
			if(receivableElementIdList!=null){
				for (Integer id : receivableElementIdList) {
					elementIds.append(id).append(",");
				}
			}
			if(publicSealIdList!=null){
				for (Integer id : publicSealIdList) {
					elementIds.append(id).append(",");
				}
			}
			if(elementIds.length()==0){
				return RespHelper.setFailRespStatus(resp, "请选择要借用的要件!");
			}else{
				elementIds.deleteCharAt(elementIds.length()-1);
			}
			List<String> copyToUidList = JSONArray.parseArray(copyToUidListStr, String.class);
			List<String> auditorUidList = JSONArray.parseArray(auditorUidListStr, String.class);
			UserDto firstAuditor = CommonDataUtil.getUserDtoByUidAndMobile(auditorUidList.get(0));
			UserDto userDto = getUserDto(request);
			AuditBaseDto auditDto = new AuditBaseDto();
			auditDto.setOrderNo(orderNo);
			auditDto.setType(type);
			auditDto.setReason(reason);
			auditDto.setElementIds(elementIds.toString());
			auditDto.setBeginTime(beginTime);
			auditDto.setEndTime(endTime);
			auditDto.setState(0);//待审批
			auditDto.setElementOperation(3);
			auditDto.setCopyTo(StringUtils.join(copyToUidList, ","));
			auditDto.setCreateTime(new Date());
			auditDto.setTitle(getAuditTitle(type,userDto.getName()));//审批标题
			auditDto.setCreateUid(userDto.getUid());
			auditDto.setApplierName(userDto.getName());
			auditDto.setCurrentAuditName(firstAuditor.getName());
			auditDto.setCurrentAuditUid(firstAuditor.getUid());
			auditDto.setCustomerName(customerName);
			auditDto.setSealDepartment(sealDepartment);
			auditDto.setFileToSeal(paramDto.getFileToSeal());
			auditDto.setSealFileCount(paramDto.getSealFileCount());
			auditDto.setFileType(paramDto.getFileType());
			auditDto.setFileImgUrl(paramDto.getFileImgUrl());
			//修改时设置id
			auditDto.setId(paramDto.getId());
			Integer id = paramDto.getId()!=null?paramDto.getId():0;
			if(id>0) {//修改判断是否已经审批通过
				Map<String,Object> dto = auditBaseService.selectAuditLevelFrist(id);
				if(dto==null) {
					return RespHelper.setFailRespStatus(resp, "该申请已进行审批，暂不支持修改！");
				}
			}
			fillBorrowItemDescrib(auditDto,riskElementIdList,receivableElementIdList,publicSealIdList);
			auditBaseService.addAudit(auditDto,auditorUidList,id);
			return RespHelper.setSuccessRespStatus(resp);
		}catch(Exception e){
			return setFailResponse(resp, e);
		}
	}
	
	/**
	 * 延长借用时间
	 * @param paramDto
	 * @param auditorUidListStr
	 * @param copyToUidListStr
	 * @param request
	 * @return
	 */
	@RequestMapping("/extendBorrowingTime")
	@ResponseBody
	public RespStatus extendBorrowingTime(AuditBaseDto param,String auditorUidListStr,String copyToUidListStr,HttpServletRequest request) {
		//再借一次
		RespStatus resp = new RespStatus ();
		try{
			String reason = param.getReason();//延长原因
			int dbId = param.getId();//审批id
			Date endTime = param.getEndTime();
			AuditBaseDto paramDto = auditBaseService.selectAuditBaseDtoById(dbId);
			Integer type = paramDto.getType();//审批类型
			String customerName = paramDto.getCustomerName();//客户姓名
			String sealDepartment = paramDto.getSealDepartment();//公章所属部门
			if(reason.length()>500) {
				RespHelper.setFailRespStatus(resp, "延长借用原因不能超过500个字");
				return resp;
			}
			List<String> auditorUidList = new ArrayList<String>();
			String[] auditorUidListStrArr = auditorUidListStr.split(",");
			for(int i=0 ;i<auditorUidListStrArr.length;i++) {
				auditorUidList.add(auditorUidListStrArr[i]);
			}
			UserDto firstAuditor = CommonDataUtil.getUserDtoByUidAndMobile(auditorUidList.get(0));
			UserDto userDto = getUserDto(request);
			String elementIds = paramDto.getElementIds();
			AuditBaseDto auditDto = new AuditBaseDto();
			auditDto = paramDto;
			auditDto.setId(null);
			auditDto.setEndTime(endTime);
			auditDto.setReason(reason);
			auditDto.setState(0);//审批中
			auditDto.setElementOperation(3);
			auditDto.setCopyTo(copyToUidListStr);
			auditDto.setCreateTime(new Date());
			auditDto.setTitle(getExtendAuditTitle(type,paramDto.getApplierName()));//审批标题
			auditDto.setCreateUid(userDto.getUid());
			auditDto.setApplierName(paramDto.getApplierName());
			auditDto.setCurrentAuditName(firstAuditor.getName());
			auditDto.setCurrentAuditUid(firstAuditor.getUid());
			auditDto.setCustomerName(customerName);
			auditDto.setSealDepartment(sealDepartment);
			auditDto.setFileToSeal(paramDto.getFileToSeal());
			auditDto.setSealFileCount(paramDto.getSealFileCount());
			auditDto.setFileType(paramDto.getFileType());
			auditDto.setFileImgUrl(paramDto.getFileImgUrl());
			//auditDto.setId(dbId);//审批id
			auditDto.setExtendId(dbId);
			//fillBorrowItemDescrib(auditDto,riskElementIdList,receivableElementIdList,publicSealIdList);
			auditBaseService.addAudit(auditDto,auditorUidList,0);
			//auditBaseService.extendAudit(auditDto,auditorUidList);
			return RespHelper.setSuccessRespStatus(resp);
		}catch(Exception e){
			e.printStackTrace();
			return setFailResponse(resp, e);
		}
	
	}
	
	/**
	 * 撤销借要件申请
	 * @param request
	 * @param params
	 * dbId 审批信息表id
	 * @return
	 */
	@RequestMapping("/cancelBorrowAudit")
	@ResponseBody
	public RespStatus cancelBorrowAudit(HttpServletRequest request,@RequestBody Map<String,Object> params) {
		RespStatus resp =new RespStatus();
		try {
			int id = MapUtils.getIntValue(params, "dbId");
			if(id>0) {//修改判断是否已经审批通过
				AuditBaseDto  dto = auditBaseService.selectAuditBaseDtoById(id);
				if(dto.getState()>0) {
					return RespHelper.setFailRespStatus(resp, "该申请已进行审批，暂不支持撤销！");
				}
			}
			int i = auditBaseService.cancelBorrowAudit(params);
			if(i==-1) {
				RespHelper.setFailRespStatus(resp,"审批已通过，暂不支持撤销！");
			}else {
				RespHelper.setSuccessRespStatus(resp);
			}
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, "撤销失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 修改借要件申请
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping("/editBorrowAudit")
	@ResponseBody
	public RespStatus editBorrowAudit(AuditBaseDto paramDto,String auditorUidListStr,String copyToUidListStr,HttpServletRequest request) {
		RespStatus resp =new RespStatus();
		try {
			String orderNo = paramDto.getOrderNo();//订单号
			Integer type = paramDto.getType();//审批类型
			String customerName = paramDto.getCustomerName();//客户姓名
			String riskElementIds = paramDto.getRiskElement();//风控要件id集合
			String receivableElementIds = paramDto.getReceivableElement();//回款要件id集合
			String publicSealIds = paramDto.getPublicSeal();//公章id集合
			String sealDepartment = paramDto.getSealDepartment();//公章所属部门
			Date beginTime = paramDto.getBeginTime();//借用开始时间
			Date endTime = paramDto.getEndTime();//借用结束时间
			String reason = paramDto.getReason();//借用原因
			List<Integer> riskElementIdList = null;
			List<Integer> receivableElementIdList = null;
			List<Integer> publicSealIdList = null;
			if(StringUtils.isNotBlank(riskElementIds)){
				riskElementIdList = JSONArray.parseArray(riskElementIds,Integer.class);
			}
			if(StringUtils.isNotBlank(receivableElementIds)){
				receivableElementIdList = JSONArray.parseArray(receivableElementIds,Integer.class);
			}
			if(StringUtils.isNotBlank(publicSealIds)){
				publicSealIdList = JSONArray.parseArray(publicSealIds,Integer.class);
			}
			StringBuilder elementIds = new StringBuilder(60);
			if(riskElementIdList!=null){
				for (Integer id : riskElementIdList) {
					elementIds.append(id).append(",");//多个要件id以逗号隔开
				}
			}
			if(receivableElementIdList!=null){
				for (Integer id : receivableElementIdList) {
					elementIds.append(id).append(",");
				}
			}
			if(publicSealIdList!=null){
				for (Integer id : publicSealIdList) {
					elementIds.append(id).append(",");
				}
			}
			if(elementIds.length()==0){
				return RespHelper.setFailRespStatus(resp, "请选择要借用的要件!");
			}else{
				elementIds.deleteCharAt(elementIds.length()-1);
			}
			List<String> copyToUidList = JSONArray.parseArray(copyToUidListStr, String.class);
			List<String> auditorUidList = JSONArray.parseArray(auditorUidListStr, String.class);
			UserDto firstAuditor = CommonDataUtil.getUserDtoByUidAndMobile(auditorUidList.get(0));
			UserDto userDto = getUserDto(request);
			AuditBaseDto auditDto = new AuditBaseDto();
			auditDto.setOrderNo(orderNo);
			auditDto.setType(type);
			auditDto.setReason(reason);
			auditDto.setElementIds(elementIds.toString());
			auditDto.setBeginTime(beginTime);
			auditDto.setEndTime(endTime);
			auditDto.setState(0);//待审批
			auditDto.setElementOperation(3);
			auditDto.setCopyTo(StringUtils.join(copyToUidList, ","));
			auditDto.setCreateTime(new Date());
			auditDto.setTitle(getAuditTitle(type,userDto.getName()));//审批标题
			auditDto.setCreateUid(userDto.getUid());
			auditDto.setApplierName(userDto.getName());
			auditDto.setCurrentAuditName(firstAuditor.getName());
			auditDto.setCurrentAuditUid(firstAuditor.getUid());
			auditDto.setCustomerName(customerName);
			auditDto.setSealDepartment(sealDepartment);
			auditDto.setFileToSeal(paramDto.getFileToSeal());
			auditDto.setSealFileCount(paramDto.getSealFileCount());
			auditDto.setFileType(paramDto.getFileType());
			auditDto.setFileImgUrl(paramDto.getFileImgUrl());
			fillBorrowItemDescrib(auditDto,riskElementIdList,receivableElementIdList,publicSealIdList);
			auditBaseService.editBorrowAudit(auditDto,auditorUidList);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, "修改失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 
	 * @Title: getAuditTitle 
	 * @Description: 拼接审批标题
	 * @param @param type
	 * @param @param name
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	private String getAuditTitle(int type,String name){
		switch (type) {
			case 1:return name+"的借要件审批";
			case 2:return name+"的借要件审批";
			case 3:return name+"的借公章审批";
			case 4:return name+"的借要件审批";
			default:return "";
		}
	}
	
	private String getExtendAuditTitle(int type,String name) {
		switch (type) {
		case 1:return name+"的延长借用时间审批";
		case 2:return name+"的延长借用时间审批";
		case 3:return name+"的延长借用时间审批";
		case 4:return name+"的延长借用时间审批";
		default:return "";
	}
	}
	/**
	 * 
	 * @Title: fillBorrowItemDescrib 
	 * @Description: 设置借用的物品描述信息 
	 * @param @param auditDto
	 * @param @param elementIds    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void fillBorrowItemDescrib(AuditBaseDto auditDto,List<Integer> riskEleIdList,List<Integer> recivEleIdList,List<Integer> sealIdList) {
		List<Integer> list = new ArrayList<Integer>();
		if(auditDto.getType()==3){//借公章
			auditDto.setPublicSeal(auditBaseService.buildElementsDescrib(sealIdList,3));
			list.addAll(sealIdList);
		}else{
			if(riskEleIdList!=null){
				auditDto.setRiskElement(auditBaseService.buildElementsDescrib(riskEleIdList,2));
				list.addAll(riskEleIdList);
			}
			if(recivEleIdList!=null){
				auditDto.setReceivableElement(auditBaseService.buildElementsDescrib(recivEleIdList,1));
				list.addAll(recivEleIdList);
			}
		}
		//如果是修改，先恢复要件状态，后更新为借用审批中
		if(auditDto.getId()!=null&&auditDto.getId()>0) {
			AuditBaseDto audit = auditBaseService.selectAuditBaseDtoById(auditDto.getId());
			String elementIds = audit.getElementIds();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("ids", elementIds);
			param.put("status", 3);
			elementFileService.updateStatusByIds(param);
		}
		//更新借用状态为借用审批中
		List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
		for (Integer t : list) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", t);
			map.put("status", 5);//借用审批中
			tList.add(map);
		}
		elementFileService.updateElementFile(tList);
	}
	
	/**
	 * 
	 * @Title: fillBorrowItemDescrib 
	 * @Description:   延长借用时间
	 * @param @param auditDto
	 * @param @param elementIds    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void extendAudit(AuditBaseDto auditDto,String[] ids) {
		//更新借用状态为借用审批中
		List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
		for (String t : ids) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", t);
			map.put("status", 5);//借用审批中
			tList.add(map);
		}
		elementFileService.updateElementFile(tList);
	}
	
	/**
	 * 
	 * @Title: list
	 * @Description: 订单列表列表
	 * @param  设定文件 
	 * @return RespData<Object>    返回类型 
	 * @throws
	 */
	@RequestMapping("/orderList")
	@ResponseBody
	public RespDataObject<List<ElementOrderVo>> orderList(String keyword,Integer orderStatus,Integer type ,Integer start,Integer pageSize,HttpServletRequest request,HttpServletResponse response){
		RespDataObject<List<ElementOrderVo>> resp = new RespDataObject<List<ElementOrderVo>>();
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			request.setAttribute("getUserByMysql", "yes");
			UserDto currentUser = getUserDto(request);
			boolean borrowAuth = true;//是否有借的权限
//			if(currentUser.getAuthIds().contains("59")||currentUser.getAuthIds().contains("60")){
//				borrowAuth = true;
//			}
			params.put("uid", currentUser.getUid());
			params.put("start", start==null?0:start);
			params.put("pageSize", pageSize);
			params.put("orderStatus", orderStatus);
			params.put("keyword", keyword);
			params.put("type", type);
			List<ElementOrderVo> list = null;
			if(type==null){
				type = 1;
			}
			if(type==1){//要件
				List<Map<String,Object>> listType = auditConfigWebService.selectAllCityType();
				Map<String,Object> mapType = new HashMap<String,Object>();
				for (Map<String, Object> map : listType) {
					if(MapUtils.getIntValue(map, "type")==1) {
						Map<String,Object> mapT = MapUtils.getMap(mapType, MapUtils.getString(map, "city"),new HashMap<String,Object>());
						mapT.put("auditType", MapUtils.getString(mapT, "auditType","").equals("")?"1":MapUtils.getString(mapT, "auditType","")+",1");
						mapType.put(MapUtils.getString(map, "city"),mapT);
					}
					if(MapUtils.getIntValue(map, "type")==4) {
						Map<String,Object> mapT = MapUtils.getMap(mapType, MapUtils.getString(map, "city"),new HashMap<String,Object>());
						mapT.put("auditType", MapUtils.getString(mapT, "auditType","").equals("")?"4":MapUtils.getString(mapT, "auditType","")+",4");
						mapType.put(MapUtils.getString(map, "city"),mapT);
					}
				}
				list = auditBaseService.selectOrderList(params,currentUser);
				if(!borrowAuth){
					for (ElementOrderVo vo : list) {
						vo.setBorrowButton(-1);//借按钮不显示
						vo.setTypeMap(MapUtils.getMap(mapType, vo.getCityName()));
					}
				}else {
					for (ElementOrderVo vo : list) {
						vo.setTypeMap(MapUtils.getMap(mapType, vo.getCityName()));
					}
				}
			}else{//公章
				params.put("orderType", "3");
				list = auditBaseService.selectOrderList(params,currentUser);
				for (ElementOrderVo vo : list) {
					if(!borrowAuth){
						vo.setBorrowButton(-1);//借按钮不显示
					}
					String elementSet = vo.getCurrentBoxElementSet();
					if(StringUtils.isNotBlank(elementSet)){
						String[] eleIds = elementSet.split(",");
						List<Integer> idList = new ArrayList<Integer>();
						for(int i=0;i<eleIds.length;i++){
							if(StringUtils.isNotBlank(eleIds[i])){
								idList.add(Integer.valueOf(eleIds[i]));
							}
						}
						String describ = auditBaseService.buildElementsDescrib(idList,3);
						if(describ!=null){
							vo.setSealDescrib(describ.replaceAll("/", "、"));
						}
					}
				}
			}
			return RespHelper.setSuccessDataObject(resp, list);
		}catch(Exception e){
			return setFailResponse(resp, e);
		}
	}
	/**
	 * 
	 * @Title: deliverAudit 
	 * @Description: 转交审批 
	 * @param @param auditId
	 * @param @param toUid
	 * @param @param request
	 * @param @return    设定文件 
	 * @return RespStatus    返回类型 
	 * @throws
	 */
	@RequestMapping("/deliverAudit")
	@ResponseBody
	public RespStatus deliverAudit(Integer auditId,String toUid,String remark,HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			if(auditId==null||StringUtils.isBlank(toUid)){
				return RespHelper.setFailRespStatus(resp, "参数错误!");
			}
			UserDto currentUser = getUserDto(request);
			UserDto toUser = CommonDataUtil.getUserDtoByUidAndMobile(toUid);
			auditBaseService.deliverAudit(currentUser.getUid(),toUser,auditId,remark);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
	}
	/**
	 * 
	 * @Title: audit 
	 * @Description: 审批操作
	 * @param @return    设定文件 
	 * @return RespStatus    返回类型 
	 * @throws
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public RespStatus audit(Integer auditId,Boolean pass,String remark,HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			if(auditId==null||pass==null){
				return RespHelper.setFailRespStatus(resp, "参数错误!");
			}
			if(pass){//审批通过时,要借用的要件/公章未被取走
				int eleState = auditBaseService.checkEleState(auditId);
				if(eleState==1){
					return RespHelper.setFailRespStatus(resp, "要件或公章已被取走!");
				}else if(eleState==2){
					return RespHelper.setFailRespStatus(resp, "该订单已结束!");
				}
			}
			params.put("id", auditId);
			params.put("pass", pass);
			params.put("remark", remark);
			int state = pass?2:3;
			UserDto userDto = getUserDto(request);
			params.put("uid", userDto.getUid());
			log.info("查询状态参数"+params);
			AuditFlowDto flowDto = auditBaseService.selectFlowInauditByUidAndAuditId(params);
			if(flowDto==null||flowDto.getState()==0){
				return RespHelper.setFailRespStatus(resp, "状态异常!");
			}
			if(flowDto.getState()!=1){
				return RespHelper.setFailRespStatus(resp, "审批已经处理,请不要重复操作!");
			}
			String uid = null;
			if(flowDto.isHasNext()){
				uid = auditBaseService.selectAuditUidByAuditIdAndLevel(auditId,flowDto.getAuditLevel()+1);
			}
			flowDto.setRemark(remark);
			flowDto.setState(state);
			auditBaseService.audit(flowDto,flowDto.getAuditBase(),uid!=null?CommonDataUtil.getUserDtoByUidAndMobile(uid):null);
			return RespHelper.setSuccessRespStatus(resp);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
	}
	/**
	 * 
	 * @Title: checkAuditEleState 
	 * @Description: 检查审批的要件/公章状态(是否还在箱子里)
	 * @param @return    设定文件 
	 * @return RespStatus    返回类型 
	 * @throws
	 */
	@RequestMapping("/checkAuditEleState")
	@ResponseBody
	public RespDataObject<Object> checkAuditEleState(Integer auditId,HttpServletRequest request){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		try{
			if(auditId==null){
				return RespHelper.setFailDataObject(resp, null, "参数错误!");
			}
			
			return RespHelper.setSuccessDataObject(resp, auditBaseService.checkEleState(auditId));
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
	}
	/**
	 * 
	 * @Title: borrowRecord
	 * @Description: 借要件/借公章记录
	 * @param  设定文件 
	 * @return RespData<Object>    返回类型 
	 * @throws
	 */
	@RequestMapping("/borrowRecord")
	@ResponseBody
	public RespDataObject<Object> borrowRecord(Integer type,Integer start,Integer pageSize,HttpServletRequest request,HttpServletResponse response){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		try{
			//start,pageSize不传时查全部
			Map<String,Object> params = new HashMap<String,Object>();
			if(type==null){
				type=1;//type==1:要件;type!=1:公章
			}
			params.put("type", type);
			params.put("start", start);
			params.put("pageSize", pageSize);
			params.put("uid",getUserDto(request).getUid());
			List<AuditInfoVo> records= auditBaseService.selectBorrowRecord(params);
			return RespHelper.setSuccessDataObject(resp, records);
		}catch(Exception e){
			return setFailResponse(resp, e);
		}
	}
	/**
	 * 
	 * @Title: auditDetail 
	 * @Description: 审批详情页
	 * @param @param params
	 * @param @param request
	 * @param @return    设定文件 
	 * @return RespDataObject<Map<String,Object>>    返回类型 
	 * @throws
	 */
	@RequestMapping("/auditDetail")
	@ResponseBody
	public RespDataObject<Map<String,Object>> auditDetail(Integer msgid,String orderNo,Integer id,HttpServletRequest request,HttpServletResponse response){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			UserDto userDto = getUserDto(request);
			if(id==null){
				log.info("==============================orderNo:"+orderNo);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("orderNo", orderNo);
				map.put("uid", userDto.getUid());
				id = auditBaseService.selectAuditIdByOrderNo(map);//当前登录人为审批人,查出登录人审批中的一条审批id
			}
			Map<String,Object> resultMap = auditBaseService.selectDetail(id,msgid,getUserDto(request));
			resultMap.put("id", id);
			return RespHelper.setSuccessDataObject(resp, resultMap);
		} catch (Exception e) {
			return setFailResponse(resp, e);
		}
	}
	
	/**
	 * 
	 * @Title: getUserDtoByUserId 
	 * @Description: 根据钉钉的用户标识获取用户对象
	 * @param @return    设定文件 
	 * @return UserDto    返回类型 
	 * @throws
	 */
	private UserDto getUserDtoByUserId(String userid){
		if(StringUtils.isNotBlank(userid)){
			List<UserDto> userDtoList = getAllUserDtoList();
			for (UserDto userDto : userDtoList) {
				if(userid.equals(userDto.getDingtalkUid())){
					return userDto;
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @Title: getUserDto 
	 * @Description: 获取当前用户对象
	 * @param @param request
	 * @param @return    设定文件 
	 * @return UserDto    返回类型 
	 * @throws
	 */
	@Override
	public UserDto getUserDto(HttpServletRequest request) {
		String userid = request.getParameter("userid");//钉钉用户标识
		log.info("钉钉用户Uid:"+userid);
		UserDto userDto = null;
		if(StringUtils.isNotBlank(userid)){
			userDto = getUserDtoByUserId(userid);//钉钉用户
		}else{
			try {
				request.setAttribute("uid", request.getParameter("uid"));
				request.setAttribute("deviceId", request.getParameter("deviceId"));
				log.info("用户uid:"+request.getParameter("uid"));
				userDto = super.getUserDtoByMysql(request);//app用户
				log.info("用户对象:"+userDto);
				userDto.setUid(request.getParameter("uid"));
			} catch (Exception e) {
				e.printStackTrace();
				throw new UnsupportedOperationException("找不到匹配的用户!");
			}
		}
		if(userDto==null||StringUtils.isBlank(userDto.getUid())){
			log.info("userDto:"+userDto);
			throw new UnsupportedOperationException("找不到匹配的用户!");
		}
		if("yes".equals(request.getAttribute("getUserByMysql"))){//从MySQL获取用户数据
			Map<String,Object> map = new HashMap<String, Object>();
			log.info("用户id"+userDto.getUid());
			map.put("uid", userDto.getUid());
			UserDto mysqlUser = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/user/base/v/get4Auth", map, UserDto.class);
			if(mysqlUser!=null&&mysqlUser.getUid()!=null){
				return mysqlUser;
			}
		}
		return userDto;
	}
	/**
	 * 
	 * @Title: setFailResponse 
	 * @Description: 设置响应失败信息
	 * @param @param resp
	 * @param @param e
	 * @param @return    设定文件 
	 * @return RespDataObject<Object>    返回类型 
	 * @throws
	 */
	private <T extends RespStatus>T setFailResponse(T  resp, Exception e) {
		if(e instanceof UnsupportedOperationException){
			resp.setCode("INVALID_USER");
			resp.setMsg(e.getMessage());
			log.info(e);
			return resp;
		}else if(e instanceof AnjboException){
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(((AnjboException) e).getMsg());
			log.info(e);
			return resp;
		}
		log.error(e.getMessage(),e);
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		return resp;
	}
	/**
	 * 
	 * @Title: initData 
	 * @Description: 日期格式绑定
	 * @param @param wdb    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@InitBinder
	public void initData(WebDataBinder wdb){
	    wdb.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
	}
}
