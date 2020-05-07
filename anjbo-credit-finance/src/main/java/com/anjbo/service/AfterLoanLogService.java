package com.anjbo.service;

import com.anjbo.bean.finance.AfterLoanLogDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/8.
 */
public interface AfterLoanLogService {

    List<AfterLoanLogDto> list(AfterLoanLogDto obj);

    Integer listCount(AfterLoanLogDto obj);

    Integer delete(AfterLoanLogDto obj);

    Integer insert(AfterLoanLogDto obj);

    Integer update(AfterLoanLogDto obj);

    List<Map<String,Object>> listOperate(AfterLoanLogDto obj);

    void bacthInsertFile(List<Map<String,Object>> list);

    List<AfterLoanLogDto> selectCurrentPeriodsLog(AfterLoanLogDto obj);

    void deleteAll(AfterLoanLogDto obj);
}
