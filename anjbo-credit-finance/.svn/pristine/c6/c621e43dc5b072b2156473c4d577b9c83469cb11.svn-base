package com.anjbo.service.impl;

import com.anjbo.bean.finance.AlterLoanBudgetRepaymentDto;
import com.anjbo.dao.AlterLoanBudgetRepaymentMapper;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/16.
 * 贷后公共类
 */
public class AfterLoanServceCommon {

    /**
     * 与当前时间比较返回时间相差天数
     * @param repaymentDate
     * @param df
     * @return
     */
    public static int compareDate(Date repaymentDate, DateFormat df)throws Exception{
        String day = df.format(repaymentDate);
        String now = df.format(new Date());
        Long l = df.parse(day).getTime() - df.parse(now).getTime();
        l = l/(24 * 60 * 60 * 1000);
        return l.intValue();
    }

    /**
     *
     * @param dayTmp
     * @param nowTmp
     * @param df
     * @return
     * @throws Exception
     */
    public static int compareDate(Date dayTmp,Date nowTmp,DateFormat df)throws Exception{
        String day = df.format(dayTmp);
        String now = df.format(nowTmp);
        Long l = df.parse(day).getTime() - df.parse(now).getTime();
        l = l/(24 * 60 * 60 * 1000);
        return l.intValue();
    }
    /**
     * 计算逾期费用
     * 先息后本:剩余本金*逾期天数*逾期利率。
     * 等额本息:应还本金*逾期天数*逾期利率。
     * @param obj
     * @param lateDay 逾期天数
     * @param repaymentType 1:先息后本,2:等额本息
     * @return
     */
    public static Double computeOverdue(AlterLoanBudgetRepaymentDto obj, Integer lateDay,int repaymentType){
        BigDecimal surplusPrincipal = null;
        if(1==repaymentType){
            surplusPrincipal = BigDecimal.valueOf(obj.getSurplusPrincipal());
        } else if(2==repaymentType){
            surplusPrincipal = BigDecimal.valueOf(obj.getRepayPrincipal());
        }
        System.out.println(surplusPrincipal.toPlainString());
        BigDecimal givePayPrincipal = BigDecimal.valueOf(null==obj.getGivePayPrincipal()?0d:obj.getGivePayPrincipal());
        if(givePayPrincipal.compareTo(BigDecimal.valueOf(0d))>0){
            surplusPrincipal = surplusPrincipal.subtract(givePayPrincipal);
        }
        Double overdueRate = obj.getOverdueRate();

        Double repayOverdue = surplusPrincipal
                .multiply(BigDecimal.valueOf(null==lateDay?0d:lateDay.doubleValue()))
                .multiply(BigDecimal.valueOf(overdueRate))
                .divide(BigDecimal.valueOf(100d),BigDecimal.ROUND_HALF_UP).doubleValue();
        return repayOverdue;
    }

