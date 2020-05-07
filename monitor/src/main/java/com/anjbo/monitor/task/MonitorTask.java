package com.anjbo.monitor.task;

import com.anjbo.core.RMqClientConnect;
import com.anjbo.execute.ExecuteData;
import com.anjbo.execute.ExecuteQueue;
import com.anjbo.monitor.common.AnjboException;
import com.anjbo.monitor.common.Enums;
import com.anjbo.monitor.common.Enums.GLOBAL_CONFIG_KEY;
import com.anjbo.monitor.common.Enums.PropertyStatusEnum;
import com.anjbo.monitor.common.RespDataObject;
import com.anjbo.monitor.common.RespStatusEnum;
import com.anjbo.monitor.entity.MonitorArchiveDto;
import com.anjbo.monitor.service.MonitorArchiveService;
import com.anjbo.monitor.util.AmsUtil;
import com.anjbo.monitor.util.ConfigUtil;
import com.anjbo.monitor.util.DateUtil;
import com.anjbo.monitor.util.MD5Utils;
import com.anjbo.monitor.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class MonitorTask
{
  private static final Logger log = LoggerFactory.getLogger(MonitorTask.class);

  @Autowired
  private MonitorArchiveService monitorArchiveService;

  @Scheduled(cron="0 0 10,12,15,18 * * ?")
  public void run() { SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    MonitorArchiveDto archiveDto = new MonitorArchiveDto();
    List<MonitorArchiveDto> archiveDtos = this.monitorArchiveService.selectArchiveListAll(archiveDto);
    for (MonitorArchiveDto monitorArchiveDto : archiveDtos)
    {
      if (!"tczd9102".equals(monitorArchiveDto.getAgencyId()))
      {
        try
        {
          Date startDate = sdf.parse(monitorArchiveDto.getStartTime());
          Date endDate = sdf.parse(monitorArchiveDto.getEndTime());
          if ((startDate.getTime() <= new Date().getTime()) && (new Date().getTime() <= endDate.getTime()))
            qArchiveMsg(monitorArchiveDto);
        }
        catch (ParseException e) {
          e.printStackTrace();
        }
      }
    } }

  public void qArchiveMsg(MonitorArchiveDto oldArchiveDto) {
    Map param = new HashMap();
    param.put("estateNo", oldArchiveDto.getEstateNo());
    param.put("estateType", oldArchiveDto.getEstateType());
    param.put("identityNo", oldArchiveDto.getIdentityNo());
    param.put("type", oldArchiveDto.getType());
    param.put("yearNo", oldArchiveDto.getYearNo());
    String msg = queryArchive(param);
    log.info("查档返回信息:" + msg);
    Map data = new HashMap();
    if ((StringUtil.isNotBlank(msg)) && (!"null".equals(msg)))
    {
      String dataMsg = msg;
      if (null == oldArchiveDto) {
        return;
      }
      MonitorArchiveDto newArchiveDto = new MonitorArchiveDto();
      newArchiveDto.setId(oldArchiveDto.getId());

      if (dataMsg.contains(Enums.PropertyStatusEnum.L1.getName())) {
        newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L1.getName());
      } else if (dataMsg.contains(Enums.PropertyStatusEnum.L2.getName())) {
        newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L2.getName());
      } else if (dataMsg.contains(Enums.PropertyStatusEnum.L3.getName())) {
        newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L3.getName());
      } else if (dataMsg.contains(PropertyStatusEnum.L4.getName())) {
        newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L4.getName());
      } else if (dataMsg.contains(Enums.PropertyStatusEnum.L5.getName())) {
        newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L5.getName());
      } else {
        newArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L6.getName());
        MonitorArchiveDto archive = new MonitorArchiveDto();
        archive.setId(oldArchiveDto.getId());
        this.monitorArchiveService.updateMonitorArchive(archive);
        return;
      }

      if ((null != oldArchiveDto.getPropertyStatus()) && (!oldArchiveDto.getPropertyStatus().equals(newArchiveDto.getPropertyStatus()))) {
        log.info("执行作业:" + msg + "结果由" + oldArchiveDto.getPropertyStatus() + "变更为" + newArchiveDto.getPropertyStatus());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(new Date());
        newArchiveDto.setIdentityNo(oldArchiveDto.getIdentityNo());
        newArchiveDto.setMessage(oldArchiveDto.getMessage() + ";<br>查询时间：" + date + "&nbsp;&nbsp;结果：" + dataMsg);
        if (null == oldArchiveDto.getChangeRecord())
          newArchiveDto.setChangeRecord(oldArchiveDto.getPropertyStatus() + "→" + newArchiveDto.getPropertyStatus());
        else {
          newArchiveDto.setChangeRecord(oldArchiveDto.getChangeRecord() + "→" + newArchiveDto.getPropertyStatus());
        }
        this.monitorArchiveService.updateMonitorArchive(newArchiveDto);

        String ipWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString());
        log.info("房产监测短信ipWhite:" + ipWhite);
        String content = "你在快鸽系统监测的房产（房 产证号：" + oldArchiveDto.getEstateNo() + "，姓名/身份证号：" + oldArchiveDto.getIdentityNo();

        content = content + "），房 产状态已从" + oldArchiveDto.getPropertyStatus() + "变为" + newArchiveDto.getPropertyStatus() + "，请知悉。";
        try {
          AmsUtil.smsSend(oldArchiveDto.getPhone(), ipWhite, content, "monitor");
        } catch (AnjboException e) {
          e.printStackTrace();
          log.error("发送短信失败：" + content);
        }

        if ((newArchiveDto.getPropertyStatus().equals("查封")) || (newArchiveDto.getPropertyStatus().equals("抵押查封"))) {
          String[] phones = ConfigUtil.getStringValue("MONITOR_PHONES").split(",");
          content = "快鸽系统监测的房 产（房 产证号：" + oldArchiveDto.getEstateNo() + "，姓名/身份证号：" + oldArchiveDto.getIdentityNo();

          content = content + "），房 产状态已从" + oldArchiveDto.getPropertyStatus() + "变为" + newArchiveDto.getPropertyStatus() + "，请知悉。";
          for (String phone : phones)
            try {
              AmsUtil.smsSend(phone, ipWhite, content, "monitor");
            } catch (AnjboException e) {
              e.printStackTrace();
              log.error("发送短信失败：" + content);
            }
        }
      }
      else {
        log.info("查档人:" + oldArchiveDto.getIdentityNo() + "结果未变更");
        MonitorArchiveDto archive = new MonitorArchiveDto();
        archive.setIdentityNo(oldArchiveDto.getIdentityNo());
        archive.setId(oldArchiveDto.getId());
        this.monitorArchiveService.updateMonitorArchive(archive);
      }
    } else {
      MonitorArchiveDto archive = new MonitorArchiveDto();
      archive.setId(oldArchiveDto.getId());
      this.monitorArchiveService.updateMonitorArchive(archive);
    }
  }

  public String queryArchive(Map<String, Object> param)
  {
    String msg = "";
    for (int i = 0; i < 6; i++) {
      RespDataObject resp = getArchiveTools(param);
      if (resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())) {
        msg = MapUtils.getString((Map)resp.getData(), "message");
      }
      log.info("查档返回====" + MapUtils.getString(param, "identityNo") + "====:" + i + msg);
      if ((StringUtil.isNotBlank(msg)) && (!"null".equals(msg)) && (!"没有找到匹配的房产记录".equals(msg))) {
        break;
      }
    }
    return msg;
  }

  @ResponseBody
  @RequestMapping({"getArchive"})
  public RespDataObject<Map<String, Object>> getArchiveTools(@RequestBody Map<String, Object> param)
  {
    RespDataObject result = new RespDataObject();
    result.setCode(RespStatusEnum.FAIL.getCode());
    result.setMsg(RespStatusEnum.FAIL.getMsg());
    try {
      if (StringUtils.isBlank(MapUtils.getString(param, "estateNo", ""))) {
        result.setMsg("查询失败,房产证号不能为空!");
        return result;
      }if (StringUtils.isBlank(MapUtils.getString(param, "estateType", ""))) {
        result.setMsg("查询失败,产权证类型不能为空!");
        return result;
      }if (StringUtils.isBlank(MapUtils.getString(param, "identityNo", ""))) {
        result.setMsg("查询失败,姓名/身份证号不能为空!");
        return result;
      }

      String id = MD5Utils.MD5Encode(MapUtils.getString(param, "estateNo") + MapUtils.getString(param, "estateType") + MapUtils.getString(param, "identityNo", "") + "1");
      Map map = new HashMap();
      map.put("id", id);
      map.put("estate", MapUtils.getString(param, "estateNo", ""));
      map.put("estateType", MapUtils.getString(param, "estateType", ""));
      String mqChannel = ConfigUtil.getStringValue("MQ_CHANNEL");
      map.put("channel", mqChannel);
      map.put("type", MapUtils.getString(param, "type", ""));
      map.put("yearNo", MapUtils.getString(param, "yearNo", ""));
      String identityNo = MapUtils.getString(param, "identityNo", "");
      String obligee = null;
      if (!StringUtil.isChineseChar(identityNo)) {
        identityNo = identityNo.toUpperCase();
        identityNo = identityNo.replace("(", "（").replace(")", "）");
      } else {
        obligee = identityNo;
      }
      map.put("identity", identityNo);
      map.put("obligee", obligee);
      ExecuteData data = new ExecuteData();
      data.setId(id);
      data.setData(map);
      RMqClientConnect.sendMsg("queue_archive", map);
      String msg = ExecuteQueue.getInstance().getMsgById(data, 30);
      Map returnMap = new HashMap();
      returnMap.put("message", msg);
      returnMap.put("createTime", DateUtil.getDateByFmt(new Date(), "yyyy-MM-dd HH:mm"));
      returnMap.put("archiveId", id);
      result.setData(returnMap);
      result.setMsg(RespStatusEnum.SUCCESS.getMsg());
      result.setCode(RespStatusEnum.SUCCESS.getCode());
    } catch (Exception e) {
      e.printStackTrace();
      result.setCode(RespStatusEnum.FAIL.getCode());
      result.setMsg(RespStatusEnum.FAIL.getMsg());
    }
    return result;
  }
}