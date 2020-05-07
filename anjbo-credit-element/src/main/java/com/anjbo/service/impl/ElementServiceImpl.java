package com.anjbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.config.page.PageTabRegionFormConfigDto;
import com.anjbo.bean.element.AuditBaseDto;
import com.anjbo.bean.element.ElementListDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.risk.JusticeAuditDto;
import com.anjbo.bean.risk.OfficerAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.dao.AccessFlowMapper;
import com.anjbo.dao.AccessFlowTempMapper;
import com.anjbo.dao.AuditBaseMapper;
import com.anjbo.dao.AuditConfigWebMapper;
import com.anjbo.dao.AuditFlowMapper;
import com.anjbo.dao.BorrowElementMapper;
import com.anjbo.dao.BoxBaseMapper;
import com.anjbo.dao.DocumentsMapper;
import com.anjbo.dao.ElementCustomerInfoFlowMapper;
import com.anjbo.dao.ElementFileFlowMapper;
import com.anjbo.dao.ElementFileMapper;
import com.anjbo.dao.ElementMapper;
import com.anjbo.dao.ElementSystemMessageMapper;
import com.anjbo.dao.ForeclosureTypeMapper;
import com.anjbo.dao.PaymentTypeMapper;
import com.anjbo.dao.SealDepartmentMapper;
import com.anjbo.dao.XAuditBaseMapper;
import com.anjbo.dao.XAuditFlowMapper;
import com.anjbo.service.ElementService;
import com.anjbo.utils.BeanToMapUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.UidUtil;

import net.sf.json.JSONArray;
@Service
public class ElementServiceImpl implements ElementService{
	
	private static final Log log = LogFactory.getLog(ElementService.class);
	
	@Resource
	private ElementMapper elementMapper;
	@Resource
	private DocumentsMapper documentsMapper;
	@Resource
	private ElementCustomerInfoFlowMapper elementCustomerInfoFlowMapper;
	@Resource
	private ElementFileMapper elementFileMapper;
	@Resource
	private ElementFileFlowMapper elementFileFlowMapper;
	@Resource
	private AccessFlowMapper accessFlowMapper;
	@Resource
	private BoxBaseMapper boxBaseMapper;
	@Resource
	private AccessFlowTempMapper accessFlowTempMapper;
	@Resource
	private XAuditFlowMapper xAuditFlowMapper;
	@Resource
	private XAuditBaseMapper xAuditBaseMapper;
	
	@Resource
	private BorrowElementMapper borrowElementMapper;
	
	@Resource
	private SealDepartmentMapper sealDepartmentMapper;
	
	@Resource
	private ForeclosureTypeMapper foreclosureTypeMapper;
	
	@Resource
	private PaymentTypeMapper paymentTypeMapper;
	@Resource
	private ElementSystemMessageMapper elementSystemMessageMapper;
	
	@Resource
	private AuditFlowMapper auditFlowMapper;
	@Resource
	private AuditBaseMapper auditBaseMapper;
	@Resource
	private AuditConfigWebMapper auditConfigWebMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public void saveElementOrder(Map<String, Object> params) throws Exception{
		
		
		
		
    	long beginTime=System.currentTimeMillis();

		params.put("operationAuthority", "");
		log.info("存入要件参数信息:"+params);
		//2.根据类型判断要件管理员，分配箱子
		//3.将要件存入要件表中
		//4.记录本次操作，存入要件操作表
		//5.记录本次要件操作流水(1.计算出本次操作后，箱子中的要件)
		//6.提取信息存入要件订单列表中
		/**/
		
		Integer hasRiskElement=MapUtils.getInteger(params, "hasRiskElement");
		if(hasRiskElement==null) {
			params.put("hasRiskElement", 0);
		}
		
		Integer hasPublicBusiness=MapUtils.getInteger(params, "hasPublicBusiness");
		if(hasPublicBusiness==null) {
			params.put("hasPublicBusiness", 0);
		}
		
		String orderNo=MapUtils.getString(params, "orderNo");
		if(orderNo==null||orderNo.equals("")) {
			orderNo=UidUtil.generateOrderId();
			params.put("orderNo", orderNo);
		}
		params.put("templateType", MapUtils.getString(params, "orderType"));//暂存模板类型1信贷要件关联2无信贷要件关联3公章 
		
		//根绝分配箱子
		//Map<String, Object>  box_map=randomBox(params);
		//System.out.println(box_map);
		
		//List<Map<String,Object>> elementlist= (List<Map<String, Object>>) params.get("elementlist");
		
		List<Map<String,Object>> elementlist= new ArrayList<Map<String, Object>>();

		String orderType=params.get("orderType").toString();
		String key="";
		/*if(orderType.equals("1")) {//存入信贷关联订单
			
		}else*/ if(orderType.equals("1")||orderType.equals("2")) {//存入非信贷关联或无关联要件
			
		List<Map<String,Object>> riskList=(List<Map<String, Object>>) params.get("riskElement");
		//123
		if(riskList!=null&&riskList.size()>0){
			
			for(Map<String,Object> o:riskList) {
				
				Map<String,Object> file=new HashMap<String,Object>();
				Integer type=MapUtils.getInteger(o, "type");
				
				if(o.get("status").toString().equals("2")) {
					file.put("elementType","2");
					file.put("type",type);
					file.put("cardType",MapUtils.getString(o, "title"));
					List<Map<String,Object>> data=(List<Map<String, Object>>) o.get("data");
					if(data!=null&&data.size()>0) {
						for(Map<String,Object> property:data) {
							key=MapUtils.getString(property, "key");
						
							if(key.equals("cardType")&&(type==5||type==6||type==7)) {	
								file.put(key, MapUtils.getString(o, "title"));
							}else {
								file.put(key, MapUtils.getString(property, "value"));
							}
						}
					}
					elementlist.add(file);
				}
			}
		}

		
		List<Map<String,Object>> backList=(List<Map<String, Object>>) params.get("receivableElement");
	
		if(backList!=null&&backList.size()>0){
			
			for(Map<String,Object> o:backList) {
				
				Map<String,Object> file=new HashMap<String,Object>();
				Integer type=MapUtils.getInteger(o, "type");
				if(o.get("status").toString().equals("2")) {
					file.put("elementType","1");
					file.put("type",type);
					file.put("cardType",MapUtils.getString(o, "title"));
					List<Map<String,Object>> data=(List<Map<String, Object>>) o.get("data");
					if(data!=null&&data.size()>0) {
						for(Map<String,Object> property:data) {
							//file.put(MapUtils.getString(property, "key"), MapUtils.getString(property, "value"));
							key=MapUtils.getString(property, "key");
							if(key.equals("cardType")&&(type==5||type==6||type==7)) {	
								file.put(key, MapUtils.getString(o, "title"));
							}else {
								file.put(key, MapUtils.getString(property, "value"));
							}
						}
					}
					elementlist.add(file);
				}
			}
		}
		
		
				
		}else if(orderType.equals("3")) {//存入公章要件订单
			
			params.put("customerName", MapUtils.getString(params, "sealDepartment"));
			
			List<Map<String,Object>> sealList=(List<Map<String, Object>>) params.get("sealElement");	
			
			if(sealList!=null&&sealList.size()>0){
				
				for(Map<String,Object> o:sealList) {
					
					Map<String,Object> file=new HashMap<String,Object>();
					
					if(o.get("status").toString().equals("2")) {
						file.put("elementType","3");
						file.put("type",MapUtils.getString(o, "type"));
						List<Map<String,Object>> data=(List<Map<String, Object>>) o.get("data");
						if(data!=null&&data.size()>0) {
							for(Map<String,Object> property:data) {
								
								/*file.put(MapUtils.getString(property, "key"), MapUtils.getString(property, "value"));*/
								
								key=MapUtils.getString(property, "key");
								if(key.equals("cardType")) {	
									file.put(key, MapUtils.getString(o, "title"));
								}else {
									file.put(key, MapUtils.getString(property, "value"));
								}
							}
						}
						elementlist.add(file);
					}
				}
			}
		}
		
		
		for(Map<String,Object> map:elementlist) {
			
			map.put("boxNo", params.get("boxNo"));
			map.put("orderNo", orderNo);
		}
		
		
		//存入要件
		elementFileMapper.insertElementFile(elementlist);	
		String elementSet="";
		//列表要件
		List<ElementListDto> list = elementMapper.selectElementByOrderNo(params);
		String currentBoxElementSet="";
		if(list!=null&&list.size()>0) {
			currentBoxElementSet = list.get(0).getCurrentBoxElementSet()==null?"":list.get(0).getCurrentBoxElementSet();
			//删除不在要件柜中的要件id
			if(StringUtil.isNotBlank(currentBoxElementSet)) {
				String ids[]= currentBoxElementSet.split(",");
				List<Map<String,Object>> mapList = elementFileMapper.selectElementFileList(ids);
				for (Map<String, Object> map : mapList) {
					if(MapUtils.getIntValue(map, "status")!=3
							&&MapUtils.getIntValue(map, "status")!=5
							&&MapUtils.getIntValue(map, "status")!=6) {
						currentBoxElementSet=addReduceBoxElement(currentBoxElementSet,MapUtils.getString(map, "id"),2);
					}
				}
			}
		}
		for(Map<String,Object> map:elementlist) {
			
			elementSet=elementSet+map.get("id").toString()+",";
			if(!currentBoxElementSet.contains(map.get("id").toString())) {
				currentBoxElementSet +=","+map.get("id").toString();
			}
		}
		elementSet=elementSet.substring(0, elementSet.length()-1);
		if(currentBoxElementSet.startsWith(",")) {
			currentBoxElementSet = currentBoxElementSet.substring(1,currentBoxElementSet.length());
		}
		//params.put("boxNo", params.get("boxNo"));
		params.put("elementSet", elementSet);
		
		//存操作后，当前要件箱中存在的要件
		params.put("currentBoxElementSet", currentBoxElementSet);
	
		
		
		if(orderType.equals("3")) {
			
			String sealElementSet="";
					
			Map<String,Object> sealDepartmentMap=sealDepartmentMapper.selectSealDepartmentInfoById(params);
			
			sealElementSet=MapUtils.getString(sealDepartmentMap, "currentBoxElementSet");
			if(StringUtil.isNotBlank(sealElementSet)) {
				sealElementSet=sealElementSet+","+elementSet;
			}else {
				sealElementSet = elementSet;
			}
			
			sealDepartmentMap.put("currentBoxElementSet", sealElementSet);
			//更新部门公章信息
			sealDepartmentMapper.updateSealElementSet(sealDepartmentMap);
			
		}
		
		
		


		
		//每次图片数据都是叠加的，查询最后一次还记录的图片,拼接新存入的图片
	 	/*List<Map<String, Object>> accessFlowList=accessFlowMapper.selectLastAccessByMap(params);
		
	 	if(accessFlowList!=null&&accessFlowList.size()>0) {
	 			String riskPicture=MapUtils.getString(accessFlowList.get(0), "riskPicture");
	 			String temp_riskPicture=MapUtils.getString(params, "riskPicture");
	 			if(temp_riskPicture!=null&&!temp_riskPicture.equals("")) {
	 				if(riskPicture!=null&&!riskPicture.equals("")) {
	 					if(!temp_riskPicture.contains(riskPicture)) {
	 						riskPicture=riskPicture+","+temp_riskPicture;
	 					}
	 					
	 					riskPicture=operationPicture(temp_riskPicture,riskPicture);
	 					params.put("riskPicture", riskPicture);
	 				}	
	 			}else {
	 				params.put("riskPicture", riskPicture);
	 			}
	 			
	 			String receivablePicture=MapUtils.getString(accessFlowList.get(0), "receivablePicture");
	 			String temp_receivablePicture=MapUtils.getString(params, "receivablePicture");
	 			if(temp_receivablePicture!=null&&!temp_receivablePicture.equals("")) {
	 				if(receivablePicture!=null&&!receivablePicture.equals("")) {
	 					if(!temp_receivablePicture.contains(receivablePicture)) {
	 						receivablePicture=receivablePicture+","+temp_receivablePicture;
	 					}
	 					receivablePicture=operationPicture(temp_receivablePicture,receivablePicture);
	 					params.put("receivablePicture", receivablePicture);
	 				}	
	 			}else {
	 				params.put("receivablePicture", receivablePicture);
	 			}
	 			
	 			
	 			String elsePicture=MapUtils.getString(accessFlowList.get(0), "elsePicture");
	 			String temp_elsePicture=MapUtils.getString(params, "elsePicture");
	 			if(temp_elsePicture!=null&&!temp_elsePicture.equals("")) {
	 				if(elsePicture!=null&&!elsePicture.equals("")) {
	 					if(!temp_elsePicture.contains(elsePicture)) {
	 						elsePicture=elsePicture+","+temp_elsePicture;
	 					}
	 					elsePicture=operationPicture(temp_elsePicture,elsePicture);
	 					params.put("elsePicture", elsePicture);
	 				}	
	 			}else {
	 				params.put("elsePicture", elsePicture);
	 			}
	 		
	 			
	 			String sealPicture=MapUtils.getString(accessFlowList.get(0), "sealPicture");
	 			String temp_sealPicture=MapUtils.getString(params, "sealPicture");
	 			if(temp_sealPicture!=null&&!temp_sealPicture.equals("")) {
	 				if(sealPicture!=null&&!sealPicture.equals("")) {
	 					if(!temp_sealPicture.contains(sealPicture)) {
	 						sealPicture=sealPicture+","+temp_sealPicture;
	 					}
	 					sealPicture=operationPicture(temp_sealPicture,sealPicture);
	 					params.put("sealPicture", sealPicture);
	 				}	
	 			}else {
	 				params.put("sealPicture", sealPicture);
	 			}
	 	}*/
		
		//记录本次要件操作流水
		accessFlowMapper.insertAccessFlowRecorde(params);
		params.put("accessFlowId", params.get("id"));
		
		List<ElementListDto> elementRecordeList=elementMapper.selectElementByOrderNo(params);
		if(elementRecordeList!=null&&elementRecordeList.size()>0) {
			//修改信贷同步的订单信息，保存修改的是否收到风控要件，和对公业务
			if(elementRecordeList.get(0).getOrderStatus()==1) {
				params.put("orderStatus", 2);
			}
			//是否對公，是否收到風控要件
			elementMapper.UpdateCreditElementByOrderNo(params);
			//狀態
			elementMapper.UpdateElementStatusByOrderNo(params);
			currentBoxElementSet=elementRecordeList.get(0).getCurrentBoxElementSet();
			currentBoxElementSet=addReduceBoxElement(currentBoxElementSet,elementSet,1);
			if(currentBoxElementSet!=null) {
				currentBoxElementSet=currentBoxElementSet.replaceAll("null", "");
			}
			if(currentBoxElementSet.indexOf(",")==0) {
				currentBoxElementSet = currentBoxElementSet.substring(1, currentBoxElementSet.length());
			}
			params.put("currentBoxElementSet", currentBoxElementSet);
		}else {

			//提取信息存入要件订单列表中
			params.put("orderStatus", 2);
			elementMapper.saveElementOrder(params);//修改订单为已存入
		}
		
		
		//修改要件订单表对应箱子当前存在的要件集合
		elementMapper.UpdateElementByOrderNo(params);
		
		//修改箱子的使用状态为已使用，绑定订单号
		params.put("useStatus", 1);
		boxBaseMapper.updateBoxBaseByBoxNo(params);
		//同步暂存信息
		
		/*if(!orderType.equals("3")) {
			saveSuspendedStoreButtonInfo(params);//同步暂存信息
		}*/
	
		accessFlowTempMapper.deleteAccessFlowTempByMap(params);
		
		long endTime=System.currentTimeMillis();
		
		log.info("保存saveElementOrder方法执行时间:"+(endTime-beginTime));
	
	}
	
