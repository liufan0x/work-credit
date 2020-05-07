package com.anjbo.service.yntrust;

import com.anjbo.bean.yntrust.YntrustRepaymentInfoDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
public interface YntrustRepaymentInfoService {

    YntrustRepaymentInfoDto select(YntrustRepaymentInfoDto obj);

    void delete(YntrustRepaymentInfoDto obj);

    Integer insert(YntrustRepaymentInfoDto obj);

    Integer update(YntrustRepaymentInfoDto obj);

    Integer deleteRepaymentDetail(Map<String, Object> map);

    void bacthInsertRepaymentDetail(List<Map<String, Object>> list);

    Integer insertMap(Map<String, Object> map);

    Integer updateMap(Map<String, Object> map);

    Integer insertRepaymentDetail(Map<String, Object> map);

}
