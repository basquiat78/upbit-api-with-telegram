package io.basquiat.common.generator.domain;

import io.basquiat.exchange.domain.ExchangeQuery;
import lombok.Data;

/**
 * 
 * 생성 요청 도메인
 * 
 * created by basquiat
 *
 */
@Data
public class RequestQuery {

	/** access key */
	private String accessKey;
	
	/** secret key */
	private String secretKey;
	
	/** 생성할 때 필요한 query 정보를 담은 객 */
	private ExchangeQuery query;
	
}
