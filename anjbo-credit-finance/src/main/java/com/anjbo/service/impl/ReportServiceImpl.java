package com.anjbo.service.impl;

import com.anjbo.bean.finance.ReportDto;
import com.anjbo.bean.finance.ReportEditRecordDto;
import com.anjbo.bean.finance.ReportReplyRecordDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RedisOperator;
import com.anjbo.dao.ReportEditRecordMapper;
import com.anjbo.dao.ReportMapper;
import com.anjbo.dao.ReportReplyRecordMapper;
import com.anjbo.service.ReportService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.UidUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/9/22.
 */
@Transactional
@Service
public class ReportServiceImpl implements ReportService{
    @Resource
    private ReportMapper reportMapper;
    @Resource
    private ReportEditRecordMapper reportEditRecordMapper;
    @Resource
    private ReportReplyRecordMapper reportReplyRecordMapper;

    public static int overIndex = 0;
    /**
     * 排序在redis中的key
     */
    private static final String  REPORT_TIME_VALIDITY_KEY = "REPORT_TIME_VALIDITY_KEY";

    @Override
    public List<ReportDto> list(ReportDto obj) {
        List<ReportDto> list = reportMapper.list(obj);
        if(StringUtils.isNotBlank(obj.getState())){
            list = mappingOrderData(list,obj);
        } else {
            mappingOrderData(list);
        }
        getReplyAndEditRecord(list);
        sortTop(list,obj);
        return list;
    }
    public void sortTop(List<ReportDto> list,ReportDto dto){
        if(null==list||list.size()<=0){
            return;
        }
        boolean isCreateTime = StringUtils.isBlank(dto.getEstimateOutLoanTimeOrderBy());
        List<ReportDto> notTop = new ArrayList<ReportDto>();
        List<ReportDto> top = new ArrayList<ReportDto>();
        TreeMap<Long,String> sm = null;
        if(StringUtils.isNotBlank(dto.getEstimateOutLoanTimeOrderBy())
                &&"DESC".equals(dto.getEstimateOutLoanTimeOrderBy())){
            sm = new TreeMap<Long,String>(Collections.<Long>reverseOrder());
        } else {
            sm = new TreeMap<Long,String>();
        }

        for (ReportDto obj:list){
            if(1==obj.getTop()&&isCreateTime){
                sm.put(obj.getCreateTime().getTime(),obj.getOrderNo());
            } else if(1==obj.getTop()&&!isCreateTime) {
                sm.put(dto.getEstimateOutLoanTime().getTime(),obj.getOrderNo());
            } else {
                notTop.add(obj);
            }
        }
        Set<Map.Entry<Long, String>> entries = sm.entrySet();
        for (Map.Entry<Long,String> en:entries){
            for (ReportDto obj:list){
                if(obj.getOrderNo().equals(en.getValue())){
                    top.add(obj);
                }
            }
        }
        top.addAll(notTop);
        list = top;
    }
    @Override
    public int listCount(ReportDto obj) {
        if(StringUtils.isNotBlank(obj.getState())){
            ReportDto tmp = obj;
            tmp.setPageSize(0);
            List<ReportDto> list = reportMapper.list(tmp);
            HttpUtil http = new HttpUtil();
            String orderNos = getOrderNos(list);
            OrderListDto order = new OrderListDto();
            order.setOrderNo(orderNos);
            order.setState(tmp.getState());
            List<OrderListDto> listOrder =  http.getList(Constants.LINK_CREDIT, "/credit/order/base/v/queryOrderBaseList",order, OrderListDto.class);
            if(null==listOrder||listOrder.size()<=0){
                return 0;
            }
            orderNos = getOrderNos(listOrder,true);
            obj.setOrderNo(orderNos);
            return reportMapper.listCount(obj);
        } else {
            return reportMapper.listCount(obj);
        }
    }
    public List<ReportDto> appList(ReportDto obj){
        if(null!=obj&&0==obj.getPageSize()){
            obj.setPageSize(10);
        }
        List<ReportDto> list = reportMapper.appList(obj);
        mappingOrderData(list);
        return list;
    }
    public List<ReportDto> mappingOrderData(List<ReportDto> list,ReportDto obj){
        if(null==list||list.size()<=0)return list;
        String orderNos = getOrderNos(list);
        HttpUtil http = new HttpUtil();
        OrderListDto order = new OrderListDto();
        order.setOrderNo(orderNos);
        order.setState(obj.getState());
        List<OrderListDto> listOrder =  http.getList(Constants.LINK_CREDIT, "/credit/order/base/v/queryOrderBaseList",order, OrderListDto.class);
        if(null==listOrder||listOrder.size()<=0){
            return Collections.EMPTY_LIST;
        }
        orderNos = getOrderNos(listOrder,true);
        obj.setOrderNo(orderNos);
        List<ReportDto> tmp = reportMapper.list(obj);
        mappingOrderData(tmp,listOrder);
        return tmp;
    }
    public void mappingOrderData(List<ReportDto> list,List<OrderListDto> order){
        for (ReportDto obj:list){
            Iterator<OrderListDto> iterator = order.iterator();
            while (iterator.hasNext()){
                OrderListDto tmpOrder = iterator.next();
                if (tmpOrder.getOrderNo().equals(obj.getOrderNo())){
                    obj.setProcessId(tmpOrder.getProcessId());
                    obj.setState(tmpOrder.getState());
                }
            }
        }
    }
    public String getOrderNos(List<ReportDto> list){
        String orderNos = "";
        for (ReportDto obj:list){
            if(StringUtils.isNotBlank(obj.getOrderNo())){
                orderNos += "'"+obj.getOrderNo()+"'"+",";
            }
        }
        if(orderNos.endsWith(",")) {
            orderNos = orderNos.substring(0, orderNos.length() - 1);
        }
        return orderNos;
    }
    public String getOrderNos(List<OrderListDto> list,boolean isOrder){
        String orderNos = "";
        for (OrderListDto obj:list){
            if(StringUtils.isNotBlank(obj.getOrderNo())){
                orderNos += "'"+obj.getOrderNo()+"'"+",";
            }
        }
        if(orderNos.endsWith(",")) {
            orderNos = orderNos.substring(0, orderNos.length() - 1);
        }
        return orderNos;
    }
    public String getOrderNos(List<ReportDto> list,SimpleDateFormat format){
        String orderNos = "";
        for (ReportDto obj:list){
            if(StringUtils.isNotBlank(obj.getOrderNo())){
                orderNos += "'"+obj.getOrderNo()+"'"+",";
            }
            if(null!=obj.getEstimateOutLoanTime()){
                obj.setEstimateOutLoanTimeStr(format.format(obj.getEstimateOutLoanTime()));
            }
            if(null!=obj.getCreateTime()){
                obj.setCreateTimeStr(format.format(obj.getCreateTime()));
            }
        }
        if(orderNos.endsWith(",")) {
            orderNos = orderNos.substring(0, orderNos.length() - 1);
        }
        return orderNos;
    }
    public void mappingOrderData(List<ReportDto> list){
        if(null==list||list.size()<=0)return;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String orderNos = getOrderNos(list,format);
        if(StringUtils.isBlank(orderNos)){
            return;
        }
        HttpUtil http = new HttpUtil();
        OrderListDto order = new OrderListDto();
        order.setOrderNo(orderNos);
        long start = System.currentTimeMillis();
        List<OrderListDto> listOrder =  http.getList(Constants.LINK_CREDIT, "/credit/order/base/v/queryOrderBaseList",order, OrderListDto.class);
        long end = System.currentTimeMillis();
        System.out.println("查询订单耗时:"+(end-start)+"ms");
        if(null==listOrder||listOrder.size()<=0)return;
        for (ReportDto obj:list){
            Iterator<OrderListDto> iterator = listOrder.iterator();
            while (iterator.hasNext()){
                OrderListDto tmpOrder = iterator.next();
                if (tmpOrder.getOrderNo().equals(obj.getOrderNo())){
                    obj.setProcessId(tmpOrder.getProcessId());
                    obj.setState(tmpOrder.getState());
                }
            }
        }
    }
    @Override
    public int insert(ReportDto record)throws ParseException {
        int success = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(StringUtils.isNotBlank(record.getEstimateOutLoanTimeStr())){
            if(record.getEstimateOutLoanTimeStr().matches("\\d{4}.\\d{2}.\\d{2}\\s\\d{2}$")){
                record.setEstimateOutLoanTimeStr(record.getEstimateOutLoanTimeStr()+":00");
            }
            Date date = format.parse(record.getEstimateOutLoanTimeStr());
            record.setEstimateOutLoanTime(date);
        }
        if(StringUtils.isBlank(record.getOrderNo())){
            String orderNo = UidUtil.generateOrderId();
            record.setOrderNo(orderNo);
            success = reportMapper.insert(record);
            ReportEditRecordDto edit = new ReportEditRecordDto();
            edit.setBeanColumn("estimateOutLoanTime");
            edit.setOrderNo(record.getOrderNo());
            edit.setCreateUid(record.getCreateUid());
            edit.setReportId(record.getId());
            edit.setStartVal(record.getEstimateOutLoanTimeStr());
            edit.setEndVal(record.getEstimateOutLoanTimeStr());
            edit.setColName("报备出款时间设置为");
            reportEditRecordMapper.insert(edit);
            return success;
        }
        ReportDto tmp =  reportMapper.detailByStatus(record);
        if(null==tmp){
            record.setRelationOrderNo(record.getOrderNo());
            ReportEditRecordDto edit = new ReportEditRecordDto();
            edit.setBeanColumn("estimateOutLoanTime");
            edit.setOrderNo(record.getOrderNo());
            edit.setCreateUid(record.getCreateUid());
            edit.setStartVal(record.getEstimateOutLoanTimeStr());
            edit.setEndVal(record.getEstimateOutLoanTimeStr());
            edit.setColName("报备出款时间设置为");
            success = reportMapper.insert(record);
            edit.setReportId(record.getId());
            reportEditRecordMapper.insert(edit);
           return success;
        } else {
            success = reportMapper.updateByStatus(record);
            record.setRelationOrderNo(record.getOrderNo());
            String endTime = null==record.getEstimateOutLoanTime()?"":format.format(record.getEstimateOutLoanTime());
            String startTime = null==tmp.getEstimateOutLoanTime()?"":format.format(tmp.getEstimateOutLoanTime());
            if(!endTime.equals(startTime)) {
                ReportEditRecordDto edit = new ReportEditRecordDto();
                edit.setBeanColumn("estimateOutLoanTime");
                edit.setReportId(tmp.getId());
                edit.setOrderNo(record.getOrderNo());
                edit.setCreateUid(record.getCreateUid());
                edit.setStartVal(startTime);
                edit.setEndVal(endTime);
                edit.setColName("报备出款时间修改为");
                reportEditRecordMapper.insert(edit);
            }
            return success;
        }
    }

