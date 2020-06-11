package com.anjbo.service.impl;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.risk.EnquiryDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.dao.CreditMapper;
import com.anjbo.dao.EnquiryMapper;
import com.anjbo.service.CreditService;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.NumberUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 征信
 * @author huanglj
 *
 */
@Service
public class CreditServiceImpl implements CreditService{

	@Resource
	private CreditMapper  creditMapper;
	@Resource
	private EnquiryMapper enquiryMapper;
	
	public  Map<String,Object> getBeanMap(){
		Map<String,Object> beanMap  = new HashMap<String,Object>();
		beanMap.put("creditCardYears", "信用卡年限");
		beanMap.put("loanRecordYears", "个人贷款记录年限");
		beanMap.put("violationProportion", "个人贷款,信用卡累计有效违约率");
		beanMap.put("allHouseWorth", "所有房产评估总值(万元)");
		beanMap.put("allHouseNumber", "房产套数");
		beanMap.put("creditCardOverdraft", "信用卡半年月均透支额(万元)");
		beanMap.put("creditQuota", "授信总额（万元）");
		beanMap.put("useQuota", "已用额度(万元)");
		beanMap.put("creditLiabilities", "征信总负债(万元)");
		beanMap.put("liabilitiesProportion", "负债比例");
		beanMap.put("loanPercentage", "借款成数");
		beanMap.put("foreclosurePercentage", "债务置换贷款成数");
		beanMap.put("creditOverdueNumber", "征信报告逾期数");
		beanMap.put("latelyHalfYearSelectNumber", "近半年征信查询次数");
		//beanMap.put("latelyTwoYearMoneyOverdueNumber", "2年内金额2000以上有逾期次数");
		beanMap.put("isCompanyProperty", "是否公司产权");
		beanMap.put("propertyMortgage", "产权抵押情况");
		return beanMap;
	}
	/**
	 * 根据订单编号查询征信信息
	 * @param orderNo 订单编号 
	 * @return CreditDto
	 */
	public CreditDto detail(String orderNo){
		CreditDto credit = creditMapper.detail(orderNo);
		HttpUtil http = new HttpUtil();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderNo", orderNo);
		OrderBaseHouseDto  house = http.getObject(Constants.LINK_CREDIT,"/credit/order/house/v/query",map,OrderBaseHouseDto.class);
		OrderBaseBorrowDto borrow = http.getObject(Constants.LINK_CREDIT,"/credit/order/borrowother/v/queryBorrow",map,OrderBaseBorrowDto.class);
		if(null!=borrow){
			if(null==credit){
				credit = new CreditDto();
			}
			credit.setLoanAmount(borrow.getLoanAmount());
			credit.setOldLoanIsBank(borrow.getIsOldLoanBank());
			credit.setNewLoanIsBank(borrow.getIsLoanBank());
		}
		//查询赎楼房产评估总值
		Double houseWorth = null;
		
		EnquiryDto enquiryDto = enquiryMapper.detailByOrderNoLimitOne(orderNo);
		
		if(credit.getForeclosurePercentage() == null && enquiryDto!=null&&null!=enquiryDto.getNetPrice()){
			houseWorth = enquiryDto.getNetPrice();
			//计算设置赎楼成数
			if(credit.getLoanAmount()!=null&&enquiryDto.getNetPrice()!=0d){
				credit.setForeclosurePercentage(NumberUtil.formatDecimal((credit.getLoanAmount()*1000000)/houseWorth,2));
			}
		}
		//计算设置借款成数
		/*if(credit.getLoanPercentage() == null &&credit.getLoanAmount()!=null&&null!=house&&house.getHouseLoanAmount()!=null){
			credit.setLoanPercentage(NumberUtil.formatDecimal((credit.getLoanAmount()*100)/house.getHouseLoanAmount(),2));
		}*/
		
		if(houseWorth!=null){
			credit.setHouseWorth(houseWorth/10000.0);
		}
		
		return credit;
	}
	
	/**
	 * 新增征信信息
	 * @param credit 征信数据
	 * @return int 影响条数
	 */
	@Transactional
	public int insertCredit(CreditDto credit){
		return creditMapper.insertCredit(credit);
	}
	
	/**
	 * 更新征信信息
	 * @param credit 征信数据
	 * @return int 影响条数
	 * @throws IllegalAccessException 
	 */
	@Transactional
	public int updateCredit(CreditDto credit) throws IllegalAccessException{
		int success = 0;
		if(credit.isCreateCreditLog()){
			CreditDto old = creditMapper.detail(credit.getOrderNo());
			
			if(null==old){
				success = creditMapper.insertCredit(credit);
			} else {
				success = creditMapper.updateCredit(credit);
			}
			if(null == old){
				old=new CreditDto();
			}
			List<Map<String,Object>> list = compare(old,credit);
			if(null!=list&&list.size()>0){
				for (Map<String, Object> map : list) {
					map.put("createUid", credit.getCreateUid());
				}
				creditMapper.insertCreditLogBatch(list);
			}
		} else {
			CreditDto tmp = creditMapper.detail(credit.getOrderNo());
			if(null==tmp){
				success = creditMapper.insertCredit(credit);
			} else {
				success = creditMapper.updateNull(credit);
			}
		}
		return success;
	}
	
