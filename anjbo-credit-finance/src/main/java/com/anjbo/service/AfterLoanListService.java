package com.anjbo.service;

import com.anjbo.bean.finance.AfterLoanListDto;
import com.anjbo.bean.finance.AfterLoanLogDto;
import com.anjbo.bean.finance.AlterLoanBudgetRepaymentDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/27.
 */
public interface AfterLoanListService {

    List<AfterLoanListDto> list(AfterLoanListDto obj);

    Integer listCount(AfterLoanListDto obj);

    Integer insert(AfterLoanListDto obj)throws Exception;

    Integer update(AfterLoanListDto obj);

    AfterLoanListDto select(AfterLoanListDto obj);

    List<Map<String,Object>> allLoan(AlterLoanBudgetRepaymentDto obj)throws Exception;

    boolean repayment(AlterLoanBudgetRepaymentDto dto,
                      AfterLoanLogDto log,
                      UserDto user,
                      int repaymentType,
                      RespDataObject<AfterLoanLogDto> result,
                      AfterLoanListDto tmpLoan);

    Map<String,Object> selectInOrderNo(String orderNo);

    Integer delete(AfterLoanListDto obj);

    Integer closeMsg(Integer repaymentType,AlterLoanBudgetRepaymentDto obj);

    void downloadRepayment(AfterLoanListDto obj,UserDto user,ExcelService excelService,HttpServletResponse response)throws Exception;

    void withdrawLogRecord(RespStatus result,AfterLoanLogDto obj,UserDto user);
}
