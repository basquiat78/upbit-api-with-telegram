package io.basquiat.quotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.quotation.domain.response.market.MarketAll;
import io.basquiat.quotation.service.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;

/**
 * 
 * Functional Route방식이 아닌 Rest방식으로 작성한다.
 * 
 * 업비트에서 거래 가능한 마켓 목록을 요청하는 컨트롤러
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api/market")
@Api(value = "Market Controller", tags = {"Upbit Quotation Market Information Controller"})
public class MarketController {

	@Autowired
	private MarketService marketService;
	
	/**
	 * 
	 * 마켓 코드 조회
	 * 
	 * @return Flux<MarketAll>
	 */
	@ApiOperation(value = "마켓 코드 조회")
	@GetMapping("/all")
	public Flux<MarketAll> findMarket() {
		return marketService.getMarketAll();
	}

}
