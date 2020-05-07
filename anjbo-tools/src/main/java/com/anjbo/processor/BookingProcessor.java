package com.anjbo.processor;

import java.net.SocketTimeoutException;
import java.util.*;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.anjbo.bean.tools.BookingDetail;
import com.anjbo.common.Enums;
import com.anjbo.common.MortgageException;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.SendEmailWorker;
import com.anjbo.service.tools.BookingService;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;

/**
 * 预约取号处理类 @author limh limh@zxsf360.com @date 2016-6-2 下午07:15:56
 */
public class BookingProcessor {
    private Logger log = Logger.getLogger(getClass());
    public final static String baseUrl = "http://onlinebook.szreorc.com:8888/onlinebook";
    public final static String dayListUrl = baseUrl+"/dwr/exec/workDayDwr.listWorkDayByRegistrationAreaOid.dwr";
    public final static String timeListUrl = baseUrl+"/dwr/exec/workTimeSoltDwr.listWorkTimeSoltByRegistrationAreaOid.dwr";
    public final static String countObjectUrl = baseUrl+"/dwr/exec/bookingInformationDwr.countAllBookingAmount.dwr";
    public final static String createBookUrl = baseUrl+"/createBookWeb.do?method=createBookWeb";
    public final static String goCancelBookWeb = baseUrl+"/goCancelBookWeb.do?method=goCancelBookWeb";
    public final static String cancelBookUrl = baseUrl+"/cancelBookWeb.do?method=cancelBookWeb";
    public final static String goSearchBookWeb = baseUrl+"/goSearchBookWeb.do?method=goSearchBookWeb";
    /**
     * 不动产权证书及登记证明业务
     **/
    public final static String createFzBookWeb = baseUrl+"/createFzBookWeb.do?method=createBookWeb";
    /**
     * 验证码
     **/
    public final static String authCodeUrl = baseUrl+"/createBookWeb.do?method=getVerificationcode&vcdemander=userregister&time=";
    /**
     * 类型
     **/
    public final static String szItemUrl = baseUrl+"/listIsActiveBookingType.do?method=listIsActiveBookingType&_dc=1509432940428&_req=ext";
    /**
     * 类型编号
     **/
    public final static String szItemNoUrl = baseUrl+"/listBookingProcessItemByBookingTypeOid.do?method=listBookingProcessItemByBookingTypeOid&bookingTypeOid=%s&_dc=1509432940531&_req=ext";
    
    private static Map<String, String> dictMap;
    private BookingService bookingService;
    private static BookingProcessor bookingProcessor;

    public static synchronized BookingProcessor getInstance() {
        if (bookingProcessor == null) bookingProcessor = new BookingProcessor();
        return bookingProcessor;
    }

    private BookingProcessor() {
    }

