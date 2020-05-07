package com.anjbo.controller;

import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.BoxBaseWebService;
import com.anjbo.util.BoxUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 要件柜管理
 * Created by lichao on 2017/12/21.
 */
@Controller
@RequestMapping("/credit/element/boxbase/web/v")
public class BoxBaseWebController extends BaseController{

    @Autowired
    private BoxBaseWebService boxBaseWebService;

    @ResponseBody
    @RequestMapping("/init")
    public RespDataObject<Map<String,Object>> init(HttpServletRequest request){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            List<String> cityList = boxBaseWebService.selectBoxBaseHaveCityList();
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
    @RequestMapping("/page")
    public RespPageData<Map<String,Object>> page(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
        try {
            UserDto userDto = getUserDto(request);
            param.put("authIds",userDto.getAuthIds());
            int count = boxBaseWebService.selectBoxBaseCount(param);
            List<Map<String, Object>> boxList = new ArrayList<Map<String, Object>>();
            if (count>0){
                boxList = boxBaseWebService.selectBoxBaseList(param);
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
    @RequestMapping("/openPage")
    public RespPageData<Map<String,Object>> openPage(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
        try {
            int count = boxBaseWebService.selectOpenBoxBaseCount(param);
            List<Map<String, Object>> boxList = new ArrayList<Map<String, Object>>();
            if (count>0){
                boxList = boxBaseWebService.selectOpenBoxBaseList(param);
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
    @RequestMapping("/open")
    public RespDataObject<Map<String,Object>> open(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            String deviceId =MapUtils.getString(param,"deviceId");
            String lock =MapUtils.getString(param,"lock");
            String address = MapUtils.getString(param,"address");
            String resp = BoxUtil.openBox(deviceId,lock+address);
            logger.info("开箱，deviceId："+deviceId+"，箱号"+lock+address);
            Map<String, Object> resultMap = JsonUtil.jsonToMap(resp);
            result.setData(param);
            if ("0".equals(MapUtils.getString(resultMap, "code"))) {
                RespHelper.setSuccessRespStatus(result);
            }else{
                RespHelper.setFailRespStatus(result,MapUtils.getString(resultMap, "msg"));
            }
        }catch (Exception e){
            logger.error(e);
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
        }
        return result;
    }

}
