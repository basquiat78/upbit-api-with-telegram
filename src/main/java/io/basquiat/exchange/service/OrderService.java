package io.basquiat.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.basquiat.common.code.ExchangeApiUri;
import io.basquiat.common.exception.ApiException;
import io.basquiat.common.util.JwtUtils;
import io.basquiat.exchange.domain.ExchangeQuery;
import io.basquiat.exchange.domain.response.order.Order;
import io.basquiat.exchange.domain.response.order.OrderChance;
import io.basquiat.exchange.domain.response.order.OrderPlace;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * 주문 관련 서비스
 * 
 * created by basquiat
 *
 */
@Slf4j
@Service("order")
public class OrderService {

	@Value("${upbit.api.url}")
	private String UPBIT_API_URL;
	
	@Value("${upbit.api.version}")
	private String UPBIT_API_VERSION;
	
	@Value("${upbit.access.key}")
	private String UPBIT_ACCESS_KEY;
	
	@Value("${upbit.secret.key}")
	private String UPBIT_SECRET_KEY;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	/**
	 * jwt를 생성하고 주문 가능 정보를 얻어온다. 
	 * @param queryParam
	 * @return Mono<OrderChance>
	 */
	public Mono<OrderChance> getOrderChanceWithoutRequestHeader(String queryParam){
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, queryParam);
		return this.getOrderChanceWithRequestHeader(queryParam, jwt);
	}
	
	/**
	 * 외부 api호출시 헤더를 통해서 받은 jwt사용. 아마도 사용할 일이...
	 * @param queryParam
	 * @param jwt
	 * @return Mono<OrderChance>
	 */
	public Mono<OrderChance> getOrderChanceWithRequestHeader(String queryParam, String jwt){
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.ORDERCHANCE.URI + queryParam)
				 			   .header("Authorization", jwt)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("X-Forwarded-Uri : " + cr.headers().asHttpHeaders().get("X-Forwarded-Uri").get(0)))
		 					   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMap(cr -> {
					 				 			if(cr.statusCode().is4xxClientError()) {
						 				 			return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
						 				 		}
						 				 		return cr.bodyToMono(OrderChance.class);
		   					  				  }
							   );
	}
	
	/**
	 * jwt를 생성하고 주문을 하자.
	 * 단, 테스트는 하지 못함..그냥 상상만으로 (아마 잘 될꺼야) 
	 * @param exchangeQuery
	 * @return Mono<OrderPlace>
	 */
	public Mono<OrderPlace> requestOrderPlaceWithoutRequestHeader(ExchangeQuery exchangeQuery){
		// exchangeQuery 내부 필드 정보 adjustEncode
		exchangeQuery.adjustEncode();
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, exchangeQuery.generateQueryParam());
		return this.requestOrderPlaceWithRequestHeader(exchangeQuery, jwt);
	}
	
	/**
	 * 외부 api호출시 헤더를 통해서 받은 jwt사용. 아마도 사용할 일이... 하지만 일단 만들자....
	 * @param exchangeQuery
	 * @param jwt
	 * @return Mono<OrderPlace>
	 */
	public Mono<OrderPlace> requestOrderPlaceWithRequestHeader(ExchangeQuery exchangeQuery, String jwt){
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .post()
				 			   .uri(ExchangeApiUri.ORDERPLACE.URI)
				 			   .body(BodyInserters.fromObject(exchangeQuery))
				 			   .header("Authorization", jwt)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("X-Forwarded-Uri : " + cr.headers().asHttpHeaders().get("X-Forwarded-Uri").get(0)))
		 					   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMap(cr -> {
					 				 			if(cr.statusCode().is4xxClientError()) {
						 				 			return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
						 				 		}
						 				 		return cr.bodyToMono(OrderPlace.class);
		   					  				  }
							   );
	}

	/**
	 * 주문 조회 리스트 요청
	 * @param queryParam
	 * @return Flux<Order>
	 */
	public Flux<Order> getOrderListWithoutRequestHeader(String queryParam) {
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, queryParam);
		return this.getOrderListWithRequestHeader(queryParam, jwt);
	}

	/**
	 * 주문 조회 리스트 요청 with RequestHeader
	 * @param queryParam
	 * @param jwt
	 * @return Flux<Order>
	 */
	public Flux<Order> getOrderListWithRequestHeader(String queryParam, String jwt) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.ORDERLIST.URI + queryParam)
				 			   .header("Authorization", jwt)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("X-Forwarded-Uri : " + cr.headers().asHttpHeaders().get("X-Forwarded-Uri").get(0)))
		 					   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
								 				 	if(cr.statusCode().is4xxClientError()) {
								 				 		return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
									 				 }
								 				 	return cr.bodyToFlux(Order.class);
							   					  }
							   );
	}

}
