package com.iec.analysis.protocol101.InformationBody;


import com.iec.utils.Util;

/**
 * @author 张雨
 * 
 */
public class ParamePreset {
	private Util util = new Util();

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
		StringBuilder builder = new StringBuilder();

		builder.append("信息对象地址：");
		builder.append(util.address(infoElement[0], infoElement[1]));
		builder.append("参数设置对象信息值：");
		builder.append(util.toHexString(infoElement[2]));
		builder.append("  ");
		builder.append(util.toHexString(infoElement[3]));
		builder.append("  ");
		builder.append(util.toHexString(infoElement[4]));
		builder.append("  ");
		builder.append(util.toHexString(infoElement[5]));
		builder.append("\n");

		return builder.toString();
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
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < num; i++) {
			builder.append("信息对象");
			builder.append((i + 1));
			builder.append("地址：");
			builder.append(util.address(infoElement[i * 6], infoElement[i * 6 + 1]));
			builder.append("参数设置对象信息值：");
			builder.append(util.toHexString(infoElement[i * 6 + 2]));
			builder.append("  ");
			builder.append(util.toHexString(infoElement[i * 6 + 3]));
			builder.append("  ");
			builder.append(util.toHexString(infoElement[i * 6 + 4]));
			builder.append("  ");
			builder.append(util.toHexString(infoElement[i * 6 + 5]));
			builder.append("\n");
		}
		return builder.toString();
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
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < num; i++) {
			builder.append("信息对象");
			builder.append((i + 1));
			builder.append("地址：");
			builder.append(util.address(infoElement[i * 6], infoElement[i * 6 + 1]));
			builder.append("预置/激活参数命令对象信息值：");
			builder.append(util.toHexString(infoElement[i * 6 + 2]));
			builder.append("  ");
			builder.append(util.toHexString(infoElement[i * 6 + 3]));
			builder.append("  ");
			builder.append(util.toHexString(infoElement[i * 6 + 4]));
			builder.append("  ");
			builder.append(util.toHexString(infoElement[i * 6 + 5]));
			builder.append("\n");
		}
		builder.append("设定命令限定词QOS：");

		builder.append(util.toHexString(infoElement[6]));
		builder.append("  ");
		if (infoElement[6] == 0x80) {
			builder.append("预置参数");
		} else if (infoElement[6] == 0x00) {
			builder.append("激活参数");

		}
		builder.append("\n");
		return builder.toString();
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
		StringBuilder builder = new StringBuilder();

		builder.append("信息对象地址：");
		builder.append(util.address(infoElement[0], infoElement[1]));
		builder.append("预置/激活参数命令对象信息值：");
		builder.append(util.toHexString(infoElement[2]));
		builder.append("  ");
		builder.append(util.toHexString(infoElement[3]));
		builder.append("  ");
		builder.append(util.toHexString(infoElement[4]));
		builder.append("  ");
		builder.append(util.toHexString(infoElement[5]));
		builder.append("\n");
		builder.append("设定命令限定词QOS：");

		builder.append(util.toHexString(infoElement[6]));
		builder.append("  ");
		if (infoElement[6] == 0x80) {
			builder.append("预置参数");
		} else if (infoElement[6] == 0x00) {
			builder.append("激活参数");

		}
		builder.append("\n");
		return builder.toString();
	}

}
