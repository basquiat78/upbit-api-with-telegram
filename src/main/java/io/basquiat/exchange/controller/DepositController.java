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
import io.basquiat.exchange.service.DepositService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * 입금 관련 요청 컨트롤러
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api")
@Api(value = "Deposit Controller", tags = {"Upbit Exchange Deposit Controller"})
public class DepositController {

	@Autowired
	private DepositService depositService;
	
	/**
 	 * 입금 리스트 조회 요청하기 for owner
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
	@ApiOperation(value = "입금 리스트 조회 (for owner)")
	@GetMapping("/deposits/owner")
	public Flux<WithdrawAndDeposit> depositListWithoutRequestHeader(@RequestParam(name = "currency", required = false) String currency,
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
		return depositService.getDepositListWithoutRequestHeader(queryParam);
	}

	/**
 	 * 입금 리스트 조회 요청하기
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
	@ApiOperation(value = "입금 리스트 조회")
	@GetMapping("/deposits")
	public Flux<WithdrawAndDeposit> depositListWithRequestHeader(@RequestParam(name = "currency", required = false) String currency,
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
		return depositService.getDepositListWithRequestHeader(queryParam, jwt);
	}
	
	/**
 	 * 개별 입금 조회 요청하기 for owner
	 * 업비트 openAPI공지에 따라 uuid, txid조회할 수 있도록 처리
	 * @param uuId
	 * @param txId
	 * @param currency
	 * @return Mono<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "개별 입금 조회 (for owner)")
	@GetMapping("/deposit/owner")
	public Mono<WithdrawAndDeposit> depositWithoutRequestHeader(@RequestParam(name = "uuId", required = false) String uuId,
													  	 	 	@RequestParam(name = "txId", required = false) String txId,
													  	 	 	@RequestParam(name = "currency", required = false) String currency) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .uuid(CommonUtils.encodingURL(uuId))
										 .txid(CommonUtils.encodingURL(txId))
										 .currency(CommonUtils.encodingURL(currency))
								   		 .build()
								   		 .generateQueryParam();
		return depositService.getDepositWithoutRequestHeader(queryParam);
	}

	/**
 	 * 개별 입금 조회 요청하기
	 * 업비트 openAPI공지에 따라 uuid, txid조회할 수 있도록 처리
	 * @param uuId
	 * @param txId
	 * @param currency
	 * @param jwt
	 * @return Mono<WithdrawAndDeposit>
	 */
	@ApiOperation(value = "개별 입금 조회")
	@GetMapping("/deposit")
	public Mono<WithdrawAndDeposit> depositWithRequestHeader(@RequestParam(name = "uuId", required = false) String uuId,
											  	 	 		 @RequestParam(name = "txId", required = false) String txId,
											  	 	 		 @RequestParam(name = "currency", required = false) String currency,
											  	 	 		 @RequestHeader(name = "Authorization", required = true) String jwt) {
		// queryParam생성
		String queryParam = ExchangeQuery.builder()
										 .uuid(CommonUtils.encodingURL(uuId))
										 .txid(CommonUtils.encodingURL(txId))
										 .currency(CommonUtils.encodingURL(currency))
								   		 .build()
								   		 .generateQueryParam();
		return depositService.getDepositWithRequestHeader(queryParam, jwt);
	}

}
