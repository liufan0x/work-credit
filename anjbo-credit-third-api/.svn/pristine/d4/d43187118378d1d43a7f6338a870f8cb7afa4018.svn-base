package com.anjbo.service.yntrust.impl;

import com.anjbo.bean.yntrust.YntrustRequestFlowDto;
import com.anjbo.dao.yntrust.YntrustRequestFlowMapper;
import com.anjbo.service.yntrust.YntrustRequestFlowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service
public class YntrustRequestFlowServiceImpl implements YntrustRequestFlowService {
    @Resource
    private YntrustRequestFlowMapper yntrustRequestFlowMapper;

    @Override
    public List<YntrustRequestFlowDto> list(YntrustRequestFlowDto obj) {
        return yntrustRequestFlowMapper.list(obj);
    }

    @Override
    public void delete(YntrustRequestFlowDto obj) {
        yntrustRequestFlowMapper.delete(obj);
    }

    @Override
    public Integer insert(YntrustRequestFlowDto obj) {
        return yntrustRequestFlowMapper.insert(obj);
    }

    @Override
    public Integer update(YntrustRequestFlowDto obj) {
        return yntrustRequestFlowMapper.update(obj);
    }
}
