package com.anjbo.controller.icbc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.icbc.ThirdIcbcQpdDto;
import com.anjbo.common.RespPageData;
import com.anjbo.controller.BaseController;
import com.anjbo.service.icbc.ThirdIcbcQpdService;
import com.anjbo.utils.icbc.IcbcClientUtil;
import com.anjbo.utils.icbc.TranCodeEnum;


/**
  *  [Controller]
  * @ClassName: ThirdIcbcQpdController
  * @Description: 
  * @date 2017-10-23 10:39:52
  * @version V3.0
 */
@RestController
@RequestMapping("/credit/third/api/icbc/qpd")
public class ThirdIcbcQpdController extends BaseController{
	@Resource private ThirdIcbcQpdService thirdIcbcQpdService;
	@Autowired private IcbcClientUtil icbcClientUtil;

	/**
	 * 分页
	 * @Title: page
	 * @Description: 
	 * @author mark 
	 * @date 2017-10-23 10:39:52
	 * @param request
	 * @return thirdIcbcQpdDto
	 */
	@RequestMapping(value = "/page")
	public RespPageData<ThirdIcbcQpdDto> page(HttpServletRequest request,@RequestBody ThirdIcbcQpdDto dto){
		return new RespPageData<ThirdIcbcQpdDto>(thirdIcbcQpdService.searchPage(dto));
	}
	
	/**
	 * 工行当日数据同步
	 * @Author KangLG<2017年11月2日>
	 * @param request
	 */
	@RequestMapping(value = "/sync")
	public void sync(HttpServletRequest request){		
		icbcClientUtil.send(TranCodeEnum.QPD);
	}
	
}
