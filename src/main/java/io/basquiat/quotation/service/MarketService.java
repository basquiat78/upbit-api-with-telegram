package io.basquiat.quotation.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.basquiat.quotation.common.code.QuotationApiUri;
import io.basquiat.quotation.domain.MarketAllStore;
import io.basquiat.quotation.domain.response.MarketAll;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * created by basquiat
 *
 * 업비트에서 거래 가능한 마켓 목록
 *
 */
@Slf4j
@Service("market")
public class MarketService {

	@Value("${upbit.api.url}")
	private String UPBIT_API_URL;
	
	@Value("${upbit.api.version}")
	private String UPBIT_API_VERSION;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	/**
	 * on meoery marketAll list from upbit
	 * @return Mono<Void>
	 */
	public Mono<Void> marketAllOnMemory(List<MarketAll> marketAllList) {
		log.info("market all from Upbit : " + marketAllList);
		MarketAllStore.onMarketAllStore(marketAllList);
		return Mono.empty();
	}
	
	/**
	 * marekt all information get from memory
	 * @return Mono<Map<String, MarketAll>>
	 */
	public Mono<Map<String, MarketAll>> getMarketAllFromMemory() {
		return Mono.just(MarketAllStore.getMarketAllStore());
	}
	
	/**
	 * upbit api call and set MarketallStore
	 * @return Mono<Void>
	 */
	public Mono<Void> setUpMarketAllStore() {
		Flux<MarketAll> flux = webClientBuilder.build().get()
									    			   .uri(UPBIT_API_URL + UPBIT_API_VERSION + QuotationApiUri.MARKET_ALL.URI)
													   .retrieve()
													   .bodyToFlux(MarketAll.class);
		flux.collectList().doOnNext(marketAllList -> this.marketAllOnMemory(marketAllList)).subscribe();
		return Mono.empty();
	}

}
