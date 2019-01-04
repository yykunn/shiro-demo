package com.example.demo.po;
public class Result<T> {

    /** 状态码 */
    private Integer Code;
    /** 提示信息 */
    private String msg;
    /** 结果内容 */
    private T data;

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}