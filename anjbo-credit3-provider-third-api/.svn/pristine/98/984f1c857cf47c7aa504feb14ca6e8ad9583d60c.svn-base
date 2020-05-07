package com.anjbo.dao.yntrust;

import com.anjbo.bean.yntrust.YntrustContractDto;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
public interface YntrustContractMapper{

    YntrustContractDto select(YntrustContractDto obj);

    void delete(YntrustContractDto obj);

    void deleteByOrderNo(@Param("orderNo") String orderNo);

    Integer insert(YntrustContractDto obj);

    Integer insertMap(Map<String, Object> map);

    Integer update(YntrustContractDto obj);

    Integer updateMap(Map<String, Object> map);
}
