package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.element.AuditBaseDto;
import com.anjbo.bean.element.vo.AuditInfoVo;
import com.anjbo.bean.element.vo.ElementOrderVo;
 
 /**
  * 审批信息表 [Dao接口类]
  * @ClassName: ElementAuditBaseMapper
  * @Description: 
  * @date 2017-12-20 18:04:58
  * @version V3.0
 */
public interface AuditBaseMapper{
	
	/**
	 * @description:添加 审批信息表
	 * @param AuditBaseDto
	 * @return int
	 */
	int addElementAuditBase(AuditBaseDto elementAuditBaseDto);
	
	/**
	 * @description:修改 审批信息表
	 * @param AuditBaseDto
	 * @return int
	 */
	int updateElementAuditBase(AuditBaseDto elementAuditBaseDto);
	/**
	 * @description:根据ID删除 审批信息表
	 * @param int
	 * @return Integer
	 */
	int deleteElementAuditBaseById(int id);	
	/**
	 * @description:根据订单编号更新订单的操作时间
	 * @param params
	 * @return Integer
	 */
	int updateTimeOfElementList(Map<String,Object> params);	
	
	/**
	 * @description:根据ID查询 审批信息表
	 * @param int
	 * @return AuditBaseDto
	 */
	AuditBaseDto selectElementAuditBaseById(int id);
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
	 * @Title: selectElementNames 
	 * @Description: 根据id集合查询要件或公章名称集合 
	 * @param @param idList
	 * @param @return    设定文件 
	 * @return List<String>    返回类型 
	 * @throws
	 */
	List<String> selectElementNames(@Param("idList")List<Integer> idList,@Param("type")int type);
	/**
	 * 
	 * @Title: selectInfoByOrderNo 
	 * @Description: 根据订单编号查询订单信息
	 * @param @param orderNo
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	Map<String,Object> selectInfoByOrderNo(String orderNo);
	/**
	 * 
	 * @Title: selectBoxInfoByBoxNo 
	 * @Description: 根据柜子编号查询柜子信息
	 * @param @param boxNo
	 * @param @return    设定文件 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	 */
	Map<String, Object> selectBoxInfoByBoxNo(String boxNo);
	/**
	 * 
	 * @Title: selectAuditConfigByCityAndType 
	 * @Description: 根据城市和类型查询审批设置
	 * @param @param city
	 * @param @param auditType
	 * @param @return    设定文件 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	 */
	Map<String, Object> selectAuditConfigByCityAndType(@Param("city")String city, @Param("type")int auditType);
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
	 * @Title: selectElementsById 
	 * @Description: 根据id集合查询要件/公章集合
	 * @param @param ids
	 * @param @return    设定文件 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws
	 */
	List<Map<String, Object>> selectElementsByIds(String[] ids);
	/**
	 * 
	 * @Title: selectBorrowRecord 
	 * @Description: 查询借用要件/借用公章记录
	 * @param @param params(uid,start,pageSize,type:type为1时查要件,其余为公章
	 * @param @return    设定文件 
	 * @return List<AuditInfoVo>    返回类型 
	 * @throws
	 */
	List<AuditInfoVo> selectBorrowRecord(Map<String, Object> params);
	/**
	 * 
	 * @Title: selectOrderList 
	 * @Description: 查询订单列表
	 * @param @param params
	 * @param @return    设定文件 
	 * @return List<ElementOrderVo>    返回类型 
	 * @throws
	 */
	List<ElementOrderVo> selectOrderList(Map<String, Object> params);
	/**
	 * 
	 * @Title: selectBorrowedOverTimeAuditIds 
	 * @Description: 查询存在超时未还的审批id集合
	 * @param @param 
	 * @param @return    设定文件 
	 * @return List<AuditBaseDto>    返回类型 
	 * @throws
	 */
	List<AuditBaseDto> selectBorrowedOverTimeAudits(Map<String,Object> params);
	/**
	 * 
	 * @Title: selectBorrowedOverTimeEle 
	 * @Description: 根据审批id查询未还的物品id集合
	 * @param @param id 审批id或开箱操作id
	 * @param @param type 类型 1:审批借用超时未还 2:开箱 超时未还
	 * @param @return    设定文件 
	 * @return List<Integer>    返回类型 
	 * @throws
	 */
	List<Integer> selectBorrowedOverTimeEle(@Param("id")Integer id,@Param("type")int type);
	/**
	 * 
	 * @Title: selectBorrowOperatorUid 
	 * @Description: 根据审批id查询操作人uid
	 * @param @param id 审批id(type==1)或操作id
 	 * @param @param type 类型 1 审批借用 2 开箱操作
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	String selectBorrowOperatorUid(@Param("id")Integer id,@Param("type")int type);
	/**
	 * 
	 * @Title: updateBorrowedOverTimeMsgState 
	 * @Description: 标记审批超时未还消息已发送
	 * @param @param id    审批id(type==1)或操作id
	 * @param @param type    类型 1审批借用超时未还 2开箱操作超时未还 
	 * @param @param noticeState  提醒状态(1:已提醒借用的人;2:已提醒审批人和要件柜管理员)
	 * @return void    返回类型 
	 * @throws
	 */
	void updateBorrowedOverTimeMsgState(Map<String,Object> params);
	/**
	 * 
	 * @Title: updateSysmsgState 
	 * @Description: 标记    已更新订单超时未还按钮显示和添加系统消息
	 * @param id    审批id(type==1)或操作id
	 * @param type    类型 1 审批借用超时未还  2 开箱操作超时未还 
	 * @return void    返回类型 
	 * @throws
	 */
	void updateSysmsgState(Map<String, Object> params);
	/**
	 * 
	 * @Title: selectAuditIdByOrderNo 
	 * @Description: 根据订单编号查出较早的审批id
	 * @param @param orderNo
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectAuditIdByOrderNo(Map<String,Object> params);
	/**
	 * 
	 * @Title: updateBtnOnState 
	 * @Description:标记按钮为展示状态
	 * @param @param type    1:取按钮;2:超时未还按钮
	 * @return void    返回类型 
	 * @throws
	 */
	void updateBtnOnState(@Param("type")int type,@Param("orderNo")String orderNo);
	/**
	 * 
	 * @Title: selectAuditorUids 
	 * @Description: 根据审批id查询审批人uid(不包含已转交)
	 * @param @param id
	 * @param @return    设定文件 
	 * @return List<String>    返回类型 
	 * @throws
	 */
	List<String> selectAuditorUids(Integer id);
	/**
	 * 
	 * @Title: selectBorrowedOverTimeOperation 
	 * @Description: 查询开箱超时未还操作
	 * @param @return    设定文件 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws
	 */
	List<Map<String, Object>> selectBorrowedOverTimeOperation(Map<String,Object> params);
	/**
	 * 
	 * @Title: selectElesInAuditByOrderNo 
	 * @Description: 查询已经申请审批的要件id集
	 * @param @param orderNo
	 * @param @return    设定文件 
	 * @return List<String>    返回类型 
	 * @throws
	 */
	List<String> selectElesInAuditByOrderNo(String orderNo);
	/**
	 * 
	 * @Title: selectBorrowedOverTimeEleByOrderNo 
	 * @Description: 查询订单的超时未还要件
	 * @param @param orderNo
	 * @param @return    设定文件 
	 * @return List<String>    返回类型 
	 * @throws
	 */
	List<String> selectBorrowedOverTimeEleByOrderNo(String orderNo);
	/**
	 * 
	 * @Title: selectApplayMsgIdByAuditId 
	 * @Description: 根据审批id查询发起的审批对应的回复消息 id
	 * @param @param id 审批id
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws
	 */
	Integer selectApplayMsgIdByAuditId(Integer id);
	
	/**
	 * 更新延长借用时间
	 * @param id
	 */
	void updateEndTime(int id);
	
	/**
	 * 查询一级审批是否通过
	 * @param dbId
	 * @return
	 */
	public Map<String,Object> selectAuditLevelFrist(int dbId);

}