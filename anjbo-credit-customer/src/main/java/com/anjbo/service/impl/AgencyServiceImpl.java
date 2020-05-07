package com.anjbo.service.impl;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.customer.AgencyIncomeModeDto;
import com.anjbo.bean.customer.AgencyProductDto;
import com.anjbo.common.*;
import com.anjbo.controller.CustomerAgencyController;
import com.anjbo.dao.AgencyIncomeModeMapper;
import com.anjbo.dao.AgencyMapper;
import com.anjbo.dao.AgencyProductMapper;
import com.anjbo.service.AgencyService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Transactional
@Service
public class AgencyServiceImpl implements AgencyService {
	final Logger logger = LoggerFactory.getLogger(getClass());
	final Lock lock = new ReentrantLock();
	@Resource
	private AgencyMapper agencyMapper;
	@Resource
	private AgencyIncomeModeMapper agencyIncomeModeMapper;
	@Resource
	private AgencyProductMapper agencyProductMapper;

	Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private CustomerAgencyController customerAgencyController;
	@Override
	public List<AgencyDto> agencyList() {
		return agencyMapper.agencyList();
	}
	
	@Override
	public AgencyDto getAgencyDto(int id) {
		return agencyMapper.getAgencyDto(id);
	}

	@Override
	public AgencyDto getEntityByAgencyCode(int agencyCode) {
		return agencyMapper.getEntityByAgencyCode(agencyCode);
	}

