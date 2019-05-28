package com.iec.analysis.common;

import com.iec.analysis.exception.UnknownTransferReasonException;

/**
 * 解析传送原因
 *
 * @author 张雨 2018.01.06
 */
public enum TransferReason {
    UNUSED(0, "未用"),
    PERIOD(1, "周期、循环 "),
    BG_SCANNER(2, "背景扫描"),
    OUTBURST(3, "突发(自发)"),
    INITIALIZATION_COMPLETE(4, "初始化完成"),
    REQUEST(5, "请求或者被请求"),
    ACTIVATE(6, "激活"),
    ACTIVATE_CONFIRMATION(7, "激活确认"),
    STOP_ACTIVATION(8, "	停止激活 "),
    STOP_ACTIVATION_CONFIRMATION(9, "停止激活确认"),
    ACTIVATE_TERMINATION(10, "激活终止 	"),
    FILE_TRANSFER(13, "文件传输	"),
    ANSWER_STATION_CALL(20, "响应站召唤（总召唤）"),
    UNKNOWN_TYPE(44, "未知的类型标识"),
    UNKNOWN_REASON_TRANSMISSION(45, "未知的传送原因"),
    UNKNOWN_ASDU(46, "未知的应用服务数据单元公共地址"),
    UNKNOWN_ADDRESS(47, "未知的信息对象地址"),
    RC_STATUS_ERROR(48, "遥控执行软压板状态错误"),
    RC_TIME_ERROR(49, "遥控执行时间戳错误"),
    RC_SIGNATURE_ERROR(50, "遥控执行数字签名认证错误");
    private int code;
    private String describe;

    TransferReason(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static String getDdescribe(int code) throws UnknownTransferReasonException {
        for (TransferReason value : TransferReason.values()) {
            if (value.code == code) return value.describe;
        }
        throw  new UnknownTransferReasonException();
    }
}
