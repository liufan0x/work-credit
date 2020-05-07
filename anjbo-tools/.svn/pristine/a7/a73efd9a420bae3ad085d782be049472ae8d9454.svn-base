package com.anjbo.service.tools;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.anjbo.bean.tools.EnquiryAssessDto;
import com.anjbo.bean.tools.EnquiryDto;
import com.anjbo.bean.tools.EnquiryReportDto;
import com.anjbo.common.MortgageException;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;

/**
 * 询价
 * @author lic
 *
 * @date 2016-6-2 上午10:44:02
 */
public interface EnquiryService {


	/**
	 * 查询相同物业，相同楼栋，相同房号同一天查询次数
	 * @user lic
	 * @date 2016-6-3 下午04:15:11 
	 * @param enquiryDto
	 * @return
	 */
	public int selectCountByCondition(EnquiryDto enquiryDto);
	
	/**
	 * 查询税费记录Id
	 * @user lic
	 * @date 2016-6-6 下午02:51:32 
	 * @param param
	 * @return
	 */
	public Integer taxExit(Map<String, Object> param);
	
	/**
	 * 新增税费记录
	 * @user lic
	 * @date 2016-6-6 下午02:51:56 
	 * @param param
	 */
	public void insertTax(Map<String, Object> param);
	
	/**
	 * 根据内部流水查询询价Id
	 * @user lic
	 * @date 2016-11-14 下午04:31:33 
	 */
	public Integer selectEnquiryBySerialid(String serialid);
	
	/**
	 * 修改询价结果
	 * @user lic
	 * @date 2016-11-14 下午04:51:27 
	 * @param enquiryReportDto
	 */
	public void updateEnquiryReport(EnquiryReportDto enquiryReportDto);
	
	/**
	 * 询价
	 * @user lic
	 * @date 2016-6-3 下午05:15:31 
	 * @param enquiryDto
	 * @throws MortgageException 
	 */
	public void createEnquiry(EnquiryDto enquiryDto) throws MortgageException;
	
	/**
	 * 针对东莞的特殊询价
	 * 
	 * @user Administrator
	 * @date 2017-6-1 上午09:38:56 
	 * @param enquiryDto
	 * @throws MortgageException 
	 */
	public void createEnquiryDongGuan(EnquiryDto enquiryDto) throws MortgageException;
	
	/**
	 * 查询询价结果记录
	 * @user Object
	 * @date 2016-11-14 下午06:29:31 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> selectEnquiryReportList(int id);

	/**
	 * 查询询价详情
	 * @user Object
	 * @date 2016-11-18 下午04:45:31 
	 * @param id
	 * @return
	 */
	public EnquiryDto selectEnquiry(Integer id);
	
	/**
	 * 查询询价列表
	 * @user lic
	 * @date 2016-11-14 下午06:28:56 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getList(Map<String, Object> param);
	
	/*********************************************************同致诚*******************************************************************/
	
	/**
	 * 获取同致诚物业名称
	 * @user lic
	 * @date 2016-6-2 下午04:45:02 
	 * @param keyWord
	 * @return Map
	 * @return key id 物业名称Id
	 * @return key name 物业名称
	 */
	public List<Map<String, String>>  getTZCPropertyName(String keyWord,String cityId);
	
	/**
	 * 获取同致诚楼栋名称
	 * @user lic
	 * @date 2016-6-2 下午04:53:20 
	 * @param propertyId
	 * @param buildingKeyWord
	 * @return Map
	 * @return key id 	楼栋名称Id
	 * @return key name 楼栋名称
	 */
	public List<Map<String, Object>> getTZCBuilding(String propertyId , String buildingKeyWord, String cityId);
	
	/**
	 * 获取同致诚房间名称
	 * @user lic
	 * @date 2016-6-2 下午05:06:35 
	 * @param buildingId
	 * @param housesKeyWord
	 * @param cityId 
	 * @return Map
	 * @return key id 	房间名称Id
	 * @return key name 房间名称
	 */
	public List<Map<String, String>> getTZCHouses(String buildingId, String housesKeyWord, String cityId);
	
	/**
	 * 获取同致诚银行
	 * @user lic
	 * @date 2016-6-2 下午05:23:51 
	 * @param bankId
	 * @return Map
	 * @return key id 	银行名称Id
	 * @return key name 银行名称
	 */
	public List<Map<String, String>> getTZCBank(String bankId);
	
	/**
	 * 获取同致诚支行银行
	 * @user lic
	 * @date 2016-6-2 下午05:23:51 
	 * @param bankId
	 * @param subBankId
	 * @return Map
	 * @return key id 	银行名称Id
	 * @return key name 银行名称
	 */
	public List<Map<String, String>> getTZCSubBank(String bankId , String subBankId);
	
	/**
	 * 获取银行客户经理信息
	 * @user lic
	 * @date 2016-6-2 下午05:40:38 
	 * @param managerName		
	 * @param phone
	 * @return Map
	 * @return key bankId        银行id	
	 * @return key bankName      银行名称 
	 * @return key subBankId     支行id 
	 * @return key subBankName   分行名称 
	 * @return key managerId     经理id 
	 * @return key managerName   经理名称 
	 */
	public Map<String, String> getTZCBankManager(String managerName, String phone);
	
