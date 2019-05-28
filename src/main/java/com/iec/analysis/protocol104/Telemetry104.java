package com.iec.analysis.protocol104;

import com.iec.utils.Util;

/**
 * 遥信信息解析
 *
 * @author 张雨 2018.01.12
 */
public class Telemetry104 {
    /**
     * 不带时标的单双点信息（遥信）
     *
     * @param infoElement
     * @param num
     * @param tI
     * @param sQ
     * @return
     */
    public String NoTime_Point(int[] infoElement, int num, int tI, int sQ) {
        StringBuilder builder = new StringBuilder();
        if (sQ == 1) {
            builder.append("信息对象地址：    ");
            builder.append(new ASDU().InfoAddress(infoElement[0], infoElement[1],
                    infoElement[2])).append("\n");
            for (int i = 0; i < num; i++) {
                builder.append("信息元素");
                builder.append(i + 1);
                builder.append("的信息元素值：");
                builder.append(Util.toHexString(infoElement[i + 3]));
                builder.append("\n");
                builder.append(this.point(infoElement[i + 3], tI));
                builder.append("\n");
            }

        } else {
            for (int i = 1; i <= num; i++) {
                builder.append("信息元素");
                builder.append(i);
                builder.append("的内容如下：\n");
                builder.append("信息对象地址：    ");
                builder.append(new ASDU().InfoAddress(infoElement[(i - 1) * 4],
                        infoElement[(i - 1) * 4 + 1],
                        infoElement[(i - 1) * 4 + 2]));
                builder.append("\n");
                builder.append("信息元素值：");
                builder.append(Util.toHexString(infoElement[i * 4 - 1]));
                builder.append("\n");
                builder.append(this.point(infoElement[i * 4 - 1], tI));
                builder.append("\n");
            }

        }
        return builder.toString();
    }

    private String point(int i, int tI) {
        StringBuilder builder = new StringBuilder();
        if (tI == 1 || tI == 30) {
            if ((i & 0x80) == 128) {
                builder.append("无效/");
            }
            if ((i & 0x80) == 0) {
                builder.append("有效/");
            }
            if ((i & 0x40) == 64) {
                builder.append("非当前值/");
            }
            if ((i & 0x40) == 0) {
                builder.append("当前值/");
            }
            if ((i & 0x20) == 32) {
                builder.append("被取代/");
            }
            if ((i & 0x20) == 0) {
                builder.append("未被取代/");
            }
            if ((i & 0x10) == 16) {
                builder.append("被闭锁/");
            }
            if ((i & 0x10) == 0) {
                builder.append("未被闭锁/");
            }
            if ((i & 0x01) == 1) {
                builder.append("开关合	");
            }
            if ((i & 0x01) == 0) {
                builder.append("开关分	");
            }
        }
        if (tI == 3 || tI == 31) {
            if ((i & 0x80) == 128) {
                builder.append("无效/");
            }
            if ((i & 0x80) == 0) {
                builder.append("有效/");
            }
            if ((i & 0x40) == 64) {
                builder.append("非当前值/");
            }
            if ((i & 0x40) == 0) {
                builder.append("当前值/");
            }
            if ((i & 0x20) == 32) {
                builder.append("被取代/");
            }
            if ((i & 0x20) == 0) {
                builder.append("未被取代/");
            }
            if ((i & 0x10) == 16) {
                builder.append("被闭锁/");
            }
            if ((i & 0x10) == 0) {
                builder.append("未被闭锁/");
            }
            if ((i & 0x03) == 0) {
                builder.append("不确定或中间状态	");
            }
            if ((i & 0x03) == 1) {
                builder.append("确定开关分	");
            }
            if ((i & 0x03) == 2) {
                builder.append("确定开关合	");
            }
            if ((i & 0x03) == 3) {
                builder.append("不确定	");
            }

        }

        return builder.toString();
    }

    /**
     * 带CP56Time2a时标的单双点信息（遥信）
     *
     * @param infoElement
     * @param num
     * @param tI
     * @param sQ
     * @return
     */
    public String Time_Point(int[] infoElement, int num, int tI, int sQ) {

        StringBuilder builder = new StringBuilder();
        if (sQ == 0) {
            for (int i = 1; i <= num; i++) {
                builder.append("信息元素");
                builder.append(i);
                builder.append("的内容如下：\n");
                builder.append("信息对象地址：");
                builder.append(new ASDU().InfoAddress(infoElement[(i - 1) * 11],
                        infoElement[(i - 1) * 11 + 1],
                        infoElement[(i - 1) * 11 + 2])).append("\n");
                builder.append("信息元素值：");
                builder.append(Util.toHexString(infoElement[(i - 1) * 11 + 3])).append("\n");

                builder.append(this.point(infoElement[(i - 1) * 11 + 3], tI)).append("\n");

                int time[] = new int[7];
                for (int j = 0; j < 7; j++) {
                    time[j] = infoElement[(i - 1) * 11 + 4 + j];
                }
                builder.append(Util.TimeScale(time));
            }
        } else {
            builder.append("按照DL/T 634.5101-2002规定，带长时标的单/双点信息遥信报文并不存在信息元素序列（SQ=1）的情况。");
        }
        return builder.toString();

    }
}
