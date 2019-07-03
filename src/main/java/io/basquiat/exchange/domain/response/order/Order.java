package io.basquiat.exchange.domain.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * 주문 정보 응답 도메인 Immutable
 * 
 * 주문 취소 접보, 주문 리스트 조회에 매핑되는 도메인
 * 
 * created by basquiat
 *
 */
@Value
public class Order {

	/** 주문의 고유 아이디 */
	@JsonProperty("uuid")
	private String uuId;

	/** 주문 종류 */
	private String side;

	/** 주문 방식 */
	@JsonProperty("ord_type")
	private String ordType;

	/** 주문 당시 화폐 가격 */
	private String price;

	/** 주문 상태 */
	private String state;

	/** 마켓의 유일키 */
	private String market;

	/** 주문 생성 시간 */
	@JsonProperty("created_at")
	private String createdAt;

	/** 사용자가 입력한 주문 양 */
	private String volume;

	/** 체결 후 남은 주문 양 */
	@JsonProperty("remaining_volume")
	private String remainingVolume;

	/** 수수료로 예약된 비용 */
	@JsonProperty("reserved_fee")
	private String reservedFee;

	/** 남은 수수료 */
	@JsonProperty("remaining_fee")
	private String remainingFee;

	/** 사용된 수수료 */
	@JsonProperty("paid_fee")
	private String paidFee;

	/** 거래에 사용중인 비용 */
	private String locked;

	/** 체결된 양 */
	@JsonProperty("executed_volume")
	private String executedVolume;

	/** 해당 주문에 걸린 체결 수 */
	@JsonProperty("trades_count")
	private int tradesCount;
	
}
