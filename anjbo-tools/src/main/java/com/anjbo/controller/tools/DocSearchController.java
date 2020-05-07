package com.anjbo.controller.tools;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.tools.DocSearch;
import com.anjbo.common.Enums;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.SendEmailWorker;
import com.anjbo.processor.DocSearchProcessor;
import com.anjbo.service.tools.DocSearchService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.StringUtil;

/**
 * 办文查询
 * @author limh limh@zxsf360.com
 * @date 2015-10-9 下午05:23:01
 */
@Controller
@RequestMapping("/tools/docSearch/v")
public class DocSearchController{           
	@Resource
	private DocSearchService docSearchService;
	@Resource
	private ThreadPoolTaskExecutor poolTaskExecutor;
	
	/**
	 * 新增办文查询
	 * 注意 1传入uid
	 * 
	 * @Title: add 
	 * @param request
	 * @param docNo
	 * @return
	 * RespDataObject<Integer>
	 * @throws
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody
	RespDataObject<DocSearch> add(@RequestBody Map<String, Object> params){
		RespDataObject<DocSearch> rdo = new RespDataObject<DocSearch>();
		String docNo = MapUtils.getString(params, "docNo");
		String uid = MapUtils.getString(params, "uid");
		if(StringUtils.isEmpty(docNo)){
			rdo.setCode(RespStatusEnum.FAIL.getCode());
			rdo.setMsg("请输入办文编号");
			return rdo;
		}
		Integer id = 0;//docSearchService.selectDocSearchId(docNo,uid);
//		if(id!=null){//默认再次查询
//			params.put("id", id);
//			RespStatus status = reAdd(params);
//			rdo.setCode(status.getCode());
//			rdo.setMsg(status.getMsg());
//		}else{
			DocSearch doc = new DocSearchProcessor().getDocSearchResult(docNo);
			if(doc==null){
				rdo.setCode(RespStatusEnum.FAIL.getCode());
				rdo.setMsg("您输入的文号不存在");
				return rdo;
			}
			doc.setUid(uid);
			doc.setDevice(MapUtils.getString(params, "device"));
			doc.setVersion(MapUtils.getString(params, "version"));
//			int r = docSearchService.addDocSearch(doc);
//			if(r==1){
//				id = doc.getId();
//				doc.setSearchId(id);
//				docSearchService.addDocSearchFlow(doc);
//				cacheDocSearch(uid, docNo);
//				rdo.setCode(RespStatusEnum.SUCCESS.getCode());
//				rdo.setMsg(RespStatusEnum.SUCCESS.getMsg());
//				String email = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.DOCSEARCH_EMAILS.toString());
//				if(StringUtil.isNotEmpty(email)){
//					SendEmailWorker.asyncSendEmail(poolTaskExecutor, doc.getTitle(), doc.toString(),email);
//				}
//			}else{
//				rdo.setCode(RespStatusEnum.FAIL.getCode());
//				rdo.setMsg("查询失败，请稍后再试");
//			}
//		}
//		DocSearch doc = docSearchService.selectDocSearch(id);
		doc.setSearch(!checkDocSearch(doc.getUid(),doc.getDocNo()));
//		List<DocSearch> doclist=docSearchService.selectDocSearchFlowPage(doc);
//		doc.setDoclist(doclist);
		rdo.setData(doc);
		rdo.setCode(RespStatusEnum.SUCCESS.getCode());
		return rdo;
	}
	/**
	 * 检测办文查询是否超过设定时间
	 * @Title: checkDocSearch 
	 * @param uid
	 * @param docNo
	 * @return
	 * boolean
	 * @throws
	 */
	private boolean checkDocSearch(String uid,String docNo){
		String key = RedisKey.PREFIX.MORTGAGE_DOC_SEARCH+uid+RedisKey.SPLIT_FLAG+docNo;
		return RedisOperator.checkKeyExisted(key);
	}
	/**
	 * 缓存办文查询过时时间
	 * @Title: cacheDocSearch 
	 * @param uid
	 * @param docNo
	 * void
	 * @throws
	 */
	private void cacheDocSearch(String uid,String docNo){
		String key = RedisKey.PREFIX.MORTGAGE_DOC_SEARCH+uid+RedisKey.SPLIT_FLAG+docNo;
		RedisOperator.set(key,new Date().getTime(),60*60*1);
	}
	/**
	 * 再次办文查询
	 * @Title: reAdd 
	 * @param request
	 * @param id
	 * @return
	 * RespStatus
	 * @throws
	 */
	@RequestMapping(value = "/addAgain")
	public @ResponseBody
	RespDataObject<DocSearch> reAdd(@RequestBody Map<String, Object> params){
		int id = MapUtils.getIntValue(params, "id");
		String uid = MapUtils.getString(params, "uid");
		RespDataObject<DocSearch> status = new RespDataObject<DocSearch>();
		DocSearch doc = docSearchService.selectDocSearch(id);
		String docNo = doc.getDocNo();
		if(checkDocSearch(uid, docNo)){
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg("1小时之内不能重复查询");
			return status;
		}
		DocSearch docSearch = new DocSearchProcessor().getDocSearchResult(docNo);
		if(docSearch==null){
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg("文号已不存在");
			return status;
		}
		docSearch.setId(id);
		docSearch.setSearchId(id);
		int r = docSearchService.addDocSearchFlow(docSearch);
		if(r==1){
			docSearchService.updateDocSearchStatus(docSearch);
			docSearch.setUid(uid);
			SendEmailWorker.asyncSendEmail(poolTaskExecutor, doc.getTitle(), doc.toString(), 
					ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.DOCSEARCH_EMAILS.toString()));
		}
		cacheDocSearch(uid, docNo);
		String ids= params.get("id")+"";
		DocSearch doc1 = docSearchService.selectDocSearch(Integer.parseInt(ids));
		doc1.setIsSearch(!checkDocSearch(doc1.getUid(),doc1.getDocNo()));
		doc1.setSearchId(Integer.parseInt(ids));
		List<DocSearch> doclist=docSearchService.selectDocSearchFlowPage(doc1);
		doc1.setDoclist(doclist);
		status.setData(doc1);
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
	/**
	 * 根据取号编号获取办理状态
	 * @user Mark
	 * @date 2016-10-19 下午04:42:50 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/docSearchResultByBookingCode")
	public @ResponseBody
	RespDataObject<Map<String,String>> docSearchResultByBookingCode(@RequestBody Map<String, Object> params){
		String bookingCode = MapUtils.getString(params, "bookingCode");
		RespDataObject<Map<String,String>> status = new RespDataObject<Map<String,String>>();
		status.setData(DocSearchProcessor.getDocSearchResultByBookingCode(bookingCode));
		status.setCode(RespStatusEnum.SUCCESS.getCode());
		status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return status;
	}
	
	/**
	 * 办文查询分页
	 * @user lic
	 * @date 2016-11-22 上午11:33:22 
	 * @param docSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public RespData<DocSearch> page(@RequestBody DocSearch docSearch) {
		RespData<DocSearch> respDataObject = new RespData<DocSearch>();
		respDataObject.setData(docSearchService.selectDocSearchPage(docSearch));
		respDataObject.setCode(RespStatusEnum.SUCCESS.getCode());
		respDataObject.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return respDataObject;
	}
	
	/**
	 * 办文查询详情
	 * @Title: detail 
	 * @param id
	 * @return
	 * ModelAndView
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/detail")
	public RespDataObject<DocSearch> detail(@RequestBody Map<String, String> params){
		RespDataObject<DocSearch> status = new RespDataObject<DocSearch>();
		try {
			String ids=params.get("id");
			if(StringUtil.isEmpty(ids)){
				status.setCode(RespStatusEnum.FAIL.getCode());
				status.setMsg(RespStatusEnum.FAIL.getMsg());
			}
			DocSearch doc = docSearchService.selectDocSearch(Integer.parseInt(ids));
			if(doc!=null){
				doc.setIsSearch(!checkDocSearch(doc.getUid(),doc.getDocNo()));
				doc.setSearchId(Integer.parseInt(ids));
				List<DocSearch> doclist=docSearchService.selectDocSearchFlowPage(doc);
				doc.setDoclist(doclist);
				status.setData(doc);
			}
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return status;
	}
}
