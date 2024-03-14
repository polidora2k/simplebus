package com.polidoraian.simplebus.web.controller;

import java.util.List;

import com.polidoraian.simplebus.shared.dto.RouteDTO;
import com.polidoraian.simplebus.shared.dto.StopDTO;
import com.polidoraian.simplebus.shared.dto.UserDTO;
import com.polidoraian.simplebus.shared.security.AuthenticatedUserService;
import com.polidoraian.simplebus.shared.service.impl.DriverServiceImpl;
import com.polidoraian.simplebus.shared.service.impl.RouteServiceImpl;
import com.polidoraian.simplebus.shared.service.impl.StopServiceImpl;
import com.polidoraian.simplebus.shared.service.impl.StudentServiceImpl;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/driver")
@PreAuthorize("hasAuthority('DRIVER')")
public class DriverController {
	private final AuthenticatedUserService aus;
	private final DriverServiceImpl driverService;
	private final RouteServiceImpl routeService;
	private final StopServiceImpl stopService;
	private final StudentServiceImpl studentService;
	
	public DriverController(@Nonnull final AuthenticatedUserService aus,
							@Nonnull final DriverServiceImpl driverService,
							@Nonnull final RouteServiceImpl routeService,
							@Nonnull final StopServiceImpl stopService,
							@Nonnull final StudentServiceImpl studentService) {
		this.aus = aus;
		this.driverService = driverService;
		this.routeService = routeService;
		this.stopService = stopService;
		this.studentService = studentService;
	}
	
	@GetMapping("/")
	public ModelAndView showDriverPage() {
		UserDTO currentUser = aus.getCurrentUser();
		ModelAndView response = new ModelAndView();
		response.setViewName("driver_dashboard");

		response.addObject("user", currentUser);
		response.addObject("routes", driverService.getRoutesForDriver(currentUser.getId()));

		return response;
	}
	
	@GetMapping("/route/{id}")
	public ModelAndView showRouteInfoPage(@PathVariable Integer id) {
		ModelAndView response = new ModelAndView();
		response.setViewName("route");
		
		RouteDTO route = routeService.getRoute(id);
		List<StopDTO> activeStops = stopService.getRouteStopsWithStudents(route.getId());
		
		response.addObject("route", route);
		response.addObject("stops", activeStops);
		response.addObject("stopLocations", stopService.getStopLocations(activeStops));
		
		return response;
	}
	
	@GetMapping("/route/{id}/onboarding")
	public ModelAndView showRouteOnboardingPage(@PathVariable Integer id) {
		ModelAndView response = new ModelAndView();
		response.setViewName("route_onboarding");
		
		RouteDTO route = routeService.getRoute(id);
		
		response.addObject("route", route);
		response.addObject("students", studentService.getStudentsForRoute(route));
		response.addObject("nextStop", stopService.getRouteStopByNumber(route.getId(), 1));
		
		
		return response;
	}
	
	@GetMapping("/route/{routeId}/stop/{stopId}")
	public ModelAndView showCurrentStop(@PathVariable Integer routeId, @PathVariable Integer stopId) {
		ModelAndView response = new ModelAndView();
		response.setViewName("stop_inprogress");
		
		RouteDTO route = routeService.getRoute(routeId);
		StopDTO currentStop = stopService.getStop(stopId);
		List<StopDTO> activeStops = stopService.getRouteStopsWithStudents(routeId);
		
		response.addObject("route", route);
		response.addObject("currentStop", currentStop);
		response.addObject("stopLocation", stopService.getStopLocation(currentStop));
		if (activeStops.indexOf(currentStop) == activeStops.size() - 1) {
			response.addObject("nextStop", null);
		} else {
			response.addObject("nextStop", activeStops.get(activeStops.indexOf(currentStop) + 1));
		}
		response.addObject("students", studentService.getStudentsByStop(currentStop));
		
		return response;
	}
	
	@ResponseBody
	@GetMapping("/complete-stop")
	public void completeStop(@RequestParam Integer stopId, @RequestParam Integer routeId) {
		stopService.completeStop(stopId);
		routeService.advanceStop(routeId, stopId);
	}
	
	@ResponseBody
	@GetMapping("/complete-route")
	public void completeRoute(@RequestParam Integer routeId) {
		routeService.completeRoute(routeId);
	}
	
	@ResponseBody
	@GetMapping("/start-route")
	public String startRoute(@RequestParam Integer routeId) {
		log.debug("Start Route method reached");
		routeService.startRoute(routeId);
		
		return "Successfully started";
	}
}
