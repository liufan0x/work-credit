package com.anjbo.controller.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.UserDto;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IAuditFinalController;
import com.anjbo.controller.ReportBaseController;
import com.anjbo.service.AuditFirstService;

/**
 * 终审人员效率统计
 * @author yis
 *
 */
@RestController
public class AuditFinalController extends ReportBaseController implements IAuditFinalController{

	@Resource
	AuditFirstService auditFirstService;
	
	@Override
	public   RespPageData<Map<String, Object>> query(@RequestBody Map<String,Object> paramMap){
		RespPageData<Map<String, Object>>  resp = new RespPageData<Map<String, Object>>();
		try{
			//获取用户
			paramMap.put("name", "风控终审[A]");
			List<UserDto> userList=auditFirstService.findByUser(paramMap);
			
			String uid="";
			for(UserDto user:userList){
				uid+="'"+user.getUid()+"',";
			}
			paramMap.put("uid", uid.substring(0, uid.length()-1));
			paramMap.put("processId", "auditFinal");
			List<Map<String, Object>>auditlist=auditFirstService.findbyFirst(paramMap);  //根据初审用户获取值
			List<Map<String, Object>> newList=new ArrayList<Map<String, Object>>();
			for (int i = 0; i < userList.size(); i++) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("firstName", userList.get(i).getName());
				map.put("createUid", userList.get(i).getUid());
				map.put("orderCount", 0);
				map.put("timeNumAvg", 0);
				map.put("orderAmount",0);
				for (int j = 0; j < auditlist.size(); j++) {
					String firstUid=auditlist.get(j).get("uid")+"";
					if(userList.get(i).getUid().equals(firstUid)) {
						map.put("orderCount", auditlist.get(j).get("orderCount"));
						map.put("timeNumAvg", auditlist.get(j).get("timeNumAvg"));
						map.put("orderAmount",auditlist.get(j).get("orderAmount"));
					}
				}
				newList.add(map);
			}
			Entry<String, Object> e=getSortType(paramMap, new String[] {"orderCount","timeNumAvg","orderAmount"});
			sort(newList, e.getKey(), (String)e.getValue());
			List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
			int start=Integer.parseInt(paramMap.get("start")+"");
			int pageSize=Integer.parseInt(paramMap.get("pageSize")+"");
			int leng=0;
			if((start+10)>newList.size() || pageSize >= newList.size()){
				leng=newList.size();
			}else{
				if(pageSize==10){
				  leng=start+10;
				}else if(pageSize==20){
				  leng=start+20;
				}else{
					leng=newList.size();
				}
			}
			for (int i = start; i < leng; i++) {
				list2.add(newList.get(i));
			}
			resp.setRows(list2);
			resp.setTotal(userList.size());
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			logger.error("订单风控统计异常:",e);
		}
		return resp;
	}
	private Entry<String, Object> getSortType(Map<String,Object> paramMap,String[] ss){
		Set<Entry<String, Object>> set=paramMap.entrySet();
		Iterator<Entry<String, Object>> i=set.iterator();
		for(;;i.hasNext()) {
			Entry<String, Object> e=i.next();
			for(String s:ss) {
				if(s.equals(e.getKey())) {
					return e;
				}
			}
		}
		
	}
	private void sort(List<Map<String, Object>> targetList,  String sortField,  String sortMode) {
		
		Collections.sort(targetList, new Comparator<Map>() {
			@Override
			public int compare(Map obj1, Map obj2) { 
				int retVal = 0;
				try {
					double d1=Double.parseDouble((String) obj1.get(sortField).toString());
					double d2=Double.parseDouble((String) obj2.get(sortField).toString());
					if (sortMode != null && "desc".equals(sortMode)) {
						retVal = d2-d1>0?1:d2-d1==0?0:-1;
					} else {
						retVal = d1-d2>0?1:d1-d2==0?0:-1;
					}
				} catch (Exception e) {
					throw new RuntimeException();
				}
				return retVal;
			}
		});
		
	}
}
