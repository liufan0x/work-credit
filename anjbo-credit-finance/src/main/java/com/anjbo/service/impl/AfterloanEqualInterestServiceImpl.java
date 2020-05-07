package com.anjbo.service.impl;

import com.anjbo.bean.finance.AlterLoanBudgetRepaymentDto;
import com.anjbo.dao.AfterloanEqualInterestMapper;
import com.anjbo.service.AfterloanEqualInterestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
@Service
public class AfterloanEqualInterestServiceImpl implements AfterloanEqualInterestService {
    @Resource
    private AfterloanEqualInterestMapper afterloanEqualInterestMapper;
    @Override
    public AlterLoanBudgetRepaymentDto select(AlterLoanBudgetRepaymentDto obj) {
        return afterloanEqualInterestMapper.select(obj);
    }

    @Override
    public List<AlterLoanBudgetRepaymentDto> list(AlterLoanBudgetRepaymentDto obj) {
        List<AlterLoanBudgetRepaymentDto> list = afterloanEqualInterestMapper.list(obj);
        try {
            computeLateDay(list);
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Integer insert(AlterLoanBudgetRepaymentDto obj) {
        Integer success = 0;
        if(null!=obj.getId()){
            success = afterloanEqualInterestMapper.update(obj);
        } else {
            success = afterloanEqualInterestMapper.insert(obj);
        }
        return success;
    }

    @Override
    public void batchInsert(List<AlterLoanBudgetRepaymentDto> list) {
        afterloanEqualInterestMapper.batchInsert(list);
    }

    @Override
    public Integer update(AlterLoanBudgetRepaymentDto obj) {
        return afterloanEqualInterestMapper.update(obj);
    }

    @Override
    public Integer listCount(AlterLoanBudgetRepaymentDto obj) {
        return afterloanEqualInterestMapper.listCount(obj);
    }

    /**
     * 计算逾期天数
     * @param list
     */
    public void computeLateDay(List<AlterLoanBudgetRepaymentDto> list)throws Exception{
        boolean statusButton = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int index = 1;
        AlterLoanBudgetRepaymentDto tmp = null;
        for (AlterLoanBudgetRepaymentDto obj:list){
            if(1==index){
                tmp = afterloanEqualInterestMapper.selectCurrentPeriod(obj);
            }
            index++;
            if(null!=tmp
                    &&!statusButton&&(1==obj.getStatus()||2==obj.getStatus())
                    &&(tmp.getRepaymentPeriods().equals(obj.getRepaymentPeriods())
                    ||tmp.getRepaymentPeriods()==obj.getRepaymentPeriods())){
                statusButton = true;
                obj.setStatusButton(statusButton);
            }
            if(1==obj.getStatus()||2==obj.getStatus()){
                boolean b = AfterLoanServceCommon.compareDate(obj,format,2,afterloanEqualInterestMapper,true,false);
                if(b)return;
            }
        }
    }

    @Override
    public Integer updateByOrderNo(AlterLoanBudgetRepaymentDto obj) {
        return afterloanEqualInterestMapper.updateByOrderNo(obj);
    }

    @Override
    public AlterLoanBudgetRepaymentDto selectByOrderNo(AlterLoanBudgetRepaymentDto obj) {
        return afterloanEqualInterestMapper.selectByOrderNo(obj);
    }

    @Override
    public AlterLoanBudgetRepaymentDto selectCurrentPeriod(AlterLoanBudgetRepaymentDto obj) {
        return afterloanEqualInterestMapper.selectCurrentPeriod(obj);
    }

    @Override
    public void delete(AlterLoanBudgetRepaymentDto obj) {
        afterloanEqualInterestMapper.delete(obj);
    }
}
