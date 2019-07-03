package io.basquiat.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.common.util.CommonUtils;
import io.basquiat.exchange.domain.ExchangeQuery;
import io.basquiat.exchange.domain.response.order.Order;
import io.basquiat.exchange.domain.response.order.OrderChance;
import io.basquiat.exchange.domain.response.order.OrderPlace;
import io.basquiat.exchange.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import reactor.core.publisher.Flux;
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
	 * 아니면 security를 적용한다. 실제 이것은 개인적인 어플리케이션이므로 상관없지만...
	 * 
	 * @param market
	 * @return Mono<OrderChance>
	 */
	@ApiOperation(value = "주문 가능 정보 조회 (for owner)")
	@GetMapping("/orders/chance/owner")
	public Mono<OrderChance> orderChanceWithoutRequestHeader(@RequestParam(name = "market", required = true) String market) {
		// queryParam생
		String queryParam = ExchangeQuery.builder()
								   		 .market(CommonUtils.encodingURL(market))
								   		 .build()
								   		 .generateQueryParam();
		return orderService.getOrderChanceWithoutRequestHeader(queryParam);
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
	public Mono<OrderChance> orderChanceWithRequestHeader(@RequestParam(name = "market", required = true) String market, @RequestHeader(name = "Authorization", required = true) String jwt) {
		// queryParam생
		String queryParam = ExchangeQuery.builder()
								   		 .market(CommonUtils.encodingURL(market))
								   		 .build()
								   		 .generateQueryParam();
		return orderService.getOrderChanceWithRequestHeader(queryParam, jwt);
	}
	
	/**
	 * 주문하기
	 * @param exchangeQuery
	 * @return Mono<OrderPlace>
	 */
	@ApiOperation(value = "주문하기 (for owner)")
	@PostMapping("/orders/owner")
	public Mono<OrderPlace> orderPlaceWithoutRequestHeader(@RequestBody ExchangeQuery exchangeQuery) {
		return orderService.requestOrderPlaceWithoutRequestHeader(exchangeQuery);
	}

	/**
	 * 주문하기
	 * @param exchangeQuery
	 * @param jwt
	 * @return Mono<OrderPlace>
	 */
	@ApiOperation(value = "주문하기")
	@PostMapping("/orders")
	public Mono<OrderPlace> orderPlaceWithRequestHeader(@RequestBody ExchangeQuery exchangeQuery, @RequestHeader(name = "Authorization", required = true) String jwt) {
		// jwt가 넘어오는 상황에서는 adjustEncode를 실행한다.
		exchangeQuery.adjustEncode();
		return orderService.requestOrderPlaceWithRequestHeader(exchangeQuery, jwt);
	}
	
	/**
	 * 주문 조회 리스트 요청하기 for owner
	 * @param market
	 * @param state
	 * @param uuIds
	 * @param identifiers
	 * @param page
	 * @param orderBy
	 * @return Flux<Order>
	 */
	@ApiOperation(value = "주문 리스트 조회 (for owner)")
	@GetMapping("/orders/owner")
	public Flux<Order> orderListWithoutRequestHeader(@RequestParam(name = "market", required = false) String market,
													 @ApiParam(value = "wait : 체결 대기 (default) | done : 전체 체결 완료 | cancel : 주문 취소") 
													 @RequestParam(name = "state", required = false) String state,
													 @RequestParam(name = "uuids", required = false) String uuIds,
													 @RequestParam(name = "identifiers", required = false) String identifiers,
													 @RequestParam(name = "page", required = false, defaultValue = "1") int page,
													 @ApiParam(value = "asc : 오름차순 (default) | desc : 내림차순") 
													 @RequestParam(name = "order_by", required = false) String orderBy) {
		// query param 생성
		String queryParam = ExchangeQuery.builder().market(CommonUtils.encodingURL(market))
													   .state(CommonUtils.encodingURL(state))
													   .uuids(uuIds)
													   .identifiers(identifiers)
													   .page(page)
													   .order_by(CommonUtils.encodingURL(orderBy))
													   .build()
													   .generateQueryParam();
		return orderService.getOrderListWithoutRequestHeader(queryParam);
	}
	
	/**
	 * 주문 조회 리스트 요청하기
	 * @param market
	 * @param state
	 * @param uuIds
	 * @param identifiers
	 * @param page
	 * @param orderBy
	 * @param jwt
	 * @return Flux<Order>
	 */
	@ApiOperation(value = "주문 리스트 조회")
	@GetMapping("/orders")
	public Flux<Order> orderListWithRequestHeader(@RequestParam(name = "market", required = false) String market,
												  @RequestParam(name = "state", required = false) String state,
												  @RequestParam(name = "uuids", required = false) String uuIds,
												  @RequestParam(name = "identifiers", required = false) String identifiers,
												  @RequestParam(name = "page", required = false, defaultValue = "1") int page,
												  @RequestParam(name = "order_by", required = false) String orderBy,
												  @RequestHeader(name = "Authorization", required = true) String jwt) {
		// query param 생성
		String queryParam = ExchangeQuery.builder().market(CommonUtils.encodingURL(market))
													   .state(CommonUtils.encodingURL(state))
													   .uuids(uuIds)
													   .identifiers(identifiers)
													   .page(page)
													   .order_by(CommonUtils.encodingURL(orderBy))
													   .build()
													   .generateQueryParam();
		return orderService.getOrderListWithRequestHeader(queryParam, jwt);
	}
	
}
