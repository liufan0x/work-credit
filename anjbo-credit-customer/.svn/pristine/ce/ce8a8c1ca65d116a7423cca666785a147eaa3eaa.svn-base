package com.anjbo.service.impl;

import com.anjbo.bean.customer.CustomerAgencyFeescaleDetailDto;
import com.anjbo.bean.customer.CustomerAgencyFeescaleDto;
import com.anjbo.bean.customer.CustomerAgencyFeescaleRiskcontrolDto;
import com.anjbo.bean.customer.CustomerAgencyFeescaleSectionDto;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.CustomerAgencyFeescaleDetailMapper;
import com.anjbo.dao.CustomerAgencyFeescaleMapper;
import com.anjbo.dao.CustomerAgencyFeescaleRiskcontrolMapper;
import com.anjbo.dao.CustomerAgencyFeescaleSectionMapper;
import com.anjbo.service.CustomerAgencyFeescaleRiskcontrolService;
import com.anjbo.service.CustomerAgencyFeescaleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
  * 收费标准 [Service实现类]
  * @ClassName: CustomerAgencyFeescaleServiceImpl
  * @Description: 收费标准业务服务
  * @author 
  * @date 2017-07-06 15:03:10
  * @version V3.0
 */
@Service
public class CustomerAgencyFeescaleServiceImpl  implements CustomerAgencyFeescaleService
{
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Resource
	private CustomerAgencyFeescaleMapper customerAgencyFeescaleMapper;
	@Resource
	private CustomerAgencyFeescaleRiskcontrolMapper customerAgencyFeescaleRiskcontrolMapper;
	@Resource
	private CustomerAgencyFeescaleSectionMapper customerAgencyFeescaleSectionMapper;
	@Resource
	private CustomerAgencyFeescaleDetailMapper customerAgencyFeescaleDetailMapper;
	@Resource 
	private CustomerAgencyFeescaleRiskcontrolService customerAgencyFeescaleRiskcontrolService;

	@Override
	public List<CustomerAgencyFeescaleDto> selectCustomerAgencyFeescaleList(CustomerAgencyFeescaleDto customerAgencyFeescaleDto){
		return customerAgencyFeescaleMapper.selectCustomerAgencyFeescaleList(customerAgencyFeescaleDto);
	}

	@Override
	public int selectCustomerAgencyFeescaleCount(CustomerAgencyFeescaleDto customerAgencyFeescaleDto) {
		return customerAgencyFeescaleMapper.selectCustomerAgencyFeescaleCount(customerAgencyFeescaleDto);
	}
	
	@Override
	public int addCustomerAgencyFeescale(CustomerAgencyFeescaleDto customerAgencyFeescaleDto) {
		return customerAgencyFeescaleMapper.addCustomerAgencyFeescale(customerAgencyFeescaleDto);
	}
	
	@Override
	public int updateCustomerAgencyFeescale(CustomerAgencyFeescaleDto customerAgencyFeescaleDto) {
		return customerAgencyFeescaleMapper.updateCustomerAgencyFeescale(customerAgencyFeescaleDto);
	}
	
	@Override
	public int deleteCustomerAgencyFeescaleById(int id){
		return customerAgencyFeescaleMapper.deleteCustomerAgencyFeescaleById(id);
	}
	
	@Override
	public CustomerAgencyFeescaleDto selectCustomerAgencyFeescaleById(int id){
		CustomerAgencyFeescaleDto customerAgencyFeescaleDto = customerAgencyFeescaleMapper.selectCustomerAgencyFeescaleById(id);
		List<CustomerAgencyFeescaleRiskcontrolDto> customerAgencyFeescaleRiskcontrolList = customerAgencyFeescaleRiskcontrolService.selectCustomerAgencyFeescaleRiskcontrolList(null);
		List<CustomerAgencyFeescaleRiskcontrolDto> tempList = new ArrayList<CustomerAgencyFeescaleRiskcontrolDto>();
		for (CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto : customerAgencyFeescaleRiskcontrolList) {
			if(customerAgencyFeescaleRiskcontrolDto.getFeescaleid() == customerAgencyFeescaleDto.getId()){
				tempList.add(customerAgencyFeescaleRiskcontrolDto);
			}
		}
		customerAgencyFeescaleDto.setCustomerAgencyFeescaleRiskcontrolList(tempList);
		return customerAgencyFeescaleDto;
	}

