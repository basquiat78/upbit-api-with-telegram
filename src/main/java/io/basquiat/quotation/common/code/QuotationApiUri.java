package io.basquiat.quotation.common.code;

/**
 * 
 * upbit QuotationApiUri uri
 * 
 * created by basquiat
 *
 */
public enum QuotationApiUri {

	/** Martket */
	MARKET_ALL("/market/all", "마켓 코드 조회"),

	/** Candle */
	CANDLE_MINUTES("/candles/minutes/{minutes}", "분(Minute) 캔들"),
	
	CANDLE_DAYS("/candles/days?market={market}", "일(Day) 캔들"),
	
	CANDLE_WEEKS("/candles/weeks?market={market}", "주(Week) 캔들"),
	
	CANDLE_MONTHS("/candles/months?market={market}", "월(Month) 캔들"),
	
	/** trade */
	TRADE_TICK("/trades/ticks?market={market}", "당일 체결 내역"),
	
	/** tick */
	TICKER("/ticker?markets={market}", "현재가 정보"),
	
	/** orderbook */
	ORDERBOOK("/orderbook?markets={market}", "호가 정보 조회");
	
	/** uri */
	public String URI;
	
	/** uri 설명 */
	public String description;

	/**
	 * enum constructor
	 * @param uri
	 * @param description
	 */
	QuotationApiUri(String uri, String description) {
		this.URI = uri;
		this.description = description;
	}

}
