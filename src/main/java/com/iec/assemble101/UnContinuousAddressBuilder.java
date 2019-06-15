package com.iec.assemble101;

import com.iec.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;

/**
 * 信息体元素地址不连续的报文建造者。
 *
 * @param <T> 信息体的类型，
 */
public class UnContinuousAddressBuilder<T> extends VariableLengthPacket {


    /**
     * 信息体元素地址。
     */
    private ArrayList<Integer> informationAdress;
    /**
     * 信息体元素。
     * 包含多个元素
     */
    private ArrayList<T> informosomes;
    /**
     * 单个信息体的字节长度。默认为2
     */
    private int infoLength;

    @Deprecated
    public UnContinuousAddressBuilder() {
        this(9, 0, 1, 0, 9, 2);//地址连续的报文
    }

    /**
     * 信息体元素地址不连续的报文
     *
     * @param con            控制域
     * @param address        地址
     * @param TI             类型标识TI(推荐使用TypeIdentifier类的code)
     * @param asduAddress    应用数据单元公共地址
     * @param transferReason 传送原因(推荐使用TransferReason类的code)
     * @param infoLength     信息体元素值的字节长度
     */
    public UnContinuousAddressBuilder(int con, int address, int TI, int asduAddress, int transferReason, int infoLength) {
        this(con, address, TI, asduAddress, transferReason, infoLength, null, -1);
    }

    private UnContinuousAddressBuilder(int con, int address, int TI, int asduAddress, int transferReason, int infoLength, Date dateTime, int qualifier) {
        super(con, address, TI, 0, asduAddress, transferReason);
        this.dateTime = dateTime;
        this.infoLength = infoLength;
        this.qualifier = qualifier;
        this.informosomes = new ArrayList<>();
        this.informationAdress = new ArrayList<Integer>();
    }

    public UnContinuousAddressBuilder builderCon(int con) {
        this.con = con;
        return this;
    }

    public UnContinuousAddressBuilder builderAddress(int address) {
        this.address = address;
        return this;
    }

    public UnContinuousAddressBuilder builderTi(int ti) {
        this.TI = ti;
        return this;
    }

    public UnContinuousAddressBuilder builderAsduAdress(int asduAdsress) {
        this.asduAddress = asduAdsress;
        return this;
    }

    public UnContinuousAddressBuilder builderTransferReason(int transferReason) {
        this.transferReason = transferReason;
        return this;
    }

    public UnContinuousAddressBuilder addInfoAddress(int informationAdress) {
        this.informationAdress.add(informationAdress);
        return this;
    }

    public UnContinuousAddressBuilder addInfo(int informationAdress, T informosome) {
        this.informationAdress.add(informationAdress);
        this.informosomes.add(informosome);
        return this;
    }

    public UnContinuousAddressBuilder builderQualifier(int qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public UnContinuousAddressBuilder builderDateTime(Date date) {
        this.dateTime = date;
        return this;
    }

    @Override
    public String builder() {
        return this.builder(x -> Integer.parseInt(x.toString()));
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
        builder.append(String.format("%02X", (this.SQ << 7) + this.informationAdress.size()));
        builder.append(String.format("%02X", this.transferReason));
        builder.append(Util.getAddressStr(asduAddress));
        for (int i = 0; i < informationAdress.size(); i++) {
            builder.append(Util.getAddressStr(this.informationAdress.get(i)));
            if (this.informosomes.size() > i)
                builder.append(Util.getInfoStr(fun.apply(this.informosomes.get(i)), infoLength));
        }
        if (qualifier != -1) builder.append(String.format("%02X", qualifier));
        if (dateTime != null) builder.append(Util.date2HStr(this.dateTime));
        long sum = 0;
        sum += this.con % 256;
        sum += Util.add2long(this.address) % 256;
        sum += this.TI % 256;
        sum += (((this.SQ << 7) + this.informationAdress.size()) % 256);
        sum += this.transferReason % 256;
        sum += Util.add2long(this.asduAddress) % 256;
        // TODO: 2019-05-31 双字节地址的校验和
        for (int i = 0; i < informationAdress.size(); i++) {
            sum += Util.add2long(this.informationAdress.get(i)) % 256;
            if (this.informosomes.size() > i)
                sum += fun.apply(this.informosomes.get(i)) % 256;
        }
        if (this.dateTime != null) sum += Util.CP56Time2Long(this.dateTime) % 256;
        if (this.qualifier != -1) sum += this.qualifier % 256;
        builder.append(String.format("%02X", sum % 256));
        builder.append("16");
        return builder.toString();
    }
}
