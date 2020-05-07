package com.anjbo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 文件系统操作
 * 
 * @ClassName: FsService
 * @author limh limh@zxsf360.com
 * @date 2014-12-9 下午02:24:12
 */
public interface FsService {

	String uploadImageByFile(MultipartFile file) throws IOException;

	Map<String,Object> uploadImageByFileMap(MultipartFile file) throws Exception;
}
