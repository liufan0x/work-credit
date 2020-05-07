package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.element.AuditBaseDto;
import com.anjbo.bean.element.AuditFlowDto;
import com.anjbo.bean.element.vo.AuditInfoVo;
import com.anjbo.bean.element.vo.ElementOrderVo;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.AnjboException;

/**
  * 审批信息表 [Service接口类]
  * @ClassName: ElementAuditBaseService
  * @Description: 审批信息表业务服务
  * @author 
  * @date 2017-12-20 18:04:58
  * @version V3.0
 */
public interface AuditBaseService{
	/**
	 * @throws AnjboException 
	 * 
	 * @Title: addAudit 
	 * @Description: 新增一条审批申请 
	 * @param @param auditDto
	 * @return void    返回类型 
	 * @throws
	 */
	void addAudit(AuditBaseDto auditDto, List<String> auditorUidList,int dbId) throws AnjboException;
	
	/**
	 * 延长借用审批
	 * @param auditDto
	 * @param auditorUidList
	 * @throws AnjboException
	 */
	void extendAudit(AuditBaseDto auditDto, List<String> auditorUidList) throws AnjboException;
	/**
	 * 撤销审批申请
	 * @param params
	 */
	int cancelBorrowAudit(Map<String,Object> params);
	
	/**
	 * 修改审批申请
	 * @param params
	 * @return
	 */
	int editBorrowAudit(AuditBaseDto auditDto, List<String> auditorUidList) throws AnjboException;
	/**
	 * 
	 * @Title: selectFlowByUidAndAuditId 
	 * @Description: 根据用户uid和审批id查询审批流水对象 
	 * @param @param params
	 * @param @return    设定文件 
	 * @return AuditFlowDto    返回类型 
	 * @throws
	 */
	AuditFlowDto selectFlowInauditByUidAndAuditId(Map<String, Object> params);
	/**
	 * 
	 * @Title: audit 
	 * @Description: 审批操作 
	 * @param @param flowDto    设定文件 
	 * @param @param next    下一级审批人,为空时表示审批结束 
	 * @param @param allUserDtoMap    所有用户uid与用户对象的映射
	 * @return void    返回类型 
	 * @throws
	 */
	void audit(AuditFlowDto flowDto, AuditBaseDto baseDto, UserDto next);
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
	String selectAuditUidByAuditIdAndLevel(int auditId, int level);
	/**
	 * 
	 * @Title: selectCandidates 
	 * @Description: 根据审批层级和审批类型查询候选人
	 * @param @param params
	 * @param @return    设定文件 
	 * @return Map<String,Object>     返回类型 
	 * @throws
	 */
	Map<String,Object>  selectCandidates(Map<String, Object> params);
	/**
	 * 
	 * @Title: selectApplyList 
	 * @Description: 根据uid查询申请的审批列表 
	 * @param params(uid,start,pageSize)
	 * @param @return    设定文件 
	 * @return List<AuditBaseDto>    返回类型 
	 * @throws
	 */
	List<AuditInfoVo> selectApplyList(Map<String, Object> params);
	/**
	 * 
	 * @Title: selectToAuditList 
	 * @Description: 根据uid查询待我审批列表 
	 * @param params(uid,start,pageSize)
	 * @param @return    设定文件 
	 * @return List<AuditBaseDto>    返回类型 
	 * @throws
	 */
	List<AuditInfoVo> selectToAuditList(Map<String, Object> params);
	/**
	 * 
	 * @Title: selectAuditedList 
	 * @Description: 根据uid查询我已审批列表
	 * @param params(uid,start,pageSize)
	 * @param @return    设定文件 
	 * @return List<AuditBaseDto>    返回类型 
	 * @throws
	 */
	List<AuditInfoVo> selectAuditedList(Map<String, Object> params);
	/**
	 * 
	 * @Title: selectCopyList 
	 * @Description: 根据uid查询抄送给我列表
	 * @param params(uid,start,pageSize)
	 * @param @return    设定文件 
	 * @return List<AuditBaseDto>    返回类型 
	 * @throws
	 */
	List<AuditInfoVo> selectCopyList(Map<String, Object> params);
	/**
	 * 
	 * @Title: buildElementsDescrib 
	 * @Description: 根据id集合拼接描述字符串
	 * @param @param idList
	 * @param @param type 要件类型(1回款要件2风控要件3公章)
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	String buildElementsDescrib(List<Integer> idList, int type);
	/**
	 * 
	 * @Title: selectToAuditCount 
	 * @Description: 根据用户查询待我审批数量 
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectToAuditCount(String uid);
	/**
	 * 
	 * @Title: selectCopyAuditCount 
	 * @Description: 根据用户查询抄送给我未读记录数
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectUnreadCopyAuditCount(String uid);
	
	/**
	 * 
	 * @Title: selectLastApplayByUid 
	 * @Description: 根据uid查询最后一次审批申请
	 * @param @param uid
	 * @param @return    设定文件 
	 * @return AuditBaseDto    返回类型 
	 * @throws
	 */
	AuditBaseDto selectLastApplayByUid(String uid);
	
