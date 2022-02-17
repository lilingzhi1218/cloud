package com.example.llz.rulengine.utils.exception.base;

/**
 * 异常结果pojo类
 * @author caihua
 * @version 1.0
 * @since 2021/8/11
 */
public class ExceptionResult {

    /**
     * 错误信息
     */
    private String msg;
    /**
     * 错误码
     */
    private String code;
    /**
     * exception类名错误
     */
    private String error;
    /**
     * throwable类名+错误
     */
    private String root;
    /**
     * 堆栈信息
     */
    private String stack;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
}
