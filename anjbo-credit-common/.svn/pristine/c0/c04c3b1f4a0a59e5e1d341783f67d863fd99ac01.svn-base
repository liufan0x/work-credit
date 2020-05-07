package com.anjbo.utils;

import java.util.ArrayList;
import java.util.List;
import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RedisOperator;

/**
 * 数据工具类 (用户,银行,支行,字典表)
 * @author lic
 * @date 2017-8-23
 */
public class CommonDataUtil {

	/**
	 * 获取所有用户
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<UserDto> getAllUserList(){
		return (List<UserDto>) RedisOperator.get("userList");
	}
	
	/**
	 * 根据城市 机构 获取 用户集合
	 * @param cityCode
	 * @param agencyId
	 * @return
	 */
	public static List<UserDto> getUserListByCityCodeOrAgencyId(String cityCode,int agencyId){
		List<UserDto> userList = getAllUserList();
		if(agencyId == 0){
			if(StringUtil.isEmpty(cityCode)){
				return userList;
			}else{
				List<UserDto> tempList = new ArrayList<UserDto>();
				for (UserDto userDto : userList) {
					if(userDto.getCityCode().equals(cityCode)){
						tempList.add(userDto);
					}
				}
				return tempList;
			}
		}else{
			List<UserDto> agencyTempList = new ArrayList<UserDto>();
			for (UserDto userDto : userList) {
				if(userDto.getAgencyId() == agencyId){
					agencyTempList.add(userDto);
				}
			}
			if(StringUtil.isNotEmpty(cityCode)){
				List<UserDto> tempList = new ArrayList<UserDto>();
				for (UserDto userDto : agencyTempList) {
					if(userDto.getCityCode().equals(cityCode)){
						tempList.add(userDto);
					}
				}
				return tempList;
			}else{
				return agencyTempList;
			}
		}
	}
	
	/**
	 * 根据Uid or Mobile 获取用户
	 * @param obj 
	 * @return
	 */
	public static UserDto getUserDtoByUidAndMobile(Object obj){
		UserDto userDto = (UserDto)RedisOperator.getFromMap("userListMapByUid", obj);
		if(userDto == null){
			userDto =  (UserDto)RedisOperator.getFromMap("userListMapByMobile", obj);
		}
		if(userDto == null ){
			return new UserDto();
		}
		return userDto;
		
	}
	
	/**
	 * 根据银行Id获取银行
	 * @param bankId
	 * @return
	 */
	public static BankDto getBankNameById(Object bankId){
		BankDto bankDto = (BankDto) RedisOperator.getFromMap("bankListMap", bankId);
		if(bankDto == null){
			bankDto = new BankDto();
		}
		return bankDto;
	}
	
	/**
	 * 根据支行Id获取支行
	 * @param subBankId
	 * @return
	 */
	public static SubBankDto getSubBankNameById(Object subBankId){
		SubBankDto subBankDto = (SubBankDto) RedisOperator.getFromMap("subBankListMap", subBankId);
		if(subBankDto == null){
			subBankDto = new SubBankDto();
		}
		return subBankDto;
	}
	
	/**
	 * 根据类型获取字典
	 * @param type
	 * @param code
	 * @return
	 */
	public static DictDto getDictDtoByTypeAndCode(Object type,Object code){
		DictDto dictDto = (DictDto)RedisOperator.getFromMap(type, code);
		if(dictDto == null){
			dictDto = new DictDto();
		}
		return dictDto;
	}
	
	/**
	 * 根据类型获取字典
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<DictDto> getDictDtoByType(String type){
		List<DictDto>  list =(List<DictDto>) RedisOperator.get(type);
		if(list == null){
			list = new ArrayList<DictDto>();
		}
		return list;
	}
	

	/**
	 * 获取所有产品
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductDto> getAllProductDtos(){
		List<ProductDto> productDtos = (List<ProductDto>) RedisOperator.get("productList");
		if(productDtos  == null){
			productDtos = new ArrayList<ProductDto>();
		}
		return productDtos;
	}
	
	/**
	 * 根据产品Id获取产品
	 * @param productId
	 * @return
	 */
	public static ProductDto getProductDtoByProductId(Object productId){
		ProductDto productDto = (ProductDto) RedisOperator.getFromMap("productListMapByPorductId", productId);
		if(productDto == null){
			productDto = new ProductDto();
		}
		return productDto;
	}
	
	/**
	 * 根据产品Code获取产品
	 * @param productCode
	 * @return
	 */
	public static ProductDto getProductDtoByProductCode(Object productCode){
		ProductDto productDto = (ProductDto) RedisOperator.getFromMap("productListMapByproductCode", productCode);
		if(productDto == null){
			productDto = new ProductDto();
		}
		return productDto;
	}
	
	/**
	 * 根据产品Code获取产品
	 * @param productCode
	 * @return
	 */
	public static List<ProductProcessDto> getProductProcessDto(String productId){
		ProductDto productDto = getProductDtoByProductId(productId);
		if(productDto != null){
			return productDto.getProductProcessDtos();
		}else{
			return new ArrayList<ProductProcessDto>();
		}
	}
	/**
	 * 根据机构id or 机构码 获取机构
	 * @param obj
	 * @return
	 */
	public static AgencyDto getAgencyByIdOrAgencyCode(Object obj){
		if(null==obj)return new AgencyDto();
		AgencyDto agency = (AgencyDto)RedisOperator.getFromMap("agencyListMapById", obj);
		if(null==agency){
			agency = (AgencyDto)RedisOperator.getFromMap("agencyListMapByAgencyCode", obj);
		}
		if(null==agency)return new AgencyDto();
		return agency;
	}
	
}
