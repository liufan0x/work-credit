package com.anjbo.task.fc.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.anjbo.bean.fc.monitor.MonitorArchiveDto;
import com.anjbo.common.AnjboException;
import com.anjbo.common.ApplicationContextHolder;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.fc.monitor.MonitorArchiveService;
import com.anjbo.service.fc.order.OrderService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
@Component
public class MonitorTask {  
	Logger Log=Logger.getLogger(MonitorTask.class);
	/** 创建人uid */
	private String createUid;
	/**任务描述*/
    private String msg;  
    /**查档Id*/
    private String archiveId;
    /**手机号*/
    private String phone;
    /**房产证号**/
	private String estateNo;
	/**身份证号**/
	private String identityNo;
	/** 查档类型 */
	private int type;
	/**产权证书类型（0房地产权证书 1不动产权证书）*/
	private int estateType;
    //定时查档
    public void run(){
    		Log.info("查档id为："+archiveId);
    		ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
			MonitorArchiveService monitorArchiveService = (MonitorArchiveService) applicationContext.getBean("monitorArchiveService");
			OrderService orderService = (OrderService)applicationContext.getBean("orderService");
			//查档
			MonitorArchiveDto archiveDto = new MonitorArchiveDto(); 
			archiveDto.setArchiveId(archiveId);
			MonitorArchiveDto oldArchiveDto=monitorArchiveService.selectArchiveByArchiveId(archiveDto);
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("estateNo", oldArchiveDto.getEstateNo());
			param.put("estateType", oldArchiveDto.getEstateType());
			param.put("identityNo", oldArchiveDto.getIdentityNo());
			param.put("type", oldArchiveDto.getType());
			param.put("yearNo", oldArchiveDto.getYearNo());
			msg = queryArchive(param);
			Log.info("查档返回信息:"+msg);
			Map<String,Object> data = new HashMap<String,Object>();
			if(StringUtil.isNotBlank(msg)&&!"null".equals(msg)){
				//有变更则更新查档记录
				String dataMsg=msg;
				if(null==oldArchiveDto){
					return;
				}
					MonitorArchiveDto newArchiveDto = new MonitorArchiveDto(); 
					//校验dataMsg
					if(dataMsg.contains(Enums.PropertyStatusEnum.L1.getName())){
						newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L1.getName());
					}else if(dataMsg.contains(Enums.PropertyStatusEnum.L2.getName())){
						newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L2.getName());
					}else if(dataMsg.contains(Enums.PropertyStatusEnum.L3.getName())){
						newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L3.getName());
					}else if(dataMsg.contains(Enums.PropertyStatusEnum.L4.getName())){
						newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L4.getName());
					}else if(dataMsg.contains(Enums.PropertyStatusEnum.L5.getName())){
						newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L5.getName());
					}else{
						newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L6.getName());
						MonitorArchiveDto archive = new MonitorArchiveDto(); 
						archive.setArchiveId(archiveId);
						monitorArchiveService.updateMonitorArchive(archive);
						return;
					}
					
					//查询客户姓名
					String name ="";
					if(StringUtils.isNotBlank(oldArchiveDto.getOrderNo())){
						Map<String,Object> m = orderService.selectCustomer(oldArchiveDto.getOrderNo());
						if(m!=null&&m.get("customerName")!=null){
							name = MapUtils.getString(m, "customerName");
						}
					}
					
