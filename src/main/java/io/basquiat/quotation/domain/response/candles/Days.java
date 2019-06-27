package io.basquiat.quotation.domain.response.candles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 
 * 일(day) 봉 응답 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"market", "candleDateTimeUtc", "candleDateTimeKst", "openingPrice", "highPrice", "lowPrice", "tradePrice", 
					"timestamp", "candleAccTradePrice", "candleAccTradeVolume", "prevClosingPrice", "changePrice", "changeRate", "convertedTradePrice"})
public class Days extends Candle {

	/**
	 * constructor for inheritance
	 * @param prevClosingPrice
	 * @param changePrice
	 * @param changeRate
	 * @param convertedTradePrice
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
	private Days(Double prevClosingPrice, Double changePrice, Double changeRate, Double convertedTradePrice,
				 String market, String candleDateTimeUtc, String candleDateTimeKst, 
				 Double openingPrice, Double highPrice, Double lowPrice, Double tradePrice,
				 long timestamp, Double candleAccTradePrice, Double candleAccTradeVolume){
		super(market, candleDateTimeUtc, candleDateTimeKst, openingPrice, highPrice, lowPrice, tradePrice, timestamp, candleAccTradePrice, candleAccTradeVolume);
		this.prevClosingPrice = prevClosingPrice;
		this.changePrice = changePrice;
		this.changeRate = changeRate;
		this.convertedTradePrice = convertedTradePrice;
	}
	
	/** 전일 종가(UTC 0시 기준) */
	@JsonProperty("prev_closing_price")
	private Double prevClosingPrice;
	
	/** 전일 종가 대비 변화 금액 */
	@JsonProperty("change_price")
	private Double changePrice;
	
	/** 전일 종가 대비 변화량 */
	@JsonProperty("change_rate")
	private Double changeRate;
					
	/** 종가 환산 화폐 단위로 환산된 가격(요청에 convertingPriceUnit 파라미터 없을 시 해당 필드 포함되지 않음.) */	
	@JsonProperty("converted_trade_price")
	private Double convertedTradePrice;

}
