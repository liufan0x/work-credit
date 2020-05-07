package com.anjbo.controller.ccb;

import com.anjbo.common.RespStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */
@Api("建行反馈")
@RequestMapping("/cm/ccb/result")
public interface IResultController {

    @ApiOperation(value = "添加反馈结果",httpMethod = "POST",response = RespStatus.class)
    @RequestMapping(value = "/add")
    public RespStatus add(@RequestBody Map<String,Object> param);
}
