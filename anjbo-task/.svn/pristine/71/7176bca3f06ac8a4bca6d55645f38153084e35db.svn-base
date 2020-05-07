package com.anjbo.dao.mort.estateprice;



import com.anjbo.bean.estateprice.NetworkOfferDto;

import java.util.List;

/**
 * 例子
 * @author limh limh@anjbo.com   
 * @date 2017-3-3 上午10:06:43
 */
public interface NetworkOfferMapper {
    NetworkOfferDto selectNetworkOfferDtoByPropertyNameAndCity(String propertyName, String city);

    List<NetworkOfferDto> selectNetworkOfferDtoAll();

    List<NetworkOfferDto> selectNetworkOfferDtoAllByArea(Integer min, Integer max);
    
    List<String> selectNetworkOfferByCity(String city);
}