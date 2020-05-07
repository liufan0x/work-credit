package com.anjbo.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.anjbo.bean.product.CancellationDto;
import com.anjbo.bean.product.ForeclosureDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.dao.CancellationMapper;
import com.anjbo.dao.ForensicsMapper;
import com.anjbo.service.ForensicsService;

@Service
public class ForensicsServiceImpl implements ForensicsService{

	@Resource ForensicsMapper forensicsMapper;
	@Resource CancellationMapper cancellationMapper;
	
	@Override
	public ForensicsDto selectForensics(ForensicsDto dto) {
		// TODO Auto-generated method stub
		return forensicsMapper.selectForensics(dto);
	}

	@Override
	public int addForensics(ForensicsDto dto) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			ForensicsDto dto2=forensicsMapper.selectForensics(dto);
			if(dto2==null){
				forensicsMapper.addForensics(dto);
			}else{
				forensicsMapper.upateForensics(dto);
			}
			CancellationDto cancellationDto=new CancellationDto();
			cancellationDto.setOrderNo(dto.getOrderNo());
			cancellationDto.setCreateUid(dto.getCreateUid());
			cancellationDto.setUpdateUid(dto.getUpdateUid());
			cancellationDto.setCancelTime(dto.getCancelTime());   //注销时间
			cancellationDto.setClandBureau(dto.getClandBureau());  //国土局
			cancellationDto.setClandBureauName(dto.getClandBureauName());
			cancellationDto.setClandBureauUid(dto.getClandBureauUid());  //国土局驻点
			cancellationDto.setClandBureauUserName(dto.getClandBureauUserName());
			CancellationDto cdto=cancellationMapper.selectCancellation(cancellationDto);
			if(cdto==null){
				cancellationMapper.addCancellation(cancellationDto);
			}else{
				cancellationMapper.updateCancellation(cancellationDto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int addFddForensics(ForensicsDto dto) {
		ForensicsDto dto2=forensicsMapper.selectForensics(dto);
		if(dto2==null){
			return forensicsMapper.addForensics(dto);
		}else{
			return forensicsMapper.upateForensics(dto);
		}
	}

	
}
