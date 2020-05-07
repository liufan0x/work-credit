package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.vo.ChartVo;

public interface ReceivableForService {
	
	/**
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	public List<ReceivableForDto> findByReceivableFor(ReceivableForDto dto);
	/**
	 * 添加多次回款
	 * @param dto
	 * @return
	 */
	public int addReceivableForToMany(ReceivableForDto dto);

	/**
	 * 添加一次回款
	 * @param dto
	 * @return
	 */
	public int addReceivableFor(ReceivableForDto dto);
	
	/**
	 * 撤回
	 * @param receivableFor
	 * @return
	 */
	public int updwithdraw(ReceivableForDto receivableFor);
	
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
	 * @param lastMonth 最近多少个月
	 * @return
	 */
	public ChartVo  search4reportChart(ReceivableForDto dto, int lastMonth);
	public List<ReceivableForDto>  search4report(ReceivableForDto dto);
	
	/**
	 * App机构回款月统计数据查询
	 * @Author jiangyq
	 * @param agencyId
	 * @param uids
	 * @return
	 */
	public List<Map<String, Object>> findPaymentStatisticMonth(String agencyId, String orderNos);
	
	/**
	 * App机构回款统计总数据查询
	 * @Author jiangyq
	 * @param agencyId
	 * @param orderNos
	 * @return
	 */
	public Map<String, Object> findPaymentStatisticTotal(String agencyId, String orderNos);
	
	/**
	 * App机构回款月统计详情数据查询
	 * @Author jiangyq
	 * @param agencyId
	 * @param orderNos
	 * @return
	 */
	public List<Map<String, Object>> findPaymentStatisticDetail(String agencyId, String orderNos, int year, int month);
	
	
	/**
	 * 根据订单查询App机构回款月统计数据
	 * @Author jiangyq
	 * @param orderNos
	 * @return
	 */
	public List<Map<String, Object>> findPaymentByLoan(String orderNos);
	
	/**
	 *  App当月回款数据 
	 * @Author jiangyq
	 * @param orderNos
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String, Object> findPaymentStatisticByMonthAndOrderNos(String orderNos, int year, int month);
}
