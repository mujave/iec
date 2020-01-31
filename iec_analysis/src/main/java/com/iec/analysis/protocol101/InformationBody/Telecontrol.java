package com.iec.analysis.protocol101.InformationBody;


import com.iec.utils.Util;

/**
 * 遥控信息解析
 *
 * @author 张雨 2018.01.12
 */
public class Telecontrol {
    private Util util = new Util();

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
        builder.append(util.address(infoElement[0], infoElement[1]));
        builder.append("单命令 SCO:");
        builder.append("  ");
        builder.append(util.toHexString(infoElement[2]));
        builder.append("\n");
        if ((infoElement[2] & 0x80) == 128) {
            builder.append("遥控选择命令      ");
        }
        if ((infoElement[2] & 0x80) == 0) {
            builder.append("遥控执行命令      ");
        }
        if ((infoElement[2] & 0x01) == 1) {
            builder.append("开关合      ");
        }
        if ((infoElement[2] & 0x01) == 0) {
            builder.append("开关分      ");
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
        builder.append(util.address(infoElement[0], infoElement[1]));
        builder.append("双命令 DCO:");
        builder.append("  ");
        builder.append(util.toHexString(infoElement[2]));
        builder.append("\n");
        if ((infoElement[2] & 0x80) == 128) {
            builder.append("遥控选择命令      ");
        }
        if ((infoElement[2] & 0x80) == 0) {
            builder.append("遥控执行命令      ");
        }
        if ((infoElement[2] & 0x03) == 0) {
            builder.append("不允许，有错误      ");
        }
        if ((infoElement[2] & 0x03) == 1) {
            builder.append("开关分      ");
        }
        if ((infoElement[2] & 0x03) == 2) {
            builder.append("开关合      ");
        }
        if ((infoElement[2] & 0x03) == 3) {
            builder.append("不允许，有错误      ");
        }
        return builder.toString();
    }

}