    /**
     * 提交申请 @param bookingDetail @return
     */
    public RespStatus submitBooking(BookingDetail bookingDetail, BookingService bookingService, ThreadPoolTaskExecutor poolTaskExecutor) {
        RespStatus status = new RespStatus();
        this.bookingService = bookingService;
        if (dictMap == null) dictMap = bookingDetail.getDictMap();
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("bookingInformationOid", "");
            param.put("bookingCode", "");
            param.put("registrationAreaOid", bookingDetail.getRegistrationAreaOid());
            String regval = dictMap.get("registrationAreaOid" + bookingDetail.getRegistrationAreaOid());
            param.put("registrationAreaName", regval.substring(0, regval.indexOf("（")));
            param.put("bookingType", bookingDetail.getBookingType());
            String type = bookingDetail.getBookingType();
            String no = bookingDetail.getSzItemNo();
            String[] tnVal = dictMap.get("bookingType|szItemNo" + type + "|" + no).split("\\|");
            param.put("bookingTypeName", tnVal[0]);
            param.put("bussName", tnVal[1]);
            param.put("itemNo", "");
            param.put("szItemNo", bookingDetail.getSzItemNo());
            param.put("bookingDateLabel", bookingDetail.getWorkDayLabel());
            param.put("bookingDateStr", bookingDetail.getWorkDay());
            param.put("workTimeSoltOid", bookingDetail.getWorkTimeSolt());
            param.put("workTimeSoltName", bookingDetail.getWorkTimeSoltName());
            param.put("verificationcodereg", bookingDetail.getVerificationcodereg());
            /**是否为证业务类**/
            boolean isTackCard = "-1060".equals(bookingDetail.getBookingType());
            if (isTackCard) {//证业务类
                param.put("fzFileNo", bookingDetail.getFzFileNo());
            } else {//非领证业务类
                param.put("proveType", bookingDetail.getProveType());
                param.put("proveTypeName", dictMap.get("prove" + bookingDetail.getProveType()));
                if ("02".equals(bookingDetail.getProveType())) {
                	param.put("proveCode", "粤（" + bookingDetail.getProveYear() + "）深圳市不动产权第" + bookingDetail.getProveCode() + "号");
                } else {
                	param.put("proveCode", bookingDetail.getProveCode());
                }
                
                param.put("houseName", bookingDetail.getHouseName() + bookingDetail.getHouseNum());
            }
            param.put("personName", bookingDetail.getPersonName());
            param.put("certificateType", bookingDetail.getCertificateType());
            param.put("certificateTypeName", dictMap.get("certificate" + bookingDetail.getCertificateType()));
            param.put("certificateNo", bookingDetail.getCertificateNo());
            param.put("phoneNumber", bookingDetail.getPhoneNumber());
            //非领证业务类
            if (!isTackCard) {
                param.put("bookingSzAreaOid", bookingDetail.getBookingSzAreaOid());
                param.put("szAreaName", dictMap.get("bookingSzAreaOid" + bookingDetail.getBookingSzAreaOid()));
            }
            String createBookUrlNew = createBookUrl;
            //房地产注销抵押业务类 --- 抵押权注销登记
            if ("30156000169555062213440300".equals(bookingDetail.getSzItemNo())) {
                createBookUrlNew += "&XLType=" + bookingDetail.getIsXL();//注销抵押事项小类
            }
            param.put("cookieValue", bookingDetail.getCookieValue());
            //区分领证非领证业务类
            String result="";
            String messgeFlag="";
            Boolean tureFlase=false;

            String errorMsg="";
            List<Map<String,String>> searchWorkList= BookingProcessor.searchWork(param);
            //根据输入信息查询国土局数据返回结果后进行参数匹配
            if(CollectionUtils.isNotEmpty(searchWorkList)){
                //保存预约信息map
                for(int i=0;i<searchWorkList.size();i++){
                    Map<String,String> bookingMap=new HashMap<String, String>();
                    bookingMap.putAll(searchWorkList.get(i));
                    //声明变量处理参数
                    Map<String,String> bookingMaptemp =searchWorkList.get(i);
                    bookingMaptemp.remove("sturtsName");
                    bookingMaptemp.remove("workTimeSolt");
                    bookingMaptemp.remove("bookingDateStr");
                    bookingMaptemp.remove("registrationAreaName");
                    bookingMaptemp.remove("bookingCode");
                    Iterator<String> it = bookingMaptemp.keySet().iterator();

                    //获取拉取数据和输入参数进行等值匹配完全相同后更新数据信息
                    while(it.hasNext()) {
                        String key = it.next();
                        //System.out.println(key);
                        tureFlase=param.containsValue(MapUtils.getString(bookingMaptemp,key));
                        if(!tureFlase){
                            break;
                        }
                    }
                    //匹配成功封装参数
                    if(tureFlase){
                        String bookingDateLabel=MapUtils.getString(bookingMap,"bookingDateStr");
                        String bookingDateStr="";
                        bookingDateStr=MapUtils.getString(bookingMap,"bookingDateStr");
                        bookingDateStr=bookingDateStr.substring(0, bookingDateStr.indexOf("("));
                        //显示时间
                        bookingMap.put("bookingDate",bookingDateStr);
                        bookingMap.put("bookingDateLabel",bookingDateLabel);
                        String sturtsName=MapUtils.getString(bookingMap,"sturtsName");
                        if(sturtsName.equals("预约未到期")){
                            param.put("status","1");
                        }else if(sturtsName.equals("已取消")){
                            //判断状态为已取消重新进入申请流程
                            tureFlase=false;
                            continue;
                        }else {
                            System.out.println("");
                        }
                        param.putAll(bookingMap);
                        try {
                            BeanUtils.populate(bookingDetail, param);
                            bookingService.updateBookingCode(bookingDetail);
                            continue;
                        } catch (Exception e) {
                            System.out.println("对比成功");
                            System.out.println("transMap2Bean2 Error " + e);
                        }

                    }else{
                        //匹配失败，但国土局有其他记录，封装提示信息返回用户
                        String bussName=MapUtils.getString(bookingMap,"bussName");
                        String bookingCode=MapUtils.getString(bookingMap,"bookingCode");
                        String bookingDateStr=MapUtils.getString(bookingMap,"bookingDateStr");
                        String workTimeSolt=MapUtils.getString(bookingMap,"workTimeSolt");
                        StringBuffer sb=new StringBuffer();
                        sb.append("预约类型\""+bussName+",\"");
                        sb.append("预约流水号:"+bookingCode+",");
                        sb.append("预约时间:"+bookingDateStr+",");
                        sb.append(workTimeSolt);
                        errorMsg="您已在\"深圳市规划和国土资源委员会\"官网预约，"+sb.toString()+",详情请在\"深圳市规划和国土资源委员会官网\"中查看";
                    }
                }
            }

            result = HttpUtil.post(isTackCard ? createFzBookWeb : createBookUrlNew, param, "GBK", "GBK");
            messgeFlag = StringUtil.processLocation(result, "var messgeFlag = '(.*?)'");
            if (!"Y".equals(messgeFlag)) {//跳转标识     不能为空    用于判断成功Y还是失败N
                String messge = StringUtil.processLocation(result,
                        "var messge = \"(.*?)\"");
               /* if (StringUtil.isEmpty(messge)) {
                    status.setCode(RespStatusEnum.FAIL.getCode());
                    status.setMsg(RespStatusEnum.FAIL.getMsg());
                    return status;
                } else {
                    status.setCode(RespStatusEnum.SYSTEM_ERROR.getCode());
                    //判断状态成功更新订单信息，并返回用户信息
                    if(tureFlase){
                        status.setCode(RespStatusEnum.SUCCESS.getCode());
                        status.setMsg("亲，您以成功预约，请查看预约记录，合理安排时间！");
                    }else{
                        //失败但有记录显示封装参数
                        if(StringUtil.isNotEmpty(errorMsg)&&errorMsg!=""){
                            status.setMsg(errorMsg);
                        }else{
                            //无记录显示官网返回错误信息
                            status.setMsg(messge);
                        }

                    }
                    return status;
                }*/

                if(tureFlase){
                    status.setCode(RespStatusEnum.SUCCESS.getCode());
                    status.setMsg(RespStatusEnum.SUCCESS.getMsg());
                    return status;
                }else {
                    //失败但有记录显示封装参数
                    if(StringUtil.isNotEmpty(errorMsg)&&errorMsg!=""){
                        status.setCode(RespStatusEnum.FAIL.getCode());
                        status.setMsg(errorMsg);
                    }else{
                        //无记录显示官网返回错误信息
                        status.setCode(RespStatusEnum.FAIL.getCode());
                        status.setMsg(messge);
                    }
                    return status;
                }

            }
            if("".equals(messgeFlag)){
                String messge = StringUtil.processLocation(result,
                        "var messge = \"(.*?)\"");
                if(tureFlase){
                    status.setCode(RespStatusEnum.SUCCESS.getCode());
                    status.setMsg(RespStatusEnum.SUCCESS.getMsg());
                    return status;
                }else {
                    //失败但有记录显示封装参数
                    if(StringUtil.isNotEmpty(errorMsg)&&errorMsg!=""){
                        status.setCode(RespStatusEnum.FAIL.getCode());
                        status.setMsg(errorMsg);
                    }else{
                        //无记录显示官网返回错误信息
                        status.setCode(RespStatusEnum.FAIL.getCode());
                        status.setMsg(messge);
                    }
                    return status;
                }

            }



            // 申办流水号
            String bookingCode = StringUtil
                    .processLocationResult(result);
            // 添加申办详细信息
            if (StringUtil.isNotEmpty(bookingCode)) {
                //log.info("申办流水号：" + bookingCode);
//                bookingDetail.setBookingCode(bookingCode);
//                bookingDetail.setBookingDate(bookingDetail.getWorkDay());
//                bookingDetail.setWorkTimeSolt(bookingDetail.getWorkTimeSoltName());
//                bookingDetail.setStatus(1);//有效
//                bookingService.updateBookingCode(bookingDetail);
//                bookingDetail.setDictMap(dictMap);
                /********异步发送邮件******/
                String title = "在线申办提醒";
                String content = bookingDetail.toString();
                String emails = com.anjbo.utils.ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.BOOKING_EMAILS.toString());
                if (StringUtil.isNotEmpty(emails)) {
                    String[] emailArr = emails.split(",");
                    for (String email : emailArr) {
                        SendEmailWorker.asyncSendEmail(poolTaskExecutor, title, content, email);
                    }
                }
                /********异步发送邮件******/
                status.setCode(RespStatusEnum.SUCCESS.getCode());
                status.setMsg(RespStatusEnum.SUCCESS.getMsg());
                return status;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            status.setCode(RespStatusEnum.REQUEST_TIMEOUT_THIRD.getCode());
            status.setMsg(RespStatusEnum.REQUEST_TIMEOUT_THIRD.getMsg());
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        status.setCode(RespStatusEnum.FAIL.getCode());
        status.setMsg(RespStatusEnum.FAIL.getMsg());
        return status;
    }

