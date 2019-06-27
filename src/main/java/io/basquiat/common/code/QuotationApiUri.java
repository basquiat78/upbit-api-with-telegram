package io.basquiat.common.code;

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
	CANDLES_MINUTES("/candles/minutes/{minutes}", "minutes"),
	
	CANDLES_DAYS("/candles/days", "days"),
	
	CANDLES_WEEKS("/candles/weeks", "weeks"),
	
	CANDLES_MONTHS("/candles/months", "months"),
	
	/** trade */
	TRADE_TICK("/trades/ticks", "당일 체결 내역"),
	
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
