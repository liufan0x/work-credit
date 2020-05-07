package com.anjbo.service;

import com.anjbo.bean.finance.ReportDto;
import com.anjbo.bean.finance.ReportEditRecordDto;
import com.anjbo.bean.finance.ReportReplyRecordDto;
import com.anjbo.bean.user.UserDto;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 出款报备
 * Created by Administrator on 2017/9/22.
 */
public interface ReportService {

    List<ReportDto> list(ReportDto obj);

    List<ReportDto> appList(ReportDto obj);

    int insert(ReportDto record)throws ParseException;

    int update(ReportDto obj);

    int updateByStatus(ReportDto obj);

    ReportDto detail(ReportDto obj);

    ReportDto detailByStatus(ReportDto obj);

    ReportDto detailById(ReportDto obj);

    int listCount(ReportDto obj);

    int insertEditRecord(ReportEditRecordDto obj);

    List<ReportEditRecordDto> listEditRecord(ReportEditRecordDto obj);

    int insertReplyRecord(ReportReplyRecordDto obj);

    List<ReportReplyRecordDto> listReplyRecord(ReportReplyRecordDto obj);

    int cancelReport(String orderNo);

    Map<String,Object> listMap(ReportDto obj);

    int cancelReport(ReportDto dto, UserDto user);


}
