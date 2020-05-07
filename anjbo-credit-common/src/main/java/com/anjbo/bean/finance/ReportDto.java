package com.anjbo.bean.finance;

import com.anjbo.bean.BaseDto;

import java.util.Date;
import java.util.List;

public class ReportDto extends BaseDto{
    /**
     *
     *
     * This field corresponds to the database column tbl_finance_report.id
     *
     * @mbggenerated 2017-09-22
     */
    private Integer id;


    /**
     *订单编号
     *
     * This field corresponds to the database column tbl_finance_report.orderNo
     *
     * @mbggenerated 2017-09-22
     */
    private String orderNo;

    /**
     *客户姓名
     *
     * This field corresponds to the database column tbl_finance_report.customerName
     *
     * @mbggenerated 2017-09-22
     */
    private String customerName;

    /**
     *城市code
     *
     * This field corresponds to the database column tbl_finance_report.cityCode
     *
     * @mbggenerated 2017-09-22
     */
    private String cityCode;

    /**
     *城市名称
     *
     * This field corresponds to the database column tbl_finance_report.cityName
     *
     * @mbggenerated 2017-09-22
     */
    private String cityName;

    /**
     *产品code
     *
     * This field corresponds to the database column tbl_finance_report.productCode
     *
     * @mbggenerated 2017-09-22
     */
    private String productCode;

    /**
     *产品名称
     *
     * This field corresponds to the database column tbl_finance_report.productName
     *
     * @mbggenerated 2017-09-22
     */
    private String productName;

    /**
     *借款金额
     *
     * This field corresponds to the database column tbl_finance_report.borrowingAmount
     *
     * @mbggenerated 2017-09-22
     */
    private Double loanAmount;

    /**
     *借款天数
     *
     * This field corresponds to the database column tbl_finance_report.borrowingDay
     *
     * @mbggenerated 2017-09-22
     */
    private Integer borrowingDays;

    /**
     *受理员uid
     *
     * This field corresponds to the database column tbl_finance_report.acceptMemberUid
     *
     * @mbggenerated 2017-09-22
     */
    private String acceptMemberUid;

    /**
     *受理员名称
     *
     * This field corresponds to the database column tbl_finance_report.acceptMemberName
     *
     * @mbggenerated 2017-09-22
     */
    private String acceptMemberName;

    /**
     *渠道经理uid
     *
     * This field corresponds to the database column tbl_finance_report.channelManagerUid
     *
     * @mbggenerated 2017-09-22
     */
    private String channelManagerUid;

    /**
     *渠道经理名称
     *
     * This field corresponds to the database column tbl_finance_report.channelManagerName
     *
     * @mbggenerated 2017-09-22
     */
    private String channelManagerName;

    /**
     *机构id
     *
     * This field corresponds to the database column tbl_finance_report.agencyId
     *
     * @mbggenerated 2017-09-22
     */
    private Integer agencyId;

    /**
     *合作机构id
     *
     * This field corresponds to the database column tbl_finance_report.cooperativeAgencyId
     *
     * @mbggenerated 2017-09-22
     */
    private Integer cooperativeAgencyId;

    /**
     *合作机构名称
     *
     * This field corresponds to the database column tbl_finance_report.cooperativeAgencyName
     *
     * @mbggenerated 2017-09-22
     */
    private String cooperativeAgencyName;

    /**
     *报备出款时间
     *
     * This field corresponds to the database column tbl_finance_report.estimateOutLoanTime
     *
     * @mbggenerated 2017-09-22
     */
    private Date estimateOutLoanTime;
    
    private String estimateOutLoanTimeStr;

    /**
     *扣款方式
     *
     * This field corresponds to the database column tbl_finance_report.paymentType
     *
     * @mbggenerated 2017-09-22
     */
    private String paymentType;

    /**
     *资金方
     *
     * This field corresponds to the database column tbl_finance_report.fund
     *
     * @mbggenerated 2017-09-22
     */
    private String fund;

