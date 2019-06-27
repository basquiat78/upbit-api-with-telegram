package io.basquiat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.basquiat.quotation.domain.response.candles.Days;
import io.basquiat.quotation.domain.response.candles.Minutes;
import io.basquiat.quotation.domain.response.candles.WeeksAndMonths;
import io.basquiat.quotation.domain.response.market.MarketAll;
import io.basquiat.quotation.domain.response.orderbook.OrderBook;
import io.basquiat.quotation.domain.response.ticker.Ticker;
import io.basquiat.quotation.domain.response.trades.TradesTick;
import io.basquiat.quotation.service.CandlesService;
import io.basquiat.quotation.service.MarketService;
import io.basquiat.quotation.service.OrderBookService;
import io.basquiat.quotation.service.TickerService;
import io.basquiat.quotation.service.TradesService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpbitQuotationApiTest {
	
	@Autowired
	private CandlesService candlesService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private TickerService tickerService;
	
	@Autowired
	private TradesService tradesService;
	
	@Autowired
	private OrderBookService orderBookService;
	
	//@Test
	public void tickerAPICallTest() {
		Flux<Ticker> flux = tickerService.getTicker("BTC-ETH,BTC-XRP");
		StepVerifier.create(flux)
					.expectNextMatches(ticker -> "BTC-ETH".equals(ticker.getMarket()))
					.expectNextMatches(ticker -> "BTC-XRP".equals(ticker.getMarket()))
					.verifyComplete();
		
	}
	
	//@Test
	public void marketAPICallTest() {
		Flux<MarketAll> flux = marketService.getMarketAll().filter(marketAll -> "KRW-BTC".equals(marketAll.getMarket()));
		StepVerifier.create(flux)
					.expectNextMatches(marketAll -> "비트코인".equals(marketAll.getKoreanName()))
					.verifyComplete();
	}
	
	//@Test
	public void minutesOfCandleAPICallTest() {
		Flux<Minutes> flux = candlesService.getMinutesOfCandles(1, "BTC-ETH", null, 3);
		StepVerifier.create(flux)
					.expectNextMatches(minute -> "BTC-ETH".equals(minute.getMarket()))
					.expectNextCount(2)
					.verifyComplete();
	}
	
	//@Test
	public void daysOfCandleAPICallTest() {
		Flux<Days> flux = candlesService.getDaysOfCandles("BTC-ETH", null, 10, "KRW");
		StepVerifier.create(flux)
					.expectNextMatches(day -> "BTC-ETH".equals(day.getMarket()))
					.expectNextCount(9)
					.verifyComplete();
	}
	
	//@Test
	public void weeksOfCandleAPICallTest() {
		Flux<WeeksAndMonths> flux = candlesService.getWeeksOfCandles("BTC-ETH", null, 3);
		StepVerifier.create(flux)
					.expectNextMatches(week -> "BTC-ETH".equals(week.getMarket()))
					.expectNextCount(2)
					.verifyComplete();
	}
	
	//@Test
	public void monthsOfCandleAPICallTest() {
		Flux<WeeksAndMonths> flux = candlesService.getMonthsOfCandles("BTC-ETH", null, 30);
		StepVerifier.create(flux)
					.expectNextMatches(month -> "BTC-ETH".equals(month.getMarket()))
					.expectNextCount(29)
					.verifyComplete();
	}
	
	//@Test
	public void tradeTicksAPICallTest() {
		Flux<TradesTick> flux = tradesService.getTradesTicks("BTC-ETH", null, 3, null);
		StepVerifier.create(flux)
					.expectNextMatches(tradesTicks -> "BTC-ETH".equals(tradesTicks.getMarket()))
					.expectNextCount(2)
					.verifyComplete();
	}
	
	@Test
	public void orderBookAPICallTest() {
		Flux<OrderBook> flux = orderBookService.getOrderBook("BTC-ETH,BTC-XRP");
		StepVerifier.create(flux)
					.expectNextMatches(orderBook -> "BTC-ETH".equals(orderBook.getMarket()))
					.expectNextCount(1)
					.verifyComplete();
	}
	
}
