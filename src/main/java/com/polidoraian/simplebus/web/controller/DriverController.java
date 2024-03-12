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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PreAuthorize("hasAuthority('DRIVER')")
public class DriverController {

	@Autowired
	private AuthenticatedUserService aus;
	
	@Autowired
	private DriverServiceImpl driverService;
	
	@Autowired
	private RouteServiceImpl routeService;
	
	@Autowired
	private StopServiceImpl stopService;
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@GetMapping("/driver")
	public ModelAndView showDriverPage() {
		UserDTO currentUser = aus.getCurrentUser();
		ModelAndView response = new ModelAndView();
		response.setViewName("driver_dashboard");

		response.addObject("user", currentUser);
		response.addObject("routes", driverService.getRoutesForDriver(currentUser.getId()));

		return response;
	}
	
	@GetMapping("/driver/route/{id}")
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
	
	@GetMapping("/driver/route/{id}/onboarding")
	public ModelAndView showRouteOnboardingPage(@PathVariable Integer id) {
		ModelAndView response = new ModelAndView();
		response.setViewName("route_onboarding");
		
		RouteDTO route = routeService.getRoute(id);
		
		response.addObject("route", route);
		response.addObject("students", studentService.getStudentsForRoute(route));
		response.addObject("nextStop", stopService.getRouteStopByNumber(route.getId(), 1));
		
		
		return response;
	}
	
	@GetMapping("/driver/route/{routeId}/stop/{stopId}")
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
	@GetMapping("/completestop")
	public void completeStop(@RequestParam Integer stopId, @RequestParam Integer routeId) {
		stopService.completeStop(stopId);
		routeService.advanceStop(routeId, stopId);
	}
	
	@ResponseBody
	@GetMapping("/completeroute")
	public void completeRoute(@RequestParam Integer routeId) {
		routeService.completeRoute(routeId);
	}
	
	@ResponseBody
	@GetMapping("/startroute")
	public String startRoute(@RequestParam Integer routeId) {
		log.debug("Start Route method reached");
		routeService.startRoute(routeId);
		
		return "Successfully started";
	}
}
