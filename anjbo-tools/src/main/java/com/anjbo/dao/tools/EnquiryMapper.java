package com.anjbo.dao.tools;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.tools.EnquiryDto;
import com.anjbo.bean.tools.EnquiryReportDto;

/**
 * 询价
 * @author lic
 *
 * @date 2016-6-6 下午02:19:54
 */
public interface EnquiryMapper {
	
	/**
	 * 新增询价
	 * @user lic
	 * @date 2016-6-6 下午02:28:47 
	 * @param enquiryDto
	 */
	public void insertEnquiry(EnquiryDto enquiryDto);
	
	/**
	 * 查询相同物业，相同楼栋，相同房号同一天查询次数
	 * @user lic
	 * @date 2016-6-3 下午04:15:11 
	 * @param param
	 * @return
	 */
	public int selectCountByCondition(EnquiryDto enquiryDto);
	
	/**
	 * 查询询相同物业，相同楼栋，相同房号的物业信息
	 * @user lic
	 * @date 2016-6-3 下午04:21:39 
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectEnquiryByCondition(Map<String, Object> params);
	
	/**
	 * 修改世联评估总价
	 * @user lic
	 * @date 2016-6-6 下午02:57:33 
	 * @param params
	 */
	public void updateSlTotalPrice(Map<String, Object> params);
	
	/**
	 * 修改询价状态
	 * @user lic
	 * @date 2016-6-6 下午03:27:10 
	 * @param params
	 */
	public void updateEnquiryStatus(Map<String, Object> params);
	
	/**
	 * 查询评估记录Id
	 * @user lic
	 * @date 2016-6-6 下午03:30:30 
	 * @param enquiryReportDto
	 * @return
	 */
	public Integer reportExit(EnquiryReportDto enquiryReportDto);
	
	/**
	 * 新增评估记录
	 * @user lic
	 * @date 2016-6-6 下午03:31:50 
	 * @param enquiryReportDto
	 */
	public void insertReport(EnquiryReportDto enquiryReportDto);
	
	/**
	 * 查询税费记录Id
	 * @user lic
	 * @date 2016-6-6 下午03:32:55 
	 * @param params
	 * @return
	 */
	public Integer taxExit(Map<String, Object> params);
	
	/**
	 * 新增税费记录
	 * @user lic
	 * @date 2016-6-6 下午03:34:06 
	 * @param params
	 * @return
	 */
	public Integer insertTax(Map<String, Object> params);

	/**
	 * 根据内部流水查询询价Id
	 * @param serialid 
	 * @user lic
	 * @date 2016-11-14 下午04:32:55 
	 * @return
	 */
	public Integer selectEnquiryBySerialid(String serialid);
	
	/**
	 * 修改询价结果
	 * @user lic
	 * @date 2016-11-14 下午04:50:32 
	 * @param enquiryReportDto
	 */
	public void updateEnquiryReport(EnquiryReportDto enquiryReportDto);
	
	/**
	 * 查询询价列表
	 * @user lic
	 * @date 2016-11-14 下午06:28:56 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getList(Map<String, Object> param);
	
	/**
	 * 查询询价结果记录
	 * @user liuw
	 * @date 2016-9-23 上午11:21:12 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> selectEnquiryReportList(int id);

	/**
	 * 查询询价详情
	 * @user Object
	 * @date 2016-11-18 下午04:46:05 
	 * @param id
	 * @return
	 */
	public EnquiryDto selectEnquiry(Integer id);

	/**
	 * 根据id删除询价记录
	 * 
	 * @user jiangyq
	 * @date 2017年10月30日 上午9:18:46 
	 * @param id
	 * @return
	 */
	public int deleteEnquiryById(Integer id);
	
	/**
	 * 根据询价id删除询价结果
	 * 
	 * @user jiangyq
	 * @date 2017年10月30日 上午9:18:46 
	 * @param enquiryId
	 * @return
	 */
	public int deleteToolsReportByEnquiryId(int enquiryId);
	/**
	 * 
	 * 根据物业名称查询enqueryReport记录数
	 * @user likf
	 * @date 2018年3月23日 上午11:48:37 
	 * @param params
	 * @return
	 */
	public int enqueryReportCountByProperty(Map<String, Object> params);
	/**
	 * 
	 * 根据物业名称查询enqueryReport记录
	 * @user likf
	 * @date 2018年3月23日 上午11:51:13 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> enqueryReportListByProperty(Map<String, Object> params);
	
}
