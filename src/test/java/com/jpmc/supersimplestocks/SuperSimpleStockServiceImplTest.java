package com.jpmc.supersimplestocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.jpmc.supersimplestocks.config.TradeDataComponent;
import com.jpmc.supersimplestocks.enums.StockType;
import com.jpmc.supersimplestocks.enums.TradeType;
import com.jpmc.supersimplestocks.model.StockVO;
import com.jpmc.supersimplestocks.model.TradeVO;
import com.jpmc.supersimplestocks.service.SuperSimpleStockService;
import com.jpmc.supersimplestocks.service.SuperSimpleStockServiceImpl;

import jodd.datetime.JDateTime;

public class SuperSimpleStockServiceImplTest {

	@InjectMocks
	SuperSimpleStockService superSimpleStockService = new SuperSimpleStockServiceImpl();
	
	@Mock
	TradeDataComponent tradeComponent;
	
	TradeVO tradeVO;
	SortedMap<JDateTime, TradeVO> trades;
	StockVO stockVO;
	
	@BeforeEach
	public void setup() {
		tradeVO = new TradeVO("TEA", TradeType.BUY, 1, 1.0);
		trades = new TreeMap<JDateTime, TradeVO>();
		trades.put(new JDateTime(new Date()), tradeVO);		
		stockVO = new StockVO("TEA", StockType.COMMON, 2.0, 0.0, 100.0);
		
	}
	
	@Test
	public void testPERatio() throws Exception{
		assertEquals(new Double(0.5),superSimpleStockService.PERatio(stockVO,1.0));
	}
	
	@Test void testDividend() throws Exception{
		assertEquals(new Double(2.0),superSimpleStockService.dividend(stockVO,1.0));
	}
}
