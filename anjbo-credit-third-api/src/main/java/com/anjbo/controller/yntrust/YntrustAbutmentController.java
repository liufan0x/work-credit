package com.anjbo.controller.yntrust;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.bean.yntrust.*;
import com.anjbo.common.*;
import com.anjbo.controller.BaseController;
import com.anjbo.service.yntrust.*;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.huarong.StringUtils;
import com.anjbo.utils.yntrust.Abutment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 * 云南信托控制器
 */
@Controller
@RequestMapping("/credit/third/api/yntrust")
public class YntrustAbutmentController extends BaseController {
    private Logger log = Logger.getLogger(getClass());
    @Resource
    private YntrustBorrowService yntrustBorrowService;
    @Resource
    private YntrustContractService yntrustContractService;
    @Resource
    private YntrustMappingService yntrustMappingService;
    @Resource
    private YntrustRepaymentInfoService yntrustRepaymentInfoService;
    @Resource
    private YntrustRepaymentPayService yntrustRepaymentPayService;
    @Resource
    private YntrustRepaymentPlanService yntrustRepaymentPlanService;
    @Resource
    private YntrustRequestFlowService yntrustRequestFlowService;
    @Resource
    private YntrustImageService yntrustImageService;
    @Resource
    private YntrustLoanService yntrustLoanService;

