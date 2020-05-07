package com.anjbo.service.estatedeal;



import com.anjbo.bean.estatedeal.XMDealDto;


import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface XiaMenDealDataService {

    /*厦门一手房数据*/
    int insertXiaMenOneHandDataBatch(List<XMDealDto> list);

    int deleteXiaMenOneHandDataByDate(Date startDate, Date endDate);

    @Deprecated
    void updateXiaMenOneHandDataBatch(List<XMDealDto> list);


    int deleteXMDataByDateType(XMDealDto xmData);

    int insertXMDataBatch(List<XMDealDto> xmData);

    void updateXMDataBatch(List<XMDealDto> xmData);
}
