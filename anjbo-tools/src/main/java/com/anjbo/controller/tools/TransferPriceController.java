package com.anjbo.controller.tools;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.tools.HouseAssess;
import com.anjbo.bean.tools.TransferPrice;
import com.anjbo.common.Enums;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.SendEmailWorker;
import com.anjbo.processor.HouseAssessProcessor;
import com.anjbo.service.tools.TransferPriceService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.ImageUtil;
import com.anjbo.utils.StringUtil;

/**
 * 过户价/税费
 * @author limh limh@zxsf360.com
 * @date 2015-9-8 下午05:43:41
 */
@Controller
@RequestMapping("/tools/transferPrice/v")
public class TransferPriceController {
	private static final Log log = LogFactory
			.getLog(TransferPriceController.class);
	@Resource
	private TransferPriceService transferPriceService;
	@Resource
	private ThreadPoolTaskExecutor poolTaskExecutor;

	/**
	 * 新增过户价/税费
	 * 注意 1传入uid
	 *     2传入cookieValue
	 * @Title: save 
	 * @param request
	 * @param transferPrice
	 * @return
	 * RespStatus
	 * @throws
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody
	RespDataObject<TransferPrice> save(HttpServletRequest request,
			@RequestBody TransferPrice transferPrice) {
		RespDataObject<TransferPrice> respStatus = new RespDataObject<TransferPrice>();
		String cookieValue = transferPrice.getCookieValue();
		String selectParamString = transferPrice.getSelectParam();

		try {
			// 调用第三方接口查询房产评估
			HouseAssess houseAssess = new HouseAssess();
			houseAssess.setRadiobutton(transferPrice.getObligee());
			houseAssess.setId_no(StringUtils.upperCase(transferPrice.getIdentityNo()));
			houseAssess.setOwner_name(transferPrice.getUnitName());
			houseAssess.setCert_no(transferPrice.getEstateNo());
			houseAssess.setCert_no2(transferPrice.getEstateNo());
			houseAssess.setCERTCODE(transferPrice.getAuthCode());
			houseAssess.setZslx(transferPrice.getEstateType());
			houseAssess.setSelectbdc(transferPrice.getYear());
			// String cookieValue = (String)
			// request.getSession().getAttribute("MORTGAGECOOKIEVALUE");
			houseAssess.setCookieValue(cookieValue);
			//log.info("查询房产评估获取的cookieValue=" + cookieValue);
			RespDataObject<Double> rdo = new HouseAssessProcessor()
					.getHouseAssessResult(houseAssess);
			if (!RespStatusEnum.SUCCESS.getCode().equals(rdo.getCode())) {
				respStatus.setCode(rdo.getCode());
				respStatus.setMsg(rdo.getMsg());
				return respStatus;
			}
			// 根据评估单价计算过户价
			transferPrice.setResult(rdo.getMsg());
			if (rdo.getData() != null) {
				transferPrice.setTransferPrice(Math.ceil(transferPrice
						.getArea() * rdo.getData()));// 过户价=建筑面积*单价
				// 计算税费
				if (transferPrice.getType() == 1) {
					if ("个人".equals(transferPrice.getObligee())) {
						calcTaxForPersonal(transferPrice);
					} else {// 单位
						calcTaxForUnit(transferPrice);
					}
					transferPrice.setStatus(2);// 已计算税费
				}
			} else {
				transferPrice.setStatus(1);// 查询失败
			}
			
			if (transferPrice.getIsCalcT() == 1 && transferPrice.getType() != 0) {
				//计算银行核准税费
				calcTransferPriceBank(transferPrice);
			}else{
				transferPrice.setIsCalcT(0);
			}
			// 保存过户价
			// String sid = getSid(request);
			// String deviceId = getDeviceId(request);
			// String uid = LoginCacheUtil.getUid(sid, deviceId);
			// transferPrice.setUid(uid);
			// transferPrice.setDevice(getDevice(request));
			// transferPrice.setVersion(getVersion(request));
			// transferPriceService.addTransferPrice(transferPrice);
			// new StatisticsController().statistics(request, "10-8-2");//统计
			respStatus.setCode(RespStatusEnum.SUCCESS.getCode());
			String selectParam = selectParamString;
			if ("1".equals(selectParam)) {
				respStatus.setMsg(transferPrice.getTax());
			} else {
				respStatus.setMsg(String.valueOf(transferPrice.getId()));
			}
			String email = ConfigUtil
			.getStringValue(Enums.GLOBAL_CONFIG_KEY.DOCSEARCH_EMAILS
					.toString());
			if(StringUtil.isNotEmpty(email)){
				SendEmailWorker.asyncSendEmail(poolTaskExecutor, transferPrice
						.getTitle(), transferPrice.toString(), email);
			}
		} catch (Exception e) {
			respStatus.setCode(RespStatusEnum.FAIL.getCode());
			respStatus.setMsg("系统异常，请稍后再试！");
			e.printStackTrace();
		}
		
		respStatus.setData(transferPrice);
		return respStatus;
	}
	
	/**
	 * 银行核准过户价计算
	 * @user jiangyq
	 * @date 2018年4月12日 下午2:13:48 
	 * @param transferPriceOld
	 */
	private void calcTransferPriceBank(TransferPrice transferPriceOld) {
		TransferPrice transferPrice = new TransferPrice();
		BeanUtils.copyProperties(transferPriceOld, transferPrice);
		//银行核准过户价
		transferPrice.setTransferPrice(transferPriceOld.getTransferPriceBank());
		// 计算税费
		if ("个人".equals(transferPrice.getObligee())) {
			calcTaxForPersonal(transferPrice);
		} else {// 单位
			calcTaxForUnit(transferPrice);
		}
		//将银行核准税费计算结果赋值
		transferPriceOld.setTaxBank(transferPrice.getTax());
		transferPriceOld.setIncomeTaxBank(transferPrice.getIncomeTax());
		transferPriceOld.setIncomeTaxHsBank(transferPrice.getIncomeTaxHs());
		transferPriceOld.setLandTaxBank(transferPrice.getLandTax());
		transferPriceOld.setSalesTaxBank(transferPrice.getSalesTax());
		transferPriceOld.setDeedTaxBank(transferPrice.getDeedTax());
		transferPriceOld.setTranFeesBank(transferPrice.getTranFees());
		transferPriceOld.setRegFeesBank(transferPrice.getRegFees());
		transferPriceOld.setPledgeRegFeesBank(transferPrice.getPledgeRegFees());
		transferPriceOld.setStampBank(transferPrice.getStamp());
		transferPriceOld.setAppliqueBank(transferPrice.getApplique());
		transferPriceOld.setCityBuildTaxBank(transferPrice.getCityBuildTax());
		transferPriceOld.setEducationFeesBank(transferPrice.getEducationFees());
		transferPriceOld.setPlaceEducationFeesBank(transferPrice.getPlaceEducationFees());
		transferPriceOld.setIsCalcT(1);
		
	}
	/**
	 * 区域标准
	 * 
	 * @user Mark
	 * @date 2016-9-8 上午10:31:59 
	 * @return
	 */
	private Map<String,Double> getMapSzArea(){
		Map<String,Double> map = new LinkedHashMap<String,Double>();
		map.put("罗湖区",3900000d);
		map.put("福田区",4700000d);
		map.put("南山区",4900000d);
		map.put("宝安区",3600000d);
		map.put("龙岗区",2800000d);
		map.put("盐田区",3300000d);
		map.put("光明新区",2500000d);
		map.put("坪山新区",2000000d);
		map.put("龙华新区",3200000d);
		map.put("大鹏新区",2300000d);
		return map;
	}
	/**
	 * 计算税费规则-权利人为个人
	 * @Title: calcTaxForPersonal 
	 * @param transferPrice
	 * void
	 * @throws
	 */
	private void calcTaxForPersonal(TransferPrice transferPrice) {
		double tax = 0;
		int range = transferPrice.getRange();// 购房期限
		double area = transferPrice.getArea();// 建筑面积
		double tp = transferPrice.getTransferPrice();// 过户价
		double rp = transferPrice.getRegPrice();// 原价（房产证登记价）
		double trp = tp - rp;
		trp = trp < 0 ? 0 : trp;// 过户价-原价【房产证登记价】
		
		//普通住宅：建筑面积≤144㎡并且套内面积≤120㎡并且过户价≤区域标准（都满足）
		Double szAreaPrice = getMapSzArea().get(transferPrice.getSzArea());
		if(szAreaPrice==null){
			szAreaPrice = 0d;
		}
		double withinArea = transferPrice.getWithinArea();
		boolean pt = area<=144&&tp<=szAreaPrice;
		if(withinArea>0&&pt){//有套内面积的情况
			pt = withinArea<=120;
		}
		transferPrice.setResidenceType(pt?0:1);//确定住宅类型
		// 增值税（原营业税）
		if (range == 0) {// 未满俩年：过户价÷1.05×5%
			transferPrice.setSalesTax(tp/1.05 * 0.05);
		} else {// 满俩年
			if (pt) {// 普通住宅：免征

			} else {// 非普通住宅：（过户价-登记价） ÷1.05*5%
				transferPrice.setSalesTax(trp/1.05 * 0.05);
			}
		}
		double zz = transferPrice.getSalesTax();//增值税
		tax += zz;
		
		//城市维护建设税  增值税×7%
		transferPrice.setCityBuildTax(zz*0.07);
		tax += transferPrice.getCityBuildTax();
		//教育费附加       增值税×3%
		transferPrice.setEducationFees(zz*0.03);
		tax += transferPrice.getEducationFees();
		//地方教育附加     增值税×2%
		transferPrice.setPlaceEducationFees(zz*0.02);
		tax += transferPrice.getPlaceEducationFees();
		
		// 个税
		if (range != 2 || transferPrice.getOnlyHouse() != 1) {// 满5年且唯一住房可免个税
			// 核定
			if (pt) {// 普通住宅（有增值税）：  （过户价-增值税）×1%
				transferPrice.setIncomeTax((tp-zz) * 0.01);
			} else {// 非普通住宅：（过户价-增值税）×1.5%
				transferPrice.setIncomeTax((tp-zz) * 0.015);
			}
			// 核实（过户价-登记价）÷1.05*20%
			transferPrice.setIncomeTaxHs(trp/1.05 * 0.2);
		}
		// tax+=transferPrice.getIncomeTax();
		
		// 契税
		if (transferPrice.getFirstHouse() == 1) {// 首套
			if (area <= 90) {// 建筑面积≤90㎡，有增值税     （过户价-增值税）×1%
				transferPrice.setDeedTax((tp-zz) * 0.01);
			} else {// 建筑面积＞90㎡，有增值税     （过户价-增值税）×1.5%
				transferPrice.setDeedTax((tp-zz) * 0.015);
			}
		} else {// 二套，有增值税：    （过户价-增值税）×3%
			transferPrice.setDeedTax((tp-zz) * 0.03);
		}
		
		//增加个税和契税不能小于0
		if (transferPrice.getIncomeTax() < 0) {
			transferPrice.setIncomeTax(0);
		}
		if (transferPrice.getIncomeTaxHs() < 0) {
			transferPrice.setIncomeTaxHs(0);	
		}
		if (transferPrice.getDeedTax() < 0) {
			transferPrice.setDeedTax(0);
		}
		
		tax += transferPrice.getDeedTax();
		// 交易手续费
//		transferPrice.setTranFees(area * 4);// 4元/㎡
//		tax += transferPrice.getTranFees();
		// 登记费
		transferPrice.setRegFees(80);// 80元
		tax += transferPrice.getRegFees();
		// 贴花
		transferPrice.setApplique(5);// 5元
		tax += transferPrice.getApplique();
		// 税费合计
		double itHd = transferPrice.getIncomeTax();
		double itHs = transferPrice.getIncomeTaxHs();
		if (itHd == 0 && itHs == 0) {
			tax = new BigDecimal(tax).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			transferPrice.setTax(String.valueOf(tax));
		} else {
			double taxHd = new BigDecimal(tax + itHd).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			double taxHs = new BigDecimal(tax + itHs).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			transferPrice.setTax(taxHd + "~" + taxHs);
		}
	}

