package com.anjbo.dao;

import com.anjbo.bean.finance.AfterLoanListDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */
public interface AfterLoanListMapper {

    List<AfterLoanListDto> list(AfterLoanListDto obj);

    Integer listCount(AfterLoanListDto obj);

    Integer insert(AfterLoanListDto obj);

    Integer update(AfterLoanListDto obj);

    AfterLoanListDto select(AfterLoanListDto obj);

    Integer delete(AfterLoanListDto obj);

    List<AfterLoanListDto> allLoan(@Param("orderNo") String orderNo);

    List<AfterLoanListDto> selectInOrderNo(@Param("orderNo") String orderNo);
}
