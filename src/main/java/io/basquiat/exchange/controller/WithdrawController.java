package io.basquiat.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * 업비트 openAPI공지에 따라 uuid, txid조회할 수 있도록 처리
	 * @param currency
	 * @param uuId
	 * @param txId
	 * @param state
	 * @param limit
	 * @return @RequestHeader(name = "Authorization", required = true) String jwt
	 */
	@ApiOperation(value = "출금 리스트 조회 (for owner)")
	@GetMapping("/withdraws/owner")
	public Flux<WithdrawAndDeposit> withdrawListWithoutRequestHeader(@RequestParam(name = "currency", required = false) String currency,
																  	 @RequestParam(name = "uuId", required = false) String uuId,
																  	 @RequestParam(name = "txId", required = false) String txId,
																  	 @ApiParam(value = "submitting : 처리 중 | submitted : 처리 완료 | almost_accepted : 출금대기중 | rejected : 거부 | accepted : 승인됨 | processing : 처리 중 | done : 완료 | canceled : 취소됨")
																  	 @RequestParam(name = "state", required = false) String state,
																  	 @RequestParam(name = "limit", required = false, defaultValue = "100") int limit) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
										 .uuid(CommonUtils.encodingURL(uuId))
										 .txid(CommonUtils.encodingURL(txId))
										 .state(CommonUtils.encodingURL(state))
										 .limit(limit)
								   		 .build()
								   		 .generateQueryParam();
		return withdrawService.getWithdrawListWithoutRequestHeader(queryParam);
	}

	/**
	 * 출금 리스트 조회 요청하기
	 * 업비트 openAPI공지에 따라 uuid, txid조회할 수 있도록 처리
	 * @param currency
	 * @param uuId
	 * @param txId
	 * @param state
	 * @param limit
	 * @param jwt
	 * @return @RequestHeader(name = "Authorization", required = true) String jwt
	 */
	@ApiOperation(value = "출금 리스트 조회")
	@GetMapping("/withdraws")
	public Flux<WithdrawAndDeposit> withdrawListWithRequestHeader(@RequestParam(name = "currency", required = false) String currency,
														  	 	  @RequestParam(name = "uuId", required = false) String uuId,
														  	 	  @RequestParam(name = "txId", required = false) String txId,
														  	 	  @ApiParam(value = "submitting : 처리 중 | submitted : 처리 완료 | almost_accepted : 출금대기중 | rejected : 거부 | accepted : 승인됨 | processing : 처리 중 | done : 완료 | canceled : 취소됨")
														  	 	  @RequestParam(name = "state", required = false) String state,
														  	 	  @RequestParam(name = "limit", required = false, defaultValue = "100") int limit,
														  	 	  @RequestHeader(name = "Authorization", required = true) String jwt) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .currency(CommonUtils.encodingURL(currency))
										 .uuid(CommonUtils.encodingURL(uuId))
										 .txid(CommonUtils.encodingURL(txId))
										 .state(CommonUtils.encodingURL(state))
										 .limit(limit)
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

}
