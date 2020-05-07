package com.anjbo.controller;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.finance.PaymentReportDto;
import com.anjbo.bean.finance.ReportReplyRecordDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.PaymentReportService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2018/1/24.
 * 出款报备
 */
@Controller
@RequestMapping("/credit/finance/paymentreport/v")
public class PaymentReportController extends BaseController{

    private Logger log = Logger.getLogger(getClass());

    @Resource
    private PaymentReportService paymentReportService;

    @ResponseBody
    @RequestMapping("/list")
    public RespPageData<PaymentReportDto> list(HttpServletRequest request, @RequestBody PaymentReportDto obj){
        RespPageData<PaymentReportDto> resp = new RespPageData<PaymentReportDto>();
        resp.setCode(RespStatusEnum.FAIL.getCode());
        resp.setMsg(RespStatusEnum.FAIL.getMsg());
        try{
            UserDto userDto = getUserDto(request);
            if(userDto.getAgencyId()>1){
                obj.setAgencyId(userDto.getAgencyId());
            }
            resp.setTotal(paymentReportService.listCount(obj));
            resp.setRows(paymentReportService.list(obj));
            resp.setCode(RespStatusEnum.SUCCESS.getCode());
            resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
        } catch (Exception e){
            resp.setCode(RespStatusEnum.FAIL.getCode());
            resp.setMsg(RespStatusEnum.FAIL.getMsg());
            log.error("加载回款报备异常:",e);
        }
        return resp;
    }


