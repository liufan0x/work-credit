package com.anjbo.service.yntrust;

import com.anjbo.bean.yntrust.YntrustBorrowDto;
import com.anjbo.bean.yntrust.YntrustContractDto;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
public interface YntrustBorrowService {

    YntrustBorrowDto select(YntrustBorrowDto obj);

    void delete(YntrustBorrowDto obj);

    Integer insert(YntrustBorrowDto obj);

    Integer update(YntrustBorrowDto obj);

    void addBorrowAndContract(YntrustBorrowDto borrow,YntrustContractDto contract);

    Integer insertMap(Map<String,Object> map);

    Integer updateMap(Map<String,Object> map);
}
