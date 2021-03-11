package com.jpmc.supersimplestocks.enums;

/** Stock Types **/
public enum StockType {
	PREFERRED("PREFERRED"), 
	COMMON("COMMON");

	private String stockType;

	StockType(String stockType) {
		this.stockType = stockType;
	}

	public String getValue() {
		return stockType;
	}
}