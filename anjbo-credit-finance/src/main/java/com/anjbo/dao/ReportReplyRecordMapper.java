package com.anjbo.dao;


import com.anjbo.bean.finance.ReportReplyRecordDto;

import java.util.List;

/**
 * 出款报备回复记录
 */
public interface ReportReplyRecordMapper {

    List<ReportReplyRecordDto> list(ReportReplyRecordDto obj);

    int insert(ReportReplyRecordDto record);

    int update(ReportReplyRecordDto obj);

}