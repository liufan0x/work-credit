package com.anjbo.service.system.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.system.AmsDto;
import com.anjbo.service.system.AmsService;
import com.anjbo.sms.SMSService;

/**
 * 短信系统服务实现
 * @author limh limh@zxsf360.com
 * @date 2016-6-1 下午03:16:49
 */

@Service
public class AmsServiceImpl implements AmsService {

	@Resource
	private SMSService smsService;

	@Override
	public int smsSendChannel1(AmsDto amsDto) {
		return smsService.smsSendChannel1(amsDto.getPhone(),amsDto.getIp(),amsDto.getSvrIp(), amsDto.getSmsContent(), amsDto.getSmsComeFrom());
	}

	@Override
	public int smsSendChannel2(AmsDto amsDto) {
		return smsService.smsSendChannel2(amsDto.getPhone(),amsDto.getIp(),amsDto.getSvrIp(), amsDto.getSmsContent(), amsDto.getSmsComeFrom());
	}

	@Override
	public int smsSendChannel3(AmsDto amsDto) {
		return smsService.smsSendChannel3(amsDto.getPhone(),amsDto.getIp(),amsDto.getSvrIp(), amsDto.getSmsContent(), amsDto.getSmsComeFrom());
	}

	@Override
	public int smsSendChannel4(AmsDto amsDto) {
		return smsService.smsSendChannel4(amsDto.getPhone(),amsDto.getIp(),amsDto.getIp(), amsDto.getSmsContent(), amsDto.getSmsComeFrom(),amsDto.getAmsCode());
	}
}
