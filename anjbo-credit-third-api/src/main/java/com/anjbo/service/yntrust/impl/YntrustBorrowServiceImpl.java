package com.anjbo.service.yntrust.impl;

import com.anjbo.bean.yntrust.YntrustBorrowDto;
import com.anjbo.bean.yntrust.YntrustContractDto;
import com.anjbo.dao.yntrust.YntrustBorrowMapper;
import com.anjbo.dao.yntrust.YntrustContractMapper;
import com.anjbo.service.yntrust.YntrustBorrowService;
import com.anjbo.utils.huarong.StringUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service
public class YntrustBorrowServiceImpl implements YntrustBorrowService {
    @Resource
    private YntrustBorrowMapper yntrustBorrowMapper;
    @Resource
    private YntrustContractMapper yntrustContractMapper;

    @Override
    public YntrustBorrowDto select(YntrustBorrowDto obj) {
        return yntrustBorrowMapper.select(obj);
    }

    @Override
    public void delete(YntrustBorrowDto obj) {
        yntrustBorrowMapper.delete(obj);
    }

    @Override
    public Integer insert(YntrustBorrowDto obj) {
        if(StringUtils.isBlank(obj.getBankReservedPhoneNo())){
            obj.setBankReservedPhoneNo(obj.getTelephoneNo());
        }
        YntrustBorrowDto old = new YntrustBorrowDto();
        old.setOrderNo(obj.getOrderNo());
        old = yntrustBorrowMapper.select(old);
        Integer success = 0;
        if(null==old){
            success = yntrustBorrowMapper.insert(obj);
        } else {
            success = yntrustBorrowMapper.update(obj);
        }
        return success;
    }

    @Override
    public Integer update(YntrustBorrowDto obj) {
        return yntrustBorrowMapper.update(obj);
    }

    @Override
    public void addBorrowAndContract(YntrustBorrowDto borrow,YntrustContractDto contract){
        insert(borrow);
        YntrustContractDto tmp = new YntrustContractDto();
        tmp.setOrderNo(contract.getOrderNo());
        tmp = yntrustContractMapper.select(contract);
        if(null==tmp){
            yntrustContractMapper.insert(contract);
        } else {
            yntrustContractMapper.update(contract);
        }
    }

    @Override
    public Integer insertMap(Map<String, Object> map) {
        String orderNo = MapUtils.getString(map,"orderNo");
        YntrustBorrowDto borrow = new YntrustBorrowDto();
        borrow.setOrderNo(orderNo);
        borrow = yntrustBorrowMapper.select(borrow);
        Integer success = 0;
        if(null==borrow){
            success = yntrustBorrowMapper.insertMap(map);
        } else {
            success = yntrustBorrowMapper.updateMap(map);
        }
        return success;
    }

    @Override
    public Integer updateMap(Map<String, Object> map) {
        return yntrustBorrowMapper.updateMap(map);
    }
}
