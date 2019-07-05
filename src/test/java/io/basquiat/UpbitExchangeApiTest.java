package io.basquiat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.basquiat.common.util.CommonUtils;
import io.basquiat.exchange.domain.ExchangeQuery;
import io.basquiat.exchange.domain.response.account.Account;
import io.basquiat.exchange.domain.response.order.IndividualOrder;
import io.basquiat.exchange.domain.response.order.Order;
import io.basquiat.exchange.domain.response.order.OrderChance;
import io.basquiat.exchange.domain.response.withdraw.WithdrawChance;
import io.basquiat.exchange.service.AccountService;
import io.basquiat.exchange.service.OrderService;
import io.basquiat.exchange.service.WithdrawService;
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
	
	@Autowired
	private WithdrawService withdrawService;
	
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
	
	//@Test
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
	
	//@Test
	public void individualOrderAPICallTest() {
		String uuId = "5e7f703d-2d2a-462d-a1d0-d16f0fb1d363";
		String queryParam = ExchangeQuery.builder().uuid(CommonUtils.encodingURL(uuId))
												   .build()
												   .generateQueryParam();
		Mono<IndividualOrder> mono = orderService.getIndividualOrderWithoutRequestHeader(queryParam);
		StepVerifier.create(mono)
					.expectNextMatches(io -> uuId.equals(io.getUuId()))
					.expectComplete();
	}
	
	@Test
	public void withdrawChanceAPICallTest() {
		String currency = "BTC";
		String queryParam = ExchangeQuery.builder().uuid(CommonUtils.encodingURL(currency))
												   .build()
												   .generateQueryParam();
		Mono<WithdrawChance> mono = withdrawService.getWithdrawChanceWithoutRequestHeader(queryParam);
		StepVerifier.create(mono)
					.expectNextMatches(wc -> wc.getMemberLevel().getSecurityLevel() == 4)
					.expectComplete();
	}
	
}
