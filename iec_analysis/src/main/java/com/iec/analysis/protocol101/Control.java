package com.iec.analysis.protocol101;

import com.iec.analysis.common.BalancedLinkCode;
import com.iec.analysis.exception.UnknownLinkCodeException;

/**
 * @author 张雨
 * @category 解析控制域（平衡式）
 */

public class Control {

    public static String control(int b) throws UnknownLinkCodeException {

        StringBuilder res_str = new StringBuilder();
        res_str.append("\t平衡链路功能码[1 bit - 4 bit]：").append(b & 0x0f);
        res_str.append(BalancedLinkCode.getDescribe(b & 0x0f)).append("\n");


        if ((b & 0x80) == 0) {
            res_str.append("\tDIR=0  传输方向：下行报文");
            res_str.append(upriver(b));
        } else if ((b & 0x80) == 128) {
            res_str.append("\tDIR=1  传输方向：上行报文");
            res_str.append(downriver(b));
        }
        return res_str.toString();
    }

    /**
     * 上行报文
     *
     * @param b 控制域
     * @return PRM RES DFC
     */
    private static String downriver(int b) {

        StringBuilder builder = new StringBuilder();
        if ((b & 0x40) == 64) {
            builder.append("\tPRM=1  报文来自启动站\n");
        } else if ((b & 0x40) == 0) {
            builder.append( "\tPRM=0  报文来自从动站\n");
        }

        if ((b & 0x20) == 32) {
            builder.append( "\tRES=1  ");
        } else if ((b & 0x20) == 0) {
            builder.append( "\tRES=0  ");
        }

        if ((b & 0x10) == 16) {
            builder.append( "\tDFC＝1  从动站不能接收后续报文  ");
        } else if ((b & 0x10) == 0) {
            builder.append( "\tDFC＝0  从动站可以接收后续报文  ");
        }
        return builder.toString();
    }

    /**
     * 下行报文
     *
     * @param b 控制域
     * @return PRM FCB FCV
     */
    private static String upriver(int b) {
        StringBuilder builder = new StringBuilder();
        if ((b & 0x40) == 64) {
            builder.append( "\tPRM=1  报文来自启动站");

        } else if ((b & 0x40) == 0) {
            builder.append( "\tPRM=0  报文来自从动站");
        }

        if ((b & 0x20) == 32) {
            builder.append( "\tFCB=1");
        } else if ((b & 0x20) == 0) {
            builder.append( "\tFCB=0");
        }

        if ((b & 0x10) == 16) {
            builder.append( "\tFCV=1  FCB有效");
        } else if ((b & 0x10) == 0) {
            builder.append( "\tFCV=0   表示FCB无效");
        }
        return builder.toString();

    }
}
