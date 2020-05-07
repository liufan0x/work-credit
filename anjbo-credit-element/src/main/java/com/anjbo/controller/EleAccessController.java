package com.anjbo.controller;

import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.EleAccessService;
import com.anjbo.service.ElementService;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 要件管理
 * Created by lichao on 2018/1/11.
 */
@Controller
@RequestMapping("/credit/element/eleaccess/web/v")
public class EleAccessController  extends BaseController{

    @Autowired
    private EleAccessService eleAccessService;
    @Resource
	private ElementService elementService;

    @ResponseBody
    @RequestMapping("/eleAccessPage")
    public RespPageData<Map<String,Object>> eleAccessPage(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
        try {
            /*权限控制 start*/
            UserDto userDto = getUserDto(request);
            String deptAllUid = "";
            String  orderNos = "";
            //查看全部订单
            if(userDto.getAuthIds().contains("1")){
                //查看部门订单
            }else if(userDto.getAuthIds().contains("2")){
                userDto.setCreateTime(null);
                userDto.setUpdateTime(null);
                RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/v/selectUidsByDeptId",userDto , Map.class);
                deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
                //查看自己订单
            }else{
                deptAllUid = userDto.getUid();
            }
            // 自己操作过的单
            RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectCreditOrderNos",userDto , Map.class);
            if (RespStatusEnum.SUCCESS.getCode().equals(respTemp.getCode())&&respTemp.getData()!=null){
                orderNos = MapUtils.getString(respTemp.getData(),"orderNos");
            }
            param.put("currentHandlerUid",deptAllUid);
            param.put("updateUid",userDto.getUid());
            param.put("orderNo",orderNos);
            /*权限控制 end*/

            int count = eleAccessService.selectElementAccessCount(param);
            List<Map<String, Object>> elementList = new ArrayList<Map<String, Object>>();
            if (count>0){
                elementList = eleAccessService.selectElementAccessList(param);
                List<Map<String, Object>> allFileList = eleAccessService.selectAllElementFileList();
                for (Map<String, Object> map : elementList){
                    StringBuffer sbRisk= new StringBuffer();
                    StringBuffer sbPay= new StringBuffer();
                    String stillFile = MapUtils.getString(map,"currentBoxElementSet");
                    if (StringUtil.isNotEmpty(stillFile)){
                        for(String file: stillFile.split(",")){
                            for (Map<String, Object> allMap: allFileList){
                                if (StringUtil.isNotEmpty(file)&&file.equals(MapUtils.getString(allMap,"id"))){
                                    if ("1".equals(MapUtils.getString(allMap,"elementType"))){
                                        if (sbPay.length()>0){
                                            sbPay.append("、");
                                        }
                                        sbPay.append(MapUtils.getString(allMap,"cardType"));
                                    }else{
                                        if (sbRisk.length()>0){
                                            sbRisk.append("、");
                                        }
                                        sbRisk.append(MapUtils.getString(allMap,"cardType"));
                                    }
                                }
                            }
                        }
                    }
                    if (sbRisk.length()>0)
                        map.put("sbRisk",sbRisk.toString());
                    if (sbPay.length()>0)
                        map.put("sbPay",sbPay.toString());
                }
            }

            result.setTotal(count);
            result.setRows(elementList);
            RespHelper.setSuccessRespStatus(result);
        }catch (Exception e){
            e.printStackTrace();
            RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/accessFlowDetail")
    public RespDataObject<Map<String,Object>> accessFlowDetail(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            Map<String, Object> flowDetail = eleAccessService.selectElementAccessFlowDetail(param);
            if (flowDetail!=null){
                if (StringUtil.isNotEmpty(MapUtils.getString(flowDetail,"elementSet"))){
                    List<Map<String, Object>> fileList = eleAccessService.selectElementFileList(flowDetail,"elementSet");
                    if (CollectionUtils.isNotEmpty(fileList)){
                        Map<String, Object> map = new HashMap<String, Object>();
                        getElementFileDetail(map,fileList);
                        flowDetail.put("handleEle",map);
                    }
                }
                if (StringUtil.isNotEmpty(MapUtils.getString(flowDetail,"currentBoxElementSet"))){
                    List<Map<String, Object>> fileList = eleAccessService.selectElementFileList(flowDetail,"currentBoxElementSet");
                    if (CollectionUtils.isNotEmpty(fileList)){
                        Map<String, Object> map = new HashMap<String, Object>();
                        getElementFileDetail(map,fileList);
                        flowDetail.put("stillEle",map);
                    }
                }
                //取
                if ("2".equals(MapUtils.getString(flowDetail,"operationType"))&&StringUtil.isNotEmpty(MapUtils.getString(flowDetail,"dbId"))){
                    List<Map<String, Object>> flowList = eleAccessService.selectElementAuditFlowListByDbId(flowDetail);
                    flowDetail.put("flowList",flowList);
                    if ("3".equals(MapUtils.getString(flowDetail,"orderType"))){
                        Map<String, Object> AuditBaseDetail = eleAccessService.getElementAuditBaseDetail(flowDetail);
                        if(AuditBaseDetail!=null){
                            flowDetail.put("fileToSeal",MapUtils.getString(AuditBaseDetail,"fileToSeal"));
                            flowDetail.put("sealFileCount",MapUtils.getString(AuditBaseDetail,"sealFileCount"));
                            flowDetail.put("fileType",MapUtils.getString(AuditBaseDetail,"fileType"));
                            flowDetail.put("reason",MapUtils.getString(AuditBaseDetail,"reason"));
                            if (StringUtil.isNotEmpty(MapUtils.getString(AuditBaseDetail,"fileImgUrl")))
                                flowDetail.put("fileImgUrl",JsonUtil.JsonTolist(MapUtils.getString(AuditBaseDetail,"fileImgUrl")));
                        }
                    }
                }
                if("6".equals(MapUtils.getString(flowDetail,"operationType"))) {//开箱
                	 if (StringUtil.isNotEmpty(MapUtils.getString(flowDetail,"fileImgUrl")))
                         flowDetail.put("fileImgUrl",JsonUtil.JsonTolist(MapUtils.getString(flowDetail,"fileImgUrl")));
                }
                //改 operationType大于7小于15不等于12
                if(MapUtils.getIntValue(flowDetail, "operationType")>7
                		&&MapUtils.getIntValue(flowDetail, "operationType")<15&&MapUtils.getIntValue(flowDetail, "operationType")!=12) {
                	Map<String,Object> map = new HashMap<String,Object>();
                	map.put("accessFlowId", MapUtils.getIntValue(flowDetail, "id"));
                	map.put("orderNo", MapUtils.getString(flowDetail, "orderNo"));
                	map.put("operationType", MapUtils.getIntValue(flowDetail, "operationType"));
                	Map<String,Object> m = elementService.updateElementDetail(map);
                	flowDetail.put("operationDetail",m);
                }
                //改基本信息
                if(MapUtils.getIntValue(flowDetail, "operationType")==12) {
                	Map<String,Object> map = new HashMap<String,Object>();
                	map.put("accessFlowId", MapUtils.getIntValue(flowDetail, "id"));
                	map.put("orderNo", MapUtils.getString(flowDetail, "orderNo"));
                	Map<String,Object> m = elementService.updateElementBaseInfoDetail(map);
                	flowDetail.put("operationDetail",m);
                }
                List<String> pictureList = new ArrayList<String>();
                List<String> riskPicture = new ArrayList<String>();
                List<String> receivablePicture = new ArrayList<String>();
                List<String> elsePicture = new ArrayList<String>();
                List<String> sealPicture = new ArrayList<String>();
                getAccessFlowList(riskPicture,MapUtils.getString(flowDetail,"riskPicture",""));
                getAccessFlowList(receivablePicture,MapUtils.getString(flowDetail,"receivablePicture",""));
                getAccessFlowList(elsePicture,MapUtils.getString(flowDetail,"elsePicture",""));
                getAccessFlowList(sealPicture,MapUtils.getString(flowDetail,"sealPicture",""));
                getAccessFlowList(pictureList,MapUtils.getString(flowDetail,"riskPicture",""));
                getAccessFlowList(pictureList,MapUtils.getString(flowDetail,"receivablePicture",""));
                getAccessFlowList(pictureList,MapUtils.getString(flowDetail,"elsePicture",""));
                getAccessFlowList(pictureList,MapUtils.getString(flowDetail,"sealPicture",""));

                if (CollectionUtils.isNotEmpty(pictureList)){
                    flowDetail.put("pictureList",pictureList);
                }
                if (CollectionUtils.isNotEmpty(riskPicture)){
                    flowDetail.put("riskPicture",riskPicture);
                }
                if (CollectionUtils.isNotEmpty(receivablePicture)){
                    flowDetail.put("receivablePicture",receivablePicture);
                }
                if (CollectionUtils.isNotEmpty(elsePicture)){
                    flowDetail.put("elsePicture",elsePicture);
                }
                if (CollectionUtils.isNotEmpty(sealPicture)){
                    flowDetail.put("sealPicture",sealPicture);
                }
            }

            result.setData(flowDetail);
            RespHelper.setSuccessRespStatus(result);
        }catch (Exception e){
            e.printStackTrace();
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getElementOrderDetail")
    public RespDataObject<Map<String,Object>> getElementOrderDetail(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try {
            Map<String, Object> flowDetail = eleAccessService.getElementOrderDetail(param);
            if (flowDetail!=null) {
                String ele=MapUtils.getString(flowDetail, "currentBoxElementSet");
                if (StringUtil.isNotEmpty(ele)) {
                    List<Map<String, Object>> fileList = eleAccessService.selectElementFileList(flowDetail, "currentBoxElementSet");
                    if (CollectionUtils.isNotEmpty(fileList)) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        getElementFileDetail(map, fileList);
                        flowDetail.put("stillEle", map);
                    }
                }
            }
            result.setData(flowDetail);
            RespHelper.setSuccessRespStatus(result);
        }catch (Exception e){
            logger.error(e);
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/pageByOrderNo")
    public RespPageData<Map<String,Object>> pageByOrderNo(HttpServletRequest request, @RequestBody Map<String,Object> param){
        RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
        try {

            int count = eleAccessService.selectElementCountByOrderNo(param);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if (count>0){
                list = eleAccessService.selectElementListByOrderNo(param);
                if (CollectionUtils.isNotEmpty(list)){
                    for (Map<String, Object> flowDetail : list) {

                        if (flowDetail!=null){
                            String ele =MapUtils.getString(flowDetail,"elementSet");
                            if (StringUtil.isNotEmpty(ele)){
                                List<Map<String, Object>> fileList = eleAccessService.selectElementFileList(flowDetail,"elementSet");
                                if (CollectionUtils.isNotEmpty(fileList)){
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    getElementFileDetail(map,fileList);
                                    flowDetail.put("handleEle",map);
                                }
                            }

                            if ("2".equals(MapUtils.getString(flowDetail,"operationType"))&&StringUtil.isNotEmpty(MapUtils.getString(flowDetail,"dbId"))){
                                List<Map<String, Object>> flowList = eleAccessService.selectElementAuditFlowListByDbId(flowDetail);
                                flowDetail.put("flowList",flowList);
                            }

                            List<String> pictureList = new ArrayList<String>();
                            getAccessFlowList(pictureList,MapUtils.getString(flowDetail,"riskPicture",""));
                            getAccessFlowList(pictureList,MapUtils.getString(flowDetail,"receivablePicture",""));
                            getAccessFlowList(pictureList,MapUtils.getString(flowDetail,"elsePicture",""));
                            getAccessFlowList(pictureList,MapUtils.getString(flowDetail,"sealPicture",""));

                            if (CollectionUtils.isNotEmpty(pictureList)){
                                flowDetail.put("pictureList",pictureList);
                            }

                            String operationType =MapUtils.getString(flowDetail,"operationType");
                            String handleOne="";
                            String handleTwo="";
                            String handleThree="";
                            if ("1".equals(operationType)){
                                handleOne="存";
                                handleTwo="存入的";
                                handleThree="存入";
                            }else if ("2".equals(operationType)||"3".equals(operationType)){
                                handleOne="借";
                                handleTwo="申请借用的";
                                handleThree="开箱";
                            }else if ("4".equals(operationType)){
                                handleOne="还";
                                handleTwo="归还的";
                                handleThree="归还";
                            }else if ("5".equals(operationType)){
                                handleOne="退";
                                handleTwo="退还的";
                                handleThree="退还";
                            }else if ("6".equals(operationType)||"7".equals(operationType)){
                                handleOne="开";
                                handleTwo="申请借用的";
                                handleThree="开箱";
                            }else if("8".equals(operationType)||"9".equals(operationType)||"10".equals(operationType)||"11".equals(operationType)) {
                            	handleOne="改";
                                handleTwo="修改的";
                                handleThree="修改";
                            }
                            flowDetail.put("handleOne",handleOne);
                            flowDetail.put("handleTwo",handleTwo);
                            flowDetail.put("handleThree",handleThree);

                        }
                    }
                }
            }

            result.setTotal(count);
            result.setRows(list);
            RespHelper.setSuccessRespStatus(result);
        }catch (Exception e){
            logger.error(e);
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
        }
        return result;
    }

    private void getAccessFlowList(List<String> pictureList,String pictures){
        for (String picture:pictures.split(",")){
            if (StringUtil.isNotEmpty(picture)){
                pictureList.add(picture);
            }
        }
    }
    private void getElementFileDetail(Map<String, Object> map,List<Map<String, Object>> fileList){
        List<Map<String, Object>> eleFilePayList = new ArrayList<Map<String, Object> >();
        List<Map<String, Object>> eleFileRiskList = new ArrayList<Map<String, Object> >();
        List<Map<String, Object>> eleFileSealList = new ArrayList<Map<String, Object> >();
//        int eleFileIDRisk=0;
//        int eleFileIDPay=0;
//        int eleFileHouse=0;
//        int eleFileBank=0;
        for (Map<String, Object> file:fileList){
            Map<String, Object> fileDetail = new HashMap<String, Object>();
            if ("1".equals(MapUtils.getString(file,"type"))){
                fileDetail.put("title","身份证");
                List<Map<String, Object>> detailList= new ArrayList<Map<String, Object>>();
                Map<String, Object> cardCustomer = new HashMap<String, Object>();
                cardCustomer.put("name","姓名");
                cardCustomer.put("value",MapUtils.getString(file,"cardCustomer"));
                detailList.add(cardCustomer);
                Map<String, Object> cardNumber = new HashMap<String, Object>();
                cardNumber.put("name","身份证号");
                cardNumber.put("value",MapUtils.getString(file,"cardNumber"));
                detailList.add(cardNumber);
                fileDetail.put("detailList",detailList);
                if ("1".equals(MapUtils.getString(file,"elementType"))){
                    eleFilePayList.add(fileDetail);
                }else {
                    eleFileRiskList.add(fileDetail);
                }
            }else if ("2".equals(MapUtils.getString(file,"type"))){
                fileDetail.put("title","户口本");
                List<Map<String, Object>> detailList= new ArrayList<Map<String, Object>>();
                Map<String, Object> cardCustomer = new HashMap<String, Object>();
                cardCustomer.put("name","户主姓名");
                cardCustomer.put("value",MapUtils.getString(file,"cardCustomer"));
                detailList.add(cardCustomer);
                fileDetail.put("detailList",detailList);
                eleFileRiskList.add(fileDetail);
            }else if ("3".equals(MapUtils.getString(file,"type"))){
                fileDetail.put("title","房产证");
                List<Map<String, Object>> detailList= new ArrayList<Map<String, Object>>();
                Map<String, Object> cardCustomer = new HashMap<String, Object>();
                cardCustomer.put("name","权利人");
                cardCustomer.put("value",MapUtils.getString(file,"cardCustomer"));
                detailList.add(cardCustomer);
                Map<String, Object> cardNumber = new HashMap<String, Object>();
                cardNumber.put("name","权属证明编号");
                cardNumber.put("value",MapUtils.getString(file,"cardNumber"));
                detailList.add(cardNumber);
                fileDetail.put("detailList",detailList);
                eleFileRiskList.add(fileDetail);
            }else if ("4".equals(MapUtils.getString(file,"type"))){
                fileDetail.put("title","银行卡");
                List<Map<String, Object>> detailList= new ArrayList<Map<String, Object>>();
                Map<String, Object> bankName = new HashMap<String, Object>();
                bankName.put("name","开户银行");
                bankName.put("value",MapUtils.getString(file,"bankName"));
                detailList.add(bankName);
                Map<String, Object> bankSubName = new HashMap<String, Object>();
                bankSubName.put("name","支行");
                bankSubName.put("value",MapUtils.getString(file,"bankSubName"));
                detailList.add(bankSubName);
                Map<String, Object> cardCustomer = new HashMap<String, Object>();
                cardCustomer.put("name","户名");
                cardCustomer.put("value",MapUtils.getString(file,"cardCustomer"));
                detailList.add(cardCustomer);
                Map<String, Object> cardNumber = new HashMap<String, Object>();
                cardNumber.put("name","账号");
                cardNumber.put("value",MapUtils.getString(file,"cardNumber"));
                detailList.add(cardNumber);
                fileDetail.put("detailList",detailList);
                eleFilePayList.add(fileDetail);
            }else if("5".equals(MapUtils.getString(file,"type"))||"6".equals(MapUtils.getString(file,"type"))){
            	fileDetail.put("title",MapUtils.getString(file,"cardType"));
            	if("离婚证".equals(MapUtils.getString(file,"cardType"))||"结婚证".equals(MapUtils.getString(file,"cardType"))) {
            		List<Map<String, Object>> detailList= new ArrayList<Map<String, Object>>();
            		Map<String, Object> cardCustomer = new HashMap<String, Object>();
            		cardCustomer.put("name","持证人");
            		cardCustomer.put("value",MapUtils.getString(file,"cardCustomer"));
                    detailList.add(cardCustomer);
                    fileDetail.put("detailList",detailList);
                    eleFileRiskList.add(fileDetail);
            	}else if("网银".equals(MapUtils.getString(file,"cardType"))) {
            		List<Map<String, Object>> detailList= new ArrayList<Map<String, Object>>();
            		Map<String, Object> cardNumber = new HashMap<String, Object>();
            		cardNumber.put("name","账号");
            		cardNumber.put("value",MapUtils.getString(file,"cardNumber"));
                    detailList.add(cardNumber);
                    fileDetail.put("detailList",detailList);
                    eleFilePayList.add(fileDetail);
            	}else {
                    if ("1".equals(MapUtils.getString(file,"elementType"))){
                        eleFilePayList.add(fileDetail);
                    }else if ("2".equals(MapUtils.getString(file,"elementType"))){
                        eleFileRiskList.add(fileDetail);
                    }else {
                    	eleFileSealList.add(fileDetail);
                    }
            	}
            }else {
            	fileDetail.put("title",MapUtils.getString(file,"cardType"));
            	 if ("1".equals(MapUtils.getString(file,"elementType"))){
                     eleFilePayList.add(fileDetail);
                 }else if ("2".equals(MapUtils.getString(file,"elementType"))){
                     eleFileRiskList.add(fileDetail);
                 }else {
                 	eleFileSealList.add(fileDetail);
                 }
            }
        }
        if(CollectionUtils.isNotEmpty(eleFileRiskList))//风控
            map.put("eleFileRiskList",eleFileRiskList);
        if(CollectionUtils.isNotEmpty(eleFilePayList))//回款
            map.put("eleFilePayList",eleFilePayList);
        if(CollectionUtils.isNotEmpty(eleFileSealList))//公章
            map.put("eleFileSealList",eleFileSealList);
    }
}
