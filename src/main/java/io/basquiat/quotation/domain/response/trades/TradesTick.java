package io.basquiat.quotation.domain.response.trades;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * 당일 체결 내역 응답 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class TradesTick {

	/** 마켓 구분 코드 */
	private String market;

	/** 체결 일자(UTC 기준) */
	@JsonProperty("trade_date_utc")
	private String tradeDateUtc;

	/** 체결 시각(UTC 기준) */
	@JsonProperty("trade_time_utc")
	private String tradeTimeUtc;

	/** 체결 타임스탬프 */
	private long timestamp;

	/** 체결 가격 */
	@JsonProperty("trade_price")
	private Double tradePrice;

	/** 체결량 */
	@JsonProperty("trade_volume")
	private Double tradeVolume;

	/** 전일 종가 */
	@JsonProperty("prev_closing_price")
	private Double prevClosingPrice;

	/** 변화량 */
	@JsonProperty("change_price")
	private Double changePrice;

	/** 매도/매수 */
	@JsonProperty("ask_bid")
	private String askBid;

	/** 체결 번호(Unique) */
	@JsonProperty("sequential_id")
	private long sequentialId;

}
