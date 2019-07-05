package io.basquiat.exchange.domain.response.withdraw;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * WithdrawChance 하위 응답 도메인 Immutable
 * 
 *  사용자의 보안등급 정보를 담는 객체
 * 
 */
@Value
public class MemberLevel {
	
	/** 사용자의 보안등급 */
	@JsonProperty("security_level")
	private Integer securityLevel;

	/** 사용자의 보안등급 사용자의 수수료등급*/
	@JsonProperty("fee_level")
	private Integer feeLevel;
    
	/** 사용자의 이메일 인증 여부 */
	@JsonProperty("email_verified")
	private boolean emailVerified;

	/** 사용자의 실명 인증 여부 */
	@JsonProperty("identity_auth_verified")
	private boolean identityAuthVerified;

	/** 사용자의 계좌 인증 여부 */
	@JsonProperty("bank_account_verified")
	private boolean bankAccountVerified;

	/** 사용자의 카카오페이 인증 여부 */
	@JsonProperty("kakao_pay_auth_verified")
	private boolean kakaoPayAuthVerified;

	/** 사용자의 계정 보호 상태 */
	private boolean locked;

	/** 사용자의 출금 보호 상태 */
	@JsonProperty("wallet_locked")
	private boolean walletLocked;

}
