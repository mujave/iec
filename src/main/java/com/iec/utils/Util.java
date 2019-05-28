package com.iec.utils;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 *
 * @author Administrator 2018-04-07
 */

public class Util {

    /**
     * 获取当前日期 : "yyyy-MM-dd HH:mm:ss"
     *
     * @return "yyyy-MM-dd HH:mm:ss"字符串
     */
    public static String formatDateStr_ss() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 字符串是否为空
     * <p>
     * 如果这个字符串为null或者trim后为空字符串则返回true，否则返回false。
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim()))
            return true;
        return false;
    }

    /**
     * 用来把mac字符串转换为long
     *
     * @param strMac
     * @return
     */
    public static long macToLong(String strMac) {
        byte[] mb = new BigInteger(strMac, 16).toByteArray();
        ByteBuffer mD = ByteBuffer.allocate(mb.length);
        mD.put(mb);
        long mac = 0;
        // 如果长度等于8代表没有补0;
        if (mD.array().length == 8) {
            mac = mD.getLong(0);
        } else if (mD.array().length == 9) {
            mac = mD.getLong(1);
        }
        return mac;
    }

    public static byte[] getBytes(Object obj) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bout.toByteArray();
        bout.close();
        out.close();

        return bytes;
    }


    public static Object getObject(byte[] bytes) throws IOException,
            ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        Object obj = oi.readObject();
        bi.close();
        oi.close();
        return obj;
    }

    public static ByteBuffer getByteBuffer(Object obj) throws IOException {
        byte[] bytes = Util.getBytes(obj);
        ByteBuffer buff = ByteBuffer.wrap(bytes);

        return buff;
    }

    /**
     * byte[] 转short 2字节
     *
     * @param bytes
     * @return
     */
    public static short bytesToshort(byte[] bytes) {
        return (short) ((bytes[0] & 0xff) | ((bytes[1] << 8) & 0xff00));

    }

    /**
     * byte 转Int
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        return (b) & 0xff;
    }

    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[0] & 0xFF;
        addr |= ((bytes[1] << 8) & 0xFF00);
        addr |= ((bytes[2] << 16) & 0xFF0000);
        addr |= ((bytes[3] << 24) & 0xFF000000);
        return addr;
    }

    public static byte[] intToByte(int i) {

        byte[] abyte0 = new byte[4];
        abyte0[0] = (byte) (0xff & i);
        abyte0[1] = (byte) ((0xff00 & i) >> 8);
        abyte0[2] = (byte) ((0xff0000 & i) >> 16);
        abyte0[3] = (byte) ((0xff000000 & i) >> 24);
        return abyte0;

    }

    public static byte[] LongToByte(Long i) {

        byte[] abyte0 = new byte[8];
        abyte0[0] = (byte) (0xff & i);
        abyte0[1] = (byte) ((0xff00 & i) >> 8);
        abyte0[2] = (byte) ((0xff0000 & i) >> 16);
        abyte0[3] = (byte) ((0xff000000 & i) >> 24);
        abyte0[4] = (byte) ((0xff00000000l & i) >> 32);
        abyte0[5] = (byte) ((0xff0000000000l & i) >> 40);
        abyte0[6] = (byte) ((0xff000000000000l & i) >> 48);
        abyte0[7] = (byte) ((0xff00000000000000l & i) >> 56);
        return abyte0;

    }

    /**
     * short 大端转小端
     *
     * @param mshort
     */
    public static short shortChange(Short mshort) {

        mshort = (short) ((mshort >> 8 & 0xFF) | (mshort << 8 & 0xFF00));

        return mshort;
    }

    /**
     * int 大端转小端
     *
     * @param mint
     */
    public static int intChange(int mint) {

        mint = (int) (((mint) >> 24 & 0xFF) | ((mint) >> 8 & 0xFF00)
                | ((mint) << 8 & 0xFF0000) | ((mint) << 24 & 0xFF000000));

        return mint;
    }

    /**
     * LONG 大端转小端
     *
     * @param mlong
     */
    public static long longChange(long mlong) {

        mlong = (long) (((mlong) >> 56 & 0xFF) | ((mlong) >> 48 & 0xFF00)
                | ((mlong) >> 24 & 0xFF0000) | ((mlong) >> 8 & 0xFF000000)
                | ((mlong) << 8 & 0xFF00000000l)
                | ((mlong) << 24 & 0xFF0000000000l)
                | ((mlong) << 40 & 0xFF000000000000l) | ((mlong) << 56 & 0xFF00000000000000l));

        return mlong;
    }

    /**
     * 将byte转换为无符号的short类型
     *
     * @param b 需要转换的字节数
     * @return 转换完成的short
     */
    public static short byteToUshort(byte b) {
        return (short) (b & 0x00ff);
    }

    /**
     * 将byte转换为无符号的int类型
     *
     * @param b 需要转换的字节数
     * @return 转换完成的int
     */
    public static int byteToUint(byte b) {
        return b & 0x00ff;
    }

    /**
     * 将byte转换为无符号的long类型
     *
     * @param b 需要转换的字节数
     * @return 转换完成的long
     */
    public static long byteToUlong(byte b) {
        return b & 0x00ff;
    }

    /**
     * 将short转换为无符号的int类型
     *
     * @param s 需要转换的short
     * @return 转换完成的int
     */
    public static int shortToUint(short s) {
        return s & 0x00ffff;
    }

    /**
     * 将short转换为无符号的long类型
     *
     * @param s 需要转换的字节数
     * @return 转换完成的long
     */
    public static long shortToUlong(short s) {
        return s & 0x00ffff;
    }

    /**
     * 将int转换为无符号的long类型
     *
     * @param i 需要转换的字节数
     * @return 转换完成的long
     */
    public static long intToUlong(int i) {
        return i & 0x00ffffffff;
    }

    /**
     * 将short转换成小端序的byte数组
     *
     * @param s 需要转换的short
     * @return 转换完成的byte数组
     */
    public static byte[] shortToLittleEndianByteArray(short s) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
                .putShort(s).array();
    }

    /**
     * 将int转换成小端序的byte数组
     *
     * @param i 需要转换的int
     * @return 转换完成的byte数组
     */
    public static byte[] intToLittleEndianByteArray(int i) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i)
                .array();
    }

    /**
     * 将long转换成小端序的byte数组
     *
     * @param l 需要转换的long
     * @return 转换完成的byte数组
     */
    public static byte[] longToLittleEndianByteArray(long l) {
        return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(l)
                .array();
    }

    /**
     * 将short转换成大端序的byte数组
     *
     * @param s 需要转换的short
     * @return 转换完成的byte数组
     */
    public static byte[] shortToBigEndianByteArray(short s) {
        return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(s)
                .array();
    }

    /**
     * 将int转换成大端序的byte数组
     *
     * @param i 需要转换的int
     * @return 转换完成的byte数组
     */
    public static byte[] intToBigEndianByteArray(int i) {
        return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putInt(i)
                .array();
    }

    /**
     * 将long转换成大端序的byte数组
     *
     * @param l 需要转换的long
     * @return 转换完成的byte数组
     */
    public static byte[] longToBigEndianByteArray(long l) {
        return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putLong(l)
                .array();
    }

    /**
     * 将short转换为16进制字符串
     *
     * @param s              需要转换的short
     * @param isLittleEndian 是否是小端序（true为小端序false为大端序）
     * @return 转换后的字符串
     */
    public static String shortToHexString(short s, boolean isLittleEndian) {
        byte byteArray[] = null;
        if (isLittleEndian) {
            byteArray = shortToLittleEndianByteArray(s);
        } else {
            byteArray = shortToBigEndianByteArray(s);
        }
        return byteArrayToHexString(byteArray);
    }

    /**
     * 将int转换为16进制字符串
     *
     * @param i              需要转换的int
     * @param isLittleEndian 是否是小端序（true为小端序false为大端序）
     * @return 转换后的字符串
     */
    public static String intToHexString(int i, boolean isLittleEndian) {
        byte byteArray[] = null;
        if (isLittleEndian) {
            byteArray = intToLittleEndianByteArray(i);
        } else {
            byteArray = intToBigEndianByteArray(i);
        }
        return byteArrayToHexString(byteArray);
    }

    /**
     * 将long转换为16进制字符串
     *
     * @param l              需要转换的long
     * @param isLittleEndian 是否是小端序（true为小端序false为大端序）
     * @return 转换后的字符串
     */
    public static String longToHexString(long l, boolean isLittleEndian) {
        byte byteArray[] = null;
        if (isLittleEndian) {
            byteArray = longToLittleEndianByteArray(l);
        } else {
            byteArray = longToBigEndianByteArray(l);
        }
        return byteArrayToHexString(byteArray);
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param array
     *            需要转换的字符串
     * @param toPrint
     *            是否为了打印输出，如果为true则会每4自己添加一个空格
     * @return 转换完成的字符串
     */
//	public static String byteArrayToHexString(byte[] array, boolean toPrint) {
//		if (array == null) {
//			return "null";
//		}
//		StringBuffer sb = new StringBuffer();
//
//		for (int i = 0; i < array.length; i++) {
//			sb.append(byteToHex(array[i]));
//			if (toPrint && (i + 1) % 4 == 0) {
//				sb.append(" ");
//			}
//		}
//		return sb.toString();
//	}


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
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
     *
     * @param src String
     * @return null | byte[]
     */
    public static byte[] HexString2Bytes(String src) {
        // String strTemp = "";
        if (src == null || "".equals(src))
            return null;
        StringBuilder builder = new StringBuilder();
        for (char c : src.trim().toCharArray()) {
            /* 去除中间的空格,去除数字,a-z,A-Z的所有字符 */
            if (c != ' ' && (((byte) c > 47 && (byte) c < 58) || ((byte) c > 64 && (byte) c < 71) || ((byte) c > 96 && (byte) c < 103))) {
                builder.append(c);
            }
        }
        src = builder.toString();
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < src.length() / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> int[]{0x2B, 0x44, 0xEF, 0xD9}
     *
     * @param src String
     * @return null | int[]
     */
    public static int[] HexString2Ints(String src) {
        // String strTemp = "";
        if (src == null || "".equals(src))
            return null;
        StringBuilder builder = new StringBuilder();
        for (char c : src.trim().toCharArray()) {
            /* 去除中间的空格,去除数字,a-z,A-Z的所有字符 */
            if (c != ' ' && (((byte) c > 47 && (byte) c < 58) || ((byte) c > 64 && (byte) c < 71) || ((byte) c > 96 && (byte) c < 103))) {
                builder.append(c);
            }
        }
        src = builder.toString();
        int[] ret = new int[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).intValue();
        }
        return ret;
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
     * 将字节数组转换成long类型
     *
     * @param bytes 字节数据
     * @return long类型
     */
    public static long byteArrayToLong(byte[] bytes) {
        return ((((long) bytes[0] & 0xff) << 24)
                | (((long) bytes[1] & 0xff) << 16)
                | (((long) bytes[2] & 0xff) << 8) | (((long) bytes[3] & 0xff) << 0));
    }

    /**
     * 合并数组
     *
     * @param firstArray  第一个数组
     * @param secondArray 第二个数组
     * @return 合并后的数组
     */
    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        if (firstArray == null || secondArray == null) {
            if (firstArray != null)
                return firstArray;
            if (secondArray != null)
                return secondArray;
            return null;
        }
        byte[] bytes = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
        System.arraycopy(secondArray, 0, bytes, firstArray.length,
                secondArray.length);
        return bytes;
    }

    /**
     * 格式化Date
     *
     * @param date
     * @return yyyy-mm-dd hh:mm:ss
     */
    public static String date2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串转int值
     *
     * @param src
     * @return
     */
    public static int String2int(String src) {
        // String strTemp = "";
        if (src == null || "".equals(src))
            return 0;
        StringBuilder builder = new StringBuilder();
        for (char c : src.trim().toCharArray()) {
            /* 去除中间的空格 */
            if (c != ' ' && ((byte) c > 47 && (byte) c < 58)) {
                builder.append(c);
            }
        }
        src = builder.toString();

        return Integer.valueOf(src);
    }

    /**
     * MD5加密字符串
     *
     * @param str
     * @return
     */
    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * int值转1位小数的float值
     *
     * @param i
     * @return
     */
    public static float int2float(int i) {
        return Math.round(i * 10) / 10;
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
     * @param vCS 需要检验的部分
     * @param i   CS校验和
     * @return 检验结果（字符串格式）
     */
    public static String variableCS(int[] vCS, int i) {
        String str = "";
        str += "校验和CS=";
        str += toHexString(i);
        int sum = 0;
        for (int j = 0; j < vCS.length; j++) {
            sum += vCS[j];
        }
        if ((sum % 256) == i) {
            str += "   校验无误！";

        } else {
            str += "   经校验，报文有误！";
        }
        return str;

    }

    /**
     * 解析地址域
     *
     * @param low 第一个地址
     * @param high 第二个地址
     * @return
     */
    public static String address(int low, int high) {

        String lowString = String.format("%02X", low);
        String highString = String.format("%02X", high);

        return highString + lowString + "H" + "\n";
    }
}
