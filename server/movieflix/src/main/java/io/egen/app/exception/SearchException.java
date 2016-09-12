package io.egen.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class SearchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1246375930813790365L;

	public SearchException(String message) {
		super(message);
	}

}
