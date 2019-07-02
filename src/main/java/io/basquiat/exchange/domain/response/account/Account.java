package io.basquiat.exchange.domain.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * 
 * 전체 계좌 조회 응답 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class Account {

	/** 화폐를 의미하는 영문 대문자 코드 */
	private String currency;
	
	/** 평단가 기준 화폐 */
	@JsonProperty("unit_currency")
	private String unitCurrency;

	/** 주문가능 금액/수량 */
    private String balance;
    
    /** 주문 중 묶여있는 금액/수량 */
    private String locked;
    
	/** 매수평균가 */
	@JsonProperty("avg_buy_price")
	private String avgBuyPrice;
	
	/** 매수평균가 수정 여부 */
	@JsonProperty("avg_buy_price_modified")
	private boolean avgBuyPriceModified;

}
