package com.jpmc.supersimplestocks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.stat.StatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jpmc.supersimplestocks.config.TradeDataComponent;
import com.jpmc.supersimplestocks.enums.TradeType;
import com.jpmc.supersimplestocks.model.StockVO;
import com.jpmc.supersimplestocks.model.TradeVO;

import jodd.datetime.JDateTime;

/**
 * SuperSimpleStockServiceImpl
 *
 */
@Service
public class SuperSimpleStockServiceImpl implements SuperSimpleStockService {

	Logger log = LoggerFactory.getLogger(SuperSimpleStockServiceImpl.class);

	@Autowired
	private TradeDataComponent tradeComponent;

	/**
	 * Method to get the Volume Weighted Stock Price based on trades in past 5
	 * minutes
	 */
	@Override
	public Double calculateVolumeWeightedStockPrice(Integer timeInMinutes) {
		try {
			log.debug("Calculating volume weight stock price");
			JDateTime jdt = new JDateTime(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(timeInMinutes));
			SortedMap<JDateTime, TradeVO> trades = tradeComponent.getTradeInfo().tailMap(jdt);
			Double volumeWeigthedStockPrice = 0.0;
			Integer totalQuantity = 0;
			for (TradeVO trade : trades.values()) {
				totalQuantity += trade.getQuantity();
				volumeWeigthedStockPrice += trade.getPrice() * trade.getQuantity();
			}
			return volumeWeigthedStockPrice / totalQuantity;
		} catch (Exception e) {
			log.error("Exception occured calculating volume weight stock price", e.getMessage());
			return 0.0;
		}

	}

	/**
	 * Method to Buy a stock
	 * 
	 * @param stockVO  stock details
	 * @param quantity quantity of the stock
	 * @param price    price info
	 */
	@Override
	public void buyStock(StockVO stockVO, Integer quantity, Double price) {
		TradeVO trade = new TradeVO(stockVO.getStockSymbol(), TradeType.BUY, quantity, price);
		tradeComponent.getTradeInfo().put(new JDateTime(System.currentTimeMillis()), trade);
	}

	/**
	 * Method to get dividend of a stock
	 * 
	 * @param stockVO stock details
	 * @param price   price info
	 */
	@Override
	public Double dividend(StockVO stockVO, Double price) {
		switch (stockVO.getStockType()) {
		case COMMON:
			return stockVO.getLastDividend() / price;
		case PREFERRED:
			return stockVO.getFixedDividend() * stockVO.getParValue() / price;
		default:
			return 0.0;
		}
	}

	/**
	 * Method to get PE ratio of a stock
	 * 
	 * @param stockVO stock details
	 * @param price   price info
	 * @return PE ratio
	 **/
	@Override
	public Double PERatio(StockVO stockVO, Double price) {
		return price / stockVO.getLastDividend();
	}

	/**
	 * Method to Sell a stock
	 * 
	 * @param stockVO  stock details
	 * @param quantity quantity of the stock
	 * @param price    price info
	 **/
	@Override
	public void sellStock(StockVO stockVO, Integer quantity, Double price) {
		TradeVO trade = new TradeVO(stockVO.getStockSymbol(), TradeType.SELL, quantity, price);
		tradeComponent.getTradeInfo().put(new JDateTime(System.currentTimeMillis()), trade);
	}

	/**
	 * Method to get the current price of the stock
	 * 
	 * @return price current price
	 **/
	@Override
	public Double getPrice() {
		if (tradeComponent.getTradeInfo().size() > 0) {
			return tradeComponent.getTradeInfo().lastEntry().getValue().getPrice();
		} else {
			return 0.0;
		}
	}

	/**
	 * Method to get the GBCE All Share Index for all stocks traded
	 * 
	 * @param stocks list of stocks
	 **/
	@Override
	public Double allShareIndex(List<StockVO> stocks) {
		try {
			Double allShareIndex = 0.0;
			Double volumeWeigthedStockPrice = 0.0;
			Integer totalQuantity = 0;
			ArrayList<Double> stockPrices = new ArrayList<Double>();
			for (TradeVO trade : tradeComponent.getTradeInfo().values()) {
				totalQuantity += trade.getQuantity();
				volumeWeigthedStockPrice += trade.getPrice() * trade.getQuantity();
				stockPrices.add(volumeWeigthedStockPrice / totalQuantity);
			}
			if (stockPrices.size() >= 1) {
				double[] stockPricesArray = new double[stockPrices.size()];
				for (int i = 0; i <= (stockPrices.size() - 1); i++) {
					stockPricesArray[i] = stockPrices.get(i).doubleValue();
				}
				allShareIndex = StatUtils.geometricMean(stockPricesArray);
			}
			return allShareIndex;
		} catch (Exception e) {
			log.error("Exception occured calculating all share index of all stocks traded", e.getMessage());
			return 0.0;
		}

	}
}
