package com.anjbo.common;

import java.util.List;

public class RespHelper {
	public static <T> RespDataObject<T> failDataObject(T t){
		RespDataObject<T> respDataObject=new RespDataObject<T>();
		respDataObject.setCode(RespStatusEnum.FAIL.getCode());
		respDataObject.setMsg(RespStatusEnum.FAIL.getMsg());
		respDataObject.setData(t);
		return respDataObject;
	}
	
	public static <T> RespData<T> failData(List<T> list){
		RespData<T> respData=new RespData<T>();
		respData.setCode(RespStatusEnum.FAIL.getCode());
		respData.setMsg(RespStatusEnum.FAIL.getMsg());
		respData.setData(list);
		return respData;
	}
	
	public static RespStatus failRespStatus(){
		RespStatus respStatus=new RespStatus();
		respStatus.setCode(RespStatusEnum.FAIL.getCode());
		respStatus.setMsg(RespStatusEnum.FAIL.getMsg());
		return respStatus;
	}

	public static <T> RespDataObject<T> setSuccessDataObject(RespDataObject<T> respDataObject,T t){
		respDataObject.setCode(RespStatusEnum.SUCCESS.getCode());
		respDataObject.setMsg(RespStatusEnum.SUCCESS.getMsg());
		respDataObject.setData(t);
		return respDataObject;
	}
	
	public static <T> RespData<T> setSuccessData(RespData<T> respData,List<T> list){
		respData.setCode(RespStatusEnum.SUCCESS.getCode());
		respData.setMsg(RespStatusEnum.SUCCESS.getMsg());
		respData.setData(list);
		return respData;
	}
	
	public static RespStatus setSuccessRespStatus(RespStatus respStatus){
		respStatus.setCode(RespStatusEnum.SUCCESS.getCode());
		respStatus.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return respStatus;
	}
	
	public static <T> RespDataObject<T> setFailDataObject(RespDataObject<T> respDataObject,T t,String msg){
		respDataObject.setCode(RespStatusEnum.FAIL.getCode());
		respDataObject.setMsg(msg);
		respDataObject.setData(t);
		return respDataObject;
	}
	
	public static <T> RespData<T> setFailData(RespData<T> respData,List<T> list,String msg){
		respData.setCode(RespStatusEnum.FAIL.getCode());
		respData.setMsg(msg);
		respData.setData(list);
		return respData;
	}
	
	public static RespStatus setFailRespStatus(RespStatus respStatus,String msg){
		respStatus.setCode(RespStatusEnum.FAIL.getCode());
		respStatus.setMsg(msg);
		return respStatus;
	}
}