	/**
	 * 注册同致诚银行客户经理
	 * @user lic
	 * @date 2016-6-2 下午07:04:46 
	 * @param bankId
	 * @param subBankId
	 * @param managerName
	 * @param phone
	 * @return Map
	 * @return key bankId		银行id	
	 * @return key bankName		银行名称
	 * @return key subBankId	支行id
	 * @return key subBankName	分行名称
	 * @return key managerId	经理id
	 * @return key managerName	经理名称
	 */
	public Map<String, String> regTZCManager(String bankId, String subBankId,String managerName, String phone);
	
	/**
	 * 查询计算同致诚物业税费
	 * @user lic
	 * @date 2016-6-2 下午07:13:43 
	 * @param params
	 * @return Map
	 * @return key totalPrice	评估总值，单位万元
	 * @return key netPrice		评估净值，单位万元
	 * @return key tax			税费合计，单位元
	 * @return key salesTax		营业税，单位元
	 * @return key urbanTax		城建税，单位元
	 * @return key eduAttached	教育费附件，单位元
	 * @return key stamp		印花税，单位元
	 * @return key landTax		土地增值税，单位元
	 * @return key income		所得税，单位元
	 * @return key tranFees		交易手续费，单位元
	 * @return key deed			契税，单位元
	 * @return key embankFees	堤围费，单位元
	 * @return key auctionFees	拍卖处理费，单位元
	 * @return key costs		诉讼费，单位元
	 * @return key regFees		登记费，单位元
	 * @return key notaryFees	公证费，单位元
	 * @return key serFees		交易服务费，单位元
	 */
	public Map<String, Object> getTZCTax(Map<String, String> params);
	
	/**
	 * 查询同致诚询价结果
	 * @user lic
	 * @date 2016-6-3 下午02:20:18 
	 * @param params
	 * @return	List<Map> poolingEnquiryReportMaps
	 * @return key referID 		询价单流水号
	 * @return key referTime 	询价时间
	 * @return key replyTime	回复时间
	 * @return key pgsqID		询价公司内部流水号
	 * @return key propertyName	物业名称
	 * @return key buildingName	楼栋
	 * @return key houseName	房号
	 * @return key buildingArea	建筑面积
	 * @return key unitPrice	评估单价
	 * @return key totalPrice	评估总价
	 * @return key tax			预计税费
	 * @return key netPrice		评估净值
	 * @return key status		评估单状态（0 已回复 1 无法评估）
	 * @return key specialMessage特别提示信息
	 */
	public List<Map<String, String>> getTZCEnquiryResult(Map<String, String> params);
	
	/**
	 * 同致诚申请评估
	 * @user lic
	 * @date 2016-6-3 下午02:40:58 
	 * @param params
	 * @return
	 * @throws MortgageException 
	 */
	public RespStatus applyAssessTZC(EnquiryAssessDto params) throws MortgageException;
	
	/**
	 * 同致诚申请评估报告
	 * @user lic
	 * @date 2016-6-3 下午02:55:20 
	 * @param params
	 * @return
	 * @throws MortgageException
	 */
	public RespStatus applyAssessReportTZC(Map<String, String> params) throws MortgageException;
	
	/*********************************************************世联*******************************************************************/
	
	/**
	 * 获取世联物业信息
	 * @user lic
	 * @date 2016-6-2 上午10:54:00 
	 * @param keyWord
	 * @param cityId
	 * @return List<Map>            
	 * @return Map key id   物业名称Id  
	 * @return Map key name 物业名称    
	 */
	public List<Map<String, String>> getSLPropertyName(String keyWord, String cityId);
	
	
	/**
	 * 获取世联楼栋信息
	 * @user lic
	 * @date 2016-6-2 下午02:52:10 
	 * @param propertyId
	 * @param cityId
	 * @return Map                           
	 * @return key id 物业名称Id                 
	 * @return key rp                        
	 * @return key buildings List<Map> 楼栋信息  
	 * @return key buildings key id 楼栋Id     
	 * @return key buildings key name 楼栋名称   
	 */
	public Map<String, Object> getSLBuilding(String propertyId , String cityId);
	
	
	/**
	 * 获取世联房号信息
	 * @user lic
	 * @date 2016-6-2 下午02:55:32 
	 * @param buildingId
	 * @param cityId
	 * @return Map
	 * @return key id 房号Id  
	 * @return key name 房号名称
	 */
	public List<Map<String, String>> getSLHouses(String buildingId , String cityId);
	
	/**
	 * 获取世联房间详细信息
	 * @user lic
	 * @date 2016-6-2 下午03:05:10 
	 * @param houseId
	 * @param cityId
	 * @return Map
	 * @return key houseId       
   	 * @return key buildingtype  
   	 * @return key purposename   
   	 * @return key structure     
   	 * @return key builddate     
   	 * @return key rp            
   	 * @return key buildarea     
   	 * @return key structureid   
   	 * @return key isautovalue   
   	 * @return key useryear      
   	 * @return key buildingid    
	 */
	public Map<String, String> getSLHouseInfo(String houseId , String cityId);
	
