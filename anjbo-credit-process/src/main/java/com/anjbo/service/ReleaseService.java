package com.anjbo.service;

import com.anjbo.bean.product.FddReleaseDto;

public interface ReleaseService {

	public FddReleaseDto findByRelease(String OrderNo);
	public int addRelease(FddReleaseDto releaseDto);
}
