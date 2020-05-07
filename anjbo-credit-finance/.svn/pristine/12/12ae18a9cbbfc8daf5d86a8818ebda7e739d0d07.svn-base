package com.anjbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.common.Enums;
import com.anjbo.dao.LendingMapper;
import com.anjbo.dao.ReceivableForMapper;
import com.anjbo.service.LendingService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.StringUtil;

@Service
public class LendingServiceImpl implements LendingService {

	@Resource LendingMapper lendingMapper;
	@Resource ReceivableForMapper receivableForMapper;
	
	 /**
	  * 详情
	  */
	@Override
	public LendingDto findByLending(LendingDto lendingPayDto) {
		// TODO Auto-generated method stub
		return lendingMapper.findByLending(lendingPayDto);
	}
	

	@Override
	public int addLending(LendingDto dto) {
		// TODO Auto-generated method stub
		return lendingMapper.addLending(dto);
	}
	
	/**
	 * 完善信息
	 */
	@Override
	public int updateLending(LendingDto lendingDto) {
		// TODO Auto-generated method stub
//		int count=0;
//		try {
//			LendingDto lend=lendingMapper.findByLending(lendingDto);
//			if(lend!=null){
//				int borrowingDays=lendingDto.getBorrowingDays();  //借款期限
//				Date newDate = addDate(lendingDto.getLendingTime(), (borrowingDays-1)); 
//				int counts=lendingMapper.updateLending(lendingDto);
//				if(counts>0){
//					
////					//新增下一步基本信息（待回款）
////					ReceivableForDto receivableFor=new ReceivableForDto();
////					receivableFor.setStatus(0);
////					receivableFor.setOrderNo(lendingDto.getOrderNo());
////					receivableFor.setCreateUid(lendingDto.getCreateUid());
////					receivableFor.setUpdateUid(lendingDto.getCreateUid());
////					receivableForMapper.addReceivableFor(receivableFor);
//					
//					
//					//订单二级流程流转情况   财务放款->赎楼
////					OrderBackDto orderDto=new OrderBackDto();
////					orderDto.setOrderNo(lendingDto.getOrderNo());
////					orderDto.setProgressId(Enums.ProgressEnum.LEND.getCode()); //节点为已放款
////					
////					orderDto.setCustomerPaymentTime(newDate);  //客户应回款款时间
//					//==============生成合同编号start================
//					Map<String,Object> to = new HashMap<String,Object>();
//					to.put("orderNo", lendingDto.getOrderNo());
//					to.put("type","citySimName");
//					to.put("times", lendingDto.getLendingTime());
////					to = orderDtoService.selectProductOrderCountBySameMonth(to);
//					String citySimName = "";
//					String productCode = "00";
//					String tmpCount = "";
//					if(MapUtils.isNotEmpty(to)){
//						count = NumberUtils.toInt(to.get("count")+"");
//						if(to.containsKey("productCode")){
//							productCode = to.get("productCode")+"";
//						}
//						if(to.containsKey("name")){
//							citySimName = to.get("name")+"";
//						}
//					}
//					count += 1;
//					if(count<10){
//						tmpCount = "000"+count;
//					}
//					else if(count<100){
//						tmpCount = "00"+count;
//					}
//					else if(count<1000){
//						tmpCount = "0"+count;
//					}
//					else{
//						tmpCount = count+"";
//					}
//					SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
//					String contractNoTime = format.format(lendingDto.getLendingTime());;
//					String contractNo = citySimName+contractNoTime+productCode+tmpCount;
////					orderDto.setContractNo(contractNo);
////					
//					//==============生成合同编号end==================
////					orderBackDtoMapper.updateOrderState(orderDto);  //修改订单表状态
////					orderDtoService.timingDistancePaymentDay();  //修改距离回款天数
//					//==============发送短信Start===================
////					OrderDto dto=orderDtoService.selectByOrderNo(lendingDto.getOrderNo());
////					UserDto acceptMemberUser = userService.selectUserByUid(dto.getAcceptMemberUid());
////					if(StringUtil.isNotEmpty(acceptMemberUser.getMobile())){
////					  String ipWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString(),ConfigUtil.CONFIG_SMS); //ip
////					  AmsUtil.smsSend(acceptMemberUser.getMobile(), ipWhite, "您受理的赎楼订单【"+dto.getBorrowerName()+dto.getLoanAmount()+"万】已放款，请保持跟进", "fc-lending-add");
////					}
//					//==============发送短信end===================
//				}
//			}else{
//				count=-1;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return lendingMapper.updateLending(lendingDto);
	}



	/**
	 * 撤回
	 */
	@Override
	public int updwithdraw(LendingDto lendingDto) {
		int count=0;
		try {
			ReceivableForDto rfor= receivableForMapper.selectByStatus(lendingDto.getOrderNo());
			if(rfor!=null){
				count=lendingMapper.updwithdraw(lendingDto);
//				receivableForMapper.delectFor(lendingDto);
			}else{
				count=-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public LendingDto selectLendingTime(String orderNO) {
		// TODO Auto-generated method stub
		return lendingMapper.selectLendingTime(orderNO);
	}


	@Override
	public List<String> selectOrderNo() {
		// TODO Auto-generated method stub
		return lendingMapper.selectOrderNo();
	}


	@Override
	public int updatereceivableForUid(LendingDto lendingDto) {
		// TODO Auto-generated method stub
		return lendingMapper.updatereceivableForUid(lendingDto);
	}


}
