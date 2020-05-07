package com.anjbo.service.impl;

import com.anjbo.dao.EleAccessMapper;
import com.anjbo.service.EleAccessService;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2018/1/11.
 */
@Service
public class EleAccessServiceImpl implements EleAccessService {

    @Autowired
    private EleAccessMapper eleAccessMapper;

    @Override
    public int selectElementAccessCount(Map<String,Object> param){
        return eleAccessMapper.selectElementAccessCount(param);
    }
    @Override
    public List<Map<String,Object>> selectElementAccessList(Map<String,Object> param){
        return eleAccessMapper.selectElementAccessList(param);
    }
    @Override
    public Map<String,Object> selectElementAccessFlowDetail(Map<String,Object> param){
        return eleAccessMapper.selectElementAccessFlowDetail(param);
    }
    @Override
    public List<Map<String, Object>> selectElementAuditFlowListByDbId(Map<String,Object> param){
        return eleAccessMapper.selectElementAuditFlowListByDbId(param);
    }

    @Override
    public Map<String,Object> getElementAuditBaseDetail (Map<String,Object> param){
        return eleAccessMapper.getElementAuditBaseDetail(param);
    }
    @Override
    public List<Map<String,Object>> selectElementFileList(Map<String,Object> param,String key){
        String[] files = MapUtils.getString(param,key).split(",");
        List<Integer> fileList = new ArrayList<Integer>();
        for (String file : files){
            if (StringUtil.isNotEmpty(file))
                fileList.add(Integer.parseInt(file));
        }
        param.put("fileList",fileList);
        List<Map<String, Object>> list = eleAccessMapper.selectElementFileList(param);
        param.remove("fileList");
        return list;
    }
    @Override
    public List<Map<String,Object>> selectAllElementFileList(){
        return eleAccessMapper.selectAllElementFileList();
    }

    @Override
    public Map<String,Object> getElementOrderDetail(Map<String,Object> param){
        return eleAccessMapper.getElementOrderDetail(param);
    }

    @Override
    public int selectElementCountByOrderNo(Map<String,Object> param){
        return eleAccessMapper.selectElementCountByOrderNo(param);
    }

    @Override
    public List<Map<String, Object>> selectElementListByOrderNo(Map<String,Object> param){
        return eleAccessMapper.selectElementListByOrderNo(param);
    }
}
