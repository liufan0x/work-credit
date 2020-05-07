package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.LendingInstructionsDto;
import com.anjbo.dao.LendingInstructionMapper;
import com.anjbo.dao.LendingMapper;
import com.anjbo.service.LendingInstructionService;

@Service
public class LendingInstructionServiceImpl implements LendingInstructionService {

	@Resource LendingInstructionMapper lendingInstructionMapper;
	@Resource LendingMapper lendingMapper;
	
	/**
	 * 详情
	 */
	@Override
	public LendingInstructionsDto findByInstruction(String orderNo) {
		// TODO Auto-generated method stub
		return lendingInstructionMapper.findByInstruction(orderNo);
	}
	
	/**
	 * 添加信息
	 */
	@Override
	public int addLendingInstruction(LendingInstructionsDto lendingInstructionsDto) {
		// TODO Auto-generated method stub
		return lendingInstructionMapper.addLendingInstruction(lendingInstructionsDto);
	}

	/**
	 * 完善信息
	 */
	@Override
	public int updateLendingInstruction(LendingInstructionsDto instructionsDto) {
//		int count=0;
//		try {
//			LendingInstructionsDto ins=lendingInstructionMapper.findByInstruction(instructionsDto.getOrderNo());
//			if(ins!=null){
//				count=lendingInstructionMapper.updateLendingInstruction(instructionsDto);//更新当前状态
//				if(count>0){
//					//新增下一步基本信息（待放款）
//					LendingDto lendingDto=new LendingDto();
//					lendingDto.setStatus(0);
//					lendingDto.setOrderNo(instructionsDto.getOrderNo());
//					lendingDto.setCreateUid(instructionsDto.getCreateUid()); 
//					lendingDto.setUpdateUid(instructionsDto.getCreateUid());  
//					lendingMapper.addLending(lendingDto);
//				}
//			}else{
//				count=-1;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return lendingInstructionMapper.updateLendingInstruction(instructionsDto);//更新当前状态;
	}


	/**
	 * 撤回
	 */
	@Override
	public int updwithdraw(LendingInstructionsDto instructionsDto) {
		int count=0;
		try {
			LendingDto dto=new LendingDto();
			dto.setOrderNo(instructionsDto.getOrderNo());
			LendingDto lendingDto=lendingMapper.findByLending(dto);
			if(lendingDto!=null && 0==lendingDto.getStatus()){
				count=lendingInstructionMapper.updwithdraw(instructionsDto);
				lendingMapper.deleteLending(instructionsDto.getOrderNo());
			}else{
				count=-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 删除
	 */
	@Override
	public int delectInstruction(String orderNo){
		// TODO Auto-generated method stub
		return lendingInstructionMapper.delectInstruction(orderNo);
	}

}
