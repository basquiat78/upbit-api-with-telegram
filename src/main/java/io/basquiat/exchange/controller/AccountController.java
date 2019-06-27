package io.basquiat.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.exchange.domain.Account;
import io.basquiat.exchange.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;

/**
 * 
 * 자산 조회 요청 컨트롤
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api")
@Api(value = "Account Controller", tags = {"Upbit Exchange Account Controller"})
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	/**
	 * 
	 * 자산 조회 for api
	 * 
	 * @return Flux<Account>
	 */
	@ApiOperation(value = "전체 계좌 조회")
	@GetMapping("/accounts")
	public Flux<Account> accounts(@RequestHeader(name = "Authorization", required = true) String jwt) {
		return accountService.getAccountsWithRequestHeader(jwt);
	}

}
