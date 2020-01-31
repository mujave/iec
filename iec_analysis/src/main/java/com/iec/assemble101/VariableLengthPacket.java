package com.iec.assemble101;

import com.iec.analysis.common.TypeIdentifier;

import java.util.Date;
import java.util.function.Function;

/**
 * 抽象的变长报文
 *
 * @Author zhangyu
 * @create 2019/5/29 16:36
 */
public abstract class VariableLengthPacket {
    /**
     * 控制域。
     * 此处的控制域的计算方式按照101平衡式报文的计算方式得出。
     * 具体的含义顺序为DIR(bit7)、PRM(bit6)、FCB(bit5)、FCV(bit4)、FC（bit0-bit3）
     */
    int con;
    /**
     * 地址域A。
     * 这个是报文中第一个地址，在控制域之后，并且选址范围为0001H～FFFFH(65535个)，
     * FFFFH为广播地址，0000H为无效地址。
     */
    int address;
    /**
     * 类型标识TI。
     * 可以提供已定义的 TypeIdentifier的code值；
     */
    int TI;
    /**
     * SQ的值，表明这条报文的信息体元素的地址是否是连续的；
     * "0.地址不连续1.地址连续"
     */
    int SQ;
    /**
     * 应用服务数据单元公共地址.
     */
    int asduAddress;
    /**
     * 传输原因。
     */
    int transferReason;
    /**
     * 限定词（品质描述词）。限定词默认填充0，无限定词时请填充-1
     */
    int qualifier;
    /**
     * 时标.默认无时标
     */
    Date dateTime;

    /**
     * 生成报文的方法，具体实现由具体的子类实现。
     *
     * @return 报文字符串
     */
    public abstract String builder();

    public VariableLengthPacket() {
    }

    public VariableLengthPacket(int SQ) {
        this.SQ = SQ;
    }

    public VariableLengthPacket(int con, int address, int TI, int SQ, int asduAddress, int transferReason) {
        this.con = con;
        this.address = address;
        this.TI = TI;
        this.SQ = SQ;
        this.asduAddress = asduAddress;
        this.transferReason = transferReason;
    }
}
