package io.basquiat.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.basquiat.common.code.ExchangeApiUri;
import io.basquiat.common.exception.ApiException;
import io.basquiat.common.util.JwtUtils;
import io.basquiat.exchange.domain.response.account.Account;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * 보유한 자산 리스트 서비스
 * 
 * created by basquiat
 *
 */
@Slf4j
@Service("accounts")
public class AccountService {

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
	 * 
	 * 자산 조회 without requestHeader
	 * 
	 * 환경 설정의 키를 통해서 jwt를 생성하고 요청한다. for owner 
	 * 
	 * @return Flux<Account>
	 */
	public Flux<Account> getAccountsWithoutRequestHeader(){
		String jwt = JwtUtils.createJwtWithoutQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY);
		return this.getAccountsWithRequestHeader(jwt);
	}

	/**
	 * api를 통해서 받은 jwt로 요청한다. (필요할까??)
	 * @param jwt
	 * @return Flux<Account>
	 */
	public Flux<Account> getAccountsWithRequestHeader(String jwt){
		return webClientBuilder.baseUrl(UPBIT_API_URL + UPBIT_API_VERSION)
							   .build()
							   .get()
				 			   .uri(ExchangeApiUri.ACCOUNTS.URI)
				 			   .header("Authorization", jwt)
				 			   .exchange()
							   .doOnSuccess(cr -> log.info(cr.headers().asHttpHeaders().get("Remaining-Req").get(0)))
							   .flatMapMany(cr -> {
								 				 	if(cr.statusCode().is4xxClientError()) {
								 				 		return cr.bodyToMono(String.class).flatMap(body -> Mono.error(new ApiException(cr.statusCode(), body)) );
									 				 }
								 				 	return cr.bodyToFlux(Account.class);
							   					  }
							   );
		
	}

}
