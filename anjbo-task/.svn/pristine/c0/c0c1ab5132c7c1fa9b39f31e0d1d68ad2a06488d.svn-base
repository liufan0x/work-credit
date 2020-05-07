package com.anjbo.service.estateprice.impl;


import com.anjbo.bean.estateprice.TZCPropertyDto;
import com.anjbo.dao.tools.estateprice.TZCPropertyMapper;
import com.anjbo.service.estateprice.TZCPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
@Transactional
@Service("tzcPropertyService")
public class TZCPropertyServiceImpl implements TZCPropertyService {

    @Autowired
    private TZCPropertyMapper tzcPropertyMapper;

    @Override
    public List<TZCPropertyDto> selectPropertyMaxAndAVGPrice() {
        return tzcPropertyMapper.selectPropertyMaxAndAVGPrice();
    }

    @Override
    public List<TZCPropertyDto> selectPropertyMaxAndAVGPriceByArea(Integer min, Integer max) {
        return tzcPropertyMapper.selectPropertyMaxAndAVGPriceByArea(min,max);
    }
}
