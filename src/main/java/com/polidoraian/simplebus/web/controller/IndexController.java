package com.polidoraian.simplebus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView response = new ModelAndView();
		response.setViewName("index");
		response.addObject("name", "Ian");
		log.debug("Successfully showing page");
		
		return response;
	}
	
	@GetMapping("/dashboard")
	public ModelAndView dash() {
		ModelAndView response = new ModelAndView();
		response.setViewName("parent_dashboard");
		return response;
	}
}
