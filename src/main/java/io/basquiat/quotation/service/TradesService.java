package io.basquiat.quotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.basquiat.common.code.QuotationApiUri;
import io.basquiat.common.exception.ApiException;
import io.basquiat.common.util.CommonUtils;
import io.basquiat.quotation.domain.QuotationQuery;
import io.basquiat.quotation.domain.response.trades.TradesTick;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * 당일 체결 내역 정보 서비스
 * 
 * created by basquiat
 *
 */
@Slf4j
@Service("trades")
public class TradesService {

	@Value("${upbit.api.url}")
	private String UPBIT_API_URL;
	
	@Value("${upbit.api.version}")
	private String UPBIT_API_VERSION;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	public Flux<TradesTick> getTradesTicks(String market, String to, int count, String cursor) {
		//query param으로 객체를 생성한다.
		//webclient의 header를 직접 세팅하는 방법도 있지만 넘어오는 파라미터에 대한 정보를 객체를 통해서 생성하는 방식으로 간다.
		String tradesTicksQuery = QuotationQuery.builder()
												.market(market)
												.to(CommonUtils.encodingURL(to))
												.count(count)
												.cursor(cursor)
												.build()
												.generateQueryParam();
		
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(QuotationApiUri.TRADE_TICK.URI + tradesTicksQuery)
				 			   .exchange()
							   .doOnSuccess(cr -> log.info(cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
								 				 	if(cr.statusCode().is4xxClientError()) {
								 				 		return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
									 				 }
								 				 	return cr.bodyToFlux(TradesTick.class);
							   					  }
							   );
	}

}
