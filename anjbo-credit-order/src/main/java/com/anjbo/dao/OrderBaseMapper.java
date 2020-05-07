package com.anjbo.dao;

import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.order.vo.OrderReportLendingVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author lic
 * @date 2017-6-5
 */
public interface OrderBaseMapper {
	
	/**
	 * 查询所有订单
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectAllOrder(Map<String,Object> map);

	List<OrderListDto> selectOrderList(OrderListDto orderListDto);
	
	/**
	 * app列表查询
	 * @param orderListDto
	 * @return
	 */
	List<OrderListDto> selectOrderListApp(OrderListDto orderListDto);

	int selectOrderCount(OrderListDto orderListDto);
	
	int selectlendingCount(OrderListDto orderListDto);
	
	int insertOrderList(OrderListDto orderListDto);
	
	int updateOrderList(OrderListDto orderListDto);
	
	int submitAuditUpdateOrderList(OrderListDto orderListDto);
	
	int deleteOrderList(@Param("orderNo")String orderNo);
	
	OrderListDto selectDetail(@Param("orderNo") String orderNo);
	
	List<OrderListDto> selectOrderAll(OrderListDto orderListDto);
	
	public int updateAcceptMember(OrderListDto orderList);

	public Integer selectIsFace(@Param("orderNo") String orderNo);

	public int updateIsFace(Map<String,Object> map);
	
	int updateCustomerName(@Param("orderNo") String orderNo, @Param("customerName") String customerName);
	
	/**
	 * 放款报表查询
	 * @Author KangLG<2017年11月10日>
	 * @param orderList
	 * @return
	 */
	List<OrderReportLendingVo> search4reportLendingChart(OrderListDto orderList);
	List<OrderReportLendingVo> search4reportLending(OrderListDto orderList);
	
	/**
	 * App机构放款月统计数据查询
	 * @Author
	 * @param agencyId
	 * @param uids
	 * @return
	 */
	public List<Map<String, Object>> findLoanStatisticMonth(@Param("agencyId") String agencyId, @Param("uids") String uids);
	
	/**
	 * App机构放款统计总数据查询
	 * @Author
	 * @param agencyId
	 * @param uids
	 * @return
	 */
	public Map<String, Object> findLoanStatisticTotal(@Param("agencyId") String agencyId, @Param("uids") String uids);
	
	/**
	 * App机构放款月统计详情数据查询
	 * @Author
	 * @param agencyId
	 * @param uids
	 * @return
	 */
	public List<Map<String, Object>> findLoanStatisticDetail(@Param("agencyId") String agencyId, @Param("uids") String uids, 
			@Param("year") int year, @Param("month") int month);
	
	
	
	/**
	 * App机构完成订单统计查询
	 * @Author
	 * @param agencyId
	 * @param uids
	 * @return
	 */
	public List<Map<String, Object>> findFinishStatisticMonth(@Param("agencyId") String agencyId, @Param("uids") String uids);
	
	/**
	 * App机构完成订单总数查询
	 * @Author
	 * @param agencyId
	 * @param uids
	 * @return
	 */
	public Map<String, Object> findFinishStatisticTotal(@Param("agencyId") String agencyId, @Param("uids") String uids);
	
	/**
	 * App机构完成订单详情查询
	 * @Author
	 * @param agencyId
	 * @param uids
	 * @return
	 */
	public List<Map<String, Object>> findFinishStatisticDetail(@Param("agencyId") String agencyId, @Param("uids") String uids, 
			@Param("year") int year, @Param("month") int month);
	
	/**
	 * 通过订单号查询并返回订单集合信息
	 * @param orderNos
	 * @return
	 */
	public List<Map<String, Object>> findLoanByOrderNos(@Param("orderNos") String orderNos);
	
	/**
	 * 通过用户uids查询并返回订单号OrderNos
	 * @param uids
	 * @return
	 */
	public List<String> findOrderNosByUids(@Param("uids") String uids);
	
	/**
	 * App当月放款数据
	 * @Author
	 * @param uids
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String, Object> findLoanStatisticByMonthAndUids(@Param("uids") String uids, @Param("year") int year, @Param("month") int month);
	
	public List<OrderListDto> selectAllOrderList();
	/**
	 * 查询畅贷可关联的订单
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectAbleRelationOrder(Map<String,Object> map);


	/**
	 * 获取合作机构放款之前回款之前放款金额
	 * @param map
	 * @return
	 */
	public OrderListDto selectAgencyLoan(Map<String,Object> map);

	
	/**
	 * 查询订单公证经办人
	 * @param orderNo
	 * @return
	 */
	public Map<String,Object> selectNotarialUidByOrderNo(@Param("orderNo") String orderNo);
	
	/**
	 * 查询用户某个节点待处理的订单数量
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectUidOrderCount(Map<String,Object> map);
	
	public OrderListDto selectOrderListByContractNo(OrderListDto orderListDto);

}