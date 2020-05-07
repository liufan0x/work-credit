package com.anjbo.service.tools;

import java.util.List;

import com.anjbo.bean.tools.DocSearch;

/**
 * 办文查询
 * @author limh limh@zxsf360.com
 * @date 2015-10-9 下午05:22:30
 */
public interface DocSearchService {

	/**
	 * 新增办文查询
	 * @Title: addDocSearch 
	 * @param docSearch
	 * @return
	 * int
	 * @throws
	 */
	int addDocSearch(DocSearch docSearch);
	/**
	 * 新增办文查询流水
	 * @Title: addDocSearchFlow 
	 * @param docSearch
	 * @return
	 * int
	 * @throws
	 */
	int addDocSearchFlow(DocSearch docSearch);
	/**
	 * 办文查询分页
	 * @Title: selectDocSearchPage 
	 * @param docSearch
	 * @return
	 * List<DocSearch>
	 * @throws
	 */
	List<DocSearch> selectDocSearchPage(DocSearch docSearch);
	/**
	 * 办文查询流水分页
	 * @Title: selectDocSearchFlowPage 
	 * @param docSearch
	 * @return
	 * List<DocSearch>
	 * @throws
	 */
	List<DocSearch> selectDocSearchFlowPage(DocSearch docSearch);
	/**
	 * 办文查询详细
	 * @Title: selectDocSearch 
	 * @param id
	 * @return
	 * DocSearch
	 * @throws
	 */
	DocSearch selectDocSearch(int id);
	/**
	 * 根据办文编号查询一条记录的id
	 * @Title: selectDocSearchId 
	 * @param docNo
	 * @return
	 * Integer
	 * @throws
	 */
	Integer selectDocSearchId(String docNo,String uid);
	/**
	 * 更新办文查询状态
	 * @param docSearch
	 * @return
	 */
	int updateDocSearchStatus(DocSearch docSearch);
}