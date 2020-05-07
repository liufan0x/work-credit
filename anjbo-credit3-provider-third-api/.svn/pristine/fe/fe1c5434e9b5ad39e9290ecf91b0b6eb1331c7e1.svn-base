package com.anjbo.service.yntrust.impl;

import com.anjbo.bean.yntrust.YntrustContractDto;
import com.anjbo.dao.yntrust.YntrustContractMapper;
import com.anjbo.service.yntrust.YntrustContractService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service
public class YntrustContractServiceImpl implements YntrustContractService {
    @Resource
    private YntrustContractMapper yntrustContractMapper;
    @Override
    public YntrustContractDto select(YntrustContractDto obj) {
        return yntrustContractMapper.select(obj);
    }

    @Override
    public void delete(YntrustContractDto obj) {
        yntrustContractMapper.delete(obj);
    }

    @Override
    public Integer insert(YntrustContractDto obj) {
        if(null==obj.getContractAmount()){
            obj.setContractAmount(obj.getAmount());
        }
        if(null!=obj.getSignDayRate()){
            BigDecimal b1 = new BigDecimal(Double.toString(obj.getSignDayRate()));
            BigDecimal b2 = new BigDecimal("360");
            Double tmp = b1.multiply(b2).doubleValue();
            obj.setSignRate(tmp);
        }
        if(null!=obj.getOverdueDayRate()){
            BigDecimal b1 = new BigDecimal(Double.toString(obj.getOverdueDayRate()));
            //b1 = b1.multiply(new BigDecimal("360"));
            Double tmp = b1.multiply(new BigDecimal("100")).doubleValue();
            obj.setOverdueRate(tmp);
        }
        YntrustContractDto old = new YntrustContractDto();
        old.setOrderNo(obj.getOrderNo());
        old = yntrustContractMapper.select(old);
        Integer success = 0;
        if(null==old){
            success = yntrustContractMapper.insert(obj);
        } else {
            success = yntrustContractMapper.update(obj);
        }
        return success;
    }

    @Override
    public Integer update(YntrustContractDto obj) {
        if(null!=obj.getSignDayRate()){
            BigDecimal b1 = new BigDecimal(Double.toString(obj.getSignDayRate()));
            BigDecimal b2 = new BigDecimal("360");
            Double tmp = b1.multiply(b2).doubleValue();
            obj.setSignRate(tmp);
        }
        if(null!=obj.getOverdueDayRate()){
            BigDecimal b1 = new BigDecimal(Double.toString(obj.getOverdueDayRate()));
            //b1 = b1.multiply(new BigDecimal("360"));
            Double tmp = b1.multiply(new BigDecimal("100")).doubleValue();
            obj.setOverdueRate(tmp);
        }
        return yntrustContractMapper.update(obj);
    }

    @Override
    public Integer insertMap(Map<String, Object> map) {
        if(null== MapUtils.getDouble(map,"contractAmount")){
            map.put("contractAmount", MapUtils.getString(map,"amount"));
        }
        if(null!= MapUtils.getDouble(map,"signDayRate")){
            BigDecimal b1 = new BigDecimal(MapUtils.getString(map,"signDayRate"));
            BigDecimal b2 = new BigDecimal("360");
            Double tmp = b1.multiply(b2).doubleValue();
            map.put("signRate",tmp);
        }
        if(null!= MapUtils.getDouble(map,"overdueDayRate")){
            BigDecimal b1 = new BigDecimal(MapUtils.getString(map,"overdueDayRate"));
            //b1 = b1.multiply(new BigDecimal("360"));
            Double tmp = b1.multiply(new BigDecimal("100")).doubleValue();
            map.put("overdueRate",tmp);
        }
        Integer success = 0;
        String orderNo = MapUtils.getString(map,"orderNo");
        YntrustContractDto contract = new YntrustContractDto();
        contract.setOrderNo(orderNo);
        contract = yntrustContractMapper.select(contract);
        if(null==contract){
            success = yntrustContractMapper.insertMap(map);
        } else {
            success = yntrustContractMapper.updateMap(map);
        }
        return success;
    }

    @Override
    public Integer updateMap(Map<String, Object> map) {
        if(null!= MapUtils.getDouble(map,"signDayRate")){
            BigDecimal b1 = new BigDecimal(MapUtils.getString(map,"signDayRate"));
            BigDecimal b2 = new BigDecimal("360");
            Double tmp = b1.multiply(b2).doubleValue();
            map.put("signRate",tmp);
        }
        if(null!= MapUtils.getDouble(map,"overdueDayRate")){
            BigDecimal b1 = new BigDecimal(MapUtils.getString(map,"overdueDayRate"));
            //b1 = b1.multiply(new BigDecimal("360"));
            Double tmp = b1.multiply(new BigDecimal("100")).doubleValue();
            map.put("overdueRate",tmp);
        }
        return yntrustContractMapper.updateMap(map);
    }
}
