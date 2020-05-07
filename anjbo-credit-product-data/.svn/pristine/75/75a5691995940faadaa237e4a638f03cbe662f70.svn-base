package com.anjbo.service.sm.impl;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.customer.AgencyProductDto;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.controller.ProductSubmitBaseController;
import com.anjbo.controller.sm.AgencyController;
import com.anjbo.dao.ProductDataBaseMapper;
import com.anjbo.dao.ProductFileBaseMapper;
import com.anjbo.dao.ProductFlowBaseMapper;
import com.anjbo.dao.ProductListBaseMapper;
import com.anjbo.dao.sm.AgencyMapper;
import com.anjbo.service.sm.AgencyService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.google.gson.Gson;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/11/9.
 */
@Service
public class AgencyServiceImpl implements AgencyService {

    @Resource
    private ProductDataBaseMapper productDataBaseMapper;
    @Resource
    private ProductListBaseMapper productListBaseMapper;
    @Resource
    private ProductSubmitBaseController productSubmitBaseController;
    @Resource
    private ProductFlowBaseMapper productFlowBaseMapper;
    @Resource
    private AgencyController agencyController;
    @Resource
    private AgencyMapper agencyMapper;
    @Resource
    private ProductFileBaseMapper productFileBaseMapper;

    protected final Log logger = LogFactory.getLog(this.getClass());

    public static Map<String,Object> processMap;
    public static Map<String,Object> cancelPreProcessMap;
    public static Map<String,Object> restartProcessMap;

    static {
        if(MapUtils.isEmpty(processMap)){
            processMap = new HashMap<String,Object>();
        }
        if(MapUtils.isEmpty(cancelPreProcessMap)){
            cancelPreProcessMap = new HashMap<String,Object>();
        }
        if(MapUtils.isEmpty(restartProcessMap)){
            restartProcessMap = new HashMap<String,Object>();
        }
        /**节点对应上一与下一节点*/
        processMap.put("addAgency","agencyWaitDistribution");
        processMap.put("agencyWaitDistribution","agencyWaitConfirm");
        processMap.put("agencyWaitConfirm","agencyWaitInvestigation");
        processMap.put("agencyWaitInvestigation","agencyWaitToexamine");
        processMap.put("agencyWaitToexamine","agencyWaitSign");
        processMap.put("agencyWaitSign","agencySignSuccess");
        /**取消对应节点*/
        cancelPreProcessMap.put("agencyCancelDistribution","agencyWaitDistribution");
        cancelPreProcessMap.put("agencyCancelConfirm","agencyWaitConfirm");
        cancelPreProcessMap.put("agencyCancelInvestigation","agencyWaitInvestigation");
        cancelPreProcessMap.put("agencyCancelToexamine","agencyWaitToexamine");
        cancelPreProcessMap.put("agencyCancelSign","agencyWaitSign");
        /**审核不通过对应正常节点*/
        restartProcessMap.put("agencyFailToexamine","agencyWaitToexamine");
    }
    /**
     * 分配
     * @param map
     * @return
     */
    @Override
    public RespDataObject<Map<String, Object>> distribution(Map<String, Object> map) {
        RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
        Map<String,Object> dataList = getCurrentListData(map);
        String previousHandlerUid = MapUtils.getString(map,"updateUid");
        UserDto userDto = getUser(previousHandlerUid);
        String previousHandler = null==userDto?"-":userDto.getName();
        String processId = MapUtils.getString(dataList,"processId");
        map.put("nextProcessId","agencyWaitDistribution");
        if("agencyWaitDistribution".equals(processId)){
            if(dataList.containsKey("agencyId")){
                Map<String,Object> agencyParam = new HashMap<String,Object>();
                agencyParam.put("id",MapUtils.getInteger(dataList,"agencyId"));
                agencyParam.put("name",MapUtils.getString(dataList,"agencyName"));
                //agencyParam.put("contactTel",MapUtils.getString(dataList,"contactsPhone"));
                agencyParam.put("contactMan",MapUtils.getString(dataList,"contactsName"));
                //agencyParam.put("accountUid",MapUtils.getString(dataList,"accountUid"));
                //agencyParam.put("indateStart",MapUtils.getString(dataList,"indateStart"));
                //agencyParam.put("indateEnd",MapUtils.getString(dataList,"indateEnd"));
                agencyParam.put("chanlMan",MapUtils.getString(dataList,"channelManagerUid"));
                agencyParam.put("orderNo",MapUtils.getString(dataList,"orderNo"));
                HttpUtil http = new HttpUtil();
                RespStatus respStatus = http.getRespStatus(Constants.LINK_CREDIT,"/credit/customer/agency/v/updateAgencyInfo",agencyParam);
                if(RespStatusEnum.FAIL.getCode().equals(respStatus.getCode())){
                    return RespHelper.setFailDataObject(result,null,respStatus.getMsg());
                }
            }

            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("currentProcessId",processId);
            Map<String,Object> nextProcess = getNextProcessId(map,"nextProcessId");
            tmp.put("state",MapUtils.getString(nextProcess,"state"));
            tmp.put("nextProcessId",MapUtils.getString(nextProcess,"nextProcessId"));
            tmp.put("previousHandlerUid",previousHandlerUid);
            tmp.put("previousHandler",previousHandler);
            tmp.put("currentHandlerUid",MapUtils.getString(dataList,"channelManagerUid"));
            RespHelper.setSuccessDataObject(result,tmp);
            sendMassage(map,"agencyWaitDistribution");
        } else if(isWithdraw(map)){
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被撤回");
        } else {
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被提交");
        }
        return result;
    }

