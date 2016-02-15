package com.cheuks.bin.anythingtest.xml;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/***
 * 报头对象
 * 
 * @author Ben-Book
 *
 */
public class MessageHeader extends BaseMessage {

	protected void loadDefaultConfig() throws IllegalArgumentException, IllegalAccessException {

	}

	public MessageHeader() {
		try {
			loadDefaultConfig();
		} catch (Exception e) {
			System.err.println(this.getClass() + "\n无法以加载messageheader.properties");
			e.printStackTrace();
		}
	}

	@Override
	public void setTransactionCode(String code) {
		setTRAN_CODE(code);
	}

	/** 前端流水号，定长10位 */
	private String JRNL_NO;
	/** 渠道号，为0001 */
	private String CHANNEL;
	/** 前置交易码，定长5位 */
	private String TRAN_CODE;
	/** 机构号 */
	private String INST_NO;
	/** 终端号 */
	private String TERM_NO;
	/** 柜员号 */
	private String TELLER_NO;
	/** 授权柜员号，本系统置空 */
	private String SUP_TELLER_NO;
	/** 后台授权柜员号，本系统置空 */
	private String SUP_ZW_TELLER_NO;
	/** 授权密码，本系统置空 */
	private String SUP_TELLER_PWD;
	/** 授权机构，支持跨机构授权 */
	private String SUP_INST_NO;
	/** 前端交易日期，YYYYMMDD */
	private String TRAN_DATE = new SimpleDateFormat("yyyyMMdd").format(new Date());
	/** 前端交易时间，HH：MM：SS */
	private String TRAN_TIME = new SimpleDateFormat("HH:mm:ss").format(new Date());;
	/** 密码终端号 */
	private String EMC_TERM_NO;
	/** 帐务集中使用 */
	private String TASK_ID;
	/** 操作日期，帐务集中使用，本系统为空 */
	private String OPER_DATE;
	/** 审批流水号，帐务集中使用，本系统为空 */
	private String APPR_JRNL_NO;
	/** 外围系统交易码 */
	private String CHL_TRAN_CODE;
	/** 代理名称，ab使用 */
	private String AGENT_NAME;
	/** 代理队列管理器名称，ab使用 */
	private String MQM_NAME;
	/** 录入员，本系统为空 */
	private String COMP_TELLER;
	/** 复合员，本系统为空 */
	private String CHK_TELLER;
	/** 保留字段1, 本系统为空 */
	private String RESV1;
	/** 保留字段2，本系统为空 */
	private String RESV2;
	/** 影像流水号，本系统为空 */
	private String IMG_JRNL_NO;
	/** 节点号，帐务集中使用，本系统为空 */
	private String NODE_ID;
	/** 当前操作柜员 */
	private String OPER_TELLER_NO;

	/** 返回码 */
	/* @ResponseResult */
	private String RET_CODE;

	/** 返回信息 */
	/* @ResponseResult */
	private String RET_MSG;

	public String getJRNL_NO() {
		return JRNL_NO;
	}

	public MessageHeader setJRNL_NO(String jRNL_NO) {
		JRNL_NO = jRNL_NO;
		return this;
	}

	public String getCHANNEL() {
		return CHANNEL;
	}