	@Override
	public RespStatus updateFeescaleInfo(final List<CustomerAgencyFeescaleDto> list){
		RespStatus status = new RespStatus();
		try {
			status = transactionTemplate.execute(new TransactionCallback<RespStatus>() {
				@Override
				public RespStatus doInTransaction(TransactionStatus arg0) {
					RespStatus status = new RespStatus();
					status.setCode(RespStatusEnum.FAIL.getCode());
					status.setMsg(RespStatusEnum.FAIL.getMsg());
					CustomerAgencyFeescaleDto ftDto = list.get(0);
					List<CustomerAgencyFeescaleDto> tmpList = customerAgencyFeescaleMapper.selectCustomerAgencyFeescaleByAgencyIdAndProductionid(ftDto);
					customerAgencyFeescaleMapper.deleteCustomerAgencyFeescaleByAgencyIdAndProductionid(ftDto);
					String feescaleids = "";
					//setup1修改收费标准
					for (CustomerAgencyFeescaleDto o:tmpList) {
						if(StringUtils.isBlank(feescaleids)){
							feescaleids = "'"+o.getId()+"'";
						} else {
							feescaleids += ",'"+o.getId()+"'";;
						}
					}
					if(StringUtils.isNotBlank(feescaleids)) {
						//setup1_1删除收费详细
						customerAgencyFeescaleDetailMapper.deleteCustomerAgencyFeescaleDetailByFeescaleIds(feescaleids);
						//setup1_2删除收费设置
						customerAgencyFeescaleSectionMapper.deleteCustomerAgencyFeescaleSectionByFeescaleIds(feescaleids);
						//setup1_3删除风控配置
						customerAgencyFeescaleRiskcontrolMapper.deleteCustomerAgencyFeescaleRiskcontrolByFeescaleIds(feescaleids);
					}
					customerAgencyFeescaleMapper.batchAddCustomerAgencyFeescale(list);
					tmpList = customerAgencyFeescaleMapper.selectCustomerAgencyFeescaleByAgencyIdAndProductionid(ftDto);

					if(null!=tmpList&&tmpList.size()>0) {
						CustomerAgencyFeescaleDto fDto = ftDto;
						fDto.setId(tmpList.get(0).getId());
						//setup2添加风控配置
						List<CustomerAgencyFeescaleRiskcontrolDto> rList = fDto.getCustomerAgencyFeescaleRiskcontrolList();
						if (null != rList && rList.size() > 0) {
							for (CustomerAgencyFeescaleRiskcontrolDto rDto : rList) {
								rDto.setFeescaleid(fDto.getId());
								rDto.setCreateUid(fDto.getCreateUid());
								customerAgencyFeescaleRiskcontrolMapper.addCustomerAgencyFeescaleRiskcontrol(rDto);
								//setup3添加收费设置
								List<CustomerAgencyFeescaleSectionDto> sList = rDto.getCustomerAgencyFeescaleSectionList();
								for (CustomerAgencyFeescaleSectionDto sDto : sList) {
									sDto.setFeescaleid(fDto.getId());
									sDto.setRaskcontrolid(rDto.getId());
									sDto.setCreateUid(fDto.getCreateUid());
									customerAgencyFeescaleSectionMapper.addCustomerAgencyFeescaleSection(sDto);
									//setup4添加收费详细
									List<CustomerAgencyFeescaleDetailDto> dList = sDto.getCustomerAgencyFeescaleDetailList();
									for (CustomerAgencyFeescaleDetailDto dDto : dList) {
										dDto.setFeescaleid(fDto.getId());
										dDto.setRaskcontrolid(rDto.getId());
										dDto.setSectionid(sDto.getId());
										dDto.setCreateUid(fDto.getCreateUid());
										customerAgencyFeescaleDetailMapper.addCustomerAgencyFeescaleDetail(dDto);
									}
								}
							}
						}
						status.setCode(RespStatusEnum.SUCCESS.getCode());
						status.setMsg(RespStatusEnum.SUCCESS.getMsg());
					} else {
						status.setCode(RespStatusEnum.FAIL.getCode());
						status.setMsg(RespStatusEnum.FAIL.getMsg());
					}
				    return status;
				}
			});
		} catch (Exception e) {
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
		}
		return status;
	}
	/**
	 * 根据机构id与产品查询收费标准
	 * @param obj
	 * @return
	 */
	@Override
	public List<CustomerAgencyFeescaleDto> selectCustomerAgencyFeescaleByAgencyIdAndProductionid(CustomerAgencyFeescaleDto obj){
		List<CustomerAgencyFeescaleDto> list = customerAgencyFeescaleMapper.selectCustomerAgencyFeescaleByAgencyIdAndProductionid(obj);
		List<CustomerAgencyFeescaleRiskcontrolDto> customerAgencyFeescaleRiskcontrolList = customerAgencyFeescaleRiskcontrolService.selectCustomerAgencyFeescaleRiskcontrolList(null);
		List<CustomerAgencyFeescaleRiskcontrolDto> tempList = new ArrayList<CustomerAgencyFeescaleRiskcontrolDto>();
		for (CustomerAgencyFeescaleDto customerAgencyFeescaleDto:list){
			for (CustomerAgencyFeescaleRiskcontrolDto customerAgencyFeescaleRiskcontrolDto : customerAgencyFeescaleRiskcontrolList) {
				if(customerAgencyFeescaleRiskcontrolDto.getFeescaleid() == customerAgencyFeescaleDto.getId()){
					tempList.add(customerAgencyFeescaleRiskcontrolDto);
				}
			}
			customerAgencyFeescaleDto.setCustomerAgencyFeescaleRiskcontrolList(tempList);
		}
		return list;
	}
}