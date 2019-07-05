package io.basquiat.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.basquiat.common.code.ExchangeApiUri;
import io.basquiat.common.exception.ApiException;
import io.basquiat.common.util.JwtUtils;
import io.basquiat.exchange.domain.response.withdraw.WithdrawAndDeposit;
import io.basquiat.exchange.domain.response.withdraw.WithdrawChance;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * 출금 관련 서비스
 * 
 * created by basquiat
 *
 */
@Slf4j
@Service("withdraw")
public class WithdrawService {

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
	 * 출금 리스트 조회 for owner
	 * @param queryParam
	 * @return Flux<WithdrawAndDeposit>
	 */
	public Flux<WithdrawAndDeposit> getWithdrawListWithoutRequestHeader(String queryParam) {
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, queryParam);
		return this.getWithdrawListWithRequestHeader(queryParam, jwt);
	}

	/**
	 * 출금 리스트 조회
	 * @param queryParam
	 * @param jwt
	 * @return Flux<WithdrawAndDeposit>
	 */
	public Flux<WithdrawAndDeposit> getWithdrawListWithRequestHeader(String queryParam, String jwt) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.WITHDRAWLIST.URI + queryParam)
				 			   .header("Authorization", jwt)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("X-Forwarded-Uri : " + cr.headers().asHttpHeaders().get("X-Forwarded-Uri").get(0)))
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
								 				 	if(cr.statusCode().is4xxClientError()) {
								 				 		return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
									 				 }
								 				 	return cr.bodyToFlux(WithdrawAndDeposit.class);
							   					  }
							   );
	}
	
	/**
	 * 개별 출금 조회 for owner
	 * @param queryParam
	 * @return Mono<WithdrawAndDeposit>
	 */
	public Mono<WithdrawAndDeposit> getWithdrawWithoutRequestHeader(String queryParam) {
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, queryParam);
		return this.getWithdrawWithRequestHeader(queryParam, jwt);
	}

	/**
	 * 개별 출금 조회
	 * @param queryParam
	 * @param jwt
	 * @return Mono<WithdrawAndDeposit>
	 */
	public Mono<WithdrawAndDeposit> getWithdrawWithRequestHeader(String queryParam, String jwt) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.INDIVIDUALWITHDRAW.URI + queryParam)
				 			   .header("Authorization", jwt)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("X-Forwarded-Uri : " + cr.headers().asHttpHeaders().get("X-Forwarded-Uri").get(0)))
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMap(cr -> {
					 				 			if(cr.statusCode().is4xxClientError()) {
						 				 			return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
						 				 		}
						 				 		return cr.bodyToMono(WithdrawAndDeposit.class);
							  				  }
							   );
	}

	/**
	 * 출금 가능 정보 for owner
	 * @param queryParam
	 * @return Mono<WithdrawChance>
	 */
	public Mono<WithdrawChance> getWithdrawChanceWithoutRequestHeader(String queryParam) {
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, queryParam);
		return this.getWithdrawChanceWithRequestHeader(queryParam, jwt);
	}

	/**
	 * 출금 가능 정보
	 * @param queryParam
	 * @param jwt
	 * @return Mono<WithdrawChance>
	 */
	public Mono<WithdrawChance> getWithdrawChanceWithRequestHeader(String queryParam, String jwt) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.WITHDRAWCHANCE.URI + queryParam)
				 			   .header("Authorization", jwt)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("X-Forwarded-Uri : " + cr.headers().asHttpHeaders().get("X-Forwarded-Uri").get(0)))
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMap(cr -> {
					 				 			if(cr.statusCode().is4xxClientError()) {
						 				 			return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
						 				 		}
						 				 		return cr.bodyToMono(WithdrawChance.class);
							  				  }
							   );
	}

}