	public MessageHeader setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
		return this;
	}

	public String getTRAN_CODE() {
		return TRAN_CODE;
	}

	public MessageHeader setTRAN_CODE(String tRAN_CODE) {
		TRAN_CODE = tRAN_CODE;
		return this;
	}

	public String getINST_NO() {
		return INST_NO;
	}

	public MessageHeader setINST_NO(String iNST_NO) {
		INST_NO = iNST_NO;
		return this;
	}

	public String getTERM_NO() {
		return TERM_NO;
	}

	public MessageHeader setTERM_NO(String tERM_NO) {
		TERM_NO = tERM_NO;
		return this;
	}

	public String getTELLER_NO() {
		return TELLER_NO;
	}

	public MessageHeader setTELLER_NO(String tELLER_NO) {
		TELLER_NO = tELLER_NO;
		return this;
	}

	public String getSUP_TELLER_NO() {
		return SUP_TELLER_NO;
	}

	public MessageHeader setSUP_TELLER_NO(String sUP_TELLER_NO) {
		SUP_TELLER_NO = sUP_TELLER_NO;
		return this;
	}

	public String getSUP_ZW_TELLER_NO() {
		return SUP_ZW_TELLER_NO;
	}

	public MessageHeader setSUP_ZW_TELLER_NO(String sUP_ZW_TELLER_NO) {
		SUP_ZW_TELLER_NO = sUP_ZW_TELLER_NO;
		return this;
	}

	public String getSUP_TELLER_PWD() {
		return SUP_TELLER_PWD;
	}

	public MessageHeader setSUP_TELLER_PWD(String sUP_TELLER_PWD) {
		SUP_TELLER_PWD = sUP_TELLER_PWD;
		return this;
	}

	public String getSUP_INST_NO() {
		return SUP_INST_NO;
	}

	public MessageHeader setSUP_INST_NO(String sUP_INST_NO) {
		SUP_INST_NO = sUP_INST_NO;
		return this;
	}

	public String getTRAN_DATE() {
		return TRAN_DATE;
	}

	public MessageHeader setTRAN_DATE(String tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
		return this;
	}

	public String getTRAN_TIME() {
		return TRAN_TIME;
	}

	public MessageHeader setTRAN_TIME(String tRAN_TIME) {
		TRAN_TIME = tRAN_TIME;
		return this;
	}

	public String getEMC_TERM_NO() {
		return EMC_TERM_NO;
	}

	public MessageHeader setEMC_TERM_NO(String eMC_TERM_NO) {
		EMC_TERM_NO = eMC_TERM_NO;
		return this;
	}

	public String getTASK_ID() {
		return TASK_ID;
	}

	public MessageHeader setTASK_ID(String tASK_ID) {
		TASK_ID = tASK_ID;
		return this;
	}

	public String getOPER_DATE() {
		return OPER_DATE;
	}

	public MessageHeader setOPER_DATE(String oPER_DATE) {
		OPER_DATE = oPER_DATE;
		return this;
	}

	public String getAPPR_JRNL_NO() {
		return APPR_JRNL_NO;
	}

	public MessageHeader setAPPR_JRNL_NO(String aPPR_JRNL_NO) {
		APPR_JRNL_NO = aPPR_JRNL_NO;
		return this;
	}

	public String getCHL_TRAN_CODE() {
		return CHL_TRAN_CODE;
	}

	public MessageHeader setCHL_TRAN_CODE(String cHL_TRAN_CODE) {
		CHL_TRAN_CODE = cHL_TRAN_CODE;
		return this;
	}

	public String getAGENT_NAME() {
		return AGENT_NAME;
	}

	public MessageHeader setAGENT_NAME(String aGENT_NAME) {
		AGENT_NAME = aGENT_NAME;
		return this;
	}

	public String getMQM_NAME() {
		return MQM_NAME;
	}

	public MessageHeader setMQM_NAME(String mQM_NAME) {
		MQM_NAME = mQM_NAME;
		return this;
	}

	public String getCOMP_TELLER() {
		return COMP_TELLER;
	}

	public MessageHeader setCOMP_TELLER(String cOMP_TELLER) {
		COMP_TELLER = cOMP_TELLER;
		return this;
	}

	public String getCHK_TELLER() {
		return CHK_TELLER;
	}

	public MessageHeader setCHK_TELLER(String cHK_TELLER) {
		CHK_TELLER = cHK_TELLER;
		return this;
	}

	public String getRESV1() {
		return RESV1;
	}

	public MessageHeader setRESV1(String rESV1) {
		RESV1 = rESV1;
		return this;
	}

	public String getRESV2() {
		return RESV2;
	}

	public MessageHeader setRESV2(String rESV2) {
		RESV2 = rESV2;
		return this;
	}

	public String getIMG_JRNL_NO() {
		return IMG_JRNL_NO;
	}

	public MessageHeader setIMG_JRNL_NO(String iMG_JRNL_NO) {
		IMG_JRNL_NO = iMG_JRNL_NO;
		return this;
	}

	public String getNODE_ID() {
		return NODE_ID;
	}

	public MessageHeader setNODE_ID(String nODE_ID) {
		NODE_ID = nODE_ID;
		return this;
	}

	public String getOPER_TELLER_NO() {
		return OPER_TELLER_NO;
	}

	public MessageHeader setOPER_TELLER_NO(String oPER_TELLER_NO) {
		OPER_TELLER_NO = oPER_TELLER_NO;
		return this;
	}

	public String getRET_CODE() {
		return RET_CODE;
	}

	public MessageHeader setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
		return this;
	}

	public String getRET_MSG() {
		return RET_MSG;
	}

	public MessageHeader setRET_MSG(String rET_MSG) {
		RET_MSG = rET_MSG;
		return this;
	}

}