    /**
     * 立项
     * @param map
     * @return
     */
    @Override
    public RespDataObject<Map<String, Object>> confirm(Map<String, Object> map) {
        RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
        Map<String,Object> dataList = getCurrentListData(map);
        String processId = MapUtils.getString(dataList,"processId");
        String previousHandlerUid = MapUtils.getString(map,"updateUid");

        UserDto userDto = getUser(previousHandlerUid);
        String previousHandler = null==userDto?"-":userDto.getName();
        map.put("nextProcessId","agencyWaitConfirm");
        if("agencyWaitConfirm".equals(processId)){
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("currentProcessId",processId);
            Map<String,Object> nextProcess = getNextProcessId(map,"nextProcessId");
            tmp.put("state",MapUtils.getString(nextProcess,"state"));
            tmp.put("nextProcessId",MapUtils.getString(nextProcess,"nextProcessId"));
            tmp.put("previousHandlerUid",previousHandlerUid);
            tmp.put("previousHandler",previousHandler);

            ProductDataDto productDataDto = new ProductDataDto();
            String orderNo = MapUtils.getString(map,"orderNo");
            String tblName = MapUtils.getString(map,"tblName");

            productDataDto.setTblDataName(tblName+"data");
            productDataDto.setTblName("tbl_sm_agencyWaitConfirm");
            productDataDto.setOrderNo(orderNo);
            productDataDto = productDataBaseMapper.selectProductDataBaseDto(productDataDto);
            if(null!=productDataDto&&StringUtils.isNotBlank(productDataDto.getDataStr())){
                Gson gson = new Gson();
                Map<String,Object> data = gson.fromJson(productDataDto.getDataStr(),Map.class);
                String investigationManagerUid = MapUtils.getString(data,"investigationManagerUid");
                String investigationManagerName = MapUtils.getString(data,"investigationManagerName");
                String sql = "investigationManagerUid='"+investigationManagerUid+"'" +
                        ",investigationManagerName='"+investigationManagerName+"'";
                productDataDto.setTblDataName(tblName+"list");
                productDataDto.setKeyValue(sql);
                productListBaseMapper.updateProductListBaseByKey(productDataDto);

                //尽调备注
                String remark = MapUtils.getString(data,"remarks");
                productDataDto.setTblDataName(tblName + "data");
                productDataDto.setTblName("tbl_sm_agencyWaitInvestigation");
                productDataDto = productDataBaseMapper.selectProductDataBaseDto(productDataDto);
                if (null != productDataDto && StringUtils.isNotBlank(productDataDto.getDataStr())) {
                    data = gson.fromJson(productDataDto.getDataStr(), Map.class);
                    data.put("remarks", remark);
                    productDataDto.setTblDataName(tblName + "data");
                    productDataDto.setTblName("tbl_sm_agencyWaitInvestigation");
                    productDataDto.setDataStr(gson.toJson(data));
                    productDataBaseMapper.updateProductDataBase(productDataDto);
                } else {
                    String dt = "{\"remarks\":\"" + remark + "\"}";
                    insertTblData(map,"tbl_sm_agencyWaitInvestigation",dt);
                }

                productDataDto = new ProductDataDto();
                productDataDto.setOrderNo(orderNo);
                productDataDto.setTblDataName(tblName+"data");
                productDataDto.setTblName("tbl_sm_agencyWaitDistribution");
                productDataDto = productDataBaseMapper.selectProductDataBaseDto(productDataDto);
                if(null!=productDataDto&&StringUtils.isNotBlank(productDataDto.getDataStr())){
                    data = gson.fromJson(productDataDto.getDataStr(),Map.class);
                    data.put("investigationManagerUid",investigationManagerUid);
                    data.put("investigationManagerName",investigationManagerName);
                    String dataStr = gson.toJson(data);
                    productDataDto.setDataStr(dataStr);
                    productDataDto.setUpdateUid(MapUtils.getString(map,"updateUid"));
                    productDataDto.setTblDataName(tblName+"data");
                    productDataDto.setTblName("tbl_sm_agencyWaitDistribution");
                    productDataBaseMapper.updateProductDataBase(productDataDto);
                }
            }
            sendMassage(map,"agencyWaitConfirm");
            RespHelper.setSuccessDataObject(result,tmp);
        } else if(isWithdraw(map)){
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被撤回");
        } else {
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被提交");
        }
        return result;
    }

    /**
     * 尽调
     *
     * @param map
     * @return
     */
    @Override
    public RespDataObject<Map<String, Object>> investigation(Map<String, Object> map) {
        RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
        Map<String,Object> dataList = getCurrentListData(map);
        String previousHandlerUid = MapUtils.getString(map,"updateUid");

        UserDto userDto = getUser(previousHandlerUid);
        String previousHandler = null==userDto?"-":userDto.getName();
        String processId = MapUtils.getString(dataList,"processId");
        map.put("nextProcessId","agencyWaitInvestigation");
        if("agencyWaitInvestigation".equals(processId)){
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("currentProcessId",processId);
            Map<String,Object> nextProcess = getNextProcessId(map,"nextProcessId");
            tmp.put("state",MapUtils.getString(nextProcess,"state"));
            tmp.put("nextProcessId",MapUtils.getString(nextProcess,"nextProcessId"));
            tmp.put("previousHandlerUid",previousHandlerUid);
            tmp.put("previousHandler",previousHandler);

            RespHelper.setSuccessDataObject(result,tmp);
        } else if(isWithdraw(map)){
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被撤回");
        } else {
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被提交");
        }
        return result;
    }

    /**
     * 审核
     * @param map
     * @return
     */
    @Override
    public RespDataObject<Map<String, Object>> toexamine(Map<String, Object> map) {
        RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
        Map<String,Object> dataList = getCurrentListData(map);
        String previousHandlerUid = MapUtils.getString(map,"updateUid");

        UserDto userDto = getUser(previousHandlerUid);
        String previousHandler = null==userDto?"-":userDto.getName();

        String processId = MapUtils.getString(dataList,"processId");
        map.put("nextProcessId","agencyWaitToexamine");
        if("agencyWaitToexamine".equals(processId)){
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("currentProcessId",processId);
            Map<String,Object> nextProcess = getNextProcessId(map,"nextProcessId");
            tmp.put("state",MapUtils.getString(nextProcess,"state"));
            tmp.put("nextProcessId",MapUtils.getString(nextProcess,"nextProcessId"));
            tmp.put("previousHandlerUid",previousHandlerUid);
            tmp.put("previousHandler",previousHandler);

            Map<String,Object> param  = new HashMap<String,Object>();
            String orderNo = MapUtils.getString(map,"orderNo");
            String tblName = MapUtils.getString(map,"tblName");
            param.put("orderNo",orderNo);
            param.put("tblName","tbl_sm_agencyWaitToexamine");
            param = getAgencyTblData(param);

            if(MapUtils.isNotEmpty(param)&&"2".equals(MapUtils.getString(param,"reviewResult"))){
                //评审概述(产品定义为审核不通过原因)
                String reviewSummary = MapUtils.getString(param,"reviewSummary");
                String currentProcessId = "agencyFailToexamine";
                try {
                    copyFile(orderNo,processId,currentProcessId,tblName,MapUtils.getString(map,"updateUid"));
                } catch (Exception e){
                    RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
                    logger.error("审核不通过复制附件信息异常:",e);
                    return result;
                }

                map.put("currentProcessId",currentProcessId);
                String state = getState(map,"currentProcessId");
                map.remove("currentProcessId");
                Gson gson = new Gson();
                insertTblData(map,"tbl_sm_agencyFailToexamine",gson.toJson(param));
                param.put("tblName",tblName);
                param.put("orderNo",orderNo);
                Map<String,Object> list = getCurrentListData(param);
                restartOrCancel(list,map,tblName,state,currentProcessId,processId,false,false,true,false);

                /**通知快鸽APP机构审核不通过*/
                param = new HashMap<String,Object>();
                param.put("agencyId",MapUtils.getInteger(list,"agencyId"));
                param.put("agencyName",MapUtils.getString(list,"agencyName"));
                param.put("agencyStatus",2);
                param.put("remark",reviewSummary);
                HttpUtil httpUtil = new HttpUtil();
                logger.info("机构审核不通过通知快鸽APP端信息:"+param.toString());
                RespStatus r = httpUtil.getRespStatus(Constants.LINK_ANJBO_APP_URL,"/mortgage/agency/updateAgencyNotify",param);
                logger.info("更新快鸽APP用户机构状态==="+r+"====");
                r = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/v/unbind4Agency_"+MapUtils.getInteger(list,"agencyId"),null);
                logger.info("更新用户机构状态==="+r+"===");
                RespHelper.setFailDataObject(result,tmp,"审核不通过");
                return result;
            }
            RespHelper.setSuccessDataObject(result,tmp);
        } else if(isWithdraw(map)){
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被撤回");
        } else {
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被提交");
        }
        return result;
    }

