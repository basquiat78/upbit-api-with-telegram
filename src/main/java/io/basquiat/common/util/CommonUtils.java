package io.basquiat.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.basquiat.common.code.ActionEnum;
import io.basquiat.common.code.DelimiterEnum;
import io.basquiat.quotation.domain.MarketAllStore;

/**
 * 
 * CommonUtils
 * 
 * created by basquiat
 *
 */
public class CommonUtils {

	/**
	 * 객체로 uri query param 생성
	 * @param object
	 * @return String
	 */
	public static String createQueryParam(Object object) {
		AtomicInteger index = new AtomicInteger(0);
		return Arrays.asList(object.getClass().getDeclaredFields()).stream()
																   .map(field -> {
																				   String keyValue = "";
																				   field.setAccessible(true);
																				   try {
																					   if(index.get() == 0) {
																						   if(field.get(object) != null) {
																							   if("uuids".equals(field.getName()) || "txids".equals(field.getName()) || "identifiers".equals(field.getName())) {
																								   AtomicInteger flag = new AtomicInteger(0);
																								   keyValue = Arrays.asList(((String) field.get(object)).split(","))
																										   			.stream().map(s -> {
																										   			 						if(flag.get() == 0) {
																											   			 						flag.set(1);
																											   			 						return DelimiterEnum.QUESTION_MARK.character
																													   			 						+ field.getName()+"[]"
																													   			 						+ DelimiterEnum.EQUALS_SIGN.character 
																													   			 						+ s.trim();
																											   			 					} else {
																												   			 					return DelimiterEnum.AMPERSAND.character
																													   			 						+ field.getName()+"[]"
																													   			 						+ DelimiterEnum.EQUALS_SIGN.character 
																													   			 						+ s.trim();
																											   			 					}
																										   			 					}
																										   					 	  )
																										   			 .collect(Collectors.joining(""));
																							   } else {
																								   keyValue = DelimiterEnum.QUESTION_MARK.character 
																										    + field.getName() 
																										    + DelimiterEnum.EQUALS_SIGN.character 
																										    + field.get(object);
																							   }
																							   index.set(1);
																						   }
																					   } else {
																						   if(field.get(object) != null) {
																							   if("uuids".equals(field.getName()) || "txids".equals(field.getName()) || "identifiers".equals(field.getName())) {
																								   keyValue = Arrays.asList(((String) field.get(object)).split(","))
																										   			.stream().map(s -> {
																														   				return DelimiterEnum.AMPERSAND.character
																											   			 						+ field.getName()+"[]"
																											   			 						+ DelimiterEnum.EQUALS_SIGN.character 
																											   			 						+ s.trim();
																										   			 					}
																										   					 	  )
																										   			 .collect(Collectors.joining(""));
																							   } else {
																								   keyValue = DelimiterEnum.AMPERSAND.character 
																										    + field.getName() 
																										    + DelimiterEnum.EQUALS_SIGN.character 
																										    + field.get(object);
																								   
																							   }
																						   }
																					   }
																				   } catch(IllegalArgumentException | IllegalAccessException e) {
																					   throw new RuntimeException(e.getMessage());
																				   }
																				   return keyValue;
																   		}
																   )
																   .collect(Collectors.joining(""));
	}

	/**
	 * 
	 * url인코딩이 필요한 쿼리 파라미터 값의 경우 url encoding을 해준다.
	 * 
	 * @param paramValue
	 * @return String
	 */
	public static String encodingURL(String paramValue) {
		String encodedURL = null;
		if(StringUtils.isEmpty(paramValue)) {
			return paramValue;
		}
			
		try {
			encodedURL = URLEncoder.encode(paramValue, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
		return encodedURL;
	}
	
	/**
	 * Object convert to json String
	 * 
	 * @param object
	 * @return String
	 * @throws JsonProcessingException
	 */
	public static String convertJsonStringFromObject(Object object) {
		String result = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}
	
	/**
	 * json String convert Object
	 * 
	 * @param content
	 * @param clazz
	 * @return T
	 * @throws Exception
	 */
	public static <T> T convertObjectFromJsonString(String content, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = (T) mapper.readValue(content, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return object;
	}
	
	/**
	 * Generic Collection Type covert method
	 * @param content
	 * @param clazz
	 * @return T
	 * @throws Exception
	 */
	public static <T> T convertObjectFromJsonStringByTypeRef(String content, TypeReference<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = mapper.readValue(content, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return object;
	}
	
	/**
	 * 넘어온 파라미터에서 마켓명 메세지 부분을 메모리에 올라온 마켓 정보와 비교하고 유효한 마켓인지 아닌지를 판별한다.
	 * @param marketArray
	 * @return
	 */
	public static Map<String, String> validateMarketName(String[] marketArray) {
		Map<String, String> result = new HashMap<>();
		if(marketArray.length < 1) { // 사이즈가 없다면 입력하지 않았기 때문에 메세지를 맵에 담아서 텔레그램으로 리플라이한다.
			result.put(ActionEnum.INVALID.name(), "You Must Send Market");
		} else {
			// 메모리에 올라온 업비트 마켓 정보를 순회하며 유효한 마켓을 구분자 (,)로 붙여서 반환한다.
			String validValue = Arrays.asList(marketArray).stream().map(marketName -> 
																					{
																					return MarketAllStore.getMarketAllStore().entrySet()
																															 .stream()
																												             .filter(marketAll -> marketAll.getKey().equals(marketName.trim()))
																												             .map(marketAll -> {
																												            	 			return marketAll.getKey();
																												             
																							             					 })
																												             .collect(Collectors.joining());
																					}
																		)
																	.filter(marketName -> !StringUtils.isEmpty(marketName))	
																	.collect(Collectors.joining(","));
			
			// 유효한 마켓으로 유효하지 않은 마켓명을 담는다.
			String invalidValue = Arrays.asList(marketArray).stream().filter(market -> !validValue.contains(market))
																 	 .collect(Collectors.joining(","));
			
			if(!StringUtils.isEmpty(validValue)) {
				result.put(ActionEnum.VALID.name(), validValue);
			}
			
			if(!StringUtils.isEmpty(invalidValue)) {
				result.put(ActionEnum.INVALID.name(), "Check Market Name : " + invalidValue);
			}
		}
		return result;
	}

}
