package com.anjbo.monitor.controller;

import com.anjbo.core.RMqClientConnect;
import com.anjbo.execute.ExecuteData;
import com.anjbo.execute.ExecuteQueue;
import com.anjbo.monitor.common.Enums;
import com.anjbo.monitor.common.Enums.PropertyStatusEnum;
import com.anjbo.monitor.common.RespDataObject;
import com.anjbo.monitor.common.RespHelper;
import com.anjbo.monitor.common.RespStatus;
import com.anjbo.monitor.common.RespStatusEnum;
import com.anjbo.monitor.entity.MonitorArchiveAgencyDto;
import com.anjbo.monitor.entity.MonitorArchiveDto;
import com.anjbo.monitor.service.MonitorArchiveAgencyService;
import com.anjbo.monitor.service.MonitorArchiveService;
import com.anjbo.monitor.util.ConfigUtil;
import com.anjbo.monitor.util.DateUtil;
import com.anjbo.monitor.util.JsonUtil;
import com.anjbo.monitor.util.MD5Utils;
import com.anjbo.monitor.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MonitorArchiveController
{
  private static final Logger log = LoggerFactory.getLogger(MonitorArchiveController.class);

  @Autowired
  private MonitorArchiveService monitorArchiveService;

  @Autowired
  private MonitorArchiveAgencyService monitorArchiveAgencyService;

  @RequestMapping({"{agencyId}/save"})
  @ResponseBody
  public RespDataObject<Map<String, Object>> save(MonitorArchiveDto monitorArchiveDto, @PathVariable("agencyId") String agencyId) { RespDataObject result = new RespDataObject();
    result.setCode(RespStatusEnum.FAIL.getCode());
    result.setMsg(RespStatusEnum.FAIL.getMsg());
    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String date = sd.format(new Date());
    try {
      if (!checkAgency(agencyId)) {
        RespHelper.setFailDataObject(result, null, "参数错误");
        return result;
      }
      monitorArchiveDto.setAgencyId(agencyId);
      if ((monitorArchiveDto.getEstateType() == null) || (StringUtil.isBlank(monitorArchiveDto.getIdentityNo())) || 
        (StringUtil.isBlank(monitorArchiveDto
        .getEstateNo()))) {
        RespHelper.setFailDataObject(result, null, "参数错误");
        return result;
      }

      if (monitorArchiveDto.getEstateType().intValue() == 1) {
        monitorArchiveDto.setEstateType(Integer.valueOf(1));
        monitorArchiveDto.setYearNo("2015");
      } else if (monitorArchiveDto.getEstateType().intValue() == 2) {
        monitorArchiveDto.setEstateType(Integer.valueOf(1));
        monitorArchiveDto.setYearNo("2016");
      } else if (monitorArchiveDto.getEstateType().intValue() == 3) {
        monitorArchiveDto.setEstateType(Integer.valueOf(1));
        monitorArchiveDto.setYearNo("2017");
      } else if (monitorArchiveDto.getEstateType().intValue() == 4) {
        monitorArchiveDto.setEstateType(Integer.valueOf(1));
        monitorArchiveDto.setYearNo("2018");
      } else if (monitorArchiveDto.getEstateType().intValue() == 5) {
        monitorArchiveDto.setEstateType(Integer.valueOf(1));
        monitorArchiveDto.setYearNo("2019");
      }else if (monitorArchiveDto.getEstateType().intValue() == 6) {
          monitorArchiveDto.setEstateType(Integer.valueOf(1));
          monitorArchiveDto.setYearNo("2020");
      }

      monitorArchiveDto.setType(Integer.valueOf(1));

      Map params = new HashMap();
      params.put("estateNo", monitorArchiveDto.getEstateNo());
      params.put("estateType", monitorArchiveDto.getEstateType());
      params.put("identityNo", monitorArchiveDto.getIdentityNo());
      params.put("type", monitorArchiveDto.getType());
      params.put("yearNo", monitorArchiveDto.getYearNo());
      String msg = queryArchive(params);

      if ((StringUtil.isNotBlank(msg)) && (!"null".equals(msg)))
        msg = "查询时间：" + date + "&nbsp;&nbsp;结果：" + msg;
      else {
        msg = "";
      }
      Map rMap = new HashMap();
      rMap.put("msg", msg);
      RespHelper.setSuccessDataObject(result, rMap);

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Calendar cal = Calendar.getInstance();
      cal.add(2, 6);
      monitorArchiveDto.setStartTime(sdf.format(new Date()));
      monitorArchiveDto.setEndTime(sdf.format(cal.getTime()));
      monitorArchiveDto.setMessage(msg);

      if (msg.contains(Enums.PropertyStatusEnum.L1.getName()))
        monitorArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L1.getName());
      else if (msg.contains(Enums.PropertyStatusEnum.L2.getName()))
        monitorArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L2.getName());
      else if (msg.contains(Enums.PropertyStatusEnum.L3.getName()))
        monitorArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L3.getName());
      else if (msg.contains(PropertyStatusEnum.L4.getName()))
        monitorArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L4.getName());
      else if (msg.contains(Enums.PropertyStatusEnum.L5.getName()))
        monitorArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L5.getName());
      else {
        monitorArchiveDto.setPropertyStatus(Enums.PropertyStatusEnum.L6.getName());
      }

      this.monitorArchiveService.insertMonitorArchive(monitorArchiveDto);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
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
      
    //HttpUtil http = new HttpUtil();
		//result = http.getRespDataObject(Enums.MODULAR_URL.TOOl.toString(), "/tools/archive/v/addArchive", param, Map.class);
		//String toolsUrl = UrlUtil.getStringValue(Enums.MODULAR_URL.TOOl.toString())+"/tools/archive/v/addArchive";
		//			String toolsUrl = ConfigUtil.getStringValue(Constants.LINK_ANJBO_TOOl_URL, ConfigUtil.CONFIG_LINK)+"/tools/archive/v/addArchive";
