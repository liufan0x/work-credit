package com.anjbo.service.impl;

import com.anjbo.bean.finance.AfterLoanLogDto;
import com.anjbo.dao.AfterLoanLogMapper;
import com.anjbo.service.AfterLoanLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/8.
 */
@Service
public class AfterLoanLogServiceImpl implements AfterLoanLogService {

    @Resource
    private AfterLoanLogMapper afterLoanLogMapper;
    @Override
    public List<AfterLoanLogDto> list(AfterLoanLogDto obj) {
        return afterLoanLogMapper.list(obj);
    }

    @Override
    public Integer listCount(AfterLoanLogDto obj) {
        return afterLoanLogMapper.listCount(obj);
    }

    @Override
    public Integer delete(AfterLoanLogDto obj) {
        return afterLoanLogMapper.delete(obj);
    }

    @Override
    public Integer insert(AfterLoanLogDto obj) {
        if(null==obj.getOperateTime()){
            obj.setOperateTime(new Date());
        }
        return afterLoanLogMapper.insert(obj);
    }

    @Override
    public Integer update(AfterLoanLogDto obj) {
        return afterLoanLogMapper.update(obj);
    }

    @Override
    public List<Map<String,Object>> listOperate(AfterLoanLogDto obj){
        return afterLoanLogMapper.listOperate(obj);
    }

    @Override
    public void bacthInsertFile(List<Map<String,Object>> list){
        afterLoanLogMapper.bacthInsertFile(list);
    }

    @Override
    public List<AfterLoanLogDto> selectCurrentPeriodsLog(AfterLoanLogDto obj){
        return afterLoanLogMapper.selectCurrentPeriodsLog(obj);
    }

    public void deleteAll(AfterLoanLogDto obj){
        afterLoanLogMapper.deleteAll(obj);
    }
}
