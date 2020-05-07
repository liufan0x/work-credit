package com.anjbo.controller.impl.dingtalk;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDto;
import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsTempDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.dingtalk.IBpmsController;
import com.anjbo.controller.dingtalk.vo.BpmsVo;
import com.anjbo.service.dingtalk.BpmsService;
import com.anjbo.service.dingtalk.BpmsTempService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/6/11.
 */
@RestController
public class BpmsController extends BaseController implements IBpmsController {

    private Log log = LogFactory.getLog(BpmsController.class);
    @Autowired
    private BpmsTempService bpmsTempService;
    @Autowired
    private BpmsService bpmsService;

    @Resource
    private UserApi userApi;
    /**
     * 订单要件审批表单
     * @Author KangLG<2017年10月17日>
     * @param record
     * @return
     */
    @Override
    public RespStatus createOrderDoc( BpmsVo record) {
        try {
            ThirdDingtalkBpmsTempDto temp = bpmsTempService.getEntityByCode(record.getBpmsFrom());
            if(null == temp){
                return RespHelper.setFailRespStatus(new RespStatus(), "流程审批模板不存在！");
            }
            // 构建表单参数
            ThirdDingtalkBpmsDto bpms = new ThirdDingtalkBpmsDto();
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(bpms, temp);
            bpms.setBpmsFrom(record.getBpmsFrom());
            bpms.setBpmsFromId(record.getBpmsObjectId());
            bpms.setFormComponent(String.format(temp.getFormComponent(), record.getFormComponentParam()[0], record.getFormComponentParam()[1]));
            UserDto userDto = userApi.getUserDto();
            // 操作人信息
            bpms.setOriginatorDeptId(userDto.getDingtalkDepId());
            bpms.setOriginatorUserId(userDto.getDingtalkUid());
            bpms.setOriginatorUserName(userDto.getName());
            bpms.setCreateUid(userDto.getAccount());
            if(bpmsService.add(bpms) < 1){
                return RespHelper.failRespStatus();
            }
        } catch (Exception e) {
            log.error("创建失败，异常信息：", e);
            return RespHelper.failRespStatus();
        }
        return RespHelper.setSuccessRespStatus(new RespStatus());
    }
    /**
     * 目前没场景，供demo操作
     * @Author KangLG<2017年10月13日>
     * @param thirdDingtalkBpmsDto
     * @return
     */
    @Override
    public RespPageData<ThirdDingtalkBpmsDto> page(ThirdDingtalkBpmsDto thirdDingtalkBpmsDto) {
        RespPageData<ThirdDingtalkBpmsDto> resp = new RespPageData<ThirdDingtalkBpmsDto>();
        resp.setTotal(bpmsService.searchCount(thirdDingtalkBpmsDto));
        resp.setRows(bpmsService.search(thirdDingtalkBpmsDto));
        resp.setCode(RespStatusEnum.SUCCESS.getCode());
        resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
        return resp;
    }
}
