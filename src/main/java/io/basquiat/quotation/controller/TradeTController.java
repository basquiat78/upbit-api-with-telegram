package io.basquiat.quotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.quotation.domain.response.trades.TradesTick;
import io.basquiat.quotation.service.TradesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import reactor.core.publisher.Flux;

/**
 * 
 * Functional Route방식이 아닌 Rest방식으로 작성한다.
 * 
 * 당일 체결 내역 정보를 요청하는 컨트롤러
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api/trades")
@Api(value = "Trades Ticks Controller", tags = {"Upbit Quotation Trades Ticks Controller"})
public class TradeTController {

	@Autowired
	private TradesService tradesService;
	
	/**
	 * 
	 * @param market
	 * @param to
	 * @param count
	 * @param cursor
	 * @return Flux<TradesTick>
	 */
	@ApiOperation(value = "당일 체결 내역 정보 데이터")
	@GetMapping("/ticks")
	public Flux<TradesTick> findTradesTicks(@RequestParam(name = "market", required = true) String market,
									   	   	@ApiParam(value = "[HHmmss 또는 HH:mm:ss]. 비워서 요청시 가장 최근 데이터") @RequestParam(name = "to", required = false) String to,
									   	   	@RequestParam(name = "count", required = false, defaultValue = "1") int count,
									   	   	@RequestParam(name = "cursor", required = false) String cursor) {
		return tradesService.getTradesTicks(market, to, count, cursor);
	}

}
