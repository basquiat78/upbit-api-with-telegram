package io.basquiat.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.exchange.domain.response.order.OrderChance;
import io.basquiat.exchange.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;

/**
 * 
 * 주문 관련 요청 컨트롤
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api")
@Api(value = "Order Controller", tags = {"Upbit Exchange Order Controller"})
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	/**
	 * 
	 * 실제 보안상 이 api는 오픈하면 안된다.
	 * 
	 * @param market
	 * @return Mono<OrderChance>
	 */
	@ApiOperation(value = "주문 가능 정보 조회")
	@GetMapping("/orders/chance/owner")
	public Mono<OrderChance> orderChanceWithoutHeader(@RequestParam(name = "market", required = true) String market) {
		return orderService.getOrderChanceWithoutRequestHeader(market);
	}
	
	/**
	 * 
	 * 외부 api 호출용. 실제 jwt만들기가 눅눅치 않다.
	 * 
	 * @param market
	 * @param jwt
	 * @return Mono<OrderChance>
	 */
	@ApiOperation(value = "주문 가능 정보 조회")
	@GetMapping("/orders/chance")
	public Mono<OrderChance> orderChanceWithoutHeader(@RequestParam(name = "market", required = true) String market, @RequestHeader(name = "Authorization", required = true) String jwt) {
		return orderService.getOrderChanceWithoutRequestHeader(market);
	}

}
