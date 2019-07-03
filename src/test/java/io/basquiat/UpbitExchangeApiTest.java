package io.basquiat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.basquiat.common.util.CommonUtils;
import io.basquiat.exchange.domain.ExchangeQuery;
import io.basquiat.exchange.domain.response.account.Account;
import io.basquiat.exchange.domain.response.order.Order;
import io.basquiat.exchange.domain.response.order.OrderChance;
import io.basquiat.exchange.service.AccountService;
import io.basquiat.exchange.service.OrderService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpbitExchangeApiTest {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private OrderService orderService;
	
	//@Test
	public void accountAPICallTest() {
		Flux<Account> flux = accountService.getAccountsWithoutRequestHeader();
		StepVerifier.create(flux)
					.expectNextMatches(account -> "BTC".equals(account.getCurrency()))
					.expectNextCount(4)
					.verifyComplete();
	}
	
	//@Test
	public void orderChanceAPICallTest() {
		Mono<OrderChance> mono = orderService.getOrderChanceWithoutRequestHeader("BTC-ETH");
		StepVerifier.create(mono)
					.expectNextMatches(oc -> "BTC-ETH".equals(oc.getMarket().getId()))
					.verifyComplete();
	}
	
	@Test
	public void orderListAPICallTest() {
		String queryParam = ExchangeQuery.builder().state(CommonUtils.encodingURL("done"))
												   .page(1)
												   .build()
												   .generateQueryParam();
		
		Flux<Order> flux = orderService.getOrderListWithoutRequestHeader(queryParam);
		StepVerifier.create(flux)
					.expectNextMatches(order -> "done".equals(order.getState()))
					.expectComplete();
	}
	
}
