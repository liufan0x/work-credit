package com.anjbo.service.yntrust.impl;

import com.anjbo.bean.yntrust.YntrustMappingDto;
import com.anjbo.dao.yntrust.YntrustMappingMapper;
import com.anjbo.service.yntrust.YntrustMappingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service
public class YntrustMappingServiceImpl implements YntrustMappingService {
    @Resource
    private YntrustMappingMapper yntrustMappingMapper;

    @Override
    public YntrustMappingDto select(YntrustMappingDto obj) {
        return yntrustMappingMapper.select(obj);
    }

    @Override
    public void delete(YntrustMappingDto obj) {
        yntrustMappingMapper.delete(obj);
    }

    @Override
    public Integer insert(YntrustMappingDto obj) {
        YntrustMappingDto tmp = new YntrustMappingDto();
        tmp.setOrderNo(obj.getOrderNo());
        tmp.setStatus(1);
        tmp = select(tmp);
        Integer success = 0;
        if(null==tmp) {
            success = yntrustMappingMapper.insert(obj);
        } else {
            obj.setId(tmp.getId());
            success = yntrustMappingMapper.update(obj);
        }
        return success;
    }

    @Override
    public Integer update(YntrustMappingDto obj) {
        return yntrustMappingMapper.update(obj);
    }
}
