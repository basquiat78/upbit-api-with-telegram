package io.basquiat.quotation.domain.response.candles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 
 * 분 단위 봉 응답 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder({"market", "candleDateTimeUtc", "candleDateTimeKst", "openingPrice", "highPrice", "lowPrice", 
					"tradePrice", "timestamp", "candleAccTradePrice", "candleAccTradeVolume", "unit"})
public class Minutes extends Candle {

	/**
	 * constructor for inheritance
	 * @param unit
	 * @param market
	 * @param candleDateTimeUtc
	 * @param candleDateTimeKst
	 * @param openingPrice
	 * @param highPrice
	 * @param lowPrice
	 * @param tradePrice
	 * @param timestamp
	 * @param candleAccTradePrice
	 * @param candleAccTradeVolume
	 */
	@JsonCreator
	private Minutes(int unit, String market, String candleDateTimeUtc, String candleDateTimeKst, Double openingPrice, Double highPrice, Double lowPrice, Double tradePrice,
    			    long timestamp, Double candleAccTradePrice, Double candleAccTradeVolume){
		super(market, candleDateTimeUtc, candleDateTimeKst, openingPrice, highPrice, lowPrice, tradePrice, timestamp, candleAccTradePrice, candleAccTradeVolume);
		this.unit = unit;
	}
	
	/** 분 단위(유닛) */
	private int unit;
	
}
