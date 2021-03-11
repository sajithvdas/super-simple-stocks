package com.jpmc.supersimplestocks.model;

import com.jpmc.supersimplestocks.enums.TradeType;

/**
 * Trade VO
 *
 */
public class TradeVO {
	private String itemName;
	private TradeType tradeType;
	private Integer quantity;
	private Double price;
	
	public TradeVO(String itemName, TradeType tradeType, Integer quantity, Double price) {
		this.itemName = itemName;
		this.tradeType = tradeType;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

}
