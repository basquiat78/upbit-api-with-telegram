package io.basquiat.common.generator.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 생성한 jwt를 담을 응답 도메인
 * 
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInformation {

	/** jwt */
	private String jwt;
	
}
