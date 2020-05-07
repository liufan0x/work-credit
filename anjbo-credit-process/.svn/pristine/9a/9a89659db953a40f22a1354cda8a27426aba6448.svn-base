package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.CancellationDto;
import com.anjbo.bean.product.MortgageDto;
import com.anjbo.bean.product.NewlicenseDto;
import com.anjbo.bean.product.TransferDto;
import com.anjbo.dao.NewlicenseMapper;
import com.anjbo.dao.TransferMapper;
import com.anjbo.service.TransferService;
@Service
public class TransferServiceImpl implements TransferService {

	
	@Resource TransferMapper transferMapper;
	@Resource NewlicenseMapper newlicenseMapper;
	
	@Override
	public TransferDto selectTransfer(TransferDto dto) {
		// TODO Auto-generated method stub
		return transferMapper.selectTransfer(dto);
	}

	@Override
	public int addTransfer(TransferDto dto) {
		int count=0;
		try {
				TransferDto dto2=transferMapper.selectTransfer(dto);
				if(dto2==null){
					transferMapper.addTransfer(dto);
				}else{
					transferMapper.updateTransfer(dto);
				}
				NewlicenseDto newlicenseDto=new NewlicenseDto();
				newlicenseDto.setOrderNo(dto.getOrderNo());
				newlicenseDto.setCreateUid(dto.getCreateUid());
				newlicenseDto.setUpdateUid(dto.getUpdateUid());
				newlicenseDto.setNewlicenseTime(dto.getNewlicenseTime());
				newlicenseDto.setNlandBureau(dto.getNlandBureau());
				newlicenseDto.setNlandBureauName(dto.getNlandBureauName());
				newlicenseDto.setNlandBureauUid(dto.getNlandBureauUid());
				newlicenseDto.setNlandBureauUserName(dto.getNlandBureauUserName());
				NewlicenseDto ndDto=newlicenseMapper.selectNewlicense(newlicenseDto);
				if(ndDto==null){
					newlicenseMapper.addNewlicense(newlicenseDto);
				}else{
					newlicenseMapper.updateNewlicense(newlicenseDto);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
