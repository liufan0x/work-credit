package com.anjbo.bean.yntrust;

import com.anjbo.bean.BaseDto;

import java.util.Date;

/**
 * 云南信托借款人信息
 */
public class YntrustBorrowDto extends BaseDto{
    /**
     *
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.id
     *
     * @mbggenerated 2018-03-08
     */
    private Integer id;

    /**
     *订单编号
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.orderNo
     *
     * @mbggenerated 2018-03-08
     */
    private String orderNo;

    /**
     *客户姓名->订单借款人姓名
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.shortName
     *
     * @mbggenerated 2018-03-08
     */
    private String shortName;

    /**
     *银行开户预留手机号(默认订单手机号码)
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.bankReservedPhoneNo
     *
     * @mbggenerated 2018-03-08
     */
    private String bankReservedPhoneNo;

    /**
     *身份证号码
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.iDCardNo
     *
     * @mbggenerated 2018-03-08
     */
    private String iDCardNo;

    /**
     *证件类型(1:身份证)
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.cardType
     *
     * @mbggenerated 2018-03-08
     */
    private Integer cardType;

    /**
     *婚姻状况(10:未婚,20:已婚,21:初婚,22:再婚,23:复婚,30:丧偶,40:离婚,90:未说明的婚姻状况)
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.maritalStatus
     *
     * @mbggenerated 2018-03-08
     */
    private Integer maritalStatus;

    /**
     *11位有效手机号码
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.telephoneNo
     *
     * @mbggenerated 2018-03-08
     */
    private String telephoneNo;

    /**
     *居住城市(省市区三级的地区code)
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.city
     *
     * @mbggenerated 2018-03-08
     */
    private String city;

    /**
     *职业分类(传对应数字，1=政府部门，2=教科文，3=金融，4=商贸，5=房地产，6=制造业，7=自由职业)
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.jobType
     *
     * @mbggenerated 2018-03-08
     */
    private String jobType;

    /**
     *职业分类名称
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.jobTypeName
     *
     * @mbggenerated 2018-03-08
     */
    private String jobTypeName;

    /**
     *角色分类(贷款借款人=1；其他投融资交易对手=2；抵质押人担保人贷款类=3；抵质押人担保人非贷款类=4；委托方=5；其他对手方等付费对象=6)
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.roleType
     *
     * @mbggenerated 2018-03-08
     */
    private String roleType;

    /**
     *角色分类分类名称
     *
     * This field corresponds to the database column tbl_third_yntrust_borrow.roleTypeName
     *
     * @mbggenerated 2018-03-08
     */
    private String roleTypeName;

    /**
     * 证件类型名称

     */
    private String cardTypeName;
    /**
     * 婚姻状态名称
     */
    private String maritalStatusName;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 推送状态(0:初始化,1:推送成功,2:推送失败)
     */
    private Integer pushStatus;
    /**
     * 云南信托产品编码
     */
    private String ynProductCode;
    /**
     * 云南信托产品编码名称
     */
    private String ynProductName;
    /**
     * 与云南信托合同全局唯一id
     */
    private String uniqueId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getBankReservedPhoneNo() {
        return bankReservedPhoneNo;
    }

    public void setBankReservedPhoneNo(String bankReservedPhoneNo) {
        this.bankReservedPhoneNo = bankReservedPhoneNo == null ? null : bankReservedPhoneNo.trim();
    }

    public String getiDCardNo() {
        return iDCardNo;
    }

    public void setiDCardNo(String iDCardNo) {
        this.iDCardNo = iDCardNo == null ? null : iDCardNo.trim();
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo == null ? null : telephoneNo.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    public String getJobTypeName() {
        return jobTypeName;
    }

    public void setJobTypeName(String jobTypeName) {
        this.jobTypeName = jobTypeName == null ? null : jobTypeName.trim();
    }
    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType == null ? null : roleType.trim();
    }

    public String getRoleTypeName() {
        return roleTypeName;
    }

    public void setRoleTypeName(String roleTypeName) {
        this.roleTypeName = roleTypeName == null ? null : roleTypeName.trim();
    }
    public String getCardTypeName() {
        return cardTypeName;
    }
    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getMaritalStatusName() {
        return maritalStatusName;
    }

    public void setMaritalStatusName(String maritalStatusName) {
        this.maritalStatusName = maritalStatusName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getYnProductCode() {
        return ynProductCode;
    }

    public void setYnProductCode(String ynProductCode) {
        this.ynProductCode = ynProductCode;
    }

    public String getYnProductName() {
        return ynProductName;
    }

    public void setYnProductName(String ynProductName) {
        this.ynProductName = ynProductName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}