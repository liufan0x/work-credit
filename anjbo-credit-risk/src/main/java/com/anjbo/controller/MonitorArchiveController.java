package com.anjbo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.risk.MonitorArchiveDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.core.RMqClientConnect;
import com.anjbo.execute.ExecuteData;
import com.anjbo.execute.ExecuteQueue;
import com.anjbo.service.ArchiveService;
import com.anjbo.service.MonitorArchiveService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.ValidHelper;


/**
 * 房产监测
 * @author linf 
 * @author huanglj
 *
 */
@Controller
@RequestMapping("/credit/risk/monitor/v")
public class MonitorArchiveController extends BaseController{
	private static final Log log = LogFactory.getLog(MonitorArchiveController.class);
	
	@Resource
	private MonitorArchiveService monitorArchiveService;
	@Resource
	private ArchiveService archiveService;
	
	@ResponseBody
	@RequestMapping(value = "init")
	public  RespDataObject<Map<String,Object>> init(HttpServletRequest request,HttpServletResponse response) {
		
    	RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
    	result.setCode(RespStatusEnum.FAIL.getCode());
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
    	
    	Map<String, Object> map=new HashMap<String, Object>();
    	try {
    		List<ProductDto> productdto =  getProductDtos();
    		ListIterator<ProductDto> list=productdto.listIterator();
    		while (list.hasNext()) {
				if(!list.next().getCityCode().equals("4403")){
					list.remove();
				}
			}
    		map.put("productdto", productdto);
    		result.setCode(RespStatusEnum.SUCCESS.getCode());
        	result.setMsg(RespStatusEnum.SUCCESS.getMsg());
        	result.setData(map);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("房产监测列表初始化数据异常 ,错误信息为==>"+e.getMessage());
		}
    	return result;
    }

