package com.anjbo.service;

import com.anjbo.bean.product.FaceSignDto;
import com.anjbo.bean.product.FacesignRecognitionDto;

import java.util.List;


public interface FacesignService {
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

	/**
	 * 人脸识别信息
	 * @param obj
	 * @return
	 */
	public List<FacesignRecognitionDto> listFacesignRecognition(FacesignRecognitionDto obj);

	/**
	 * 添加人脸识别信息
	 * @param obj
	 * @return
	 */
	public int insertFacesignRecognition(FacesignRecognitionDto obj);

	/**
	 * 根据id更新人脸信息
	 * @param obj
	 * @return
	 */
	public int updateFacesignRecognition(FacesignRecognitionDto obj);

	public FacesignRecognitionDto selectFacesignRecognitionById(FacesignRecognitionDto obj);

	public int deleteFacesignRecognition(FacesignRecognitionDto obj);

	/**
	 * 根据订单编号和客户类型删除面签人脸识别数据
	 * @param obj
	 * @return
	 */
	public int deleteByOrderNoAndCustomerType(FacesignRecognitionDto obj);
	
	/**
	 * 批量添加人脸识别
	 * @param FacesignRecognitionDtos
	 */
	public void insertFacesignRecognitions(List<FacesignRecognitionDto> FacesignRecognitionDtos );
}
