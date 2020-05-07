package com.anjbo.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.anjbo.bean.config.page.PageConfigDto;
import com.anjbo.bean.config.page.PageTabConfigDto;
import com.anjbo.bean.config.page.PageTabRegionConfigDto;
import com.anjbo.bean.config.page.PageTabRegionFormConfigDto;
import com.anjbo.bean.element.AuditBaseDto;
import com.anjbo.bean.element.AuditFlowDto;
import com.anjbo.bean.element.vo.AuditInfoVo;
import com.anjbo.bean.element.vo.ElementOrderVo;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.AnjboException;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.dao.AuditBaseMapper;
import com.anjbo.dao.AuditFlowMapper;
import com.anjbo.dao.BorrowElementMapper;
import com.anjbo.dao.ElementFileMapper;
import com.anjbo.dao.ElementMapper;
import com.anjbo.dao.ElementSystemMessageMapper;
import com.anjbo.service.AuditBaseService;
import com.anjbo.service.ElementSystemMessageService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;

/**
  * 审批信息表 [Service实现类]
  * @ClassName: ElementAuditBaseServiceImpl
  * @Description: 审批信息表业务服务
  * @author 
  * @date 2017-12-20 18:04:58
  * @version V3.0
 */
@Service
public class AuditBaseServiceImpl  implements AuditBaseService{
	
