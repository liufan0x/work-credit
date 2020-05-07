package com.anjbo.bean.yntrust;

import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 云南信托与快鸽关联信息
 */
@ApiModel(value = "云南信托与快鸽关联映射信息")
public class YntrustMappingDto extends BaseDto {
    /**
     *
     *
     * This field corresponds to the database column tbl_third_yntrust_mapping.id
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "云南信托与快鸽关联映射信息")
    private Integer id;

    /**
     *快鸽订单号
     *
     * This field corresponds to the database column tbl_third_yntrust_mapping.orderNo
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "信贷系统订单编号")
    private String orderNo;

    /**
     *此次推送给云南信托对应全局编号
     *
     * This field corresponds to the database column tbl_third_yntrust_mapping.uniqueId
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "信托系统与云南信托全局唯一编号")
    private String uniqueId;

    /**
     *推送状态
     *
     * This field corresponds to the database column tbl_third_yntrust_mapping.status
     *
     * @mbggenerated 2018-03-08
     */
    @ApiModelProperty(value = "推送状态(-1:作废,1:正常,2:完成)")
    private Integer status;
    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称")
    private String statusName;
    /**
     * 云南信托产品编码
     */
    @ApiModelProperty(value = "云南信托产品编码")
    private String ynProductCode;
    /**
     * 云南信托产品编码名称
     */
    @ApiModelProperty(value = "云南信托产品编码名称")
    private String ynProductName;

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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId == null ? null : uniqueId.trim();
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
}