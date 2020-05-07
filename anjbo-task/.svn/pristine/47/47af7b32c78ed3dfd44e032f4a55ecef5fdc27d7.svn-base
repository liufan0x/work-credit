package com.anjbo.service.estateprice.impl;


import com.anjbo.bean.estateprice.NetworkOfferDto;
import com.anjbo.dao.mort.estateprice.NetworkOfferMapper;
import com.anjbo.service.estateprice.NetworkOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
@Transactional
@Service("networkOfferService")
public class NetworkOfferServiceImpl implements NetworkOfferService {

    @Autowired
    private NetworkOfferMapper networkOfferMapper;
    @Override
    public NetworkOfferDto selectNetworkOfferDtoByPropertyNameAndCity(String propertyName, String city) {
        return networkOfferMapper.selectNetworkOfferDtoByPropertyNameAndCity(propertyName,city);
    }

    @Override
    public List<NetworkOfferDto> selectNetworkOfferDtoAll() {
        return networkOfferMapper.selectNetworkOfferDtoAll();
    }

    @Override
    public List<NetworkOfferDto> selectNetworkOfferDtoAllByArea(Integer min, Integer max) {
        return networkOfferMapper.selectNetworkOfferDtoAllByArea(min,max);
    }

	@Override
	public List<String> selectNetworkOfferByCity(String city) {
		return networkOfferMapper.selectNetworkOfferByCity(city);
	}
}