    /**
     * 签约
     * @param map
     * @return
     */
    @Override
    public RespDataObject<Map<String, Object>> sign(Map<String, Object> map) {
        RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
        Map<String,Object> dataList = getCurrentListData(map);
        String processId = MapUtils.getString(dataList,"processId");
        map.put("nextProcessId","agencyWaitSign");
        if("agencyWaitSign".equals(processId)){
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("currentProcessId",processId);
            Map<String,Object> nextProcess = getNextProcessId(map,"nextProcessId");
            tmp.put("state",MapUtils.getString(nextProcess,"state"));
            tmp.put("nextProcessId",MapUtils.getString(nextProcess,"nextProcessId"));

            ProductDataDto productDataDto = new ProductDataDto();
            String orderNo = MapUtils.getString(map,"orderNo");
            String tblName = MapUtils.getString(map,"tblName");

            /******新增一条数据到机构表与机构费用支付方式表中***/
            productDataDto.setTblDataName(tblName+"data");
            productDataDto.setTblName("tbl_sm_agencyWaitDistribution");
            productDataDto.setOrderNo(orderNo);
            productDataDto = productDataBaseMapper.selectProductDataBaseDto(productDataDto);
            Map<String,Object> data = null;
            if(null!=productDataDto&&StringUtils.isNotBlank(productDataDto.getDataStr())){
                Gson gson = new Gson();
                data = gson.fromJson(productDataDto.getDataStr(),Map.class);
            }
            productDataDto = new ProductDataDto();
            productDataDto.setOrderNo(orderNo);
            productDataDto.setTblDataName(tblName+"data");
            productDataDto.setTblName("tbl_sm_agencyWaitSign");
            productDataDto = productDataBaseMapper.selectProductDataBaseDto(productDataDto);
            if(null!=productDataDto&&StringUtils.isNotBlank(productDataDto.getDataStr())){
                Gson gson = new Gson();
                Map<String,Object> signData = gson.fromJson(productDataDto.getDataStr(),Map.class);
                if(MapUtils.isNotEmpty(data)){
                    data.putAll(signData);
                } else {
                    data = signData;
                }
            }

            Map<String,Object> tmpMap = getCurrentListData(map);
            String previousHandlerUid = MapUtils.getString(map,"updateUid");

            UserDto userDto = getUser(previousHandlerUid);
            String previousHandler = null==userDto?"-":userDto.getName();
            tmp.put("previousHandlerUid",previousHandlerUid);
            tmp.put("previousHandler",previousHandler);
            String accountUid = "";
            Integer agencyId = null;
            if(MapUtils.isNotEmpty(tmpMap)){
                accountUid = MapUtils.getString(tmpMap,"accountUid");
                agencyId = MapUtils.getInteger(tmpMap,"agencyId",null);
            }
            if(null==agencyId){
                RespHelper.setFailDataObject(result,null,"签约失败,缺少机构数据主键!");
                return result;
            }

            String openCity = "";
            String applyProduct = "";
            String applyProductName = "";
            String applyCity = "";
            String applyCityName = "";

            if(MapUtils.isNotEmpty(data)&&data.containsKey("productList")){
                List<Map<String,Object>> list = (List<Map<String,Object>>)data.get("productList");

                List<AgencyProductDto> tmpList = new ArrayList<AgencyProductDto>();
                AgencyProductDto productDto = null;
                List<DictDto> productList = agencyController.getDictDtoByType("product");
                for (Map<String,Object> en:list){
                    String[] codes = MapUtils.getString(en,"finalApplyProductCode").split(",");

                    for (DictDto d:productList){
                        for (String c:codes){
                            if(d.getCode().equals(c)){
                                productDto = new AgencyProductDto();
                                productDto.setProductCode(d.getCode());
                                productDto.setProductName(d.getName());
                                productDto.setCityCode(MapUtils.getString(en,"finalApplyCity"));
                                productDto.setCityName(MapUtils.getString(en,"finalApplyCityName"));
                                productDto.setAgencyId(agencyId);
                                productDto.setCreateUid(MapUtils.getString(map,"createUid"));
                                tmpList.add(productDto);
                            }
                        }
                    }

                    if(StringUtils.isBlank(openCity)){
                        openCity = productDto.getCityName();
                    } else if(!openCity.contains(productDto.getCityName())){
                        openCity += ","+productDto.getCityName();
                    }
                    if(StringUtils.isBlank(applyProduct)){
                        applyProduct = productDto.getProductCode();
                        applyProductName = productDto.getProductName();
                    } else if(!applyProduct.contains(productDto.getProductCode())){
                        applyProduct += ","+productDto.getProductCode();
                        applyProductName += ","+productDto.getProductName();
                    }
                    if(StringUtils.isBlank(applyCity)){
                        applyCity = productDto.getCityCode();
                        applyCityName = productDto.getCityName();
                    } else if(!applyCity.contains(productDto.getCityCode())){
                        applyCity += ","+productDto.getCityCode();
                        applyCityName += ","+productDto.getCityName();
                    }
                }
                data.put("productList",tmpList);
                data.put("openCity",openCity);
            }

            RespDataObject<AgencyDto> reTmp = null;
            int agencyCode = -1;

            HttpUtil http = new HttpUtil();
            if(MapUtils.isNotEmpty(data)){
                data.put("accountUid",accountUid);
                data.put("agency_id",agencyId);
                data.put("expandChiefUid",MapUtils.getString(dataList,"expandChiefUid"));
                reTmp = http.getRespDataObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/insertAgency", data, AgencyDto.class);
            }
            if(null!=reTmp
                    &&RespStatusEnum.SUCCESS.getCode().equals(reTmp.getCode())){
                AgencyDto agencyDto = reTmp.getData();
                agencyCode = agencyDto.getAgencyCode();
                accountUid = agencyDto.getAccountUid();
            } else {
                RespHelper.setFailDataObject(result,map,reTmp.getMsg());
                return result;
            }
            /******新增一条数据到机构表与机构费用支付方式表中***/

            /****************更新机构码与产品,城市******************/
            String updateSql = "agencyCode='"+agencyCode+"'";
            if(StringUtils.isNotBlank(applyProduct)){
                updateSql += ",applyProduct='"+applyProduct+"'";
            }
            if(StringUtils.isNotBlank(applyProductName)){
                updateSql += ",applyProductName='"+applyProductName+"'";
            }
            if(StringUtils.isNotBlank(applyCity)){
                updateSql += ",applyCity='"+applyCity+"'";
            }
            if(StringUtils.isNotBlank(applyCityName)){
                updateSql += ",applyCityName='"+applyCityName+"'";
            }
            if(StringUtils.isNotBlank(accountUid)){
                updateSql += ",accountUid='"+accountUid+"'";
            }
            productDataDto.setTblDataName(tblName+"list");
            productDataDto.setKeyValue(updateSql);
            productListBaseMapper.updateProductListBaseByKey(productDataDto);
            /****************更新机构码******************/
            RespHelper.setSuccessDataObject(result,tmp);
            sendMassage(map,"agencyWaitSign");
        } else if(isWithdraw(map)){
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被撤回");
        } else {
            RespHelper.setFailDataObject(result,null,"抱歉,该数据已被提交");
        }
        return result;
    }

