package io.basquiat.exchange.domain.response.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * 마켓 정보 도메인 Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class Market {

	/** 마켓의 유일 키 */
	private String id;

	/** 마켓 이름 */
	private String name;

	/** 지원 주문 방식 */
	@JsonProperty("order_types")
	private List<String> orderTypes;

	/** 지원 주문 종류 */
	@JsonProperty("order_sides")
	private List<String> ordeSides;

	/** 매수 시 제약사항 도메인 */
	private Bid bid;

	/** 매도 시 제약사항 도메인 */
	private Ask ask;

	/** 최대 매도/매수 금액 */
	@JsonProperty("max_total")
	private String maxTotal;

	/** 마켓 운영 상태 */
	private String state;

}
