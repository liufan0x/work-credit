package com.anjbo.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.service.FsService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;


/**
 * 文件系统操作
 * 
 * @ClassName: FsServiceImpl
 * @author limh limh@zxsf360.com
 * @date 2014-12-9 下午02:24:12
 */
@Service
public class FsServiceImpl implements FsService {
	
	/**
	 * 上传图片到FS
	 * @return String
	 * @throws IOException 
	 */
	public String uploadImageByFile(MultipartFile file) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = ConfigUtil.getStringValue(Constants.LINK_FS_URL,ConfigUtil.CONFIG_LINK)+"/fs/img/upload";
		map.put("path", "/fc-infos/");
		return HttpUtil.filePost(url, map,file.getInputStream());
	}

}
