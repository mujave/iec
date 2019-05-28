package com.iec.analysis.protocol104;

import com.iec.utils.Util;

/**
 * 遥控信息解析
 *
 * @author 张雨 2018.01.12
 */
public class Telecontrol104 {

    /**
     * 单命令信息解析
     *
     * @param infoElement 信息元素集报文
     * @param num         信息元素的个数
     * @param tI          类型标识
     * @return
     */
    public String Single_command(int[] infoElement, int num, int tI) {
        StringBuilder builder = new StringBuilder();
        builder.append("信息元素地址：");
        builder.append(new ASDU().InfoAddress(infoElement[0], infoElement[1],
                infoElement[2])).append("\n");
        builder.append("单命令 SCO:").append("\t");
        builder.append(Util.toHexString(infoElement[3])).append("\n");
        if ((infoElement[3] & 0x80) == 128) {
            builder.append("遥控选择命令\t");
        }
        if ((infoElement[3] & 0x80) == 0) {
            builder.append("遥控执行命令\t");
        }
        if ((infoElement[3] & 0x01) == 1) {
            builder.append("开关合\t");
        }
        if ((infoElement[3] & 0x01) == 0) {
            builder.append("开关分\t");
        }
        return builder.toString();
    }

    /**
     * 双命令信息解析
     *
     * @param infoElement 信息元素集报文
     * @param num         信息元素的个数
     * @param tI          类型标识
     * @return
     */
    public String Double_command(int[] infoElement, int num, int tI) {
        StringBuilder builder = new StringBuilder();
        builder.append("信息元素地址：");
        builder.append(new ASDU().InfoAddress(infoElement[0], infoElement[1],
                infoElement[2])).append("\n");
        builder.append("双命令 DCO:\t");
        builder.append(Util.toHexString(infoElement[3])).append("\n");
        if ((infoElement[3] & 0x80) == 128) {
            builder.append("遥控选择命令\t");
        }
        if ((infoElement[3] & 0x80) == 0) {
            builder.append("遥控执行命令\t");
        }
        if ((infoElement[2] & 0x03) == 0) {
            builder.append("不允许，有错误\t");
        }
        if ((infoElement[3] & 0x03) == 1) {
            builder.append("开关分\t");
        }
        if ((infoElement[3] & 0x03) == 2) {
            builder.append("开关合\t");
        }
        if ((infoElement[3] & 0x03) == 3) {
            builder.append("不允许，有错误\t");
        }
        return builder.toString();
    }

}
