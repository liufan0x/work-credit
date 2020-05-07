package com.anjbo.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.finance.AfterLoanListDto;
import com.anjbo.bean.finance.AfterLoanLogDto;
import com.anjbo.bean.finance.AlterLoanBudgetRepaymentDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.FddMortgageStorageDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AfterLoanListService;
import com.anjbo.service.AfterLoanLogService;
import com.anjbo.service.AfterloanEqualInterestService;
import com.anjbo.service.AfterloanFirstInterestService;
import com.anjbo.service.ExcelService;
import com.anjbo.utils.BeanToMapUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;

/**
 * Created by Administrator on 2018/3/27.
 * 贷后管理列表
 */
@Controller
@RequestMapping("/credit/finance/afterLoanList")
public class AfterLoanListController extends BaseController{

    private Logger log = org.apache.log4j.Logger.getLogger(getClass());

    @Resource
    private AfterLoanListService afterLoanListService;
    @Resource
    private AfterloanFirstInterestService afterloanFirstInterestService;
    @Resource
    private AfterLoanLogService afterLoanLogService;
    @Resource
    private AfterloanEqualInterestService afterloanEqualInterestService;
    @Resource
    private ExcelService excelService;
    @ResponseBody
    @RequestMapping("/v/list")
    public RespPageData<AfterLoanListDto> list(HttpServletRequest request, @RequestBody AfterLoanListDto obj){
        RespPageData<AfterLoanListDto> result = new RespPageData<AfterLoanListDto>();
        result.setCode(RespStatusEnum.FAIL.getCode());
        result.setMsg(RespStatusEnum.FAIL.getMsg());
        try{
            result.setTotal(afterLoanListService.listCount(obj));
            result.setRows(afterLoanListService.list(obj));
            result.setCode(RespStatusEnum.SUCCESS.getCode());
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
        } catch (Exception e){
            result.setCode(RespStatusEnum.FAIL.getCode());
            result.setMsg(RespStatusEnum.FAIL.getMsg());
            log.error("加载贷后信息列表异常:",e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/v/insertAfterLoanList")
    public RespStatus insertAfterLoanList(HttpServletRequest request,@RequestBody AfterLoanListDto obj){
        RespStatus result = new RespStatus();
        try{
            if(StringUtil.isBlank(obj.getOrderNo())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            if(null==obj.getLendingTime()){
                RespHelper.setFailRespStatus(result,"放款时间不能为空!");
                return result;
            }
            if(obj.getBorrowingPeriods()<=0){
                RespHelper.setFailRespStatus(result,"借款期限不能小于等于0!");
                return result;
            }
            if(obj.getRepaymentType()<=0){
                RespHelper.setFailRespStatus(result,"还款方式不能为空!");
                return result;
            }
            UserDto channelManager = getUserDtoByUid(obj.getChannelManagerUid());
            UserDto acceptMember = getUserDtoByUid(obj.getAcceptMemberUid());
            obj.setChannelManagerName(channelManager.getName());
            obj.setChannelManagerPhone(channelManager.getMobile());
            obj.setAcceptMemberName(acceptMember.getName());
            List<ProductDto> productDtos = getProductDtos();
            for (ProductDto p:productDtos){
                if(p.getProductCode().equals(obj.getProductCode())){
                    obj.setProductName(p.getProductName());
                    break;
                }
            }
            List<DictDto> cityList = getDictDtoByType("cityList");
            for (DictDto d:cityList){
                if(d.getCode().equals(obj.getCityCode())){
                    obj.setCityName(d.getName());
                    break;
                }
            }

            UserDto user = getUserDto(request);
            obj.setCreateUid(user.getUid());
            obj.setUpdateUid(user.getUid());
            Integer insert = afterLoanListService.insert(obj);
            if(-1==insert){
                RespHelper.setFailRespStatus(result,"该订单已在还款列表中不能重复创建!");
                return result;
            }
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("新增贷后列表信息异常:",e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/v/restart")
    public RespStatus restart(HttpServletRequest request,@RequestBody Map<String,Object> map){
        //TODO 测试用,上线需删除
        RespStatus respStatus = new RespStatus();
        String orderNo = MapUtils.getString(map,"orderNo");
        if(StringUtil.isBlank(orderNo)){
            RespHelper.setFailRespStatus(respStatus,RespStatusEnum.FAIL.getMsg());
            return respStatus;
        }
        AfterLoanListDto obj = new AfterLoanListDto();
        obj.setOrderNo(orderNo);
        obj = afterLoanListService.select(obj);
        if(null!=obj){
            AfterLoanLogDto log = new AfterLoanLogDto();
            log.setOrderNo(orderNo);
            afterLoanLogService.deleteAll(log);
            AlterLoanBudgetRepaymentDto t = new AlterLoanBudgetRepaymentDto();
            t.setOrderNo(orderNo);
            afterloanEqualInterestService.delete(t);
            afterloanFirstInterestService.delete(t);
            afterLoanListService.delete(obj);
            try {
                afterLoanListService.insert(obj);
            } catch (Exception e){
                e.printStackTrace();
            }
            RespHelper.setSuccessRespStatus(respStatus);
        } else {
            RespHelper.setFailRespStatus(respStatus,"没有该订单匹配的数据");
        }
        return respStatus;
    }
    /**
     * 获取贷后列表详细信息
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/loanDetail")
    public RespDataObject<AfterLoanListDto> loanDetail(HttpServletRequest request,@RequestBody AfterLoanListDto obj){
        RespDataObject<AfterLoanListDto> result = new RespDataObject<AfterLoanListDto>();
        try{
            AfterLoanListDto select = afterLoanListService.select(obj);
            if(null!=select&&null!=select.getLoanAmount()) {
                double v = new BigDecimal(Double.toString(select.getLoanAmount())).multiply(new BigDecimal("10000")).doubleValue();
                select.setLoanAmount(v);
            }
            RespHelper.setSuccessDataObject(result,select);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("获取贷后列表详细信息异常:",e);
        }
        return result;
    }

    /**
     * 等额本息
     * @param request
     * @param obj
     */
    @ResponseBody
    @RequestMapping("/v/equalInterestList")
    public RespPageData<AlterLoanBudgetRepaymentDto> equalInterestList(HttpServletRequest request,@RequestBody AlterLoanBudgetRepaymentDto obj){
        RespPageData<AlterLoanBudgetRepaymentDto> result = new RespPageData<AlterLoanBudgetRepaymentDto>();
        try{
            List<AlterLoanBudgetRepaymentDto> list = afterloanEqualInterestService.list(obj);
            //修改应还款日
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("orderNo", obj.getOrderNo());
            RespDataObject<Map<String,Object>> resp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/third/api/sgtongBorrowerInformation/v/find",map,Map.class);
            if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())) {
            	Map<String,Object> m = resp.getData();
            	if(m!=null&&m.get("sgtLendingTime")!=null&&StringUtil.isNotBlank(MapUtils.getString(m, "sgtLendingTime"))) {
            		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            		Date sgtLendingTime = sdf.parse(MapUtils.getString(m, "sgtLendingTime"));
            		 for (int i=0;i<list.size() ;i++) {
            			 AlterLoanBudgetRepaymentDto temp = list.get(i);
            			 Calendar cal = Calendar.getInstance();
            			 cal.setTime(sgtLendingTime);
            			 cal.add(Calendar.MONTH, i+1);
         				temp.setRepaymentDate(cal.getTime());//计算应还款时间
         			}
            	}
            }
            Integer count = afterloanEqualInterestService.listCount(obj);
            result.setRows(list);
            result.setTotal(count);
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("加载等额本息信息列表异常:",e);
        }
        return result;
    }

    /**
     * 先息后本
     * @param request
     * @param obj
     */
    @ResponseBody
    @RequestMapping("/v/firstInterestList")
    public RespPageData<AlterLoanBudgetRepaymentDto> firstInterestList(HttpServletRequest request,@RequestBody AlterLoanBudgetRepaymentDto obj){
        RespPageData<AlterLoanBudgetRepaymentDto> result = new RespPageData<AlterLoanBudgetRepaymentDto>();
        try {
            List<AlterLoanBudgetRepaymentDto> list = afterloanFirstInterestService.list(obj);
            //修改应还款日
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("orderNo", obj.getOrderNo());
            RespDataObject<Map<String,Object>> resp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/third/api/sgtongBorrowerInformation/v/find",map,Map.class);
            if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())) {
            	Map<String,Object> m = resp.getData();
            	if(m!=null&&m.get("sgtLendingTime")!=null&&StringUtil.isNotBlank(MapUtils.getString(m, "sgtLendingTime"))) {
            		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            		Date sgtLendingTime = sdf.parse(MapUtils.getString(m, "sgtLendingTime"));
            		 for (int i=0;i<list.size() ;i++) {
            			 AlterLoanBudgetRepaymentDto temp = list.get(i);
            			 Calendar cal = Calendar.getInstance();
            			 cal.setTime(sgtLendingTime);
            			 cal.add(Calendar.MONTH, i+1);
         				temp.setRepaymentDate(cal.getTime());//计算应还款时间
         			}
            	}
            }
            Integer count = afterloanFirstInterestService.listCount(obj);
            result.setTotal(count);
            result.setRows(list);
            result.setCode(RespStatusEnum.SUCCESS.getCode());
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("加载先息后本列表异常:",e);
        }
        return result;
    }

    /**
     * 贷后日志列表
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/listLog")
    public RespPageData<AfterLoanLogDto> listLog(HttpServletRequest request,@RequestBody AfterLoanLogDto obj){
        RespPageData<AfterLoanLogDto> result = new RespPageData<AfterLoanLogDto>();
        try{
            List<AfterLoanLogDto> list = afterLoanLogService.list(obj);
            Integer count = afterLoanLogService.listCount(obj);
            result.setTotal(count);
            result.setRows(list);
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("加载贷后日志列表异常:",e);
        }
        return result;
    }

    /**
     * 加载贷后日志列表检索初始化数据
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/listWhere")
    public RespDataObject<Map<String,Object>> listWhere(HttpServletRequest request,@RequestBody AfterLoanLogDto obj){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            List<Map<String, Object>> list = afterLoanLogService.listOperate(obj);
            map.put("listOperate",list);
            RespHelper.setSuccessDataObject(result,map);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("加载贷后日志列表检索初始化数据异常:",e);
        }
        return result;
    }

    /**
     * 加载所有未完结贷款订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/allLoan")
    public RespDataObject<List<Map<String,Object>>> allLoan(HttpServletRequest request){
        RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
        try{
            AlterLoanBudgetRepaymentDto obj = new AlterLoanBudgetRepaymentDto();
            List<Map<String, Object>> maps = afterLoanListService.allLoan(obj);
            RespHelper.setSuccessDataObject(result,maps);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("加载所有未完结贷款订单异常:",e);
        }
        return result;
    }

    /**
     * 新增贷后日志
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/addLog")
    public RespDataObject<AfterLoanLogDto> addLog(HttpServletRequest request,@RequestBody AfterLoanLogDto obj){
        RespDataObject<AfterLoanLogDto> result = new RespDataObject<AfterLoanLogDto>();
        try{
            if(StringUtil.isBlank(obj.getOrderNo())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            obj.setCreateUid(user.getUid());
            obj.setCreateName(user.getName());
            afterLoanLogService.insert(obj);
            RespHelper.setSuccessDataObject(result,obj);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("新增贷后日志异常:",e);
        }
        return result;
    }

    /**
     * 上传贷后日志附件
     * @param request
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/uploadLogFile")
    public RespStatus uploadLogFile(HttpServletRequest request,@RequestBody List<Map<String,Object>> list){
        RespStatus result = new RespStatus();
        try{
            UserDto userDto = getUserDto(request);
            for(Map<String,Object> m:list){
                m.put("createUid",userDto.getUid());
            }
            afterLoanLogService.bacthInsertFile(list);
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("上传贷后日志附件异常:",e);
        }
        return result;
    }

    /**
     * 等额本息还款
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/equalrepayment")
    public RespDataObject<AfterLoanLogDto> equalrepayment(HttpServletRequest request,@RequestBody AfterLoanLogDto obj){
        RespDataObject<AfterLoanLogDto> result = new RespDataObject<AfterLoanLogDto>();
        try {
            if(StringUtil.isBlank(obj.getOrderNo())
                    ||null==obj.getRepaymentPeriods()
                    ||obj.getRepaymentPeriods()<=0){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            AlterLoanBudgetRepaymentDto dto = getAlterLoanBudgetRepayment(user, obj);
            AfterLoanListDto tmpLoan = null;
            boolean isOver = afterLoanListService.repayment(dto,obj,user,2,result,tmpLoan);
            if(isOver){
                nextFlow(obj.getOrderNo(),user,null==tmpLoan?"":tmpLoan.getAcceptMemberUid());
                if(null==tmpLoan){
                    log.info("等额本息还款结清还款没有获取到AfterLoanListDto对象!");
                } else {
                    log.info("等额本息还款结清还款获取的受理员uid为:"+tmpLoan.getAcceptMemberUid());
                }
            }
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("等额本息还款异常:",e);
        }
        return result;
    }

    /**
     * 先息后本还款
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/firstrepayment")
    public RespDataObject<AfterLoanLogDto> firstrepayment(HttpServletRequest request,@RequestBody AfterLoanLogDto obj){
        RespDataObject<AfterLoanLogDto> result = new RespDataObject<AfterLoanLogDto>();
        try {
            if(StringUtil.isBlank(obj.getOrderNo())
                    ||null==obj.getRepaymentPeriods()
                    ||obj.getRepaymentPeriods()<=0){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            AlterLoanBudgetRepaymentDto dto = getAlterLoanBudgetRepayment(user, obj);
            AfterLoanListDto tmpLoan = null;
            boolean isOver = afterLoanListService.repayment(dto,obj,user,1,result,tmpLoan);
            if(isOver){
                nextFlow(obj.getOrderNo(),user,null==tmpLoan?"":tmpLoan.getAcceptMemberUid());
                if(null==tmpLoan){
                    log.info("先息后本还款结清还款没有获取到AfterLoanListDto对象!");
                } else {
                    log.info("先息后本还款结清还款获取的受理员uid为:"+tmpLoan.getAcceptMemberUid());
                }
            }
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("先息后本还款异常:",e);
        }
        return result;
    }

    /**
     * 查询最新一期的贷款信息
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/detailLastLoan")
    public RespDataObject<Map<String,Object>> detailLastLoan(HttpServletRequest request,@RequestBody AfterLoanListDto obj){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try{
            if(StringUtil.isBlank(obj.getOrderNo())){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            obj = afterLoanListService.select(obj);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("loan",obj);
            if(null==obj){
                RespHelper.setSuccessDataObject(result,map);
                return result;
            }
            AlterLoanBudgetRepaymentDto dto = new AlterLoanBudgetRepaymentDto();
            dto.setOrderNo(obj.getOrderNo());
            if(1==obj.getRepaymentType()){
                dto = afterloanFirstInterestService.selectByOrderNo(dto);
            } else if(2==obj.getRepaymentType()){
                dto = afterloanEqualInterestService.selectByOrderNo(dto);
            }
            if(null!=dto){
                String statusName = getStatusName(dto.getStatus());
                dto.setStatusName(statusName);
            }
            Map<String,Object> data = BeanToMapUtil.beanToMap(dto);
            Set<String> d = data.keySet();
            for (String string : d) {
				if(data.get(string)!=null && data.get(string) instanceof Double){
					Double dou = MapUtils.getDouble(data, string);
					data.put(string, BigDecimal.valueOf(dou).toPlainString());
				}
			}
            map.put("detail",data);
            RespHelper.setSuccessDataObject(result,map);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询最新一期的贷款信息异常:",e);
        }
        return result;
    }

    /**
     * 查询指定期数的还款记录
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/selectCurrentPeriodsLog")
    public RespDataObject<List<AfterLoanLogDto>> selectCurrentPeriodsLog(HttpServletRequest request,@RequestBody AfterLoanLogDto obj){
        RespDataObject<List<AfterLoanLogDto>> result = new RespDataObject<List<AfterLoanLogDto>>();
        try{
            if(StringUtil.isBlank(obj.getOrderNo())
                    ||null==obj.getRepaymentPeriods()
                    ||obj.getRepaymentPeriods()<=0){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            List<AfterLoanLogDto> list = afterLoanLogService.selectCurrentPeriodsLog(obj);
            RespHelper.setSuccessDataObject(result,list);
        } catch (Exception e){
            RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
            log.error("查询指定期数的还款记录异常:",e);
        }
        return result;
    }

    /**
     * 关闭短信提醒
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/closeMsg")
    public RespStatus closeMsg(HttpServletRequest request,Map<String,Object> map){
        RespStatus result = new RespStatus();
        String orderNo = MapUtils.getString(map,"orderNo");
        Integer repaymentPeriods = MapUtils.getInteger(map,"repaymentPeriods",null);
        Integer repaymentType = MapUtils.getInteger(map,"repaymentType",null);
        Integer closeMsg = MapUtils.getInteger(map,"closeMsg",null);
        try{
            if(StringUtil.isBlank(orderNo)
                    ||null==repaymentPeriods
                    ||null==repaymentType
                    ||null==closeMsg){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            AlterLoanBudgetRepaymentDto dto = new AlterLoanBudgetRepaymentDto();
            dto.setOrderNo(orderNo);
            dto.setRepaymentPeriods(repaymentPeriods);
            dto.setCloseMsg(closeMsg);
            afterLoanListService.closeMsg(repaymentType,dto);
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            logger.error("关闭orderNo:"+orderNo+",第"+repaymentPeriods+"期短信提醒异常:",e);
        }
        return result;
    }
    public AlterLoanBudgetRepaymentDto getAlterLoanBudgetRepayment(UserDto user,AfterLoanLogDto obj){
        AlterLoanBudgetRepaymentDto dto  = new AlterLoanBudgetRepaymentDto();
        dto.setCreateUid(user.getUid());
        dto.setUpdateUid(user.getUid());
        dto.setOrderNo(obj.getOrderNo());
        dto.setRepaymentPeriods(obj.getRepaymentPeriods());
        //本金
        dto.setGivePayPrincipal(null==obj.getGivePayPrincipal()?0d:obj.getGivePayPrincipal());
        //利息
        dto.setGivePayInterest(obj.getGivePayInterest());
        //逾期
        dto.setGivePayOverdue(obj.getGivePayOverdue());
        dto.setStart(0);
        dto.setPageSize(1000);
        obj.setCreateUid(user.getUid());
        obj.setCreateName(user.getName());
        return dto;
    }

    /**
     * 根据订单号查询该订单的贷后还款状态
     * @param request
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/listLoanStatus")
    public RespDataObject<Map<String,Object>> listLoanStatus(HttpServletRequest request,@RequestBody Map<String,Object> map){
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        try{
            String orderNo = MapUtils.getString(map,"orderNo");
            if(StringUtil.isBlank(orderNo)){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            Map<String, Object> data = afterLoanListService.selectInOrderNo(orderNo);
            RespHelper.setSuccessDataObject(result,data);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("加载贷后状态集合异常:",e);
        }
        return result;
    }

    /**
     * 下载还款计划
     * @param request
     * @param map
     */
    @ResponseBody
    @RequestMapping("/v/downloadRepayment")
    public void downloadRepayment(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String,Object> map){
        try{
            if(StringUtil.isBlank(MapUtils.getString(map,"orderNo"))){
                response.setStatus(403);
                return;
            }
            AfterLoanListDto obj = new AfterLoanListDto();
            obj.setOrderNo(MapUtils.getString(map,"orderNo"));
            UserDto user = getUserDto(request);
            afterLoanListService.downloadRepayment(obj,user,excelService,response);
        } catch (Exception e){
            log.error("下载还款计划异常:",e);
        }
    }

    /**
     * 撤回还款记录
     * @param request
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping("/v/withdrawLogRecord")
    public RespStatus withdrawLogRecord(HttpServletRequest request, @RequestBody AfterLoanLogDto obj){
        RespStatus result = new RespStatus();
        try{
            if(obj.getId()<=0){
                RespHelper.setFailRespStatus(result,RespStatusEnum.PARAMETER_ERROR.getMsg());
                return result;
            }
            UserDto user = getUserDto(request);
            afterLoanListService.withdrawLogRecord(result,obj,user);
            RespHelper.setSuccessRespStatus(result);
        } catch (Exception e){
            RespHelper.setFailRespStatus(result,RespStatusEnum.FAIL.getMsg());
            log.error("还款记录撤回失败:",e);
        }
        return result;
    }
    public String getStatusName(Integer status){
        String statusName = "";
        if(null==status)return statusName;
        switch (status){
            case 1:statusName="待还款";
                break;
            case 2:statusName="部分还款";
                break;
            case 3:statusName="已还款";
                break;
            case 4:statusName="结清还款";
                break;
        }
        return statusName;
    }
    public void nextFlow(String orderNo,UserDto user,String acceptMemberUid){
            OrderFlowDto orderFlowDto=new OrderFlowDto();
            orderFlowDto.setOrderNo(orderNo);
            orderFlowDto.setCurrentProcessId("fddRepayment");
            orderFlowDto.setCurrentProcessName("还款");
            orderFlowDto.setHandleUid(user.getUid());  //当前处理人
            orderFlowDto.setHandleName(user.getName());
            OrderListDto orderListDto=new OrderListDto();
            orderFlowDto.setNextProcessId("fddMortgageRelease");
            orderFlowDto.setNextProcessName("抵押品出库");

            FddMortgageStorageDto fddMortgageStorageDto =new FddMortgageStorageDto();
            fddMortgageStorageDto.setOrderNo(orderNo);
            HttpUtil httpUtil = new HttpUtil();
            RespDataObject<FddMortgageStorageDto>  respDataObject = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/fddMortgageStorage/v/detail", fddMortgageStorageDto,FddMortgageStorageDto.class);
            if(null!=respDataObject.getData()  || null!=respDataObject.getData().getCollateralUid()){
                orderListDto.setCurrentHandlerUid(respDataObject.getData().getCollateralUid());//下一处理人入库操作人
                UserDto userDto= CommonDataUtil.getUserDtoByUidAndMobile(respDataObject.getData().getCollateralUid());
                orderListDto.setCurrentHandler(userDto.getName());
            }else{
                orderListDto.setCurrentHandlerUid(acceptMemberUid);//下一处理人默认受理员
                UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(acceptMemberUid);
                orderListDto.setCurrentHandler(userDto.getName());
            }
            goNextNode(orderFlowDto, orderListDto);  //流程方法
    }

    @InitBinder
    public void InitBinder(HttpServletRequest request,
                           ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
