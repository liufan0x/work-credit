package com.anjbo.service.tools.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.anjbo.bean.tools.MessageDto;
import com.anjbo.bean.tools.TokenDto;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.push.AndroidNotification;
import com.anjbo.common.push.IOSNotification;
import com.anjbo.common.push.android.AndroidUnicast;
import com.anjbo.common.push.ios.IOSUnicast;
import com.anjbo.common.push.umeng.ReqIosUMDto;
import com.anjbo.common.push.umeng.UMengConst;
import com.anjbo.dao.tools.TokenMapper;
import com.anjbo.service.tools.TokenService;
import com.anjbo.utils.StringUtil;
/**
 * 推送token
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:18:09
 */
@Service
public class TokenServiceImpl implements TokenService {
	private Logger log = Logger.getLogger(getClass());
	@Resource
	private TokenMapper tokenMapper;

	public int addToken(TokenDto tokenDto) {
		return tokenMapper.addToken(tokenDto);
	}

	public List<TokenDto> findByType(String type) {
		return tokenMapper.findByType(type);
	}
	
	public TokenDto findByToken(String token,String uid){
		return tokenMapper.findByToken(token,uid);
	}
	
	public int updateById(int id,String uid){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("uid",uid);
		return this.tokenMapper.updateById(map);
	}
	
	public List<TokenDto> findByUid(String uid){
		return this.tokenMapper.findByUid(uid);
	}

	@Override
	public int updateSid(TokenDto tokenDto) {
		return tokenMapper.updateSid(tokenDto);
	}

	@Override
	public int updateToken(TokenDto tokenDto) {
		return tokenMapper.updateToken(tokenDto);
	}

	@Override
	public int updateDeviceId(String sid, String deviceId) {
		return tokenMapper.updateDeviceId(sid, deviceId);
	}
	@Override
	public void pushMsg(String title, String msg, String uid, String code,
			String params, int type) {
		try{
			List<TokenDto> tokenList = findByUid(uid);
			for (TokenDto tokenDto : tokenList) {
				String key = RedisKey.PREFIX.MORTGAGE_LOGININFO+
				tokenDto.getSid()+RedisKey.SPLIT_FLAG+tokenDto.getDeviceId();
				log.info("发送推送消息key="+key);
				if(!RedisOperator.checkKeyExisted(key)){//未登录状态不推送消息
					log.info("判断为未登录，无法发送推送消息key="+key+"");
					continue;
				}
				ReqIosUMDto um = new ReqIosUMDto();
				um.setTokens(tokenDto.getToken());
				um.setTitle(title);
				um.setMsg(msg);
				um.setCode(code);
				um.setParams(params);
				if(UMengConst.TYPE_IOS.equals(tokenDto.getType())){
					um.setImSecret(UMengConst.IOS_AMS);
					iosCast(new IOSUnicast(), um);
				}else if(UMengConst.TYPE_ANDROID.equals(tokenDto.getType())){
					um.setAmSecret(UMengConst.ANDROID_AMS);
					androidCast(new AndroidUnicast(), um);
				}
			}
			tokenMapper.addMessage(getMsgDto(uid, new ReqIosUMDto(title, msg),type));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public MessageDto getMsgDto(String uid, ReqIosUMDto um,int type) {
		MessageDto msg = new MessageDto();
		msg.setCreateTime(new Date());
		msg.setIsRead(0);
		String title = um.getTitle();
		title = StringUtil.isNotEmpty(title) ? title : "系统消息";
		msg.setTitle(title);
		msg.setContent(um.getMsg().replace("【快鸽按揭】",""));
		msg.setType(type);
		msg.setUserId(uid);
		return msg;
	}
	@Override
	public Boolean iosCast(IOSNotification cast, ReqIosUMDto um) {
		Boolean flag = false;
		try {
			cast.setAppMasterSecret(um.getImSecret());
			cast.setPredefinedKeyValue("appkey", UMengConst.IOS_APPKEY);
			cast.setPredefinedKeyValue("timestamp", System.currentTimeMillis()
					+ "");
			// TODO Set your device token
			// b852235f9a597c824fd14f3bdec852ead3dfde8d692fd625087995a3d798fd16
			cast.setPredefinedKeyValue("device_tokens", um.getTokens());
			cast.setPredefinedKeyValue("alert", um.getMsg());
			cast.setPredefinedKeyValue("badge", 1);
			cast.setPredefinedKeyValue("sound", "chime");
			
			// TODO set 'production_mode' to 'true' if your app is under
			// production mode
			cast.setPredefinedKeyValue("production_mode", "true");
			// Set customized fields 设置自定义字段
			cast.setCustomizedField("code", um.getCode());
			cast.setCustomizedField("params", um.getParams());
			flag = cast.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean androidCast(AndroidNotification cast, ReqIosUMDto um) {
		Boolean flag = false;
		try {
			cast.setAppMasterSecret(um.getAmSecret());
			cast.setPredefinedKeyValue("appkey", UMengConst.ANDROID_APPKEY);
			cast.setPredefinedKeyValue("timestamp", System.currentTimeMillis()
					+ "");
			cast.setPredefinedKeyValue("device_tokens", um.getTokens());
			cast.setPredefinedKeyValue("ticker", "Android broadcast ticker");
			cast.setPredefinedKeyValue("title", um.getTitle());
			cast.setPredefinedKeyValue("text", um.getMsg());
			cast.setPredefinedKeyValue("after_open", "go_app");
			cast.setPredefinedKeyValue("display_type", "notification");
			// TODO Set 'production_mode' to 'false' if it's a test device.
			// For how to register a test device, please see the developer doc.
			cast.setPredefinedKeyValue("production_mode", "false");
			// Set customized fields
			cast.setExtraField("code",  um.getCode());
			cast.setExtraField("params", um.getParams());
			flag = cast.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
