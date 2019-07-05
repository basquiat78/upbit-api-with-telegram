package io.basquiat.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.common.util.CommonUtils;
import io.basquiat.exchange.domain.ExchangeQuery;
import io.basquiat.exchange.domain.response.withdraw.WithdrawAndDeposit;
import io.basquiat.exchange.domain.response.withdraw.WithdrawChance;
import io.basquiat.exchange.service.WithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * 출금 관련 요청 컨트롤
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api")
@Api(value = "Withdraw Controller", tags = {"Upbit Exchange Withdraw Controller"})
public class WithdrawController {

	@Autowired
	private WithdrawService withdrawService;

	/**
	 * 출금 리스트 조회 요청하기 for owner
	 * 업비트 openAPI공지에 따라 uuids, txids조회할 수 있도록 처리
	 * @param currency
	 * @param state
	 * @param uuIds
	 * @param txIds
	 * @param limit
	 * @param page
	 * @param orderBy
	 * @return Flux<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "출금 리스트 조회 (for owner)")
	@GetMapping("/withdraws/owner")
	public Flux<WithdrawAndDeposit> withdrawListWithoutRequestHeader(@RequestParam(name = "currency", required = false) String currency,
																	 @ApiParam(value = "submitting : 처리 중 | submitted : 처리 완료 | almost_accepted : 출금대기중 | rejected : 거부 | accepted : 승인됨 | processing : 처리 중 | done : 완료 | canceled : 취소됨")
																	 @RequestParam(name = "state", required = false) String state,
																	 @ApiParam(value = "uuid를 구분자 ','를 통해서 붙여서 정보를 보낸다.")
																	 @RequestParam(name = "uuIds", required = false) String uuIds,
																	 @ApiParam(value = "txid를 구분자 ','를 통해서 붙여서 정보를 보낸다.")
																  	 @RequestParam(name = "txIds", required = false) String txIds,
																 	 @RequestParam(name = "limit", required = false, defaultValue = "100") int limit,
																 	 @RequestParam(name = "page", required = false, defaultValue = "1") int page,
																 	 @RequestParam(name = "orderBy", required = false) String orderBy) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
										 .state(CommonUtils.encodingURL(state))
										 .uuids(uuIds)
										 .txids(txIds)
										 .limit(limit)
										 .page(page)
										 .order_by(CommonUtils.encodingURL(orderBy))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawListWithoutRequestHeader(queryParam);
	}

	/**
	 * 출금 리스트 조회 요청하기
	 * 업비트 openAPI공지에 따라 uuids, txids조회할 수 있도록 처리
	 * @param currency
	 * @param state
	 * @param uuIds
	 * @param txIds
	 * @param limit
	 * @param page
	 * @param orderBy
	 * @param jwt
	 * @return Flux<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "출금 리스트 조회")
	@GetMapping("/withdraws")
	public Flux<WithdrawAndDeposit> withdrawListWithRequestHeader(@RequestParam(name = "currency", required = false) String currency,
															 	  @ApiParam(value = "submitting : 처리 중 | submitted : 처리 완료 | almost_accepted : 출금대기중 | rejected : 거부 | accepted : 승인됨 | processing : 처리 중 | done : 완료 | canceled : 취소됨")
														 		  @RequestParam(name = "state", required = false) String state,
														 		  @ApiParam(value = "uuid를 구분자 ','를 통해서 붙여서 정보를 보낸다.")
														 		  @RequestParam(name = "uuIds", required = false) String uuIds,
																  @ApiParam(value = "txid를 구분자 ','를 통해서 붙여서 정보를 보낸다.")
															  	  @RequestParam(name = "txIds", required = false) String txIds,
													  	 		  @RequestParam(name = "limit", required = false, defaultValue = "100") int limit,
													  	 		  @RequestParam(name = "page", required = false, defaultValue = "1") int page,
													  	 		  @RequestParam(name = "orderBy", required = false) String orderBy,
														  	 	  @RequestHeader(name = "Authorization", required = true) String jwt) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
										 .state(CommonUtils.encodingURL(state))
										 .uuids(uuIds)
										 .txids(txIds)
										 .limit(limit)
										 .page(page)
										 .order_by(CommonUtils.encodingURL(orderBy))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawListWithRequestHeader(queryParam, jwt);
	}
	
	/**
	 * 개별 출금 조회 요청하기
	 * 업비트 openAPI공지에 따라 uuid, txid, state조회할 수 있도록 처리
	 * @param currency
	 * @param uuId
	 * @param txId
	 * @param state
	 * @return Mono<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "개별 출금 조회 (for owner)")
	@GetMapping("/withdraw/owner")
	public Mono<WithdrawAndDeposit> withdrawWithoutRequestHeader(@RequestParam(name = "currency", required = false) String currency,
															  	 @RequestParam(name = "uuId", required = false) String uuId,
															  	 @RequestParam(name = "txId", required = false) String txId,
															  	 @ApiParam(value = "submitting : 처리 중 | submitted : 처리 완료 | almost_accepted : 출금대기중 | rejected : 거부 | accepted : 승인됨 | processing : 처리 중 | done : 완료 | canceled : 취소됨")
															  	 @RequestParam(name = "state", required = false) String state) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
										 .uuid(CommonUtils.encodingURL(uuId))
										 .txid(CommonUtils.encodingURL(txId))
										 .state(CommonUtils.encodingURL(state))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawWithoutRequestHeader(queryParam);
	}

	/**
	 * 개별 출금 조회 요청하기
	 * 업비트 openAPI공지에 따라 uuid, txid, state조회할 수 있도록 처리
	 * @param currency
	 * @param uuId
	 * @param txId
	 * @param state
	 * @param jwt
	 * @return Mono<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "개별 출금 조회")
	@GetMapping("/withdraw")
	public Mono<WithdrawAndDeposit> withdrawWithRequestHeader(@RequestParam(name = "currency", required = false) String currency,
												  	 	 	  @RequestParam(name = "uuId", required = false) String uuId,
													  	 	  @RequestParam(name = "txId", required = false) String txId,
													  	 	  @ApiParam(value = "submitting : 처리 중 | submitted : 처리 완료 | almost_accepted : 출금대기중 | rejected : 거부 | accepted : 승인됨 | processing : 처리 중 | done : 완료 | canceled : 취소됨")
													  	 	  @RequestParam(name = "state", required = false) String state,
													  	 	  @RequestHeader(name = "Authorization", required = true) String jwt) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
										 .uuid(CommonUtils.encodingURL(uuId))
										 .txid(CommonUtils.encodingURL(txId))
										 .state(CommonUtils.encodingURL(state))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawWithRequestHeader(queryParam, jwt);
	}
	
	/**
	 * 출금 가능 정보 요청하기 for owner
	 * @param currency
	 * @return Mono<WithdrawChance>
	 */
	@ApiOperation(value = "출금 가능 정보 (for owner)")
	@GetMapping("/withdraws/chance/owner")
	public Mono<WithdrawChance> withdrawWithoutRequestHeader(@RequestParam(name = "currency", required = true) String currency) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawChanceWithoutRequestHeader(queryParam);
	}

	/**
	 * 출금 가능 정보 요청하기
	 * @param currency
	 * @param jwt
	 * @return Mono<WithdrawChance>
	 */
	@ApiOperation(value = "출금 가능 정보")
	@GetMapping("/withdraws/chance")
	public Mono<WithdrawChance> withdrawWithRequestHeader(@RequestParam(name = "currency", required = true) String currency, 
														  @RequestHeader(name = "Authorization", required = true) String jwt) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawChanceWithRequestHeader(queryParam, jwt);
	}
	
	/**
	 * 코인 출금하기 for owner
	 * @param currency
	 * @param amount
	 * @param address
	 * @param secondaryAddress
	 * @param transactionType
	 * @return Mono<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "코인 출금하기 (for owner)")
	@PostMapping("/withdraws/coin/owner")
	public Mono<WithdrawAndDeposit> withdrawCoinWithoutRequestHeader(@RequestParam(name = "currency", required = true) String currency,
														 		 	 @RequestParam(name = "amount", required = true) String amount,
														 		 	 @RequestParam(name = "address", required = true) String address,
														 		 	 @RequestParam(name = "secondaryAddress", required = false) String secondaryAddress,
														 		 	 @ApiParam(value = "출금 유형 default : 일반출금 | default : 일반출금")
														 		 	 @RequestParam(name = "transactionType", required = false) String transactionType) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
										 .amount(CommonUtils.encodingURL(amount))
										 .address(CommonUtils.encodingURL(address))
										 .secondary_address(CommonUtils.encodingURL(secondaryAddress))
										 .transaction_type(CommonUtils.encodingURL(transactionType))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawCoinWithoutRequestHeader(queryParam);
	}

	/**
	 * 코인 출금하기
	 * @param currency
	 * @param jwt
	 * @return Mono<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "코인 출금하기")
	@PostMapping("/withdraws/coin")
	public Mono<WithdrawAndDeposit> withdrawCoinWithRequestHeader(@RequestParam(name = "currency", required = true) String currency,
													 	  		  @RequestParam(name = "amount", required = true) String amount,
													 	  		  @RequestParam(name = "address", required = true) String address,
													 	  		  @RequestParam(name = "secondaryAddress", required = false) String secondaryAddress,
													 	  		  @ApiParam(value = "출금 유형 default : 일반출금 | default : 일반출금")
																  @RequestParam(name = "transactionType", required = false) String transactionType,
																  @RequestHeader(name = "Authorization", required = true) String jwt) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
										 .amount(CommonUtils.encodingURL(amount))
										 .address(CommonUtils.encodingURL(address))
										 .secondary_address(CommonUtils.encodingURL(secondaryAddress))
										 .transaction_type(CommonUtils.encodingURL(transactionType))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawCoinWithRequestHeader(queryParam, jwt);
	}
	
	/**
	 * 원화 출금하기 for owner
	 * @param amount
	 * @return Mono<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "원화 출금하기 (for owner)")
	@PostMapping("/withdraws/krw/owner")
	public Mono<WithdrawAndDeposit> withdrawKrwWithoutRequestHeader(@RequestParam(name = "amount", required = true) String amount) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .amount(CommonUtils.encodingURL(amount))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawKrwWithoutRequestHeader(queryParam);
	}

	/**
	 * 원화 출금하기
	 * @param amount
	 * @param jwt
	 * @return Mono<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "원화 출금하기")
	@PostMapping("/withdraws/krw")
	public Mono<WithdrawAndDeposit> withdrawKrwWithRequestHeader(@RequestParam(name = "amount", required = true) String amount,
																 @RequestHeader(name = "Authorization", required = true) String jwt) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .amount(CommonUtils.encodingURL(amount))
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawKrwWithRequestHeader(queryParam, jwt);
	}

}
