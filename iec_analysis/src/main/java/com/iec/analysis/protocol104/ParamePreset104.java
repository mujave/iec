package com.iec.analysis.protocol104;

import com.iec.utils.Util;

/**
 * @author 张雨
 * 
 */
public class ParamePreset104 {
	/**
	 * 读单个参数命令(参数设置)
	 * 
	 * @param infoElement
	 * @param num
	 * @param tI
	 * @param sQ
	 * @return
	 */
	public String single_parmeter(int[] infoElement, int num, int tI, int sQ) {
		String string = "";

		string += "信息对象地址：";
		string += new ASDU().InfoAddress(infoElement[0], infoElement[1],
				infoElement[2]);
		string += "\n";
		string += "参数设置对象信息值：";
		string +=Util.toHexString(infoElement[3]);
		string += "  ";
		string += Util.toHexString(infoElement[4]);
		string += "  ";
		string += Util.toHexString(infoElement[5]);
		string += "  ";
		string += Util.toHexString(infoElement[6]);
		string += "\n";

		return string;
	}

	/**
	 * 读多个参数命令(参数设置)
	 * 
	 * @param infoElement
	 * @param num
	 * @param tI
	 * @param sQ
	 * @return
	 */
	public String multi_parmeter(int[] infoElement, int num, int tI, int sQ) {
		String string = "";
		for (int i = 0; i < num; i++) {
			string += "信息对象";
			string += (i + 1);
			string += "地址：";
			string += new ASDU().InfoAddress(infoElement[i * 7],
					infoElement[i * 7 + 1], infoElement[i * 7 + 2]);
			string += "\n";
			string += "参数设置对象信息值：";
			string += Util.toHexString(infoElement[i * 7 + 3]);
			string += "  ";
			string += Util.toHexString(infoElement[i * 7 + 4]);
			string += "  ";
			string += Util.toHexString(infoElement[i * 7 + 5]);
			string += "  ";
			string += Util.toHexString(infoElement[i * 7 + 6]);
			string += "\n";
		}
		return string;
	}

	/**
	 * 预置/激活多个参数命令(参数设置)
	 * 
	 * @param infoElement
	 * @param num
	 * @param tI
	 * @param sQ
	 * @return
	 */
	public String activate_multi_parmeter(int[] infoElement, int num, int tI,
			int sQ) {
		String string = "";
		for (int i = 0; i < num; i++) {
			string += "信息对象";
			string += (i + 1);
			string += "地址：";
			string += new ASDU().InfoAddress(infoElement[i * 7],
					infoElement[i * 7 + 1], infoElement[i * 7 + 2]);
			string += "\n";
			string += "预置/激活参数命令对象信息值：";
			string += Util.toHexString(infoElement[i * 7 + 3]);
			string += "  ";
			string += Util.toHexString(infoElement[i * 7 + 4]);
			string += "  ";
			string += Util.toHexString(infoElement[i * 7 + 5]);
			string += "  ";
			string += Util.toHexString(infoElement[i * 7 + 6]);
			string += "\n";
		}
		string += "设定命令限定词QOS：";

		string += Util
				.toHexString(infoElement[infoElement.length - 1]);
		string += "  ";
		if (infoElement[infoElement.length - 1] == 0x80) {
			string += "预置参数";
		} else if (infoElement[infoElement.length - 1] == 0x00) {
			string += "激活参数";

		}
		string += "\n";
		return string;
	}

	/**
	 * 预置/激活单个参数命令(参数设置)
	 * 
	 * @param infoElement
	 * @param num
	 * @param tI
	 * @param sQ
	 * @return
	 */
	public String activate_single_parmeter(int[] infoElement, int num, int tI,
			int sQ) {
		String string = "";

		string += "信息对象地址：";
		string += new ASDU().InfoAddress(infoElement[0], infoElement[1],
				infoElement[2]);
		string += "\n";
		string += "预置/激活参数命令对象信息值：";
		string += Util.toHexString(infoElement[3]);
		string += "  ";
		string += Util.toHexString(infoElement[4]);
		string += "  ";
		string += Util.toHexString(infoElement[5]);
		string += "  ";
		string += Util.toHexString(infoElement[6]);
		string += "\n";
		string += "设定命令限定词QOS：";

		string += Util.toHexString(infoElement[7]);
		string += "  ";
		if (infoElement[7] == 0x80) {
			string += "预置参数";
		} else if (infoElement[7] == 0x00) {
			string += "激活参数";

		}
		string += "\n";
		return string;
	}

}
