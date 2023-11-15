package com.xuecheng.base.execption;

import java.io.Serializable;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2023/11/15/19:49
 * @Description: 错误响应参数包装
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }


}
