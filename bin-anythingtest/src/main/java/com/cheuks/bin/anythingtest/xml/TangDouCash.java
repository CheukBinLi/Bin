package com.cheuks.bin.anythingtest.xml;

/***
 * 4.3	唐豆兑付--07505
 * 
 * @author Ben-Book
 *
 */
public class TangDouCash extends BaseMessage {

	/***
	 * 
	 * @param aCCT_NO 账号
	 * @param sUB_ACCT_NO 子账号
	 * @param aCCT_BAL 余额
	 */

	public TangDouCash() {
		super();
	}

	public static class ACCT_INFO_CLASS {
		private String ACCT_NO;//	账号
		private String SUB_ACCT_NO;//	子账号
		private String ACCT_BAL;//	余额

		public String getACCT_NO() {
			return ACCT_NO;
		}

		public ACCT_INFO_CLASS setACCT_NO(String aCCT_NO) {
			ACCT_NO = aCCT_NO;
			return this;
		}

		public String getSUB_ACCT_NO() {
			return SUB_ACCT_NO;
		}

		public ACCT_INFO_CLASS setSUB_ACCT_NO(String sUB_ACCT_NO) {
			SUB_ACCT_NO = sUB_ACCT_NO;
			return this;
		}

		public String getACCT_BAL() {
			return ACCT_BAL;
		}

		public ACCT_INFO_CLASS setACCT_BAL(String aCCT_BAL) {
			ACCT_BAL = aCCT_BAL;
			return this;
		}
	}

	private ACCT_INFO_CLASS ACCT_INFO;

	//	private String ACCT_INFO;
	private String PRODUCT_CODE;//	产品代码
	private String DEP_TERM;//	存期
	private String ACCEPT_DATE;//	兑付日
	private String TOTAL_BAL;//	账户总余额
	private String TERM_CODE;//	唐豆期次代码
	private String COUNT;//	唐豆数量
	private String EXCHANGE_MODE;//	唐豆兑换方式
	private String EXCHANGE_PROP;//	唐豆兑现比例
	private String EXCHANGE_AMT;//	兑现金额
	private String CUSTOM_MANAGER_NO;//	客户经理号
	private String CUSTOM_MANAGER_NAME;//	客户经理名称

	private String SVR_DATE;//	交易日期

	private String SVR_JRNL_NO;//	流水号

	private String CUST_NO;//	客户号

	private String CUST_NAME;//	客户名称

	private String R_PRODUCT_CODE;//	产品代码

	private String R_COUNT;//	唐豆数量

	private String R_EXCHANGE_AMT;//	唐豆兑现金额

	private String INT_FROM_ACCT;//	利息支出账户

	private String PUT_INT_ACCT;//	应付利息账户

	private String AMT_ACCT_NO;//	现金账户

	// 公共返回:返回码

	private String RET_CODE;
	// 公共返回:返回信息

