package io.basquiat.exchange.domain.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * @See OrderChance
 * 
 * AskAccount와 BidAccount는 같은 필드지만 구분하기 위해 따라 생
 * 
 * created by basquiat
 *
 */
@Value
public class AskAccount {

	/** 화폐를 의미하는 영문 대문자 코드 */
	private String currency;

	/** 주문가능 금액/수량 */
	private String balance;

	/** 주문 중 묶여있는 금액/수량 */
	private String locked;

	/** 매수평균가 */
	@JsonProperty("avg_krw_buy_price")
	private String avgKrwBuyPrice;

	/** 매수평균가 수정 여부 */
	private boolean modified;

}
