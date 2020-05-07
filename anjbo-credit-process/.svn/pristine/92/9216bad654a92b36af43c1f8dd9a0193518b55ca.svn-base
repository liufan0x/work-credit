package com.anjbo.dao;

import com.anjbo.bean.product.FaceSignDto;
import com.anjbo.bean.product.FacesignRecognitionDto;

import java.util.List;


public interface FacesignMapper {
	
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public FaceSignDto selectFacesign(FaceSignDto dto);
	
	/**
	 * 添加信息
	 * @param dto
	 * @return
	 */
	public int addFacesign(FaceSignDto dto);
	public int updateFacesign(FaceSignDto dto);

	public List<FacesignRecognitionDto> listFacesignRecognition(FacesignRecognitionDto obj);

	public int insertFacesignRecognition(FacesignRecognitionDto obj);

	public int updateFacesignRecognition(FacesignRecognitionDto obj);

	public FacesignRecognitionDto selectFacesignRecognitionById(FacesignRecognitionDto obj);

	public int deleteFacesignRecognition(FacesignRecognitionDto obj);
	
	public int deleteByOrderNoAndCustomerType(FacesignRecognitionDto obj);
	/**
	 * 批量录入人脸识别表
	 * @param FacesignRecognitionDtos
	 */
	public void insertFacesignRecognitions(List<FacesignRecognitionDto> FacesignRecognitionDtos );
}
