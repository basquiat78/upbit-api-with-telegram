package io.basquiat.quotation.domain.response.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * 호가 정보 Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class OrderBookUnit {

	/** 매도호가 */
	@JsonProperty("ask_price")
	private Double askPrice;

	/** 매수호가 */
	@JsonProperty("bid_price")
	private Double bidPrice;

	/** 매도 잔량 */
	@JsonProperty("ask_size")
	private Double askSize;

	/** 매수 잔량 */
	@JsonProperty("bid_size")
	private Double bidSize;

}
