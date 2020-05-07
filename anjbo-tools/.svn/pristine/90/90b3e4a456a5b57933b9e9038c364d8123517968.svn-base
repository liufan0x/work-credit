package com.anjbo.common.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.IpUtil;
import com.anjbo.utils.StringUtil;


/**
 * 校验控制
 * @author limh limh@anjbo.com   
 * @date 2017-12-28 上午10:39:17
 */
@Aspect
@Component
public class ValidateAop implements Ordered {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "@annotation(com.anjbo.common.aop.ValidateAnt)")
    public void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        
        methodSignature = (MethodSignature) signature;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        ValidateAnt validateAnt = currentMethod.getAnnotation(ValidateAnt.class);
        if (validateAnt != null) {
            log.debug("开始校验：" + validateAnt.name());
        }
        
        Object[] args = point.getArgs();  
        HttpServletRequest request = null;  
    	Map map = new HashMap();
        //通过分析aop监听参数分析出request等信息  
        for (int i = 0; i < args.length; i++) {  
            if (args[i] instanceof HttpServletRequest) {  
                request = (HttpServletRequest) args[i];  
            } else if (args[i] instanceof Map) {  
            	map = (Map) args[i];  
            } 
        }
        if(request!=null){
        	String ip = IpUtil.getClientIp(request);
        	log.info("ValidateAop:ip="+ip);
        }
        log.info("ValidateAop:map="+map.toString());
        String uid = MapUtils.getString(map, "uid");
        if(StringUtil.isEmpty(uid)){
			return returnObj(currentMethod,"用户uid不能为空");
		}
    	String key = RedisKey.PREFIX.MORTGAGE_VALIDATEAOP_DAY_COUNT+validateAnt.name().toUpperCase()+RedisKey.SPLIT_FLAG+uid;
    	//限制1
    	String close = ConfigUtil.getStringValue("ENQUIRY_CLOSE");
		if("true".equals(close)){
			return returnObj(currentMethod,validateAnt.nameDesc()+"功能维护中");
		}
		//限制2
		String uidBlank = ConfigUtil.getStringValue("ENQUIRY_UIDBLANK","");
		if(uidBlank.contains(uid)){
			return returnObj(currentMethod,"黑名单用户");
		}
		//限制3
		String uidWhite = ConfigUtil.getStringValue("ENQUIRY_UIDWHITE","");
		if(validateAnt.limit()&&!uidWhite.contains(uid)){
	    	int r = NumberUtils.toInt(RedisOperator.getString(key));
			if(r>=validateAnt.limitCount()){
				return returnObj(currentMethod, "当天"+validateAnt.nameDesc()+"超限");
			}
		}
        try {
        	return point.proceed();
        } finally {
        	//执行完方法后处理后续限制逻辑
            if(validateAnt.limit()&&!uidWhite.contains(uid)){
            	if(RedisOperator.getString(key)==null){
            		RedisOperator.setString(key,"1",validateAnt.limitSecondsTime());
            	}else{
            		RedisOperator.increase(key,1);
            	}
            }
            log.debug("完成校验！");
        }
    }
    /**
     * 返回对象
     * @param currentMethod
     * @param msg
     * @return
     */
    public Object returnObj(Method currentMethod,String msg){
    	for (Annotation an : currentMethod.getAnnotations()) {
			if(an instanceof ResponseBody){
				Class<?> returnType = currentMethod.getReturnType();
		    	if(returnType.equals(RespStatus.class)){
		    		return new RespStatus(RespStatusEnum.FAIL.getCode(), msg);
		    	}else if(returnType.equals(RespDataObject.class)){
		    		return new RespDataObject<Object>(null,RespStatusEnum.FAIL.getCode(), msg);
		    	}else if(returnType.equals(RespData.class)){
		    		return new RespData<Object>(RespStatusEnum.FAIL.getCode(), msg);
		    	}else if(returnType.equals(RespPageData.class)){
		    		RespPageData<Object> resp = new RespPageData<Object>();
		    		resp.setCode(RespStatusEnum.FAIL.getCode());
		    		resp.setMsg(msg);
		    		return resp;
		    	}else if(returnType.equals(Map.class)){
		    		Map<Object,Object> map = new HashMap<Object,Object>();
		    		map.put("code", RespStatusEnum.FAIL.getCode());
		    		map.put("msg", msg);
		    		return map;
		    	}else{
		    		return new Object();
		    	}
			}
		}
    	ModelAndView view = new ModelAndView("common/error.ftl");
    	view.addObject("msg",msg);
        ValidateAnt validateAnt = currentMethod.getAnnotation(ValidateAnt.class);
    	view.addObject("title",validateAnt.nameDesc()+"出错");
    	return view;
    }

    /**
     * aop的顺序要早于spring的事务
     */
    public int getOrder() {
        return 1;
    }

}
