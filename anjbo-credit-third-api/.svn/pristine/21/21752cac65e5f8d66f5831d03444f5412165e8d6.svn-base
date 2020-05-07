package com.anjbo.service.yntrust.impl;

import com.anjbo.bean.yntrust.YntrustRepaymentInfoDto;
import com.anjbo.dao.yntrust.YntrustRepaymentInfoMapper;
import com.anjbo.service.yntrust.YntrustRepaymentInfoService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service
public class YntrustRepaymentInfoServiceImpl implements YntrustRepaymentInfoService {
    @Resource
    private YntrustRepaymentInfoMapper yntrustRepaymentInfoMapper;

    @Override
    public YntrustRepaymentInfoDto select(YntrustRepaymentInfoDto obj) {
        return yntrustRepaymentInfoMapper.select(obj);
    }

    @Override
    public void delete(YntrustRepaymentInfoDto obj) {
        yntrustRepaymentInfoMapper.delete(obj);
    }

    @Override
    public Integer insert(YntrustRepaymentInfoDto obj) {
        Integer success = 0;
        YntrustRepaymentInfoDto tmp = new YntrustRepaymentInfoDto();
        tmp.setOrderNo(obj.getOrderNo());
        tmp = yntrustRepaymentInfoMapper.select(tmp);
        if(null==tmp){
            success = yntrustRepaymentInfoMapper.insert(obj);
        } else {
            obj.setId(tmp.getId());
            success = yntrustRepaymentInfoMapper.update(obj);
        }
        return success;
    }

    @Override
    public Integer update(YntrustRepaymentInfoDto obj) {
        return yntrustRepaymentInfoMapper.update(obj);
    }

    @Override
    public Integer deleteRepaymentDetail(Map<String, Object> map) {
        return yntrustRepaymentInfoMapper.deleteRepaymentDetail(map);
    }

    @Override
    public void bacthInsertRepaymentDetail(List<Map<String, Object>> list) {
        yntrustRepaymentInfoMapper.bacthInsertRepaymentDetail(list);
    }

    @Override
    public Integer insertMap(Map<String, Object> map) {
        YntrustRepaymentInfoDto info = new YntrustRepaymentInfoDto();
        info.setOrderNo(MapUtils.getString(map,"orderNo"));
        info = yntrustRepaymentInfoMapper.select(info);
        Integer success = 0;
        if(null==info){
            success = yntrustRepaymentInfoMapper.insertMap(map);
        } else {
            success = yntrustRepaymentInfoMapper.updateMap(map);
        }
        return success;
    }

    @Override
    public Integer updateMap(Map<String, Object> map) {
        return yntrustRepaymentInfoMapper.updateMap(map);
    }

    public Integer insertRepaymentDetail(Map<String,Object> map){
        return yntrustRepaymentInfoMapper.insertRepaymentDetail(map);
    }
}
