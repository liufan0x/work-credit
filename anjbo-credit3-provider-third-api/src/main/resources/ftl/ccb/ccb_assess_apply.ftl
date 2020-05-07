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
			<Cst_Nm>${ownerName!}</Cst_Nm>
			<Crdt_TpCd>${ownerCertificateType!}</Crdt_TpCd>
			<Crdt_No>${ownerCertificateNo!}</Crdt_No>
			<propertyId>${estateNo!}</propertyId>
			<RspbPsn_Phone>${custManagerMobile!}</RspbPsn_Phone>
			<propertyType>${estateType!}</propertyType>
			<yearNo>${yearNo!}</yearNo>
			<actPrice><#if actPrice?exists>${actPrice}<#else>0.00</#if></actPrice>
			<index>${index}</index>
			<remark>${remark!}</remark>
		</BASEINFO>
	</BODY>
</ROOT>