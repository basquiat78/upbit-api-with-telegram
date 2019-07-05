package io.basquiat.exchange.domain.response.deposit;

import lombok.Value;

/**
 * 
 * 계좌 생성 요청 응답 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class GenerateAddress {

	/** 요청 성공 여부 */
	private boolean success;

	/** 요청 결과에 대한 메세지 */
	private String message;

}
