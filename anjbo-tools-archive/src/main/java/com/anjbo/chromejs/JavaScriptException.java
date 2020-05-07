package com.anjbo.chromejs;

public class JavaScriptException extends Exception {
    private static final long serialVersionUID = 1L;
    public JavaScriptException() {}
    public JavaScriptException(Throwable e) {
        super(e);
    }
    public JavaScriptException(String msg) {
        super(msg);
    }
    public JavaScriptException(String msg, Throwable e) {
        super(msg, e);
    }
}