    /**
     * 新增机构
     * @param map
     * @return
     */
    public RespDataObject<Map<String,Object>> addAgency(Map<String,Object> map){
        RespDataObject<Map<String, Object>> dataObject = new RespDataObject<Map<String, Object>>();
        Map<String,Object> datamap = new HashMap<String,Object>();
        ProductDataDto productDataDto = new ProductDataDto();
        String orderNo = MapUtils.getString(map, "orderNo");
        String tblName = MapUtils.getString(map, "tblName");
        productDataDto.setOrderNo(orderNo);
        HttpUtil http = new HttpUtil();
        Map<String, Object> dataList = Collections.EMPTY_MAP;
        try {
            //新增一条数据到到机构表中
            productDataDto.setTblDataName(tblName + "data");
            productDataDto.setTblName("tbl_sm_agencyWaitDistribution");
            productDataDto = productDataBaseMapper.selectProductDataBaseDto(productDataDto);
            Map<String, Object> data = null;
            dataList = getCurrentListData(map);
            String dataStr = "";
            if (null != productDataDto && StringUtils.isNotBlank(productDataDto.getDataStr())) {
                Gson gson = new Gson();
                data = gson.fromJson(productDataDto.getDataStr(), Map.class);
                try {
                    String dstr = MapUtils.getString(dataList, "createTime");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date d = format.parse(dstr);
                    dstr = format.format(d);
                    data.put("applyDate", dstr);
                } catch (Exception e) {
                    logger.error("新增机构格式化申请时间异常:", e);
                }
                dataStr = gson.toJson(data);
            }

            String channelManagerUid = "";
            if (null != data) {
                channelManagerUid = MapUtils.getString(data,"channelManagerUid");
                data.put("orderNo",orderNo);
                RespDataObject<AgencyDto> reTmp = http.getRespDataObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/insertAgency", data, AgencyDto.class);
                if (RespStatusEnum.SUCCESS.getCode().equals(reTmp.getCode())) {
                    AgencyDto agencyDto = reTmp.getData();
                    datamap.put("agencyId", agencyDto.getId());
                    data.put("agencyId", agencyDto.getId());
                    if(StringUtils.isNotBlank(agencyDto.getAccountUid())){
                        datamap.put("accountUid", agencyDto.getAccountUid());
                    }
                } else {
                    Map<String, Object> de = new HashMap<String, Object>();
                    de.put("orderNo", productDataDto.getOrderNo());
                    de.put("tblDataName", tblName + "list");
                    productListBaseMapper.delete(de);
                    de.put("tblDataName", tblName + "data");
                    productDataBaseMapper.delete(de);
                    RespHelper.setFailDataObject(dataObject, null, reTmp.getMsg());
                    return dataObject;
                }
                productDataDto.setDataStr(dataStr);
                productDataDto.setTblDataName(tblName + "data");
                productDataBaseMapper.updateProductDataBase(productDataDto);
            }

            if(map.containsKey("app")){
                datamap.put("currentProcessId","addAgency");
                datamap.put("nextProcessId","agencyWaitDistribution");
                datamap.put("state","待分配");
                String currentHandlerUid = ConfigUtil.getStringValue(Constants.BASE_AGENCY_APPLY_DEFAULT_UID,ConfigUtil.CONFIG_BASE);
                logger.info("获取系统默认分配人uid:"+currentHandlerUid);
                if(StringUtils.isNotBlank(currentHandlerUid)){
                    datamap.put("currentHandlerUid",currentHandlerUid);
                    datamap.put("expandChiefUid",currentHandlerUid);
                    UserDto userDto = getUser(currentHandlerUid);
                    if(null!=userDto){
                        datamap.put("userName",userDto.getName());
                        logger.info("APP申请机构入驻分配默认分配人:"+userDto.getName()+"_"+userDto.getUid());
                    }
                }
                data.put("tblName",tblName);
                map.remove("app");
                sendMassage(data,"addAgency");
            } else {
                datamap.put("currentProcessId","agencyWaitDistribution");
                datamap.put("nextProcessId","agencyWaitConfirm");
                datamap.put("state","待立项");
                data.put("tblName",tblName);
                String previousHandlerUid = MapUtils.getString(map,"updateUid");
                UserDto userDto = getUser(previousHandlerUid);
                String previousHandler = null==userDto?"-":userDto.getName();
                datamap.put("previousHandlerUid",previousHandlerUid);
                datamap.put("currentHandlerUid",channelManagerUid);
                datamap.put("previousHandler",previousHandler);
                dataObject.setMsg("");
                sendAddAgencyToApp(data,http,dataObject);
                sendMassage(data,"agencyWaitDistribution");
            }
        } catch (Exception e){
            if(StringUtils.isBlank(dataObject.getMsg())){
                dataObject.setMsg(RespStatusEnum.FAIL.getMsg());
            }
            RespHelper.setFailDataObject(dataObject,null,dataObject.getMsg());
            logger.error("机构申请入驻异常:",e);
            logger.info("===============数据开始回滚===============");
            Map<String, Object> de = new HashMap<String, Object>();
            de.put("orderNo", orderNo);
            de.put("tblDataName", tblName + "list");
            productListBaseMapper.delete(de);
            de.put("tblDataName",tblName+"flow");
            productFlowBaseMapper.deleteLastFlow(de);
            if(datamap.containsKey("agencyId")){
                http.getRespStatus(Constants.LINK_CREDIT,"/credit/customer/agency/v/delete",datamap);
            } else {
                logger.info("机构表数据回滚失败:机构名称为:"+MapUtils.getString(dataList,"agencyName")+"原因是没有获取到返回的agencyId");
            }
            logger.info("===============数据回滚结束===============");
            return dataObject;
        }
        RespHelper.setSuccessDataObject(dataObject,datamap);
        return dataObject;
    }

    public void sendAddAgencyToApp(Map<String,Object> map,HttpUtil http,RespDataObject dataObject)throws Exception{
        Map<String,Object> param = new HashMap<String,Object>();
        param.putAll(map);
        param.put("agencyStatus",-1);
        param.put("agencyCode",0);
        logger.info("PC端新增机构通知快鸽APP_Param:"+param.toString());
        RespStatus respStatus = http.getRespStatus(Constants.LINK_ANJBO_APP_URL,"/mortgage/agency/addAgencyNotify",param);
        logger.info("PC端新增机构通知快鸽APP返回结果："+respStatus);
        if(RespStatusEnum.FAIL.getCode().equals(respStatus.getCode())){
            dataObject.setMsg(respStatus.getMsg());
            throw new Exception(respStatus.getMsg());
        }
    }
    /**
     * 获取当前列表数据
     * @param map
     * @return
     */
    public Map<String,Object> getCurrentListData(Map<String,Object> map){
        ProductDataDto productDataDto = new ProductDataDto();
        String orderNo = MapUtils.getString(map,"orderNo");
        String tblName = MapUtils.getString(map,"tblName");
        productDataDto.setTblDataName(tblName+"list");
        productDataDto.setOrderNo(orderNo);
        Map<String,Object> lmap = productListBaseMapper.selectProductListBaseByOrderNo(productDataDto);
        return lmap;
    }

    /**
     * 获取下一节点
     * @param map(key=processId:当前节点,key=cityCode:城市code,key=productCode:产品)
     * @param key(有则根据传入的key名称获取Map数据)
     * @return key=nextProcessId(下一节点),key=state(下一节点名称)
     */
    public Map<String,Object> getNextProcessId(Map<String,Object> map,String key){
        String processId = "";
        if(StringUtils.isBlank(key)){
            processId = MapUtils.getString(map,"processId");
        } else {
            processId = MapUtils.getString(map,key);
        }
        Map<String,Object> nextMap = new HashMap<String,Object>();
        String nextProcessId = MapUtils.getString(processMap,processId);
        map.put("nextProcessId",nextProcessId);
        String state = getState(map,"nextProcessId");
        nextMap.put("state",state);
        nextMap.put("nextProcessId",nextProcessId);
        map.remove("nextProcessId");
        return nextMap;
    }

