package com.quortexservice.quortex.controller;

import com.quortexservice.quortex.exception.QuortexException;
import com.quortexservice.quortex.model.*;
import com.quortexservice.quortex.service.QuestionService;
import com.quortexservice.quortex.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WelcomeController {
	
	private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

	@RequestMapping(value = ConstantUtil.WELCOME_ENDPOINT, method = RequestMethod.GET, produces = "text/plain")
	public String welcomeToQuortex() throws QuortexException {
		log.info("Calling "+ ConstantUtil.WELCOME_ENDPOINT +" endpoint");
		return "Welcome to Quortex....";
	}
}
