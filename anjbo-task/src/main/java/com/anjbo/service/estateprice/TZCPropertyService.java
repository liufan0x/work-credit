package com.anjbo.service.estateprice;



import com.anjbo.bean.estateprice.TZCPropertyDto;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
public interface TZCPropertyService {

    List<TZCPropertyDto> selectPropertyMaxAndAVGPrice();

    List<TZCPropertyDto> selectPropertyMaxAndAVGPriceByArea(Integer min, Integer max);
}
