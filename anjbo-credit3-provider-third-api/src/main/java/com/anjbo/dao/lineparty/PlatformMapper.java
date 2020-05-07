package com.anjbo.dao.lineparty;

import com.anjbo.bean.lineparty.PlatformDto;

public interface PlatformMapper {
 
	
	 PlatformDto selectOne(String idCardNumber);
	 
	 int insertOne(PlatformDto platformDto);
}
