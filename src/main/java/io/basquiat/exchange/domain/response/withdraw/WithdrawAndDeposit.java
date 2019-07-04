package io.basquiat.exchange.domain.response.withdraw;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * 출금 정보 응답 도메인 Immutable
 * 
 * 개별 출금 접보, 출금 리스트 조회, 코인/원화 출금하기에 매핑되는 도메인
 * 개별 입금 정보, 입금 리스트 조회에도 매핑되는 도메인
 * 
 * created by basquiat
 *
 */
@Value
public class WithdrawAndDeposit {

	/** 입/출금 종류 */
	private String type;

	/** 입/출금의 고유 아이디 */
	@JsonProperty("uuid")
	private String uuId;

	/** 화폐를 의미하는 영문 대문자 코드 */
	private String currency;

	/** 입/출금의 트랜잭션 아이디 */
	@JsonProperty("txid")
	private String txId;

	/** 입/출금 상태 */
	private String state;

	/** 입/출금 생성 시간 */
	@JsonProperty("created_at")
	private String createdtAt;

	/** 입/출금 완료 시간 */
	@JsonProperty("done_at")
	private String doneAt;

	/** 입/출금 금액/수량 */
	private String amount;

	/** 입/출금 수수료 */
	private String fee;

	/** 원화 환산 가격 */
	@JsonProperty("krw_amount")
	@JsonInclude(Include.NON_NULL)
	private String krwAmount;

	/** 입/출금 유형 */
	@JsonProperty("transaction_type")
	private String transactionType;

}
