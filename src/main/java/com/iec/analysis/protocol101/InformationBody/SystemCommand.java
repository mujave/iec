package com.iec.analysis.protocol101.InformationBody;


import com.iec.utils.Util;

public class SystemCommand {
	private Util util = new Util();

	/**
	 * 初始化结束信息元素集解析
	 * 
	 * @param infoElement
	 *            信息元素集报文
	 * @param num
	 *            信息元素的个数
	 * @param tI
	 *            类型标识
	 * @return
	 */
	public String initialize(int[] infoElement, int num, int tI) {
		StringBuilder builder = new StringBuilder();
		builder.append("信息元素1内容：\n");
		builder.append("信息对象地址：  ");
		builder.append(util.address(infoElement[0], infoElement[1]));
		builder.append("初始化进程命令限定词QRP：");
		builder.append(infoElement[2]);
		builder.append("\n");

		return builder.toString();
	}

	/**
	 * 总召唤信息元素集解析
	 * 
	 * @param infoElement
	 *            信息元素集报文
	 * @param num
	 *            信息元素的个数
	 * @param TI
	 *            类型标识
	 * @return
	 */
	public String GeneralCall(int[] infoElement, int num, int TI) {
		StringBuilder builder = new StringBuilder();
		builder.append("信息元素1内容：\n");
		builder.append("信息对象地址：  ");
		builder.append(util.address(infoElement[0], infoElement[1]));
		builder.append("召唤限定词QOI：");
		builder.append(infoElement[2]);

		return builder.toString();

	}

	/**
	 * 时钟同步/读取命令信息元素集解析
	 * 
	 * @param infoElement
	 *            信息元素集报文
	 * @param num
	 *            信息元素的个数
	 * @param tI
	 *            类型标识
	 * @return
	 */
	public String clock_syschro(int[] infoElement, int num, int tI) {
		StringBuilder builder = new StringBuilder();
		builder.append("信息元素1的地址：");
		builder.append(util.address(infoElement[0], infoElement[1]));
		builder.append("时钟同步/读取命令，信息元素值无\n");

		int clock[] = { infoElement[2], infoElement[3], infoElement[4],
				infoElement[5], infoElement[6], infoElement[7], infoElement[8] };
		builder.append(util.TimeScale(clock));
		return builder.toString();
	}

	/**
	 * 复位进程命令信息元素集解析
	 * 
	 * @param infoElement
	 *            信息元素集报文
	 * @param num
	 *            信息元素的个数
	 * @param tI
	 *            类型标识
	 * @return
	 */
	public String reset_proxess(int[] infoElement, int num, int tI) {
		StringBuilder builder = new StringBuilder();
		builder.append("信息元素1内容：\n");
		builder.append("信息对象地址：  ");
		builder.append(util.address(infoElement[0], infoElement[1]));
		builder.append("复位进程命令限定词QRP：");
		builder.append(infoElement[2]);
		builder.append("\n");
		return builder.toString();
	}

	/**
	 * 链路测试命令
	 * 
	 * @param infoElement
	 * @param num
	 * @param tI
	 * @return
	 */
	public String link_test(int[] infoElement, int num, int tI) {
		StringBuilder builder = new StringBuilder();
		builder.append("信息对象地址：  ");
		builder.append(util.address(infoElement[0], infoElement[1]));
		builder.append("固定测试字FBP：");
		builder.append(util.toHexString(infoElement[2]));
		builder.append("  ");
		builder.append(util.toHexString(infoElement[3]));
		builder.append("  ");
		builder.append("\n");
		return builder.toString();
	}

}
