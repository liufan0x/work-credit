package com.anjbo.controller.dingtalk;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsTempDto;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/6/11.
 */
@Api
@RequestMapping("/credit/third/api/dingtalk/bpmsTemp")
public interface IBpmsTempController {

    @ApiOperation(value = "分页",httpMethod = "POST",response = ThirdDingtalkBpmsTempDto.class)
    @ApiImplicitParam(dataTypeClass = ThirdDingtalkBpmsTempDto.class,required = true)
    @RequestMapping(value = "/page")
    public RespPageData<ThirdDingtalkBpmsTempDto> page(@RequestBody ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto);


    @ApiOperation(value = "",httpMethod = "GET",response = RespStatus.class)
    @ApiImplicitParam(value = "id")
    @RequestMapping(value = "/get")
    public RespStatus get( long id);

    @ApiOperation(value = "编辑",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(dataTypeClass = ThirdDingtalkBpmsTempDto.class,required = true)
    @RequestMapping(value = "/edit")
    public RespStatus edit(@RequestBody ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto);
}
