package io.basquiat.exchange.domain.response.withdraw;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.basquiat.exchange.domain.response.account.Account;
import lombok.Value;

/**
 * 
 * 출금 가능 정보 응답 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class WithdrawChance {

	/** 사용자의 보안등급 정보 */
	@JsonProperty("member_level")
	private MemberLevel memberLevel;

	/** 화폐 정보 */
	private Currency currency;

	/** 사용자의 계좌 정보 */
	private Account account;
	
	/** 출금 제약 정보 */
	@JsonProperty("withdraw_limit")
	private WithdrawLimit withdrawLimit;

}
