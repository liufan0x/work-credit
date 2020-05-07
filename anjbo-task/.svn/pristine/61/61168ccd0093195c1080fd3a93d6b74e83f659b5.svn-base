package com.anjbo.bean.msloan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 民生银行dto
 */
public class MSCustomerDto {
    /*姓名*/
    private String name;
    /*身份证*/
    private String idCard;
    /*手机号*/
    private String phone;
    /*婚姻状况（0 未婚 1 已婚 2离异 3 丧偶）*/
    private String marriage;
    /*房屋面积*/
    private Double acreage;
    /*1 消费贷  2 经营贷*/
    private String mode;
    /*楼盘号*/
    private String lpNum;
    /*楼号*/
    private String louNum;
    /*房号*/
    private String roomNum;
    /*访问令牌*/
    private String token;
	/*工单状态*/
    private String state;
    /*工单id*/
    private String workorderId;
    /*期望可贷金额*/
    private Double loan;
    /*个人手持身份证*/
    private String handCard;
    /*个人身份证正面*/
    private String handCardZm;
    /*个人身份证背面*/
    private String handCardFm;
    /*个人签名*/
    private String handSign;
    /*营业执照*/
    private String busLicen;
    /*房产证房产证原件*/
    private String estateCer;
    
    private String propertyStr;
    
    private String processId;

    private String uid;
    
    private String propertyName;
    /*民生接口返回的审批意见*/
    private String opinion;
    /*是否已经发送过信息，1为已发送，其他为未发送*/
    private String SmsState;
    /*是否以完善资料  1=完善*/
    private String dataState;
    
    /*可贷款金额 0表示审核中*/
    private Double quota;
    
    private Double loanAmount;
    
    private String interest;
    
    private String deadline;
    
    
    public String getSmsState() {
		return SmsState;
	}

	public void setSmsState(String smsState) {
		SmsState = smsState;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getHandCard() {
		return handCard;
	}
    
	public String getEstateCer() {
		return estateCer;
	}

	public void setEstateCer(String estateCer) {
		this.estateCer = estateCer;
	}

	public void setHandCard(String handCard) {
		this.handCard = handCard;
	}

	public String getHandCardZm() {
		return handCardZm;
	}

	public void setHandCardZm(String handCardZm) {
		this.handCardZm = handCardZm;
	}

	public String getHandCardFm() {
		return handCardFm;
	}

	public void setHandCardFm(String handCardFm) {
		this.handCardFm = handCardFm;
	}

	public String getHandSign() {
		return handSign;
	}

	public void setHandSign(String handSign) {
		this.handSign = handSign;
	}

	public String getBusLicen() {
		return busLicen;
	}

	public void setBusLicen(String busLicen) {
		this.busLicen = busLicen;
	}

	public Double getLoan() {
		return loan;
	}

	public void setLoan(Double loan) {
		this.loan = loan;
	}


    public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public Double getQuota() {
		return quota;
	}

	public void setQuota(Double quota) {
		this.quota = quota;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public Double getAcreage() {
        return acreage;
    }

    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getLpNum() {
        return lpNum;
    }

    public void setLpNum(String lpNum) {
        this.lpNum = lpNum;
    }

    public String getLouNum() {
        return louNum;
    }

    public void setLouNum(String louNum) {
        this.louNum = louNum;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(String workorderId) {
        this.workorderId = workorderId;
    }

    public String getPropertyStr() {
        return propertyStr;
    }

    public void setPropertyStr(String propertyStr) {
        this.propertyStr = propertyStr;
    }
    
    public static Map<Integer,String> defaultProcessMap(int Mode){
        Map<Integer,String> map=new LinkedHashMap<Integer, String>();
        switch (Mode) {
            case 1:	//个人贷
                map.put(1,"提交申请");
                map.put(2,"客服回访");
                map.put(3,"确定贷款方案");
                map.put(4,"银行审批");
                map.put(5,"银行面签");
                map.put(6,"放款");
                map.put(7,"完成订单");
                break;
            case 2: //企业经营贷
                map.put(1,"提交申请");
                map.put(2,"客服回访");
                map.put(3,"确定贷款方案");
                map.put(4,"银行审批");
                map.put(5,"银行面签");
                map.put(6,"放款");
                map.put(7,"完成订单");
                break;
            default:
                break;
        }
        return map;
    }
    
    @Override
	public String toString() {
		return "MSCustomerDto [name=" + name + ", idCard=" + idCard
				+ ", phone=" + phone + ", marriage=" + marriage + ", acreage="
				+ acreage + ", mode=" + mode + ", lpNum=" + lpNum + ", louNum="
				+ louNum + ", roomNum=" + roomNum + ", token=" + token
				+ ", state=" + state + ", workorderId=" + workorderId
				+ ", loan=" + loan + ", handCard=" + handCard + ", handCardZm="
				+ handCardZm + ", handCardFm=" + handCardFm + ", handSign="
				+ handSign + ", busLicen=" + busLicen + ", estateCer="
				+ estateCer + ", propertyStr=" + propertyStr + ", processId="
				+ processId + ", uid=" + uid + ", propertyName=" + propertyName
				+ ", dataState=" + dataState + ", quota=" + quota
				+ ", loanAmount=" + loanAmount + ", interest=" + interest
				+ ", deadline=" + deadline + "]";
	}

	public static List<Map<String,Object>> getProgressListByType(String loanTypeId){
    	int model = NumberUtils.toInt(loanTypeId);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<Integer,String> map = defaultProcessMap(model);
		Iterator<Integer> it = map.keySet().iterator();
		Map<String,Object> newMap = null;
		while(it.hasNext()){
			Integer key = it.next();
			newMap = new HashMap<String,Object>();
			newMap.put("id", key);
			newMap.put("name", map.get(key));
			list.add(newMap);
		}
		return list;
	}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
