package com.anjbo.dao.tools; 

import java.util.List;
import java.util.Map;

import com.anjbo.bean.tools.VersionDto;

/**
 * 版本信息
 * @ClassName: VersionMapper 
 * @author limh limh@zxsf360.com
 * @date 2015-2-12 下午03:13:15
 */
public interface VersionMapper {
	/**
	 * 获取版本
	 * @Title: selectVersion 
	 * @param pack 包名 唯一标示
	 * @param type 类型 apk,ipa...
	 * @return
	 * Map<String,Object>
	 */
	VersionDto selectVersion(String pack,String type);
	/**
	 * 获取版本详细
	 * @Title: selectVersionDetail 
	 * @param versionId 版本id
	 * @return
	 * Map<String,Object>
	 */
	VersionDto selectVersionDetail(int versionId);
	/**
	 * 新增版本
	 * @Title: addVersion 
	 * @param map
	 * @return
	 * int
	 */
	int addVersion(VersionDto version);
	/**
	 * 新增版本详细
	 * @Title: addVersionDetail 
	 * @param map
	 * @return
	 * int
	 */
	int addVersionDetail(VersionDto version);
	/**
	 * 所有版本列表
	 * @Title: selectVersionList 
	 * @return
	 * List<VersionDto>
	 */
	List<VersionDto> selectVersionList();
	
	/**
	 * 查询审核状态
	 * @user Administrator
	 * @date 2018年9月27日 上午10:21:58 
	 * @param pack 包名
	 * @param code 渠道code
	 * @param version 版本号
	 * @return
	 */
	Integer selectAudit(String pack, String code, int version);
	
	List<Map<String, Object>> selectAuditList(Map<String, Object> map);
	
	int addAuditDetail(Map<String, Object> map);
	
	int updateAudit(Map<String, Object> map);
	
	int deleteAudit(Map<String, Object> map);
}
  