package com.anjbo.controller.sm;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.config.page.PageConfigDto;
import com.anjbo.bean.config.page.PageTabConfigDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.controller.BaseController;
import com.anjbo.service.ProductDataBaseService;
import com.anjbo.service.ProductListBaseService;
import com.anjbo.service.ProductSubmitBaseService;
import com.anjbo.service.sm.AgencyService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.UidUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/11/10.
 */
@RequestMapping("/credit/product/data/sm/agency")
@Controller
public class AgencyController extends BaseController{
    @Resource
    private AgencyService agencyService;
    @Resource
    private ProductDataBaseService productDataBaseService;
    @Resource
    private ProductSubmitBaseService productSubmitBaseService;
    @Resource
    private ProductListBaseService productListBaseService;

    protected final Log logger = LogFactory.getLog(this.getClass());
    /**
     *撤回
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/withdraw")
    public RespStatus withdraw(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try {
            if(MapUtils.isEmpty(map)
                    ||"".equals(MapUtils.getString(map,"orderNo"))){
                return RespHelper.setFailRespStatus(result, "撤回操作缺少参数");
            }
            UserDto user = getUserDto(request);
            if(null!=user){
                map.put("createUid",user.getUid());
                map.put("updateUid",user.getUid());
                map.put("userName",user.getName());
            }
            result =  agencyService.withdraw(map);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
            logger.error("机构审批撤回异常:",e);
        }
        return result;
    }

    /**
     * 取消
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/cancel")
    public RespStatus cancel(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try {
            if(MapUtils.isEmpty(map)
                    ||"".equals(MapUtils.getString(map,"orderNo"))){
                return RespHelper.setFailRespStatus(result, "取消操作缺少参数");
            }
            UserDto user = getUserDto(request);
            if(null!=user){
                map.put("createUid",user.getUid());
                map.put("updateUid",user.getUid());
                map.put("userName",user.getName());
            }
            result = agencyService.cancel(map);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
            logger.error("机构审批取消异常:",e);
        }
        return RespHelper.setSuccessRespStatus(result);
    }

    /**
     * 重启审批
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/restart")
    public RespStatus restart(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try {
            if(MapUtils.isEmpty(map)
                    ||"".equals(MapUtils.getString(map,"orderNo"))){
                return RespHelper.setFailRespStatus(result, "重启操作缺少参数");
            }
            UserDto user = getUserDto(request);
            if(null!=user){
                map.put("createUid",user.getUid());
                map.put("updateUid",user.getUid());
                map.put("userName",user.getName());
            }
            result = agencyService.restart(map);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
            logger.error("机构审批重启异常:",e);
        }
        return RespHelper.setSuccessRespStatus(result);
    }

    /**
     * APP新增机构申请
     * @param request
     * @param map(key=agencyName:机构名称,key=contactsName:联系人姓名,key=contactsPhone:联系人电话
     *              key=applyProduct:申请产品多个用逗号隔开，key=applyCity:申请城市,多个用逗号隔开)
     * @return
     */
    @ResponseBody
    @RequestMapping("/appAddAgency")
    public RespDataObject<Integer> appAddAgency(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Integer> result = new RespDataObject<Integer>();
        try {
            logger.info("APP提交申请入驻信息:"+map.toString());
            ProductDataDto productDataDto = new ProductDataDto();
            if(StringUtils.isNotBlank(MapUtils.getString(map,"uid"))) {
                productDataDto.setCreateUid(MapUtils.getString(map, "uid", ""));
                productDataDto.setUpdateUid(MapUtils.getString(map, "uid", ""));
                map.remove("uid");
            } else {
                return  RespHelper.setFailDataObject(result,null, "缺少登录人用户信息!");
            }
            map.put("app","yes");
            RespStatus respStatus = checkMobile(request,map);
            if(RespStatusEnum.FAIL.getCode().equals(respStatus.getCode())){
                return  RespHelper.setFailDataObject(result,null, respStatus.getMsg());
            }

            PageConfigDto pageConfigDto = (PageConfigDto) RedisOperator.get("tbl_sm_addAgency_page");
            String orderNo = UidUtil.generateOrderId();
            String tblName = "";
            if(null!=pageConfigDto){
                PageTabConfigDto pageTabConfigDto = pageConfigDto.getPageTabConfigDtos().get(0);
                tblName = pageTabConfigDto.getTblName();
            } else {
                tblName = "tbl_sm_agencyWaitDistribution";
            }
            String applyProductName = "";
            String applyCity = "";
            String applyCityName = "";

            //获取所有城市
            if(StringUtils.isNotBlank(MapUtils.getString(map,"applyCityName",""))){
                String tmpCityName = MapUtils.getString(map,"applyCityName");
                String[] str = tmpCityName.split(",");
                int index = 0;
                List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
                for(DictDto d:dictList){
                    for (String s:str){
                        if(d.getName().contains(s)&&StringUtils.isBlank(applyCityName)){
                            applyCity = d.getCode();
                            applyCityName = s+"市";
                            index++;
                            continue;
                        } else if(d.getName().contains(s)&&!applyCityName.contains(s)){
                            applyCity += ","+d.getCode();
                            applyCityName += ","+s+"市";
                            index++;
                            continue;
                        }
                    }
                    if(index==str.length){
                        break;
                    }
                }
            }
            //获取产品
            if(StringUtils.isNotBlank(MapUtils.getString(map,"applyProduct"))){
                String applyProduct = MapUtils.getString(map,"applyProduct");
                String[] str = applyProduct.split(",");
                int index = 0;
                List<DictDto> dictList = getDictDtoByType("product");
                for(DictDto d:dictList){
                    for (String s:str){
                        if(d.getCode().equals(s)&&StringUtils.isBlank(applyProductName)){
                            applyProductName = d.getName();
                            index++;
                            continue;
                        } else if(d.getCode().equals(s)){
                            applyProductName += ","+d.getName();
                            index++;
                            continue;
                        }
                    }
                    if(index==str.length){
                        break;
                    }
                }
            }
            Map<String,Object> param = new HashMap<String,Object>();
            Map<String,Object> otherData = new HashMap<String,Object>();
            map.put("applyProductName",applyProductName);
            map.put("applyCityName",applyCityName);
            map.put("applyCity",applyCity);

            Map<String,Object> listMap = new HashMap<String,Object>();
            listMap.putAll(map);
            listMap.put("accountUid",productDataDto.getCreateUid());
            otherData.put("tbl_sm_list",listMap);

            productDataDto.setOrderNo(orderNo);
            productDataDto.setTblName(tblName);
            productDataDto.setProductCode(MapUtils.getString(map,"productCode","100"));
            productDataDto.setCityCode(MapUtils.getString(map,"cityCode","104403"));
            productDataDto.setOtherData(otherData);
            productDataDto.setSource("APP");
            productDataDto.setData(map);
            param.put("app","yes");
            param.put("packageClassMethodName",pageConfigDto.getPackageClassMethodName());
            param.put("orderNo",productDataDto.getOrderNo());
            param.put("otherTblName",productDataDto.getOtherData());
            param.put("tblName","tbl_sm_");
            param.put("productCode",productDataDto.getProductCode());
            param.put("cityCode",productDataDto.getCityCode());
            param.put("pageClass","tbl_sm_addAgency_page");
            param.put("updateUid",productDataDto.getUpdateUid());
            param.put("createUid",productDataDto.getCreateUid());
            System.out.println(param);
            String re = productDataBaseService.updateProductDataBase(productDataDto);
            if("error".equals(re)){
                RespHelper.setFailDataObject(result,null,"请填写相关信息");
                return result;
            }
            RespStatus r =  productSubmitBaseService.submit(param);
            Integer agencyId = null;
            if(RespStatusEnum.SUCCESS.getCode().equals(r.getCode())){
                ProductDataDto p = new ProductDataDto();
                p.setOrderNo(orderNo);
                p.setTblDataName("tbl_sm_list");
                Map<String,Object> m = productListBaseService.selectProductListBaseByOrderNo(p);
                agencyId = MapUtils.getInteger(m,"agencyId");
            } else {
                RespHelper.setFailDataObject(result,null,r.getMsg());
                return result;
            }
            RespHelper.setSuccessDataObject(result,agencyId);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null, RespStatusEnum.FAIL.getMsg());
            logger.error("机构申请入驻审批异常:",e);
        }
        return result;
    }

    /**
     * 根据机构码或者agencyId获取机构的申请信息
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/getAgencyApplyDate")
    public RespDataObject<Map<String,Object>> getAgencyApplyDate(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String, Object>> dataObject = new RespDataObject<Map<String, Object>>();
        try {
            if(!map.containsKey("agencyCode")&&!map.containsKey("agencyId")){
                RespHelper.setFailDataObject(dataObject,null,"缺少机构码或者机构id");
                return dataObject;
            }
            Map<String,Object> tmp = agencyService.getAgencyApplyDate(map);
            RespHelper.setSuccessDataObject(dataObject,tmp);
        } catch (Exception e){
            RespHelper.setFailDataObject(dataObject,null,RespStatusEnum.FAIL.getMsg());
            logger.error("查询机构申请信息数据异常:",e);
        }
        return dataObject;
    }

    /**
     * 根据机构码获取机构申请列表信息
     * @param request
     * @param map key=agencyCode(机构码),key=agencyId(机构id)
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/getAgencyApplyList")
    public RespDataObject<Map<String,Object>> getAgencyApplyList(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String, Object>> dataObject = new RespDataObject<Map<String, Object>>();
        try {
            if(StringUtils.isBlank(MapUtils.getString(map,"agencyCode",""))
                    &&null==MapUtils.getInteger(map,"agencyId",null)){
                RespHelper.setFailDataObject(dataObject,null,"缺少机构码或者机构id");
                return dataObject;
            }
            Map<String,Object> tmp = productListBaseService.selectSmList(map);
            RespHelper.setSuccessDataObject(dataObject,tmp);
        } catch (Exception e){
            RespHelper.setFailDataObject(dataObject,null,RespStatusEnum.FAIL.getMsg());
            logger.error("查询机构申请信息数据异常:",e);
        }
        return dataObject;
    }

    /**
     * 生成测试账号
     * @param request
     * @param map key=agencyId(机构id),key=name(用户名),key=mobile(手机号码),key=uid(用户uid)
     *            key=orderNo(订单号),key=indateStart(有效开始时间),key=indateEnd(有效结束时间),
     *            key=isEnable(账号状态是否启用（-1待审批, 0启用 ，1未启用，2不通过, 3已解绑）)
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/testAgencyAccount")
    public RespStatus testAgencyAccount(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus result = new RespStatus();
        try{
            if(!map.containsKey("orderNo")){
                RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
                return result;
            }
            String orderNo = MapUtils.getString(map,"orderNo");
            String tblName = MapUtils.getString(map,"tblName");
            map.remove("tblName");
            String password = ConfigUtil.getStringValue(Constants.BASE_AGENCY_DEFAULT_PASSWORLD,ConfigUtil.CONFIG_BASE);
            map.put("password",password);
            HttpUtil http = new HttpUtil();
            String accountUid = MapUtils.getString(map,"uid");
            result = http.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/v/editAgentAdmin", map);
           if(RespStatusEnum.SUCCESS.getCode().equals(result.getCode())){
               String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
               String key = Constants.SMS_AGENCY_TEST_ACCOUNT;
               String mobile = MapUtils.getString(map,"mobile");

               String indateStartTmp = MapUtils.getString(map,"indateStart","");
               String indateEndTmp = MapUtils.getString(map,"indateEnd","");
               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               Date startd = null;
               if(StringUtils.isNotBlank(indateStartTmp)){
                   startd = format.parse(indateStartTmp);
               } else {
                   indateStartTmp = "-";
               }
               Date endd = null;
               if(StringUtils.isNotBlank(indateEndTmp)){
                   endd = format.parse(indateEndTmp);
               } else {
                   indateEndTmp = "-";
               }
               format = new SimpleDateFormat("yyyy年MM月dd日");
               String timeLimit = "";
               if(null!=startd&&null!=endd){
                   timeLimit = format.format(startd)+"至"+format.format(endd);
               } else if(null==startd&&null!=endd){
                   timeLimit = indateStartTmp+"至"+format.format(endd);
               } else if(null!=startd&&null==endd){
                   timeLimit = format.format(startd)+"至"+indateEndTmp;
               } else if(null==startd&&null==endd){
                   timeLimit = indateStartTmp+"至"+indateEndTmp;
               }
               //状态为不开启不发送短信
               if(1==MapUtils.getInteger(map,"isOpen")) {
                   AmsUtil.smsSend(mobile, ipWhite, key, mobile, password, timeLimit);
               }
               format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
               String indateEnd = "";
               String indateStart = "";
               Calendar c = Calendar.getInstance();
               if(null!=endd){
                   c.setTime(endd);
                   c.set(Calendar.HOUR_OF_DAY, 23);
                   c.set(Calendar.MINUTE, 59);
                   c.set(Calendar.SECOND, 59);
                   indateEnd = format.format(c.getTime());
               }
               if(null!=startd){
                   c.setTime(startd);
                   c.set(Calendar.HOUR_OF_DAY, 0);
                   c.set(Calendar.MINUTE, 0);
                   c.set(Calendar.SECOND, 0);
                   indateStart = format.format(c.getTime());
               }
               String updateSql = "isOpen="+MapUtils.getInteger(map,"isOpen");
               if(StringUtils.isNotBlank(indateStart)){
                   updateSql += ",indateStart='"+indateStart+"'";
               }
               if(StringUtils.isNotBlank(indateEnd)){
                   updateSql += ",indateEnd='"+indateEnd+"'";
               }
               updateSql += ",accountUid='"+result.getMsg()+"'";
               ProductDataDto productDataDto =  new ProductDataDto();
               productDataDto.setOrderNo(orderNo);
               productDataDto.setTblDataName(tblName+"list");
               productDataDto.setKeyValue(updateSql);
               productListBaseService.updateProductListBaseByKey(productDataDto);
               //更新机构表渠道经理
               /*
               Map<String,Object> tmp = productListBaseService.selectProductListBaseByOrderNo(productDataDto);
               if(null!=tmp){
                   AgencyDto agencyDto = new AgencyDto();
                   agencyDto.setId(MapUtils.getInteger(tmp,"agencyId"));
                   agencyDto.setChanlMan(MapUtils.getString(tmp,"channelManagerUid"));
                   RespStatus r = http.getRespStatus(Constants.LINK_CREDIT,"/credit/customer/agency/v/update",AgencyDto.class);
                   logger.info("生成测试更新更新机构渠道经理:===="+r+"====");
               }*/

            }
            logger.info("生成测试账号==="+result+"===");
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            logger.error("生成测试账号异常:",e);
        }
        return result;
    }

    /**
     * 待签约列表检索框初始化数据
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/waitSignListInit")
    public RespDataObject<Map<String,Object>> waitSignListInit(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try{
            HttpUtil http = new HttpUtil();
            Map<String, Object> param = new HashMap<String,Object>();
            param.put("type","roleName");
            param.put("choicePersonnel",Enums.RoleEnum.EXPANDMANAGER.getName());
            RespData<UserDto> expandList =  http.getRespData(Constants.LINK_CREDIT,"/credit/user/base/v/choicePersonnel",param,UserDto.class);
            param.put("choicePersonnel",Enums.RoleEnum.INVESTIGATIONMANAGER.getName());
            RespData<UserDto> investigationList =  http.getRespData(Constants.LINK_CREDIT,"/credit/user/base/v/choicePersonnel",param,UserDto.class);
            map.put("expandList",expandList.getData());
            map.put("investigationList",investigationList.getData());
            RespHelper.setSuccessDataObject(result,map);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,"待签约列表检索框初始化数据异常");
            logger.error("",e);
        }
        return result;
    }

    /**
     * 获取指定节点的上一节点
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/getPreProcessId")
    public RespDataObject<Map<String,Object>> getPreProcessId(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try{
            if(!map.containsKey("processId")){
                RespHelper.setFailDataObject(result,null,"查询上一节点缺少当前节点参数");
                return result;
            }
            Map<String,Object> processId = agencyService.getPreProcessId(map,"processId");
            RespHelper.setSuccessDataObject(result,processId);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,"获取指定节点的上一节点异常");
            logger.error("获取指定节点的上一节点异常",e);
        }
        return result;
    }

    /**
     * 校验申请的机构联系手机号是否重复有冲突
     * @param request
     * @param tmp
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/checkMobile")
    public RespStatus checkMobile(HttpServletRequest request,@RequestBody Map<String,Object> tmp){
        RespStatus respStatus = new RespStatus();
        try{
            HttpUtil httpUtil = new HttpUtil();
            Map<String,Object> map = new HashMap<String,Object>();
            map.putAll(tmp);
            map.put("mobile",MapUtils.getString(tmp,"contactsPhone"));
            map.put("contactTel",MapUtils.getString(tmp,"contactsPhone"));
            //if(tmp.containsKey("app")||tmp.containsKey("uid")){
            respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/checkEnableMobile_"+MapUtils.getString(map,"contactsPhone"),map);
            if(tmp.containsKey("app")) {
                tmp.remove("app");
            }
            /*
            } else {
                respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/checkMobile_"+MapUtils.getString(map,"contactsPhone"),map);
            }*/

            /**
             * <p>1. 先检查用户数据中是否有相同的号码</p>
             * <p>2. 如果用户数据中不存在则检查机构表中是否存在相同的手机号</p>
             * <p>3. 如果机构数据中存在则看是否是在未签约状态,并且待签约中相同手机号节点为取消状态或审核不通过状态</p>
             */
            List<Map<String,Object>> listMap = null;
            if(RespStatusEnum.SUCCESS.getCode().equals(respStatus.getCode())){
                RespDataObject<AgencyDto> respDataObject =  httpUtil.getRespDataObject(Constants.LINK_CREDIT,"/credit/customer/agency/v/selectAgencyByMobile",map, AgencyDto.class);
                if(RespStatusEnum.SUCCESS.getCode().equals(respDataObject.getCode())){
                    AgencyDto agencyDto = respDataObject.getData();
                    if(null!=agencyDto
                            &&2==agencyDto.getSignStatus()){
                        RespHelper.setFailRespStatus(respStatus,"已存在相同的手机号码!");
                    } else if(null!=agencyDto
                            &&1==agencyDto.getSignStatus()){
                        String tblDataName = "tbl_sm_list";
                        String whereCondition = "contactsPhone='"+MapUtils.getString(map,"contactsPhone")+"'";
                        map.put("tblDataName",tblDataName);
                        map.put("whereCondition",whereCondition);
                       listMap =  productListBaseService.selectProductListCustomWhereCondition(map);
                    }
                } else {
                    RespHelper.setFailRespStatus(respStatus,respDataObject.getMsg());
                }
            }
            if(RespStatusEnum.SUCCESS.getCode().equals(respStatus.getCode())){
                if(null!=listMap){
                    for (Map<String,Object> m:listMap){
                        String processId = MapUtils.getString(m,"processId");
                        if(StringUtils.isNotBlank(processId)
                                &&(!processId.contains("Cancel")
                                &&!processId.contains("Fail")
                                &&!processId.contains("SignSuccess"))){
                            RespHelper.setFailRespStatus(respStatus,"已存在相同的手机号码!");
                            return respStatus;
                        }
                    }
                }
            }
        } catch (Exception e){
            RespHelper.setFailRespStatus(respStatus,"请求异常,请联系开发部!");
            logger.error("机构提交申请入驻校验联系人手机号异常:",e);
        }
        return respStatus;
    }

    /**
     * 根据机构名查询是否存在相同数据
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/isListByAgencyName")
    public RespStatus selectListByAgencyName(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespStatus respStatus = new RespStatus();
        try {
            String orderNo = MapUtils.getString(map,"orderNo");
            String tblDataName = "tbl_sm_list";
            String whereCondition = "agencyName='"+MapUtils.getString(map,"agencyName")+"'";
            map.put("tblDataName",tblDataName);
            map.put("whereCondition",whereCondition);
            List<Map<String,Object>> listMap =  productListBaseService.selectProductListCustomWhereCondition(map);
            if(null!=listMap){
                String processId = "";
                String tmpOrderNo = "";
                String agencyIds = MapUtils.getString(map,"agencyId","");
                Map<String,Object> agencyMap = null;
                if(StringUtils.isNotBlank(agencyIds)){
                    agencyMap = new HashMap<String,Object>();
                    String[] agencyIdarr = agencyIds.split(",");
                    String[] tmp = null;
                    for(String s:agencyIdarr){
                        tmp = s.split("-");
                        agencyMap.put(tmp[0],tmp[1]);
                    }
                }
                String agencyTmp = "";
                for (Map<String,Object> m:listMap){
                    processId = MapUtils.getString(m,"processId");
                    tmpOrderNo = MapUtils.getString(m,"orderNo");
                    if(null==agencyMap){
                        if(StringUtils.isNotBlank(processId)
                                &&!processId.contains("Cancel")
                                &&!processId.contains("Fail")
                                &&!orderNo.equals(tmpOrderNo)){
                            RespHelper.setFailRespStatus(respStatus,"已存在相同的机构名!");
                            return respStatus;
                        }
                    } else {
                        agencyTmp = MapUtils.getString(m,"agencyId");
                        if(StringUtils.isNotBlank(processId)
                                &&processId.contains("SignSuccess")
                                &&!agencyMap.containsKey(agencyTmp)){
                            RespHelper.setFailRespStatus(respStatus,"已存在相同的机构名!");
                        } else if(StringUtils.isNotBlank(processId)
                                &&processId.contains("SignSuccess")
                                &&agencyMap.containsKey(agencyTmp)
                                &&MapUtils.getString(agencyMap,agencyTmp).equals("3")){
                            continue;
                        } else if(StringUtils.isNotBlank(processId)
                                &&processId.contains("SignSuccess")
                                &&agencyMap.containsKey(agencyTmp)
                                &&!MapUtils.getString(agencyMap,agencyTmp).equals("3")){
                            RespHelper.setFailRespStatus(respStatus,"已存在相同的机构名!");
                        } else if(StringUtils.isNotBlank(processId)
                                &&!processId.contains("Cancel")
                                &&!processId.contains("Fail")
                                &&!orderNo.equals(tmpOrderNo)){
                            RespHelper.setFailRespStatus(respStatus,"已存在相同的机构名!");
                        }
                        if(RespStatusEnum.FAIL.getCode().equals(respStatus.getCode())){
                            return respStatus;
                        }
                    }

                }
            }
            if(StringUtils.isNotBlank(respStatus.getMsg())){
                respStatus.setCode(RespStatusEnum.SUCCESS.getCode());
            } else {
                RespHelper.setSuccessRespStatus(respStatus);
            }
        } catch (Exception e){
            RespHelper.setFailRespStatus(respStatus,RespStatusEnum.FAIL.getMsg());
            logger.error("根据手机号码查询待签约列表异常",e);
        }
        return respStatus;
    }
    @RequestMapping("/v/downloadfile")
    public void  downfile(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String,Object> map){
        InputStream in = null;
        OutputStream out = null;
        try{
            if(StringUtils.isBlank(MapUtils.getString(map,"url"))){
                return;
            }
            URL url = new URL(MapUtils.getString(map,"url"));
            URLConnection conn = url.openConnection();
            in = conn.getInputStream();
            out = response.getOutputStream();
            byte[] body = new byte[1024];
            int index;
            while ((index=in.read(body))!=-1){
                out.write(body,0,index);
            }
            response.setContentType("Content-Disposition=attachment;filename="+ MapUtils.getString(map,"name"));
        } catch (Exception e){
            logger.error("下载文件异常:",e);
        } finally {
            try {
                if (null != in) {
                    in.close();
                    in = null;
                }
                if(null!=out){
                    out.close();
                    out = null;
                }
            } catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }
}
