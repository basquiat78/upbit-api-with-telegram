package io.basquiat.exchange.domain.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * @See Market
 * 
 * Bid, Ask 도메인 통
 * 
 * created by basquiat
 *
 */
@Value
public class AskAndBid {

	/** 화폐를 의미하는 영문 대문자 코드 */
	private String currency;

	/** 주문금액 단위 */
	@JsonProperty("price_unit")
	private String priceUnit;

	/** 최소 매도/매수 금액 */
	@JsonProperty("min_total")
	private Double minTotal;

}
