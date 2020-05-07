package com.anjbo.service.impl;

import com.anjbo.bean.finance.AfterLoanListDto;
import com.anjbo.bean.finance.AfterLoanLogDto;
import com.anjbo.bean.finance.AlterLoanBudgetRepaymentDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.dao.AfterLoanListMapper;
import com.anjbo.dao.AfterLoanLogMapper;
import com.anjbo.dao.AfterloanEqualInterestMapper;
import com.anjbo.dao.AfterloanFirstInterestMapper;
import com.anjbo.service.AfterLoanListService;
import com.anjbo.service.ExcelService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/3/27.
 */
@Service
@Transactional
public class AfterLoanListServiceImpl implements AfterLoanListService {

    private Logger logger = org.apache.log4j.Logger.getLogger(getClass());

    @Resource
    private AfterLoanListMapper afterLoanListMapper;

    @Resource
    private AfterloanEqualInterestMapper afterloanEqualInterestMapper;
    @Resource
    private AfterloanFirstInterestMapper afterloanFirstInterestMapper;
    @Resource
    private AfterLoanLogMapper afterLoanLogMapper;

    private final static int scale = 2;

    @Override
    public List<AfterLoanListDto> list(AfterLoanListDto obj) {
        List<AfterLoanListDto> list = afterLoanListMapper.list(obj);
        try {
            if(null!=list&&list.size()>0){
                computeLateDay(list);
                list = mappingOrderData(list);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Integer listCount(AfterLoanListDto obj) {
        return afterLoanListMapper.listCount(obj);
    }

    @Override
    public Integer insert(AfterLoanListDto obj) throws Exception{
        AfterLoanListDto tmp = new AfterLoanListDto();
        tmp.setOrderNo(obj.getOrderNo());
        tmp = afterLoanListMapper.select(tmp);
        Integer success = 0;
        try {
            if (null == tmp) {
                Calendar instance = Calendar.getInstance();
                instance.setTime(obj.getLendingTime());
                instance.add(Calendar.MONTH,1);
                obj.setNewRepayment(instance.getTime());
                obj.setRepaymentStatus(1);
                obj.setRepaymentStatusName("待还款");
                //先息后本
                if(1==obj.getRepaymentType()){
                    insertFirstInterest(obj);
                //等额本息
                } else if(2==obj.getRepaymentType()){
                    insertEqualInterest(obj);
                }
                success = afterLoanListMapper.insert(obj);
            } else {
                success = -1;
                return success;
            }
        } catch (Exception e){
            afterLoanListMapper.delete(obj);
            throw new Exception(e);
        }
        return null==success?0:success;
    }

    @Override
    public Integer update(AfterLoanListDto obj) {
        return afterLoanListMapper.update(obj);
    }

    public AlterLoanBudgetRepaymentDto mappingDetailEntity(AfterLoanListDto obj){
        AlterLoanBudgetRepaymentDto dto = new AlterLoanBudgetRepaymentDto();
        dto.setCreateUid(obj.getUid());
        dto.setUpdateUid(obj.getUid());
        dto.setOrderNo(obj.getOrderNo());
        return dto;
    }

    /**
     * 新增提前还款
     */
    public void insertAdvance(AfterLoanListDto obj){
        //TODO 为先息后本与等额本息分支提前还款 这期先暂时不做 不新增列表在原来的列表改还款状态
    }

    /**
     * 新增等额本息
     */
    public void insertEqualInterest(AfterLoanListDto obj)throws Exception{
        obj.setRepaymentName("等额本息");
        Integer borrowingPeriods = obj.getBorrowingPeriods();
        Calendar instance = Calendar.getInstance();
        instance.setTime(obj.getLendingTime());
        AlterLoanBudgetRepaymentDto dto = null;

        BigDecimal b1 = BigDecimal.valueOf(obj.getRate()).divide(BigDecimal.valueOf(100d));
        BigDecimal b3 = BigDecimal.valueOf(obj.getLoanAmount()).multiply(BigDecimal.valueOf(10000));
        BigDecimal b5 = b3;
        /**
         * 应还金额
         */
        Double repayAmount = computeRepayAmount(obj, b1.doubleValue(), b5.doubleValue());

        List<AlterLoanBudgetRepaymentDto> list = new ArrayList<AlterLoanBudgetRepaymentDto>();

        for (int i=1;i<=borrowingPeriods;i++){
            instance.add(Calendar.MONTH,1);
            dto = mappingDetailEntity(obj);
            dto.setRate(obj.getRate());
            dto.setOverdueRate(obj.getOverdueRate());
            dto.setRepaymentPeriods(i);
            dto.setRepaymentDate(instance.getTime());
            dto.setRepayAmount(repayAmount);
            dto.setSurplusPrincipal(b3.doubleValue());
            dto.setCloseMsg(2);
            /**
             * 应还利息
             */
            Double repayInterest = computeInterest(obj, b1.doubleValue(), b5.doubleValue(), i);
            /**
             * 应还本金=应还金额-应还利息
             */
            Double repayPrincipal = computeRepayPrincipal(repayAmount, repayInterest);
            if(i==borrowingPeriods&&b3.doubleValue()!=repayPrincipal){
                //检查是否有误差
                if(b3.compareTo(BigDecimal.valueOf(repayPrincipal))>0
                        ||b3.compareTo(BigDecimal.valueOf(repayPrincipal))<0){
                    dto.setRepayAmount(b3.add(BigDecimal.valueOf(repayInterest)).doubleValue());
                    repayPrincipal = b3.doubleValue();
                }
            }
            dto.setRepayPrincipal(repayPrincipal);
            dto.setRepayInterest(repayInterest);
            dto.setLateDay(0);
            dto.setStatus(1);
            list.add(dto);
            if(i<borrowingPeriods) {
                b3 = b3.subtract(BigDecimal.valueOf(repayPrincipal));
            }
        }
        try {
            afterloanEqualInterestMapper.batchInsert(list);
        } catch (Exception e){
            afterloanEqualInterestMapper.delete(dto);
            throw new Exception(e);
        }
    }

    /**
     * 计算等额本息的应还利息
     * 公式为: 借款金额*利率*[(1+利率)总期数次方-(1+利率)当前期数-1次方]/(1+利率)总期数次方 -1
     * 保留两位小数
     * @param rate 费率
     * @param loanAmount 借款金额/元
     * @param repaymentPeriods 当前期数
     * @return
     */
    public Double computeInterest(AfterLoanListDto obj,Double rate,Double loanAmount,int repaymentPeriods){
        Double repayInterest = 0d;
        /**
         * 借款金额*利率
         */
        BigDecimal multiply = BigDecimal.valueOf(loanAmount).multiply(BigDecimal.valueOf(rate));
        if(1==repaymentPeriods){
            repayInterest = multiply.setScale(scale,BigDecimal.ROUND_DOWN).doubleValue();
        } else {

            /**
             *  (1+利率)总期数次方
             */
            double p1 = StrictMath.pow(1 + rate, obj.getBorrowingPeriods());
            /**
             * (1+利率)当前期数-1次方
             */
            double p2 = StrictMath.pow(1 + rate, repaymentPeriods - 1);
            /**
             * (1+利率)总期数次方-(1+利率)当前期数-1次方
             */
            BigDecimal s1 = BigDecimal.valueOf(p1).subtract(BigDecimal.valueOf(p2));
            /**
             * (1+利率)总期数次方 -1
             */
            BigDecimal s2 = BigDecimal.valueOf(p1).subtract(BigDecimal.valueOf(1d));
            /**
             * 借款金额*利率*[(1+利率)总期数次方-(1+利率)当前期数-1次方]/(1+利率)总期数次方 -1
             */
            BigDecimal divide = multiply.multiply(s1).divide(s2,BigDecimal.ROUND_HALF_UP);
            repayInterest = divide.setScale(scale,BigDecimal.ROUND_DOWN).doubleValue();

        }
        return repayInterest;
    }

    /**
     * 计算等额本息的应还金额
     * 公式为：借款金额*利率*(1+利率)总期数次方/(1+利率)总期数次方 -1
     * @param rate 费率
     * @param loanAmount 借款金额/元
     * @return
     */
    public Double computeRepayAmount(AfterLoanListDto obj,Double rate,Double loanAmount){
        /**
         * 借款金额*利率
         */
        BigDecimal multiply = BigDecimal.valueOf(loanAmount).multiply(BigDecimal.valueOf(rate));
        /**
         *  (1+利率)总期数次方
         */
        double p1 = StrictMath.pow(1 + rate, obj.getBorrowingPeriods());
        BigDecimal b1 = BigDecimal.valueOf(p1);
        /**
         * (1+利率)总期数次方 -1
         */
        BigDecimal dividend = b1.subtract(BigDecimal.valueOf(1d));
        /**
         * 借款金额*利率*(1+利率)总期数次方
         */
        BigDecimal  divisor = multiply.multiply(b1);
        /**
         * 借款金额*利率*(1+利率)总期数次方/(1+利率)总期数次方 -1
         */
        Double repayAmount = divisor.divide(dividend,BigDecimal.ROUND_HALF_UP).setScale(scale,BigDecimal.ROUND_DOWN).doubleValue();
        return repayAmount;
    }

    /**
     * 计算等额本息应还本金
     * 公式为: 应还金额-应还利息
     * @param repayAmount 应还金额
     * @param repayInterest 应还利息
     * @return
     */
    public Double computeRepayPrincipal(Double repayAmount,Double repayInterest){
        Double repayPrincipal = BigDecimal.valueOf(repayAmount)
                .subtract(BigDecimal.valueOf(repayInterest))
                .setScale(scale,BigDecimal.ROUND_DOWN).doubleValue();
        return repayPrincipal;
    }

    /**
     * 新增先息后本
     */
    public void insertFirstInterest(AfterLoanListDto obj)throws Exception{
        obj.setRepaymentName("先息后本");
        Integer borrowingPeriods = obj.getBorrowingPeriods();
        Calendar instance = Calendar.getInstance();
        instance.setTime(obj.getLendingTime());
        AlterLoanBudgetRepaymentDto dto = null;

        BigDecimal b1 = BigDecimal.valueOf(obj.getRate());
        BigDecimal b2 = BigDecimal.valueOf(100d);
        BigDecimal b3 = BigDecimal.valueOf(obj.getLoanAmount()).multiply(BigDecimal.valueOf(10000d));

        Double repayAmount = Double.parseDouble(b3.multiply(b1).divide(b2).toPlainString());

        List<AlterLoanBudgetRepaymentDto> list = new ArrayList<AlterLoanBudgetRepaymentDto>();
        for (int i=1;i<=borrowingPeriods;i++){
            instance.add(Calendar.MONTH,1);
            dto = mappingDetailEntity(obj);
            dto.setRate(obj.getRate());
            dto.setOverdueRate(obj.getOverdueRate());
            dto.setRepaymentPeriods(i);
            dto.setRepaymentDate(instance.getTime());
            dto.setRepayInterest(repayAmount);
            dto.setSurplusPrincipal(b3.doubleValue());
            dto.setCloseMsg(2);
            if(i==borrowingPeriods){
                repayAmount = BigDecimal.valueOf(repayAmount).add(b3).doubleValue();
                dto.setRepayAmount(repayAmount);
                dto.setRepayPrincipal(b3.doubleValue());
            } else {
                dto.setRepayAmount(repayAmount);
                dto.setRepayPrincipal(0d);
            }
            dto.setStatus(1);
            dto.setLateDay(0);
            list.add(dto);
        }
        try {
            afterloanFirstInterestMapper.batchInsert(list);
        } catch (Exception e){
            afterloanFirstInterestMapper.delete(dto);
            throw new Exception(e);
        }
    }

    @Override
    public AfterLoanListDto select(AfterLoanListDto obj){
        return afterLoanListMapper.select(obj);
    }



    @Override
    public List<Map<String,Object>> allLoan(AlterLoanBudgetRepaymentDto all)throws Exception{
        List<AlterLoanBudgetRepaymentDto> first = afterloanFirstInterestMapper.allLoan(all);
        List<AlterLoanBudgetRepaymentDto> equal = afterloanEqualInterestMapper.allLoan(all);
        Map<String,Object> status = new HashMap<String,Object>();
        if(null!=equal&&equal.size()>0){
            first.addAll(equal);
            for(AlterLoanBudgetRepaymentDto dto:equal){
                status.put(dto.getOrderNo(),2);
            }
        }

        String orderNo = "";
        for (AlterLoanBudgetRepaymentDto obj:first){
            if(StringUtil.isBlank(orderNo)){
                orderNo = "'"+obj.getOrderNo()+"'";
            } else {
                orderNo += ",'"+obj.getOrderNo()+"'";
            }
        }
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        if(StringUtil.isBlank(orderNo)){
            return data;
        }
        List<AfterLoanListDto> list = afterLoanListMapper.allLoan(orderNo);

        Map<String,Object> map = null;
        BigDecimal repayAmount = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        for (AlterLoanBudgetRepaymentDto obj:first){
            map = new HashMap<String,Object>();
            Integer repaymentType = MapUtils.getInteger(status,obj.getOrderNo(),1);
            AfterLoanServceCommon.compareDate(obj,format,repaymentType ,null,false,true);
            map.put("overdueDay",obj.getLateDay());
            map.put("repaymentDate",format.format(obj.getRepaymentDate()));
            map.put("repaymentDateToDay",format1.format(obj.getRepaymentDate()));
            map.put("closeMsg",obj.getCloseMsg());
            BigDecimal bigDecimal = BigDecimal.valueOf(obj.getRepayAmount())
                    .subtract(BigDecimal.valueOf(null == obj.getGivePayOverdue() ? 0d : obj.getGivePayOverdue()))
                    .subtract(BigDecimal.valueOf(null == obj.getGivePayPrincipal() ? 0d : obj.getGivePayPrincipal()))
                    .subtract(BigDecimal.valueOf(null == obj.getGivePayInterest() ? 0d : obj.getGivePayInterest()))
                    .setScale(scale, BigDecimal.ROUND_DOWN);
            map.put("repayAmount",bigDecimal.toPlainString());
            map.put("repaymentType",repaymentType);
            map.put("repaymentPeriods",obj.getRepaymentPeriods());
            Iterator<AfterLoanListDto> iterator = list.iterator();
            while (iterator.hasNext()){
                AfterLoanListDto next = iterator.next();
                if(next.getOrderNo().equals(obj.getOrderNo())){
                    if(next.getBorrowingPeriods()==obj.getRepaymentPeriods()
                            ||next.getBorrowingPeriods().equals(obj.getRepaymentPeriods())){
                        map.put("last",1);
                    } else {
                        map.put("last",2);
                    }
                    map.put("borrowerName",next.getBorrowerName());
                    map.put("channelManagerPhone",next.getChannelManagerPhone());
                    map.put("phoneNumber",next.getPhoneNumber());
                    map.put("channelManagerName",next.getChannelManagerName());
                    map.put("branchCompany",next.getBranchCompany());
                    iterator.remove();
                    break;
                }
            }

            data.add(map);
        }
        return data;
    }

    /**
     * 计算逾期天数
     * @param list
     */
    public void computeLateDay(List<AfterLoanListDto> list)throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (AfterLoanListDto obj:list){
            if(4==obj.getRepaymentStatus()
                    ||7==obj.getRepaymentStatus()){
                continue;
            }
            compareDate(obj,format);
        }
    }

    /**
     * 与当前时间比较,大于当前时间则为true,反之为false
     * @return
     */
    public boolean compareDate(AfterLoanListDto obj,DateFormat df)throws Exception{
        int l = AfterLoanServceCommon.compareDate(obj.getNewRepayment(),new Date(),df);
        if(l<0){
            int abs = StrictMath.abs(l);
            if(null==obj.getOverdueDay()
                    ||abs!=obj.getOverdueDay()
                    ||6!=obj.getRepaymentStatus()){
                obj.setRepaymentStatus(6);
                obj.setRepaymentStatusName("已逾期");
                obj.setOverdueDay(abs);
                afterLoanListMapper.update(obj);
            }
            return false;
        }
        return true;
    }


    @Override
    public boolean repayment(AlterLoanBudgetRepaymentDto dto,
                             AfterLoanLogDto log,
                             UserDto user,
                             int repaymentType,
                             RespDataObject<AfterLoanLogDto> result,
                             AfterLoanListDto tmpLoan){
        boolean isOver = false;
        List<AlterLoanBudgetRepaymentDto> list = null;
        if(1==repaymentType){
            list = afterloanFirstInterestMapper.list(dto);
        } else if(2==repaymentType){
            list = afterloanEqualInterestMapper.list(dto);
        } else {
            return isOver;
        }
        //获取当期与下期的贷款
        AlterLoanBudgetRepaymentDto tmp = null;
        AlterLoanBudgetRepaymentDto next = null;
        for (AlterLoanBudgetRepaymentDto obj:list){
        	System.out.println("obj.repaymentPeriods:"+obj.getRepaymentPeriods());
        	System.out.println("dto.repaymentPeriods:"+dto.getRepaymentPeriods());
        	System.out.println("dto.getStatus:"+dto.getStatus());
            if(obj.getRepaymentPeriods()==dto.getRepaymentPeriods()){
                tmp = obj;
            }
            if(obj.getRepaymentPeriods()==(dto.getRepaymentPeriods()+1)){
                next = obj;
                if(null!=tmp)break;
            }
        }

        if(null!=tmp&&3==tmp.getStatus()){
            RespHelper.setFailDataObject(result,log,"该分期已经结清不能重复操作");
            return isOver;
        }
        compareStatus(tmp,dto,log,repaymentType,result);
        if(1==repaymentType){
            afterloanFirstInterestMapper.updateByOrderNo(dto);
        } else if(2==repaymentType){
            afterloanEqualInterestMapper.updateByOrderNo(dto);
        }
        log.setOperateTime(new Date());
        afterLoanLogMapper.insert(log);
        AfterLoanListDto loan = new AfterLoanListDto();
        loan.setOrderNo(dto.getOrderNo());
        loan.setUpdateUid(dto.getUpdateUid());
        int repaymentStatus = 0;
        if(null==next&&3==dto.getStatus()){
            loan.setRepaymentStatus(7);
            loan.setRepaymentStatusName("已结清");
            loan.setOverdueDay(0);
            isOver = true;
            tmpLoan = afterLoanListMapper.select(loan);
            repaymentStatus = 3;
        } else if(null!=next&&3==dto.getStatus()){
            loan.setRepaymentStatus(1);
            loan.setRepaymentStatusName("待还款");
            loan.setNewRepayment(next.getRepaymentDate());
            loan.setOverdueDay(0);
            repaymentStatus = 3;
        } else if(2==dto.getStatus()){
            loan.setRepaymentStatus(2);
            loan.setRepaymentStatusName("部分还款");
            repaymentStatus = 2;
        }
        afterLoanListMapper.update(loan);
        RespHelper.setSuccessDataObject(result,log);
        try {
            if (null == tmpLoan) {
                //sendMsg(repaymentStatus, afterLoanListMapper.select(loan), log.getRepaymentPeriods(),log);
            } else {
                //sendMsg(repaymentStatus,tmpLoan, log.getRepaymentPeriods(),log);
            }
        } catch (Exception e){
            logger.error("还款发送短信异常:",e);
        }
        return isOver;
    }

    public void compareStatus(AlterLoanBudgetRepaymentDto tmp,AlterLoanBudgetRepaymentDto dto,AfterLoanLogDto log,int repaymentType,RespDataObject<AfterLoanLogDto> result){
        //保存最新实际还款日期
        if(null==tmp.getRealityPayDate()
                ||tmp.getRealityPayDate().getTime()<log.getRealityPayDate().getTime()){
            dto.setRealityPayDate(log.getRealityPayDate());
        } else {
            dto.setRealityPayDate(tmp.getRealityPayDate());
        }
        //利息
        BigDecimal givePayInterest = BigDecimal.valueOf(null==tmp.getGivePayInterest()?0d:tmp.getGivePayInterest())
                .add(BigDecimal.valueOf(null==dto.getGivePayInterest()?0d:dto.getGivePayInterest()));
        //已还逾期
        BigDecimal givePayOverdue = BigDecimal.valueOf(null==dto.getGivePayOverdue()?0d:dto.getGivePayOverdue())
                .add(BigDecimal.valueOf(null==tmp.getGivePayOverdue()?0d:tmp.getGivePayOverdue()));

        if(null==tmp.getRepayPrincipal()||0==tmp.getRepayPrincipal()){
            //应还金额=已还利息+已还逾期
            BigDecimal repayAmount = givePayInterest
                    .add(givePayOverdue)
                    .add(BigDecimal.valueOf(null==log.getOverflow()?0d:log.getOverflow()));
            int a = repayAmount.compareTo(BigDecimal.valueOf(tmp.getRepayAmount()));
            int b = givePayInterest.compareTo(BigDecimal.valueOf(tmp.getRepayInterest()));
            if(a>=0&&b==0){
                dto.setStatus(3);
            } else {
                dto.setStatus(2);
            }
            //因为当期还的是先息后本没有本金所以应该把页面分配到本金的金额计入溢出处理
            if(null!=log.getGivePayPrincipal()&&log.getGivePayPrincipal()>0){
                BigDecimal add = BigDecimal.valueOf(null == log.getOverflow() ? 0d : log.getOverflow())
                        .add(BigDecimal.valueOf(log.getGivePayPrincipal()));
                log.setOverflow(add.doubleValue());
            }
        } else {
            int c = givePayInterest.compareTo(BigDecimal.valueOf(tmp.getRepayInterest()));
            //判断利息是否有溢出
            if(c>0){
                BigDecimal overflow = givePayInterest.subtract(BigDecimal.valueOf(tmp.getRepayInterest())).setScale(scale,BigDecimal.ROUND_DOWN);
                dto.setRepayInterest(tmp.getRepayInterest());
                //将溢出计入本金处理
                Double givePayPrincipal = BigDecimal.valueOf(null == dto.getGivePayPrincipal() ? 0d : dto.getGivePayPrincipal())
                                            .add(overflow).doubleValue();
                dto.setGivePayPrincipal(givePayPrincipal);
                c = 0;
            }

            //本金
            BigDecimal givePayPrincipal = BigDecimal.valueOf(null == tmp.getGivePayPrincipal() ? 0d : tmp.getGivePayPrincipal())
                    .add(BigDecimal.valueOf(null == dto.getGivePayPrincipal() ? 0d : dto.getGivePayPrincipal()));
            int b = givePayPrincipal.compareTo(BigDecimal.valueOf(tmp.getRepayPrincipal()));

            //判断本金是否有溢出
            if(b>0){
                BigDecimal overflow = givePayPrincipal.subtract(BigDecimal.valueOf(tmp.getRepayPrincipal())).setScale(scale, BigDecimal.ROUND_DOWN);
                dto.setRepayPrincipal(tmp.getRepayPrincipal());

                //将多余的计入溢出处理
                log.setOverflow(BigDecimal.valueOf(null==log.getOverflow()?0d:log.getOverflow())
                        .add(overflow).doubleValue());
                b = 0;
            }

            //应还金额=已还利息加+已还逾期+已还本金
            BigDecimal repayAmount = givePayInterest
                    .add(givePayPrincipal)
                    .add(givePayOverdue);
            int a = repayAmount.compareTo(BigDecimal.valueOf(tmp.getRepayAmount()));
            if(a>=0&&b==0&&c==0){
                dto.setStatus(3);
            } else {
                dto.setStatus(2);
            }

            if(givePayPrincipal.doubleValue()<=0){
                dto.setGivePayPrincipal(0d);
            } else {
                dto.setGivePayPrincipal(givePayPrincipal.doubleValue());
            }
        }

        dto.setGivePayInterest(givePayInterest.doubleValue());
        //更新应还逾期 中途修改应还逾期同时更新列表应0等免息 应该加上时间判断 当前选择的时间是逾期的情况才可以计算,如果没有逾期则不计算
        if(null!=log.getRepayOverdue()){
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                int day = AfterLoanServceCommon.compareDate(log.getRealityPayDate(),tmp.getRepaymentDate(),format);
                if(day>0){
                    dto.setRepayOverdue(BigDecimal.valueOf(log.getRepayOverdue())
                            .add(BigDecimal.valueOf(null==tmp.getGivePayOverdue()?0d:tmp.getGivePayOverdue())).doubleValue());
                }
            } catch (Exception e){
                logger.error("更新逾期逾期时间比较异常",e);
            }

        }
        //计算已还逾期与应还逾期
        if((null!=dto.getRepayOverdue()&&dto.getRepayOverdue()>0)
                ||(null!=dto.getGivePayOverdue()&&dto.getGivePayOverdue()>0)){
            dto.setGivePayOverdue(givePayOverdue.doubleValue());
        } else{
            dto.setGivePayOverdue(null);
        }

        //如果逾期没有还清重新计算逾期费用(剩余应还本金*(逾期天数=当前日期与最新实际还款日期时间差)*逾期费率)+已还逾期费
        /*
        if(2==dto.getStatus()){
            try {
                int day = AfterLoanServceCommon.compareDate(tmp.getRepaymentDate(),dto.getRealityPayDate(),new SimpleDateFormat("yyyy-MM-dd"));
                if(day<0) {
                    BigDecimal surplusPrincipal = null;
                    if (1 == repaymentType) {
                        surplusPrincipal = new BigDecimal(Double.toString(tmp.getSurplusPrincipal()))
                                .subtract(new BigDecimal(Double.toString(null == dto.getGivePayPrincipal() ? 0 : dto.getGivePayPrincipal())));
                    } else if (2 == repaymentType) {
                        surplusPrincipal = new BigDecimal(Double.toString(tmp.getRepayPrincipal()))
                                .subtract(new BigDecimal(Double.toString(null == dto.getGivePayPrincipal() ? 0 : dto.getGivePayPrincipal())));
                    }
                    int abs = Math.abs(day);
                    Double repayOverdue = surplusPrincipal.multiply(new BigDecimal(Double.toString(abs)))
                            .multiply(new BigDecimal(Double.toString(tmp.getOverdueRate())))
                            .divide(new BigDecimal("100"),BigDecimal.ROUND_HALF_UP)
                            .add(new BigDecimal(Double.toString(tmp.getGivePayOverdue()))).doubleValue();
                    dto.setRepayOverdue(repayOverdue);
                }
            } catch (Exception e){
                RespHelper.setFailDataObject(result,null, RespStatusEnum.FAIL.getMsg());
                e.printStackTrace();
            }
            if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        }
        */
        //计算应还金额 应还本金+应还利息+逾期罚息
        if(null!=dto.getRepayOverdue()){
            BigDecimal repayAmount = BigDecimal.valueOf(null==tmp.getRepayPrincipal()?0d:tmp.getRepayPrincipal())
                    .add(BigDecimal.valueOf(tmp.getRepayInterest()))
                    .add(BigDecimal.valueOf(dto.getRepayOverdue()));
            dto.setRepayAmount(repayAmount.doubleValue());
        }
        //溢出
        if(null!=log.getOverflow()&&log.getOverflow()>0){
            dto.setOverflow(BigDecimal.valueOf(null==tmp.getOverflow()?0d:tmp.getOverflow())
                    .add(BigDecimal.valueOf(log.getOverflow())).doubleValue());
        }
    }

    @Override
    public Map<String,Object> selectInOrderNo(String orderNo){
        List<AfterLoanListDto> list = afterLoanListMapper.selectInOrderNo(orderNo);
        Map<String,Object> map = new HashMap<String,Object>();
        if(null!=list&&list.size()>0) {
            for (AfterLoanListDto obj : list) {
                map.put(obj.getOrderNo(),obj.getRepaymentStatus());
            }
        }
        return map;
    }

    public String getOrderNos(List<AfterLoanListDto> list){
        String orderNos = "";
        for (AfterLoanListDto obj:list){
            if(StringUtils.isNotBlank(obj.getOrderNo())){
                orderNos += "'"+obj.getOrderNo()+"'"+",";
            }
        }
        if(StringUtil.isNotBlank(orderNos)&&
                orderNos.endsWith(",")) {
            orderNos = orderNos.substring(0, orderNos.length() - 1);
        }
        return orderNos;
    }
    public List<AfterLoanListDto>  mappingOrderData(List<AfterLoanListDto> list){
        String orderNos = getOrderNos(list);
        HttpUtil http = new HttpUtil();
        OrderListDto order = new OrderListDto();
        order.setOrderNo(orderNos);
        List<OrderListDto> listOrder =  http.getList(Constants.LINK_CREDIT, "/credit/order/base/v/queryOrderBaseList",order, OrderListDto.class);
        if(null==listOrder||listOrder.size()<=0){
            return list;
        }
        mappingOrderData(list,listOrder);
        return list;
    }
    public void mappingOrderData(List<AfterLoanListDto> list, List<OrderListDto> order){
        for (AfterLoanListDto obj:list){
            Iterator<OrderListDto> iterator = order.iterator();
            while (iterator.hasNext()){
                OrderListDto tmpOrder = iterator.next();
                if (tmpOrder.getOrderNo().equals(obj.getOrderNo())){
                    obj.setProcessId(tmpOrder.getProcessId());
                    obj.setState(tmpOrder.getState());
                }
            }
        }
    }

    @Override
    public Integer delete(AfterLoanListDto obj) {
        return afterLoanListMapper.delete(obj);
    }

    /**
     *
     * @param repaymentStatus 还款状态:2部分还款,3已还款
     * @param repaymentPeriods 还款期数
     * @param loan
     */
    public void sendMsg(int repaymentStatus,
                        AfterLoanListDto loan,
                        int repaymentPeriods,
                        AfterLoanLogDto log){
        String repaymentStatusName = "";
        if(2==repaymentStatus){
            repaymentStatusName = "部分还款";
        } else if(3==repaymentStatus){
            repaymentStatusName = "还款";
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        Double loanAmount = BigDecimal.valueOf(null==log.getGivePayPrincipal()?0d:log.getGivePayPrincipal())
                .add(BigDecimal.valueOf(null==log.getGivePayInterest()?0d:log.getGivePayInterest()))
                .add(BigDecimal.valueOf(null==log.getGivePayOverdue()?0d:log.getGivePayOverdue())).doubleValue();
        String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
        String loanAmountStr = "";
        if(loanAmount>1) {
            loanAmountStr = decimalFormat.format(loanAmount);
        } else {
            loanAmountStr = Double.toString(loanAmount);
        }
        //发送客户
        AmsUtil.smsSend2(loan.getPhoneNumber(), ipWhite,Constants.SMS_FINANCE_AFTERLOAN_CUSTOMER,"hyt",loan.getBorrowerName(),repaymentStatusName,loanAmountStr,loan.getChannelManagerName(),loan.getChannelManagerPhone());
        //发送渠道经理
        AmsUtil.smsSend2(loan.getChannelManagerPhone(), ipWhite,Constants.SMS_FINANCE_AFTERLOAN_CHANNELMANAGER,"hyt",loan.getBorrowerName(),loan.getPhoneNumber(),repaymentPeriods,repaymentStatusName);
    }
    @Override
    public Integer closeMsg(Integer repaymentType,AlterLoanBudgetRepaymentDto obj){
        Integer success = 0;
        if(1==repaymentType){
            success = afterloanFirstInterestMapper.updateByOrderNo(obj);
        } else if(2==repaymentType){
            success = afterloanEqualInterestMapper.updateByOrderNo(obj);
        }
        return success;
    }

    @Override
    public void downloadRepayment(AfterLoanListDto obj, UserDto user, ExcelService excelService, HttpServletResponse response) throws Exception{
        HSSFWorkbook workbook = null;
        try{
            AfterLoanListDto select = afterLoanListMapper.select(obj);
            workbook = excelService.createWriteWork();
            AlterLoanBudgetRepaymentDto dto = new AlterLoanBudgetRepaymentDto();
            dto.setOrderNo(obj.getOrderNo());
            dto.setPageSize(0);
            List<AlterLoanBudgetRepaymentDto> list = null;
            if(1==select.getRepaymentType()){
                list = afterloanFirstInterestMapper.list(dto);
            } else if(2==select.getRepaymentType()){
                list = afterloanEqualInterestMapper.list(dto);
            } else {
                return;
            }
            HSSFSheet sheet = createSheet(workbook, excelService);
            setSheetDate(workbook,sheet,list,excelService);
            getBytes(select.getBorrowerName()+"还款计划.xlsx",workbook,"application/vnd.ms-excel",response);
        } catch (Exception e){
            throw new Exception(e);
        } finally {
            workbook = null;
        }
    }
    public void getBytes(String fileName, HSSFWorkbook workbook,String contentType,HttpServletResponse response)throws Exception{
        response.setContentType(StringUtil.isBlank(contentType)? MediaType.APPLICATION_OCTET_STREAM.getType():contentType);
        response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
    }
    public void setSheetDate(HSSFWorkbook workbook,HSSFSheet sheet,List<AlterLoanBudgetRepaymentDto> list,ExcelService excelService){
        HSSFRow row;
        HSSFCell cell;
        HSSFCellStyle borderAll = excelService.createCellStyle(workbook, (short) 10);
        excelService.getBorderAll(borderAll);
        AlterLoanBudgetRepaymentDto obj;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String val = "";
        for (int i=1;i<=list.size();i++){
            obj = list.get(i-1);
            row = sheet.createRow(i);
            row.setHeight((short) (25 * 20));
            cell = row.createCell(0);
            cell.setCellValue(obj.getRepaymentPeriods());//还款期数
            cell.setCellStyle(borderAll);

            cell = row.createCell(1);
            cell.setCellValue(null==obj.getRepaymentDate()?"":format.format(obj.getRepaymentDate()));//还款日期
            cell.setCellStyle(borderAll);

            cell = row.createCell(2);
            val = BigDecimal.valueOf(null==obj.getSurplusPrincipal()?0d:obj.getSurplusPrincipal()).toPlainString();
            cell.setCellValue(val);//剩余本金（元）
            cell.setCellStyle(borderAll);

            cell = row.createCell(3);
            val = BigDecimal.valueOf(null==obj.getRepayAmount()?0d:obj.getRepayAmount()).toPlainString();
            cell.setCellValue(val);//应还金额（元）
            cell.setCellStyle(borderAll);

            cell = row.createCell(4);
            val = BigDecimal.valueOf(null==obj.getRepayPrincipal()?0d:obj.getRepayPrincipal()).toPlainString();
            cell.setCellValue(val);//应还本金（元）
            cell.setCellStyle(borderAll);

            cell = row.createCell(5);
            val = BigDecimal.valueOf(null==obj.getRepayInterest()?0d:obj.getRepayInterest()).toPlainString();
            cell.setCellValue(val);//应还利息（元）
            cell.setCellStyle(borderAll);

            cell = row.createCell(6);
            val = BigDecimal.valueOf(null==obj.getRepayOverdue()?0d:obj.getRepayOverdue()).toPlainString();
            cell.setCellValue(val);//应还逾期费（元）
            cell.setCellStyle(borderAll);

            cell = row.createCell(7);
            val = BigDecimal.valueOf(null==obj.getGivePayPrincipal()?0d:obj.getGivePayPrincipal()).toPlainString();
            cell.setCellValue(val);//已还本金（元）
            cell.setCellStyle(borderAll);

            cell = row.createCell(8);
            val = BigDecimal.valueOf(null==obj.getGivePayInterest()?0d:obj.getGivePayInterest()).toPlainString();
            cell.setCellValue(val);//已还利息（元）
            cell.setCellStyle(borderAll);

            cell = row.createCell(9);
            val = BigDecimal.valueOf(null==obj.getGivePayOverdue()?0d:obj.getGivePayOverdue()).toPlainString();
            cell.setCellValue(val);//已还逾期费（元）
            cell.setCellStyle(borderAll);

            cell = row.createCell(10);
            cell.setCellValue(obj.getLateDay());//逾期天数
            cell.setCellStyle(borderAll);

            cell = row.createCell(11);
            cell.setCellValue(getStatusName(obj.getStatus()));//还款状态
            cell.setCellStyle(borderAll);
        }
    }
    public String getStatusName(int status){
        String statusName = "";
        switch (status){
            case 1:statusName = "待还款";
                break;
            case 2: statusName = "部分还款";
                break;
            case 3: statusName = "已还款";
                break;
            case 4: statusName = "结清还款";
                break;

        }
        return statusName;
    }
    public HSSFSheet createSheet(HSSFWorkbook workbook,ExcelService excelService){
         HSSFCellStyle cellStyle = excelService.createCellStyle(workbook, (short) 12);
         HSSFFont font = workbook.createFont();
         //设置粗体
         font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
         cellStyle.setFont(font);
         excelService.getBorderAll(cellStyle);
         //创建工作表
         HSSFSheet sheet = workbook.createSheet();
         sheet.setColumnWidth(0,20 * 200);
         sheet.setColumnWidth(1,20 * 200);
         sheet.setColumnWidth(2,20 * 200);
         sheet.setColumnWidth(3,20 * 200);
         sheet.setColumnWidth(4,20 * 200);
         sheet.setColumnWidth(5,20 * 200);
         sheet.setColumnWidth(6,20 * 200);
         sheet.setColumnWidth(7,20 * 200);
         sheet.setColumnWidth(8,20 * 200);
         sheet.setColumnWidth(9,20 * 200);
         sheet.setColumnWidth(10,20 * 200);
         sheet.setColumnWidth(11,20 * 200);
         sheet.setColumnWidth(12,20 * 200);
         //创建行:第1行
         HSSFRow row = sheet.createRow(0);
         //设置行高度
         row.setHeight((short) (25 * 20));

         //创建单元格:第1列
         HSSFCell cell = row.createCell(0);
         cell.setCellValue("还款期数");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(1);
         cell.setCellValue("还款日期");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(2);
         cell.setCellValue("剩余本金（元）");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(3);
         cell.setCellValue("应还金额（元）");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(4);
         cell.setCellValue("应还本金（元）");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(5);
         cell.setCellValue("应还利息（元）");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(6);
         cell.setCellValue("应还逾期费（元）");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(7);
         cell.setCellValue("已还本金（元）");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(8);
         cell.setCellValue("已还利息（元）");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(9);
         cell.setCellValue("已还逾期费（元）");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(10);
         cell.setCellValue("逾期天数");
         cell.setCellStyle(cellStyle);

         cell = row.createCell(11);
         cell.setCellValue("还款状态");
         cell.setCellStyle(cellStyle);
         return sheet;
    }

    @Transactional
    @Override
    public void withdrawLogRecord(RespStatus result, AfterLoanLogDto obj, UserDto user){

        AfterLoanLogDto afterLoanLogDto = afterLoanLogMapper.selectLogById(obj);
        if(null==afterLoanLogDto){
            RespHelper.setFailRespStatus(result,"没有查询到该操作记录,或已被删除!");
        }
        AfterLoanListDto loan = new AfterLoanListDto();
        loan.setOrderNo(afterLoanLogDto.getOrderNo());
        loan = afterLoanListMapper.select(loan);

        AfterLoanLogDto pre = afterLoanLogMapper.selectPreLog(afterLoanLogDto);
        AlterLoanBudgetRepaymentDto tmp = new AlterLoanBudgetRepaymentDto();
        tmp.setOrderNo(loan.getOrderNo());
        tmp.setRepaymentPeriods(afterLoanLogDto.getRepaymentPeriods());
        if(1==loan.getRepaymentType()){
            tmp = afterloanFirstInterestMapper.selectByOrderNoAndPeriods(tmp);
        } else if(2==loan.getRepaymentType()) {
            tmp = afterloanEqualInterestMapper.selectByOrderNoAndPeriods(tmp);
        }
        if(null==pre){
            tmp.setGivePayPrincipal(0d);
            tmp.setGivePayInterest(0d);
            tmp.setGivePayOverdue(0d);
            tmp.setStatus(1);
            tmp.setLateDay(0);
            tmp.setStatusName("待还款");
            tmp.setRealityPayDate(null);
            tmp.setSpecial("setNull");
        } else {
            tmp.setRepayOverdue(pre.getRepayOverdue());     //应还逾期/元(取上一期应还逾期)
            Double givePayOverdue = BigDecimal.valueOf(null==tmp.getGivePayOverdue()?0d:tmp.getGivePayOverdue())
                                    .subtract(BigDecimal.valueOf(null==afterLoanLogDto.getGivePayOverdue()?0d:afterLoanLogDto.getGivePayOverdue()))
                                    .setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
            tmp.setGivePayOverdue(0d>=givePayOverdue?0d:givePayOverdue); //已还逾期
            Double givePayInterest = BigDecimal.valueOf(null==tmp.getGivePayInterest()?0d:tmp.getGivePayInterest())
                                    .subtract(BigDecimal.valueOf(null==afterLoanLogDto.getGivePayInterest()?0d:afterLoanLogDto.getGivePayInterest()))
                                    .setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
            tmp.setGivePayInterest(0d>=givePayInterest?0d:givePayInterest);  //已还利息/元
            Double givePayPrincipal = BigDecimal.valueOf(null==tmp.getGivePayPrincipal()?0d:tmp.getGivePayPrincipal())
                                    .subtract(BigDecimal.valueOf(null==afterLoanLogDto.getGivePayPrincipal()?0d:afterLoanLogDto.getGivePayPrincipal()))
                                    .setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
            tmp.setGivePayPrincipal(givePayPrincipal);      //已还本金

            Double repayAmount = BigDecimal.valueOf(null==tmp.getRepayPrincipal()?0d:tmp.getRepayPrincipal())
                                .add(BigDecimal.valueOf(null==tmp.getRepayInterest()?0d:tmp.getRepayInterest()))
                                .add(BigDecimal.valueOf(null==tmp.getRepayOverdue()?0d:tmp.getRepayOverdue()))
                                .setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
            tmp.setRepayAmount(repayAmount);
            tmp.setRealityPayDate(pre.getRealityPayDate());
        }
        if((null!=tmp.getGivePayPrincipal()&&tmp.getGivePayPrincipal()>0)
                ||(null!=tmp.getGivePayInterest()&&tmp.getGivePayInterest()>0)
                ||(null!=tmp.getGivePayOverdue()&&tmp.getGivePayOverdue()>0)){
            tmp.setStatus(2);
            tmp.setStatusName("部分还款");
        } else {
            tmp.setStatus(1);
            tmp.setStatusName("待还款");
        }
        tmp.setUpdateUid(user.getUid());
        if(1==loan.getRepaymentType()){
            afterloanFirstInterestMapper.updateByOrderNo(tmp);
        } else if(2==loan.getRepaymentType()) {
           afterloanEqualInterestMapper.updateByOrderNo(tmp);
        }
        afterLoanLogDto.setUpdateUid(user.getUid());
        afterLoanLogDto.setWithdraw(1);
        afterLoanLogMapper.update(afterLoanLogDto);
        RespHelper.setSuccessRespStatus(result);

    }
}
