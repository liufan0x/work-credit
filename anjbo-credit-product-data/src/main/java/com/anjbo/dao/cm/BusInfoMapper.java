package com.anjbo.dao.cm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.cm.CMBusInfoDto;

public interface BusInfoMapper {


	public List<CMBusInfoDto> getByOrderAndCode(@Param("orderNo") String orderNo,@Param("code") String code);

}
