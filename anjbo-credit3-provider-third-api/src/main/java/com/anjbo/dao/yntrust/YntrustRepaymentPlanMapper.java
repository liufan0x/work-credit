package com.anjbo.dao.yntrust;

import com.anjbo.bean.yntrust.YntrustRepaymentPlanDto;

/**
 * Created by Administrator on 2018/3/8.
 */
public interface YntrustRepaymentPlanMapper{

    YntrustRepaymentPlanDto select(YntrustRepaymentPlanDto obj);

    void delete(YntrustRepaymentPlanDto obj);

    Integer insert(YntrustRepaymentPlanDto obj);

    Integer update(YntrustRepaymentPlanDto obj);

}