    /**
     *报备状态(0:初始化1:已放款,2,未放款,3:报备撤销)
     *
     * This field corresponds to the database column tbl_finance_report.status
     *
     * @mbggenerated 2017-09-22
     */
    private Integer status;

    /**
     *备注
     *
     * This field corresponds to the database column tbl_finance_report.remark
     *
     * @mbggenerated 2017-09-22
     */
    private String remark;
    /**
     * 报备出款开始时间(主要用于列表过滤)
     */
    private Date outLoanStartTime;
    /**
     * 报备出款结束时间(主要用于列表过滤)
     */
    private Date outLoanEndTime;
    /**
     * 报备开始时间(主要用于列表过滤)
     */
    private Date createStartTime;
    /**
     * 报备结束时间(主要用于列表过滤)
     */
    private Date createEndTime;
    /**
     * 节点
     */
    private String processId;
    
    private String state;
    /**
     * 修改记录
     */
    private List<ReportEditRecordDto> reportEditRecord;
    /**
     * 回复记录
     */
    private List<ReportReplyRecordDto> reportReplyRecord;
    /**
     * 将修改记录集合以拼接的形式保存
     */
    private String reportEditRecordStr;
    
    /**
     * app展示key，修改记录
     */
    private String editrecord;
    
    /**
     * app展示key，财务回复
     */
    private String replyrecord;
    /**
     * 将回复记录集合以拼接的形式展示
     */
    private String reportReplyRecordStr;
    /**
     * 关联订单编号
     */
    private String relationOrderNo;
    
    private String createTimeStr;
    /**
     * 创建人名字
     */
    private String createName;
    /**
     * 区别是在那里访问
     */
    private String arrangement;
    /**
     * 报备出款时间排序
     */
    private String estimateOutLoanTimeOrderBy;
    /**
     * 财务预计出款时间
     */
    private Date financeOutLoanTime;
    /**
     * 财务预计出款时间(用于列表检索)
     */
    private Date financeOutLoanStartTime;
    /**
     * 财务预计出款时间(用于列表检索)
     */
    private Date financeOutLoanEndTime;

    private String financeOutLoanEndTimeStr;
    /**
     * 资方审核状态
     */
    private String fundExamine;
    /**
     * 是否置顶
     */
    private int top;
    /**
     * 排队
     */
    private Long sort;
    /**
     * 客户类型
     */
    private String customerType;

