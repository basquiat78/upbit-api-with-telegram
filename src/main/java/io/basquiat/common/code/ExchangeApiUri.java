package io.basquiat.common.code;

/**
 * 
 * upbit QuotationApiUri uri
 * 
 * created by basquiat
 *
 */
public enum ExchangeApiUri {

	/** Martket */
	ACCOUNTS("/accounts", "전체 계좌 조회");
	
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
