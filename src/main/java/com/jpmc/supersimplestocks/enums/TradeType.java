package com.jpmc.supersimplestocks.enums;

/** Trade Types **/
public enum TradeType {
	BUY("BUY"), 
	SELL("SELL");
	
	private String tradeType;

	TradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getValue() {
		return tradeType;
	}
}