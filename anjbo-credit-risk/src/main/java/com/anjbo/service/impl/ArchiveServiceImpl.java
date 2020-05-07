package com.anjbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.risk.ArchiveDto;
import com.anjbo.bean.risk.MonitorArchiveDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.Enums.PropertyStatusEnum;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.EnquiryController;
import com.anjbo.dao.ArchiveMapper;
import com.anjbo.dao.MonitorArchiveMapper;
import com.anjbo.dao.RiskBaseMapper;
import com.anjbo.service.ArchiveService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;

/**
 * 查档
 * @author huanglj
 *
 */
@Transactional
@Service
public class ArchiveServiceImpl implements ArchiveService{

	@Resource
	private ArchiveMapper archiveMapper;
	@Resource
	private  RiskBaseMapper riskBaseMapper;
	
	@Resource
	private MonitorArchiveMapper monitorArchiveMapper;
	
	public ArchiveDto detail(String archiveId){
		ArchiveDto obj = archiveMapper.detail(archiveId);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderNo", obj.getOrderNo());
		param.put("imgType", "archive");
		List<Map<String,Object>> list = riskBaseMapper.listImg(param);
		obj.setArchiveImgs(list);
		return obj;
	}
	
	public List<ArchiveDto> listArchive(String orderNo){
		List<ArchiveDto> list = archiveMapper.listArchive(orderNo);
		if(null==list||list.size()<=0){
			list = new ArrayList();
			ArchiveDto tmp = new ArchiveDto();
			tmp.setOrderNo(orderNo);
			list.add(tmp);
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderNo", orderNo);
		param.put("imgType", "archive");
		List<Map<String,Object>> imgs = riskBaseMapper.listImg(param);

		for(ArchiveDto obj:list){
			obj.setArchiveImgs(imgs);
		}
		return list;
	}
	
	public int insert(ArchiveDto obj){
		return archiveMapper.insert(obj);
	}
	
	public int insert(Map<String,Object> map){
		return archiveMapper.insertByMap(map);
	}
	
	public int update(ArchiveDto obj){
		int success = 0;
		ArchiveDto tmp = archiveMapper.selectByOrderNoAndArchiveId(obj);
		if(null==tmp){
			archiveMapper.insert(obj);
		} else {
			archiveMapper.update(obj);
		}
		return success;
	}
	
	public int update(Map<String,Object> map){
		int success = 0;
		ArchiveDto tmp = archiveMapper.selectArchiveByOrderNoAndArchiveId(map);
		if(null==tmp){
			success = archiveMapper.insertByMap(map);
		} else {
			tmp.setArchiveId(MapUtils.getString(map, "archiveId"));
			tmp.setMessage(MapUtils.getString(map, "message",""));
			success = archiveMapper.update(tmp);
		}
		return success;
	}
	
	public int updateByArchiveId(ArchiveDto obj){
		return archiveMapper.updateByArchiveId(obj);
	}
	public int getArchiveId(RespDataObject<Map<String,Object>> result,Map<String,Object> params) {
		int success = 0;
		String orderNo = MapUtils.getString(params, "orderNo");
		String archiveId=null;
		
		if(RespStatusEnum.SUCCESS.getCode().equals(result.getCode())||
				"SAME_ROOM_NUMBER".equals(result.getCode())||
				"SYSTEM_UPDATE".equals(result.getCode())){
			Map<String,Object> tmp  = result.getData();
			archiveId = MapUtils.getString(params, "id", "");
//			if(!params.containsKey("archiveId")){
//				params.put("archiveId", archiveId);
//			}
			String dataMsg = MapUtils.getString(tmp, "message", "");
			ArchiveDto archive = new ArchiveDto();
			archive.setOrderNo(orderNo);
			archive.setArchiveId(archiveId);
			archive.setCreateUid(MapUtils.getString(params, "createUid",""));
			archive.setMessage(dataMsg);
			if("0".equals(MapUtils.getString(params, "estateType"))){
				archive.setEstateType("0");
			}else{
				archive.setEstateType(MapUtils.getString(params, "yearNo",""));
			}
			archive.setEstateNo(MapUtils.getString(params, "estateNo", ""));
			archive.setIdentityNo(MapUtils.getString(params, "identityNo", ""));
			insertSelective(archive,params);
			
			if(!tmp.containsKey("createTime")){
				tmp.put("createTime", new Date());
			}
			tmp.put("dataMsg", dataMsg);
			tmp.put("code",result.getCode());
			tmp.put("msg",result.getMsg());
			insertMonitorArchive(tmp,params);
		}
		return success;
	}
	
	@Transactional
	public int insertSelective(ArchiveDto dto,Map<String,Object> map){
//		ArchiveDto tmp = archiveMapper.selectArchiveByOrderNoAndArchiveId(map);
		int success = 0;
//		if(null==tmp){
			success = archiveMapper.insert(dto);
//		}
//		else{
//			tmp.setArchiveId(dto.getArchiveId());
//			tmp.setEstateType(dto.getEstateType());
//			tmp.setEstateNo(dto.getEstateNo());
//			tmp.setIdentityNo(dto.getIdentityNo());
//			if(StringUtils.isNotBlank(dto.getMessage())){tmp.setMessage(dto.getMessage());}
//			success = archiveMapper.update(tmp);
//		}
		return success;
	}
	@Transactional
	public int insertMonitorArchive(Map<String,Object> map,Map<String,Object> params){
		int success = 0;
		if("SAME_ROOM_NUMBER".equals(MapUtils.getString(map, "code"))){
			return success;
		}
		MonitorArchiveDto monitorArchiveDto = new MonitorArchiveDto();
		monitorArchiveDto.setArchiveId(MapUtils.getString(map, "archiveId",""));
		MonitorArchiveDto archiveTmp = monitorArchiveMapper.selectArchiveByArchiveId(monitorArchiveDto);
		if(null==archiveTmp){
			monitorArchiveDto.setOrderNo(MapUtils.getString(params, "orderNo"));
			monitorArchiveDto.setArchiveId(MapUtils.getString(map, "archiveId"));
			monitorArchiveDto.setType(MapUtils.getInteger(params, "type"));
			monitorArchiveDto.setEstateType(MapUtils.getInteger(params, "estateType"));
			monitorArchiveDto.setIdentityNo(MapUtils.getString(params, "identityNo"));
			monitorArchiveDto.setEstateNo(MapUtils.getString(params, "estateNo"));
			monitorArchiveDto.setCreateUid(MapUtils.getString(params, "createUid"));
			monitorArchiveDto.setYearNo(MapUtils.getString(params, "yearNo"));
			monitorArchiveDto.setAgencyId(MapUtils.getIntValue(params, "agencyId"));
			monitorArchiveDto.setCreateTime(new Date());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String startTime = format.format(new Date());
			if(StringUtils.isBlank(MapUtils.getString(params, "startTime",""))){
				monitorArchiveDto.setStartTime(startTime);
			}else{
				monitorArchiveDto.setStartTime(MapUtils.getString(params, "startTime",""));
			}
			String msg = MapUtils.getString(map, "dataMsg");
			String propertyStatus = propertyStatus(msg,params);
			if(propertyStatus.equals(PropertyStatusEnum.L6.getName())){
				msg = propertyStatus;
			}
			monitorArchiveDto.setPropertyStatus(propertyStatus);
			monitorArchiveDto.setMessage(startTime+"&  "+msg);
			Calendar now =Calendar.getInstance(); 
			now.setTime(new Date());
			now.set(Calendar.DATE,now.get(Calendar.DATE)+30);
			if(StringUtils.isBlank(MapUtils.getString(params, "endTime",""))){
				monitorArchiveDto.setEndTime(format.format(now.getTime()));
			}else{
				monitorArchiveDto.setEndTime(MapUtils.getString(params, "endTime",""));
			}
			String productCode = MapUtils.getString(params, "productCode")!=null?MapUtils.getString(params, "productCode"):"02";
			if(StringUtils.isBlank(MapUtils.getString(params, "queryUsage",""))){
				monitorArchiveDto.setQueryUsage(productCode);
			}else{
				monitorArchiveDto.setQueryUsage(MapUtils.getString(params, "queryUsage",""));
			}
			String phone = ConfigUtil.getStringValue(Constants.BASE_HOUSE_MONITOR_INIT_PHONE,ConfigUtil.CONFIG_BASE);
			Integer num = ConfigUtil.getIntegerValue(Constants.BASE_HOUSE_MONITOR_INIT_FREQUENCY,ConfigUtil.CONFIG_BASE);
			if(StringUtils.isBlank(MapUtils.getString(params, "phone",""))){
				monitorArchiveDto.setPhone(phone);
			}else{
				monitorArchiveDto.setPhone(MapUtils.getString(params, "phone",""));
			}
			if(StringUtils.isBlank(MapUtils.getString(params, "queryFrequency",""))){
				monitorArchiveDto.setQueryFrequency(num);
			}else{
				monitorArchiveDto.setQueryFrequency(MapUtils.getInteger(params, "queryFrequency",3));
			}
			success = monitorArchiveMapper.insertMonitorArchive(monitorArchiveDto);
		} else if(archiveTmp.getPropertyStatus().equals(PropertyStatusEnum.L6.getName())){
			String msg = MapUtils.getString(map, "dataMsg", "");
			if(StringUtils.isNotBlank(msg)&&!archiveTmp.getPropertyStatus().equals(msg)){
				String propertyStatus = propertyStatus(msg,params);
				String message = archiveTmp.getMessage();
				message = message.replace(archiveTmp.getPropertyStatus(), msg);
				archiveTmp.setMessage(message);
				archiveTmp.setPropertyStatus(propertyStatus);
				success = monitorArchiveMapper.updateMonitorArchive(archiveTmp);
			}
		}
		return success;
	}
	
	public String propertyStatus(String str,Map<String,Object> params){
		String msg = "";
		String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
		if(StringUtils.isNotBlank(str)&&str.indexOf(PropertyStatusEnum.L1.getName())!=-1){
			msg = PropertyStatusEnum.L1.getName();
			
			String acceptContext = "快鸽赎 楼系统监测的房 产（房 产证号："
				+MapUtils.getString(params, "estateNo", "")
				+"，姓名/身份证号："
				+MapUtils.getString(params, "identityNo", "")+"），房 产状态为"
				+Enums.PropertyStatusEnum.L1.getName()+"，请知悉。";
			//AmsUtil.smsSend(ConfigUtil.getStringValue("MONITOR_PHONES"), ipWhite,acceptContext, Constants.SMSCOMEFROM_FC_ORDER);
			 AmsUtil.smsSend(ConfigUtil.getStringValue(Constants.BASE_MONITOR_PHONES,ConfigUtil.CONFIG_BASE), ipWhite,Constants.SMS_ARCHIVE,MapUtils.getString(params, "estateNo", ""),MapUtils.getString(params, "identityNo", ""),Enums.PropertyStatusEnum.L1.getName());
		} else if(StringUtils.isNotBlank(str)&&str.indexOf(PropertyStatusEnum.L2.getName())!=-1){
			msg = PropertyStatusEnum.L2.getName();
		} else if(StringUtils.isNotBlank(str)&&str.indexOf(PropertyStatusEnum.L3.getName())!=-1){
			msg = PropertyStatusEnum.L3.getName();
			
		    String acceptContext = "快鸽赎 楼系统监测的房 产（房 产证号："
								+MapUtils.getString(params, "estateNo", "")
								+"，姓名/身份证号："
								+MapUtils.getString(params, "identityNo", "")+"），房 产状态为"
								+Enums.PropertyStatusEnum.L3.getName()+"，请知悉。";
		    
		    //AmsUtil.smsSend(ConfigUtil.getStringValue("MONITOR_PHONES"), ipWhite,acceptContext, Constants.SMSCOMEFROM_FC_ORDER);
		    AmsUtil.smsSend(ConfigUtil.getStringValue(Constants.BASE_MONITOR_PHONES,ConfigUtil.CONFIG_BASE), ipWhite,Constants.SMS_ARCHIVE,MapUtils.getString(params, "estateNo", ""),MapUtils.getString(params, "identityNo", ""),Enums.PropertyStatusEnum.L3.getName());
			
		} else if(StringUtils.isNotBlank(str)&&str.indexOf(PropertyStatusEnum.L4.getName())!=-1){
			msg = PropertyStatusEnum.L4.getName();
		} else if(StringUtils.isNotBlank(str)&&str.indexOf(PropertyStatusEnum.L5.getName())!=-1){
			msg = PropertyStatusEnum.L5.getName();
		} else{
			msg = PropertyStatusEnum.L6.getName();
		}
		return msg;
	}
	public int update(RespDataObject<Map<String,Object>> result,Map<String,Object> param){
		int success = 0;
		
		Map<String,Object> tmp = result.getData();
		EnquiryController.nullToString(tmp);
		param.put("message",MapUtils.getString(tmp, "message", ""));
		param.put("identityNo",MapUtils.getString(tmp, "identityNo"));
		param.put("estateNo",MapUtils.getString(tmp, "estateNo"));
		param.put("dataMsg", MapUtils.getString(tmp, "message"));
		param.put("code", MapUtils.getString(tmp, "code"));
		param.put("estateType",MapUtils.getInteger(tmp, "estateType"));
		param.put("yearNo", MapUtils.getString(tmp, "yearNo"));
		param.put("type", MapUtils.getString(tmp, "type"));
		param.put("archiveId", MapUtils.getString(tmp, "id"));
		update(param);
		
		insertMonitorArchive(param, param);
		result.setData(tmp);
		return success;
	}
	
	public int updateByOrderNo(ArchiveDto obj){
		List<ArchiveDto> list = archiveMapper.detailByOrderNo(obj.getOrderNo());
		if(null==list||list.size()<=0){
			return archiveMapper.insert(obj);
		}
		return archiveMapper.updateByOrderNo(obj);
	}

	public int delete(ArchiveDto obj){
		return archiveMapper.delete(obj);
	}

	public List<ArchiveDto> detailByOrderNo(String orderNo){
		return archiveMapper.detailByOrderNo(orderNo);
	}
}
