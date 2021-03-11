package com.jpmc.supersimplestocks.config;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.jpmc.supersimplestocks.enums.StockType;
import com.jpmc.supersimplestocks.model.StockVO;
import com.jpmc.supersimplestocks.model.TradeVO;

import jodd.datetime.JDateTime;

@Component
public class TradeDataComponent {
 
	private TreeMap<JDateTime, TradeVO> TradeInfo = new TreeMap<JDateTime, TradeVO>();
	
	@Bean(name = "testData")
	public List<StockVO> testData(){
		List<StockVO> testDataList = new ArrayList<StockVO>();
		// Sample test data
		testDataList.add(new StockVO("TEA", StockType.COMMON, 2.0, 0.0, 100.0));
		testDataList.add(new StockVO("COFEE", StockType.PREFERRED, 8.0, 0.2, 100.0));
		return testDataList;
	}

	public TreeMap<JDateTime, TradeVO> getTradeInfo() {
		return TradeInfo;
	}

	public void setTradeInfo(TreeMap<JDateTime, TradeVO> tradeInfo) {
		TradeInfo = tradeInfo;
	}
}
