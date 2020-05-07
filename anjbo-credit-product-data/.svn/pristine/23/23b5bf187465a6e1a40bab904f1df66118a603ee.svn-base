package com.anjbo.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class AntiSqlInjectionfilter implements Filter {
	
	Logger log = Logger.getLogger(AntiSqlInjectionfilter.class);
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
          
        ServletRequest requestWrapper = null;  
        if(request instanceof HttpServletRequest) {  
            requestWrapper = new MyHttpServletRequest((HttpServletRequest) request,(HttpServletResponse) response);  
        }  
        if(null == requestWrapper) {  
            chain.doFilter(request, response);  
        } else {  
            chain.doFilter(requestWrapper, response);  
        }  
           
    }
      
    /**
     * getInputStream()和getReader() 都只能读取一次，由于RequestBody是流的形式读取，那么流读了一次就没有了，所以只能被调用一次。
     * 先将RequestBody保存，然后通过Servlet自带的HttpServletRequestWrapper类覆盖getReader()和getInputStream()方法，
     * 使流从保存的body读取。然后再Filter中将ServletRequest替换为ServletRequestWrapper
     * @description
     * @author liuf
     */
    private class MyHttpServletRequest extends HttpServletRequestWrapper {
 
        private String _body;
        private HttpServletRequest _request;
 
        public MyHttpServletRequest(HttpServletRequest request,HttpServletResponse response) throws IOException
        {
            super(request);
            _request = request;
 
            StringBuffer jsonStr = new StringBuffer();
        	BufferedReader bufferedReader = request.getReader();
            String line;
            String sql ="";
            while ((line = bufferedReader.readLine()) != null)
                jsonStr.append(line);
            JSONObject json = JSONObject.fromObject(jsonStr.toString());
            log.info(json);
            _body = jsonStr.toString();
            String[] strArray = _body.split(":");
    		for (String string : strArray) {
    			if(string.indexOf(",")!=-1){
    				string = string.substring( 0,string.indexOf(","));
    				sql+=string;
    			}else if(string.indexOf("}")!=-1){
    				string = string.substring( 0,string.indexOf("}"));
    				sql+=string;
    			}
    		}
    		log.info(sql);
    		bufferedReader.close();
    		 //有sql关键字，跳转到error.html  
            if (sqlValidate(sql)) { 
                throw new IOException("illegal parameter");  
            } else {  
            	_body = jsonStr.toString();
            }  
        }
 
        @Override
        public ServletInputStream getInputStream() throws IOException
        {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(_body.getBytes());
            return new ServletInputStream()
            {
                public int read() throws IOException
                {
                    return byteArrayInputStream.read();
                }

				@Override
				public boolean isFinished() {
					return false;
				}

				@Override
				public boolean isReady() {
					return false;
				}

				@Override
				public void setReadListener(ReadListener arg0) {
					
				}
            };
        }
 
        @Override
        public BufferedReader getReader() throws IOException
        {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
 
    }
 
    @Override
    public void destroy() {
 
    }
    
  //效验  
    protected static boolean sqlValidate(String str) {  
        str = str.toLowerCase();//统一转为小写  
        String badStr = "'|exec|execute|insert|delete|update|drop|*|%|master|truncate|" +  
                "declare|sitename|net user|xp_cmdshell|create|drop|" +  
                "grant|group_concat|column_name|" +  
                "information_schema.columns|table_schema|delete|update|*|" +  
                "truncate|declare|;|--|%|#";//过滤掉的sql关键字，可以手动添加  
        String[] badStrs = badStr.split("\\|");  
        for (int i = 0; i < badStrs.length; i++) {  
            if (str.indexOf(badStrs[i]) >= 0) {  
                return true;  
            }  
        }  
        return false;  
    }
    
    public static void main(String[] args) {
    	String params ="";
    	String str ="{'tblName': 'tbl_cm_','start': 0,'productCode': '01', 'cityCode': '440301','pageSize': 15, 'condition': [ { 'key': 'estateType','value': '不动产权证书','type': '1' }, {'key': 'cooperativeAgencyName','value': '鑫华','type': '2'},{'key': 'previousHandleTime','startValue': '2017-01-01',";
		String[] strArray = str.split(":");
		for (String string : strArray) {
			if(string.indexOf(",")!=-1){
				string = string.substring( 0,string.indexOf(","));
				params+=string;
			}
		}
		System.out.println(params);
	}
      
      
 
}