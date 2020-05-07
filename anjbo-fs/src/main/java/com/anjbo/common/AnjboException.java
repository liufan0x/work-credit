package com.anjbo.common;
/**
 * 
 * @author Kevin Chang
 *
 */
public class AnjboException extends Exception
{
    private static final long serialVersionUID = -3720258570036335132L;

    private String code;// 错误码

    private String sysMsg; // 打印日志的描述

    private String msg; // 出错的提示信息

    public AnjboException(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }
    
    public AnjboException(String code, String sysMsg, String msg)
    {
        super(sysMsg);
        this.code = code;
        this.msg = msg;
    }

    public AnjboException(String code, String sysMsg, String msg, Exception e)
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
