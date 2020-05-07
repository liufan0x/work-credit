package com.anjbo.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.customer.AgencyProductDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.user.AuthorityDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.AuthorityMapper;
import com.anjbo.service.AuthorityService;
import com.anjbo.utils.HttpUtil;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	@Resource
	private AuthorityMapper authorityMapper;
	
	@Override
	public AuthorityDto selectAuthorityByProductId(AuthorityDto authorityDto) {
		return authorityMapper.selectAuthorityByProductId(authorityDto);
	}
	
	@Override
	public List<Map<String, Object>> selectUserAuthorityList() {
		return authorityMapper.selectUserAuthorityList();
	}
	
	@Override
	public List<Map<String, Object>> selectResource() {
		return authorityMapper.selectResource();
	}
	
	@Override
	public List<Map<String, Object>> selectAuthority() {
		return authorityMapper.selectAuthority();
	}
	
	@Override
	public int insertUserAuthority(Map<String, Object> params){
		return authorityMapper.insertUserAuthority(params);
	}
	@Override
	public int insertUserAuthorityAdmin(Integer agentId, String uid, boolean isAllPermi){
//		多方调用，后期也不会变动，考虑SQL实现
//		RespData<AgencyProductDto> resp = new HttpUtil().getRespData(Constants.LINK_CREDIT, "/credit/customer/agencyProduct/search_"+agentId, null, AgencyProductDto.class);
//		if(null!=resp && RespStatusEnum.SUCCESS.getCode().equals(resp.getCode()) && null!=resp.getData() && !resp.getData().isEmpty()){
//			List<AgencyProductDto> lstAgencyProduct = resp.getData();	
//			List<Integer> lstProIds = new LinkedList<Integer>();
//			for (AgencyProductDto agencyProductDto : lstAgencyProduct) {
//				lstProIds.add(Integer.valueOf(agencyProductDto.getCityCode()+""+agencyProductDto.getProductCode()));				
//			}
//			List<ProductDto> lstProduct = (List<ProductDto>) RedisOperator.get("productList");
//			for (ProductDto productDto : lstProduct) {
//				if(lstProIds.contains(productDto.getId())){
//					
//				}
//			}
//		}		
		return authorityMapper.insertUserAuthorityAdmin(agentId, uid, isAllPermi);
	}
	@Override
	public int signUserAuthorityAdmin(String uid){
		return authorityMapper.signUserAuthorityAdmin(uid);
	}

	@Override
	public int updateUserAuthority(Map<String, Object> params){
		return authorityMapper.updateUserAuthority(params);
	}

	@Override
	public Map<String, Object> selectUserAuthority(String uid){
		return authorityMapper.selectUserAuthority(uid);
	}

	@Override
	public int insertRoleAuthority(Map<String, Object> params){
		return authorityMapper.insertRoleAuthority(params);
	}

	@Override
	public int updateRoleAuthority(Map<String, Object> params){
		return authorityMapper.updateRoleAuthority(params);
	}

	@Override
	public Map<String, Object> selectRoleAuthority(int roleId){
		return authorityMapper.selectRoleAuthority(roleId);
	}
	
	@Override
	public List<AuthorityDto> selectAuthorityDtoByAuthorityDto(AuthorityDto authorityDto) {
		return authorityMapper.selectAuthorityDtoByAuthorityDto(authorityDto);
	}

	@Override
	public List<AuthorityDto> selectAuthorityByProcessIds(String processIds) {
		return authorityMapper.selectAuthorityByProcessIds(processIds);
	}
	
}
