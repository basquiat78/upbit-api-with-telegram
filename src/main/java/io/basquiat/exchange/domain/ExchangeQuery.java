package io.basquiat.exchange.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.basquiat.common.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * exchange api query param domain
 * 
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeQuery {

	/** market e.g KRW-BTC */
	@JsonInclude(Include.NON_NULL)
	private String market;
	
	/** 주문 종류 (필수) bid:매수 | ask:매도 */
	@JsonInclude(Include.NON_NULL)
	private String side;

	/** 주문량 (지정가, 시장가 매도 시 필수) */
	@JsonInclude(Include.NON_NULL)
	private String volume;

	/**
	 * 주문 가격. (지정가, 시장가 매수 시 필수)
	 *	ex) KRW-BTC 마켓에서 1BTC당 1,000 KRW로 거래할 경우, 값은 1000 이 된다.
	 *	ex) KRW-BTC 마켓에서 1BTC당 매도 1호가가 500 KRW 인 경우, 시장가 매수 시 값을 1000으로 세팅하면 2BTC가 매수된다.
	 *	(수수료가 존재하거나 매도 1호가의 수량에 따라 상이할 수 있음)
	 *
	 * UPBIT NOTICE
	 * 	원화 마켓에서 주문을 요청 할 경우, 원화 마켓 주문 가격 단위 를 확인하여 값을 입력해주세요.
	 *  @See https://docs.upbit.com/v1.0/docs/%EC%9B%90%ED%99%94-%EB%A7%88%EC%BC%93-%EC%A3%BC%EB%AC%B8-%EA%B0%80%EA%B2%A9-%EB%8B%A8%EC%9C%84
	 */
	@JsonInclude(Include.NON_NULL)
	private String price;

	/**
	 * 
	 * 주문 타입 (필수)
	 * limit : 지정가 주문
	 * price : 시장가 주문(매수)
	 * market : 시장가 주문(매도)
	 * 
	 * UPBIT NOTICE
	 *  시장가 주문
	 *	시장가 주문은 ord_type 필드를 price or market 으로 설정해야됩니다.
	 *	매수 주문의 경우 ord_type을 price로 설정하고 volume을 null 혹은 제외해야됩니다.
	 *	매도 주문의 경우 ord_type을 market로 설정하고 price을 null 혹은 제외해야됩니다.
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	private String ord_type;

	/** 
	 * 조회용 사용자 지정값 (선택) 
	 * 예를 들면 일반적으로 주문을 할때 업비트에서는 uuid를 제너레이터를 통해 생성한다.
	 * 이것을 사용자가 자신만의 유니크한 값으로 설정할 수 있다.
	 * 조회할때 이 유니크한 값으로 조회가 가능하다.
	 * 
	 * UPBIT NOTICE
	 * 	identifier 파라미터 사용
	 *		identifier는 서비스에서 발급하는 uuid가 아닌 이용자가 직접 발급하는 키값으로, 주문을 조회하기 위해 할당하는 값입니다. 해당 값은 사용자의 전체 주문 내 유일한 값을 전달해야하며, 비록 주문 요청시 오류가 발생하더라도 같은 값으로 다시 요청을 보낼 수 없습니다.
	 *		주문의 성공 / 실패 여부와 관계없이 중복해서 들어온 identifier 값에서는 중복 오류가 발생하니, 매 요청시 새로운 값을 생성해주세요.
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	private String identifier;

	/** identifier 배열정보, 정보는 ','자로 붙여서 */
	@JsonInclude(Include.NON_NULL)
	private String identifiers;
	
	/** 주문의 고유 아이디 */
	@JsonInclude(Include.NON_NULL)
	private String uuid;
	
	/** uuid 배열정보, 정보는 ','자로 붙여서 */
	@JsonInclude(Include.NON_NULL)
	private String uuids;
	
	/**
	 *  주문 상태 <-
	 *  	wait : 체결 대기 (default)
	 *		done : 전체 체결 완료
	 *		cancel : 주문 취소
	 *
	 *	출금 상태
	 *		submitting : 처리 중
	 *		submitted : 처리 완료
	 *		almost_accepted : 출금대기중
	 *		rejected : 거부
	 *		accepted : 승인됨
	 *		processing : 처리 중
	 *		done : 완료
	 *		canceled : 취소됨
	 *  
	 */
	@JsonInclude(Include.NON_NULL)
	private String state;

	/** 페이지 수, default: 1 */
	@JsonInclude(Include.NON_NULL)
	private Integer page;

	/**
	 * 
	 * 정렬 방식
	 *	asc : 오름차순 (default)
	 *	desc : 내림차순
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	private String order_by;
	
	/** Currency 코드 */
	@JsonInclude(Include.NON_NULL)
	private String currency;

	/** 개수 제한 (default: 100, max: 100) */
	@JsonInclude(Include.NON_NULL)
	private Integer limit;

	/** 
	 * 입/출금 트랜잭션 아이디
	 * 새로 적용된 업비트 API에서 입출금 리스트 조회 관련 txid조회가 가능하기 때문에 추가
	 */
	@JsonInclude(Include.NON_NULL)
	private String txid;
	
	/** txid 배열정보, 정보는 ','자로 붙여서 */
	@JsonInclude(Include.NON_NULL)
	private String txids;
	
	/** 출금 코인/원화 수량 */
	@JsonInclude(Include.NON_NULL)
	private String amount;
	
	/** 출금 가능 주소에 등록된 출금 주소 */
	@JsonInclude(Include.NON_NULL)
	private String address;

	/** 2차 출금 주소 (필요한 코인에 한해서) */
	@JsonInclude(Include.NON_NULL)
	private String secondary_address;

	/** 출금 유형 default : 일반출금 | default : 일반출금 */
	@JsonInclude(Include.NON_NULL)
	private String transaction_type;
	
	/**
	 * domain정보로 uri에 붙을 queryparam을 만든다.
	 * @return String
	 */
	public String generateQueryParam() {
		return CommonUtils.createQueryParam(this); 
	}
	
	/**
	 * 간지나는 방법이 없나??
	 * identifiers, uuids 변수는 인코딩하지 않는다.
	 */
	public void adjustEncode() {
		this.market = CommonUtils.encodingURL(this.market);
		this.side = CommonUtils.encodingURL(this.side);
		this.volume = CommonUtils.encodingURL(this.volume);
		this.price = CommonUtils.encodingURL(this.price);
		this.uuid = CommonUtils.encodingURL(this.uuid);
		this.identifier = CommonUtils.encodingURL(this.identifier);
		this.ord_type = CommonUtils.encodingURL(this.ord_type);
		this.order_by = CommonUtils.encodingURL(this.order_by);
	}

}
