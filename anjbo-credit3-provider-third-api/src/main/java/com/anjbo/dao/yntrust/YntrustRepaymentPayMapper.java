package com.anjbo.dao.yntrust;

import com.anjbo.bean.yntrust.YntrustRepaymentPayDto;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
public interface YntrustRepaymentPayMapper{

    YntrustRepaymentPayDto select(YntrustRepaymentPayDto obj);

    void delete(YntrustRepaymentPayDto obj);

    Integer insert(YntrustRepaymentPayDto obj);

    Integer update(YntrustRepaymentPayDto obj);

    Integer insertMap(Map<String, Object> map);

    Integer updateMap(Map<String, Object> map);
}
