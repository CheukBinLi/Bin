package com.cheuks.bin.anythingtest.xml;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.cheuks.bin.anythingtest.xml.TangDouCash.ACCT_INFO_CLASS;
import com.cheuks.bin.anythingtest.xml.XmlEntity.B;

public class MakeXml {

	public String r() {
		return null;
	}

	private List<String> oooo = new ArrayList<String>();

	public void x(Object o) {
		Class c = o.getClass();
		System.out.println(c.getName());

		String[] a = new String[0];
		System.err.println(a.getClass());
	}

	public boolean isGeneralType(Class<?> c) {
		if (!c.isArray() && (c == int.class || c == Integer.class || c == String.class || c == char.class || c == Character.class || c == short.class | c == Short.class || c == long.class || c == Long.class || c == float.class || c == Float.class || c == byte.class || c == Byte.class || c == double.class || c == Double.class))
			return true;
		return false;
	}

	public String sub(Object o) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		if (isGeneralType(o.getClass()))
			return o.toString();
		Field[] fields = o.getClass().getDeclaredFields();
		Object tempO;
		StringBuffer sb = new StringBuffer();
		for (Field f : fields) {
			f.setAccessible(true);
			sb.append("<").append(f.getName()).append(">").append(sub(null == (tempO = f.get(o)) ? getInstance(f.getType()) : tempO)).append("</").append(f.getName()).append(">");
		}
		return sb.toString();
	}

	public Object getInstance(Class<?> c) throws InstantiationException, IllegalAccessException {
		//		if (isGeneralType(c))
		return c.newInstance();
		//		if (c.isInterface())
		//			if (c == Set.class) {
		//				System.err.println(c.getGenericSuperclass());
		//				return new HashSet();
		//			}
		//		return null;
	}

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException, ParserConfigurationException, SAXException, IOException {
		MakeXml mx = new MakeXml();
		//		Field f = MakeXml.class.getDeclaredField("oooo");
		//		Object o = f.get(mx);
		//		List ox = (List) o;
		//		ox.add("哇哈哈");
		//		ox.add("1");
		//		System.err.println(mx.oooo.get(0));
		//		System.err.println(new String(mx.oooo.get(1)));
		//		for (Class c : o.getClass().getInterfaces())
		//			if (c == List.class)
		//				System.err.println(c.getName());
		XmlEntity xe = new XmlEntity();
		xe.setA("aaaaa").setB("bbbbb").setBclass(new B()).getBclass().setA("A").setB("B");
		System.err.println(mx.sub(xe));

		Set<String> set = new HashSet<String>();
		System.err.println(set.getClass().getGenericSuperclass().getTypeName());

		TangDouCash tdc = new TangDouCash().setACCT_INFO(new ACCT_INFO_CLASS().setACCT_BAL("A").setACCT_NO("B").setSUB_ACCT_NO("C")).setACCEPT_DATE("112233").setAMT_ACCT_NO("711").setCOUNT("11231213");
		System.err.println(mx.sub(tdc));

		String tangdou = "<?xml version='1.0' encoding='utf-8'?><A><ACCT_INFO><ACCT_NO>B</ACCT_NO><SUB_ACCT_NO>C</SUB_ACCT_NO><ACCT_BAL>A</ACCT_BAL></ACCT_INFO><PRODUCT_CODE>一</PRODUCT_CODE><DEP_TERM>经</DEP_TERM><ACCEPT_DATE>112233</ACCEPT_DATE><TOTAL_BAL></TOTAL_BAL><TERM_CODE></TERM_CODE><COUNT>11231213</COUNT><EXCHANGE_MODE></EXCHANGE_MODE><EXCHANGE_PROP></EXCHANGE_PROP><EXCHANGE_AMT></EXCHANGE_AMT><CUSTOM_MANAGER_NO></CUSTOM_MANAGER_NO><CUSTOM_MANAGER_NAME></CUSTOM_MANAGER_NAME><SVR_DATE></SVR_DATE><SVR_JRNL_NO></SVR_JRNL_NO><CUST_NO></CUST_NO><CUST_NAME></CUST_NAME><R_PRODUCT_CODE></R_PRODUCT_CODE><R_COUNT></R_COUNT><R_EXCHANGE_AMT></R_EXCHANGE_AMT><INT_FROM_ACCT></INT_FROM_ACCT><PUT_INT_ACCT></PUT_INT_ACCT><AMT_ACCT_NO>711</AMT_ACCT_NO><RET_CODE></RET_CODE><RET_MSG></RET_MSG></A>";
		XmlReaderAll xra = new XmlReaderAll();
		//		System.out.println();
		//		TangDouCash tangDouCash = xra.padding(tangdou.getBytes(), TangDouCash.class);
		//		System.out.println(tangDouCash.getACCT_INFO().getACCT_BAL());
		//		System.out.println(tangDouCash.getACCEPT_DATE());
		//		System.out.println(tangDouCash.getCOUNT());
		String productQuery = "<?xml version='1.0' encoding='GBK'?><ROOT><HEAD><JRNL_NO>0202250885</JRNL_NO><CHANNEL>0027</CHANNEL><TRAN_CODE>02867</TRAN_CODE><INST_NO>050100122</INST_NO><TERM_NO>BXH</TERM_NO><TELLER_NO>12214</TELLER_NO><SUP_TELLER_NO></SUP_TELLER_NO><SUP_ZW_TELLER_NO></SUP_ZW_TELLER_NO><SUP_TELLER_PWD></SUP_TELLER_PWD><SUP_INST_NO></SUP_INST_NO><TRAN_DATE>20160108</TRAN_DATE><TRAN_TIME>08:42:14</TRAN_TIME><EMC_TERM_NO></EMC_TERM_NO><TASK_ID>1302020012212214157727</TASK_ID><OPER_DATE></OPER_DATE><APPR_JRNL_NO></APPR_JRNL_NO><CHL_TRAN_CODE>3820</CHL_TRAN_CODE><AGENT_NAME>ZHQZCLT.AGENT</AGENT_NAME><MQM_NAME>MSQZ_AGENT_QM</MQM_NAME><COMP_TELLER></COMP_TELLER><CHK_TELLER></CHK_TELLER><RESV1></RESV1><RESV2></RESV2><IMG_JRNL_NO></IMG_JRNL_NO><NODE_ID></NODE_ID><OPER_TELLER_NO>12214</OPER_TELLER_NO></HEAD><BODY><QRY_TYPE>4</QRY_TYPE><PRODUCT_CODE></PRODUCT_CODE></BODY></ROOT>";
		ProductRateInfoQuery productRateInfoQuery = xra.padding(productQuery.getBytes(), ProductRateInfoQuery.class);
		System.out.println(productRateInfoQuery.getAMT_FL_NM());
	}
}
