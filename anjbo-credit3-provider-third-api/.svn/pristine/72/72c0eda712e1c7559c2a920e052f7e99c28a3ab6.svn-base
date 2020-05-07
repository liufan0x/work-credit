package com.anjbo.service.umeng.impl;

import org.springframework.stereotype.Service;
import com.anjbo.common.ThirdApiConstants;
import com.anjbo.service.umeng.UmengService;
import com.anjbo.utils.umeng.AndroidNotification;
import com.anjbo.utils.umeng.PushClient;
import com.anjbo.utils.umeng.android.AndroidUnicast;
import com.anjbo.utils.umeng.ios.IOSUnicast;

/**
 * 友盟统计
 * @author Administrator
 */
@Service
public class UmengServiceImpl implements UmengService{
	
	@Override
	public void pushText(String token,String message) throws Exception {
		PushClient client = new PushClient();
		IOSUnicast iosUnicast = new IOSUnicast(ThirdApiConstants.UMENG_IOS_APPKEY,ThirdApiConstants.UMENG_IOS_SECRET);
		iosUnicast.setDeviceToken(token);
		iosUnicast.setAlert(message);
		iosUnicast.setBadge(1);
		iosUnicast.setSound("default");
		iosUnicast.setProductionMode();
		client.send(iosUnicast);
		
		AndroidUnicast androidUnicast = new AndroidUnicast(ThirdApiConstants.UMENG_ANDROID_APPKEY,ThirdApiConstants.UMENG_ANDROID_SECRET);
		androidUnicast.setDeviceToken(token);
		androidUnicast.setTicker(message);
		androidUnicast.setTitle("快马金服");
		androidUnicast.setText(message);
		androidUnicast.goActivityAfterOpen("com.src.xiaogeloan.ui.activity.MiHWPushActivity");
		androidUnicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		androidUnicast.setProductionMode();
		androidUnicast.setExtraField("test", "helloworld");
		client.send(androidUnicast);
	}
	
}
