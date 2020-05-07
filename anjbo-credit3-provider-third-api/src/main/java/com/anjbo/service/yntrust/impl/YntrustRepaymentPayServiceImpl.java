package com.anjbo.service.yntrust.impl;

import com.anjbo.bean.yntrust.YntrustRepaymentPayDto;
import com.anjbo.dao.yntrust.YntrustRepaymentPayMapper;
import com.anjbo.service.yntrust.YntrustRepaymentPayService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service
public class YntrustRepaymentPayServiceImpl implements YntrustRepaymentPayService {
    @Resource
    private YntrustRepaymentPayMapper yntrustRepaymentPayMapper;
    @Override
    public YntrustRepaymentPayDto select(YntrustRepaymentPayDto obj) {
        return yntrustRepaymentPayMapper.select(obj);
    }

    @Override
    public void delete(YntrustRepaymentPayDto obj) {
        yntrustRepaymentPayMapper.delete(obj);
    }

    @Override
    public Integer insert(YntrustRepaymentPayDto obj) {
        YntrustRepaymentPayDto tmp = new YntrustRepaymentPayDto();
        tmp.setOrderNo(obj.getOrderNo());
        Integer success = 0;
        tmp = yntrustRepaymentPayMapper.select(obj);
        if(null==tmp){
            success = yntrustRepaymentPayMapper.insert(obj);
        } else {
            obj.setId(tmp.getId());
            success = yntrustRepaymentPayMapper.update(obj);
        }
        return success;
    }

    @Override
    public Integer update(YntrustRepaymentPayDto obj) {
        return yntrustRepaymentPayMapper.update(obj);
    }

    @Override
    public Integer insertMap(Map<String, Object> map) {
        YntrustRepaymentPayDto pay = new YntrustRepaymentPayDto();
        pay.setOrderNo(MapUtils.getString(map,"orderNo"));
        pay = yntrustRepaymentPayMapper.select(pay);
        Integer success = 0;
        if(null==pay){
            success = yntrustRepaymentPayMapper.insertMap(map);
        } else {
            success = yntrustRepaymentPayMapper.updateMap(map);
        }
        return success;
    }

    @Override
    public Integer updateMap(Map<String, Object> map) {
        return null;
    }
}