	/**
	 * 世联自动询价
	 * @user lic
	 * @date 2016-6-2 下午03:16:40 
	 * @param params
	 * @param key cityId 		城市Id
	 * @param key propertyId	物业Id
	 * @param key buildingArea	面积
	 * @param key buildingId	楼栋Id
	 * @param key cons_rp		物业rp值
	 * @param key room_rp		房号rp值
	 * @param key propertyuse	物业用途
	 * @return autoPriceInfoMap
	 * @return key pricecount	案例数量
	 * @return key pricemax  	案例最大值
	 * @return key pricemin  	案例最小值
	 * @return key priceavg  	案列平均值
	 * @return key priceunit	单价
	 * @return key pricetotal	总价
	 */
	public Map<String, Object> autoPriceSL(Map<String, Object> params);

	/*********************************************************云评估*******************************************************************/

	/**
	 * 获取云评估物业名称
	 * @user Object
	 * @date 2016-11-10 下午03:47:38 
	 * @param keyWord
	 * @param cityId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Map<String, String>> getYPGPropertyName(String keyWord,String cityId) throws ClientProtocolException, IOException;
	
	/**
	 * 获取云评估楼栋
	 * @user Object
	 * @date 2016-11-10 下午03:47:27 
	 * @param propertyId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Map<String, Object>> getYPGBuilding(String propertyId,String cityId) throws ClientProtocolException, IOException;
	
	/**
	 * 获取云评估房号
	 * @user Object
	 * @date 2016-11-10 下午03:47:19 
	 * @param houseBuildingId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Map<String, String>> getYPGHouses(String houseBuildingId,String cityId) throws ClientProtocolException, IOException;
	
	/*********************************************************国策********************************************************************/
	
	/**
	 * 获取国策物业名称
	 * @user Object
	 * @date 2016-11-10 下午03:47:38 
	 * @param keyWord
	 * @param cityId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Map<String, String>> getGJPropertyName(String keyWord,String cityId) throws ClientProtocolException, IOException;
	
	/**
	 * 获取国策楼栋
	 * @user Object
	 * @date 2016-11-10 下午03:47:27 
	 * @param houseDataId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Map<String, Object>> getGJBuilding(String houseDataId,String cityId) throws ClientProtocolException, IOException;
	
	/**
	 * 获取国策房号
	 * @user Object
	 * @date 2016-11-10 下午03:47:19 
	 * @param houseBuildingId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Map<String, String>> getGJHouses(String houseBuildingId,String cityId) throws ClientProtocolException, IOException;
	
	/**
	 * 获取国策房间信息
	 * @user Object
	 * @date 2016-11-10 下午03:47:10 
	 * @param houseRoomId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Map<String, String> getGJHousesData(String houseRoomId,String cityId) throws ClientProtocolException, IOException;

	/**
	 * 删除询价
	 * 
	 * @user jiangyq
	 * @date 2017年10月30日 上午9:26:03 
	 * @param eid
	 * @return
	 */
	public int deleteEnquiry(int eid);
	/**
	 * 根据小区查询enqueryReport分页数据
	 * @user likf
	 * @date 2018年3月23日 上午11:41:24 
	 * @param params
	 * @return
	 */
	public RespPageData<Map<String, Object>> enqueryReportListByProperty(Map<String, Object> params);

	public List<Map<String, String>> getLKPGPropertyName(String keyWord,
			String cityId) throws ClientProtocolException, IOException;

	public List<Map<String, Object>> getLKPGBuilding(String propertyId, String cityName)
			throws ClientProtocolException, IOException;

	public List<Map<String, String>> getLKPGHouses(String propertyId,
			String buildingId, String cityName) throws ClientProtocolException,
			IOException;


	public List<Map<String, Object>> getLKPGPropertyImgs(String propertyId,
			String propertyName, String cityName, int type)
			throws ClientProtocolException, IOException;

	public List<Map<String, Object>> getLKPGMarketBargain(int start, int pageSize,
			int orderMode, String propertyId, String propertyName,
			String cityName) throws ClientProtocolException, IOException;

	public List<Map<String, String>> getLKPGNetworkOffer(int start, int pageSize,
			int orderMode, String propertyId, String propertyName,
			String cityName) throws ClientProtocolException, IOException;

	public Map<String, Object> selectLKPGPropertyImgAndBargainAndNetworkCount(
			String propertyId, String cityName) throws ClientProtocolException,
			IOException;

	public RespDataObject<Map<String, Object>> lKPGPropertyFeedback(
			Map<String, Object> params) throws ClientProtocolException,
			IOException;

	public RespDataObject<Map<String, Object>> lKPGEnquiryFeedback(
			Map<String, Object> params) throws ClientProtocolException,
			IOException;

	public RespDataObject<Map<String, Object>> resetLkpgToken(
			Map<String, Object> param);
	
}