    @Override
    public int update(ReportDto obj) {
        return reportMapper.update(obj);
    }

    @Override
    public ReportDto detail(ReportDto obj) {
        ReportDto dto = reportMapper.detail(obj);
        if(null==dto)return dto;
        getReplyAndEditRecord(dto);
        Map<String,Long> sort = getReportSort(true);
        Long index = getSortIndex(sort,dto);
        dto.setSort(index);
        return dto;
    }

    /**
     * 回复与修改记录
     * @param dto
     */
    public void getReplyAndEditRecord(ReportDto dto){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ReportReplyRecordDto reply = new ReportReplyRecordDto();
        String orderNo = "'"+dto.getOrderNo()+"'";
        reply.setOrderNo(orderNo);
        reply.setReportId(dto.getId());
        List<ReportReplyRecordDto> listReply = reportReplyRecordMapper.list(reply);
        if(null!=listReply&&listReply.size()>0){
            dto.setReportReplyRecord(listReply);
            String tmp = "";
            for (ReportReplyRecordDto obj:listReply){
                tmp += format.format(obj.getCreateTime())+"："+obj.getReplyContent()+"\n";
            }
            dto.setReportReplyRecordStr(tmp);
        }

        ReportEditRecordDto edit = new ReportEditRecordDto();
        edit.setReportId(dto.getId());
        edit.setOrderNo(orderNo);
        List<ReportEditRecordDto> listEdit =  reportEditRecordMapper.list(edit);
        if(null!=listEdit&&listEdit.size()>0){
            dto.setReportEditRecord(listEdit);
            String tmp = "";
            for(ReportEditRecordDto obj:listEdit){
                if(StringUtils.isNotBlank(obj.getEndVal())){
                    tmp += format.format(obj.getCreateTime())+":"
                            +obj.getColName();
                    if(StringUtils.isNotBlank(obj.getEndVal())){
                        tmp += "："+obj.getEndVal()+"\n";
                    } else {
                        tmp += "\n";
                    }
                }

            }
            dto.setReportEditRecordStr(tmp);
        }
    }