	/**
	 * 计算税费规则-权利人为单位
	 * @Title: calcTaxForUnit 
	 * @param transferPrice
	 * void
	 * @throws
	 */
	private void calcTaxForUnit(TransferPrice transferPrice) {
		double tax = 0;
		double area = transferPrice.getArea();// 建筑面积
		double tp = transferPrice.getTransferPrice();// 过户价
		double rp = transferPrice.getRegPrice();// 原价（房产证登记价）
		double trp = tp - rp;
		trp = trp < 0 ? 0 : trp;// 过户价-原价【房产证登记价】

		// 增值税（原营业税）
		transferPrice.setSalesTax(trp * 0.05);// （过户价-登记价）×5%
		double zz = transferPrice.getSalesTax();//增值税
		tax += zz;

		//城市维护建设税  增值税×7%
		transferPrice.setCityBuildTax(zz*0.07);
		tax += transferPrice.getCityBuildTax();
		//教育费附加       增值税×3%
		transferPrice.setEducationFees(zz*0.03);
		tax += transferPrice.getEducationFees();
		//地方教育附加     增值税×2%
		transferPrice.setPlaceEducationFees(zz*0.02);
		tax += transferPrice.getPlaceEducationFees();
		
		// 契税
		double tsp = tp - transferPrice.getSalesTax();
		tsp = tsp < 0 ? 0 : tsp;// 过户价-本次增值税
		if (transferPrice.getFirstHouse() == 1) {// 首套
			if (area <= 90) {//  建筑面积≤90㎡  （过户价-增值税）*1%
				transferPrice.setDeedTax((tp-zz) * 0.01);
			} else {// 建筑面积＞90㎡  （过户价-增值税）*1.5%
				transferPrice.setDeedTax((tp-zz) * 0.015);
			}
		} else {// 二套     （过户价-增值税）*3%
			transferPrice.setDeedTax((tp-zz) * 0.03);
		}
		tax += transferPrice.getDeedTax();

		// 交易手续费
//		transferPrice.setTranFees(area * 4);// 4元/㎡
//		tax += transferPrice.getTranFees();

		// 登记费
		transferPrice.setRegFees(80);// 80元
		tax += transferPrice.getRegFees();

		// 贴花
		transferPrice.setApplique(5);// 5元
		tax += transferPrice.getApplique();

		// 印花
		transferPrice.setStamp((tp-zz) * 0.0005);// （过户价-增值税）×0.05%
		tax += transferPrice.getStamp();

		// 土地增值税
		// 核定：（过户价-本次增值税）*10%
		double minLandTax = new BigDecimal(tsp * 0.1).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
		minLandTax = minLandTax < 0 ? 0 : minLandTax;
		// 核实
		double maxLandTax = 0d;
		double op = (tp - rp) / rp;// （过户价-原价）/原价
		if (op <= 0.5) {// 若（过户价-原价）/原价≤50%，（过户价-本次增值税-原价）*30%
			maxLandTax = (tsp - rp) * 0.3;
		} else if (op <= 1) {// 若50%＜（过户价-原价）/原价≤100%，（过户价-本次增值税-原价）*40%
			maxLandTax = (tsp - rp) * 0.4;
		} else if (op <= 2) {// 若100%＜（过户价-原价）/原价≤200%，（过户价-本次增值税-原价）*50%
			maxLandTax = (tsp - rp) * 0.5;
		} else {// 若（过户价-原价）/原价﹥200%，（过户价-本次增值税-原价）*60%
			maxLandTax = (tsp - rp) * 0.6;
		}
		maxLandTax = new BigDecimal(maxLandTax).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
		maxLandTax = maxLandTax < 0 ? 0 : maxLandTax;
		double minTax = new BigDecimal(tax + minLandTax).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
		double maxTax = new BigDecimal(tax + maxLandTax).setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
		if (minLandTax == 0 && maxLandTax == 0) {
			transferPrice.setLandTax("0");
			transferPrice.setTax(String.valueOf(minTax));
		} else {
			transferPrice.setLandTax(minLandTax + "~" + maxLandTax);
			transferPrice.setTax(minTax + "~" + maxTax);// 税费合计
		}
	}

