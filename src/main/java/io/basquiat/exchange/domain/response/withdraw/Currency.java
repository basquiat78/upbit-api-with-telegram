package io.basquiat.exchange.domain.response.withdraw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * WithdrawChance 하위 응답 도메인 Immutable
 * 
 * 화폐 정보를 담는 객체
 * 
 * created by basquiat
 *
 */
@Value
public class Currency {

	/** 화폐를 의미하는 영문 대문자 코드 */
	private String code;

	/** 해당 화폐의 출금 수수료 */
	@JsonProperty("withdraw_fee")
	private String withdrawFee;

	/** 화폐의 코인 여부 */
	@JsonProperty("is_coin")
	private boolean isCoin;

	/** 해당 화폐의 지갑 상태 */
	@JsonProperty("wallet_state")
	private String walletState;

	/** 해당 화폐가 지원하는 입출금 정보 */
	@JsonProperty("wallet_support")
	private List<String> walletSupport;

}
