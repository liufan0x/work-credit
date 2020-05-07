package com.anjbo.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.UserDto;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.ReportStatisticsMapper;
import com.anjbo.service.ExcelService;
import com.anjbo.service.StatisticsService;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.SortUtil;
import com.anjbo.utils.StringUtil;

/**
 * Created by Administrator on 2018/5/8.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private ReportStatisticsMapper reportStatisticsMapper;

    /**
     * 查询回款报备统计按城市分组
     *
     * @param map key=day(时间条件),key=cityCode(城市条件),key=productCode(产品条件)
     * @return
     */
    @Override
    public List<Map<String, Object>> selectInPayment(Map<String, Object> map) {
        //今日回款款款统计
        map.put("day",0);
        List<Map<String,Object>> l1 = reportStatisticsMapper.selectInPayment(map);
        //明日回款统计
        map.put("day",2);
        List<Map<String,Object>> l2 = reportStatisticsMapper.selectInPayment(map);
        //三日内回款统计
        map.put("day",3);
        List<Map<String,Object>> l3 = reportStatisticsMapper.selectInPayment(map);
        //七日内回款统计
        map.put("day",7);
        List<Map<String,Object>> l7 = reportStatisticsMapper.selectInPayment(map);
        //15日内回款统计
        map.put("day",15);
        List<Map<String,Object>> l15 = reportStatisticsMapper.selectInPayment(map);
        List<Map<String,Object>> data = mappingPayment(l1,l2,l3,l7,l15,map);
        return data;
    }

    /**
     * 查询出款报备统计按城市分组
     *
     * @param map key=day(时间条件),key=cityCode(城市条件),key=productCode(产品条件)
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOutPayment(Map<String, Object> map) {
        //今日出款款统计
        map.put("day",0);
        List<Map<String,Object>> l1 = reportStatisticsMapper.selectOutPayment(map);
        //明日出款统计
        map.put("day",2);
        List<Map<String,Object>> l2 = reportStatisticsMapper.selectOutPayment(map);
        //三日内出款统计
        map.put("day",3);
        List<Map<String,Object>> l3 = reportStatisticsMapper.selectOutPayment(map);
        //七日内出款统计
        map.put("day",7);
        List<Map<String,Object>> l7 = reportStatisticsMapper.selectOutPayment(map);
        //15日内出款统计
        map.put("day",15);
        List<Map<String,Object>> l15 = reportStatisticsMapper.selectOutPayment(map);
        List<Map<String,Object>> data = mappingPayment(l1,l2,l3,l7,l15,map);
        return data;
    }

    /**
     * 排序(按当天出/回款由高往底排序,这里会出现并列情况的发生),截取分页内数据(无法用SQL实现只能用代码实现)
     * @param l1 当天数据
     * @param l2 明天数据
     * @param l3 3天内数据
     * @param l7 7天内数据
     * @param l15 15天内的数据
     * @param param 页面参数
     * @return
     */
    public List<Map<String,Object>> mappingPayment(List<Map<String, Object>> l1,
                                                   List<Map<String, Object>> l2,
                                                   List<Map<String, Object>> l3,
                                                   List<Map<String, Object>> l7,
                                                   List<Map<String, Object>> l15,
                                                   Map<String,Object> param){
    	param.put("limitType", 1);
        List<Map<String, Object>> listCity = reportStatisticsMapper.findByCityCount(param);

        if(null==listCity||listCity.size()<=0)
            return listCity;

        Map<String,Object> m1 = new HashMap<String,Object>();
        for (Map<String,Object> m:l1){
            m1.put(MapUtils.getString(m,"cityCode"),MapUtils.getDouble(m,"loanAmount",0d));
        }
        Map<String,Object> m2 = new HashMap<String,Object>();
        for (Map<String,Object> m:l2){
            m2.put(MapUtils.getString(m,"cityCode"),MapUtils.getDouble(m,"loanAmount",0d));
        }
        Map<String,Object> m3 = new HashMap<String,Object>();
        for (Map<String,Object> m:l3){
            m3.put(MapUtils.getString(m,"cityCode"),MapUtils.getDouble(m,"loanAmount",0d));
        }
        Map<String,Object> m7 = new HashMap<String,Object>();
        for (Map<String,Object> m:l7){
            m7.put(MapUtils.getString(m,"cityCode"),MapUtils.getDouble(m,"loanAmount",0d));
        }
        Map<String,Object> m15 = new HashMap<String,Object>();
        for (Map<String,Object> m:l15){
            m15.put(MapUtils.getString(m,"cityCode"),MapUtils.getDouble(m,"loanAmount",0d));
        }

        for (Map<String,Object> m:listCity){
            m.put("loanAmount",MapUtils.getDouble(m1,MapUtils.getString(m,"cityCode"),0d));
            m.put("tomorrowLoanAmount",MapUtils.getDouble(m2,MapUtils.getString(m,"cityCode"),0d));
            m.put("tridLoanAmount",MapUtils.getDouble(m3,MapUtils.getString(m,"cityCode"),0d));
            m.put("sevenLoanAmount",MapUtils.getDouble(m7,MapUtils.getString(m,"cityCode"),0d));
            m.put("fifteenLoanAmount",MapUtils.getDouble(m15,MapUtils.getString(m,"cityCode"),0d));
        }
        //TODO 需要按进入出/回款由高往低排序
        return listCity;
    }


    /**
     * 用代码实现分页处理并实现排序
     * @param list
     * @param sort
     * @param param
     * @param repeat
     * @return
     */
    public List<Map<String,Object>> subData(List<Map<String,Object>> list,
                                            Map<Double,String> sort,
                                            Map<String,Object> param,
                                            Map<String,Double> repeat){

        if(null==sort||sort.size()<=0)
            return list;
        int index = 0;
        int dataIndex = 0;
        int start = MapUtils.getInteger(param,"start",0);
        int pageSize = MapUtils.getInteger(param,"pageSize",15);

        int repeatInd = null==repeat?0:repeat.size();
        int len = sort.size()<list.size()?sort.size()+repeatInd:sort.size();
        if(len<start||0==pageSize)
            return new ArrayList<Map<String,Object>>();

        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        sort(sort,repeat,data,list,start,pageSize,index,dataIndex);
        return data;
    }

    /**
     * 将数据排序,并判断是否有并列数据
     * @param sort
     * @param repeat 并列数据
     * @param data 返回的数据
     * @param start 分页开始值
     * @param pageSize 分页展示数量
     * @param index
     */
    public void sort(Map<Double,String> sort,
                     Map<String,Double> repeat,
                     List<Map<String,Object>> data,
                     List<Map<String,Object>> list,
                     int start,int pageSize,
                     int index,int dataIndex){
        Set<Map.Entry<Double, String>> entries = sort.entrySet();

        for (Map.Entry<Double, String> m:entries){
            if(data.size()==pageSize)
                break;
            if(index<start) {
                if(repeat.containsKey(m.getValue()))
                    repeat.remove(m.getValue());
                index++;
                continue;
            }
            /**
             * 判断是否在并列的Map里
             */
            if(repeat.containsKey(m.getValue())){
                sort(repeat,data,list,start,pageSize,dataIndex);
                continue;
            }
            Iterator<Map<String, Object>> iterator = list.iterator();
            while (iterator.hasNext()){
                Map<String, Object> md = iterator.next();
                if(m.getValue().equals(MapUtils.getString(md,"cityCode"))){
                    dataIndex++;
                    data.add(md);
                    iterator.remove();
                    break;
                }
            }
        }
        if(data.size()==pageSize)
            return;
        index = 0;
        Iterator<Map.Entry<String, Double>> iterator = repeat.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Double> next = iterator.next();
            if(data.size()==pageSize)
                break;
            if(0<=data.size()&&index<start) {
                index++;
                continue;
            }
            Iterator<Map<String, Object>> listIterator = list.iterator();
            while (listIterator.hasNext()){
                Map<String, Object> md = listIterator.next();
                if(next.getKey().equals(MapUtils.getString(md,"cityCode"))){
                    data.add(md);
                    dataIndex++;
                    listIterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * 处理并列的数据
     * @param repeat
     * @param data
     * @param list
     * @param pageSize
     */
    public void sort(Map<String,Double> repeat,
                     List<Map<String,Object>> data,
                     List<Map<String,Object>> list,
                     int start,
                     int pageSize,
                     int dataIndex){
        Iterator<Map.Entry<String, Double>> iterator = repeat.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()){
            Map.Entry<String, Double> next = iterator.next();
            if(data.size()==pageSize)
                break;
            if(0<=data.size()&&index<start){
                index++;
                continue;
            }
            Iterator<Map<String, Object>> listIterator = list.iterator();
            while (listIterator.hasNext()){
                Map<String, Object> md = listIterator.next();
                if(next.getKey().equals(MapUtils.getString(md,"cityCode"))){
                    data.add(md);
                    iterator.remove();
                    dataIndex++;
                    break;
                }
            }
        }
    }
    /**
     * 查询今日待提单,初审,终审,首席风险官统计
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectToDayOrder(Map<String, Object> map) {
        List<Map<String,Object>> list = reportStatisticsMapper.selectToDayOrder(map);
        Map<String,Object> m = new HashMap<String,Object>();
        if(null==list||list.size()==0){
            m.put("placeOrder",0);
            m.put("auditFirst",0);
            m.put("auditFinal",0);
            m.put("auditOfficer",0);
            return m;
        }
        for(Map<String,Object> t:list){
            m.put(MapUtils.getString(t,"processId"),MapUtils.getInteger(t,"count",0));
        }
        if(!m.containsKey("placeOrder"))
            m.put("placeOrder",0);
        if(!m.containsKey("auditFirst"))
            m.put("auditFirst",0);
        if(!m.containsKey("auditFinal"))
            m.put("auditFinal",0);
        if(!m.containsKey("auditOfficer"))
            m.put("auditOfficer",0);
        return m;
    }

    @Override
    public Integer selectInPaymentCount(Map<String, Object> map) {
        Integer integer = reportStatisticsMapper.findByCityCount(map).size();
        return integer;
    }

    @Override
    public Integer selectOutPaymentCount(Map<String, Object> map) {
        Integer integer = reportStatisticsMapper.findByCityCount(map).size();
        return integer;
    }

    @Override
    public  List<Map<String,Object>> selectCityCount(Map<String, Object> map) {
        return reportStatisticsMapper.selectCityCount(map);
    }

    /**
     * 创收目标报表
     * 返回结构为 城市->部门->部门人数
     * @param map
     * @param listDic
     * @return
     */
    @Override
    public List<Map<String, Object>> createIncome(Map<String, Object> map, List<DictDto> listDic) {

        Integer effective = MapUtils.getInteger(map,"effective",0);
        List<Map<String, Object>> maps = reportStatisticsMapper.selectCity(map);
        if(null==maps||maps.size()<=0)
            return maps;
        //该机构下指定角色所有用户
        List<UserDto> userDtos = reportStatisticsMapper.selectRoleByAgencyIdRoleName(map);
        //该机构下的所有部门(去除按部门分组,改为按城市分组20180529)
        //List<Map<String,Object>> listDept = reportStatisticsMapper.selectDept(map);
        //部门上传的创收目标报表
        map.put("effective",1);
        List<Map<String,Object>> files = reportStatisticsMapper.selectIncomeFile(map);
        List<Map<String,Object>> data = groupByCity(maps,userDtos,effective,files);
        return data;
    }

    /**
     * 创收目标报表按城市分组归类人员
     * @param retureData 返回的数据
     * @param userDtos   用户集合
     * @param effective  页面检索(1上传,2未上传)
     * @param files      上传的目标报表
     * @return
     */
    public List<Map<String,Object>> groupByCity(List<Map<String, Object>> retureData,
                                                List<UserDto> userDtos,
                                                Integer effective,
                                                List<Map<String,Object>> files){
        Map<String,Object> cityMap = new HashMap<String,Object>();
        List<UserDto> userTmp;
        for (UserDto u:userDtos){
            if(StringUtil.isBlank(u.getCityCode()))
                continue;
            Object obj = MapUtils.getObject(cityMap,u.getCityCode());
            if(null==obj)
                userTmp = new ArrayList<UserDto>();
            else
                userTmp = (List<UserDto>)obj;

            userTmp.add(u);
            cityMap.put(u.getCityCode(),userTmp);
        }
        String cityCode;
        String deptName;
        for (Map<String,Object> m:retureData){
            cityCode = MapUtils.getString(m, "cityCode");
            Object obj = MapUtils.getObject(cityMap,cityCode);
            deptName = MapUtils.getString(m,"name");
            deptName = deptName.replace("市","")+"分公司";
            m.put("deptName",deptName);
            if(null==obj){
                m.put("count",0);
                m.put("userList",obj);
            } else {
                userTmp = (List<UserDto>)obj;
                m.put("count",userTmp.size());
                m.put("userList",obj);
            }
            getFiles(m,files,cityCode,"cityCode");
        }
        return retureData;
    }
    /**
     * 创收目标报表按部门分组归类人员
     * @param listDept 当前登录人机构下的所有部门
     * @param files    上传的目标报表
     * @param userDtos 用户集合
     * @param effective 页面参数(主要为了页面检索筛选)
     * @return 按城市封装好部门以及部门下的人员数据
     */
    public List<Map<String, Object>>  groupByDept(List<Map<String,Object>> listDept,
                                          List<Map<String,Object>> files,
                                          List<UserDto> userDtos,
                                          Integer effective,
                                          List<Map<String, Object>> maps){

        Map<String,Object> cityMap = new HashMap<String,Object>();
        List<Map<String,Object>> tmpDept;       //部门临时map
        List<UserDto> tmpuser;                  //用户临时map
        Map<String,Object> tmpMap;              //部门下保存数据临时map
        List<Map<String,Object>> tmp;           //城市下保存部门临时map
        String deptId;                          //部门id
        String tmpDeptId;
        String pid;                             //上级部门id
        String pname;                           //上级部门名称
        String deptName;                        //部门名称
        String cityCode;

        boolean exist;
        boolean fileExist;
        for (Map<String,Object> dept:listDept){
            deptId = MapUtils.getString(dept,"id");
            deptName = MapUtils.getString(dept,"name");
            pid = MapUtils.getString(dept,"pid");
            pname = MapUtils.getString(dept,"pname");
            for (UserDto u:userDtos){
                if(u.getDeptIdArray().contains(deptId)||deptId.contains(u.getDeptId()+"")){
                    //城市下部门集合
                    Object obj = MapUtils.getObject(cityMap,u.getCityCode());
                    if(null==obj){
                        tmp = new ArrayList<Map<String,Object>>();
                        tmpMap = new HashMap<String,Object>();
                        tmpuser = new ArrayList<UserDto>();
                        tmpuser.add(u);
                        tmpMap.put("userList",tmpuser);
                        tmpMap.put("count",1);
                        tmpMap.put("deptName",deptName);
                        tmpMap.put("deptId",deptId);
                        tmpMap.put("pid",pid);
                        tmpMap.put("pname",pname);
                        fileExist = getFiles(tmpMap,files,deptId,"deptId");
                        if((!fileExist&&effective==1)
                                ||fileExist&&effective==2){
                            break;
                        }
                        tmp.add(tmpMap);
                    } else {
                        exist = false;
                        tmp = (List<Map<String,Object>>)obj;
                        tmpDept = new ArrayList<Map<String,Object>>();
                        for (Map<String,Object> m:tmp){
                            tmpDeptId = MapUtils.getString(m,"deptId");
                            if(deptId.equals(tmpDeptId)){
                                Object userobj = MapUtils.getObject(m,"userList");
                                tmpuser = null==userobj?new ArrayList<UserDto>():(List<UserDto>)userobj;
                                tmpuser.add(u);
                                m.put("userList",tmpuser);
                                m.put("count",MapUtils.getInteger(m,"count",0)+1);
                                exist = true;
                                break;
                            }
                        }
                        if(!exist){
                            tmpMap = new HashMap<String,Object>();
                            tmpuser = new ArrayList<UserDto>();
                            tmpuser.add(u);
                            tmpMap.put("userList",tmpuser);
                            tmpMap.put("count",1);
                            tmpMap.put("deptName",deptName);
                            tmpMap.put("deptId",deptId);
                            tmpMap.put("pid",pid);
                            tmpMap.put("pname",pname);
                            fileExist = getFiles(tmpMap,files,deptId,"deptId");
                            if((!fileExist&&effective==1)
                                    ||fileExist&&effective==2){
                                break;
                            }
                            tmpDept.add(tmpMap);
                        }
                        if(tmpDept.size()>0)
                            tmp.addAll(tmpDept);
                    }
                    cityMap.put(u.getCityCode(),tmp);
                }
            }
        }

        for (Map<String,Object> m:maps){
            cityCode = MapUtils.getString(m, "cityCode");
            Object obj = MapUtils.getObject(cityMap,cityCode);
            if(null==obj){
                tmp = new ArrayList<Map<String,Object>>();
                tmpMap = new HashMap<String,Object>();
                deptName = MapUtils.getString(m,"name");
                deptName = deptName.replace("市","")+"分公司";
                tmpMap.put("deptName",deptName);
                tmpMap.put("count",0);
                tmpMap.put("userList",null);
                tmp.add(tmpMap);
                m.put("deptList",tmp);
            } else {
                m.put("deptList",obj);
            }
        }
        return maps;
    }

    /**
     * 匹配该部门上传的创收目标报表文件
     * @param map
     * @param files
     * @param value
     */
    public boolean getFiles(Map<String,Object> map,
                            List<Map<String,Object>> files,
                            String value,
                            String key){
        boolean exist = false;
        for (Map<String,Object> f:files){
            if(value.equals(MapUtils.getString(f,key))){
                map.put("fileUrl",MapUtils.getString(f,"fileUrl"));
                map.put("effective",1);
                exist = true;
                break;
            }
        }
        if(!exist) {
            map.put("fileUrl", null);
            map.put("effective", 2);
        }
        return exist;
    }

    /**
     * 查询机构放回款统计
     *
     * @param map key=productCode(产品编码),cooperativeModeId(合作模式(1.兜底,2非兜底))
     *            startTime(检索条件：开始时间),endTime(检索条件：结束时间)
     *            timeWhere=lastWeek(检索条件：上周)
     *            timeWhere=lastMonth(检索条件：上月)
     *            timeWhere=yesterday(检索条件：昨日)
     *            timeWhere=thisMonth(检索条件：当月)
     * @return
     * key=agencyId(合作机构id),
     * agencyName(合作机构名称)
     * creditLimit(机构首席额度)
     * surplusQuota(剩余额度)
     * orderCount(业务单量)
     * income(创收)
     * loanAmount(放款金额)
     * payMentAmount(回款金额)
     * payCount(回款笔数)
     */
    @Override
    public List<Map<String, Object>> statisticsAgency(Map<String, Object> map) {
        Integer start = MapUtils.getInteger(map,"start",0);
        Integer pageSize = MapUtils.getInteger(map,"pageSize",0);

        List<Map<String,Object>> data =  reportStatisticsMapper.statisticsAgency(map);
        String agencyIds = "";
        Integer agencyId = null;
        Integer cooperativeModeId;
        //总计
        Map<String,Object> total = null;
        //剩余额度
        Double surplusQuota;
        //授信额度
        Double creditLimit;
        for (Map<String,Object> m: data){
            agencyId = MapUtils.getInteger(m,"agencyId",null);
            if(StringUtil.isBlank(agencyIds)){
                agencyIds = null==agencyId?"":"'"+agencyId+"'";
            } else {
                agencyIds += null==agencyId?"":",'"+agencyId+"'";
            }
            cooperativeModeId = MapUtils.getInteger(m,"cooperativeModeId",0);
            if(cooperativeModeId!=1){
                m.put("surplusQuota",0d);
                m.put("creditLimit",0d);
            }
            if(!m.containsKey("surplusQuota"))
                m.put("surplusQuota",0);
            if(!m.containsKey("creditLimit"))
                m.put("creditLimit",0);
            agencyId = MapUtils.getInteger(m,"agencyId",0);
            if(0==agencyId){
                m.put("loanAmount",0d);
                m.put("payMentAmount",0d);
                m.put("payCount",0);
                m.put("orderCount",0);
                m.put("income",0d);
            }
            if(1==cooperativeModeId&&0==start){
                if(null==total){
                    total = new HashMap<String,Object>(data.size());
                }
                surplusQuota = BigDecimal.valueOf(MapUtils.getDouble(m,"surplusQuota",0d))
                        .add(BigDecimal.valueOf(MapUtils.getDouble(total,"surplusQuota",0d))).doubleValue();
                total.put("surplusQuota",surplusQuota);
                creditLimit = BigDecimal.valueOf(MapUtils.getDouble(m,"creditLimit",0d))
                        .add(BigDecimal.valueOf(MapUtils.getDouble(total,"creditLimit",0d))).doubleValue();
                total.put("creditLimit",creditLimit);
            }
        }
        agencyIds = StringUtil.isBlank(agencyIds)?"--":agencyIds;
        map.put("agencyIds",agencyIds);

        //放款额
        Map<Integer,Object> loanAmountMap = new HashMap<Integer,Object>();
        //创收
        Map<Integer,Object> incomeMap = new HashMap<Integer,Object>();
        //放款量
        Map<Integer,Object> orderCountMap = new HashMap<Integer,Object>();
        Double loanAmount = 0d,income = 0d;
        Integer orderCount = 0;
        List<Map<String,Object>> totalLoan = reportStatisticsMapper.statisticsAgencyTotalLoan(map);
        for (Map<String,Object> m:totalLoan){
            agencyId = MapUtils.getInteger(m,"agencyId",0);

            loanAmount = MapUtils.getDouble(m,"loanAmount",0d);
            loanAmountMap.put(agencyId,loanAmount);

            income = MapUtils.getDouble(m,"income",0d);
            if(null!=income&&income>0){
                income = BigDecimal.valueOf(income)
                        .divide(BigDecimal.valueOf(10000d)).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            incomeMap.put(agencyId,income);

            orderCount = MapUtils.getInteger(m,"orderCount",0);
            orderCountMap.put(agencyId,orderCount);

            if(0==start){
                if(null==total){
                    total = new HashMap<String,Object>(data.size());
                }
                loanAmount = BigDecimal.valueOf(loanAmount)
                        .add(BigDecimal.valueOf(MapUtils.getDouble(total,"loanAmount",0d))).doubleValue();
                total.put("loanAmount",loanAmount);

                income = BigDecimal.valueOf(income)
                        .add(BigDecimal.valueOf(MapUtils.getDouble(total,"income",0d))).doubleValue();
                total.put("income",income);

                orderCount += MapUtils.getInteger(total,"orderCount",0);
                total.put("orderCount",orderCount);
            }
        }

        List<Map<String,Object>> totalPay = reportStatisticsMapper.statisticsAgencyTotalPay(map);
        //回款笔数
        Map<Integer,Object> payCountMap = new HashMap<Integer,Object>(null==totalPay?16:totalPay.size());
        //回款额
        Map<Integer,Object> payMentAmountMap = new HashMap<Integer,Object>(null==totalPay?16:totalPay.size());
        Double payMentAmount = 0d;
        Integer payCount = 0;
        for (Map<String,Object> m:totalPay){
            agencyId = MapUtils.getInteger(m,"agencyId",0);
            payMentAmount = MapUtils.getDouble(m,"payMentAmount",0d);
            payCount = MapUtils.getInteger(m,"payCount",0);
            payCountMap.put(agencyId,payCount);
            payMentAmountMap.put(agencyId,payMentAmount);
            if(0==start){
                if(null==total){
                    total = new HashMap<String,Object>(data.size());
                }
                payMentAmount = BigDecimal.valueOf(payMentAmount)
                        .add(BigDecimal.valueOf(MapUtils.getDouble(total,"payMentAmount",0d))).doubleValue();
                total.put("payMentAmount",payMentAmount);

                payCount += MapUtils.getInteger(total,"payCount",0);
                total.put("payCount",payCount);
            }
        }
        String loanAmountSort = MapUtils.getString(map,"loanAmountSort");
        String orderCountSort = MapUtils.getString(map,"orderCountSort");
        String incomeSort = MapUtils.getString(map,"incomeSort");
        String payCountSort = MapUtils.getString(map,"payCountSort");
        String payMentAmountSort = MapUtils.getString(map,"payMentAmountSort");
        String surplusQuotaSort = MapUtils.getString(map,"surplusQuotaSort");
        String creditLimitSort = MapUtils.getString(map,"creditLimitSort");
        data = orderByData(data, totalLoan, "agencyId", start, pageSize,null,loanAmountSort, orderCountSort, incomeSort);
        data = orderByData(data, totalPay, "agencyId", start, pageSize,null,payCountSort, payMentAmountSort);
        data = orderByData(data, null, "agencyId", start, pageSize, 1,surplusQuotaSort, creditLimitSort);
        for (Map<String,Object> m:data){
            agencyId = MapUtils.getInteger(m,"agencyId",0);
            payMentAmount = MapUtils.getDouble(payMentAmountMap,agencyId,0d);
            loanAmount = MapUtils.getDouble(loanAmountMap,agencyId,0d);
            income = MapUtils.getDouble(incomeMap,agencyId,0d);
            payCount = MapUtils.getInteger(payCountMap,agencyId,0);
            orderCount = MapUtils.getInteger(orderCountMap,agencyId,0);
            m.put("payMentAmount",payMentAmount);
            m.put("payCount",payCount);
            m.put("loanAmount",loanAmount);
            m.put("income",income);
            m.put("orderCount",orderCount);
        }
        if(0==start&&null!=data&&data.size()>0){
            if(null==total){
                total = new HashMap<String,Object>();
            }
            total.put("agencyName","总计");
            if(!total.containsKey("loanAmount"))
                total.put("loanAmount",0);
            if(!total.containsKey("income"))
                total.put("income",0);
            if(!total.containsKey("payCount"))
                total.put("payCount",0);
            if(!total.containsKey("orderCount"))
                total.put("orderCount",0);
            if(!total.containsKey("payMentAmount"))
                total.put("payMentAmount",0);
            if(!total.containsKey("surplusQuota"))
                total.put("surplusQuota",0);
            if(!total.containsKey("creditLimit"))
                total.put("creditLimit",0);
            List<Map<String,Object>> listData = new ArrayList<Map<String,Object>>();
            listData.add(total);
            listData.addAll(data);
            return listData;
        }
        return data;
    }

    @Override
    public Integer statisticsAgencyCount(Map<String, Object> map) {
        return reportStatisticsMapper.statisticsAgencyCount(map);
    }

    /**
     * 资金方统计
     *
     * @param map productCode(产品code)
     *            startTime(检索条件：开始时间),endTime(检索条件：结束时间)
     *            timeWhere=lastWeek(检索条件：上周)
     *            timeWhere=lastMonth(检索条件：上月)
     *            timeWhere=yesterday(检索条件：昨日)
     *            timeWhere=thisMonth(检索条件：当月)
     * @return fundId(资金id), fundName(资方名称)
     * fundLoanAmount(资方放款金额),payMentAmount(回款金额)
     * payCount(回款笔数),orderCount(业务单量)
     */
    @Override
    public List<Map<String, Object>> statisticsFund(Map<String, Object> map) {
        List<Map<String,Object>> data = reportStatisticsMapper.statisticsFund(map);
        Integer start = MapUtils.getInteger(map,"start",0);
        Integer pageSize = MapUtils.getInteger(map,"pageSize",0);

        String fundids = "";
        String fundid = "";

        for (Map<String,Object> m:data){
            fundid = MapUtils.getString(m,"fundId");
            if(StringUtil.isBlank(fundid))
                continue;
            if(StringUtil.isBlank(fundids)){
                fundids = "'"+fundid+"'";
            } else {
                fundids += ",'"+fundid+"'";
            }
        }
        fundids = StringUtil.isBlank(fundids)?"--":fundids;
        /**
         * 计算放款额与放款量
         */
        map.put("fundids",fundids);
        //回款额
        Double payMentAmount = 0d;
        //回款单量
        Integer payCount = 0;
        //放款单量
        Integer orderNoCount = 0;
        //放款额
        Double lendingAmount = 0d;
        //总计
        Map<String,Object> total = new HashMap<String,Object>();

        List<Map<String, Object>> listLoan = reportStatisticsMapper.statisticsFundLoanTotal(map);
        Map<String,Object> loanMap = new HashMap<String,Object>();
        Map<String,Object> loanCountMap = new HashMap<String,Object>();
        for (Map<String,Object> m:listLoan){
            fundid = MapUtils.getString(m,"fundId");

            orderNoCount = MapUtils.getInteger(m,"orderNoCount",0);
            loanCountMap.put(fundid,orderNoCount);

            lendingAmount = MapUtils.getDouble(m,"lendingAmount",0d);
            loanMap.put(fundid,lendingAmount);

            if(0==start){
                total.put("orderNoCount",MapUtils.getInteger(total,"orderNoCount",0)+orderNoCount);
                lendingAmount = BigDecimal.valueOf(MapUtils.getDouble(total,"fundLoanAmount",0d))
                        .add(BigDecimal.valueOf(lendingAmount)).doubleValue();
                total.put("fundLoanAmount",lendingAmount);
            }
        }

        /**
         * 计算回款额与回款订单量
         */
        List<Map<String,Object>> listPay = reportStatisticsMapper.statisticsFundPayTotal(map);
        Map<String,Object> payMap = new HashMap<String,Object>();
        Map<String,Object> payCountMap = new HashMap<String,Object>();


        for (Map<String,Object> m:listPay){
            fundid = MapUtils.getString(m,"fundId");
            payMentAmount = MapUtils.getDouble(m,"payMentAmount",0d);
            payMap.put(fundid,payMentAmount);
            payCount = MapUtils.getInteger(m,"payCount",0);
            payCountMap.put(fundid,payCount);

            if(0==start){
                total.put("payCount",MapUtils.getInteger(total,"payCount",0)+payCount);

                payMentAmount = BigDecimal.valueOf(MapUtils.getDouble(total,"payMentAmount",0d))
                        .add(BigDecimal.valueOf(payMentAmount)).doubleValue();
                total.put("payMentAmount",payMentAmount);
            }
        }

        String payMentAmountSort = MapUtils.getString(map,"payMentAmountSort");
        String payCountSort = MapUtils.getString(map,"payCountSort");
        String fundLoanAmountSort = MapUtils.getString(map,"fundLoanAmountSort");
        String orderNoCountSort = MapUtils.getString(map,"orderNoCountSort");

        data = orderByData(data,listPay,"fundId",start,pageSize,null,payMentAmountSort,payCountSort);
        data = orderByData(data,listLoan,"fundId",start,pageSize,null,fundLoanAmountSort,orderNoCountSort);

        for (Map<String,Object> m:data){
            fundid = MapUtils.getString(m,"fundId");
            payCount = MapUtils.getInteger(payCountMap,fundid,0);
            orderNoCount = MapUtils.getInteger(loanCountMap,fundid,0);
            lendingAmount = MapUtils.getDouble(loanMap,fundid,0d);
            payMentAmount = MapUtils.getDouble(payMap,fundid,0d);
            m.put("orderNoCount",orderNoCount);
            m.put("fundLoanAmount",lendingAmount);
            m.put("payMentAmount",payMentAmount);
            m.put("payCount",payCount);
        }

        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        if(0==start&&null!=data&&data.size()>0){
            total.put("fundName","总计");
            if(!total.containsKey("orderNoCount"))
                total.put("orderNoCount",0);
            if(!total.containsKey("fundLoanAmount"))
                total.put("fundLoanAmount",0d);
            if(!total.containsKey("payMentAmount"))
                total.put("payMentAmount",0d);
            if(!total.containsKey("payCount"))
                total.put("payCount",0);
            dataList.add(total);
        }
        dataList.addAll(data);
        return dataList;
    }

    /**
     * 实现列表排序
     * @param data 原始数据
     * @param sortData 排序参照数据
     * @param key 原始数据与排序参照数据 匹配key
     * @param start 列表开始索引
     * @param pageSize 列表展示长度
     * @param special 特殊字段
     * @param sort 排序字段
     * @return
     */
    public List<Map<String,Object>> orderByData(List<Map<String,Object>> data,
                                                List<Map<String,Object>> sortData,
                                                String key,
                                                Integer start,
                                                Integer pageSize,
                                                Integer special,
                                                String ... sort){
        if(null==data||data.size()<=0)
            return data;
        boolean isAsc = false,isSort = false,isSurplusQuotaSort = false,isCreditLimitSort = false;
        for (String s:sort){
            if(StringUtil.isNotBlank(s)){
                isSort = true;
                s = s.toLowerCase();
            }
            if("asc".equals(s)){
                isAsc = true;
            }
            if(isAsc&&isSort){
                break;
            }
        }
        int index = -1;
        List<Map<String,Object>> tmp = new ArrayList<Map<String,Object>>();
        if(null!=special&&1==special&&isSort){
            Iterator<Map<String, Object>> iterator = data.iterator();
            while (iterator.hasNext()){
                Map<String, Object> next = iterator.next();
                index++;
                if(index<start)
                    continue;
                tmp.add(next);
                if(tmp.size()>=pageSize)
                    break;
            }
            return tmp;
        }

        List<Map<String,Object>> orderByList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> exitsList = new ArrayList<Map<String,Object>>();
        String sortKey = "";
        if(isSort){
            for (Map<String,Object> p:sortData){
                Iterator<Map<String,Object>> it = data.iterator();
                while (it.hasNext()){
                    Map<String, Object> m = it.next();
                    sortKey = MapUtils.getString(m,key,"");
                    if(sortKey.equals(MapUtils.getString(p,key,""))){
                        orderByList.add(m);
                        it.remove();
                        break;
                    }
                }
            }
            exitsList.addAll(data);
        } else {
            return data;
        }

        if(isAsc){
            exitsList.addAll(orderByList);
            data = exitsList;
        } else {
            orderByList.addAll(exitsList);
            data = orderByList;
        }

        Iterator<Map<String, Object>> iterator = data.iterator();
        while (iterator.hasNext()){
            Map<String, Object> next = iterator.next();
            index++;
            if(index<start)
                continue;
            tmp.add(next);
            if(tmp.size()>=pageSize)
                break;
        }
        return tmp;
    }
    @Override
    public Integer statisticsFundCount(Map<String, Object> map) {
        return reportStatisticsMapper.statisticsFundCount(map);
    }

    /**
     * 下载个人目标创收模板
     *
     * @param map
     * @return
     */
    @Override
    public void downloadIncome(Map<String, Object> map,UserDto userDto,ExcelService excelService,HttpServletResponse response) throws Exception{
        Integer deptId = MapUtils.getInteger(map,"deptId",0);
        //获取部门
        List<Map<String, Object>> listDept = reportStatisticsMapper.selectDept(map);
        if(null==listDept||listDept.size()<=0)
            return;
        Map<String,Object> deptMap = new HashMap<String,Object>();
        for (Map<String,Object> m:listDept){
            Integer tmpDeptId = MapUtils.getInteger(m,"id",-1);
            if(tmpDeptId==deptId||tmpDeptId.equals(deptId)){
                deptMap.putAll(m);
                break;
            }
        }

        map.put("roleName", Enums.RoleEnum.CHANNEL_MANAGER.getName());
        //按城市分组(不再按部门分组,主要为了解决有多个部门的情况,但是会有重名的情况)
        map.put("deptId",0);
        List<UserDto> listUser = reportStatisticsMapper.selectRoleByAgencyIdRoleName(map);

        /* 去除二级部门20180529
        String name = MapUtils.getString(deptMap,"name");
        String pid = MapUtils.getString(deptMap,"pid");
        String pname = MapUtils.getString(deptMap,"pname");
        if("10000002".equals(pid)|| StringUtil.isBlank(pname)){
            pname = name;
        }*/
        String pname = MapUtils.getString(map,"cityName");
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String sheetName = "";
        String fileName = "创收目标报表.xls";
        String title = format.format(new Date())+"年";
        String tmpTitle;
        HSSFWorkbook writeWork = excelService.createWriteWork();
        try{
            HSSFCellStyle cellStyle = excelService.createCellStyle(writeWork, (short) 10);
            excelService.getBorderAll(cellStyle);
            HSSFSheet sheet;
            HSSFRow row;
            HSSFCell cell;
            UserDto tmpUser;
            for (int k=1;k<=12;k++){
                sheetName = k+"月";
                //tmpTitle = title+sheetName+"-"+pname+"-"+name+"目标统计";
                tmpTitle = title+sheetName+"-"+pname+"目标统计";
                sheet = createSheet(writeWork, sheetName, tmpTitle, excelService, listUser.size());
                for(int i=0;i<listUser.size();i++){
                    tmpUser = listUser.get(i);
                    row = sheet.createRow(i+3);
                    //设置行高度
                    row.setHeight((short) (25 * 16));
                    cell = row.createCell(0);
                    cell.setCellValue(i+1);
                    cell.setCellStyle(cellStyle);

                    if(i==0){
                        cell = row.createCell(1);
                        cell.setCellValue(pname);
                        cell.setCellStyle(cellStyle);
                    }
                    /*二级部门20180529
                    cell = row.createCell(2);
                    cell.setCellValue(name);
                    cell.setCellStyle(cellStyle);
                    */
                    cell = row.createCell(2);
                    cell.setCellValue(tmpUser.getName());
                    cell.setCellStyle(cellStyle);

                    cell = row.createCell(3);
                    cell.setCellStyle(cellStyle);

                    cell = row.createCell(4);
                    cell.setCellStyle(cellStyle);

                    cell = row.createCell(5);
                    cell.setCellStyle(cellStyle);
                    
                    if(i==(listUser.size()-1)){
                        row = sheet.createRow(i+4);
                        cell = row.createCell(0);
                        cell.setCellStyle(cellStyle);

                        cell = row.createCell(2);
                        cell.setCellValue("总计");
                        cell.setCellStyle(cellStyle);

                        cell = row.createCell(3);
                        cell.setCellStyle(cellStyle);

                        cell = row.createCell(4);
                        cell.setCellStyle(cellStyle);
                        
                        cell = row.createCell(5);
                        cell.setCellStyle(cellStyle);
                        
                        cell = row.createCell(1);
                        cell.setCellStyle(cellStyle);
                    }
                }

            }
            getBytes(fileName,writeWork,"application/vnd.ms-excel",response);

        } finally {
            writeWork = null;
        }
    }

    /**
     * 创建个人创收工作薄标题模板
     * @param workbook
     * @param sheetName
     * @param tile
     * @return
     */
    public HSSFSheet createSheet(HSSFWorkbook workbook,String sheetName,String tile,ExcelService excelService,int length){
        HSSFCellStyle cellStyle = excelService.createCellStyle(workbook, (short) 15);
        HSSFFont font = workbook.createFont();
        //设置粗体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        //创建工作表
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //合并单元格开始行
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, 5);
        sheet.addMergedRegion(cellRangeAddress);
        cellRangeAddress = new CellRangeAddress(3, length+3, 1, 1);
        sheet.addMergedRegion(cellRangeAddress);
        
        cellRangeAddress = new CellRangeAddress(length+3+2, length+3+3, 0, 5);
        sheet.addMergedRegion(cellRangeAddress);
        //创建行:第1行
        HSSFRow row = sheet.createRow(0);
        //设置行高度
        row.setHeight((short) (25 * 20));

        //创建单元格:第1列
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(tile);
        cell.setCellStyle(cellStyle);

        sheet.setColumnWidth(1,20 * 200);
        sheet.setColumnWidth(2,20 * 200);
        sheet.setColumnWidth(3,20 * 230);
        sheet.setColumnWidth(4,20 * 215);
        sheet.setColumnWidth(5,20 * 215);
        row = sheet.createRow(2);
        //设置行高度
        row.setHeight((short) (25 * 16));
        cell = row.createCell(0);
        cell.setCellValue("序号");
        //有右边框实线
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        //上边框实线
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("一级部门");

        /*
        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("二级级部门");
        */

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("姓名");

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("放款量目标（万）");

        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("创收目标（万）");
        
        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("目标订单量");
        
        //注释
        row = sheet.createRow(length+3+2);
        row.setHeight((short) (25 * 20));
        cell = row.createCell(0);
        cellStyle.setWrapText(true);
        cellStyle.setBorderTop(CellStyle.BORDER_NONE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("分公司目标报表下载后，请按照原格式进行填写，不允许变更表格内\r\n容或增加、修改、删除表格，否则会导致上传报表时发生错误");
        
        return sheet;
    }

    public void getBytes(String fileName, HSSFWorkbook workbook,String contentType,HttpServletResponse response)throws Exception{
        response.setContentType(StringUtil.isBlank(contentType)?MediaType.APPLICATION_OCTET_STREAM.getType():contentType);
        response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
    }

    /**
     * 上传个人创收目标文件并解析保存数据库
     *
     * @param file
     * @param map
     */
    @Override
    public void uploadIncome(MultipartFile file, Map<String, Object> map,UserDto userDto,ExcelService excelService,RespStatus result) throws Exception {
        String fileName = file.getOriginalFilename();
        String cityCode = MapUtils.getString(map,"cityCode");
        String cityName = MapUtils.getString(map,"cityName");
        Integer deptId = MapUtils.getInteger(map,"deptId");
        ByteArrayOutputStream byteArrayOutputStream = excelService.copyStream(file.getInputStream());
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Workbook readWork;
        try{
            readWork  = excelService.createReadWork(inputStream);
            List<Map<String,Object>> data = analyzeExcel(readWork,userDto,cityCode,cityName,deptId);
            map.put("roleName", Enums.RoleEnum.CHANNEL_MANAGER.getName());
            map.put("agencyId", userDto.getAgencyId());
            List<UserDto> userDtos = reportStatisticsMapper.selectRoleByAgencyIdRoleName(map);
            if(data.size()<=0){
                RespHelper.setFailRespStatus(result,"没有解析到数据!");
                return;
            }
            String name;
            for (Map<String,Object> t:data){
                name = MapUtils.getString(t,"name");
                for (UserDto u:userDtos){
                    if(u.getName().equals(name)){
                        t.put("uid",u.getUid());
                        break;
                    }
                }
            }
            RespDataObject<Map<String, Object>> upload = HttpUtil.upload(file.getBytes(), new String(fileName.getBytes("UTF-8"),"ISO-8859-1"), "application/vnd.ms-excel","创收目标报表");
            if(RespStatusEnum.FAIL.getCode().equals(upload.getCode())) {
                result.setCode(upload.getCode());
                result.setMsg(upload.getMsg());
                return;
            }
            Map<String, Object> mdata = upload.getData();
            mdata.put("createUid",userDto.getUid());
            mdata.put("cityCode",cityCode);
            mdata.put("cityName",cityName);
            mdata.put("deptId",deptId);
            mdata.put("effective",1);
            mdata.put("fileUrl",MapUtils.getString(mdata,"url"));
            //作废旧的
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("cityCode",cityCode);
            Map<String,Object> old = reportStatisticsMapper.selectIncomeFileByCityCode(param);
            if(MapUtils.isEmpty(old)) {
                reportStatisticsMapper.insertIncomFile(mdata);
            } else {
                mdata.put("id",MapUtils.getInteger(old,"id"));
                mdata.put("effective",1);
                old.put("createUid",userDto.getUid());
                reportStatisticsMapper.updateIncomeFile(mdata);
                reportStatisticsMapper.insertIncomeFileHistory(old);
            }
            param.put("updateUid",userDto.getUid());
            reportStatisticsMapper.cancelPersonalIncome(param);
            reportStatisticsMapper.insertPersonalIncome(data);
            RespHelper.setSuccessRespStatus(result);
        } finally {
            readWork = null;
            if(null!=inputStream){
                inputStream.close();
                inputStream = null;
            }
            if(null!=byteArrayOutputStream){
                byteArrayOutputStream.close();
                byteArrayOutputStream = null;
            }
        }
    }

    /**
     * 解析Excel
     * @param readWork
     * @param userDto
     * @param cityCode
     * @param cityName
     * @param deptId
     * @return
     */
    public List<Map<String,Object>> analyzeExcel(Workbook readWork,UserDto userDto,String cityCode,String cityName,Integer deptId){
        Sheet sheet;

        String sheetName ;
        String year;
        String mouth;

        int lastRowNum;
        int firstRowNum ;
        Row row;
        Cell cell;

        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        Map<String,Object> m = null;
        int numberOfSheets = readWork.getNumberOfSheets();
        for (int k=0;k<numberOfSheets;k++){
            sheet = readWork.getSheetAt(k);

            lastRowNum = sheet.getLastRowNum();
            firstRowNum = sheet.getFirstRowNum();

            sheetName = sheet.getSheetName();
            mouth = getMouth(sheetName);

            row = sheet.getRow(0);
            cell = row.getCell(0);
            year = getYear(cell.getStringCellValue());
            //去除了二级部门解析时索引往前移1位
            for (int i=firstRowNum+3;i<lastRowNum-2;i++){
                m = new HashMap<String,Object>();
                row = sheet.getRow(i);

                cell = row.getCell(2);
                m.put("name",cell.getStringCellValue());

                cell = row.getCell(3);
                m.put("loanAim",cell.getNumericCellValue());
                cell = row.getCell(4);
                m.put("incomeAim",cell.getNumericCellValue());
                cell = row.getCell(5);
                m.put("orderAim",cell.getNumericCellValue());
                m.put("createUid",userDto.getUid());
                m.put("cityCode",cityCode);
                m.put("cityName",cityName);
                m.put("deptId",deptId);
                m.put("month",StringUtil.isBlank(mouth)?k+1:mouth);
                m.put("year",year);
                data.add(m);
            }
        }
        return data;
    }
    public String getYear(String str){
        if(StringUtil.isNotBlank(str)
                &&str.matches("\\d{4}.*")){
            String tmp = str.substring(0,4);
            return tmp;
        }
        return "";
    }
    public String getMouth(String str){
        if(StringUtil.isNotBlank(str)
                &&str.matches("\\d{1,2}.*")){
            String tmp = str.substring(0,2);
            if(tmp.matches("\\d{2}")){
                return tmp;
            } else {
                tmp = tmp.substring(0,1);
            }
            return tmp;
        }
        return "";
    }

    /**
     * 删除目标报表
     *
     * @param map
     */
    @Override
    public void cancelIncomeFileByDeptId(Map<String, Object> map) {
        Map<String, Object> file = reportStatisticsMapper.selectIncomeFileByCityCode(map);
        if(MapUtils.isEmpty(file))
            return;
        file.put("createUid",MapUtils.getString(map,"createUid"));
        reportStatisticsMapper.cancelIncomeFile(map);
        reportStatisticsMapper.insertIncomeFileHistory(file);
    }

    /**
     * 查询个人创收概览
     *
     * @param map productCode(产品code),cityCode(城市code)
     *            startTime(开始时间),endTime(结束时间)
     *            timeWhere=lastWeek(检索条件：上周)
     *            timeWhere=lastMonth(检索条件：上月)
     *            timeWhere=yesterday(检索条件：昨日)
     *            timeWhere=thisMonth(检索条件：当月)
     * @return
     * orderCount(订单量), channelManagerName(渠道经理名称)
     * channelManagerUid(渠道经理uid),branchCompany(分公司)
     * productCode(产品code),productName(产品名称)
     * lendingAmount(放款金额),interest(利息)
     * fine(罚息),serviceCharge(服务费)
     * income(创收),loanAim(放款量目标)
     * incomeAim(创收目标),loanAimPercentage(放款量完成百分比)
     * incomeAimPercentage(创收目标完成百分比),
     * deptName(部门名称),deptId(部门id)
     */
    @Override
    public List<Map<String, Object>> selectPersonalView(Map<String, Object> map,List<UserDto> users,List<DictDto> dics) {
    	List<Map<String,Object>> ulist = reportStatisticsMapper.selectPersonalUser(map);
    	if(ulist==null|| ulist.size()==0) {
    		return null;
    	}
    	map.put("userList", ulist);
    	List<Map<String,Object>> list=null;
    	
    		list = reportStatisticsMapper.selectPersonalView(map);
    	
        List<Map<String,Object>> nlist=new ArrayList<>();
        map.remove("userList");
        Entry<String, Object> e=SortUtil.getSortType(map,new String[] {"orderCountSort","incomeSort","lendingAmountSort","interestSort","fineSort","serviceChargeSort"});
        for(Map<String,Object> u:ulist) {
        	for(Map<String,Object> r:list) {
        		if(MapUtils.getString(u,"channelManagerUid").equals(MapUtils.getString(r,"channelManagerUid"))) {
        			u.putAll(r);
        		}
        	}
        }
        SortUtil.sort(ulist, e.getKey().substring(0, e.getKey().length()-4),(String)e.getValue());
        list=ulist;
        List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
		int start=Integer.parseInt(map.get("start")+"");
		int pageSize=Integer.parseInt(map.get("pageSize")+"");
		int leng=0;
		if((start+10)>list.size() || pageSize >= list.size()){
			leng=list.size();
		}else{
			if(pageSize==10){
			  leng=start+10;
			}else if(pageSize==20){
			  leng=start+20;
			}else{
				leng=list.size();
			}
		}
		for (int i = start; i < leng; i++) {
			list2.add(list.get(i));
		}
		list=list2;
        List<Map<String,Object>> listIncome = reportStatisticsMapper.selectPersonalIncome(map);
        String channelManagerUid = "";
        String tmpChannelManagerUid = "";
        Double loanAim = 0d;
        Double incomeAim = 0d;
        Double orderAim = 0d;
        String cityCode = "";
        for (Map<String,Object> m:list){
            tmpChannelManagerUid = MapUtils.getString(m,"channelManagerUid");
            cityCode = MapUtils.getString(m,"cityCode");
            if(StringUtil.isBlank(tmpChannelManagerUid))
                continue;
            if(StringUtil.isBlank(channelManagerUid)){
                channelManagerUid = "'"+tmpChannelManagerUid+"'";
            } else {
                channelManagerUid += ",'"+tmpChannelManagerUid+"'";
            }
            for (DictDto d:dics){
                if(d.getCode().equals(cityCode)){
                    m.put("branchCompany",d.getName());
                    break;
                }
            }
            for (Map<String,Object> im:listIncome){
                if(tmpChannelManagerUid.equals(MapUtils.getString(im,"uid"))){
                    loanAim = MapUtils.getDouble(im,"loanAim",0d);
                    incomeAim = MapUtils.getDouble(im,"incomeAim",0d);
                    orderAim = MapUtils.getDouble(im,"orderAim",0d);
                    m.put("loanAim",loanAim);
                    m.put("incomeAim",incomeAim);
                    m.put("orderAim",orderAim);
                    break;
                }
            }
        }
        List<Map<String, Object>> otherList = null;
        if(StringUtil.isNotBlank(channelManagerUid)) {
            map.put("channelManagerUid", channelManagerUid);
            otherList = reportStatisticsMapper.selectPersonalViewOther(map);
        }
        if(null==otherList){
            otherList = new ArrayList<Map<String,Object>>();
        }
        compute(map,otherList,list);
        return list;
    }

    public Map<String,Object> compute(Map<String,Object> param,List<Map<String,Object>> otherList,List<Map<String,Object>> list){
        String sortKey = "";
        Map<String,Object> dataMap = null;
        if(StringUtil.isNotBlank(MapUtils.getString(param,"incomeSort"))){
            sortKey = "income";
        } else if(StringUtil.isNotBlank(MapUtils.getString(param,"lendingAmountSort"))){
            sortKey = "lendingAmount";
        } else if(StringUtil.isNotBlank(MapUtils.getString(param,"interestSort"))){
            sortKey = "interest";
        } else if(StringUtil.isNotBlank(MapUtils.getString(param,"fineSort"))){
            sortKey = "fine";
        } else if(StringUtil.isNotBlank(MapUtils.getString(param,"serviceChargeSort"))){
            sortKey = "serviceCharge";
        } else if(StringUtil.isNotBlank(MapUtils.getString(param,"orderCountSort"))){
            sortKey = "orderCount";
        }
        Map<String,Object> map = new HashMap<String,Object>();
        String channelManagerUid;
        BigDecimal bincome;
        BigDecimal blendingAmount;
        BigDecimal interest;
        BigDecimal fine;
        BigDecimal serviceCharge;

        //放款量目标
        Double loanAim;
        //放款量
        Double lendingAmount;
        //创收目标
        Double incomeAim;
        //目标单量
        Double orderAim;
        //创收
        Double income;

        Map<String,Object> countMap = new HashMap<String,Object>();
        int orderCount = 0;
        for (Map<String,Object> m:otherList){

            channelManagerUid = MapUtils.getString(m,"channelManagerUid","");
            if(StringUtil.isBlank(channelManagerUid))
                continue;

            dataMap = MapUtils.getMap(map,channelManagerUid);
            if(null==dataMap)
                dataMap = new HashMap<String,Object>();
            if(!sortKey.equals("income")) {
                bincome = BigDecimal.valueOf(MapUtils.getDoubleValue(dataMap, "income", 0d))
                        .add(BigDecimal.valueOf(MapUtils.getDoubleValue(m, "income", 0d)));
                dataMap.put("income",bincome);
            }
            if(!sortKey.equals("lendingAmount")) {
                blendingAmount = BigDecimal.valueOf(MapUtils.getDoubleValue(dataMap, "lendingAmount", 0d))
                        .add(BigDecimal.valueOf(MapUtils.getDoubleValue(m, "lendingAmount", 0d)));
                dataMap.put("lendingAmount",blendingAmount);
            }
            if(!sortKey.equals("interest")) {
                interest = BigDecimal.valueOf(MapUtils.getDoubleValue(dataMap, "interest", 0d))
                        .add(BigDecimal.valueOf(MapUtils.getDoubleValue(m, "interest", 0d)));
                dataMap.put("interest",interest);
            }
            if(!sortKey.equals("fine")) {
                fine = BigDecimal.valueOf(MapUtils.getDoubleValue(dataMap, "fine", 0d))
                        .add(BigDecimal.valueOf(MapUtils.getDoubleValue(m, "fine", 0d)));
                dataMap.put("fine",fine);
            }
            if(!sortKey.equals("serviceCharge")) {
                serviceCharge = BigDecimal.valueOf(MapUtils.getDoubleValue(dataMap, "serviceCharge", 0d))
                        .add(BigDecimal.valueOf(MapUtils.getDoubleValue(m, "serviceCharge", 0d)));
                dataMap.put("serviceCharge",serviceCharge);
            }
            if(!sortKey.equals("orderCount")) {
                if (countMap.containsKey(channelManagerUid)) {
                    orderCount = MapUtils.getInteger(countMap, channelManagerUid, 0);
                    countMap.put(channelManagerUid, orderCount + 1);
                } else {
                    countMap.put(channelManagerUid, 1);
                }
            }
            map.put(channelManagerUid,dataMap);
        }

        for (Map<String,Object> m:list){
            channelManagerUid = MapUtils.getString(m,"channelManagerUid","");
            Map<String,Object> tmpMap = MapUtils.getMap(map, channelManagerUid);
            if(null!=tmpMap) {
                m.putAll(tmpMap);
            }
            if(!sortKey.equals("orderCount")) {
                orderCount = MapUtils.getInteger(countMap, channelManagerUid, 0);
                m.put("orderCount", orderCount);
            }

            if(!m.containsKey("lendingAmount"))
                m.put("lendingAmount",0d);

            interest = BigDecimal.valueOf(MapUtils.getDouble(m,"interest",0d))
                        .divide(BigDecimal.valueOf(10000d)).setScale(4,BigDecimal.ROUND_HALF_UP);
            m.put("interest",interest);

            fine = BigDecimal.valueOf(MapUtils.getDouble(m,"fine",0d))
                    .divide(BigDecimal.valueOf(10000d)).setScale(4,BigDecimal.ROUND_HALF_UP);
            m.put("fine",fine);

            serviceCharge = BigDecimal.valueOf(MapUtils.getDouble(m,"serviceCharge",0d))
                    .divide(BigDecimal.valueOf(10000d)).setScale(4,BigDecimal.ROUND_HALF_UP);
            m.put("serviceCharge",serviceCharge);

            loanAim = MapUtils.getDouble(m,"loanAim",0d);
            lendingAmount = MapUtils.getDouble(m,"lendingAmount",0d);
            incomeAim = MapUtils.getDouble(m,"incomeAim",0d);
            income = MapUtils.getDouble(m,"income",0d);
            orderAim = MapUtils.getDouble(m,"orderAim",0d);
            orderCount = MapUtils.getIntValue(m,"orderCount",0);
            if(null!=income&&income>0){
                income = BigDecimal.valueOf(income)
                            .divide(BigDecimal.valueOf(10000d)).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
                m.put("income",income);
            }

            if(0==loanAim&&lendingAmount>0){
                m.put("loanAimPercentage","--");
            } else if((loanAim>0&&0==lendingAmount)||(0==loanAim&&0==lendingAmount)){
                m.put("loanAimPercentage",0);
            } else if(loanAim>0&&lendingAmount>0){
                BigDecimal tmp = BigDecimal.valueOf(lendingAmount)
                        .divide(BigDecimal.valueOf(loanAim),4,BigDecimal.ROUND_HALF_UP)
                        .multiply(BigDecimal.valueOf(100d)).setScale(2,BigDecimal.ROUND_HALF_UP);
                m.put("loanAimPercentage",tmp.toPlainString());
            }

            if(0==incomeAim&&income>0){
                m.put("incomeAimPercentage","--");
            } else if((incomeAim>0&&0==income)||(0==incomeAim&&0==income)){
                m.put("incomeAimPercentage",0);
            } else if(incomeAim>0&&income>0){
                BigDecimal tmp = BigDecimal.valueOf(income)
                        .divide(BigDecimal.valueOf(incomeAim),4,BigDecimal.ROUND_HALF_UP)
                        .multiply(BigDecimal.valueOf(100d)).setScale(2,BigDecimal.ROUND_HALF_UP);
                m.put("incomeAimPercentage",tmp.toPlainString());
            }
            
            if(0==orderAim&&orderCount>0){
                m.put("orderAimPercentage","--");
            } else if((orderAim>0&&0==orderCount)||(0==orderAim&&0==orderCount)){
                m.put("orderAimPercentage",0);
            } else if(orderAim>0&&orderCount>0){
                BigDecimal tmp = BigDecimal.valueOf(orderCount)
                        .divide(BigDecimal.valueOf(orderAim),4,BigDecimal.ROUND_HALF_UP)
                        .multiply(BigDecimal.valueOf(100d)).setScale(2,BigDecimal.ROUND_HALF_UP);
                m.put("orderAimPercentage",tmp.toPlainString());
            }
        }
        return map;
    }
    @Override
    public Integer selectPersonalViewCount(Map<String, Object> map) {
        return reportStatisticsMapper.selectPersonalViewCount(map);
    }

}
