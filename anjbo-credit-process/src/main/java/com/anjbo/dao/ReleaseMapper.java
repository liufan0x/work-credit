package com.anjbo.dao;

import com.anjbo.bean.product.FddReleaseDto;

public interface ReleaseMapper {
	public FddReleaseDto findByRelease(String OrderNo);
	public int addRelease(FddReleaseDto releaseDto);
	public int updRelease(FddReleaseDto releaseDto);
}
