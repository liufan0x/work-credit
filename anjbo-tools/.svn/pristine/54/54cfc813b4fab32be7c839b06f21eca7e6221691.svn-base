  
package com.anjbo.service.tools.impl;  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.anjbo.bean.tools.VersionDto;
import com.anjbo.dao.tools.VersionMapper;
import com.anjbo.service.tools.VersionService;

/**
 * 版本信息
 * @ClassName: VersionServiceImpl 
 * @author limh limh@zxsf360.com
 * @date 2015-2-12 下午03:13:15
 */
@Service
public class VersionServiceImpl implements VersionService {

	@Resource
	private VersionMapper versionMapper;

	@Override
	public VersionDto selectVersion(String pack, String type) {
		return versionMapper.selectVersion(pack, type);
	}

	@Override
	public VersionDto selectVersionDetail(int versionId) {
		return versionMapper.selectVersionDetail(versionId);
	}

	@Override
	public int addVersion(VersionDto version) {
		return versionMapper.addVersion(version);
	}

	@Override
	public int addVersionDetail(VersionDto version) {
		return versionMapper.addVersionDetail(version);
	}

	@Override
	public List<VersionDto> selectVersionList() {
		return versionMapper.selectVersionList();
	}

	@Override
	public int selectAudit(String pack, String code, int version) {
		Integer audit = versionMapper.selectAudit(pack, code, version);
		return audit == null? 2 : audit.intValue();
	}

	@Override
	public Map<String, Object> auditHandle(Map<String, Object> param) {
		String key = MapUtils.getString(param, "key");
		Map<String, Object> map = new HashMap<String,Object>();
		if ("add".equals(key)) {
			versionMapper.addAuditDetail(param);
		}else if ("del".equals(key)) {
			versionMapper.deleteAudit(param);
		}else if ("upd".equals(key)) {
			versionMapper.updateAudit(param);
		}else if ("sel".equals(key)) {
			List<Map<String, Object>> list = versionMapper.selectAuditList(param);
			map.put("list", list);
		}
		
		return map;
	}
}
  