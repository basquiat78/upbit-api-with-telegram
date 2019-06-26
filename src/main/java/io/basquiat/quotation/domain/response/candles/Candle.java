package io.basquiat.quotation.domain.response.candles;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * Common Candle Response Domain
 * 
 * created by basquiat
 *
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Candle {

	/** 마켓명 */
    private String market;
    
    /** 캔들 기준 시각(UTC 기준) */
	@JsonProperty("candle_date_time_utc")
    private String candleDateTimeUtc;
    
	/** 캔들 기준 시각(KST 기준) */
	@JsonProperty("candle_date_time_kst")
    private String candleDateTimeKst;
    
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
    
    /** 해당 캔들에서 마지막 틱이 저장된 시각 */
    private long timestamp;
    
	/** 누적 거래 금액 */
	@JsonProperty("candle_acc_trade_price")
    private Double candleAccTradePrice;
    
    /** 누적 거래량 */
	@JsonProperty("candle_acc_trade_volume")
    private Double candleAccTradeVolume;
    
}
