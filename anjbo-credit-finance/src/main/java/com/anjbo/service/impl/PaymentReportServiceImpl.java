package com.anjbo.service.impl;

import com.anjbo.bean.finance.PaymentReportDto;
import com.anjbo.bean.finance.ReportEditRecordDto;
import com.anjbo.bean.finance.ReportReplyRecordDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.dao.PaymentreportMapper;
import com.anjbo.dao.ReportEditRecordMapper;
import com.anjbo.dao.ReportReplyRecordMapper;
import com.anjbo.service.PaymentReportService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.UidUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/1/24.
 */
@Service
public class PaymentReportServiceImpl implements PaymentReportService {

    @Resource
    private PaymentreportMapper paymentreportMapper;
    @Resource
    private ReportEditRecordMapper reportEditRecordMapper;
    @Resource
    private ReportReplyRecordMapper reportReplyRecordMapper;

    @Override
    public List<PaymentReportDto> list(PaymentReportDto obj) {
        List<PaymentReportDto> list = paymentreportMapper.list(obj);
        if(StringUtils.isNotBlank(obj.getState())){
            list = mappingOrderData(list,obj);
        } else {
            mappingOrderData(list);
        }
        getReplyAndEditRecord(list);
        return list;
    }

    @Override
    public Integer listCount(PaymentReportDto obj) {
        if(StringUtils.isNotBlank(obj.getState())){
            PaymentReportDto tmp = obj;
            tmp.setPageSize(0);
            List<PaymentReportDto> list = paymentreportMapper.list(tmp);
            String orderNos = getOrderNos(list);
            List<OrderListDto> listOrder = getOrderData(orderNos,obj);
            orderNos = getOrderNos(listOrder,true);
            obj.setOrderNo(orderNos);
            return paymentreportMapper.listCount(obj);
        } else {
            return paymentreportMapper.listCount(obj);
        }
    }

    @Override
    public List<PaymentReportDto> appList(PaymentReportDto obj) {
        if(null!=obj
                ||0==obj.getPageSize()){
            obj.setPageSize(10);
        }
        List<PaymentReportDto> list = paymentreportMapper.appList(obj);
        mappingOrderData(list);
        return list;
    }

    @Override
    public int insert(PaymentReportDto obj) throws ParseException {
        int success = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(StringUtils.isNotBlank(obj.getEstimateInLoanTimeStr())){
            if(obj.getEstimateInLoanTimeStr().matches("\\d{4}.\\d{2}.\\d{2}\\s\\d{2}$")){
                obj.setEstimateInLoanTimeStr(obj.getEstimateInLoanTimeStr()+":00");
            }
            Date date = format.parse(obj.getEstimateInLoanTimeStr());
            obj.setEstimateInLoanTime(date);
        }
        if(StringUtils.isBlank(obj.getOrderNo())){
            String orderNo = UidUtil.generateOrderId();
            obj.setOrderNo(orderNo);
            success = paymentreportMapper.insert(obj);
            ReportEditRecordDto edit = new ReportEditRecordDto();
            edit.setBeanColumn("estimateInLoanTime");
            edit.setOrderNo(obj.getOrderNo());
            edit.setCreateUid(obj.getCreateUid());
            edit.setReportId(obj.getId());
            edit.setStartVal(obj.getEstimateInLoanTimeStr());
            edit.setEndVal(obj.getEstimateInLoanTimeStr());
            edit.setColName("预计回款时间设置为");
            reportEditRecordMapper.insert(edit);
            return success;
        }
        PaymentReportDto tmp =  paymentreportMapper.detailByStatus(obj);
        if(null==tmp){
            obj.setRelationOrderNo(obj.getOrderNo());
            ReportEditRecordDto edit = new ReportEditRecordDto();
            edit.setBeanColumn("estimateOutLoanTime");
            edit.setOrderNo(obj.getOrderNo());
            edit.setCreateUid(obj.getCreateUid());
            edit.setStartVal(obj.getEstimateInLoanTimeStr());
            edit.setEndVal(obj.getEstimateInLoanTimeStr());
            edit.setColName("预计回款时间设置为");
            success = paymentreportMapper.insert(obj);
            edit.setReportId(obj.getId());
            reportEditRecordMapper.insert(edit);
            return success;
        } else {
            success = paymentreportMapper.updateByStatus(obj);
            obj.setRelationOrderNo(obj.getOrderNo());
            String endTime = null==obj.getEstimateInLoanTime()?"":format.format(obj.getEstimateInLoanTime());
            String startTime = null==tmp.getEstimateInLoanTime()?"":format.format(tmp.getEstimateInLoanTime());
            if(!endTime.equals(startTime)) {
                ReportEditRecordDto edit = new ReportEditRecordDto();
                edit.setBeanColumn("estimateOutLoanTime");
                edit.setReportId(tmp.getId());
                edit.setOrderNo(obj.getOrderNo());
                edit.setCreateUid(obj.getCreateUid());
                edit.setStartVal(startTime);
                edit.setEndVal(endTime);
                edit.setColName("预计回款时间修改为");
                reportEditRecordMapper.insert(edit);
            }
            return success;
        }
    }

