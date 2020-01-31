package com.iec.analysis.protocol104;


import com.iec.analysis.exception.IllegalFormatException;
import com.iec.analysis.exception.LengthException;
import com.iec.analysis.exception.UnknownTransferReasonException;
import com.iec.analysis.exception.UnknownTypeIdentifierException;
import com.iec.utils.Util;

/**
 * 104解析
 *
 * @author 张雨 2018-02-01
 */
public class Analysis {

    public static String analysis(String message) throws IllegalFormatException, LengthException, UnknownTransferReasonException, UnknownTypeIdentifierException {

        StringBuilder contentbuilder = new StringBuilder();

        String mes = message.replaceAll(" ", "");
        if ((mes.length() == 0) || (mes.length() % 2) == 1) {
            //104 报文没有结束字符，所以最好判断一下报文串的长度是否是偶数
            throw new IllegalFormatException();
        }
        // 将报文转化成int数组
        int msgArray[] = Util.hexStringToIntArray(mes);
        int length = msgArray.length;// 记录报文的长度
        if (msgArray[0] == 0x68 && length >= 2) {
            contentbuilder.append("*APCI应用规约控制信息*").append("\n");
            contentbuilder.append("启动字符[1 byte]: 0x68 ").append("\n");
        } else {
            throw new IllegalFormatException();
        }
        if (length != msgArray[1] + 2) {
            throw new LengthException(msgArray[1] + 2, length);
        }
        contentbuilder.append("应用规约数据单元(APDU)长度[2 byte]:").append(msgArray[1]).append("字节").append("\n");
        contentbuilder.append("控制域[3 byte - 6 byte]：").append("\n").append(Control(new int[]{msgArray[2], msgArray[3], msgArray[4], msgArray[5]}));

        if ((msgArray[2] & 0x03) != 3 && (msgArray[2] & 0x03) != 1) {
            //解析ASDU
            contentbuilder.append("*ASDU应用服务数据单元*\n");
            int asdu[] = new int[length - 6];
            for (int j = 0; j < length - 6; j++) {
                asdu[j] = msgArray[6 + j];
            }
            contentbuilder.append(ASDU.ASDU_analysis(asdu));
        }
        return contentbuilder.toString();

    }

    /**
     * 解析104规约的控制域
     *
     * @param con
     * @return
     */
    private static String Control(int[] con) {
        StringBuilder conBuilder = new StringBuilder();
        switch (con[0] & 0x03) {
            case 1:
                conBuilder.append("\t(S格式控制域标志)\n");
                conBuilder.append("\t接受序列号：").append(((con[3] << 8) + con[2]) >> 1);
                break;
            case 3:
                conBuilder.append("\t(U格式控制域标志)\n");
                if ((con[0] & 0xC0) == 128) {
                    conBuilder.append("\t链路测试TESTFR:确认\n");
                } else if ((con[0] & 0xC0) == 64) {
                    conBuilder.append("\t链路测试TESTFR：命令\n");
                }
                if ((con[0] & 0x30) == 32) {
                    conBuilder.append("\t断开数据传输STOPDT:确认\n");
                } else if ((con[0] & 0x30) == 16) {
                    conBuilder.append("\t断开数据传输STOPDT：命令\n");
                }
                if ((con[0] & 0x0C) == 8) {
                    conBuilder.append("\t启动数据传输STARTDT:确认\n");
                } else if ((con[0] & 0x0C) == 4) {
                    conBuilder.append("\t启动数据传输STARTDT：命令\n");
                }
                break;
            default:
                conBuilder.append("\t(I格式控制域标志)\n");
                conBuilder.append("\t发送序列号：").append(((con[1] << 8) + con[0]) >> 1).append("\n");
                conBuilder.append("\t接受序列号：").append(((con[3] << 8) + con[2]) >> 1).append("\n");
                break;
        }
        return conBuilder.toString();
    }

}
