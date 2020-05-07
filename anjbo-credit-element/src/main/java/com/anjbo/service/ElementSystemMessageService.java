package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.element.AuditBaseDto;
import com.anjbo.bean.element.AuditFlowDto;
import com.anjbo.bean.element.ElementSystemMessageDto;
import com.anjbo.bean.element.vo.SystemMsgVO;
import com.anjbo.bean.user.UserDto;

 /**
  * 系统消息表 [Service接口类]
  * @ClassName: ElementSystemMessageService
  * @Description: 系统消息表业务服务
  * @author 
  * @date 2017-12-21 15:01:44
  * @version V3.0
 */
public interface ElementSystemMessageService{
	
	/**
	 * 
	 * @Title: addToAuditMsg 
	 * @Description: 添加一条待审核消息 
	 * @param @param map    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	void addToAuditMsg(AuditFlowDto dto, UserDto nextAuditor);
	/**
	 * 
	 * @Title: addAuditResultMsg 
	 * @Description: 审批结果添加消息 
	 * @param @param baseDto
	 * @param @param pass   是否通过
	 * @return void    返回类型 
	 * @throws
	 */
	void addAuditResultMsg(AuditBaseDto baseDto, boolean pass);
	/**
	 * 
	 * @Title: selectUnreadCount 
	 * @Description: 查询用户未读系统消息总数
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectUnreadCount(String uid);
	/**
	 * 
	 * @Title: selectUnreadCopyAuditCount 
	 * @Description: 查询用户未读的审批抄送消息数
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectUnreadCopyAuditCount(String uid);
	/**
	 * 
	 * @Title: updateAuditMsg 
	 * @Description: 更新待我审批的消息状态
	 * @param @param pass 审批是否同意 
	 * @param @param auditBase 审批对象 
	 * @return void    返回类型 
	 * @throws
	 */
	void updateAuditMsgState(boolean pass, AuditBaseDto auditBase, UserDto auditor);
	/**
	 * 
	 * @Title: updateCopyMsgState 
	 * @Description: 更新抄送消息状态为已读
	 * @param @param id
	 * @param @param uid    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	void updateCopyMsgState(Integer id, String uid);
	/**
	 * 
	 * @Title: getById 
	 * @Description: 根据id获取对象
	 * @param @param id
	 * @param @return    设定文件 
	 * @return ElementSystemMessageDto    返回类型 
	 * @throws
	 */
	ElementSystemMessageDto getById(Integer id);
	/**
	 * 
	 * @Title: listByUid 
	 * @Description: 根据uid查询消息列表 
	 * @param @param uid,start,pageSize
	 * @param @return    设定文件 
	 * @return List<SystemMsgVO>    返回类型 
	 * @throws
	 */
	List<SystemMsgVO> listByUid(Map<String,Object> params);
	/**
	 * 
	 * @Title: addMsg 
	 * @Description: 添加一条系统消息
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	void addMsg(ElementSystemMessageDto msgDto);
	/**
	 * 
	 * @Title: updateElementSystemMessage 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param msgdto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	void updateElementSystemMessage(ElementSystemMessageDto msgdto);
	/**
	 * 
	 * @Title: unreadApplayCount 
	 * @Description: 我发起审批结果的未读消息数
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return Object    返回类型 
	 * @throws
	 */
	Integer unreadApplayCount(String uid);
	/**
	 * 
	 * @Title: updateMessageHasRead 
	 * @Description: 标记消息已读
	 * @param @param msgid    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	void updateMessageHasRead(Integer msgid);
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