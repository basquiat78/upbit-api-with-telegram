package io.basquiat.exchange.domain.response.withdraw;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * WithdrawChance 하위 응답 도메인 Immutable
 * 
 * 출금 제약 정보를 담는 객체
 * 
 * created by basquiat
 *
 */
@Value
public class WithdrawLimit {

	/** 화폐를 의미하는 영문 대문자 코드 */
	private String currency;

	/** 출금 최소 금액/수량 */
	private String minimum;

	/** 1회 출금 한도 */
	private String onetime;

	/** 1일 출금 한도 */
	private String daily;

	/** 1일 잔여 출금 한도 */
	@JsonProperty("remaining_daily")
	private String remainingDaily;

	/** 통합 1일 잔여 출금 한도 */
	@JsonProperty("remaining_daily_krw")
	private String remainingDailyKrw;

	/** 출금 금액/수량 소수점 자리 수 */
	private Integer fixed;

	/** 출금 지원 여부 */
	@JsonProperty("can_withdraw")
	private boolean canWithdraw;

}
