package com.jpmc.supersimplestocks;

import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.jpmc.supersimplestocks.model.StockVO;
import com.jpmc.supersimplestocks.service.SuperSimpleStockService;

/**
 * SuperSimpleStocksApplication
 *
 */
@SpringBootApplication
public class SuperSimpleStocksApplication implements CommandLineRunner{

	Logger log = LoggerFactory.getLogger(SuperSimpleStocksApplication.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	
	private SuperSimpleStockService stockService;
	
	public static void main(String[] args) {
		SpringApplication.run(SuperSimpleStocksApplication.class, args);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void run(String... arg0) throws Exception {
		log.info("SuperSimpleStocksApplication Start");
		List<StockVO> sampleTestStock = context.getBean("testData", List.class);
		Integer minVal = 1;
		Integer maxVal = 10;
		for(StockVO stockVO : sampleTestStock) {
			double dividend = stockService.dividend(stockVO, 6.1);
			double peRatio = stockService.PERatio(stockVO, 5.1);
			log.info("Dividend = "+dividend+ " PERatio = "+peRatio);
        	for (int i=1; i <= 5; i++) {
        		Random r = new Random();
        		double randomValue = minVal + (maxVal - minVal) * r.nextDouble();
        		stockService.buyStock(stockVO,i, randomValue);
        		log.info( stockVO.getStockSymbol() + " Bought " + i + " Shares at ₹" + randomValue);
        		randomValue = minVal + (maxVal - minVal) * r.nextDouble();
        		stockService.sellStock(stockVO, i, randomValue);
        		log.info( stockVO.getStockSymbol()  + " Sold " + i + " Shares at ₹" + randomValue);
        	}
		}
		double volumeWeightStockPrice = stockService.calculateVolumeWeightedStockPrice(5);
		double shareIndex = stockService.allShareIndex(sampleTestStock);
		log.info("Volume Weight Stock Price = "+volumeWeightStockPrice+ " All share index = "+shareIndex);
	}
}
