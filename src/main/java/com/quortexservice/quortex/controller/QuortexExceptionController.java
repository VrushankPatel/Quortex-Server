package com.quortexservice.quortex.controller;

import com.quortexservice.quortex.exception.QuortexException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@CrossOrigin
@ControllerAdvice
public class QuortexExceptionController {

	
	private static final Logger log = LoggerFactory.getLogger(QuortexExceptionController.class);

	@ExceptionHandler(value = QuortexException.class)
	public ResponseEntity<Object> emailExisEexception(QuortexException exception) {
		log.info("QuortexExceptionController:: handle QuortexException["+exception.getMessage()+"]");
		return new ResponseEntity<>(exception.getExceptionResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> exception(Exception exception) {
		log.info("QuortexExceptionController:: handle Exception["+exception.getMessage()+"]");
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
