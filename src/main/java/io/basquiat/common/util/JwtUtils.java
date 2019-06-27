package io.basquiat.common.util;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * CommonUtils
 * 
 * created by basquiat
 *
 */
public class JwtUtils {

	/**
	 * 
	 * accessKey와 secretKey로 jwt를 생성한다.
	 * upbit spec에 맞춰서 만든다. 
	 * 발급된 secret key는 base64로 encoding 되어있지 않기 때문에 다음과 같이 생성한다.
	 * 거래소마다 다를 수 있기 때문에 이 부분은 그에 맞춰 수정하던가 따로 메소드를 만드는게 유리.
	 * 
	 * @param apiAccessKey
	 * @param apiSecretKey
	 * @return String
	 */
	public static String createJwt(String apiAccessKey, String apiSecretKey) {

		// payload 생성
		// nonce부분은 new Date()의 타임으로도 가능하지만 업비트는 uuid를 권장한다.
		Map<String, Object> payload = new HashMap<>();
		payload.put("access_key", apiAccessKey);
		payload.put("nonce", UUID.randomUUID().toString());
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	    byte[] apiKeySecretBytes = apiSecretKey.getBytes();
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	    
		return "Bearer " + Jwts.builder()
							   .setClaims(payload)
							   .signWith(signatureAlgorithm, signingKey).compact();
		
	}

}
