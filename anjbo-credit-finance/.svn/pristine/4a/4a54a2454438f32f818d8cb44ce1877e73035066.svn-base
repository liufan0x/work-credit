package com.anjbo.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.finance.ReceivableForDto;

public interface ReceivableForMapper {
	/**
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	public List<ReceivableForDto>  findByReceivableFor(ReceivableForDto dto);
	
	
	/**
	 * 添加订单基本信息
	 * @param dto
	 * @return
	 */
	public int addReceivableFor(ReceivableForDto dto);
	
	/**
	 * 完善信息
	 * @param dto
	 * @return
	 */
    public int updateReceivableFor(ReceivableForDto dto);
	
    /**
     * 撤回
     * @param orderNo
     * @return
     */
    public int updwithdraw(ReceivableForDto dto);
    
	/**
	 * 删除
	 * @param orderNo
	 * @return
	 */
	public int delectFor(ReceivableForDto dto);
	
	/**
	 * 根据订单 查询未处理的
	 * @param orderNo
	 * @return
	 */
	public ReceivableForDto selectByStatus(String orderNo);
	
	/**
	 * 计算总回款额度
	 * @return
	 */
	public BigDecimal findByCountPay(String orderNo);

	/**
	 * 根据机构回款时间查询订单
	 * @Author KangLG<2017年11月9日>
	 * @param ReceivableForDto: agencyId/payMentAmountDateStart/payMentAmountDateEnd
	 * @return 订单号集合
	 */
	public List<String> search4AgencyOrderTime(ReceivableForDto dto);
	
	/**
	 * 回款报表查询
	 * @Author KangLG<2017年11月9日>
	 * @return
	 */
	public List<ReceivableForDto>  search4reportChart(ReceivableForDto dto);
	public List<ReceivableForDto>  search4report(ReceivableForDto dto);

	/**
	 * App机构回款月统计数据查询
	 * @Author
	 * @param agencyId
	 * @param orderNos
	 * @return
	 */
	public List<Map<String, Object>> findPaymentStatisticMonth(@Param("agencyId") String agencyId, @Param("orderNos") String orderNos);
	
	/**
	 * App机构回款统计总数据查询
	 * @Author
	 * @param agencyId
	 * @param orderNos
	 * @return
	 */
	public Map<String, Object> findPaymentStatisticTotal(@Param("agencyId") String agencyId, @Param("orderNos") String orderNos);
	
	/**
	 * App机构回款月统计详情数据查询
	 * @Author
	 * @param agencyId
	 * @param orderNos
	 * @return
	 */
	public List<Map<String, Object>> findPaymentStatisticDetail(@Param("agencyId") String agencyId, @Param("orderNos") String orderNos, 
			@Param("year") int year, @Param("month") int month);
	
	/**
	 * 根据订单查询App机构回款月统计数据
	 * @Author jiangyq
	 * @param orderNos
	 * @return
	 */
	public List<Map<String, Object>> findPaymentByLoan(@Param("orderNos") String orderNos);
	
	/**
	 *  App当月回款数据 
	 * @Author jiangyq
	 * @param orderNos
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String, Object> findPaymentStatisticByMonthAndOrderNos(@Param("orderNos") String orderNos, 
			@Param("year") int year, @Param("month") int month);
}