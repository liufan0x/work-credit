package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.element.ElementSystemMessageDto;
import com.anjbo.bean.element.vo.SystemMsgVO;
 
 /**
  * 系统消息表 [Dao接口类]
  * @ClassName: ElementSystemMessageMapper
  * @Description: 
  * @date 2017-12-20 18:04:57
  * @version V3.0
 */
public interface ElementSystemMessageMapper{
	
	/**
	 * @description:添加 系统消息表
	 * @param ElementSystemMessageDto
	 * @return int
	 */
	int addElementSystemMessage(ElementSystemMessageDto talElementSystemMessageDto);
	
	/**
	 * @description:修改 系统消息表
	 * @param ElementSystemMessageDto
	 * @return int
	 */
	int updateElementSystemMessage(ElementSystemMessageDto talElementSystemMessageDto);
	/**
	 * @description:根据ID删除 系统消息表
	 * @param int
	 * @return Integer
	 */
	int deleteElementSystemMessageById(int id);	
	
	/**
	 * 删除审批消息
	 * @param dbId
	 * @return
	 */
	int deleteByDbId(int dbId);
	
	/**
	 * @description:根据ID查询 系统消息表
	 * @param int
	 * @return ElementSystemMessageDto
	 */
	ElementSystemMessageDto selectElementSystemMessageById(int id);
	/**
	 * 
	 * @Title: selectUnreadCount 
	 * @Description: 查询用户未读的消息总数
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectUnreadCount(String uid);
	/**
	 * 
	 * @Title: selectUnreadCopyAuditCount 
	 * @Description: 根据用户查询未读的审批抄送消息数
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectUnreadCopyAuditCount(String uid);
	/**
	 * 
	 * @Title: updateAuditMsgState 
	 * @Description: 更新待我审批的审批消息状态
	 * @param @param state  当前审批人审批状态
	 * @param @param id  审批id
	 * @param @param uid  审批人uid
	 * @return void    返回类型 
	 * @throws
	 */
	void updateAuditMsgState(Map<String,Object> params);
	/**
	 * 
	 * @Title: updateCopyMsgState 
	 * @Description: 更新抄送审批消息为已读
	 * @param @param params    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	void updateCopyMsgState(Map<String, Object> params);
	/**
	 * 
	 * @Title: listByUid 
	 * @Description: 根据uid查询消息列表
	 * @param @param params
	 * @param @return    设定文件 
	 * @return List<SystemMsgVO>    返回类型 
	 * @throws
	 */
	List<SystemMsgVO> listByUid(Map<String, Object> params);
	/**
	 * 
	 * @Title: unreadApplayCount 
	 * @Description: 我发起的审批结果未读消息数
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer unreadApplayCount(String uid);
	
	
	void updateMessageHasRead(Map<String, Object> params);
	/**
	 * 
	 * @Title: selectTitle 
	 * @Description: 查询消息标题 
	 * @param @param params
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	String selectTitle(Map<String, Object> params); 

}