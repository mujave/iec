package com.iec.analysis.exception;

/**
 * @description
 * @author: mujave
 * @create: 2020-02-01 14:41
 **/
public class CsCheckException extends Exception {
    public CsCheckException() {
    }

    @Override
    public String toString() {
        return "CS校验错误";
    }

}