	@Override
	public RespDataObject<AgencyDto> insert(Map<String,Object> map)throws Exception{
		RespDataObject<AgencyDto> respDataObject = new RespDataObject<AgencyDto>();
		//新增机构表
		AgencyDto agencyDto = new AgencyDto();
		String agencyName = MapUtils.getString(map,"agencyName");
		String simName = MapUtils.getString(map,"simName","");
		agencyDto.setName(agencyName);
		agencyDto.setSimName(StringUtils.isBlank(simName)?agencyName:simName);
		agencyDto.setChanlMan(MapUtils.getString(map,"channelManagerUid",""));
		agencyDto.setContactMan(MapUtils.getString(map,"contactsName",""));
		agencyDto.setContactTel(MapUtils.getString(map,"contactsPhone",""));
		agencyDto.setStatus(1);
		agencyDto.setChargeStandard(MapUtils.getString(map,"chargeStandard",null));
		String isBond = MapUtils.getString(map,"isBond","");
		if("1".equals(isBond)){
			isBond = "0";
		} else if("2".equals(isBond)){
			isBond = "1";
		}
		agencyDto.setIsBond(isBond);
		agencyDto.setProportionResponsibility(MapUtils.getString(map,"proportionResponsibility",null));
		agencyDto.setBond(MapUtils.getDouble(map,"bond",null));
		agencyDto.setMinBond(MapUtils.getDouble(map,"minBond",null));
		agencyDto.setCreditLimit(MapUtils.getDouble(map,"creditLimit",null));
		agencyDto.setAgencyId(MapUtils.getInteger(map,"agencyId",null));
		agencyDto.setCreateUid(MapUtils.getString(map,"createUid",""));
		agencyDto.setCooperativeModeId(MapUtils.getInteger(map,"cooperativeModeId",0));
		agencyDto.setAgencyType(MapUtils.getString(map,"agencyType",""));
		agencyDto.setSurplusQuotaRemind(MapUtils.getDouble(map,"surplusQuotaRemind",95d));
		agencyDto.setSurplusQuota(MapUtils.getDouble(map,"creditLimit",null));//初始化 剩余额度=授信额度
		agencyDto.setExpandManagerUid(MapUtils.getString(map,"expandManagerUid"));
		agencyDto.setChanlMan(MapUtils.getString(map,"channelManagerUid"));
		agencyDto.setAcceptManagerUid(MapUtils.getString(map,"acceptManagerUid"));
		agencyDto.setRiskBearMultiple(MapUtils.getDouble(map,"riskBearMultiple",null));
		agencyDto.setManageAccount(MapUtils.getString(map,"contactsPhone",null));
		agencyDto.setSignStatus(1);
		agencyDto.setExpandChiefUid(MapUtils.getString(map,"expandChiefUid",""));
		if(map.containsKey("applyDate")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss");
			Date d = format.parse(MapUtils.getString(map,"applyDate"));
			agencyDto.setApplyDate(d);
		}
		//没有机构id说明是在新增机构申请入驻时新增的数据
		Integer agency_id = MapUtils.getInteger(map,"agency_id",null);
		if(null==agency_id){

			List<AgencyDto> agencyList = agencyMapper.selectAgencyByName(agencyDto.getName());
			String agencyIdsStr = "";
			if(null!=agencyList||agencyList.size()>0) {
				for (AgencyDto a : agencyList) {
					if (2 == a.getSignStatus()) {
						RespHelper.setFailDataObject(respDataObject, null, "已存在相同的机构名称!");
						return respDataObject;
					} else {
						agencyIdsStr += ","+a.getId()+"-"+a.getSignStatus();
					}
				}
			}
			if(StringUtils.isNotBlank(agencyIdsStr)){
				agencyIdsStr = agencyIdsStr.substring(1,agencyIdsStr.length());
			}
			if(null==agencyList||agencyList.size()<=0) {
				agencyMapper.insert(agencyDto);
				RespHelper.setSuccessDataObject(respDataObject, agencyDto);
			} else {
				HttpUtil httpUtil = new HttpUtil();
				map.put("agencyId",agencyIdsStr);
				RespStatus respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/sm/agency/v/isListByAgencyName", map);
				if (RespStatusEnum.SUCCESS.getCode().equals(respStatus.getCode())) {
					agencyMapper.insert(agencyDto);
					RespHelper.setSuccessDataObject(respDataObject, agencyDto);
				} else {
					RespHelper.setFailDataObject(respDataObject, null, "已存在相同的机构名称!");
				}
			}

			if(null!=agencyList||agencyList.size()>0){
				String signStatus = "";
				for(AgencyDto agencyTmp:agencyList) {
					if(1==agencyTmp.getSignStatus()){
						signStatus = "未签约";
					} else if(2==agencyTmp.getSignStatus()){
						signStatus = "已签约";
					} else if(3==agencyTmp.getSignStatus()){
						signStatus = "已解约";
					} else {
						signStatus = "-";
					}
					logger.info("=====该:" + agencyTmp.getName() + "机构已存在,签约状态为:" + signStatus + "=====");
				}
			}
			if(RespStatusEnum.FAIL.getCode().equals(respDataObject.getCode())){
				return respDataObject;
			}
			//初始化机构角色
			HttpUtil http = new HttpUtil();
			Map<String,Object> initRole = new HashMap<String,Object>();
			initRole.put("agencyName", agencyName);
			RespStatus result = http.getRespStatus(Constants.LINK_CREDIT,"/credit/user/role/v/batchInsert_"+agencyDto.getId(), initRole);
			if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
				agencyMapper.detail(agencyDto);
				log.info("初始化机构角色失败:==="+result.getMsg()+"===");
				RespHelper.setFailDataObject(respDataObject,agencyDto,result.getMsg());
			}
			//初始化费用支付方式
			AgencyIncomeModeDto a = new AgencyIncomeModeDto();
			a.setAgencyId(agencyDto.getId());
			a.setCreateUid(agencyDto.getCreateUid());
			a.setName("费用前置");
			a.setIncomeMode("1");
			agencyIncomeModeMapper.delete(a);
			agencyIncomeModeMapper.insert(a);
			//初始化申请城市产品
			List<AgencyProductDto> pc = new ArrayList<AgencyProductDto>();
			List<DictDto> cityList = customerAgencyController.getDictDtoByType("cityList");
			List<DictDto> productList = customerAgencyController.getDictDtoByType("product");
			String applyProduct = MapUtils.getString(map,"applyProduct");
			String applyCity = MapUtils.getString(map,"applyCity");
			String[] cs = applyCity.split(",");
			String[] ps = applyProduct.split(",");
			AgencyProductDto dto = null;
			for (String c :cs){
				for (DictDto dc:cityList){
					if(c.equals(dc.getCode())){
						for (String p:ps){
							for (DictDto dp:productList){
								if(p.equals(dp.getCode())){
									dto = new AgencyProductDto();
									dto.setAgencyId(agencyDto.getId());
									dto.setStatus(1);
									dto.setCityName(dc.getName());
									dto.setCityCode(dc.getCode());
									dto.setProductName(dp.getName());
									dto.setProductCode(dp.getCode());
									dto.setCreateUid(agencyDto.getCreateUid());
									pc.add(dto);
								}
							}
						}
					}
				}
			}
			if(pc.size()>0){
				dto = new AgencyProductDto();
				dto.setAgencyId(agencyDto.getId());
				agencyProductMapper.delete(dto);
				agencyProductMapper.batchInsert(pc);
			}
			return respDataObject;
		}

