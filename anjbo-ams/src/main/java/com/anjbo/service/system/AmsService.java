package com.anjbo.service.system;

import com.anjbo.bean.system.AmsDto;

/**
 * 短信系统服务接口
 * @author limh limh@zxsf360.com
 * @date 2016-6-1 下午03:16:59
 */
public interface AmsService {
	int smsSendChannel1(AmsDto amsDto);
	int smsSendChannel2(AmsDto amsDto);
	int smsSendChannel3(AmsDto amsDto);
	int smsSendChannel4(AmsDto amsDto);
}
