package com.iec.analysis.protocol104;

import com.iec.utils.Util;

/**
 * 遥测信息解析
 * 
 * @author 张雨 2018.01.12
 * 
 */
public class Telesignalling104 {
	/**
	 * 测量值，归一化值
	 * 
	 * @param infoElement
	 * @param num
	 * @param tI
	 * @param sQ
	 * @return
	 */
	public String normalization(int[] infoElement, int num, int tI, int sQ) {
		StringBuilder builder = new StringBuilder();
		if (sQ == 1) {

			builder.append("遥测信息对象地址");
			builder.append(new ASDU().InfoAddress(infoElement[0], infoElement[1],
					infoElement[2]));
			builder.append("\n");
			for (int i = 0; i < num; i++) {
				builder.append("信息对象");
				builder.append(i + 1);
				builder.append("归一化值NVA:");
				builder.append(Util.toHexString(infoElement[i * 3 + 3]));
				builder.append("\t");
				builder.append(Util.toHexString(infoElement[i * 3 + 4]));
				builder.append("\n");
				builder.append("品质描述词QDS:");
				builder.append(Util.toHexString(infoElement[i * 3 + 5]));
				builder.append("\n");
			}
		} else {
			for (int i = 0; i < num; i++) {
				builder.append("信息对象");
				builder.append((i + 1));
				builder.append("的地址：");
				builder.append(new ASDU().InfoAddress(infoElement[i * 6],
						infoElement[i * 6 + 1], infoElement[i * 6 + 2]));
				builder.append("\n");
				builder.append("归一化值NVA:");
				builder.append(Util.toHexString(infoElement[i * 6 + 3]));
				builder.append("\t");
				builder.append(Util.toHexString(infoElement[i * 6 + 4]));
				builder.append("\n");
				builder.append("品质描述词QDS:");
				builder.append(Util.toHexString(infoElement[i * 6 + 5]));
				builder.append("\n");
			}
		}

		return builder.toString();
	}

	/**
	 * 测量值，标度化值
	 * 
	 * @param infoElement
	 * @param num
	 * @param tI
	 * @param sQ
	 * @return
	 */
	public String standardization(int[] infoElement, int num, int tI, int sQ) {
		String string = "";

		return string;
	}

	/**
	 * 测量值，短浮点数
	 * 
	 * @param infoElement
	 * @param num
	 * @param tI
	 * @param sQ
	 * @return
	 */
	public String short_float(int[] infoElement, int num, int tI, int sQ) {
		StringBuilder builder = new StringBuilder();
		if (sQ == 1) {

			builder.append("遥测信息对象地址");
			builder.append(new ASDU().InfoAddress(infoElement[0], infoElement[1],
					infoElement[2])).append("\n");
			for (int i = 0; i < num; i++) {
				builder.append("遥测");
				builder.append((i + 1));
				builder.append("IEEE STD745短浮点数:");
				builder.append(Util.toHexString(infoElement[i * 5 + 3]));
				builder.append("\t");
				builder.append(Util.toHexString(infoElement[i * 5 + 4]));
				builder.append("\t");
				builder.append(Util.toHexString(infoElement[i * 5 + 5]));
				builder.append("\t");
				builder.append(Util.toHexString(infoElement[i * 5 + 6]));
				builder.append("\n");
				builder.append("品质描述词QDS:");
				builder.append(Util.toHexString(infoElement[i * 5 + 7]));
				builder.append("\n");
			}
		} else {
			for (int i = 0; i < num; i++) {
				builder.append("信息对象");
				builder.append((i + 1));
				builder.append("的地址：");
				builder.append(new ASDU().InfoAddress(infoElement[i * 8],
						infoElement[i * 8 + 1], infoElement[i * 8 + 2]));
				builder.append("\n");
				builder.append("IEEE STD745短浮点数:");
				builder.append(Util.toHexString(infoElement[i * 8 + 3]));
				builder.append("\t");
				builder.append(Util.toHexString(infoElement[i * 8 + 4]));
				builder.append("\t");
				builder.append(Util.toHexString(infoElement[i * 8 + 5]));
				builder.append("\t");
				builder.append(Util.toHexString(infoElement[i * 8 + 6]));
				builder.append("\t");

				builder.append("\n");
				builder.append("品质描述词QDS:");
				builder.append(Util.toHexString(infoElement[i * 8 + 7])).append("\n");
			}
		}

		return builder.toString();
	}

}