	private HttpUtil httpUtil = new HttpUtil();
	@Resource
	private AuditBaseMapper auditBaseMapper;
	@Resource
	private AuditFlowMapper auditFlowMapper;
	@Resource
	private ElementSystemMessageService systemMessageService;
	@Resource
	private ElementSystemMessageMapper elementSystemMessageMapper;
	@Resource
	private ElementFileMapper elementFileMapper;
	@Resource
	private ElementMapper elementMapper;
	@Resource
	private BorrowElementMapper borrowElementMapper;
	//单选框表单配置归类:回款,风控,公章
	private static final String[] RADIO_FROM_CLASS = new String[]{"eleFilePayRadio","eleFileRiskRadio","eleFileSealRadio"};
	@SuppressWarnings("serial")
	private static Map<Integer,String> KEYMAP = new HashMap<Integer, String>(){{put(1,"receivableElementIds");put(2,"riskElementIds");put(3,"publicSealIds");}};
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addAudit(AuditBaseDto auditDto, List<String> auditorUidList,int dbId) throws AnjboException {
		addAuditRecord(auditDto);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("time", new Date());
		params.put("orderNo", auditDto.getOrderNo());
		auditBaseMapper.updateTimeOfElementList(params);
		int count = auditorUidList.size();
		if(dbId>0) {//修改删除审批流水
			auditFlowMapper.deleteByDbId(dbId);
		}
		for(int i=1;i<=count;i++){//审批流程准备工作
			AuditFlowDto dto = new AuditFlowDto();
			dto.setDbId(auditDto.getId());
			dto.setAuditLevel(i);
			dto.setOrderNo(auditDto.getOrderNo());
			dto.setAuditorUid(auditorUidList.get(i-1));
			UserDto auditor = CommonDataUtil.getUserDtoByUidAndMobile(dto.getAuditorUid());
			if(auditor==null){
				throw new AnjboException("UNKNOWN_USERUID","用户uid不存在!");
			}
			dto.setAuditorName(auditor.getName());
			dto.setState(i==1?1:0);
			dto.setHasNext(i!=count);
			auditFlowMapper.addElementAuditFlow(dto);
			if(i==1){//给第一个审批人添加消息
				dto.setAuditBase(auditDto);
				systemMessageService.addToAuditMsg(dto, auditor);
			}
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void extendAudit(AuditBaseDto auditDto, List<String> auditorUidList) throws AnjboException {
		extendAuditRecord(auditDto);
		String orderNo = auditDto.getOrderNo();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("time", new Date());
		params.put("orderNo", orderNo);
		auditBaseMapper.updateTimeOfElementList(params);
		int count = auditorUidList.size();
		for(int i=1;i<=count;i++){//审批流程准备工作
			AuditFlowDto dto = new AuditFlowDto();
			dto.setDbId(auditDto.getId());
			dto.setAuditLevel(i);
			dto.setOrderNo(orderNo);
			dto.setAuditorUid(auditorUidList.get(i-1));
			UserDto auditor = CommonDataUtil.getUserDtoByUidAndMobile(dto.getAuditorUid());
			if(auditor==null){
				throw new AnjboException("UNKNOWN_USERUID","用户uid不存在!");
			}
			dto.setAuditorName(auditor.getName());
			dto.setState(i==1?1:0);
			dto.setHasNext(i!=count);
			dto.setIsExtend(2);
			auditFlowMapper.addElementAuditFlow(dto);
			if(i==1){//给第一个审批人添加消息
				dto.setAuditBase(auditDto);
				systemMessageService.addToAuditMsg(dto, auditor);
			}
		}
	}
	/**
	 * 撤销审批申请
	 */
	@Override
	public int cancelBorrowAudit(Map<String, Object> params) {
		int dbId = MapUtils.getIntValue(params, "dbId");
		//是否有审批通过的记录，审批通过后不可撤销
		AuditFlowDto audit =new AuditFlowDto();
		audit.setDbId(dbId);
		audit.setState(2);
		List<AuditFlowDto> list = auditFlowMapper.selectAuditFlow(audit);
		if(list!=null && list.size()> 0) {
			return -1;
		}
		//申请借用的elementId
		AuditBaseDto auditBaseDto = auditBaseMapper.selectElementAuditBaseById(dbId);
		String elementIds = auditBaseDto.getElementIds();
		String[] ids = elementIds.split(",");
		//更新借用状态为已存入
		List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
		for (String t : ids) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", Integer.parseInt(t));
			map.put("status", 3);//已存入
			tList.add(map);
		}
		elementFileMapper.updateElementFile(tList);
		//更新状态为已存入
		//删除审批申请信息
		//auditBaseMapper.deleteElementAuditBaseById(dbId);
		//更新审批申请信息为已撤销
		AuditBaseDto auditBase = new AuditBaseDto();
		auditBase.setId(dbId);
		auditBase.setState(3);
		auditBaseMapper.updateElementAuditBase(auditBase);
		//删除审批流水表
		auditFlowMapper.deleteByDbId(dbId);
		//删除系统消息
		elementSystemMessageMapper.deleteByDbId(dbId);
		return 1;
	}
	
	/**
	 * 修改审批申请
	 */
	@Override
	public int editBorrowAudit(AuditBaseDto auditDto, List<String> auditorUidList) throws AnjboException{
		//更新审批信息表
		auditBaseMapper.updateElementAuditBase(auditDto);
		//删除审批流水
		auditFlowMapper.deleteByDbId(auditDto.getId());
		//录入审批流水
		int count = auditorUidList.size();
		for(int i=1;i<=count;i++){//审批流程准备工作
			AuditFlowDto dto = new AuditFlowDto();
			dto.setDbId(auditDto.getId());
			dto.setAuditLevel(i);
			dto.setOrderNo(auditDto.getOrderNo());
			dto.setAuditorUid(auditorUidList.get(i-1));
			UserDto auditor = CommonDataUtil.getUserDtoByUidAndMobile(dto.getAuditorUid());
			if(auditor==null){
				throw new AnjboException("UNKNOWN_USERUID","用户uid不存在!");
			}
			dto.setAuditorName(auditor.getName());
			dto.setState(i==1?1:0);
			dto.setHasNext(i!=count);
			auditFlowMapper.addElementAuditFlow(dto);
			if(i==1){//给第一个审批人添加消息
				dto.setAuditBase(auditDto);
				systemMessageService.addToAuditMsg(dto, auditor);
			}
		}
		return 1;
	}
	/**
	 * @throws AnjboException 
	 * 
	 * @Title: addAuditRecord 
	 * @Description: 防止同一个物品同一时间多次提交审批 /通过同步确保审批中的借要件申请不重叠
	 * @param @param auditDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private synchronized void addAuditRecord(AuditBaseDto auditDto) throws AnjboException{
		String ids = auditDto.getElementIds();
		AuditBaseDto old = null ;
		if(auditDto.getId()!=null&&auditDto.getId()>0) {
			old = auditBaseMapper.selectElementAuditBaseById(auditDto.getId());
		}
		Set<String> eleSetInAudit = new HashSet<String>();
		List<String> eleIdsList = auditBaseMapper.selectElesInAuditByOrderNo(auditDto.getOrderNo());
		if(eleIdsList!=null&&eleIdsList.size()>0){
			String eleIdsInAudit = StringUtils.join(eleIdsList,",");
			eleSetInAudit.addAll(Arrays.asList(eleIdsInAudit.split(",")));
		}
		if(auditDto.getExtendId()>0) {//延长
			
		}else if(eleSetInAudit.size()>0){
			for (String id : ids.split(",")) {
				//修改上次借用的不用判断
				if(old!=null&&old.getElementIds().contains(id)) {
					continue;
				}
				if(eleSetInAudit.contains(id)){
					throw new AnjboException("", "您要借用的部分或全部物品已被他人借用,如仍需借用,请尝试重新提交审批!");
				}
			}
		}
		//修改或者录入
		if(auditDto.getId()!=null&&auditDto.getId()>0) {
			System.out.println("修改审批信息======================");
			auditBaseMapper.updateElementAuditBase(auditDto);
		}else {
			auditBaseMapper.addElementAuditBase(auditDto);
		}
	}
	
	/**
	 * @throws AnjboException 
	 * 
	 * @Title: addAuditRecord 
	 * @Description: 防止同一个物品同一时间多次提交审批 /通过同步确保审批中的借要件申请不重叠
	 * @param @param auditDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private synchronized void extendAuditRecord(AuditBaseDto auditDto) throws AnjboException{
		auditBaseMapper.updateElementAuditBase(auditDto);
	}
	
	@Override
	public AuditFlowDto selectFlowInauditByUidAndAuditId(Map<String, Object> params) {
		return auditFlowMapper.selectFlowInauditByUidAndAuditId(params);
	}
	@Transactional
	@Override
	public void audit(AuditFlowDto flowDto,AuditBaseDto baseDto,UserDto next) {
		AuditFlowDto dto = new AuditFlowDto();
		dto.setId(flowDto.getId());
		dto.setAuditTime(new Date());
		dto.setState(flowDto.getState());
		dto.setRemark(flowDto.getRemark());
		AuditBaseDto auditBaseDto = auditBaseMapper.selectElementAuditBaseById(flowDto.getDbId());
		if(flowDto.getState()==2){//审批同意
			if(next!=null){//审批未结束
				baseDto.setCurrentAuditName(next.getName());
				baseDto.setCurrentAuditUid(next.getUid());
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("uid", next.getUid());
				params.put("id", flowDto.getDbId());
				params.put("level", flowDto.getAuditLevel()+1);
				auditBaseMapper.updateElementAuditBase(baseDto);
				systemMessageService.updateAuditMsgState(true,baseDto,CommonDataUtil.getUserDtoByUidAndMobile(flowDto.getAuditorUid()));
				AuditFlowDto nextFlow = auditFlowMapper.selectFlowInauditByUidAndAuditId(params);
				nextFlow.setState(1);
				auditFlowMapper.updateElementAuditFlow(nextFlow);
				systemMessageService.addToAuditMsg(nextFlow,next);
			}else{//审批通过并结束
				closeAudit(baseDto,true);
				systemMessageService.updateAuditMsgState(true,baseDto,CommonDataUtil.getUserDtoByUidAndMobile(flowDto.getAuditorUid()));
				//更新要件状态为已借出
				String elementIds = auditBaseDto.getElementIds();
				String[] ids = elementIds.split(",");
				//更新借用状态为借用审批中
				List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
				for (String t : ids) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("id", Integer.parseInt(t));
					map.put("status", 4);//已借出
					tList.add(map);
				}
				elementFileMapper.updateElementFile(tList);
			}
		}else{//审批拒绝并结束
			closeAudit(baseDto,false);
			systemMessageService.updateAuditMsgState(false,baseDto,CommonDataUtil.getUserDtoByUidAndMobile(flowDto.getAuditorUid()));
			//更新要件状态为已存入
			String elementIds = auditBaseDto.getElementIds();
			String[] ids = elementIds.split(",");
			//更新借用状态为已存入
			List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
			for (String t : ids) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", Integer.parseInt(t));
				map.put("status", 3);//已存入
				tList.add(map);
			}
			elementFileMapper.updateElementFile(tList);
		}
		
		auditFlowMapper.updateElementAuditFlow(dto);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("time", new Date());
		params.put("orderNo", baseDto.getOrderNo());
		auditBaseMapper.updateTimeOfElementList(params);
	}
	/**
	 * 
	 * @Title: closeAudit 
	 * @Description: 结束审批 
	 * @param @param baseDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void closeAudit(AuditBaseDto baseDto,boolean pass) {
		if(pass){//通过,抄送消息及申请人添加消息
			//延长审批通过，取消超时未还检查，更新订单状态为已借出
			AuditBaseDto  audit = auditBaseMapper.selectElementAuditBaseById(baseDto.getId());
			if(audit.getExtendId()>0) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("orderNo", audit.getOrderNo());
				map.put("orderStatus", 3);
				elementMapper.UpdateElementStatusByOrderNo(map);
				map.put("dbId", audit.getExtendId());
				List<Map<String,Object>> borowList =borrowElementMapper.selectBorrowElementByDbId(map);
				borrowElementMapper.updateHasMarkOverTime(map);
				//通过录入新的超时未还检查数据
				if(borowList!=null&&borowList.size()>0) {
					for (Map<String, Object> map2 : borowList) {
						map2.put("dbId", audit.getId());
						map2.put("hasSendMsg", 0);
						map2.put("hasMarkOverTime", 0);
						map2.put("status", 0);
						map2.put("endBorrowElementTime", audit.getEndTime());
					}
					borrowElementMapper.insertBorrowElementRecorde(borowList);
				}
			}
			baseDto.setState(1);
			systemMessageService.addAuditResultMsg(baseDto,true);
		}else{//拒绝,申请人添加消息
			baseDto.setState(2);
			systemMessageService.addAuditResultMsg(baseDto,false);
		}
		auditBaseMapper.updateElementAuditBase(baseDto);
	}

	@Override
	public String selectAuditUidByAuditIdAndLevel(int auditId, int level) {
		return auditFlowMapper.selectAuditUidByAuditIdAndLevel(auditId,level);
	}

	@Override
	public Map<String,Object>  selectCandidates(Map<String, Object> params) {
		return auditBaseMapper.selectAuditConfigByCityAndType(MapUtils.getString(params, "city"),MapUtils.getInteger(params, "type"));
	}

	@Override
	public List<AuditInfoVo> selectApplyList(Map<String, Object> params) {
		return auditBaseMapper.selectApplyList(params);
	}

	@Override
	public List<AuditInfoVo> selectToAuditList(Map<String, Object> params) {
		return auditBaseMapper.selectToAuditList(params);
	}

	@Override
	public List<AuditInfoVo> selectAuditedList(Map<String, Object> params) {
		return auditBaseMapper.selectAuditedList(params);
	}

	@Override
	public List<AuditInfoVo> selectCopyList(Map<String, Object> params) {
		return auditBaseMapper.selectCopyList(params);
	}

	@Override
	public List<AuditInfoVo> selectBorrowRecord(Map<String, Object> params) {
		return auditBaseMapper.selectBorrowRecord(params);
	}

	@Override
	public String buildElementsDescrib(List<Integer> idList,int type) {
		if(idList!=null){
			List<String> names = auditBaseMapper.selectElementNames(idList,type);
			if(names.size()>0){
				Set<String> nameSet = new HashSet<String>();
				nameSet.addAll(names);
				StringBuilder sb = new StringBuilder(30);
				for (String name : nameSet) {
					sb.append(name).append("/");
				}
				sb.deleteCharAt(sb.length()-1);
				return sb.toString();
			}
		}
		return null;
	}

	@Override
	public Integer selectToAuditCount(String uid) {
		return auditFlowMapper.selectToAuditCount(uid);
	}

	@Override
	public Integer selectUnreadCopyAuditCount(String uid) {
		return systemMessageService.selectUnreadCopyAuditCount(uid);
	}

	@Override
	public AuditBaseDto selectLastApplayByUid(String uid) {
		return auditBaseMapper.selectLastApplayByUid(uid);
	}
	/**
	 * 要件/公章表单元素组装
	 * @param eleSetExclude 
	 */
	private void putEleList(Map<String, Object> resultMap, String eleIds, Set<String> eleSetExclude,int dbId){
		if(eleSetExclude == null){
			eleSetExclude = Collections.emptySet();
		}
		String[] ids = eleIds.split(",");
		List<Map<String,Object>> eles = new ArrayList<Map<String,Object>>();
		if(ids.length>0){
			eles = auditBaseMapper.selectElementsByIds(ids);
			Map<String, PageTabRegionConfigDto> regionConfigDtosMap = getPageTabRegionConfigDtosMap();
			Map<String, PageTabRegionFormConfigDto> pageTabRegionFormConfigDtosMap = getPageTabRegionFormConfigDtosMap();
			List<Map<String,Object>> riskElements = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> receivableElements = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> seals = new ArrayList<Map<String,Object>>();
			Iterator<Map<String, Object>> iterator = eles.iterator();
			String elementIds="";
			if(dbId!=0) {
				//修改审批时，部分要件需要恢复为已存入才能支持修改
				AuditBaseDto audit = auditBaseMapper.selectElementAuditBaseById(dbId);
				//审批借用的要件
				elementIds=audit.getElementIds();
				resultMap.put("elementIds", audit.getElementIds());
				//是否修改延长审批
				if(audit.getExtendId()>0) {
					resultMap.put("editExtend", 1);
				}
			}
			while (iterator.hasNext()) {
				Map<String,Object> map = iterator.next();
				Map<String,Object> pageTabRegionMap = null;
				Integer type = MapUtils.getInteger(map, "elementType");
				String title = MapUtils.getString(map, "cardType");
				List<Map<String,Object>> valueList = new ArrayList<Map<String,Object>>();
				PageTabRegionConfigDto pageTabRegionConfigDto = regionConfigDtosMap.get(title);
				if(pageTabRegionConfigDto!=null){
					pageTabRegionMap = describe(pageTabRegionConfigDto);
					pageTabRegionMap.put("data", valueList);
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
						Map<String,Object> reginFormMap = describe(pageTabRegionFormConfigDto);
						reginFormMap.put("value", map.get(pageTabRegionFormConfigDto.getKey()));
						reginFormMap.put("isReadOnly",1);
						valueList.add(reginFormMap);
					}
				}else{
					switch (type) {
						case 1: pageTabRegionConfigDto = regionConfigDtosMap.get("其他回款");break;
						case 2: pageTabRegionConfigDto = regionConfigDtosMap.get("其他风控");break;
						case 3: ;
						default: pageTabRegionConfigDto = regionConfigDtosMap.get("其他公章");break;
					}
					pageTabRegionMap = describe(pageTabRegionConfigDto);
					pageTabRegionMap.put("data", valueList);
					pageTabRegionMap.put("title", title);
					PageTabRegionFormConfigDto pageTabRegionFormConfigDto = pageTabRegionFormConfigDtosMap.get(title);
					Map<String,Object> reginFormMap = describe(pageTabRegionFormConfigDto);
					valueList.add(reginFormMap);
				}
				String id = MapUtils.getString(map, "id");
				pageTabRegionMap.put("value", id);
				int status = 0;
				if(elementIds.contains(MapUtils.getString(map, "id"))) {
					status = 3;
				}else {
					status = MapUtils.getIntValue(map, "status");
				}
				pageTabRegionMap.put("status", status);
				if(eleSetExclude.contains(id)){
					pageTabRegionMap.put("flag",true);
				}
				if(Integer.valueOf(1).equals(type)){//回款
					pageTabRegionMap.put("key", KEYMAP.get(1));
					receivableElements.add(pageTabRegionMap);
				}
				if(Integer.valueOf(2).equals(type)){
					pageTabRegionMap.put("key", KEYMAP.get(2));
					riskElements.add(pageTabRegionMap);
				}
				if(Integer.valueOf(3).equals(type)){
					pageTabRegionMap.put("key", KEYMAP.get(3));
					seals.add(pageTabRegionMap);
				}
			}
			resultMap.put("sealElement", seals);
			resultMap.put("riskElement",riskElements);
			resultMap.put("receivableElement",receivableElements);
		}
	}
	@Override
	public AuditBaseDto selectAuditBaseDtoById(Integer id) {
		return auditBaseMapper.selectElementAuditBaseById(id);
	}