    /**
     * 获取上一节点
     * @param map(key=processId:当前节点,key=cityCode:城市code,key=productCode:产品)
     * @param key(有则根据传入的key名称获取Map数据)
     * @return key=preProcessId(上一节点),key=state(上一节点名称)
     */
    public Map<String,Object> getPreProcessId(Map<String,Object> map,String key){

        String processId = "";
        if(StringUtils.isBlank(key)) {
            processId = MapUtils.getString(map, "processId");
        } else {
            processId = MapUtils.getString(map, key);
        }
        String preProcessId = "";
        Map<String,Object> preMap = new HashMap<String,Object>();
        Set<Map.Entry<String, Object>> entries = processMap.entrySet();
        for (Map.Entry<String,Object> e:entries){
            if(e.getValue().equals(processId)){
                preProcessId = e.getKey();
                break;
            }
        }
        map.put("preProcessId",preProcessId);
        String state = getState(map,"preProcessId");
        preMap.put("preProcessId",preProcessId);
        preMap.put("state",state);
        map.remove("preProcessId");
        return preMap;
    }

    /**
     * 判断是否被撤回
     * @param map
     * @return
     */
    public boolean isWithdraw(Map<String,Object> map){

        ProductDataDto productDataDto = new ProductDataDto();
        String orderNo = MapUtils.getString(map,"orderNo");
        String tblName = MapUtils.getString(map,"tblName");
        productDataDto.setTblDataName(tblName+"list");
        productDataDto.setOrderNo(orderNo);
        Map<String,Object> lmap = productListBaseMapper.selectProductListBaseByOrderNo(productDataDto);
        List<ProductProcessDto> list =  getAllProcessId(map);
        String currentProcessId = MapUtils.getString(lmap,"processId");
        int currentListSort = 0;
        int currentSort = 0;
        String processId = MapUtils.getString(map,"processId");
        for (ProductProcessDto p:list){
            if(p.getProcessId().equals(currentProcessId)){
                currentListSort = p.getSort();
            }
            if(p.getProcessId().equals(processId)){
                currentSort = p.getSort();
            }
        }
        if(currentSort>currentListSort){
            return true;
        }
        return false;
    }