	/**
	 * 
	 * @Title: selectAuditBaseDtoById 
	 * @Description: 根据id查询审批信息对象
	 * @param @param id
	 * @param @return    设定文件 
	 * @return AuditBaseDto    返回类型 
	 * @throws
	 */
	AuditBaseDto selectAuditBaseDtoById(Integer id);
	/**
	 * 
	 * @Title: selectAuditHistoryById 
	 * @Description: 根据id查询一个审批申请的审批人记录
	 * @param @param id
	 * @param @return    设定文件 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws
	 */
	List<Map<String, Object>> selectAuditHistoryById(Integer id,int isExtend);
	/**
	 * @throws Exception 
	 * 
	 * @Title: selectDetail 
	 * @Description: 查询审批详情
	 * @param @param 审批id
	 * @param @param msgid 消息id
	 * @param @return    设定文件 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	 */
	Map<String, Object> selectDetail(Integer id,Integer msgid, UserDto userDto) throws Exception;
	/**
	 * 
	 * @Title: selectBorrowRecord 
	 * @Description: 查询借用要件/借用公章记录
	 * @param @param params(uid,start,pageSize,type:type为1时查要件,其余为公章)
	 * @param @return    设定文件 
	 * @return List<AuditInfoVo>    返回类型 
	 * @throws
	 */
	List<AuditInfoVo> selectBorrowRecord(Map<String, Object> params);
	/**
	 * @throws Exception 
	 * @Title: getApplayPageInfo 
	 * @Description: 获取申请审批页面信息
	 * @param @param orderNo
	 * @param @param userDto
	 * @param @return    设定文件 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	 */
	Map<String, Object> getApplayPageInfo(String orderNo, UserDto userDto,int auditType,int id) throws Exception;
	/**
	 * 
	 * @Title: selectOrderList 
	 * @Description: 订单列表
	 * @param params
	 * @param user 当前用户
	 * @param 设定文件 
	 * @return List<ElementOrderVo>    返回类型 
	 * @throws
	 */
	List<ElementOrderVo> selectOrderList(Map<String,Object> params,UserDto user);
	/**
	 * 
	 * @Title: copyToList 
	 * @Description: 抄送人选择数据
	 * @param @return    设定文件 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	 */
	Map<String, Object> copyToList(List<UserDto> allUserList);
	/**
	 * 
	 * @Title: selectBorrowedOverTimeAudits 
	 * @Description: 查询存在超时未还的审批id集合
	 * @param type 类型 : 1 查询超时未添加系统消息和标记订单未还的记录 2查询未发送短通知给借用者的超时记录3查询未发送短信通知给审批人和要件管理员的超时记录  
	 * @return List<AuditBaseDto>    返回类型 
	 * @throws
	 */
	List<AuditBaseDto> selectBorrowedOverTimeAudits(int type);
	/**
	 * 
	 * @Title: selectBorrowedOverTimeReceiverUid 
	 * @Description: 查询超时未还消息接收人(所有审批人)uid
	 * @param @param id 审批id
	 * @param @return    设定文件 
	 * @return List<String>    返回类型 
	 * @throws
	 */
	List<String> selectBorrowedOverTimeReceiverUid(Integer id);
	/**
	 * 
	 * @Title: selectBorrowedOverTimeEle 
	 * @Description: 查询超时未还的物品id
	 * @param @param id 审批id或操作id
	 * @param @param type 类型 1 :审批借用超时未还 2:开箱超时未还
	 * @param @return    设定文件 
	 * @return List<Integer>    返回类型 
	 * @throws
	 */
	List<Integer> selectBorrowedOverTimeEle(Integer id,int type);
	/**
	 * 
	 * @Title: selectBorrowOperatorUid 
	 * @Description: 根据审批id查询借用操作uid
	 * @param @param id 审批id(type==1)或操作id
	 * @param @param type 类型 1 审批借用的 2 开箱操作
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	String selectBorrowOperatorUid(Integer id,int type);
	/**
	 * 
	 * @Title: updateBorrowedOverTimeMsgState 
	 * @Description: 标记审批抄送未还消息已发送
	 * @param id    审批id(type==1)或操作id
	 * @param type    类型 1 审批借用超时未还  2 开箱操作超时未还
	 * @param noticeState    短信发送状态(0未发送,1已发送给借用人,2已发送给借用人和审批人及要件管理员)
	 * @return void    返回类型 
	 * @throws
	 */
	void updateBorrowedOverTimeMsgState(Integer id,int type,int noticeState);
	/**
	 * @Title: updateSysmsgState 
	 * @Description: 标记    已更新订单超时未还按钮显示和添加系统消息
	 * @param id    审批id(type==1)或操作id
	 * @param type    类型 1 审批借用超时未还  2 开箱操作超时未还
	 * @return void    返回类型 
	 * @throws
	 */
	void updateSysmsgState(Integer id, int type);
	/**
	 * 
	 * @Title: getOrderInfo 
	 * @Description: 查询订单详情信息
	 * @param @param orderNo
	 * @param @return    设定文件 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	 */
	Map<String, Object> getOrderInfo(String orderNo);
	/**
	 * 
	 * @Title: selectAuditIdByOrderNo 
	 * @Description: 根据订单编号查出较早的审批id
	 * @param @param orderNo
	 * @param @param uid	审批人uid
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectAuditIdByOrderNo(Map<String,Object> map);
	/**
	 * 
	 * @Title: updateBtnState 
	 * @Description: 标记按钮的展示 
	 * @param @param type    1:取按钮;2:超时未还按钮
	 * @return void    返回类型 
	 * @throws
	 */
	void updateBtnOnState(int type,String orderNo);
	/**
	 * 
	 * @Title: selectBorrowedOverTimeOperation 
	 * @Description: 查询开箱超时未还操作
	 * @param type 类型 : 1 查询超时未添加系统消息和标记订单未还的记录 2查询为发送短信给开箱人的记录 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws
	 */
	List<Map<String, Object>> selectBorrowedOverTimeOperation(int type);
	/**
	 * 
	 * @Title: checkEleState 
	 * @Description: 校验审批的要件/公章是否还在箱子
	 * @param @param auditId 审批id
	 * @param @return    设定文件 
	 * @return boolean    true 要件/公章还在箱子里
	 * @throws
	 */
	int checkEleState(Integer auditId);
	/**
	 * @throws AnjboException 
	 * 
	 * @Title: deliverAudit 
	 * @Description: 转交审批
	 * @param @param uid 当前用户uid
	 * @param @param toUser 要转交的用户
	 * @param @param auditId  审批id 
	 * @return void    返回类型 
	 * @throws
	 */
	void deliverAudit(String uid, UserDto toUser, int auditId,String remark) throws AnjboException;
	/**
	 * 
	 * @Title: selectBoxcityByOrderNo 
	 * @Description: 根据订单编号查询要件柜城市名
	 * @param @param orderNo
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	String selectBoxcityByOrderNo(String orderNo);
	
	/**
	 * 查询一级审批是否通过
	 * @param dbId
	 * @return
	 */
	public Map<String,Object> selectAuditLevelFrist(int dbId);
	
}