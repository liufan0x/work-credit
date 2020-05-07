package com.anjbo.common;
/**
 * 自定义异常类
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:12:27
 */
public class MortgageException extends Exception
{
    private static final long serialVersionUID = -3720258570036335132L;

    private String code;// 错误码

    private String sysMsg; // 打印日志的描述

    private String msg; // 出错的提示信息

    public MortgageException(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }
    
    public MortgageException(String code, String sysMsg, String msg)
    {
        super(sysMsg);
        this.code = code;
        this.msg = msg;
    }

    public MortgageException(String code, String sysMsg, String msg, Exception e)
    {
        super(sysMsg, e);
        this.code = code;
        this.msg = msg;
    }

    public String getCode()
    {
        return code;
    }

    public String getSysMsg()
    {
        return sysMsg;
    }

    public String getMsg()
    {
        return msg;
    }

}
