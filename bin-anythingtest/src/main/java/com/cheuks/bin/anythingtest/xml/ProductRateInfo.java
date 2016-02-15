package com.cheuks.bin.anythingtest.xml;

import org.apache.zookeeper.version.Info;

/***
 * 产品利率信息
 *
 */
public class ProductRateInfo implements Info {

	//	产品代码||存期||到期利率
	private String productCode;
	private String savingCount;
	private String interestRate;

	public String getProductCode() {
		return productCode;
	}

	public ProductRateInfo setProductCode(String productCode) {
		this.productCode = productCode;
		return this;
	}

	public String getSavingCount() {
		return savingCount;
	}

	public ProductRateInfo setSavingCount(String savingCount) {
		this.savingCount = savingCount;
		return this;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public ProductRateInfo setInterestRate(String interestRate) {
		this.interestRate = interestRate;
		return this;
	}

}
