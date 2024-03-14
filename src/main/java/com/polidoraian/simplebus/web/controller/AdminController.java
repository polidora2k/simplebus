package com.polidoraian.simplebus.web.controller;



import com.polidoraian.simplebus.shared.dto.RouteDTO;
import com.polidoraian.simplebus.shared.dto.StopCreationDTO;
import com.polidoraian.simplebus.shared.dto.UserDTO;
import com.polidoraian.simplebus.shared.security.AuthenticatedUserService;
import com.polidoraian.simplebus.shared.service.impl.RouteServiceImpl;
import com.polidoraian.simplebus.shared.service.impl.StopServiceImpl;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
	private final AuthenticatedUserService aus;
	private final StopServiceImpl stopService;
	private final RouteServiceImpl routeService;
	
	public AdminController(@Nonnull final AuthenticatedUserService aus,
						   @Nonnull final StopServiceImpl stopService,
						   @Nonnull final RouteServiceImpl routeService) {
		this.aus = aus;
		this.stopService = stopService;
		this.routeService = routeService;
	}
	
	@GetMapping("/")
	public ModelAndView showAdminPage() {
		UserDTO currentUser = aus.getCurrentUser();
		ModelAndView response = new ModelAndView();
		response.setViewName("admin_dashboard");

		response.addObject("user", currentUser);

		return response;
	}
	
	@GetMapping("/add-route")
	public ModelAndView showAddRoutePage() {
		UserDTO currentUser = aus.getCurrentUser();
		ModelAndView response = new ModelAndView();
		response.addObject("newRoute", new RouteDTO());
		response.setViewName("add_route");
		
		response.addObject("unassignedStops", stopService.getUnassignedStops());

		return response;
	}
	
	@PostMapping("/add-route")
	public ModelAndView addRoute(@RequestParam RouteDTO newRoute) {
		UserDTO currentUser = aus.getCurrentUser();
		ModelAndView response = new ModelAndView();
		response.addObject("newRoute", newRoute);
		
		RouteDTO route = routeService.addRoute(newRoute.getName());
		
		response.setViewName("redirect:/admin/add-route/" + route.getId() + "/add-stops");
		
		return response;
	}
	
	@GetMapping("/add-route/{id}/add-stops")
	public ModelAndView showAddRouteStopsPage(@PathVariable Integer id) {
		ModelAndView response = new ModelAndView();
		response.setViewName("add_stop");
		response.addObject("routeId", id);
		
		return response;
	}
	
	@GetMapping("/edit-routes")
	public ModelAndView showEditRoutesPage() {
		ModelAndView response = new ModelAndView();
		response.setViewName("edit_routes");
		response.addObject("routes", routeService.getAllRoutes());
		
		return response;
	}
	
	@PostMapping("/add-route/{id}/add-stops")
	public ModelAndView addStop(@Valid StopCreationDTO stopCreationDTO, BindingResult result, @PathVariable Integer id) {
		ModelAndView response = new ModelAndView();
		log.debug("addStudent Handler method.");
		
		
		for (ObjectError e : result.getAllErrors()) {
			if (e instanceof FieldError) {
				log.debug(((FieldError) e).getField() + ": " + e.getDefaultMessage());
			}
		}
		
		if (!result.hasErrors()) {
			stopService.addStop(stopCreationDTO, id);
			response.setViewName("redirect:/admin/add-route/" + id + "/add-stops");
			log.debug("Successful added stop");
		} else {
			response.addObject("form", stopCreationDTO);
			for (ObjectError e : result.getAllErrors()) {
				if (e instanceof FieldError) {
					response.addObject(((FieldError) e).getField() + "Message", e.getDefaultMessage());
				}
			}
			response.setViewName("add_stop");
		}
		
		
		return response;
	}
	
	@GetMapping("/reset-routes")
	public ModelAndView resetRoutes() {
		UserDTO currentUser = aus.getCurrentUser();
		ModelAndView response = new ModelAndView();
		response.setViewName("admin_dashboard");

		response.addObject("user", currentUser);
		response.addObject("success", routeService.resetRoutes());
		
		return response;
	}
}
