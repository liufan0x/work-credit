package com.anjbo.service.estateprice.impl;


import com.anjbo.bean.estateprice.SZCFJDto;
import com.anjbo.bean.estateprice.TZCPropertyDto;
import com.anjbo.common.DateUtil;
import com.anjbo.dao.app.estateprice.CFJMapper;
import com.anjbo.service.estateprice.CFJService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
@Transactional
@Service("cfjService")
public class CFJServiceImpl implements CFJService {


    @Autowired
    private CFJMapper cfjMapper;

    @Override
    public int insertShenZhenCFJDtoBatch(List<SZCFJDto> dtos) {
        return cfjMapper.insertShenZhenCFJDtoBatch(dtos);
    }

    @Override
    public SZCFJDto selectShenZhenCFJDtoByPropertyNameCurrentDay(String propertyName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        return cfjMapper.selectShenZhenCFJDtoByPropertyNameCurrentDay(propertyName,date);
    }

    @Override
    public List<SZCFJDto> selectShenZhenCFJDtoAllCurrentDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        return cfjMapper.selectShenZhenCFJDtoAllCurrentDay(date);
    }

    @Override
    public int insertTZCPropertyDtoBatch(List<TZCPropertyDto> dtos) {
        return cfjMapper.insertTZCPropertyDtoBatch(dtos);
    }

    @Override
    public int deleteTZCPropertyDtoByDate(String date) {
        return cfjMapper.deleteTZCPropertyDtoByDate(date);
    }

    @Override
    public void updateTZCPropertyDtoByDate(List<TZCPropertyDto> dtos) {
        if(dtos!=null&&dtos.size()>0){
            deleteTZCPropertyDtoAll();
            insertTZCPropertyDtoBatch(dtos);
        }
    }

    @Override
    public void insertTZCPropertyDtoByDate(List<TZCPropertyDto> tzcPropertyDtos) {
        if(tzcPropertyDtos!=null&&tzcPropertyDtos.size()>0){
            TZCPropertyDto  tzcPropertyDto= tzcPropertyDtos.get(0);
            Date date = tzcPropertyDto.getDate();
            String dateByFmt = DateUtil.getDateByFmt(date, "yyyy-MM-dd");
            deleteTZCPropertyDtoByDateAndArea(dateByFmt,tzcPropertyDto.getAreaRange());
            insertTZCPropertyDtoBatch2(tzcPropertyDtos);
        }
    }

    private void deleteTZCPropertyDtoByDateAndArea(String byFmt, String areaRange) {
        cfjMapper.deleteTZCPropertyDtoByDateAndArea(byFmt,areaRange);
    }

    private void insertTZCPropertyDtoBatch2(List<TZCPropertyDto> tzcPropertyDtos) {
        cfjMapper.insertTZCPropertyDtoBatch2(tzcPropertyDtos);
    }

    @Override
    public int deleteShenZhenCFJDtoAll() {
        return cfjMapper.deleteShenZhenCFJDtoAll();
    }

    @Override
    public int updateShenZhenCFJDtoBatch(List<SZCFJDto> dtos) {
        deleteShenZhenCFJDtoAll();
        int i = insertShenZhenCFJDtoBatch(dtos);
        return i;
    }

    @Override
    public int deleteTZCPropertyDtoAll() {
        return  cfjMapper.deleteTZCPropertyDtoAll();
    }

    @Override
    public Integer selectTZCPropertyDtoCount(TZCPropertyDto tzcPropertyDto) {
        return cfjMapper.selectTZCPropertyDtoCount(tzcPropertyDto);
    }
}
