package io.basquiat.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.basquiat.common.code.ExchangeApiUri;
import io.basquiat.common.exception.ApiException;
import io.basquiat.common.util.CommonUtils;
import io.basquiat.common.util.JwtUtils;
import io.basquiat.exchange.domain.ExchangeQuery;
import io.basquiat.exchange.domain.response.order.OrderChance;
import lombok.extern.slf4j.Slf4j;
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
	 * @param market
	 * @return Mono<OrderChance>
	 */
	public Mono<OrderChance> getOrderChanceWithoutRequestHeader(String market){
		// query param 생성
		String queryParam = ExchangeQuery.builder()
								   		 .market(CommonUtils.encodingURL(market))
								   		 .build()
								   		 .generateQueryParam();
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, queryParam);
		return this.getOrderChanceWithRequestHeader(market, jwt);
	}
	
	/**
	 * 외부 api호출시 헤더를 통해서 받은 jwt사용. 아마도 사용할 일이...
	 * @param market
	 * @param jwt
	 * @return Mono<OrderChance>
	 */
	public Mono<OrderChance> getOrderChanceWithRequestHeader(String market, String jwt){
		// query param 생성
		String orderChanceQuery = ExchangeQuery.builder()
											   .market(CommonUtils.encodingURL(market))
											   .build()
											   .generateQueryParam();
		
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.ORDERCHANCE.URI + orderChanceQuery)
				 			   .header("Authorization", jwt)
				 			   .exchange()
							   .doOnSuccess(cr -> log.info(cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMap(cr -> {
					 				 			if(cr.statusCode().is4xxClientError()) {
						 				 			return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
						 				 		}
						 				 		return cr.bodyToMono(OrderChance.class);
		   					  				  }
							   );
	}
	
}
