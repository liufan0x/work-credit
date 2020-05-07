package com.anjbo.service;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.UserDto;
import com.anjbo.common.RespStatus;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/8.
 */
public interface StatisticsService {

    /**
     * 查询回款报备统计按城市分组
     * @param map key=day(时间条件),key=cityCode(城市条件),key=productCode(产品条件)
     * @return
     */
    List<Map<String,Object>> selectInPayment(Map<String,Object> map);

    /**
     * 查询出款报备统计按城市分组
     * @param map key=day(时间条件),key=cityCode(城市条件),key=productCode(产品条件)
     * @return
     */
    List<Map<String,Object>> selectOutPayment(Map<String,Object> map);

    /**
     * 查询今日待提单,初审,终审,首席风险官统计
     * @param map
     * @return
     */
    Map<String,Object> selectToDayOrder(Map<String,Object> map);

    Integer selectInPaymentCount(Map<String,Object> map);

    Integer selectOutPaymentCount(Map<String,Object> map);

    List<Map<String,Object>> selectCityCount(Map<String,Object> map);

    List<Map<String,Object>> createIncome(Map<String,Object> map, List<DictDto> listDic);

    /**
     * 查询机构的业务单量,授信额度,剩余额度,创收
     * @param map
     * key=productCode(产品编码),cooperativeModeId(合作模式(1.兜底,2非兜底))
     * startTime(检索条件：开始时间),endTime(检索条件：结束时间)
     * timeWhere=lastWeek(检索条件：上周)
     * timeWhere=lastMonth(检索条件：上月)
     * timeWhere=yesterday(检索条件：昨日)
     * timeWhere=thisMonth(检索条件：当月)
     * @return
     */
    List<Map<String,Object>> statisticsAgency(Map<String,Object> map);

    Integer statisticsAgencyCount(Map<String,Object> map);

    /**
     * 资金方统计
     * @param map
     * productCode(产品code)
     * startTime(检索条件：开始时间),endTime(检索条件：结束时间)
     * timeWhere=lastWeek(检索条件：上周)
     * timeWhere=lastMonth(检索条件：上月)
     * timeWhere=yesterday(检索条件：昨日)
     * timeWhere=thisMonth(检索条件：当月)
     * @return
     * fundId(资金id),fundName(资方名称)
     * fundLoanAmount(资方放款金额),payMentAmount(回款金额)
     * payCount(回款笔数),orderCount(业务单量)
     */
    List<Map<String,Object>> statisticsFund(Map<String,Object> map);

    Integer statisticsFundCount(Map<String,Object> map);

    /**
     * 下载个人目标创收模板
     * @param map
     * @return
     */
    void downloadIncome(Map<String,Object> map,UserDto userDto,ExcelService excelService,HttpServletResponse response)throws Exception;

    /**
     * 上传个人创收目标文件并解析保存数据库
     * @param file
     * @param map
     */
    void uploadIncome(MultipartFile file,Map<String,Object> map,UserDto userDto,ExcelService excelService,RespStatus result)throws Exception;

    /**
     * 删除目标报表
     * @param map
     */
    void cancelIncomeFileByDeptId(Map<String,Object> map);

    /**
     * 查询个人创收概览
     * @param map
     * productCode(产品code),cityCode(城市code)
     * startTime(开始时间),endTime(结束时间)
     * timeWhere=lastWeek(检索条件：上周)
     * timeWhere=lastMonth(检索条件：上月)
     * timeWhere=yesterday(检索条件：昨日)
     * timeWhere=thisMonth(检索条件：当月)
     * @return
     * orderCount(订单量),channelManagerName(渠道经理名称)
     * channelManagerUid(渠道经理uid),branchCompany(分公司)
     * productCode(产品code),productName(产品名称)
     * lendingAmount(放款金额),interest(利息)
     * fine(罚息),serviceCharge(服务费)
     * income(创收),loanAim(放款量目标)
     * incomeAim(创收目标)
     */
    List<Map<String,Object>> selectPersonalView(Map<String,Object> map,List<UserDto> users,List<DictDto> dics);

    Integer selectPersonalViewCount(Map<String,Object> map);
}
