package com.anjbo.service.impl;

import com.anjbo.bean.finance.AlterLoanBudgetRepaymentDto;
import com.anjbo.dao.AfterloanFirstInterestMapper;
import com.anjbo.service.AfterloanFirstInterestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
@Service
public class AfterloanFirstInterestServiceImpl implements AfterloanFirstInterestService{
    @Resource
    private AfterloanFirstInterestMapper afterloanFirstInterestMapper;

    @Override
    public AlterLoanBudgetRepaymentDto select(AlterLoanBudgetRepaymentDto obj) {
        return afterloanFirstInterestMapper.select(obj);
    }

    @Override
    public List<AlterLoanBudgetRepaymentDto> list(AlterLoanBudgetRepaymentDto obj) {
        List<AlterLoanBudgetRepaymentDto> list = afterloanFirstInterestMapper.list(obj);
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
            success = afterloanFirstInterestMapper.update(obj);
        } else {
            success = afterloanFirstInterestMapper.insert(obj);
        }
        return success;
    }

    @Override
    public void batchInsert(List<AlterLoanBudgetRepaymentDto> list) {
        afterloanFirstInterestMapper.batchInsert(list);
    }

    @Override
    public Integer update(AlterLoanBudgetRepaymentDto obj) {
        return afterloanFirstInterestMapper.update(obj);
    }

    @Override
    public Integer listCount(AlterLoanBudgetRepaymentDto obj) {
        return afterloanFirstInterestMapper.listCount(obj);
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
                tmp = afterloanFirstInterestMapper.selectCurrentPeriod(obj);
            }
            index++;
            if(null!=tmp
                    &&!statusButton&&(1==obj.getStatus()||2==obj.getStatus())
                    &&(tmp.getRepaymentPeriods().equals(obj.getRepaymentPeriods())
                    ||tmp.getRepaymentPeriods()==obj.getRepaymentPeriods())){
                statusButton = true;
                obj.setStatusButton(statusButton);
            }
            if(list.size()==obj.getRepaymentPeriods()){
                obj.setLastPeriods(true);
            }
            if(1==obj.getStatus()||2==obj.getStatus()){
                boolean b = AfterLoanServceCommon.compareDate(obj,format,1,afterloanFirstInterestMapper,true,false);
                if(b)return;
            }
        }
    }



    @Override
    public Integer updateByOrderNo(AlterLoanBudgetRepaymentDto obj) {
        return afterloanFirstInterestMapper.updateByOrderNo(obj);
    }

    @Override
    public AlterLoanBudgetRepaymentDto selectByOrderNo(AlterLoanBudgetRepaymentDto obj) {
        return afterloanFirstInterestMapper.selectByOrderNo(obj);
    }

    @Override
    public AlterLoanBudgetRepaymentDto selectCurrentPeriod(AlterLoanBudgetRepaymentDto obj) {
        return afterloanFirstInterestMapper.selectCurrentPeriod(obj);
    }

    @Override
    public void delete(AlterLoanBudgetRepaymentDto obj) {
        afterloanFirstInterestMapper.delete(obj);
    }
}
