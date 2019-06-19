package io.basquiat.quotation.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * 코인별 시세 정보 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class Ticker {

	/** */
	private String market;
	
	/** */
	@JsonProperty("trade_date")
	private String tradeDate;
	
	/** */
	@JsonProperty("trade_time")
	private String tradeTime;
	
	/** */
	@JsonProperty("trade_date_kst")
	private String tradeDateKst;
	
	/** */
	@JsonProperty("trade_time_kst")
	private String tradeTimeKst;
	
	/** */
	@JsonProperty("trade_timestamp")
	private long tradeTimestamp;
	
	/** 시가 */
	@JsonProperty("opening_price")
	private Double openingPrice;
	
	/** 고가 */
	@JsonProperty("high_price")
	private Double highPrice;
	
	/** 저가 */
	@JsonProperty("low_price")
	private Double lowPrice;
	
	/** 종가 */
	@JsonProperty("trade_price")
	private Double tradePrice;

	/** 전일 종가 */
	@JsonProperty("prev_closing_price")
	private Double prevClosingPrice;
	
	/** EVEN : 보합 | RISE : 상승 | FALL : 하락 */
	private String change;
	
	/** 변화액의 절대값 */
	@JsonProperty("change_price")
	private Double changePrice;
	
	/** 변화율의 절대값 */
	@JsonProperty("change_rate")
	private Double changeRate;
	
	/** 부호가 있는 변화액 */
	@JsonProperty("signed_change_price")
	private Double signedChangePrice;
	
	/** 부호가 있는 변화 */
	@JsonProperty("signed_change_rate")
	private Double signedChangeRate;
	
	/** 가장 최근 거래량 */
	@JsonProperty("trade_volume")
	private Double tradeVolume;
	
	/** 누적 거래대금(UTC 0시 기준) */
	@JsonProperty("acc_trade_price")
	private Double accTradePrice;
	
	/** 24시간 누적 거래대금 */
	@JsonProperty("acc_trade_price_24h")
	private Double accTradePrice24h;
	
	/** 누적 거래량(UTC 0시 기준) */
	@JsonProperty("acc_trade_volume")
	private Double accTradeVolume;
	
	/** 24시간 누적 거래대금 */
	@JsonProperty("acc_trade_volume_24h")
	private Double accTradeVolume24h;
	
	/** 52주 신고가 */
	@JsonProperty("highest_52_week_price")
	private Double highest52WeekPrice;
	
	/** 52주 신고가 달성일 */
	@JsonProperty("highest_52_week_date")
	private String highest52WeekDate;
	
	/** 52주 신가 달성일 */
	@JsonProperty("lowest_52_week_price")
	private Double lowest52WeekPrice;
	
	/** 52주 신가 달성일 */
	@JsonProperty("lowest_52_week_date")
	private String lowest52WeekDate;
	
	/** 타임스탬프 */
	private long timestamp; 
	
}