    @Override
    public int update(PaymentReportDto obj) {
        return paymentreportMapper.update(obj);
    }

    /**
     * 根据订单号查询最新一条详情
     *
     * @param obj
     * @return
     */
    @Override
    public PaymentReportDto detail(PaymentReportDto obj) {
        PaymentReportDto tmp = paymentreportMapper.detail(obj);
        mappingOrderData(tmp);
        return tmp;
    }

    @Override
    public PaymentReportDto detailById(PaymentReportDto obj) {
        PaymentReportDto tmp =  paymentreportMapper.detailById(obj);
        mappingOrderData(tmp);
        return tmp;
    }

    /**
     * 根据订单号查询不为撤销状态的最新回款报备
     *
     * @param obj
     * @return
     */
    @Override
    public PaymentReportDto detailByStatus(PaymentReportDto obj) {
        PaymentReportDto tmp = paymentreportMapper.detailByStatus(obj);
        if(null==tmp)return tmp;
        mappingOrderData(tmp);
        getReplyAndEditRecord(tmp);
        return tmp;
    }

    @Override
    public int cancelPaymentReport(String orderNo) {
        return paymentreportMapper.cancelPaymentReport(orderNo);
    }

    @Override
    public int cancelPaymentReportById(Integer id) {
        return paymentreportMapper.cancelPaymentReportById(id);
    }

    @Override
    public List<PaymentReportDto> listPaymentReportByStatus(PaymentReportDto obj) {
        if(null==obj||null==obj.getStatus()){
            obj = null==obj?new PaymentReportDto():obj;
            obj.setStatus(2);
        }
        return paymentreportMapper.listPaymentReportByStatus(obj);
    }


