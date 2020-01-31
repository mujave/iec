package com.iec.analysis.common;

import com.iec.analysis.exception.UnknownLinkCodeException;

/**
 * @Author zhangyu
 * @create 2019/5/27 16:09
 */
public enum BalancedLinkCode {
    RESET_REMOTE_LINK(0, "复位远方链路"),
    RESET_USER_PROCESS(1, "复位用户进程"),
    CONFIRM_LINK_TESTING(2, "发送/确认链路测试功能 "),
    CONFIRM_USER_DATA(3, "发送/确认用户数据"),
    NO_ANSWERED_DATA(4, "发送/无回答用户数据"),
    REQUEST_REQUEST_LINK_STATUS(9, "请求/响应请求链路状态"),
    RESPONSE_LINK_STATE(11, "响应：链路状态");

    private int code;
    private String describe;

    BalancedLinkCode(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static String getDescribe(int code) throws UnknownLinkCodeException {
        for (BalancedLinkCode balancedLinkCode : BalancedLinkCode.values()) {
            if (balancedLinkCode.code == code) return balancedLinkCode.describe;
        }
        throw new UnknownLinkCodeException();
    }
}
