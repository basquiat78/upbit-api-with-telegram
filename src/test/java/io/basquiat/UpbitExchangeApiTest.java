package io.basquiat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.basquiat.exchange.domain.Account;
import io.basquiat.exchange.service.AccountService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpbitExchangeApiTest {
	
	@Autowired
	private AccountService accountService;
	
	@Test
	public void accountAPICallTest() {
		Flux<Account> flux = accountService.getAccountsWithoutRequestHeader();
		StepVerifier.create(flux)
					.expectNextMatches(account -> "BTC".equals(account.getCurrency()))
					.expectNextCount(4)
					.verifyComplete();
	}
	
}
