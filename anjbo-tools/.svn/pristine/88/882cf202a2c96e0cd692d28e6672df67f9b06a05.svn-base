package com.anjbo.service.tools.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.tools.TransferPrice;
import com.anjbo.dao.tools.TransferPriceMapper;
import com.anjbo.service.tools.TransferPriceService;

/**
 * 过户价/税费
 * @author limh limh@zxsf360.com
 * @date 2015-9-8 下午05:20:49
 */
@Service
public class TransferPriceServiceImpl implements TransferPriceService{
	@Resource
	private TransferPriceMapper transferPriceMapper;

	@Override
	public int addTransferPrice(TransferPrice transferPrice) {
		return transferPriceMapper.addTransferPrice(transferPrice);
	}

	@Override
	public int updateTax(TransferPrice transferPrice) {
		return transferPriceMapper.updateTax(transferPrice);
	}

	@Override
	public List<TransferPrice> selectTransferPricePage(
			TransferPrice transferPrice) {
		return transferPriceMapper.selectTransferPricePage(transferPrice);
	}

	@Override
	public TransferPrice selectTransferPrice(int id) {
		return transferPriceMapper.selectTransferPrice(id);
	}

	@Override
	public int selectTransferPricePageCount(TransferPrice transferPrice) {
		return transferPriceMapper.selectTransferPricePageCount(transferPrice);
	}

	@Override
	public int addTransferPriceBank(TransferPrice transferPrice) {
		return transferPriceMapper.addTransferPriceBank(transferPrice);
	}

}