	private String RET_MSG;

	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}

	public TangDouCash setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
		return this;
	}

	public String getDEP_TERM() {
		return DEP_TERM;
	}

	public TangDouCash setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
		return this;
	}

	public String getACCEPT_DATE() {
		return ACCEPT_DATE;
	}

	public TangDouCash setACCEPT_DATE(String aCCEPT_DATE) {
		ACCEPT_DATE = aCCEPT_DATE;
		return this;
	}

	public String getTOTAL_BAL() {
		return TOTAL_BAL;
	}

	public TangDouCash setTOTAL_BAL(String tOTAL_BAL) {
		TOTAL_BAL = tOTAL_BAL;
		return this;
	}

	public String getTERM_CODE() {
		return TERM_CODE;
	}

	public TangDouCash setTERM_CODE(String tERM_CODE) {
		TERM_CODE = tERM_CODE;
		return this;
	}

	public String getCOUNT() {
		return COUNT;
	}

	public TangDouCash setCOUNT(String cOUNT) {
		COUNT = cOUNT;
		return this;
	}

	public String getEXCHANGE_MODE() {
		return EXCHANGE_MODE;
	}

	public TangDouCash setEXCHANGE_MODE(String eXCHANGE_MODE) {
		EXCHANGE_MODE = eXCHANGE_MODE;
		return this;
	}

	public String getEXCHANGE_PROP() {
		return EXCHANGE_PROP;
	}

	public TangDouCash setEXCHANGE_PROP(String eXCHANGE_PROP) {
		EXCHANGE_PROP = eXCHANGE_PROP;
		return this;
	}

	public String getEXCHANGE_AMT() {
		return EXCHANGE_AMT;
	}

	public TangDouCash setEXCHANGE_AMT(String eXCHANGE_AMT) {
		EXCHANGE_AMT = eXCHANGE_AMT;
		return this;
	}

	public String getCUSTOM_MANAGER_NO() {
		return CUSTOM_MANAGER_NO;
	}

	public TangDouCash setCUSTOM_MANAGER_NO(String cUSTOM_MANAGER_NO) {
		CUSTOM_MANAGER_NO = cUSTOM_MANAGER_NO;
		return this;
	}

	public String getCUSTOM_MANAGER_NAME() {
		return CUSTOM_MANAGER_NAME;
	}

	public TangDouCash setCUSTOM_MANAGER_NAME(String cUSTOM_MANAGER_NAME) {
		CUSTOM_MANAGER_NAME = cUSTOM_MANAGER_NAME;
		return this;
	}

	public String getSVR_DATE() {
		return SVR_DATE;
	}

	public TangDouCash setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
		return this;
	}

	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}

	public TangDouCash setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
		return this;
	}

	public String getCUST_NO() {
		return CUST_NO;
	}

	public TangDouCash setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
		return this;
	}

	public String getCUST_NAME() {
		return CUST_NAME;
	}

	public TangDouCash setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
		return this;
	}

	public String getR_PRODUCT_CODE() {
		return R_PRODUCT_CODE;
	}

	public TangDouCash setR_PRODUCT_CODE(String r_PRODUCT_CODE) {
		R_PRODUCT_CODE = r_PRODUCT_CODE;
		return this;
	}

	public String getR_COUNT() {
		return R_COUNT;
	}

	public TangDouCash setR_COUNT(String r_COUNT) {
		R_COUNT = r_COUNT;
		return this;
	}

	public String getR_EXCHANGE_AMT() {
		return R_EXCHANGE_AMT;
	}

	public TangDouCash setR_EXCHANGE_AMT(String r_EXCHANGE_AMT) {
		R_EXCHANGE_AMT = r_EXCHANGE_AMT;
		return this;
	}

	public String getINT_FROM_ACCT() {
		return INT_FROM_ACCT;
	}

	public TangDouCash setINT_FROM_ACCT(String iNT_FROM_ACCT) {
		INT_FROM_ACCT = iNT_FROM_ACCT;
		return this;
	}

	public String getPUT_INT_ACCT() {
		return PUT_INT_ACCT;
	}

	public TangDouCash setPUT_INT_ACCT(String pUT_INT_ACCT) {
		PUT_INT_ACCT = pUT_INT_ACCT;
		return this;
	}

	public String getAMT_ACCT_NO() {
		return AMT_ACCT_NO;
	}

	public TangDouCash setAMT_ACCT_NO(String aMT_ACCT_NO) {
		AMT_ACCT_NO = aMT_ACCT_NO;
		return this;
	}

	public String getRET_CODE() {
		return RET_CODE;
	}

	public TangDouCash setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
		return this;
	}

	public String getRET_MSG() {
		return RET_MSG;
	}

	public TangDouCash setRET_MSG(String rET_MSG) {
		RET_MSG = rET_MSG;
		return this;
	}

	public ACCT_INFO_CLASS getACCT_INFO() {
		return ACCT_INFO;
	}

	public TangDouCash setACCT_INFO(ACCT_INFO_CLASS aCCT_INFO) {
		ACCT_INFO = aCCT_INFO;
		return this;
	}
}
