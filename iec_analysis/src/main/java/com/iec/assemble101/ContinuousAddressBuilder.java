package com.iec.assemble101;

import com.iec.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;

/**
 * 地址连续的报文建造者
 *
 * @Author zhangyu
 * @create 2019/5/29 17:23
 */
public class ContinuousAddressBuilder<T> extends VariableLengthPacket {

    /**
     * 信息体元素地址。
     * 地址连续的报文只需提供第一个信息提的地址即可。
     * 不提供，默认有一个信息体，且地址为100，信息体元素值为0.
     */
    private int informationAdress;
    /**
     * 信息体元素。
     * 包含多个元素,只接受int、float、double类型数据
     */
    private ArrayList<T> informosomes;
    /**
     * 单个信息体的字节长度。默认为2
     */
    private int infoLength;

    @Deprecated
    public ContinuousAddressBuilder() {
        this(9, 100, 1, 100, 9, 100, 2);//地址连续的报文
    }

    public ContinuousAddressBuilder(int con, int address, int TI, int asduAddress, int transferReason, int informationAdress, int infoLength) {
        this(con, address, TI,  asduAddress, transferReason, informationAdress, infoLength, null, -1);
    }

    private ContinuousAddressBuilder(int con, int address, int TI,  int asduAddress, int transferReason, int informationAdress, int infoLength, Date dateTime, int qualifier) {
        super(con, address, TI, 1, asduAddress, transferReason);
        this.informationAdress = informationAdress;
        this.dateTime = dateTime;
        this.infoLength = infoLength;
        this.qualifier = qualifier;
        this.informosomes = new ArrayList<>();
    }

    public ContinuousAddressBuilder builderCon(int con) {
        this.con = con;
        return this;
    }

    public ContinuousAddressBuilder builderAdress(int adress) {
        this.address = adress;
        return this;
    }

    public ContinuousAddressBuilder builderTi(int ti) {
        this.TI = ti;
        return this;
    }

    public ContinuousAddressBuilder builderAsduAdress(int asduAdsress) {
        this.asduAddress = asduAdsress;
        return this;
    }

    public ContinuousAddressBuilder builderTransferReason(int transferReason) {
        this.transferReason = transferReason;
        return this;
    }

    public ContinuousAddressBuilder builderInformationAdress(int informationAdress) {
        this.informationAdress = informationAdress;
        return this;
    }

    public ContinuousAddressBuilder addInformosome(T informosome) {
        this.informosomes.add(informosome);
        return this;
    }

    public ContinuousAddressBuilder builderQualifier(int qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public ContinuousAddressBuilder builderDateTime(Date date) {
        this.dateTime = date;
        return this;
    }

    public String builder(Function<T, Integer> fun) {
        StringBuilder builder = new StringBuilder();
        builder.append("68");
        int length = 8;//控制域截至到应用服务单元地址的长度
        length += 2;//加上信息体地址的长度
        length += (informosomes.size() * infoLength);//加上信息体个数乘以信息体的单位长度
        if (qualifier != -1) length++; //有限定词（品质描述词）
        if (dateTime != null) length += 7;
        builder.append(String.format("%02X", length));
        builder.append(String.format("%02X", length));
        builder.append("68");
        builder.append(String.format("%02X", this.con));
        builder.append(Util.getAddressStr(this.address));
        builder.append(String.format("%02X", this.TI));
        builder.append(String.format("%02X", (this.SQ << 7) + this.informosomes.size()));
        builder.append(String.format("%02X", this.transferReason));
        builder.append(Util.getAddressStr(asduAddress));
        builder.append(Util.getAddressStr(informationAdress));
        for (T informosome : informosomes) {
            builder.append(Util.getInfoStr(fun.apply(informosome), infoLength));
        }
        if (qualifier != -1) builder.append(String.format("%02X", qualifier));
        if (dateTime != null) builder.append(Util.date2HStr(this.dateTime));//todo:加入时标
        //TODO：加入校验
        long sum = 0;
        sum += Util.add2long(this.address) % 0xFF;
        sum += this.TI % 0xFF;
        sum += ((this.SQ << 7) + this.informosomes.size()) % 0xFF;
        sum += this.transferReason % 0xFF;
        sum += Util.add2long(this.asduAddress) % 0xFF;
        sum += Util.add2long(this.informationAdress) & 0xFF;
        // TODO: 2019-05-31 双字节地址的校验和
        for (T informosome : informosomes) {
            sum += fun.apply(informosome) % 0xFF;
        }
        builder.append(String.format("%02X", sum % 0xFF));
        builder.append("16");
        return builder.toString();
    }

    public String builder() {
        //默认信息体元素值为int值
        return this.builder(x -> Integer.parseInt(x.toString()));
    }
}
