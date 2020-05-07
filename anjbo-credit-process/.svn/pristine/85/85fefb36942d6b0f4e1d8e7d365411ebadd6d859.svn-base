package com.anjbo.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.ForeclosureDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.dao.ForeclosureMapper;
import com.anjbo.dao.ForensicsMapper;
import com.anjbo.service.ForeclosureService;
import com.anjbo.service.ForensicsService;

@Service
public class ForeclosureServiceImpl implements ForeclosureService {

	@Resource ForeclosureMapper foreclosureMapper;
	@Resource ForensicsMapper forensicsMapper;
	
	@Override
	public ForeclosureDto selectForeclosure(ForeclosureDto dto) {
		// TODO Auto-generated method stub
		return foreclosureMapper.selectForeclosure(dto);
	}

	@Override
	public int addForeclosure(ForeclosureDto dto) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			ForeclosureDto foreclosureDto=foreclosureMapper.selectForeclosure(dto);
			if(foreclosureDto==null){
				count=foreclosureMapper.addForeclosure(dto);
			}else{
				count=foreclosureMapper.updateForeclosure(dto);
			}
			ForensicsDto forensicsDto=new ForensicsDto();
			forensicsDto.setOrderNo(dto.getOrderNo());
			forensicsDto.setCreateTime(new Date());
			forensicsDto.setCreateUid(dto.getCreateUid());
			forensicsDto.setUpdateUid(dto.getCreateUid());
			forensicsDto.setLicenseRevTime(dto.getLicenseRevTime());  //取证时间
			forensicsDto.setLicenseRever(dto.getLicenseRever());  //取证人
			forensicsDto.setLicenseReverUid(dto.getLicenseReverUid());//取证人Uid
			forensicsDto.setLicenseRevBank(dto.getLicenseRevBank());  //取证银行
			forensicsDto.setLicenseRevBankName(dto.getLicenseRevBankName());
			forensicsDto.setLicenseRevBankSub(dto.getLicenseRevBankSub());  //取证支行
			forensicsDto.setLicenseRevBankSubName(dto.getLicenseRevBankSubName());
			ForensicsDto dto2=forensicsMapper.selectForensics(forensicsDto);
			if(dto2==null){
				forensicsMapper.addForensics(forensicsDto);
			}else{
				forensicsMapper.upateForensics(forensicsDto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
