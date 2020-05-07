package com.anjbo.service.estatedeal.impl;


import com.anjbo.bean.estatedeal.XMDealDto;
import com.anjbo.dao.app.estatedeal.XiaMenDealDataMapper;
import com.anjbo.service.estatedeal.XiaMenDealDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
@Transactional
@Service("xiaMenDealDataService")
public class XiaMenDealDataServiceImpl implements XiaMenDealDataService {

    @Autowired
    private XiaMenDealDataMapper xiaMenDealDataMapper;

    /*厦门一手房数据*/
    @Override
    public int insertXiaMenOneHandDataBatch(List<XMDealDto> list) {
        return xiaMenDealDataMapper.insertXiaMenOneHandDataBatch(list);
    }

    @Override
    public int deleteXiaMenOneHandDataByDate(Date startDate,Date endDate) {
        return xiaMenDealDataMapper.deleteXiaMenOneHandDataByDate(startDate,endDate);
    }

    @Override
    public void updateXiaMenOneHandDataBatch(List<XMDealDto> list) {
        if(list!=null&&list.size()>0){
            XMDealDto xiaMenDealDto = list.get(0);
            deleteXiaMenOneHandDataByDate(xiaMenDealDto.getStartDate(),xiaMenDealDto.getEndDate());
            insertXiaMenOneHandDataBatch(list);
        }
    }


    @Override
    public int deleteXMDataByDateType(XMDealDto xmData) {
        return xiaMenDealDataMapper.deleteXMDataByDateType(xmData);
    }

    @Override
    public int insertXMDataBatch(List<XMDealDto> xmData) {
        return xiaMenDealDataMapper.insertXMDataBatch(xmData);
    }

    @Override
    public void updateXMDataBatch(List<XMDealDto> xmData) {
        for(XMDealDto dto:xmData){
            deleteXMDataByDateType(dto);
        }
        insertXMDataBatch(xmData);
    }
}
