package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.anjbo.bean.UserAuthorityDto;
import com.anjbo.bean.UserDto;
import com.anjbo.dao.AuditFirstMapper;
import com.anjbo.service.AuditFirstService;

@Service
public class AuditFirstServiceImpl implements AuditFirstService {

	@Resource
	AuditFirstMapper auditFirstMapper;
	
	@Override
	public List<Map<String, Object>> findbyFirst(Map<String, Object> pareamt) {
		return auditFirstMapper.findbyFirst(pareamt);
	}

	@Override
	public List<UserAuthorityDto> findByAuthority(String name) {
		return auditFirstMapper.findByAuthority(name);
	}
	@Override
	public List<UserDto> findByUser(Map<String, Object> pareamt) {
		return auditFirstMapper.findByUser(pareamt);
	}

}
