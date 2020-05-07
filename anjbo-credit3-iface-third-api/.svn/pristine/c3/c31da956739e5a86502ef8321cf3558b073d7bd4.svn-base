package com.anjbo.controller.hrtrust;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/12.
 */
@Api("华融")
@RequestMapping("/hr")
public interface IHrtrustAbutmentController {

    @ApiOperation(value = "申请信息接口",httpMethod = "POST",response = RespStatus.class)
    @RequestMapping("/v/apply")
    public RespStatus apply(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "附件发送接口",httpMethod = "POST",response = RespStatus.class)
    @RequestMapping("/v/fileApply")
    public RespStatus fileApply(@RequestBody Map<String, Object> map);

    @ApiOperation(value = "放款接口",httpMethod = "POST",response = RespStatus.class)
    @RequestMapping("/v/lend")
    public RespStatus lend(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "应还款接口",httpMethod = "POST",response = RespStatus.class)
    @RequestMapping("/v/repayment")
    public RespStatus repayment(@RequestBody Map<String,Object> requestMap);

    @ApiOperation(value = "回款接口",httpMethod = "POST",response = RespStatus.class)
    @RequestMapping("/v/rayMentPlan")
    public RespStatus PayMentPlan(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "放款状态查询接口",httpMethod = "POST",response = Map.class)
    @RequestMapping("/v/queryLoanStatus")
    public RespDataObject<Map<String,Object>> queryLoanStatus(@RequestBody Map<String,Object> map);
    
    @ApiOperation(value = "审批状态查询接口",httpMethod = "POST",response = Map.class)               //新增加的
    @RequestMapping("/v/allStatus")
    public RespDataObject<Map<String,Object>> allStatus(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "返回申请数据接口",httpMethod = "POST",response = Map.class)
    @RequestMapping("/v/queryApply")
    public RespDataObject<Map<String,Object>> queryApply(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "返回附件数据接口",httpMethod = "POST",response = List.class)
    @RequestMapping("/v/queryFileApply")
    public RespDataObject<List<Map<String, Object>>> queryFileApply(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "返回放款数据接口",httpMethod = "POST",response = Map.class)
    @RequestMapping("/v/queryLend")
    public RespDataObject<Map<String,Object>> queryLend(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "返回应还款数据接口",httpMethod = "POST",response = Map.class)
    @RequestMapping("/v/queryRepayment")
    public RespDataObject<Map<String,Object>> queryRepayment(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "返回回款数据接口",httpMethod = "POST",response = Map.class)
    @RequestMapping("/v/queryRayMentPlan")
    public RespDataObject<Map<String,Object>> queryRayMentPlan(@RequestBody Map<String,Object> map);
}
