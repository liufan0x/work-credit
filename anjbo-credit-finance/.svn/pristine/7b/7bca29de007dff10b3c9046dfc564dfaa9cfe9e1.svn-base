package com.anjbo.dao;

import com.anjbo.bean.finance.PaymentReportDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */
public interface PaymentreportMapper {

    List<PaymentReportDto> list(PaymentReportDto obj);

    Integer listCount(PaymentReportDto obj);

    List<PaymentReportDto> appList(PaymentReportDto obj);

    int insert(PaymentReportDto obj);

    int update(PaymentReportDto obj);

    PaymentReportDto detail(PaymentReportDto obj);

    PaymentReportDto detailById(PaymentReportDto obj);

    PaymentReportDto detailByStatus(PaymentReportDto obj);

    int cancelPaymentReport(@Param("orderNo") String orderNo);

    int cancelPaymentReportById(@Param("id") Integer id);

    List<PaymentReportDto> listPaymentReportByStatus(PaymentReportDto obj);

    int updateByStatus(PaymentReportDto obj);

    int cancelReport(@Param("orderNo") String orderNo);

    int cancelReportById(@Param("id") Integer id);

    PaymentReportDto detailByRelationOrder(PaymentReportDto obj);
}
