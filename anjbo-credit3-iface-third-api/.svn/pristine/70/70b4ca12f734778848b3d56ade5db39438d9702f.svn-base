package com.anjbo.controller.dingtalk;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDetailsDto;
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
@Api("")
@RequestMapping("/credit/third/api/dingtalk/bpmsDetails")
public interface IBpmsDetailsController {

    @ApiOperation(value = "目前没场景，供demo操作",httpMethod = "POST",response = ThirdDingtalkBpmsDetailsDto.class)
    @ApiImplicitParam(dataTypeClass = ThirdDingtalkBpmsDetailsDto.class,required = true)
    @RequestMapping(value = "/page")
    public RespPageData<ThirdDingtalkBpmsDetailsDto> page(@RequestBody ThirdDingtalkBpmsDetailsDto thirdDingtalkBpmsDetailsDto);

    @ApiOperation(value = "审批流程回调",httpMethod = "GET",response = RespStatus.class)
    @ApiImplicitParam(value = "msg",name = "")
    @RequestMapping(value = "/notify")
    public RespStatus notify(String msg);
}