		agencyDto.setSignStatus(2);
		agencyDto.setSignDate(new Date());
		agencyDto.setId(agency_id);
		Integer agencyCode = generateAgencyCode();
		agencyDto.setAgencyCode(agencyCode);
		//重新生成管理员账号
		HttpUtil http = new HttpUtil();
		Map<String,Object> tmpAccount = new HashMap<String,Object>();
		tmpAccount.put("agencyId",agencyDto.getId());
		tmpAccount.put("isEnable",0);
		tmpAccount.put("name",agencyDto.getContactMan());
		tmpAccount.put("mobile",agencyDto.getContactTel());
		tmpAccount.put("indateStart",null);
		tmpAccount.put("indateEnd",null);
		if(StringUtils.isBlank(MapUtils.getString(map,"accountUid"))){
			tmpAccount.put("password", ConfigUtil.getStringValue(Constants.BASE_AGENCY_DEFAULT_PASSWORLD,ConfigUtil.CONFIG_BASE));
		} else {
			tmpAccount.put("uid",MapUtils.getString(map,"accountUid"));
		}

		RespStatus result = http.getRespStatus(Constants.LINK_CREDIT,"/credit/user/base/v/editAgentAdmin", tmpAccount);
		if(RespStatusEnum.FAIL.getCode().equals(result.getCode())){
			log.info("生成管理员账号失败:==="+result.getMsg()+"===");
			RespHelper.setFailDataObject(respDataObject,agencyDto,result.getMsg());
			return respDataObject;
		}
		agencyDto.setAccountUid(result.getMsg());

		//新增费用支付方式表
		List<AgencyIncomeModeDto> tmpA = new ArrayList<AgencyIncomeModeDto>();
		String incomeMode = MapUtils.getString(map,"incomeMode");
		String incomeModeValue = MapUtils.getString(map,"incomeModeValue");
		String[] in = incomeMode.split(",");
		String[] inv = incomeModeValue.split(",");
		for (int i=0;i<in.length;i++){
			AgencyIncomeModeDto a = new AgencyIncomeModeDto();
			a.setIncomeMode(in[i]);
			a.setName(inv[i]);
			a.setAgencyId(agencyDto.getId());
			a.setCreateUid(agencyDto.getCreateUid());
			tmpA.add(a);
		}
		if(tmpA.size()>0){
			AgencyIncomeModeDto dto = new AgencyIncomeModeDto();
			dto.setAgencyId(agency_id);
			agencyIncomeModeMapper.delete(dto);
			agencyIncomeModeMapper.batchInsert(tmpA);
		}
		//新增机构关联产品表
		if(map.containsKey("productList")){
			List<AgencyProductDto> list = (List<AgencyProductDto>)map.get("productList");
			if(list.size()>0){
				AgencyProductDto dto = new AgencyProductDto();
				dto.setAgencyId(agency_id);
				agencyProductMapper.delete(dto);
				agencyProductMapper.batchInsert(list);
			}
		}
		agencyDto.setOpenCity(MapUtils.getString(map,"openCity"));
		agencyMapper.updateById(agencyDto);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("agencyId",agencyDto.getId());
		param.put("agencyName",agencyDto.getName());
		param.put("agencyCode",agencyDto.getAgencyCode());
		param.put("agencyStatus",0);

