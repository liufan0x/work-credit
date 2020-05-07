package com.anjbo.service.yntrust.impl;


import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.yntrust.YntrustBorrowDto;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.ThirdApiConstants;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.dao.yntrust.YntrustBorrowMapper;
import com.anjbo.dao.yntrust.YntrustImageMapper;
import com.anjbo.service.yntrust.YntrustImageService;
import com.anjbo.utils.StringUtil;
import com.google.gson.Gson;

import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service
public class YntrustImageServiceImpl implements YntrustImageService {

    @Resource
    private YntrustImageMapper yntrustImageMapper;
    @Resource
    private  OrderApi orderApi;
    @Resource
    private YntrustBorrowMapper yntrustBorrowMapper;
    @Override
    public List<Map<String,Object>> list(Map<String, Object> map) {
        map.put("isDelete",2);
        map.put("isPlus",1);
        List<Map<String,Object>> list = yntrustImageMapper.list(map);
        return list;
    }

    @Override
    public List<Map<String, Object>> lisMas(Map<String,Object> map) {
    	return yntrustImageMapper.lisMas(map);
    }
    
    /**
     * 影像资料分类
     * @param list
     * @return
     */
    public Map<String,Object> classify(String orderNo, List<Map<String,Object>> list,String ynProductCode){
        Map<String,Object> map = new TreeMap<String,Object>();
        Map<String,Object> data = null;
        String[] imgType = ThirdApiConstants.YNTRUST_IMG_ONE.split(",");                
        String[] imgNameType = ThirdApiConstants.YNTRUST_IMG_NAME_ONE.split(",");       
        if ("I22500".equals(ynProductCode)) {
        	imgType = ThirdApiConstants.YNTRUST_IMG_TWO.split(",");
        	imgNameType = ThirdApiConstants.YNTRUST_IMG_NAME_TWO.split(",");
		}
        
        for (int i = 0; i < imgType.length; i++) {
        	data = new HashMap<String,Object>();
        	data.put("img",new ArrayList<Map<String,Object>>());
            data.put("name", imgNameType[i]);
            map.put(imgType[i],data);
		}
        String typeId = getTypeId(orderNo,map);
        if(null!=list&&list.size()>0){
            mappingImg(map,list);
        } else {
            if(StringUtils.isNotBlank(typeId)){
                getBusinfoTypeTree(typeId,orderNo,map);
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("orderNo",orderNo);
                param.put("isDelete",2);
                List<Map<String,Object>> tmpList = yntrustImageMapper.list(param);
                mappingImg(map,tmpList);
            }
        }
        return map;
    }

