package io.basquiat.exchange.domain.response.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * 입금 주소 정보 응답 도메인 Immutable
 * created by basquiat
 *
 */
@Value
public class DepositAddress {

	/** 화폐를 의미하는 영문 대문자 코드 */
	private String currency;

	/** 입금 주소 */
	@JsonProperty("deposit_address")
	private String depositAddress;

	/** 2차 입금 주소 */
	@JsonProperty("secondary_address")
	private String secondaryAddress;

}
