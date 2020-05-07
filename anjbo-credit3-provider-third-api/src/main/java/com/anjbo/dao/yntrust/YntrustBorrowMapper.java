package com.anjbo.dao.yntrust;

import com.anjbo.bean.yntrust.YntrustBorrowDto;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
public interface YntrustBorrowMapper{

    YntrustBorrowDto select(YntrustBorrowDto obj);

    void delete(YntrustBorrowDto obj);

    void deleteByOrderNo(@Param("orderNo") String orderNo);

    Integer insert(YntrustBorrowDto obj);

    Integer insertMap(Map<String, Object> map);

    Integer update(YntrustBorrowDto obj);

    Integer updateMap(Map<String, Object> map);
}
