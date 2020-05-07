package com.anjbo.service.impl;

import com.anjbo.bean.product.data.ProductBusinfoDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.ProductBusinfoBaseMapper;
import com.anjbo.dao.ProductListBaseMapper;
import com.anjbo.service.ProductBusinfoBaseService;
import com.anjbo.utils.HttpUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ProductBusinfoBaseServiceImpl implements ProductBusinfoBaseService {

	@Resource
	private ProductBusinfoBaseMapper productBusinfoBaseMapper;
	
	@Resource
	private ProductListBaseMapper productListBaseMapper;
	
	@Override
	public int insertProductBusinfoBase(ProductBusinfoDto productBusinfoDto,boolean flag) {
		String tblName = productBusinfoDto.getTblName();
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_businfo";
		productBusinfoDto.setTblDataName(tblDataName);
		String urls = productBusinfoDto.getUrls();
		String[] urlss = urls.split(",");
		int index = 0;
		try {
			index = productBusinfoBaseMapper.selectLastIndex(productBusinfoDto);
		} catch (Exception e) {
			index = 0;
		}
		int id = 0;
		List<Map<String,Object>> images = productBusinfoDto.getImages();
		if(null!=images&&images.size()>0){
			String name = "";
			String ext = "";
			String tempUrl = "";
			RespDataObject<Map<String, Object>> resp = null;
			for (Map<String,Object> m:images){
				tempUrl = MapUtils.getString(m,"name");
				name =  tempUrl.substring(tempUrl.lastIndexOf("/")+1, tempUrl.lastIndexOf("."));
				ext = tempUrl.substring(tempUrl.lastIndexOf(".")+1);
				index += 1;
				m.put("createUid",productBusinfoDto.getCreateUid());
				m.put("orderNo",productBusinfoDto.getOrderNo());
				m.put("typeId",productBusinfoDto.getTypeId());
				m.put("ext",ext);
				m.put("index",index);
				m.put("tblDataName",productBusinfoDto.getTblDataName());
				m.put("name",name);
				if(flag){
					resp = new HttpUtil().getRespDataObject(Constants.LINK_CREDIT, "/credit/product/data/cm/assess/v/busInfoTask", m, Map.class);
					if(RespStatusEnum.FAIL.getCode().equals(resp.getCode())){
						return 0;
					}
				}
			}
			id = productBusinfoBaseMapper.batchInsertProductBusinfoBase(images);
		} else {
			for (String tempUrl : urlss) {
				ProductBusinfoDto busInfoDto = new ProductBusinfoDto();
				busInfoDto.setCreateUid(productBusinfoDto.getCreateUid());
				busInfoDto.setOrderNo(productBusinfoDto.getOrderNo());
				busInfoDto.setTypeId(productBusinfoDto.getTypeId());
				busInfoDto.setUrl(tempUrl);
				String name =  tempUrl.substring(tempUrl.lastIndexOf("/")+1, tempUrl.lastIndexOf("."));
				String ext = tempUrl.substring(tempUrl.lastIndexOf(".")+1);
				busInfoDto.setName(name);
				busInfoDto.setExt(ext);
				busInfoDto.setTblDataName(productBusinfoDto.getTblDataName());
				busInfoDto.setCreateTime(new Date());
				//busInfoDto.setIsOrder(isOrder);
				index += 1;
				busInfoDto.setIndex(index);

				if(flag){
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("url", tempUrl);
					map.put("orderNo", productBusinfoDto.getOrderNo());
					map.put("typeId", productBusinfoDto.getTypeId());
					RespDataObject<Map<String, Object>> resp =new HttpUtil().getRespDataObject(Constants.LINK_CREDIT, "/credit/product/data/cm/assess/v/busInfoTask", map, Map.class);
					if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
						productBusinfoBaseMapper.insertProductBusinfoBase(busInfoDto);
						id = busInfoDto.getId();
					}else{
						return 0;
					}
				}else{
					productBusinfoBaseMapper.insertProductBusinfoBase(busInfoDto);
					id = busInfoDto.getId();
				}
			}
		}
		return id;
	}

	@Override
	public List<Map<String, Object>> selectProductBusinfoBase(
			Map<String, Object> map) {
		String tblName = MapUtils.getString(map, "tblName");
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_businfo";
		map.put("tblDataName", tblDataName);
		List<Map<String, Object>> list = productBusinfoBaseMapper.selectProductBusinfoBase(map);
		return list;
	}

	@Override
	public int updateProductBusinfo(ProductBusinfoDto productBusinfoDto) {
		return productBusinfoBaseMapper.updateProductBusinfo(productBusinfoDto);
	}

	@Override
	public int move(Map<String,Object> map) {
		List<String> businfoIds = (List<String>)map.get("businfoIds");
		Integer toTypeId = MapUtils.getInteger(map, "toTypeId");
		String tblName = MapUtils.getString(map, "tblName");
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_businfo";
		int index = 0;
		try {
			ProductBusinfoDto  busInfo = new ProductBusinfoDto();
			busInfo.setTypeId(toTypeId);
			busInfo.setOrderNo(MapUtils.getString(map, "orderNo"));
			busInfo.setTblDataName(tblDataName);
			index = productBusinfoBaseMapper.selectLastIndex(busInfo);
		} catch (Exception e) {
			index = 0;
		}
		for (String businfoId : businfoIds) {
			ProductBusinfoDto busInfoDto = new ProductBusinfoDto();
			busInfoDto.setId(Integer.parseInt(businfoId));
			busInfoDto.setTypeId(toTypeId);
			index += 1;
			busInfoDto.setIndex(index);
			busInfoDto.setTblDataName(tblDataName);
			productBusinfoBaseMapper.updateProductBusinfo(busInfoDto);
		}
		return 1;
	}

	@Override
	public int deleteImgByIds(Map<String,Object> map) {
		List<String> businfoIds = (List<String>)map.get("businfoIds");
		String ids = "";
		for (String businfoId : businfoIds) {
			ids+=","+businfoId;
		}
		ids=ids.substring(1);
		map.put("ids", ids);
		String tblName = MapUtils.getString(map, "tblName");
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_businfo";
		map.put("tblDataName", tblDataName);
		return productBusinfoBaseMapper.deleteImgByIds(map);
	}
	/**
	 * 校验必备影像资料
	 * @param map(key=productCode:产品code,key=tblName,key=orderNo)
	 * @return true(校验通过),false(校验失败)
	 */
	public boolean verificationImgage(Map<String,Object> map){
		List<Map<String,Object>> listImg = selectProductBusinfoBase(map);
		HttpUtil httpUtil = new HttpUtil();
		List<Map<String,Object>> listType = httpUtil.getList(Constants.LINK_CREDIT,"/credit/config/page/businfo/type/selectNecessaryBusinfo",map,Map.class);
		if(null==listType||listType.size()<=0){
			return true;
		} else if(null==listImg||listImg.size()<=0){
			return false;
		} else {
			Integer id = -1;
			Integer typeId = -1;
			for (Map<String,Object> m:listImg){
				typeId = MapUtils.getInteger(m,"typeId",-1);
				Iterator<Map<String,Object>> it =  listType.iterator();
				while (it.hasNext()){
					Map<String,Object> mm = it.next();
					id = MapUtils.getInteger(mm, "id", 0);
					if(id==typeId||id.equals(typeId)){
						it.remove();
						break;
					}

				}
				if(null==listType||listType.size()<=0){
					return true;
				}
			}
		}
		return false;
	}
}
