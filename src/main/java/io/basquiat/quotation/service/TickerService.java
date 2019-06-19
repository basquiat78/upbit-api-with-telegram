package io.basquiat.quotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.basquiat.quotation.common.code.QuotationApiUri;
import io.basquiat.quotation.domain.response.Ticker;
import reactor.core.publisher.Flux;

/**
 * 
 * created by basquiat
 *
 * 현재가 정보 서비
 *
 */
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
		return webClientBuilder.build().get()
						 			   .uri(UPBIT_API_URL + UPBIT_API_VERSION + QuotationApiUri.TICKER.URI, market)
									   .retrieve()
									   .bodyToFlux(Ticker.class);
	}
	
}
