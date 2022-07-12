package com.example.jkm_web.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class RestMessage implements Serializable {
    private static final long serialVersionUID = 8174771045563900428L;
    @ApiModelProperty(value = "是否成功",required=true)
    private boolean success=true;

    @ApiModelProperty(value = "错误码")
    private Integer errCode;

    @ApiModelProperty(value = "提示信息")
    private String message;

    @ApiModelProperty(value = "数据")
    private Object data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public RestMessage() {
    }

    public RestMessage(boolean success, Integer errCode, String message, Object data) {
        this.success = success;
        this.errCode = errCode;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
