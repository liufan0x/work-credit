package com.anjbo.service.impl;

import com.anjbo.bean.finance.AlterLoanBudgetRepaymentDto;
import com.anjbo.dao.AfterloanAdvanceMapper;
import com.anjbo.service.AfterloanAdvanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
@Service
public class AfterloanAdvanceServiceImpl implements AfterloanAdvanceService {
    @Resource
    private AfterloanAdvanceMapper afterloanAdvanceMapper;

    @Override
    public AlterLoanBudgetRepaymentDto select(AlterLoanBudgetRepaymentDto obj) {
        return afterloanAdvanceMapper.select(obj);
    }

    @Override
    public List<AlterLoanBudgetRepaymentDto> list(AlterLoanBudgetRepaymentDto obj) {
        return afterloanAdvanceMapper.list(obj);
    }

    @Override
    public Integer insert(AlterLoanBudgetRepaymentDto obj) {
        Integer success = 0;
        if(null!=obj.getId()){
            success = afterloanAdvanceMapper.update(obj);
        } else {
            success = afterloanAdvanceMapper.insert(obj);
        }
        return success;
    }

    @Override
    public void batchInsert(List<AlterLoanBudgetRepaymentDto> list) {
        afterloanAdvanceMapper.batchInsert(list);
    }

    @Override
    public Integer listCount(AlterLoanBudgetRepaymentDto obj) {
        return afterloanAdvanceMapper.listCount(obj);
    }

    @Override
    public Integer update(AlterLoanBudgetRepaymentDto obj) {
        return afterloanAdvanceMapper.update(obj);
    }

    @Override
    public Integer updateByOrderNo(AlterLoanBudgetRepaymentDto obj) {
        //TODO 未实现
        return null;
    }

    @Override
    public AlterLoanBudgetRepaymentDto selectByOrderNo(AlterLoanBudgetRepaymentDto obj) {
        //TODO 未实现
        return null;
    }

    @Override
    public AlterLoanBudgetRepaymentDto selectCurrentPeriod(AlterLoanBudgetRepaymentDto obj) {
        //TODO 未实现
        return null;
    }

    @Override
    public void delete(AlterLoanBudgetRepaymentDto obj) {
        //TODO 未实现
    }
}
