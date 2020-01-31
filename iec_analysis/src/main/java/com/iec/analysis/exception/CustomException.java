package com.iec.analysis.exception;

/**
 * @Author zhangyu
 * @create 2019/5/27 15:49
 */
public class CustomException extends Exception {
    private String content;

    public CustomException(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return this.content;
    }

}
