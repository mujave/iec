package com.iec.analysis.protocol101;

import com.iec.analysis.exception.*;
import com.iec.utils.Util;

/**
 * 报文解析类
 *
 * @author 张雨
 */
public class Analysis {

    public static String analysis(String messaage) throws LengthException, CustomException, UnknownLinkCodeException, UnknownTransferReasonException, UnknownTypeIdentifierException, IllegalFormatException, CsCheckException {

        int[] msgArray = Util.hexStringToIntArray(messaage);
        int length = msgArray.length;
        StringBuilder contentBuilder = new StringBuilder();

        if (msgArray[0] == 0x68) {
            //这是变长报文
            //检查报文合法性
            contentBuilder.append("启动符[1 byte]：68 》》变长报文\n");
            if (msgArray[1] + 6 != length) {
                throw new LengthException(msgArray[1] + 6, length);
            } else if (msgArray[1] != msgArray[2]) {
                throw new CustomException("报文错误。第二字节应与第三字节保持一致");
            }
            // 校验CS校验
            int CS[] = new int[length - 6];
            for (int i = 4, j = 0; i < msgArray.length - 2; i++, j++) {
                CS[j] = msgArray[i];
            }
            if (Util.variableCS(CS, msgArray[length - 2])) {

                contentBuilder.append("报文长度[2 byte/3 byte]：").append(msgArray[1]).append("字节\n");
                contentBuilder.append("控制域[5 byte]：").append(Util.toHexString(msgArray[4])).append("\n");
                contentBuilder.append(Control.control(msgArray[4])).append("\n");        // 解析控制域
                contentBuilder.append("链路地址[6 byte-7 byte]：").append(Util.address(msgArray[5], msgArray[6]));
                contentBuilder.append("-----------------ASDU解析内容-----------------------\n");
                // 解析ASDU内容
                int[] asdu = new int[length - 9];
                for (int j = 0, i = 7; i < msgArray.length - 2; i++, j++) {
                    asdu[j] = msgArray[i];
                }
                contentBuilder.append(ASDU.asdu_analysis(asdu)).append("\n");
                contentBuilder.append("校验和CS=").append(msgArray[length - 2]).append("校验无误！");
            } else {
                throw new CsCheckException();
            }
        } else if (msgArray[0] == 0x10) {
            contentBuilder.append("启动符[1 byte]：10   定长报文\n");
            if (length != 6) {
                throw new LengthException(6, length);
            }
            if (Util.variableCS(new int[]{msgArray[1], msgArray[2],
                    msgArray[3]}, msgArray[4])) {
                contentBuilder.append("控制域[2 byte]：").append(Control.control(msgArray[1])).append("\n");
                contentBuilder.append("地址域[3 byte - 4 byte]：").append(Util.address(msgArray[2], msgArray[3]));

                contentBuilder.append("校验[5 byte]：").append(msgArray[4]).append("校验无误！");
            } else {
                throw new CsCheckException();
            }

        } else {
            throw new IllegalFormatException();
        }
        return contentBuilder.toString();
    }
}