    /**
     * 取消
     * @param map
     * @return
     */
    @Override
    public RespStatus cancel(Map<String, Object> map) {
        RespStatus respStatus = new RespStatus();
        String processId = MapUtils.getString(map,"processId");
        ProductDataDto productDataDto = new ProductDataDto();
        String orderNo = MapUtils.getString(map,"orderNo");
        String tblName = MapUtils.getString(map,"tblName");

        /******************更新列表********************/
        productDataDto.setTblDataName(tblName+"list");
        productDataDto.setOrderNo(orderNo);
        Map<String,Object> tmpMap = productListBaseMapper.selectProductListBaseByOrderNo(productDataDto);
        String previousHandlerUid = MapUtils.getString(tmpMap,"currentHandlerUid");
        UserDto userDto = getUser(previousHandlerUid);
        String previousHandler = null==userDto?"-":userDto.getName();
        map.put("previousHandlerUid",previousHandlerUid);
        map.put("previousHandler",previousHandler);
        if(MapUtils.isNotEmpty(tmpMap)
                &&processId.equals(MapUtils.getString(tmpMap,"processId"))){
            String preProcessId = MapUtils.getString(tmpMap,"processId");
            Map<String,Object> tmp  = subProcess(tmpMap,"Cancel","Wait","取消","待");
            String state = MapUtils.getString(tmp,"state");
            String currentProcessId = MapUtils.getString(tmp,"processId");
            Map<String,Object> param = new HashMap<String,Object>();
            String applyDate = MapUtils.getString(tmpMap,"createTime");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            param.put("agencyName",MapUtils.getString(tmpMap,"agencyName"));
            param.put("state",state);
            param.put("applyCityName",MapUtils.getString(tmpMap,"applyCityName"));
            param.put("applyProductName",MapUtils.getString(tmpMap,"applyProductName"));
            param.put("contactsName",MapUtils.getString(tmpMap,"contactsName"));
            param.put("contactsPhone",MapUtils.getString(tmpMap,"contactsPhone"));
            try {
                if(StringUtils.isNotBlank(applyDate)) {
                    Date d = format.parse(applyDate);
                    param.put("applyDate",format.format(d));
                }
            } catch (Exception e){}
            param.put("cancelDate",new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            param.put("channelManagerName",MapUtils.getString(tmpMap,"channelManagerName"));
            param.put("investigationManagerName",MapUtils.getString(tmpMap,"investigationManagerName"));
            param.put("cancelReason",MapUtils.getString(map,"backReason"));
            Gson gson = new Gson();
            String dataStr = gson.toJson(param);
            productDataDto.setOrderNo(MapUtils.getString(map,"orderNo"));
            productDataDto.setTblDataName(tblName+"data");
            productDataDto.setTblName(tblName+currentProcessId);
            productDataDto = productDataBaseMapper.selectProductDataBaseDto(productDataDto);
            if(null==productDataDto) {
                productDataDto = new ProductDataDto();
                productDataDto.setCreateUid(MapUtils.getString(map,"createUid"));
                productDataDto.setOrderNo(MapUtils.getString(map,"orderNo"));
                productDataDto.setTblDataName(tblName+"data");
                productDataDto.setTblName(tblName+currentProcessId);
                productDataDto.setDataStr(dataStr);
                productDataBaseMapper.insertProductDataBase(productDataDto);
            } else {
                productDataDto.setTblDataName(tblName+"data");
                productDataDto.setDataStr(dataStr);
                productDataDto.setUpdateUid(MapUtils.getString(map,"updateUid"));
                productDataBaseMapper.updateProductDataBase(productDataDto);
            }

            restartOrCancel(tmpMap,map,tblName,state,currentProcessId,preProcessId,true,false,false,false);
            /**通知快鸽APP机构审核不通过*/
            Map<String,Object> list = getCurrentListData(map);
            param = new HashMap<String,Object>();
            param.put("agencyId",MapUtils.getInteger(list,"agencyId"));
            param.put("agencyName",MapUtils.getString(list,"agencyName"));
            param.put("agencyStatus",2);
            param.put("remark",MapUtils.getString(map,"backReason"));
            logger.info("同步快鸽APP信息:"+param.toString());
            HttpUtil httpUtil = new HttpUtil();
            RespStatus r = httpUtil.getRespStatus(Constants.LINK_ANJBO_APP_URL,"/mortgage/agency/updateAgencyNotify",param);
            logger.info("取消更新快鸽APP用户机构状态==="+r+"====");
            r = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/v/unbind4Agency_"+MapUtils.getInteger(list,"agencyId"),null);
            logger.info("同步用户更新机构状态:==="+r+"===");
            RespHelper.setFailRespStatus(respStatus,r.getMsg());
            if(null!=productDataDto&&StringUtils.isNotBlank(productDataDto.getDataStr())){
                Map<String,Object> d = gson.fromJson(productDataDto.getDataStr(),Map.class);
                param.putAll(d);
                param.putAll(tmpMap);
            }
            param.put("tblName",tblName);
            sendMassage(param,currentProcessId);
        } else if(MapUtils.isNotEmpty(tmpMap)){
            RespHelper.setFailRespStatus(respStatus, "已被操作不能取消!");
        } else {
            RespHelper.setFailRespStatus(respStatus, "没有查询到数据!");
        }
        /******************更新列表********************/
        return respStatus;
    }

    /**
     * 更新列表与流水节点数据
     * @param tmpMap 要更新的列表数据
     * @param map
     * @param tblName
     * @param state 节点名
     * @param processId 当前节点(作为流水的下一节点)
     * @param preProcessId 上一节点(主要用于流水作为当前节点)
     * @param isCancel 是否是取消方法调用
     * @param isConfirmWithdraw 是否是待立项节点调用
     * @param isFailToexamine 是否是审核不通过节点调用
     * @param isWithdraw 是否是撤回方法调用
     */
    public void restartOrCancel(Map<String,Object> tmpMap,
                                Map<String,Object> map,
                                String tblName,
                                String state,
                                String processId,
                                String preProcessId,
                                boolean isCancel,
                                boolean isConfirmWithdraw,
                                boolean isFailToexamine,
                                boolean isWithdraw){
        tmpMap.put("state", state);
        tmpMap.put("processId", processId);
        tmpMap.put("previousHandleTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tmpMap.put("tblDataName", tblName+"list");
        tmpMap.put("previousHandlerUid", map.get("previousHandlerUid"));
        tmpMap.put("previousHandler", map.get("previousHandler"));
        String currentHandlerUid = map.get("currentHandlerUid")!=null?MapUtils.getString(map, "currentHandlerUid"):map.get("updateUid").toString();
        UserDto user = getUser(currentHandlerUid);
        tmpMap.put("currentHandlerUid", currentHandlerUid);
        tmpMap.put("currentHandler", null==user?"-":user.getName());
        productListBaseMapper.updateProductListBase(tmpMap);

        if("agencyWaitDistribution".equals(processId)){
            ProductDataDto p = new ProductDataDto();
            p.setTblDataName(MapUtils.getString(tmpMap,"tblDataName"));
            p.setOrderNo(MapUtils.getString(tmpMap,"orderNo"));
            String keyVal = "channelManagerUid='',channelManagerName='',investigationManagerUid='',investigationManagerName=''";
            p.setKeyValue(keyVal);
            productListBaseMapper.updateProductListBaseByKey(p);
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("tblDataName",tblName+"data");
            tmp.put("orderNo",p.getOrderNo());
            tmp.put("tblName",tblName+processId);
            tmp = getAgencyTblData(tmp);
            if(MapUtils.isNotEmpty(tmp)){
                tmp.put("channelManagerUid","");
                tmp.put("channelManagerName","");
                tmp.put("investigationManagerUid","");
                tmp.put("investigationManagerName","");
                p.setTblDataName(tblName+"data");
                p.setTblName(tblName+processId);
                Gson gson = new Gson();
                String data = gson.toJson(tmp);
                p.setDataStr(data);
                productDataBaseMapper.updateProductDataBase(p);
            }
        } else if("agencyWaitConfirm".equals(processId)){
            ProductDataDto p = new ProductDataDto();
            p.setTblDataName(MapUtils.getString(tmpMap,"tblDataName"));
            p.setOrderNo(MapUtils.getString(tmpMap,"orderNo"));
            String keyVal = "investigationManagerUid='',investigationManagerName=''";
            p.setKeyValue(keyVal);
            productListBaseMapper.updateProductListBaseByKey(p);
            Map<String,Object> tmp = new HashMap<String,Object>();
            tmp.put("tblDataName",tblName+"data");
            tmp.put("orderNo",p.getOrderNo());
            tmp.put("tblName",tblName+"agencyWaitDistribution");
            tmp = getAgencyTblData(tmp);
            if(MapUtils.isNotEmpty(tmp)){
                tmp.put("investigationManagerUid","");
                tmp.put("investigationManagerName","");
                p.setTblDataName(tblName+"data");
                p.setTblName(tblName+"agencyWaitDistribution");
                Gson gson = new Gson();
                String data = gson.toJson(tmp);
                p.setDataStr(data);
                productDataBaseMapper.updateProductDataBase(p);
            }

        }
        if("agencyWaitDistribution".equals(processId)){
            //撤回到最初的节点删除所有流水
            tmpMap.put("tblDataName", tblName+"flow");
            productFlowBaseMapper.deleteAllFlow(tmpMap);

            tmpMap.put("createUid", map.get("createUid"));
            tmpMap.put("nextProcessId","agencyWaitDistribution");
            tmpMap.put("handleUid", map.get("updateUid"));
            tmpMap.put("currentProcessId","addAgency");
            productFlowBaseMapper.insertProductFlowBase(tmpMap);
            return;
        } else if(isConfirmWithdraw){
            //删除最后的一条流水
            tmpMap.put("tblDataName", tblName+"flow");
            productFlowBaseMapper.deleteLastFlow(tmpMap);
            return;
        } else if(isCancel||isFailToexamine){
            /****************更新流程表********************/
            tmpMap.put("tblDataName", tblName+"flow");
            tmpMap.put("createUid", map.get("createUid"));
            tmpMap.put("nextProcessId", preProcessId);
            tmpMap.put("handleUid", map.get("updateUid"));
            tmpMap.put("currentProcessId", processId);
            productFlowBaseMapper.insertProductFlowBase(tmpMap);
            /****************更新流程表********************/
            return;
        } else if(isWithdraw){
            //删除流水
            tmpMap.put("tblDataName", tblName+"flow");
            productFlowBaseMapper.deleteLastFlow(tmpMap);
            return;
        }
        /****************更新流程表********************/
        tmpMap.put("tblDataName", tblName+"flow");
        tmpMap.put("createUid", map.get("createUid"));
        tmpMap.put("nextProcessId", processId);
        tmpMap.put("backReason", MapUtils.getString(map,"backReason",""));
        tmpMap.put("handleUid", map.get("updateUid"));
        tmpMap.put("currentProcessId", preProcessId);
        productFlowBaseMapper.insertProductFlowBase(tmpMap);
        /****************更新流程表********************/
    }
    /**
     * 撤回
     * @param map
     * @return
     */
    @Override
    public RespStatus withdraw(Map<String, Object> map) {
        String processId = MapUtils.getString(map,"processId") ;
        RespStatus respStatus = new RespStatus();
        RespHelper.setSuccessRespStatus(respStatus);
        String tblName = MapUtils.getString(map,"tblName");
        Map<String,Object> tmpMap = getCurrentListData(map);

        if(MapUtils.isEmpty(tmpMap)){
            RespHelper.setFailRespStatus(respStatus, "没有查询到数据!");
            return respStatus;
        }
        String nextProcessId = MapUtils.getString(tmpMap,"processId");
        if(!nextProcessId.equals(processId)) {
            RespHelper.setFailRespStatus(respStatus, "已被操作不能撤回!");
            return respStatus;
        } else if("agencyWaitDistribution".equals(nextProcessId)){
            RespHelper.setFailRespStatus(respStatus, "待分配节点不能撤回!");
            return respStatus;
        } else if(nextProcessId.indexOf("Cancel")>-1){
            RespHelper.setFailRespStatus(respStatus, "取消状态不能撤回!");
            return respStatus;
        }
        if("agencyWaitConfirm".equals(nextProcessId)){
            String currentHandlerUid = ConfigUtil.getStringValue(Constants.BASE_AGENCY_APPLY_DEFAULT_UID,ConfigUtil.CONFIG_BASE);
            logger.info("获取系统默认分配人uid:"+currentHandlerUid);
            map.put("currentHandlerUid",currentHandlerUid);
            map.put("previousHandlerUid","-");
            map.put("previousHandler","-");
            restartOrCancel(tmpMap,map,tblName,"待分配","agencyWaitDistribution",nextProcessId,false,true,false,true);
            return respStatus;
        }
        /**
         * <p>1.获取当前节点的上一节点作为当前节点与流水的下一节点</p>
         * <p>2.获取上一节点的上一节点作为流水的当前节点</p>
         */
        Map<String,Object> preProcessMap = getPreProcessId(map,"");
        String state = MapUtils.getString(preProcessMap,"state");
        String currentProcessId = MapUtils.getString(preProcessMap,"preProcessId");
        map.put("preProcessId",currentProcessId);
        preProcessMap = getPreProcessId(map,"preProcessId");
        String preProcessId = MapUtils.getString(preProcessMap,"preProcessId");
        map.remove("preProcessId");
        /**
         * <p>1.获取流水的下一节点是当前节点的流水</p>
         * <p>2.再根据查询到流水的当前节点作为下一节点的流水的处理人就是当前节点撤回的上一节点处理人</p>
         */
        String currentHandlerUid = MapUtils.getString(tmpMap,"previousHandlerUid");
        map.put("nextProcessId",nextProcessId);
        map.put("tblDataName",tblName+"flow");
        Map<String,Object> flow = productFlowBaseMapper.selectProductFlowByNextProcessId(map);
        map.put("nextProcessId",MapUtils.getString(flow,"currentProcessId"));
        flow = productFlowBaseMapper.selectProductFlowByNextProcessId(map);
        map.put("currentHandlerUid",currentHandlerUid);
        map.put("previousHandlerUid",MapUtils.getString(flow,"handleUid"));
        UserDto userDto = getUser(MapUtils.getString(flow,"handleUid"));
        map.put("previousHandler",null==userDto?"-":userDto.getName());
        restartOrCancel(tmpMap,map,tblName,state,currentProcessId,preProcessId,false,false,false,true);
        return respStatus;
    }

    /**
     *
     * @param map key=processId(节点),key=state(节点名)
     * @param replace 替换
     * @param target 被替换
     * @param replaceName 替换名称
     * @param targetName 被替换名称
     * @return
     */
    public Map<String,Object> subProcess(Map<String,Object> map,String replace,String target,String replaceName,String targetName){
        Map<String,Object> tmp = new HashMap<String,Object>();
        String processId = MapUtils.getString(map,"processId");
        processId = processId.replace(target,replace);
        String state = MapUtils.getString(map,"state");
        state = state.replace(targetName,replaceName);
        tmp.put("processId",processId);
        tmp.put("state",state);
        return tmp;
    }

    /**
     * 重启机构审批
     * @param map
     * @return
     */
    @Override
    public RespStatus restart(Map<String, Object> map) {
        RespStatus respStatus = new RespStatus();
        String tblName = MapUtils.getString(map,"tblName");

        /******************更新列表********************/

        Map<String,Object> tmpMap = getCurrentListData(map);
        String previousHandlerUid = MapUtils.getString(map,"updateUid");
        UserDto userDto = getUser(previousHandlerUid);

        map.put("previousHandlerUid",previousHandlerUid);
        map.put("previousHandler",null==userDto?"-":userDto.getName());
        if(MapUtils.isEmpty(tmpMap)){
            RespHelper.setFailRespStatus(respStatus, "没有查询到数据!");
        }
        Map<String,Object> tmp  = subProcess(tmpMap,"Wait","Cancel","待","取消");
        String state = MapUtils.getString(tmp,"state");
        String processId = MapUtils.getString(tmp,"processId");
        restartOrCancel(tmpMap,map,tblName,state,processId,MapUtils.getString(tmpMap,"processId"),false,false,false,false);
        return respStatus;
    }

    /**
     * 返回节点名称
     * @param map key=cityCode(城市code),key=productCode(产品code)
     * @param key 根据那个字段获取map的节点
     * @return
     */
    public String getState(Map<String,Object> map,String key){
        List<ProductProcessDto> list =  getAllProcessId(map);
        String processId = MapUtils.getString(map,key);
        String state = "";
        for (ProductProcessDto p:list){
            if(p.getProcessId().equals(processId)){
                state = p.getProcessName();
                break;
            }
        }
        return state;
    }

    /**
     * 获取 指定城市cityCode的产品productCode流程
     * @param map key=cityCode(城市),key=productCode(产品)
     * @return
     */
    public List<ProductProcessDto> getAllProcessId(Map<String,Object> map){
        int productId = NumberUtils.toInt(MapUtils.getString(map, "cityCode")+MapUtils.getString(map, "productCode"));
        logger.info("=======================指定城市cityCode的产品productCode流程========================");
        logger.info(productId+"");
        logger.info("=======================指定城市cityCode的产品productCode流程========================");
        List<ProductProcessDto> list = agencyController.getProductProcessDto(productId);
        logger.info("返回的数据为:"+list.toString());
        return list;
    }

    /**
     * 查询机构申请信息
     * @param map
     * @return
     */
    public  Map<String,Object> getAgencyApplyDate(Map<String,Object> map){
        Map<String, Object> data = new HashMap<String,Object>();
        Map<String,Object> list = productListBaseMapper.selectSmList(map);
        data.put("list",list);
        if(MapUtils.isNotEmpty(list)) {
            String orderNo = MapUtils.getString(list, "orderNo");
            ProductDataDto productDataDto = new ProductDataDto();
            productDataDto.setOrderNo(orderNo);
            productDataDto.setTblDataName("tbl_sm_data");
            List<ProductDataDto> listData = productDataBaseMapper.selectAllProductDataBaseList(productDataDto);
            data.put("data", listData);
        }
        return data;
    }

    /**
     * 获取机构指定Tbl信息
     * @param map key=orderNo,key=tblName,key=tblDataName(默认tbl_sm_data)
     * @return
     */
    public Map<String,Object> getAgencyTblData(Map<String,Object> map){
        String tblDataName = "tbl_sm_data";
        if(map.containsKey("tblDataName")){
            tblDataName = MapUtils.getString(map,"tblDataName");
        }
        ProductDataDto productDataDto = new ProductDataDto();
        productDataDto.setOrderNo(MapUtils.getString(map,"orderNo"));
        productDataDto.setTblDataName(tblDataName);
        productDataDto.setTblName(MapUtils.getString(map,"tblName"));
        productDataDto = productDataBaseMapper.selectProductDataBaseDto(productDataDto);
        if(null!=productDataDto&&StringUtils.isNotBlank(productDataDto.getDataStr())){
            Gson gson = new Gson();
            Map<String,Object> data =  gson.fromJson(productDataDto.getDataStr(),Map.class);
            return data;
        }
        return null;
    }

    /**
     * 发送短信
     * @param map 节点对应需要发送的内容信息
     * @param currentProcessId 已经完成的节点
     */
    public void sendMassage(Map<String,Object> map,String currentProcessId){
        String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
        //分配拓展经理，确认分配,拓展经理(改为渠道经理)
        if("agencyWaitDistribution".equals(currentProcessId)){
            String key = Constants.SMS_AGENCY_DISTRIBUTION_EXPANDMANAGER;
            Map<String,Object> tmp = getCurrentListData(map);
            UserDto user = getUser(MapUtils.getString(tmp,"channelManagerUid"));
            if(null!=user) {
                String agencyName = MapUtils.getString(tmp, "agencyName");
                String contactsName = MapUtils.getString(tmp, "contactsName");
                String contactsPhone = MapUtils.getString(tmp, "contactsPhone");
                AmsUtil.smsSend(user.getMobile(), ipWhite, key, agencyName, contactsName, contactsPhone);
            }

        //分配尽调经理,确认立项,尽调经理
        } else if("agencyWaitConfirm".equals(currentProcessId)){
            String key = Constants.SMS_AGENCY_DISTRIBUTION_INVESTIGATIONMANAGER;
            Map<String,Object> tmp = getCurrentListData(map);
            UserDto user = getUser(MapUtils.getString(tmp,"investigationManagerUid"));
            if(null!=user) {
                String agencyName = MapUtils.getString(tmp, "agencyName");
                String contactsName = MapUtils.getString(tmp, "contactsName");
                String contactsPhone = MapUtils.getString(tmp, "contactsPhone");
                AmsUtil.smsSend(user.getMobile(), ipWhite, key, agencyName, contactsName, contactsPhone);
            }
        //机构正式签约,正式签约后,拓展经理(改为渠道经理)
        } else if("agencyWaitSign".equals(currentProcessId)){
            String key = Constants.SMS_AGENCY_SIGNSUCCESS;
            Map<String,Object> tmp = getCurrentListData(map);
            UserDto user = getUser(MapUtils.getString(tmp,"channelManagerUid"));
            if(null!=user) {
                String agencyName = MapUtils.getString(tmp, "agencyName");
                AmsUtil.smsSend(user.getMobile(), ipWhite, key, agencyName);
            }
        //取消立项,取消立项后,拓展经理(改为渠道经理)
        } else if("agencyCancelConfirm".equals(currentProcessId)){
            String key = Constants.SMS_AGENCY_CANCEL_CONFIRM;
            UserDto expandUser = getUser(MapUtils.getString(map,"channelManagerUid"));
            String agencyName = MapUtils.getString(map, "agencyName");
            String backReason = MapUtils.getString(map, "cancelReason");
            if(null!=expandUser) {
                AmsUtil.smsSend(expandUser.getMobile(), ipWhite, key, agencyName, backReason);
            }

        //取消尽调,取消尽调后,拓展经理(改为渠道经理)/尽调经理
        } else if("agencyWaitInvestigation".equals(currentProcessId)){
            String key = Constants.SMS_AGENCY_CANCEL_INVESTIGATION;
            UserDto expandUser = getUser(MapUtils.getString(map,"channelManagerUid"));
            UserDto investigationUser = getUser(MapUtils.getString(map,"investigationManagerUid"));
            String agencyName = MapUtils.getString(map, "agencyName");
            String backReason = MapUtils.getString(map, "cancelReason");
            if(null!=expandUser) {
                AmsUtil.smsSend(expandUser.getMobile(), ipWhite, key, agencyName, backReason);
            }
            if(null!=investigationUser){
               AmsUtil.smsSend(investigationUser.getMobile(), ipWhite, key, agencyName, backReason);
            }
        //取消审核,取消审核后,拓展经理(改为渠道经理)
        } else if("agencyCancelToexamine".equals(currentProcessId)){
            String key = Constants.SMS_AGENCY_CANCEL_TOEXAMINE;
            UserDto expandUser = getUser(MapUtils.getString(map,"channelManagerUid"));
            String agencyName = MapUtils.getString(map, "agencyName");
            String backReason = MapUtils.getString(map, "cancelReason");
            if(null!=expandUser) {
                AmsUtil.smsSend(expandUser.getMobile(), ipWhite, key, agencyName, backReason);
            }
        //取消签约,取消签约后,拓展经理(改为渠道经理)
        } else if("agencyCancelSign".equals(currentProcessId)){
            String key = Constants.SMS_AGENCY_CANCEL_SIGN;
            UserDto expandUser = getUser(MapUtils.getString(map,"channelManagerUid"));
            String agencyName = MapUtils.getString(map, "agencyName");
            String backReason = MapUtils.getString(map, "cancelReason");
            if(null!=expandUser) {
                AmsUtil.smsSend(expandUser.getMobile(), ipWhite, key, agencyName, backReason);
            }
        } else if("addAgency".equals(currentProcessId)){
            String key = Constants.SMS_AGENCY_APPLY;
            String agencyName = MapUtils.getString(map, "agencyName");
            String contactsName = MapUtils.getString(map, "contactsName");
            String contactsPhone = MapUtils.getString(map, "contactsPhone");
            String expandUid = ConfigUtil.getStringValue(Constants.BASE_AGENCY_APPLY_DEFAULT_UID,ConfigUtil.CONFIG_BASE);
            UserDto expandUser = getUser(expandUid);
            if(null!=expandUser) {
                AmsUtil.smsSend(expandUser.getMobile(), ipWhite, key, agencyName, contactsName,contactsPhone);
            }
        }
    }

    /**
     * 根据uid获取用户信息
     * @param uid
     * @return
     */
    public UserDto getUser(String uid){
    	return CommonDataUtil.getUserDtoByUidAndMobile(uid);
    }

    /**
     * 将数据新增到指定tbl开头data结尾的表中
     * @param map
     * @param tbl
     * @param dataStr
     */
    public void insertTblData(Map<String,Object> map,String tbl,String dataStr){
        ProductDataDto productDataDto = new ProductDataDto();
        String tblName = MapUtils.getString(map,"tblName");
        productDataDto.setCreateUid(MapUtils.getString(map,"createUid"));
        productDataDto.setOrderNo(MapUtils.getString(map,"orderNo"));
        productDataDto.setTblDataName(tblName+"data");
        productDataDto.setTblName(tbl);
        productDataDto.setDataStr(dataStr);
        productDataBaseMapper.insertProductDataBase(productDataDto);
    }

    /**
     *获取节点保存的文件信息
     * @param orderNo 订单号
     * @param processId 节点
     * @param tblName tbl_xx_
     * @return 节点保存的文件信息集合
     */
    public List<Map<String,Object>> getFile(String orderNo,String processId,String tblName){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("tblDataName",tblName+"file");
        param.put("orderNo",orderNo);
        param.put("tblName",tblName+processId);
        return productFileBaseMapper.listFile(param);
    }


    public void copyFile(String orderNo,String processId,String currentProcessId,String tblName,String createUid){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("tblDataName",tblName+"file");
        param.put("orderNo",orderNo);
        param.put("tblName",tblName+processId);
        List<Map<String,Object>> listFile = productFileBaseMapper.listFile(param);
        if(null!=listFile&&listFile.size()>0) {
            for (Map<String, Object> m : listFile) {
                m.put("createUid",createUid);
                m.put("tblName",tblName+currentProcessId);
                m.put("tblDataName",tblName+"file");
            }
            productFileBaseMapper.batchFile(listFile);
            logger.info("将文件从:"+processId+"节点拷贝到:"+currentProcessId+"节点,总共拷贝:"+listFile.size());
        }
    }
}
