package com.xuecheng.base.execption;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2023/11/15/19:40
 * @Description: 学成在线项目异常类
 */
public class XueChengPlusException extends RuntimeException {

    private String errMessage;

    /**
     * 无参
     */
    public XueChengPlusException () {
        super();
    }

    /**
     * 有参
     * @param errMessage 传入的错误
     */
    public XueChengPlusException (String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public String getErrMessage () {
        return errMessage;
    }

    /**
     * 有固定枚举
     * @param commonError 枚举入参
     */
    public static void cast (CommonError commonError) {
        throw new XueChengPlusException(commonError.getErrMessage());
    }

    /**
     * 自定义入参
     * @param errMessage 入参
     */
    public static void cast (String errMessage) {
        throw new XueChengPlusException(errMessage);
    }

}