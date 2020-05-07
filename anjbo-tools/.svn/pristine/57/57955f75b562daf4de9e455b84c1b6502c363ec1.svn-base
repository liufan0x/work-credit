package com.anjbo.dao.tools;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.tools.EnquiryAssessDto;


public interface EnquiryAssessMapper {
	
	public int addEnquiryAccess(EnquiryAssessDto assessDto);
	
	 /**
     * 查询正在评估或者已经评估的信息
     * @param assess
     * @return
     */
    public List<EnquiryAssessDto> findAlreadyassess(EnquiryAssessDto assess);
    /**
     * 查询是否提交评估
     * @param assessDto
     * @return
     */
    public EnquiryAssessDto selEnquiryAssess(EnquiryAssessDto assessDto);
	
	public int updateAccessByApply(EnquiryAssessDto assess);
	
	/**
	 * 查询评估记录
	 * @param uid
	 * @return
	 */
	public List<EnquiryAssessDto> selEnquiryAssessAll(Map<String, Object> params);
	
	/***
	 * 查询评估详情
	 * @param id
	 * @return
	 */
	public EnquiryAssessDto selEnquirAssessDetail(String id);
	
	public EnquiryAssessDto findById(String id);
	
	public int updateStatus(Map<String,Object> map);
	
	public int findEnquiryIdBySerialid(String serialid);
	
	public String findIdByEnquiryId(int enquiryId);
	
	public int updateApplyTime(Map<String,Object> map);
	public int updateApplyReportTime(Map<String,Object> map);
	
	public int updateAssessIsRead(Map<String,Object> map);
	
	public int selectAssessIsRead(String uid);
	
	public EnquiryAssessDto findAssessByEnquiryId(int enquiryId);
	
	public int detailAssess(int enquiryId);
	EnquiryAssessDto findBySerialid(String serialid);

	public Integer selectProgressIdByOrderNo(String orderNo);
	
	public int updateEnquiryAccess(EnquiryAssessDto assess);

	public List<EnquiryAssessDto> selEnquiryAssessByDistrict(
			Map<String, Object> map);
	
	/** 根据id删除评估记录 */
	public int delAssessById(String id);

	/**
	 * 删除或启用评估记录
	 * 
	 * @user jiangyq
	 * @date 2017年11月6日 上午10:41:58 
	 * @param map
	 * @return
	 */
	public int updateIsClose(Map<String,Object> map);
	
	/**
	 * 提交申请修改参数以及状态
	 * 
	 * @user Jiangyq
	 * @date 2017年11月7日 下午1:49:54 
	 * @param map
	 * @return
	 */
	public int updateAssessByLimitApply(Map<String,Object> map);
}
