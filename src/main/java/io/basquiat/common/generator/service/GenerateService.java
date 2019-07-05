package io.basquiat.common.generator.service;

import org.springframework.stereotype.Service;

import io.basquiat.common.generator.domain.RequestQuery;
import io.basquiat.common.generator.domain.response.TokenInformation;
import io.basquiat.common.util.JwtUtils;
import io.basquiat.exchange.domain.ExchangeQuery;
import reactor.core.publisher.Mono;

/**
 * 
 * jwt Generate Service
 * 
 * created by basquiat
 *
 */
@Service("generateService")
public class GenerateService {

	/**
	 * 넘어온 객체로부터 정보를 추출해 jwt를 생성한다.
	 * @param requestQuery
	 * @return Mono<TokenInformation>
	 */
	public Mono<TokenInformation> jwtGenerate(RequestQuery requestQuery) {
		ExchangeQuery exchangeQuery = requestQuery.getQuery();
		exchangeQuery.adjustEncode();
		String jwt = JwtUtils.createJwWithQueryParameters(requestQuery.getAccessKey(), requestQuery.getSecretKey(), exchangeQuery.generateQueryParam());
		return Mono.just(TokenInformation.builder().jwt(jwt).build());
	}

}
