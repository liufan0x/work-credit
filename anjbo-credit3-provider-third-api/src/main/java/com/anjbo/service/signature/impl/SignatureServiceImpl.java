package com.anjbo.service.signature.impl;

import com.anjbo.dao.signature.SignatureMapper;
import com.anjbo.service.signature.SignatureService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/30.
 */
@Service
public class SignatureServiceImpl implements SignatureService {
    @Resource
    private SignatureMapper signatureMapper;
    @Override
    public Map<String, Object> select(Map<String, Object> map) {
        return signatureMapper.select(map);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> map) {
        return signatureMapper.list(map);
    }

    @Override
    public Integer insert(Map<String, Object> map) {
        Map<String, Object> select = signatureMapper.select(map);
        Integer success = 0;
        if(MapUtils.isEmpty(select)){
            success = signatureMapper.insert(map);
        } else {
            success = signatureMapper.update(map);
        }
        return null==success?0:success;
    }

    @Override
    public void batchInsert(List<Map<String, Object>> list) {
        signatureMapper.batchInsert(list);
    }

    @Override
    public Integer update(Map<String, Object> map) {
        return signatureMapper.update(map);
    }
}
