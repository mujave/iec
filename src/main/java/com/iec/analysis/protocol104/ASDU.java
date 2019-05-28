package com.iec.analysis.protocol104;


import com.iec.analysis.common.TransferReason;
import com.iec.analysis.common.TypeIdentifier;
import com.iec.analysis.exception.UnknownTransferReasonException;
import com.iec.analysis.exception.UnknownTypeIdentifierException;
import com.iec.utils.Util;

public class ASDU {


    public static String ASDU_analysis(int[] asdu) throws UnknownTypeIdentifierException, UnknownTransferReasonException {
        StringBuilder builder = new StringBuilder();
        builder.append("类属性标识符[7 byte]:").append(TypeIdentifier.getDescribe(asdu[0])).append("\n");
        builder.append("可变结构限定词[8 byte]:").append(VariTureDete(asdu[1])).append("\n");
        builder.append("传送原因[9 byte - 10 byte]:").append(CotAnalysis(asdu[2], asdu[3])).append("\n");

        builder.append("应用服务数据单元公共地址[11 byte - 12 byte]：").append(Util.address(asdu[4], asdu[5]));
        int info[] = new int[asdu.length - 6];
        // 将[信息元素+限定词+（时标）]装入数组info
        for (int i = 0; i < info.length; i++) {
            info[i] = asdu[6 + i];
        }
        builder.append(Infoanalysis(info, asdu[0],
                ((asdu[1] & 0x80) >> 7), (asdu[1] & 0x7f)));
        return builder.toString();
    }

    /**
     * @param info 信息地址+信息元素+限定词+（时标）
     * @param i    类型标识
     * @param j    SQ
     * @param k    信息体个数
     * @return
     * @throws Exception
     */
    private static String Infoanalysis(int[] info, int i, int j, int k) {
        String s = "";
        switch (i) {
            case 1:
                s += new Telemetry104().NoTime_Point(info, k, i, j);
                break;
            case 3:
                s += new Telemetry104().NoTime_Point(info, k, i, j);
                break;
            case 9:// 测量值，归一化值（遥测）*
                s += new Telesignalling104().normalization(info, k, i, j);
                break;
            case 11:// 测量值，标度化值（遥测）
                s += new Telesignalling104().standardization(info, k, i, j);
                break;
            case 13:// 测量值，短浮点数（遥测）
                s += new Telesignalling104().short_float(info, k, i, j);
                break;
            case 30:
                s += new Telemetry104().Time_Point(info, k, i, j);
                break;
            case 31:
                s += new Telemetry104().Time_Point(info, k, i, j);
                break;
            case 45:// 单命令（遥控）
                s = new Telecontrol104().Single_command(info, k, i);
                break;
            case 46:// 双命令（遥控）
                s = new Telecontrol104().Double_command(info, k, i);
                break;
            case 48:
                s += new ParamePreset104().activate_single_parmeter(info, k, i, j);
                break;
            case 70:
                s += "信息对象地址：";
                s += InfoAddress(info[0], info[1], info[2]);
                s += "\n";
                s += "初始化原因：" + (info[3]) + " ";
                s += (info[3] == 0) ? "当地电源合上" : (info[3] == 1) ? "当地手动复位"
                        : (info[3] == 2) ? "远方复位" : "使用保留";
                break;
            case 100:
                s += "信息对象地址：";
                s += InfoAddress(info[0], info[1], info[2]);
                s += "\n";
                if (info[3] == 20) {
                    s += "召唤限定词QOI:20";
                } else {
                    s += "召唤限定词出错！当前值为0x"
                            + ((info[3] < 10) ? "0" + Integer.toString(info[3], 16)
                            : Integer.toString(info[3], 16))
                            + ",正确值应为0x14！";
                }

                break;
            case 102:
                s += new ParamePreset104().single_parmeter(info, k, i, j);
                break;
            case 103:
                s += "信息对象地址：";
                s += InfoAddress(info[0], info[1], info[2]);
                s += "\n";
                int[] time = new int[7];
                for (int l = 0; l < time.length; l++) {
                    time[l] = info[3 + l];
                }
                s += TimeScale(time);
                break;

            case 105:
                s += "信息对象地址：";
                s += InfoAddress(info[0], info[1], info[2]);
                s += "\n";
                if (info[3] == 1) {
                    s += "复位进程限定词:1";
                } else {
                    s += "复位进程限定词出错！当前值为0x"
                            + ((info[3] < 10) ? "0" + Integer.toString(info[3], 16)
                            : Integer.toString(info[3], 16))
                            + ",正确值应为0x01！";
                }
                break;
            case 132:
                s += new ParamePreset104().multi_parmeter(info, k, i, j);
                break;
            case 136:
                s += new ParamePreset104().activate_multi_parmeter(info, k, i, j);
                break;
            default:
                s = "类型标识出错，无法解析信息对象！";
                break;
        }

        return s;
    }

    /**
     * 信息对象地址转换
     *
     * @param i bit0 ~bit7
     * @param j bit8 ~bit15
     * @param k bit16 ~bit23
     * @return 十进制地址（十六进制地址）
     */
    public static String InfoAddress(int i, int j, int k) {

        int add;
        add = (k << 16) + (j << 8) + i;

        return add
                + "("
                + ((k < 10) ? "0" + Integer.toString(k, 16) : Integer.toString(
                k, 16))
                + ((j < 10) ? "0" + Integer.toString(j, 16) : Integer.toString(
                j, 16))
                + ((i < 10) ? "0" + Integer.toString(i, 16) : Integer.toString(
                i, 16)) + "H)" + "";
    }

    /**
     * 解析传送原因
     *
     * @param i
     * @param j
     * @return
     */
    public static String CotAnalysis(int i, int j) throws UnknownTransferReasonException {
        StringBuilder builder = new StringBuilder();
        if ((i & 0x80) == 128) {
            builder.append("[T(test) bit7:1   实验]");
        } else if ((i & 0x80) == 0) {
            builder.append("[T(test) bit7:0  未实验]");
        }
        if ((i & 0x40) == 64) {
            builder.append("[P/N  bit6:1  否定确认]");
        } else if ((i & 0x40) == 0) {
            builder.append("[P/N  bit6:0  肯定确认]");
        }
        builder.append("[原因  bit5~bit0:").append(TransferReason.getDdescribe(i & 0x3F)).append("]");
        return builder.toString();
    }

    /**
     * 解析可变结构限定词
     *
     * @param b
     * @return
     */
    private static String VariTureDete(int b) {
        String vvString = "可变结构限定词:";
        vvString += Util.toHexString(b);
        vvString += "   ";
        if ((b & 0x80) == 128) {
            vvString += "SQ=1 信息元素地址顺序     ";
        } else if ((b & 0x80) == 0) {
            vvString += "SQ=0  信息元素地址非顺序     ";
        }
        vvString += "信息元素个数：";
        vvString += b & 0x7f;
        return vvString;
    }

    private static  String TimeScale(int b[]) {

        StringBuilder result = new StringBuilder();

        int year = b[6] & 0x7F;
        int month = b[5] & 0x0F;
        int day = b[4] & 0x1F;
        int week = (b[4] & 0xE0) / 32;
        int hour = b[3] & 0x1F;
        int minute = b[2] & 0x3F;
        int second = (b[1] << 8) + b[0];

        result.append("时标CP56Time2a:20");
        result.append(year).append("-");
        result.append(String.format("%02d", month)).append("-");
        result.append(String.format("%02d", day)).append(",");
        result.append(hour).append(":").append(minute).append(":");
        result.append(second / 1000 + "." + second % 1000).append("\n");

        return result.toString();
    }
}
