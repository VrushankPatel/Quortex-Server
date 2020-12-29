package com.quortexservice.quortex.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quortexservice.quortex.model.QuortexResponse;

public class QuortexException extends RuntimeException {

	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private final QuortexResponse exceptionResponse;
	
	public QuortexException(String errorMessage, Integer ErrorCode) {
		exceptionResponse = new QuortexResponse(errorMessage, ErrorCode,false,null); 
	}

	public QuortexResponse getExceptionResponse() {
		return exceptionResponse;
	}
	
}

