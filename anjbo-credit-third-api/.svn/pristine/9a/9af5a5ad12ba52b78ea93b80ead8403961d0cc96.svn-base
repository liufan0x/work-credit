package com.anjbo.service.yntrust.impl;

import com.anjbo.bean.yntrust.YntrustLoanDto;
import com.anjbo.dao.yntrust.YntrustLoanMapper;
import com.anjbo.service.yntrust.YntrustLoanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
@Service
public class YntrustLoanServiceImpl implements YntrustLoanService {
    @Resource
    private YntrustLoanMapper yntrustLoanMapper;

    @Override
    public List<YntrustLoanDto> list(YntrustLoanDto obj) {
        return yntrustLoanMapper.list(obj);
    }

    @Override
    public Integer delete(YntrustLoanDto obj) {
        return yntrustLoanMapper.delete(obj);
    }

    @Override
    public Integer insert(YntrustLoanDto obj) {
        return yntrustLoanMapper.insert(obj);
    }

    @Override
    public Integer batchInsert(List<YntrustLoanDto> obj) {
        YntrustLoanDto tmp = new YntrustLoanDto();
        tmp.setOrderNo(obj.get(0).getOrderNo());
        //删除旧的
        yntrustLoanMapper.deleteByOrderNo(tmp);
        return yntrustLoanMapper.batchInsert(obj);
    }

    @Override
    public Integer update(YntrustLoanDto obj) {
        return yntrustLoanMapper.update(obj);
    }

    public Integer updateByOrderNo(YntrustLoanDto obj){
        return yntrustLoanMapper.updateByOrderNo(obj);
    }

    public Integer deleteByOrderNo(YntrustLoanDto obj){
        return yntrustLoanMapper.deleteByOrderNo(obj);
    }
}
