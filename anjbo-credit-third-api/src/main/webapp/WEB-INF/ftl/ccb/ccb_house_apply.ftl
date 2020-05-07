<?xml version="1.0" encoding="UTF-8"?>
<ROOT>
	<HEADER>
		<TRANNO>${tranNo!}</TRANNO>
		<COMNO>${comNo!}</COMNO>
		<BNKNO>${bankNo!}</BNKNO>
		<CHANNELNO>${channelNo!}</CHANNELNO>
		<ISN>${isn!}</ISN>
	</HEADER>
	<BODY>
		<BASEINFO>
			<propertyId>${estateNo!}</propertyId>
			<RspbPsn_Phone>${custManagerMobile!}</RspbPsn_Phone>
			<propertyType>${estateType!}</propertyType>
			<yearNo><#if yearNo?exists && yearNo!=''>${yearNo!}<#else>0</#if></yearNo>
			<#list sellList as sel>
			<SELINFO>
				<sel_CustName>${sel.name!}</sel_CustName>
				<sel_CertId>${sel.certificateNo!}</sel_CertId>
				<sel_CertTyp>${sel.certificateType!}</sel_CertTyp>
				<sel_Phone>${sel.mobile!}</sel_Phone>
			</SELINFO>
			</#list>
			<#list buyList as buy>
			<BUYINFO>
				<buy_CustName>${buy.name!}</buy_CustName>
				<buy_CertId>${buy.certificateNo!}</buy_CertId>
				<buy_CertTyp>${buy.certificateType!}</buy_CertTyp>
				<buy_Phone>${buy.mobile!}</buy_Phone>
			</BUYINFO>
			</#list>
			<actPrice><#if actPrice?exists>${actPrice!}<#else>0.00</#if></actPrice>
			<Appno>${appNo!}</Appno>
		</BASEINFO>
	</BODY>
</ROOT>