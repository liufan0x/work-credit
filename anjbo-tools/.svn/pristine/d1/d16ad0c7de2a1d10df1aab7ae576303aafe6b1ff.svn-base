package com.anjbo.controller.tools;  

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.anjbo.bean.BaseDto;
import com.anjbo.bean.tools.VersionDto;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.tools.VersionService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.StringUtil;
import com.sinaapp.msdxblog.apkUtil.entity.ApkInfo;
import com.sinaapp.msdxblog.apkUtil.utils.ApkUtil;
/**
 * 版本信息
 * @ClassName: VersionController 
 * @author limh limh@zxsf360.com
 * @date 2015-2-12 下午03:13:15
 */
@Controller
@RequestMapping("/tools/version")
public class VersionController{
	private Logger log = Logger.getLogger(getClass());
	
	private final String basePath=System.getProperty("web.root");
	
	/**apk解析包路径**/
	private String aaptPath=basePath+"/WEB-INF/lib/aapt"+(StringUtil.isOSLinux()?"":".exe");
	@Resource
	private VersionService versionService;
	/**
	 * 上传 apk
	 * @Title: uploadApk
	 * @author limh 
	 * @return
	 * String
	 */
	@RequestMapping(value = "/uploadVersion")
	public @ResponseBody 
	RespDataObject<String> uploadVersion(HttpServletRequest request,VersionDto version){
		RespDataObject<String> status = new RespDataObject<String>();
		MultipartFile file = version.getFile();
		if(file.isEmpty()){
			status.setCode(RespStatusEnum.FILE_ERROR.getCode());
			status.setMsg(RespStatusEnum.FILE_ERROR.getMsg());
			return status;
		}
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) file;
		FileItem fileItem = commonsMultipartFile.getFileItem();
		// 文件格式
		String fileName = fileItem.getName();
		String regex = ".+\\.(?i)(apk|ipa)$";
		if (!fileName.matches(regex)) {
			status.setCode(RespStatusEnum.FILE_TYPE_ERROR.getCode());
			status.setMsg(RespStatusEnum.FILE_TYPE_ERROR.getMsg());
			return status;
		}
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		
		/**下载地址**/
		String domain = ConfigUtil.getStringValue("VERSION_DOWNLOAD_URL","http://tools.anjbo.com");
		String downUrl= domain + "/download/"+fileType+"/";
		/**下载路径**/
		String downPath=basePath+"/download/"+fileType+"/";
		/**上传路径**/
		String uploadPath=basePath+"/upload/"+fileType+"/";
		/**临时路径**/
		String tempPath=basePath+"/temp/"+fileType+"/";
		log.info("aaptPath:"+aaptPath);
		try {
			tempPath +=fileName;
			File tempFile = new File(tempPath);
			if(!tempFile.getParentFile().exists()){
				tempFile.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(tempFile);
			byte[] buffer = new byte[1024 * 1024];
			int byteread = 0;
			InputStream fis = fileItem.getInputStream();
			while ((byteread = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, byteread);
				fos.flush();
			}
			fos.close();
			fis.close();
			ApkUtil apkUtil = new ApkUtil();
			apkUtil.setmAaptPath(aaptPath);
			String tempPack=version.getPack();
			int tempVcode=version.getVersion();
			if("apk".equals(fileType)){
				ApkInfo apkInfo = apkUtil.getApkInfo(tempPath);
				tempPack = apkInfo.getPackageName();
				tempVcode = Integer.parseInt(apkInfo.getVersionCode());
			}else if(StringUtils.isEmpty(tempPack)){
				status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
				status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
				return status;
			}
			VersionDto vd = versionService.selectVersion(tempPack,fileType);
			String code = version.getCode();
			if(vd!=null){
				VersionDto vdd = versionService.selectVersionDetail(vd.getId());
				String srcPath = vdd.getPath();
				if("apk".equals(fileType)){
					ApkInfo ai = apkUtil.getApkInfo(srcPath);
					int srcVcode = Integer.parseInt(ai.getVersionCode());
					if(tempVcode==srcVcode){
						status.setCode(RespStatusEnum.HAD_VERSION_ERROR.getCode());
						status.setMsg(RespStatusEnum.HAD_VERSION_ERROR.getMsg());
						return status;
					}else if(tempVcode<srcVcode){
						status.setCode(RespStatusEnum.LOW_VERSION_ERROR.getCode());
						status.setMsg(RespStatusEnum.LOW_VERSION_ERROR.getMsg());
						return status;
					}
				}
				String url=vd.getUrl();
				downUrl=url;
				downPath+=url.substring(url.lastIndexOf("/")+1);
				String path = srcPath.substring(0,srcPath.lastIndexOf("/"));
				code=path.substring(path.lastIndexOf("/")+1);
				FileUtils.deleteQuietly(new File(downPath));
			}else{
				if(StringUtils.isEmpty(code)||StringUtils.isEmpty(version.getName())){
					status.setCode(RespStatusEnum.PROJECT_CODE_NAME_EMPTY_ERROR.getCode());
					status.setMsg(RespStatusEnum.PROJECT_CODE_NAME_EMPTY_ERROR.getMsg());
					return status;
				}
				Pattern pattern = Pattern.compile("^[0-9a-zA-Z_-]+$");
				Matcher matcher = pattern.matcher(code);
				if(!matcher.find()){
					status.setCode(RespStatusEnum.PROJECT_CODE_FORMAT_ERROR.getCode());
					status.setMsg(RespStatusEnum.PROJECT_CODE_FORMAT_ERROR.getMsg());
					return status;
				}
				downUrl+=code+"."+fileType;
				downPath+=code+"."+fileType;
			}
			uploadPath+=code+"/"+fileName;
			//上传下载文件
			File downFile = new File(downPath);
			if(!downFile.getParentFile().exists()){
				downFile.getParentFile().mkdirs();
			}
			FileUtils.copyFile(new File(tempPath),downFile);
			//上传版本分析文件
			File vcFile = new File(uploadPath);
			if(!vcFile.getParentFile().exists()){
				vcFile.getParentFile().mkdirs();
			}
			FileUtils.copyFile(new File(tempPath),vcFile);
			if(vd==null){
				//添加版本
				version.setPack(tempPack);
				version.setUrl(downUrl);
				version.setType(fileType);
				versionService.addVersion(version);
				version.setVersionId(version.getId());
			}else{
				version.setVersionId(vd.getId());
			}
			//添加版本详细
			version.setPath(uploadPath);
			version.setVersion(tempVcode);
			version.setAuthor(new BaseDto().getVersion());
			versionService.addVersionDetail(version);
		} catch (Exception e) {
			e.printStackTrace();
			//e.printStackTrace();
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(e.getMessage());
			return status;
		}finally{
			//清除临时文件
			FileUtils.deleteQuietly(new File(tempPath));
		}
		status.setData(downUrl);
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
	/**
	 * 查询版本信息
	 * @Title: version 
	 * @return
	 * String
	 */
	@RequestMapping(value = "/checkVersion")
	public @ResponseBody 
	RespDataObject<VersionDto> checkVersion(@RequestBody Map<String, String> param){
		RespDataObject<VersionDto> status = new RespDataObject<VersionDto>();
		String pack = param.get("pack");
		String type = param.get("type");
		String vcode = param.get("vcode");
		String channel = param.get("channel");
		if(StringUtils.isEmpty(pack)||StringUtils.isEmpty(type)||StringUtils.isEmpty(vcode)){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		return checkVersion(pack, type, vcode, channel);
	}
	
	/**
	 * 查询版本信息
	 * 其他项目检测版本使用
	 * @Title: version 
	 * @return
	 * String
	 */
	@RequestMapping(value = "/checkVersionForOther")
	public @ResponseBody 
	RespDataObject<VersionDto> checkVersionForOther(String pack,String type,String vcode){
		RespDataObject<VersionDto> status = new RespDataObject<VersionDto>();
		if(StringUtils.isEmpty(pack)||StringUtils.isEmpty(type)||StringUtils.isEmpty(vcode)){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		return checkVersion(pack, type, vcode, "");
	}
	
	private RespDataObject<VersionDto> checkVersion(String pack,String type,String vcode, String channel){
		RespDataObject<VersionDto> status = new RespDataObject<VersionDto>();
		try {
			 log.info("===>pack:"+pack+",type:"+type+",vcode:"+vcode+",channel:"+channel);
			 VersionDto version = versionService.selectVersion(pack, type);
			 log.info("version.id:"+version.getId());
			 VersionDto versionDetail = versionService.selectVersionDetail(version.getId());
			 log.info("versionDetail:"+versionDetail.getPath());
			 ApkUtil apkUtil = new ApkUtil();
			 apkUtil.setmAaptPath(aaptPath);
			 ApkInfo apkInfo = apkUtil.getApkInfo(versionDetail.getPath());
			 String pk = apkInfo.getPackageName();
			 int vc = Integer.parseInt(apkInfo.getVersionCode());
			 //根据渠道查询审核状态，默认为2审核通过
			 if (StringUtils.isNotEmpty(channel)) {
				 int audit = versionService.selectAudit(pack, channel, Integer.parseInt(vcode));
				 versionDetail.setAudit(audit);
			 }
			 if(pack.equals(pk)){
				if(Integer.parseInt(vcode)<vc){
					 versionDetail.setPath(null);
					 versionDetail.setCode(version.getCode());
					 versionDetail.setName(version.getName());
					 versionDetail.setPack(version.getPack());
					 versionDetail.setUrl(version.getUrl()+"?"+Math.random()+".apk");
					 versionDetail.setVersionName(apkInfo.getVersionName());
					 versionDetail.setIsNeedUpdate(1);//不是最新版本，需要更新
					 status.setCode(RespStatusEnum.SUCCESS.getCode());
					 status.setMsg(RespStatusEnum.SUCCESS.getMsg());
					 status.setData(versionDetail);
					 return status;
				}
			}
			 
			versionDetail.setIsNeedUpdate(0);//已是最新版本，不需要更新
			status.setData(versionDetail);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.VERSION_HAD_NEW.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(RespStatusEnum.FAIL.getMsg());
			return status;
		}
		return status;
	}
	
	/**
	 * 查询版本信息列表
	 * @Title: versionList 
	 * @return
	 * String
	 */
	@RequestMapping(value = "/versionList")
	public @ResponseBody 
	RespData<VersionDto> versionList(){
		RespData<VersionDto> status = new RespData<VersionDto>();
		 try {
			status.setData(versionService.selectVersionList());
			status.setCode(RespStatusEnum.VERSION_HAD_NEW.getCode());
			status.setMsg(RespStatusEnum.VERSION_HAD_NEW.getMsg());
		} catch (Exception e) {
			//e.printStackTrace();
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(RespStatusEnum.FAIL.getMsg());
			return status;
		}
		return status;
	}
	/**
	 * IOS更新版本提醒
	 * @Title: iOSReminder 
	 * @param pack
	 * @param type
	 * @param vcode
	 * @return
	 * RespDataObject<VersionDto>
	 * @throws
	 */
	@RequestMapping(value = "/iOSReminder")
	public @ResponseBody
	RespDataObject<Map<String,Object>> iOSReminder(@RequestBody Map<String,Object> param){
		String sid = MapUtils.getString(param, "sid");
		String deviceId = MapUtils.getString(param, "deviceId");
		int vcode = MapUtils.getIntValue(param, "vcode");
		String pack = MapUtils.getString(param, "pack");
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());

		if(StringUtils.isEmpty(pack)){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("version",0);
		map.put("iOSReminder",0);
		map.put("iOSReminderTitle","");
		map.put("iOSReminderForce", 0);
		map.put("iOSAudit", 0);
		try {
			VersionDto version = versionService.selectVersion(pack, "ipa");
			if(version!=null){
				VersionDto versionDetail = versionService.selectVersionDetail(version.getId());
				if(versionDetail!=null){
					int dbVersion = versionDetail.getVersion();
					map.put("version",dbVersion);
					if(dbVersion>=vcode){//只有线上或新版审核通过才切换正式功能
						map.put("iOSAudit", 1);
					}
					if(dbVersion>vcode){//未更新版本情况下才提示
						if(versionDetail.getiOSReminderForce()==0&&
								versionDetail.getiOSReminder()==1){//需要提示IOS
							String a = RedisOperator.getStringFromMap(RedisKey.PREFIX.MORTGAGE_IOSREMINDER, sid+deviceId+vcode);
							if(!"1".equals(a)){
								map.put("iOSReminder",1);
								RedisOperator.putStringToMap(RedisKey.PREFIX.MORTGAGE_IOSREMINDER, sid+deviceId+vcode, "1");
							}
						}
						map.put("iOSReminderTitle",versionDetail.getiOSReminderTitle());
						map.put("iOSReminderForce", versionDetail.getiOSReminderForce());
					}
				}
			}
		} catch (Exception e) {
			log.info("iOSReminder error:"+e.getMessage());
			e.printStackTrace();
		}
		status.setData(map);
		return status;
	}
	
	/**
	 * 审核操作
	 * @Title: version 
	 * @return
	 * String
	 */
	@RequestMapping(value = "/auditHandle")
	public @ResponseBody 
	RespDataObject<Map<String, Object>> auditHandle(@RequestBody Map<String, Object> param){
		RespDataObject<Map<String, Object>> status = new RespDataObject<Map<String, Object>>();
		String key = MapUtils.getString(param, "key");
		if(!"sel".equals(key) && !"del".equals(key) && !"upd".equals(key) && !"add".equals(key)){
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return status;
		}
		
		Map<String, Object> map = versionService.auditHandle(param);
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		status.setData(map);
		return status;
	}
}