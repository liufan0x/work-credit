package com.anjbo.plug;

import java.sql.Statement;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.anjbo.common.RedisOperator;
import com.anjbo.utils.ReflectUtils;
import com.anjbo.utils.SerializeUtil;

@Component
@Intercepts({
	@Signature(type=ResultSetHandler.class, method="handleResultSets", args={Statement.class}),
    @Signature(type=StatementHandler.class, method="update", args={Statement.class})
})
public class RedisMybatisInterceptor implements Interceptor {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Value("${mybatis.dbmsCache.aliases}") private String[] dbmsCacheAliases;
	@Value("${mybatis.dbmsCache.ignores}") private String[] dbmsCacheIgnores;
	
	@Override
    public Object intercept(Invocation invocation) throws Throwable {
		logger.debug("DBMS_CACHE>RedisMybatisInterceptor>..."+ invocation.getTarget());
        if (invocation.getTarget() instanceof DefaultResultSetHandler) {
            DefaultResultSetHandler handler = (DefaultResultSetHandler)invocation.getTarget();    
                        
            MappedStatement mappedStatement = (MappedStatement)ReflectUtils.getFieldValue(handler, "mappedStatement");
            byte[] bytesKey = this.buildKey(mappedStatement, null);
            
            BoundSql boundSql = (BoundSql)ReflectUtils.getFieldValue(handler, "boundSql");
            logger.debug("DBMS_CACHE>Sql>"+ boundSql.getSql());
            byte[] bytesField = this.buildField(boundSql.getParameterObject());
            // valid cache
            Object results = SerializeUtil.unserialize(RedisOperator.getFromMap(bytesKey, bytesField));
            if(null == results){
            	results = invocation.proceed();
            	logger.debug("DBMS_CACHE>Results(sync cache)>"+ results);
            	RedisOperator.putToMap(bytesKey, bytesField, SerializeUtil.serialize(results));
            }
            return results;
        } else if(invocation.getTarget() instanceof RoutingStatementHandler){
        	RoutingStatementHandler handler = (RoutingStatementHandler)invocation.getTarget();      
        	StatementHandler delegate = (StatementHandler)ReflectUtils.getFieldValue(handler, "delegate");   
        	MappedStatement mappedStatement = (MappedStatement)ReflectUtils.getFieldValue(delegate, "mappedStatement");
        	String key = new String(this.buildKey(mappedStatement, "*"));
    		Set<String> keys = RedisOperator.keys(key);   
    		Iterator<String> it=keys.iterator();
            while(it.hasNext()){
            	logger.debug(String.format("DBMS_CACHE>del(%s)>%d", key, RedisOperator.delete(it.next())));
            }    		
        	
        	return invocation.proceed();
        }       
        return null;
    }
        
    @Override
    public Object plugin(Object target) {    	
    	if (target instanceof ResultSetHandler || target instanceof RoutingStatementHandler) {    
    		MappedStatement mappedStatement = null;
    		if(target instanceof RoutingStatementHandler){
    			StatementHandler delegate = (StatementHandler)ReflectUtils.getFieldValue(target, "delegate");   
    			mappedStatement = (MappedStatement)ReflectUtils.getFieldValue(delegate, "mappedStatement");
    		}else{
    			mappedStatement = (MappedStatement)ReflectUtils.getFieldValue(target, "mappedStatement");
    		}
        	if(this.isNeedCached(mappedStatement)){
        		return Plugin.wrap(target, this);
        	}
        }    	
        return target;
    }
    
    @Override
    public void setProperties(Properties properties) {
    	logger.debug("DBMS_CACHE>setProperties>"+ properties);
    }
    
    /**
     * 当前拦截Mapper是否需要被缓存
     * @Author KangLG<2018年2月24日>
     * @param mappedStatement
     * @return
     */
    private boolean isNeedCached(MappedStatement mappedStatement){
    	if(null!=dbmsCacheAliases && dbmsCacheAliases.length>0){
    		for (String cacheMapperPrefix : dbmsCacheAliases) {
    			if(mappedStatement.getId().startsWith(cacheMapperPrefix)){
    				if(null!=dbmsCacheIgnores && dbmsCacheIgnores.length>0){
    					for (String ignore : dbmsCacheIgnores) {
        					if(mappedStatement.getId().startsWith(ignore)){
        						return false;
        					}
    					}
    				}
    				return true;
    			}
    		}
    	}    	
    	return false;
    }
    private byte[] buildKey(MappedStatement mappedStatement, String keySuffix) {	
    	String cachId = mappedStatement.getId();
		int index = cachId.lastIndexOf(".");
		String key = String.format("DBMS_CACHE:%s:%s", 
				cachId.substring(0, index), 
				StringUtils.isNotBlank(keySuffix) ? keySuffix : cachId.substring(index+1));
		logger.debug("DBMS_CACHE>key>"+ key);
		return key.getBytes();
	}
    private byte[] buildField(Object params) {		
		return SerializeUtil.serialize(null!=params ? params : "--");
	}
    	
}
