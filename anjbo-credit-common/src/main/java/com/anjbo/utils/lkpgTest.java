package com.anjbo.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

public class lkpgTest {

	public static void main(String[] args) throws IOException, JsonMappingException, IOException {
		HttpUtil httpUtil=new HttpUtil();
		//自动估价维护列表list接口
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("propertyName", "凤凰路");
				map.put("start", "0");
				map.put("pageSize", "10");
				map.put("tableCity", "深圳");
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/autoAssessMaintain/v/list",map);
				System.out.println(result);*/
				
				
				//全部套数列表list接口
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("propertyName", "凤凰路");
				map.put("houseType", "办公、商务公寓");
				map.put("propertyId", "1");
				map.put("tableCity", "深圳");
				map.put("start", "0");
				map.put("pageSize", "10");
						
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/totalsuite/v/list",map);
				System.out.println(result);*/
				
				//获取房屋信息  上半部分信息
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("buildingId", "2");
				map.put("roomId", "1");
						
				map.put("start", "0");
				map.put("pageSize", "10");
				map.put("tableCity", "深圳");				
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/totalsuitedetail/v/roomInfo",map);
				System.out.println(result);*/
				
				//获取具体房屋信息 房屋参数界面，下半部分信息
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("roomId", "907");
						
				map.put("start", "0");
				map.put("pageSize", "10");
				map.put("tableCity", "深圳");
								
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/totalsuitedetail/v/housedetail",map);
				System.out.println(result);*/
		
		
		
				//保存房屋参数信息接口
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"housedetail\":{\"id\":null,\"roomId\":null,\"imgs\":null,\"located\":\"测试坐落\",\"totalFloor\":\"20\",\"floor\":\"15\",\"unit\":\"一梯俩户\",\"unitStructure\":\"平面\",\"room\":\"1\",\"hall\":\"1\",\"bathroom\":\"1\",\"kitchen\":\"1\",\"towards\":\"东\",\"position\":\"中间位置\",\"buildingStructure\":\"框剪结构\",\"landShape\":\"矩形\",\"developmentDegree\":\"五通一平\",\"oldDegree\":\"九成新\",\"useStatus\":\"自用\",\"airLight\":\"良好\",\"landscape\":\"海景\",\"giveArea\":\"10\",\"decorationLevel\":\"精装\",\"exteriorWall\":null,\"ground\":null,\"interiorWall\":null,\"ceiling\":null,\"window\":\"钢窗\",\"balcony\":\"开放式\",\"east\":\"1\",\"western\":\"3\",\"south\":\"4\",\"north\":\"2\",\"network\":true,\"television\":true,\"gas\":null,\"elevator\":null,\"school\":\"深圳大学\",\"hospital\":\"南山医院\",\"bank\":\"招商银行\",\"residential\":\"某某小区\",\"terrane\":\"深大\",\"publicTransport\":\"测试站台\",\"busRoutes\":\"385\",\"fitment\":\"测试装置装修\",\"imgsOne\":null,\"imgsTwo\":null},\"imgs\":[{\"type\":\"位置图\",\"url\":\"http://fs.zxsf360.com/imgs/all/1508314711500.gif\"}],\"imgsOne\":[{\"type\":\"客厅照片\",\"url\":\"http://fs.zxsf360.com/imgs/all/1508314721571.gif\"},{\"type\":\"房间照片\",\"url\":\"http://fs.zxsf360.com/imgs/all/1508314730713.gif\"},{\"type\":\"阳台照片\",\"url\":\"http://fs.zxsf360.com/imgs/all/1508314738761.gif\"},{\"type\":\"厨房照片\",\"url\":\"http://fs.zxsf360.com/imgs/all/1508314746736.gif\"},{\"type\":\"卫生间照片\",\"url\":\"http://fs.zxsf360.com/imgs/all/1508314754746.gif\"},{\"type\":\"门牌号照片\",\"url\":\"http://fs.zxsf360.com/imgs/all/1508314764639.gif\"}],\"imgsTwo\":[{\"type\":\"附录\",\"url\":\"http://fs.zxsf360.com/imgs/all/1508314773004.gif\"},{\"type\":\"附录\",\"url\":\"http://fs.zxsf360.com/imgs/all/1508314786833.gif\"}],\"room\":{\"id\":null,\"propertyId\":\"10\",\"propertyName\":\"35区住宅\",\"buildingId\":null,\"buildingName\":\"B4栋\",\"roomId\":\"2533\",\"roomName\":\"202\",\"area\":70,\"region\":null,\"averagePrice\":null,\"totalSuite\":null,\"houseType\":\"住宅\",\"totalFloor\":null,\"unitPrice\":null,\"remarks\":null,\"registeredDate\":\"2017-10-18\",\"registeredPrice\":\"5000000\",\"rightHolder\":\"个人\",\"updateAveraggePriceTime\":null,\"mark\":null,\"uId\":null,\"name\":null,\"beforePrice\":null,\"landLocation\":\"深大\",\"estateType\":null,\"titleCard\":null,\"houseCardNo\":null,\"landUseTime\":\"60\",\"housingType\":\"住宅\",\"landType\":\"住宅\",\"landArea\":\"50\"}}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);				
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/totalsuitedetail/v/savehousedetail",map);
				System.out.println(result);*/
		
		
		
	
		
				//获取税费配置List
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("start", 0);	
				map.put("pageSize", 20);
				map.put("subNum", "10");				
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/taxConfigure/v/list",map);
				System.out.println(result);*/
				//获取税费配置详细信息接口
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("configId", 780);//1109			
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/taxConfigure/v/taxConfigure",map);
				System.out.println(result);*/
				//添加机构税费配置
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"configId\":-1,\"netValues\":[{\"assessNetValueName\":\"评估净值一\",\"enabled\":\"1\",\"urbanConstructionTax\":true,\"stampDuty\":true},{\"assessNetValueName\":\"评估净值二\",\"enabled\":\"1\",\"stampDuty\":true,\"urbanConstructionTax\":true},{\"assessNetValueName\":\"评估净值三\",\"enabled\":\"1\",\"landAppreciationTax\":true,\"additionalTaxOfEducation\":true}],\"type\":\"1\",\"bankId\":\"42\",\"agencyName\":\"测试机构\",\"cityName\":\"测试城市名\"}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				//String result=httpUtil.jsonPost("http://localhost:8093/pg/system/taxConfigure/v/save",map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/taxConfigure/v/save",map);
				System.out.println(result);*/
				//添加银行税费配置
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"configId\":-1,\"netValues\":[{\"assessNetValueName\":\"评估净值一\",\"enabled\":\"1\",\"urbanConstructionTax\":true,\"stampDuty\":true},{\"assessNetValueName\":\"评估净值二\",\"enabled\":\"1\",\"stampDuty\":true,\"urbanConstructionTax\":true}],\"type\":\"0\",\"bankId\":\"10000020\",\"cityName\":\"测试城市名深圳\"}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/taxConfigure/v/save",map);
				System.out.println(result);*/
				//编辑银行税费配置
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"id\":10000023,\"configId\":0,\"type\":\"0\",\"agencyId\":0,\"agencyName\":\"深圳分行\",\"bankId\":\"10000020\",\"bankName\":null,\"subBankId\":\"11413\",\"subName\":null,\"netValues\":[{\"id\":7736,\"configId\":10000023,\"assessNetValueName\":\"评估净值一\",\"enabled\":1,\"vat\":true,\"urbanConstructionTax\":false,\"additionalTaxOfEducation\":false,\"stampDuty\":false,\"landAppreciationTax\":false,\"incomeTax\":true,\"tradingServiceCharges\":true},{\"id\":7637,\"configId\":10000023,\"assessNetValueName\":\"评估净值二\",\"enabled\":1,\"vat\":false,\"urbanConstructionTax\":true,\"additionalTaxOfEducation\":true,\"stampDuty\":true,\"landAppreciationTax\":false,\"incomeTax\":true,\"tradingServiceCharges\":true}],\"assessNetValueName\":null}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/taxConfigure/v/save",map);
				System.out.println(result);*/
				
				//编辑机构税费配置
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"id\":948,\"configId\":0,\"type\":\"1\",\"agencyId\":0,\"agencyName\":\"非凡机构\",\"bankId\":\"10000019\",\"bankName\":null,\"subBankId\":\"11411\",\"subName\":null,\"netValues\":[{\"id\":4475,\"configId\":948,\"assessNetValueName\":\"评估净值一\",\"enabled\":1,\"vat\":true,\"urbanConstructionTax\":false,\"additionalTaxOfEducation\":false,\"stampDuty\":true,\"landAppreciationTax\":false,\"incomeTax\":false,\"tradingServiceCharges\":false},{\"id\":4476,\"configId\":948,\"assessNetValueName\":\"评估净值二\",\"enabled\":1,\"vat\":false,\"urbanConstructionTax\":false,\"additionalTaxOfEducation\":true,\"stampDuty\":false,\"landAppreciationTax\":false,\"incomeTax\":false,\"tradingServiceCharges\":false},{\"id\":4477,\"configId\":948,\"assessNetValueName\":\"评估净值三\",\"enabled\":1,\"vat\":false,\"urbanConstructionTax\":true,\"additionalTaxOfEducation\":false,\"stampDuty\":false,\"landAppreciationTax\":false,\"incomeTax\":true,\"tradingServiceCharges\":false}],\"assessNetValueName\":null}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/taxConfigure/v/save",map);
				System.out.println(result);*/
				
				//新增城市
				/*Map<String,Object> map=new HashMap<String,Object>();
				//String str="{\"cityCode\":\"sh01\",\"cityFormwork\":1,\"cityName\":\"上海\",\"isDelete\":0,\"isEnable\":0,\"sort\":5}";
				//String str="{\"cityCode\":\"dg01\",\"cityFormwork\":1,\"cityName\":\"东莞\",\"isDelete\":0,\"isEnable\":0,\"sort\":2}";
				String str="{\"cityCode\":\"gz01\",\"cityFormwork\":1,\"cityName\":\"广州\",\"isDelete\":0,\"isEnable\":0,\"sort\":1}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/cityConfigure/v/save",map);
				System.out.println(result);*/
				//编辑城市
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"cityCode\":\"hz01\",\"cityFormwork\":1,\"cityName\":\"惠州\",\"id\":4,\"isDelete\":0,\"isEnable\":0,\"sort\":4}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/cityConfigure/v/save",map);
				System.out.println(result);*/
				//生成城市代码接口
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"cityCode\":\"hz01\",\"cityFormwork\":1,\"cityName\":\"惠州\",\"id\":4,\"isDelete\":0,\"isEnable\":0,\"sort\":4}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/cityConfigure/v/code",map);
				System.out.println(result);*/
		
				//判断城市是否重复接口
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"cityCode\":\"hz01\",\"cityFormwork\":1,\"cityName\":\"惠州\",\"id\":4,\"isDelete\":0,\"isEnable\":0,\"sort\":4}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/cityConfigure/v/isRepeatToAdd",map);
				System.out.println(result);*/
		
				//获取内部成员总数
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("start", 0);
				map.put("pageSize", 0);
				map.put("subNum", 10);
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/user/v/count",map);
				System.out.println(result);*/
				
				//获取内部成员列表
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("start", 0);
				map.put("pageSize", 10);
				map.put("subNum", 10);
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/user/v/list",map);
				System.out.println(result);*/
		
				//获取对应的角色列表
				/*Map<String,Object> map=new HashMap<String,Object>();
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/roleConfigure/v/listAll",map);
				System.out.println(result);*/
		
		 		//内部成员编辑接口，获取该帐号的详细信息
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"mobile\":18926473051}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/user/v/detail",map);
				System.out.println(result);*/
		
				//保存或修改内部成员接口
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"start\":0,\"pageSize\":0,\"sid\":null,\"device\":null,\"deviceId\":null,\"version\":\"pg-1.0\",\"id\":210,\"uid\":\"1498630613034\",\"name\":\"李灿\",\"account\":\"lc001\",\"password\":\"4a7bf604eac459ac6125f094f4a627b6\",\"agencyId\":-1,\"mobile\":\"18926473051\",\"bankId\":0,\"subBankId\":0,\"agencyType\":0,\"createTime\":1498630610000,\"type\":0,\"insideName\":null,\"roleId\":0,\"bankName\":null,\"subName\":null,\"roleConfigureDtos\":[{\"start\":0,\"pageSize\":0,\"sid\":null,\"device\":null,\"deviceId\":null,\"version\":\"pg-1.0\",\"id\":239,\"name\":\"估价师\",\"describe\":null,\"count\":0,\"agencyId\":0,\"isEnable\":0,\"createTime\":null,\"lastUpdateTime\":null}],\"roleIds\":[239],\"retain\":null}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/user/v/addOrUpdate",map);
				System.out.println(result);*/
		
				//生成帐号和密码接口
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("id", 0);
				map.put("retain", 0);
				map.put("name", "李某某");
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/user/v/gennerAccountPwd",map);
				System.out.println(result);*/
		
				//保存帐号接口，新增内部成员
				Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"citys\":[{\"cityCode\":\"cs1\",\"cityName\":\"测试1\"},{\"cityCode\":\"cs2\",\"cityName\":\"测试2\"}],\"id\":0,\"retain\":0,\"name\":\"李某某\",\"account\":\"lmm001\",\"password\":\"326200\",\"roleIds\":[240],\"roleConfigureDtos\":[{\"start\":0,\"pageSize\":0,\"sid\":null,\"device\":null,\"deviceId\":null,\"version\":\"pg-1.0\",\"id\":240,\"name\":\"业务员\",\"describe\":\"业务员1\",\"count\":0,\"agencyId\":0,\"isEnable\":0,\"createTime\":1482994790000,\"lastUpdateTime\":1483700867000}],\"mobile\":\"18271261013\"}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/user/v/addOrUpdate",map);
				System.out.println(result);
		
				//内部成员重置密码接口
				/*Map<String,Object> map=new HashMap<String,Object>();
				String str="{\"mobile\":\"18271261013\",\"type\":1}";
				map=(Map<String, Object>) JsonUtil.parseJsonToObj(str,map.getClass());
				System.out.println(map);
				String result=httpUtil.jsonPost("http://localhost:8094/lkpg/system/user/v/resetPwd",map);
				System.out.println(result);*/
		
		
				
	}

}
