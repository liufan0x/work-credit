package com.anjbo.utils.yntrust;


import com.anjbo.bean.user.UserDto;
import com.anjbo.bean.yntrust.*;
import com.anjbo.common.*;
import com.anjbo.service.yntrust.*;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.UidUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2018/3/5.
 * 对接云南信托
 */

public class Abutment {
    private Logger log = Logger.getLogger(getClass());
    /**
     * 私钥名称
     */
    public static String PRIVATE_KEY;
    public static String PRIVATE_KEY_NAME = "base.yntrust.private.key";
    /**
     * 公钥名称
     */
    public static String PUBLIC_KEY;
    public static String PUBLIC_KEY_NAME = "base.yntrust.public.key";
    /**
     * 商户号
     */
    public static String MERID;
    public static  String MERID_KEY_NAME = "base.yntrust.mer.id";
    /**
     * 商户密钥
     */
    public static String SECRETKEY;
    private static String SECRETKEY_KEY_NAME = "base.yntrust.secret.key";
    /**
     * 私钥路径
     */
    public static String PRIVATE_KEY_PATH;
    /**
     * 私钥密码
     */
    public static String PRIVATECERT_PASSWORD;
    public static String PRIVATECERT_PASSWORD_KEY_NAME = "base.yntrust.privateCert.password";
    /**
     * 配置文件名称
     */
    public static String PROPERTIES_NAME = "third-config.properties";
    /**
     * 云南信托URL
     */
    public static String YNTRUST_URL;
    public static String YNTRUST_URL_KEY_NAME = "link.yntrust.url";
    /**
     * 云南信托产品编号
     */
    public String YNTRUST_PRODUCT_CODE;
    public static String YNTRUST_PRODUCT_CODE_KEY = "base.yntrust.product.code";
    /**
     * 云南信托专户账号
     */
    public String YNTRUST_ACCOUNT_NO;
    public static String YNTRUST_ACCOUNT_NO_KEY = "base.yntrust.account.no";
    /**
     * 云南信托上传文件
     */
    private String YNTRUST_FILE_URL;
    public static String YNTRUST_FILE_URL_KEY = "link.yntrust.file.url";

    private static RestTemplate restTemplate;

    private Gson gson;

    private ObjectMapper mapper;

    private static HttpUtil httpUtil;

    private Object obj =  new Object();

    /**
     * 创建借款人
     */
    private static String CBACTION = "/Loan/CreateBorrower";
    /**
     * 创建订单：借款人与合同一体化接口
     */
    private static String COACTION = "/Loan/Borrower/Createorder";
    /**
     * 创建借款合同
     */
    private static String CLACTION = "/Loan/CreateLoan";
    /**
     * 创建还款计划
     */
    private static String CRSACTION = "/Loan/CreateRepaySchedule";
    /**
     * 更新还款计划
     */
    private static String UPDATE_REPAY_SCHEDULE = "/Loan/UpdateRepaySchedule";
    /**
     * 上传文件
     */
    private static String FUACTION = "/Loan/FileUpload";
    /**
     * 上传身份证
     */
    private static String IUACTION = "/Loan/ImageUpload";
    /**
     * 发送放款指令
     */
    private static String CPACTION = "/Loan/ConfirmPayment";
    /**
     * 补单放款指令
     */
    private static String RPACTION = "/Loan/RemedyPayment";
    /**
     * 放款状态查询
     */
    private static String QTSACTION = "/Loan/QueryTradingStatus";
    /**
     * 取消贷款
     */
    private static String CCLACTION = "/Loan/CancelLoan";
    /**
     * 查询流水记录
     */
    private static String QBSACTION = "/Loan/QueryTrustAccountStatement";

    /**
     * 信托专户查询 查询余额
     */
    private static String QUERY_TRUST_ACCOUNT = "/Loan/QueryTrustAccount";
    /**
     * 获取二维码接口
     */
    private static String GET_QRCODE = "/Loan/GetQRCode";
    /**
     * 获取云信签章合同文件
     */
    private static String GET_CONTRACT_FILE = "/Loan/GetContractFile";
    /**
     * 还款订单信息接口
     */
    private static String REPAYMENT_ORDER_INFO = "/Loan/RepaymentOrderInfo";
    /**
     *还款订单查询接口
     */
    private static String QUERY_REPAY_ORDER = "/Loan/QueryRepayOrder";
    /**
     * 订单支付接口
     */
    private static String PAYMENTORDER = "/Loan/PaymentOrder";
    /**
     * 订单支付状态查询接口
     */
    private static String QUERY_PAYMENT_ORDER = "/Loan/QueryPaymentOrder";
    /**
     * 更新逾期费用
     */
    private static String UPDATE_OVERDUEFEE = "/Loan/UpdateOverDueFee";
    /**
     * 修改还款信息
     */
    private static String MODIFY_REPAY = "/Loan/ModifyRepay";
    /**
     * 放款对账查询
     */
    private static String LOAN_INFO = "/Loan/LoanInfo";
    /**
     * 查询最新的还款计划
     */
    private static String QUERY_REPAY_SCHEDULE = "/Loan/QueryRepaySchedule";


    private YntrustBorrowService yntrustBorrowService;

    private YntrustContractService yntrustContractService;

    private YntrustMappingService yntrustMappingService;

    private YntrustRepaymentInfoService yntrustRepaymentInfoService;

    private YntrustRepaymentPayService yntrustRepaymentPayService;

    private YntrustRepaymentPlanService yntrustRepaymentPlanService;

    private YntrustRequestFlowService yntrustRequestFlowService;

    private YntrustImageService yntrustImageService;

    private YntrustLoanService yntrustLoanService;

    private UserDto userDto;

    private static Object object = new Object();

    private static Abutment abutment;

    final Lock lock = new ReentrantLock();

    public static Abutment getInstance(){
        synchronized (object){
            if(null==abutment){
                abutment = new Abutment();
                abutment.init();
            }
        }
        return abutment;
    }

    /**
     * 信托专户查询 查询余额
     * @param result 返回信息
     * @param orderNo 订单号
     */
    public void queryTrustAccount(RespDataObject<Map<String,Object>> result, String orderNo){
        Map<String,Object> param = new HashMap<String,Object>();
        String  requestId = UuidUtil.getUUID();
        log.info("=============信托专户查询 查询余额requestId:"+requestId+"==============");
        param.put("requestId",requestId);

        if(StringUtil.isBlank(YNTRUST_ACCOUNT_NO)||StringUtil.isBlank(YNTRUST_PRODUCT_CODE)) {
            param.put("productCode", "G31700");
            param.put("accountNo", "600000000001");//信托专户账号
        } else {
            param.put("productCode",YNTRUST_PRODUCT_CODE);
            param.put("accountNo",YNTRUST_ACCOUNT_NO);//信托专户账号
        }

        Map<String,Object> responseMsg = postForObject(param, Map.class, YNTRUST_URL+QUERY_TRUST_ACCOUNT,"信托专户查询 查询余额",orderNo,result);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        Map<String,Object> dataMaptmp = null;
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            dataMap.put("Balance","查询异常");
            dataMaptmp = new HashMap<String,Object>();
            dataMaptmp.put("Balance","查询异常");
            dataMaptmp.put("ynProductName","一期");
        } else {
            dataMaptmp = MapUtils.getMap(responseMsg,"Data");
            if(MapUtils.isNotEmpty(dataMaptmp)) {
                Double balance = MapUtils.getDouble(dataMaptmp, "Balance");
                BigDecimal bigDecimal = new BigDecimal(Double.toString(null==balance?0d:balance));
                BigDecimal divide = bigDecimal.divide(new BigDecimal("10000"));
                dataMap.put("Balance",divide);
                dataMaptmp.put("Balance",divide);
                dataMaptmp.put("ynProductName","一期");
            }
        }
        dataList.add(dataMaptmp);
        requestId = UuidUtil.getUUID();
        log.info("=============信托专户查询 查询余额requestId:"+requestId+"==============");
        param.put("requestId",requestId);
        if(StringUtil.isBlank(YNTRUST_ACCOUNT_NO)||StringUtil.isBlank(YNTRUST_PRODUCT_CODE)) {
            param.put("productCode", "G31700");
            param.put("accountNo", "600000000001");//信托专户账号
        } else {
            param.put("productCode","I22500");
            param.put("accountNo","0120030000000039");//信托专户账号
        }

