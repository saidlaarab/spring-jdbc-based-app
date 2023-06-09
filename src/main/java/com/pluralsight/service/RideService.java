package com.pluralsight.service;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideService {

	List<Ride> getRides();

	Ride createNewRide(Ride newRide);
	
	Ride getRideById(Integer id);

	Ride updateRide(Ride updatedRide);

	Object batchUpdate();

	Object deleteRide(Integer id);

}