package com.polidoraian.simplebus.web.controller;

import com.polidoraian.simplebus.shared.security.RoleEnum;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.polidoraian.simplebus.shared.dto.UserCreationDTO;
import com.polidoraian.simplebus.shared.security.AuthenticatedUserService;
import com.polidoraian.simplebus.shared.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
	private final UserServiceImpl userService;
	private final AuthenticatedUserService aus;
	
	public UserController(@Nonnull final UserServiceImpl userService,
						  @Nonnull final AuthenticatedUserService aus) {
		this.userService = userService;
		this.aus = aus;
	}
	
	@GetMapping("/signup")
	public ModelAndView showSignUpPage() {
		ModelAndView response = new ModelAndView();
		response.addObject("newUser", new UserCreationDTO());
		response.addObject("roles", RoleEnum.values());
		response.setViewName("signup");
		log.debug("Successfully showing signup page");

		return response;
	}

	@PostMapping("/signup")
	public ModelAndView submitSignUp(@Valid UserCreationDTO userCreationDTO, BindingResult result,
			HttpServletRequest request) {
		ModelAndView response = new ModelAndView();

		response.addObject("newUser", userCreationDTO);
		
		for (ObjectError e : result.getAllErrors()) {
			if (e instanceof FieldError) {
				log.debug(((FieldError) e).getField() + ": " + e.getDefaultMessage());
			}
		}

		if (!result.hasErrors()) {
			userService.signup(userCreationDTO);
			aus.loginNewUser(userCreationDTO, request);

			switch (userCreationDTO.getRole()) {
			case "Parent":
				response.setViewName("redirect:/parent");
				break;
			case "Driver":
				response.setViewName("redirect:/driver");
				break;
			default:
				break;
			}
			log.debug("Successful sign up");
		} else {
			for (ObjectError e : result.getAllErrors()) {
				if (e instanceof FieldError) {
					response.addObject(((FieldError) e).getField() + "Message", e.getDefaultMessage());
				}
			}
			
			response.setViewName("signup");
		}

		return response;
	}

	@GetMapping("/login")
	public ModelAndView showLoginPage() {
		ModelAndView response = new ModelAndView();
		response.setViewName("login");

		return response;
	}
}