	/**
	 * 更新税费
	 * @Title: updateTax 
	 * @param request
	 * @param transferPrice
	 * @return
	 * RespStatus
	 * @throws
	 */
	@RequestMapping(value = "/updateTax")
	public @ResponseBody
	RespStatus updateTax(@RequestBody TransferPrice transferPrice) {
		RespStatus respStatus = new RespStatus();
		try {
			TransferPrice tfp = transferPriceService
					.selectTransferPrice(transferPrice.getId());
			if (tfp == null) {
				respStatus.setCode(RespStatusEnum.FAIL.getCode());
				respStatus.setMsg("数据不存在");
				return respStatus;
			}
			if (tfp.getStatus() == 1) {
				respStatus.setCode(RespStatusEnum.FAIL.getCode());
				respStatus.setMsg("查询失败，无法计算税费");
				return respStatus;
			}
			if (tfp.getStatus() == 2) {
				respStatus.setCode(RespStatusEnum.FAIL.getCode());
				respStatus.setMsg("该数据已计算税费");
				return respStatus;
			}
			tfp.setRange(transferPrice.getRange());
			tfp.setRegPrice(transferPrice.getRegPrice());
			tfp.setResidenceType(transferPrice.getResidenceType());
			tfp.setOnlyHouse(transferPrice.getOnlyHouse());
			tfp.setFirstHouse(transferPrice.getFirstHouse());
			
			tfp.setSzArea(transferPrice.getSzArea());
			tfp.setWithinArea(transferPrice.getWithinArea());
			tfp.setCityBuildTax(transferPrice.getCityBuildTax());
			tfp.setEducationFees(transferPrice.getEducationFees());
			tfp.setPlaceEducationFees(transferPrice.getPlaceEducationFees());
			tfp.setTransferPriceBank(transferPrice.getTransferPriceBank());
			tfp.setIsCalcT(transferPrice.getIsCalcT());
			
			if ("个人".equals(tfp.getObligee())) {
				calcTaxForPersonal(tfp);
			} else {// 单位
				calcTaxForUnit(tfp);
			}
			tfp.setStatus(2);// 已计算税费
			
			if (transferPrice.getIsCalcT() == 1) {
				//计算银行核准税费
				calcTransferPriceBank(tfp);
			}
			
			//transferPriceService.updateTax(tfp);
			respStatus.setCode(RespStatusEnum.SUCCESS.getCode());
			respStatus.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			respStatus.setCode(RespStatusEnum.FAIL.getCode());
			respStatus.setMsg(RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
		}
		return respStatus;
	}

	/**
	 * 生成验证码并获取第三方服务器的cookie
	 * @Title: authCode 
	 * @param request
	 * @return
	 * RespStatus
	 * @throws
	 */
	@RequestMapping(value = "/authCode")
	public @ResponseBody
	RespDataObject<Map<String, Object>> authCode(HttpServletRequest request) {
		RespDataObject<Map<String, Object>> status = new RespDataObject<Map<String, Object>>();
		status.setCode(RespStatusEnum.FAIL.getCode());
		status.setMsg(RespStatusEnum.FAIL.getCode());
		try {
			String url = "/code/transferprice/" + UUID.randomUUID().toString()
					+ ".jpg";
			String path = request.getSession().getServletContext()
					.getRealPath(url);
			String cookieValue = ImageUtil.saveToFile(
					HouseAssessProcessor.authCodeUrl, path);
			// request.getSession().setAttribute("MORTGAGECOOKIEVALUE",
			// cookieValue);
			log.info("生成验证码返回cookieValue=" + cookieValue);
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("cookieValue", cookieValue);
			respMap.put("url", url + "?" + Math.random());
			status.setData(respMap);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.info("生成验证码异常");
			e.printStackTrace();
		}
		return status;
	}
	/**
	 * 获取所在区域
	 * 
	 * @user Mark
	 * @date 2016-9-8 上午11:08:39 
	 * @return
	 */
	@RequestMapping(value = "/szArea")
	public @ResponseBody
	RespDataObject<Map<String, Double>> szArea() {
		RespDataObject<Map<String, Double>> status = new RespDataObject<Map<String, Double>>();
		status.setData(getMapSzArea());
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
	
	/**
	 * 过户价/税费分页
	 * @Title: page 
	 * @param transferPrice
	 * @return
	 * RespData<TransferPrice>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespData<TransferPrice> page(HttpServletRequest request,
			@RequestBody TransferPrice transferPrice) {
		RespData<TransferPrice> respDataObject = new RespData<TransferPrice>();
		List<TransferPrice> list = transferPriceService.selectTransferPricePage(transferPrice);
		for (TransferPrice tp : list) {
			if(tp.getTax()!=null && tp.getTax().indexOf('~') > -1){
				 double hd = NumberUtils.toDouble(tp.getTax().split("~")[0]);
				 double hs = NumberUtils.toDouble(tp.getTax().split("~")[1]);
				 if( hs > hd){
				 	tp.setTax(String.valueOf(hd));
				 }else{
					tp.setTax(String.valueOf(hs));
				 }
			}
			
			if(tp.getTaxBank()!=null && tp.getTaxBank().indexOf('~') > -1){
				 double hd = NumberUtils.toDouble(tp.getTaxBank().split("~")[0]);
				 double hs = NumberUtils.toDouble(tp.getTaxBank().split("~")[1]);
				 if( hs > hd){
				 	tp.setTaxBank(String.valueOf(hd));
				 }else{
					tp.setTaxBank(String.valueOf(hs));
				 }
			}
		}
		respDataObject.setData(list);
		respDataObject.setCode(RespStatusEnum.SUCCESS.getCode());
		respDataObject.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return respDataObject;
	}
	/**
	 * 过户价/税费总记录数
	 * @Title: pageCount 
	 * @param transferPrice
	 * @return
	 * RespData<TransferPrice>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/pageCount")
	public RespDataObject<Integer> pageCount(HttpServletRequest request,
			@RequestBody TransferPrice transferPrice) {
		RespDataObject<Integer> respDataObject = new RespDataObject<Integer>();
		int count = transferPriceService.selectTransferPricePageCount(transferPrice);
		respDataObject.setData(count);
		respDataObject.setCode(RespStatusEnum.SUCCESS.getCode());
		respDataObject.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return respDataObject;
	}
	
	/**
	 * 过户价/税费详情
	 * @user Object
	 * @date 2016-11-28 下午06:05:16 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail")
	public TransferPrice detail(@RequestBody Map<String, Object> params) {
		TransferPrice transferPrice = transferPriceService.selectTransferPrice(MapUtils.getIntValue(params, "id"));
		return transferPrice;
	}
	
	/**
	 * 三价合一计算税费
	 * @param request
	 * @param transferPrice
	 * @return
	 * RespStatus
	 * @throws
	 */
	@RequestMapping(value = "/calcTransferPrice")
	public @ResponseBody
	RespDataObject<TransferPrice> calcTransferPrice(HttpServletRequest request, @RequestBody TransferPrice transferPrice) {
		RespDataObject<TransferPrice> respStatus = new RespDataObject<TransferPrice>();
		String selectParamString = transferPrice.getSelectParam();
		try {
			// 计算税费
			transferPrice.setIsCalcT(1);
			transferPrice.setTransferPrice(transferPrice.getTransferPriceBank());
			calcTransferPriceBank(transferPrice);
			transferPrice.setStatus(2);// 已计算税费
			//transferPriceService.addTransferPriceBank(transferPrice);
			respStatus.setCode(RespStatusEnum.SUCCESS.getCode());
			String selectParam = selectParamString;
			if ("1".equals(selectParam)) {
				respStatus.setMsg(transferPrice.getTaxBank());
			} else {
				respStatus.setMsg(String.valueOf(transferPrice.getId()));
			}
		} catch (Exception e) {
			respStatus.setCode(RespStatusEnum.FAIL.getCode());
			respStatus.setMsg("系统异常，请稍后再试！");
			e.printStackTrace();
		}
		respStatus.setData(transferPrice);
		return respStatus;
	}
}