    @ResponseBody
    @RequestMapping("/appReportList")
    public RespDataObject<Map<String, Object>> appReportList(HttpServletRequest request, @RequestBody PaymentReportDto obj){
        RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
        RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
        try{

            UserDto userDto = getUserDto(request);
            String deptAllUid = getAuthUid(userDto);
            if(userDto.getAgencyId()>1){
                obj.setAgencyId(userDto.getAgencyId());
            }
            obj.setChannelManagerUid(deptAllUid);
            obj.setAcceptMemberUid(deptAllUid);
            List<PaymentReportDto> list = paymentReportService.appList(obj);
            List<Map<String, Object>> orderList = new ArrayList<Map<String,Object>>();
            Map<String, Object> retMap = new HashMap<String, Object>();
            String cityName = "";
            String productName = "";
            Map<String, Object> tempMap = null;
            for (PaymentReportDto dto:list){
                tempMap = new HashMap<String, Object>();
                cityName = "";
                productName = "";
                if(StringUtils.isNotBlank(dto.getCityName())){
                    cityName = dto.getCityName().replace("市", "");
                }
                if(StringUtils.isNotBlank(dto.getProductName())){
                    productName = dto.getProductName().replace("债务", "");
                }
                tempMap.put("orderNo", dto.getOrderNo());
                tempMap.put("customerName", dto.getCustomerName()==null?"-":dto.getCustomerName());
                tempMap.put("cityName", cityName);
                tempMap.put("productName",productName);
                tempMap.put("borrowingAmount", dto.getLoanAmount()==null?"-":dto.getLoanAmount());
                tempMap.put("borrowingDay", dto.getBorrowingDays());
                tempMap.put("channelManagerName", dto.getChannelManagerName()==null?"-":dto.getChannelManagerName());
                tempMap.put("cooperativeAgencyName", dto.getCooperativeAgencyName()==null?"-":dto.getCooperativeAgencyName());
                tempMap.put("acceptMemberName", dto.getAcceptMemberName()==null?"-":dto.getAcceptMemberName());
                tempMap.put("state", dto.getState());
                tempMap.put("processId", dto.getProcessId());
                String butName = "查看详情";
                String pageType ="";
                if(dto.getAcceptMemberUid().equals(userDto.getUid())
                        ||dto.getChannelManagerUid().equals(userDto.getUid())){
                    butName = "撤销回款报备,修改回款报备";
                    pageType ="cancelPaymentReport,editPaymentReport";
                }
                tempMap.put("butName", butName);
                tempMap.put("pageType", pageType);
                orderList.add(tempMap);
            }
            retMap.put("orderList", orderList);
            RespHelper.setSuccessDataObject(result, retMap);
        } catch (Exception e){
            RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
            log.error("加载回款报备异常:",e);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/detail")
    public RespDataObject<PaymentReportDto> detail(HttpServletRequest request, @RequestBody PaymentReportDto obj){
        RespDataObject<PaymentReportDto> result = new RespDataObject<PaymentReportDto>();
        try {
            PaymentReportDto dto = paymentReportService.detail(obj);
            result.setData(dto);
            RespHelper.setSuccessDataObject(result, dto);
        } catch (Exception e){
            RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
            log.error("加载回款报备详情异常:",e);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/detailByStatus")
    public RespDataObject<PaymentReportDto> detailByStatus(HttpServletRequest request, @RequestBody PaymentReportDto obj){
        RespDataObject<PaymentReportDto> result = new RespDataObject<PaymentReportDto>();
        try {
            PaymentReportDto dto = paymentReportService.detailByStatus(obj);
            result.setData(dto);
            RespHelper.setSuccessDataObject(result, dto);
        } catch (Exception e){
            RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
            log.error("加载出款报备详情异常:",e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/insert")
    public RespStatus insert(HttpServletRequest request, @RequestBody PaymentReportDto obj){
        RespStatus result = new RespStatus();
        try{
            UserDto user = getUserDto(request);
            obj.setCreateUid(user.getUid());
            obj.setCreateName(user.getName());
            //获取所有城市
            List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
            for (DictDto dictDto : dictList) {
                if(dictDto.getCode().equals(obj.getCityCode())){
                    obj.setCityName(dictDto.getName());
                    break;
                }
            }
            //获取产品名称
            List<ProductDto> prductList = getProductDtos();
            for (ProductDto productDto : prductList) {
                if(productDto.getProductCode().equals(obj.getProductCode())){
                    obj.setProductName(productDto.getProductName());
                    break;
                }
            }
            //渠道经理
            if(StringUtils.isNotBlank(obj.getChannelManagerUid())){
                obj.setChannelManagerName(CommonDataUtil.getUserDtoByUidAndMobile(obj.getChannelManagerUid()).getName());
            }
            //受理员
            if(StringUtils.isNotBlank(obj.getAcceptMemberUid())){
                obj.setAcceptMemberName(CommonDataUtil.getUserDtoByUidAndMobile(obj.getAcceptMemberUid()).getName());
            }
            //机构名称
            Map<String,Object> maps = new HashMap<String, Object>();
            maps.put("agencyId", obj.getCooperativeAgencyId());
            AgencyDto agency = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", maps, AgencyDto.class);
            if(agency!=null){
                obj.setCooperativeAgencyName(agency.getName());
            }
            obj.setStatus(2);
            obj.setAgencyId(user.getAgencyId());
            paymentReportService.insert(obj);
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("新增报备信息异常:",e);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/update")
    public RespStatus update(HttpServletRequest request, @RequestBody PaymentReportDto obj){
        RespStatus result = new RespStatus();
        try{
            if(StringUtils.isBlank(obj.getOrderNo())){
                RespHelper.setFailRespStatus(result,"保存报备记录缺少订单编号");
                return result;
            }
            if(null!=obj.getStatus()&&1==obj.getStatus()){
                PaymentReportDto tmp = paymentReportService.detailById(obj);
                if(null!=tmp&&1==tmp.getStatus()){
                    RespHelper.setFailRespStatus(result,"该回款报备已经确认回款不能重复确认");
                    return result;
                } else if(null!=tmp&&3==tmp.getStatus()){
                    RespHelper.setFailRespStatus(result,"该回款报备已经撤销不能执行确认回款操作");
                    return result;
                }
            }

            UserDto user = getUserDto(request);
            if(null!=user) {
                obj.setUpdateUid(user.getUid());
            }
            paymentReportService.update(obj);
            if(1==obj.getStatus()){
                //发送短信给渠道经理与受理员
                PaymentReportDto tmp = paymentReportService.detailById(obj);
                String acceptMemberUid = tmp.getAcceptMemberUid();
                String channelManagerUid = tmp.getChannelManagerUid();
                UserDto acceptMember = getUserDtoByUid(acceptMemberUid);
                UserDto channelManager = getUserDtoByUid(channelManagerUid);
                String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
                if(null!=acceptMember){
                    AmsUtil.smsSend(acceptMember.getMobile(), ipWhite,Constants.SMS_FINANCE_PAYMENT_INLOAN_REPORT,tmp.getCustomerName(),tmp.getLoanAmount());
                }
                if(null!=channelManager){
                    AmsUtil.smsSend(channelManager.getMobile(), ipWhite,Constants.SMS_FINANCE_PAYMENT_INLOAN_REPORT,tmp.getCustomerName(),tmp.getLoanAmount());
                }
            }
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("更新报备信息异常:",e);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/insertReplyRecord")
    public RespStatus insertReplyRecord(HttpServletRequest request, @RequestBody ReportReplyRecordDto obj){
        RespStatus result = new RespStatus();
        try{
            if (StringUtils.isBlank(obj.getOrderNo())){
                RespHelper.setFailRespStatus(result,"保存报备回复记录缺少订单编号");
                return result;
            } else if(StringUtils.isBlank(obj.getReplyContent())){
                RespHelper.setFailRespStatus(result,"请填写回复信息");
                return result;
            } else if(obj.getReportId()<=0){
                RespHelper.setFailRespStatus(result,"缺少出款报备主键");
                return result;
            }
            PaymentReportDto pay = new PaymentReportDto();
            pay.setOrderNo(obj.getOrderNo());
            pay = paymentReportService.detail(pay);
            if(null!=pay&&1==pay.getStatus()){
                RespHelper.setFailRespStatus(result,"该出款报备已放款不能再回复");
                return result;
            } else if(null!=pay&&3==pay.getStatus()){
                RespHelper.setFailRespStatus(result,"该出款报备已撤销不能再回复");
                return result;
            }
            UserDto user = getUserDto(request);
            obj.setCreateUid(user.getUid());
            int success = paymentReportService.insertReplyRecord(obj);
            if(success<1){
                RespHelper.setFailRespStatus(result,"保存报备回复记录失败");
                return result;
            }
            UserDto acceptMember = new UserDto();
            UserDto channelManager = new UserDto();
            if(null!=pay){
                acceptMember = CommonDataUtil.getUserDtoByUidAndMobile(pay.getAcceptMemberUid());
                channelManager = CommonDataUtil.getUserDtoByUidAndMobile(pay.getChannelManagerUid());
                pay.setAcceptMemberName(acceptMember.getName());
            }
            String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
            //发送短信给受理员
            if(null!=acceptMember&&StringUtils.isNotBlank(acceptMember.getMobile())){
                AmsUtil.smsSend(acceptMember.getMobile(), ipWhite,Constants.SMS_FINANCE_PAYMENT_INLOAN_REPORT,pay.getCustomerName(),pay.getLoanAmount());
            }
            //发送短信给渠道经理
            if(null!=channelManager&&StringUtils.isNotBlank(channelManager.getMobile())){
                AmsUtil.smsSend(channelManager.getMobile(), ipWhite,Constants.SMS_FINANCE_PAYMENT_INLOAN_REPORT,pay.getCustomerName(),pay.getLoanAmount());
            }
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("新增修改回复记录信息异常:",e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/cancelReport")
    public RespDataObject<Map<String,Object>> cancelReport(HttpServletRequest request, @RequestBody PaymentReportDto obj){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try{
            if (StringUtils.isBlank(obj.getOrderNo())){
                RespHelper.setFailRespStatus(result,"撤销报备缺少订单编号");
                return result;
            }

            PaymentReportDto reportDto = new PaymentReportDto();
            reportDto.setOrderNo(obj.getOrderNo());
            reportDto = paymentReportService.detail(reportDto);
            if(null!=reportDto&&1==reportDto.getStatus()){
                RespHelper.setFailRespStatus(result,"该回款报备已回款不能再撤销");
                return result;
            } else if(null!=reportDto&&3==reportDto.getStatus()){
                RespHelper.setFailRespStatus(result,"该回款报备已撤销不能再重复撤销");
                return result;
            }

            UserDto userDto = getUserDto(request);
            paymentReportService.cancelReport(obj,userDto);

            Map<String, Object> tempMap = new HashMap<String, Object>();
            if(StringUtils.isNotBlank(obj.getArrangement())
                    &&obj.getArrangement().contains("orderList")){
                OrderListDto orderDto = new OrderListDto();
                orderDto.setOrderNo(obj.getOrderNo());
                orderDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderDto, OrderListDto.class);
                encapsulation(tempMap,orderDto,userDto);
            }
            RespHelper.setSuccessDataObject(result,tempMap);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("撤销出款报备信息异常:",e);
        }
        return result;
    }
    public void encapsulation(Map<String,Object> tempMap,OrderListDto orderDto,UserDto userDto){
        if(null==orderDto) orderDto = new OrderListDto();
        String cityName = "";
        String productName = "";
        if(StringUtils.isNotBlank(orderDto.getCityName())){
            cityName = orderDto.getCityName().replace("市", "");
        }
        if(StringUtils.isNotBlank(orderDto.getProductName())){
            productName = orderDto.getProductName().replace("债务", "");
        }
        if(StringUtils.isBlank(productName)){
            productName = orderDto.getProductName();
        }
        tempMap.put("orderNo", orderDto.getOrderNo());
        tempMap.put("customerName", orderDto.getCustomerName()==null?"-":orderDto.getCustomerName());
        tempMap.put("cityName", cityName);
        tempMap.put("productName",productName);
        tempMap.put("borrowingAmount", orderDto.getBorrowingAmount()==null?"-":orderDto.getBorrowingAmount());
        tempMap.put("borrowingDay", orderDto.getBorrowingDay());
        tempMap.put("channelManagerName", orderDto.getChannelManagerName()==null?"-":orderDto.getChannelManagerName());
        tempMap.put("cooperativeAgencyName", orderDto.getCooperativeAgencyName()==null?"-":orderDto.getCooperativeAgencyName());
        tempMap.put("acceptMemberName", orderDto.getAcceptMemberName()==null?"-":orderDto.getAcceptMemberName());
        tempMap.put("state", orderDto.getState());
        tempMap.put("currentHandler", orderDto.getCurrentHandler());
        tempMap.put("processId", orderDto.getProcessId());
        tempMap.put("productCode",orderDto.getProductCode());
        String butName = "";
        String pageType ="";
        boolean isChangLoan = "03".equals(orderDto.getProductCode());
        if(orderDto.getState().contains("待结清原贷款")){
            if(orderDto.getCurrentHandlerUid().equals(userDto.getUid())){
                tempMap.put("butName", "结清原贷款");
                tempMap.put("pageType", orderDto.getProcessId());
            }
            if(!isChangLoan) {
                tempMap.put("appShowKey1", "预计出款");
                tempMap.put("appShowKey2", "结清原贷款");
                tempMap.put("appShowValue1", orderDto.getAppShowValue1()==null?"-":orderDto.getAppShowValue1());
                tempMap.put("appShowValue2", orderDto.getAppShowValue2());
            }
        }else if(orderDto.getState().contains("待取证")&&!orderDto.getState().contains("待取证抵押")){
            if(!isChangLoan) {
                tempMap.put("appShowKey1", "预计取证");
                tempMap.put("appShowKey2", "取证地点");
                tempMap.put("appShowValue1", orderDto.getAppShowValue1());
                tempMap.put("appShowValue2", orderDto.getAppShowValue2());
            }
            if(orderDto.getCurrentHandlerUid().equals(userDto.getUid())){
                tempMap.put("butName", "取证");
                tempMap.put("pageType", orderDto.getProcessId());
            }
        }else if(orderDto.getState().contains("待注销")){
            if(!isChangLoan) {
                tempMap.put("appShowKey1", "预计注销");
                tempMap.put("appShowKey2", "国土局");
                tempMap.put("appShowValue1", orderDto.getAppShowValue1());
                tempMap.put("appShowValue2", orderDto.getAppShowValue2());
            }

            if(orderDto.getCurrentHandlerUid().equals(userDto.getUid())){
                tempMap.put("butName", "注销");
                tempMap.put("pageType", orderDto.getProcessId());
            }
        }else if(orderDto.getState().contains("待过户")){
            if(!isChangLoan) {
                tempMap.put("appShowKey1", "预计过户");
                tempMap.put("appShowKey2", "国土局");
                tempMap.put("appShowValue1", orderDto.getAppShowValue1());
                tempMap.put("appShowValue2", orderDto.getAppShowValue2());
            }
            if(orderDto.getCurrentHandlerUid().equals(userDto.getUid())){
                tempMap.put("butName", "过户");
                tempMap.put("pageType", orderDto.getProcessId());
            }
        }else if(orderDto.getState().contains("待领新证")){
            if(!isChangLoan) {
                tempMap.put("appShowKey1", "预计领新证");
                tempMap.put("appShowKey2", "国土局");
                tempMap.put("appShowValue1", orderDto.getAppShowValue1());
                tempMap.put("appShowValue2", orderDto.getAppShowValue2());
            }

            if(orderDto.getCurrentHandlerUid().equals(userDto.getUid())){
                tempMap.put("butName", "领新证");
                tempMap.put("pageType", orderDto.getProcessId());
            }
        }else if(orderDto.getState().contains("待回款")){
        }else if(orderDto.getState().contains("待抵押")){
            if(!isChangLoan) {
                tempMap.put("appShowKey1", "预计抵押");
                tempMap.put("appShowKey2", "国土局");
                tempMap.put("appShowValue1", orderDto.getAppShowValue1());
                tempMap.put("appShowValue2", orderDto.getAppShowValue2());
            }
            if(orderDto.getCurrentHandlerUid().equals(userDto.getUid())){
                tempMap.put("butName", "抵押");
                tempMap.put("pageType", orderDto.getProcessId());
            }
        }
        butName = MapUtils.getString(tempMap,"butName");
        pageType = MapUtils.getString(tempMap,"pageType");
        butName = StringUtils.isBlank(butName)?"回款报备":butName+",回款报备";
        pageType = StringUtils.isBlank(pageType)?"editPaymentReport":pageType+",editPaymentReport";
        tempMap.put("butName", butName);
        tempMap.put("pageType", pageType);
    }
    @ResponseBody
    @RequestMapping("/listMap")
    public RespDataObject<Map<String,Object>> listMap(HttpServletRequest request, @RequestBody PaymentReportDto obj){
        RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
        try{
            if(StringUtils.isBlank(obj.getRelationOrderNo())){
                resp.setCode(RespStatusEnum.FAIL.getCode());
                resp.setMsg(RespStatusEnum.FAIL.getMsg());
                return resp;
            }
            Map<String,Object> map = paymentReportService.listMap(obj);
            resp.setData(map);
            resp.setCode(RespStatusEnum.SUCCESS.getCode());
            resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
        } catch (Exception e){
            log.error("加载回款报备异常:",e);
        }
        return resp;
    }
    public String getAuthUid(UserDto userDto){
        String deptAllUid = "";
        if(userDto.getAuthIds().contains("1")){
            //查看全部订单
        }else if(userDto.getAuthIds().contains("2")){
            //查看部门订单
            userDto.setCreateTime(null);
            userDto.setUpdateTime(null);
            RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/v/selectUidsByDeptId", userDto, Map.class);
            deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
        }else{
            //查看自己订单
            deptAllUid = userDto.getUid();
        }
        return deptAllUid;
    }

}
