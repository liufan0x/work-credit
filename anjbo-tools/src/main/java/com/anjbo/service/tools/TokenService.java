package com.anjbo.service.tools;

import java.util.List;

import com.anjbo.bean.tools.MessageDto;
import com.anjbo.bean.tools.TokenDto;
import com.anjbo.common.push.AndroidNotification;
import com.anjbo.common.push.IOSNotification;
import com.anjbo.common.push.umeng.ReqIosUMDto;

public interface TokenService {
	public int addToken(TokenDto tokenDto);

	public List<TokenDto> findByType(String type);
	
	public TokenDto findByToken(String token,String uid);
	
	public List<TokenDto> findByUid(String uid);
	
	public int updateById(int id,String uid);
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
	/**
	 * 推送消息
	 * @Title: pushMsg 
	 * @param title标题
	 * @param msg 内容
	 * @param uid 接收消息人
	 * @param code 前端消息跳转标记，说明见UMengConst类
	 * @param params 如果消息跳转需要参数可以用此字段，比如往详情页跳转需要数据的id值
	 * @param type 消息类型，说明见MessageDto类
	 * void
	 * @throws
	 */
	public void pushMsg(String title,String msg,String uid, String code,String params,int type);
	/**
	 * 组装消息对象
	 * @Title: getMsgDto 
	 * @param uid 消息接收人
	 * @param um 消息标题跟内容
	 * @param type 消息类型，说明见MessageDto类
	 * @return
	 * MessageDto
	 * @throws
	 */
	public MessageDto getMsgDto(String uid, ReqIosUMDto um,int type);
	public Boolean iosCast(IOSNotification cast, ReqIosUMDto um);
	public Boolean androidCast(AndroidNotification cast, ReqIosUMDto um);
}