        responseMsg = postForObject(param, Map.class, YNTRUST_URL+QUERY_TRUST_ACCOUNT,"信托专户查询 查询余额",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            dataMap.put("secondBalance","查询异常");
            dataMaptmp = new HashMap<String,Object>();
            dataMaptmp.put("Balance","查询异常");
            dataMaptmp.put("ynProductName","二期");
        } else {
            dataMaptmp = MapUtils.getMap(responseMsg,"Data");
            if(MapUtils.isNotEmpty(dataMaptmp)) {
                Double balance = MapUtils.getDouble(dataMaptmp, "Balance");
                BigDecimal bigDecimal = new BigDecimal(Double.toString(null==balance?0d:balance));
                BigDecimal divide = bigDecimal.divide(new BigDecimal("10000"));
                dataMap.put("secondBalance",divide);
                dataMaptmp.put("Balance",divide);
                dataMaptmp.put("ynProductName","二期");
            }
        }
        dataList.add(dataMaptmp);
        dataMap.put("balanceList",dataList);
        RespHelper.setSuccessDataObject(result,dataMap);
    }

    /**
     * 打印云南信托返回的状态信息
     * @param requestId
     * @param responseStatusMsg
     */
    public void print(String requestId,String responseStatusMsg){
        log.info("===================requestId:"+requestId+",请求云南信托返回:"+responseStatusMsg+"========================");
    }
    /**
     * 推送借款信息与合同信息
     * @param result
     * @param map
     */
    public void plusYnTrustInfo(RespStatus result,Map<String,Object> map){
        Map<String,Object> param = new HashMap<String,Object>();
        String orderNo = MapUtils.getString(map,"orderNo");
        String ynProductCode = MapUtils.getString(map,"ynProductCode");
        String ynProductName = MapUtils.getString(map,"ynProductName");

        List<Map<String,Object>> img = checkImg(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;

        YntrustMappingDto mapping = new YntrustMappingDto();
        mapping.setOrderNo(orderNo);
        mapping.setStatus(1);
        mapping = yntrustMappingService.select(mapping);
        if(null==mapping) {
            packagBorrowAndContract(param, orderNo, result,ynProductCode);
            if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
                return;
            }
            String uniqueId = MapUtils.getString(param, "uniqueId");
            Map<String, Object> responseMsg = postForObject(param, Map.class, YNTRUST_URL + COACTION, "创建借款与合同信息", orderNo, result);
            if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
                YntrustBorrowDto borrow = new YntrustBorrowDto();
                borrow.setOrderNo(orderNo);
                borrow.setPushStatus(2);
                borrow.setUpdateUid(userDto.getUid());
                yntrustBorrowService.update(borrow);
                YntrustContractDto contract = new YntrustContractDto();
                contract.setOrderNo(orderNo);
                contract.setPushStatus(2);
                contract.setUpdateUid(userDto.getUid());
                yntrustContractService.update(contract);
                return;
            }
            insertMapping(orderNo, uniqueId, 1, "正常",ynProductCode,ynProductName);
            YntrustBorrowDto borrow = new YntrustBorrowDto();
            borrow.setOrderNo(orderNo);
            borrow.setPushStatus(1);
            borrow.setUpdateUid(userDto.getUid());
            yntrustBorrowService.update(borrow);
            YntrustContractDto contract = new YntrustContractDto();
            contract.setOrderNo(orderNo);
            contract.setPushStatus(1);
            contract.setUpdateUid(userDto.getUid());
            yntrustContractService.update(contract);
        }
        pushImg(result,orderNo,img);
    }

    /**
     * 上传影像资料
     * 有多个文件时，请一起上传；若需我方签章的情况下，无法满足一起上传，则最后一批上传的文件内必须包含『G:贷款合同』文件；
     * 单张图片请在上传前务必压缩至300K以内
     * @param result
     * @param orderNo
     */
    public void pushImg(RespStatus result,String orderNo,List<Map<String,Object>> tmp){
        String requestId = UuidUtil.getUUID();
        YntrustMappingDto obj = getYntrustMapping(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            return;
        }
        YntrustBorrowDto borrow = new YntrustBorrowDto();
        borrow.setOrderNo(orderNo);
        borrow = yntrustBorrowService.select(borrow);
        if(null==borrow){
            RespHelper.setFailRespStatus(result,"没有找到该订单与云南信托关联借款人信息");
            return;
        }
        if(null==tmp||tmp.size()<=0){
            tmp = checkImg(result,orderNo);
            if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
                return;
            }
        }
        Map<String,Object> imgparam = new HashMap<String,Object>();
        imgparam.put("imageContent",tmp);
        imgparam.put("requestId",requestId);
        imgparam.put("uniqueId",obj.getUniqueId());
        imgparam.put("iDCardNo",borrow.getiDCardNo());
        String url = "";
        if("http://test-api.yntrust.com".equals(YNTRUST_URL)){
            url = YNTRUST_URL+FUACTION;
        } else {
            url = YNTRUST_FILE_URL+FUACTION;
        }
        Map<String,Object> responseMsg = postForObjectHeadSignedMsgNull(imgparam, Map.class,url,"上传影像资料",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        RespHelper.setSuccessRespStatus(result);
    }

    /**
     * 校验影像资料成功则返回影像资料的BASE64数据
     * @param result
     * @param orderNo
     * @return
     */
    public List<Map<String,Object>> checkImg(RespStatus result,String orderNo){
        Map<String,Object> imgMap = new HashMap<String,Object>();
        imgMap.put("orderNo",orderNo);
        List<Map<String,Object>> imgList = yntrustImageService.list(imgMap);
        if(null==imgList||imgList.size()<=0){
            RespHelper.setFailRespStatus(result,"没有找到该推送数据的影像资料");
            return null;
        }
        /**
         * 上传影像资料
         */
        List<Map<String,Object>> tmp =  analyzeImg(imgList,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            log.info("============获取影像资料BASE64数据失败,具体原因请查看FS文件系统日志============");
            return null;
        }
        return tmp;
    }
    /**
     * 获取影像资料的BASE64数据
     * @param list
     * @return
     */
    public List<Map<String,Object>> analyzeImg(List<Map<String,Object>> list,RespStatus result){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<List> requestEntity = new HttpEntity<List>(list, headers);
        String FS_URL = ConfigUtil.getStringValue(Constants.LINK_FS_URL,ConfigUtil.CONFIG_LINK);
        //http://182.254.149.92:9206/
        RespDataObject<List<Map<String,Object>>> tmp = restTemplate.postForObject(FS_URL+"/fs/yntrust/getImgBaseCode", requestEntity, RespDataObject.class);
        //RespDataObject<List<Map<String,Object>>> tmp = httpUtil.getRespDataObject(Constants.LINK_FS_URL,"/fs/yntrust/getImgBaseCode",list,List.class);
        result.setMsg(tmp.getMsg());
        result.setCode(tmp.getCode());
        return tmp.getData();
    }

    public boolean analyzeResponseMsg(Map<String,Object> responseMsg,RespStatus result,Map<String,Object> tmp){
        Map<String,Object> statusMap = MapUtils.getMap(responseMsg,"Status");
        String responseStatusMsg = MapUtils.getString(statusMap,"ResponseMessage");
        String ResponseCode = MapUtils.getString(statusMap,"ResponseCode");
        tmp.put("responseStatusMsg",responseStatusMsg);
        tmp.put("responseStatusCode",ResponseCode);
        if(null==statusMap||statusMap.size()<=0){
            result.setCode(RespStatusEnum.FAIL.getCode());
            result.setMsg(RespStatusEnum.FAIL.getMsg());
            return false;
        }
        if(StringUtil.isBlank(ResponseCode)
                ||!ResponseCode.equals("0000")){
            result.setCode(RespStatusEnum.FAIL.getCode());
            if("0040".equals(ResponseCode)){
                result.setMsg("第三方返回:"+responseStatusMsg);
            } else {
                result.setMsg(responseStatusMsg);
            }
            return false;
        }
        return true;
    }

    /**
     * 组装借款人与合同信息
     * @param param
     */
    public void packagBorrowAndContract(Map<String,Object> param,String orderNo,RespStatus result,String ynProductCode){

        YntrustContractDto contract = new YntrustContractDto();
        contract.setOrderNo(orderNo);
        YntrustBorrowDto borrow = new YntrustBorrowDto();
        borrow.setOrderNo(orderNo);
        borrow = yntrustBorrowService.select(borrow);
        contract = yntrustContractService.select(contract);
        if(null==borrow||null==contract){
            RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
            return;
        }
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> map = new HashMap<String,Object>();
        /**
         * 对手简称
         */
        map.put("shortName",borrow.getShortName());
        /**
         * 银行开户预留手机号 默认手机号
         */
        map.put("bankReservedPhoneNo",borrow.getTelephoneNo());
        /**
         * 身份证号码
         */
        map.put("iDCardNo",borrow.getiDCardNo());
        /**
         * 婚姻状况
         * 10未婚, 20已婚, 21初婚, 22再婚, 23复婚, 30丧偶, 40离婚, 90 未说明的婚姻状况
         */
        map.put("maritalStatus",borrow.getMaritalStatus());
        /**
         * 手机号码
         */
        map.put("telephoneNo",borrow.getTelephoneNo());

        if(StringUtil.isNotBlank(borrow.getCityName())){
            String city = "";
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("name",borrow.getCityName());
            RespDataObject<Map<String,Object>> r = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/common/base/v/selectAdministrationDivideByName", m,Map.class);
            if(RespStatusEnum.SUCCESS.getCode().equals(r.getCode())){
                city = MapUtils.getString(r.getData(),"code");
            } else {
                city = borrow.getCity();
                log.info("没有匹配到云南信托城市编码,城市:"+borrow.getCityName());
            }

            /**
             * 居住城市
             */
            map.put("city",city);
        } else {
            /**
             * 居住城市
             */
            map.put("city",borrow.getCity());
        }

        /**
         * 居住地址 取合同归属地
         */
        map.put("address",StringUtil.isBlank(contract.getAddress())?"999999":contract.getAddress());
        map.put("zipCode","999999");//邮编
        /**
         * 职业分类
         * 1=政府部门，2=教科文，3=金融，4=商贸，5=房地产，6=制造业，7=自由职业，
         8=其他
         */
        map.put("jobType",borrow.getJobType());
        /**
         * 角色分类
         * 贷款借款人=1；其他投融资交易对手=2；抵质押人担保人贷款类=3；抵质押人担保人非贷款类=4；委托方=5；其他对手方等付费对象=6
         */
        map.put("roleType",borrow.getRoleType());
        /**
         * 还款银行卡账号
         */
        map.put("accountNo",contract.getAccountno());
        /**
         * 借款人开户银行名称
         */
        map.put("bankCode",contract.getBankcode());
        /**
         * 借款人开户支行名称
         */
        map.put("branchBankName",contract.getBranchbankname());
        /**
         * 银行卡归属地:回款卡
         */
        String bankCardAttribution = StringUtil.isBlank(contract.getPaymentsBankAttributionCountyCode())?contract.getPaymentsBankAttributionCityCode():contract.getPaymentsBankAttributionCountyCode();
        map.put("bankCardAttribution",bankCardAttribution);
        /**
         * 产品代码
         */
        map.put("productCode",StringUtil.isBlank(ynProductCode)?YNTRUST_PRODUCT_CODE:ynProductCode);
        /**
         * 逾期费率(合同签署地已经放在居住地址,该字段现在第三方映射为逾期费率)
         */
        map.put("communicationAddress",contract.getOverdueRate());
        /**
         * 请求唯一标示
         */
        param.put("requestId",UuidUtil.getUUID());
        /**
         * 快鸽与云南信托全局唯一标示
         */
        param.put("uniqueId",UuidUtil.getUUID());

        param.put("borrower",map);

        map = new HashMap<String,Object>();
        //合同信息
        /**
         * 合同金额 非授信模式该字段值与放款金额一致
         */
        map.put("contractAmount",new BigDecimal(new BigDecimal(Double.toString(contract.getAmount())).toPlainString()));
        /**
         * 签约日期
         */
        map.put("signDate",format.format(contract.getSignDate()));
        /**
         * 开始日期
         */
        map.put("beginDate",format.format(contract.getBeginDate()));
        /**
         * 签约利率
         */
        map.put("signRate",new BigDecimal(new BigDecimal(Double.toString(contract.getSignRate())).toPlainString()));
        /**
         * 付息周期 默认0
         * 0=到期结算
             1=按月结算
             10=按十个月结算
             11=按十一个月结算
             12=按年结算
             2=按二个月结算
             24=每两年结算
             3=按季结算
             36=每三年结算
             4=按四个月结算
             48=每四年结算
             5=按五个月结算
             6=每半年结算
             60=每五年结算
             7=按七个月结算
             8=按八个月结算
             9=按九个月结算
         */
        map.put("repaymentCycle",contract.getRepaymentCycle());
        /**
         * 还款方式 默认3
         *  等额本息=1,
            等额本金=2,
            利随本清=3,
            其他方式=9
         */
        map.put("repaymentMode",contract.getRepaymentMode());
        /**
         * 还款期数 默认1期
         */
        map.put("repaymentPeriod",contract.getRepaymentPeriod());
        /**
         * 合同结束时间为当前时间加上借款期限
         */
        /*
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH,contract.getBorrowingDays());
        map.put("endDate",format.format(instance.getTime()));
        */
        /**
         * 借款期限
         */
        map.put("PartnerProductName",""+contract.getBorrowingDays());
        Map<String,Object> tmp = new HashMap<String,Object>();
        /**
         * 放款银行账号
         */
        /*
        boolean isSuccess = verification(contract.getAccountno());
        if(!isSuccess){
            RespHelper.setFailRespStatus(result,"请填写正确的放款银行账号");
            return;
        }*/
        tmp.put("accountno",contract.getAccountno());
        /**
         * 银行编码
         */
        tmp.put("bankcode",contract.getBankcode());
        /**
         * 支行名称
         */
        tmp.put("branchbankname",contract.getBranchbankname());
        /**
         * 银行卡归属地
         */
        String bankcardattribution = StringUtil.isBlank(contract.getBankcardAttributionCountyCode())?contract.getBankcardAttributionCityCode():contract.getBankcardAttributionCountyCode();
        tmp.put("bankcardattribution",bankcardattribution);
        /**
         * 账户开户名
         */
        tmp.put("accountname",contract.getAccountname());
        /**
         * 放款金额 非授信模式该字段值与合同金额一致
         */
        tmp.put("amount",new BigDecimal(new BigDecimal(Double.toString(contract.getAmount())).toPlainString()));
        /**
         * 放款对象类型: 默认0
         * 0：借款人
             1：受托支付-个人
             2：受托支付-金融机构
             3：受托支付-非金融机构
         */
        tmp.put("type",contract.getType());
        /**
         * 放款信息集合
         */
        map.put("paymentBankCards",tmp);
        param.put("loan",map);
    }
    /**
     * 保存请求流水
     * @param orderNo 订单编号
     * @param requestId 本次请求的唯一标示
     * @param responseMsg 返回信息
     * @param requestInterface 请求接口
     * @param requestInterfaceName 请求接口名称
     * @param responseStatusMsg 返回的状态名称
     */
    public void insertFlow(String orderNo,
                           String requestId,
                           String responseMsg,
                           String requestInterface,
                           String requestInterfaceName,
                           String responseStatusMsg,
                           String requestMsg){
        YntrustRequestFlowDto flow = new YntrustRequestFlowDto();
        flow.setCreateUid(userDto.getUid());
        flow.setOrderNo(orderNo);
        flow.setRequestId(requestId);
        flow.setResponseMsg(responseMsg);
        flow.setRequestInterface(requestInterface);
        flow.setRequestInterfaceName(requestInterfaceName);
        flow.setResponseStatusMsg(responseStatusMsg);
        flow.setRequestMsg(requestMsg);
        yntrustRequestFlowService.insert(flow);
    }

    /**
     * 放款指令
     * @param result
     * @param orderNo
     */
    public void confirmPayment(RespStatus result,String orderNo){
        Map<String,Object> param = new HashMap<String,Object>();
        YntrustMappingDto obj = getYntrustMapping(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            return;
        }
        YntrustLoanDto loan = new YntrustLoanDto();
        loan.setOrderNo(orderNo);
        loan.setCancelStatus(1);
        List<YntrustLoanDto> loanList = yntrustLoanService.list(loan);
        if(null!=loanList&&loanList.size()>0){
            YntrustLoanDto yntrustLoanDto = loanList.get(0);
            if(1==yntrustLoanDto.getProcessStatus()||0==yntrustLoanDto.getProcessStatus()) {
                RespHelper.setFailRespStatus(result, "该订单已经发送过放款指令不能重复发送!");
                return;
            }
        }
        String requestId = UuidUtil.getUUID();
        List<String> list = new ArrayList<String>();
        list.add(obj.getUniqueId());
        param.put("uniqueIdList",list);
        param.put("requestId",requestId);
        Map<String,Object> responseMap = postForObject(param,Map.class,YNTRUST_URL+CPACTION,"发送放款指令",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        RespHelper.setSuccessRespStatus(result);
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        try {
            final String tmpOrderNo = orderNo;
            scheduledThreadPool.schedule(new Runnable() {
                public void run() {
                    try {
                        queryTradingStatus(new RespDataObject<List<YntrustLoanDto>>(),tmpOrderNo);
                    } catch (Exception e){
                        log.error("放款查询异常:",e);
                    }
                }
            }, 0, TimeUnit.SECONDS);
        } finally {
            if(null!=scheduledThreadPool){
                scheduledThreadPool.shutdown();
            }
        }
    }

    /**
     * 补单放款指令
     * 只有在查获“放款指令发送失败”状态
     */
    public void remedyPayment(RespStatus result,String orderNo){
        Map<String,Object> param = new HashMap<String,Object>();
        YntrustMappingDto obj = getYntrustMapping(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            return;
        }
        String requestId = UuidUtil.getUUID();
        param.put("requestId",requestId);
        param.put("uniqueId",obj.getUniqueId());
        Map<String,Object> responseMsg = postForObject(param,Map.class,YNTRUST_URL+RPACTION,"补单发送放款指令",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        RespHelper.setSuccessRespStatus(result);
    }
    /**
     * 放款状态查询
     * @param result
     * @param orderNo
     */
    public void queryTradingStatus(RespDataObject<List<YntrustLoanDto>> result,String orderNo)throws Exception{
        Map<String,Object> param = new HashMap<String,Object>();
        YntrustMappingDto obj = getYntrustMapping(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            return;
        }
        String requestId = UuidUtil.getUUID();
        param.put("uniqueId",obj.getUniqueId());
        param.put("requestId",requestId);
        Map<String,Object> responseMap = postForObject(param,Map.class,YNTRUST_URL+QTSACTION,"放款查询",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        //云南信托返回的合同号
        String ynxtLoanContactNumber = MapUtils.getString(responseMap,"YnxtLoanContactNumber");
        YntrustContractDto contract = new YntrustContractDto();
        contract.setOrderNo(orderNo);
        contract.setLoanContractNumber(ynxtLoanContactNumber);
        contract.setUpdateUid(userDto.getUid());
        yntrustContractService.update(contract);

        String auditMessage = MapUtils.getString(responseMap,"AuditMessage");
        String auditStatus = MapUtils.getString(responseMap,"AuditStatus");
        String actExcutedTime;
        Object tmp = MapUtils.getObject(responseMap,"PaymentDetails");
        YntrustLoanDto loan = null;
        List<YntrustLoanDto> listLoan = new ArrayList<YntrustLoanDto>();
        if(null!=tmp){
            String json = gson.toJson(tmp);
            List<Map<String,Object>> tmpList = gson.fromJson(json,List.class);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Map<String,Object> m:tmpList){
                loan = new YntrustLoanDto();
                loan.setYnxtLoanContractNumber(ynxtLoanContactNumber);
                loan.setOrderNo(orderNo);
                loan.setCreateUid(userDto.getUid());
                loan.setAccountNo(MapUtils.getString(m,"AccountNo"));
                loan.setAmount(MapUtils.getDouble(m,"Amount"));
                loan.setBankSerialNo(MapUtils.getString(m,"BankSerialNo"));
                actExcutedTime = MapUtils.getString(m,"ActExcutedTime");
                if(StringUtils.isNotBlank(actExcutedTime)){
                    loan.setActExcutedTime(format.parse(actExcutedTime));
                }
                loan.setAuditStatus(auditStatus);
                if(StringUtil.isNotBlank(auditStatus)){
                    String auditStatusName = "2".equals(auditStatus)?"审核成功":"3".equals(auditStatus)?"作废":"待审核";
                    loan.setAuditStatusName(auditStatusName);
                }
                loan.setAuditMessage(auditMessage);
                loan.setName(MapUtils.getString(m,"Name"));
                loan.setProcessStatus(MapUtils.getInteger(m,"ProcessStatus"));
                String processStatusName = getProcessStatusName(loan.getProcessStatus());
                loan.setProcessStatusName(processStatusName);
                loan.setResult(MapUtils.getString(m,"Result"));
                listLoan.add(loan);
            }
            if(listLoan.size()>0){
                yntrustLoanService.batchInsert(listLoan);
            }
        }
        RespHelper.setSuccessDataObject(result,listLoan);
    }

    /**
     * 映射放款状态
     * @param processStatus
     * @return
     */
    public String getProcessStatusName(Integer processStatus){
        String processStatusName = "";
        if(null==processStatus)return processStatusName;

        switch (processStatus){
            case 0:processStatusName = "放款中";
            break;
            case 1:processStatusName = "成功";
                break;
            case 2:processStatusName = "失败";
                break;
            case 3:processStatusName = "业务不执行";
                break;
            case 4:processStatusName = "异常";
                break;
            case 9:processStatusName = "放款指令发送失败";
                break;
        }
        return processStatusName;
    }

    /**
     * 获取合同信息电子签证的链接与二维码图片
     * @param result
     * @param orderNo
     */
    public void getQRCode(RespDataObject<Map<String,Object>> result,String orderNo){
        Map<String,Object> param = new HashMap<String,Object>();
        YntrustMappingDto obj = getYntrustMapping(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            return;
        }
        String requestId = UuidUtil.getUUID();
        param.put("uniqueId",obj.getUniqueId());
        param.put("requestId",requestId);
        Map<String,Object> responseMap = postForObject(param,Map.class,YNTRUST_URL+GET_QRCODE,"获取二维码",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        Map<String,Object> tmp = new HashMap<String,Object>();
        tmp.put("url",MapUtils.getString(responseMap,"QRUrl"));
        result.setData(tmp);
        RespHelper.setSuccessDataObject(result,tmp);
    }

    /**
     * 获取云信签章合同文件
     * @param result
     * @param orderNo
     */
    public void getContractFile(RespDataObject<Map<String,Object>> result,String orderNo,Map<String,Object> map){
        Map<String,Object> param = new HashMap<String,Object>();
        YntrustMappingDto obj = getYntrustMapping(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            return;
        }
        String requestId = UuidUtil.getUUID();
        param.put("uniqueId",obj.getUniqueId());
        param.put("requestId",requestId);
        /**
         * 填写数字0代表不获取合同内容只是查看文件签章状态
         */
        param.put("isGetFileContent",MapUtils.getString(map,"isGetFileContent","0"));
        Map<String,Object> responseMap = postForObject(param,Map.class,YNTRUST_URL+GET_CONTRACT_FILE,"获取云信签章合同文件",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        String signStatus = MapUtils.getString(responseMap,"SignStatus");
        String signStatusName = mappingSignStatus(signStatus);
        responseMap.put("signStatusName",signStatusName);
        RespHelper.setSuccessDataObject(result,responseMap);
    }

    /**
     * 映射合同状态
     * @param signStatus
     * @return
     */
    public String mappingSignStatus(String signStatus){
        String signStatusName = "";
        if("1".equals(signStatus)){
            signStatusName = "未生成合同";
        } else if("2".equals(signStatus)){
            signStatusName = "生成合同成功";
        } else if("3".equals(signStatus)){
            signStatusName = "生成合同失败";
        } else if("4".equals(signStatus)){
            signStatusName = "合同签章成功";
        } else if("5".equals(signStatus)){
            signStatusName = "合同签章失败";
        } else if("6".equals(signStatus)){
            signStatusName = "发送放款指令成功";
        } else if("7".equals(signStatus)){
            signStatusName = "发送放款指令失败";
        } else if("8".equals(signStatus)){
            signStatusName = "签章处理中";
        } else if("9".equals(signStatus)){
            signStatusName = "签章人工处理";
        }
        return signStatusName;
    }
    /**
     * 创建应还款计划
     * @param result
     * @param orderNo
     */
    public void createRepaySchedule(RespStatus result,String orderNo){
        YntrustMappingDto obj = getYntrustMapping(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            return;
        }
        Map<String,Object> param = new HashMap<String,Object>();
        YntrustRepaymentPlanDto plan = new YntrustRepaymentPlanDto();
        plan.setOrderNo(orderNo);
        plan = yntrustRepaymentPlanService.select(plan);
        if(null==plan){
            RespHelper.setFailRespStatus(result,"没有获取到与该订单号应还款计划信息");
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String requestId = UuidUtil.getUUID();
        param.put("requestId", requestId);
        Map<String,Object> t = new HashMap<String,Object>();
        List<Map<String,Object>> repaySchedules = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> loanRepaySchedules = new ArrayList<Map<String,Object>>();
        t.put("uniqueId",obj.getUniqueId());
        Map<String,Object> t1 = new HashMap<String,Object>();
        t1.put("repayDate",format.format(plan.getRepayDate()));//还款时间
        t1.put("repayPrincipal",new BigDecimal(new BigDecimal(Double.toString(plan.getRepayPrincipal())).toPlainString()));//还款本金
        t1.put("repayProfit",new BigDecimal(new BigDecimal(Double.toString(plan.getRepayProfit())).toPlainString()));//还款利息
        t1.put("rechMaintenanceFee",0d);//技术服务费 非必传
        t1.put("otherFee",0d);//其他费用 非必传
        t1.put("loanServiceFee",0d);//贷款服务费 非必传

        String partnerScheduleNo = "";
        if(StringUtils.isNotBlank(plan.getPartnerScheduleNo())){
            t1.put("partnerScheduleNo",plan.getPartnerScheduleNo());//还款计划编号 每期还款计划的唯一标识
        } else {
            partnerScheduleNo = UidUtil.generateOrderId();
            t1.put("partnerScheduleNo",partnerScheduleNo);//还款计划编号 每期还款计划的唯一标识
        }

        repaySchedules.add(t1);
        t.put("repaySchedules",repaySchedules);//还款计划集合
        loanRepaySchedules.add(t);
        param.put("loanRepaySchedules",loanRepaySchedules);//还款计划所属合同集
        Map<String,Object> responseMap = postForObject(param, Map.class, YNTRUST_URL + CRSACTION,"创建还款计划",orderNo,result);
        if(StringUtils.isNotBlank(partnerScheduleNo)) {
            plan.setPartnerScheduleNo(partnerScheduleNo);
        }
        plan.setUpdateUid(userDto.getUid());
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            plan.setPushStatus(2);
            yntrustRepaymentPlanService.update(plan);
            return;
        }
        plan.setPushStatus(1);
        yntrustRepaymentPlanService.update(plan);
        RespHelper.setSuccessRespStatus(result);
    }

    /**
     * 查询最新的还款计划
     * @param result
     * @param orderNo
     */
    public void queryRepaySchedule(RespDataObject<Map<String,Object>> result,String orderNo){
        YntrustMappingDto obj = getYntrustMapping(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            return;
        }
        Map<String,Object> param = new HashMap<String,Object>();
        String requestId = UuidUtil.getUUID();
        param.put("requestId", requestId);
        param.put("uniqueId",obj.getUniqueId());
        Map<String,Object> responseMap = postForObject(param, Map.class, YNTRUST_URL + CRSACTION,"查询最新的还款计划",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        RespHelper.setSuccessDataObject(result,responseMap);
    }
    /**
     * 提前或者逾期才调用
     * 更新还款计划
     *   需要注意还款计划集合需要按照执行时间顺序传递；覆盖旧的未执行的还款计划；
         每期还款计划本金总和应等于贷款总金额；
         每期还款日应早于等于合同到期日；
         还款计划状态为『成功』、『扣款中』、『处理异常』或该条还款计划已发生『逾期』的情况下，无法进行变更；
         调用本接口时，更新的还款计划相较原还款计划未有任何变动（还款日期、还款本金、利息及其他费用金额），则将被拒绝；
         若有单条或多条还款计划需变更，则必须将所有未执行的还款计划都通过本接口传递至云信。
     */
    public void updateRepaySchedule(RespStatus result,String orderNo,YntrustRepaymentPlanDto plan){
        YntrustMappingDto obj = getYntrustMapping(result,orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
            return;
        }
        YntrustRepaymentPlanDto tmp = new YntrustRepaymentPlanDto();
        tmp.setOrderNo(orderNo);
        tmp = yntrustRepaymentPlanService.select(tmp);

        if(null==tmp){
            RespHelper.setFailRespStatus(result,"没有获取到与该订单号应还款计划信息");
            return;
        } else {
            plan.setPartnerScheduleNo(tmp.getPartnerScheduleNo());
        }
        if(null==plan.getRepayDate()){
            RespHelper.setFailRespStatus(result,"还款计划时间不能为空");
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<String,Object> param  = new HashMap<String,Object>();
        String requestId = UuidUtil.getUUID();
        param.put("requestId", requestId);
        param.put("uniqueId", obj.getUniqueId());
        /**
         * 还款计划变更原因
         * 目前固定为3种变更原因：
             0=项目结清
             1=提前部分还款
             2=错误更正
         */
        param.put("changeReason",plan.getChangeReason());
        List<Map<String,Object>> repaySchedules = new ArrayList<Map<String,Object>>();

        Map<String,Object> t1 = new HashMap<String,Object>();
        t1.put("repayDate",format.format(plan.getRepayDate()));//还款时间
        String partnerScheduleNo = "";
        if(StringUtils.isBlank(plan.getPartnerScheduleNo())){
            partnerScheduleNo = UidUtil.generateOrderId();
            t1.put("partnerScheduleNo",partnerScheduleNo);//还款计划编号 每期还款计划的唯一标识
        } else {
            t1.put("partnerScheduleNo",plan.getPartnerScheduleNo());//还款计划编号 每期还款计划的唯一标识
        }
        t1.put("repayPrincipal",new BigDecimal(new BigDecimal(Double.toString(plan.getRepayPrincipal())).toPlainString()));//还款本金
        t1.put("repayProfit",new BigDecimal(new BigDecimal(Double.toString(plan.getRepayProfit())).toPlainString()));//还款利息
        /**
         * 还款计划类型
         * 如果为空则为正常未改变，为0代表提前还款（提前部分或全额还款）类型
         */
        t1.put("scheduleType",plan.getScheduleType());
        repaySchedules.add(t1);
        param.put("repaySchedules",repaySchedules);

        Map<String,Object> responseMap = postForObject(param, Map.class, YNTRUST_URL + UPDATE_REPAY_SCHEDULE,"更新还款计划还款计划",orderNo,result);
        if(StringUtils.isNotBlank(partnerScheduleNo)) {
            plan.setPartnerScheduleNo(partnerScheduleNo);
        }
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        plan.setUpdateUid(userDto.getUid());
        yntrustRepaymentPlanService.update(plan);
        RespHelper.setSuccessRespStatus(result);

    }

    /**
     * 还款订单信息接口
     * @param result
     * @param orderNo
     */
    public void repaymentOrderInfo(RespStatus result,String orderNo,YntrustRepaymentInfoDto info)throws Exception{
        lock.lock();
        try {
            YntrustRepaymentInfoDto tmp = new YntrustRepaymentInfoDto();
            tmp.setOrderNo(info.getOrderNo());
            tmp = yntrustRepaymentInfoService.select(tmp);
            if(null!=tmp&&null!=tmp.getStatus()&&2!=tmp.getStatus()&&8!=tmp.getStatus()){
                RespHelper.setFailRespStatus(result, "回款计划还款信息已经推送成功不能重复推送");
                return;
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            if (null == info.getRepayDate() || null == info.getRealityPayDate()) {
                RespHelper.setFailRespStatus(result, "预计回款时间与实际回款时间不能为空");
                return;
            }
            RespDataObject<YntrustRepaymentInfoDto> tmpResult = new RespDataObject<YntrustRepaymentInfoDto>();
            queryRepayOrder(tmpResult,null,info.getOrderNo(),false);
            if (RespStatusEnum.SUCCESS.getCode().equals(tmpResult.getCode())
                    && tmpResult.getData().getStatus() > 0 && 2 != tmpResult.getData().getStatus()&&8!=tmpResult.getData().getStatus()) {
                RespHelper.setFailRespStatus(result, "回款计划还款信息已经校验成功不能重复推送");
                return;
            }
            if (RespStatusEnum.SUCCESS.getCode().equals(tmpResult.getCode())
                    && tmpResult.getData().getStatus()==0) {
                RespHelper.setFailRespStatus(result, "回款计划还款信息已经推送成功不能重复推送");
                return;
            }
            YntrustRepaymentPlanDto plantmp = new YntrustRepaymentPlanDto();
            plantmp.setOrderNo(info.getOrderNo());
            plantmp = yntrustRepaymentPlanService.select(plantmp);

            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            String repayDate = fm.format(info.getRepayDate());
            String nowtimestr = fm.format(new Date());
            String oldRepayDate = fm.format(plantmp.getRepayDate());
            String realityPayDate = fm.format(info.getRealityPayDate());
            /**
             * 修改了计划还款时间
             * 或者提前还款(用实际还款时间与预计还款时间比较是否提前还款)
             * 则更新还款计划
             */
            if (!oldRepayDate.equals(repayDate)
                    ||fm.parse(repayDate).getTime()>fm.parse(realityPayDate).getTime()) {
                YntrustRepaymentPlanDto plan = new YntrustRepaymentPlanDto();
                plan.setOrderNo(info.getOrderNo());
                plan.setRepayPrincipal(info.getRepayPrincipal());
                plan.setRepayProfit(info.getGivePayProfit());

                if(oldRepayDate.equals(repayDate)
                        &&fm.parse(repayDate).getTime()>fm.parse(realityPayDate).getTime()){
                    plan.setRepayDate(info.getRealityPayDate());
                    //项目结清
                    plan.setChangeReason("0");
                } else  {
                    plan.setRepayDate(info.getRepayDate());
                    //错误更正
                    plan.setChangeReason("2");
                }
                updateRepaySchedule(result, info.getOrderNo(), plan);
                if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) return;
            }

            /**
             * 判断是否是逾期
             */
            /*old
            if(fm.parse(nowtimestr).getTime()>fm.parse(repayDate).getTime()
                    &&(info.getPenaltyFee() > 0||info.getOverDueFee()>0)){
                updateOverDueFee(result, info.getOrderNo(), info);
                if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
                    return;
                }
            } else if(fm.parse(nowtimestr).getTime()>fm.parse(repayDate).getTime()
                    &&info.getPenaltyFee() <=0&&info.getOverDueFee()<=0){
                RespHelper.setFailRespStatus(result,"请填写逾期罚息");
                return;
            }*/
            /**
             * <1>先用预计还款时间对比当前时间,如果有逾期则用第二步判断
             * <2>用实际还款时间对比预计还款时间
             */
            if(fm.parse(nowtimestr).getTime()>fm.parse(repayDate).getTime()
                    &&fm.parse(realityPayDate).getTime()>fm.parse(repayDate).getTime()){
                updateOverDueFee(result, info.getOrderNo(), info);
                if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
                    return;
                }
            }
            /*
            else if(fm.parse(nowtimestr).getTime()>fm.parse(repayDate).getTime()
                    &&fm.parse(realityPayDate).getTime()>fm.parse(repayDate).getTime()
                    &&info.getPenaltyFee() <=0&&info.getOverDueFee()<=0){
                RespHelper.setFailRespStatus(result,"请填写逾期罚息");
                return;
            }*/

            YntrustMappingDto obj = getYntrustMapping(result, orderNo);
            if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) return;

            YntrustRepaymentPlanDto plan = new YntrustRepaymentPlanDto();
            plan.setOrderNo(orderNo);
            plan = yntrustRepaymentPlanService.select(plan);
            Map<String, Object> param = new HashMap<String, Object>();
            String requestId = UuidUtil.getUUID();
            param.put("requestId", requestId);
            param.put("productCode", (null==obj||StringUtil.isBlank(obj.getYnProductCode()))?YNTRUST_PRODUCT_CODE:obj.getYnProductCode());
            /**
             * 交易请求编号
             * 交易类型为“提交”时，唯一性校验；
             * 交易类型为“撤销”时，存在性校验
             */
            String transactionNo = UidUtil.generateOrderId();
            param.put("transactionNo", transactionNo);
            /**
             * 交易类型
             * 1 提交
             * 2 撤销
             */
            param.put("transactionType", 1);

            /**
             * 还款业务明细
             */
            List<Map<String, Object>> orderdetails = new ArrayList<Map<String, Object>>();
            Map<String, Object> orderMap = new HashMap<String, Object>();
            /**
             * 还款方式 默认2001
             * 1000、线上代扣,2001、委托转付
               2002、商户担保,2003、回购
               2004、差额划拨,2005、代偿
               3001、主动付款,3002、他人代偿
               4001、商户代扣,5001、线上代扣-快捷支付
               6001 线上代扣-在线支付
             */
            orderMap.put("paymentMethod", "".equals(info.getPaymentMethod()) ? "2001" : info.getPaymentMethod());
            orderMap.put("uniqueId", obj.getUniqueId());

            List<Map<String, Object>> businessDetails = new ArrayList<Map<String, Object>>();
            BigDecimal detailAmount = mappingAmount(businessDetails, info);
            /**
             * 业务金额明细
             */
            orderMap.put("businessDetails", businessDetails);
            /**
             * 明细总金额
             * 实际还款金额
             */
            orderMap.put("detailAmount", new BigDecimal(detailAmount.toPlainString()));
            /**
             * 订单总金额
             * 订单总金额=明细金额总和
             */
            param.put("totalAmount", new BigDecimal(detailAmount.toPlainString()));
            /**
             * 还款计划编号
             */
            String partnerScheduleNo = "";
            if (null != plan && StringUtils.isNotBlank(plan.getPartnerScheduleNo())) {
                partnerScheduleNo = plan.getPartnerScheduleNo();
            } else {
                partnerScheduleNo = UidUtil.generateOrderId();
            }
            orderMap.put("partnerScheduleNo", partnerScheduleNo);
            orderMap.put("transactionTime", format.format(info.getRealityPayDate()));
            orderdetails.add(orderMap);
            param.put("orderdetails", orderdetails);
            Map<String, Object> responseMap = postForObject(param, Map.class, YNTRUST_URL + REPAYMENT_ORDER_INFO, "还款订单信息接口", orderNo, result);
            if (StringUtils.isNotBlank(transactionNo)) {
                info.setTransactionNo(transactionNo);
            }
            info.setUpdateUid(userDto.getUid());
            if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
                info.setPushStatus(2);
                yntrustRepaymentInfoService.insert(info);
                return;
            }
            info.setPushStatus(1);
            yntrustRepaymentInfoService.insert(info);
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            throw new Exception(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 计算订单总金额
     * @param businessDetails
     * @param info
     * @return
     */
    public BigDecimal mappingAmount(List<Map<String,Object>> businessDetails,YntrustRepaymentInfoDto info){
        BigDecimal totalAmount = new BigDecimal(Double.toString(info.getRepayPrincipal()));
        Map<String,Object> businessMap = new HashMap<String,Object>();
        /**
         * 费用类型 这里是多项,如果有逾期违约的
         * 1000  计划本金,  1001  计划利息
           1002  贷款服务费,1003  技术维护费
           1004  其他费用,  1005  逾期罚息
           1006  逾期违约金,1007  逾期服务费
           1008  逾期其他费用

           2000  回购本金
           2001  回购利息
           2002  回购罚息
           2003  回购其他费用
         */
        businessMap.put("feeType",1000);
        /**
         * 金额
         * 更新值必须小于等于（当前应收未收-支付中金额）
         */
        businessMap.put("amount",new BigDecimal(totalAmount.toPlainString()));
        businessDetails.add(businessMap);
        if(null!=info.getGivePayProfit()) {
            businessMap = new HashMap<String, Object>();
            businessMap.put("feeType", 1001);
            businessMap.put("amount", new BigDecimal(new BigDecimal(Double.toString(info.getGivePayProfit())).toPlainString()));
            totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getGivePayProfit())));
            businessDetails.add(businessMap);
        }
        if(null!=info.getOtherFee()) {
            businessMap = new HashMap<String, Object>();
            businessMap.put("feeType", 1004);
            businessMap.put("amount", new BigDecimal(new BigDecimal(Double.toString(info.getOtherFee())).toPlainString()));
            totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getOtherFee())));
            businessDetails.add(businessMap);
        }
        if(null!=info.getPenaltyFee()) {
            businessMap = new HashMap<String, Object>();
            businessMap.put("feeType", 1005);
            businessMap.put("amount",new BigDecimal(new BigDecimal(Double.toString(info.getPenaltyFee())).toPlainString()));
            totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getPenaltyFee())));
            businessDetails.add(businessMap);
        }
        if(null!=info.getLatePenalty()) {
            businessMap = new HashMap<String, Object>();
            businessMap.put("feeType", 1006);
            businessMap.put("amount", new BigDecimal(new BigDecimal(Double.toString(info.getLatePenalty())).toPlainString()));
            totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getLatePenalty())));
            businessDetails.add(businessMap);
        }
        if(null!=info.getLateFee()) {
            businessMap = new HashMap<String, Object>();
            businessMap.put("feeType", 1007);
            businessMap.put("amount", new BigDecimal(new BigDecimal(Double.toString(info.getLateFee())).toPlainString()));
            totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getLateFee())));
            businessDetails.add(businessMap);
        }
        if(null!=info.getLateOtherCost()) {
            businessMap = new HashMap<String, Object>();
            businessMap.put("feeType", 1008);
            businessMap.put("amount", new BigDecimal(new BigDecimal(Double.toString(info.getLateOtherCost())).toPlainString()));
            totalAmount = totalAmount.add(new BigDecimal(Double.toString(info.getLateOtherCost())));
            businessDetails.add(businessMap);
        }
        return totalAmount;
    }
    /**
     * 还款状态查询
     * @param resultInfo
     * @param orderNo
     * @param isPay 是否是支付状态查询(true) 否是还款状态调用
     */
    public void queryRepayOrder(RespDataObject<YntrustRepaymentInfoDto> resultInfo,RespDataObject<YntrustRepaymentPayDto> resultPay,String orderNo,boolean isPay){
        YntrustMappingDto obj = getYntrustMapping(isPay?resultPay:resultInfo, orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(isPay?resultPay.getCode():resultInfo.getCode()))
            return;
        YntrustRepaymentInfoDto info = new YntrustRepaymentInfoDto();
        info.setOrderNo(orderNo);
        info = yntrustRepaymentInfoService.select(info);
        if(null==info||StringUtils.isBlank(info.getTransactionNo())){
            RespHelper.setFailDataObject(isPay?resultPay:resultInfo,null,"没有获取到该订单的还款交易编号");
            return;
        }
        Map<String,Object> param = new HashMap<String,Object>();
        String requestId = UuidUtil.getUUID();
        param.put("requestId", requestId);
        param.put("uniqueId", obj.getUniqueId());
        param.put("productCode", (null==obj||StringUtil.isBlank(obj.getYnProductCode()))?YNTRUST_PRODUCT_CODE:obj.getYnProductCode());
        List<String> transactionNos = new ArrayList<String>();
        transactionNos.add(info.getTransactionNo());
        param.put("transactionNos",transactionNos);
        Map<String,Object> responseMap = postForObject(param, Map.class, YNTRUST_URL + QUERY_REPAY_ORDER,"还款订单状态查询",orderNo,isPay?resultPay:resultInfo);
        if(RespStatusEnum.FAIL.getCode().equals(isPay?resultPay.getCode():resultInfo.getCode()))return;
        if(isPay){
            YntrustRepaymentPayDto pay = mappingPay(responseMap,orderNo,requestId,info.getTransactionNo(),obj);
            RespHelper.setSuccessDataObject(resultPay,pay);
        } else {
            mappingInfo(responseMap,info,orderNo,requestId);
            RespHelper.setSuccessDataObject(resultInfo,info);
        }

    }

    public YntrustRepaymentPayDto mappingPay(Map<String,Object> responseMap,String orderNo,String requestId,String transactionNo,YntrustMappingDto obj){
        Object data = MapUtils.getObject(responseMap,"Data");
        YntrustRepaymentPayDto pay = new YntrustRepaymentPayDto();
        if(null!=data){
            Map<String,Object> dataMap = gson.fromJson(gson.toJson(data),Map.class);
            Object obj2 = MapUtils.getObject(dataMap,"RepaymentOrderList");
            if(null!=obj2){
                List<Map<String,Object>> l1 = gson.fromJson(gson.toJson(obj2),List.class);
                if(null!=l1&&l1.size()>0) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Map<String, Object> m1 = l1.get(0);
                    Integer orderStatus = 0;
                    if(m1.containsKey("orderStatus")){
                        orderStatus  = MapUtils.getInteger(m1, "orderStatus",0);
                    } else {
                        orderStatus  = MapUtils.getInteger(m1, "OrderStatus",0);
                    }
                    String statusName = getOrderStatus(orderStatus);
                    String occurTimeStr = "";
                    if(m1.containsKey("occurTime")) {
                        occurTimeStr = MapUtils.getString(m1, "occurTime");
                    } else {
                        occurTimeStr = MapUtils.getString(m1, "OccurTime");
                    }
                    Date occurTime = null;
                    if(StringUtils.isNotBlank(occurTimeStr)){
                        try {
                            occurTime = format.parse(occurTimeStr);
                        } catch (Exception e){
                            log.error("还款订单状态查询转换云南信托返回occurTime处理时间异常,云南信托返回的时间为"+occurTimeStr+",本次请求的requestId为"+requestId+":",e);
                        }
                    }

                    Object obj3 = null;
                    if(m1.containsKey("repaymentDetailList")) {
                        obj3 = MapUtils.getObject(m1, "repaymentDetailList");
                    } else {
                        obj3 = MapUtils.getObject(m1, "RepaymentDetailList");
                    }
                    List<Map<String,Object>> l2 = gson.fromJson(gson.toJson(obj3),List.class);
                    String remark = "-";
                    if(null!=l2&&l2.size()>0) {
                        Map<String,Object> deletMap = new HashMap<String,Object>();
                        deletMap.put("transactionNo",transactionNo);
                        Map<String,Object> m = l2.get(0);
                        m.put("createUid",userDto.getUid());
                        m.put("orderNo",orderNo);
                        m.put("transactionNo",transactionNo);
                        m.put("checkStatusName",1==MapUtils.getInteger(m,"checkStatus")?"检验成功":"校验失败");
                        m.put("recoverStatusName",1==MapUtils.getInteger(m,"recoverStatus")?"已核销":"未核销");
                        if(m.containsKey("remark")) {
                            remark = MapUtils.getString(m, "remark");
                        } else {
                            remark = MapUtils.getString(m, "Remark");
                        }
                        yntrustRepaymentInfoService.deleteRepaymentDetail(deletMap);
                        yntrustRepaymentInfoService.insertRepaymentDetail(m);
                    }

                    pay.setOrderNo(orderNo);
                    pay.setUpdateUid(userDto.getUid());
                    pay.setStatusName(statusName);
                    if(null!=orderStatus&&4==orderStatus){
                        pay.setStatus(""+0);
                    } else if(null!=orderStatus&&6==orderStatus){
                        pay.setStatus(""+1);
                    } else if(null!=orderStatus&&(5==orderStatus||9==orderStatus)){
                        pay.setStatus(""+2);
                    } else if(null!=orderStatus&&9==orderStatus){
                        pay.setStatus(""+orderStatus);
                    } else {
                        pay.setStatusName("");
                    }

                    pay.setFailMsg(remark);
                    pay.setCompleteTime(occurTime);
                    yntrustRepaymentPayService.update(pay);

                    if(6==orderStatus||7==orderStatus){
                        /*
                        YntrustRepaymentInfoDto info = new YntrustRepaymentInfoDto();
                        info.setOrderNo(orderNo);
                        info.setUpdateUid(userDto.getUid());
                        info.setOccurTime(occurTime);
                        info.setStatus(orderStatus);
                        info.setStatusName(statusName);
                        yntrustRepaymentInfoService.update(info);
                        */
                        obj.setStatus(2);
                        obj.setStatusName("完成");
                        obj.setUpdateUid(userDto.getUid());
                        yntrustMappingService.update(obj);
                    }

                }
            }
        }
        return pay;
    }
    public void mappingInfo(Map<String,Object> responseMap,YntrustRepaymentInfoDto info,String orderNo,String requestId){
        Object data = MapUtils.getObject(responseMap,"Data");

        if(null!=data){
            Map<String,Object> dataMap = gson.fromJson(gson.toJson(data),Map.class);
            Object obj2 = MapUtils.getObject(dataMap,"RepaymentOrderList");
            if(null!=obj2){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List<Map<String,Object>> l1 = gson.fromJson(gson.toJson(obj2),List.class);
                if(null!=l1&&l1.size()>0){
                    Map<String,Object> m1 = l1.get(0);
                    Integer orderStatus = 0;
                    if(m1.containsKey("OrderStatus")) {
                        orderStatus = MapUtils.getInteger(m1, "OrderStatus");
                    } else {
                        orderStatus = MapUtils.getInteger(m1, "orderStatus");
                    }

                    String statusName = getOrderStatus(orderStatus);
                    String occurTimeStr = "";
                    if(m1.containsKey("OccurTime")) {
                        occurTimeStr = MapUtils.getString(m1, "OccurTime");
                    } else {
                        occurTimeStr = MapUtils.getString(m1, "occurTime");
                    }

                    Date occurTime = null;
                    if(StringUtils.isNotBlank(occurTimeStr)){
                        try {
                            occurTime = format.parse(occurTimeStr);
                        } catch (Exception e){
                            log.error("还款订单状态查询转换云南信托返回occurTime处理时间异常,云南信托返回的时间为"+occurTimeStr+",本次请求的requestId为"+requestId+":",e);
                        }
                    }
                    info.setStatus(orderStatus);
                    info.setStatusName(statusName);
                    info.setOccurTime(occurTime);
                    info.setUpdateUid(userDto.getUid());
                    info.setUpdateTime(new Date());
                    Object obj3 = null;
                    if(m1.containsKey("RepaymentDetailList")) {
                        obj3 = MapUtils.getObject(m1, "RepaymentDetailList");
                    } else {
                        obj3 = MapUtils.getObject(m1, "repaymentDetailList");
                    }
                    List<Map<String,Object>> l2 = gson.fromJson(gson.toJson(obj3),List.class);
                    if(null!=l2&&l2.size()>0){
                        Map<String,Object> m = l2.get(0);
                        String remark = "";
                        if(m.containsKey("Remark")) {
                            remark = MapUtils.getString(m, "Remark");
                        } else {
                            remark = MapUtils.getString(m, "remark");
                        }
                        info.setFailMsg(remark);
                    } else {
                        info.setFailMsg("-");
                    }
                    yntrustRepaymentInfoService.update(info);
                }
            }
        }
    }


    public String getOrderStatus(Integer orderStatus){

        /**
         * 5=还款方式为1000线上代扣代扣失败后对应状态
         * 9=还款方式为5001快捷支付代扣失败后对应状态
         */
        String statusName = "";
        if(null==orderStatus)return statusName;

        switch (orderStatus){
            case 0:statusName = "已创建";
                break;
            case 1:statusName = "检验中";
                break;
            case 2:statusName = "检验失败";
                break;
            case 3:statusName = "检验成功";
                break;
            case 4:statusName = "支付中";
                break;
            case 5:statusName = "支付异常";
                break;
            case 6:statusName = "支付成功";
                break;
            case 7:statusName = "已完成";
                break;
            case 8:statusName = "撤销";
                break;
            case 9:statusName = "支付失败";
                break;
        }
        return statusName;
    }
    /**
     * 订单支付接口
     * @param result
     * @param orderNo
     */
    public void paymentOrder(RespDataObject<Map<String,Object>> result,String orderNo,YntrustRepaymentPayDto pay)throws Exception{
        lock.lock();
        try {
            YntrustRepaymentPayDto tmpPay = new YntrustRepaymentPayDto();
            tmpPay.setOrderNo(pay.getOrderNo());
            tmpPay = yntrustRepaymentPayService.select(tmpPay);
            if(null!=tmpPay&&"1".equals(tmpPay.getStatus())){
                RespHelper.setFailDataObject(result,null,"该订单已经支付成功不能重复提交支付!");
                return;
            }
            RespDataObject<YntrustRepaymentPayDto> tmpResult = new RespDataObject<YntrustRepaymentPayDto>();
            //queryPaymentOrder(tmpResult,pay.getOrderNo());
            queryRepayOrder(null,tmpResult,pay.getOrderNo(),true);
            if(RespStatusEnum.SUCCESS.getCode().equals(tmpResult.getCode())){
                tmpPay = tmpResult.getData();
                if("0".equals(tmpPay.getStatus())){
                    RespHelper.setFailDataObject(result,null,"该订单已经在支付中,不能重复提交支付!");
                    return;
                } else if("1".equals(tmpPay.getStatus())){
                    RespHelper.setFailDataObject(result,null,"该订单已经支付成功,不能重复提交支付!");
                    return;
                }
            }

            YntrustMappingDto obj = getYntrustMapping(result, orderNo);
            if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) return;
            YntrustRepaymentInfoDto info = new YntrustRepaymentInfoDto();
            info.setOrderNo(orderNo);
            info = yntrustRepaymentInfoService.select(info);
            if (null == info || StringUtils.isBlank(info.getTransactionNo())) {
                RespHelper.setFailDataObject(result, null, "没有查询到该订单的交易编号transactionNo");
                return;
            }

            Map<String, Object> param = new HashMap<String, Object>();
            String requestId = UuidUtil.getUUID();
            param.put("requestId", requestId);
            param.put("uniqueId", obj.getUniqueId());
            param.put("productCode", (null==obj||StringUtil.isBlank(obj.getYnProductCode()))?YNTRUST_PRODUCT_CODE:obj.getYnProductCode());

            /**
             * 还款订单交易请求编号
             */
            param.put("transactionNo", info.getTransactionNo());
            /**
             * 交易类型,0:线下转账,2:线上代扣
             */
            param.put("voucherType", pay.getVoucherType());
            /**
             * 银行帐户号
             */
            /*
            boolean verification = verification(pay.getAccountNo());
            if (!verification) {
                RespHelper.setFailDataObject(result, null, "银行账号格式有误");
                return;
            }*/
            param.put("accountNo", pay.getAccountNo());
            /**
             * 银行帐户名称
             */
            param.put("accountName", pay.getAccountName());
            /**
             * 通道交易号
             * 线上代扣则为空
             * 线下转账必填（付款流水号,银行转账流水号）
             */
            param.put("bankTransactionNo", pay.getBankTransactionNo());
            param.put("amount", new BigDecimal(new BigDecimal(Double.toString(pay.getAmount())).toPlainString()));
            /**
             * 收款方账号 为空则默认信托专户账户
             */
            param.put("trustAccountNo", pay.getTrustAccountNo());
            /**
             * 收款方户名 为空则默认信托专户账户名
             */
            param.put("trustAccountName", pay.getTrustAccountName());
            /**
             * 收款方开户行 为空则默认信托专户开户行
             */
            param.put("trustBankCode", pay.getTrustBankCode());
            Map<String, Object> responseMap = postForObject(param, Map.class, YNTRUST_URL + PAYMENTORDER, "订单支付", orderNo, result);
            if (RespStatusEnum.FAIL.getCode().equals(result.getCode())) {
                pay.setTransactionNo(info.getTransactionNo());
                pay.setUpdateUid(userDto.getUid());
                pay.setPushStatus(2);
                yntrustRepaymentPayService.insert(pay);
                return;
            }
            pay.setTransactionNo(info.getTransactionNo());
            pay.setUpdateUid(userDto.getUid());
            pay.setPushStatus(1);
            yntrustRepaymentPayService.insert(pay);
            RespHelper.setSuccessDataObject(result, responseMap);
        } catch (Exception e){
            throw new Exception(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 订单支付状态查询
     * @param result
     * @param orderNo
     */
    public void queryPaymentOrder(RespDataObject<YntrustRepaymentPayDto> result,String orderNo)throws Exception{
        YntrustMappingDto obj = getYntrustMapping(result, orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        YntrustRepaymentInfoDto info = new YntrustRepaymentInfoDto();
        info.setOrderNo(orderNo);
        info = yntrustRepaymentInfoService.select(info);
        if(null==info||StringUtils.isBlank(info.getTransactionNo())){
            RespHelper.setFailDataObject(result,null,"没有查询到该订单还款交易编号");
            return;
        }
        Map<String,Object> param = new HashMap<String,Object>();
        String requestId = UuidUtil.getUUID();
        param.put("requestId", requestId);
        param.put("uniqueId", obj.getUniqueId());
        param.put("productCode", (null==obj||StringUtil.isBlank(obj.getYnProductCode()))?YNTRUST_PRODUCT_CODE:obj.getYnProductCode());
        List<String> transactionNoList = new ArrayList<String>();
        transactionNoList.add(info.getTransactionNo());
        param.put("transactionNoList",transactionNoList);
        Map<String,Object> responseMap  = postForObject(param, Map.class, YNTRUST_URL+QUERY_PAYMENT_ORDER,"订单支付状态查询",orderNo,result);
        Object obj1 = MapUtils.getObject(responseMap,"PaymentOrderLists");
        YntrustRepaymentPayDto pay = new YntrustRepaymentPayDto();
        pay.setOrderNo(orderNo);
        if(null!=obj1){
            List<Map<String,Object>> list = gson.fromJson(gson.toJson(obj1),List.class);
            if(null!=list&&list.size()>0){
                Map<String,Object> t1 = list.get(0);
                Object obj2 = MapUtils.getObject(t1,"PaymentOrders");
                if(null!=obj2){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    List<Map<String,Object>> l2 = gson.fromJson(gson.toJson(obj2),List.class);
                    Map<String,Object> t2 = l2.get(0);
                    /**
                     * 支付状态,0:支付中,1:支付成功,2:支付失败
                     */
                    String paymentStatus = MapUtils.getString(t2,"PaymentStatus");
                    /**
                     * 交易完成时间
                     */
                    String completeTimeStr = MapUtils.getString(t2,"CompleteTime");
                    if(StringUtils.isNotBlank(completeTimeStr)){
                        pay.setCompleteTime(format.parse(completeTimeStr));
                    }
                    /**
                     * 资金入账时间
                     */
                    String fundAcceptTimeStr = MapUtils.getString(t2,"FundAcceptTime");
                    if(StringUtils.isNotBlank(fundAcceptTimeStr)){
                        pay.setFundAcceptTime(format.parse(fundAcceptTimeStr));
                    }
                    pay.setStatus(paymentStatus);
                    String remark = "";
                    if(t2.containsKey("Remark")){
                        remark = MapUtils.getString(t2,"Remark");
                    } else {
                        remark = MapUtils.getString(t2,"remark");
                    }
                    pay.setFailMsg(remark);
                    if("0".equals(paymentStatus)){
                        pay.setStatusName("支付中");
                    } else if("1".equals(paymentStatus)){
                        pay.setStatusName("支付成功");
                        obj.setStatus(2);
                        obj.setStatusName("完成");
                        obj.setUpdateUid(userDto.getUid());
                        yntrustMappingService.update(obj);
                    } else if("2".equals(paymentStatus)){
                        pay.setStatusName("支付失败");
                    }
                    pay.setUpdateUid(userDto.getUid());
                    pay.setCreateUid(userDto.getUid());
                    yntrustRepaymentPayService.update(pay);
                }
            }
        }
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        RespHelper.setSuccessDataObject(result,pay);
    }

    /**
     * 取消贷款，让此笔贷款的流程作废
     * @param result
     * @param orderNo
     */
    public void cancelLoan(RespStatus result,String orderNo){
        YntrustMappingDto obj = getYntrustMapping(result, orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        Map<String,Object> param = new HashMap<String,Object>();
        String requestId = UuidUtil.getUUID();
        param.put("requestId", requestId);
        param.put("uniqueId", obj.getUniqueId());
        Map<String,Object> responseMap = postForObject(param,Map.class,YNTRUST_URL+CCLACTION,"取消贷款",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        obj.setStatus(-1);
        obj.setStatusName("作废");
        obj.setUpdateUid(userDto.getUid());
        yntrustMappingService.update(obj);
        YntrustLoanDto loan = new YntrustLoanDto();
        loan.setOrderNo(orderNo);
        loan.setCancelStatus(-1);
        yntrustLoanService.updateByOrderNo(loan);
        RespHelper.setSuccessRespStatus(result);
    }

    /**
     * 更新逾期费用
     * @param result
     * @param orderNo
     */
    public void updateOverDueFee(RespStatus result,String orderNo,YntrustRepaymentInfoDto info){
        YntrustMappingDto obj = getYntrustMapping(result, orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        YntrustRepaymentPlanDto plan = new YntrustRepaymentPlanDto();
        plan.setOrderNo(orderNo);
        plan = yntrustRepaymentPlanService.select(plan);
        if(null==plan||StringUtils.isBlank(plan.getPartnerScheduleNo())){
            RespHelper.setFailRespStatus(result,"没有查询到改订单的还款计划信息或还款计划编号为空");
            return;
        }

        Map<String,Object> param = new HashMap<String,Object>();
        String requestId = UuidUtil.getUUID();
        param.put("requestId", requestId);

        List<Map<String,Object>> overDueFeeDetails = new ArrayList<Map<String,Object>>();
        Map<String,Object> overDueFee = new HashMap<String,Object>();
        /**
         * 还款计划编号
         */
        overDueFee.put("partnerScheduleNo",plan.getPartnerScheduleNo());
        overDueFee.put("uniqueId", obj.getUniqueId());
        /**
         * 罚息
         * 该金额为发生逾期后的全量金额，非计算日增量金额；
         * 更新值需大于等于该项已扣金额
         */
        //BigDecimal repayPrincipal = new BigDecimal(info.getRepayPrincipal());
        //overDueFee.put("penaltyFee", null==info.getPenaltyFee()?0d:repayPrincipal.add(new BigDecimal(info.getPenaltyFee())).doubleValue());
        overDueFee.put("penaltyFee", null==info.getPenaltyFee()?0d:new BigDecimal(new BigDecimal(Double.toString(info.getPenaltyFee())).toPlainString()));
        /**
         * 逾期违约金
         */
        overDueFee.put("latePenalty", null==info.getLatePenalty()?0d:new BigDecimal(new BigDecimal(Double.toString(info.getLatePenalty())).toPlainString()));
        /**
         * 逾期服务费
         */
        overDueFee.put("lateFee", null==info.getLateFee()?0d:new BigDecimal(new BigDecimal(Double.toString(info.getLateFee())).toPlainString()));
        /**
         * 逾期其他费用
         */
        overDueFee.put("lateOtherCost", null==info.getLateOtherCost()?0d:new BigDecimal(new BigDecimal(Double.toString(info.getLateOtherCost())).toPlainString()));
        /**
         * 逾期费用总计
         */
        overDueFee.put("totalOverdueFee", null==info.getOverDueFee()?0d:new BigDecimal(new BigDecimal(Double.toString(info.getOverDueFee())).toPlainString()));

        overDueFeeDetails.add(overDueFee);

        param.put("overDueFeeDetails", overDueFeeDetails);
        Map<String,Object> responseMap = postForObject(param,Map.class,YNTRUST_URL+UPDATE_OVERDUEFEE,"更新逾期费用",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        info.setUpdateUid(userDto.getUid());
        info.setCreateUid(userDto.getUid());
        info.setUpdateTime(new Date());
        yntrustRepaymentInfoService.update(info);
        RespHelper.setSuccessRespStatus(result);

    }

    /**
     * 修改还款信息
     * @param result
     * @param orderNo
     */
    public void modifyRepay(RespStatus result,String orderNo){
        YntrustMappingDto mapping = getYntrustMapping(result, orderNo);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        YntrustRepaymentInfoDto info = new YntrustRepaymentInfoDto();
        info.setOrderNo(orderNo);
        info = yntrustRepaymentInfoService.select(info);
        if(null==info){
            RespHelper.setFailRespStatus(result,"没有查询到该订单关联的还款信息");
            return;
        }

        Map<String,Object> param = new HashMap<String,Object>();
        String requestId = UuidUtil.getUUID();
        param.put("requestId", requestId);
        param.put("uniqueId",mapping.getUniqueId());
        //TODO 改动态
        param.put("bankCode","C10104");//银行编号
        param.put("bankAccount","123456789");//银行账号
        param.put("bankName","jianshe");//银行名称
        param.put("bankProvince","44");//银行开户行省份
        param.put("bankCity","4401");//银行开户行城市

        postForObject(param,Map.class,YNTRUST_URL+MODIFY_REPAY,"修改还款信息",orderNo,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        RespHelper.setSuccessRespStatus(result);
    }

    /**
     * 放款对账查询
     * @param result
     * @param orderNo
     */
    public void loaninfo(RespDataObject<Map<String,Object>> result,
                         String orderNo,Map<String,Object> param){
        YntrustMappingDto mapping = getYntrustMapping(result, orderNo);
        String requestId = UuidUtil.getUUID();
        param.put("requestId", requestId);
        param.put("productCode",(null==mapping||StringUtil.isBlank(mapping.getYnProductCode()))?YNTRUST_PRODUCT_CODE:mapping.getYnProductCode());
        Map<String,Object> responseMap = postForObject(param, Map.class, YNTRUST_URL + LOAN_INFO, "放款对账查询", orderNo, result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        RespHelper.setSuccessDataObject(result,responseMap);
    }

    /**
     * 获取快鸽与云南信托推送映射的中间表
     * @param result
     * @param orderNo
     * @return
     */
    public YntrustMappingDto getYntrustMapping(RespStatus result,String orderNo){
        YntrustMappingDto obj = new YntrustMappingDto();
        obj.setOrderNo(orderNo);
        obj.setStatus(1);
        obj = yntrustMappingService.select(obj);
        if(null==obj||StringUtils.isBlank(obj.getUniqueId())){
            RespHelper.setFailRespStatus(result,"没有获取到与该订单号关联的uniqueId信息");
        }
        return obj;
    }
    /**
     *  发送请求给第三方
     * @param param 请求第三方接口参数
     * @param clazz 返回类型
     * @param url 请求路径
     * @return
     */
    public <T> T postForObject(Map<String,Object> param,Class<T> clazz,String url){
        HttpHeaders headers = getHeader(param);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(param, headers);
        String json = restTemplate.postForObject(url, requestEntity, String.class);
        if(!url.contains(GET_QRCODE)) {
            log.info("返回信息为:" + json);
        }
        T t = gson.fromJson(json,clazz);
        return t;
    }

    /**
     * 发送请求给第三方
     * @param param 请求参数
     * @param clazz 返回类型
     * @param url 请求路径
     * @param requestInterfaceName 接口名称
     * @param orderNo 订单号
     * @param <T>
     * @return
     */
    public <T> T postForObject(Map<String,Object> param,
                               Class<T> clazz,
                               String url,
                               String requestInterfaceName,
                               String orderNo,
                               RespStatus result){
        String requestId = MapUtils.getString(param,"requestId");
        log.info("==========================请求接口名称:"+requestInterfaceName+":"+requestId+"=============================");
        T t = postForObject(param,clazz,url);
        String json = gson.toJson(t);
        Map<String,Object> responseMsg = new HashMap<String,Object>();
        boolean isSuccess = analyzeResponseMsg(gson.fromJson(json,Map.class),result,responseMsg);
        String responseStatusMsg = MapUtils.getString(responseMsg,"responseStatusMsg");
        String requestMsg = "";
        if(!url.equals(YNTRUST_URL + FUACTION)
                &&!url.equals(YNTRUST_URL+GET_CONTRACT_FILE)){
            requestMsg = gson.toJson(param);
        }
        if("1".equals(MapUtils.getString(param,"isGetFileContent"))
                &&url.equals(YNTRUST_URL+GET_CONTRACT_FILE)){
            json = "";
        }
        insertFlow(orderNo,requestId,json,url,requestInterfaceName,responseStatusMsg,requestMsg);
        if(!isSuccess){
            print(requestId,responseStatusMsg);
        }
        return t;
    }

    /**
     *  发送请求给第三方
     * @param param 请求第三方接口参数
     * @param clazz 返回类型
     * @param url 请求路径
     * @return
     */
    public <T> T postForObjectHeadSignedMsgNull(Map<String,Object> param,Class<T> clazz,String url){
        HttpHeaders headers = getHeader("");
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(param, headers);
        T t = restTemplate.postForObject(url, requestEntity, clazz);
        return t;
    }

    /**
     * 发送请求给第三方
     * @param param 请求参数
     * @param clazz 返回类型
     * @param url 请求路径
     * @param requestInterfaceName 请求接口名称
     * @param orderNo 订单号
     * @param result
     * @param <T>
     * @return
     */
    public <T> T postForObjectHeadSignedMsgNull(Map<String,Object> param,
                                                Class<T> clazz,
                                                String url,
                                                String requestInterfaceName,
                                                String orderNo,
                                                RespStatus result){
        T t = postForObjectHeadSignedMsgNull(param,clazz,url);
        String json = gson.toJson(t);
        String requestId = MapUtils.getString(param,"requestId");
        Map<String,Object> responseMsg = new HashMap<String,Object>();
        boolean isSuccess = analyzeResponseMsg(gson.fromJson(json,Map.class),result,responseMsg);
        String responseStatusMsg = MapUtils.getString(responseMsg,"responseStatusMsg");
        String requestMsg = "";
        if(!url.equals(YNTRUST_URL + FUACTION)){
            requestMsg = gson.toJson(param);
        }
        insertFlow(orderNo,requestId,json,url,requestInterfaceName,responseStatusMsg,requestMsg);
        if(!isSuccess){
            print(requestId,responseStatusMsg);
        }
        return t;
    }

    /**
     * 更新与新增快鸽与云南信托推送状态
     * @param orderNo 订单号
     * @param uniqueId 快鸽与云南信托推送唯一标示
     * @param status 状态
     * @param statusMsg 状态名称
     */
    public void insertMapping(String orderNo,
                              String uniqueId,
                              Integer status,
                              String statusMsg,
                              String ynProductCode,
                              String ynProductName){
        YntrustMappingDto mapping = new YntrustMappingDto();
        mapping.setCreateUid(userDto.getUid());
        mapping.setOrderNo(orderNo);
        mapping.setUniqueId(uniqueId);
        mapping.setStatus(status);
        mapping.setStatusName(statusMsg);
        if(StringUtil.isBlank(ynProductCode)) {
            mapping.setYnProductCode(YNTRUST_PRODUCT_CODE);
        } else {
            mapping.setYnProductCode(ynProductCode);
        }
        mapping.setYnProductName(ynProductName);
        yntrustMappingService.insert(mapping);
    }
    /**
     * 获取证书
     * @Title: getKeyPath /WEB-INF/key 路径下
     * @param @param keyName 证书名称
     * @param @return
     * @return String
     */
    public String getCerKeyPath(String keyName){
        if(StringUtils.isEmpty(keyName)){
            return StringUtils.EMPTY;
        }
        String keyPath = "";
        String folderPath = getClass().getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        if (folderPath.indexOf("WEB-INF") > 0) {
            keyPath = folderPath.substring(0, folderPath
                    .indexOf("WEB-INF"));
        }
        keyPath += "WEB-INF"+ File.separator+"key"+File.separator+keyName;
        return keyPath;
    }

    /**
     * 初始化所需的Service
     */
    public void initService(YntrustBorrowService yntrustBorrowService,
                            YntrustContractService yntrustContractService,
                            YntrustMappingService yntrustMappingService,
                            YntrustRepaymentInfoService yntrustRepaymentInfoService,
                            YntrustRepaymentPayService yntrustRepaymentPayService,
                            YntrustRepaymentPlanService yntrustRepaymentPlanService,
                            YntrustRequestFlowService yntrustRequestFlowService,
                            YntrustImageService yntrustImageService,
                            YntrustLoanService yntrustLoanService,
                            UserDto userDto){
        this.yntrustBorrowService = yntrustBorrowService;
        this.yntrustContractService = yntrustContractService;
        this.yntrustMappingService = yntrustMappingService;
        this.yntrustRepaymentInfoService = yntrustRepaymentInfoService;
        this.yntrustRepaymentPayService = yntrustRepaymentPayService;
        this.yntrustRepaymentPlanService = yntrustRepaymentPlanService;
        this.yntrustRequestFlowService = yntrustRequestFlowService;
        this.yntrustImageService = yntrustImageService;
        this.yntrustLoanService = yntrustLoanService;
        this.userDto = userDto;
    }
    /**
     * 初始化对接第三方所需要的数据
     */
    public void init(){
        if(StringUtils.isBlank(PRIVATE_KEY)) {
            this.PRIVATE_KEY = ConfigUtils.INSTANCE.getStringValue(PRIVATE_KEY_NAME, PROPERTIES_NAME);
        }
        if(StringUtils.isBlank(PUBLIC_KEY)) {
            this.PUBLIC_KEY = ConfigUtils.INSTANCE.getStringValue(PUBLIC_KEY_NAME, PROPERTIES_NAME);
        }
        if(StringUtils.isBlank(MERID)) {
            this.MERID = ConfigUtils.INSTANCE.getStringValue(MERID_KEY_NAME, PROPERTIES_NAME);
        }
        if(StringUtils.isBlank(SECRETKEY)) {
            this.SECRETKEY = ConfigUtils.INSTANCE.getStringValue(SECRETKEY_KEY_NAME, PROPERTIES_NAME);
        }
        if(StringUtils.isBlank(YNTRUST_URL)) {
            this.YNTRUST_URL = ConfigUtils.INSTANCE.getStringValue(YNTRUST_URL_KEY_NAME, PROPERTIES_NAME);
        }
        if(StringUtils.isBlank(PRIVATECERT_PASSWORD)) {
            this.PRIVATECERT_PASSWORD = ConfigUtils.INSTANCE.getStringValue(PRIVATECERT_PASSWORD_KEY_NAME, PROPERTIES_NAME);
        }
        if(StringUtils.isBlank(PRIVATE_KEY_PATH)) {
            this.PRIVATE_KEY_PATH = getCerKeyPath(PRIVATE_KEY);
        }
        if(StringUtils.isBlank(YNTRUST_PRODUCT_CODE)){
            this.YNTRUST_PRODUCT_CODE = ConfigUtils.INSTANCE.getStringValue(YNTRUST_PRODUCT_CODE_KEY, PROPERTIES_NAME);
        }
        if(StringUtils.isBlank(YNTRUST_ACCOUNT_NO)){
            this.YNTRUST_ACCOUNT_NO = ConfigUtils.INSTANCE.getStringValue(YNTRUST_ACCOUNT_NO_KEY, PROPERTIES_NAME);
        }
        if(StringUtil.isBlank(YNTRUST_FILE_URL)){
            this.YNTRUST_FILE_URL = ConfigUtils.INSTANCE.getStringValue(YNTRUST_FILE_URL_KEY, PROPERTIES_NAME);
        }

        if(null==gson){
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
        if(null==mapper){
            mapper = new ObjectMapper();
        }
        getHttpUtil();
        getRestTemplate();
    }

    public RestTemplate getRestTemplate(){
        if(null==this.restTemplate){
            synchronized (obj) {
                if(null==this.restTemplate) {
                    this.restTemplate = new RestTemplate();
                }
            }
        }
        return this.restTemplate;
    }
    public HttpUtil getHttpUtil(){
        if(null==this.httpUtil){
            synchronized (obj) {
                if(null==this.httpUtil){
                    this.httpUtil = new HttpUtil();
                }
            }
        }
        return this.httpUtil;
    }
    public void clear(){
        this.PRIVATE_KEY = ConfigUtils.INSTANCE.getStringValue(PRIVATE_KEY_NAME, PROPERTIES_NAME);
        this.PUBLIC_KEY = ConfigUtils.INSTANCE.getStringValue(PUBLIC_KEY_NAME, PROPERTIES_NAME);
        this.MERID = ConfigUtils.INSTANCE.getStringValue(MERID_KEY_NAME, PROPERTIES_NAME);
        this.SECRETKEY = ConfigUtils.INSTANCE.getStringValue(SECRETKEY_KEY_NAME, PROPERTIES_NAME);
        this.YNTRUST_URL = ConfigUtils.INSTANCE.getStringValue(YNTRUST_URL_KEY_NAME, PROPERTIES_NAME);
        this.PRIVATECERT_PASSWORD = ConfigUtils.INSTANCE.getStringValue(PRIVATECERT_PASSWORD_KEY_NAME, PROPERTIES_NAME);
        this.PRIVATE_KEY_PATH = getCerKeyPath(PRIVATE_KEY);
        this.YNTRUST_PRODUCT_CODE = ConfigUtils.INSTANCE.getStringValue(YNTRUST_PRODUCT_CODE_KEY, PROPERTIES_NAME);
        this.YNTRUST_ACCOUNT_NO = ConfigUtils.INSTANCE.getStringValue(YNTRUST_ACCOUNT_NO_KEY, PROPERTIES_NAME);
        this.YNTRUST_FILE_URL = ConfigUtils.INSTANCE.getStringValue(YNTRUST_FILE_URL_KEY, PROPERTIES_NAME);
    }
    /**
     * 信息加密
     * @param msg 加密信息
     * @return
     */
    private  String getSignMsg(String msg){
        RsaSignUtils utl = new RsaSignUtils(PRIVATE_KEY_PATH,PRIVATECERT_PASSWORD);
        return utl.generate(msg);
    }
    private  HttpHeaders getHeader(Object obj) {
        HttpHeaders headers = new HttpHeaders ();
        headers.setContentType (MediaType.APPLICATION_JSON_UTF8);
        headers.add ("MerId", MERID);
        headers.add ("SecretKey", SECRETKEY);
        try {
            headers.add ("SignedMsg", getSignMsg(mapper.writeValueAsString(obj)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return headers;
    }

    /**
     * 银行卡校验
     */
    public static boolean verification(String number){
        if(StringUtil.isBlank(number))return false;
        int sumEven = 0;
        int sumOdd = 0;
        int length = number.length();
        int[] bankcard = new int[number.length()];
        if(number.matches("\\d{13,19}")){
            for (int i = 0; i < number.length(); i++) {
                bankcard[i] = Integer.parseInt(number.substring(length - i - 1, length- i));
            }
            for (int i = 0; i <length; i++) {
                if((i+1)%2==0){
                    if((bankcard[i]*2)>9){
                        sumEven += (bankcard[i]<<1)-9;
                    }
                    else{
                        sumEven += bankcard[i]<<1;
                    }
                }
                else{
                    sumOdd += bankcard[i];
                }
            }
        }
        return sumEven+sumOdd==0?false:(sumEven+sumOdd)%10==0;
    }

}
