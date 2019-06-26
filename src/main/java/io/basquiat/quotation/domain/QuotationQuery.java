package io.basquiat.quotation.domain;

import io.basquiat.common.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * 캔들 query param info
 * 
 * 분/일/주/월 별로 공통된 부분도 있고 아닌 부분도 있지만 하나의 도메인에 다 때려받음.
 * 
 * 사용하지 않을 도메인
 * 
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
public class QuotationQuery {

	/** market e.g KRW-BTC */
	private String market;
	
	/** 마지막 캔들 시각 (exclusive). 포맷 : yyyy-MM-dd'T'HH:mm:ssXXX. 비워서 요청시 가장 최근 캔들 */
	private String to;
	
	/** 가져온 데이터 리스트 사이즈 */
	private int count;
	
	/** 종가 환산 화폐 단위 (생략 가능, KRW로 명시할 시 원화 환산 가격을 반환.) */
	private String convertingPriceUnit;
	
	/**
	 * domain정보로 uri에 붙을 queryparam을 만든다.
	 * @return String
	 */
	public String generateQueryParam() {
		return CommonUtils.createQueryParam(this); 
	}
	
	/**
	 * 
	 */
	public void adjustURLEncoding() {
		this.to = CommonUtils.encodingURL(to);
	}
	
}
