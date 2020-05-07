package com.anjbo.dao.system;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface SMSMapper {

	public int insertSMS(@Param("ip") String ip, @Param("date") Date date,
			@Param("mobile") String mobile, @Param("comeFrom") String comeFrom,
			@Param("content") String content);

	public int selectSMS(@Param("todayDate") String todayDate,
			@Param("mobile") String mobile, @Param("comeFrom") String comeFrom);

	public int selectSMSMounth(@Param("todayYearMonth") String todayDate,
			@Param("mobile") String mobile, @Param("comeFrom") String comeFrom);

	public int selectSMSByIp(@Param("todayDate") String todayDate,
			@Param("ip") String ip, @Param("comeFrom") String comeFrom);

	public int selectSMSMounthByIp(@Param("todayYearMonth") String todayDate,
			@Param("ip") String ip, @Param("comeFrom") String comeFrom);

}
