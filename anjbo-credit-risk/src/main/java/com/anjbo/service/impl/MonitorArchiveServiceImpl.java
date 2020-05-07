package com.anjbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.axis.wsdl.symbolTable.ContainedAttribute;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.risk.MonitorArchiveDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.dao.MonitorArchiveMapper;
import com.anjbo.service.MonitorArchiveService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
@Service
@Transactional
public class MonitorArchiveServiceImpl implements MonitorArchiveService{
	Logger log = Logger.getLogger(MonitorArchiveServiceImpl.class);
	@Resource
	private MonitorArchiveMapper monitorArchiveMapper; 
	@Override
	public int insertMonitorArchive(MonitorArchiveDto monitorArchiveDto) {
		return monitorArchiveMapper.insertMonitorArchive(monitorArchiveDto);
	}
	@Override
	public List<MonitorArchiveDto> selectArchiveList(
			MonitorArchiveDto archiveDto) {
		return monitorArchiveMapper.selectArchiveList(archiveDto);
	}
	@Override
	public int selectArchiveCount(MonitorArchiveDto archiveDto) {
		return monitorArchiveMapper.selectArchiveCount(archiveDto);
	}
	@Override
	public MonitorArchiveDto findBymonitor(MonitorArchiveDto archiveDto) {
		return monitorArchiveMapper.findBymonitor(archiveDto);
	}
	@Transactional
	@Override
	public Map<String, Object> getArchiveId(Map<String, Object> params,MonitorArchiveDto monitorDao) {
		Map<String, Object> data = new HashMap<String, Object>();
		String toolsUrl = ConfigUtil.getStringValue(Constants.LINK_ANJBO_TOOl_URL,ConfigUtil.CONFIG_LINK);
		toolsUrl += "/tools/archive/v/addArchive";
		String dataMsg="";
		try {
			params.put("noLimitCountHour",true);
			params.put("noLimitCount", true);
			String statusString = HttpUtil.jsonPost(toolsUrl, params);
			String archiveId=null;
			JSONObject jsonObject = JSONObject.fromObject(statusString);
			if("相同房号请在记录详情再次查询".equals(jsonObject.getString("msg"))){
				archiveId =jsonObject.getJSONObject("data").getString("archiveId");
				//通过查档id查档
				toolsUrl = ConfigUtil.getStringValue(Constants.LINK_ANJBO_TOOl_URL,ConfigUtil.CONFIG_LINK)+ "/tools/archive/v/getArchiveById";
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("noLimitCountHour",true);
				param.put("noLimitCount", true);
				param.put("id", archiveId);
				String result = HttpUtil.jsonPost(toolsUrl, param);
				JSONObject jsonObj = JSONObject.fromObject(result);
				 data = new HashMap<String,Object>();
				if(jsonObj.containsKey("data")&&null!=jsonObj.get("data")&&!jsonObj.get("code").equals("FAIL")){
					JSONObject tmp = jsonObj.getJSONObject("data");
					dataMsg=String.valueOf(tmp.getString("message"));
				}
			}else if(!"相同房号请在记录详情再次查询".equals(jsonObject.getString("msg"))&&"SUCCESS".equals(jsonObject.getString("code"))||
					"SAME_ROOM_NUMBER".equals(jsonObject.getString("code"))||
					"SYSTEM_UPDATE".equals(jsonObject.getString("code"))){
				archiveId =jsonObject.getJSONObject("data").getString("archiveId");
				data.put("archiveId", archiveId);
				if(jsonObject.getString("msg")!=""){
					dataMsg = jsonObject.getString("msg");
				}
				data.put("dataMsg", dataMsg);
				if("SUCCESS".equals(jsonObject.getString("code"))){
					dataMsg = jsonObject.getJSONObject("data").getString("msg");
					data.put("dataMsg", dataMsg);
				}
			}else{
				data.put("code", "SUCCESS");
				data.put("msg","没有找到匹配的房产记录，添加监控失败");
				return data;
			}
			//校验dataMsg
			if(dataMsg.contains(Enums.PropertyStatusEnum.L1.getName())){
				monitorDao.setPropertyStatus(Enums.PropertyStatusEnum.L1.getName());
			}else if(dataMsg.contains(Enums.PropertyStatusEnum.L2.getName())){
				monitorDao.setPropertyStatus(Enums.PropertyStatusEnum.L2.getName());
			}else if(dataMsg.contains(Enums.PropertyStatusEnum.L3.getName())){
				monitorDao.setPropertyStatus(Enums.PropertyStatusEnum.L3.getName());
			}else if(dataMsg.contains(Enums.PropertyStatusEnum.L4.getName())){
				monitorDao.setPropertyStatus(Enums.PropertyStatusEnum.L4.getName());
			}else if(dataMsg.contains(Enums.PropertyStatusEnum.L5.getName())){
				monitorDao.setPropertyStatus(Enums.PropertyStatusEnum.L5.getName());
			}else{
				monitorDao.setPropertyStatus(Enums.PropertyStatusEnum.L6.getName());
			}
			monitorDao.setArchiveId(archiveId);
			//加查档时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String date=sdf.format(new Date());
			monitorDao.setMessage(date+"&"+dataMsg);
			int i=monitorArchiveMapper.insertMonitorArchive(monitorDao);
			
			//发短信给指定的人
			String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
			if(dataMsg.contains(Enums.PropertyStatusEnum.L1.getName())){
				String[] phones = ConfigUtil.getStringValue(Constants.BASE_MONITOR_PHONES,ConfigUtil.CONFIG_BASE).split(",");
				String content = "快鸽赎 楼系统监测的房 产（房 产证号："+monitorDao.getEstateNo()+"，姓名/身份证号："+monitorDao.getIdentityNo()+"），房 产状态为"+Enums.PropertyStatusEnum.L1.getName()+"，请知悉。";
				for (String phone : phones) {
					try {
						AmsUtil.smsSend(phone, ipWhite,content , Constants.SMS_FC_ORDER);
					} catch (Exception e) {
						e.printStackTrace();
						log.error("发送短信失败："+content);
					}
				}
			}else if(dataMsg.contains(Enums.PropertyStatusEnum.L3.getName())){
				String[] phones = ConfigUtil.getStringValue(Constants.BASE_MONITOR_PHONES,ConfigUtil.CONFIG_BASE).split(",");
				String content = "快鸽赎 楼系统监测的房 产（房 产证号："+monitorDao.getEstateNo()+"，姓名/身份证号："+monitorDao.getIdentityNo()+"），房 产状态为"+Enums.PropertyStatusEnum.L3.getName()+"，请知悉。";
				for (String phone : phones) {
					try {
						AmsUtil.smsSend(phone, ipWhite,content , Constants.SMS_FC_ORDER);
					} catch (Exception e) {
						e.printStackTrace();
						log.error("发送短信失败："+content);
					}
				}
			}
			
			data.put("code", "SUCCESS");
			data.put("msg","添加成功");
			if(i>1){
				data.put("msg","同一个房产监测已经被添加");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(toolsUrl + "远程调用失败");
		}
		return data;
	}
	@Override
	public int deleteMonitorArchiveById(MonitorArchiveDto monitorArchiveDto) {
		return monitorArchiveMapper.deleteMonitorArchiveById(monitorArchiveDto);
	}
	@Override
	public int updateMonitorArchive(MonitorArchiveDto monitorArchiveDto) {
		// TODO Auto-generated method stub
		return monitorArchiveMapper.updateMonitorArchive(monitorArchiveDto);
	}

}
