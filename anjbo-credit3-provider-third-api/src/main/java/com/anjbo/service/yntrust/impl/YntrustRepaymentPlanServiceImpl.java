package com.anjbo.service.yntrust.impl;

import com.anjbo.bean.yntrust.YntrustRepaymentPlanDto;
import com.anjbo.dao.yntrust.YntrustRepaymentPlanMapper;
import com.anjbo.service.yntrust.YntrustRepaymentPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service
public class YntrustRepaymentPlanServiceImpl implements YntrustRepaymentPlanService {

    @Resource
    private YntrustRepaymentPlanMapper yntrustRepaymentPlanMapper;

    @Override
    public YntrustRepaymentPlanDto select(YntrustRepaymentPlanDto obj) {
        return yntrustRepaymentPlanMapper.select(obj);
    }

    @Override
    public void delete(YntrustRepaymentPlanDto obj) {
        yntrustRepaymentPlanMapper.delete(obj);
    }

    @Override
    public Integer insert(YntrustRepaymentPlanDto obj) {
        YntrustRepaymentPlanDto tmp = new YntrustRepaymentPlanDto();
        tmp.setOrderNo(obj.getOrderNo());
        tmp = yntrustRepaymentPlanMapper.select(obj);
        Integer success = 0;
        if(null==tmp){
            success = yntrustRepaymentPlanMapper.insert(obj);
        } else {
            success = yntrustRepaymentPlanMapper.update(obj);
        }
        return success;
    }

    @Override
    public Integer update(YntrustRepaymentPlanDto obj) {
        return yntrustRepaymentPlanMapper.update(obj);
    }
}
