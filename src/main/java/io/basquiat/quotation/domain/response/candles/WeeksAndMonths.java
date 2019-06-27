package io.basquiat.quotation.domain.response.candles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 
 * 주/월 단위 응답 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"market", "candleDateTimeUtc", "candleDateTimeKst", "openingPrice", "highPrice", "lowPrice", 
					"tradePrice", "timestamp", "candleAccTradePrice", "candleAccTradeVolume", "firstDayOfPeriod"})
public class WeeksAndMonths extends Candle {

	/**
	 * constructor for inheritance
	 * @param firstDayOfPeriod
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
	private WeeksAndMonths(String firstDayOfPeriod, String market, String candleDateTimeUtc, String candleDateTimeKst, 
						   Double openingPrice, Double highPrice, Double lowPrice, Double tradePrice,
    			    	   long timestamp, Double candleAccTradePrice, Double candleAccTradeVolume){
		super(market, candleDateTimeUtc, candleDateTimeKst, openingPrice, highPrice, lowPrice, tradePrice, timestamp, candleAccTradePrice, candleAccTradeVolume);
		this.firstDayOfPeriod = firstDayOfPeriod;
	}
	
	/** 캔들 기간의 가장 첫 날 */
	@JsonProperty("first_day_of_period")
	private String firstDayOfPeriod;

}
