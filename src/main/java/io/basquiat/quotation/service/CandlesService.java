package io.basquiat.quotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.basquiat.common.code.QuotationApiUri;
import io.basquiat.common.exception.ApiException;
import io.basquiat.common.util.CommonUtils;
import io.basquiat.quotation.domain.QuotationQuery;
import io.basquiat.quotation.domain.response.candles.Days;
import io.basquiat.quotation.domain.response.candles.Minutes;
import io.basquiat.quotation.domain.response.candles.WeeksAndMonths;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * 캔들 차트 조회 서비스
 * 
 * created by basquiat
 *
 */
@Slf4j
@Service("candles")
public class CandlesService {

	@Value("${upbit.api.url}")
	private String UPBIT_API_URL;
	
	@Value("${upbit.api.version}")
	private String UPBIT_API_VERSION;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	/**
	 * 1분봉 캔들 차트 정보 가져오기
	 * @param unit
	 * @param market
	 * @param to
	 * @param count
	 * @return Flux<Minutes>
	 */
	public Flux<Minutes> getMinutesOfCandles(int unit, String market, String to, int count) {
		//query param으로 객체를 생성한다.
		//webclient의 header를 직접 세팅하는 방법도 있지만 넘어오는 파라미터에 대한 정보를 객체를 통해서 생성하는 방식으로 간다.
		String candleQuery = QuotationQuery.builder()
										   .market(market)
										   .to(CommonUtils.encodingURL(to))
										   .count(count)
										   .build()
										   .generateQueryParam();
		// 요청 수 제한을 체크하기 위해 header정보로부터 로그 체크
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
							   .uri(QuotationApiUri.CANDLES_MINUTES.URI + candleQuery, unit)
							   .exchange()
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
									 				 if(cr.statusCode().is4xxClientError()) {
									 					 return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
									 				 }
									 				 	return cr.bodyToFlux(Minutes.class);
				   							  	  }
							   );
	}

	/**
	 * 일봉 캔들 차트 정보 가져오
	 * @param market
	 * @param to
	 * @param count
	 * @param convertingPriceUnit
	 * @return Flux<Days>
	 */
	public Flux<Days> getDaysOfCandles(String market, String to, int count, String convertingPriceUnit) {
		//query param으로 객체를 생성한다.
		//webclient의 header를 직접 세팅하는 방법도 있지만 넘어오는 파라미터에 대한 정보를 객체를 통해서 생성하는 방식으로 간다.
		String candleQuery = QuotationQuery.builder()
										   .market(market)
										   .to(CommonUtils.encodingURL(to))
										   .count(count)
										   .convertingPriceUnit(convertingPriceUnit)
										   .build()
										   .generateQueryParam();

		// 요청 수 제한을 체크하기 위해 header정보로부터 로그 체크
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
							   .uri(QuotationApiUri.CANDLES_DAYS.URI + candleQuery)
							   .exchange()
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
				 				 					if(cr.statusCode().is4xxClientError()) {
							 				 			return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
							 				 		}
							 				 		return cr.bodyToFlux(Days.class);
								 				  }
							   );
	}
	
	/**
	 * 주봉 캔들 차트 가져오기
	 * @param market
	 * @param to
	 * @param count
	 * @return Flux<WeeksAndMonths>
	 */
	public Flux<WeeksAndMonths> getWeeksOfCandles(String market, String to, int count) {
		//query param으로 객체를 생성한다.
		//webclient의 header를 직접 세팅하는 방법도 있지만 넘어오는 파라미터에 대한 정보를 객체를 통해서 생성하는 방식으로 간다.
		String candleQuery = QuotationQuery.builder()
										   .market(market)
										   .to(CommonUtils.encodingURL(to))
										   .count(count)
										   .build()
										   .generateQueryParam();
		
		// 요청 수 제한을 체크하기 위해 header정보로부터 로그 체크
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
							   .uri(QuotationApiUri.CANDLES_WEEKS.URI + candleQuery)
							   .exchange()
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
												 	if(cr.statusCode().is4xxClientError()) {
												 		return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
												 	}
												 	return cr.bodyToFlux(WeeksAndMonths.class);
						   						  }
							   );
	}
	
	/**
	 * 월봉 캔들 차트 가져오기
	 * @param market
	 * @param to
	 * @param count
	 * @return Flux<WeeksAndMonths>
	 */
	public Flux<WeeksAndMonths> getMonthsOfCandles(String market, String to, int count) {
		//query param으로 객체를 생성한다.
		//webclient의 header를 직접 세팅하는 방법도 있지만 넘어오는 파라미터에 대한 정보를 객체를 통해서 생성하는 방식으로 간다.
		String candleQuery = QuotationQuery.builder()
										   .market(market)
										   .to(CommonUtils.encodingURL(to))
										   .count(count)
										   .build()
										   .generateQueryParam();
		
		// 요청 수 제한을 체크하기 위해 header정보로부터 로그 체크
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
							   .uri(QuotationApiUri.CANDLES_MONTHS.URI + candleQuery)
							   .exchange()
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
												 	if(cr.statusCode().is4xxClientError()) {
												 		return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
												 	}
											 		return cr.bodyToFlux(WeeksAndMonths.class);
						   						  }
							   );
	}
	
}
