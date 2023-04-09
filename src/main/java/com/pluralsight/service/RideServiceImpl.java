package com.pluralsight.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;

@Service("rideService")
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;
	
	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}

	@Override
	public Ride createNewRide(Ride newRide) {
		return rideRepository.createNewRide(newRide);
	}
	
	@Override
	public Ride getRideById(Integer id) {
		return rideRepository.getRide(id);
	}

	@Override
	public Ride updateRide(Ride updatedRide) {
		return rideRepository.updateRide(updatedRide);
	}

	@Override
	public Object batchUpdate() {
		final List<Ride> rides = getRides();
		final List<Object[]> batchArgs = rides.stream().map(ride -> new Object[]{LocalDateTime.now() , ride.getId()}).collect(Collectors.toList());
		return rideRepository.updateAll(batchArgs);
	}
	
	@Override
	public Object deleteRide(Integer id) {
		return rideRepository.deleteById(id);
	}
}
