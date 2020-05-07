package com.anjbo.service.impl;

import com.anjbo.dao.AuditConfigWebMapper;
import com.anjbo.service.AuditConfigWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2017/12/21.
 */
@Service
public class AuditConfigWebServiceImpl implements AuditConfigWebService {

    @Autowired
    private AuditConfigWebMapper auditConfigWebMapper;
    @Override
    public int save(Map<String, Object> param) {
        return auditConfigWebMapper.save(param);
    }
    @Override
    public int edit(Map<String,Object> param){
        return auditConfigWebMapper.edit(param);
    }
    @Override
    public Map<String,Object> selectAuditConfigDetail(Map<String,Object> param){
        return auditConfigWebMapper.selectAuditConfigDetail(param);
    }
    @Override
    public List<String> selectAuditConfigHaveCityList(){
        return auditConfigWebMapper.selectAuditConfigHaveCityList();
    }

    @Override
    public List<Integer> selectAuditConfigTypeList(Map<String,Object> param){
        return auditConfigWebMapper.selectAuditConfigTypeList(param);
    }

    @Override
    public int selectAuditConfigCount(Map<String,Object> param){
        return auditConfigWebMapper.selectAuditConfigCount(param);
    }

    @Override
    public List<Map<String,Object>> selectAuditConfigList(Map<String,Object> param){
        return auditConfigWebMapper.selectAuditConfigList(param);
    }
	@Override
	public void editState(Map<String, Object> param) {
		auditConfigWebMapper.editState(param);
	}
	@Override
	public List<Map<String, Object>> selectAllCityType() {
		return auditConfigWebMapper.selectAllCityType();
	}
}
