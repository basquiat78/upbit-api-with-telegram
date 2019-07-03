package io.basquiat.common.code;

/**
 * 
 * upbit ExchangeApiUri uri
 * 
 * created by basquiat
 *
 */
public enum ExchangeApiUri {

	/** account */
	ACCOUNTS("/accounts", "전체 계좌 조회"),
	
	ORDERCHANCE("/orders/chance", "주문 가능 정보"),
	
	ORDERPLACE("/orders", "주문하기"),
	
	ORDERLIST("/orders", "주문 조회하기");
	
	/** uri */
	public String URI;
	
	/** uri 설명 */
	public String description;

	/**
	 * enum constructor
	 * @param uri
	 * @param description
	 */
	ExchangeApiUri(String uri, String description) {
		this.URI = uri;
		this.description = description;
	}

}
