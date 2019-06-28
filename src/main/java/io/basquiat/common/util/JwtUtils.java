package io.basquiat.common.util;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import io.basquiat.common.code.AlgorithmEnum;
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
	 * accessKey와 secretKey로 jwt를 생성한다.
	 * upbit spec에 맞춰서 만든다. 
	 * 발급된 secret key는 base64로 encoding 되어있지 않기 때문에 다음과 같이 생성한다.
	 * 거래소마다 다를 수 있기 때문에 이 부분은 그에 맞춰 수정하던가 따로 메소드를 만드는게 유리.
	 * @param apiAccessKey
	 * @param apiSecretKey
	 * @param payload
	 * @return String
	 */
	public static String createJwt(String apiAccessKey, String apiSecretKey, Map<String, Object> payload) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	    byte[] apiKeySecretBytes = apiSecretKey.getBytes();
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return "Bearer " + Jwts.builder()
							   .setClaims(payload)
							   .signWith(signatureAlgorithm, signingKey).compact();
	}
	
	
	/**
	 * 
	 * queryparam이 없는 경우
	 * @param apiAccessKey
	 * @param apiSecretKey
	 * @return String
	 */
	public static String createJwtWithoutQueryParameters(String apiAccessKey, String apiSecretKey) {
		// payload 생성
		// nonce부분은 new Date()의 타임으로도 가능하지만 업비트는 uuid를 권장한다.
		Map<String, Object> payload = new HashMap<>();
		payload.put("access_key", apiAccessKey);
		payload.put("nonce", UUID.randomUUID().toString());
		return JwtUtils.createJwt(apiAccessKey, apiSecretKey, payload);
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param apiAccessKey
	 * @param apiSecretKey
	 * @param queryParam
	 * @return String
	 */
	public static String createJwWithQueryParameters(String apiAccessKey, String apiSecretKey, String queryParam) {
		// payload 생성
		// nonce부분은 new Date()의 타임으로도 가능하지만 업비트는 uuid를 권장한다.
		// uri에 처음 붙은 ?가 있다면 이 부분은 잘라낸다.
		queryParam = queryParam.indexOf("?") == 0 ? queryParam.substring(1) : queryParam;
		Map<String, Object> payload = new HashMap<>();
		payload.put("access_key", apiAccessKey);
		payload.put("nonce", UUID.randomUUID().toString());
		payload.put("query_hash", CryptoUtils.createHashBySHA512(queryParam));
		payload.put("query_hash_alg", AlgorithmEnum.SHA512.name());
		return JwtUtils.createJwt(apiAccessKey, apiSecretKey, payload);
	}
	

}
