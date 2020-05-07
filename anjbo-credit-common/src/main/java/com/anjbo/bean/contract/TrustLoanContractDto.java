package com.anjbo.bean.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrustLoanContractDto {

	/**业务信息*/
	/**签署日期*/
	private Date dateOfSigning;
	private String dateOfSigningStr;
	/**签署城市*/
	private String cityOfSigning;
	private String cityNameOfSigning;
	/**签署区*/
	private String regionOfSigning;
	private String regionNameOfSigning;
	/** 借款金额（万元） **/
	private Double borrowingAmount;
	/** 偿还总金额（万元） */
	private Double repayTotalAmount;
	 /**出款卡*/
    /**银行卡户名*/
    private String bankCardMaster;
    /**银行卡账号*/
    private String bankNo;
    /**开户银行id*/
    private Integer bankNameId;
    /**支行id*/
    private Integer bankSubNameId;
    /**银行名称*/
    private String bankName;
    /**支行名称*/
    private String bankSubName;
    /**合同共同借款人*/
    private List<ContractCustomerBorrowerDto> contractCustomerBorrowerDto;
	public TrustLoanContractDto() {
		super();
	}
	public Date getDateOfSigning() {
		return dateOfSigning;
	}
	public void setDateOfSigning(Date dateOfSigning) {
		this.dateOfSigning = dateOfSigning;
	}
	public String getDateOfSigningStr() {
		return dateOfSigningStr;
	}
	public void setDateOfSigningStr(String dateOfSigningStr) {
		this.dateOfSigningStr = dateOfSigningStr;
	}
	public String getCityOfSigning() {
		return cityOfSigning;
	}
	public void setCityOfSigning(String cityOfSigning) {
		this.cityOfSigning = cityOfSigning;
	}
	public String getCityNameOfSigning() {
		return cityNameOfSigning;
	}
	public void setCityNameOfSigning(String cityNameOfSigning) {
		this.cityNameOfSigning = cityNameOfSigning;
	}
	public String getRegionOfSigning() {
		return regionOfSigning;
	}
	public void setRegionOfSigning(String regionOfSigning) {
		this.regionOfSigning = regionOfSigning;
	}
	public String getRegionNameOfSigning() {
		return regionNameOfSigning;
	}
	public void setRegionNameOfSigning(String regionNameOfSigning) {
		this.regionNameOfSigning = regionNameOfSigning;
	}
	public Double getBorrowingAmount() {
		return borrowingAmount;
	}
	public void setBorrowingAmount(Double borrowingAmount) {
		this.borrowingAmount = borrowingAmount;
	}
	public Double getRepayTotalAmount() {
		return repayTotalAmount;
	}
	public void setRepayTotalAmount(Double repayTotalAmount) {
		this.repayTotalAmount = repayTotalAmount;
	}
	public String getBankCardMaster() {
		return bankCardMaster;
	}
	public void setBankCardMaster(String bankCardMaster) {
		this.bankCardMaster = bankCardMaster;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public Integer getBankNameId() {
		return bankNameId;
	}
	public void setBankNameId(Integer bankNameId) {
		this.bankNameId = bankNameId;
	}
	public Integer getBankSubNameId() {
		return bankSubNameId;
	}
	public void setBankSubNameId(Integer bankSubNameId) {
		this.bankSubNameId = bankSubNameId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankSubName() {
		return bankSubName;
	}
	public void setBankSubName(String bankSubName) {
		this.bankSubName = bankSubName;
	}
	public List<ContractCustomerBorrowerDto> getContractCustomerBorrowerDto() {
		if(null==this.contractCustomerBorrowerDto||this.contractCustomerBorrowerDto.size()<=0){
			this.contractCustomerBorrowerDto = new ArrayList<ContractCustomerBorrowerDto>();
		}
		return contractCustomerBorrowerDto;
	}
	public void setContractCustomerBorrowerDto(
			List<ContractCustomerBorrowerDto> contractCustomerBorrowerDto) {
		this.contractCustomerBorrowerDto = contractCustomerBorrowerDto;
	}
    
    
}
