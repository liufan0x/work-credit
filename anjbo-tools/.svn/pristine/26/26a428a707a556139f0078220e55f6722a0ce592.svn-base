package com.anjbo.dao.tools;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.tools.MessageDto;
import com.anjbo.bean.tools.TokenDto;

public interface TokenMapper {

	public int addToken(TokenDto tokenDto);
	
	public List<TokenDto> findByType(String type);
	
	public TokenDto findByToken(String token,String uid);
	
	public int updateById(Map<String,Object> map);
	
	public List<TokenDto> findByUid(String uid);
	/**
	 * 更新登录sid
	 * @author limh
	 * @date 2015-8-27 下午02:03:59
	 * @param tokenDto
	 * sid 登录sid
	 * <br>
	 * deviceId 设备id
	 * <br>
	 * uid 登录用户uid
	 * @return 受影响的行数
	 */
	int updateSid(TokenDto tokenDto);

	/**
	 * 更新token
	 * @author limh
	 * @date 2015-8-27 下午02:03:59
	 * @param tokenDto
	 * sid 登录sid
	 * <br>
	 * deviceId 设备id
	 * <br>
	 * uid 登录用户uid
	 * <br>
	 * id 需要更新的主键id
	 * @return 受影响的行数
	 */
	int updateToken(TokenDto tokenDto);
	/**
	 * 根据sid更新deviceId
	 * @Title: updateDeviceId 
	 * @param sid
	 * @param deviceId
	 * @return
	 * int
	 * @throws
	 */
	int updateDeviceId(String sid,String deviceId);
	
	int addMessage(MessageDto messageDto);
}