    /**
     * 与当前时间比较,大于当前时间则为true,反之为false
     * @param obj
     * @param df 时间格式化
     * @param repaymentType 贷后类型
     * @param alterLoanBudgetRepaymentMapper
     * @param isUpdate 是否需要更新数据
     * @param timing 是否是定时任务调用
     * @return
     * @throws Exception
     */
    public static boolean compareDate(AlterLoanBudgetRepaymentDto obj, DateFormat df, int repaymentType, AlterLoanBudgetRepaymentMapper alterLoanBudgetRepaymentMapper,boolean isUpdate,boolean timing)throws Exception{
        int l = compareDate(obj.getRepaymentDate(),df);
        boolean isreture = true;
        if(l<0){
            int abs = StrictMath.abs(l);
            //逾期没有还则按还款日期计算逾期费用
            Date realityPayDate = obj.getRealityPayDate();
            if(null==obj.getLateDay())obj.setLateDay(0);
            if(abs!=obj.getLateDay()
                    &&(null==obj.getGivePayOverdue()
                    ||0==obj.getGivePayOverdue())
                    &&null==realityPayDate){
                obj.setLateDay(abs);
                computeOverdue(obj,repaymentType,alterLoanBudgetRepaymentMapper,isUpdate);
                //逾期有还则按分段计算逾期
            } else if((abs!=obj.getLateDay()
                    &&null!=obj.getGivePayOverdue()
                    &&obj.getGivePayOverdue()>0)){
                int day = compareDate(obj.getRealityPayDate(),df);
                if(day<0&&abs!=obj.getLateDay()){
                    obj.setLateDay(abs);
                    day = StrictMath.abs(day);
                    Double repayOverdue = computeOverdue(obj, day,repaymentType);
                    obj.setRepayOverdue(BigDecimal.valueOf(obj.getRepayOverdue())
                            .add(BigDecimal.valueOf(repayOverdue)).doubleValue());
                    Double repayAmount = BigDecimal.valueOf(obj.getRepayPrincipal())
                            .add(BigDecimal.valueOf(obj.getRepayInterest()))
                            .add(BigDecimal.valueOf(obj.getRepayOverdue())).doubleValue();
                    obj.setRepayAmount(repayAmount);
                    if(isUpdate) {
                        alterLoanBudgetRepaymentMapper.update(obj);
                    }
                    isreture = false;
                } else {
                    isreture = true;
                }
                if(timing){
                    obj.setLateDay(l);
                }
                return isreture;

            } else if(abs==obj.getLateDay()
                    &&null!=realityPayDate
                    &&(null==obj.getRepayOverdue()
                    ||0==obj.getRepayOverdue())){
                Date d1 = df.parse(df.format(realityPayDate));
                Date d2 = df.parse(df.format(new Date()));
                if(d1.getTime()<d2.getTime()){
                    int day = compareDate(obj.getRealityPayDate(),df);
                    if(day<0){
                        obj.setLateDay(abs);
                        day = StrictMath.abs(day);
                        Double repayOverdue = computeOverdue(obj, day,repaymentType);
                        obj.setRepayOverdue(BigDecimal.valueOf(obj.getRepayOverdue())
                                .add(BigDecimal.valueOf(repayOverdue)).doubleValue());
                        Double repayAmount = BigDecimal.valueOf(obj.getRepayPrincipal())
                                .add(BigDecimal.valueOf(obj.getRepayInterest()))
                                .add(BigDecimal.valueOf(obj.getRepayOverdue())).doubleValue();
                        obj.setRepayAmount(repayAmount);
                        if(isUpdate) {
                            alterLoanBudgetRepaymentMapper.update(obj);
                        }
                        isreture = false;
                    } else {
                        isreture = true;
                    }
                    if(timing){
                        obj.setLateDay(l);
                    }
                    return isreture;
                }
            }
            if(timing){
                obj.setLateDay(l);
            }
            return false;
        }
        if(timing){
            obj.setLateDay(l);
        }
        return true;
    }

    /**
     * 计算逾期费用
     * 公式为:
     * 先息后本:剩余本金*逾期天数*逾期利率。
     * 等额本息:应还本金*逾期天数*逾期利率。
     * @param repaymentType 1:先息后本,2:等额本息
     */
    public static void computeOverdue(AlterLoanBudgetRepaymentDto obj,int repaymentType, AlterLoanBudgetRepaymentMapper alterLoanBudgetRepaymentMapper,boolean isUpdate){
        Double repayOverdue = computeOverdue(obj,obj.getLateDay(),repaymentType);
        obj.setRepayOverdue(repayOverdue);
        Double repayAmount = BigDecimal.valueOf(obj.getRepayPrincipal())
                .add(BigDecimal.valueOf(obj.getRepayInterest()))
                .add(BigDecimal.valueOf(repayOverdue)).doubleValue();
        obj.setRepayAmount(repayAmount);
        if(isUpdate) {
            alterLoanBudgetRepaymentMapper.update(obj);
        }
    }
}
