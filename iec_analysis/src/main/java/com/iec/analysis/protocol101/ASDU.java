package com.iec.analysis.protocol101;

import com.iec.analysis.common.TransferReason;
import com.iec.analysis.common.TypeIdentifier;
import com.iec.analysis.exception.UnknownTransferReasonException;
import com.iec.analysis.exception.UnknownTypeIdentifierException;
import com.iec.analysis.protocol101.InformationBody.InfoElement;
import com.iec.utils.Util;

/**
 * 解析ASDU应用服务数据单元
 *
 * @author 张雨 2018.01.06
 */
public class ASDU {

    public Util util = new Util();
    public String str = "";

    /**
     * 解析ASDU应用服务数据单元
     *
     * @param asdu
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String asdu_analysis(int[] asdu) throws UnknownTypeIdentifierException, UnknownTransferReasonException {

        StringBuilder builder = new StringBuilder();
        // 解析ASDU中的类型标识符TI
        builder.append("类型标识符TI[8 byte]").append(TypeIdentifier.getDescribe(asdu[0])).append("\n");
        // 解析ASDU中的可变结构限定词
        builder.append(VariTureDete(asdu[1])).append("\n");
        // 解析传送原因
        builder.append("传送原因[10 byte]").append(TransferReason.getDdescribe(asdu[2])).append("\n");
        // 解析应用服务数据单元公共地址
        builder.append("应用服务数据单元公共地址[11 byte - 12 byte]：").append(Util.address(asdu[3], asdu[4]));
        // 解析信息元素

        int num = asdu[1] & 0x7F;// 信息元素的个数
        // 将信息元素集装到数组中
        int infoElement[] = new int[asdu.length - 5];
        for (int i = 0; i + 5 < asdu.length; i++) {
            infoElement[i] = asdu[i + 5];
        }
        int SQ = (asdu[1] & 0x80) / 128;
        builder.append(new InfoElement().info_element(infoElement, num, asdu[0], SQ));

        return builder.toString();

    }

    /**
     * 解析ASDU中的可变结构限定词
     *
     * @param b 中的第二个字节
     * @return 返回SQ和信息元素的个数
     */
    private static String VariTureDete(int b) {
        StringBuilder builder = new StringBuilder();
        builder.append("可变结构限定词[9 byte]:").append(Util.toHexString(b)).append("\t");
        builder.append("[bit 7 : SQ ");
        if ((b & 0x80) == 128) {
            builder.append("SQ=1-信息元素顺序]");
        } else if ((b & 0x80) == 0) {
            builder.append("SQ=0-信息元素非顺序]");
        } else {
            builder.append("SQ解析错误！");
        }
        builder.append("]").append("\t");
        builder.append("[bit 0~bit 6:信息元素个数：");
        builder.append(b & 0x7f).append("]");
        return builder.toString();
    }

}
