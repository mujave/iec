package com.iec.assemble104;

import com.iec.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/**
 * 连续信息体的104报文
 *
 * @Author zhangyu
 * @create 2019/6/14 15:37
 */
public class ContinuousAddressBuilder<T> extends VaribleLengthPacket {
    /**
     * 信息体元素的地址
     */
    private int info_address;
    /**
     * 信息元素的长度
     * 说明;遥信/遥控 1字节：遥测：2字节;参数设置：4字节：
     * 总召唤/时钟同步/复位进程/初始化无此项，所以应该填充位0
     */
    private int infoLength;
    /**
     * 信息体元素
     */
    private ArrayList<T> infos;

    @Deprecated
    public ContinuousAddressBuilder() {
        this("00000000", 1, 0, 1, 0, 0);
    }

    public ContinuousAddressBuilder(String con, int TI, int asduAddress, int transferReason, int info_address, int infoLength) {
        this(con, TI, asduAddress, transferReason, info_address, infoLength, -1);
    }

    public ContinuousAddressBuilder(String con, int TI, int asduAddress, int transferReason, int info_address, int infoLength, int qualifier) {
        super(con, TI, 1, asduAddress, transferReason);
        this.info_address = info_address;
        this.infoLength = infoLength;
        this.qualifier = qualifier;
    }


    public ContinuousAddressBuilder<T> buildCon(String con) {
        this.con = con;
        return this;
    }

    public ContinuousAddressBuilder<T> buildTI(int Ti) {
        this.TI = Ti;
        return this;
    }

    public ContinuousAddressBuilder<T> buildAsduAddress(int asduAddress) {
        this.asduAddress = asduAddress;
        return this;
    }

    public ContinuousAddressBuilder<T> buildTransferReason(int transferReason) {
        this.transferReason = transferReason;
        return this;
    }

    public ContinuousAddressBuilder<T> buildInfoAddress(int info_address) {
        this.info_address = info_address;
        return this;
    }

    public ContinuousAddressBuilder<T> buildInfoLength(int infoLength) {
        this.infoLength = infoLength;
        return this;
    }

    public ContinuousAddressBuilder<T> buildInfos(T info) {
        this.infos.add(info);
        return this;
    }

    public ContinuousAddressBuilder<T> buildInfosBatch(T[] infos) {
        Arrays.stream(infos).forEach(x -> this.infos.add(x));
        return this;
    }

    public ContinuousAddressBuilder<T> buildInfosBatch(ArrayList<T> infos) {
        infos.stream().forEach(x -> this.infos.add(x));
        return this;
    }

    public String build() {
        return this.build(x -> Integer.parseInt(x.toString()));
    }

    public String build(Function<T, Integer> fun) {
        StringBuilder builder = new StringBuilder();
        builder.append("68");
        int messageLen = 10 + 3;
        messageLen += (infos.size() * infoLength);
        if (this.qualifier != -1) messageLen++;
        if (this.dateTime != null) {
            messageLen += 7;
        }
        builder.append(String.format("%02X", messageLen));
        builder.append(this.con);
        builder.append(String.format("%02X", this.TI));
        builder.append(String.format("%02X", (this.SQ << 7) + this.infos.size()));
        builder.append(String.format("%04X", this.transferReason));
        builder.append(Util.getAddressStr(this.asduAddress));
        builder.append(Util.getAddressStr3(this.info_address));
        infos.stream().forEach(x -> builder.append(Util.getInfoStr(fun.apply(x), infoLength)));
        if (qualifier != -1) builder.append(String.format("%02X", qualifier));
        if (dateTime != null) builder.append(Util.date2HStr(this.dateTime));
        return builder.toString();
    }


}