    /**
     * 修改报备时间记录
     * @param list
     */
    public void getReplyAndEditRecord(List<ReportDto> list){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(null==list||list.size()<=0)return;
        String orderNos = getOrderNos(list);
        ReportReplyRecordDto reply = new ReportReplyRecordDto();
        reply.setOrderNo(orderNos);
        List<ReportReplyRecordDto> listReply = reportReplyRecordMapper.list(reply);
        listReply = null==listReply?Collections.EMPTY_LIST:listReply;
        ReportEditRecordDto edit = new ReportEditRecordDto();
        edit.setOrderNo(orderNos);
        /**
         *获取所有指定订单号修改记录
         */
        List<ReportEditRecordDto> listEdit =  reportEditRecordMapper.list(edit);
        listEdit = null==listEdit?Collections.EMPTY_LIST:listEdit;
        long nowtime = System.currentTimeMillis();
        Map<String,Long> sort = getReportSort(nowtime,true);
        Long index = 0L;
        for(ReportDto dto:list){
        	try {
				dto.setDeptName(CommonDataUtil.getUserDtoByUidAndMobile(dto.getCreateUid()).getDeptName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            index = getSortIndex(sort,dto);
            dto.setSort(index);
            Iterator<ReportReplyRecordDto> iterator = listReply.iterator();
            String replyStr = "";
            while (iterator.hasNext()){
                ReportReplyRecordDto replyTmp = iterator.next();
                if(replyTmp.getReportId()==dto.getId()){
                    replyStr += format.format(replyTmp.getCreateTime())+"："+replyTmp.getReplyContent()+"<br/>";
                    iterator.remove();
                }
            }
            dto.setReportReplyRecordStr(replyStr);
            Iterator<ReportEditRecordDto> editIterator = listEdit.iterator();
            String editStr = "";
            while (editIterator.hasNext()){
                ReportEditRecordDto editTmp = editIterator.next();
                if(dto.getId()==editTmp.getReportId()){
                    editStr += format.format(editTmp.getCreateTime())+":"
                            +editTmp.getColName();
                    if(StringUtils.isNotBlank(editTmp.getEndVal())){
                        editStr += "："+editTmp.getEndVal()+"<br/>";
                    } else {
                        editStr += "<br/>";
                    }
                }
            }
            dto.setReportEditRecordStr(editStr);
        }
    }

    @Override
    public int insertEditRecord(ReportEditRecordDto obj) {
        return reportEditRecordMapper.insert(obj);
    }

    @Override
    public List<ReportEditRecordDto> listEditRecord(ReportEditRecordDto obj) {
        return reportEditRecordMapper.list(obj);
    }

    @Override
    public int insertReplyRecord(ReportReplyRecordDto obj) {
        return reportReplyRecordMapper.insert(obj);
    }

    @Override
    public List<ReportReplyRecordDto> listReplyRecord(ReportReplyRecordDto obj) {
        return reportReplyRecordMapper.list(obj);
    }
    public int cancelReport(String orderNo){
        ReportDto dto = new ReportDto();
        dto.setOrderNo(orderNo);
        dto = reportMapper.detailByStatus(dto);
        return reportMapper.cancelReportById(dto.getId());
    }
    public int cancelReport(ReportDto dto, UserDto user){
        ReportDto tmp = reportMapper.detailByStatus(dto);
        ReportEditRecordDto edit = new ReportEditRecordDto();
        edit.setBeanColumn("estimateOutLoanTime");
        edit.setOrderNo(dto.getOrderNo());
        edit.setCreateUid(user.getUid());
        edit.setReportId(tmp.getId());
        edit.setStartVal(dto.getEstimateOutLoanTimeStr());
        edit.setColName("取消出款报备");
        reportEditRecordMapper.insert(edit);
        int success = reportMapper.cancelReportById(tmp.getId());
        return success;
    }

    public Map<String,Object> listMap(ReportDto obj){
        List<ReportDto> list = reportMapper.list(obj);
        if(null==list||list.size()<=0)return Collections.EMPTY_MAP;
        Map<String,Object> map = new HashMap<String,Object>();
        for (ReportDto dto:list){
            compareDate(map,dto);
        }
        return map;
    }
    public void compareDate(Map<String,Object> map,ReportDto obj){
        Integer status = MapUtils.getInteger(map,obj.getOrderNo(),-1);
        if(status==-1){
            map.put(obj.getOrderNo(),obj.getStatus());
            map.put("createTime",obj.getCreateTime().getTime());
            return;
        }
        Long date = MapUtils.getLong(map,"createTime",0L);
        if(obj.getCreateTime().getTime()>date){
            map.put(obj.getOrderNo(),obj.getStatus());
            map.put("createTime",obj.getCreateTime().getTime());
        }
    }
    public ReportDto detailByStatus(ReportDto obj){
        ReportDto dto = reportMapper.detailByStatus(obj);
        if(null==dto)return dto;
        getReplyAndEditRecord(dto);
        Map<String,Long> sort = getReportSort(true);
        Long index = getSortIndex(sort,dto);
        dto.setSort(index);
        return dto;
    }

    public int updateByStatus(ReportDto obj){
        return reportMapper.updateByStatus(obj);
    }

    public ReportDto detailById(ReportDto obj){
        return reportMapper.detailById(obj);
    }

    /**
     * 获取报备列表 如果status==null则获取未报备列表
     * @param status 报备状态0:初始化1:已放款,2,未放款,3:报备撤销
     * @return ReportDto 只查询createTime,estimateOutLoanTime,orderNo,financeOutLoanTime,top字段
     */
    public List<ReportDto> listReportByStatus(Integer status){
        if(null==status){
            status = 2;
        }
        ReportDto dto = new ReportDto();
         dto.setStatus(status);
        List<ReportDto> list = reportMapper.listReportByStatus(dto);
        return list;
    }

    /**
     * 获取当天报备列表 如果status==null则获取未报备列表
     * @param status 报备状态0:初始化1:已放款,2,未放款,3:报备撤销
     * @return ReportDto 只查询createTime,estimateOutLoanTime,orderNo,financeOutLoanTime,top字段
     */
    public List<ReportDto> listReportByStatusToday(Integer status){
        if(null==status){
            status = 2;
        }
        ReportDto dto = new ReportDto();
        dto.setStatus(status);
        List<ReportDto> list = reportMapper.listReportByStatusToday(dto);
        return list;
    }

    /**
     * 获取报备列表
     * @param status 报备状态0:初始化1:已放款,2,未放款,3:报备撤销 status==null时,status=2
     * @param isToday 是否查询当天的报备
     * @return
     */
    public List<ReportDto> listReport(Integer status,boolean isToday){
        List<ReportDto> list = null;
        if(isToday){
            list = listReportByStatusToday(status);
        } else {
            list = listReportByStatus(status);
        }
        return list;
    }
    /**
     * 获取排名 集合
     * @param nowtime 当前时间
     * @param isToday 是否查询当天时间报备信息
     * @return 如果没有则返回空集合
     */
    public Map<String,Long> getReportSort(long nowtime,boolean isToday){
        List<ReportDto> list  = listReport(null,isToday);
        if(null==list||list.size()<=0){
            return Collections.EMPTY_MAP;
        }
        return sort(list,nowtime);
    }

    /**
     * 获取排名 集合
     * @return
     */
    public Map<String,Long> getReportSort(boolean isToday){
        Map<String,Long> sort = getReportSort(0,isToday);
        return sort;
    }

    /**
     * 获取排名
     * @param sort 排序集合
     * @param dto 出款报备对象
     * @return 如果排序集合或者出款报备为null或者没有获取到排名返回为-1
     */
    public Long getSortIndex(Map<String,Long> sort,ReportDto dto){
        if(null==sort||null==dto||(null!=dto&&null==dto.getEstimateOutLoanTime())){
            return -1L;
        }
        Long index = MapUtils.getLong(sort,dto.getOrderNo(),-1L);
        return index;
    }
    /**
     * 比较两个时间与现在的时间进行比较,
     * 如果相等返回now,d1近则返回d1,d2近则返回d2,两个时间为空则返回null;
     * @param d1
     * @param d2
     * @return
     */
    public Date compareDate(Date now,Date d1,Date d2){
        if(null==d1&&null==d2){
            return null;
        } else if(d1.getTime()==d2.getTime()){
            return  now;
        }
        long differ1 = d1.getTime()-now.getTime();
        long differ2 = d2.getTime()-now.getTime();
        if(differ1>differ2){
            return d2;
        } else {
            return d1;
        }
    }

    /**
     * 清除在redis中的报备排队
     * @param key
     */
    public void clearRedisReportSort(String key){
        if(StringUtils.isNotBlank(key)) {
            RedisOperator.delete(key);
        }
    }


    /**
     * 删除指定key,同时在指定key之后的排名往上加1
     * @param key
     * @param sort
     */
    public void deleteByKey(String key,Map<String,Long> sort){
        Long remove = sort.remove(key);
        if(null!=remove&&!sort.containsValue(remove)){
            sortForward(remove,sort);
        }
    }

    /**
     * 将排序好的添加到redis中
     * @param sort
     * @param key
     */
    public void addSort(Map<String,Long> sort,String key){
        RedisOperator.addToListLeft(key,sort);
    }

    /**
     * 排序向前进一位,如果val(排名)的值不为null则val后面的向前一位
     * @param val
     * @param sort
     */
    public boolean sortForward(Long val,Map<String,Long> sort){
        Set<Map.Entry<String,Long>> set = sort.entrySet();
        Long index = 0L;
        boolean isOver = false;
        for (Map.Entry<String,Long> m:set){
            index = m.getValue()-1;
            if(null!=val&&val<m.getValue()){
                sort.put(m.getKey(),index);
                isOver = true;
            } else if(null==val){
                sort.put(m.getKey(),index);
            }
        }
        return isOver;
    }

    /**
     * 排序向后退一位,如果val(排名)的值不为null则val后面的后退一位(默认一位)
     * @param val 当前位置
     * @param sort
     * @param num (后退位数)
     */
    public void sortBackward(Long val,Integer num,Map<String,Long> sort){
        if(null==num){
            num = 1;
        }
        Set<Map.Entry<String,Long>> set = sort.entrySet();
        Long index = 0L;
        for (Map.Entry<String,Long> m:set){
            index = m.getValue()+num;
            if(null!=val&&val<m.getValue()){
                sort.put(m.getKey(),index);
            } else if(null==val){
                sort.put(m.getKey(),index);
            }
        }
    }
    /**
     * 排序向后退一位,如果val(排名)的值不为null则val后面的后退一位(默认一位)
     * @param val 当前位置
     * @param sort
     * @param num (后退位数)
     */
    public void sortBackwardByLongKey(Long val,Integer num,Map<Long,Long> sort){
        if(null==num){
            num = 1;
        }
        Set<Map.Entry<Long,Long>> set = sort.entrySet();
        Long index = 0L;
        for (Map.Entry<Long,Long> m:set){
            index = m.getValue()+num;
            if(null!=val&&val<m.getValue()){
                sort.put(m.getKey(),index);
            } else if(null==val){
                sort.put(m.getKey(),index);
            }
        }
    }
    /**
     * 排序向后退一位,如果val(排名)的值不为null则val后面的后退一位(默认一位)
     * @param val 当前位置
     * @param sort
     * @param num (后退位数)
     */
    public void sortBackwardByLongVal(Long val,Integer num,Map<Long,Long> sort){
        if(null==num){
            num = 1;
        }
        Set<Map.Entry<Long,Long>> set = sort.entrySet();
        Long index = 0L;
        for (Map.Entry<Long,Long> m:set){
            index = m.getKey()+num;
            if(null!=val&&val<m.getValue()){
                sort.put(m.getKey(),index);
            } else if(null==val){
                sort.put(m.getKey(),index);
            }
        }
    }

    /**
     * 新增出款报备重新排序
     * @param dto
     */
    public Map<String,Long> insertSort(ReportDto dto){
        List<ReportDto> list = listReportByStatus(null);
        if(null!=list&&list.size()>0){
            list.add(dto);
        } else {
            Map<String,Long> sort = new HashMap<String,Long>();
            sort.put(dto.getOrderNo(),1L);
            return sort;
        }
        return sort(list,System.currentTimeMillis());
    }


    public void removeNullSort(Map<String,Long> sort,long size){
        long len = sort.size()+size;
        boolean isOver = false;
        for (long i=size;i<=len;i++){
            if(!sort.containsValue(i)){
                isOver = sortForward(i-1,sort);
                i=size;
                overIndex++;
                if(!isOver||size==overIndex){
                    break;
                }
            }
        }

    }


    public Map<String,Long> sort(List<ReportDto> list,long nowtime){

        long now = 0;
        if(0<=nowtime){
            now = System.currentTimeMillis();
        } else {
            now = nowtime;
        }
        /**
         * 存放排序好的数据
         */
        Map<String,Long> sort = new HashMap<String,Long>();
        /**
         * 存放未过期的数据
         */
        Map<Long,String> link = new  TreeMap<Long,String>();
        /**
         * 存放已过期的数据
         */
        Map<Long,String> over = new  TreeMap<Long,String>();
        /**
         * 存放排队的索引
         * key为时间差,值为排队位置
         */
        Map<Long,Long> sortIndex = new HashMap<Long,Long>();
        /**
         * 存放重复数据(key为时间差,value为重复数据的Map)
         */
        Map<Long,Map<String,Long>> existMap = new HashMap<Long,Map<String,Long>>();
        /**
         * 重复数据(key为订单号,value为提交时间)
         */
        Map<String,Long> existTime = null;

        Map<String,Long> tmp = new HashMap<String,Long>();
        /**
         * 存放置顶的数据
         */
        Map<String,ReportDto> topMap = new HashMap<String,ReportDto>();

        Long distance = 0L;
        String value = null;

        for (ReportDto dto:list){
            if(null==dto||null==dto.getEstimateOutLoanTime()){
                System.out.println("不参与排队:"+dto.getOrderNo());
                continue;
            }
            distance = dto.getEstimateOutLoanTime().getTime()-now;
            tmp.put(dto.getOrderNo(),dto.getCreateTime().getTime());
            if(null!=distance&&0>distance){
               value = over.put(distance,dto.getOrderNo());
            } else {
               value = link.put(distance,dto.getOrderNo());
            }
            if(null!=value){
                existTime = MapUtils.getMap(existMap,distance);
                if(null==existTime){
                    existTime = new HashMap<String,Long>();
                }
                existTime.put(dto.getOrderNo(),dto.getCreateTime().getTime());
                existTime.put(value,MapUtils.getLong(tmp,value));
                existMap.put(distance,existTime);
            }
            if(1==dto.getTop()){
                topMap.put(dto.getOrderNo(),dto);
            }
            sort.put(dto.getOrderNo(),0L);
        }

        /**
         * 处理报备时间未过期的排队情况
         */
        Long index = 1L;
        Set<Map.Entry<Long, String>> entries = link.entrySet();
        for (Map.Entry<Long,String> m:entries){
            sort.put(m.getValue(),index);
            sortIndex.put(m.getKey(),index);
            index++;
        }
        /**
         * 处理报备时间过期后的排队情况
         */
        if(over.size()>0){
            entries = over.entrySet();
            for (Map.Entry<Long,String> m:entries){
                sort.put(m.getValue(),index);
                sortIndex.put(m.getKey(),index);
                index++;
            }
        }
        if(null!=existMap&&existMap.size()>0){
            repeatSort(sort,sortIndex,existMap,nowtime);
        }

        /**
         * 有置顶的数据修改其排位
         */
        if(topMap.size()>0){
            Set<Map.Entry<String, ReportDto>> topIt = topMap.entrySet();
            Map<Long,String> topTree = new TreeMap<Long,String>();
            for (Map.Entry<String,ReportDto> en:topIt){
                index =  MapUtils.getLong(sort,en.getKey(),-1L);
                if(index>0) {
                    topTree.put(index, en.getKey());
                }
            }
            sortBackward(0L,topTree.size(),sort);
            Set<Map.Entry<Long, String>> topTreeIt = topTree.entrySet();
            index = 1L;
            for (Map.Entry<Long,String> en:topTreeIt){
                sort.put(en.getValue(),index);
                System.out.println("原先排位:"+en.getKey()+",置顶后:"+index);
                index++;
            }
            System.out.println("置顶改变排位数量为:"+topTree.size());
            overIndex = 0;
            removeNullSort(sort,topTreeIt.size());
        }
        Set<Map.Entry<String, Long>> tmpSorts = sort.entrySet();
        Map<Long,String> t = new TreeMap<Long,String>();
        String v = "";
        Long maxSort = 0L;
        for (Map.Entry<String,Long> en:tmpSorts){
            v = t.put(en.getValue(),en.getKey());
            if(StringUtils.isNotBlank(v)){
                System.out.println("重复排队:"+en.getKey()+"----"+en.getValue());
            }
            if(en.getValue()>maxSort){
                maxSort = en.getValue();
            }
        }
        System.out.println("最大排队位置为:"+maxSort);
        System.out.println("排队数量为:"+sort.size());
        System.out.println("参与排队数量为:"+list.size());
        return sort;
    }

    /**
     *
     * @param sort 排序的报备
     * @param sortIndex 存放排队的索引(key为时间差,value为排序位置)
     * @param existMap 重复的数据(key=时间差,value为存放重复的数据Map)
     */
    private void repeatSort(Map<String,Long> sort,Map<Long,Long> sortIndex,Map<Long,Map<String,Long>> existMap,Long nowtime){
        if(null==nowtime){
            nowtime = System.currentTimeMillis();
        }
        Long index = -1L;
        Set<Map.Entry<Long, Map<String, Long>>> entries = existMap.entrySet();
        for (Map.Entry<Long,Map<String,Long>> en:entries){
            index = MapUtils.getLong(sortIndex,en.getKey(),-1L);
            if(index>0) {
                repeatSort(index, en.getValue(),sort,nowtime,sortIndex);
            } else {
                continue;
            }
        }
    }

    /**
     *
     * @param index 当前排队位置
     * @param map 重复的数据
     * @param sort 排序好的数据
     * @param newtime 当前时间
     */
    private void repeatSort(Long index,Map<String,Long> map,Map<String,Long> sort,Long newtime,Map<Long,Long> sortIndex){
        Long tmpSortIndex = index;

        Set<Map.Entry<String,Long>> entries = map.entrySet();
        TreeMap<Long,String> tmpSort = new TreeMap<Long,String>(Collections.<Long>reverseOrder());
        /**
         * 重复
         */
        Map<String,Long> exist = new HashMap<String,Long>();
        String value = "";
        for (Map.Entry<String,Long> en:entries){
            value = tmpSort.put(newtime-en.getValue(),en.getKey());
            if(StringUtils.isNotBlank(value)){
                exist.put(en.getKey(),newtime-en.getValue());
            }
        }
        Set<Map.Entry<Long,String>> tmpentries = tmpSort.entrySet();
        Long tmpIndex = 0L;
        sortBackward(tmpSortIndex,tmpSort.size()-1,sort);
        sortBackwardByLongKey(tmpSortIndex,tmpSort.size()-1,sortIndex);

        for (Map.Entry<Long,String> en:tmpentries){
            if(0L==tmpIndex){
                sort.put(en.getValue(),index);
            } else {
                sort.put(en.getValue(),index);
            }
            index++;
            tmpIndex++;
        }
        /**
         * 如果重复则获取重复的排队位置
         */
        if(exist.size()>0){
            Set<Map.Entry<String, Long>> existIt = exist.entrySet();
            for (Map.Entry<String,Long> en:existIt){
                String orderNo = MapUtils.getString(tmpSort,en.getValue());
                sort.put(en.getKey(),MapUtils.getLong(sort,orderNo));
            }
        }
    }
}
