package com.anjbo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AuditConfigWebService;
import com.anjbo.utils.StringUtil;

/**
 * 审批设置
 * Created by lichao on 2017/12/21.
 */
@Controller
@RequestMapping("/credit/element/auditconfig/web/v/")
public class AuditConfigWebController extends BaseController {

    @Autowired
    private AuditConfigWebService auditConfigService;

    @ResponseBody
    @RequestMapping("/init")
    public RespDataObject<Map<String,Object>> init(HttpServletRequest request){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            List<String> cityList = auditConfigService.selectAuditConfigHaveCityList();

            map.put("cityList",cityList);
            result.setData(map);
            result.setCode(RespStatusEnum.SUCCESS.getCode());
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
        }catch (Exception e){
            logger.error(e);
            result.setCode(RespStatusEnum.FAIL.getCode());
            result.setMsg(RespStatusEnum.FAIL.getMsg());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/initDetail")
    public RespDataObject<Map<String,Object>> initDetail(@RequestBody Map<String,Object> param){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            if (StringUtil.isEmpty(MapUtils.getString(param, "id"))){

                List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
                List<DictDto> cityListTemp = (List)RedisOperator.get("bookingSzAreaOid");
                if(cityListTemp == null) {
                    this.httpUtil.getRespStatus("link.credit", "/credit/common/base/v/initData", (Object)null);
                    cityListTemp = (List)RedisOperator.get("bookingSzAreaOid");
                }
                for (DictDto dictDto : cityListTemp) {
                    if(StringUtils.isEmpty(dictDto.getPcode())){
                        Map<String, Object> cityMap = new HashMap<String, Object>();
                        cityMap.put("cityCode", dictDto.getCode());
                        cityMap.put("cityName", dictDto.getName());
                        cityList.add(cityMap);
                    }
                }
                map.put("cityList",cityList);
            }else{
                map  = auditConfigService.selectAuditConfigDetail(param);
            }

            result.setData(map);
            result.setCode(RespStatusEnum.SUCCESS.getCode());
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
        }catch (Exception e){
            logger.error(e);
            result.setCode(RespStatusEnum.FAIL.getCode());
            result.setMsg(RespStatusEnum.FAIL.getMsg());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAuditConfigTypeList")
    public RespDataObject<Map<String,Object>> getAuditConfigTypeList(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            List<Integer> typeList = new ArrayList<Integer>();
            // 非业务流程（要件审批（普通申请）,审批要件审批（财务申请),'公章审批','业务流程审批'
            Integer[] array = {1,2,3,4};

            List<Integer> typeListTemp = auditConfigService.selectAuditConfigTypeList(param);
            if (CollectionUtils.isEmpty(typeListTemp)){
                typeList=Arrays.asList(array);
            }else{
                for (Integer i :array){
                    if (!typeListTemp.contains(i)){
                        typeList.add(i);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(typeList)) {
                map.put("typeList", typeList);
            }
            result.setData(map);
            result.setCode(RespStatusEnum.SUCCESS.getCode());
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
        }catch (Exception e){
            logger.error(e);
            result.setCode(RespStatusEnum.FAIL.getCode());
            result.setMsg(RespStatusEnum.FAIL.getMsg());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/page")
    public RespPageData<Map<String,Object>> page(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
        try {
            int count = auditConfigService.selectAuditConfigCount(param);
            List<Map<String, Object>> boxList = new ArrayList<Map<String, Object>>();
            if (count>0){
                boxList = auditConfigService.selectAuditConfigList(param);
            }

            result.setTotal(count);
            result.setRows(boxList);
            RespHelper.setSuccessRespStatus(result);
        }catch (Exception e){
            logger.error(e);
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveAuditConfig")
    public RespStatus saveAuditConfig(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespStatus result = new RespStatus();
        try {

            if(StringUtil.isEmpty(MapUtils.getString(param,"id"))){
                auditConfigService.save(param);
            }else{
                auditConfigService.edit(param);
            }

            result.setCode(RespStatusEnum.SUCCESS.getCode());
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(RespStatusEnum.FAIL.getCode());
            result.setMsg(RespStatusEnum.FAIL.getMsg());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/selectAuditConfigDetail")
    public RespDataObject selectAuditConfigDetail(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespDataObject result = new RespDataObject();
        try {
            Map<String, Object> map = auditConfigService.selectAuditConfigDetail(param);
            result.setData(map);

            result.setCode(RespStatusEnum.SUCCESS.getCode());
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(RespStatusEnum.FAIL.getCode());
            result.setMsg(RespStatusEnum.FAIL.getMsg());
        }

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/getUserListByDept")
    public RespData<Map<String,Object>> userListByDept( @RequestBody Map<String,Object> param){

        String deptId = MapUtils.getString(param, "deptId","");
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        List<UserDto> userList = getAllUserDtoList();
        for (UserDto user :userList){
            if ((user.getDeptIdArray()).contains(deptId)){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("uid",user.getUid());
                map.put("name",user.getName());
                list.add(map);
            }
        }

        return RespHelper.setSuccessData(new RespData<Map<String,Object>>(), list);
    }

    @ResponseBody
    @RequestMapping(value = "/getDeptList")
    public RespData<Map<String ,Object>> getDeptList(){
        RespData<Map<String ,Object>> resp = new RespData<Map<String ,Object>>(RespStatusEnum.FAIL.getCode(),RespStatusEnum.FAIL.getMsg());
        try {
            RespPageData<DeptDto> respPageData = getDeptListByHttp();
            if(RespStatusEnum.SUCCESS.getCode().equals(respPageData.getCode())){
                List<DeptDto> deptList = respPageData.getRows();
                List<UserDto> userDtoList = getAllUserDtoList();
                Map<String,Object> userMap = new HashMap<String,Object>();
                for (UserDto userDto:userDtoList){
                    String deptIdArray = userDto.getDeptIdArray();
                    if(deptIdArray!=null){
                        for (String deptId:deptIdArray.split(",")){
                            Set<String> userSet = (Set<String>)MapUtils.getObject(userMap,deptId);
                            if (CollectionUtils.isEmpty(userSet)){
                                userSet = new HashSet<String>();
                            }
                            userSet.add(userDto.getUid());
                            userMap.put(deptId,userSet);
                        }
                    }
                }
                List<Map<String ,Object>> list = new ArrayList<Map<String ,Object>>();

                for (DeptDto dept:deptList){
                    Map<String ,Object> map = new HashMap<String ,Object>();
                    map.put("id",dept.getId());
                    map.put("pid",dept.getPid());
                    map.put("name",dept.getName());
                    Set<String> set = new HashSet<String>();
                    Set<String> userSet = (Set<String>)MapUtils.getObject(userMap,dept.getId()+"");
                    if (userSet==null){
                        userSet = new HashSet<String>();
                    }
                    set.addAll(userSet);
                    map.put("userSet",set);
                    list.add(map);
                }
                getUserCount(list);

                resp.setData(list);
                resp.setCode(RespStatusEnum.SUCCESS.getCode());
                resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserListByName")
    public RespData<UserDto> getUserListByName( @RequestBody Map<String,Object> param){

        List<UserDto> userList =getAllUserDtoList();
        List<UserDto> list = new ArrayList<UserDto>();
        String name = MapUtils.getString(param,"name");
        if (CollectionUtils.isNotEmpty(userList)){
            for (UserDto user : userList){
                if (user.getName().contains(name)&&1==user.getAgencyId()){
                    list.add(user);
                }
            }
        }

        return RespHelper.setSuccessData(new RespData<UserDto>(), list);
    }

    private RespPageData<DeptDto> getDeptListByHttp(){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("agencyId", 1);
        param.put("start", 0);
        param.put("pageSize", 10000);
        RespPageData<DeptDto> respPageData = httpUtil.getRespPageData(Constants.LINK_CREDIT, "/credit/user/dept/v/list", param, DeptDto.class);

        return respPageData;
    }

    private void getUserCount(List<Map<String ,Object>> list){
        for (Map<String,Object> map:list){
            if("0".equals(MapUtils.getString(map,"pid"))){
                Set<String> set = (Set<String>)MapUtils.getObject(map,"userSet");
                for (Map<String,Object> map1:list){
                    if (MapUtils.getString(map,"id").equals(MapUtils.getString(map1,"pid"))){
                        Set<String> set1 = (Set<String>)MapUtils.getObject(map1,"userSet");
                        for (Map<String,Object> map2:list){
                            if (MapUtils.getString(map1,"id").equals(MapUtils.getString(map2,"pid"))) {
                                Set<String> set2 = (Set<String>) MapUtils.getObject(map2, "userSet");
                                for (Map<String,Object> map3:list){
                                    if (MapUtils.getString(map2,"id").equals(MapUtils.getString(map3,"pid"))) {
                                        Set<String> set3 = (Set<String>) MapUtils.getObject(map3, "userSet");
               /*                         for (Map<String,Object> map4:list){
                                            if (MapUtils.getString(map3,"id").equals(MapUtils.getString(map4,"pid"))) {
                                                Set<String> set4 = (Set<String>) MapUtils.getObject(map4, "userSet");
                                                set3.addAll(set4);
                                            }
                                        }*/
                                        set2.addAll(set3);
                                    }
                                }
                                set1.addAll(set2);
                            }
                        }
                        set.addAll(set1);
                    }
                }
            }
        }
        for (Map<String ,Object> map:list){
            Set<String> userSet = (Set<String>)MapUtils.getObject(map,"userSet");
            map.put("userCount",userSet.size());
            map.remove("userSet");
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/editSate")
    public RespStatus editSate(HttpServletRequest request,@RequestBody Map<String,Object> param) {
    	RespStatus respStatus =new RespStatus();
    	try {
			auditConfigService.editState(param);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(respStatus, "禁用/启用失败");
			e.printStackTrace();
		}
    	return respStatus;
    }
}
