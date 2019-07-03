package io.basquiat.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * 사용하지 않음....
 * 테스트해봤는데 먹히질 않는다.... 흔적만 남겨두자..
 * 
 * created by basquiat
 *
 */
@ControllerAdvice
public class RequestValidExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		// 유효성 검사에 걸리는 필드 정보를 맵에 담는다.
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
															  String fieldName = ((FieldError) error).getField();
															  String errorMessage = error.getDefaultMessage();
															  errors.put(fieldName, errorMessage);
												 	});
		return errors;
	}
	
}
