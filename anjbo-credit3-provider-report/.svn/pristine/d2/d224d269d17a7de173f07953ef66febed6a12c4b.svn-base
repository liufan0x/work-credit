package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.anjbo.bean.OrderReceivablleReportVo;
import com.anjbo.dao.ReceivableReportMapper;
import com.anjbo.service.ReceivableReportService;

@Service
public class ReceivableReportServiceImpl implements ReceivableReportService {

	@Resource ReceivableReportMapper receivableReportMapper;
	@Override
	public List<OrderReceivablleReportVo> findByAll(Map<String, Object> pareamt) {
		// TODO Auto-generated method stub
		return receivableReportMapper.findByAll(pareamt);
	}

}
