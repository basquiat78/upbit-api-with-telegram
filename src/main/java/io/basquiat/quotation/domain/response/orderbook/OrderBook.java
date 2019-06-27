package io.basquiat.quotation.domain.response.orderbook;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * 호가 정보 조회 응답 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class OrderBook {

	/** 마켓 코드 */
	private String market;
	
	/** 호가 생성 시각 */
	private long timestamp;
	
	/** 호가 매도 총 잔량 */
	@JsonProperty("total_ask_size")
	private Double totalAskSize;
	
	/** 호가 매수 총 잔량 */
	@JsonProperty("total_bid_size")
	private Double totalBidSize;
	
	/** 호가 */
	@JsonProperty("orderbook_units")
	private List<OrderBookUnit> orderbookUnits;

}
