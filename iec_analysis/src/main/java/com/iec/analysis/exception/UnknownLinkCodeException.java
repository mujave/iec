package com.iec.analysis.exception;

/**
 * @Author zhangyu
 * @create 2019/5/27 16:24
 */
public class UnknownLinkCodeException extends Exception {

    public UnknownLinkCodeException() {
        super();
    }

    @Override
    public String toString() {
        return "未知类型的链路功能码（平衡式）";
    }
}
