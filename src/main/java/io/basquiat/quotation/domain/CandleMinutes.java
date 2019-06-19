package io.basquiat.quotation.domain;

import io.basquiat.quotation.common.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 일 캔들 query param info
 * 
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CandleMinutes {

	/** market e.g KRW-BTC */
	private String market;
	
	/** 마지막 캔들 시각 (exclusive). 포맷 : yyyy-MM-dd'T'HH:mm:ssXXX. 비워서 요청시 가장 최근 캔들 */
	private String to;
	
	/** 가져온 데이터 리스트 사이즈 */
	private int count;
	
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