	@Override
	public List<Map<String, Object>> selectAuditHistoryById(Integer id,int isExtend) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("dbId", id);
		m.put("isExtend", isExtend);
		List<Map<String, Object>> list = auditFlowMapper.selectAuditHistoryById(m);
		for (Map<String, Object> map : list) {
			String uid = MapUtils.getString(map, "auditorUid");
			Integer state = MapUtils.getInteger(map, "state");
			switch (state) {
				case 1:map.put("stateStr", "审批中");break;
				case 2:map.put("stateStr", "已同意");break;
				case 3:map.put("stateStr", "已拒绝");break;
				case 4:map.put("stateStr", "已转交给"+MapUtils.getString(map, "deliverTo",""));break;
				default:break;
			}
			UserDto user = CommonDataUtil.getUserDtoByUidAndMobile(uid);
			map.put("name", user.getName());
		}
		return list;
	}

	@Override
	public Map<String, Object> selectDetail(Integer id,Integer msgid, UserDto currentUser) throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//审批申请对象
		AuditBaseDto dto = auditBaseMapper.selectElementAuditBaseById(id);
		int extendId = dto.getExtendId();
		String elementIds = dto.getElementIds();
		//查询审批人对象(已审批和审批中)
		List<Map<String, Object>> auditList = new ArrayList<Map<String, Object>>();
		Map<String,Object> applier = new HashMap<String,Object>();
		if(currentUser.getUid().equals(dto.getCreateUid())){
			applier.put("current", true);
		}
		applier.put("name", dto.getApplierName());
		applier.put("stateStr", "发起审批");
		auditList.add(applier);
		List<Map<String, Object>> auditors = selectAuditHistoryById(dto.getId(),1);
		//修改页需要默认前一次审批的审批人
		List<Map<String,Object>> auditorsMapList = new ArrayList<Map<String,Object>>();
		boolean l1= false;
		boolean l2= false;
		boolean l3= false;
		boolean l4= false;
		boolean l5= false;
		for (Map<String, Object> map : auditors) {
			UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(MapUtils.getString(map, "auditorUid"));
			if(userDto!=null&&!l1&&MapUtils.getIntValue(map, "auditLevel")==1){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("uid", userDto.getUid());
				m.put("name",userDto.getName());
				m.put("level",1);
				m.put("describ", "主管");
				auditorsMapList.add(m);
				l1 = true;
			}
			if(userDto!=null&&!l2&&MapUtils.getIntValue(map, "auditLevel")==2){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("uid", userDto.getUid());
				m.put("name",userDto.getName());
				m.put("level",2);
				m.put("describ", "初审");
				auditorsMapList.add(m);
				l2 = true;
			}
			if(userDto!=null&&!l3&&MapUtils.getIntValue(map, "auditLevel")==3){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("uid", userDto.getUid());
				m.put("name",userDto.getName());
				m.put("level",3);
				m.put("describ", "终审");
				auditorsMapList.add(m);
				l3 = true;
			}
			if(userDto!=null&&!l4&&MapUtils.getIntValue(map, "auditLevel")==4){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("uid", userDto.getUid());
				m.put("name",userDto.getName());
				m.put("level",4);
				m.put("describ", "首席");
				auditorsMapList.add(m);
				l4 = true;
			}
			if(userDto!=null&&!l5&&MapUtils.getIntValue(map, "auditLevel")==5){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("uid", userDto.getUid());
				m.put("name",userDto.getName());
				m.put("level",5);
				m.put("describ", "法务");
				auditorsMapList.add(m);
				l5 = true;
			}
		}
		resultMap.put("auditorsList", auditorsMapList);
		
		//延长审批信息
		if(extendId>0) {
			auditors = selectAuditHistoryById(dto.getExtendId(),1);
		}
		for (Map<String, Object> map : auditors) {
			if(currentUser.getUid().equals(map.get("auditorUid"))){
				map.put("current", true);
				Integer state = MapUtils.getInteger(map,"state");
				switch (state) {
					case 1: 
						resultMap.put("auditing", true);
						resultMap.put("myState", "等待我审批");
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("uid", currentUser.getUid());
						params.put("id", id);
						AuditFlowDto flowDto = auditFlowMapper.selectFlowInauditByUidAndAuditId(params);
						resultMap.put("level", flowDto.getAuditLevel());
						resultMap.put("uid", currentUser.getUid());
						break;
					case 2: resultMap.put("myState", "已审批通过");break;
					case 3: resultMap.put("myState", "已拒绝");break;
					case 4: resultMap.put("myState", "已转交给"+MapUtils.getString(map, "deliverTo",""));
					default: break;
				}
				if(map.get("auditing")!=null){
					resultMap.put("myState", "等待我审批");
				}
			}
			auditList.add(map);
		}
		resultMap.put("auditList", auditList);
		
		//延长审批信息
		if(extendId>0) {
			List<Map<String,Object>> extendAuditList=new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> auditorsExtend = selectAuditHistoryById(dto.getId(),1);
			if(auditorsExtend!=null&&auditorsExtend.size()>0) {
				for (Map<String, Object> map : auditorsExtend) {
					if(currentUser.getUid().equals(map.get("auditorUid"))){
						map.put("current", true);
						Integer state = MapUtils.getInteger(map,"state");
						switch (state) {
							case 1: 
								resultMap.put("auditing", true);
								resultMap.put("myState", "等待我审批");
								Map<String,Object> params = new HashMap<String,Object>();
								params.put("uid", currentUser.getUid());
								params.put("id", id);
								AuditFlowDto flowDto = auditFlowMapper.selectFlowInauditByUidAndAuditId(params);
								resultMap.put("level", flowDto.getAuditLevel());
								resultMap.put("uid", currentUser.getUid());
								break;
							case 2: resultMap.put("myState", "已审批通过");break;
							case 3: resultMap.put("myState", "已拒绝");break;
							case 4: resultMap.put("myState", "已转交给"+MapUtils.getString(map, "deliverTo",""));
							default: break;
						}
						if(map.get("auditing")!=null){
							resultMap.put("myState", "等待我审批");
						}
					}
					extendAuditList.add(map);
				}
			}
			resultMap.put("extendAuditList", extendAuditList);
		}
		
		//借要件或借公章明细列表
		Map<String,Object> orderInfo = auditBaseMapper.selectInfoByOrderNo(dto.getOrderNo());
		resultMap.put("city", MapUtils.getString(orderInfo, "cityName"));
		String eleIds = MapUtils.getString(orderInfo, "currentBoxElementSet","");//当前要件箱物品id集
		Set<String> borrowEleSet = new HashSet<String>();
		borrowEleSet.addAll(Arrays.asList(elementIds.split(",")));
		String[] eleIdArr = eleIds.split(",");
		StringBuilder sb = new StringBuilder(elementIds);
		for (String eleId : eleIdArr) {
			if(!borrowEleSet.contains(eleId)){
				sb.append(",").append(eleId);
			}
		}
		putEleList(resultMap, sb.toString(),borrowEleSet,0);
		String copyTo = dto.getCopyTo();
		//默认上一次的抄送人
		String[] copyUids = copyTo.split(",");
		List<Map<String,Object>> copyMapList = new ArrayList<Map<String,Object>>();
		boolean defaultCopy = false;
		for (String uid : copyUids) {
			UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(uid);
			if(userDto!=null){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("uid", userDto.getUid());
				map.put("name",userDto.getName());
				if(!defaultCopy) {
					map.put("defaultCopy",true);
					defaultCopy = true;
				}
				//延长审批信息
				if(extendId>0) {
					map.put("defaultCopy",true);
				}
				copyMapList.add(map);
			}
		}
		resultMap.put("copysList", copyMapList);
		if(dto.getState()==1&&StringUtils.isNotBlank(copyTo)&&copyTo.contains(currentUser.getUid())){//审批通过,消息为抄送
			//判断是否是抄送人,如果是查询抄送人列表并设置当前用户的抄送流水和消息为已读
			systemMessageService.updateCopyMsgState(id, currentUser.getUid());
			/*for (String uid : copyUids) {
				UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(uid);
				if(userDto!=null){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("uid", userDto.getUid());
					map.put("name",userDto.getName());
					copyMapList.add(map);
				}
			}*/
			resultMap.put("copyList", copyMapList);
		}
		
		resultMap.put("applierName", dto.getApplierName());
		if(dto.getType()==3){
			resultMap.put("sealDepartment", dto.getSealDepartment());
		}else{
			resultMap.put("customerName", dto.getCustomerName());
		}
		resultMap.put("beginTime", DateUtil.getDateByFmt(dto.getBeginTime(), "yyyy-MM-dd HH:mm"));
		AuditBaseDto audit = new AuditBaseDto();
		if(extendId>0) {
			audit = auditBaseMapper.selectElementAuditBaseById(extendId);
			resultMap.put("endTime", DateUtil.getDateByFmt(audit.getEndTime(), "yyyy-MM-dd HH:mm"));
			resultMap.put("reason", audit.getReason());
			resultMap.put("extendReason", dto.getReason());
			resultMap.put("newEndTime", DateUtil.getDateByFmt(dto.getEndTime(), "yyyy-MM-dd HH:mm"));
			resultMap.put("borrowDay",audit.getBorrowDay());
			resultMap.put("newBorrowDay", dto.getBorrowDay());
		}else {
			resultMap.put("borrowDay",dto.getBorrowDay());
			resultMap.put("endTime", DateUtil.getDateByFmt(dto.getEndTime(), "yyyy-MM-dd HH:mm"));
			resultMap.put("reason", dto.getReason());
		}
		resultMap.put("title", dto.getTitle());
		resultMap.put("state", dto.getState());
		resultMap.put("type", dto.getType());
		resultMap.put("currentAuditor",dto.getCurrentAuditName());
		resultMap.put("title", dto.getTitle());
		resultMap.put("orderNo", dto.getOrderNo());
		if(dto.getType() == 3){
			resultMap.put("fileToSeal",dto.getFileToSeal());
			resultMap.put("fileType",dto.getFileType());
			resultMap.put("sealFileCount",dto.getSealFileCount());
			resultMap.put("fileImgUrl",dto.getFileImgUrl());
		}
		if(resultMap.get("auditing")==null){
			resultMap.put("auditing", false);
		}
		if(dto.getState()>0){//审批操作已结束,标记消息已读
			if(dto.getCreateUid().equals(currentUser.getUid())){//我发起的审批消息
				msgid = auditBaseMapper.selectApplayMsgIdByAuditId(id);
				systemMessageService.updateMessageHasRead(msgid);
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * @Title: getPageTabConfigDtosMap 
	 * @Description: 获取表单页面标签区域配置对象
	 * @param @return    设定文件 
	 * @return List<PageTabConfigDto>    返回类型 
	 * @throws
	 */
	private Map<String,PageTabRegionConfigDto> getPageTabRegionConfigDtosMap(){
		PageConfigDto pageConfigDto = getPageConfigDto();
		if(pageConfigDto==null||pageConfigDto.getPageTabConfigDtos()==null||pageConfigDto.getPageTabConfigDtos().size()==0){
			return Collections.emptyMap();
		}
		List<PageTabConfigDto> tabConfigDtos = pageConfigDto.getPageTabConfigDtos();
		Map<String,PageTabRegionConfigDto> map = new HashMap<String, PageTabRegionConfigDto>();
		for (PageTabConfigDto pageTabConfigDto : tabConfigDtos) {
			for (PageTabRegionConfigDto tabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
				map.put(tabRegionConfigDto.getTitle(), tabRegionConfigDto);
			}
		}
		return map;
	}
	/**
	 * 
	 * @Title: getPageConfigDto 
	 * @Description: 获取页面配置对象
	 * @param @return    设定文件 
	 * @return PageConfigDto    返回类型 
	 * @throws
	 */
	private PageConfigDto getPageConfigDto(){
		PageConfigDto pageConfigDto = (PageConfigDto)RedisOperator.get("tbl_element_elementFile_page");
		if(pageConfigDto==null){
			new HttpUtil().getData(Constants.LINK_CREDIT, "/credit/config/page/base/v/initPageConfig", new HashMap<String,String>());
			pageConfigDto = (PageConfigDto)RedisOperator.get("tbl_element_elementFile_page");
		}
		return pageConfigDto;
	}
	/**
	 * 
	 * @Title: getPageTabRegionFormConfigDtosMap 
	 * @Description: 根据表单归类获取单选框Map
	 * @param @return    设定文件 
	 * @return Map<String,PageTabRegionFormConfigDto>    返回类型 
	 * @throws
	 */
	private Map<String,PageTabRegionFormConfigDto> getPageTabRegionFormConfigDtosMap(){
		Map<String, PageTabRegionFormConfigDto> map = new HashMap<String, PageTabRegionFormConfigDto>();
		List<String> formClassList = Arrays.asList(RADIO_FROM_CLASS);
		PageConfigDto pageConfigDto = getPageConfigDto();
		List<PageTabConfigDto> tabConfigDtos = pageConfigDto.getPageTabConfigDtos();
		for (PageTabConfigDto pageTabConfigDto : tabConfigDtos) {
			for (PageTabRegionConfigDto tabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
				if (formClassList.contains(tabRegionConfigDto.getRegionClass())) {
					for (PageTabRegionFormConfigDto dto : tabRegionConfigDto.getValueList().get(0)) {
						map.put(dto.getTitle(), dto);
					}
				}
			}
		}
		return map;
	}
	/**
	 * 
	 * @Title: describe 
	 * @Description:javaBean 转Map
	 * @param @param obj
	 * @param @return    设定文件 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	 */
	private Map<String,Object> describe(Object obj) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(obj!=null){
				Class<? extends Object> clz = obj.getClass();
				BeanInfo beanInfo = Introspector.getBeanInfo(clz,Object.class);
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					Method mtd = propertyDescriptor.getReadMethod();
					Object result = mtd.invoke(obj);
					if(result!=null&&!"valueList".equals(propertyDescriptor.getName())){
						map.put(propertyDescriptor.getName(), result);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> getApplayPageInfo(String orderNo, UserDto userDto,/*auditType审批类型*/int auditType,/*审批id*/int id) throws Exception {
		//1根据订单查询订单信息,获取订单对应的要件箱
		Map<String,Object> orderInfo = auditBaseMapper.selectInfoByOrderNo(orderNo);
		String boxNo = MapUtils.getString(orderInfo, "boxNo");//要件箱编号
		String customerName = MapUtils.getString(orderInfo, "customerName");//客户姓名
		String sealDepartment = MapUtils.getString(orderInfo, "sealDepartment");//公章所属部门
		Integer orderType = MapUtils.getInteger(orderInfo, "orderType");//1:信贷要件,2:无关联要件3:公章
		if(StringUtils.isBlank(boxNo)){
			throw new AnjboException("", "该订单没有要件存入件柜中!");
		}
		//如果已分配要件箱,查询箱子里的所有要件
		Map<String,Object> boxInfo = auditBaseMapper.selectBoxInfoByBoxNo(boxNo);
		String city = MapUtils.getString(boxInfo, "city");//柜子所在城市
//		MapUtils.getString(boxInfo, "subsidiary");//柜子所在区域
		String eleIds = MapUtils.getString(orderInfo, "currentBoxElementSet");//存入的物品id集
		Integer eleType = orderType==3?2:1;//当前存入要件的要件类型（1.要件，2.公章）
		//根据箱子的城市查询审批设置,确定层级
		//1借要件普通审批2借要件财务审批3借公章4业务流程审批
		if(orderType==3){
			auditType = 3;
		}else if("会计".equals(userDto.getRoleName())||"出纳".equals(userDto.getRoleName())){
			auditType = 2;
		}
		AuditBaseDto dto = selectLastApplayByUid(userDto.getUid());//上一次提交审批
		Map<String,Object> auditConfig = auditBaseMapper.selectAuditConfigByCityAndType(city,auditType);
		int levelCount = getAuditLevelCount(auditConfig);//审批层级数
		List<String> auditorUids = Collections.emptyList();
		if(dto!=null){
			auditorUids = auditBaseMapper.selectAuditorUids(dto.getId());
		}
		List<Map<String,Object>> auditors = new ArrayList<Map<String,Object>>();
		for(int i=1;i<=levelCount;i++){//设置审批人
			Map<String,Object> map = new HashMap<String,Object>();
			if(auditorUids.size()>=i){
				String auditorConf = MapUtils.getString(auditConfig, "degree"+i);
				if(auditConfig!=null){
					Pattern compile = Pattern.compile("\"uid\":\""+auditorUids.get(i-1)+"\",\"name\":\"([^\"]+)\"}");
					Matcher matcher = compile.matcher(auditorConf);
					if(matcher.find(1)){
						map.put("name", matcher.group(1));
						map.put("uid", auditorUids.get(i-1));
					}
				}
			}
			map.put("level", i);
			map.put("describ", MapUtils.getString(auditConfig, "describ"+i));
			auditors.add(map);
		}
		//查询抄送人
		List<Map<String,Object>> copyToList = new ArrayList<Map<String,Object>>();
		Set<String> copyUidSet = new HashSet<String>();
		String copyListStr = MapUtils.getString(auditConfig, "other");
		if(StringUtils.isNotBlank(copyListStr)){
			List<Map> users  = JSONArray.parseArray(copyListStr, Map.class);
			for (Map map : users) {
				map.put("defaultCopy", true);
				copyToList.add(map);
				copyUidSet.add(MapUtils.getString(map, "uid"));
			}
		}
		if(dto!=null){
			copyListStr = dto.getCopyTo();
			if(StringUtils.isNotBlank(copyListStr)){
				String[] copyUids = copyListStr.split(",");//上一次选择的抄送人uid数组
				for (String uid : copyUids) {
					if(!copyUidSet.contains(uid)){
						UserDto user = CommonDataUtil.getUserDtoByUidAndMobile(uid);
						if(user!=null&&StringUtils.isNotBlank(user.getUid())){
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("uid", user.getUid());
							map.put("name", user.getName());
							copyToList.add(map);
						}
					}
				}
			}
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(orderType==3){
			resultMap.put("sealDepartment",sealDepartment);//公章所属部门
		}else{
			resultMap.put("customerName",customerName);//客户姓名
		}
		if(StringUtils.isNotBlank(eleIds)){
			//查询已申请借用审批的要件
			List<String> eleIdsList = auditBaseMapper.selectElesInAuditByOrderNo(orderNo);
			Set<String> eleSetInAudit = new HashSet<String>();
			if(eleIdsList!=null&&eleIdsList.size()>0){
				String eleIdsInAudit = StringUtils.join(eleIdsList,",");
				eleSetInAudit.addAll(Arrays.asList(eleIdsInAudit.split(",")));
			}
			putEleList(resultMap,eleIds,eleSetInAudit,id);
		}
		if((eleType==2&&resultMap.get("sealElement")==null)||(eleType==1&&(resultMap.get("riskElement")==null&&resultMap.get("receivableElement")==null))){
			throw new AnjboException("", "当前箱子没有要件或公章!");
		}
		resultMap.put("auditors",auditors);
		resultMap.put("copyTo",copyToList);
		resultMap.put("city",city);
		resultMap.put("orderNo",orderNo);
		resultMap.put("type", auditType);//审批类型(1借要件普通审批2借要件财务审批3借公章)
		resultMap.put("applierName", userDto.getName());
		return resultMap;
	}
	/**
	 * 
	 * @Title: getAuditLevelCount 
	 * @Description: 获取审批层级数
	 * @param  auditConfig
	 * @param  设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	private int getAuditLevelCount(Map<String, Object> auditConfig) {
		int count = 1;
		for(;count<=5;){
			if(StringUtils.isNotBlank(MapUtils.getString(auditConfig, "degree"+count))){
				count++;
			}else{
				break;
			}
		}
		return count-1;
	}

	@Override
	public List<ElementOrderVo> selectOrderList(Map<String, Object> params,UserDto userDto) {
		Integer orderType = MapUtils.getInteger(params, "orderType");
		String deptAllUid = "";
		if(orderType!=null&&orderType==3){
			return auditBaseMapper.selectOrderList(params);
		}else{
			if(userDto.getAuthIds()==null){
				deptAllUid = userDto.getUid();
			}else{
				//查看全部订单
				if(userDto.getAuthIds().contains("1")&&userDto.getIsEnable()==0&&1==userDto.getAgencyId()){
					//查看部门订单
				}else if(userDto.getAuthIds().contains("2")&&userDto.getIsEnable()==0&&1==userDto.getAgencyId()){
					RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/v/selectUidsByDeptId", userDto, Map.class);
					deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
					//查看自己订单
				}else{
					deptAllUid = userDto.getUid();
				}
			}
			Map<String,Object> respMap=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectCreditOrderNos", params,Map.class);
			params.put("orderNos", MapUtils.getString(respMap, "orderNos"));
			params.put("updateUid", userDto.getUid());
			params.put("currentHandlerUid", deptAllUid);
			List<ElementOrderVo> list = auditBaseMapper.selectOrderList(params);
			return list;
		}
	}

	@Override
	public Map<String, Object> copyToList(List<UserDto> allUserList) {
		Map<Integer, List<Map<String, String>>> userListMapByDeptId = mapUserListByDeptId(allUserList);
		DeptDto deptDto = new DeptDto(1);
		RespPageData<DeptDto> pageData = new HttpUtil().getRespPageData(Constants.LINK_CREDIT, "credit/user/dept/list", deptDto,DeptDto.class);
		List<DeptDto> deptList = pageData.getRows();//所有部门
		Map<Integer,List<Map<String,Object>>> mapDeptByPid = new HashMap<Integer,List<Map<String,Object>>>();
		for (DeptDto dept : deptList) {
			int pid = dept.getPid();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("deptId", dept.getId()); 
			map.put("deptName", dept.getName());
			
			if(mapDeptByPid.get(pid)==null){
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				list.add(map);
				mapDeptByPid.put(pid,list);
			}else{
				mapDeptByPid.get(pid).add(map);
			}
			map.put("users", userListMapByDeptId.get(dept.getId()));
		}
		Map<String,Object> deptStruct = new HashMap<String,Object>();
		assembleDeptStruct(deptStruct,mapDeptByPid,0);
//		deptStruct.put("users", userListMapByDeptId.get(0));
		return deptStruct;
	}

	/**
	 * 
	 * @Title: mapUserListByDeptId 
	 * @Description: 通过部门id映射用户集合
	 * @param @return    设定文件 
	 * @return Map<Integer,List<Map<String,String>>>    返回类型 
	 * @throws
	 */
	private Map<Integer,List<Map<String,String>>> mapUserListByDeptId(List<UserDto> allUserList){
		Map<Integer,List<Map<String,String>>> map = new HashMap<Integer,List<Map<String,String>>>();
		for (UserDto userDto : allUserList) {
			String deptIdArrayStr = userDto.getDeptIdArray();
			String[] deptIdArray = deptIdArrayStr.split(",");
			for (String deptIdStr : deptIdArray) {
				Integer deptId = Integer.valueOf(deptIdStr);
				if(map.get(deptId)==null){
					List<Map<String,String>> list = new ArrayList<Map<String,String>>();
					Map<String,String> userMap = new HashMap<String,String>();
					userMap.put("uid", userDto.getUid());
					userMap.put("userName", userDto.getName());
					list.add(userMap);
					map.put(deptId, list);
				}else{
					Map<String,String> userMap = new HashMap<String,String>();
					userMap.put("uid", userDto.getUid());
					userMap.put("userName", userDto.getName());
					map.get(deptId).add(userMap);
				}
			}
		}
		return map;
	}
	/**
	 * 
	 * @Title: assembleDeptStruct 
	 * @Description: 组装部门层级结构 
	 * @param @param deptStruct
	 * @param @param mapDeptByPid
	 * @param @param userListMapByDeptId    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void assembleDeptStruct(Map<String, Object> deptStruct,
			Map<Integer, List<Map<String, Object>>> mapDeptByPid,int pid) {
		List<Map<String, Object>> depts = mapDeptByPid.get(pid);
		if(depts==null){
			return;
		}
		deptStruct.put("subdepts", depts);
		for (Map<String, Object> dept : depts) {
			Integer deptId = MapUtils.getInteger(dept, "deptId");
			assembleDeptStruct(dept,mapDeptByPid,deptId);
		}
	}

	@Override
	public List<AuditBaseDto> selectBorrowedOverTimeAudits(int type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("time", type==3?new Date(System.currentTimeMillis()-1000*60*60*24):new Date());
		switch (type) {
		case 1:
			params.put("hasMarkOverTime", 0);
			break;
		case 2:
			params.put("hasSendMsg", 0);
			break;
		case 3:
			params.put("hasSendMsg", 1);
			break;
			default:break;
		}
		return auditBaseMapper.selectBorrowedOverTimeAudits(params);
	}

	@Override
	public List<Map<String, Object>> selectBorrowedOverTimeOperation(int type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("time", new Date());
		if(type == 1){
			params.put("hasMarkOverTime", 0);
		}else if(type == 2){
			params.put("hasSendMsg", 0);
		}
		return auditBaseMapper.selectBorrowedOverTimeOperation(params);
	}
	
	@Override
	public List<String> selectBorrowedOverTimeReceiverUid(Integer id) {
		return auditFlowMapper.selectBorrowedOverTimeReceiverUid(id);
	}

	@Override
	public List<Integer> selectBorrowedOverTimeEle(Integer id,int type) {
		if(type==1||type==2){
			return auditBaseMapper.selectBorrowedOverTimeEle(id,type);
		}
		return Collections.emptyList();
	}

	@Override
	public String selectBorrowOperatorUid(Integer id,int type) {
		return auditBaseMapper.selectBorrowOperatorUid(id,type);
	}

	@Override
	public void updateBorrowedOverTimeMsgState(Integer id,int type,int noticeState) {
		if(type==1||type==2){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", id);
			params.put("type", type);
			params.put("noticeState", noticeState);
			auditBaseMapper.updateBorrowedOverTimeMsgState(params);
		}
	}
	
	@Override
	public void updateSysmsgState(Integer id, int type) {
		if(type==1||type==2){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", id);
			params.put("type", type);
			auditBaseMapper.updateSysmsgState(params);
		}
		
	}
	@Override
	public Map<String, Object> getOrderInfo(String orderNo) {
		Map<String,Object> orderInfo = auditBaseMapper.selectInfoByOrderNo(orderNo);
		String boxNo = MapUtils.getString(orderInfo, "boxNo");//要件箱编号
		String customerName = MapUtils.getString(orderInfo, "customerName");//客户姓名
		String sealDepartment = MapUtils.getString(orderInfo, "sealDepartment");//公章所属部门
		Double borrowingAmount = MapUtils.getDouble(orderInfo, "borrowingAmount");//借款金额
		Integer borrowingDay = MapUtils.getInteger(orderInfo, "borrowingDay");//借款期限
		String channelManagerName = MapUtils.getString(orderInfo, "channelManagerName");//渠道经理
		String acceptMemberName = MapUtils.getString(orderInfo, "acceptMemberName");//受理员
		Integer orderType = MapUtils.getInteger(orderInfo, "orderType");//1:信贷要件,2:无关联要件3:公章
		String creditType = MapUtils.getString(orderInfo, "creditType");//信贷系统对应的订单类型
		String eleIds = MapUtils.getString(orderInfo, "currentBoxElementSet");//存入的物品id集
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("customerName", customerName);
		resultMap.put("boxNo", boxNo);
		resultMap.put("sealDepartment", sealDepartment);
		resultMap.put("borrowingAmount", borrowingAmount);
		resultMap.put("borrowingDay", borrowingDay);
		resultMap.put("channelManagerName", channelManagerName);
		resultMap.put("acceptMemberName", acceptMemberName);
		resultMap.put("orderType", orderType);
		resultMap.put("creditType", creditType);
		if(StringUtils.isNotBlank(boxNo)){
			Map<String, Object> boxInfoByBoxNo = auditBaseMapper.selectBoxInfoByBoxNo(boxNo);
			if(boxInfoByBoxNo!=null){
				resultMap.put("city", boxInfoByBoxNo.get("city"));
			}
		}
		if(StringUtils.isNotBlank(eleIds)){
			List<String> excludeEleIds = auditBaseMapper.selectBorrowedOverTimeEleByOrderNo(orderNo);
			Set<String> excludeEleIdSet = new HashSet<String>();
			excludeEleIdSet.addAll(excludeEleIds);
			putEleList(resultMap, eleIds,excludeEleIdSet,0);
		}
		return resultMap;
	}

	@Override
	public Integer selectAuditIdByOrderNo(Map<String,Object> map) {
		return auditBaseMapper.selectAuditIdByOrderNo(map);
	}

	@Override
	public void updateBtnOnState(int type,String orderNo) {
		auditBaseMapper.updateBtnOnState(type,orderNo);
	}

	@Override
	public int checkEleState(Integer auditId) {
		AuditBaseDto auditBaseDto = auditBaseMapper.selectElementAuditBaseById(auditId);
		String auditEleIds = auditBaseDto.getElementIds();//审批的物品id集
		String orderNo = auditBaseDto.getOrderNo();
		Map<String,Object> orderInfo = auditBaseMapper.selectInfoByOrderNo(orderNo);
		String stateStr = MapUtils.getString(orderInfo, "state","");
		if(stateStr.indexOf("已关闭")>-1||stateStr.indexOf("已停止")>-1||stateStr.indexOf("已完结")>-1){
			return 2;
		}
		String eleIds = MapUtils.getString(orderInfo, "currentBoxElementSet","");//存入的物品id集
		String[] auditEleIdArr = auditEleIds.split(",");
		String[] eleIdArr = eleIds.split(",");
		Set<String> eleIdSet = new HashSet<String>();
		eleIdSet.addAll(Arrays.asList(eleIdArr));
		for (String id : auditEleIdArr) {
			if(!eleIdSet.contains(id)){
				return 1;
			}
		}
		return 0;
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deliverAudit(String uid, UserDto toUser, int auditId,String remark) throws AnjboException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", auditId);
		params.put("uid", uid);
		AuditFlowDto flowDto = auditFlowMapper.selectFlowInauditByUidAndAuditId(params);
		if(flowDto==null){
			throw new AnjboException("FAIL", "更新审批转交失败!");
		}
		AuditFlowDto auditFlowDto = new AuditFlowDto();
		auditFlowDto.setId(flowDto.getId());
		auditFlowDto.setDeliverTo(toUser.getName());
		auditFlowDto.setAuditTime(new Date());
		auditFlowDto.setRemark(remark);
		auditFlowDto.setState(4);//状态为转交
		auditFlowMapper.updateElementAuditFlow(auditFlowDto);
		AuditFlowDto newFlow = new AuditFlowDto();
		newFlow.setDbId(flowDto.getDbId());
		newFlow.setAuditorName(toUser.getName());
		newFlow.setAuditorUid(toUser.getUid());
		newFlow.setHasNext(flowDto.isHasNext());
		newFlow.setOrderNo(flowDto.getOrderNo());
		newFlow.setAuditLevel(flowDto.getAuditLevel());
		newFlow.setAuditTime(new Date());
		newFlow.setState(1);
		auditFlowMapper.addElementAuditFlow(newFlow);
		AuditBaseDto auditBaseDto = new AuditBaseDto();
		auditBaseDto.setId(flowDto.getDbId());
		auditBaseDto.setCurrentAuditName(toUser.getName());
		auditBaseDto.setCurrentAuditUid(toUser.getUid());
		auditBaseMapper.updateElementAuditBase(auditBaseDto);
		systemMessageService.addToAuditMsg(flowDto, toUser);
	}
	@Override
	public String selectBoxcityByOrderNo(String orderNo) {
		Map<String,Object> orderInfo = auditBaseMapper.selectInfoByOrderNo(orderNo);
		String boxNo = MapUtils.getString(orderInfo, "boxNo");//要件箱编号
		Map<String,Object> boxInfo = auditBaseMapper.selectBoxInfoByBoxNo(boxNo);
		return MapUtils.getString(boxInfo, "city","");
	}

	@Override
	public Map<String, Object> selectAuditLevelFrist(int dbId) {
		return auditBaseMapper.selectAuditLevelFrist(dbId);
	}

}