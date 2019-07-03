package io.basquiat.exchange.domain.response.order;

import lombok.Value;

/**
 * 
 * IndividualOrder 하위 응답 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class Trade {

	/** 마켓의 유일 키 */
	private String market;

	/** 체결의 고유 아이디 */
	private String uuid;

	/** 체결 가격 */
	private String price;

	/** 체결 양 */
	private String volume;

	/** 체결된 총 가격 */
	private String funds;

	/** 체결 종류 */
	private String side;

}
