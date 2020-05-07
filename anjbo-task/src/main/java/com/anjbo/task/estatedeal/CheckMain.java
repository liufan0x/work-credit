package com.anjbo.task.estatedeal;

import java.util.Date;
import java.util.List;

import com.anjbo.bean.estatedeal.DGDealDto;
import com.anjbo.bean.estatedeal.DealdataAvgPricce;
import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.GZDealDto;
import com.anjbo.bean.estatedeal.HZDealDto;
import com.anjbo.bean.estatedeal.SZDealDto;
import com.anjbo.bean.estatedeal.XMDealDto;
import com.anjbo.common.DateUtil;

public class CheckMain {
	public static void main(String[] args) {
		try {
//			//深圳
//			List<SZDealDto> shenZhenSecondHandData = SZDataUtil.getSZSecondHandData();
//			System.out.println(shenZhenSecondHandData.size());
//			List<SZDealDto> shenZhenSecondHandDetail = SZDataUtil.getSZSecondHandDetail();
//			System.out.println(shenZhenSecondHandDetail.size());
//			List<SZDealDto> shenZhenOneHandData = SZDataUtil.getSZOneHandData();
//			System.out.println(shenZhenOneHandData.size());
//			List<DealdataTrend> shenZhenSecondHandDataTrend = SZDataUtil.getSZSecondHandTrend();
//			System.out.println(shenZhenSecondHandDataTrend.size());
//			DealdataAvgPricce avgPrcie=SZDataUtil.getSZOneHandAvgPrice();
//			System.out.println(avgPrcie.getDate());
//			//东莞
//			List<DGDealDto> dongGuanSecondHandData=DGDataUtil.getDGSecondHandData();
//			System.out.println(dongGuanSecondHandData.size());
//			List<DGDealDto> dongGuanSecondHandDetail=DGDataUtil.getDGSecondHandDetail();
//			System.out.println(dongGuanSecondHandDetail.size());
//			List<DGDealDto> dongGuanOneHandData = DGDataUtil.getDGOneHandData();
//			System.out.println(dongGuanOneHandData.size());
//            List<DGDealDto> dongGuanOneHandDetail = DGDataUtil.getDGOneHandDetail();
//			System.out.println(dongGuanOneHandDetail.size());
//            List<DealdataTrend> dongGuanSecondHandDataTrend = DGDataUtil.getDGSecondHandTrend();
//			System.out.println(dongGuanSecondHandDataTrend.size());
//            //广州
            String dateStr="2018-01-01";
            Date date = new Date();
//            List<GZDealDto> guangZhouOneHandData = GZDataUtil.getGZOneHandData(dateStr, DateUtil.getDateByFmt(date, "yyyy-MM-dd"));
//			System.out.println(guangZhouOneHandData.size());
//            List<GZDealDto> guangZhouSecondHandData = GZDataUtil.getGZSecondHandData(dateStr, DateUtil.getDateByFmt(date, "yyyy-MM-dd"));
//			System.out.println(guangZhouSecondHandData.size());
//            List<DealdataTrend> guangZhouSecondHandDataTrend = GZDataUtil.getGZSecondHandTrend();
//			System.out.println(guangZhouSecondHandDataTrend.size());
//            //厦门
//            List<XMDealDto> xmData = XMDataUtil.getXMData();
//			System.out.println(xmData.size());
			//惠州
            List<HZDealDto> huiZhouOneHandData = HZDataUtil.getHZOneHandData(dateStr, DateUtil.getDateByFmt(date,"yyyy-MM-dd"));
			System.out.println(huiZhouOneHandData.size());
            List<HZDealDto> huiZhouSecondHandData = HZDataUtil.getHZSecondHandData(dateStr, DateUtil.getDateByFmt(date,"yyyy-MM-dd"));
			System.out.println(huiZhouSecondHandData.size());
            List<DealdataTrend> huiZhouSecondHandDataTrend = HZDataUtil.getHZSecondHandTrend();
			System.out.println(huiZhouSecondHandDataTrend.size());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
