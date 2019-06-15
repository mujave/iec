package com.iec.assemble104;


import java.util.Date;

/**
 * @Author zhangyu
 * @create 2019/6/14 15:10
 */
public abstract class VaribleLengthPacket {
    /**
     * 控制域 4字节 三种格式
     */
    String  con;
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

    public VaribleLengthPacket() {
    }

    public VaribleLengthPacket(int SQ) {
        this.SQ = SQ;
    }

    public VaribleLengthPacket(String con, int TI, int SQ, int asduAddress, int transferReason ) {
        this.con = con;
        this.TI = TI;
        this.SQ = SQ;
        this.asduAddress = asduAddress;
        this.transferReason = transferReason;
    }
}
