package io.basquiat.exchange.domain.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.basquiat.exchange.domain.response.account.Account;
import lombok.Value;

/**
 * 
 * 주문 가능 정보 응답 도메인 Immutable
 * 마켓별 주문 가능 정보를 확인한다.
 * 
 * BidAccount, AskAccount 어짜피 Account랑 같은 도메인으로 굳이 같은 도메인을 여러개 만들 필요가 없다.
 * 
 * created by basquiat
 *
 */
@Value
public class OrderChance {

	/** 매수 수수료 비율 */
	@JsonProperty("bid_fee")
	private String bidFee;

	/** 매도 수수료 비율 */
	@JsonProperty("ask_fee")
	private String askFee;

	/** 마켓 정보 도메인 */
	private Market market;

	/** 매수 시 사용하는 화폐의 계좌 상태 */
	@JsonProperty("bid_account")
	private Account bidAccount;

	/** 매도 시 사용하는 화폐의 계좌 상태 */
	@JsonProperty("ask_account")
	private Account askAccount;

}
