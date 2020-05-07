package com.anjbo.service.impl;

import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.dao.ProductDataBaseMapper;
import com.anjbo.dao.ProductListBaseMapper;
import com.anjbo.service.ProductDataBaseService;
import com.anjbo.service.sl.OrderBaseService;
import com.anjbo.utils.IdcardUtils;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.UidUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class ProductDataBaseServiceImpl implements ProductDataBaseService {

	Logger log =Logger.getLogger(ProductDataBaseService.class);
	
	@Resource
	private ProductDataBaseMapper productDataBaseMapper; 
	
	@Resource
	private ProductListBaseMapper productListBaseMapper; 
	
	@Resource 
	private OrderBaseService orderBaseService;
	
	@Override
	@Transactional
	public int insertProductDataBase(ProductDataDto productDataDto) {
		return productDataBaseMapper.insertProductDataBase(productDataDto);
	}

	@Override
	@Transactional
	public String updateProductDataBase(ProductDataDto productDataDto) {
		String tblName = productDataDto.getTblName();
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_data";
		productDataDto.setTblDataName(tblDataName);
		Map<String,Object> dataBase = productDataBaseMapper.selectProductDataBase(productDataDto);
		String orderNo=productDataDto.getOrderNo();
		Map<String,Object> data = productDataDto.getData();
		//数据不为空时录入
		if(MapUtils.isNotEmpty(data)){
			Iterator<Map.Entry<String, Object>> iter = data.entrySet().iterator();
			int i =0 ;
			while (iter.hasNext()) {
				Map.Entry<String, Object> m = iter.next();
				if(!"".equals(m.getValue())&&m.getValue()!=null){
					i++;
					break;
				}
			}
			if(i==0){
				return "error";
			}
		}
		if(MapUtils.isNotEmpty(data)){
			data.remove("additional");
		}
		String dataStr = JSONObject.fromObject(data).toString();
		if(MapUtils.isEmpty(data)){
			dataStr = null;
		}
		productDataDto.setDataStr(dataStr);
		if(dataBase!=null){
			productDataBaseMapper.updateProductDataBase(productDataDto);
		}else{
			if(StringUtils.isBlank(orderNo)){
				orderNo = UidUtil.generateOrderId();
				productDataDto.setOrderNo(orderNo);
			}
			productDataBaseMapper.insertProductDataBase(productDataDto);
		}
		//初始化其他表的默认值
		Map<String,Object> otherData = productDataDto.getOtherData();
		if(MapUtils.isNotEmpty(otherData)){
			Iterator<Map.Entry<String, Object>> it = otherData.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				tblName = entry.getKey();
				if(tblName.contains("_list")){//更新列表
					Map<String,Object> listValue = (Map<String, Object>) entry.getValue();
					if(MapUtils.isNotEmpty(listValue)){
						Iterator<Map.Entry<String, Object>> listMap = listValue.entrySet().iterator();
						String key = "";
						String value = "";
						String keyValue = "";
						while(listMap.hasNext()){
							Map.Entry<String, Object> temp = listMap.next();
							//特殊处理，包含branchCompanyCode时，将branchCompanyCode的值复制给branchCompany
							//云按揭分公司特殊处理更新到列表
							if("branchCompany".equals(temp.getKey())){
								continue;
							}
							if("branchCompanyCode".equals(temp.getKey())){
								String k =temp.getValue()==null?"":","+"branchCompany";
								key += k;
								String val =temp.getValue()==null?"":",'"+temp.getValue()+"'";
								value += val;
								String keyVal = null==temp.getValue()?"":",branchCompany='"+temp.getValue()+"'";
								keyValue += keyVal;
								continue;
							}
							String k =temp.getValue()==null?"":","+temp.getKey();
							key += k;
							String val =temp.getValue()==null?"":",'"+temp.getValue()+"'";
							value += val;
							String keyVal = temp.getKey()==null||null==temp.getValue()?"":","+temp.getKey()+"='"+temp.getValue()+"'";
							keyValue += keyVal;
						}
						//更新业主姓名，房产证号到appShowValue1，appShowValue2
						if(data.get("ownerName")!=null){
							String k =",appShowValue1";
							key += k;
							String val =data.get("ownerName")==null?"":",'"+data.get("ownerName")+"'";
							value += val;
							String keyVal = data.get("ownerName")==null?"":",appShowValue1='"+data.get("ownerName")+"'";
							keyValue += keyVal;
						}
						if(data.get("estateNo")!=null&&data.get("estateType")!=null){
							String appShowValue2;
							if(MapUtils.getString(data, "estateType").contains("2015")){
								appShowValue2 = "不动产证-2015-"+data.get("estateNo");
							}else if(MapUtils.getString(data, "estateType").contains("2016")){
								appShowValue2 = "不动产证-2016-"+data.get("estateNo");
							}else if(MapUtils.getString(data, "estateType").contains("2017")){
								appShowValue2 = "不动产证-2017-"+data.get("estateNo");
							}else{
								appShowValue2 = "房地产权证-"+data.get("estateNo");
							}
							String k =",appShowValue2";
							key += k;
							String val =data.get("estateNo")==null?"":",'"+appShowValue2+"'";
							value += val;
							String keyVal = data.get("estateNo")==null?"":",appShowValue2='"+appShowValue2+"'";
							keyValue += keyVal;
						}
						log.info("updateProductListBase :--- key=="+key+"keyValue=="+keyValue+"value=="+value);
						if("".equals(key)||"".equals(keyValue)||"".equals(value)){
							continue;
						}
						if(StringUtil.isNotEmpty(key) && StringUtil.isNotEmpty(value) && StringUtil.isNotEmpty(keyValue)){
							try {
								log.info("updateProductDataBase :key-----yes");
								key = key.substring(1);
								productDataDto.setKey(key);
								value = value.substring(1);
								productDataDto.setValue(value);
								keyValue = keyValue.substring(1);
								productDataDto.setKeyValue(keyValue);
							} catch (Exception e) {
								e.printStackTrace();
							}
							productDataDto.setTblDataName(tblName);
							Map<String,Object> mapList = productListBaseMapper.selectProductListBaseByOrderNo(productDataDto);
							if(MapUtils.isNotEmpty(mapList)){
								productListBaseMapper.updateProductListBaseByKey(productDataDto);
							}else{
								if(StringUtils.isBlank(productDataDto.getSource())){
									productDataDto.setSource("信贷系统");
								}
								if(!productDataDto.getTblName().contains("tbl_sm")&&StringUtil.isNotBlank(productDataDto.getPreviousHandler())) {
									String k = ",createPeopleName";
									key += k;
									String val = ",'" + productDataDto.getPreviousHandler() + "'";
									value += val;
									productDataDto.setKey(key);
									productDataDto.setValue(value);
								}
								productListBaseMapper.insertProductListBaseByKey(productDataDto);
							}
						}
					}
				}else{//录入初始化其他表
					Map<String,Object> v = (Map<String, Object>) entry.getValue();
					if(MapUtils.isNotEmpty(v)){
						JSONObject dataJson = JSONObject.fromObject(v);
						if("tbl_cm_customer".equals(tblName)){//初始化年龄，出生日期
							String certificateNo = dataJson.getString("certificateNo");
							if(StringUtils.isNotBlank(certificateNo)&&(certificateNo.length()==15||certificateNo.length()==18)){
								String sexId = IdcardUtils.getGenderByIdCard(certificateNo);
								String sexCode;
								String sex;
								if("M".equals(sexId)){
									sexCode ="女";
									sex="2";
								}else{
									sexCode="男";
									sex="1";
								}
								String birthBy = IdcardUtils.getBirthByIdCard(certificateNo);
								StringBuilder  sb = new StringBuilder (birthBy);  
								sb.insert(6, "-");
								sb.insert(4, "-");
								String dateBirth = sb.toString();
								dataJson.put("sexCode", sexCode);
								dataJson.put("sex", sex);
								dataJson.put("dateBirth", dateBirth);
							}
						}
						dataStr = dataJson.toString();
//						tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_data";
						ProductDataDto product = new ProductDataDto();
						product.setTblName(tblName);
						product.setDataStr(dataStr);
						product.setTblDataName(tblDataName);
						product.setCreateUid(productDataDto.getCreateUid());
						product.setOrderNo(orderNo);
						Map<String,Object> otherMap = productDataBaseMapper.selectProductDataBase(product);
						if(MapUtils.isEmpty(otherMap)){
							productDataBaseMapper.insertProductDataBase(product);
						}else{
							productDataBaseMapper.updateProductDataBase(product);
						}
					}
				}
			}
		}
		
		if("01".equals(productDataDto.getProductCode()) || "02".equals(productDataDto.getProductCode())){			
			orderBaseService.saveSlData(productDataDto);
		}
		return orderNo;
	}

	@Override
	@Transactional
	public Map<String, Object> selectProductDataBase(ProductDataDto productDataDto) {
		String tblName = productDataDto.getTblName();
		System.out.println(tblName.indexOf("_",5));
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_data";
		productDataDto.setTblDataName(tblDataName);
		Map<String,Object>  map= productDataBaseMapper.selectProductDataBase(productDataDto);
		if(map==null || map.get("data")==null || MapUtils.getString(map, "data").equals("")){
			if("tbl_cm_forensicsMortgage".equals(tblName)){//设置新证年份初始值
				map = new HashMap<String, Object>();
				Calendar date =Calendar.getInstance();
				String newYear = String.valueOf(date.get(Calendar.YEAR));
				map.put("data", "{\"newYear\":\""+newYear+"\"}");
			}else{
				map = new HashMap<String, Object>();
				map.put("data", "{}");
			}
		}
		return map;
	}

	@Override
	public ProductDataDto selectProductDataBaseDto(ProductDataDto productDataDto) {
		String tblName = productDataDto.getTblName();
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_data";
		productDataDto.setTblDataName(tblDataName);
		ProductDataDto productData = productDataBaseMapper.selectProductDataBaseDto(productDataDto);
		if(productData!=null){
			String dataStr = productData.getDataStr();
			JSONObject json=JSONObject.fromObject(dataStr);
			Map<String, Object> data = (Map)json;
			productData.setData(data);
		}
		return productData;
	}

	@Override
	public List<Map<String, Object>> selectProductDataBaseList(
			ProductDataDto productDataDto) {
		String tblName = productDataDto.getTblName();
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_data";
		productDataDto.setTblDataName(tblDataName);
		List<Map<String, Object>> list = productDataBaseMapper.selectProductDataBaseList(productDataDto);
		if(list==null || list.size()==0){
			list = new ArrayList<Map<String,Object>>();
		}
		return list;
	}
	
}
