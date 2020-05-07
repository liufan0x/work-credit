package com.anjbo.controller.baidu;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anjbo.bean.baidu.BaiduRiskVo;
import com.anjbo.common.RespDataObject;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */
@Api("百度相关")
@RequestMapping("baidu/v")
public interface IBaiduController {

    @ApiOperation(value = "查询风险黑名单",httpMethod = "POST",response = Map.class)
    @RequestMapping("/searchBlacklist")
    public Map<String, Object> searchBlacklist(@RequestBody BaiduRiskVo baiduRiskVo);
    

    @ApiOperation(value = "人脸识别",httpMethod = "POST",response = RespDataObject.class)
    @RequestMapping("/faceRecognition")
    public RespDataObject<JSONObject> faceRecognition(Map<String, Object> map);
}
