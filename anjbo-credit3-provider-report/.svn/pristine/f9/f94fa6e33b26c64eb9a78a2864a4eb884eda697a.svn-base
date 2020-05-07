package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.UserDto;

/**
 * Created by Administrator on 2018/5/8.
 */
public interface ReportStatisticsMapper {
    /**
     * 查询几天内回款报备
     * @param map key=day(时间条件),key=cityCode(城市条件),key=productCode(产品条件)
     * @return
     */
    List<Map<String,Object>> selectInPayment(Map<String,Object> map);

    /**
     * 查询几天内出款报备
     * @param map key=day(时间条件),key=cityCode(城市条件),key=productCode(产品条件)
     * @return
     */
    List<Map<String,Object>> selectOutPayment(Map<String,Object> map);

    /**
     * 查询今日待提单,初审,终审,首席风险官统计
     * @param map
     * @return
     */
    List<Map<String,Object>> selectToDayOrder(Map<String,Object> map);

    /**
     * 查询机构下指定角色
     * @param map
     * @return
     */
    List<UserDto> selectRoleByAgencyIdRoleName(Map<String,Object> map);

    /**
     * 查询所以城市
     * @param map key=cityCode(城市code),effective(创收目标报表是否有效，1有效,2无效)
     * @return
     */
    List<Map<String,Object>> selectCity(Map<String,Object> map);

    List<Map<String,Object>> selectCityCount(Map<String,Object> map);
    
    List<Map<String, Object>> findByCityCount(Map<String,Object> map);

    /**
     * 查询知道机构部门
     * @param map key=agencyId(机构id)
     * @return
     */
    List<Map<String,Object>> selectDept(Map<String,Object> map);

    /**
     * 查询创收目标报表上传的文件
     * @param map key=effective(创收目标报表是否有效，1有效,2无效),deptId(部门id),cityCode(城市code)
     * @return
     */
    List<Map<String,Object>> selectIncomeFile(Map<String,Object> map);



    /**
     * 查询机构的授信额度,剩余额度, 创收,放款额,业务单量,回款额,回款单量(只有页面是该字段排序才会返回)
     * @param map
     * key=productCode(产品编码),cooperativeModeId(合作模式(1.兜底,2非兜底))
     * startTime(检索条件：开始时间),endTime(检索条件：结束时间)
     * timeWhere=lastWeek(检索条件：上周)
     * timeWhere=lastMonth(检索条件：上月)
     * timeWhere=yesterday(检索条件：昨日)
     * timeWhere=thisMonth(检索条件：当月)
     * timeWhere=lastYear(检索条件：去年)
     */
    List<Map<String,Object>> statisticsAgency(Map<String,Object> map);

    Integer statisticsAgencyCount(Map<String,Object> map);

    /**
     * 查询机构的创收,放款额,业务单量,回款额,回款单量(只有页面是该字段没有排序才会返回)
     * @param map
     * startTime(检索条件：开始时间),endTime(检索条件：结束时间)
     * timeWhere=lastWeek(检索条件：上周)
     * timeWhere=lastMonth(检索条件：上月)
     * timeWhere=yesterday(检索条件：昨日)
     * timeWhere=thisMonth(检索条件：当月)
     * timeWhere=lastYear(检索条件：去年)
     * @return
     */
    List<Map<String,Object>> statisticsAgencyOther(Map<String,Object> map);


    /**
     * 资金方统计
     * @param map
     * @return
     */
    List<Map<String,Object>> statisticsFund(Map<String,Object> map);

    Integer statisticsFundCount(Map<String,Object> map);


    /**
     * 新增创收目标报表文件
     * @param map
     */
    void insertIncomFile(Map<String,Object> map);

    /**
     * 更新创收目标报表文件
     * @param map
     */
    void updateIncomeFile(Map<String,Object> map);

    /**
     * 新增个人创收目标报表
     * @param list
     */
    void insertPersonalIncome(List<Map<String,Object>> list);

    /**
     * 更新个人创收目标报表
     * @param map
     */
    void updatePersonalIncome(Map<String,Object> map);

    /**
     * 将个人创收目标报表更新为无效状态
     * @param map
     */
    void cancelPersonalIncome(Map<String,Object> map);

    /**
     * 更新创收报表文件为无效
     * @param map
     */
    void cancelIncomeFile(Map<String,Object> map);

    /**
     * 将目标报表文件保存到历史记录中
     * @param map
     */
    void insertIncomeFileHistory(Map<String,Object> map);

    /**
     * 根据部门id查询创收目标报表文件历史记录
     * @param map
     * @return
     */
    List<Map<String,Object>> selectIncomeFileHistoryByDeptId(Map<String,Object> map);

    /**
     * 查询所有的创收目标报表历史记录
     * @param map
     * @return
     */
    List<Map<String,Object>> selectAllIncomeFileHistory(Map<String,Object> map);

    /**
     * 根据部门id查询创收目标报表文件
     * @param map
     * @return
     */
    Map<String,Object> selectIncomeFileByCityCode(Map<String,Object> map);

    /**
     * 查询个人创收概览
     * @param map
     * @return
     */
    List<Map<String,Object>> selectPersonalView(Map<String,Object> map);

    /**
     * 查询income（创收）lendingAmount(放款金额)interest(利息),fine(罚息),serviceCharge(服务费)
     * @param map
     * @return
     */
    List<Map<String,Object>> selectPersonalViewOther(Map<String,Object> map);

    Integer selectPersonalViewCount(Map<String,Object> map);

    /**
     * 查询个人创收目标
     * @param map
     * month=月份
     * year=年份
     * deptId=部门id
     * @return
     */
    List<Map<String,Object>> selectPersonalIncome(Map<String,Object> map);

    List<Map<String,Object>> statisticsFundUsing(Map<String,Object> map);

    /**
     * 资方放款总计
     * @param map
     * @return
     */
    List<Map<String,Object>> statisticsFundLoanTotal(Map<String,Object> map);

    /**
     * 资方放款总计
     * @param map
     * @return
     */
    List<Map<String,Object>> statisticsFundPayTotal(Map<String,Object> map);

    List<Map<String,Object>> statisticsAgencyTotalLoan(Map<String,Object> map);

    List<Map<String,Object>> statisticsAgencyTotalPay(Map<String,Object> map);
    
    List<Map<String,Object>> selectPersonalUser(Map<String,Object> map);
}
