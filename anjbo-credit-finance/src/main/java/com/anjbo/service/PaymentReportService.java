package com.anjbo.service;

import com.anjbo.bean.finance.PaymentReportDto;
import com.anjbo.bean.finance.ReportEditRecordDto;
import com.anjbo.bean.finance.ReportReplyRecordDto;
import com.anjbo.bean.user.UserDto;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/24.
 */
public interface PaymentReportService {

    List<PaymentReportDto> list(PaymentReportDto obj);

    Integer listCount(PaymentReportDto obj);

    List<PaymentReportDto> appList(PaymentReportDto obj);

    int insert(PaymentReportDto obj)throws ParseException;

    int update(PaymentReportDto obj);

    /**
     * 根据订单号查询最新一条详情
     * @param obj
     * @return
     */
    PaymentReportDto detail(PaymentReportDto obj);

    PaymentReportDto detailById(PaymentReportDto obj);

    /**
     * 根据订单号查询不为撤销状态的最新回款报备
     * @param obj
     * @return
     */
    PaymentReportDto detailByStatus(PaymentReportDto obj);


    int cancelPaymentReport(@Param("orderNo") String orderNo);

    int cancelPaymentReportById(@Param("id") Integer id);

    List<PaymentReportDto> listPaymentReportByStatus(PaymentReportDto obj);

    List<ReportEditRecordDto> listEditRecord(ReportEditRecordDto obj);

    int insertReplyRecord(ReportReplyRecordDto obj);

    List<ReportReplyRecordDto> listReplyRecord(ReportReplyRecordDto obj);

    int cancelReport(PaymentReportDto dto, UserDto user);

    int cancelReport(String orderNo);

    PaymentReportDto detailByRelationOrder(PaymentReportDto obj);

    Map<String,Object> listMap(PaymentReportDto obj);

    int updateByStatus(PaymentReportDto obj);
}
