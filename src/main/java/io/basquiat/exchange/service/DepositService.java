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
import io.basquiat.exchange.domain.response.deposit.DepositAddress;
import io.basquiat.exchange.domain.response.deposit.GenerateAddress;
import io.basquiat.exchange.domain.response.withdraw.WithdrawAndDeposit;
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
@Service("deposit")
public class DepositService {

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
	 * 입금 리스트 조회 요청 for owner
	 * @param queryParam
	 * @return Flux<WithdrawAndDeposit>
	 */
	public Flux<WithdrawAndDeposit> getDepositListWithoutRequestHeader(String queryParam) {
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, queryParam);
		return this.getDepositListWithRequestHeader(queryParam, jwt);
	}

	/**
	 * 입금 리스트 조회 요청
	 * @param queryParam
	 * @param jwt
	 * @return Flux<WithdrawAndDeposit>
	 */
	public Flux<WithdrawAndDeposit> getDepositListWithRequestHeader(String queryParam, String jwt) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.DEPOSITLIST.URI + queryParam)
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
	 * 개별 입금 조회 요청 for owner
	 * @param queryParam
	 * @return Mono<WithdrawAndDeposit>
	 */
	public Mono<WithdrawAndDeposit> getDepositWithoutRequestHeader(String queryParam) {
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, queryParam);
		return this.getDepositWithRequestHeader(queryParam, jwt);
	}

	/**
	 * 개별 입금 조회 요청
	 * @param queryParam
	 * @param jwt
	 * @return Mono<WithdrawAndDeposit>
	 */
	public Mono<WithdrawAndDeposit> getDepositWithRequestHeader(String queryParam, String jwt) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.INDIVIDUALDEPOSIT.URI + queryParam)
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
	 * 전체 입금 주소 조회 요청 for owner
	 * @return Flux<DepositAddress>
	 */
	public Flux<DepositAddress> getDepositAddressesWithoutRequestHeader() {
		String jwt = JwtUtils.createJwtWithoutQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY);
		return this.getDepositAddressesWithRequestHeader(jwt);
	}

	/**
	 * 전체 입금 주소 조회 요청
	 * @param jwt
	 * @return Flux<DepositAddress>
	 */
	public Flux<DepositAddress> getDepositAddressesWithRequestHeader(String jwt) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.ALLADDRESSES.URI)
				 			   .header("Authorization", jwt)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("X-Forwarded-Uri : " + cr.headers().asHttpHeaders().get("X-Forwarded-Uri").get(0)))
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
								 				 	if(cr.statusCode().is4xxClientError()) {
								 				 		return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
									 				 }
								 				 	return cr.bodyToFlux(DepositAddress.class);
							   					  }
							   );
	}

	public Mono<DepositAddress> getDepositAddressWithoutRequestHeader(String queryParam) {
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, queryParam);
		return this.getDepositAddressWithRequestHeader(queryParam, jwt);
	}

	public Mono<DepositAddress> getDepositAddressWithRequestHeader(String queryParam, String jwt) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.COINADDRESS.URI + queryParam)
				 			   .header("Authorization", jwt)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("X-Forwarded-Uri : " + cr.headers().asHttpHeaders().get("X-Forwarded-Uri").get(0)))
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMap(cr -> {
					 				 			if(cr.statusCode().is4xxClientError()) {
						 				 			return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
						 				 		}
						 				 		return cr.bodyToMono(DepositAddress.class);
							  				  }
							   );
	}

	/**
	 * 입금 주소 생성 요청 for owner
	 * @param queryParam
	 * @return Mono<GenerateAddress>
	 */
	public Mono<GenerateAddress> generateAddressWithoutRequestHeader(ExchangeQuery exchangeQuery) {
		exchangeQuery.adjustEncode();
		String jwt = JwtUtils.createJwWithQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY, exchangeQuery.generateQueryParam());
		return this.generateAddressWithRequestHeader(exchangeQuery, jwt);
	}

	/**
	 * 입금 주소 생성 요청
	 * @param queryParam
	 * @param jwt
	 * @return Mono<GenerateAddress>
	 */
	public Mono<GenerateAddress> generateAddressWithRequestHeader(ExchangeQuery exchangeQuery, String jwt) {
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .post()
				 			   .uri(ExchangeApiUri.GENERATECOINADDRESS.URI)
				 			   .body(BodyInserters.fromObject(exchangeQuery))
				 			   .header("Authorization", jwt)
				 			   .exchange()
				 			   .doOnSuccess(cr -> log.info("X-Forwarded-Uri : " + cr.headers().asHttpHeaders().get("X-Forwarded-Uri").get(0)))
							   .doOnSuccess(cr -> log.info("Remaining-Req : " + cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMap(cr -> {
					 				 			if(cr.statusCode().is4xxClientError()) {
						 				 			return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
						 				 		}
						 				 		return cr.bodyToMono(GenerateAddress.class);
							  				  }
							   );
	}

}
