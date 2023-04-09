package com.pluralsight.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Test(timeout=10_000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8082/ride_tracker/rides", HttpMethod.POST,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}
	
	@Test(timeout=8000)
	public void testCreateNewRide() {
		RestTemplate restTemplate = new RestTemplate();
		
		Ride ride = new Ride();
		ride.setName("RUssia trip ride");
		ride.setDuration(35);
		
		ride = restTemplate.postForObject("http://localhost:8082/ride_tracker/ride", ride, Ride.class);
		
		System.out.println("Saved ride : " + ride);
	}
	
	@Test(timeout=9000)
	public void testGetRideById() {
		Ride ride = restTemplate.getForObject("http://localhost:8082/ride_tracker/ride/1", Ride.class);
		System.out.println("Fetched ride : " + ride);
	}
	
	@Test(timeout=9000)
	public void testUpdateRide() {
		Ride ride = restTemplate.getForObject("http://localhost:8082/ride_tracker/ride/1", Ride.class);
		
		ride.setDuration(ride.getDuration()+10);
		
		restTemplate.put("http://localhost:8082/ride_tracker/ride", ride);
		
		System.out.println("updated ride : " + ride);
	}
	
	@Test(timeout=9000)
	public void testUpdateBatch() {
		restTemplate.getForObject("http://localhost:8082/ride_tracker/update/batch", Object.class);
		
	}
	
	@Test(timeout=9000)
	public void testDelete() {
		restTemplate.delete("http://localhost:8082/ride_tracker/ride/20");
		
	}
}
