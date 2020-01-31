package com.iec.assemble104;

import com.iec.utils.Util;

import java.time.zone.ZoneOffsetTransitionRule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/**
 * 不连续信息体的104报文
 *
 * @Author zhangyu
 * @create 2019/6/14 15:37
 */
public class UnContinuousAddressBuilder<T> extends VaribleLengthPacket {
    /**
     * 信息体元素的地址
     */
    private ArrayList<Integer> info_adds;
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
    public UnContinuousAddressBuilder() {
        this("00000000", 1, 0, 1, 0, 0);
    }

    public UnContinuousAddressBuilder(String con, int TI, int asduAddress, int transferReason, int infoLength) {
        this(con, TI, asduAddress, transferReason, 0, infoLength, -1);
    }

    public UnContinuousAddressBuilder(String con, int TI, int asduAddress, int transferReason, int info_address, int infoLength) {
        this(con, TI, asduAddress, transferReason, info_address, infoLength, -1);
    }

    /**
     * @param con            控制域
     * @param TI             类型标识符
     * @param asduAddress    应用服务数据单元公共地址
     * @param transferReason 传送原因
     * @param info_address   信息体地址
     * @param infoLength     信息体长度
     * @param qualifier      限定词
     */
    public UnContinuousAddressBuilder(String con, int TI, int asduAddress, int transferReason, int info_address, int infoLength, int qualifier) {
        super(con, TI, 0, asduAddress, transferReason);
        this.info_adds = new ArrayList<Integer>();
        this.info_adds.add(info_address);
        this.infos = new ArrayList<T>();
        this.infoLength = infoLength;
        this.qualifier = qualifier;
    }


    public UnContinuousAddressBuilder<T> buildCon(String con) {
        this.con = con;
        return this;
    }

    public UnContinuousAddressBuilder<T> buildTI(int Ti) {
        this.TI = Ti;
        return this;
    }

    public UnContinuousAddressBuilder<T> buildAsduAddress(int asduAddress) {
        this.asduAddress = asduAddress;
        return this;
    }

    public UnContinuousAddressBuilder<T> buildTransferReason(int transferReason) {
        this.transferReason = transferReason;
        return this;
    }

    public UnContinuousAddressBuilder<T> buildInfoAddress(int info_address) {
        this.info_adds.add(info_address);
        return this;
    }

    public UnContinuousAddressBuilder<T> buildInfoLength(int infoLength) {
        this.infoLength = infoLength;
        return this;
    }

    public UnContinuousAddressBuilder<T> buildInfos(T info) {
        this.infos.add(info);
        return this;
    }

    public UnContinuousAddressBuilder<T> buildInfosBatch(T[] infos) {
        Arrays.stream(infos).forEach(x -> this.infos.add(x));
        return this;
    }

    public UnContinuousAddressBuilder<T> buildInfosBatch(ArrayList<T> infos) {
        infos.stream().forEach(x -> this.infos.add(x));
        return this;
    }

    public String build() {
        return this.build(x -> Integer.parseInt(x.toString()));
    }

    public String build(Function<T, Integer> fun) {
        StringBuilder builder = new StringBuilder();
        builder.append("68");
        int messageLen = 10;
        messageLen += info_adds.size() * 3;
        messageLen += (infos.size() * infoLength);
        if (this.qualifier != -1) messageLen++;
        if (this.dateTime != null) {
            messageLen += 7;
        }
        builder.append(String.format("%02X", messageLen));
        builder.append(con);
        builder.append(String.format("%02X", this.TI));
        builder.append(String.format("%02X", (this.SQ << 7) + this.infos.size()==0?1:this.infos.size()));
        builder.append( Util.getAddressStr(this.transferReason));
        builder.append(Util.getAddressStr(this.asduAddress));
        if (this.infos.size() == 0) {
            builder.append(Util.getAddressStr3(this.info_adds.get(0)));
        } else {
            for (int i = 0; i < info_adds.size(); i++) {
                builder.append(Util.getAddressStr3(this.info_adds.get(i)));
                builder.append(Util.getInfoStr(fun.apply(this.infos.get(i)), infoLength));
            }
        }
        if (qualifier != -1) builder.append(String.format("%02X", qualifier));
        if (dateTime != null) builder.append(Util.date2HStr(this.dateTime));
        return builder.toString();
    }


}
