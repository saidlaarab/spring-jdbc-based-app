package com.pluralsight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pluralsight.model.Ride;
import com.pluralsight.service.RideService;

@Controller
public class RideController {

	@Autowired
	private RideService rideService;
	
	@RequestMapping(value = "/rides", method = RequestMethod.POST)
	public @ResponseBody List<Ride> getRides() {
		return rideService.getRides();
	}
	
	@PostMapping("/ride")
	public @ResponseBody Ride createRide(@RequestBody Ride newRide) {
		return rideService.createNewRide(newRide);
	}
	
	@GetMapping("/ride/{id}")
	public @ResponseBody Ride getRide(@PathVariable("id") Integer id) {
		return rideService.getRideById(id);
	}
	
	@PutMapping("/ride")
	public @ResponseBody Ride updateRide(@RequestBody Ride updatedRide) {
		return rideService.updateRide(updatedRide);
	}
	
	@GetMapping("/update/batch")
	public @ResponseBody Object batchUupdate() {
		return rideService.batchUpdate();
	}
	
	@DeleteMapping("/ride/{id}")
	public @ResponseBody Object deleteRideById(@PathVariable("id") Integer id) {
		return rideService.deleteRide(id);
	}
}
