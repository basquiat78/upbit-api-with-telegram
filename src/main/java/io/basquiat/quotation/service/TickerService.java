package io.basquiat.quotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.basquiat.common.code.QuotationApiUri;
import io.basquiat.common.exception.ApiException;
import io.basquiat.quotation.domain.response.ticker.Ticker;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * 현재가 정보 서비스
 * 
 * created by basquiat
 *
 */
@Slf4j
@Service("ticker")
public class TickerService {

	@Value("${upbit.api.url}")
	private String UPBIT_API_URL;
	
	@Value("${upbit.api.version}")
	private String UPBIT_API_VERSION;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	/**
	 * get ticker market information from upbit
	 * 
	 * market: e.g. BTC-ETH,BTC-XPM 
	 * 여러개의 마켓에 대한 시세 정보를 가져올때는 구분자 콤마 ','로 붙여서 날린다.
	 * 
	 * @param market
	 * @return Flux<Ticker>
	 */
	public Flux<Ticker> getTicker(String market) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(QuotationApiUri.TICKER.URI, market)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
								 				 	if(cr.statusCode().is4xxClientError()) {
								 				 		return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
									 				 }
								 				 	return cr.bodyToFlux(Ticker.class);
							   					  }
							   );
	}

}