    public List<PaymentReportDto> mappingOrderData(List<PaymentReportDto> list, PaymentReportDto obj){
        if(null==list||list.size()<=0)return list;
        String orderNos = getOrderNos(list);
        List<OrderListDto> listOrder = getOrderData(orderNos,obj);
        if(null==listOrder||listOrder.size()<=0){
            return Collections.EMPTY_LIST;
        }
        orderNos = getOrderNos(listOrder,true);
        obj.setOrderNo(orderNos);
        List<PaymentReportDto> tmp = paymentreportMapper.list(obj);
        mappingOrderData(tmp,listOrder);
        return tmp;
    }
    public String getOrderNos(List<PaymentReportDto> list){
        String orderNos = "";
        for (PaymentReportDto obj:list){
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
    public String getOrderNos(List<PaymentReportDto> list,SimpleDateFormat format){
        String orderNos = "";
        for (PaymentReportDto obj:list){
            if(StringUtils.isNotBlank(obj.getOrderNo())){
                orderNos += "'"+obj.getOrderNo()+"'"+",";
            }
            if(null!=obj.getEstimateInLoanTime()){
                obj.setEstimateInLoanTimeStr(format.format(obj.getEstimateInLoanTime()));
            }
        }
        if(orderNos.endsWith(",")) {
            orderNos = orderNos.substring(0, orderNos.length() - 1);
        }
        return orderNos;
    }
    /**
     * 回复与修改记录
     * @param dto
     */
    public void getReplyAndEditRecord(PaymentReportDto dto){
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
    public void getReplyAndEditRecord(List<PaymentReportDto> list){
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
        //long nowtime = System.currentTimeMillis();
       // Map<String,Long> sort = getReportSort(nowtime);
       // Long index = 0L;
        for(PaymentReportDto dto:list){
        	try {
				dto.setDeptName(CommonDataUtil.getUserDtoByUidAndMobile(dto.getCreateUid()).getDeptName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //index = getSortIndex(sort,dto);
            //dto.setSort(index);
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
            if((null==listReply||listReply.size()<=0)&&(null==listEdit||listEdit.size()<=0)){
                break;
            }
        }
    }
    public int updateByStatus(PaymentReportDto obj){
        return paymentreportMapper.updateByStatus(obj);
    }
    /**
     * 获取排名 集合
     * @return
     */
    public Map<String,Long> getReportSort(){
        Map<String,Long> sort = getReportSort(0);
        return sort;
    }

    /**
     * 获取排名
     * @param sort 排序集合
     * @param dto 出款报备对象
     * @return 如果排序集合或者出款报备为null或者没有获取到排名返回为-1
     */
    public Long getSortIndex(Map<String,Long> sort,PaymentReportDto dto){
        if(null==sort||null==dto||(null!=dto&&null==dto.getEstimateInLoanTime())){
            return -1L;
        }
        Long index = MapUtils.getLong(sort,dto.getOrderNo()+"-"+dto.getEstimateInLoanTime().getTime(),-1L);
        return index;
    }

    public Map<String,Long> sort(List<PaymentReportDto> list,long nowtime){
        long now = 0;
        if(0<=nowtime){
            now = System.currentTimeMillis();
        } else {
            now = nowtime;
        }
        Map<String,Long> tmp = new HashMap<String,Long>();
        /**未过期的*/
        Map<Long,String> link = new  TreeMap<Long,String>();
        /**已过期的*/
        Map<Long,String> over = new  TreeMap<Long,String>();
        /**时间重复的*/
        Map<String,Long> exist = new HashMap<String,Long>();
        /**排序对应的的索引*/
        Map<Long,Long> sortindex = new HashMap<Long,Long>();
        Long distance = 0L;
        String value = null;
        for (PaymentReportDto dto:list){
            if(null==dto||null==dto.getEstimateInLoanEndTime())continue;
            distance = dto.getEstimateInLoanTime().getTime()-now;
            if(null!=distance&&0>distance){
                value =  over.put(distance,dto.getOrderNo()+"-"+dto.getEstimateInLoanTime().getTime());
            } else {
                value = link.put(distance,dto.getOrderNo()+"-"+dto.getEstimateInLoanTime().getTime());
            }
            if(null!=value){
                exist.put(value,distance);
            }
            tmp.put(dto.getOrderNo(),dto.getEstimateInLoanTime().getTime());
            tmp.put(dto.getOrderNo()+"-"+dto.getEstimateInLoanTime().getTime(),0L);
        }
        Long index = 1L;
        Set<Map.Entry<Long, String>> entries = link.entrySet();
        for (Map.Entry<Long,String> m:entries){
            tmp.put(m.getValue(),index);
            sortindex.put(m.getKey(),index);
            index++;
        }
        if(over.size()>0){
            entries = over.entrySet();
            for (Map.Entry<Long,String> m:entries){
                tmp.put(m.getValue(),index);
                sortindex.put(m.getKey(),index);
                index++;
            }
        }

        if(exist.size()>0){
            Long sortin = -1L;
            Set<Map.Entry<String, Long>> setEny = exist.entrySet();
            for (Map.Entry<String,Long> m:setEny){
                sortin =  MapUtils.getLong(sortindex,m.getValue());
                tmp.put(m.getKey(),sortin);
            }
        }
        return tmp;
    }
    /**
     * 获取排名 集合
     * @param nowtime 当前时间
     * @return 如果没有则返回空集合
     */
    public Map<String,Long> getReportSort(long nowtime){
        List<PaymentReportDto> list  = listPaymentReportByStatus(null);
        if(null==list||list.size()<=0){
            return Collections.EMPTY_MAP;
        }
        return sort(list,nowtime);
    }
    public List<OrderListDto> getOrderData(String orderNos,PaymentReportDto obj){
        HttpUtil http = new HttpUtil();
        OrderListDto order = new OrderListDto();
        order.setOrderNo(orderNos);
        if(null!=obj) {
            order.setState(obj.getState());
        }
        List<OrderListDto> listOrder =  http.getList(Constants.LINK_CREDIT, "/credit/order/base/v/queryOrderBaseList",order, OrderListDto.class);
        return listOrder;
    }

    public void mappingOrderData(List<PaymentReportDto> list){
        String orderNos = getOrderNos(list);
        List<OrderListDto> listOrder = getOrderData(orderNos,null);
        if(null==listOrder||listOrder.size()<=0||null==list||list.size()<=0)return;
        for (PaymentReportDto obj:list){
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

    public void mappingOrderData(PaymentReportDto obj){
        List<OrderListDto> listOrder = getOrderData(obj.getOrderNo(),null);
        if(null==listOrder||listOrder.size()<=0)return;
        Iterator<OrderListDto> iterator = listOrder.iterator();
        while (iterator.hasNext()){
            OrderListDto tmpOrder = iterator.next();
            if (tmpOrder.getOrderNo().equals(obj.getOrderNo())){
                obj.setProcessId(tmpOrder.getProcessId());
                obj.setState(tmpOrder.getState());
                break;
            }
        }
    }

    public void mappingOrderData(List<PaymentReportDto> list, List<OrderListDto> order){
        for (PaymentReportDto obj:list){
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
    public List<ReportEditRecordDto> listEditRecord(ReportEditRecordDto obj){
        return reportEditRecordMapper.list(obj);
    }

    public int insertReplyRecord(ReportReplyRecordDto obj){
        return reportReplyRecordMapper.insert(obj);
    }

    public List<ReportReplyRecordDto> listReplyRecord(ReportReplyRecordDto obj){
        return reportReplyRecordMapper.list(obj);
    }

    public int cancelReport(PaymentReportDto dto, UserDto user){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        PaymentReportDto tmp = paymentreportMapper.detailByStatus(dto);
        ReportEditRecordDto edit = new ReportEditRecordDto();
        edit.setBeanColumn("estimateInLoanTime");
        edit.setOrderNo(dto.getOrderNo());
        edit.setCreateUid(user.getUid());
        edit.setReportId(tmp.getId());
        edit.setStartVal(format.format(tmp.getEstimateInLoanTime()));
        edit.setColName("取消回款报备");
        reportEditRecordMapper.insert(edit);
        int success = paymentreportMapper.cancelReportById(tmp.getId());
        return success;
    }
    public int cancelReport(String orderNo){
        PaymentReportDto dto = new PaymentReportDto();
        dto.setOrderNo(orderNo);
        dto = paymentreportMapper.detailByStatus(dto);
        return paymentreportMapper.cancelReportById(dto.getId());
    }
    public PaymentReportDto detailByRelationOrder(PaymentReportDto obj){
        return paymentreportMapper.detailByRelationOrder(obj);
    }

    public Map<String,Object> listMap(PaymentReportDto obj){
        List<PaymentReportDto> list = paymentreportMapper.list(obj);
        if(null==list||list.size()<=0)return Collections.EMPTY_MAP;
        Map<String,Object> map = new HashMap<String,Object>();
        for (PaymentReportDto dto:list){
            compareDate(map,dto);
        }
        return map;
    }
    public void compareDate(Map<String,Object> map,PaymentReportDto obj){
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
}
