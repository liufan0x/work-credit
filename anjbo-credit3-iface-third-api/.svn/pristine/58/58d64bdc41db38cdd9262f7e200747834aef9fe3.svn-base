package com.anjbo.controller.dingtalk;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDto;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.controller.dingtalk.vo.BpmsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/6/11.
 */
@Api
@RequestMapping("/credit/third/api/dingtalk/bpms")
public interface IBpmsController {

    @ApiOperation(value = "订单要件审批表单",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(dataTypeClass = BpmsVo.class,required = true)
    @RequestMapping(value = "/createOrderDoc")
    public RespStatus createOrderDoc(@RequestBody BpmsVo record);

    @ApiOperation(value = "目前没场景，供demo操作",httpMethod = "POST",response = ThirdDingtalkBpmsDto.class)
    @ApiImplicitParam(dataTypeClass = ThirdDingtalkBpmsDto.class,required = true)
    @RequestMapping(value = "/page")
    public RespPageData<ThirdDingtalkBpmsDto> page(@RequestBody ThirdDingtalkBpmsDto thirdDingtalkBpmsDto);
}
