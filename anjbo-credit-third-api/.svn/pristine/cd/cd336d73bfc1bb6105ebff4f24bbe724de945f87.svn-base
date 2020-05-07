/*
 *Project: anjbo-credit-third-api
 *File: test.third.api.XmlJsonTest.java  <2017年10月19日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Ignore;
import org.junit.Test;

import com.anjbo.bean.icbc.bo.ResQPD;
import com.anjbo.utils.JsonUtil;


/**
 * @Author KangLG 
 * @Date 2017年10月19日 下午2:57:03
 * @version 1.0
 */
public class XmlJsonTest {

	@Test
	public void xml2jsonTest() throws JSONException, JsonParseException, JsonMappingException, IOException {
		//将xml转为json
		String xml = "<?xml  version=\"1.0\" encoding=\"GBK\" ?> <CMS> 	<eb> 		<pub> 			<TransCode>QPD</TransCode> 			<CIS>400090001604411</CIS> 			<BankCode>102</BankCode> 			<ID>rp.y.4000</ID> 			<TranDate>20171019</TranDate> 			<TranTime>091759000</TranTime> 			<fSeqno>20171019T091759000</fSeqno> 			<RetCode>0</RetCode> 			<RetMsg></RetMsg> 		</pub> 		<out> 			<AccNo>4000023029200124946</AccNo> 			<AccName>谬奸疙非尧架漏亭迹灿件粉帅圳申呵架（歌辐未捷歧）</AccName> 			<CurrType>RMB</CurrType> 			<AreaCode></AreaCode> 			<NextTag>2017-10-18-09.51.49.438583</NextTag> 			<TotalNum>10</TotalNum> 			<RepReserved1></RepReserved1> 			<RepReserved2></RepReserved2> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>20000</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>214124523411233</RecipAccNo> 				<RecipName>李斯</RecipName> 				<Summary>test-</Summary> 				<UseCN>保险理赔</UseCN> 				<PostScript>37102100749336</PostScript> 				<Ref>27102100914482</Ref> 				<BusCode></BusCode> 				<Oref>27102100914482</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-20.07.19.483163</TimeStamp> 				<RepReserved3>4000|00230|17294|000027</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>15000</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>12344224124142441</RecipAccNo> 				<RecipName>老杜</RecipName> 				<Summary>test-</Summary> 				<UseCN>保险理赔</UseCN> 				<PostScript>37102100749336</PostScript> 				<Ref>27102100914481</Ref> 				<BusCode></BusCode> 				<Oref>27102100914481</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-20.07.19.065698</TimeStamp> 				<RepReserved3>4000|00230|17294|000026</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>25000</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>234212414211</RecipAccNo> 				<RecipName>李泰</RecipName> 				<Summary>test-</Summary> 				<UseCN>保险理赔</UseCN> 				<PostScript>37102100749336</PostScript> 				<Ref>27102100914483</Ref> 				<BusCode></BusCode> 				<Oref>27102100914483</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-20.07.18.371085</TimeStamp> 				<RepReserved3>4000|00230|17294|000025</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>120000</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>23142421442332322</RecipAccNo> 				<RecipName>刘岩</RecipName> 				<Summary>保险理赔-</Summary> 				<UseCN>保险理赔</UseCN> 				<PostScript>37102100749330</PostScript> 				<Ref>27102100914475</Ref> 				<BusCode></BusCode> 				<Oref>27102100914475</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-13.46.37.306094</TimeStamp> 				<RepReserved3>4000|00230|17294|000024</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>110000</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>211233331111222</RecipAccNo> 				<RecipName>扬子</RecipName> 				<Summary>保险理赔-</Summary> 				<UseCN>保险理赔</UseCN> 				<PostScript>37102100749330</PostScript> 				<Ref>27102100914473</Ref> 				<BusCode></BusCode> 				<Oref>27102100914473</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-13.46.37.110807</TimeStamp> 				<RepReserved3>4000|00230|17294|000023</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>100000</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>42141242142414444</RecipAccNo> 				<RecipName>刘立</RecipName> 				<Summary>保险理赔-</Summary> 				<UseCN>保险理赔</UseCN> 				<PostScript>37102100749330</PostScript> 				<Ref>27102100914474</Ref> 				<BusCode></BusCode> 				<Oref>27102100914474</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-13.46.35.531700</TimeStamp> 				<RepReserved3>4000|00230|17294|000022</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>242</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>6222024000799000134</RecipAccNo> 				<RecipName>张一</RecipName> 				<Summary>报销</Summary> 				<UseCN>报销</UseCN> 				<PostScript></PostScript> 				<Ref></Ref> 				<BusCode></BusCode> 				<Oref>171021101531016</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-10.18.13.275216</TimeStamp> 				<RepReserved3>4000|00230|17294|000021</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>255</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>6222024000799000134</RecipAccNo> 				<RecipName>张一</RecipName> 				<Summary>报销</Summary> 				<UseCN>报销</UseCN> 				<PostScript></PostScript> 				<Ref></Ref> 				<BusCode></BusCode> 				<Oref>171021101531016</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-10.18.12.640907</TimeStamp> 				<RepReserved3>4000|00230|17294|000020</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>254</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>6222024000799000134</RecipAccNo> 				<RecipName>张一</RecipName> 				<Summary>报销</Summary> 				<UseCN>报销</UseCN> 				<PostScript></PostScript> 				<Ref></Ref> 				<BusCode></BusCode> 				<Oref>171021094918004</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-09.51.50.076294</TimeStamp> 				<RepReserved3>4000|00230|17294|000019</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 			<rd> 				<Drcrf>1</Drcrf> 				<VouhNo>0</VouhNo> 				<Amount>242</Amount> 				<RecipBkNo>0</RecipBkNo> 				<RecipAccNo>6222024000799000134</RecipAccNo> 				<RecipName>张一</RecipName> 				<Summary>报销</Summary> 				<UseCN>报销</UseCN> 				<PostScript></PostScript> 				<Ref></Ref> 				<BusCode></BusCode> 				<Oref>171021094918004</Oref> 				<EnSummary></EnSummary> 				<BusType>0</BusType> 				<CvouhType>000</CvouhType> 				<AddInfo></AddInfo> 				<TimeStamp>2017-10-18-09.51.49.438583</TimeStamp> 				<RepReserved3>4000|00230|17294|000018</RepReserved3> 				<RepReserved4></RepReserved4> 			</rd> 		</out> 	</eb> </CMS> ";
		Matcher m = Pattern.compile("(<[/]?[A-Z])").matcher(xml);
		while (m.find()) {
			xml = xml.replace(m.group(), m.group().toLowerCase());
		}
		System.out.println(xml);
		
		JSONObject json = XML.toJSONObject(xml);				
		//设置缩进  
	    String jsonFormat = json.toString(2);  
	    System.out.println(jsonFormat); 
	    
		JSONObject jsonOut = json.getJSONObject("cMS").getJSONObject("eb").getJSONObject("out");
		
//		ResQPD obj = (ResQPD)net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(jsonOut.toString()), ResQPD.class);
		ResQPD obj = (ResQPD)JsonUtil.parseJsonToObj(jsonOut.toString(), ResQPD.class);
		
		System.out.println(obj.getAccNo());
	    System.out.println(obj.getAccName());
	    System.out.println(obj.getRd().size());
	}
	
	@Ignore
	@Test
	public void test() throws JSONException, JsonParseException, JsonMappingException, IOException {
		String xml = "<AccNo>123456</AccNo><accName>哈哈</accName>";
		Matcher m = Pattern.compile("(<[/]?[A-Z])").matcher(xml);
		while (m.find()) {
			xml = xml.replaceAll(m.group(), m.group().toLowerCase());
		}
		System.out.println(xml);
		
		JSONObject json = XML.toJSONObject(xml);
		ResQPD obj = (ResQPD)JsonUtil.parseJsonToObj(json.toString(), ResQPD.class);
		
		System.out.println(obj.getAccNo());
	    System.out.println(obj.getAccName());
	}
	

}
