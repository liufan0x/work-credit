package com.anjbo.service.lineparty;

import com.anjbo.bean.lineparty.PlatformDto;

public interface PlatformService {
	 PlatformDto selectOne(String idCardNumber);
	 
	 int insertOne(PlatformDto platformDto);
}