    private void mappingImg(Map<String,Object> map, List<Map<String,Object>> list){
        Map<String,Object> data = new HashMap<String,Object>();
        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()){
            Map<String,Object> m = iterator.next();
            Integer isDelete = MapUtils.getInteger(m,"isDelete",null);
            if(null!=isDelete&&1==isDelete){
                iterator.remove();
                continue;
            }
            if(map.containsKey(MapUtils.getString(m,"type"))){
                data = MapUtils.getMap(map, MapUtils.getString(m,"type"));
                List<Map<String,Object>> t = (List<Map<String,Object>>) MapUtils.getObject(data,"img");
                t.add(m);
                data.put("img",t);
                map.put(MapUtils.getString(m,"type"),data);
            }
        }
    }

    public String getTypeId(String orderNo, Map<String,Object> map){
        BaseBorrowDto borrow = new BaseBorrowDto();
        borrow.setOrderNo(orderNo);
        borrow = orderApi.findBorrowByOrderNo(orderNo);

        if(null==borrow)return "";
        Set<String> keys = map.keySet();
        for(String key:keys){
            Map<String,Object> data = MapUtils.getMap(map,key);
            if(borrow.getProductCode().equals("01")){
                if(key.equals("A")){
                    data.put("typeId",10181);
                } else if(key.equals("B")){
                    data.put("typeId",101810);
                } else if(key.equals("D")){
                    data.put("typeId",102050);
                }
            } else if(borrow.getProductCode().equals("02")){
                if(key.equals("A")){
                    data.put("typeId",20201);
                } else if(key.equals("B")){
                    data.put("typeId",202010);
                } else if(key.equals("D")){
                    data.put("typeId",203040);
                }
            } else if(borrow.getProductCode().equals("03")){
                if(key.equals("A")){
                    data.put("typeId",60181);
                } else if(key.equals("B")){
                    data.put("typeId",601810);
                } else if(key.equals("D")){
                    data.put("typeId",910205);
                }
            }

        }
        String typeId = "";
        if(borrow.getProductCode().equals("01")){
            typeId = "10181";
        } else if(borrow.getProductCode().equals("02")){
            typeId = "20201";
        } else if(borrow.getProductCode().equals("03")){
            typeId = "60181";
        }
        return typeId;
    }

    public void getBusinfoTypeTree(String typeId, String orderNo, Map<String,Object> img){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("orderNo",orderNo);
        map.put("typeId",typeId);
        JSONObject jsons = JSONObject.fromObject(orderApi.findBorrowByOrderNo(orderNo));
        //httpUtil.getData(Constants.LINK_CREDIT,"/credit/order/businfo/v/getBusinfoAndType",map);
        Map<String,Object> allData = new Gson().fromJson(jsons.toString(), Map.class);
        if(RespStatusEnum.SUCCESS.getCode().equals(MapUtils.getString(allData, "code", ""))) {
            Object allObj = MapUtils.getObject(allData, "data", null);
            Map<String, Object> allMap = null;
            Gson gson = new Gson();
            if (null != allObj) {
                allMap = gson.fromJson(gson.toJson(allObj),Map.class);
            }
            if(MapUtils.isEmpty(allMap))return;

            Object imgObj = MapUtils.getObject(allMap, "listMap",null);

            List<Map<String,Object>> imgList = null;
            if(null!=imgObj){
                imgList = gson.fromJson(gson.toJson(imgObj),List.class);
            }
            Map<String,Object> b;
            List<Map<String,Object>> tmpList = new ArrayList<Map<String,Object>>();
            if(null!=imgList&&imgList.size()>0){
                Set<String> keys = img.keySet();
                Integer imgTypeId = -1;
                Integer tmpTypeId = 0;
                for (String key:keys){
                    Map<String,Object> data = MapUtils.getMap(img,key);
                    imgTypeId = MapUtils.getInteger(data,"typeId");
                    Iterator<Map<String, Object>> iterator = imgList.iterator();
                    while (iterator.hasNext()){
                        Map<String, Object> next = iterator.next();
                        tmpTypeId = MapUtils.getInteger(next,"typeId");
                        if(imgTypeId==tmpTypeId||imgTypeId.equals(tmpTypeId)){
                            //String url = MapUtils.getString(next, "url", "").replaceAll("_18", "_48");
                            //List<Map<String,Object>> t = (List<Map<String,Object>>) MapUtils.getObject(data,"img");
                            //t.add(next);
                            //data.put("img",t);
                            next.put("type",key);
                            if("A".equals(key)){
                                b = new HashMap<String,Object>();
                                b.putAll(next);
                                b.put("type","B");
                                tmpList.add(b);
                            }
                        }
                    }
                }
                if(null!=tmpList&&tmpList.size()>0){
                    imgList.addAll(tmpList);
                }
                yntrustImageMapper.batchInsert(imgList);
            }
        }
    }

    @Override
    public void insert(Map<String, Object> map) {
        yntrustImageMapper.insert(map);
    }

    @Override
    public void batchInsert(List<Map<String, Object>> list) {
        yntrustImageMapper.batchInsert(list);
    }

    @Override
    public void delete(Map<String, Object> map) {
        map.put("isDelete",1);
        yntrustImageMapper.update(map);
        //yntrustImageMapper.delete(map);
    }

    @Override
    public Map<String,Object> listByMap(Map<String,Object> map){
        List<Map<String, Object>> list = yntrustImageMapper.list(map);
         String orderNo=MapUtils.getString(map,"orderNo");
         String ynProductCode=MapUtils.getString(map,"ynProductCode");
         YntrustBorrowDto yntrustBorrowDto = new YntrustBorrowDto();
         yntrustBorrowDto.setOrderNo(orderNo);
         yntrustBorrowDto= yntrustBorrowMapper.select(yntrustBorrowDto);
         if (yntrustBorrowDto!=null&&StringUtil.isNotBlank(yntrustBorrowDto.getYnProductCode())) {
        	 ynProductCode = yntrustBorrowDto.getYnProductCode();
		}
         Map<String,Object> img = classify(MapUtils.getString(map,"orderNo"),list,ynProductCode);
        return img;
    }

    @Override
    public void batchDelete(Map<String, Object> map) {
        map.put("isDelete",1);
        yntrustImageMapper.update(map);
        //yntrustImageMapper.batchDelete(map);
    }

    @Override
    public void updateIsPush(Map<String, Object> map) {
    	yntrustImageMapper.updateIsPush(map);
    }
    
}