//		String resultStr = HttpUtil.jsonPost(toolsUrl, param);
//		result = JSON.parseObject(resultStr, RespDataObject.class);
//		if(null!=result&&"SUCCESS".equals(result.getCode())
//				&&" 没有找到任何匹配的数据".equals(MapUtils.getString(result.getData(), "msg", ""))){
//			resultStr = HttpUtil.jsonPost(toolsUrl, param);
//			result = JSON.parseObject(resultStr, RespDataObject.class);
//		}
      
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
  @RequestMapping({"/"})
  @ResponseBody
  public String we() {
    return "欢迎使用查档系统";
  }

  @GetMapping({"{agencyId}/add"})
  public String add(HttpServletRequest request, Model model, @PathVariable("agencyId") String agencyId) {
    if (!checkAgency(agencyId)) {
      return "error";
    }
    model.addAttribute("agencyId", agencyId);
    return "index";
  }

  @GetMapping({"{agencyId}/index"})
  public String index(HttpServletRequest request, Model model, @PathVariable("agencyId") String agencyId) {
    if (!checkAgency(agencyId)) {
      return "error";
    }
    model.addAttribute("agencyId", agencyId);
    return "monitorArchiveList";
  }
  @ResponseBody
  @RequestMapping({"{agencyId}/edit"})
  public RespStatus edit(MonitorArchiveDto monitorArchiveDto, @PathVariable("agencyId") String agencyId) {
    RespStatus respStatus = new RespStatus();
    if (!checkAgency(agencyId)) {
      RespHelper.setFailRespStatus(respStatus, "参数错误");
      return respStatus;
    }
    try {
      this.monitorArchiveService.updateMonitorArchive(monitorArchiveDto);
    } catch (Exception e) {
      e.printStackTrace();
    }
    RespHelper.setSuccessRespStatus(respStatus);
    return respStatus;
  }
  @ResponseBody
  @RequestMapping({"{agencyId}/delete"})
  public RespStatus delete(@RequestParam("ids") String ids, @PathVariable("agencyId") String agencyId) {
    RespStatus respStatus = new RespStatus();
    if (!checkAgency(agencyId)) {
      RespHelper.setFailRespStatus(respStatus, "参数错误");
      return respStatus;
    }
    try {
      this.monitorArchiveService.deleteMonitorArchiveById(ids);
    } catch (Exception e) {
      e.printStackTrace();
    }
    RespHelper.setSuccessRespStatus(respStatus);
    return respStatus;
  }

  @ResponseBody
  @RequestMapping({"{agencyId}/page"})
  public String page(MonitorArchiveDto monitorDao, @PathVariable("agencyId") String agencyId)
  {
    String json = null;
    try {
      if (!checkAgency(agencyId)) {
        return "error";
      }
      monitorDao.setAgencyId(monitorDao.getAgencyId());
      int total = this.monitorArchiveService.selectArchiveCount(monitorDao);
      List monitorArchiveList = this.monitorArchiveService.selectArchiveList(monitorDao);
      List monList = new ArrayList();
      for (int i = 0; i < monitorArchiveList.size(); i++) {
        ((MonitorArchiveDto)monitorArchiveList.get(i)).setSectionTime(((MonitorArchiveDto)monitorArchiveList.get(i)).getStartTimeStr() + "-" + ((MonitorArchiveDto)monitorArchiveList.get(i)).getEndTimeStr());
        if (((MonitorArchiveDto)monitorArchiveList.get(i)).getEstateType().intValue() == 0)
          ((MonitorArchiveDto)monitorArchiveList.get(i)).setEstateTypeStr("房地产权证书");
        else {
          ((MonitorArchiveDto)monitorArchiveList.get(i)).setEstateTypeStr("不动产权证书(粤" + ((MonitorArchiveDto)monitorArchiveList.get(i)).getYearNo() + ")");
        }
        ((MonitorArchiveDto)monitorArchiveList.get(i)).setCreateTimeStr(((MonitorArchiveDto)monitorArchiveList.get(i)).getStartTimeStr());
        monList.add(monitorArchiveList.get(i));
      }
      json = "{\"total\":" + total + ",\"rows\":" + JsonUtil.listToJson(monList) + "}";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return json;
  }

  public boolean checkAgency(String agencyId) {
    boolean flag = false;
    List<MonitorArchiveAgencyDto> list = this.monitorArchiveAgencyService.selectAll();
    for (MonitorArchiveAgencyDto dto : list) {
      if (dto.getAgencyId().equals(agencyId)) {
        flag = true;
      }
    }
    return flag;
  }
}