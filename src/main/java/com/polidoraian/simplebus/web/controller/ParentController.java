package com.polidoraian.simplebus.web.controller;


import com.polidoraian.simplebus.shared.dto.StudentCreationDTO;
import com.polidoraian.simplebus.shared.dto.UserDTO;
import com.polidoraian.simplebus.shared.security.AuthenticatedUserService;
import com.polidoraian.simplebus.shared.service.impl.ParentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PreAuthorize("hasAuthority('PARENT')")
public class ParentController {

	@Autowired
	private AuthenticatedUserService aus;

	@Autowired
	private ParentServiceImpl parentService;
	
	

	@GetMapping("/parent")
	public ModelAndView showParentPage() {
		UserDTO currentUser = aus.getCurrentUser();
		ModelAndView response = new ModelAndView();
		response.setViewName("parent_dashboard");

		response.addObject("students", parentService.getAllStudents(aus.getCurrentUser().getId()));
		response.addObject("ridingStudents", parentService.getRidingStudents(aus.getCurrentUser().getId()));

		response.addObject("user", currentUser);

		return response;
	}

	@GetMapping("/parent/addstudent")
	public ModelAndView showAddStudentPage() {
		ModelAndView response = new ModelAndView();
		response.setViewName("add_student");

		return response;
	}

	@PostMapping("/parent/addstudent")
	public ModelAndView addStudent(@Valid StudentCreationDTO studentCreationDTO, BindingResult result) {
		ModelAndView response = new ModelAndView();
		log.debug("addStudent Handler method.");
		response.addObject("form", studentCreationDTO);

		for (ObjectError e : result.getAllErrors()) {
			if (e instanceof FieldError) {
				log.debug(((FieldError) e).getField() + ": " + e.getDefaultMessage());
			}
		}

		if (!result.hasErrors()) {
			parentService.addStudent(studentCreationDTO, aus.getCurrentUser().getId());

			response.setViewName("redirect:/parent");
			log.debug("Successful added student");
		} else {
			for (ObjectError e : result.getAllErrors()) {
				if (e instanceof FieldError) {
					response.addObject(((FieldError) e).getField() + "Message", e.getDefaultMessage());
				}
			}

			response.setViewName("add_student");
		}

		return response;
	}
}
