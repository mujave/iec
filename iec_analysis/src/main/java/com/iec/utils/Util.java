package com.iec.utils;


import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 工具类
 *
 * @author Administrator 2018-04-07
 */

public class Util {
    /**
     * 字节数组转换成String，指定长度转换长度
     *
     * @param arrBytes
     * @param count    转换长度
     * @param blank    要不要空格（每个byte字节，最是否用一个“ ”隔开）
     * @return "" | arrBytes换成的字符串（不存在null）
     */
    public static String byteArray2HexString(byte[] arrBytes, int count, boolean blank) {
        String ret = "";
        if (arrBytes == null || arrBytes.length < 1)
            return ret;
        if (count > arrBytes.length)
            count = arrBytes.length;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < count; i++) {
            ret = Integer.toHexString(arrBytes[i] & 0xFF).toUpperCase();
            if (ret.length() == 1)
                builder.append("0").append(ret);
            else
                builder.append(ret);
            if (blank)
                builder.append(" ");
        }

        return builder.toString();

    }

    /**
     * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
     *
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param array 需要转换的字符串(字节间没有分隔符)
     * @return 转换完成的字符串
     */
    public static String byteArrayToHexString(byte[] array) {
        return byteArray2HexString(array, Integer.MAX_VALUE, false);
    }

    /**
     * 时标CP56Time2a解析
     *
     * @param b 时标CP56Time2a（长度为7 的int数组）
     * @return 解析结果
     */
    public static String TimeScale(int b[]) {

        String str = "";
        int year = b[6] & 0x7F;
        int month = b[5] & 0x0F;
        int day = b[4] & 0x1F;
        int week = (b[4] & 0xE0) / 32;
        int hour = b[3] & 0x1F;
        int minute = b[2] & 0x3F;
        int second = (b[1] << 8) + b[0];

        str += "时标CP56Time2a:" + "20" + year + "-"
                + String.format("%02d", month) + "-"
                + String.format("%02d", day) + "," + hour + ":" + minute + ":"
                + second / 1000 + "." + second % 1000;
        return str + "\n";
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static int[] hexStringToIntArray(String s) {
        int len = s.length();
        int[] b = new int[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (int) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }

    /**
     * int转换成16进制字符串
     *
     * @param b 需要转换的int值
     * @return 16进制的String
     */
    public static String toHexString(int b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return "0x" + hex.toUpperCase();
    }

    /**
     * 检验CS校验和
     *
     * @param VCS 需要检验的部分
     * @param i   CS校验和
     * @return 检验结果（字符串格式）
     */
    public static boolean variableCS(int[] VCS, int i) {

        int sum = 0;
        for (int j = 0; j < VCS.length; j++) {
            sum += VCS[j];
        }
        if ((sum % 256) == i) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解析地址域
     *
     * @param low  第一个地址
     * @param high 第二个地址
     * @return
     */
    public static String address(int low, int high) {

        String lowString = String.format("%02X", low);
        String highString = String.format("%02X", high);

        return highString + lowString + "H" + "\n";
    }

    public static String getAddressStr(int address) {
        String addressStr = String.format("%04X", address);
        return addressStr.substring(2, 4) + addressStr.substring(0, 2);
    }

    public static String getAddressStr3(int address) {
        String addressStr = String.format("%06X", address);
        return addressStr.substring(4, 6) + addressStr.substring(2, 4) + addressStr.substring(0, 2);
    }

    public static String getInfoStr(int info, int length) {
        StringBuilder builder = new StringBuilder();
        String infoFormat = "%0" + 2 * length + "X";
        String infoStr = String.format(infoFormat, info);
        for (int i = infoStr.length() / 2; i > 0; i--) {
            builder.append(infoStr.substring(2 * i - 2, 2 * i));
        }
        return builder.toString();
    }

    public static String date2HStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        StringBuilder builder = new StringBuilder();
        String milliSecond = String.format("%04X", (calendar.get(Calendar.SECOND) * 1000) + calendar.get(Calendar.MILLISECOND));
        builder.append(milliSecond.substring(2, 4));
        builder.append(milliSecond.substring(0, 2));
        builder.append(String.format("%02X", calendar.get(Calendar.MINUTE) & 0x3F));
        builder.append(String.format("%02X", calendar.get(Calendar.HOUR_OF_DAY) & 0x1F));
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (week == Calendar.SUNDAY)
            week = 7;
        else week--;
        builder.append(String.format("%02X", (week << 5) + (calendar.get(Calendar.DAY_OF_MONTH) & 0x1F)));
        builder.append(String.format("%02X", calendar.get(Calendar.MONTH) + 1));
        builder.append(String.format("%02X", calendar.get(Calendar.YEAR) - 2000));
        return builder.toString();
    }

    public static long add2long(int address) {
        int addressLong = ((address & 0xff00) >> 8) + (address & 0x00ff);
        return addressLong % 256;
    }

    public static long CP56Time2Long(Date dateTime) {
        long sum = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        StringBuilder builder = new StringBuilder();
        long milliSecond = (calendar.get(Calendar.SECOND) * 1000) + calendar.get(Calendar.MILLISECOND);
        sum += ((milliSecond & 0xff00) >> 8) % 256;
        sum += (milliSecond & 0x00ff) % 256;
        sum += (calendar.get(Calendar.MINUTE) & 0x3F) % 256;
        sum += (calendar.get(Calendar.HOUR_OF_DAY) & 0x1F) % 256;

        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (week == Calendar.SUNDAY)
            week = 7;
        else week--;
        sum += ((week << 5) + (calendar.get(Calendar.DAY_OF_MONTH) & 0x1F)) % 256;
        sum += (calendar.get(Calendar.MONTH) + 1) % 256;
        sum += (calendar.get(Calendar.YEAR) - 2000) % 256;
        return sum;
    }

    /**
     * 获取I格式的104报文的控制域
     */
    public static String getInformationTransmitFormat(int sendNumber, int receiveNnumber) {
        sendNumber = sendNumber << 1;
        receiveNnumber = receiveNnumber << 1;
        String s = String.format("%04X", sendNumber);
        String r = String.format("%04X", receiveNnumber);

        return s.substring(2, 4) + s.substring(0, 2) + r.substring(2, 4) + r.substring(0, 2);
    }

    /**
     * 获取S格式的104报文的控制域
     */
    public static String getNumberedSupervisoryFunction(int receiveNnumber) {
        receiveNnumber = receiveNnumber << 1;
        String r = String.format("%04X", receiveNnumber);

        return "01" + "00" + r.substring(2, 4) + r.substring(0, 2);
    }

    /**
     * 获取U格式的104报文的控制域
     */
    public static String getUnnumberedControlFunction(boolean tester, boolean stopdt, boolean startdt) {
        int con = 3;
        con += (tester ? 2 : 1) << 6;
        con += (stopdt ? 2 : 1) << 4;
        con += (startdt ? 2 : 1) << 2;
        return String.format("%02X", con) + "000000";
    }
}
