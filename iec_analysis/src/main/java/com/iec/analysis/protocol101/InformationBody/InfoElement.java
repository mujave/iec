package com.iec.analysis.protocol101.InformationBody;

/**
 * @author 张雨 2018.01.11
 * 
 */
public class InfoElement {
	/**
	 * 解析信息元素
	 * 
	 * @param infoElement
	 *            信息元素集报文
	 * @param num
	 *            信息元素的个数
	 * @param TI
	 *            类型标识
	 * @param SQ
	 *            SQ值
	 * @return
	 * 
	 */
	public String info_element(int[] infoElement, int num, int TI, int SQ) {

		String str = "";
		switch (TI) {
		case 1:// 单点信息（遥信）
			str = new Telemetry().NoTime_Point(infoElement, num, TI, SQ);
			break;
		case 3:// 双点信息 （遥信）
			str = new Telemetry().NoTime_Point(infoElement, num, TI, SQ);
			break;
		case 9:// 测量值，归一化值（遥测）*
			str += new Telesignalling().normalization(infoElement, num, TI, SQ);
			break;
		case 11:// 测量值，标度化值（遥测）
			str += new Telesignalling().standardization(infoElement, num, TI,
					SQ);
			break;
		case 13:// 测量值，短浮点数（遥测）
			str += new Telesignalling().short_float(infoElement, num, TI, SQ);
			break;
		case 30:// 带CP56Time2a时标的单点信息（遥信）
			str = new Telemetry().Time_Point(infoElement, num, TI, SQ);
			break;
		case 31:// 带CP56Time2a时标的双点信息（遥信）
			str = new Telemetry().Time_Point(infoElement, num, TI, SQ);
			break;
		case 45:// 单命令（遥控）
			str = new Telecontrol().Single_command(infoElement, num, TI);
			break;
		case 46:// 双命令（遥控）
			str = new Telecontrol().Double_command(infoElement, num, TI);
			break;
		case 70:// 初始化结束
			str = new SystemCommand().initialize(infoElement, num, TI);
			break;
		case 100:// 召唤命令
			str = new SystemCommand().GeneralCall(infoElement, num, TI);
			break;
		case 103:// 时钟同步/读取命令
			str = new SystemCommand().clock_syschro(infoElement, num, TI);
			break;
		case 104:// 链路测试命令
			str = new SystemCommand().link_test(infoElement, num, TI);
			break;
		case 105:// 复位进程命令
			str = new SystemCommand().reset_proxess(infoElement, num, TI);
			break;
		case 102:// 读单个参数命令(参数设置)
			str += new ParamePreset().single_parmeter(infoElement, num, TI, SQ);
			break;
		case 132:// 读多个参数命令(参数设置)
			str += new ParamePreset().multi_parmeter(infoElement, num, TI, SQ);
			break;
		case 48:// 预置/激活单个参数命令(参数设置)
			str += new ParamePreset().activate_single_parmeter(infoElement,
					num, TI, SQ);
			break;
		case 136:// 预置/激活多个参数命令(参数设置)
			str += new ParamePreset().activate_multi_parmeter(infoElement, num,
					TI, SQ);
			break;
		default:
			str += "无效的类型标识TI,无法解析！";

		}

		return str;
	}

}
