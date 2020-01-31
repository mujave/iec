package com.iec.analysis.exception;

/**
 * @Author zhangyu
 * @create 2019/5/27 17:26
 */
public class UnknownTypeIdentifierException extends Exception {
    public UnknownTypeIdentifierException() {
        super();
    }

    @Override
    public String toString() {
        return "未知类型标识符";
    }
}
