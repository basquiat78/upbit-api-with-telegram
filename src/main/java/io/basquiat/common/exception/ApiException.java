package io.basquiat.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * 
 * 
 * Upbit ResponseStatus Error Exception
 * 
 * created by basquiat
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiException extends ResponseStatusException {

	private static final long serialVersionUID = -481314952407166188L;

	public ApiException(HttpStatus httpStatus, String message) {
		super(httpStatus, message);
	}

}
