package io.basquiat.quotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.quotation.domain.response.ticker.Ticker;
import io.basquiat.quotation.service.TickerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import reactor.core.publisher.Flux;

/**
 * 
 * Functional Route방식이 아닌 Rest방식으로 작성한다.
 * 
 * 현재가 정보를 요청하는 컨트롤러
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api")
@Api(value = "Ticker Controller", tags = {"Upbit Quotation Ticker Controller"})
public class TickerController {

	@Autowired
	private TickerService tickerService;
	
	/**
	 * 
	 * 현재가 정보
	 * 
	 * @param market
	 * @return Flux<Ticker>
	 */
	@ApiOperation(value = "현재가 정보 데이터")
	@GetMapping("/ticker")
	public Flux<Ticker> findTicker(@ApiParam(value = "e.g BTC-ETH or array BTC-ETH,BTC-XRP") @RequestParam(name = "market", required = true) String market) {
		return tickerService.getTicker(market);
	}

}
