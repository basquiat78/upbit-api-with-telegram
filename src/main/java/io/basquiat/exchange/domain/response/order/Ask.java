package io.basquiat.exchange.domain.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * @See Market
 * 
 * Bid와 Ask는 필드가 같지만 구분하기위해서 나눈다.
 * 
 * created by basquiat
 *
 */
@Value
public class Ask {

	/** 화폐를 의미하는 영문 대문자 코드 */
	private String currency;

	/** 주문금액 단위 */
	@JsonProperty("price_unit")
	private String priceUnit;

	/** 최소 매도/매수 금액 */
	@JsonProperty("min_total")
	private Double minTotal;
	
}
