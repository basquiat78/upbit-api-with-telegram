package io.basquiat.exchange.domain;

import io.basquiat.common.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ExchangeQuery {

	/** market e.g KRW-BTC */
	private String market;
	
	/**
	 * domain정보로 uri에 붙을 queryparam을 만든다.
	 * @return String
	 */
	public String generateQueryParam() {
		return CommonUtils.createQueryParam(this); 
	}
	
}
