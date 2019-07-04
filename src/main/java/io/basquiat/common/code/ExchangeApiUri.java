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
	
	/** 주문 */
	INDIVIDUALORDER("/order", "개별 주문 조회"),
	
	ORDERCANCEL("/order", "주문 취소 접수"),
	
	ORDERCHANCE("/orders/chance", "주문 가능 정보"),
	
	ORDERPLACE("/orders", "주문하기"),
	
	ORDERLIST("/orders", "주문 조회하기"),
	
	/** 출금 */
	WITHDRAWLIST("/withdraws", "출금 리스트 조회"),
	
	INDIVIDUALWITHDRAW("/withdraw", "개별 출금 조회"),
	
	WITHDRAWCHANCE("/withdraws/chance", "출금 가능 정보"),
	
	WITHDRAWCOIN("/withdraws/coin", "코인 출금하기"),
	
	WITHDRAWKRW("/withdraws/krw", "원화 출금하기");
	
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
