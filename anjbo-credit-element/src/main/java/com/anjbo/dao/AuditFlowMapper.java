package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.element.AuditFlowDto;
 
 /**
  * 审批流水表 [Dao接口类]
  * @ClassName: ElementAuditFlowMapper
  * @Description: 
  * @date 2017-12-20 18:04:58
  * @version V3.0
 */
public interface AuditFlowMapper{
	/**
	 * 获取审批流水表列表
	 * @param elementAuditFlowDto
	 * @return list
	 */
	List<AuditFlowDto> selectElementAuditFlowList(AuditFlowDto elementAuditFlowDto);

	/**
	 * 获取审批流水表列表总数
	 * @param elementAuditFlowDto
	 * @return int
	 */
	int selectElementAuditFlowCount(AuditFlowDto elementAuditFlowDto);
	
	/**
	 * @description:添加 审批流水表
	 * @param AuditFlowDto
	 * @return int
	 */
	int addElementAuditFlow(AuditFlowDto elementAuditFlowDto);
	
	/**
	 * @description:修改 审批流水表
	 * @param AuditFlowDto
	 * @return int
	 */
	int updateElementAuditFlow(AuditFlowDto elementAuditFlowDto);
	/**
	 * @description:根据ID删除 审批流水表
	 * @param int
	 * @return Integer
	 */
	int deleteElementAuditFlowById(int id);	
	
	/**
	 * 删除审批流水表
	 * @param dbId
	 * @return
	 */
	int deleteByDbId(int dbId);
	
	/**
	 * @description:根据ID查询 审批流水表
	 * @param int
	 * @return AuditFlowDto
	 */
	AuditFlowDto selectElementAuditFlowById(int id);
	/**
	 * 
	 * @Title: selectFlow 
	 * @Description: 根据查询审批流水对象 
	 * @param @param params
	 * @param @return    设定文件 
	 * @return AuditFlowDto    返回类型 
	 * @throws
	 */
	AuditFlowDto selectFlowInauditByUidAndAuditId(Map<String, Object> params);
	/**
	 * 
	 * @Title: selectAuditUidByAuditIdAndLevel 
	 * @Description: 根据审批层级和审批id查询 审批人uid 
	 * @param @param auditId
	 * @param @param level
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	String selectAuditUidByAuditIdAndLevel(@Param("auditId")int auditId, @Param("level")int level);
	/**
	 * 
	 * @Title: selectToAuditCount 
	 * @Description: 根据用户查询带我审批记录数量
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectToAuditCount(String uid);
	/**
	 * 
	 * @Title: selectAuditHistoryById 
	 * @Description: 根据id查询一个审批申请的审批人记录
	 * @param @param id
	 * @param @return    设定文件 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws
	 */
	List<Map<String, Object>> selectAuditHistoryById(Map<String,Object> map);
	/**
	 * 
	 * @Title: selectBorrowedOverTimeReceiverUid 
	 * @Description: 查询超时未还的消息接收人(所有审批人)uid
	 * @param @param auditId
	 * @param @return    设定文件 
	 * @return List<String>    返回类型 
	 * @throws
	 */
	List<String> selectBorrowedOverTimeReceiverUid(Integer auditId);
	
	/**
	 * 根据审批id查询是否有审批通过的
	 * @param auditFlowDto
	 * @return
	 */
	List<AuditFlowDto> selectAuditFlow(AuditFlowDto auditFlowDto);

}