package com.jpmc.supersimplestocks.service;

import java.util.List;

import com.jpmc.supersimplestocks.model.StockVO;

/**
 * SuperSimpleStockService
 *
 */
public interface SuperSimpleStockService {

	 Double calculateVolumeWeightedStockPrice(Integer timeInMinutes);
	 
	 Double dividend(StockVO stockVO, Double price);
	 
	 void buyStock(StockVO stockVO, Integer quantity, Double price);
	 
	 Double PERatio(StockVO stockVO, Double price);
	 
	 void sellStock(StockVO stockVO, Integer quantity, Double price);
	 
	 Double getPrice();
	 
	 Double allShareIndex(List<StockVO> stocks);
}
