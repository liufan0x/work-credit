package com.anjbo.service.tools.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.tools.DocSearch;
import com.anjbo.dao.tools.DocSearchMapper;
import com.anjbo.service.tools.DocSearchService;

/**
 * 办文查询
 * @author limh limh@zxsf360.com
 * @date 2015-10-9 下午05:22:21
 */
@Service
public class DocSearchServiceImpl implements DocSearchService{
	@Resource
	private DocSearchMapper docSearchMapper;

	@Override
	public int addDocSearch(DocSearch docSearch) {
		return docSearchMapper.addDocSearch(docSearch);
	}

	@Override
	public int addDocSearchFlow(DocSearch docSearch) {
		return docSearchMapper.addDocSearchFlow(docSearch);
	}

	@Override
	public List<DocSearch> selectDocSearchPage(DocSearch docSearch) {
		return docSearchMapper.selectDocSearchPage(docSearch);
	}

	@Override
	public List<DocSearch> selectDocSearchFlowPage(DocSearch docSearch) {
		return docSearchMapper.selectDocSearchFlowPage(docSearch);
	}

	@Override
	public DocSearch selectDocSearch(int id) {
		return docSearchMapper.selectDocSearch(id);
	}

	@Override
	public Integer selectDocSearchId(String docNo,String uid) {
		return docSearchMapper.selectDocSearchId(docNo, uid);
	}

	@Override
	public int updateDocSearchStatus(DocSearch docSearch) {
		return docSearchMapper.updateDocSearchStatus(docSearch);
	}

}
