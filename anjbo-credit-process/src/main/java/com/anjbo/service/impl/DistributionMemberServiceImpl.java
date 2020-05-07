package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.dao.DistributionMemberMapper;
import com.anjbo.service.DistributionMemberService;
@Service
public class DistributionMemberServiceImpl implements DistributionMemberService {

	@Resource DistributionMemberMapper distributionMemberMapper;
	@Override
	public DistributionMemberDto selectDistributionMember(
			DistributionMemberDto dto) {
		// TODO Auto-generated method stub
		return distributionMemberMapper.selectDistributionMember(dto);
	}

	@Override
	public int addDistributionMember(DistributionMemberDto dto) {
		// TODO Auto-generated method stub
		return distributionMemberMapper.addDistributionMember(dto);
	}

	@Override
	public int updateDistributionMember(DistributionMemberDto dto) {
		// TODO Auto-generated method stub
		return distributionMemberMapper.updateDistributionMember(dto);
	}

}
