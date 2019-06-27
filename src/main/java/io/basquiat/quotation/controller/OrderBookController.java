package io.basquiat.quotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.quotation.domain.response.orderbook.OrderBook;
import io.basquiat.quotation.service.OrderBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import reactor.core.publisher.Flux;

/**
 * 
 * Functional Route방식이 아닌 Rest방식으로 작성한다.
 * 
 * 시세 호가 정보(Orderbook)를 요청하는 컨트롤러
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api")
@Api(value = "OrderBook Controller", tags = {"Upbit Quotation Orderbook Controller"})
public class OrderBookController {

	@Autowired
	private OrderBookService orderBookService;
	
	/**
	 * 
	 * 호가 정보
	 * 
	 * @param market
	 * @return Flux<OrderBook>
	 */
	@ApiOperation(value = "호가 정보 데이터")
	@GetMapping("/orderbook")
	public Flux<OrderBook> findOrderBook(@ApiParam(value = "e.g BTC-ETH or array BTC-ETH,BTC-XRP") @RequestParam(name = "market", required = true) String market) {
		return orderBookService.getOrderBook(market);
	}

}
