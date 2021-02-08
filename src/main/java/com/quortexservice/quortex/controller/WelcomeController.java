package com.quortexservice.quortex.controller;

import com.quortexservice.quortex.exception.QuortexException;
import com.quortexservice.quortex.model.*;
import com.quortexservice.quortex.service.QuestionService;
import com.quortexservice.quortex.util.ConstantUtil;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WelcomeController {
	
	private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

	@GetMapping(value = ConstantUtil.WELCOME_ENDPOINT, produces = "text/plain")
	@ResponseBody
	public ResponseEntity  welcomeToQuortex() throws QuortexException {
		log.info("Calling "+ ConstantUtil.WELCOME_ENDPOINT +" endpoint");
		return ResponseEntity.status(ConstantUtil.BACKEND_SERVICE_LIVE_SUCCESS_CODE).body("Welcome to Quortex Server....");
	}
}
