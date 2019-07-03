package io.basquiat.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * 
 * 사용하지 않음.
 * 애초에 @RequestBody의 필수 필드를 체크하기 위해 만들었으나...
 * 에러는 업비트에서 넘어오는 에러를 그대로 보여주고 말것이다...
 * 흔적만 남겨두자...
 * 
 * created by basquiat
 *
 */
@Component
public class ErrorAttributes extends DefaultErrorAttributes {
	
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
    	//Throwable ex = getError(request);
    	Map<String, Object> attributes = new LinkedHashMap<String, Object>();
        attributes.put("status", HttpStatus.BAD_REQUEST.value());
        attributes.put("message", "bad");
        return attributes;
    }

}