		result = http.getRespStatus(Constants.LINK_ANJBO_APP_URL,"/mortgage/agency/updateAgencyNotify",param);
		log.info(agencyDto.getName()+"更新快鸽APP用户机构状态==="+result+"====");
		RespHelper.setSuccessDataObject(respDataObject,agencyDto);
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		try {
			scheduledThreadPool.schedule(new Runnable() {
				public void run() {
					RespStatus respStatus = initAgencyToRedis();
					logger.info("新增机构初始化到Redis返回:"+respStatus);
				}
			}, 0, TimeUnit.SECONDS);
		} finally {
			if(null!=scheduledThreadPool){
				scheduledThreadPool.shutdown();
			}
		}
		return respDataObject;
	}

	/**
	 * 获取机构码
	 * @return
	 */
	public Integer selectMaxAgencyCode(){
		return agencyMapper.selectMaxAgencyCode();
	}

	public int generateAgencyCode(){
		lock.lock();
		try{
			Integer agecnyCode = agencyMapper.selectMaxAgencyCode();
			agecnyCode = (null==agecnyCode||100000>agecnyCode)?100000:++agecnyCode;
			return agecnyCode;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public List<AgencyDto> selectAgencyByName(String name){
		return agencyMapper.selectAgencyByName(name);
	}

	public int updateById(AgencyDto agencyDto){
		return agencyMapper.updateById(agencyDto);
	}

	@Override
	public int updSurplusQuota(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return agencyMapper.updSurplusQuota(map);
	}
	@Override
	public int delete(AgencyDto agencyDto){
		AgencyDto t = agencyMapper.detail(agencyDto);
		if(null!=t&&1==t.getSignStatus()){
			return agencyMapper.delete(agencyDto);
		}
		return 0;
	}
	@Override
	public AgencyDto detail(AgencyDto agencyDto){
		return agencyMapper.detail(agencyDto);
	}

	public AgencyDto selectAgencyByMobile(AgencyDto agencyDto){
		return agencyMapper.selectAgencyByMobile(agencyDto);
	}

	public AgencyDto selectAgencyById(Map<String,Object> map){
		return agencyMapper.selectAgencyById(map);
	}

	/**
	 * 根据简称查询机构
	 * @param simName
	 * @return
	 */
	@Override
	public List<AgencyDto> selectAgencyBySimName(String simName){
		return agencyMapper.selectAgencyBySimName(simName);
	}

	public RespStatus initAgencyToRedis(){
		List<AgencyDto> agencyList = agencyMapper.agencyList();
		List<AgencyIncomeModeDto> listIncomeMode = agencyIncomeModeMapper.allAgencyIncomeMode();
		List<AgencyProductDto> productDtoList = agencyProductMapper.allAgencyProduct();
		List<AgencyIncomeModeDto> listIncomeModeTmp = null;
		List<AgencyProductDto> productTmp = null;
		Iterator<AgencyDto> agencyIt = agencyList.iterator();

		Map<Object,Object> agencyMapById = new HashMap<Object,Object>();
		Map<Object,Object> agemcyMapByCode = new HashMap<Object,Object>();

		RespStatus respStatus = new RespStatus();
		try {
			while (agencyIt.hasNext()) {
				AgencyDto agencyDto = agencyIt.next();

				if (null == agencyDto || 2 != agencyDto.getSignStatus()) continue;

				if (null != listIncomeMode && listIncomeMode.size() > 0) {
					listIncomeModeTmp = new ArrayList<AgencyIncomeModeDto>();
					Iterator<AgencyIncomeModeDto> incomeIt = listIncomeMode.iterator();
					while (incomeIt.hasNext()) {
						AgencyIncomeModeDto in = incomeIt.next();
						if (in.getAgencyId() == agencyDto.getId()) {
							listIncomeModeTmp.add(in);
							incomeIt.remove();
							continue;
						}
					}
					agencyDto.setListIncome(listIncomeModeTmp);
				}
				if (null != productDtoList && productDtoList.size() > 0) {
					productTmp = new ArrayList<AgencyProductDto>();
					Iterator<AgencyProductDto> productIt = productDtoList.iterator();
					while (productIt.hasNext()) {
						AgencyProductDto productDto = productIt.next();
						if (productDto.getAgencyId() == agencyDto.getId()) {
							productTmp.add(productDto);
							productIt.remove();
							continue;
						}
					}
					agencyDto.setListProduct(productTmp);
				}
				agencyMapById.put(agencyDto.getId(), agencyDto);
				agemcyMapByCode.put(agencyDto.getAgencyCode(), agencyDto);
			}
			RedisOperator.putToMap("agencyListMapById",agencyMapById);
			RedisOperator.putToMap("agencyListMapByAgencyCode",agemcyMapByCode);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e){
			logger.error("初始化机构信息到Redis缓存异常:",e);
			RespHelper.setFailRespStatus(respStatus,RespStatusEnum.FAIL.getMsg());
		}
		return respStatus;
	}
}