    /**
     * 获取日期
     *
     * @param registrationAreaOid
     * @return
     * @throws MortgageException
     */
    public static String workDay(String registrationAreaOid) throws MortgageException {
        try {
            StringBuffer sb = new StringBuffer(dayListUrl);
            sb.append("?callCount=1");
            sb.append("&c0-scriptName=workDayDwr");
            sb.append("&c0-methodName=listWorkDayByRegistrationAreaOid");
            sb.append("&c0-id=5382_1432883453012");
            sb.append("&c0-param0=string:").append(registrationAreaOid);
            sb.append("&xml=true");
            String resule = HttpUtil.get(sb.toString());
            resule = StringUtil.processLocationArray(resule);
            resule = StringUtil.decode(resule);
            return resule;
        } catch (SocketTimeoutException e) {
            throw new MortgageException(RespStatusEnum.REQUEST_TIMEOUT_THIRD.getCode(),
                    RespStatusEnum.REQUEST_TIMEOUT_THIRD.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MortgageException(RespStatusEnum.REQUEST_FALL_THIRD.getCode(),
                    RespStatusEnum.REQUEST_FALL_THIRD.getMsg());
        }
    }

    /**
     * 获取时间
     *
     * @param registrationAreaOid
     * @param bookingType
     * @return
     * @throws MortgageException
     */
    public static String workTime(String registrationAreaOid, String bookingType) throws MortgageException {
        StringBuffer sb = new StringBuffer(timeListUrl);
        sb.append("?callCount=1");
        sb.append("&c0-scriptName=workTimeSoltDwr");
        sb.append("&c0-methodName=listWorkTimeSoltByRegistrationAreaOid");
        sb.append("&c0-id=1006_1432804917124");
        sb.append("&c0-param0=string:").append(registrationAreaOid);
        sb.append("&c0-param1=string:").append(bookingType);
        sb.append("&xml=true");
        try {
            String resule = HttpUtil.get(sb.toString());
            resule = StringUtil.processLocationArray(resule);
            resule = StringUtil.decode(resule);
            return resule;
        } catch (SocketTimeoutException e) {
            throw new MortgageException(RespStatusEnum.REQUEST_TIMEOUT_THIRD.getCode(),
                    RespStatusEnum.REQUEST_TIMEOUT_THIRD.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MortgageException(RespStatusEnum.REQUEST_FALL_THIRD.getCode(),
                    RespStatusEnum.REQUEST_FALL_THIRD.getMsg());
        }
    }

    /**
     * 获取数量
     *
     * @param registrationAreaOid
     * @param bookingType
     * @return
     * @throws MortgageException
     */
    public static String workCount(String registrationAreaOid, String bookingType) throws MortgageException {
        StringBuffer sb = new StringBuffer(countObjectUrl);
        sb.append("?callCount=1");
        sb.append("&c0-scriptName=bookingInformationDwr");
        sb.append("&c0-methodName=countAllBookingAmount");
        sb.append("&c0-id=8434_1432807482809");
        sb.append("&c0-param0=string:").append(registrationAreaOid);
        sb.append("&c0-param1=string:").append(bookingType);
        sb.append("&xml=true");
        try {
            String resule = HttpUtil.get(sb.toString());
            resule = StringUtil.processLocationObject(resule);
            resule = StringUtil.decode(resule);
            return resule;
        } catch (SocketTimeoutException e) {

            throw new MortgageException(RespStatusEnum.REQUEST_TIMEOUT_THIRD.getCode(),
                    RespStatusEnum.REQUEST_TIMEOUT_THIRD.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MortgageException(RespStatusEnum.REQUEST_FALL_THIRD.getCode(),
                    RespStatusEnum.REQUEST_FALL_THIRD.getMsg());
        }
    }

    /**
     * 查询预约取号状态处理类
     *
     * @param params
     * @return
     */
    public static List<Map<String, String>> searchWork(Map<String, String> params) {
        List<Map<String, String>> workStateMap = new ArrayList<Map<String, String>>();
        StringBuffer sb = new StringBuffer(BookingProcessor.goSearchBookWeb);
        String certificateNo= MapUtils.getString(params,"certificateNo");
        String phoneNumber=MapUtils.getString(params,"phoneNumber");
        sb.append("&certificateNo=").append(certificateNo);
        sb.append("&phoneNumber=").append(phoneNumber);
        //预约流水号
        String bookingCode = "";
        //业务事项
        String tempItemName = "";
        //办理登记点
        String registrationAreaName = "";
        //预约日期
        String bookingDateStr = "";
        //预约时段
        String workTimeSolt = "";
        //状态
        String sturtsName = "";
        //用户名
        String personName="";

        try {
            String result = HttpUtil.get(sb.toString(), "GBK", 10000, null,
                    null);
            Document document = Jsoup.parse(result);

            //提取所需要的数据
            Elements trs = document.getElementById("dataDiv").getElementsByTag("table");
            for (Element el : trs) {
                String elResult = el.html();
                bookingCode = StringUtil.processLocation(elResult, "<td colspan=\"3\" id=\"bookingCode\">&nbsp;(.*?)</td>");
                tempItemName = StringUtil.processLocation(elResult, "<td id=\"tempItemName\" width=\"15%\">(.*?)</td>");
                registrationAreaName = StringUtil.processLocation(elResult, "<td width=\"18%\">&nbsp;<font id=\"registrationAreaName\">(.*?)</font></td>");
                bookingDateStr = StringUtil.processLocation(elResult, "<td width=\"19%\">&nbsp;<font id=\"bookingDateStr\">(.*?)</font></td>");
                workTimeSolt = StringUtil.processLocation(elResult, "<td id=\"workTimeSolt\" width=\"12%\">&nbsp;(.*?)</td>");
                personName = StringUtil.processLocation(elResult, "<td id=\"personName\">&nbsp;(.*?)</td>");


                sturtsName = StringUtil.processLocation(elResult, "<td id=\"sturtsName\">&nbsp;(.*?)</td>");
                Map<String, String> mapTemp = new HashMap<String, String>();

                //拆分类型 大类 小类
                tempItemName=tempItemName.replaceAll("&nbsp;","");
                String bookingTypeName="";//大类
                String bussName="";//小类不动产登记证明或房地产证
                String a[]=tempItemName.split("---");
                bookingTypeName=a[0].trim();
                bussName=a[1].trim();
                mapTemp.put("bookingCode", bookingCode);
                //封装大类小类参数
                mapTemp.put("bussName", bussName);
                mapTemp.put("bookingTypeName", bookingTypeName);
                mapTemp.put("personName", personName);

                mapTemp.put("registrationAreaName", registrationAreaName);
                mapTemp.put("bookingDateStr", bookingDateStr);
                mapTemp.put("workTimeSolt", workTimeSolt);
                mapTemp.put("sturtsName", sturtsName);
                if(bookingTypeName.equals(MapUtils.getString(params,"bookingTypeName"))&&bussName.equals(MapUtils.getString(params,"bussName")))
                    workStateMap.add(mapTemp);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return workStateMap;
    }

}
