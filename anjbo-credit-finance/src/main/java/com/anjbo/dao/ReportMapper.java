package com.anjbo.dao;


import com.anjbo.bean.finance.ReportDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出款报备
 */
public interface ReportMapper {

    List<ReportDto> list(ReportDto obj);

    List<ReportDto> appList(ReportDto obj);

    int insert(ReportDto record);

    int update(ReportDto obj);

    int updateByStatus(ReportDto obj);

    ReportDto detail(ReportDto obj);

    ReportDto detailById(ReportDto obj);

    ReportDto detailByStatus(ReportDto obj);

    int listCount(ReportDto obj);

    /**
     * 根据订单号取消报备
     * @param orderNo
     * @return
     */
    int cancelReport(@Param("orderNo") String orderNo);

    /**
     * 根据id取消报备
     * @param id
     * @return
     */
    int cancelReportById(@Param("id") Integer id);

    ReportDto detailRelationOrder(ReportDto obj);

    /**
     * 根据放款状态查询出款报备
     * @param obj
     * @return
     */
    List<ReportDto> listReportByStatus(ReportDto obj);

    /**
     * 根据放款状态查询当天的出款报备
     * @param obj
     * @return
     */
    List<ReportDto> listReportByStatusToday(ReportDto obj);
}