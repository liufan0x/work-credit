package com.anjbo.controller.yntrust;

import com.anjbo.bean.yntrust.*;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/6/8.
 */
@Api(value = "云南信托")
@RequestMapping(value = "/yntrust")
public interface IYntrustAbutmentController{

    @ApiOperation(value = "保存云南信托借款与合同信息,并将信息推送给云南信托",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "borrow",name = "借款信息",required = true),
            @ApiImplicitParam(value = "contract",name = "合同信息",required = true)
    })
    @RequestMapping("/v/addBorrowAndContract")
    public RespDataObject<String> addBorrowAndContract(@RequestBody Map<String, Object> map);
    
   
    @ApiOperation(value = "单推影像资料",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "orderNo",name = "订单编号",required = true)
    })
    @RequestMapping("/v/pushImgs")
    public RespStatus pushImgs(@RequestBody Map<String, Object> map);

    @ApiOperation(value = "保存影像资料",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "orderNo",name = "订单编号",required = true),
            @ApiImplicitParam(value = "typeId", name = "分类，关联业务资料分类表 tbl_fc_order_businfo_type",required = false),
            @ApiImplicitParam(value = "url",name = "资料地址",required = true),
            @ApiImplicitParam(value = "type",name = "图片类型",required = true)
    })
    @RequestMapping("/v/addImage")
    public RespStatus addImage(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "批量保存云南信托影像资料",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "orderNo",name = "订单编号",required = true),
            @ApiImplicitParam(value = "typeId", name = "分类，关联业务资料分类表 tbl_fc_order_businfo_type",required = false),
            @ApiImplicitParam(value = "url",name = "资料地址",required = true),
            @ApiImplicitParam(value = "type",name = "图片类型",required = true)
    })
    @RequestMapping("/v/batchAddImage")
    public RespStatus batchAddImage(@RequestBody List<Map<String,Object>> list);

    @ApiOperation(value = "查询信托专户余额",httpMethod = "POST",response = Map.class)
    @ApiImplicitParam(value = "orderNo",name = "订单编号",required = true)
    @RequestMapping("/v/queryTrustAccount")
    public RespDataObject<Map<String,Object>> queryTrustAccount(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "发送放款指令",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/confirmPayment")
    public RespStatus confirmPayment(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "放款状态查询",httpMethod = "POST",response = List.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/queryTradingStatus")
    public RespDataObject<YntrustLoanDto> queryTradingStatus(@RequestBody YntrustLoanDto obj);

    @ApiOperation(value = " 获取合同信息电子签证的链接与二维码图片",httpMethod = "POST",response = Map.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/getQRCode")
    public RespDataObject<Map<String,Object>> getQRCode(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "获取云信签章合同文件",httpMethod = "POST",response = Map.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/getContractFile")
    public RespDataObject<Map<String,Object>> getContractFile(@RequestBody Map<String,Object> map);
    
    @ApiOperation(value = "下载云信签章合同文件",httpMethod = "POST",response = Map.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/downloadContractFile")
    public String downloadContractFile(String orderNo,HttpServletResponse response);

    @ApiOperation(value = "保存应还款计划并推送",httpMethod = "",response = RespStatus.class)
    @ApiImplicitParam(dataTypeClass = YntrustRepaymentPlanDto.class,name = "还款计划实体类字段",required = true)
    @RequestMapping("/v/addRepaymentPlan")
    public RespStatus addRepaymentPlan(@RequestBody YntrustRepaymentPlanDto obj);

    @ApiOperation(value = "保存回款计划的还款信息",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(dataTypeClass = YntrustRepaymentInfoDto.class,name = "回款计划还款信息",required = true)
    @RequestMapping("/v/addRepaymentInfoAndPayInfo")
    public RespStatus addRepaymentInfoAndPayInfo(@RequestBody YntrustRepaymentInfoDto info);

    @ApiOperation(value = "",httpMethod = "POST",response = YntrustRepaymentInfoDto.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/queryRepayOrder")
    public RespDataObject<YntrustRepaymentInfoDto> queryRepayOrder(@RequestBody YntrustRepaymentInfoDto info);


    @ApiOperation(value = "",httpMethod = "POST",response = Map.class)
    @ApiImplicitParam(dataTypeClass = YntrustRepaymentPayDto.class,name = "计划支付信息",required = true)
    @RequestMapping("/v/paymentOrder")
    public RespDataObject<Map<String,Object>> paymentOrder(@RequestBody YntrustRepaymentPayDto pay);

    @ApiOperation(value = "",httpMethod = "",response = YntrustRepaymentPayDto.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/queryPaymentOrder")
    public RespDataObject<YntrustRepaymentPayDto> queryPaymentOrder(@RequestBody YntrustRepaymentPayDto pay);

    @ApiOperation(value = "取消贷款",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/cancelLoan")
    public RespStatus cancelLoan(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "更新还款计划",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(dataTypeClass = YntrustRepaymentPlanDto.class,name = "还款计划信息",required = true)
    @RequestMapping("/v/updateRepaySchedule")
    public RespStatus updateRepaySchedule(@RequestBody YntrustRepaymentPlanDto plan);

    @ApiOperation(value = "更新逾期费用",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(dataTypeClass = YntrustRepaymentInfoDto.class,name = "回款计划还款信息实体类",required = true)
    @RequestMapping("/v/updateOverDueFee")
    public RespStatus updateOverDueFee(@RequestBody YntrustRepaymentInfoDto info);

    @ApiOperation(value = "查询云南信托推送的借款人与合同信息与影像资料(key=(borrow,contract,img))",httpMethod = "POST",response = Map.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/selectBorrowContractImg")
    public RespDataObject<Map<String,Object>> selectBorrowContract(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "查询云南信托推送的影像资料",httpMethod = "POST",response = Map.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/selectBusinfo")
    public RespDataObject<Map<String,Object>> selectBusinfo(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "查询应还款计划",httpMethod = "POST",response = YntrustRepaymentPlanDto.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/selectRepaymentPlan")
    public RespDataObject<YntrustRepaymentPlanDto> selectRepaymentPlan(@RequestBody YntrustRepaymentPlanDto plan);

    @ApiOperation(value = "查询应还款计划与客户信息key=plan,contract",httpMethod = "POST",response = Map.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/selectRepaymentPlanMap")
    public RespDataObject<Map<String,Object>> selectRepaymentPlanMap(@RequestBody YntrustRepaymentPlanDto plan);
    
    @ApiOperation(value = "云南状态",httpMethod = "POST",response = RespStatus.class)            //新增加
    @RequestMapping("/v/ynStuats")
    public RespDataObject<Map<String,Object>> ynStuats(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "查询回款计划还款信息与支付信息key=info,pay",httpMethod = "POST",response = Map.class)
    @ApiImplicitParam(value = "orderNo",name = "订单号",required = true)
    @RequestMapping("/v/selectRepaymentInfoAndPay")
    public RespDataObject<Map<String,Object>> selectRepaymentInfoAndPay(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "删除影像资料",httpMethod = "POST",response =RespStatus.class)
    @ApiImplicitParam(value = "id",name = "数据库图片id",required = true)
    @RequestMapping("/v/deleteImg")
    public RespStatus deleteImg(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "批量删除影像资料",httpMethod = "POST",response = RespStatus.class)
    @ApiImplicitParam(value = "ids",name = "数据库图片id,多个用,隔开")
    @RequestMapping("/v/batchDeleteImg")
    public RespStatus batchDeleteImg(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "测试",httpMethod = "GET",response = RespStatus.class)
    @RequestMapping("/v/test")
    public RespStatus test();
}