	//同步信贷订单
	@SuppressWarnings("unused")
	public void saveCredit(Map<String, Object> params) throws Exception{
		HttpUtil httpUtil=new HttpUtil();
		
		//RespDataObject<List<OrderListDto>> orderList = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/elementCreditOrderList", null, List.class);
		/*RespData<OrderListDto> respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/order/base/v/elementCreditOrderList", null, OrderListDto.class);
		List<OrderListDto> orderList=respData.getData(); 
		System.out.println("1");*/

		/*if(orderList!=null&&orderList.size()>0) {
			for(OrderListDto o:orderList) {
				o.put("creditTypeUid",o.getProductCode());
				o.put("creditType",MapUtils.getString(o, "productName") );
				elementMapper.saveCredit(o);
			}
		}*/
		
		RespData<Map<String,Object>> respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/order/base/v/elementCreditOrderList", null, Map.class);
		List<Map<String,Object>> orderList=respData.getData();
		if(orderList!=null&&orderList.size()>0) {
			for(Map<String,Object> o:orderList) {
				o.put("orderType",1);
				o.put("creditTypeUid",MapUtils.getString(o, "productCode"));
				o.put("creditType",MapUtils.getString(o, "productName") );
				o.put("orderStatus",1);
				List<ElementListDto> listDto=elementMapper.selectElementByOrderNo(o);
				if(listDto!=null&&listDto.size()>0) {
					elementMapper.updateCreditElementOrder(o);
				}else {
					elementMapper.saveCredit(o);
				}
				
			}
		}
	}

	
	public List<ElementListDto> selectElementList(Map<String, Object> params,UserDto userDto) {
		List<Map<String,Object>> listType = auditConfigWebMapper.selectAllCityType();
		Map<String,Object> mapType = new HashMap<String,Object>();
		for (Map<String, Object> map : listType) {
			if(MapUtils.getIntValue(map, "type")==1) {
				Map<String,Object> mapT = MapUtils.getMap(mapType, MapUtils.getString(map, "city"),new HashMap<String,Object>());
				mapT.put("1", "非业务流程借出");
				mapType.put(MapUtils.getString(map, "city"),mapT);
			}
			if(MapUtils.getIntValue(map, "type")==4) {
				Map<String,Object> mapT = MapUtils.getMap(mapType, MapUtils.getString(map, "city"),new HashMap<String,Object>());
				mapT.put("4", "业务流程借出");
				mapType.put(MapUtils.getString(map, "city"),mapT);
			}
		}
		List<ElementListDto> list=elementMapper.selectElementList(params);
		//只查询要件柜中的要件
		for(ElementListDto o:list ) {
			//借是否弹框选类型
			o.setTypeMap(MapUtils.getMap(mapType, o.getCityName()));
			params.put("boxNo", o.getBoxNo());	
			
			if(o.getOrderType()==3) {
				o.setSealMark(MapUtils.getString(operationElementName(o.getCurrentBoxElementSet(),2), "sealMark"));
			}
			
			
			if(userDto.getAuthIds().contains("57")||userDto.getAuthIds().contains("58")) {//判断是否显示存按钮
				o.setStoreButton(0);
			}else {
				o.setStoreButton(1);
			}
			
			//if(o.getCurrentBoxElementSet()!=null&&!o.getCurrentBoxElementSet().equals("")&&userDto.getAuthIds().contains("59")||userDto.getAuthIds().contains("60")) {//判断是否显示借按钮
			if(o.getCurrentBoxElementSet()!=null&&!o.getCurrentBoxElementSet().equals("")) {//判断是否显示借按钮
				Map<String, Object> AccessFlowRecorde_map=new HashMap<String, Object>();
				AccessFlowRecorde_map.put("orderNo", o.getOrderNo());
				AccessFlowRecorde_map.put("operationType", 1);
				
				String ids[]={};
				String id[]={};
				String elementSet="";
				//查询当前要件箱中的要件是否全被选中，处于已通过借用审批或借用审批中
				
				List<Map<String,Object>> lendList=xAuditBaseMapper.selectFileIdsByOrderNo(AccessFlowRecorde_map);
				if(lendList!=null&&lendList.size()>0) {
					for(Map<String,Object> map:lendList) {
						elementSet=elementSet+MapUtils.getString(map, "elementIds")+",";
					}
				}
				log.info("elementSet"+elementSet);
				ids=elementSet.split(",");
				if(o.getCurrentBoxElementSet()!=null) {
					id=o.getCurrentBoxElementSet().split(",");
					log.info("CurrentBoxElementSet"+o.getCurrentBoxElementSet());
				}
				
		
				boolean flag=useLoop(ids, id);//true为包含,false为不包含

				//查询是否存在，存操作，若存在需显示借按钮
				List<Map<String, Object>> AccessFlowRecorde_List=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
				if(AccessFlowRecorde_List!=null&&AccessFlowRecorde_List.size()>0&&!flag) {
					o.setBorrowButton(0);//显示借按钮
				}
			}
			
			
			if(userDto.getAuthIds().contains("61")||userDto.getAuthIds().contains("62")) {//判断是否显示取按钮
				Map<String, Object> AuditBase_map=new HashMap<String, Object>();
				AuditBase_map.put("orderNo", o.getOrderNo());
				//查询是否存在，审批通过的借要件，若存在需显示取按钮
				List<Map<String, Object>> AuditBase_List=xAuditBaseMapper.selectAuditBaseList(AuditBase_map);
				if(AuditBase_List!=null&&AuditBase_List.size()>0&&o.getCurrentBoxElementSet()!=null&&!o.getCurrentBoxElementSet().equals("")) {
					o.setTakeButton(0);//显示取按钮
				}
			}
			

			if(userDto.getAuthIds().contains("65")||userDto.getAuthIds().contains("66")) {//判断是否显示还按钮
				Map<String, Object> AccessFlowRecorde_map=new HashMap<String, Object>();
				AccessFlowRecorde_map.put("orderNo",o.getOrderNo());
				AccessFlowRecorde_map.put("operationType", 2);
				
				//查询是否存在未还完的要件,存在未还完的要件才显示还按钮
				List<Map<String, Object>> borrowList=borrowElementMapper.selectBorrowElementByOrderNo(AccessFlowRecorde_map);
				

				//查询是否存在，取操作，若存在需显示还按钮
				List<Map<String, Object>> AccessFlowRecorde_List=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
				if(AccessFlowRecorde_List!=null&&AccessFlowRecorde_List.size()>0&&borrowList!=null&&borrowList.size()>0) {
					o.setReturnButton(0);//显示还按钮
				}
				
				//查询是否有开箱操作
				AccessFlowRecorde_map.put("operationType", 6);
				//查询是否存在，开箱操作，若存在需显示还按钮
				List<Map<String, Object>> AccessFlowRecorde_List1=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
				if(AccessFlowRecorde_List1!=null&&AccessFlowRecorde_List1.size()>0&&borrowList!=null&&borrowList.size()>0) {
					o.setReturnButton(0);//显示还按钮
				}
				//如果订单状态已归还，不显示还按钮
				if(o.getOrderStatus()==2||o.getOrderStatus()==4) {
					o.setReturnButton(1);//不显示还按钮
				}
				
			}
			
			
			//if((userDto.getAuthIds().contains("67")||userDto.getAuthIds().contains("68"))) {
			if(true) {
				Map<String, Object> AuditFlow_map=new HashMap<String, Object>();
				AuditFlow_map.put("orderNo", o.getOrderNo());
				AuditFlow_map.put("currentHandlerUid", userDto.getUid());
				if(xAuditFlowMapper.selectAuditFlowList(AuditFlow_map)>0) {
					o.setAuditButton(0);//显示审批按钮
				}
			}
	
			
				if(o.getCurrentBoxElementSet()!=null&&!o.getCurrentBoxElementSet().equals("")&&userDto.getAuthIds().contains("69")||userDto.getAuthIds().contains("70")) {//判断是否显示开箱按钮
					Map<String, Object> fileMap=new HashMap<String, Object>();
					fileMap.put("orderNo", o.getOrderNo());
					fileMap.put("status", 3);
					//查询是否存在，存操作，若存在需显示开箱按钮
					List<Map<String, Object>> AccessFlowRecorde_List = elementFileMapper.selectElementFileListbyOrderNo(fileMap);
					if(AccessFlowRecorde_List!=null&&AccessFlowRecorde_List.size()>0) {
						o.setOpenButton(0);//显示开箱按钮
					}
				}
			
				
				

				if((userDto.getAuthIds().contains("63")||userDto.getAuthIds().contains("64"))&&o.getOrderType()==2&&o.getCurrentBoxElementSet()!=null&&!o.getCurrentBoxElementSet().equals("")) {//判断非信贷订单，显示退按钮
					Map<String, Object> AccessFlowRecorde_map=new HashMap<String, Object>();
					AccessFlowRecorde_map.put("orderNo", o.getOrderNo());
					AccessFlowRecorde_map.put("operationType", 1);
					//查询是否存在，存操作，若存在需显示退按钮
					List<Map<String, Object>> AccessFlowRecorde_List=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
					if(AccessFlowRecorde_List!=null&&AccessFlowRecorde_List.size()>0) {
						o.setRefundButton(0);//显示退按钮
						/*o.setStoreButton(1);
						o.setReturnButton(1);
						o.setBorrowButton(1);
						o.setAuditButton(1);	*/
					}
				}
				
				
				List<Map<String,Object>> buttonList=new ArrayList<Map<String,Object>>();
				if(o.getState()!=null) {
					String State=o.getState();
					if((State.equals("待要件退还")||o.getState().toString().equals("已完结"))&&o.getOrderType()==1&&(userDto.getAuthIds().contains("63")||userDto.getAuthIds().contains("64"))) {//判断是否到达待要件退还节点
						Map<String, Object> AccessFlowRecorde_map=new HashMap<String, Object>();
						AccessFlowRecorde_map.put("orderNo", o.getOrderNo());
						AccessFlowRecorde_map.put("operationType", 1);
						//查询是否存在，存操作，若存在需显示退按钮
						List<Map<String, Object>> AccessFlowRecorde_List=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
						if(AccessFlowRecorde_List!=null&&AccessFlowRecorde_List.size()>0&&o.getCurrentBoxElementSet()!=null&&!o.getCurrentBoxElementSet().equals("")) {
							o.setRefundButton(0);//显示退回按钮
						}
						o.setStoreButton(1);//存按钮消失
						o.setBorrowButton(1);//借按钮消失
					}	
					
					if(State.equals("订单已停止")&&o.getOrderType()==1&&(userDto.getAuthIds().contains("63")||userDto.getAuthIds().contains("64"))) {//判断是否到达待要件退还节点
						Map<String, Object> AccessFlowRecorde_map=new HashMap<String, Object>();
						AccessFlowRecorde_map.put("orderNo", o.getOrderNo());
						AccessFlowRecorde_map.put("operationType", 1);
						//查询是否存在，存操作，若存在需显示退按钮
						List<Map<String, Object>> AccessFlowRecorde_List=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
						if(AccessFlowRecorde_List!=null&&AccessFlowRecorde_List.size()>0&&o.getCurrentBoxElementSet()!=null&&!o.getCurrentBoxElementSet().equals("")) {
							o.setRefundButton(0);//显示退回按钮
						}
						o.setStoreButton(1);//存按钮消失
						o.setBorrowButton(1);//借按钮消失
						o.setReturnButton(1);
						o.setAuditButton(1);	
						o.setTakeButton(1);
					}	
					
					
					if(State.equals("订单已停止")&&o.getOrderType()==1&&(userDto.getAuthIds().contains("65")||userDto.getAuthIds().contains("66"))) {//判断订单状态是否为订单已完结,显示还按钮
						Map<String, Object> AccessFlowRecorde_map=new HashMap<String, Object>();
						AccessFlowRecorde_map.put("orderNo",o.getOrderNo());
						AccessFlowRecorde_map.put("operationType", 2);
						
						//查询是否存在未还完的要件,存在未还完的要件才显示还按钮
						List<Map<String, Object>> borrowList=borrowElementMapper.selectBorrowElementByOrderNo(AccessFlowRecorde_map);
						

						//查询是否存在，取操作，若存在需显示还按钮
						List<Map<String, Object>> AccessFlowRecorde_List=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
						if(AccessFlowRecorde_List!=null&&AccessFlowRecorde_List.size()>0&&borrowList!=null&&borrowList.size()>0) {
							o.setReturnButton(0);//显示还按钮
						}
						o.setStoreButton(1);//存按钮消失
						o.setBorrowButton(1);//借按钮消失
						o.setAuditButton(1);	
						o.setTakeButton(1);
					}	
					
					if((State.equals("已关闭")||State.equals("待收罚息"))&&o.getOrderType()==1&&(userDto.getAuthIds().contains("63")||userDto.getAuthIds().contains("64"))) {//判断订单状态是否为订单已关闭,显示退按钮
						Map<String, Object> AccessFlowRecorde_map=new HashMap<String, Object>();
						AccessFlowRecorde_map.put("orderNo", o.getOrderNo());
						AccessFlowRecorde_map.put("operationType", 1);
						//查询是否存在，存操作，若存在需显示退按钮
						List<Map<String, Object>> AccessFlowRecorde_List=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
						if(AccessFlowRecorde_List!=null&&AccessFlowRecorde_List.size()>0&&o.getCurrentBoxElementSet()!=null&&!o.getCurrentBoxElementSet().equals("")) {
							o.setRefundButton(0);//显示退按钮
						}
						o.setStoreButton(1);
						o.setBorrowButton(1);
						o.setAuditButton(1);	
					}
					
					
					if((State.equals("已关闭")||State.equals("待收罚息"))&&o.getOrderType()==1&&(userDto.getAuthIds().contains("65")||userDto.getAuthIds().contains("66"))) {//判断订单状态是否为订单已关闭,显示还按钮
						Map<String, Object> AccessFlowRecorde_map=new HashMap<String, Object>();
						AccessFlowRecorde_map.put("orderNo",o.getOrderNo());
						AccessFlowRecorde_map.put("operationType", 2);
						
						//查询是否存在未还完的要件,存在未还完的要件才显示还按钮
						List<Map<String, Object>> borrowList=borrowElementMapper.selectBorrowElementByOrderNo(AccessFlowRecorde_map);
						

						//查询是否存在，取操作，若存在需显示还按钮
						List<Map<String, Object>> AccessFlowRecorde_List=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
						if(AccessFlowRecorde_List!=null&&AccessFlowRecorde_List.size()>0&&borrowList!=null&&borrowList.size()>0) {
							o.setReturnButton(0);//显示还按钮
						}
						o.setStoreButton(1);
						o.setBorrowButton(1);
						o.setAuditButton(1);	
					}	
				}
				
				
				//若要件退还完毕后，隐藏所有按钮和要件箱号
				if(o.getOrderStatus()==6&&(o.getCurrentBoxElementSet()==null||o.getCurrentBoxElementSet().equals(""))) {//判断非信贷订单，显示退按钮
						o.setRefundButton(1);//隐藏所有按钮
						o.setStoreButton(1);
						o.setReturnButton(1);
						o.setBorrowButton(1);
						o.setAuditButton(1);	
						o.setTakeButton(1);
						o.setBoxNo("");
				}
				//如果订单状态已归还，不显示还按钮
				if(o.getOrderStatus()==2||o.getOrderStatus()==4) {
					o.setReturnButton(1);//不显示还按钮
				}

			List<Map<String, Object>> boxBaseList=boxBaseMapper.selectBoxBaseByBoxNo(params);
			if(boxBaseList!=null&&boxBaseList.size()>0) {
				o.setDeviceStatus(MapUtils.getInteger(boxBaseList.get(0), "deviceStatus"));
			}else {
				o.setDeviceStatus(1);
			}
			
			if(o.getStoreButton()!=null&&o.getStoreButton()==0&&o.getOrderStatus()!=6) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", "storeButton");
				if(o.getOrderNo()==null||o.getOrderNo().equals("")) {
					map.put("deviceStatus", 1);	
				}else {
					map.put("deviceStatus", o.getDeviceStatus());
				}
				
				buttonList.add(map);
			}
			
			if(o.getTakeButton()!=null&&o.getTakeButton()==0&&o.getOrderStatus()!=6) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", "takeButton");
				map.put("deviceStatus", o.getDeviceStatus());
				buttonList.add(map);
			}
			