	/**
	 * 列表
	 * @param request
	 * @param response
	 * @param harvestDto
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/page")
	public RespPageData<MonitorArchiveDto> page(HttpServletRequest request,
			HttpServletResponse response,@RequestBody MonitorArchiveDto monitorDao) {
		
		RespPageData<MonitorArchiveDto> result= new RespPageData<MonitorArchiveDto>();
    	result.setCode(RespStatusEnum.FAIL.getCode());
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
    	
		try {
			UserDto user = getUserDto(request);  //获取用户信息
			monitorDao.setAgencyId(user.getAgencyId());
			if(null!=monitorDao.getEstateType() && monitorDao.getEstateType()==1){  //不动产2015
			    monitorDao.setYearNo("2015");
			}else if(null!=monitorDao.getEstateType() && monitorDao.getEstateType()==2){ //不动产 2016
			    monitorDao.setEstateType(1);
			    monitorDao.setYearNo("2016");
			}else if(null!=monitorDao.getEstateType() && monitorDao.getEstateType()==3){ //不动产 2017
			    monitorDao.setEstateType(1);
			    monitorDao.setYearNo("2017");
			}
			result.setTotal(monitorArchiveService.selectArchiveCount(monitorDao));
			List<MonitorArchiveDto> monitorArchiveList = monitorArchiveService.selectArchiveList(monitorDao);
			List<MonitorArchiveDto> monList = new ArrayList<MonitorArchiveDto>();
			List<ProductDto> productdto =  getProductDtos();
			for (int i = 0; i < monitorArchiveList.size(); i++) {
				monitorArchiveList.get(i).setSectionTime(monitorArchiveList.get(i).getStartTimeStr()+"-"+monitorArchiveList.get(i).getEndTimeStr());
				if(monitorArchiveList.get(i).getEstateType()==0){
					monitorArchiveList.get(i).setEstateTypeStr("房地产权证书");
				}else{
					monitorArchiveList.get(i).setEstateTypeStr("不动产权证书(粤"+monitorArchiveList.get(i).getYearNo()+")");
				}
				monitorArchiveList.get(i).setName(CommonDataUtil.getUserDtoByUidAndMobile(monitorArchiveList.get(i).getCreateUid())==null?"-":CommonDataUtil.getUserDtoByUidAndMobile(monitorArchiveList.get(i).getCreateUid()).getName());;
				monList.add(monitorArchiveList.get(i));
				for(int j=0;j<productdto.size();j++){
					if(productdto.get(j).getProductCode().equals(monitorArchiveList.get(i).getQueryUsage())){
						monitorArchiveList.get(i).setQueryUsageStr(productdto.get(j).getProductName());
					}
				}
				if("03".equals(monitorArchiveList.get(i).getQueryUsage())){
					monitorArchiveList.get(i).setQueryUsageStr("畅贷");
				}
			}
			result.setRows(monList);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
	    	result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("房产监测加载列表数据异常,错误信息为 ==>"+e.getMessage());
		}
		return result;
	}
    /**获取预约总条数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMonitorCount")
    public RespDataObject<Integer> getLendingtCount(HttpServletRequest request,
			HttpServletResponse response,@RequestBody MonitorArchiveDto monitorDao){
    	RespDataObject<Integer> result = new RespDataObject<Integer>();
    	try {
    		UserDto user = getUserDto(request);  //获取用户信息
			monitorDao.setAgencyId(user.getAgencyId());
			if(null!=monitorDao.getEstateType() && monitorDao.getEstateType()==1){  //不动产2015
			    monitorDao.setYearNo("2015");
			}else if(null!=monitorDao.getEstateType() && monitorDao.getEstateType()==2){ //不动产 2016
			    monitorDao.setEstateType(1);
			    monitorDao.setYearNo("2016");
			}else if(null!=monitorDao.getEstateType() && monitorDao.getEstateType()==3){ //不动产 20167
			    monitorDao.setEstateType(1);
			    monitorDao.setYearNo("2017");
			}
			
			int count = monitorArchiveService.selectArchiveCount(monitorDao);
			result.setData(count);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.info("房产监测加载列表总数据量异常,错误信息为==>"+e.getMessage());
		}
		return result;
    }
    
    /**
     * 详情
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail")
	public RespDataObject<Map<String, Object>> detail(HttpServletRequest request,HttpServletResponse response,@RequestBody MonitorArchiveDto monitorDao) {
    	RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
    	result.setCode(RespStatusEnum.FAIL.getCode());
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
    	Map<String, Object> map=new HashMap<String, Object>();
    	try {
    		MonitorArchiveDto archiveDto = monitorArchiveService.findBymonitor(monitorDao);
//TODO 暂时注释 接口未实现
    		List<ProductDto> productList = getProductDtos();//dataService.businessProductList();
    		ListIterator<ProductDto> list=productList.listIterator();
    		while (list.hasNext()) {
				if(!list.next().getCityCode().equals("4403")){
					list.remove();
				}
			}
    		for (ProductDto p:productList) {
				   if(archiveDto.getQueryUsage().equals(p.getProductCode())){
					   archiveDto.setQueryUsageStr(p.getProductName());
				   }
			} 
    		map.put("archive", archiveDto);
    		map.put("productdto", productList);
    		List<Map<String, Object>> listmap = new ArrayList<Map<String,Object>>();
    		if(StringUtil.isNotEmpty(archiveDto.getMessage())){
    			String cont[]=archiveDto.getMessage().split(";");
        		for (int i = 0; i < cont.length; i++) {
        			Map<String, Object> paramt=new HashMap<String, Object>();
        			String conts[]=cont[i].split("&");
        			//如果只有一条记录，那么复制一条
        			if(cont.length==1){
        				paramt.put("time", conts[0]);
        				paramt.put("cont", conts[1]);
        				listmap.add(paramt);
        			}
        			//取数组中的第一条和最后一条记录
        			if(i==0||i==cont.length-1){
	       				if(conts.length>1){
	       					 paramt=new HashMap<String, Object>();
	       					 if((i+1)==cont.length){
	       						 paramt.put("time", archiveDto.getUpdateTimeStr());
	       					 }else{
	       						 paramt.put("time", conts[0]);
	       					 }
	           				 paramt.put("cont", conts[1]);
	           				 listmap.add(paramt);
	       				 }
        			}
    			}
    		}
    		
    		map.put("message", listmap);
    		result.setData(map);
    		result.setCode(RespStatusEnum.SUCCESS.getCode());
        	result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.info("房产监测加载详情数据异常,错误信息为 ==>"+e.getMessage());
		}
    	return result;
    }
    
    /**
     * 添加房产监测页面初始化数据
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addIndex")
	public RespDataObject<Map<String,Object>> addIndex(HttpServletRequest request) {
    	RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
    	result.setCode(RespStatusEnum.FAIL.getCode());
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			UserDto user = getUserDto(request);  //获取用户信息
			Map<String,Object> retMap = new HashMap<String, Object>();
			List<ProductDto> productList =getProductDtos();
			ListIterator<ProductDto> list=productList.listIterator();
    		while (list.hasNext()) {
				if(!list.next().getCityCode().equals("4403")){
					list.remove();
				}
			}
			retMap.put("productList",null);
	    	result.setData(retMap);
	    	result.setCode(RespStatusEnum.SUCCESS.getCode());
	    	result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.info("添加房产监测页面数据异常,错误信息为==>"+e.getMessage());
		}
		return result;
    }
    
    /**
     * 添加房产监测
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addMonitorArchive")
	public @ResponseBody
	RespDataObject<Map<String, Object>> addMonitorArchive(HttpServletRequest request,@RequestBody MonitorArchiveDto monitorDao) {
    	RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
    	result.setCode(RespStatusEnum.FAIL.getCode());
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			
			UserDto userDto=getUserDto(request);  //获取用户信息
			//设置产权证类型
			if(monitorDao.getEstateType()==2){
				monitorDao.setEstateType(1);
				monitorDao.setYearNo("2016");
			}else if(monitorDao.getEstateType()==3){
				monitorDao.setEstateType(1);
				monitorDao.setYearNo("2017");
			}
			monitorDao.setType(1);//分户
			//先查档
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("uid", userDto.getUid());
			param.put("uMobile", userDto.getMobile());
			param.put("type", monitorDao.getType());
			param.put("yearNo", monitorDao.getYearNo());
			param.put("estateType", monitorDao.getEstateType());
			param.put("estateNo", monitorDao.getEstateNo());
			param.put("identityNo", monitorDao.getIdentityNo());
			if(!ValidHelper.isNotEmptyByKeys(param, "type","estateType","estateNo","identityNo","uid")){//查档类型(1分户，2分栋)
				result.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());					//产权证书类型（0房地产权证书 1不动产权证书）
				return result;													
			}
			param.put("startTime", monitorDao.getStartTime());
			param.put("endTime", monitorDao.getEndTime());
			param.put("queryUsage", monitorDao.getQueryUsage());
			param.put("phone", monitorDao.getPhone());
			param.put("queryFrequency", monitorDao.getQueryFrequency());
			monitorDao.setCreateUid(userDto.getUid());
			monitorDao.setAgencyId(userDto.getAgencyId());
			
			//取登录人信息
			String id = MD5Utils.MD5Encode(MapUtils.getString(param, "estateNo") + MapUtils.getString(param, "estateType") + MapUtils.getString(param, "identityNo", "") +  "1");
			Map<String,Object> map =new HashMap<String, Object>();
			map.put("id",id);
			map.put("estate", MapUtils.getString(param, "estateNo", ""));
			map.put("estateType", MapUtils.getString(param, "estateType", ""));
			String mqChannel=ConfigUtil.getStringValue(Constants.MQ_CHANNEL,ConfigUtil.CONFIG_BASE);
			map.put("channel", mqChannel);
			map.put("type", MapUtils.getString(param, "type", ""));
			map.put("yearNo",MapUtils.getString(param, "yearNo", ""));
			String identityNo = MapUtils.getString(param, "identityNo", "");
			String obligee=null;
			if (!StringUtil.isChineseChar(identityNo)) {
			    identityNo = identityNo.toUpperCase();
			    identityNo=identityNo.replace("(","（").replace(")","）");
			}else{
				obligee=identityNo;
			} 
			map.put("identity",identityNo);
			map.put("obligee",obligee);
			ExecuteData<Map<String, Object>> data = new ExecuteData<Map<String, Object>>(); 
			data.setId(id);
			data.setData(map);
			RMqClientConnect.sendMsg("queue_archive", map);
			String msg = ExecuteQueue.getInstance().getMsgById(data, 30);
			
			Map<String, Object> returnMap=new HashMap<String, Object>();
			returnMap.put("message", msg);
			returnMap.put("createTime", DateUtil.getDateByFmt(new Date(), DateUtil.FMT_TYPE11));
			returnMap.put("archiveId", id);
			result.setData(returnMap);
			param.put("createUid", userDto.getUid());
			param.put("uid", userDto.getUid());
			param.put("uMobile",userDto.getMobile());
			param.put("agencyId",userDto.getAgencyId());
			param.put("id",id);
			result.setCode("SYSTEM_UPDATE");
			archiveService.getArchiveId(result,param);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
	    	result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("添加房产监测异常,错误信息为==>"+e.getMessage());
		}
		return result;
    }
    
    /**
     * 删除房产监测
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteMonitorArchiveById")
	public RespStatus deleteMonitorArchiveById(HttpServletRequest request,@RequestBody MonitorArchiveDto monitorArchiveDto) {
    	RespStatus result = new RespStatus();
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
    	result.setCode(RespStatusEnum.FAIL.getCode());
		try {
			monitorArchiveService.deleteMonitorArchiveById(monitorArchiveDto);
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
	    	result.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("删除房产监测异常,错误信息为==>"+e.getMessage());
		}
		return result;
    }
	
    /**
     * 修改房产监测
     * @param request
     * @param monitorArchiveDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateMonitorArchive")
	public RespStatus updateMonitorArchive(HttpServletRequest request,@RequestBody MonitorArchiveDto monitorArchiveDto) {
    	RespStatus result = new RespStatus();
    	result.setMsg(RespStatusEnum.FAIL.getMsg());
    	result.setCode(RespStatusEnum.FAIL.getCode());
		try {
			monitorArchiveService.updateMonitorArchive(monitorArchiveDto);
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
	    	result.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改房产监测异常,错误信息为==>"+e.getMessage());
			return result;
		}
		return result;
    }
}
