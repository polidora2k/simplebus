package com.polidoraian.simplebus.controller;

import com.polidoraian.simplebus.dto.RouteDTO;
import com.polidoraian.simplebus.dto.StopDTO;
import com.polidoraian.simplebus.dto.StudentDTO;
import com.polidoraian.simplebus.service.DriverService;
import com.polidoraian.simplebus.service.RouteService;
import com.polidoraian.simplebus.service.StopService;
import com.polidoraian.simplebus.service.StudentService;
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
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	StopService stopService;
	
	@Autowired
	RouteService routeService;
	
	@Autowired
	DriverService driverService;
	
	@PreAuthorize("hasAuthority('PARENT')")
	@GetMapping("/parent/student/{id}")
	public ModelAndView getStudent(@PathVariable Integer id) {
		ModelAndView response = new ModelAndView();
		response.setViewName("student");
		
		StudentDTO sd = studentService.getStudent(id);
		
		StopDTO stop = stopService.getStop(sd.getStopId());
		
		RouteDTO route = routeService.getRoute(stop.getRouteId());
		
		response.addObject("student", sd);
		response.addObject("route", route);
		response.addObject("stop", stop);
		response.addObject("status", studentService.getStudentStatus(sd));
		response.addObject("driver", driverService.getDriverForRoute(route.getId()));
		
		return response;
	}
	
	@ResponseBody
	@GetMapping("/changeriding")
	public void changeRiding(@RequestParam Integer id, @RequestParam Boolean riding) {
		studentService.changeRiding(id, riding);
	}
}