    /**
     * 保存云南信托借款人信息
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/addBorrow")
    public RespStatus addBorrow(HttpServletRequest request, @RequestBody YntrustBorrowDto obj){
        RespStatus respStatus = new RespStatus();
        try{
            if(StringUtils.isBlank(obj.getOrderNo())){
                RespHelper.setFailRespStatus(respStatus,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return respStatus;
            }
            UserDto user = getUserDto(request);
            obj.setCreateUid(user.getUid());
            obj.setUpdateUid(user.getUid());
            yntrustBorrowService.insert(obj);
            RespHelper.setSuccessRespStatus(respStatus);
        } catch (Exception e){
            log.error("保存云南信托借款人信息异常:",e);
            RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
        }
        return respStatus;
    }

    /**
     * 保存云南信托合同信息
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/addContract")
    public RespStatus addContract(HttpServletRequest request, @RequestBody YntrustContractDto obj){
        RespStatus respStatus = new RespStatus();
        try {
            if(StringUtils.isBlank(obj.getOrderNo())){
                RespHelper.setFailRespStatus(respStatus,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return respStatus;
            }
            UserDto user = getUserDto(request);
            obj.setCreateUid(user.getUid());
            obj.setUpdateUid(user.getUid());
            yntrustContractService.insert(obj);
            RespHelper.setSuccessRespStatus(respStatus);
        } catch (Exception e){
            log.error("保存云南信托合同信息异常:",e);
            RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
        }
        return respStatus;
    }

    /**
     * 保存云南信托借款与合同信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/addBorrowAndContract")
    public RespStatus addBorrowAndContract(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus respStatus = new RespStatus();

        Object borrowObj = MapUtils.getObject(map, "borrow");
        Object contractObj = MapUtils.getObject(map, "contract");
        if(null==borrowObj||null==contractObj){
            RespHelper.setFailRespStatus(respStatus,RespStatusEnum.PARAMETER_ERROR.getMsg());
            return respStatus;
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        UserDto user = null;
        String orderNo = "";
        try{
            user = getUserDto(request);
            Map<String,Object> borrow = gson.fromJson(gson.toJson(borrowObj),Map.class);
            if(StringUtils.isBlank(MapUtils.getString(borrow,"orderNo"))){
                RespHelper.setFailRespStatus(respStatus,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return respStatus;
            }
            String ynProductCode = MapUtils.getString(borrow,"ynProductCode");
            String ynProductName = MapUtils.getString(borrow,"ynProductName");
            map.put("ynProductCode",ynProductCode);
            map.put("ynProductName",ynProductName);
            orderNo = MapUtils.getString(borrow,"orderNo");
            borrow.put("createUid",user.getUid());
            borrow.put("updateUid",user.getUid());
            yntrustBorrowService.insertMap(borrow);
        } catch (Exception e){
            log.error("保存云南信托借款人信息异常:",e);
            RespHelper.setFailRespStatus(respStatus,"保存借款人信息失败");
            return respStatus;
        }
        try {
            Map<String,Object> contract = gson.fromJson(gson.toJson(contractObj),Map.class);
            if(StringUtils.isBlank(MapUtils.getString(contract,"orderNo"))){
                RespHelper.setFailRespStatus(respStatus,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return respStatus;
            }
            contract.put("createUid",user.getUid());
            contract.put("updateUid",user.getUid());
            /*
            boolean verification = Abutment.verification(MapUtils.getString(contract,"accountno"));
            if(!verification){
                RespHelper.setFailRespStatus(respStatus,"放款卡银行账号格式有误");
                return respStatus;
            }
            verification = Abutment.verification(MapUtils.getString(contract,"paymentsBankAccount"));
            if(!verification){
                RespHelper.setFailRespStatus(respStatus,"回款卡银行账号格式有误");
                return respStatus;
            }*/
            yntrustContractService.insertMap(contract);
            RespHelper.setSuccessRespStatus(respStatus);
        } catch (Exception e){
            log.error("保存云南信托合同信息异常:",e);
            RespHelper.setFailRespStatus(respStatus,"保存合同信息失败");
            return respStatus;
        }
        try{
            map.put("orderNo",orderNo);
            respStatus = plusYnTrustInfo(request,map);
        } catch (Exception e){
            RespHelper.setFailRespStatus(respStatus,RespStatusEnum.FAIL.getMsg());
            log.error("推送数据给云南信托异常:",e);
        }
        return respStatus;
    }
    /**
     * 保存云南信托影像资料
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/addImage")
    public RespStatus addImage(HttpServletRequest request, @RequestBody Map<String,Object> map){
        RespStatus respStatus = new RespStatus();
        try {
            UserDto user = getUserDto(request);
            map.put("createUid",user.getUid());
            yntrustImageService.insert(map);
            RespHelper.setSuccessRespStatus(respStatus);
        } catch (Exception e){
            log.error("保存云南信托影像资料异常:",e);
            RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
        }
        return respStatus;
    }

    /**
     * 批量保存云南信托影像资料
     * @param request
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/batchAddImage")
    public RespStatus batchAddImage(HttpServletRequest request, @RequestBody List<Map<String,Object>> list){
        RespStatus respStatus = new RespStatus();
        try{
            UserDto user = getUserDto(request);
            for (Map<String,Object> m:list){
                m.put("createUid",user.getUid());
            }
            yntrustImageService.batchInsert(list);
            RespHelper.setSuccessRespStatus(respStatus);
        } catch (Exception e){
            log.error("批量保存云南信托影像资料异常:",e);
            RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
        }
        return respStatus;
    }

    /**
     * 查询信托专户余额
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/queryTrustAccount")
    public RespDataObject<Map<String,Object>> queryTrustAccount(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.queryTrustAccount(result, MapUtils.getString(map,"orderNo"));
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询信托专户余额异常:",e);
        }
        return result;
    }

    /**
     * 推送借款,合同信息,影像资料给云南信托
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/plusYnTrustInfo")
    public RespStatus plusYnTrustInfo(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try{
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.plusYnTrustInfo(result,map);
            if(RespStatusEnum.SUCCESS.getCode().equals(result.getCode())){
                RespDataObject<Map<String, Object>> qrCode = getQRCode(request, map);
                if(RespStatusEnum.SUCCESS.getCode().equals(qrCode.getCode())){
                    String key = Constants.SMS_YNTRUST_QR_CODE;
                    OrderBaseBorrowDto dto = new OrderBaseBorrowDto();
                    dto.setOrderNo(orderNo);
                      
                    Object borrowObj = MapUtils.getObject(map, "borrow");
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    Map<String,Object> borrow = gson.fromJson(gson.toJson(borrowObj),Map.class);
//                    HttpUtil httpUtil = new HttpUtil();
//                    JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto);
//                    if(null!=jsons&&jsons.containsKey("data")&&null!=jsons.get("data")) {
//                        String data = jsons.getString("data");
//                    Map<String,Object> borrow = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(data, Map.class);
//                    String acceptMemberUid = MapUtils.getString(borrow,"acceptMemberUid");
                    String borrowerName = MapUtils.getString(borrow,"shortName");
                    String telephoneNo= MapUtils.getString(borrow,"telephoneNo");
                    if(StringUtil.isNotBlank(telephoneNo)){
                        String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
//                        UserDto userDtoByUid = getUserDtoByUid(acceptMemberUid);
                        AmsUtil.smsSend2(telephoneNo, ipWhite,key,"hyt","客户名: "+borrowerName+" "+MapUtils.getString(qrCode.getData(),"url"));
                    } else {
                        log.info("没有订单为:"+orderNo+"电话号码信息,没有发送电子合同链接地址短信");
                    }
//                    }
                    log.info("获取成功:"+qrCode.getData());
                } else {
                    log.info("云南信息推送信息成功获取合同电子签证链接状态为"+qrCode.getMsg()+",请通过/credit/third/api/yntrust/v/getQRCode获取,");
                }
            }
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("推送数据给云南信托异常",e);
        }
        return result;
    }

    /**
     * 推送影像资料
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/pushYnTrustImg")
    public RespStatus pushYnTrustImg(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try{
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.pushImg(result,orderNo,null);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("推送云南信托影像资料异常",e);
        }
        return result;
    }

    /**
     * 测试
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/test")
    public RespDataObject<List<Map<String,Object>>> testImag(@RequestBody YntrustRepaymentPlanDto obj){
        RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
        System.out.println(obj);
        //yntrustLoanService.updateByOrderNo(loan);
        //yntrustMappingService.update(mapping);
        return result;
    }

    /**
     * 发送放款指令
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/confirmPayment")
    public RespStatus confirmPayment(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try{
            if(!map.containsKey("orderNo")){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.confirmPayment(result,MapUtils.getString(map,"orderNo"));
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("云南信托发送放款指令异常:",e);
        }
        return result;
    }

    /**
     * 补单放款指令 只有在查获“放款指令发送失败”状态
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/remedyPayment")
    public RespStatus remedyPayment(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try {
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.remedyPayment(result,orderNo);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("云南信托补单放款指令异常:",e);
        }
        return result;
    }
    /**
     * 放款状态查询
     * 处理状态：放款中=0,成功=1,失败=2,业务不执行=3,异常 =4，放款指令发送失败=9
     * 只有在查获“放款指令发送失败”状态，才能调用“补单放款接口”；
     * 只有在放款查询中，查获“失败”状态，贷款服务机构才可考虑是否转其他通道放款。为避免重复放款，建议不要做自动切换通道放款。发生失败后，请先调用取消放款接口，再转其他通道放款。
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/queryTradingStatus")
    public RespDataObject<List<YntrustLoanDto>> queryTradingStatus(HttpServletRequest request,@RequestBody YntrustLoanDto obj){
        RespDataObject<List<YntrustLoanDto>> result = new RespDataObject<List<YntrustLoanDto>>();
        try{
            String orderNo = obj.getOrderNo();
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailDataObject(result,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            YntrustLoanDto tmp = new YntrustLoanDto();
            tmp.setOrderNo(obj.getOrderNo());
            tmp.setCancelStatus(1);
            List<YntrustLoanDto> list = yntrustLoanService.list(tmp);
            YntrustLoanDto loan = null;
            if(null!=list&&list.size()>0){
                loan = list.get(0);
            }
            if(null!=loan&&"2".equals(loan.getAuditStatus())&&1==loan.getProcessStatus()){
                RespHelper.setSuccessDataObject(result,list);
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.queryTradingStatus(result,orderNo);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询云南信托放款状态信息异常",e);
        }
        return result;
    }

    /**
     * 获取合同信息电子签证的链接与二维码图片
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/getQRCode")
    public RespDataObject<Map<String,Object>> getQRCode(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailDataObject(result,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.getQRCode(result,orderNo);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("获取云南信托合同信息电子签证的链接与二维码图片异常:",e);
        }
        return result;
    }

    /**
     * 获取云信签章合同文件
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/getContractFile")
    public RespDataObject<Map<String,Object>> getContractFile(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try{
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailDataObject(result,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.getContractFile(result,orderNo,map);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("获取云信签章合同文件异常:",e);
        }
        return result;
    }

    /**
     * 保存应还款计划并推送
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/addRepaymentPlan")
    public RespStatus addRepaymentPlan(HttpServletRequest request, @RequestBody YntrustRepaymentPlanDto obj){
        RespStatus result = new RespStatus();
        try{
            if(StringUtils.isBlank(obj.getOrderNo())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            obj.setCreateUid(user.getUid());
            obj.setUpdateUid(user.getUid());
            yntrustRepaymentPlanService.insert(obj);
            Abutment abutment = getAbutment(request, user);
            abutment.createRepaySchedule(result,obj.getOrderNo());
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("推送云南信托应还款计划异常:",e);
        }
        return result;
    }

    /**
     * 查询推送给云南信托的最新还款计划
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/queryRepaySchedule")
    public RespDataObject<Map<String,Object>> queryRepaySchedule(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtil.isBlank(orderNo)){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.queryRepaySchedule(result,orderNo);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询推送给云南信托的最新还款计划异常:",e);
        }
        return result;
    }
    /**
     * 保存回款计划的还款信息
     * @param request
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/addRepaymentInfoAndPayInfo")
    public RespStatus addRepaymentInfoAndPayInfo(HttpServletRequest request,@RequestBody YntrustRepaymentInfoDto info){
        RespStatus result = new RespStatus();
        if(null==info||StringUtils.isBlank(info.getOrderNo())){
            RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
            return result;
        }
        UserDto userDto = getUserDto(request);
        try{
            Abutment abutment = getAbutment(request, userDto);
            abutment.repaymentOrderInfo(result,info.getOrderNo(),info);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,"保存还款信息失败");
            log.error("保存回款计划还款信息异常:",e);
            return result;
        }
        return result;
    }

    /**
     * 还款状态查询
     * @param request
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/queryRepayOrder")
    public RespDataObject<YntrustRepaymentInfoDto> queryRepayOrder(HttpServletRequest request,@RequestBody YntrustRepaymentInfoDto info){
        RespDataObject<YntrustRepaymentInfoDto> result = new RespDataObject<YntrustRepaymentInfoDto>();
        try{
            String orderNo = info.getOrderNo();
            if(StringUtils.isBlank(info.getOrderNo())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            info = yntrustRepaymentInfoService.select(info);
            if(null!=info
                    &&null!=info.getStatus()
                    &&(7==info.getStatus()
                    ||6==info.getStatus())){
                RespHelper.setSuccessDataObject(result,info);
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.queryRepayOrder(result,null,orderNo,false);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("还款订单状态查询异常:",e);
        }
        return result;
    }

    /**
     * 订单支付
     * @param request
     * @param pay
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/paymentOrder")
    public RespDataObject<Map<String,Object>> paymentOrder(HttpServletRequest request,@RequestBody YntrustRepaymentPayDto pay){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try{
            String orderNo = pay.getOrderNo();
            if(StringUtils.isBlank(pay.getOrderNo())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            pay.setCreateUid(user.getUid());
            pay.setUpdateUid(user.getUid());
            Abutment abutment = getAbutment(request, user);
            abutment.paymentOrder(result,orderNo,pay);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("云南信托订单支付异常:",e);
        }
        return result;
    }

    /**
     * 支付状态查询
     * @param request
     * @param pay
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/queryPaymentOrder")
    public RespDataObject<YntrustRepaymentPayDto> queryPaymentOrder(HttpServletRequest request,@RequestBody YntrustRepaymentPayDto pay){
        RespDataObject<YntrustRepaymentPayDto> result = new RespDataObject<YntrustRepaymentPayDto>();
        try{
            if(StringUtils.isBlank(pay.getOrderNo())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            YntrustRepaymentPayDto tmp = yntrustRepaymentPayService.select(pay);
            if(null==tmp){
                RespHelper.setSuccessDataObject(result,pay);
                return result;
            }
            if("1".equals(tmp.getStatus())||"7".equals(tmp.getStatus())){
                RespHelper.setSuccessDataObject(result,tmp);
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            //abutment.queryPaymentOrder(result,pay.getOrderNo());
            //TODO 使用还款状态接口
            abutment.queryRepayOrder(null,result,pay.getOrderNo(),true);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("订单支付状态查询异常:",e);
        }
        return  result;
    }

    /**
     * 取消贷款
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/cancelLoan")
    public RespStatus cancelLoan(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try{
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.cancelLoan(result,orderNo);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("取消贷款异常:",e);
        }
        return result;
    }

    /**
     * 更新还款计划
     * @param request
     * @param plan
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/updateRepaySchedule")
    public RespStatus updateRepaySchedule(HttpServletRequest request,@RequestBody YntrustRepaymentPlanDto plan){
        RespStatus result = new RespStatus();
        try{
            if(StringUtils.isBlank(plan.getOrderNo())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            /**
             * map包含两个key
             * changeReason:还款计划变更原因(0=项目结清,1=提前部分还款,2=错误更正),
             * scheduleType:还款计划类型(如果为空则为正常未改变，为0代表提前还款（提前部分或全额还款）类型)
             */
            abutment.updateRepaySchedule(result,plan.getOrderNo(),plan);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("更新还款计划异常:",e);
        }
        return result;
    }

    /**
     * 更新逾期费用
     * @param request
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/updateOverDueFee")
    public RespStatus updateOverDueFee(HttpServletRequest request,@RequestBody YntrustRepaymentInfoDto info){
        RespStatus result = new RespStatus();
        try {
            if(StringUtil.isBlank(info.getOrderNo())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            yntrustRepaymentInfoService.insert(info);
            Abutment abutment = getAbutment(request, user);
            abutment.updateOverDueFee(result,info.getOrderNo(),info);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("更新云南信托逾期费用异常:",e);
        }
        return result;
    }

    /**
     * 修改还款信息
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/modifyRepay")
    public RespStatus modifyRepay(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try{
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtil.isBlank(orderNo)){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto userDto = getUserDto(request);
            Abutment abutment = getAbutment(request, userDto);
            abutment.modifyRepay(result,orderNo);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("修改还款计划异常:",e);
        }
        return result;
    }
    /**
     * 查询云南信托推送的借款人与合同信息与影像资料
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/selectBorrowContractImg")
    public RespDataObject<Map<String,Object>> selectBorrowContract(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailDataObject(result,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            YntrustBorrowDto borrow = new YntrustBorrowDto();
            borrow.setOrderNo(orderNo);
            borrow.setPushStatus(1);
            borrow = yntrustBorrowService.select(borrow);
            YntrustContractDto contract = new YntrustContractDto();
            contract.setOrderNo(orderNo);
            contract.setPushStatus(1);
            contract = yntrustContractService.select(contract);
            Map<String,Object> img = yntrustImageService.listByMap(map);
            Map<String,Object> returnParam = new HashMap<String,Object>();
            YntrustMappingDto mapping  = new YntrustMappingDto();
            mapping.setOrderNo(orderNo);
            mapping = yntrustMappingService.select(mapping);
            if(null!=mapping){
                if(StringUtil.isBlank(mapping.getYnProductName())){
                    borrow.setYnProductName("一期");
                } else {
                    borrow.setYnProductName(mapping.getYnProductName());
                    borrow.setYnProductCode(mapping.getYnProductCode());
                }
                borrow.setUniqueId(mapping.getUniqueId());
            }
            returnParam.put("borrow",borrow);
            returnParam.put("contract",contract);
            returnParam.put("img",img);
            RespHelper.setSuccessDataObject(result,returnParam);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询云南信托推送的借款人与合同信息异常:",e);
        }
        return result;
    }

    /**
     * 查询云南信托推送的影像资料
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/selectBusinfo")
    public RespDataObject<Map<String,Object>> selectBusinfo(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailDataObject(result,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            Map<String,Object> img = yntrustImageService.listByMap(map);
            RespHelper.setSuccessDataObject(result,img);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询云南信托推送的影像资料:",e);
        }
        return result;
    }

    /**
     * 查询应还款计划
     * @param request
     * @param plan
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/selectRepaymentPlan")
    public RespDataObject<YntrustRepaymentPlanDto> selectRepaymentPlan(HttpServletRequest request,@RequestBody YntrustRepaymentPlanDto plan){
        RespDataObject<YntrustRepaymentPlanDto> result = new RespDataObject<YntrustRepaymentPlanDto>();
        try{
            if(StringUtils.isBlank(plan.getOrderNo())){
                RespHelper.setFailDataObject(result,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            plan = yntrustRepaymentPlanService.select(plan);
            RespHelper.setSuccessDataObject(result,plan);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询应还款计划异常:",e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/v/selectRepaymentPlanMap")
    public RespDataObject<Map<String,Object>> selectRepaymentPlanMap(HttpServletRequest request,@RequestBody YntrustRepaymentPlanDto plan){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try{
            if(StringUtils.isBlank(plan.getOrderNo())){
                RespHelper.setFailDataObject(result,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            YntrustContractDto contract = new YntrustContractDto();
            contract.setOrderNo(plan.getOrderNo());
            contract = yntrustContractService.select(contract);
            plan = yntrustRepaymentPlanService.select(plan);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("plan",plan);
            map.put("contract",contract);
            RespHelper.setSuccessDataObject(result,map);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询应还款计划异常:",e);
        }
        return result;
    }

    /**
     *  查询回款计划还款信息与支付信息
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/selectRepaymentInfoAndPay")
    public RespDataObject<Map<String,Object>> selectRepaymentInfoAndPay(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtils.isBlank(orderNo)){
                RespHelper.setFailDataObject(result,null,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            YntrustRepaymentInfoDto info = new YntrustRepaymentInfoDto();
            info.setOrderNo(orderNo);
            info = yntrustRepaymentInfoService.select(info);
            YntrustRepaymentPayDto pay = new YntrustRepaymentPayDto();
            pay.setOrderNo(orderNo);
            pay = yntrustRepaymentPayService.select(pay);
            map.put("info",info);
            map.put("pay",pay);
            RespHelper.setSuccessDataObject(result,map);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询回款计划还款信息与支付信息异常:",e);
        }
        return result;
    }

    /**
     * 删除影像资料
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/deleteImg")
    public RespStatus deleteImg(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try{
            Integer id = MapUtils.getInteger(map,"id");
            if(null==id){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            map.put("updateUid",user.getUid());
            map.put("ids",id);
            yntrustImageService.delete(map);
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("删除云南信托影像资料异常:",e);
        }
        return result;
    }

    /**
     * 批量删除影像资料
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/batchDeleteImg")
    public RespStatus batchDeleteImg(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try{
            if(!map.containsKey("ids")||null==MapUtils.getObject(map,"ids")){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            map.put("updateUid",user.getUid());
            yntrustImageService.batchDelete(map);
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("批量删除云南信托影像资料异常:",e);
        }
        return result;
    }

    /**
     * 放款对账查询
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/loaninfo")
    public RespDataObject<Map<String,Object>> loaninfo(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            if(!map.containsKey("startDate")||!map.containsKey("endDate")){
                RespHelper.setFailRespStatus(result,"请传开始时间与结束时间");
                return result;
            }
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.loaninfo(result,"",map);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("云南信托放款对账查询异常:",e);
        }
        return result;
    }

    /**
     * 重置配置信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/restart")
    public RespStatus restart(HttpServletRequest request){
        RespStatus result = new RespStatus();
        try {
            UserDto user = getUserDto(request);
            Abutment abutment = getAbutment(request, user);
            abutment.clear();
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("重置配置信息异常:",e);
        }
        return result;
    }
    private Abutment getAbutment(HttpServletRequest request,UserDto user){
        Abutment abutment = Abutment.getInstance();
        initService(abutment,user);
        return abutment;
    }
    private void initService(Abutment abutment,UserDto userDto){
        abutment.initService(yntrustBorrowService,
                            yntrustContractService,
                            yntrustMappingService,
                            yntrustRepaymentInfoService,
                            yntrustRepaymentPayService,
                            yntrustRepaymentPlanService,
                            yntrustRequestFlowService,
                            yntrustImageService,
                            yntrustLoanService,
                            userDto);
    }
    @InitBinder
    public void InitBinder(HttpServletRequest request,
                           ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


}
