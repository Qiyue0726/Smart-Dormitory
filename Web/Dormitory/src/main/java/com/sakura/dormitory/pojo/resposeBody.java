package com.sakura.dormitory.pojo;

public class resposeBody<T> {

    /** 状态码 */
    private Integer statusCode;

    /** 提示信息 */
    private String message;

    /** 数据 */
    private T data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