    private String customerTypeName;
    private String deptName;  //部门
    

    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }


    public String getAcceptMemberUid() {
        return acceptMemberUid;
    }

    public void setAcceptMemberUid(String acceptMemberUid) {
        this.acceptMemberUid = acceptMemberUid == null ? null : acceptMemberUid.trim();
    }

    public String getAcceptMemberName() {
        return acceptMemberName;
    }

    public void setAcceptMemberName(String acceptMemberName) {
        this.acceptMemberName = acceptMemberName == null ? null : acceptMemberName.trim();
    }

    public String getChannelManagerUid() {
        return channelManagerUid;
    }

    public void setChannelManagerUid(String channelManagerUid) {
        this.channelManagerUid = channelManagerUid == null ? null : channelManagerUid.trim();
    }

    public String getChannelManagerName() {
        return channelManagerName;
    }

    public void setChannelManagerName(String channelManagerName) {
        this.channelManagerName = channelManagerName == null ? null : channelManagerName.trim();
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Integer getCooperativeAgencyId() {
        return cooperativeAgencyId;
    }

    public void setCooperativeAgencyId(Integer cooperativeAgencyId) {
        this.cooperativeAgencyId = cooperativeAgencyId;
    }

    public String getCooperativeAgencyName() {
        return cooperativeAgencyName;
    }

    public void setCooperativeAgencyName(String cooperativeAgencyName) {
        this.cooperativeAgencyName = cooperativeAgencyName == null ? null : cooperativeAgencyName.trim();
    }
    public Date getEstimateOutLoanTime() {
        return estimateOutLoanTime;
    }

    public void setEstimateOutLoanTime(Date estimateOutLoanTime) {
        this.estimateOutLoanTime = estimateOutLoanTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund == null ? null : fund.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Date getOutLoanStartTime() {
		return outLoanStartTime;
	}

	public void setOutLoanStartTime(Date outLoanStartTime) {
		this.outLoanStartTime = outLoanStartTime;
	}

	public Date getOutLoanEndTime() {
		return outLoanEndTime;
	}

	public void setOutLoanEndTime(Date outLoanEndTime) {
		this.outLoanEndTime = outLoanEndTime;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<ReportEditRecordDto> getReportEditRecord() {
		return reportEditRecord;
	}

	public void setReportEditRecord(List<ReportEditRecordDto> reportEditRecord) {
		this.reportEditRecord = reportEditRecord;
	}

	public List<ReportReplyRecordDto> getReportReplyRecord() {
		return reportReplyRecord;
	}

	public void setReportReplyRecord(List<ReportReplyRecordDto> reportReplyRecord) {
		this.reportReplyRecord = reportReplyRecord;
	}

	public String getReportEditRecordStr() {
		return reportEditRecordStr;
	}

	public void setReportEditRecordStr(String reportEditRecordStr) {
		this.reportEditRecordStr = reportEditRecordStr;
	}

	public String getReportReplyRecordStr() {
		return reportReplyRecordStr;
	}

	public void setReportReplyRecordStr(String reportReplyRecordStr) {
		this.reportReplyRecordStr = reportReplyRecordStr;
	}

	public String getRelationOrderNo() {
		return relationOrderNo;
	}

	public void setRelationOrderNo(String relationOrderNo) {
		this.relationOrderNo = relationOrderNo;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getBorrowingDays() {
		return borrowingDays;
	}

	public void setBorrowingDays(Integer borrowingDays) {
		this.borrowingDays = borrowingDays;
	}

	public String getEstimateOutLoanTimeStr() {
		return estimateOutLoanTimeStr;
	}

	public void setEstimateOutLoanTimeStr(String estimateOutLoanTimeStr) {
		this.estimateOutLoanTimeStr = estimateOutLoanTimeStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getArrangement() {
		return arrangement;
	}

	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}

	public String getEstimateOutLoanTimeOrderBy() {
		return estimateOutLoanTimeOrderBy;
	}

	public void setEstimateOutLoanTimeOrderBy(String estimateOutLoanTimeOrderBy) {
		this.estimateOutLoanTimeOrderBy = estimateOutLoanTimeOrderBy;
	}

	public String getEditrecord() {
		return editrecord;
	}

	public void setEditrecord(String editrecord) {
		this.editrecord = editrecord;
	}

	public String getReplyrecord() {
		return replyrecord;
	}

	public void setReplyrecord(String replyrecord) {
		this.replyrecord = replyrecord;
	}

    public Date getFinanceOutLoanTime() {
        return financeOutLoanTime;
    }

    public void setFinanceOutLoanTime(Date financeOutLoanTime) {
        this.financeOutLoanTime = financeOutLoanTime;
    }

    public String getFundExamine() {
        return fundExamine;
    }

    public void setFundExamine(String fundExamine) {
        this.fundExamine = fundExamine;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Date getFinanceOutLoanStartTime() {
        return financeOutLoanStartTime;
    }

    public void setFinanceOutLoanStartTime(Date financeOutLoanStartTime) {
        this.financeOutLoanStartTime = financeOutLoanStartTime;
    }

    public Date getFinanceOutLoanEndTime() {
        return financeOutLoanEndTime;
    }

    public void setFinanceOutLoanEndTime(Date financeOutLoanEndTime) {
        this.financeOutLoanEndTime = financeOutLoanEndTime;
    }

    public String getFinanceOutLoanEndTimeStr() {
        return financeOutLoanEndTimeStr;
    }

    public void setFinanceOutLoanEndTimeStr(String financeOutLoanEndTimeStr) {
        this.financeOutLoanEndTimeStr = financeOutLoanEndTimeStr;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
}