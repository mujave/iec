package com.iec.test;

import com.iec.analysis.exception.*;
import com.iec.analysis.protocol101.Analysis;
import com.iec.assemble101.UnContinuousAddressBuilder;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Analysis101Test {


    @Test
    public void FixedLengthFrameAnalysisTest() {

        String analysis = null;

        try {
            analysis = Analysis.analysis("10 49 01 00 49 16".replaceAll(" ", ""));
            System.out.println(analysis);
        } catch (LengthException e) {
            System.out.println(e.toString());
        } catch (UnknownTransferReasonException e) {
            System.out.println(e.toString());
        } catch (UnknownLinkCodeException e) {
            System.out.println(e.toString());
        } catch (CustomException e) {
            System.out.println(e.toString());
        } catch (UnknownTypeIdentifierException e) {
            System.out.println(e.toString());
        } catch (IllegalFormatException e) {
            System.out.println(e.toString());
        } catch (CsCheckException e) {
            System.out.println(e.toString());
        }

    }


    @Test
    public void VariableLengthFrameParsingTest() {
        String analysis = null;
        try {
            analysis = Analysis.analysis("68  0f  0f  68  53  01  00  88  01  06  01  00  03  50  58  D4  44  40  80  66  16".replaceAll(" ", ""));
            System.out.println(analysis);
        } catch (LengthException e) {
            System.out.println(e.toString());
        } catch (CustomException e) {
            System.out.println(e.toString());
        } catch (UnknownLinkCodeException e) {
            System.out.println(e.toString());
        } catch (UnknownTransferReasonException e) {
            System.out.println(e.toString());
        } catch (UnknownTypeIdentifierException e) {
            System.out.println(e.toString());
        } catch (IllegalFormatException e) {
            System.out.println(e.toString());
        } catch (CsCheckException e) {
            System.out.println(e.toString());
        }

    }

    @Test
    public void buliderTest1() {
        /**
         * 描述：这里测试变长帧中信息体地址不连续的时候的报文，并且第一个信息体元素值为空。
         * 报文示例：召唤命令（具体报文结构请参照附件1：章节5.1.1 Page23）
         *          报文选自附件1：章节8.4 第三部分 Page83
         *
         * 注明：这里采用这条报文作为测试实例是因为这条报文是一条变长的、SQ(信息体元素地
         *      址是否连续标志)为0、并且信息体元素没有值。
         *      也就是说报文有一个信息体，且只有信息体元素地址，没有值，且在校验位之前有召
         *      唤限定词(0x14)。
         *
         * 适用范围：总召唤过程，时钟同步过程，复位进程，初始化进程。
         */
        /**
         * 报文简单解析：
         * 68  0B  0B  68  73  01  00  64  01  06  01  00  00  00  14  F4  16
         * 73  控制域  115
         * 01 00 地址域  1
         * 64 TI 100
         * 01 sq=0 信息体个数1
         * 06 传送原因 6
         * 01 00 asdu地址
         * 00 00 第一个信息体的地址
         * 14 召唤限定词 20
         * F4 校验位
         */
        UnContinuousAddressBuilder<Integer> unContinuousAddressBuilder = new UnContinuousAddressBuilder<Integer>(115, 1, 100, 1, 6, 0);
        unContinuousAddressBuilder.addInfoAddress(0);//添加第一个信息体的地址
        unContinuousAddressBuilder.builderQualifier(20);//添加召唤限定词
        String s = unContinuousAddressBuilder.builder();
        System.out.println(s);
        System.out.println("68  0B  0B  68  73  01  00  64  01  06  01  00  00  00  14  F4  16".replaceAll(" ", "").equals(s));
    }

    @Test
    public void buliderTest2() throws ParseException {
        /**
         * 描述：这里测试边长帧中信息体地址不连续的时候的报文，并且附带一个时标的实例。
         * 报文示例：带CP56Time2a时标的单点信息（具体报文结构请参照附件1：章节5.2 b) Page28）
         *
         * 注明：这里采用这条报文作为测试实例是因为这条报文是一条变长的、SQ(信息体元素地
         *      址是否连续标志)为0、有一个完整的信息体，并且附带一个时标。
         *
         */
        /**
         * 68  12  12  68  d3  01  00  1e  01  03  01  00  21  00  01  9a  e5  19  0a  83  03  10  51  16
         * 报文长度24字节
         * d3 控制域 211
         * 01 00 地址域 1
         * 1e TI 30
         * 01 可变结构限定词 sq=0；信息体个数为1
         * 03 传输原因COT 3
         * 01 00  ASDU地址 1
         * 21 00  第一个信息体的地址 21
         * 1 第一个信息体的值 1
         * 9a e5 19 0a 83 03 10  CP56Time2a时标 2016-03-03,10:25:58.778
         *
         */
        UnContinuousAddressBuilder<Integer> unContinuousAddressBuilder = new UnContinuousAddressBuilder<>(211, 1, 30, 1, 3, 1);
        unContinuousAddressBuilder.addInfo(33, 1);
        unContinuousAddressBuilder.builderDateTime(new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss.SSS").parse("2016-03-03,10:25:58.778"));
        String builder = unContinuousAddressBuilder.builder();
        System.out.println(builder.toString());
        UnContinuousAddressBuilder<Integer> unContinuousAddressBuilder2 = new UnContinuousAddressBuilder<>(211, 1, 30, 1, 3, 3);
        unContinuousAddressBuilder2.addInfo(33, 12);
        unContinuousAddressBuilder2.builderDateTime(new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss.SSS").parse("2016-03-03,10:25:58.778"));
        String builder2 = unContinuousAddressBuilder2.builder();
        System.out.println(builder2.toString());

    }


}
