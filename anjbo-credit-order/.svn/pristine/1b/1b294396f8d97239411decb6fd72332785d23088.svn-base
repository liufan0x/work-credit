package com.anjbo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.huanTempService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.DateUtils;

/**
 * 更新放款时间
 * @author admin
 *
 */
@RequestMapping("/credit/order/huanTemp")
@Controller
public class huanTempController extends BaseController{
	Logger log = Logger.getLogger(huanTempController.class);
	@Resource huanTempService huanTempService;
	
	@ResponseBody
	@RequestMapping(value = "/updataHuaanTime") 
	public RespStatus updataHuaanTime(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			List<Map<String, Object>> list = huanTempService.findByAll();
			for(int i=0;i<list.size();i++){
				String orderNo=list.get(i).get("orderNo")+"";
				LendingDto lendingDto=huanTempService.findByLending(orderNo);
				if(lendingDto!=null){
					 log.info("更新华安回款相关数据开始：订单号："+orderNo+"------------");
					 try {
						Date lendingTime=lendingDto.getLendingTime(); //放款时间
						 String huanDay=list.get(i).get("day")+""=="null"?"":list.get(i).get("day")+"";
						 Integer day=Integer.parseInt(huanDay.replace(" ", ""));  //借款天数
						 Date newDate = DateUtils.addDate(lendingTime, (day-1)); //计算预计回款时间
						 SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						 String planPaymentTime = format2.format(newDate);
						 String receivableTime =list.get(i).get("date")+""; //华安回款时间
						 if("".equals(receivableTime) || "null".equals(receivableTime)){
							 log.info("回款时间为空 ！订单： "+orderNo+"不做处理");
							 continue;
						 }
						 Date receivableDate=format2.parse(receivableTime);
						 //---修改订单列表及放款表 -回款时间/预计回款时间--Start-----
						 Map<String, Object> listMap=new HashMap<String, Object>();
						 listMap.put("orderNo", orderNo);
						 listMap.put("planPaymentTime", planPaymentTime);
						 huanTempService.updOrderList(listMap);   //修改订单列表 预计回款时间
						 huanTempService.updFinanceLending(listMap);//修改放款 预计回款时间
						 listMap.put("payMentAmountDate", receivableTime);
						 huanTempService.updFinanceReceivable(listMap);  //修改回款表回款时间
						 //---修改订单列表及放款表 -回款时间/预计回款时间--end-----
						 
						 //---修改回款后流水时间及操作时间Start--------
						  	listMap.put("currentProcessId", null);
						    OrderFlowDto flowDto=huanTempService.findByFlow(listMap);
						    if(flowDto!=null){ //修改回款流水表时间
						    	listMap.put("currentProcessId", flowDto.getCurrentProcessId());
						    	listMap.put("handleTime", receivableTime);
							    huanTempService.updateOrderFlow(listMap);
						    }
						    listMap.put("currentProcessId", "elementReturn");
							OrderFlowDto rflowDto=huanTempService.findByFlow(listMap);
						    //要见退还
//					    DocumentsReturnDto returnDto=huanTempService.finyByReturn(orderNo);
						    if(rflowDto!=null && flowDto!=null){
						    	Date payTime=flowDto.getHandleTime(); //原回款流水时间
						    	Date returnTime=rflowDto.getHandleTime(); //原要件流水
						    	long time=returnTime.getTime()-payTime.getTime();
						    	long paytime=receivableDate.getTime() + time;  //新要件日期
						    	Date receuvavkePayTime=new Date(paytime);
						    	listMap.put("returnTime", receuvavkePayTime);
						    	huanTempService.updateReturn(listMap);
						    	listMap.put("handleTime", receuvavkePayTime);
						    	huanTempService.updateOrderFlow(listMap);
						    }
						 	//付费
						    listMap.put("currentProcessId", "pay");
							OrderFlowDto pflowDto=huanTempService.findByFlow(listMap);
							if(pflowDto!=null && flowDto!=null){
								Date payTime=flowDto.getHandleTime(); //原回款流水时间
						    	Date financePayTime=pflowDto.getHandleTime(); //原付费流水
						    	long time=financePayTime.getTime()-payTime.getTime();
						    	long paytime=receivableDate.getTime() + time;  //新付费日期
						    	Date receuvavkePayTime=new Date(paytime);
						    	listMap.put("payTime", receuvavkePayTime);
						    	huanTempService.updatePay(listMap);
						    	listMap.put("handleTime", receuvavkePayTime);
						    	huanTempService.updateOrderFlow(listMap);
							}
						 	//返佣
							listMap.put("currentProcessId", "rebate");
							OrderFlowDto raflowDto=huanTempService.findByFlow(listMap);
							if(raflowDto!=null && flowDto!=null){
								Date payTime=flowDto.getHandleTime(); //原回款流水时间
						    	Date financePayTime=raflowDto.getHandleTime(); 
						    	long time=financePayTime.getTime()-payTime.getTime();
						    	long paytime=receivableDate.getTime() + time; 
						    	Date receuvavkePayTime=new Date(paytime);
						    	listMap.put("rebateTime", receuvavkePayTime);
						    	huanTempService.updateRebate(listMap);
						    	listMap.put("handleTime", receuvavkePayTime);
						    	huanTempService.updateOrderFlow(listMap);
							}
							//完结
							listMap.put("currentProcessId", "wanjie");
							OrderFlowDto wanjieflowDto=huanTempService.findByFlow(listMap);
							if(wanjieflowDto!=null && flowDto!=null){
								Date payTime=flowDto.getHandleTime(); //原回款流水时间
						    	Date financePayTime=wanjieflowDto.getHandleTime(); 
						    	long time=financePayTime.getTime()-payTime.getTime();
						    	long paytime=receivableDate.getTime() + time; 
						    	Date receuvavkePayTime=new Date(paytime);
//						    	listMap.put("rebateTime", receuvavkePayTime);
//						    	huanTempService.updateReturn(listMap);
						    	listMap.put("handleTime", receuvavkePayTime);
						    	huanTempService.updateOrderFlow(listMap);
							}
							 //---修改回款后流水时间end--------	 			
					} catch (Exception e) {
						log.info("更新华安回款相关数据异常：订单号："+orderNo+"---------"); 
						e.printStackTrace();
					} 
				    log.info("更新华安回款相关数据结束：订单号："+orderNo+"------------"); 	
				}
			}
			
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 修改华融
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updataHuarongTime") 
	public RespStatus updataHuarongTime(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			List<Map<String, Object>> list = huanTempService.findByAllHuarong();
			for(int i=0;i<list.size();i++){
				 String orderNo=list.get(i).get("orderNo")+"";
				 String receivableTime =list.get(i).get("date")+""; //华融回款时间
				 if("".equals(receivableTime) || "null".equals(receivableTime)){
					 log.info("回款时间为空 ！订单： "+orderNo+"不做处理");
					 continue;
				 }
				 //修改回款表回款时间
				 Map<String, Object> listMap=new HashMap<String, Object>();
				 listMap.put("orderNo", orderNo);
				 listMap.put("payMentAmountDate", receivableTime);
				 huanTempService.updFinanceReceivable(listMap);  
				 ReceivableForDto forDto=huanTempService.findByOrderNo(orderNo);
				 if(forDto!=null){
					  SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					  long time=forDto.getCreateTime().getTime()-1000;
					  Date date=new Date(time);
					  OrderFlowDto flowDto=new OrderFlowDto();
					  flowDto.setOrderNo(orderNo);
					  flowDto.setCreateUid(forDto.getCreateUid());
					  flowDto.setUpdateUid(forDto.getCreateUid());
					  flowDto.setHandleUid(forDto.getCreateUid());
					  flowDto.setCurrentProcessId("customerReceivable");
					  flowDto.setNextProcessId("receivableFor");
					  flowDto.setCreateTime(date);
					  flowDto.setUpdateTime(date);
					  flowDto.setHandleTime(date);
					  huanTempService.addFlow(flowDto);
				 }
			}
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 快鸽
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updataKgTime") 
	public RespStatus updataKgTime(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			List<Map<String, Object>> list = huanTempService.findByAll();
			for(int i=0;i<list.size();i++){
				String orderNo=list.get(i).get("orderNo")+"";
				LendingDto lendingDto=huanTempService.findByLending(orderNo);
				if(lendingDto!=null){
					 log.info("更新快鸽回款相关数据开始：订单号："+orderNo+"------------");
					 try {
						Date lendingTime=lendingDto.getLendingTime(); //放款时间
						 String huanDay=list.get(i).get("kgday")+""=="null"?"":list.get(i).get("kgday")+"";
						 Integer day=Integer.parseInt(huanDay.replace(" ", ""));  //借款天数
						 Date newDate = DateUtils.addDate(lendingTime, (day-1)); //计算预计回款时间
						 SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						 String planPaymentTime = format2.format(newDate);
						 String receivableTime =list.get(i).get("kgdate")+""; //快歌回款时间
						 if("".equals(receivableTime) || "null".equals(receivableTime)){
							 log.info("回款时间为空 ！订单： "+orderNo+"不做处理");
							 continue;
						 }
						 Date receivableDate=format2.parse(receivableTime);
						 //---修改订单列表及放款表 -回款时间/预计回款时间--Start-----
						 Map<String, Object> listMap=new HashMap<String, Object>();
						 listMap.put("orderNo", orderNo);
						 listMap.put("planPaymentTime", planPaymentTime);
						 huanTempService.updOrderList(listMap);   //修改订单列表 预计回款时间
						 huanTempService.updFinanceLending(listMap);//修改放款 预计回款时间
						 listMap.put("payMentAmountDate", receivableTime);
						 huanTempService.updFinanceReceivable(listMap);  //修改回款表回款时间
						 //---修改订单列表及放款表 -回款时间/预计回款时间--end-----
						 
						 //---修改回款后流水时间及操作时间Start--------
						  	listMap.put("currentProcessId", null);
						    OrderFlowDto flowDto=huanTempService.findByFlow(listMap);
						    if(flowDto!=null){ //修改回款流水表时间
						    	listMap.put("currentProcessId", flowDto.getCurrentProcessId());
						    	listMap.put("handleTime", receivableTime);
							    huanTempService.updateOrderFlow(listMap);
						    }
						    listMap.put("currentProcessId", "elementReturn");
							OrderFlowDto rflowDto=huanTempService.findByFlow(listMap);
						    //要见退还
//					    DocumentsReturnDto returnDto=huanTempService.finyByReturn(orderNo);
						    if(rflowDto!=null && flowDto!=null){
						    	Date payTime=flowDto.getHandleTime(); //原回款流水时间
						    	Date returnTime=rflowDto.getHandleTime(); //原要件流水
						    	long time=returnTime.getTime()-payTime.getTime();
						    	long paytime=receivableDate.getTime() + time;  //新要件日期
						    	Date receuvavkePayTime=new Date(paytime);
						    	listMap.put("returnTime", receuvavkePayTime);
						    	huanTempService.updateReturn(listMap);
						    	listMap.put("handleTime", receuvavkePayTime);
						    	huanTempService.updateOrderFlow(listMap);
						    }
						 	//付费
						    listMap.put("currentProcessId", "pay");
							OrderFlowDto pflowDto=huanTempService.findByFlow(listMap);
							if(pflowDto!=null && flowDto!=null){
								Date payTime=flowDto.getHandleTime(); //原回款流水时间
						    	Date financePayTime=pflowDto.getHandleTime(); //原付费流水
						    	long time=financePayTime.getTime()-payTime.getTime();
						    	long paytime=receivableDate.getTime() + time;  //新付费日期
						    	Date receuvavkePayTime=new Date(paytime);
						    	listMap.put("payTime", receuvavkePayTime);
						    	huanTempService.updatePay(listMap);
						    	listMap.put("handleTime", receuvavkePayTime);
						    	huanTempService.updateOrderFlow(listMap);
							}
						 	//返佣
							listMap.put("currentProcessId", "rebate");
							OrderFlowDto raflowDto=huanTempService.findByFlow(listMap);
							if(raflowDto!=null && flowDto!=null){
								Date payTime=flowDto.getHandleTime(); //原回款流水时间
						    	Date financePayTime=raflowDto.getHandleTime(); 
						    	long time=financePayTime.getTime()-payTime.getTime();
						    	long paytime=receivableDate.getTime() + time; 
						    	Date receuvavkePayTime=new Date(paytime);
						    	listMap.put("rebateTime", receuvavkePayTime);
						    	huanTempService.updateRebate(listMap);
						    	listMap.put("handleTime", receuvavkePayTime);
						    	huanTempService.updateOrderFlow(listMap);
							} 
							//完结
							listMap.put("currentProcessId", "wanjie");
							OrderFlowDto wanjieflowDto=huanTempService.findByFlow(listMap);
							if(wanjieflowDto!=null && flowDto!=null){
								Date payTime=flowDto.getHandleTime(); //原回款流水时间
						    	Date financePayTime=wanjieflowDto.getHandleTime(); 
						    	long time=financePayTime.getTime()-payTime.getTime();
						    	long paytime=receivableDate.getTime() + time; 
						    	Date receuvavkePayTime=new Date(paytime);
						    	listMap.put("handleTime", receuvavkePayTime);
						    	huanTempService.updateOrderFlow(listMap);
							}
						 //---修改回款后流水时间end--------	 	
					} catch (Exception e) {
						log.info("更新快鸽回款相关数据异常：订单号："+orderNo+"------------");	
						e.printStackTrace();
					}
				    log.info("更新快鸽回款相关数据结束：订单号："+orderNo+"------------");	
				}
			}
			
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	public static void main(String[] args) {
		int min = DateUtil.betweenMinutes(DateUtil.parse("2018-03-22 11:51:13", DateUtil.FMT_TYPE1), DateUtil.parse("2018-04-16 18:21:4", DateUtil.FMT_TYPE1));
		System.out.println(min);
	}
	
	/**
	 * 计算流水表流程处理时间（分钟）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updataFlowTime") 
	public RespStatus updataFlowTime(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			List<String> orderNoAll=huanTempService.selectOrderNoAll(); //获取所有orderNo
			for (String orderNo:orderNoAll) {
				List<OrderFlowDto> flowList=huanTempService.selectOrderFlow(orderNo); //查询流水
				if(flowList!=null &&flowList.size()>1){
					for (int i = 0; i < flowList.size()-1; i++) {
						Date handleTime=flowList.get(i).getHandleTime();
						Date nextHandleTime=flowList.get(i+1).getHandleTime();
						String format = "HH:mm:ss";
					    Date handleshijian = new SimpleDateFormat(format).parse(DateUtil.getDateByFmt(handleTime, format));
					    Date nextHandleshijian = new SimpleDateFormat(format).parse(DateUtil.getDateByFmt(nextHandleTime, format));
					    
					    Date startTime1 = new SimpleDateFormat(format).parse("09:00:00");
				        Date endTime1 = new SimpleDateFormat(format).parse("12:00:00");
				        
				        Date startTime2 = new SimpleDateFormat(format).parse("13:30:00");
				        Date endTime2 = new SimpleDateFormat(format).parse("18:30:00");
				        

				        Date endTime3 = new SimpleDateFormat(format).parse("23:59:59");
				        
				        if(DateUtil.isEffectiveDate(nextHandleshijian, endTime1, startTime2)){
				        	nextHandleTime.setHours(12);
				        	nextHandleTime.setMinutes(0);
				        	nextHandleTime.setSeconds(0);
				        	System.out.println(DateUtil.getNowyyyyMMddHHmmss(handleTime));
				        	//时间设置成 12:00:00
				        	
				        }else if(DateUtil.isEffectiveDate(nextHandleshijian, endTime2, endTime3)){
				        	nextHandleTime.setHours(18);
				        	nextHandleTime.setMinutes(30);
				        	nextHandleTime.setSeconds(0);
				        	//时间设置成 18:30:00
				        	System.out.println(DateUtil.getNowyyyyMMddHHmmss(handleTime));
				        }
				        				        
						int min = DateUtil.betweenMinutes(handleTime, nextHandleTime);
						int day = DateUtil.betDays(handleTime, nextHandleTime);
						int jiejiari = calLeaveDays(handleTime, nextHandleTime); //节假日
						int gongzuori = day - jiejiari;
						double shijian = min - gongzuori*14.5*60 - jiejiari*24*60;
				        
				        if(nextHandleshijian.getTime() - handleshijian.getTime() > 0){
				        	if(DateUtil.isEffectiveDate(handleshijian, startTime1, endTime1) && DateUtil.isEffectiveDate(nextHandleshijian, startTime2, endTime2)){
				        		shijian = shijian - 90;
					        }
				        }
						if(shijian < 0 ){
							shijian = 0;
						}
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("timeNum", shijian);
						map.put("id", flowList.get(i+1).getId());
						huanTempService.updateFlow(map);
					}
				}
			}
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	

	@ResponseBody
	@RequestMapping(value = "/updataFlowCity") 
	public RespStatus updataFlowCity(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			List<String> list=new ArrayList<String>();
			list.add("深圳市");
			list.add("广州市");
			list.add("东莞市");
			list.add("厦门市");
			list.add("郑州市");
			list.add("武汉市");
			list.add("长沙市");
			list.add("上海市");
			list.add("杭州市");
			list.add("南京市");
			list.add("南宁市");
			list.add("重庆市");
			list.add("福州市");
			list.add("惠州市");
			list.add("成都市");
			
			List<String> processsList=new ArrayList<String>();
			processsList.add("managerAudit");
			processsList.add("auditFirst");
			processsList.add("auditFinal");
			processsList.add("auditOfficer");
			processsList.add("allocationFund");
			processsList.add("notarization");
			processsList.add("facesign");
			processsList.add("auditJustice");
			processsList.add("fundDocking");
			processsList.add("repaymentMember");
			processsList.add("element");
			processsList.add("dataAudit");  //资料审核
			processsList.add("applyLoan");
			processsList.add("isLendingHarvest");
			processsList.add("lendingHarvest");
			processsList.add("lending");
			processsList.add("foreclosure");  //结清原贷款
			processsList.add("forensics");   //取证
			processsList.add("cancellation");  //注销
			processsList.add("transfer");  //过户
			processsList.add("newlicense");
			processsList.add("receivableForFirst");
			processsList.add("mortgage");
			processsList.add("receivableForEnd");
			processsList.add("receivableFor");  //回款
			for (String cityName:list) {
				for(String currentProcessId:processsList){
					try {
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("cityName", cityName);
						map.put("currentProcessId", currentProcessId);
						String avg=huanTempService.numTimeCount(map);
						if(null==avg){
							avg="0";
						}
						log.info(cityName+" 在 " +currentProcessId+"的节点平均耗时为："+avg);
					} catch (Exception e) {
						e.printStackTrace();
						log.info(cityName+" 在 "+currentProcessId +"计算异常"+e);
					}
				}
			}
			
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/backOrder") 
	public RespStatus backOrder(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			List<String> list=new ArrayList<String>();
			list.add("深圳市");
			list.add("广州市");
			list.add("东莞市");
			list.add("厦门市");
			list.add("郑州市");
			list.add("武汉市");
			list.add("长沙市");
			list.add("上海市");
			list.add("杭州市");
			list.add("南京市");
			list.add("南宁市");
			list.add("重庆市");
			list.add("福州市");
			list.add("惠州市");
			
			for (String cityName:list) {
				List<String> orderList=huanTempService.selectOrderNoAlls(cityName);
				log.info(cityName+"订单量为"+orderList.size());
				int backOrder=huanTempService.findByBack(cityName);
				log.info(cityName+"退单量为"+backOrder);
			}
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	public int calLeaveDays(Date startTime,Date endTime){
		int leaveDays = 0;
		//从startTime开始循环，若该日期不是节假日或者不是周六日则请假天数+1
		Date flag = startTime;//设置循环开始日期
		Calendar cal = Calendar.getInstance();
		//循环遍历每个日期
		while(flag.compareTo(endTime)!=1){
			 cal.setTime(flag);
			 //判断是否为周六日
			 int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			 if(week == 0 || week == 6){//0为周日，6为周六
				  //跳出循环进入下一个日期
				  cal.add(Calendar.DAY_OF_MONTH, +1);
				  flag = cal.getTime();
				  leaveDays = leaveDays + 1;  //节假日
				  continue;
			 }
			 //判断是否为节假日
			try{
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				boolean istime=isVacations(dateFormat.format(flag));
			    if (istime){
				    //跳出循环进入下一个日期
					cal.add(Calendar.DAY_OF_MONTH, +1);
					flag = cal.getTime();
					leaveDays = leaveDays + 1;
				    continue;
				}
			 }catch (Exception e){
			   e.printStackTrace();
			 }
			 //日期往后加一天
			cal.add(Calendar.DAY_OF_MONTH, +1);
			flag = cal.getTime();
		}
   	   return leaveDays;
	 }
	public boolean  isVacations(String time){
		boolean vacations=false;
		List<String> days=new ArrayList<String>();
		days.add("2016-12-31");
		days.add("2017-01-01");
		days.add("2017-01-02");
		
		days.add("2017-01-30");
		days.add("2017-01-31");
		days.add("2017-02-01");
		
		days.add("2017-04-02");
		
		days.add("2017-05-01");
		
		days.add("2017-05-30");
		
		days.add("2017-10-03");
		days.add("2017-10-04");
		days.add("2017-10-05");
		days.add("2017-10-06");
		
		days.add("2018-01-01");

		days.add("2018-02-19");
		days.add("2018-02-20");
		days.add("2018-02-21");
		
		days.add("2018-04-05");
		
		days.add("2018-05-01");
		
		days.add("2018-06-18");
		
		days.add("2018-09-24");
		for(String s:days){
			if(s.equals(time)){
				vacations=true;
			}
		}
		return vacations;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/orderBackReason") 
	public RespStatus orderBackReason(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			List<String> list=new ArrayList<String>();
			list.add("深圳市");
			list.add("广州市");
			list.add("东莞市");
			list.add("厦门市");
			list.add("郑州市");
			list.add("武汉市");
			list.add("长沙市");
			list.add("上海市");
			list.add("杭州市");
			list.add("南京市");
			list.add("南宁市");
			list.add("重庆市");
			list.add("福州市");
			list.add("惠州市");
//			sql= SELECT b.cityName,b.productName,b.customerName,a.createTime,u.`name`,b.acceptMemberName,b.branchCompany,a.returnType,a.backReason FROM `anjbo-server-uaa`.tbl_order_flow a 
//			LEFT JOIN `anjbo-server-uaa`.tbl_order_list_base b on b.orderNo=a.orderNo
//			LEFT JOIN `anjbo-server-uaa`.tbl_user_base1 u on u.uid=a.createUid
//			WHERE  (a.backReason != '' or a.backReason != null) and b.cityName in ('深圳市','广州市','东莞市','厦门市','郑州市','武汉市','长沙市','上海市','杭州市','南京市','南宁市','重庆市','福州市','惠州市')
//			order BY b.cityName desc;
			for (String cityName:list) {
				List<Map<String, Object>> backOrder=huanTempService.fingByBackList(cityName);
				for (Map m:backOrder) {
					log.info(" cssssss "+cityName+"---"+m.get("productName")+"---"+m.get("customerName")+"---"+m.get("createTime")+"---"+m.get("backReason")+"---"+m.get("name")+
							"---"+m.get("acceptMemberName")+"---"+m.get("branchCompany"));
				}
			}
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	
	/**
	 * 计算流水表流程处理时间（分钟）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updataFlowTimeByAll") 
	public RespStatus updataFlowTimeByAll(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			List<String> orderNoAll=huanTempService.selectOrderNoAll2(); //获取所有orderNo
			log.info("---开始批量处理流水审批时间----");
			for (String orderNo:orderNoAll) {
				List<OrderFlowDto> flowList=huanTempService.selectOrderFlow2(orderNo); //查询流水
				if(flowList!=null &&flowList.size()>1){
					for (int i = 0; i < flowList.size()-1; i++) {
						Date handleTime=flowList.get(i).getHandleTime();  //上一个时间  第一为提单时间
						Date nextHandleTime=flowList.get(i+1).getHandleTime(); // 下个时间  也就是计算时间
						String format = "HH:mm:ss";
					    Date handleshijian = new SimpleDateFormat(format).parse(DateUtil.getDateByFmt(handleTime, format));
					    Date nextHandleshijian = new SimpleDateFormat(format).parse(DateUtil.getDateByFmt(nextHandleTime, format));
					    
					    Date startTime1 = new SimpleDateFormat(format).parse("09:00:00");
				        Date endTime1 = new SimpleDateFormat(format).parse("12:00:00");
				        
				        Date startTime2 = new SimpleDateFormat(format).parse("13:30:00");
				        Date endTime2 = new SimpleDateFormat(format).parse("18:30:00");
				        

				        Date endTime3 = new SimpleDateFormat(format).parse("23:59:59");
				        
				        if(DateUtil.isEffectiveDate(nextHandleshijian, endTime1, startTime2)){
				        	nextHandleTime.setHours(12);
				        	nextHandleTime.setMinutes(0);
				        	nextHandleTime.setSeconds(0);
				        	//时间设置成 12:00:00
				        	
				        }else if(DateUtil.isEffectiveDate(nextHandleshijian, endTime2, endTime3)){
				        	nextHandleTime.setHours(18);
				        	nextHandleTime.setMinutes(30);
				        	nextHandleTime.setSeconds(0);
				        	//时间设置成 18:30:00
				        }
				        				        
						int min = DateUtil.betweenMinutes(handleTime, nextHandleTime);
						int day = DateUtil.betDays(handleTime, nextHandleTime);
						int jiejiari = calLeaveDays(handleTime, nextHandleTime); //节假日
						int gongzuori = day - jiejiari;
						double shijian = min - gongzuori*14.5*60 - jiejiari*24*60;
				        
				        if(nextHandleshijian.getTime() - handleshijian.getTime() > 0){
				        	if(DateUtil.isEffectiveDate(handleshijian, startTime1, endTime1) && DateUtil.isEffectiveDate(nextHandleshijian, startTime2, endTime2)){
				        		shijian = shijian - 90;
					        }
				        }
						if(shijian < 0 ){
							shijian = 0;
						}
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("timeNum", shijian);
						map.put("id", flowList.get(i+1).getId());
						int j = huanTempService.updateFlow2(map);
						if(orderNo.equals("2018060620401000213")){
							log.info("---:"+j);
							log.info("---:"+map);
						}
					}
				}
			}
			log.info("---结束批量处理流水审批时间------");
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp,RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
}
