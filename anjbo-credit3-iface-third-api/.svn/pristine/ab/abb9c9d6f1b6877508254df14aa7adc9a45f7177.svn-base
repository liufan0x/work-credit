package com.anjbo.controller.signature;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/12.
 */
@Api("电子签章")
@RequestMapping("/signature/v")
public interface IElectronSignatureController {

    @ApiOperation(value = "电子签章",httpMethod = "POST",response = Map.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "cusList",name = "签章信息(包括姓名,证件,证件类型)集合",required = true),
            @ApiImplicitParam(value = "signatureImg",name = "要签章的pdf文件Url",required = true)
    })
    @RequestMapping("/stamp")
    public RespDataObject<Map<String,Object>> stamp( @RequestBody Map<String, Object> map);

    @ApiOperation(value = "初始化电子签章E签宝信息",httpMethod = "GET",response = RespStatus.class)
    @RequestMapping("/initSdk")
    public RespStatus initSdk();

    @ApiOperation(value = "获取电子签章图片",httpMethod = "POST",response = Map.class)
    @RequestMapping("/getImageUrl")
    public RespDataObject<Map<String,Object>> getImageUrl(@RequestBody Map<String, Object> params);
}
