package com.iec.analysis.common;


import com.iec.analysis.exception.UnknownTypeIdentifierException;

/**
 * 归一化值
 *
 * @Authorzhangyu
 * @create2019/5/2716:59
 */
public enum TypeIdentifier {
    SINGLE_POINT(1, "不带时标的单点信息"),
    TWO_POINT(3, "不带时标的双点信息"),
    NORMALIZED(9, "测量值，归一化值"),
    SCALE(11, "测量值，标度化值"),
    SHORT_FLOAT(13, "测量值，短浮点数"),
    SINGLE_POINT_TIME(30, "带CP56Time2a时标的单点信息"),
    TWO_POINT_TIME(31, "带CP56Time2a时标的双点信息"),
    SINGLE_COMMAND(45, "单命令（遥控）"),
    TWO_COMMAND(46, "双命令（遥控）"),
    PRESETS_SINGLE_PARAmeter(48, "预置/激活单个参数命令"),
    READ_SINGLE_PARAMETer(102, "读单个参数命令"),
    END_OF_INITIALIZATIon(70, "初始化结束"),
    CALL_COMMAND(100, "召唤命令"),
    CLOCK_SYNCHRONIZATIon(103, "时钟同步/读取命令"),
    TEST_COMMAND(104, "测试命令"),
    RESET_PROCESS(105, "复位进程命令"),
    READ_MULTIPLE_PARAMETERS(132, "读多个参数命令"),
    PRESETS_MULTIPLE_PARAMETERS(136, "预置/激活多个参数命令");


    private int code;
    private String describe;

    TypeIdentifier(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static String getDescribe(int code) throws UnknownTypeIdentifierException {
        for (TypeIdentifier value : TypeIdentifier.values()) {
            if (value.code == code) return value.describe;
        }

        throw new UnknownTypeIdentifierException();
    }
}