	/**
	 * 查询征信日志流水
	 * @param orderNo
	 * @return
	 */
	public List<Map<String,Object>> selectCreditLog(String orderNo){
		if(StringUtils.isBlank(orderNo))return Collections.EMPTY_LIST;
		
		List<Map<String,Object>> tmp = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> l1 = creditMapper.selectCreditLogAsc(orderNo);
		List<Map<String, Object>> l2 = creditMapper.selectCreditLogDesc(orderNo);
		
		String beanColumn = "";
		String beanColumnTmp = "";
		Object endVal = null;
		Object startVal = null;
		for(Map<String,Object> map:l2){
			Iterator<Map<String, Object>> it1 = l1.iterator();
			beanColumn = MapUtils.getString(map, "beanColumn", "");
			endVal = MapUtils.getObject(map, "endVal");
			
			if(StringUtils.isBlank(beanColumn))continue;
			if(null==endVal)continue;
			
			while(it1.hasNext()){
				Map<String,Object> map1 = it1.next();
				beanColumnTmp = MapUtils.getString(map1, "beanColumn", "");
				startVal = MapUtils.getObject(map1, "startVal");
				if(beanColumn.equals(beanColumnTmp)
						&&!endVal.equals(startVal)){
					map.put("startVal", MapUtils.getObject(map1, "startVal"));
					tmp.add(map);
					it1.remove();
					break;
				}
			}
		}
			
			
		return tmp;
	}
	
	/**
	 * 新增修改征信日志流水
	 * @param map{key=createUid(创建人),key=createTime(创建时间),key=orderNo(订单编号)
	 * 				,key=beanColumn(修改的字段对应实体属性),key=colName(字段名称),key=startVal(初始值)
	 * 				,key=endVal(修改值),key=isShow(是否展示),key=type(类型),key=operationType(操作类型)}
	 * @return
	 */
	@Transactional
	public int insertCreditLog(Map<String,Object> map){
		return creditMapper.insertCreditLog(map);
	}
	/**
	 * 批量新增修改征信日志流水
	 * @param list{key=createUid(创建人),key=createTime(创建时间),key=orderNo(订单编号)
	 * 				,key=beanColumn(修改的字段对应实体属性),key=colName(字段名称),key=startVal(初始值)
	 * 				,key=endVal(修改值),key=isShow(是否展示),key=type(类型),key=operationType(操作类型)}
	 * @param list
	 * @return
	 */
	@Transactional
	public int insertCreditLogBatch(List<Map<String,Object>> list){
		return creditMapper.insertCreditLogBatch(list);
	}
	
	/**
	 * 更新修改征信日志流水
	* @param map{key=updateUid(修改人),key=colName(字段名称),key=beanColumn(修改的字段对应实体属性)
	 * 				,key=startVal(初始值),key=endVal(修改值),key=isShow(是否展示)
	 * 				,key=type(类型),key=operationType(操作类型)}
	 * @return
	 */
	@Transactional
	public int updateCreditLog(Map<String,Object> map){
		return creditMapper.updateCreditLog(map);
	}
	
	private <T> List<Map<String,Object>> compare(T obj1, T obj2) throws IllegalAccessException {


        Map<String,Object> m1 = new HashMap<String,Object>();
        Map<String,Object> m2 = new HashMap<String,Object>();
        Field[] fs1 = obj1.getClass().getDeclaredFields();
        Field[] fs2 = obj2.getClass().getDeclaredFields();

        for (Field f:fs1){
            f.setAccessible(true);
            Object v = f.get(obj1);
            m1.put(f.getName(),v);
        }
        for (Field f:fs2){
            f.setAccessible(true);
            Object v = f.get(obj2);
            m2.put(f.getName(),v);
        }
        List<Map<String,Object>> list = difference(m1,m2);
        return list;
    }

    private <K,V> List<Map<String,Object>> difference(Map<K,V> m1,Map<K,V> m2){
        if (m1.isEmpty()||m2.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Set<K> ks = m1.keySet();
        HashMap<String, Object> map = null;
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (K k:ks){
            V v = m1.get(k);
            if(shieldCol(k)){
            	continue;
            }
            if(m2.containsKey(k)
            		&&null!=m2.get(k)
            		&&!m2.get(k).equals(v)){
            	String colName = MapUtils.getString(getBeanMap(), k,"null");
            	if(!"null".equals(colName)) {
					map = new HashMap<String, Object>();
					map.put("beanColumn", k);
					map.put("colName", colName);
					map.put("startVal", MapUtils.getString(m1, k, "null"));
					map.put("endVal", m2.get(k));
					map.put("createTime", new Date());
					map.put("updateUid", MapUtils.getString(m2, "updateUid", ""));
					map.put("createUid", MapUtils.getString(m2, "createUid", ""));
					map.put("isShow", MapUtils.getInteger(m2, "isShow", 1));
					map.put("type", MapUtils.getInteger(m2, "type", 1));
					map.put("operationType", MapUtils.getInteger(m2, "operationType", 1));
					map.put("orderNo", MapUtils.getString(m2, "orderNo", ""));
					list.add(map);
				}
            }
        }
        return  list;
    }
    
    private boolean shieldCol(Object obj){
    	boolean flg = false;
    	if(obj.equals("updateUid")){
    		flg = true;
    	} else if(obj.equals("createUid")){
    		flg = true;
    	} else if(obj.equals("createTime")){
    		flg = true;
    	} else if(obj.equals("id")){
    		flg = true;
    	} else if(obj.equals("updateTime")){
    		flg = true;
    	} else if(obj.equals("orderNo")){
    		flg = true;
    	} else if(obj.equals("remark")){
    		flg = true;
    	} else if(obj.equals("createCreditLog")){
    		flg = true;
    	}
    	return flg;
    }

	@Override
	public int updateNull(CreditDto dto) {
		int success = 0;
		CreditDto tmp = creditMapper.detail(dto.getOrderNo());
		if(null==tmp){
			success = creditMapper.insertCredit(dto);
		} else {
			success = creditMapper.updateNull(dto);
		}
		return success;
	}
}
