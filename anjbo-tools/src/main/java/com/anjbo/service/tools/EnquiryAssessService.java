package com.anjbo.service.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anjbo.bean.tools.EnquiryAssessDto;
import com.anjbo.bean.tools.OrderDto;
import com.anjbo.bean.tools.OrderResultDto;
import com.anjbo.common.MortgageException;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

public interface EnquiryAssessService {
	public RespStatus tzcEnquiryAccess(EnquiryAssessDto assess) throws MortgageException;
	
	public RespStatus tzcWebReportApply(Map<String, String> params) throws MortgageException;

    public int addEnquiryAccess(EnquiryAssessDto assessDto) throws MortgageException;
    
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
	public List<EnquiryAssessDto> selEnquiryAssessAll(String uid,int pagesize,int pageindex);
	
	/***
	 * 查询评估详情
	 * @param id
	 * @return
	 * @throws MortgageException 
	 */
	public EnquiryAssessDto selEnquirAssessDetail(String id) throws MortgageException;
	
	/**
	 * 根据id查询评估记录
	 * @param id
	 * @return
	 */
	public EnquiryAssessDto findById(String id);
	
	public int updateStatus(String id,int status,String content);
	
	/**
	 * 根据询价Serialid查对应的id
	 * @param enquiryId
	 * @return
	 */
	public int findEnquiryIdBySerialid(String serialid);
	
	/**
	 * 根据询价id查询评估申请信息
	 * @param id
	 * @return
	 */
	public String findIdByEnquiryId(int enquiryId);
	
	public int updateApplyTime(String id);
	public int updateApplyReportTime(String id);
	
	public int updateAssessIsRead(String id ,int isRead);
	
	public int selectAssessIsRead(String uid);
	
	public EnquiryAssessDto findAssessByEnquiryId(int enquiryId);
	
	public int detailAssess(int enquiryId);
	EnquiryAssessDto findBySerialid(String serialid);
	
	Integer selectProgressIdByOrderNo(String orderNo);

	/**
	 * 	渠道经理提交修改信息
	 * @Title: updateEnquiryAccess 
	 * @Description: TODO
	 * @param @param assess
	 * @param @return    
	 * @return int 
	 * @throws
	 */
	public int updateEnquiryAccess(EnquiryAssessDto assess);

	/**
	 * 根据区域查询记录
	 * @Title: selEnquiryAssessByDistrict 
	 * @Description: TODO
	 * @param @param uid
	 * @param @param pagesize
	 * @param @param pageindex
	 * @param @return    
	 * @return List<EnquiryAssessDto> 
	 * @throws
	 */
	public List<EnquiryAssessDto> selEnquiryAssessByDistrict(String uid,
			String [] district, int pagesize, int pageindex);
	
	/**
	 * 根据ID删除评估记录
	 * @Title: delAssessById 
	 * @Description: TODO
	 * @param @param id
	 * @param @return    
	 * @return int 
	 * @throws
	 */
	public int delAssessById(String id);
	
	/**
	 * 关闭或启用评估记录
	 * 
	 * @user jiangyq
	 * @date 2017年11月6日 上午10:40:26 
	 * @param id
	 * @param isClose
	 * @return
	 */
	public int updateIsClose(String id, int isClose);
	
	/**
	 * 提交申请修改参数以及状态
	 * 
	 * @user Jiangyq
	 * @date 2017年11月7日 下午1:52:30 
	 * @param id
	 * @param status
	 * @param content
	 * @param dealAmount
	 * @param loanAmount
	 * @return
	 */
	public int updateAssessByLimitApply(String id, int status, String content, double dealAmount, double loanAmount);
	
}

	