//					if(null!=oldArchiveDto.getPropertyStatus()&&!oldArchiveDto.getPropertyStatus().equals(newArchiveDto.getPropertyStatus())){
					if(null!=oldArchiveDto.getPropertyStatus()&&!oldArchiveDto.getPropertyStatus().equals(newArchiveDto.getPropertyStatus()) &&  "查封".equals(newArchiveDto.getPropertyStatus())){
						Log.info("执行作业:"+msg+"查档id:"+archiveId+"结果由"+oldArchiveDto.getPropertyStatus()+"变更为"+newArchiveDto.getPropertyStatus());
						//加查档时间
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						String date=sdf.format(new Date());
						newArchiveDto.setArchiveId(archiveId);
						newArchiveDto.setMessage(oldArchiveDto.getMessage()+";"+date+"&"+dataMsg);
						if(null==oldArchiveDto.getChangeRecord()){
							newArchiveDto.setChangeRecord(oldArchiveDto.getPropertyStatus()+"→"+newArchiveDto.getPropertyStatus());
						}else{
							newArchiveDto.setChangeRecord(oldArchiveDto.getChangeRecord()+"→"+newArchiveDto.getPropertyStatus());
						}
						monitorArchiveService.updateMonitorArchive(newArchiveDto);
						//发短信
						String ipWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString());
						Log.info("房产监测短信ipWhite:"+ipWhite);
						String content = "你在快鸽信贷系统监测的房产（房 产证号："+estateNo+"，姓名/身份证号："+identityNo;
						if(StringUtils.isNotBlank(name)){
							content+="，订单客户姓名："+name;
						}
						content += "），房 产状态已从"+oldArchiveDto.getPropertyStatus()+"变为"+newArchiveDto.getPropertyStatus()+"，请知悉。";
						try {
							AmsUtil.smsSend(phone, ipWhite,content , Constants.SMSCOMEFROM_TEST);
						} catch (AnjboException e) {
							e.printStackTrace();
							Log.error("发送短信失败："+content);
						}
						//发短信给指定的人
						if(newArchiveDto.getPropertyStatus().equals("查封") || newArchiveDto.getPropertyStatus().equals("抵押查封")){
							String[] phones = ConfigUtil.getStringValue("MONITOR_PHONES").split(",");
							content = "快鸽信贷系统监测的房 产（房 产证号："+estateNo+"，姓名/身份证号："+identityNo;
							if(StringUtils.isNotBlank(name)){
								content+="，订单客户姓名："+name;
							}
							content += "），房 产状态已从"+oldArchiveDto.getPropertyStatus()+"变为"+newArchiveDto.getPropertyStatus()+"，请知悉。";
							for (String phone : phones) {
								try {
									AmsUtil.smsSend(phone, ipWhite,content , Constants.SMSCOMEFROM_TEST);
								} catch (AnjboException e) {
									e.printStackTrace();
									Log.error("发送短信失败："+content);
								}
							}
						}
					}else{
						Log.info("查档id:"+archiveId+"结果未变更");
						MonitorArchiveDto archive = new MonitorArchiveDto(); 
						archive.setArchiveId(archiveId);
						monitorArchiveService.updateMonitorArchive(archive);
					}
			}else{
				MonitorArchiveDto archive = new MonitorArchiveDto(); 
				archive.setArchiveId(archiveId);
				monitorArchiveService.updateMonitorArchive(archive);
			}
			
    }
    
    /**
     * 循环查档6次，出结果为止
     * @param param
     * @return
     */
    public String queryArchive(Map<String,Object> param) {
    	String msg ="";
    	for(int i=0;i<6;i++) {
    		RespDataObject<Map<String, Object>> resp = HttpUtil.getRespDataObject(ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.CREDIT_URL.toString()), "/credit/risk/archive/getArchive", param, Map.class);
    		Log.info(resp);
    		if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
    			msg = MapUtils.getString(resp.getData(), "message");
    		}
    		Log.info("查档返回===="+identityNo+"====:"+i+msg);
    		
    		if(StringUtil.isNotBlank(msg)&&!"null".equals(msg)) {
    			break;
    		}
    	}
    	return msg;
    }
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getArchiveId() {
		return archiveId;
	}
	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}
	public MonitorTask() {
		super();
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone() {
		return phone;
	}
	public String getEstateNo() {
		return estateNo;
	}
	public void setEstateNo(String estateNo) {
		this.estateNo = estateNo;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getEstateType() {
		return estateType;
	}
	public void setEstateType(int estateType) {
		this.estateType = estateType;
	}
	public MonitorTask(String createUid,String msg, String archiveId, String phone,
			String estateNo, String identityNo,int type,int estateType) {
		super();
		this.createUid = createUid;
		this.msg = msg;
		this.archiveId = archiveId;
		this.phone = phone;
		this.estateNo = estateNo;
		this.identityNo = identityNo;
		this.type = type;
		this.estateType = estateType;
	}
	
}  
