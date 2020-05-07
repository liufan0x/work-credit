package com.anjbo.service.impl;

import com.anjbo.bean.product.FaceSignDto;
import com.anjbo.bean.product.FacesignRecognitionDto;
import com.anjbo.dao.FacesignMapper;
import com.anjbo.service.FacesignService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Transactional
@Service
public class FacesignServiceImpl implements FacesignService {

	@Resource FacesignMapper facesignMapper;

	@Override
	public FaceSignDto selectFacesign(FaceSignDto dto) {
		// TODO Auto-generated method stub
		return facesignMapper.selectFacesign(dto);
	}

	@Override
	public int addFacesign(FaceSignDto dto) {
		// TODO Auto-generated method stub
		return facesignMapper.addFacesign(dto);
	}

	@Override
	public int updateFacesign(FaceSignDto dto) {
		// TODO Auto-generated method stub
		return facesignMapper.updateFacesign(dto);
	}

	@Override
	public List<FacesignRecognitionDto> listFacesignRecognition(FacesignRecognitionDto obj) {
		return facesignMapper.listFacesignRecognition(obj);
	}

	@Override
	public int insertFacesignRecognition(FacesignRecognitionDto obj) {
		return facesignMapper.insertFacesignRecognition(obj);
	}

	@Override
	public int updateFacesignRecognition(FacesignRecognitionDto obj) {
		return facesignMapper.updateFacesignRecognition(obj);
	}
	public FacesignRecognitionDto selectFacesignRecognitionById(FacesignRecognitionDto obj){
		return facesignMapper.selectFacesignRecognitionById(obj);
	}

	public int deleteFacesignRecognition(FacesignRecognitionDto obj){
		return facesignMapper.deleteFacesignRecognition(obj);
	}

	@Override
	public int deleteByOrderNoAndCustomerType(FacesignRecognitionDto obj) {
		return facesignMapper.deleteByOrderNoAndCustomerType(obj);
	}

	@Override
	public void insertFacesignRecognitions(
			List<FacesignRecognitionDto> FacesignRecognitionDtos) {
		facesignMapper.insertFacesignRecognitions(FacesignRecognitionDtos);
	}
}
