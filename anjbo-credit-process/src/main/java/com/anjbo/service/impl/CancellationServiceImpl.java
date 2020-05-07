package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.CancellationDto;
import com.anjbo.bean.product.MortgageDto;
import com.anjbo.bean.product.TransferDto;
import com.anjbo.dao.CancellationMapper;
import com.anjbo.dao.MortgageMapper;
import com.anjbo.dao.TransferMapper;
import com.anjbo.service.CancellationService;
@Service
public class CancellationServiceImpl implements CancellationService {

	@Resource CancellationMapper cancellationMapper;
	@Resource TransferMapper transferMapper;   //过户
	@Resource MortgageMapper mortgageMapper;  //抵押
	@Override
	public CancellationDto selectCancellation(CancellationDto dto) {
		// TODO Auto-generated method stub
		return cancellationMapper.selectCancellation(dto);
	}

	@Override
	public int addCancellation(CancellationDto dto) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			CancellationDto cdto=cancellationMapper.selectCancellation(dto);
			if(cdto==null){
				cancellationMapper.addCancellation(dto);
			}else{
				cancellationMapper.updateCancellation(dto);
			}
			if(dto.getType()==1){ //交易类 -过户
				TransferDto transferDto=new TransferDto();
				transferDto.setOrderNo(dto.getOrderNo());
				transferDto.setCreateUid(dto.getCreateUid());
				transferDto.setUpdateUid(dto.getCreateUid());
				transferDto.setTransferTime(dto.getTransferTime());
				transferDto.setTlandBureau(dto.getTlandBureau());  //国土局
				transferDto.setTlandBureauName(dto.getTlandBureauName());
				transferDto.setTlandBureauUid(dto.getTlandBureauUid()); //驻点
				transferDto.setTlandBureauUserName(dto.getTlandBureauUserName());
				TransferDto dto2=transferMapper.selectTransfer(transferDto);
				if(dto2==null){
					transferMapper.addTransfer(transferDto);
				}else{
					transferMapper.updateTransfer(transferDto);
				}
			}else{  //非交  -抵押
				MortgageDto mortgageDto=new MortgageDto();
				mortgageDto.setOrderNo(dto.getOrderNo());
				mortgageDto.setCreateUid(dto.getCreateUid());
				mortgageDto.setUpdateUid(dto.getCreateUid());
				mortgageDto.setMortgageTime(dto.getTransferTime());
				mortgageDto.setMlandBureau(dto.getTlandBureau());  //国土局
				mortgageDto.setMlandBureauName(dto.getTlandBureauName());
				mortgageDto.setMlandBureauUid(dto.getTlandBureauUid()); //驻点
				mortgageDto.setMlandBureauUserName(dto.getTlandBureauUserName());
				MortgageDto dto2=mortgageMapper.selectMortgage(mortgageDto);
				if(dto2==null){
					mortgageMapper.addMortgage(mortgageDto);
				}else{
					mortgageMapper.updateMortgage(mortgageDto);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public CancellationDto selectCancellationByMortgage(CancellationDto dto) {
		// TODO Auto-generated method stub
		return cancellationMapper.selectCancellationByMortgage(dto);
	}

}
