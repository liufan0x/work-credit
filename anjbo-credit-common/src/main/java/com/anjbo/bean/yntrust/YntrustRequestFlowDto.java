package com.anjbo.bean.yntrust;

import com.anjbo.bean.BaseDto;

public class YntrustRequestFlowDto extends BaseDto {
    /**
     *
     *
     * This field corresponds to the database column tbl_third_yntrust_request_flow.id
     *
     * @mbggenerated 2018-03-08
     */
    private Integer id;


    /**
     *订单编号
     *
     * This field corresponds to the database column tbl_third_yntrust_request_flow.orderNo
     *
     * @mbggenerated 2018-03-08
     */
    private String orderNo;

    /**
     *请求唯一表示方便定位
     *
     * This field corresponds to the database column tbl_third_yntrust_request_flow.requestId
     *
     * @mbggenerated 2018-03-08
     */
    private String requestId;

    /**
     *请求接口名称
     *
     * This field corresponds to the database column tbl_third_yntrust_request_flow.requestInterfaceName
     *
     * @mbggenerated 2018-03-08
     */
    private String requestInterfaceName;

    /**
     *请求接口url
     *
     * This field corresponds to the database column tbl_third_yntrust_request_flow.requestInterface
     *
     * @mbggenerated 2018-03-08
     */
    private String requestInterface;

    /**
     *第三方返回
     *
     * This field corresponds to the database column tbl_third_yntrust_request_flow.responseMsg
     *
     * @mbggenerated 2018-03-08
     */
    private String responseMsg;
    /**
     * 返回的状态信息
     */
    private String responseStatusMsg;
    /**
     * 请求参数信息
     */
    private String requestMsg;

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId == null ? null : requestId.trim();
    }

    public String getRequestInterfaceName() {
        return requestInterfaceName;
    }

    public void setRequestInterfaceName(String requestInterfaceName) {
        this.requestInterfaceName = requestInterfaceName == null ? null : requestInterfaceName.trim();
    }

    public String getRequestInterface() {
        return requestInterface;
    }

    public void setRequestInterface(String requestInterface) {
        this.requestInterface = requestInterface == null ? null : requestInterface.trim();
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg == null ? null : responseMsg.trim();
    }

    public String getResponseStatusMsg() {
        return responseStatusMsg;
    }

    public void setResponseStatusMsg(String responseStatusMsg) {
        this.responseStatusMsg = responseStatusMsg;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }
}