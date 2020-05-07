package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.CancellationDto;
import com.anjbo.bean.product.MortgageDto;
import com.anjbo.bean.product.NewlicenseDto;
import com.anjbo.bean.product.TransferDto;
import com.anjbo.dao.MortgageMapper;
import com.anjbo.dao.NewlicenseMapper;
import com.anjbo.service.NewlicenseService;

@Service
public class NewlicenseServiceImpl implements NewlicenseService {

	@Resource NewlicenseMapper newlicenseMapper;
	@Resource MortgageMapper mortgageMapper;  //抵押
	
	@Override
	public NewlicenseDto selectNewlicense(NewlicenseDto dto) {
		// TODO Auto-generated method stub
		return newlicenseMapper.selectNewlicense(dto);
	}

	@Override
	public int addNewlicense(NewlicenseDto dto) {
		int count=0;
		try {
			NewlicenseDto ndto=newlicenseMapper.selectNewlicense(dto);
			if(ndto==null){
				newlicenseMapper.addNewlicense(dto);
			}else{
				newlicenseMapper.updateNewlicense(dto);
			}
			MortgageDto mortgageDto=new MortgageDto();
			mortgageDto.setOrderNo(dto.getOrderNo());
			mortgageDto.setCreateUid(dto.getCreateUid());
			mortgageDto.setUpdateUid(dto.getCreateUid());
			mortgageDto.setMortgageTime(dto.getMortgageTime());
			mortgageDto.setMlandBureau(dto.getMlandBureau());  //国土局
			mortgageDto.setMlandBureauName(dto.getMlandBureauName());
			mortgageDto.setMlandBureauUid(dto.getMlandBureauUid()); //驻点
			mortgageDto.setMlandBureauUserName(dto.getMlandBureauUserName());
			MortgageDto dto2=mortgageMapper.selectMortgage(mortgageDto);
			if(dto2==null){
				mortgageMapper.addMortgage(mortgageDto);
			}else{
				mortgageMapper.updateMortgage(mortgageDto);
			}
			count =1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
