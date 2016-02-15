package com.cheuks.bin.anythingtest.xml;

import java.util.List;

/***
 * 4.6	产品利率信息查询--02867
 * 
 * @author Ben-Book
 *
 */
public class ProductRateInfoQuery extends BaseMessage {

	private String QRY_TYPE = "4";// 	查询类型
	private String PRODUCT_CODE;//	产品代码

	private String SVR_DATE;//	核心日期

	private String SVR_JRNL_NO;//	流水号

	private String PRODUCT_FI_NM;//	产品文件

	private String AMT_FL_NM;//	金额文件

	private List<ProductRateInfo> list;

	// 公共返回:返回码

	private String RET_CODE;
	// 公共返回:返回信息

	private String RET_MSG;

	public String getQRY_TYPE() {
		return QRY_TYPE;
	}

	public ProductRateInfoQuery setQRY_TYPE(String qRY_TYPE) {
		QRY_TYPE = qRY_TYPE;
		return this;
	}

	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}

	public ProductRateInfoQuery setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
		return this;
	}

	public String getSVR_DATE() {
		return SVR_DATE;
	}

	public ProductRateInfoQuery setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
		return this;
	}

	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}

	public ProductRateInfoQuery setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
		return this;
	}

	public String getPRODUCT_FI_NM() {
		return PRODUCT_FI_NM;
	}

	public ProductRateInfoQuery setPRODUCT_FI_NM(String pRODUCT_FI_NM) {
		PRODUCT_FI_NM = pRODUCT_FI_NM;
		return this;
	}

	public String getAMT_FL_NM() {
		return AMT_FL_NM;
	}

	public ProductRateInfoQuery setAMT_FL_NM(String aMT_FL_NM) {
		AMT_FL_NM = aMT_FL_NM;
		return this;
	}

	public String getRET_CODE() {
		return RET_CODE;
	}

	public ProductRateInfoQuery setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
		return this;
	}

	public String getRET_MSG() {
		return RET_MSG;
	}

	public ProductRateInfoQuery setRET_MSG(String rET_MSG) {
		RET_MSG = rET_MSG;
		return this;
	}

	public List<ProductRateInfo> getList() {
		return list;
	}

	public ProductRateInfoQuery setList(List<ProductRateInfo> list) {
		this.list = list;
		return this;
	}

	//	public static void main(String[] args) {
	//		String a="<?xml version='1.0' encoding='GBK'?><ROOT><HEAD><JRNL_NO>0202250885</JRNL_NO>
	//<CHANNEL>0027</CHANNEL>
	//<TRAN_CODE>02867</TRAN_CODE>
	//<INST_NO>050100122</INST_NO>
	//<TERM_NO>BXH</TERM_NO>
	//<TELLER_NO>12214</TELLER_NO>
	//<SUP_TELLER_NO></SUP_TELLER_NO>
	//<SUP_ZW_TELLER_NO></SUP_ZW_TELLER_NO>
	//<SUP_TELLER_PWD></SUP_TELLER_PWD>
	//<SUP_INST_NO></SUP_INST_NO>
	//<TRAN_DATE>20160107</TRAN_DATE>
	//<TRAN_TIME>17:20:07</TRAN_TIME>
	//<EMC_TERM_NO></EMC_TERM_NO>
	//<TASK_ID>1302020012212214157727</TASK_ID>
	//<OPER_DATE></OPER_DATE>
	//<APPR_JRNL_NO></APPR_JRNL_NO>
	//<CHL_TRAN_CODE>3820</CHL_TRAN_CODE>
	//<AGENT_NAME>ZHQZCLT.AGENT</AGENT_NAME>
	//<MQM_NAME>MSQZ_AGENT_QM</MQM_NAME>
	//<COMP_TELLER></COMP_TELLER>
	//<CHK_TELLER></CHK_TELLER>
	//<RESV1></RESV1><RESV2></RESV2>
	//<IMG_JRNL_NO></IMG_JRNL_NO><NODE_ID></NODE_ID><OPER_TELLER_NO>12214</OPER_TELLER_NO><RET_CODE></RET_CODE><RET_MSG></RET_MSG></HEAD><BODY><QRY_TYPE>4</QRY_TYPE><PRODUCT_CODE></PRODUCT_CODE><SVR_DATE>2016-01-07 17:20:07</SVR_DATE><SVR_JRNL_NO>601409</SVR_JRNL_NO><PRODUCT_FI_NM></PRODUCT_FI_NM><AMT_FL_NM>2016_01_07_17_20_07_343</AMT_FL_NM><RET_CODE>0000</RET_CODE><RET_MSG>成功</RET_MSG></BODY></ROOT>";
	//	}

}