			if(o.getBorrowButton()!=null&&o.getBorrowButton()==0&&o.getOrderStatus()!=6) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", "borrowButton");
				map.put("deviceStatus", 1);
				buttonList.add(map);
			}
			
			if(o.getReturnButton()!=null&&o.getReturnButton()==0&&o.getOrderStatus()!=6) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", "returnButton");
				map.put("deviceStatus", o.getDeviceStatus());
				buttonList.add(map);
			}
			
			if(o.getRefundButton()!=null&&o.getRefundButton()==0&&o.getOrderStatus()!=6) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", "refundButton");
				map.put("deviceStatus", o.getDeviceStatus());
				buttonList.add(map);
			}
			
			if(o.getOpenButton()!=null&&o.getOpenButton()==0&&o.getOrderStatus()!=6) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", "openButton");
				map.put("deviceStatus", o.getDeviceStatus());
				buttonList.add(map);
			}
			
			if(o.getAuditButton()!=null&&o.getAuditButton()==0&&o.getOrderStatus()!=6) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", "auditButton");
				map.put("deviceStatus", 1);
				buttonList.add(map);
			}	
			
			o.setButtonList(buttonList);
		}
	
		return list;
	}

	public int selectElementListCount(Map<String, Object> params) {
		
		return elementMapper.selectElementListCount(params);
	}

	
	/*@Override
	//返回点击存按钮的空模板信息
	public Map<String, Object> retrunStoreButtonInfo(Map<String, Object> params) throws Exception {
	
		Map<String, Object> result=new HashMap<String, Object>();
		//先查询是否有暂存数据
		String orderType=params.get("orderType").toString();
		
		params.put("templateType", orderType);//暂存模板类型1信贷要件关联2无信贷要件关联3公章
		ElementListDto elementListDto=null;
		HttpUtil httpUtil=new HttpUtil();
		result=accessFlowTempMapper.selectAccessFlowTemp(params);
		System.out.println(result);
		if(result==null) {
			//返回空模板
			if(orderType.equals("1")||orderType.equals("2")) {
				result=initDataTemplate(params,httpUtil);
			}else {

				List<ElementListDto>	elementList=elementMapper.selectElementByOrderNo(params);
			 	if(elementList!=null&&elementList.size()>0) {//若无订单，则表示第一次存入，加载空模板
			 		elementListDto=elementList.get(0);
			 		String ids[]= {};
			 		String currentBoxElementSet=elementListDto.getCurrentBoxElementSet();
				 		if(currentBoxElementSet!=null&&!currentBoxElementSet.equals("")) {
				 			ids=currentBoxElementSet.split(",");
				 			List<Map<String,Object>> elementFileList=elementFileMapper.selectElementFileList(ids);
				 			if(elementFileList!=null&&elementFileList.size()>0) {
				 				result=getSealDataTemplate(elementFileList,httpUtil);
				 			}else {//当前要件id在要件表中查询不出要件
				 				result=initDataTemplate(params,httpUtil);
				 			}
				 		}else {//当前要件箱中公章要件已经全部取出
			 				result=initDataTemplate(params,httpUtil);
			 			}
				 		
				 		if(result==null) {
				 			result=new HashMap<String, Object>();
				 		}
				 		
				 		result.put("sealDepartment", elementListDto.getSealDepartment());
					 	result.put("sealDepartmentId", elementListDto.getSealDepartmentId());
				 		
			 		}else {
			 			result=initDataTemplate(params,httpUtil);
			 		}	
			}

			//返回
			if(orderType.equals("1")) {
			 List<ElementListDto>	elementList=elementMapper.selectElementByOrderNo(params);
			 	if(elementList!=null&&elementList.size()>0) {
			 		elementListDto=elementList.get(0);
			 		result.put("customerName", elementListDto.getCustomerName());
			 		
			 		result.put("channelManagerName", elementListDto.getChannelManagerName());
			 		result.put("channelManagerUid", elementListDto.getChannelManagerUid());
			 		
			 		result.put("acceptMemberName", elementListDto.getAcceptMemberName());
			 		result.put("acceptMemberUid", elementListDto.getAcceptMemberUid());
			 		
			 		result.put("cityCode", elementListDto.getCityCode());
			 		result.put("cityName", elementListDto.getCityName());
			 		result.put("orderNo", elementListDto.getOrderNo());
			 		
			 	} 
		
			}
	
		}else {
			result=JsonUtil.parseJsonToMap(result.get("content").toString());
		}

		
		log.info("返回点击存按钮的空模板信息"+result);
		return result;
	}*/
	
	
	@Override
	//返回点击存按钮的空模板信息
	public Map<String, Object> retrunStoreButtonInfo(Map<String, Object> params) throws Exception {
	
		Map<String, Object> result=new HashMap<String, Object>();
		//先查询是否有暂存数据
		String orderType=params.get("orderType").toString();
		int operationType = MapUtils.getIntValue(params, "operationType");
		params.put("templateType", orderType);//暂存模板类型1信贷要件关联2无信贷要件关联3公章
		ElementListDto elementListDto=null;
		HttpUtil httpUtil=new HttpUtil();
		result=accessFlowTempMapper.selectAccessFlowTemp(params);
		System.out.println(result);
		if(result==null) {//表示无暂存数据或不是创建订单人登录
			//返回空模板
			if(orderType.equals("1")||orderType.equals("2")) {
				
				//通过订单号查询是否已经存入的订单
				
			List<Map<String,Object>> storeList=accessFlowMapper.selectAccessFlowListByOrderNo(params);//查寻已存要件
			if(storeList==null||storeList.size()==0) {
				result=initDataTemplate(params,httpUtil);//若不存在存要件记录
			}else {
				result=new HashMap<String, Object>();
				String storeFileid="";
				for(Map<String,Object> map:storeList) {
					storeFileid=storeFileid+MapUtils.getString(map, "elementSet")+",";
				}
				 String [] array = storeFileid.split(",");  
			        Set <String> set =  new  HashSet <String>();  
			        for (int  i = 0 ; i <array.length; i ++){  
			            set.add(array[i]);  
			        }  
			        String [] arrayResult =(String [])set.toArray(new  String [set.size()]);

			      List<Map<String,Object>> fileList= elementFileMapper.selectElementFileList(arrayResult); 
			        
					List<Map<String, Object>> riskList=new  ArrayList<Map<String, Object>>();
					List<Map<String, Object>> backList=new  ArrayList<Map<String, Object>>();
					List<Map<String, Object>> sealList=new  ArrayList<Map<String, Object>>();
			      
			      for(int i=0;i<fileList.size();i++) {
			    	    Map<String,Object> o = fileList.get(i);
			    	    //已取消存入的不再带入
			    	    if(MapUtils.getIntValue(o, "status")==7) {
			    	    	continue;
			    	    }
			    	    log.info("点击存按钮，数据库中的要件id:"+MapUtils.getString(o, "id"));
						if(o.get("elementType").toString().equals("1")) {//回款要件
							backList.add(fullDataTemplate(o,httpUtil,"3"));
							//第二个之后的身份证设置为hasAdd=2
							//多个已取消存入的同名要件只保留一个
							Map<String,Object> m = new HashMap<String,Object>();
							Map<String,Object> cancelElementMap = new HashMap<String,Object>();
							//需要删除的
							Map<String,Object> deleteElementMap = new HashMap<String,Object>();
							Iterator<Map<String,Object>> iter = backList.iterator();
							while(iter.hasNext()) {
								Map<String,Object> map = iter.next();
								if(cancelElementMap.get(MapUtils.getString(map, "title"))!=null&&MapUtils.getIntValue(map, "status")==1) {
									iter.remove();
								}
								cancelElementMap.put(MapUtils.getString(map, "title"), MapUtils.getString(map, "title"));
								if(MapUtils.getIntValue(map, "status")>1) {
									deleteElementMap.put(MapUtils.getString(map, "title"), MapUtils.getString(map, "title"));
								}
							}
							//如果有对应的数据则删除已取消存入的
							Iterator<Map<String,Object>> iter2 = backList.iterator();
							while(iter2.hasNext()) {
								Map<String,Object> map = iter2.next();
								if(deleteElementMap.get(MapUtils.getString(map, "title"))!=null&&MapUtils.getIntValue(map, "status")==1) {
									iter2.remove();
									continue;
								}
								if(m.get(MapUtils.getString(map, "title"))!=null) {
									map.put("hasAdd", 2);
								}
								if("1".equals(MapUtils.getString(map, "hasAdd"))) {
									m.put(MapUtils.getString(map, "title"), MapUtils.getString(map, "title"));
								}
							}
						}
						
						if(o.get("elementType").toString().equals("2")) {//风控要件
							riskList.add(fullDataTemplate(o,httpUtil,"3"));
							//第二个之后的身份证设置为hasAdd=2
							//多个已取消存入的同名要件只保留一个
							Map<String,Object> m = new HashMap<String,Object>();
							Map<String,Object> cancelElementMap = new HashMap<String,Object>();
							//需要删除的
							Map<String,Object> deleteElementMap = new HashMap<String,Object>();
							Iterator<Map<String,Object>> iter = riskList.iterator();
							while(iter.hasNext()) {
								Map<String,Object> map = iter.next();
								if(cancelElementMap.get(MapUtils.getString(map, "title"))!=null&&MapUtils.getIntValue(map, "status")==1) {
									iter.remove();
								}
								cancelElementMap.put(MapUtils.getString(map, "title"), MapUtils.getString(map, "title"));
								if(MapUtils.getIntValue(map, "status")>1) {
									deleteElementMap.put(MapUtils.getString(map, "title"), MapUtils.getString(map, "title"));
								}
							}
							//如果有对应的数据则删除已取消存入的
							Iterator<Map<String,Object>> iter2 = riskList.iterator();
							while(iter2.hasNext()) {
								Map<String,Object> map = iter2.next();
								if(deleteElementMap.get(MapUtils.getString(map, "title"))!=null&&MapUtils.getIntValue(map, "status")==1) {
									iter2.remove();
									continue;
								}
								if(m.get(MapUtils.getString(map, "title"))!=null) {
									map.put("hasAdd", 2);
								}
								if("1".equals(MapUtils.getString(map, "hasAdd"))) {
									m.put(MapUtils.getString(map, "title"), MapUtils.getString(map, "title"));
								}
							}
						}
						
						if(o.get("elementType").toString().equals("3")) {//公章要件
							sealList.add(fullDataTemplate(o,httpUtil,"3"));
							//第二个之后的身份证设置为hasAdd=2
							//多个已取消存入的同名要件只保留一个
							Map<String,Object> m = new HashMap<String,Object>();
							Map<String,Object> cancelElementMap = new HashMap<String,Object>();
							//需要删除的
							Map<String,Object> deleteElementMap = new HashMap<String,Object>();
							Iterator<Map<String,Object>> iter = sealList.iterator();
							while(iter.hasNext()) {
								Map<String,Object> map = iter.next();
								if(MapUtils.getIntValue(map, "status")==7) {
									iter.remove();
								}
							}
						}
					}
				
			      //已存入的要件模板数据，去除对应的初始化要件模板数据

			      result=initDataTemplate(params,httpUtil);//若不存在存要件记录
			      
			      List<Map<String,Object>> modelRiskList=(List<Map<String, Object>>) MapUtils.getObject(result, "riskElement");
			      
			      List<Map<String,Object>> modelReceivableList=(List<Map<String, Object>>) MapUtils.getObject(result, "receivableElement");
			      if(modelRiskList!=null&&modelRiskList.size()>0) {
				    	  Iterator<Map<String,Object>> iteratorRisk = modelRiskList.iterator();
						  	
					        while(iteratorRisk.hasNext()){
					        	Map<String,Object> o = iteratorRisk.next();
					        	String oTitle=MapUtils.getString(o, "title");
					        	
					            for(Map<String,Object> risk:riskList) {
					            	String riskTitle=MapUtils.getString(risk, "title");
					            	if(oTitle!=null&&riskTitle!=null&&oTitle.equals(riskTitle)) {
					            		iteratorRisk.remove();
					            		//o= risk;
					            		break;
					            	}
					            }
					        }
				        //需要按顺序替换
				        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();    
				        riskList.addAll(modelRiskList);
					    result.put("riskElement",riskList);
			      }
			      
			      if(modelReceivableList!=null&&modelReceivableList.size()>0) {
				        Iterator<Map<String,Object>> iteratorReceivable = modelReceivableList.iterator();
					  	
				        while(iteratorReceivable.hasNext()){
				        	Map<String,Object> o = iteratorReceivable.next();
				        	String oTitle=MapUtils.getString(o, "title");
				        	
				            for(Map<String,Object> back:backList) {
				            	String backTitle=MapUtils.getString(back, "title");
				            	if(oTitle!=null&&backTitle!=null&&oTitle.equals(backTitle)) {
				            		iteratorReceivable.remove();
				            		//o = back;
				            		break;
				            	}
				            }
				        }
				        //需要按顺序替换
				        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				        backList.addAll(modelReceivableList);
				        result.put("receivableElement", backList);
			      }
			      
			     /* result.put("riskElement", riskList);
			        
			      result.put("receivableElement", backList);
			      
			      result.put("sealElement", sealList);*/
				        
			  	log.info("加载已存要件数据result"+result);
				}

			}else {
				List<ElementListDto>	elementList=elementMapper.selectElementByOrderNo(params);
			 	if(elementList!=null&&elementList.size()>0) {//若无订单，则表示第一次存入，加载空模板
			 		elementListDto=elementList.get(0);
			 		String ids[]= {};
			 		
			 		
			 		//String currentBoxElementSet=elementListDto.getCurrentBoxElementSet();
			 		params.put("sealDepartmentId", elementListDto.getSealDepartmentId());
			 		Map<String,Object> sealDepartmentMap=sealDepartmentMapper.selectSealDepartmentInfoById(params);
			 		//查询改存公章和改还公章
//			 		List<Map<String, Object>> tempList = accessFlowMapper.selectLastAccessByMap(params);
//			 		String elementSet = (String) tempList.get(0).get("elementSet");
//			 		String currentBoxElementSet=MapUtils.getString(sealDepartmentMap, "currentBoxElementSet");
			 		String currentBoxElementSet = elementListDto.getCurrentBoxElementSet();
				 		if(currentBoxElementSet!=null&&!currentBoxElementSet.equals("")) {
				 			ids=currentBoxElementSet.split(",");
				 			//查,还公章详情
//				 			if(StringUtil.isNotBlank(elementSet)) {
//				 				ids = elementSet.split(",");
//				 			}
				 			List<Map<String,Object>> elementFileList=elementFileMapper.selectElementFileListbyOrderNo(params);
				 			if(elementFileList!=null&&elementFileList.size()>0) {
				 				result=getSealDataTemplate(elementFileList,httpUtil);
				 			}else {//当前要件id在要件表中查询不出要件
				 				result=initDataTemplate(params,httpUtil);
				 			}
				 		}else {//当前要件箱中公章要件已经全部取出
			 				result=initDataTemplate(params,httpUtil);
			 			}
				 		
				 		if(result==null) {
				 			result=new HashMap<String, Object>();
				 		}
				 		
				 		result.put("sealDepartment", elementListDto.getSealDepartment());
					 	result.put("sealDepartmentId", elementListDto.getSealDepartmentId());
				 		
			 		}else {
			 			result=initDataTemplate(params,httpUtil);
			 		}	
			 	log.info("加载公章："+result);
			}

			//返回
			//if(orderType.equals("1")) {
			 List<ElementListDto>	elementList=elementMapper.selectElementByOrderNo(params);
			 	if(elementList!=null&&elementList.size()>0) {
			 		elementListDto=elementList.get(0);
			 		result.put("customerName", elementListDto.getCustomerName());
			 		
			 		result.put("channelManagerName", elementListDto.getChannelManagerName());
			 		result.put("channelManagerUid", elementListDto.getChannelManagerUid());
			 		
			 		result.put("acceptMemberName", elementListDto.getAcceptMemberName());
			 		result.put("acceptMemberUid", elementListDto.getAcceptMemberUid());
			 		
			 		result.put("cityCode", elementListDto.getCityCode());
			 		result.put("cityName", elementListDto.getCityName());
			 		result.put("orderNo", elementListDto.getOrderNo());
			 		
			 		result.put("creditType", elementListDto.getCreditType());
			 		result.put("creditTypeUid", elementListDto.getCreditTypeUid());
			 		
			 		result.put("hasRiskElement", elementListDto.getHasRiskElement());
			 		result.put("hasPublicBusiness", elementListDto.getHasPublicBusiness());
			 		
			 		result.put("borrowingAmount", elementListDto.getBorrowingAmount());
			 		result.put("borrowingDay", elementListDto.getBorrowingDay());
			 		
			 		result.put("boxNo", elementListDto.getBoxNo());
			 	} 
			 	log.info("添加基础信息后："+result);
			 	
				//每次图片数据都是叠加的，查询最后一次还记录的图片即可
			 /*	List<Map<String, Object>> accessFlowList=accessFlowMapper.selectLastAccessByMap(params);
				
			 	if(accessFlowList!=null&&accessFlowList.size()>0) {
			 		result.put("riskPicture", MapUtils.getString(accessFlowList.get(0), "riskPicture"));
					
					result.put("receivablePicture", MapUtils.getString(accessFlowList.get(0), "receivablePicture"));
					
					result.put("elsePicture", MapUtils.getString(accessFlowList.get(0), "elsePicture"));
					
					result.put("sealPicture", MapUtils.getString(accessFlowList.get(0), "sealPicture"));
			 	}*/
				
				
			//}
			 	/*	//1.在要件表中查询是否存在信贷同步过来的订单要件
			 	List<Map<String, Object>> element_list=elementFileMapper.selectElementFileListbyOrderNo(params);
			 	if(element_list!=null&&element_list.size()>0) {
			 		//3.拼装固定模板及多出固定模板的要件
			 	}else {
			 		//查询出要件
			 		Map riskMap=foreclosureTypeMapper.selectRiskElement(params);
			 		List<Map<String,Object>> riskList=joinRiskElement(riskMap);
			 		
			 		Map receivableMap=paymentTypeMapper.selectReceivableElement(params);
			 		
			 		List<Map<String,Object>> receivableList=joinRiskElement(receivableMap);
			 	}
			 	
			 	//2.若存在拼接成相应的要件格式的要件集合
*/				
			
	
		}else {
			result=JsonUtil.parseJsonToMap(result.get("content").toString());
			log.info("暂存要件："+result);
		}
/*		if(orderType.equals("3")) {
		String 	currentBoxElementSet=MapUtils.getString(result, "currentBoxElementSet");
			if(currentBoxElementSet!=null&&!currentBoxElementSet.equals("")) {
				result.put("sealMark", operationElementName(currentBoxElementSet));
			}
		}*/
		
		log.info("返回点击存按钮的空模板信息"+result);
		return result;
	}
	
	@Override
	//保存暂存存要件信息方法
	public void saveSuspendedStoreButtonInfo(Map<String, Object> params) {
		Map<String, Object> result=new HashMap<String, Object>();
		
		Integer hasRiskElement=MapUtils.getInteger(params, "hasRiskElement");
		if(hasRiskElement==null) {
			params.put("hasRiskElement", 0);
		}
		
		Integer hasPublicBusiness=MapUtils.getInteger(params, "hasPublicBusiness");
		if(hasPublicBusiness==null) {
			params.put("hasPublicBusiness", 0);
		}
		
		
		params.put("templateType", MapUtils.getString(params, "orderType"));
		params.put("currentHandlerUid", MapUtils.getString(params, "uid"));
		params.put("content", JsonUtil.BeanToJson(params));
		//先查询是否有暂存数据
		result=accessFlowTempMapper.selectAccessFlowTemp(params);
		if(result==null) {//新增
			accessFlowTempMapper.insertAccessFlowTemp(params);
		}else {//修改
			params.put("id", result.get("id"));
			accessFlowTempMapper.updateAccessFlowTempById(params);
		}
		
	}

	

	@Override
	public int insertAccessFlowTemp(Map<String, Object> params) {
		
		return accessFlowTempMapper.insertAccessFlowTemp(params);
	}

	@Override
	//保存取要件信息
	@Transactional(rollbackFor=Exception.class)
	public void saveTakeElementInfo(Map<String, Object> params) {
		log.info("执行取要件");
		long beginTime=System.currentTimeMillis();
		String elementSet=MapUtils.getString(params, "elementSet");
	/*	String elements="";//当前要件箱中的要件
		//查询当前箱子中存在的要件
		List<Map<String, Object>> box_List=boxBaseMapper.selectBoxBaseByBoxNo(params);
		if(box_List!=null&&box_List.size()>0) {
			Map<String, Object>	box_Map=box_List.get(0);
			elements=box_Map.get("elements").toString();
		}*/
		params.put("operationType", 2);
		String currentBoxElementSet="";//当前要件箱中的要件
		ElementListDto	elementListDto=null;
		//查询当前箱子中存在的要件
		List<ElementListDto> element_List=elementMapper.selectElementByOrderNo(params);
		if(element_List!=null&&element_List.size()>0) {
			elementListDto=element_List.get(0);
			currentBoxElementSet=elementListDto.getCurrentBoxElementSet();
			//删除不在要件柜中的要件id
			if(StringUtil.isNotBlank(currentBoxElementSet)) {
				String ids[]= currentBoxElementSet.split(",");
				List<Map<String,Object>> mapList = elementFileMapper.selectElementFileList(ids);
				for (Map<String, Object> map : mapList) {
					if(MapUtils.getIntValue(map, "status")!=3
							&&MapUtils.getIntValue(map, "status")!=5
							&&MapUtils.getIntValue(map, "status")!=6) {
						currentBoxElementSet=addReduceBoxElement(currentBoxElementSet,MapUtils.getString(map, "id"),2);
					}
				}
			}
			currentBoxElementSet=addReduceBoxElement(currentBoxElementSet,elementSet,2);
		}
		//计算此处操作后，箱子中的要件
		//String currentBoxElementSet=addReduceBoxElement(elements,params.get("elementSet").toString(),2);//当前要件箱中的要件
		//归还不删除列表的要件id
//		params.put("currentBoxElementSet", currentBoxElementSet);
		params.put("elements", currentBoxElementSet);
		
		
		params.put("id", params.get("dbId"));//用于修改借用审批信息表中该数据的借用状态
		//更新為已取
		xAuditBaseMapper.updateAuditBaseById(params);
		
		AuditBaseDto audit = auditBaseMapper.selectElementAuditBaseById(MapUtils.getIntValue(params, "dbId"));
		if(audit.getExtendId()>0) {//取出延長的要件，原有審批也修改為已取出
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("id", audit.getExtendId());
			xAuditBaseMapper.updateAuditBaseById(m);
		}
		//修改要件订单表对应箱子当前存在的要件集合
		elementMapper.updateTimeByOrderNo(params);
		if(elementListDto!=null&&elementListDto.getOrderStatus()!=5) {
			params.put("orderStatus", 3);
			elementMapper.UpdateElementStatusByOrderNo(params);//修改为已借出状态
		}
		currentBoxElementSet = currentBoxElementSet.replace(elementSet, "").replace(",,", ",");
		if(StringUtil.isNotBlank(currentBoxElementSet)&&currentBoxElementSet.startsWith(",")) {
			currentBoxElementSet = currentBoxElementSet.substring(1,currentBoxElementSet.length());
		}
		if(StringUtil.isNotBlank(currentBoxElementSet)&&currentBoxElementSet.endsWith(",")) {
			currentBoxElementSet = currentBoxElementSet.substring(0,currentBoxElementSet.length()-1);
		}
		params.put("currentBoxElementSet", currentBoxElementSet);
		int num=accessFlowMapper.insertAccessFlowRecorde(params);//保存取记录操作流水
		
		
		String ids[]={};
		
		if(elementSet!=null&&!elementSet.equals("")) {
			ids=elementSet.split(",");
		}
		List<Map<String,Object>> elementList=elementFileMapper.selectElementFileList(ids);
		
		//更新要件状态
		List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
		for (String t : ids) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", Integer.parseInt(t));
			map.put("status", 4);//已借出
			tList.add(map);
		}
		elementFileMapper.updateElementFile(tList);
		
		if(elementList!=null&&elementList.size()>0) {
			for(Map<String,Object> o:elementList) {
				o.put("elementId", MapUtils.getString(o, "id"));
				o.put("beginBorrowElementTime", MapUtils.getString(params, "beginBorrowElementTime"));
				o.put("endBorrowElementTime", MapUtils.getString(params, "endBorrowElementTime"));
				o.put("borrowDay", MapUtils.getString(params, "borrowDay"));
				o.put("orderNo", MapUtils.getString(params, "orderNo"));
				o.put("dbId", MapUtils.getString(params, "dbId"));
				o.put("elementOperationId",MapUtils.getString(params, "id"));	
			}
		}
			
		borrowElementMapper.insertBorrowElementRecorde(elementList);//存入借要件集合表
		
		long endTime=System.currentTimeMillis();
		
		log.info("保存saveTakeElementInfo方法执行时间:"+(endTime-beginTime));
		
	}
	
	
	
	//返回取要件信息
	public Map<String, Object> retrunTakeButtonInfo(Map<String, Object> params) {
		//查询出最新一条未取出的借要件审批记录
		List<Map<String, Object>> AuditBase_List=xAuditBaseMapper.selectAuditBaseList(params);
		Map<String, Object> audit_Map=null;
		String ids[]= {};
		if(AuditBase_List!=null&&AuditBase_List.size()>0) {
			audit_Map=AuditBase_List.get(0);
		}
		
		String elementIds=MapUtils.getString(audit_Map,"elementIds");
		if(elementIds!=null&&!elementIds.equals("")) {
			ids=elementIds.split(",");
		}

		//查询得到要件集合
		List<Map<String, Object>> element_List=elementFileMapper.selectElementFileList(ids);//查询得到要件集合
		
		List<Map<String, Object>> riskList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> backList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sealList=new  ArrayList<Map<String, Object>>();
		
		
		HttpUtil httpUtil=new HttpUtil();
		
		Map<String, Object> returnMap=new HashMap<String, Object>();
		//待组装数据模板
		if(element_List!=null&&element_List.size()>0) {
			
			returnMap.put("boxNo", MapUtils.getString(element_List.get(0), "boxNo"));//从要件对象中取要件箱号
			
			for(Map<String, Object> o:element_List) {
				if(o.get("elementType").toString().equals("1")) {//回款要件
					backList.add(fullDataTemplate(o,httpUtil,"1"));
				}
				
				if(o.get("elementType").toString().equals("2")) {//风控要件
					riskList.add(fullDataTemplate(o,httpUtil,"1"));
				}
				
				if(o.get("elementType").toString().equals("3")) {//公章要件
					sealList.add(fullDataTemplate(o,httpUtil,"1"));
				}
			}
		}
		
	
		
		String dbId=MapUtils.getString(audit_Map,"id");//关联的审批ID
		
		returnMap.put("dbId", dbId);

		List<Map<String, Object>> auditFlow_List=xAuditFlowMapper.selectAuditFlowListByDbId(returnMap);
		
		returnMap.put("riskElement", riskList);
		
		returnMap.put("receivableElement", backList);
		
		returnMap.put("sealElement", sealList);
		
		List<Map<String,Object>> auditList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : auditFlow_List) {
				map.put("current", true);
				Integer state = MapUtils.getInteger(map,"state");
				switch (state) {
					case 2: map.put("stateStr", "已审批通过");break;
					case 3: map.put("stateStr", "已拒绝");break;
					case 4: map.put("stateStr", "已转交给"+MapUtils.getString(map, "deliverTo",""));
					default: break;
				}
			auditList.add(map);
		}
		returnMap.put("auditors", auditList);//一批次审批人
		
		returnMap.put("applierName", MapUtils.getString(audit_Map,"applierName"));//申请人
		returnMap.put("customerName", MapUtils.getString(audit_Map,"customerName"));//客户姓名
		
		returnMap.put("beginBorrowElementTime", StringToDate(MapUtils.getString(audit_Map,"beginTime")));
		returnMap.put("endBorrowElementTime",StringToDate(MapUtils.getString(audit_Map,"endTime")));
		
		returnMap.put("remark",MapUtils.getString(audit_Map,"reason"));
		
		
		returnMap.put("fileToSeal",MapUtils.getString(audit_Map,"fileToSeal"));
		
		returnMap.put("sealFileCount",MapUtils.getString(audit_Map,"sealFileCount"));
		
		returnMap.put("fileType",MapUtils.getString(audit_Map,"fileType"));
		
		String fileImgUrl="";
		String lastFileImgUrl="";
		fileImgUrl=MapUtils.getString(audit_Map,"fileImgUrl");
		if(fileImgUrl!=null&&!fileImgUrl.equals("")) {
			JSONArray jArray = JSONArray.fromObject(fileImgUrl);
			if(jArray!=null) {
				for (Object object : jArray) {
					lastFileImgUrl=lastFileImgUrl+object+",";
				}
			}	
		}

		if(!lastFileImgUrl.equals("")) {
			lastFileImgUrl=lastFileImgUrl.substring(0,lastFileImgUrl.length()-1);
		}
		
		
		returnMap.put("fileImgUrl",lastFileImgUrl);
		
	
		returnMap.put("borrowDay", MapUtils.getString(audit_Map,"borrowDay"));//待计算
		
		returnMap.put("sealDepartment", MapUtils.getString(audit_Map,"sealDepartment"));//待计算
		
		return returnMap;
	}
	
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
	
	/**
	 * 返回审批人信息
	 * @param dbId
	 * @return
	 */
	public List<Map<String,Object>> queryAuditors(int dbId){
		List<Map<String, Object>> auditors = selectAuditHistoryById(dbId,1);
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
		return auditorsMapList;
	}
	
	@Override
	//返回超时未还信息
	public Map<String, Object> retrunTimeOutInfo(Map<String, Object> params) {
		log.info("超时未还参数params"+params);
		List<ElementListDto> ElementListDtoList=elementMapper.selectElementByOrderNo(params);
					
		
		String elementOperationId=MapUtils.getString(params, "elementOperationId");
		String ids[]= {};
		String elementIds="";

		HttpUtil httpUtil=new HttpUtil();
		Map<String, Object> returnMap=new HashMap<String, Object>();
		if(elementOperationId!=null&&!elementOperationId.equals("")) {//开箱超时未还
			params.put("id", elementOperationId);
			Map<String, Object> open_Map=accessFlowMapper.selectAccessFlowById(params);
			//elementIds=MapUtils.getString(open_Map,"elementSet");//开箱操作记录中拿要件
			
			returnMap.put("operationTime", StringToDate(MapUtils.getString(open_Map,"operationTime")));
			
			returnMap.put("beginBorrowElementTime", StringToDate(MapUtils.getString(open_Map,"beginBorrowElementTime")));
			
			returnMap.put("endBorrowElementTime",StringToDate(MapUtils.getString(open_Map,"endBorrowElementTime")));
			
			returnMap.put("remark",MapUtils.getString(open_Map,"remark"));
			
			
			returnMap.put("applierName", MapUtils.getString(open_Map,"currentHandler"));//申请人
			
			returnMap.put("applierUid", MapUtils.getString(open_Map,"currentHandlerUid"));//申请人uid
			
			if(ElementListDtoList!=null&&ElementListDtoList.size()>0) {
				
			returnMap.put("sealDepartment",ElementListDtoList.get(0).getSealDepartment());
			
			returnMap.put("sealDepartmentId",ElementListDtoList.get(0).getSealDepartmentId());
			
			returnMap.put("customerName", ElementListDtoList.get(0).getCustomerName());//客户姓名
			}
			
			
			returnMap.put("borrowDay", MapUtils.getString(open_Map,"borrowDay"));//待计算	
			
			
			returnMap.put("fileToSeal",MapUtils.getString(open_Map,"fileToSeal"));
			returnMap.put("fileType",MapUtils.getString(open_Map,"fileType"));
			returnMap.put("sealFileCount",MapUtils.getString(open_Map,"sealFileCount"));
			String fileImgUrl = MapUtils.getString(open_Map,"fileImgUrl");
			String lastFileImgUrl="";
			if(fileImgUrl!=null&&!fileImgUrl.equals("")) {
				JSONArray jArray = JSONArray.fromObject(fileImgUrl);
				if(jArray!=null) {
					for (Object object : jArray) {
						lastFileImgUrl=lastFileImgUrl+object+",";
					}
				}	
			}

			if(!lastFileImgUrl.equals("")) {
				lastFileImgUrl=lastFileImgUrl.substring(0,lastFileImgUrl.length()-1);
			}
			returnMap.put("fileImgUrl",lastFileImgUrl);
				 
		}else {//取要件超时未还
			//查询出借要件审批记录	
			
			 int dbId=MapUtils.getIntValue(params,"dbId");
			 params.put("id", dbId);
			 
			 params.put("operationType", 2);
			 
			//查询存取记录流水表,查询取记录详情
			 
			Map<String,Object> takeMap=accessFlowMapper.selectAccessFlowbyDbId(params);
			 
			//查询审信息表
			 AuditBaseDto auditBaseDto = auditBaseMapper.selectElementAuditBaseById(dbId);
			 
			 //elementIds=MapUtils.getString(audit_Map,"elementIds");

			 returnMap.put("dbId",dbId);
			 
			/*if(dbId==null&&!dbId.equals("")) {
				dbId=MapUtils.getString(audit_Map,"id");//关联的审批ID
			}*/
			
			List<Map<String, Object>> auditFlow_List=xAuditFlowMapper.selectAuditFlowListByDbId(returnMap);
			
			returnMap.put("operationTime", StringToDate(MapUtils.getString(takeMap,"operationTime")));
			
			returnMap.put("auditors", auditFlow_List);//一批次审批人

			returnMap.put("applierName", auditBaseDto.getApplierName());//申请人
			
			if(ElementListDtoList!=null&&ElementListDtoList.size()>0) {
				
				returnMap.put("customerName", ElementListDtoList.get(0).getCustomerName());//客户姓名
			}
			
			returnMap.put("beginBorrowElementTime", DateUtil.getDateByFmt(auditBaseDto.getBeginTime(), "yyyy-MM-dd HH:mm"));
			
			returnMap.put("endBorrowElementTime",DateUtil.getDateByFmt(auditBaseDto.getEndTime(), "yyyy-MM-dd HH:mm"));
			
			returnMap.put("remark",auditBaseDto.getReason());
			
			returnMap.put("sealDepartment", auditBaseDto.getSealDepartment());
			
			returnMap.put("borrowDay", auditBaseDto.getBorrowDay());//待计算	
			//公章超时未还信息
			if(auditBaseDto.getType() == 3){
				returnMap.put("fileToSeal",auditBaseDto.getFileToSeal());
				returnMap.put("fileType",auditBaseDto.getFileType());
				returnMap.put("sealFileCount",auditBaseDto.getSealFileCount());
				String fileImgUrl = auditBaseDto.getFileImgUrl();
				String lastFileImgUrl="";
				if(fileImgUrl!=null&&!fileImgUrl.equals("")) {
					JSONArray jArray = JSONArray.fromObject(fileImgUrl);
					if(jArray!=null) {
						for (Object object : jArray) {
							lastFileImgUrl=lastFileImgUrl+object+",";
						}
					}	
				}

				if(!lastFileImgUrl.equals("")) {
					lastFileImgUrl=lastFileImgUrl.substring(0,lastFileImgUrl.length()-1);
				}
				returnMap.put("fileImgUrl",lastFileImgUrl);
			}
		}
				//查询所有要件
				elementIds = ElementListDtoList.get(0).getCurrentBoxElementSet();
				if(elementIds!=null&&!elementIds.equals("")) {
					ids=elementIds.split(",");
				}

				//查询得到要件集合
				List<Map<String, Object>> element_List=elementFileMapper.selectElementFileList(ids);//查询得到要件集合
				
				List<Map<String, Object>> riskList=new  ArrayList<Map<String, Object>>();
				List<Map<String, Object>> backList=new  ArrayList<Map<String, Object>>();
				List<Map<String, Object>> sealList=new  ArrayList<Map<String, Object>>();
		
				//待组装数据模板
				if(element_List!=null&&element_List.size()>0) {
					returnMap.put("boxNo", MapUtils.getString(element_List.get(0), "boxNo"));//从要件对象中取要件箱号
					
					for(Map<String, Object> o:element_List) {
						if(MapUtils.getIntValue(o, "status")==7) {
							continue;
						}
						if(o.get("elementType").toString().equals("1")) {//回款要件
							backList.add(fullDataTemplate(o,httpUtil,"3"));
						}
						
						if(o.get("elementType").toString().equals("2")) {//风控要件
							riskList.add(fullDataTemplate(o,httpUtil,"3"));
						}
						
						if(o.get("elementType").toString().equals("3")) {//公章要件
							sealList.add(fullDataTemplate(o,httpUtil,"3"));
						}
					}
				}
				
				String msgId=MapUtils.getString(params, "msgId");
				if(msgId!=null&&!msgId.equals("")) {
					elementSystemMessageMapper.updateMessageHasRead(params);
				}
				

				returnMap.put("riskElement", riskList);
				
				returnMap.put("receivableElement", backList);
				
				returnMap.put("sealElement", sealList);
	
				return returnMap;
	}

	
	@Override
	//保存退要件信息
	@Transactional(rollbackFor=Exception.class)
	public void saveRefundElementInfo(Map<String, Object> params) {
		/*String elements="";//当前要件箱中的要件
		//查询当前箱子中存在的要件
		List<Map<String, Object>> box_List=boxBaseMapper.selectBoxBaseByBoxNo(params);
		if(box_List!=null&&box_List.size()>0) {
			Map<String, Object>	box_Map=box_List.get(0);
			elements=box_Map.get("elements").toString();
		}*/
		log.info("执行退要件"+params);
		String elements="";//当前要件箱中的要件
		int orderType=0;
		ElementListDto	elementListDto=null;
		//查询当前箱子中存在的要件
		List<ElementListDto> element_List=elementMapper.selectElementByOrderNo(params);
		if(element_List!=null&&element_List.size()>0) {
			elementListDto=element_List.get(0);
			elements=elementListDto.getCurrentBoxElementSet();
			orderType=elementListDto.getOrderType();
		}
		
		//计算此处操作后，箱子中的要件
		String currentBoxElementSet=addReduceBoxElement(elements,params.get("elementSet").toString(),2);//当前要件箱中的要件

		params.put("currentBoxElementSet", currentBoxElementSet);
		
		params.put("elements", currentBoxElementSet);
		
		//修改tbl_element_box_base表中箱子中存在的要件,操作ID,订单状态，若不存在要件，则修改订单状态为已退还
		
		if(currentBoxElementSet.equals("")) {//表示要件已经退还完,恢复箱子的使用状态
			Map<String,Object> boxMap=new HashMap<String,Object>();
			boxMap.put("useStatus", 0);
			boxMap.put("orderNo", "");
			boxMap.put("boxNo", MapUtils.getString(params, "boxNo"));
			boxBaseMapper.updateBoxBaseByBoxNo(boxMap);
		}
		//修改要件订单表对应箱子当前存在的要件集合		
		elementMapper.UpdateElementByOrderNo(params);
		if(orderType==1||orderType==2) {
				params.put("orderStatus", 6);
				elementMapper.UpdateElementStatusByOrderNo(params);//修改为已退要件	
		}
		

		accessFlowMapper.insertAccessFlowRecorde(params);//保存取记录操作流水
		
	}
	
	
	@Override
	//点击退按钮返回信息
	public Map<String, Object> retrunRefundButtonInfo(Map<String, Object> params) {
		Map<String, Object> returnMap=new HashMap<String, Object>();
		HttpUtil httpUtil=new HttpUtil();
		String elements="";//当前要件箱中的要件
		String state="";
		String ids[]= {};
		//查询当前箱子中存在的要件
		List<ElementListDto> element_List=elementMapper.selectElementByOrderNo(params);
		if(element_List!=null&&element_List.size()>0) {
			ElementListDto	elementListDto=element_List.get(0);
			returnMap.put("boxNo", elementListDto.getBoxNo());
			elements=elementListDto.getCurrentBoxElementSet();
			if(elements!=null) {
				ids=elements.split(",");
			}
			state=elementListDto.getState();
		}
		
		List<Map<String, Object>> element_File_List=elementFileMapper.selectElementFileList(ids);//查询得到要件集合
		
		List<Map<String, Object>> riskList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> backList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sealList=new  ArrayList<Map<String, Object>>();

		//待组装数据模板
		if(element_List!=null&&element_List.size()>0) {
			for(Map<String, Object> o:element_File_List) {
				if(MapUtils.getIntValue(o, "status")==7) {
					continue;
				}
				if(o.get("elementType").toString().equals("1")) {//回款要件
					backList.add(fullDataTemplate(o,httpUtil,"1"));
				}
				
				if(o.get("elementType").toString().equals("2")) {//风控要件
					riskList.add(fullDataTemplate(o,httpUtil,"1"));
				}
				
				if(o.get("elementType").toString().equals("3")) {//公章要件
					sealList.add(fullDataTemplate(o,httpUtil,"1"));
				}
			}
		}
		
		if(state!=null) {
			if(state.equals("订单已停止")||state.equals("已关闭")||state.equals("待收罚息")) {
				returnMap.put("state",state);
			}
		}
		returnMap.put("riskElement", riskList);
		
		returnMap.put("receivableElement", backList);
		
		returnMap.put("sealElement", sealList);
		
		return returnMap;
	}
	
	@Override
	//要件列表点击进入的要件详情信息
	public Map<String, Object> selectElementDetailInfo(Map<String, Object> params) {
		//查询该订单信息
		//List<ElementListDto> list=elementMapper.selectElementByOrderNo(params);
		
		
		String elements="";//当前要件箱中的要件
		String ids[]= {};
		/*//查询当前箱子中存在的要件
		List<Map<String, Object>> box_List=boxBaseMapper.selectBoxBaseByBoxNo(params);
		if(box_List!=null&&box_List.size()>0) {
			Map<String, Object>	box_Map=box_List.get(0);
			elements=box_Map.get("elements").toString();
			ids=elements.split(",");
		}
		
		*/
	
		//查询该订单信息
		ElementListDto	elementListDto=new ElementListDto();
		List<ElementListDto> element_order_List=elementMapper.selectElementByOrderNo(params);
		String orderNo = "";
		if(element_order_List!=null&&element_order_List.size()>0) {
			elementListDto=element_order_List.get(0);
			elements=elementListDto.getCurrentBoxElementSet();
			ids=elements.split(",");
			orderNo = elementListDto.getOrderNo();
		}
	
		//查询当前箱子中存在的要件
		List<Map<String, Object>> element_List=elementFileMapper.selectElementFileList(ids);//查询得到要件集合
		
		List<Map<String, Object>> riskList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> backList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sealList=new  ArrayList<Map<String, Object>>();
		
		
		HttpUtil httpUtil=new HttpUtil();
		//待组装数据模板
		if(element_List!=null&&element_List.size()>0) {
			for(Map<String, Object> o:element_List) {
				o.put("orderNo", orderNo);
				if(o.get("elementType").toString().equals("1")) {//回款要件
					backList.add(fullDataTemplate(o,httpUtil,"1"));
				}
				
				if(o.get("elementType").toString().equals("2")) {//风控要件
					riskList.add(fullDataTemplate(o,httpUtil,"1"));
				}
				
				if(o.get("elementType").toString().equals("3")) {//公章要件
					sealList.add(fullDataTemplate(o,httpUtil,"1"));
				}
			}
		}
		
		Map<String, Object> returnMap=new HashMap<String, Object>();
		returnMap.put("riskElement", riskList);
		
		returnMap.put("receivableElement", backList);
		
		returnMap.put("sealElement", sealList);
		
		returnMap.put("customerName", elementListDto.getCustomerName());
		
		returnMap.put("borrowingAmount", elementListDto.getBorrowingAmount());
		
		returnMap.put("borrowingDay", elementListDto.getBorrowingDay());
		
		returnMap.put("channelManagerName", elementListDto.getChannelManagerName());
		
		returnMap.put("acceptMemberName", elementListDto.getAcceptMemberName());
		
		returnMap.put("cityName", elementListDto.getCityName());
		
		returnMap.put("creditType", elementListDto.getCreditType());
		
		returnMap.put("boxNo", elementListDto.getBoxNo());
		
		return returnMap;
	}
	
	
	@Override
	//获取存取记录列表
	public List<Map<String, Object>> selectAccessFlowList(Map<String, Object> params) {
		
		//按时间倒序排列，获取存取记录流水列表
		List<Map<String, Object>> accessFlow_List=accessFlowMapper.selectAccessFlowList(params);
		
		for(Map<String, Object> o:accessFlow_List) {
			Map<String, Object> markMap=operationElementName(MapUtils.getString(o,"elementSet"),1);
			//获取客户基本信息
			if(MapUtils.getIntValue(o, "operationType")==12) {
				o.put("customerMark", queryCustomerInfo(o));
			}else {
				o.put("riskMark", MapUtils.getString(markMap, "riskMark"));
				o.put("receivableMark", MapUtils.getString(markMap, "receivableMark"));
				o.put("sealMark", MapUtils.getString(markMap, "sealMark"));
			}
			o.put("operationTime",StringToDate(MapUtils.getString(o, "operationTime")));
			o.put("beginBorrowElementTime", StringToDate(MapUtils.getString(o, "beginBorrowElementTime")));
			o.put("endBorrowElementTime", StringToDate(MapUtils.getString(o, "endBorrowElementTime")));
		}
		return accessFlow_List;
	}
	
	public String queryCustomerInfo(Map<String,Object> params){
		String str="";
		List<ElementListDto> list = elementMapper.selectElementByOrderNo(params);
		ElementListDto dto = list.get(0);
		str+="客户姓名("+dto.getCustomerName()+"),";
		str+="渠道经理("+dto.getChannelManagerName()+"),";
		str+="受理员("+dto.getAcceptMemberName()+"),";
		str+="业务城市("+dto.getCityName()+"),";
		str+="业务类型("+dto.getCreditType()+"),";
		str+="借款金额("+dto.getBorrowingAmount()+"万),";
		str+="借款期限("+dto.getBorrowingDay()+"天)";
		return str;
	}

	
	@Override
	//获取存取记录详情信息
	public Map<String, Object> selectAccessFlowDetailInfo(Map<String, Object> params) {
	
		String elementSet="";//此次操作处理的要件
		String ids[]= {};
		Map<String, Object> accessFlowMap=accessFlowMapper.selectAccessFlowById(params);
		int operationType = MapUtils.getIntValue(accessFlowMap, "operationType");
		//返回客户姓名，渠道经理，受理员，是否对公业务，是否已收到客户的风控要件
		params.put("orderNo", MapUtils.getString(accessFlowMap, "orderNo"));
		List<ElementListDto> list = elementMapper.selectElementByOrderNo(params);
		ElementListDto element = list.get(0);
		accessFlowMap.put("cityName", element.getCityName());
		accessFlowMap.put("creditType", element.getCreditType());
		accessFlowMap.put("customerName", element.getCustomerName());
		accessFlowMap.put("channelManagerAcceptMemberName", element.getChannelManagerName()+"/"+element.getAcceptMemberName());
		accessFlowMap.put("hasRiskElement", "1".equals(element.getHasRiskElement())?"收到":"未收到");
		accessFlowMap.put("hasPublicBusiness", "1".equals(element.getHasPublicBusiness())?"是":"否");
		accessFlowMap.put("borrowingAmount", element.getBorrowingAmount());
		accessFlowMap.put("borrowingDay", element.getBorrowingDay());
		accessFlowMap.put("borrowingAmountDay", element.getBorrowingAmount()+"万/"+element.getBorrowingDay()+"天");
		accessFlowMap.put("channelManagerAcceptMemberName", element.getChannelManagerName()+"/"+element.getAcceptMemberName());
		accessFlowMap.put("beginBorrowElementTime",StringToDate(MapUtils.getString(accessFlowMap, "beginBorrowElementTime")));
		accessFlowMap.put("endBorrowElementTime", StringToDate(MapUtils.getString(accessFlowMap, "endBorrowElementTime")));
		if(element.getOrderStatus()==6){
			accessFlowMap.put("canAdd", false);
		}
		elementSet=MapUtils.getString(accessFlowMap, "elementSet");
		if(elementSet!=null) {
			ids=elementSet.split(",");
		}
		//是否展示客户信息信息修改按钮
	    boolean canAddCustomer = true;
		String[] elementIds = element.getCurrentBoxElementSet().split(",");
		//查询所有的要件集合
		List<Map<String, Object>> elementAllList=elementFileMapper.selectElementFileList(elementIds);//查询得到要件集合
		if(elementAllList!=null&&elementAllList.size()>0) {
			for(Map<String, Object> o:elementAllList) {
				//要件状态为借用审批中和已借出时，不允许修改客户信息
				if("4".equals(MapUtils.getString(o, "status"))||"5".equals(MapUtils.getString(o, "status"))
						||"9".equals(MapUtils.getString(o, "status"))) {
					canAddCustomer = false;
				}
			}
		}
		//查询此次操作的要件集合
		List<Map<String, Object>> element_List = new ArrayList<Map<String,Object>>();
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("orderNo", MapUtils.getString(accessFlowMap, "orderNo"));
		m.put("operationAccessFlowId", MapUtils.getIntValue(params, "id"));
		m.put("flowType", 2);//修改后
		m.put("ids",elementSet);
		//存还详情查流水表
		List<Map<String,Object>> l = elementFileFlowMapper.selectElementFileFlowByOrderNo(m);
		int accessFlowId = 0;
		System.out.println("========查询存还详情operationType："+operationType);
		if(l!=null&&l.size()>0&&operationType==4) {
			System.out.println("查询存还详情operationType："+operationType);
			element_List=elementFileMapper.selectElementFileList(ids);//查询得到要件集合
			Iterator<Map<String,Object>> iter = l.iterator();
			while(iter.hasNext()) {
				Map<String,Object> map = iter.next();
				int flowId = MapUtils.getIntValue(map, "accessFlowId");
				if(accessFlowId ==0 ) {
					accessFlowId = flowId;
				}
				if(flowId!=accessFlowId) {
					iter.remove();//多次修改时，取最后一次的修改记录
				}
			}
			List<Map<String,Object>> elementList = l;
			Iterator<Map<String,Object>> it = element_List.iterator();
			List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
			while(it.hasNext()) {
				Map<String,Object> map = it.next();
				for(Map<String, Object> map2:elementList) {
					if(MapUtils.getIntValue(map, "id")==MapUtils.getIntValue(map2, "elementId")) {
						int id = MapUtils.getIntValue(map, "id");
						map=map2;
						map.put("id", id);
					}
				}
				lists.add(map);
			}
			element_List = lists;
		}else {
			element_List=elementFileMapper.selectElementFileList(ids);//查询得到要件集合
		}
		
		List<Map<String, Object>> riskList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> backList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sealList=new  ArrayList<Map<String, Object>>();
		
		HttpUtil httpUtil=new HttpUtil();
		//待组装数据模板
		if(element_List!=null&&element_List.size()>0) {
			for(Map<String, Object> o:element_List) {
				o.put("operationType", operationType);
				if(o.get("elementType").toString().equals("1")) {//回款要件
					backList.add(fullDataTemplate(o,httpUtil,MapUtils.getString(o, "status")));
				}
				
				if(o.get("elementType").toString().equals("2")) {//风控要件
					riskList.add(fullDataTemplate(o,httpUtil,MapUtils.getString(o, "status")));
				}
				
				if(o.get("elementType").toString().equals("3")) {//公章要件
					sealList.add(fullDataTemplate(o,httpUtil,MapUtils.getString(o, "status")));
				}
			}
		}
		accessFlowMap.put("canAddCustomer", canAddCustomer);
		Map<String, Object> returnMap=new HashMap<String, Object>();
		accessFlowMap.put("riskElement", riskList);
		
		accessFlowMap.put("receivableElement", backList);
		
		accessFlowMap.put("sealElement", sealList);	
				
				
		params.put("orderNo", MapUtils.getString(accessFlowMap, "orderNo"));
		
		
		
		//查询出最新一条未取出的借要件审批记录
		String dbId=MapUtils.getString(accessFlowMap,"dbId");
			
		params.put("id", dbId);
		
		Map<String, Object>	audit_Map=xAuditBaseMapper.selectAuditBaseById(params);
		
		
	/*	List<Map<String, Object>> AuditBase_List=xAuditBaseMapper.selectAuditBaseList(params);
		Map<String, Object> audit_Map=null;
		if(AuditBase_List!=null&&AuditBase_List.size()>0) {
			audit_Map=AuditBase_List.get(0);
		}*/
		if(MapUtils.getIntValue(accessFlowMap, "operationType")!=6) {
			accessFlowMap.put("fileToSeal",MapUtils.getString(audit_Map,"fileToSeal"));
			
			accessFlowMap.put("sealFileCount",MapUtils.getString(audit_Map,"sealFileCount"));
			
			accessFlowMap.put("fileType",MapUtils.getString(audit_Map,"fileType"));
			
			String fileImgUrl="";
			String lastFileImgUrl="";
			fileImgUrl=MapUtils.getString(audit_Map,"fileImgUrl");
			if(fileImgUrl!=null&&!fileImgUrl.equals("")) {
				JSONArray jArray = JSONArray.fromObject(fileImgUrl);
				if(jArray!=null) {
					for (Object object : jArray) {
						lastFileImgUrl=lastFileImgUrl+object+",";
					}
				}	
			}

			if(!lastFileImgUrl.equals("")) {
				lastFileImgUrl=lastFileImgUrl.substring(0,lastFileImgUrl.length()-1);
			}

			accessFlowMap.put("fileImgUrl",lastFileImgUrl);
		}else {
			String fileImgUrl="";
			String lastFileImgUrl="";
			fileImgUrl = MapUtils.getString(accessFlowMap, "fileImgUrl");
			if(fileImgUrl!=null&&!fileImgUrl.equals("")) {
				JSONArray jArray = JSONArray.fromObject(fileImgUrl);
				if(jArray!=null) {
					for (Object object : jArray) {
						lastFileImgUrl=lastFileImgUrl+object+",";
					}
				}	
			}

			if(!lastFileImgUrl.equals("")) {
				lastFileImgUrl=lastFileImgUrl.substring(0,lastFileImgUrl.length()-1);
			}

			accessFlowMap.put("fileImgUrl",lastFileImgUrl);
		}
		ElementListDto	elementListDto=new ElementListDto();
		List<ElementListDto> element_order_List=elementMapper.selectElementByOrderNo(params);
		if(element_order_List!=null&&element_order_List.size()>0) {
			elementListDto=element_order_List.get(0);
		}
		
		accessFlowMap.put("boxNo", elementListDto.getBoxNo());
		
		accessFlowMap.put("orderType", elementListDto.getOrderType());//判断展示为公章页还是普通的审批页
		
		accessFlowMap.put("sealDepartment", elementListDto.getSealDepartment());//公章所属部门
		
		accessFlowMap.put("sealDepartmentId", elementListDto.getSealDepartmentId());//公章所属部门
		
		accessFlowMap.put("hasRiskElement", elementListDto.getHasRiskElement());//公章所属部门
		
		accessFlowMap.put("hasPublicBusiness", elementListDto.getHasPublicBusiness());//公章所属部门
		
		accessFlowMap.put("operationTime", StringToDate(MapUtils.getString(accessFlowMap, "operationTime")));
		
		if(operationType==2) {//取要件时的审批记录信息
			
			accessFlowMap.put("remark",MapUtils.getString(audit_Map,"reason"));
			
			params.put("dbId", MapUtils.getString(accessFlowMap, "dbId"));
			
			List<Map<String, Object>> auditFlow_List=xAuditFlowMapper.selectAuditFlowListByDbId(params);
			List<Map<String,Object>> auditList = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : auditFlow_List) {
					map.put("current", true);
					Integer state = MapUtils.getInteger(map,"state");
					switch (state) {
						case 2: map.put("stateStr", "已审批通过");break;
						case 3: map.put("stateStr", "已拒绝");break;
						case 4: map.put("stateStr", "已转交给"+MapUtils.getString(map, "deliverTo",""));
						default: break;
					}
				auditList.add(map);
			}

			accessFlowMap.put("auditors", auditList);//一批次审批人
			
		}
		return accessFlowMap;
	}


	@Override
	//点击获取还按钮信息
	public Map<String, Object> retrunButtonInfo(Map<String, Object> params) throws Exception {
		
		//456
		
		//先查询是否有暂存数据
		String templateType=MapUtils.getString(params, "orderType");
		params.put("templateType", templateType);
		Map<String, Object> result=accessFlowTempMapper.selectAccessFlowTemp(params);
		System.out.println(result);
		if(result==null) {
			result=new HashMap<String, Object>();
			String elementSet="";
			String ids[]= {"0"};
			params.put("status", 0);
			List<Map<String, Object>> borrowElementList=borrowElementMapper.selectBorrowElementByOrderNo(params);
			if(borrowElementList!=null&&borrowElementList.size()>0) {
				for(Map<String, Object> o :borrowElementList) {
					elementSet=elementSet+MapUtils.getString(o, "elementId")+",";
				}
			}
			
			if(elementSet!=null) {
				ids=elementSet.split(",");
			}
			
			//查询当前箱子中存在的要件
			List<Map<String, Object>> element_List=elementFileMapper.selectElementFileList(ids);//查询得到要件集合
					
			List<Map<String, Object>> riskList=new  ArrayList<Map<String, Object>>();
			List<Map<String, Object>> backList=new  ArrayList<Map<String, Object>>();
			List<Map<String, Object>> sealList=new  ArrayList<Map<String, Object>>();
					
					
			HttpUtil httpUtil=new HttpUtil();
			//待组装数据模板
			if(element_List!=null&&element_List.size()>0) {
				for(Map<String, Object> o:element_List) {
					//已借出的才可以还
					if(MapUtils.getIntValue(o, "status")==4||MapUtils.getIntValue(o, "status")==8
							||MapUtils.getIntValue(o, "status")==9) {
						if(o.get("elementType").toString().equals("1")) {//回款要件
							backList.add(fullDataTemplate(o,httpUtil,"1"));
						}
								
						if(o.get("elementType").toString().equals("2")) {//风控要件
							riskList.add(fullDataTemplate(o,httpUtil,"1"));
						}
								
						if(o.get("elementType").toString().equals("3")) {//公章要件
							sealList.add(fullDataTemplate(o,httpUtil,"1"));
						}
					}
					
				}
			}

			result.put("riskElement", riskList);
					
			result.put("receivableElement", backList);
					
			result.put("sealElement", sealList);
			
			
			ElementListDto	elementListDto=new ElementListDto();
			List<ElementListDto> element_order_List=elementMapper.selectElementByOrderNo(params);
			if(element_order_List!=null&&element_order_List.size()>0) {
				elementListDto=element_order_List.get(0);
			}
			
			//每次图片数据都是叠加的，查询最后一次还记录的图片即可
			/*List<Map<String, Object>> accessFlowList=accessFlowMapper.selectLastAccessByMap(params);
			
		 	if(accessFlowList!=null&&accessFlowList.size()>0) {
		 		result.put("riskPicture", MapUtils.getString(accessFlowList.get(0), "riskPicture"));
				
				result.put("receivablePicture", MapUtils.getString(accessFlowList.get(0), "receivablePicture"));
				
				result.put("elsePicture", MapUtils.getString(accessFlowList.get(0), "elsePicture"));
				
				result.put("sealPicture", MapUtils.getString(accessFlowList.get(0), "sealPicture"));
		 	}*/
			
			
			result.put("boxNo", elementListDto.getBoxNo());
			
			result.put("customerName", elementListDto.getCustomerName());
					
			result.put("channelManagerName", elementListDto.getChannelManagerName());
			
			result.put("acceptMemberName", elementListDto.getAcceptMemberName());
			
			result.put("sealDepartment", elementListDto.getSealDepartment());
			
			result.put("sealDepartmentId", elementListDto.getSealDepartmentId());
			
			
			}else {
				result=JsonUtil.parseJsonToMap(result.get("content").toString());
			}
		return result;
		
		//return returnMap;
	}
	
	@Override
	//保存暂还信息
	public void saveSuspendedRetrunButtonInfo(Map<String, Object> params) {
		Map<String, Object> result=new HashMap<String, Object>();
		params.put("templateType", MapUtils.getString(params, "orderType"));
		params.put("currentHandlerUid", MapUtils.getString(params, "uid"));
		params.put("content", JsonUtil.BeanToJson(params));
		//先查询是否有暂存数据
		result=accessFlowTempMapper.selectAccessFlowTemp(params);
		if(result==null) {//新增
			accessFlowTempMapper.insertAccessFlowTemp(params);
		}else {//修改
			params.put("id", result.get("id"));
			accessFlowTempMapper.updateAccessFlowTempById(params);
		}
	}
	
	@Override
	//保存还要件信息
	@Transactional(rollbackFor=Exception.class)
	public void saveReturnElementOrder(Map<String, Object> params) {
		
			log.info("执行还要件"+params);
			params.put("templateType", MapUtils.getString(params, "orderType"));//暂存模板类型1信贷要件关联2无信贷要件关联3公章 
			//789
			String currentBoxElementSet="";//当前要件箱中的要件
			String elementSet=MapUtils.getString(params, "elementSet");//此次还要件ID集合
			String ids[]= {};
			ElementListDto	elementListDto=null;
			if(elementSet!=null&&!elementSet.equals("")) {
				ids=elementSet.split(",");
			}
			//查询当前箱子中存在的要件
			List<ElementListDto> element_List=elementMapper.selectElementByOrderNo(params);
			if(element_List!=null&&element_List.size()>0) {
				elementListDto=element_List.get(0);
				currentBoxElementSet=elementListDto.getCurrentBoxElementSet();
				//删除不在要件柜中的要件id
				if(StringUtil.isNotBlank(currentBoxElementSet)) {
					String idss[]= currentBoxElementSet.split(",");
					List<Map<String,Object>> mapList = elementFileMapper.selectElementFileList(idss);
					for (Map<String, Object> map : mapList) {
						if(MapUtils.getIntValue(map, "status")!=3
								&&MapUtils.getIntValue(map, "status")!=5
								&&MapUtils.getIntValue(map, "status")!=6&&!elementSet.contains(MapUtils.getString(map, "id"))) {
							currentBoxElementSet=addReduceBoxElement(currentBoxElementSet,MapUtils.getString(map, "id"),2);
						}
					}
				}
			}
			
			//记录还操作流水
			params.put("currentBoxElementSet", currentBoxElementSet);
			accessFlowMapper.insertAccessFlowRecorde(params);
			//计算此处操作后，箱子中的要件
			//String currentBoxElementSet=addReduceBoxElement(elements,params.get("elementSet").toString(),1);//当前要件箱中的要件
			//修改要件订单表对应箱子当前存在的要件集合	
			//更新列表时间
			params.put("currentBoxElementSet", elementListDto.getCurrentBoxElementSet());
			elementMapper.UpdateElementByOrderNo(params);
			
			//不能删除借要件信息，取消归还后还要计算是否超时未还
			//删除借要件表中的借用要件
			/*if(ids.length>0) {
				for(String id:ids) {
					borrowElementMapper.deleteBorrowElementRecorde(id);
				}
			}	*/
			
			//是否已全部归还
			Map<String,Object> mm = new HashMap<String,Object>();
			mm.put("ids", elementSet);
			mm.put("status", 1);
			log.info("=============还要件id:"+elementSet);
			borrowElementMapper.updateBorrowElement(mm);
			params.put("status", 0);
			List<Map<String,Object>> borrowElementList=borrowElementMapper.selectBorrowElementByOrderNo(params);
			
			//更新要件状态
			List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
			for (String t : ids) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", Integer.parseInt(t));
				map.put("status", 3);//已存入
				tList.add(map);
			}
			elementFileMapper.updateElementFile(tList);
			//没有借出的要件
			List<Map<String,Object>> list = elementFileMapper.selectElementFileListbyOrderNo(params);
			boolean isHuan = false;
			boolean isBorrow = false;
			for (Map<String, Object> map : list) {
				if(MapUtils.getIntValue(map, "status")==4
					||MapUtils.getIntValue(map, "status")==8) {
					isBorrow = true;
				}
				if(MapUtils.getIntValue(map, "status")==9) {
					isHuan = true;
				}
			}
			if(borrowElementList==null||borrowElementList.size()==0||(!isHuan&&!isBorrow)) {
				params.put("orderStatus", "4");
				//隐藏还按钮
				params.put("returnButton", 1);
				elementMapper.operationReturnButton(params);
			}else if(isBorrow&&!isHuan) {//已借出
				params.put("orderStatus", "3");
				params.put("returnButton", 0);
				elementMapper.operationReturnButton(params);
			}else if(isHuan) {//超时未还
				params.put("orderStatus", "5");
				params.put("returnButton", 0);
				elementMapper.operationReturnButton(params);
			}
			accessFlowTempMapper.deleteAccessFlowTempByMap(params);
	}
	
	
	@Override
	//点击开箱按钮返回信息
	public Map<String, Object> retrunOpenButtonInfo(Map<String, Object> params) {
		HttpUtil httpUtil=new HttpUtil();
		String elements="";//当前要件箱中的要件
		String ids[]= {};
		Map<String, Object> returnMap=new HashMap<String, Object>();
		//查询当前箱子中存在的要件
		List<ElementListDto> element_List=elementMapper.selectElementByOrderNo(params);
		if(element_List!=null&&element_List.size()>0) {
			ElementListDto	elementListDto=element_List.get(0);
			
			returnMap.put("customerName", elementListDto.getCustomerName());
			returnMap.put("cityName", elementListDto.getCityName());
			returnMap.put("cityCode", elementListDto.getCityCode());
			returnMap.put("channelManagerName", elementListDto.getChannelManagerName());
			returnMap.put("channelManagerUid", elementListDto.getChannelManagerUid());
			returnMap.put("acceptMemberUid", elementListDto.getAcceptMemberUid());
			returnMap.put("acceptMemberName", elementListDto.getAcceptMemberName());
			returnMap.put("borrowingAmount", elementListDto.getBorrowingAmount());
			returnMap.put("borrowingDay", elementListDto.getBorrowingDay());
			returnMap.put("creditType",elementListDto.getCreditType());
			returnMap.put("creditTypeUid", elementListDto.getCreateUid());
			returnMap.put("boxNo", elementListDto.getBoxNo());
			returnMap.put("sealDepartment", elementListDto.getSealDepartment());
			returnMap.put("sealDepartmentId", elementListDto.getSealDepartmentId());
			
			elements=elementListDto.getCurrentBoxElementSet();//查询要件ID集合
			ids=elements.split(",");
		}
		
	
		String elementSet="";
		//查询当前要件箱中的要件是否全被选中，处于已通过借用审批或借用审批中
		
		List<Map<String,Object>> lendList=xAuditBaseMapper.selectFileIdsByOrderNo(params);
		if(lendList!=null&&lendList.size()>0) {
			for(Map<String,Object> map:lendList) {
				elementSet=elementSet+MapUtils.getString(map, "elementIds")+",";
			}
		}
		log.info("elementSet"+elementSet);

		List<Map<String, Object>> element_File_List=elementFileMapper.selectElementFileList(ids);//查询得到要件集合
		
		List<Map<String, Object>> riskList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> backList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sealList=new  ArrayList<Map<String, Object>>();

		//待组装数据模板
		if(element_List!=null&&element_List.size()>0) {
			for(Map<String, Object> o:element_File_List) {
				//过滤要件,只有已存入的可以开箱取
				int status = MapUtils.getIntValue(o, "status");
				if(status!=3&&status!=6) {
					continue;
				}
				String fileId=MapUtils.getString(o, "id");
				
				if(o.get("elementType").toString().equals("1")) {//回款要件
//					if(elementSet.contains(fileId)) {
//						backList.add(fullDataTemplate(o,httpUtil,"3"));
//					}else {
						backList.add(fullDataTemplate(o,httpUtil,"1"));
//					}	
				}
				
				if(o.get("elementType").toString().equals("2")) {//风控要件
//					if(elementSet.contains(fileId)) {
//						riskList.add(fullDataTemplate(o,httpUtil,"3"));
//					}else {
						riskList.add(fullDataTemplate(o,httpUtil,"1"));
//					}
					
				}
				
				if(o.get("elementType").toString().equals("3")) {//公章要件
//					if(elementSet.contains(fileId)) {
//						sealList.add(fullDataTemplate(o,httpUtil,"3"));
//					}else {
						sealList.add(fullDataTemplate(o,httpUtil,"1"));
//					}
				}
			}
		}
		
	
		returnMap.put("riskElement", riskList);
		
		returnMap.put("receivableElement", backList);
		
		returnMap.put("sealElement", sealList);
		return returnMap;
	}

	@Override
	//保存开箱信息
	@Transactional(rollbackFor=Exception.class)
	public void saveOpenElementInfo(Map<String, Object> params) {
	
		String currentBoxElementSet="";//当前要件箱中的要件
		ElementListDto	elementListDto=null;
		String elementSet=MapUtils.getString(params, "elementSet");
		//查询当前箱子中存在的要件
		List<ElementListDto> element_List=elementMapper.selectElementByOrderNo(params);
		if(element_List!=null&&element_List.size()>0) {
			elementListDto=element_List.get(0);
			currentBoxElementSet=elementListDto.getCurrentBoxElementSet();
			//删除不在要件柜中的要件id
			if(StringUtil.isNotBlank(currentBoxElementSet)) {
				String ids[]= currentBoxElementSet.split(",");
				List<Map<String,Object>> mapList = elementFileMapper.selectElementFileList(ids);
				for (Map<String, Object> map : mapList) {
					if(MapUtils.getIntValue(map, "status")!=3
							&&MapUtils.getIntValue(map, "status")!=5
							&&MapUtils.getIntValue(map, "status")!=6) {
						currentBoxElementSet=addReduceBoxElement(currentBoxElementSet,MapUtils.getString(map, "id"),2);
					}
				}
			}
			currentBoxElementSet=addReduceBoxElement(currentBoxElementSet,elementSet,2);
		}
		//计算此处操作后，箱子中的要件
		//String currentBoxElementSet=addReduceBoxElement(elements,params.get("elementSet").toString(),2);//当前要件箱中的要件
		params.put("operationType", 6);
		
		params.put("currentBoxElementSet", currentBoxElementSet);
		
		params.put("elements", currentBoxElementSet);
		
		//修改tbl_element_box_base表中箱子中存在的要件,操作ID,订单状态，若不存在要件，则修改订单状态为已退还
		
		/*if(currentBoxElementSet.equals("")) {//表示要件已经退还完,恢复箱子的使用状态
			boxBaseMapper.updateBoxBaseByBoxNo(params);
		}*/
		//修改要件订单表对应箱子当前存在的要件集合		
		elementMapper.updateTimeByOrderNo(params);
		if(elementListDto!=null&&elementListDto.getOrderStatus()!=5) {
			params.put("orderStatus", 3);
			elementMapper.UpdateElementStatusByOrderNo(params);//修改为已借出状态
		}
		//需要对开箱图片进行处理，保存json数组
		String fileImgUrl = MapUtils.getString(params, "fileImgUrl");
		if(StringUtil.isNotBlank(fileImgUrl)) {
			String[] fileImgArr = fileImgUrl.split(",");
			JSONArray jsonArr = JSONArray.fromObject(fileImgArr);
			params.put("fileImgUrl", jsonArr.toString());
		}
		//删除借用的要件
		currentBoxElementSet = currentBoxElementSet.replace(elementSet, "").replace(",,", ",");
		if(StringUtil.isNotBlank(currentBoxElementSet)&&currentBoxElementSet.startsWith(",")) {
			currentBoxElementSet = currentBoxElementSet.substring(1,currentBoxElementSet.length());
		}
		if(StringUtil.isNotBlank(currentBoxElementSet)&&currentBoxElementSet.endsWith(",")) {
			currentBoxElementSet = currentBoxElementSet.substring(0,currentBoxElementSet.length()-1);
		}
		params.put("currentBoxElementSet", currentBoxElementSet);
		accessFlowMapper.insertAccessFlowRecorde(params);//保存取记录操作流水
		
		String ids[]={};
		
		
		if(elementSet!=null&&!elementSet.equals("")) {
			ids=elementSet.split(",");
		}
		List<Map<String,Object>> elementList=elementFileMapper.selectElementFileList(ids); 
		//更新要件状态
		List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
		for (String t : ids) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", Integer.parseInt(t));
			map.put("status", 4);//已借出
			tList.add(map);
		}
		elementFileMapper.updateElementFile(tList);
		if(elementList!=null&&elementList.size()>0) {
			for(Map<String,Object> o:elementList) {
				o.put("elementId", MapUtils.getString(o, "id"));
				o.put("beginBorrowElementTime", MapUtils.getString(params, "beginBorrowElementTime"));
				o.put("endBorrowElementTime", MapUtils.getString(params, "endBorrowElementTime"));
				o.put("borrowDay", MapUtils.getString(params, "borrowDay"));
				o.put("orderNo", MapUtils.getString(params, "orderNo"));
				o.put("dbId", MapUtils.getString(params, "dbId"));
				o.put("elementOperationId",MapUtils.getString(params, "id"));	
			}
		}
		borrowElementMapper.insertBorrowElementRecorde(elementList);//存入借要件集合表
	}
	
	@Override
	//加载风控意见
	public Map<String, Object> retrunRiskOpinion(Map<String, Object> params) {
		
		
		HttpUtil httpUtil=new HttpUtil();
		String orderNo=MapUtils.getString(params, "orderNo");
		
		FirstAuditDto firstAudit=new FirstAuditDto();
		firstAudit.setOrderNo(orderNo);
		RespDataObject<FirstAuditDto> FirstAuditResp=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/first/v/detail", firstAudit, FirstAuditDto.class);
		
		
		
		FinalAuditDto finalAudit=new FinalAuditDto();
		finalAudit.setOrderNo(orderNo);
		RespDataObject<FinalAuditDto> finalAuditResp=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAudit, FinalAuditDto.class);
		
		
		
		OfficerAuditDto officerAudit=new OfficerAuditDto();
		officerAudit.setOrderNo(orderNo);
		RespDataObject<OfficerAuditDto> officerAuditResp=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/officer/v/detail", officerAudit, OfficerAuditDto.class);
		
	
		JusticeAuditDto justiceAudit=new JusticeAuditDto();
		justiceAudit.setOrderNo(orderNo);
		RespDataObject<JusticeAuditDto> justiceAuditResp=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/officer/v/detail", justiceAudit, JusticeAuditDto.class);
		
		Map<String,Object> retrunMap=new HashMap<String,Object>();
		
		retrunMap.put("first", FirstAuditResp.getData());
		retrunMap.put("final", finalAuditResp.getData());	
		retrunMap.put("officer", officerAuditResp.getData());
		retrunMap.put("justice", justiceAuditResp.getData());
		
		
		/*Map<String,Object> firstMap=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/first/v/detail", params,Map.class);
		
		Map<String,Object> finalMap=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", params,Map.class);
		
		Map<String,Object> officeMap=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/officer/v/detail", params,Map.class);
		
		Map<String,Object> justiceMap=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/justice/v/detail", params,Map.class);
		
		retrunMap.put("first", MapUtils.getObject(firstMap, "remark"));
		
		retrunMap.put("final", MapUtils.getObject(finalMap, "remark"));
		
		retrunMap.put("office", MapUtils.getObject(officeMap, "remark"));
		
		retrunMap.put("justice", MapUtils.getObject(justiceMap, "remark"));*/
		
		
		return retrunMap;
	}
	
	@Override
	//查询公章所属部门
	public List<Map<String, Object>> selectSealDepartmentList(Map<String, Object> params) {
		
		
		List<Map<String, Object>> sealDepartmentList=sealDepartmentMapper.selectSealDepartmentList(params);
	
		
		return sealDepartmentList;
	}
	
	//加减要件箱中的要件  type=1表示加，Type=2表示减少
	public static String addReduceBoxElement(String elements,String elementSet,int type) {
		String result="";
		if(type==1) {
			result = elements;
			if(StringUtil.isBlank(elements)) {
				result = elementSet;
			}else if(!elements.contains(elementSet)) {
				result=elements+","+elementSet;
			}
		}
		
		if(type==2) {
			if(elementSet.equals("")) {
				return elements;
			}
			
			String elements_array[]=elements.split(",");
			
			String elementSet_array[]=elementSet.split(",");
			
			List<String> lelements = new ArrayList(Arrays.asList(elements_array));
			
			List<String> lelementSet= new ArrayList(Arrays.asList(elementSet_array));
			
			lelements.removeAll(lelementSet);
			
			for(String o:lelements) {
				result=result+o+",";
			}
		}
		
		if(!result.equals("")&&result.endsWith(",")) {
			result=result.substring(0, result.length()-1);
		}
		
		if(!result.equals("")&&result.startsWith(",")) {
			result=result.substring(1, result.length());
		}
		
		return result;
	}
	
	public static Map<String,Object> templateMappering() {
		HttpUtil httpUtil=new HttpUtil();
		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("pageClass", "tbl_element_elementFile_page");
		RespDataObject<Map<String,Object>>  pageTabRegionConfigMap = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/config/page/base/v/pageTabRegionConfig", pageMap, Map.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map = pageTabRegionConfigMap.getData();
		/*Map<String,Object> map=new HashMap<String,Object>();
		map.put("身份证", "eleFileID");
		
		map.put("户口本", "eleFileBook");
		
		map.put("房产证", "eleFileHouse");
		
		map.put("银行卡", "eleFileBank");
		
		map.put("结婚证", "eleFileRiskRadio");
		
		
		map.put("网银", "eleFilePayRadio");
		
		map.put("公章", "eleFilePayRadio");
		
		map.put("私章", "eleFilePayRadio");
		
		map.put("财务章", "eleFilePayRadio");
		
		map.put("营业执照副本", "eleFilePayRadio");
		
		map.put("开户许可证", "eleFilePayRadio");
		
		map.put("机构信用代码证", "eleFilePayRadio");
		
		map.put("支票", "eleFilePayRadio");
		
		map.put("印鉴卡", "eleFilePayRadio");
		
		map.put("组织机构代码证", "eleFilePayRadio");
		
		map.put("税务登记本", "eleFilePayRadio");
		
	
		map.put("公司公章", "eleFileSealRadio");
		
		map.put("业务章", "eleFileSealRadio");
		
		map.put("财务章", "eleFileSealRadio");
		
		map.put("法人私章", "eleFileSealRadio");
		
		map.put("合同章", "eleFileSealRadio");
		
		*/
		return map;
	}
	
	
	
	
	
	//初始化空模板
	public  Map<String,Object> initDataTemplate(Map<String, Object> params,HttpUtil httpUtil){
		Map<String,Object> pageParamMap=new HashMap<String,Object>();
		Map<String,Object> resultMap=new HashMap<String,Object>();
		pageParamMap.put("pageClass", "tbl_element_elementFile_page");
		RespData<PageTabRegionFormConfigDto> respData=new RespData<PageTabRegionFormConfigDto>();
		List<Map<String, Object>> riskList=new  ArrayList<Map<String, Object>>();
		List<Map<String, Object>> backList=new  ArrayList<Map<String, Object>>();
		
		String orderType=params.get("orderType").toString();
		
		
		
		
		/*if(orderType.equals("1")) {
			List<Map<String, Object>> elementFileList=elementFileMapper.selectElementFileListbyOrderNo(params);
			if(elementFileList!=null&&elementFileList.size()>0) {
				
				for(Map<String, Object> o:elementFileList) {
					if(o.get("elementType").toString().equals("1")) {//回款要件
						backList.add(fullDataTemplate(o,httpUtil));
					}
					
					if(o.get("elementType").toString().equals("2")) {//风控要件
						riskList.add(fullDataTemplate(o,httpUtil));
					}
				}
				resultMap.put("riskElement", riskList);
				resultMap.put("receivableElement", backList);		
			}else {
				params.put("orderType", "2");
				resultMap=initDataTemplate(params,httpUtil);
			}
		}*/
		

		
		if(orderType.equals("1")) {
			Map riskMap=foreclosureTypeMapper.selectRiskElement(params);//根据订单ordeNo查询
/*	 		List<Map<String,Object>> risk=joinRiskElement(riskMap);
	 		
	 		Map<String,Object> riskIdCardMap=risk.get(0);
	 		
	 		Map<String,Object> riskBankMap=risk.get(1);*/
	 		
	 		Map receivableMap=paymentTypeMapper.selectReceivableElement(params);//根据订单ordeNo查询
	 		
	 		/*List<Map<String,Object>> receivable=joinRiskElement(receivableMap);
	 		
	 		Map<String,Object> receivableIdCardMap=risk.get(0);
	 		
	 		Map<String,Object> receivableBankMap=risk.get(1);*/
	 		
	 		
	 		//风控要件
			pageParamMap.put("regionClass", "eleFileID");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_IdCard = respData.getData();
			
			
			String accountType=MapUtils.getString(riskMap, "accountType");
			
			if(accountType!=null&&!accountType.equals("公司")) {
				configlist_IdCard.get(0).setValue(MapUtils.getString(riskMap, "bankCardMaster"));
				configlist_IdCard.get(1).setValue(MapUtils.getString(riskMap, "idCard"));
			}
			
			Map<String,Object> idCardMap=new HashMap<String,Object>();
			idCardMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			idCardMap.put("hasAdd", "1");//是否可添加
			idCardMap.put("type", "1");//身份证
			idCardMap.put("title", "身份证");
			idCardMap.put("data", configlist_IdCard);
			
			pageParamMap.put("regionClass", "eleFileBook");
			
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_Book = respData.getData();
			
			Map<String,Object> BookMap=new HashMap<String,Object>();//户口本
			BookMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			BookMap.put("hasAdd", "1");//是否可添加1可添加2不可添加
			BookMap.put("type", "2");
			BookMap.put("title", "户口本");
			BookMap.put("data", configlist_Book);
			
			pageParamMap.put("regionClass", "eleFileHouse");
			
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_House = respData.getData();
			
			Map<String,Object> HouseMap=new HashMap<String,Object>();//户口本
			HouseMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			HouseMap.put("hasAdd", "1");//是否可添加
			HouseMap.put("type", "3");
			HouseMap.put("title", "房产证");
			HouseMap.put("data", configlist_House);
			
			//结婚证
			pageParamMap.put("regionClass", "eleMarriage");
			
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_Marriage = respData.getData();
			
			Map<String,Object> MarriageMap=new HashMap<String,Object>();//户口本
			MarriageMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			MarriageMap.put("hasAdd", "1");//是否可添加1添加2不可添加
			MarriageMap.put("title", "结婚证");
			MarriageMap.put("type", "5");
			MarriageMap.put("data", configlist_Marriage);
			
			//离婚证
			pageParamMap.put("regionClass", "eleDivorce");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> divorceCertificate = respData.getData();
			Map<String,Object> divorceCertificateMap =new HashMap<String,Object>();//户口本
			divorceCertificateMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			divorceCertificateMap.put("hasAdd", "1");//是否可添加1添加2不可添加
			divorceCertificateMap.put("title", "离婚证");
			divorceCertificateMap.put("type", "5");
			divorceCertificateMap.put("data", divorceCertificate);
						
			//回款要件
			//身份证
			
			pageParamMap.put("regionClass", "eleFileID");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_IdCard1 = respData.getData();
			
			String paymentaccountType=MapUtils.getString(receivableMap, "paymentaccountType");
			
			if(paymentaccountType!=null&&!paymentaccountType.equals("公司")) {
				configlist_IdCard1.get(0).setValue(MapUtils.getString(receivableMap, "paymentBankCardName"));
				configlist_IdCard1.get(1).setValue(MapUtils.getString(receivableMap, "paymentIdCardNo"));
			}
			

			Map<String,Object> idCardMap1=new HashMap<String,Object>();
			idCardMap1.put("status", "1");//1表示未选中2表示已选中3表示已存
			idCardMap1.put("hasAdd", "1");//是否可添加
			idCardMap1.put("type", "1");//身份证
			idCardMap1.put("title", "身份证");
			idCardMap1.put("data", configlist_IdCard1);
			
			//银行卡
			pageParamMap.put("regionClass", "eleFileBank");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_Bank = respData.getData();
			
			if(receivableMap!=null&&!receivableMap.isEmpty()) {
				
				String paymentBankName=MapUtils.getString(receivableMap, "paymentBankName");
				log.info("信贷同步银行卡信息"+receivableMap);
				if(paymentBankName!=null&&!paymentBankName.equals("")) {
					configlist_Bank.get(0).setValue(paymentBankName);
				}else {
					Integer paymentBankNameId=MapUtils.getInteger(receivableMap, "paymentBankNameId");
					BankDto bankDto=CommonDataUtil.getBankNameById(paymentBankNameId);
					configlist_Bank.get(0).setValue(bankDto.getName());
				}
				
				
				String paymentBankSubName=MapUtils.getString(receivableMap, "paymentBankSubName");
				if(paymentBankSubName!=null&&!paymentBankSubName.equals("")) {
					configlist_Bank.get(1).setValue(MapUtils.getString(receivableMap, "paymentBankSubName"));
				}else {
					Integer paymentBankSubNameId=MapUtils.getInteger(receivableMap, "paymentBankSubNameId");
					SubBankDto subBankDto=CommonDataUtil.getSubBankNameById(paymentBankSubNameId);
					configlist_Bank.get(1).setValue(subBankDto.getName());
				}
				
				
				configlist_Bank.get(2).setValue(MapUtils.getString(receivableMap, "paymentBankCardName"));
				configlist_Bank.get(3).setValue(MapUtils.getString(receivableMap, "paymentBankNumber"));
			}
			

			Map<String,Object> BankMap=new HashMap<String,Object>();//银行卡
			BankMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			BankMap.put("hasAdd", "1");//是否可添加
			BankMap.put("title", "银行卡");
			BankMap.put("type", "4");
			BankMap.put("data", configlist_Bank);
			//待加载网银
			
			pageParamMap.put("regionClass", "netSilver");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_NetSilver = respData.getData();
			
			Map<String,Object> NetSilverMap=new HashMap<String,Object>();//户口本
			NetSilverMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			NetSilverMap.put("hasAdd", "1");//是否可添加1添加2不可添加
			NetSilverMap.put("title", "网银");
			NetSilverMap.put("type", "5");
			NetSilverMap.put("data", configlist_NetSilver);
			
			riskList.add(idCardMap);
			riskList.add(BookMap);
			riskList.add(HouseMap);
			riskList.add(MarriageMap);
			riskList.add(divorceCertificateMap);
			backList.add(idCardMap1);
			backList.add(BankMap);
			backList.add(NetSilverMap);
			//获取自定义名称的要件，没有具体内容
			pageParamMap.put("regionClass", "eleFileRiskRadio");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_risk = respData.getData();
			for(PageTabRegionFormConfigDto dto:configlist_risk) {
				Map<String,Object> dtoMap=new HashMap<String,Object>();//每一个章
				List<PageTabRegionFormConfigDto> dtoList=new ArrayList<PageTabRegionFormConfigDto>();//每一个章的数据属性集合
				dtoList.add(dto);
				if(!dto.getTitle().equals("身份证")&&!dto.getTitle().equals("结婚证")&&!dto.getTitle().equals("离婚证")&&!dto.getTitle().equals("银行卡")
						&&!dto.getTitle().equals("户口本")&&!dto.getTitle().equals("房产证")) {
					dtoMap.put("status", "1");//1表示未选中2表示已选中3表示已存
					dtoMap.put("hasAdd", "2");//是否可添加
					dtoMap.put("title",dto.getTitle());
					dtoMap.put("data", dtoList);
					dtoMap.put("type", 6);
					riskList.add(dtoMap);
				}
			}
			
			//获取自定义名称的要件，没有具体内容
			pageParamMap.put("regionClass", "eleFilePayRadio");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_back = respData.getData();
			for(PageTabRegionFormConfigDto dto:configlist_back) {
				Map<String,Object> dtoMap=new HashMap<String,Object>();//每一个章
				List<PageTabRegionFormConfigDto> dtoList=new ArrayList<PageTabRegionFormConfigDto>();//每一个章的数据属性集合
				dtoList.add(dto);
				if(!dto.getTitle().equals("身份证")&&!dto.getTitle().equals("银行卡")) {
					dtoMap.put("status", "1");//1表示未选中2表示已选中3表示已存
					dtoMap.put("hasAdd", "2");//是否可添加
					dtoMap.put("title",dto.getTitle());
					dtoMap.put("data", dtoList);
					dtoMap.put("type", 5);
					backList.add(dtoMap);
				}
			}
			
			resultMap.put("riskElement", riskList);
			resultMap.put("receivableElement", backList);
	 			
		}else if(orderType.equals("2")) {
			//风控要件
			pageParamMap.put("regionClass", "eleFileID");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_IdCard = respData.getData();
			Map<String,Object> idCardMap=new HashMap<String,Object>();
			idCardMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			idCardMap.put("hasAdd", "1");//是否可添加
			idCardMap.put("type", "1");//身份证
			idCardMap.put("title", "身份证");
			idCardMap.put("data", configlist_IdCard);
			
			pageParamMap.put("regionClass", "eleFileBook");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_Book = respData.getData();
			Map<String,Object> BookMap=new HashMap<String,Object>();//户口本
			BookMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			BookMap.put("hasAdd", "1");//是否可添加1可添加2不可添加
			BookMap.put("type", "2");
			BookMap.put("title", "户口本");
			BookMap.put("data", configlist_Book);
			
			pageParamMap.put("regionClass", "eleFileHouse");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_House = respData.getData();
			Map<String,Object> HouseMap=new HashMap<String,Object>();//户口本
			HouseMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			HouseMap.put("hasAdd", "1");//是否可添加
			HouseMap.put("type", "3");
			HouseMap.put("title", "房产证");
			HouseMap.put("data", configlist_House);
			
			//结婚证
			pageParamMap.put("regionClass", "eleMarriage");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_Marriage = respData.getData();
			Map<String,Object> MarriageMap=new HashMap<String,Object>();//户口本
			MarriageMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			MarriageMap.put("hasAdd", "1");//是否可添加1添加2不可添加
			MarriageMap.put("title", "结婚证");
			MarriageMap.put("type", "5");
			MarriageMap.put("data", configlist_Marriage);
			
			//离婚证
			pageParamMap.put("regionClass", "eleDivorce");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> divorceCertificate = respData.getData();
			Map<String,Object> divorceCertificateMap =new HashMap<String,Object>();//户口本
			divorceCertificateMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			divorceCertificateMap.put("hasAdd", "1");//是否可添加1添加2不可添加
			divorceCertificateMap.put("title", "离婚证");
			divorceCertificateMap.put("type", "5");
			divorceCertificateMap.put("data", divorceCertificate);
						
			//回款要件
			//银行卡
			pageParamMap.put("regionClass", "eleFileBank");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_Bank = respData.getData();
			Map<String,Object> BankMap=new HashMap<String,Object>();//银行卡
			BankMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			BankMap.put("hasAdd", "1");//是否可添加
			BankMap.put("title", "银行卡");
			BankMap.put("type", "4");
			BankMap.put("data", configlist_Bank);
			//待加载网银
			
			pageParamMap.put("regionClass", "netSilver");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_NetSilver = respData.getData();
			Map<String,Object> NetSilverMap=new HashMap<String,Object>();//户口本
			NetSilverMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			NetSilverMap.put("hasAdd", "1");//是否可添加1添加2不可添加
			NetSilverMap.put("title", "网银");
			NetSilverMap.put("type", "5");
			NetSilverMap.put("data", configlist_NetSilver);
			
			riskList.add(idCardMap);
			riskList.add(BookMap);
			riskList.add(HouseMap);
			riskList.add(MarriageMap);
			riskList.add(divorceCertificateMap);//离婚证
			backList.add(idCardMap);
			backList.add(BankMap);
			backList.add(NetSilverMap);//网银
			
			//获取自定义名称的要件，没有具体内容
			pageParamMap.put("regionClass", "eleFileRiskRadio");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_risk = respData.getData();
			for(PageTabRegionFormConfigDto dto:configlist_risk) {
				Map<String,Object> dtoMap=new HashMap<String,Object>();//每一个章
				List<PageTabRegionFormConfigDto> dtoList=new ArrayList<PageTabRegionFormConfigDto>();//每一个章的数据属性集合
				dtoList.add(dto);
				if(!dto.getTitle().equals("身份证")&&!dto.getTitle().equals("结婚证")&&!dto.getTitle().equals("离婚证")&&!dto.getTitle().equals("银行卡")
						&&!dto.getTitle().equals("户口本")&&!dto.getTitle().equals("房产证")) {
					dtoMap.put("status", "1");//1表示未选中2表示已选中3表示已存
					dtoMap.put("hasAdd", "2");//是否可添加
					dtoMap.put("title",dto.getTitle());
					dtoMap.put("data", dtoList);
					dtoMap.put("type", 6);
					riskList.add(dtoMap);
				}
			}
			
			//获取自定义名称的要件，没有具体内容
			pageParamMap.put("regionClass", "eleFilePayRadio");
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_back = respData.getData();
			for(PageTabRegionFormConfigDto dto:configlist_back) {
				Map<String,Object> dtoMap=new HashMap<String,Object>();//每一个章
				List<PageTabRegionFormConfigDto> dtoList=new ArrayList<PageTabRegionFormConfigDto>();//每一个章的数据属性集合
				dtoList.add(dto);
				if(!dto.getTitle().equals("身份证")&&!dto.getTitle().equals("银行卡")) {
					dtoMap.put("status", "1");//1表示未选中2表示已选中3表示已存
					dtoMap.put("hasAdd", "2");//是否可添加
					dtoMap.put("title",dto.getTitle());
					dtoMap.put("data", dtoList);
					dtoMap.put("type", 5);
					backList.add(dtoMap);
				}
			}
		
			resultMap.put("riskElement", riskList);
			resultMap.put("receivableElement", backList);
	
		}else if(orderType.equals("3")) {//加载公章
			
			List<Map<String, Object>> sealList=new  ArrayList<Map<String, Object>>();
			
			pageParamMap.put("regionClass", "eleFileSealRadio");
			
			respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist_Seal = respData.getData();
			
			for(PageTabRegionFormConfigDto dto:configlist_Seal) {
				Map<String,Object> dtoMap=new HashMap<String,Object>();//每一个章
				List<PageTabRegionFormConfigDto> dtoList=new ArrayList<PageTabRegionFormConfigDto>();//每一个章的数据属性集合
				dtoList.add(dto);
				dtoMap.put("status", "1");//1表示未选中2表示已选中3表示已存
				dtoMap.put("hasAdd", "2");//是否可添加
				dtoMap.put("title",dto.getTitle());
				dtoMap.put("data", dtoList);
				dtoMap.put("type", 6);
				sealList.add(dtoMap);
			}
		/*	Map<String,Object> SealMap=new HashMap<String,Object>();//公章
			SalMap.put("status", "1");//1表示未选中2表示已选中3表示已存
			SalMap.put("hasAdd", "2");//是否可添加
			SalMap.put("title", "公章");
			SalMap.put("data", configlist_Seal);
			sealList.add(SealMap);	*/
			resultMap.put("sealElement", sealList);
		}
		return resultMap;
	}

	//组装模版数据
	public Map<String,Object> fullDataTemplate(Map<String,Object> map,HttpUtil httpUtil,String selected){
		log.info("组装模板数据"+map);
		Map<String,Object> retrunMap=new HashMap<String,Object>();
		String pageClass="tbl_element_elementFile_page";
		
		String cardType=map.get("cardType").toString();
		
		Integer type=MapUtils.getInteger(map, "type");
		//查询要件状态
		int id = MapUtils.getIntValue(map, "id");
		map.put("elementId", id);
		//已申请借出或者已借出或者超市未还不允许修改
		
		
		String regionClass="";
		Map<String,Object> templateMappering_map=templateMappering();
		System.out.println("要件名称："+cardType);
		if(templateMappering_map.get(cardType)==null) {
			regionClass = "eleFilePayRadio";
		}else {
			regionClass=templateMappering_map.get(cardType).toString();
		}
		log.info("要件名称对应regionClass:"+regionClass);
		Map<String,Object> pageParamMap=new HashMap<String,Object>();
		
		if(cardType.equals("财务章")&&type==5) {
			regionClass="eleFilePayRadio";
		}	
		pageParamMap.put("pageClass", pageClass);
		
		pageParamMap.put("regionClass", regionClass);
		
		RespData<PageTabRegionFormConfigDto> respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
		List<PageTabRegionFormConfigDto> configlist = respData.getData();
	
		for(PageTabRegionFormConfigDto o:configlist) {
				//o.put(MapUtils.getString(o, "key"), map.get(MapUtils.getString(o, "key")));
//			if(type==5||type==6) {//5,6类型使用title匹配
//				if(o.getTitle().equals(MapUtils.getString(map,"cardType"))) {
//					o.setValue("1");
//					//123
//				}
//			}else {
			o.setValue(MapUtils.getString(map, o.getKey()));
//			}
			
		}  
		
		  /* Iterator<PageTabRegionFormConfigDto> iterator = configlist.iterator();
	        while(iterator.hasNext()){
	            PageTabRegionFormConfigDto o = iterator.next();
	            if(o.getValue()==null||o.getValue().equals(""))
	                iterator.remove();   //注意这个地方
	        }*/
		
		if(MapUtils.getString(map, "id")!=null) {
			retrunMap.put("id", MapUtils.getString(map, "id"));
		}
		

		if(type==1||type==3||type==4||"户口本".equals(MapUtils.getString(map, "cardType"))
				||"结婚证".equals(MapUtils.getString(map, "cardType"))||"离婚证".equals(MapUtils.getString(map, "cardType"))
				||"网银".equals(MapUtils.getString(map, "cardType"))) {
			retrunMap.put("hasAdd", "1");//是否可添加
		}
		
		retrunMap.put("title", MapUtils.getString(map, "cardType"));
		if("eleFileRiskRadio".equals(regionClass)||"eleFileSealRadio".equals(regionClass)||"eleFilePayRadio".equals(regionClass)) {//没有data
			PageTabRegionFormConfigDto formDto = configlist.get(0);
			formDto.setTitle(cardType);
			configlist = new ArrayList<PageTabRegionFormConfigDto>();
			configlist.add(formDto);
		}
		retrunMap.put("data", configlist);
		retrunMap.put("type", MapUtils.getString(map, "type"));
		if(!"1".equals(selected)) {
			selected = MapUtils.getString(map, "status");
		}
		retrunMap.put("status", selected);//1表示未选中2表示已选中3表示已存
		return retrunMap;
	}

	    //初始化公章模板方法
		public static Map<String,Object> getSealDataTemplate(List<Map<String,Object>> list,HttpUtil httpUtil){
			
			List<String> seal=new ArrayList<String>();
			seal.add("公司公章");
			seal.add("业务章");
			seal.add("财务章");
			seal.add("法人私章");
			seal.add("合同章");

			Map<String,Object> retrunMap=new HashMap<String,Object>();
			
			
			Map<String,Object> pageParamMap=new HashMap<String,Object>();
		
			pageParamMap.put("pageClass", "tbl_element_elementFile_page");
			
			pageParamMap.put("regionClass", "eleFileSealRadio");
			
			RespData<PageTabRegionFormConfigDto> respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", pageParamMap, PageTabRegionFormConfigDto.class);
			List<PageTabRegionFormConfigDto> configlist = respData.getData();
						
			
								
			List<Map<String,Object>> sealList=new ArrayList<Map<String,Object>>();
			for(PageTabRegionFormConfigDto o:configlist) {
				
				List<PageTabRegionFormConfigDto> tempconfiglist=new ArrayList<PageTabRegionFormConfigDto>();
				tempconfiglist.addAll(configlist);
				Map<String,Object> Template=new HashMap<String,Object>();
						for(String sealName:seal) {
							if(o.getTitle().equals(sealName)) {
								 Iterator<PageTabRegionFormConfigDto> iterator = tempconfiglist.iterator();
							        while(iterator.hasNext()){
							            PageTabRegionFormConfigDto dto = iterator.next();
							            if(!dto.getTitle().equals(sealName)) {
							            	  iterator.remove();   //注意这个地方
							            }
							              
							        }
								
							}
						}

						
						Template.put("title", o.getTitle());
						Template.put("data", tempconfiglist);
						Template.put("type", "6");
						Template.put("status", "1");//1表示未选中2表示已选中3表示已存
						sealList.add(Template);		
			}
			
			
			for(Map<String,Object> elementmap:list) {
				for(Map<String,Object> Template:sealList) {
					String cardType=MapUtils.getString(elementmap, "cardType");
					//1:未选中2暂存3已存入4已借出5借用审批中6已修改7已取消存入8已取消归还9超时未还';
					int status = MapUtils.getIntValue(elementmap, "status");
					log.info("公章名称："+cardType+"状态："+status);
					List<PageTabRegionFormConfigDto> templist=(List<PageTabRegionFormConfigDto>) MapUtils.getObject(Template, "data");
					String title=templist.get(0).getTitle();
					if(cardType.equals(title)&&status!=7) {
						Template.put("status", status);
					}
				}
			}
			
			retrunMap.put("sealElement", sealList);

			return retrunMap;
		}
	
		
		
		//获取要件名称中文字符串
		public Map<String, Object> operationElementName(String elementIds,/*1:存取记录列表，2：订单列表*/int y) {
			Map<String,Object> returnMap=new HashMap<String,Object>();
			String ids[]= {};
			String riskMark="";
			String receivableMark="";
			String sealMark="";
			if(elementIds!=null&&!elementIds.equals("")) {
				ids=elementIds.split(",");
			}
			List<Map<String, Object>> element_File_List=elementFileMapper.selectElementFileList(ids);//查询得到要件集合
			
			for(Map<String, Object> o:element_File_List) {
				int elementType=MapUtils.getInteger(o, "elementType");
				//System.out.println(o.toString());
				int type=MapUtils.getInteger(o, "type");
				int status = MapUtils.getIntValue(o, "status");
				if(y==2) {//订单列表
					if(status!=3&&status!=5&&status!=6) {
						continue;
					}
				}
				if(elementType==1) {//回款要件
					if(type==1) {
						receivableMark=receivableMark+MapUtils.getString(o, "cardType")+"("+MapUtils.getString(o, "cardCustomer")+")"+",";
					}else {
						receivableMark=receivableMark+MapUtils.getString(o, "cardType")+",";
					}
					
				}
				if(elementType==2) {//风控要件
					if(type==1) {
						riskMark=riskMark+MapUtils.getString(o, "cardType")+"("+MapUtils.getString(o, "cardCustomer")+")"+",";
					}else {
						riskMark=riskMark+MapUtils.getString(o, "cardType")+",";
					}
				}
				
				if(elementType==3) {//公章要件
					sealMark=sealMark+MapUtils.getString(o, "cardType")+",";
				}
			}
			if(receivableMark.length()>0) {
				
				receivableMark=receivableMark.substring(0, receivableMark.length()-1);
			}
			if(riskMark.length()>0) {
				
				riskMark=riskMark.substring(0, riskMark.length()-1);
			}
			
			if(sealMark.length()>0) {
				
				sealMark=sealMark.substring(0, sealMark.length()-1);
			}

			if(sealMark.equals("")) {
				sealMark="暂无公章";
			}
			
			returnMap.put("riskMark", riskMark);
			returnMap.put("receivableMark", receivableMark);
			returnMap.put("sealMark", sealMark);
			return returnMap;
		}
		
		//拼接风控要件
		public List<Map<String,Object>> joinRiskElement(Map<String,Object> params){
			List<Map<String,Object>> riskList=new ArrayList<Map<String,Object>>();
			//身份证
			Map<String,Object> idCardMap=new HashMap<String,Object>();
			idCardMap.put("elementType", "1");//风控要件
			idCardMap.put("type", "1");//身份证
			idCardMap.put("cardType", "身份证");
			idCardMap.put("cardNumber", MapUtils.getString(params, "idCard"));
			//idCardMap.put("cardCustomer", MapUtils.getString(params, "bankCardMaster"));
			
			//银行卡
			Map<String,Object> BankMap=new HashMap<String,Object>();//银行卡
			BankMap.put("elementType", "1");//1表示未选中2表示已选中3表示已存
			BankMap.put("cardType", "银行卡");
			BankMap.put("type", "4");
			BankMap.put("cardCustomer", MapUtils.getString(params, "bankCardMaster"));
			BankMap.put("cardNumber", MapUtils.getString(params, "bankNo"));
			BankMap.put("bankName", MapUtils.getString(params, "bankName"));
			BankMap.put("bankSubName", MapUtils.getString(params, "bankSubName"));
			
			riskList.add(idCardMap);
			riskList.add(BankMap);
	
			
			return riskList;
		}
		
		
		//拼接回款要件
		public List<Map<String,Object>> joinReceivableElement(Map<String,Object> params){
			List<Map<String,Object>> ReceivableList=new ArrayList<Map<String,Object>>();
			//身份证
			Map<String,Object> idCardMap=new HashMap<String,Object>();
			idCardMap.put("elementType", "1");//风控要件
			idCardMap.put("type", "1");//身份证
			idCardMap.put("cardType", "身份证");
			idCardMap.put("cardNumber", MapUtils.getString(params, "paymentIdCardNo"));
			//idCardMap.put("cardCustomer", MapUtils.getString(params, "bankCardMaster"));
			
			//银行卡
			Map<String,Object> BankMap=new HashMap<String,Object>();//银行卡
			BankMap.put("elementType", "1");//1表示未选中2表示已选中3表示已存
			BankMap.put("cardType", "银行卡");
			BankMap.put("type", "4");
			BankMap.put("cardCustomer", MapUtils.getString(params, "paymentBankCardName"));
			BankMap.put("cardNumber", MapUtils.getString(params, "paymentBankNumber"));
			BankMap.put("bankName", MapUtils.getString(params, "paymentBankName"));
			BankMap.put("bankSubName", MapUtils.getString(params, "paymentBankSubName"));
			
			ReceivableList.add(idCardMap);
			ReceivableList.add(BankMap);
			
			return null;
		}

		//同步信贷要件，填充要件信息
		public Map<String,Object> fillElement(Map<String,Object> params,List<Map<String,Object>> elementList,String type){
			
			//List<Map<String,Object>> List =(List<Map<String, Object>>) MapUtils.getObject(params, "riskElement");
			//List<Map<String,Object>> backList =(List<Map<String, Object>>) MapUtils.getObject(params, "receivableElement");
			
			List<Map<String,Object>> List =(List<Map<String, Object>>) MapUtils.getObject(params, "type");
			
			//Map<String,Object> idCardMap= 
			
			
			
			//resultMap.put("riskElement", riskList);
			//resultMap.put("receivableElement", backList);
			
			return params;
		}

		@Override
		//根据公章所属部门查询公章订单
		public List<ElementListDto> selectElementBydepartId(Map<String, Object> params) {
			
			return elementMapper.selectElementBydepartId(params);
		}

		@Override
		//返回该部门已存公章信息，刷新界面存公章数据
		public Map<String, Object> selectSealElementInfo(Map<String, Object> params) {
			HttpUtil httpUtil=new HttpUtil();
			String elementSet="";
			//根据公章部门查询出，该公章部门对应的订单箱子中存在的要件，这些要件禁止再次存入
			List<ElementListDto>  orderList=elementMapper.selectElementBydepartId(params);
			String ids[]= {};
			
			//params.put("sealDepartmentId", value);
			if(orderList!=null&&orderList.size()>0) {
				//elementSet=orderList.get(0).getCurrentBoxElementSet();
				
				Map<String,Object> sealDepartmentMap=sealDepartmentMapper.selectSealDepartmentInfoById(params);
				elementSet=MapUtils.getString(sealDepartmentMap, "currentBoxElementSet");
				if(elementSet!=null) {
					ids=elementSet.split(",");
				}
			}
			List<Map<String,Object>> elementFileList=elementFileMapper.selectElementFileList(ids);
			
			Map<String,Object> sealMap=getSealDataTemplate(elementFileList,httpUtil);
			return sealMap;
		}
		
		//
		@Override
		public List<ElementListDto> selectElementByOrderNo(Map<String, Object> params) {
			
			return elementMapper.selectElementByOrderNo(params);
		}  
			
		
		private static String StringToDate(String s) {  
			if(s==null||s.equals("")) {
				return "";
			}
		    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");  
		    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		    Date date = null;  
		    String time="";
		    try {  
		        date = format1.parse(s);  
		        time=format2.format(date);
		    } catch (Exception e) {  
		        System.out.println(e.getMessage());  
		    }  
		    return  time;
		}  
		
		//信贷系统要件详情入口
		@Override
		public Map<String,Object> receiveElementInfo(Map<String, Object> params) {
			
			Map<String, Object> reslut=new HashMap<String, Object>();
			
			
			String state1="是否已收到要件:"+"否";
			String state2="";
					
			Map<String, Object> AccessFlowRecorde_map=new HashMap<String, Object>();
			AccessFlowRecorde_map.put("orderNo", MapUtils.getString(params, "orderNo"));
			AccessFlowRecorde_map.put("operationType", 1);
			//查询是否存在，存操作
			List<Map<String, Object>> AccessFlowRecorde_storeButton=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
			if(AccessFlowRecorde_storeButton!=null&&AccessFlowRecorde_storeButton.size()>0) {
				state1="是否已收到要件:"+"是";
				state2="要件详情";
			}
			
			
			List<ElementListDto>  ElementListDto_list=elementMapper.selectElementByOrderNo(AccessFlowRecorde_map);//查询该订单对应的箱子当前要件是否退完
			ElementListDto elementListDto=null;
				if(ElementListDto_list!=null&&ElementListDto_list.size()>0) {
					elementListDto=ElementListDto_list.get(0);
				}
			
			
			AccessFlowRecorde_map.put("orderNo", MapUtils.getString(params, "orderNo"));
			AccessFlowRecorde_map.put("operationType", 5);
			//查询是否存在，退操作
			List<Map<String, Object>> AccessFlowRecorde_refundButton=accessFlowMapper.selectAccessFlowRecorde(AccessFlowRecorde_map);
			if(AccessFlowRecorde_refundButton!=null&&AccessFlowRecorde_refundButton.size()>0&&elementListDto!=null&&elementListDto.getCurrentBoxElementSet()!=null&&elementListDto.getCurrentBoxElementSet().equals("")) {
				state1="要件已退还";
				state2="要件详情";
			}
			
			/*List<ElementListDto> list=elementMapper.selectElementByOrderNo(AccessFlowRecorde_map);
			if(list!=null&&list.size()>0) {
				if(list.get(0).getState().equals("已完结")) {
					state2="要件详情";
				}
			}*/
			reslut.put("state1", state1);
			reslut.put("state2", state2);
			return reslut;
		}
		
		
		
		public static void main(String[] args) {
			String fileImgUrl ="[\"http://182.254.149.92:9206/img/credit-app-img/ffe9585960554c4680ef8b36b18e2e3f_48.png\",\"http://182.254.149.92:9206/img/credit-app-img/b2d5991311e34b53ac5bd9f700425724_48.png\"]";
//			String b="0,1,2,3,4,5,6";
//			System.out.println(addReduceBoxElement("",b,1));
//			String[] fileImgArr = fileImgUrl.split(",");
//			JSONArray jsonArr = JSONArray.fromObject(fileImgArr);
//			System.out.println(jsonArr.toString());
			
			
			JSONArray arr = JSONArray.fromObject(fileImgUrl);
			fileImgUrl = arr.join(",");
			System.out.println(fileImgUrl);
			//StringToDate("2017-05-25");
			
			//String currentBoxElementSet="null,";
			
			//currentBoxElementSet=currentBoxElementSet.replaceAll("null", "");
			//System.out.println(currentBoxElementSet);
/*			String storeFileid="1,2,1,5,6";
			 String [] array = storeFileid.split(",");  
		        Set <String> set =  new  HashSet <String>();  
		        for (int  i = 0 ; i <array.length; i ++){  
		            set.add(array[i]);  
		        }  
		        String [] arrayResult =(String [])set.toArray(new  String [set.size()]);
		        for(String o:arrayResult) {
		        	System.out.println(o);
		        }*/
			
			/*String a[]= {"1027","1028","1029","1031"};
			String b[]= {"1028","1029","1031","1035"};
			System.out.println(useLoop(a,b));*/
			//System.out.println(DateUtils.dateToString(new Date(),""));
			
		/*	Map params=new HashMap<String,Object>();
			//params.put("hasRiskElement", 1);
			Integer hasRiskElement=MapUtils.getInteger(params, "hasRiskElement");
			if(hasRiskElement==null) {
				params.put("hasRiskElement", 0);
				System.out.println(params.get("hasRiskElement"));
			}*/
			
			/*String str="[\"http://fs.anjbo.com/img/null/f4273cdd01564b1f917ac3c92aed636b_48.jpeg\",\"http://fs.anjbo.com/img/null/f4273cdd01564b1f917ac3c92aed636b_48.jpeg\"]";
			
			try {
				JSONArray jArray = JSONArray.fromObject(str);
				
				for (Object object : jArray) {
						System.out.println(object);
				}
				//org.json.JSONObject json=JsonUtil.parseStringToJson(str);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}

		@Override
		//同步信贷订单数据
		@Transactional(rollbackFor=Exception.class)
		public void saveCreditOrderListDto(OrderListDto orderListDto) {
			
			log.info("执行保存同步信贷系统要件订单saveCredit"+orderListDto.getOrderNo()+orderListDto.getCustomerName());
			
			System.out.println(orderListDto);
			
			Map<String,Object> elementOrderMap=new HashMap<String,Object>();	
			
			elementOrderMap.put("orderType",1);
			elementOrderMap.put("creditTypeUid",orderListDto.getProductCode());
			elementOrderMap.put("creditType",orderListDto.getProductName());
			elementOrderMap.put("orderStatus",1);
			elementOrderMap.put("orderNo", orderListDto.getOrderNo());

			elementOrderMap.put("customerName", orderListDto.getCustomerName());
			elementOrderMap.put("cityName", orderListDto.getCityName());
			elementOrderMap.put("borrowingAmount", orderListDto.getBorrowingAmount());
			elementOrderMap.put("borrowingDay", orderListDto.getBorrowingDay());

			elementOrderMap.put("channelManagerUid", orderListDto.getChannelManagerUid());
			elementOrderMap.put("channelManagerName", orderListDto.getChannelManagerName());
			elementOrderMap.put("acceptMemberUid", orderListDto.getAcceptMemberUid());
			elementOrderMap.put("acceptMemberName", orderListDto.getAcceptMemberName());
			elementOrderMap.put("currentHandlerUid", orderListDto.getCurrentHandlerUid());
			elementOrderMap.put("currentHandler", orderListDto.getCurrentHandler());

			elementOrderMap.put("state", orderListDto.getState());
			
			elementOrderMap.put("cityCode", orderListDto.getCityCode());
			elementOrderMap.put("previousHandlerUid", orderListDto.getPreviousHandlerUid());
			elementOrderMap.put("notarialUid", orderListDto.getNotarialUid());
			elementOrderMap.put("facesignUid", orderListDto.getFacesignUid());
			elementOrderMap.put("createUid", orderListDto.getCreateUid());
			
			synchronized (this){//防止并发情况时同一订单重复添加
				List<ElementListDto> listDto=elementMapper.selectElementByOrderNo(elementOrderMap);
				if(listDto!=null&&listDto.size()>0) {
					elementMapper.updateCreditElementOrder(elementOrderMap);
				}else {
					if(orderListDto.getCustomerName()!=null&&!orderListDto.getCustomerName().equals("")) {
						elementOrderMap.put("createTime",DateUtils.dateToString(new Date(),""));
						elementMapper.saveCredit(elementOrderMap);
					}
				}
			}
			
			log.info("同步信贷订单参数elementOrderMap"+elementOrderMap);
		}
			
		
		public static boolean useLoop(String[] a, String[] b) {  
		       boolean flag = false;    
		       if(a==null||b==null) {
					 return flag;
				}
		       
		       for(String i:b) {
		    	   boolean  f=Arrays.asList(a).contains(i);
		    	   if(f) {
		    		   flag=true;
		    	   }else {
		    		   return false;
		    	   }
		       }
		       return flag;    
		   }
	
		
		public String operationPicture(String newpic,String oldpic) {
			String old_str[]= {};
			if(oldpic!=null) {
				old_str=oldpic.split(",");
			}else {
				return newpic;
			}
		
			for(int i=0;i<old_str.length;i++) {
				if(!newpic.contains(old_str[i])) {
					newpic=old_str[i]+","+newpic;
				}
			}
			return newpic;
		}

		/**
		 * 批量更新要件
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int updateElementFile(Map<String, Object> params) {
			int i = 0;
			String orderNo = MapUtils.getString(params, "orderNo");
			int operationType = MapUtils.getIntValue(params, "operationType");
			List<Map<String,Object>> riskList=(List<Map<String, Object>>) params.get("riskElement");
			List<Map<String,Object>> backList=(List<Map<String, Object>>) params.get("receivableElement");
			List<Map<String,Object>> sealList=new ArrayList<Map<String,Object>>();
			//int operationType=0;
			String orderType=params.get("orderType").toString();
			System.out.println("要件操作动作"+operationType);
			System.out.println("orderType"+orderType);
			String ids="";
			//是否展示还按钮
			boolean returnButton = false;
			String idss ="";
			if(orderType.equals("1")||orderType.equals("2")) {
				if(riskList!=null&&riskList.size()>0) {
					for(Map<String,Object> risk:riskList) {
						if("6".equals(MapUtils.getString(risk, "status"))
								||"7".equals(MapUtils.getString(risk, "status"))||"8".equals(MapUtils.getString(risk, "status"))) {
							ids += MapUtils.getString(risk, "id")+",";
						}
						//展示还按钮
						if("8".equals(MapUtils.getString(risk, "status"))) {
							//修改tbl_element_borrow_element status为未归还
							idss +=","+MapUtils.getString(risk, "id");
							returnButton = true;
						}
					}
					//已申请借出或者已借出或者超市未还不允许修改
					Map<String,Object> m = new HashMap<String,Object>();
					if(StringUtil.isNotBlank(ids)) {
						ids = ids.substring(0,ids.length()-1);
					}
					m.put("ids", ids);
					m.put("elementType", 2);
					List<Map<String,Object>> fileList = elementFileMapper.selectElementFileListByElementType(m);
					if(fileList!=null && fileList.size()>0) {
						for (Map<String, Object> map : fileList) {
							if(MapUtils.getIntValue(map, "status")==4||MapUtils.getIntValue(map, "status")==5) {
								//查询是否被借出
								return 1;
							}
						}
					}
				}else if(backList!=null&&backList.size()>0) {
					for(Map<String,Object> risk:backList) {
						if("6".equals(MapUtils.getString(risk, "status"))
								||"7".equals(MapUtils.getString(risk, "status"))||"8".equals(MapUtils.getString(risk, "status"))) {
							ids += MapUtils.getString(risk, "id")+",";
						}
						//展示还按钮
						if("8".equals(MapUtils.getString(risk, "status"))) {
							idss +=","+MapUtils.getString(risk, "id");
							returnButton = true;
						}
					}
					//已申请借出或者已借出或者超市未还不允许修改
					Map<String,Object> m = new HashMap<String,Object>();
					if(StringUtil.isNotBlank(ids)) {
						ids = ids.substring(0,ids.length()-1);
					}
					m.put("ids", ids);
					m.put("elementType", 1);
					List<Map<String,Object>> fileList = elementFileMapper.selectElementFileListByElementType(m);
					if(fileList!=null && fileList.size()>0) {
						for (Map<String, Object> map : fileList) {
							if(MapUtils.getIntValue(map, "status")==4||MapUtils.getIntValue(map, "status")==5) {
								//查询是否被借出
								return 1;
							}
						}
					}
				}
			}else if(orderType.equals("3")) {
				sealList=(List<Map<String, Object>>) params.get("sealElement");
				for(Map<String,Object> risk:sealList) {
					if("6".equals(MapUtils.getString(risk, "status"))
							||"7".equals(MapUtils.getString(risk, "status"))||"8".equals(MapUtils.getString(risk, "status"))) {
						ids += MapUtils.getString(risk, "id")+",";
					}
					//展示还按钮
					if("8".equals(MapUtils.getString(risk, "status"))) {
						idss +=","+MapUtils.getString(risk, "id");
						returnButton = true;
					}
				}
				//已申请借出或者已借出或者超市未还不允许修改
				Map<String,Object> m = new HashMap<String,Object>();
				if(StringUtil.isNotBlank(ids)) {
					ids = ids.substring(0,ids.length()-1);
				}
				m.put("ids", ids);
				m.put("elementType", 3);
				List<Map<String,Object>> fileList = elementFileMapper.selectElementFileListByElementType(m);
				if(fileList!=null && fileList.size()>0) {
					for (Map<String, Object> map : fileList) {
						if(MapUtils.getIntValue(map, "status")==4||MapUtils.getIntValue(map, "status")==5) {
							//查询是否被借出
							return 1;
						}
					}
				}
			}
			//修改取消归还
			if(StringUtil.isNotBlank(idss)) {
				idss = idss.substring(1,idss.length());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status", 0);
				map.put("hasMarkOverTime", 0);
				map.put("ids", idss);
				borrowElementMapper.updateBorrowElement(map);
			}
			//显示还按钮，订单状态更新为已借出
			if(returnButton) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("returnButton", "0");
				map.put("orderStatus", 3);
				map.put("orderNo", orderNo);
				elementMapper.operationReturnButton(map);
			}
	    	long beginTime=System.currentTimeMillis();
			params.put("operationAuthority", "");
			log.info("存入要件参数信息:"+params);
			//2.根据类型判断要件管理员，分配箱子
			//3.将要件存入要件表中
			//4.记录本次操作，存入要件操作表
			//5.记录本次要件操作流水(1.计算出本次操作后，箱子中的要件)
			//6.提取信息存入要件订单列表中
			/**/
			
			Integer hasRiskElement=MapUtils.getInteger(params, "hasRiskElement");
			if(hasRiskElement==null) {
				params.put("hasRiskElement", 0);
			}
			
			Integer hasPublicBusiness=MapUtils.getInteger(params, "hasPublicBusiness");
			if(hasPublicBusiness==null) {
				params.put("hasPublicBusiness", 0);
			}
			
			if(orderNo==null||orderNo.equals("")) {
				orderNo=UidUtil.generateOrderId();
				params.put("orderNo", orderNo);
			}
			params.put("templateType", MapUtils.getString(params, "orderType"));//暂存模板类型1信贷要件关联2无信贷要件关联3公章 
			
			//根绝分配箱子
			//Map<String, Object>  box_map=randomBox(params);
			//System.out.println(box_map);
			
			//List<Map<String,Object>> elementlist= (List<Map<String, Object>>) params.get("elementlist");
			
			List<Map<String,Object>> elementlist= new ArrayList<Map<String, Object>>();
			String key="";
			/*if(orderType.equals("1")) {//存入信贷关联订单
				
			}else*/ 
			Map<String,Object> mmm = new HashMap<String,Object>();
			mmm.put("orderNo", orderNo);
			List<Map<String,Object>> entity = elementFileMapper.selectElementFileListByElementType(mmm);
			for (Map<String, Object> map : entity) {
				mmm.put(MapUtils.getString(map, "id"), MapUtils.getIntValue(map, "status"));
			}
			if(orderType.equals("1")||orderType.equals("2")) {//存入非信贷关联或无关联要件//123
				if(riskList!=null&&riskList.size()>0){
					
					for(Map<String,Object> o:riskList) {
						
						Map<String,Object> file=new HashMap<String,Object>();
						Integer type=MapUtils.getInteger(o, "type");
						
						if(o.get("status").toString().equals("6")||o.get("status").toString().equals("7")||o.get("status").toString().equals("8")) {
							if((MapUtils.getIntValue(mmm, MapUtils.getString(o, "id"))==MapUtils.getIntValue(o, "status")
									&&MapUtils.getIntValue(o, "status")!=6)
									||MapUtils.getIntValue(mmm, MapUtils.getString(o, "id"))==9) {//没修改
								continue;
							}
							file.put("elementType","2");
							file.put("type",type);
							file.put("cardType",MapUtils.getString(o, "title"));
							file.put("id", MapUtils.getIntValue(o, "id"));
							file.put("status", o.get("status").toString());
							List<Map<String,Object>> data=(List<Map<String, Object>>) o.get("data");
							if(data!=null&&data.size()>0) {
								for(Map<String,Object> property:data) {
									key=MapUtils.getString(property, "key");
								
									if(key.equals("cardType")&&(type==5||type==6||type==7)) {	
										file.put(key, MapUtils.getString(o, "title"));
									}else {
										file.put(key, MapUtils.getString(property, "value"));
									}
								}
							}
							elementlist.add(file);
						}
					}
				}else if(backList!=null&&backList.size()>0){
					
					for(Map<String,Object> o:backList) {
						
						Map<String,Object> file=new HashMap<String,Object>();
						Integer type=MapUtils.getInteger(o, "type");
						if(o.get("status").toString().equals("6")||o.get("status").toString().equals("7")||o.get("status").toString().equals("8")) {
							if((MapUtils.getIntValue(mmm, MapUtils.getString(o, "id"))==MapUtils.getIntValue(o, "status")
									&&MapUtils.getIntValue(o, "status")!=6)
									||MapUtils.getIntValue(mmm, MapUtils.getString(o, "id"))==9) {//没修改
								continue;
							}
							file.put("elementType","1");
							file.put("type",type);
							file.put("cardType",MapUtils.getString(o, "title"));
							file.put("id", MapUtils.getIntValue(o, "id"));
							file.put("status", o.get("status").toString());
							List<Map<String,Object>> data=(List<Map<String, Object>>) o.get("data");
							if(data!=null&&data.size()>0) {
								for(Map<String,Object> property:data) {
									//file.put(MapUtils.getString(property, "key"), MapUtils.getString(property, "value"));
									key=MapUtils.getString(property, "key");
									if(key.equals("cardType")&&(type==5||type==6||type==7)) {	
										file.put(key, MapUtils.getString(o, "title"));
									}else {
										file.put(key, MapUtils.getString(property, "value"));
									}
								}
							}
							elementlist.add(file);
						}
					}
				}
			}else if(orderType.equals("3")) {//存入公章要件订单
				
				params.put("customerName", MapUtils.getString(params, "sealDepartment"));
				if(sealList!=null&&sealList.size()>0){
					for(Map<String,Object> o:sealList) {
						Map<String,Object> file=new HashMap<String,Object>();
						if(o.get("status").toString().equals("6")||o.get("status").toString().equals("7")||o.get("status").toString().equals("8")) {
							if((MapUtils.getIntValue(mmm, MapUtils.getString(o, "id"))==MapUtils.getIntValue(o, "status")
									&&MapUtils.getIntValue(o, "status")!=6)
									||MapUtils.getIntValue(mmm, MapUtils.getString(o, "id"))==9) {//没修改
								continue;
							}
							file.put("elementType","3");
							file.put("type",MapUtils.getString(o, "type"));
							file.put("id", MapUtils.getIntValue(o, "id"));
							file.put("status", o.get("status").toString());
							List<Map<String,Object>> data=(List<Map<String, Object>>) o.get("data");
							if(data!=null&&data.size()>0) {
								for(Map<String,Object> property:data) {
									file.put(MapUtils.getString(property, "key"), MapUtils.getString(property, "value"));
									key=MapUtils.getString(property, "key");
									if(key.equals("cardType")) {	
										file.put(key, MapUtils.getString(o, "title"));
									}else {
										file.put(key, MapUtils.getString(property, "value"));
									}
								}
							}
							elementlist.add(file);
						}
					}
				}
			}
			
			
			for(Map<String,Object> map:elementlist) {
				
				map.put("boxNo", params.get("boxNo"));
				map.put("orderNo", orderNo);
			}
			
			List<ElementListDto> elementList = elementMapper.selectElementByOrderNo(params);
			//记录本次要件操作流水
			params.put("boxNo", elementList.get(0).getBoxNo());
			params.put("operationType", operationType);//改
			params.put("operationTime",DateUtils.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
			String elementSet="";
			for(Map<String,Object> map:elementlist) {
				elementSet=elementSet+map.get("id").toString()+",";
			}
			elementSet=elementSet.substring(0, elementSet.length()-1);
			params.put("elementSet", elementSet);
			int operationAccessFlowId = MapUtils.getIntValue(params, "accessFlowId");
			log.info("修改流程id:"+operationAccessFlowId);
			accessFlowMapper.insertAccessFlowRecorde(params);
			int accessFlowId = Integer.parseInt(params.get("id").toString());
			Map<String,Object> m = new HashMap<String,Object>();
			//m.put("ids", elementList.get(0).getCurrentBoxElementSet());
			m.put("orderNo", orderNo);
			int elementType = 0;
			if(operationType==8||operationType==9) {
				elementType=2;
			}else if(operationType==10||operationType==11) {
				elementType=1;
			}else if(operationType==13||operationType==14) {
				elementType=3;
			}
			m.put("elementType", elementType);
			List<Map<String,Object>> entityList = elementFileMapper.selectElementFileListByElementType(m);
			//查询修改对象的操作时间
			Map<String,Object> mm = new HashMap<String,Object>();
			mm.put("id", operationAccessFlowId);
			Map<String,Object> accessFlow = accessFlowMapper.selectAccessFlowById(mm);
			log.info("修改要件记录："+accessFlow);
			for (Map<String, Object> map2 : entityList) {
				map2.put("orderNo", orderNo);
				map2.put("createUid", MapUtils.getString(params, "createUid"));
				map2.put("createName", MapUtils.getString(params, "createName"));
				map2.put("editReason", MapUtils.getString(params, "editReason"));
				map2.put("operationType", operationType);
				map2.put("flowType", 1);
				map2.put("accessFlowId", accessFlowId);
				//修改对象的操作时间
				map2.put("operationTime", MapUtils.getString(accessFlow, "operationTime"));
				map2.put("operationAccessFlowId", operationAccessFlowId);
				map2.put("elementId", MapUtils.getIntValue(map2, "id"));
			}
			//录入更新前流水
			elementFileFlowMapper.insertElementFileFlow(entityList);
			//更新要件
			elementFileMapper.updateElementFile(elementlist);
			//更新操作流水表currentBoxElementSet
			List<Map<String,Object>> mapList = elementFileMapper.selectElementFileList(elementList.get(0).getCurrentBoxElementSet().split(","));
			String idds ="";
			for (Map<String, Object> map : mapList) {
				if(MapUtils.getIntValue(map, "status")==3||MapUtils.getIntValue(map, "status")==5
						||MapUtils.getIntValue(map, "status")==6) {
					idds += MapUtils.getIntValue(map, "id")+",";
				}
			}
			if(StringUtil.isNotEmpty(idds)) {
				idds = idds.substring(0, idds.length()-1);
			}
			Map<String,Object> mapp = new HashMap<String,Object>();
			mapp.put("id", accessFlowId);
			mapp.put("currentBoxElementSet", idds);
			accessFlowMapper.updateById(mapp);
			//录入更新后流水
			List<Map<String,Object>> entityListAfter = elementFileMapper.selectElementFileListByElementType(m);
			for(Map<String,Object> map:entityListAfter) {
				//更新后录入流水表
				map.put("orderNo", orderNo);
				map.put("createUid", MapUtils.getString(params, "createUid"));
				map.put("createName", MapUtils.getString(params, "createName"));
				map.put("editReason", MapUtils.getString(params, "editReason"));
				map.put("operationType", operationType);
				map.put("flowType", 2);//修改后
				map.put("accessFlowId", accessFlowId);
				//修改对象的操作时间
				map.put("operationTime", MapUtils.getString(accessFlow, "operationTime"));
				map.put("operationAccessFlowId", operationAccessFlowId);
				map.put("elementId", MapUtils.getIntValue(map, "id"));
			}
			//录入更新后流水
			elementFileFlowMapper.insertElementFileFlow(entityListAfter);

			//params.put("boxNo", params.get("boxNo"));
			params.put("elementSet", elementSet);
			

			//存操作后，当前要件箱中存在的要件
			String currentBoxElementSet=elementSet;
			params.put("currentBoxElementSet", currentBoxElementSet);
		
			
			
			if(orderType.equals("3")) {
				
//				String sealElementSet="";
//						
//				Map<String,Object> sealDepartmentMap=sealDepartmentMapper.selectSealDepartmentInfoById(params);
//				
//				sealElementSet=MapUtils.getString(sealDepartmentMap, "currentBoxElementSet");
//				if(sealList!=null&&sealList.size()>0){
//					for (Map<String, Object> map : sealList) {
//						sealElementSet=sealElementSet+","+MapUtils.getIntValue(map, "id");
//					}
//				}
//				if(StringUtil.isNotBlank(sealElementSet)) {
//					sealElementSet = sealElementSet.substring(1,sealElementSet.length());
//				}
//				
//				sealDepartmentMap.put("currentBoxElementSet", sealElementSet);
//				//更新部门公章信息
//				sealDepartmentMapper.updateSealElementSet(sealDepartmentMap);
//				params.put("currentBoxElementSet", sealElementSet);
				
			}
					
			
			/*params.put("accessFlowId", params.get("id"));
			
			List<ElementListDto> elementRecordeList=elementMapper.selectElementByOrderNo(params);
			if(elementRecordeList!=null&&elementRecordeList.size()>0) {
				//修改信贷同步的订单信息，保存修改的是否收到风控要件，和对公业务
				elementMapper.UpdateElementByOrderNo(params);
				
				//if(orderType.equals("1")) {//修改箱号
//					params.put("orderStatus", 2);
//					elementMapper.UpdateElementStatusByOrderNo(params);//修改订单为已存入
				//}
				currentBoxElementSet=elementRecordeList.get(0).getCurrentBoxElementSet();	
				currentBoxElementSet=addReduceBoxElement(currentBoxElementSet,elementSet,1);
				if(currentBoxElementSet!=null) {
					currentBoxElementSet=currentBoxElementSet.replaceAll("null", "");
				}
				params.put("currentBoxElementSet", currentBoxElementSet);
			}else {

				//提取信息存入要件订单列表中
				params.put("orderStatus", 2);
				elementMapper.saveElementOrder(params);//修改订单为已存入
			}*/
			
			
			//修改要件订单表对应箱子当前存在的要件集合
			elementMapper.updateTimeByOrderNo(params);
			
			//修改箱子的使用状态为已使用，绑定订单号
//			params.put("useStatus", 1);
//			boxBaseMapper.updateBoxBaseByBoxNo(params);
			//同步暂存信息
			
			/*if(!orderType.equals("3")) {
				saveSuspendedStoreButtonInfo(params);//同步暂存信息
			}*/
		
			//accessFlowTempMapper.deleteAccessFlowTempByMap(params);
			
			long endTime=System.currentTimeMillis();
			
			log.info("修改updateElementOrder方法执行时间:"+(endTime-beginTime));
			return i;
		
			/*//增加要件修改流水tbl_element_element_file_flow
			String orderNo = MapUtils.getString(map, "orderNo");
			//删除旧订单要件修改流水
			elementFileFlowMapper.deleteFormOrderNo(orderNo);
			List<ElementListDto> elementList = elementMapper.selectElementByOrderNo(map);
			List<Map<String,Object>> entityList = elementFileMapper.selectElementFileList(elementList.get(0).getCurrentBoxElementSet().split(","));
			for (Map<String, Object> map2 : entityList) {
				map2.put("orderNo", orderNo);
				map2.put("createUid", MapUtils.getString(map, "createUid"));
				map2.put("createName", MapUtils.getString(map, "createName"));
				map2.put("editReason", MapUtils.getString(map, "editReason"));
			}
			elementFileFlowMapper.insertElementFileFlow(entityList);
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("elementList");
			//更新要件信息
			elementFileMapper.updateElementFile(list);
			//更新订单列表要件字段
			String currentBoxElementSet=""; 
			for (Map<String, Object> element : list) {
				currentBoxElementSet+=MapUtils.getInteger(element,"id")+",";
			}
			currentBoxElementSet=currentBoxElementSet.substring(0, currentBoxElementSet.length()-1);
			map.put("currentBoxElementSet", currentBoxElementSet);
			elementMapper.updateCreditElementOrder(map);*/
		}

		@Override
		public void updateElementCustomerInfo(Map<String, Object> map) {
			
			int operationType = MapUtils.getIntValue(map, "operationType");
			
			List<ElementListDto> oldData = elementMapper.selectElementByOrderNo(map);
			Map<String,Object> oldMap = new HashMap<String,Object>();
			oldMap = BeanToMapUtil.beanToMap(oldData.get(0));
			
			//录入存取记录流水表
			map.put("boxNo", MapUtils.getString(oldMap, "boxNo"));
			map.put("operationType", operationType);//改
			map.put("operationTime",DateUtils.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
			map.put("elementSet", MapUtils.getString(oldMap, "currentBoxElementSet"));
			map.put("currentBoxElementSet", MapUtils.getString(oldMap, "currentBoxElementSet"));
			accessFlowMapper.insertAccessFlowRecorde(map);
			int accessFlowId = Integer.parseInt(map.get("id").toString());
			oldMap.put("editReason", MapUtils.getString(map, "editReason"));
			oldMap.put("createUid", MapUtils.getString(map, "createUid"));
			oldMap.put("createName", MapUtils.getString(map, "createName"));
			oldMap.put("accessFlowId", accessFlowId);
			oldMap.put("flowType", 1);
			elementCustomerInfoFlowMapper.insertElementCustomerInfoFlow(oldMap);
			//更新基本信息
			elementMapper.updateCreditElementOrder(map);
			List<ElementListDto> newData = elementMapper.selectElementByOrderNo(map);
			Map<String,Object> newMap = new HashMap<String,Object>();
			newMap = BeanToMapUtil.beanToMap(newData.get(0));
			newMap.put("editReason", MapUtils.getString(map, "editReason"));
			newMap.put("createUid", MapUtils.getString(map, "createUid"));
			newMap.put("createName", MapUtils.getString(map, "createName"));
			newMap.put("accessFlowId", accessFlowId);
			newMap.put("flowType", 2);
			elementCustomerInfoFlowMapper.insertElementCustomerInfoFlow(newMap);
		}

		/**
		 * 获取修改要件详情
		 */
		@Override
		public Map<String, Object> updateElementDetail(Map<String, Object> map) throws Exception {
			HttpUtil httpUtil = new HttpUtil();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Map<String,Object> m = new HashMap<String,Object>();
			Map<String,Object> pageParamMap = new HashMap<String,Object>();
			//基本信息
			List<ElementListDto> elementList = elementMapper.selectElementByOrderNo(map);
			//查询修改的要件ids
			Map<String,Object> mp = new HashMap<String,Object>();
			mp.put("id", MapUtils.getIntValue(map, "accessFlowId"));
			Map<String,Object> accessFlow = accessFlowMapper.selectAccessFlowById(mp);
			//修改前信息
			map.put("ids", MapUtils.getString(accessFlow, "elementSet"));
			map.put("flowType", 1);
			List<Map<String,Object>> elementFileFlowBefore = elementFileFlowMapper.selectElementFileFlowByOrderNo(map);
			//修改后信息
			map.put("flowType", 2);
			List<Map<String,Object>> elementFileFlowAfter = elementFileFlowMapper.selectElementFileFlowByOrderNo(map);
			Map<String,Object> templateMappering_map = templateMappering();
			List<Map<String,Object>> beforeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> afterList = new ArrayList<Map<String,Object>>();
			String operationTime ="";
			pageParamMap.put("pageClass", "tbl_element_elementFile_page");
			RespDataObject<Map<String,Object>> respDataObject = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigMap", pageParamMap, Map.class);
			Map<String,Object> dataMap = respDataObject.getData();
			if(elementFileFlowBefore!=null&&elementFileFlowBefore.size()>0) {
				for(Map<String, Object> maps:elementFileFlowBefore) {
					String regionClass="";
					//Map<String,Object> templateMappering_map=templateMappering();
					
					String cardType = MapUtils.getString(maps, "cardType");
					int type = MapUtils.getIntValue(maps, "type");
					regionClass=templateMappering_map.get(cardType).toString();
					if(cardType.equals("财务章")&&type==5) {
						regionClass="eleFilePayRadio";
					}	
					List<PageTabRegionFormConfigDto> configlist = (List<PageTabRegionFormConfigDto>) dataMap.get(regionClass);
					List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
					Iterator<PageTabRegionFormConfigDto> iter = configlist.iterator();
					while (iter.hasNext()) {
						String json = JsonUtil.BeanToJson(iter.next());
						PageTabRegionFormConfigDto temp = (PageTabRegionFormConfigDto) JsonUtil.parseJsonToObj(json, PageTabRegionFormConfigDto.class);		
						if(!"eleFileSealRadio".equals(regionClass)) {
							temp.setValue(MapUtils.getString(maps, temp.getKey()));
						}
//						if("eleFileSealRadio".equals(regionClass)&&!temp.getTitle().equals(MapUtils.getString(maps, "cardType"))) {
//							iter.remove();
//						}else {
							tempList.add(temp);
//						}
					}
					Map<String,Object> retMap = new HashMap<String,Object>();
					retMap.put("title", MapUtils.getString(maps, "cardType"));
					if("eleFileRiskRadio".equals(regionClass)||"eleFileSealRadio".equals(regionClass)||"eleFilePayRadio".equals(regionClass)) {//没有data
						PageTabRegionFormConfigDto formDto = tempList.get(0);
						formDto.setTitle(cardType);
						tempList = new ArrayList<PageTabRegionFormConfigDto>();
						tempList.add(formDto);
					}
					retMap.put("data", tempList);
					retMap.put("type", MapUtils.getString(maps, "type"));
					int status = 0;
					if(MapUtils.getInteger(map, "operationType")==13) {//存公章
						status =MapUtils.getIntValue(maps, "status")==0?7:MapUtils.getIntValue(maps, "status");
					}else if(MapUtils.getInteger(map, "operationType")==14){
						status =MapUtils.getIntValue(maps, "status")==0?8:MapUtils.getIntValue(maps, "status");
					}else {
						status = MapUtils.getIntValue(maps, "status");
					}
					retMap.put("status", status);//
					beforeList.add(retMap);
					m.put("editReason", elementFileFlowBefore.get(0).get("editReason"));
					//修改时间
					Date createTime = (Date) elementFileFlowBefore.get(0).get("createTime");
					String createName = MapUtils.getString(elementFileFlowBefore.get(0), "createName");
					m.put("createTime", sdf.format(createTime));
					m.put("createName", createName);
					m.put("operationAccessFlowId", MapUtils.getInteger(maps, "operationAccessFlowId"));
				}
				m.put("beforeList", beforeList);
				for(Map<String, Object> maps:elementFileFlowAfter) {
					pageParamMap.put("pageClass", "tbl_element_elementFile_page");
					String regionClass="";
					//Map<String,Object> templateMappering_map=templateMappering();
					
					String cardType = MapUtils.getString(maps, "cardType");
					int type = MapUtils.getIntValue(maps, "type");
					regionClass=templateMappering_map.get(cardType).toString();
					if(cardType.equals("财务章")&&type==5) {
						regionClass="eleFilePayRadio";
					}	
					List<PageTabRegionFormConfigDto> configlist = (List<PageTabRegionFormConfigDto>) dataMap.get(regionClass);
					List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
					Iterator<PageTabRegionFormConfigDto> iter = configlist.iterator();
					while (iter.hasNext()) {
						String json = JsonUtil.BeanToJson(iter.next());
						PageTabRegionFormConfigDto temp = (PageTabRegionFormConfigDto) JsonUtil.parseJsonToObj(json, PageTabRegionFormConfigDto.class);
						if(!"eleFileSealRadio".equals(regionClass)) {
							temp.setValue(MapUtils.getString(maps, temp.getKey()));
						}
//						if("eleFileSealRadio".equals(regionClass)&&!temp.getTitle().equals(MapUtils.getString(maps, "cardType"))) {
//							iter.remove();
//						}else {
							tempList.add(temp);
//						}
					}
					Map<String,Object> retMap = new HashMap<String,Object>();
					retMap.put("title", MapUtils.getString(maps, "cardType"));
					if("eleFileRiskRadio".equals(regionClass)||"eleFileSealRadio".equals(regionClass)||"eleFilePayRadio".equals(regionClass)) {//没有data
						PageTabRegionFormConfigDto formDto = tempList.get(0);
						formDto.setTitle(cardType);
						tempList = new ArrayList<PageTabRegionFormConfigDto>();
						tempList.add(formDto);
					}
					retMap.put("data", tempList);
					retMap.put("type", MapUtils.getString(maps, "type"));
					int status = 0;
					if(MapUtils.getInteger(map, "operationType")==13) {//存公章
						status =MapUtils.getIntValue(maps, "status")==0?7:MapUtils.getIntValue(maps, "status");
					}else if(MapUtils.getInteger(map, "operationType")==14){
						status =MapUtils.getIntValue(maps, "status")==0?8:MapUtils.getIntValue(maps, "status");
					}else {
						status = MapUtils.getIntValue(maps, "status");
					}
					retMap.put("status", status);//
					afterList.add(retMap);
					m.put("editReason", elementFileFlowAfter.get(0).get("editReason"));
					//修改时间
					Date createTime = (Date) elementFileFlowAfter.get(0).get("createTime");
					String createName = MapUtils.getString(elementFileFlowAfter.get(0), "createName");
					m.put("createTime", sdf.format(createTime));
					m.put("createName", createName);
					if(maps.get("operationTime")!=null) {
						operationTime = MapUtils.getString(maps, "operationTime");
					}
					Date operTime = sdf.parse(operationTime);
					operationTime = sdf.format(operTime);
					m.put("operationAccessFlowId", MapUtils.getInteger(maps, "operationAccessFlowId"));
				}
				m.put("afterList", afterList);
			}
			
			//修改对象的操作时间
			if(MapUtils.getInteger(map, "operationType")==8||MapUtils.getInteger(map, "operationType")==10) {
				m.put("updateObject", "存要件("+operationTime+")");
			}else if(MapUtils.getInteger(map, "operationType")==9||MapUtils.getInteger(map, "operationType")==11) {
				m.put("updateObject", "还要件("+operationTime+")");
			}else if(MapUtils.getInteger(map, "operationType")==13) {
				m.put("updateObject", "存公章("+operationTime+")");
			}else{
				m.put("updateObject", "还公章("+operationTime+")");
			}
			
			m.put("customerName", elementList.get(0).getCustomerName());
			m.put("channelManagerAcceptMemberName", elementList.get(0).getChannelManagerName()+"/"+elementList.get(0).getAcceptMemberName());
			return m;
		}

		/**
		 * 获取基本信息修改详情
		 */
		@Override
		public Map<String, Object> updateElementBaseInfoDetail(Map<String, Object> map) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Map<String,Object> reMap = new HashMap<String,Object>();
			map.put("flowType", 1);
			Map<String,Object> beforeInfo = elementCustomerInfoFlowMapper.selectElementCustomerInfoFlow(map);
			map.put("flowType", 2);
			Map<String,Object> afterInfo = elementCustomerInfoFlowMapper.selectElementCustomerInfoFlow(map);
			reMap.put("beforeInfo", beforeInfo);
			reMap.put("afterInfo", afterInfo);
			reMap.put("editReason", MapUtils.getString(beforeInfo, "editReason"));
			//修改时间
			Date createTime = (Date)beforeInfo.get("createTime");
			String createName = MapUtils.getString(beforeInfo, "createName");
			reMap.put("createTime", sdf.format(createTime));
			reMap.put("createName", createName);
			reMap.put("updateObject", "存要件("+sdf.format(createTime)+")");
			return reMap;
		}

		@Override
		public int updateByMap(Map<String, Object> map) {
			return documentsMapper.updateByMap(map);
		}
}
